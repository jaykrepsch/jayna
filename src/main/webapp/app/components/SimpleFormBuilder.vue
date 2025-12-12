<template>
  <!-- <div :class="['md:grid md:gap-6', fields.length <= 2 ? 'md:grid-cols-2' : 'md:grid-cols-4']"> -->
  <div :class="['md:grid md:gap-6', 'md:grid-cols-3']">
    <!-- Fallback-Felder anzeigen wenn keine Felder definiert sind -->
    <div v-if="!fields || fields.length === 0" class="col-span-3 bg-red-100 p-4 rounded">
      <h4 class="font-bold text-red-800">⚠️ Keine Felder definiert für: {{ sectionConfig?.title || 'Unbekannte Sektion' }}</h4>
      <p class="text-red-600">Form-Konfiguration Problem für Sektion: {{ sectionConfig?.title || 'Unbekannt' }}</p>
      <p class="text-sm text-red-500">Form-Name: {{ formName }}</p>
    </div>
    <!-- :class="[editable ? 'relative group hover:opacity-50' : '', field.class]" -->
    <div
      v-else
      v-for="(field, index) in fields"
      v-show="field.dependsOnField ? v$[field.dependsOnField].$model === field.dependsOnValue : true"
      :key="index"
      :class="[field.class]"
    >
              <div>
        <p v-if="field.type === 'info'">
          {{ $t(field['text-key']) }}
        </p>
        <s-input
          v-if="field.type === 'text' || field.type === 'number'"
          v-model="v$[field.name].$model"
          :name="field.name"
          :type="field.type"
          :placeholder="getPlaceHolder(field)"
          :label="getLabel(field, formName, sectionConfig)"
          :errors="v$[field.name].$errors"
          :required="isFieldRequired(field)"
          :disabled="field.disabled"
          :class="getFieldValidationClass(field)"
          @input="onFieldInput(field)"
        />

        <s-textarea
          v-if="field.type === 'textarea'"
          v-model="v$[field.name].$model"
          :name="field.name"
          :placeholder="getPlaceHolder(field)"
          :label="getLabel(field, formName, sectionConfig)"
          :errors="v$[field.name].$errors"
          :required="isFieldRequired(field)"
          :class="getFieldValidationClass(field)"
          @input="onFieldInput(field)"
        />

        <div v-if="field.type === 'date'">
          <label
            :for="field.name"
            :class="[isFieldRequired(field) ? 'required' : '', 'block text-sm font-medium text-gray-700']"
            v-text="getLabel(field, formName, sectionConfig)"
          />
          <date-picker
            v-model="v$[field.name].$model"
            class="mt-1"
            locale="de"
            :name="field.name"
            :placeholder="getPlaceHolder(field)"
            :label="getLabel(field, formName, sectionConfig)"
            :select-text="$t('global.menu.choose')"
            :cancel-text="$t('button.cancel')"
            :enable-time-picker="field.enableTimePicker || false"
            :auto-apply="field.autoApply || true"
            :text-input="field.textInput || true"
            :format="field.format || 'dd.MM.yyyy'"
            :input-class-name="getDatePickerValidationClass(field)"
            :position="field.position || 'left'"
            @update:model-value="onFieldInput(field)"
          />
        </div>

        <Dropdown
          v-if="field.type === 'dropdown'"
          v-model="v$[field.name].$model"
          :label="getLabel(field, formName, sectionConfig)"
          :box-label="$t(field.boxLabel || 'global.menu.choose')"
          :items="getDropdownItemsForField(field).value || []"
          :required="isFieldRequired(field)"
          :class="getFieldValidationClass(field)"
          @update:model-value="onFieldInput(field)"
        />

        <Checkbox
          v-if="field.type === 'checkbox'"
          v-model="v$[field.name].$model"
          :name="field.name"
          :label="getLabel(field, formName, sectionConfig)"
          @update:model-value="onFieldInput(field)"
        />

        <RadioGroup
          v-if="field.type === 'radio'"
          v-model="v$[field.name].$model"
          :name="field.name"
          :label="getLabel(field, formName, sectionConfig)"
          :items="field.items"
          @update:model-value="onFieldInput(field)"
        />

        <RelationDropdown
          v-if="field.type === 'relation-dropdown'"
          v-model="v$[field.name].$model"
          :label="getLabel(field, formName, sectionConfig)"
          :box-label="$t(field.boxLabel || 'global.menu.choose')"
          :required="isFieldRequired(field)"
          :entity-name="field.entityName"
          :display-field="field.displayField"
          :class="getFieldValidationClass(field)"
          @update:model-value="onFieldInput(field)"
        />

        <!-- Spezielle Feldtypen werden ignoriert -->

        <!-- <FileUpload
          v-if="field.type === 'file'"
          v-model="fileData"
          :name="field.name"
          :placeholder="$t(useGetLabel(field, formName))"
          :label="$t(useGetLabel(field, formName))"
          :multiple="true"
        /> -->
      </div>

    </div>

  </div>

