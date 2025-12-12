<template>
  <div v-if="entityData">
    <EntityUpdateLayout
      :message-key="getTitle()"
      :back-button-path="getBackButtonPath()"
      @cancel="$router.go(-1)"
      @save="saveForm()"
      @delete="deleteContact()"
    >
      <FormBuilder
        v-if="formConfig"
        ref="formBuilder"
        :form-config="formConfig"
        :entity-name="groupType?.entityName"
        :form-name="groupType?.formName"
        :group-type="groupType"
      />
    </EntityUpdateLayout>
    <div class="mt-4">
      <Divider />
      <MetaInline
        :created-date="entityData.createdDate"
        :created-by="entityData.createdBy"
        :last-modified-date="entityData.lastModifiedDate"
        :last-modified-by="entityData.lastModifiedBy"
        :form-name="groupType && groupType.formName"
      />
    </div>
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
import ContactService from '@/services/contact.service';

import EntityUpdateLayout from '@/core/layouts/entity-update-layout.vue';
import { useI18n } from 'vue-i18n';
import { extractEnglishNameFromTranslationKey } from '@/composables/entity-helper';
import FormBuilder from '@/components/FormBuilder.vue';
import Divider from '@/components/divider.vue';
import MetaInline from '@/components/MetaInline.vue';

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
const formBuilder = ref(null);
const generatedFormName = ref(null);

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
        if (groupType.value && !groupType.value.formName) {
          // Fallback, falls der formName nicht sauber berechnet wurde
          const fallbackName = extractEnglishNameFromTranslationKey(groupType.value.translationKey) || groupType.value.name || 'contact';
          groupType.value.formName = fallbackName;
          generatedFormName.value = fallbackName;
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

const saveForm = async () => {
  if (!formBuilder.value) {
    AlertService.showError('Formular nicht verfügbar');
    return;
  }

  try {
    const formData = await formBuilder.value.getData();
    
    // Aktualisiere die Entity-Daten mit den Formulardaten
    Object.assign(entityData.value, formData);
    
    // Speichere die aktualisierten Daten
    const updatedEntity = await ContactService.update(entityData.value);
    
    AlertService.showSuccess('Kontakt erfolgreich aktualisiert');
    
    // Navigiere zur Detailansicht
    router.push({
      name: 'ContactView',
      params: { entityid: updatedEntity.id }
    });
  } catch (error) {
    console.error('Fehler beim Speichern:', error);
    AlertService.showError('Fehler beim Speichern des Kontakts');
  }
};

const deleteContact = async () => {
  if (!entityData.value?.id) {
    AlertService.showError('Kontakt-ID nicht verfügbar');
    return;
  }

  try {
    await ContactService.delete(entityData.value.id);
    AlertService.showSuccess('Kontakt erfolgreich gelöscht');
    
    // Navigiere zur Kontakt-Übersicht
    router.push('/contact');
  } catch (error) {
    console.error('Fehler beim Löschen:', error);
    AlertService.showError('Fehler beim Löschen des Kontakts');
  }
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
  if (!groupType.value) return 'Kontakt bearbeiten';
  
  // Verwende spezifischen Titel aus der Formulardefinition, falls verfügbar
  if (groupType.value?.formName) {
    const specificTitleKey = `jaynaApp.${groupType.value.formName}.updateTitle`;
    const specificTitle = t(specificTitleKey);
    if (specificTitle !== specificTitleKey) {
      return specificTitle;
    }
  }
  
  // Fallback: Verwende nur den GroupType-Namen für den Titel
  return `${groupType.value.name || 'Kontakt'} bearbeiten`;
};

const getBackButtonPath = () => {
  return `/contact/view/${route.params.entityid}`;
};

</script>
