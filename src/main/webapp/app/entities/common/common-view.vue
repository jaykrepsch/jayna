<template>
  <div v-if="entityData">
    <EntityDetailsLayout
      :message-key="getTitle(groupType)"
      :back-button-path="getBackButtonPath()"
      :edit-router-name="getEditRouterName()"
      :edit-router-params="editRouterParams"
    >
      <!-- Kategorie-Abschnitt für alle Entitäten außer Kontakte -->
      <div
        v-if="!isSingleGroupType(category?.entityName) && category?.entityName !== 'contact'"
      >
        <FormPanel
          :title="getCategoryTitle()"
          bg-color="bg-white"
        >
          <template v-slot:content>
            <div class="md:grid md:grid-cols-3 md:gap-6">
              <s-input
                v-if="!isSelectGroupType(category?.entityName)"
                name="subCategory"
                type="text"
                :model-value="getSubCategoryName()"
                :label="$t(`jaynaApp.baseOverview.subCategory`)"
                :disabled="true"
              />
              <s-input
                v-if="!isSelectGroupType(category?.entityName)"
                name="subCategoryGroup"
                type="text"
                :model-value="getSubCategoryGroupName()"
                :label="$t('jaynaApp.baseOverview.subCategoryGroup')"
                :disabled="true"
              />
              <s-input
                name="groupType"
                type="text"
                :model-value="getGroupTypeName()"
                :label="$t('jaynaApp.baseOverview.groupType')"
                :disabled="true"
              />
            </div>
          </template>
        </FormPanel>

        <Divider />
      </div>

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

import { ref, computed, watch, onUnmounted, onMounted } from 'vue';
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
import { getCorrectFormName } from '@/composables/form-name-generator';
import { isSingleGroupType, isSelectGroupType } from '@/composables/render-utils';
import SInput from '@/components/forms/input.vue';
import FormPanel from '@/components/formpanel.vue';
import Divider from '@/components/divider.vue';
import MetaInline from '@/components/MetaInline.vue';
import ContractService from '@/services/contract.service';
import ContactService from '@/services/contact.service';
import RealEstateService from '@/services/realestate.service';
import MobilityService from '@/services/mobility.service';
import FinanceAccountService from '@/services/financeaccount.service';
import CategoryService from '@/services/category.service';
import SubCategoryService from '@/services/sub-category.service';
import SubCategoryGroupService from '@/services/sub-category-group.service';
import GroupTypeService from '@/services/group-type.service';

/** data */
const { t, te } = useI18n();
const route = useRoute();
const router = useRouter();
const formConfig = ref(null);
const entityData = ref(null);
const service = ref(null);

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

// Hilfsfunktionen für die Anzeige der Feldwerte
const getSubCategoryDisplayName = (subCategory) => {
  if (!subCategory) return '-';
  if (subCategory.translationKey) {
    const translation = t(subCategory.translationKey);
    if (translation !== subCategory.translationKey) return translation;
  }
  return subCategory.name || '-';
};

const getSubCategoryGroupDisplayName = (subCategoryGroup) => {
  if (!subCategoryGroup) return '-';
  if (subCategoryGroup.translationKey) {
    const translation = t(subCategoryGroup.translationKey);
    if (translation !== subCategoryGroup.translationKey) return translation;
  }
  return subCategoryGroup.name || '-';
};

const getGroupTypeDisplayName = (groupType) => {
  if (!groupType) return '-';
  if (groupType.translationKey) {
    const translation = t(groupType.translationKey);
    if (translation !== groupType.translationKey) return translation;
  }
  return groupType.name || '-';
};

const getSubCategoryName = () => {
  return getSubCategoryDisplayName(subCategory.value);
};

const getSubCategoryGroupName = () => {
  return getSubCategoryGroupDisplayName(subCategoryGroup.value);
};

const getGroupTypeName = () => {
  return getGroupTypeDisplayName(groupType.value);
};

const getCategoryTitle = () => {
  return 'Kategorie';
};