</template>

<script setup>

import DatePicker from '@vuepic/vue-datepicker';
import SInput from '@/components/forms/input.vue';
import STextarea from '@/components/forms/textarea.vue';
import Dropdown from '@/components/forms/dropdown.vue';
import Checkbox from '@/components/forms/checkbox.vue';
// import FileUpload from '@/components/FileUpload.vue';
import RadioGroup from '@/components/forms/RadioGroup.vue';
import RelationDropdown from '@/components/forms/RelationDropdown.vue';


import { required, email } from '@/shared/config/validators';

import useVuelidate from '@vuelidate/core';
import { useStore } from 'vuex';
import { reactive, computed, ref, watch } from 'vue';
import { useGetLabel } from '@/composables/utils';
import { useI18n } from 'vue-i18n';

const store = useStore();
const fileData = ref(null);
const { t, te } = useI18n();

// Validierungsstatus für jedes Feld
const fieldValidationStatus = reactive({});

const props = defineProps({
  fields: {
    type: Array,
    required: true
  },
  data: {
    type: Object,
    default: null
  },
  scoped: {
    type: Boolean,
    default: false
  },
  formName: String,
  editable: {
    type: Boolean,
    default: false
  },
  sectionConfig: {
    type: Object,
    default: null
  }
});

const tmpState = {};
const tmpRules = {};
const dropdownItems = reactive({});

const getValidationRules = (ruleNames) => {
  const rules = {};

  ruleNames?.split(',').forEach((ruleName) => {
    if (ruleName === 'required') {
      rules['required'] = required;
    } else if (ruleName === 'email') {
      rules['email'] = email;
    }
  });

  return rules;
};

// Reaktive Dropdown-Items mit Computed Properties
const getDropdownItemsForField = (field) => {
  if (field.enumName) {
    return computed(() => {
      return store?.getters?.[`config/${field.enumName}`] || [];
    });
  }
  return field.items || [];
};

for (const field of props.fields) {
  if (field.type === 'info' || field.type === 'file') continue;
  tmpRules[field.name] = getValidationRules(field.rules);
  if (field.type === 'dropdown') {
    // Erstelle reaktive Dropdown-Items
    dropdownItems[field.name] = getDropdownItemsForField(field);
    
    // Initialisiere den State basierend auf verfügbaren Items
    const items = field.enumName ? 
      computed(() => store?.getters?.[`config/${field.enumName}`] || []) : 
      field.items || [];

    if (props.data && props.data[field.name]) {
      tmpState[field.name] = items.value?.find(i => i.name === props.data[field.name]) || null;
    } else if (field.defaultValue) {
      tmpState[field.name] = items.value?.find(i => i.name === field.defaultValue) || null;
    } else {
      tmpState[field.name] = null;
    }
  } else if (field.type === 'checkbox') {
    tmpState[field.name] = props.data ? props.data[field.name] : (field.defaultValue || false);
  } else if (field.type === 'relation-dropdown') {
    tmpState[field.name] = props.data && props.data[field.relationName] && props.data[field.relationName][0] ? props.data[field.relationName][0][field.entityName] : null;
  } else {
    tmpState[field.name] = props.data ? props.data[field.name] : (field.defaultValue || null);
  }
}

