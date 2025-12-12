<template>
  <div>


    <div
      v-for="(form, index) in formConfig?.parts"
      :key="index"
      class="mt-10 sm:mt-4"
    >
      <FormPanel
        :title="getFormTitle(form, effectiveFormName)"
        :sub-title="form.subTitle ? $t(form.subTitle) : ''"
      >
        <template v-slot:content>
          <div :class="['md:grid md:gap-6', 'md:grid-cols-3']">
            <div
              v-for="(field, fieldIndex) in form.fields"
              :key="fieldIndex"
            >
              <div class="space-y-4">
                <!-- Info Felder -->
                <p v-if="field.type === 'info'" class="text-sm text-gray-600">
                  {{ $t(field['text-key']) }}
                </p>

                <!-- Text und Number Felder -->
                <SInput
                  v-if="field.type === 'text' || field.type === 'number'"
                  :model-value="getFieldValue(field)"
                  :name="field.name"
                  :type="field.type"
                  :label="getLabel(field, effectiveFormName, getSectionName(form.title), form)"
                  :disabled="true"
                  :required="isFieldRequired(field)"
                  class="detail-view-field"
                />

                <!-- Textarea Felder -->
                <STextarea
                  v-if="field.type === 'textarea'"
                  :model-value="getFieldValue(field)"
                  :label="getLabel(field, effectiveFormName, getSectionName(form.title), form)"
                  :disabled="true"
                  :required="isFieldRequired(field)"
                  class="detail-view-field"
                />

                <!-- Date Felder -->
                <div v-if="field.type === 'date'" class="detail-view-field">
                <label
                  :for="field.name"
                  :class="[isFieldRequired(field) ? 'required' : '', 'block text-sm font-medium text-gray-700']"
                  v-text="getLabel(field, effectiveFormName, getSectionName(form.title), form)"
                />
                  <date-picker
                    :model-value="data[field.name] ? new Date(data[field.name]) : null"
                    class="mt-1"
                    locale="de"
                    :name="field.name"
                    :select-text="$t('global.menu.choose')"
                    :cancel-text="$t('button.cancel')"
                    :enable-time-picker="field.enableTimePicker || false"
                    :auto-apply="field.autoApply || true"
                    :text-input="field.textInput || true"
                    :format="field.format || 'dd.MM.yyyy'"
                    :input-class-name="(field.inputClassName || '') + ' date-picker'"
                    :position="field.position || 'left'"
                    :disabled="true"
                  />
                </div>

                <!-- Dropdown Felder -->
                <Dropdown
                  v-if="field.type === 'dropdown'"
                  :model-value="getDropdownValue(field)"
                  :label="getLabel(field, effectiveFormName, getSectionName(form.title), form)"
                  :items="getDropdownItems(field)"
                  :disabled="true"
                  class="detail-view-field"
                />

                <!-- Checkbox Felder -->
                <Checkbox
                  v-if="field.type === 'checkbox'"
                  :model-value="data[field.name]"
                  :label="getLabel(field, effectiveFormName, getSectionName(form.title), form)"
                  :disabled="true"
                  class="detail-view-field"
                />

                <!-- Radio Felder -->
                <RadioGroup
                  v-if="field.type === 'radio'"
                  :model-value="data[field.name]"
                  :label="getLabel(field, effectiveFormName, getSectionName(form.title), form)"
                  :items="field.items"
                  :disabled="true"
                  class="detail-view-field"
                />

                <!-- Relation Dropdown Felder -->
                <RelationDropdown
                  v-if="field.type === 'relation-dropdown'"
                  :model-value="getRelationValue(field)"
                  :label="getLabel(field, effectiveFormName, getSectionName(form.title), form)"
                  :entity-name="field.entityName"
                  :display-field="field.displayField"
                  :disabled="true"
                  class="detail-view-field"
                />
              </div>
            </div>
          </div>
        </template>
      </FormPanel>

      <Divider v-if="index < formConfig?.parts.length - 1" />
    </div>

    <Divider v-if="formConfig?.relations?.length" />
    <div v-if="formConfig?.relations?.length">
      <div class="flex flex-col space-y-4">
        <div class="flex justify-between items-center">
          <h3 class="text-lg font-medium leading-6 text-gray-900">{{ getRelationsTitle() }}</h3>
        </div>
        <RelationList
          :form-config="formConfig"
          :data="data"
          :group-type="groupType"
          :editable="false"
          @relation-saved="emit('relation-saved')"
        />
      </div>
    </div>



    
  </div>
</template>

<script setup>
import { computed, defineEmits } from 'vue';
import { useStore } from 'vuex';

