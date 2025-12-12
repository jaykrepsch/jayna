<template>
  <EntityUpdateLayout
    v-if="dataIsReady"
    :message-key="getUpdateTitle(groupType)"
    @delete="remove()"
    @cancel="cancel()"
    @save="save()"
  >

    <div
      v-if="!isSingleGroupType(category?.entityName)"
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

      <FormBuilder
        v-if="formConfig && entityData && generatedFormName"
        ref="form"
        :form-config="formConfig"
        :data="entityData"
        :entity-name="groupType?.entityName"
        :form-name="generatedFormName"
        :group-type="groupType"
      />


  </EntityUpdateLayout>
  <div v-if="entityData" class="mt-4">
    <Divider />
    <MetaInline
      :created-date="entityData.createdDate"
      :created-by="entityData.createdBy"
      :last-modified-date="entityData.lastModifiedDate"
      :last-modified-by="entityData.lastModifiedBy"
      :form-name="generatedFormName || (groupType && groupType.formName) || null"
    />
  </div>
</template>

<script setup>

import { ref, computed, watch, onMounted, defineProps } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useI18n } from 'vue-i18n';
import { useStore } from 'vuex';

import AlertService from '@/services/alert.service';

const props = defineProps({
  entityid: {
    type: [String, Number],
    default: null
  }
});

import ContractService from '@/services/contract.service';
import ContactService from '@/services/contact.service';
import RealEstateService from '@/services/realestate.service';
import MobilityService from '@/services/mobility.service';
import FinanceAccountService from '@/services/financeaccount.service';
import CategoryService from '@/services/category.service';
import SubCategoryService from '@/services/sub-category.service';
import SubCategoryGroupService from '@/services/sub-category-group.service';
import GroupTypeService from '@/services/group-type.service';

import SInput from '@/components/forms/input.vue';
import FormPanel from '@/components/formpanel.vue';
import Divider from '@/components/divider.vue';
import MetaInline from '@/components/MetaInline.vue';
import EntityUpdateLayout from '@/core/layouts/entity-update-layout.vue';

import FormBuilder from '@/components/FormBuilder.vue';
import { isSingleGroupType, isSelectGroupType } from '@/composables/render-utils';
import { extractEnglishNameFromTranslationKey } from '@/composables/entity-helper';
import { getCorrectFormName } from '@/composables/form-name-generator';

const route = useRoute();
const router = useRouter();
const { t } = useI18n();
const store = useStore();

/** data */
const loading = ref(false);
const form = ref(null);
const formConfig = ref(null);
const entityData = ref(null);
const service = ref(null);

const category = ref(null);
const subCategory = ref(null);
const subCategoryGroup = ref(null);
const groupType = ref(null);
const generatedFormName = ref(null);

const dataIsReady = computed(() => {
  return category.value && formConfig.value && entityData.value;
});

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

const remove = () => {
  loading.value = true;
  service.value
    .delete(entityData.value.id)
    .then(data => {
      entityData.value = null;
      router.push('/' + category.value?.entityName);
    })
    .catch(error => {
      error.value = error;
    })
    .finally(() => {
      loading.value = false
  });
};

const cancel = () => {
  router.push('/' + category.value.entityName)
};