const processFormInternalRelations = (data) => {
  const formInternalRelations = [];

  props.fields.forEach(f => {
    if (f.type === 'relation-dropdown') {
      formInternalRelations.push(f);
    }
  });

  formInternalRelations.forEach(r => {
    const relationObject = {};
    relationObject[r.entityName] = data[r.name];
    data[r.relationName] = [relationObject];
    delete data[r.name];
  });
};

const state = reactive(tmpState);
const rules = computed(() => (tmpRules));
const v$ = useVuelidate(rules, state);

// Validierungsfunktionen
const isFieldRequired = (field) => {
  // Prüfe zuerst field.required (Boolean)
  if (field.required === true) {
    return true;
  }
  
  // Prüfe dann field.rules (String oder Array)
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

const isFieldEmpty = (field) => {
  const value = state[field.name];
  
  if (field.type === 'dropdown') {
    return value === null || value === undefined || value === '';
  } else if (field.type === 'checkbox') {
    return value === false;
  } else {
    return value === null || value === undefined || value === '' || (Array.isArray(value) && value.length === 0);
  }
};

const isFieldValid = (field) => {
  if (!isFieldRequired(field)) {
    return true; // Nicht-required Felder sind immer gültig
  }
  
  return !isFieldEmpty(field);
};

const getFieldValidationClass = (field) => {
  if (!isFieldRequired(field)) {
    return '';
  }
  
  const isValid = isFieldValid(field);
  const hasBeenTouched = fieldValidationStatus[field.name]?.touched || false;
  
  // Wenn das Feld berührt wurde oder beim Speichern-Klick validiert werden soll
  if (hasBeenTouched || fieldValidationStatus[field.name]?.shouldValidate) {
    return isValid ? '' : 'field-invalid';
  }
  
  return ''; // Keine Validierung vor der ersten Berührung
};

const getDatePickerValidationClass = (field) => {
  const baseClass = 'date-picker';
  const validationClass = getFieldValidationClass(field);
  return `${baseClass} ${validationClass}`;
};

const onFieldInput = (field) => {
  // Markiere das Feld als berührt
  if (!fieldValidationStatus[field.name]) {
    fieldValidationStatus[field.name] = {};
  }
  fieldValidationStatus[field.name].touched = true;
  
  // Validiere das Feld sofort
  const isValid = isFieldValid(field);
  fieldValidationStatus[field.name].valid = isValid;
  
  // Trigger Vuelidate validation
  v$.value.$touch();
};

// Neue Funktion: Markiere alle required Felder für Validierung
const markAllRequiredFieldsForValidation = () => {
  props.fields.forEach(field => {
    if (isFieldRequired(field)) {
      if (!fieldValidationStatus[field.name]) {
        fieldValidationStatus[field.name] = {};
      }
      fieldValidationStatus[field.name].shouldValidate = true;
    }
  });
};

const getData = () => {
  try {
    const data = Object.assign({}, state);

    processFormInternalRelations(data);

    props.fields.forEach(field => {
      if (field.enumName !== undefined) {
        data[field.name] = state[field.name]?.name;
      }
      if (field.type === 'number' && state[field.name] !== null) {
        if (Number.isInteger(state[field.name])) {
          data[field.name] = parseInt(state[field.name]);
        } else {
          data[field.name] = parseFloat(state[field.name]);
        }
      }
    });

    console.log('SimpleFormBuilder getData result:', data);
    return data;
  } catch (error) {
    console.error('Error in SimpleFormBuilder getData:', error);
    return {};
  }
};

const isValid = () => {
  try {
    // Markiere alle required Felder für Validierung beim ersten Aufruf
    markAllRequiredFieldsForValidation();
    
    // Prüfe alle Validierungsregeln von Vuelidate
    if (v$.value.$invalid) {
      console.log('Vuelidate validation failed:', v$.value.$errors);
      return false;
    }
    
    // Prüfe required Felder
    for (const field of props.fields) {
      if (isFieldRequired(field)) {
        if (isFieldEmpty(field)) {
          console.log(`Required field '${field.name}' is empty`);
          return false;
        }
      }
    }
    
    console.log('Form validation passed');
    return true;
  } catch (error) {
    console.error('Error in form validation:', error);
    return false;
  }
};

const getPlaceHolder = (field) => {
  // Für Dokumente: Keine Placeholder verwenden
  if (props.formName && (props.formName.includes('document') || props.formName.includes('Document'))) {
    return '';
  }
  
  if (field.placeholder && te(field.placeholder)) {
    return t(field.placeholder);
  }
  
  // Prüfe i18nSource aus dem Abschnitt (falls verfügbar)
  if (props.sectionConfig?.i18nSource) {
    const i18nSourceKey = `jaynaApp.${props.sectionConfig.i18nSource}.fields.${field.name}`;
    if (te(i18nSourceKey)) {
      return t(i18nSourceKey);
    }
  }
  
  // Prüfe form-spezifische Übersetzung
  if (te(`jaynaApp.${props.formName}.fields.${field.name}`)) {
    return t(`jaynaApp.${props.formName}.fields.${field.name}`)
  }
  
  // Prüfe Base-Übersetzungen für alle Entitäten
  const baseKeys = [
    `jaynaApp.baseContract.fields.${field.name}`,
    `jaynaApp.baseContact.fields.${field.name}`,
    `jaynaApp.baseRealestate.fields.${field.name}`,
    `jaynaApp.baseMobility.fields.${field.name}`,
    `jaynaApp.baseFinanceaccount.fields.${field.name}`
  ];
  
  for (const baseKey of baseKeys) {
    if (te(baseKey)) {
      return t(baseKey);
    }
  }
  
  return field.placeholder;
};

const getLabel = (field, formName, sectionConfig = null) => {
  // SYSTEMATISCHE FELD-ÜBERSETZUNGSLOGIK (wie im DetailViewBuilder)

  // 1. Direkte Übersetzung (falls field.label ein Übersetzungsschlüssel ist)
  if (field.label) {
    const labelTranslation = t(field.label);
    if (labelTranslation !== field.label) {
      return labelTranslation;
    }
  }

  // 2. Spezifische Form-Übersetzung (für spezifische Abschnitte)
  const sectionName = getSectionName(sectionConfig?.title);
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
};


// Hilfsfunktion zum Extrahieren des Abschnittsnamens aus dem Formular-Titel
const getSectionName = (title) => {
  if (!title) return null;
  
  // Extrahiere den Abschnittsnamen direkt aus dem Übersetzungsschlüssel
  const match = title.match(/jaynaApp\.[^.]+\.([^.]+)\.title/);
  if (match) {
    return match[1];
  }
  
  return null;
};

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
};