import FormPanel from '@/components/formpanel.vue';
import Divider from '@/components/divider.vue';
import RelationLayout from '@/core/layouts/relation-layout.vue';
import SInput from '@/components/forms/input.vue';
import STextarea from '@/components/forms/textarea.vue';
import Dropdown from '@/components/forms/dropdown.vue';
import Checkbox from '@/components/forms/checkbox.vue';
import RadioGroup from '@/components/forms/RadioGroup.vue';
import RelationDropdown from '@/components/forms/RelationDropdown.vue';
import DatePicker from '@vuepic/vue-datepicker';

import { useGetLabel } from '@/composables/utils';
import { isSingleGroupType, isSelectGroupType } from '@/composables/render-utils';
import RelationList from '@/components/RelationList.vue';

import FormPanelLabel from '@/components/FormPanelLabel.vue';
import Button from '@/components/buttons/button.vue';
import { useI18n } from 'vue-i18n';

const emit = defineEmits(['relation-saved']);

const { t, te } = useI18n();
const store = useStore();

const props = defineProps({
  formConfig: Object,
  data: Object,
  subCategory: Object,
  subCategoryGroup: Object,
  groupType: Object,
  formName: String
});

const hasRelations = computed(() => {
  if (props.formConfig?.relations) {
    if (props.formConfig?.relations.filter(i => props.data[i.relationName]?.length > 0).length) {
      return true;
    }
  }
  return false;
});

const getFormTitle = (form, formName) => {
  if (!form.title) return '';
  
  // EINFACHE UND DIREKTE LÖSUNG
  const translation = t(form.title);
  
  // Wenn die Übersetzung gefunden wurde, verwende sie
  if (translation !== form.title) {
    return translation;
  }
  
  // Fallback: Verwende den Titel selbst
  return form.title;
}

// EINHEITLICHE FUNKTION: Extrahiere Abschnittsnamen aus Titel
const getSectionName = (title) => {
  if (!title || !title.includes('.')) return null;
  
  const parts = title.split('.');
  
  // Für base-Titel: jaynaApp.baseContract.general.title -> general
  if (title.includes('base')) {
    const baseIndex = parts.findIndex(part => part.startsWith('base'));
    if (baseIndex >= 0 && parts.length > baseIndex + 1) {
      return parts[baseIndex + 1];
    }
  }
  
  // Für spezifische Titel: jaynaApp.formName.sectionName.title -> sectionName
  if (parts.length >= 3) {
    return parts[parts.length - 2];
  }
  
  return null;
}

const effectiveFormName = computed(() => {
  // Verwende den formName aus den Props, falls verfügbar
  if (props.formName) {
    return props.formName;
  }
  
  // Fallback auf groupType.formName aus der Datenbank
  let formName = props.groupType?.formName || props.groupType?.entityName;
  
  // Behalte die Bindestriche bei - NICHT zu CamelCase konvertieren
  return formName || '';
});

// Funktion zum Abrufen der Feldwerte
const getFieldValue = (field) => {
  return props.data[field.name] || '';
}

// Funktion zum Abrufen der Dropdown-Werte
const getDropdownValue = (field) => {
  if (field.enumName) {
    const items = store?.getters?.[`config/${field.enumName}`] || [];
    return items.find(i => i.name === props.data[field.name]) || null;
  }
  return props.data[field.name];
}

// Funktion zum Abrufen der Dropdown-Items
const getDropdownItems = (field) => {
  if (field.enumName) {
    return store?.getters?.[`config/${field.enumName}`] || [];
  }
  return field.items || [];
}

// Funktion zum Abrufen der Relation-Werte
const getRelationValue = (field) => {
  return props.data && props.data[field.relationName] && props.data[field.relationName][0] 
    ? props.data[field.relationName][0][field.entityName] 
    : null;
}

// SYSTEMATISCHE FELD-ÜBERSETZUNGSLOGIK
const getLabel = (field, formName, sectionName = null, sectionConfig = null) => {
  // 1. Direkte Übersetzung (falls field.label ein Übersetzungsschlüssel ist)
  if (field.label) {
    const labelTranslation = t(field.label);
    if (labelTranslation !== field.label) {
      return labelTranslation;
    }
  }
  
  // 2. Spezifische Form-Übersetzung (für spezifische Abschnitte)
  if (formName && sectionName) {
    const specificKey = `jaynaApp.${formName}.${sectionName}.fields.${field.name}`;
    const specificTranslation = t(specificKey);
    if (specificTranslation !== specificKey) {
      return specificTranslation;
    }
  }
  
  // 3. Base-Entity-Übersetzung (für allgemeine Abschnitte)
  if (sectionName) {
    const entityType = getEntityTypeFromFormName(formName);
    const baseKey = `jaynaApp.base${entityType}.${sectionName}.fields.${field.name}`;
    const baseTranslation = t(baseKey);
    if (baseTranslation !== baseKey) {
      return baseTranslation;
    }
  }
  
  // 4. Fallback: Verwende field.label oder field.name
  return field.label || field.name;
}