const save = () => {
  // Prüfe Validierung vor dem Speichern
  if (form.value && typeof form.value.isValid === 'function') {
    if (!form.value.isValid()) {
      // Zeige Fehlermeldung an
      store.commit('app/showErrorModal', {
        titleKey: 'error.modal.title',
        contentKey: 'Bitte füllen Sie alle erforderlichen Felder aus.'
      });
      return;
    }
  }

  loading.value = true;
  let data = {};

  try {
    if (groupType.value.creationType === 'CUSTOM') {
      // Für CUSTOM GroupTypes: Verwende CustomFormInput Service
      data = {
        payload: form.value.getData(),
        config: formConfig.value,
        id: parsedEntityId
      };
      
      // Verwende CustomFormInput Service für CUSTOM Entities
      import('@/services/custom-form-input.service.js').then(({ default: CustomFormInputService }) => {
        CustomFormInputService.update(data)
          .then(res => {
            AlertService.showSuccess('Datensatz erfolgreich aktualisiert');
            // Navigiere zur View-Seite des bearbeiteten Datensatzes
            const path = route.path;
            let viewRouteName;
            
            if (path.includes('/contract/')) {
              viewRouteName = 'ContractView';
            } else if (path.includes('/contact/')) {
              viewRouteName = 'ContactView';
            } else if (path.includes('/realestate/')) {
              viewRouteName = 'RealEstateView';
            } else if (path.includes('/mobility/')) {
              viewRouteName = 'MobilityView';
            } else if (path.includes('/financeaccount/')) {
              viewRouteName = 'FinanceAccountView';
            } else {
              viewRouteName = 'ContractView';
            }
            
            router.push({
              name: viewRouteName,
              params: { entityid: parsedEntityId }
            });
          })
          .catch(error => {
            console.error('Fehler beim Speichern:', error);
            AlertService.showError('Fehler beim Speichern: ' + (error.response?.data?.message || error.message));
          })
          .finally(() => {
            loading.value = false;
          });
      });
      return;
    } else {
      // Für normale Entities
      data = Object.assign(
        form.value.getData(),
        { id: parsedEntityId },
        { groupType: groupType.value }
      );
      
      service.value
        .update(data)
        .then(res => {
          AlertService.showSuccess('Datensatz erfolgreich aktualisiert');
          // Navigiere zur View-Seite des bearbeiteten Datensatzes
          const path = route.path;
          let viewRouteName;
          
          if (path.includes('/contract/')) {
            viewRouteName = 'ContractView';
          } else if (path.includes('/contact/')) {
            viewRouteName = 'ContactView';
          } else if (path.includes('/realestate/')) {
            viewRouteName = 'RealEstateView';
          } else if (path.includes('/mobility/')) {
            viewRouteName = 'MobilityView';
          } else if (path.includes('/financeaccount/')) {
            viewRouteName = 'FinanceAccountView';
          } else {
            viewRouteName = 'ContractView';
          }
          
          router.push({
            name: viewRouteName,
            params: { entityid: parsedEntityId }
          });
        })
        .catch(error => {
          console.error('Fehler beim Speichern:', error);
          AlertService.showError('Fehler beim Speichern: ' + (error.response?.data?.message || error.message));
        })
        .finally(() => {
          loading.value = false;
        });
    }
  } catch (error) {
    console.error('Unerwarteter Fehler:', error);
    AlertService.showError('Unerwarteter Fehler beim Speichern');
    loading.value = false;
  }
};