// Spezielle Feldtypen werden ignoriert

defineExpose({ getData, isValid });

// Reaktiver Watcher für Validierung
watch(() => state, () => {
  // Trigger validation check when state changes
  v$.value.$touch();
}, { deep: true });

// Zusätzlicher Watcher für v$ Änderungen
watch(v$, () => {
  // Validation state changed
}, { deep: true });

defineEmits(['deleteField']);

</script>

<style>
.required::after {
  content: " *";
  color: #dc2626; /* rot für required Felder */
  font-weight: bold;
}

/* Validierungsstile für Felder */
.field-invalid {
  border-color: #dc2626 !important; /* rot */
  border-width: 2px !important;
}

/* Spezifische Styles für DatePicker mit Validierung */
.date-picker.field-invalid {
  border-color: #dc2626 !important;
  border-width: 2px !important;
}

.date-picker {
  border-color: #6b7280;
  font-size: 0.875rem;
  background-color: #f9fafb !important; /* 50% helleres Grau (bg-gray-50) */
  color: #111827 !important; /* text-gray-900 */
  border-color: #d1d5db !important; /* border-gray-300 */
  min-height: 2.5rem !important;
  padding: 0.5rem 0.75rem !important;
}

.date-picker:focus {
  outline: 2px solid transparent;
  outline-offset: 2px;
  --tw-ring-inset: var(--tw-empty,/*!*/ /*!*/);
  --tw-ring-offset-width: 0px;
  --tw-ring-offset-color: #fff;
  --tw-ring-color: #2563eb;
  --tw-ring-offset-shadow: var(--tw-ring-inset) 0 0 0 var(--tw-ring-offset-width) var(--tw-ring-offset-color);
  --tw-ring-shadow: var(--tw-ring-inset) 0 0 0 calc(1px + var(--tw-ring-offset-width)) var(--tw-ring-color);
  -webkit-box-shadow: var(--tw-ring-offset-shadow), var(--tw-ring-shadow), var(--tw-shadow);
  box-shadow: var(--tw-ring-offset-shadow), var(--tw-ring-shadow), var(--tw-shadow);
  border-color: #2563eb;
}