const loadEntityData = () => {
  if (route.params.entityid) {
    const path = route.path;
    let service;
    let entityName;
    
    if (path.includes('/contract/')) {
      service = ContractService;
      entityName = 'contract';
    } else if (path.includes('/contact/')) {
      service = ContactService;
      entityName = 'contact';
    } else if (path.includes('/realestate/')) {
      service = RealEstateService;
      entityName = 'realestate';
    } else if (path.includes('/mobility/')) {
      service = MobilityService;
      entityName = 'mobility';
    } else if (path.includes('/financeaccount/')) {
      service = FinanceAccountService;
      entityName = 'financeaccount';
    } else {
      service = ContractService;
      entityName = 'contract';
    }
    
    const entityId = parseInt(route.params.entityid);
    if (isNaN(entityId) || entityId <= 0) {
      AlertService.showError('Ungültige Entity-ID');
      return;
    }
    
    // Bestimme die baseApiUrl basierend auf dem Service
    let baseApiUrl = 'api/contracts'; // Default
    if (service === MobilityService) {
      baseApiUrl = 'api/mobilities';
    } else if (service === RealEstateService) {
      baseApiUrl = 'api/realestates';
    } else if (service === FinanceAccountService) {
      baseApiUrl = 'api/finance-accounts';
    } else if (service === ContactService) {
      baseApiUrl = 'api/contacts';
    }
    
    // Verwende find für alle Services (bereits mit relations=true)
    const serviceCall = service.find(entityId);
    
    console.log('Loading entity data for ID:', entityId, 'with service:', service, 'entityName:', entityName);
    
    serviceCall.then(response => {
        if (!response || !response.id) {
          throw new Error('Entity not found');
        }
        console.log('Entity data loaded successfully:', response);
        console.log('GroupType formName from database:', response.groupType?.formName);
        console.log('GroupType translationKey:', response.groupType?.translationKey);
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
          
          // Verwende die neue getCorrectFormName Funktion
          console.log('Calling getCorrectFormName with groupType:', groupType.value, 'entityName:', entityName);
          return getCorrectFormName(groupType.value, entityName);
        } else {
          // Verwende eine generische Fallback-Formulardefinition basierend auf der Entity
          return import(`@/shared/form-definitions/${entityName}/${entityName}.json`);
        }
      })
      .then(formNameToLoad => {
        console.log('formNameToLoad result:', formNameToLoad, 'type:', typeof formNameToLoad);
        if (typeof formNameToLoad === 'string') {
          // Es ist ein formName, lade die entsprechende Formulardefinition
          if (formNameToLoad) {
            // Setze den formName im groupType für die Titel-Anzeige
            groupType.value.formName = formNameToLoad;
            generatedFormName.value = formNameToLoad;
            console.log('Loading form definition:', `@/shared/form-definitions/${entityName}/${formNameToLoad}.json`);
            return import(`@/shared/form-definitions/${entityName}/${formNameToLoad}.json`);
          } else {
            throw new Error('Konnte formName nicht generieren');
          }
        } else {
          // Es ist bereits eine Formulardefinition (Fallback)
          console.log('Using fallback form definition');
          return formNameToLoad;
        }
      })
      .then(formData => {
        console.log('Form definition loaded successfully:', formData);
        formConfig.value = formData.default || formData;
        console.log('formConfig set to:', formConfig.value);
        console.log('Number of parts:', formConfig.value?.parts?.length || 0);
        if (formConfig.value?.parts) {
          formConfig.value.parts.forEach((part, index) => {
            console.log(`Part ${index}: ${part.title} - Fields: ${part.fields?.length || 0}`);
          });
        }
      })
      .catch(err => {
        AlertService.showHttpError(this, err.response);
      });
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

const getTitle = (groupType) => { 
  if (!groupType) return 'Vertrag';
  
  // Verwende spezifischen Titel aus der Formulardefinition, falls verfügbar
  // Der formName sollte bereits korrekt durch die Hierarchie-Logik gesetzt sein
  if (groupType?.formName) {
    const specificTitleKey = `jaynaApp.${groupType.formName}.viewTitle`;
    const specificTitle = t(specificTitleKey);
    if (specificTitle !== specificTitleKey) {
      return specificTitle;
    }
  }
  
  // Fallback: Verwende nur den GroupType-Namen für den Titel
  return groupType.name || 'Vertrag';
};

const getBackButtonPath = () => {
  const path = route.path;
  if (path.includes('/contract/')) return '/contract';
  if (path.includes('/contact/')) return '/contact';
  if (path.includes('/realestate/')) return '/realestate';
  if (path.includes('/mobility/')) return '/mobility';
  if (path.includes('/financeaccount/')) return '/financeaccount';
  return '/' + (category.value?.entityName || 'contract');
};

const getEditRouterName = () => {
  const path = route.path;
  if (path.includes('/contract/')) return 'ContractUpdate';
  if (path.includes('/contact/')) return 'ContactUpdate';
  if (path.includes('/realestate/')) return 'RealEstateUpdate';
  if (path.includes('/mobility/')) return 'MobilityUpdate';
  if (path.includes('/financeaccount/')) return 'FinanceAccountUpdate';
  return 'ContractUpdate';
};

const getGroupTypeDisplayNameFromBaseEntity = (groupType) => {
  if (!groupType) return '-';
  // Der formName sollte bereits korrekt durch die Hierarchie-Logik gesetzt sein
  const formName = groupType.formName;
  if (!formName) {
    return groupType.name || '-';
  }
  const entityType = getEntityTypeFromFormName(formName);
  const groupTypeKey = getGroupTypeKeyFromFormName(formName, groupType.name);
  const baseEntityKey = `jaynaApp.base${entityType}.groupType.${groupTypeKey}`;
  const baseEntityTranslation = t(baseEntityKey);
  if (baseEntityTranslation !== baseEntityKey) {
    return baseEntityTranslation;
  }
  const entityKey = `jaynaApp.${entityType.toLowerCase()}.group-type.${groupTypeKey}`;
  const entityTranslation = t(entityKey);
  if (entityTranslation !== entityKey) {
    return entityTranslation;
  }
  return groupType.name || '-';
};

const getEntityTypeFromFormName = (formName) => {
  if (formName.startsWith('contract-')) return 'Contract';
  if (formName.startsWith('contact-')) return 'Contact';
  if (formName.startsWith('mobility-')) return 'Mobility';
  if (formName.startsWith('realestate-')) return 'Realestate';
  if (formName.startsWith('financeaccount-')) return 'Financeaccount';
  return 'Contract';
};



const getGroupTypeKeyFromFormName = (formName, groupTypeName) => {
  // Specific mappings can be added here if needed
  return groupTypeName;
};


</script>
