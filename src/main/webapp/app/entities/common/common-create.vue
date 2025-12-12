<template>
  <EntityCreateLayout
    v-if="dataIsReady"
    :message-key="getCreateTitle()"
    :show-edit-form-button="groupType?.creationType === 'CUSTOM'"
    @cancel="cancel()"
    @save="save()"
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

    <FormBuilder
      v-if="formConfig"
      ref="form"
      :form-config="formConfig"
      :entity-name="groupType?.entityName"
      :form-name="groupType?.formName"
      :group-type="groupType"
    />

  </EntityCreateLayout>
</template>

<script setup>

import { ref, computed, watch, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useI18n } from 'vue-i18n';
import { useStore } from 'vuex';

import AlertService from '@/services/alert.service';

import { usePrepare } from '@/composables/entity-helper';

import SInput from '@/components/forms/input.vue';
import FormPanel from '@/components/formpanel.vue';
import Divider from '@/components/divider.vue';
import EntityCreateLayout from '@/core/layouts/entity-create-layout.vue';

import FormBuilder from '@/components/FormBuilder.vue';
import AddButton from '@/components/buttons/add-button.vue';

import { isSingleGroupType, isSelectGroupType } from '@/composables/render-utils';

const route = useRoute();
const router = useRouter();
const { te, t } = useI18n();
const store = useStore();

/** data */
const loading = ref(false);
const success = ref(null);
const error = ref(null);
const form = ref(null);
const formConfig = ref(null);

const service = ref(null);
const category = ref(null);
const subCategory = ref(null);
const subCategoryGroup = ref(null);
const groupType = ref(null);

const dataIsReady = computed(() => {
  return category.value && subCategory.value && subCategoryGroup.value && groupType.value && formConfig.value;
});

// Neue Funktion zur Extraktion der Kategorie-Hierarchie aus groupType (wie in view.vue)
const extractCategoryHierarchyFromGroupType = (groupType) => {
  if (!groupType) return;
  
  groupType.value = groupType;
  subCategoryGroup.value = groupType.subCategoryGroup;
  subCategory.value = groupType.subCategoryGroup.subCategory;
  category.value = groupType.subCategoryGroup.subCategory.category;
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
        config: formConfig.value,
        payload: form.value.getData()
      };
      
      // Verwende CustomFormInput Service für CUSTOM Entities
      import('@/services/custom-form-input.service.js').then(({ default: CustomFormInputService }) => {
        CustomFormInputService.create(data)
          .then(res => {
            AlertService.showSuccess('Datensatz erfolgreich erstellt');
            router.push(`/${category.value.entityName}`);
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
        { groupType: groupType.value }
      );
      
      service.value
        .create(data)
        .then(res => {
          AlertService.showSuccess('Datensatz erfolgreich erstellt');
          router.push(`/${category.value.entityName}`);
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

usePrepare(category, subCategory, subCategoryGroup, groupType, formConfig, service);

// Zusätzlicher Watch für groupType, um die Hierarchie zu extrahieren (wie in view.vue)
watch(groupType, (newGroupType) => {
  if (newGroupType) {
    extractCategoryHierarchyFromGroupType(newGroupType);
  }
}, { immediate: true });

function getCategoryTitle() {
  return 'Kategorie';
}

function getCreateTitle() {
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

// Alle Übersetzungen sind jetzt in der global.json



</script>