/* Focus-States für alle Eingabefelder */
:deep(input:focus),
:deep(textarea:focus),
:deep(select:focus),
:deep(.dp__input:focus) {
  outline: 2px solid transparent !important;
  outline-offset: 2px !important;
  --tw-ring-inset: var(--tw-empty,/*!*/ /*!*/);
  --tw-ring-offset-width: 0px !important;
  --tw-ring-offset-color: #fff !important;
  --tw-ring-color: #2563eb !important;
  --tw-ring-offset-shadow: var(--tw-ring-inset) 0 0 0 var(--tw-ring-offset-width) var(--tw-ring-offset-color) !important;
  --tw-ring-shadow: var(--tw-ring-inset) 0 0 0 calc(1px + var(--tw-ring-offset-width)) var(--tw-ring-color) !important;
  -webkit-box-shadow: var(--tw-ring-offset-shadow), var(--tw-ring-shadow), var(--tw-shadow) !important;
  box-shadow: var(--tw-ring-offset-shadow), var(--tw-ring-shadow), var(--tw-shadow) !important;
  border-color: #2563eb !important;
}

/* Zusätzliche spezifische Selektoren für verschiedene Input-Typen */
:deep(.block.w-full.rounded-md.pr-10.sm\\:text-sm input),
:deep(.block.w-full.rounded-md input),
:deep(.date-input) {
  background-color: #f9fafb !important; /* 50% helleres Grau (bg-gray-50) */
  color: #000000 !important; /* schwarze Schrift */
  border: 1px solid #d1d5db !important;
  min-height: 2.5rem !important;
  padding: 0.5rem 0.75rem !important;
  border-radius: 0.375rem !important;
}

/* Sehr spezifische Styles für alle Eingabefelder in der Bearbeitungsansicht */
:deep(.relative.mt-1.rounded-md.shadow-sm input),
:deep(.block.w-full.rounded-md input),
:deep(input[type="text"]),
:deep(input[type="number"]),
:deep(input[type="email"]),
:deep(input[type="password"]),
:deep(input[type="search"]),
:deep(input[type="tel"]),
:deep(input[type="url"]),
:deep(input[type="date"]) {
  background-color: #f9fafb !important; /* 50% helleres Grau (bg-gray-50) */
  color: #000000 !important; /* schwarze Schrift */
  border-color: #d1d5db !important; /* border-gray-300 */
  min-height: 2.5rem !important;
  padding: 0.5rem 0.75rem !important;
  border-radius: 0.375rem !important;
  border: 1px solid #d1d5db !important;
}

:deep(textarea) {
  background-color: #f9fafb !important; /* 50% helleres Grau (bg-gray-50) */
  color: #000000 !important; /* schwarze Schrift */
  border-color: #d1d5db !important;
  min-height: 2.5rem !important;
  padding: 0.5rem 0.75rem !important;
  border-radius: 0.375rem !important;
  border: 1px solid #d1d5db !important;
}