// Einfache Implementierung für direkte Entity-Bearbeitung
onMounted(() => {
  console.log('common-update.vue mounted with route:', route.path, 'params:', route.params);
  const entityId = props.entityid || route.params.entityid;
  if (entityId) {
    // Validiere die Entity-ID - nur positive Zahlen erlauben
    const parsedEntityId = parseInt(entityId);
    if (isNaN(parsedEntityId) || parsedEntityId <= 0) {
      AlertService.showError('Ungültige Entity-ID');

      // Navigiere zur korrekten Entity-Übersicht basierend auf der Route
      const path = route.path;
      let listRoute;

      if (path.includes('/contract/')) {
        listRoute = '/contract';
      } else if (path.includes('/contact/')) {
        listRoute = '/contact';
      } else if (path.includes('/realestate/')) {
        listRoute = '/realestate';
      } else if (path.includes('/mobility/')) {
        listRoute = '/mobility';
      } else if (path.includes('/financeaccount/')) {
        listRoute = '/financeaccount';
      } else {
        listRoute = '/contract';
      }
      
      router.push(listRoute);
      return;
    }
    
    // Bestimme den Service basierend auf der Route
    const path = route.path;
    let selectedService;
    let entityName;
    let defaultFormName;
    
    if (path.includes('/contract/')) {
      selectedService = ContractService;
      entityName = 'contract';
      defaultFormName = 'contract-employment-employee-terminationcontract';
    } else if (path.includes('/contact/')) {
      selectedService = ContactService;
      entityName = 'contact';
      defaultFormName = 'contact-person-person-person';
    } else if (path.includes('/realestate/')) {
      selectedService = RealEstateService;
      entityName = 'realestate';
      defaultFormName = 'realestate-withdevelopment-farmland-farmland';
    } else if (path.includes('/mobility/')) {
      selectedService = MobilityService;
      entityName = 'mobility';
      defaultFormName = 'mobility-passengervehicle-car-car';
    } else if (path.includes('/financeaccount/')) {
      selectedService = FinanceAccountService;
      entityName = 'financeaccount';
      defaultFormName = 'financeaccount-bank-bankaccount-checkingaccount';
    } else {
      selectedService = ContractService;
      entityName = 'contract';
      defaultFormName = 'contract-employment-employee-terminationcontract';
    }
    
    // Setze den Service in das ref
    service.value = selectedService;
    
    // Bestimme die baseApiUrl basierend auf dem Service
    let baseApiUrl = 'api/contracts'; // Default
    if (selectedService === MobilityService) {
      baseApiUrl = 'api/mobilities';
    } else if (selectedService === RealEstateService) {
      baseApiUrl = 'api/realestates';
    } else if (selectedService === FinanceAccountService) {
      baseApiUrl = 'api/finance-accounts';
    } else if (selectedService === ContactService) {
      baseApiUrl = 'api/contacts';
    }
    
    // Lade die Entity-Daten mit Verknüpfungen
    console.log('Loading entity data for ID:', entityId, 'with service:', selectedService, 'entityName:', entityName);
    service.value.find(entityId)
      .then(response => {
        if (!response || !response.id) {
          throw new Error('Entity not found');
        }
        console.log('Entity data loaded successfully:', response);
        console.log('GroupType formName from database:', response.groupType?.formName);
        console.log('GroupType translationKey:', response.groupType?.translationKey);
        entityData.value = response;
        
        // Extrahiere die Kategorie-Hierarchie wie in view.vue
        groupType.value = response.groupType;
        subCategoryGroup.value = response.groupType?.subCategoryGroup;
        subCategory.value = response.groupType?.subCategoryGroup?.subCategory;
        category.value = response.groupType?.subCategoryGroup?.subCategory?.category;

        // Verwende die generische Form-Name-Generierung
        return getCorrectFormName(groupType.value, entityName);
      })
      .then(formName => {
        formName = formName || defaultFormName;
        
        console.log('Generated formName:', formName);
        console.log('Default formName:', defaultFormName);

        // für konsistente Titelanzeige auch in groupType übernehmen
        if (groupType.value) {
          groupType.value.formName = formName;
        }

        generatedFormName.value = formName;
        
        console.log('Loading form definition:', `@/shared/form-definitions/${entityName}/${formName}.json`);
        return import(`@/shared/form-definitions/${entityName}/${formName}.json`);
      })
      .then(formData => {
        console.log('Form definition loaded successfully:', formData);
        formConfig.value = formData.default || formData;
      })
      .catch(err => {
        // Unterscheide zwischen Entity-Fehlern und Formulardefinitions-Fehlern
        if (err.message && err.message.includes('Cannot find module')) {
          AlertService.showError('Formulardefinition nicht gefunden, verwende Standard-Formular');
          
          // Verwende die Standard-Formulardefinition
          import(`@/shared/form-definitions/${entityName}/${defaultFormName}.json`)
            .then(defaultFormData => {
              formConfig.value = defaultFormData.default || defaultFormData;
            })
            .catch(defaultErr => {
              AlertService.showError('Standard-Formular nicht gefunden');
              router.push('/' + entityName);
            });
        } else {
          AlertService.showError('Entity nicht gefunden');
          
          // Navigiere zur korrekten Entity-Übersicht basierend auf der Route
          const path = route.path;
          let listRoute;
          
          if (path.includes('/contract/')) {
            listRoute = '/contract';
          } else if (path.includes('/contact/')) {
            listRoute = '/contact';
          } else if (path.includes('/realestate/')) {
            listRoute = '/realestate';
          } else if (path.includes('/mobility/')) {
            listRoute = '/mobility';
          } else if (path.includes('/financeaccount/')) {
            listRoute = '/financeaccount';
          } else {
            listRoute = '/contract';
          }
          
          router.push(listRoute);
        }
      });
  }
});

function getCategoryTitle() {
  return 'Kategorie';
}

function getUpdateTitle(groupType) {
  if (!groupType?.name) return '';
  
  // Verwende spezifischen Titel aus der Formulardefinition, falls verfügbar
  // Der formName sollte bereits korrekt durch die Hierarchie-Logik gesetzt sein
  if (groupType?.formName) {
    const specificTitleKey = `jaynaApp.${groupType.formName}.updateTitle`;
    const specificTitle = t(specificTitleKey);
    if (specificTitle !== specificTitleKey) {
      return specificTitle;
    }
  }
  
  // Fallback: Verwende den Titel aus der global.json und ändere ihn zu "bearbeiten"
  if (category.value?.entityName) {
    const entityTitle = t(`jaynaApp.${category.value.entityName}.title`);
    return `${entityTitle} bearbeiten`;
  }
  
  // Fallback: Verwende den generischen Titel aus baseOverview
  return t('jaynaApp.baseOverview.updateTitle');
}

// Alle Übersetzungen sind jetzt in der global.json




</script>
