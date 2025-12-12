<template>
  <EntityCreateLayout
    v-if="dataIsReady"
    :message-key="getCreateTitle()"
    @cancel="$router.go(-1)"
    @save="saveForm()"
  >
    <div
        v-if="!isSingleGroupType(category?.entityName) && props.entityName !== 'contact'"
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
      v-if="formConfig"
      ref="formBuilder"
      :form-config="formConfig"
      :entity-name="groupType?.entityName"
      :form-name="groupType?.formName"
      :group-type="groupType"
    />

  </EntityCreateLayout>
</template>

<script setup>

import { ref, computed, watch, defineProps } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex'
import { useI18n } from 'vue-i18n';

import AlertService from '@/services/alert.service';
import ContactService from '@/services/contact.service';

import { usePrepare } from '@/composables/entity-helper';

import SInput from '@/components/forms/input.vue';
import FormPanel from '@/components/formpanel.vue';
import Divider from '@/components/divider.vue';
import EntityCreateLayout from '@/core/layouts/entity-create-layout.vue';

import FormConfigService from '@/services/form-config.service';

import { isSingleGroupType, isSelectGroupType } from '@/composables/render-utils';
import FormBuilder from '@/components/FormBuilder.vue';

const props = defineProps({
  entityName: {
    type: String,
    required: false
  }
});

const store = useStore();
const router = useRouter();
const { t } = useI18n();

/** data */
const formConfig = ref({});

const service = ref(null);
const category = ref(null);
const subCategory = ref(null);
const subCategoryGroup = ref(null);
const groupType = ref(null);
const formBuilder = ref(null);

const dataIsReady = computed(() => {
  // Für Kontakte: Zeige das Formular sofort an, wenn es geladen ist
  if (props.entityName === 'contact') {
    return formConfig.value && Object.keys(formConfig.value).length > 0;
  }
  // Für andere Entitäten: Warte auf die vollständige Kategorie-Hierarchie und Form-Konfiguration
  return category.value && subCategory.value && subCategoryGroup.value && groupType.value && formConfig.value && Object.keys(formConfig.value).length > 0;
});

// Hilfsfunktionen für die Anzeige der Feldwerte
const getSubCategoryName = () => {
  if (subCategory.value?.translationKey) {
    return t(subCategory.value.translationKey);
  }
  if (subCategory.value?.name) {
    return subCategory.value.name;
  }
  // Fallback auf Übersetzungsschlüssel
  return t(`jaynaApp.${category.value?.entityName}.subCategory`) || 'Art';
};

const getSubCategoryGroupName = () => {
  if (subCategoryGroup.value?.translationKey) {
    return t(subCategoryGroup.value.translationKey);
  }
  if (subCategoryGroup.value?.name) {
    return subCategoryGroup.value.name;
  }
  // Fallback auf Übersetzungsschlüssel
  return t('jaynaApp.baseOverview.subCategoryGroup') || 'Gruppierung';
};

const getGroupTypeName = () => {
  if (groupType.value?.translationKey) {
    return t(groupType.value.translationKey);
  }
  if (groupType.value?.name) {
    return groupType.value.name;
  }
  // Fallback auf Übersetzungsschlüssel
  return t('jaynaApp.baseOverview.groupType') || 'Sparte';
};

const saveForm = () => {
  // Prüfe Validierung vor dem Speichern
  if (formBuilder.value && typeof formBuilder.value.isValid === 'function') {
    if (!formBuilder.value.isValid()) {
      // Zeige Fehlermeldung an
      store.commit('app/showErrorModal', {
        titleKey: 'error.modal.title',
        contentKey: 'Bitte füllen Sie alle erforderlichen Felder aus.'
      });
      return;
    }
  }

  try {
    const data = formBuilder.value?.getData();
    
    // Füge die GroupType zu den Daten hinzu (wie in create.vue)
    if (groupType.value && props.entityName !== 'contact') {
      data.groupType = groupType.value;
    }
    
    if (props.entityName === 'contact') {
      ContactService.create(data)
        .then(() => {
          AlertService.showSuccess('Kontakt erfolgreich erstellt');
          router.push({ name: 'ContactList' });
        })
        .catch(err => {
          console.error('Fehler beim Speichern:', err);
          AlertService.showError('Fehler beim Speichern: ' + (err.response?.data?.message || err.message));
        });
    } else if (service.value) {
      // Für alle anderen Entitäten
      service.value.create(data)
        .then(() => {
          AlertService.showSuccess('Datensatz erfolgreich erstellt');
          // Bestimme den korrekten Routennamen basierend auf der Entität
          let routeName;
          switch (category.value?.entityName) {
            case 'contract':
              routeName = 'ContractList';
              break;
            case 'contact':
              routeName = 'ContactList';
              break;
            case 'realestate':
              routeName = 'RealEstateList';
              break;
            case 'financeaccount':
              routeName = 'FinanceAccountList';
              break;
            case 'mobility':
              routeName = 'MobilityList';
              break;
            default:
              routeName = `${category.value?.entityName}List`;
          }
          
          router.push({ name: routeName });
        })
        .catch(err => {
          console.error('Fehler beim Speichern:', err);
          AlertService.showError('Fehler beim Speichern: ' + (err.response?.data?.message || err.message));
        });
    }
  } catch (error) {
    console.error('Unerwarteter Fehler:', error);
    AlertService.showError('Unerwarteter Fehler beim Speichern');
  }
};

// Lade das Kontaktformular direkt, wenn entityName "contact" ist
if (props.entityName === 'contact') {
  store.commit('app/enableLoading');
  import('@/shared/form-definitions/contact/contact.json').then((data) => {
    formConfig.value = data.default || data;
    store.commit('app/disableLoading');
  });
} else {
  // Für andere Entitäten verwende usePrepare
  usePrepare(category, subCategory, subCategoryGroup, groupType, formConfig, service);
}

function getCategoryTitle() {
  return 'Kategorie';
}

function getCreateTitle() {
  // Für Kontakte: Verwende spezifischen Titel
  if (props.entityName === 'contact') {
    return t('jaynaApp.baseContact.create');
  }
  
  // Verwende spezifischen Titel aus der Formulardefinition, falls verfügbar
  if (groupType.value?.formName) {
    const specificTitleKey = `jaynaApp.${groupType.value.formName}.createTitle`;
    const specificTitle = t(specificTitleKey);
    if (specificTitle !== specificTitleKey) {
      return specificTitle;
    }
  }
  
  // Fallback: Verwende den GroupType-Namen für den Titel (wie in der Detail-Ansicht)
  return groupType.value?.name ? `${groupType.value.name} erstellen` : 'Vertrag erstellen';
}

</script>