// Hilfsfunktion um Entity-Typ aus Form-Namen zu extrahieren
const getEntityTypeFromFormName = (formName) => {
  if (formName.startsWith('contract-')) return 'Contract';
  if (formName.startsWith('contact-')) return 'Contact';
  if (formName.startsWith('mobility-')) return 'Mobility';
  if (formName.startsWith('realestate-')) return 'Realestate';
  if (formName.startsWith('financeaccount-')) return 'Financeaccount';
  if (formName.startsWith('document-')) return 'Document';
  if (formName === 'contact') return 'Contact';
  if (formName === 'contract') return 'Contract';
  if (formName === 'mobility') return 'Mobility';
  if (formName === 'realestate') return 'Realestate';
  if (formName === 'financeaccount') return 'Financeaccount';
  if (formName === 'document') return 'Document';
  return 'Contract'; // Fallback
}

// Hilfsfunktion um zu prüfen, ob ein Feld ein Pflichtfeld ist
const isFieldRequired = (field) => {
  if (!field.rules) return false;
  
  // Falls rules ein String ist
  if (typeof field.rules === 'string') {
    return field.rules.includes('required');
  }
  
  // Falls rules ein Array ist
  if (Array.isArray(field.rules)) {
    return field.rules.includes('required');
  }
  
  return false;
};

const getRelationsTitle = () => {
  // Versuche zuerst die spezifische Übersetzung
  const specificKey = `jaynaApp.${effectiveFormName.value}.relations-title`;
  const specificTranslation = t(specificKey);
  if (specificTranslation !== specificKey) {
    return specificTranslation;
  }
  
  // Fallback auf Base-Entity
  const entityType = getEntityTypeFromFormName(effectiveFormName.value);
  const baseKey = `jaynaApp.base${entityType}.relations.title`;
  const baseTranslation = t(baseKey);
  if (baseTranslation !== baseKey) {
    return baseTranslation;
  }
  
  // Finaler Fallback
  return 'Verknüpfungen';
}





const getSubCategoryDisplayName = (subCategory) => {
  if (!subCategory) return '';
  
  // Verwende Übersetzungsschlüssel, falls verfügbar
  if (subCategory.translationKey) {
    const translation = t(subCategory.translationKey);
    if (translation !== subCategory.translationKey) {
      return translation;
    }
  }
  
  // Fallback auf name
  return subCategory.name || '';
};

const getSubCategoryGroupDisplayName = (subCategoryGroup) => {
  if (!subCategoryGroup) return '';
  
  // Verwende Übersetzungsschlüssel, falls verfügbar
  if (subCategoryGroup.translationKey) {
    const translation = t(subCategoryGroup.translationKey);
    if (translation !== subCategoryGroup.translationKey) {
      return translation;
    }
  }
  
  // Fallback auf name
  return subCategoryGroup.name || '';
};

const getGroupTypeDisplayName = (groupType) => {
  if (!groupType) return '';
  
  // Verwende Übersetzungsschlüssel, falls verfügbar
  if (groupType.translationKey) {
    const translation = t(groupType.translationKey);
    if (translation !== groupType.translationKey) {
      return translation;
    }
  }
  
  // Fallback auf name
  return groupType.name || '';
};

const formatGroupTypeName = (name) => {
  if (!name) return '';
  
  // Behalte Sterne und Zusatz in der Detailansicht bei
  return name;
};




</script>

<style scoped>
/* Styles für Detail-Ansicht Felder */
.detail-view-field :deep(input) {
  background-color: #f3f4f6 !important; /* bg-gray-100 - dunkleres Grau für Detail-Ansicht */
  color: #111827 !important; /* text-gray-900 */
  border-color: #d1d5db !important; /* border-gray-300 */
  min-height: 2.5rem !important; /* Höhere Felder */
  padding: 0.5rem 0.75rem !important; /* Mehr Padding */
}

.detail-view-field :deep(textarea) {
  background-color: #f3f4f6 !important; /* bg-gray-100 - dunkleres Grau für Detail-Ansicht */
  color: #111827 !important;
  border-color: #d1d5db !important;
  min-height: 2.5rem !important;
  padding: 0.5rem 0.75rem !important;
  cursor: not-allowed !important;
  pointer-events: none !important;
}

