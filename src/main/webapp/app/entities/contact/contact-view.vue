<template>
  <div v-if="entityData">
    <EntityDetailsLayout
      :message-key="getTitle()"
      :back-button-path="getBackButtonPath()"
      :edit-router-name="getEditRouterName()"
      :edit-router-params="editRouterParams"
    >
      <DetailViewBuilder
        v-if="formConfig"
        :form-config="formConfig"
        :data="entityData"
        :group-type="groupType"
        :sub-category="subCategory"
        :sub-category-group="subCategoryGroup"
        :form-name="generatedFormName"
        @relation-saved="handleRelationSaved"
      />
      <!-- Kompakter Meta-Abschnitt unten -->
      <div class="mt-4">
        <Divider />
        <MetaInline
          :created-date="entityData.createdDate"
          :created-by="entityData.createdBy"
          :last-modified-date="entityData.lastModifiedDate"
          :last-modified-by="entityData.lastModifiedBy"
          :form-name="generatedFormName || (groupType && groupType.formName) || null"
        />
      </div>
    </EntityDetailsLayout>
  </div>
  <div v-else class="d-flex justify-content-center">
    <div class="spinner-border" role="status">
      <span class="visually-hidden">Loading...</span>
    </div>
  </div>
</template>

<script setup>

import { ref, computed, watch, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';

// Props definieren
const props = defineProps({
  entityid: {
    type: [String, Number],
    required: false
  }
});

import AlertService from '@/services/alert.service';

import DetailViewBuilder from '@/components/DetailViewBuilder.vue';
import EntityDetailsLayout from "@/core/layouts/entity-details-layout.vue";
import { useI18n } from 'vue-i18n';
import { extractEnglishNameFromTranslationKey } from '@/composables/entity-helper';
import Divider from '@/components/divider.vue';
import MetaInline from '@/components/MetaInline.vue';
import ContactService from '@/services/contact.service';

/** data */
const { t, te } = useI18n();
const route = useRoute();
const router = useRouter();
const formConfig = ref(null);
const entityData = ref(null);

const category = ref(null);
const subCategory = ref(null);
const subCategoryGroup = ref(null);
const groupType = ref(null);
const generatedFormName = ref(null);

const editRouterParams = computed(() => {
  return {
    entityid: entityData.value?.id
  }
});

const handleRelationSaved = (updatedEntity) => {
  entityData.value = updatedEntity;
  
  if (updatedEntity.groupType) {
    groupType.value = updatedEntity.groupType;
    subCategoryGroup.value = updatedEntity.groupType.subCategoryGroup;
    subCategory.value = updatedEntity.groupType.subCategoryGroup?.subCategory;
    category.value = updatedEntity.groupType.subCategoryGroup?.subCategory?.category;
  }
};

const loadEntityData = () => {
  if (route.params.entityid) {
    const entityId = parseInt(route.params.entityid);
    if (isNaN(entityId) || entityId <= 0) {
      AlertService.showError('Ungültige Entity-ID');
      return;
    }
    
    // Verwende ContactService mit relations=true
    const serviceCall = ContactService.find(entityId);
    
    serviceCall.then(response => {
        if (!response || !response.id) {
          throw new Error('Entity not found');
        }
        entityData.value = response;
        
        if (response.groupType) {
          groupType.value = response.groupType;
          
          // Set category hierarchy with fallbacks
          if (response.groupType.subCategoryGroup) {
            subCategoryGroup.value = response.groupType.subCategoryGroup;
            
            if (response.groupType.subCategoryGroup.subCategory) {
              subCategory.value = response.groupType.subCategoryGroup.subCategory;
              
              if (response.groupType.subCategoryGroup.subCategory.category) {
                category.value = response.groupType.subCategoryGroup.subCategory.category;
              }
            }
          }
          
          // Generiere formName basierend auf der Hierarchie
          const formNameToLoad = generateFormNameFromHierarchy(
            category.value,
            subCategory.value,
            subCategoryGroup.value,
            groupType.value
          );
          
          if (formNameToLoad) {
            groupType.value.formName = formNameToLoad;
            generatedFormName.value = formNameToLoad;
            return import(`@/shared/form-definitions/contact/${formNameToLoad}.json`);
          } else {
            throw new Error('Konnte formName nicht aus Hierarchie generieren');
          }
        } else {
          // Verwende Fallback-Formulardefinition für Kontakte
          return import(`@/shared/form-definitions/contact/contact.json`);
        }
      })
      .then(formData => {
        formConfig.value = formData.default || formData;
        if (!generatedFormName.value) {
          generatedFormName.value = (groupType.value && groupType.value.formName) || 'contact';
        }
      })
      .catch(err => {
        AlertService.showHttpError(this, err.response);
      });
  }
};

// Generiert den formName basierend auf der Hierarchie-Kombination
const generateFormNameFromHierarchy = (category, subCategory, subCategoryGroup, groupType) => {
  if (!groupType) {
    return null;
  }

  // Wenn die Hierarchie-Daten nicht verfügbar sind, verwende den formName aus der Datenbank
  if (!subCategory || !subCategoryGroup) {
    return groupType.formName;
  }

  // Generiere formName aus der Hierarchie mit translationKeys
  const entityName = groupType.entityName || 'contact';
  let formName = entityName;
  
  if (subCategory) {
    // Verwende translationKey für englischen Namen
    const subCategoryName = extractEnglishNameFromTranslationKey(subCategory.translationKey) || normalizeName(subCategory.name, 'subcategory');
    formName += `-${subCategoryName}`;
  }
  
  if (subCategoryGroup) {
    // Verwende translationKey für englischen Namen
    const subCategoryGroupName = extractEnglishNameFromTranslationKey(subCategoryGroup.translationKey) || normalizeName(subCategoryGroup.name, 'subcategorygroup');
    formName += `-${subCategoryGroupName}`;
  }
  
  if (groupType) {
    // Verwende translationKey für englischen Namen
    const groupTypeName = extractEnglishNameFromTranslationKey(groupType.translationKey) || normalizeName(groupType.name, 'grouptype');
    formName += `-${groupTypeName}`;
  }
  
  return formName;
};

// Normalisiert Namen für Datei-Namenskonvention (kebab-case)
const normalizeName = (name, type = 'default') => {
  if (!name) return '';
  
  // Konvertiere zu kebab-case und normalisiere
  return name
    .toLowerCase()
    .replace(/\s+/g, '-') // Leerzeichen zu Bindestrichen
    .replace(/[^a-z0-9-]/g, '') // Entferne Sonderzeichen
    .replace(/-+/g, '-') // Mehrere Bindestriche zu einem
    .replace(/^-|-$/g, '') // Entferne führende/abschließende Bindestriche
    .replace(/-/g, ''); // Entferne interne Bindestriche für Formdatei-Namen
};

onMounted(() => {
  loadEntityData();
});

// Watch für Route-Änderungen, um neue Entity-Daten zu laden
watch(() => route.params.entityid, (newEntityId, oldEntityId) => {
  if (newEntityId && newEntityId !== oldEntityId) {
    loadEntityData();
  }
});

const getTitle = () => {
  if (!groupType.value) return 'Kontakt';
  
  // Verwende spezifischen Titel aus der Formulardefinition, falls verfügbar
  if (groupType.value?.formName) {
    const specificTitleKey = `jaynaApp.${groupType.value.formName}.viewTitle`;
    const specificTitle = t(specificTitleKey);
    if (specificTitle !== specificTitleKey) {
      return specificTitle;
    }
  }
  
  // Fallback: Verwende nur den GroupType-Namen für den Titel
  return groupType.value.name || 'Kontakt';
};

const getBackButtonPath = () => {
  return '/contact';
};

const getEditRouterName = () => {
  return 'ContactUpdate';
};



</script>