:deep(.dp__input) {
  padding-left: 0.75rem !important;
  padding-right: 2.5rem !important;
  min-height: 2.5rem !important;
  background-color: #f9fafb !important; /* 50% helleres Grau (bg-gray-50) */
  color: #000000 !important; /* schwarze Schrift */
  border-color: #d1d5db !important;
  border-radius: 0.375rem !important;
  border: 1px solid #d1d5db !important;
}

/* Zusätzliche spezifische Styles für DatePicker in Create/Update-Ansicht */
:deep(.date-picker .dp__input) {
  background-color: #f9fafb !important; /* 50% helleres Grau (bg-gray-50) */
  color: #000000 !important; /* schwarze Schrift */
  border-color: #d1d5db !important;
  border-radius: 0.375rem !important;
  border: 1px solid #d1d5db !important;
  min-height: 2.5rem !important;
  padding: 0.5rem 0.75rem !important;
}

/* Überschreibe alle möglichen DatePicker-Styles */
:deep(.dp__main .dp__input) {
  background-color: #f9fafb !important; /* 50% helleres Grau (bg-gray-50) */
  color: #000000 !important; /* schwarze Schrift */
  border-color: #d1d5db !important;
  border-radius: 0.375rem !important;
  border: 1px solid #d1d5db !important;
  min-height: 2.5rem !important;
  padding: 0.5rem 0.75rem !important;
}

/* Styles für Dropdown-Komponenten (Headless UI) */
:deep(.relative.w-full.cursor-pointer.rounded-md.border.border-gray-500.bg-white.py-2.pl-3.pr-10) {
  background-color: #f9fafb !important; /* 50% helleres Grau (bg-gray-50) */
  color: #000000 !important; /* schwarze Schrift */
  border-color: #d1d5db !important;
  min-height: 2.5rem !important;
  padding: 0.5rem 0.75rem !important;
  display: flex !important;
  align-items: center !important;
  border: 1px solid #d1d5db !important;
}

/* Alternative Dropdown-Selektoren */
:deep(.relative.w-full.cursor-pointer.rounded-md) {
  background-color: #f9fafb !important; /* 50% helleres Grau (bg-gray-50) */
  color: #000000 !important; /* schwarze Schrift */
  border-color: #d1d5db !important;
  min-height: 2.5rem !important;
  padding: 0.5rem 0.75rem !important;
  border: 1px solid #d1d5db !important;
}

/* Styles für Select-Komponenten */
:deep(select) {
  background-color: #f9fafb !important; /* 50% helleres Grau (bg-gray-50) */
  color: #000000 !important; /* schwarze Schrift */
  border-color: #d1d5db !important;
  min-height: 2.5rem !important;
  padding: 0.5rem 0.75rem !important;
  border-radius: 0.375rem !important;
  border: 1px solid #d1d5db !important;
}

/* Styles für Checkbox und Radio */
:deep(.h-4.w-4.rounded.border-gray-300) {
  background-color: #f9fafb !important; /* 50% helleres Grau (bg-gray-50) */
  border-color: #d1d5db !important;
}

:deep(.h-4.w-4.border-gray-300) {
  background-color: #f9fafb !important; /* 50% helleres Grau (bg-gray-50) */
  border-color: #d1d5db !important;
}

/* Zusätzliche Styles für s-input Komponenten */
:deep(.s-input input) {
  background-color: #f9fafb !important; /* 50% helleres Grau (bg-gray-50) */
  color: #000000 !important; /* schwarze Schrift */
  border-color: #d1d5db !important;
  min-height: 2.5rem !important;
  padding: 0.5rem 0.75rem !important;
  border-radius: 0.375rem !important;
  border: 1px solid #d1d5db !important;
}

/* Styles für s-textarea Komponenten */
:deep(.s-textarea textarea) {
  background-color: #f9fafb !important; /* 50% helleres Grau (bg-gray-50) */
  color: #000000 !important; /* schwarze Schrift */
  border-color: #d1d5db !important;
  min-height: 2.5rem !important;
  padding: 0.5rem 0.75rem !important;
  border-radius: 0.375rem !important;
  border: 1px solid #d1d5db !important;
}
</style>