.detail-view-field :deep(.dp__input) {
  padding-left: 0.75rem !important;
  padding-right: 2.5rem !important;
  min-height: 2.5rem !important;
  background-color: #f3f4f6 !important; /* bg-gray-100 - dunkleres Grau für Detail-Ansicht */
  color: #111827 !important;
  border-color: #d1d5db !important;
  cursor: not-allowed !important;
}

/* Spezifische Styles für DatePicker in Detail-Ansicht */
.detail-date-input {
  padding-left: 0.75rem !important;
  padding-right: 2.5rem !important;
  min-height: 2.5rem !important;
  background-color: #f3f4f6 !important; /* bg-gray-100 - dunkleres Grau für Detail-Ansicht */
  color: #111827 !important;
  border-color: #d1d5db !important;
  cursor: not-allowed !important;
}

/* Zusätzliche DatePicker Styles für disabled Zustand */
.detail-view-field :deep(.dp__main) {
  cursor: not-allowed !important;
}

.detail-view-field :deep(.dp__input_wrap) {
  cursor: not-allowed !important;
}

.detail-view-field :deep(.dp__input_wrap input) {
  cursor: not-allowed !important;
  pointer-events: none !important;
}

/* Headless UI Dropdown Button Styling */
.detail-view-field :deep(.relative.w-full.cursor-pointer.rounded-md.border.border-gray-500.bg-white.py-2.pl-3.pr-10) {
  background-color: #f3f4f6 !important; /* bg-gray-100 - dunkleres Grau für Detail-Ansicht */
  color: #111827 !important;
  border-color: #d1d5db !important;
  min-height: 2.5rem !important;
  padding: 0.5rem 0.75rem !important;
  display: flex !important;
  align-items: center !important;
  cursor: not-allowed !important;
}

/* Dropdown Container Höhe anpassen */
.detail-view-field :deep(.relative.mt-1) {
  min-height: 2.5rem !important;
}

/* Verstecke Chevron-Pfeil bei disabled Dropdowns */
.detail-view-field :deep(.pointer-events-none.absolute.inset-y-0.right-0.flex.items-center.pr-2) {
  display: none !important;
}

/* Entferne "Bitte auswählen" Text bei disabled Dropdowns wenn kein Wert ausgewählt */
.detail-view-field :deep(.block.truncate:empty::before) {
  content: "" !important;
}

/* Alternative: Verstecke den Text wenn er "Bitte auswählen" ist */
.detail-view-field :deep(.block.truncate) {
  color: #111827 !important;
}

/* Checkbox und Radio Styling */
.detail-view-field :deep(.form-checkbox),
.detail-view-field :deep(.form-radio) {
  background-color: #f3f4f6 !important; /* bg-gray-100 - dunkleres Grau für Detail-Ansicht */
  border-color: #d1d5db !important;
  cursor: not-allowed !important;
}

/* Checkbox und Radio Input Styling */
.detail-view-field :deep(input[type="checkbox"]),
.detail-view-field :deep(input[type="radio"]) {
  cursor: not-allowed !important;
}

/* Entferne opacity bei disabled Feldern */
.detail-view-field :deep(.disabled\:opacity-50) {
  opacity: 1 !important;
}

/* Entferne cursor-pointer bei disabled Dropdowns */
.detail-view-field :deep(.cursor-pointer) {
  cursor: default !important;
}

/* Spezifische Styles für s-input Komponenten im oberen Bereich */
.detail-view-field :deep(.s-input input) {
  background-color: #f3f4f6 !important; /* bg-gray-100 - dunkleres Grau für Detail-Ansicht */
  color: #111827 !important;
  border-color: #d1d5db !important;
  min-height: 2.5rem !important;
  padding: 0.5rem 0.75rem !important;
}

/* Zusätzliche Styles für s-input Container */
.detail-view-field :deep(.s-input) {
  background-color: #f3f4f6 !important; /* bg-gray-100 - dunkleres Grau für Detail-Ansicht */
}

/* Styles für s-input Labels */
.detail-view-field :deep(.s-input label) {
  color: #374151 !important; /* text-gray-700 */
}

/* Textarea Styles für Detail-Ansicht */
.detail-view-field :deep(textarea) {
  background-color: #f3f4f6 !important; /* bg-gray-100 - dunkleres Grau für Detail-Ansicht */
  color: #111827 !important;
  border-color: #d1d5db !important;
  cursor: not-allowed !important;
  pointer-events: none !important;
  opacity: 1 !important;
}

.required::after {
  content: " *";
  color: #e53e3e;
  font-weight: bold;
}
</style>

