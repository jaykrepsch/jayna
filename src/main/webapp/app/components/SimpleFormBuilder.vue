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
      v-show="isFieldVisible(field)"
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
import { reactive, computed, ref, watch, onMounted } from 'vue';
import { useStore } from 'vuex';
import { useI18n } from 'vue-i18n';
import useVuelidate from '@vuelidate/core';
import DatePicker from '@vuepic/vue-datepicker';

import SInput from '@/components/forms/input.vue';
import STextarea from '@/components/forms/textarea.vue';
import Dropdown from '@/components/forms/dropdown.vue';
import Checkbox from '@/components/forms/checkbox.vue';
import RadioGroup from '@/components/forms/RadioGroup.vue';
import RelationDropdown from '@/components/forms/RelationDropdown.vue';

import { required, email } from '@/shared/config/validators';

const props = defineProps({
  fields: { type: Array, required: true },
  data: { type: Object, default: null },
  scoped: { type: Boolean, default: false },
  formName: String,
  editable: { type: Boolean, default: false },
  sectionConfig: { type: Object, default: null }
});

const emit = defineEmits(['deleteField']);
const store = useStore();
const { t, te } = useI18n();

const state = reactive({});
const fieldValidationStatus = reactive({});

// Hilfsfunktion für Validierungsregeln
const getValidationRules = (ruleNames) => {
  const rules = {};
  if (!ruleNames) return rules;
  
  const names = typeof ruleNames === 'string' ? ruleNames.split(',') : (Array.isArray(ruleNames) ? ruleNames : []);
  
  names.forEach((ruleName) => {
    if (ruleName.trim() === 'required') {
      rules['required'] = required;
    } else if (ruleName.trim() === 'email') {
      rules['email'] = email;
    }
  });
  return rules;
};

// Hilfsfunktion zur Prüfung der Sichtbarkeit eines Feldes
const isFieldVisible = (field) => {
  if (!field.dependsOnField) return true;
  const dependsOnValue = state[field.dependsOnField];
  return dependsOnValue === field.dependsOnValue;
};

// Computed Rules für Vuelidate (wichtig für asynchron geladene Felder)
const validationRules = computed(() => {
  const rules = {};
  props.fields.forEach(field => {
    // Nur Regeln für sichtbare Felder hinzufügen
    if (field.type !== 'info' && field.type !== 'file' && isFieldVisible(field)) {
      rules[field.name] = getValidationRules(field.rules);
    }
  });
  return rules;
});

const v$ = useVuelidate(validationRules, state);

const getDropdownItemsForField = (field) => {
  if (field.enumName) {
    return computed(() => store?.getters?.[`config/${field.enumName}`] || []);
  }
  return field.items || [];
};

const initForm = () => {
  props.fields.forEach(field => {
    if (field.type === 'info' || field.type === 'file') return;
    
    if (state[field.name] === undefined) {
      if (field.type === 'dropdown') {
        const items = field.enumName ? (store?.getters?.[`config/${field.enumName}`] || []) : (field.items || []);
        if (props.data && props.data[field.name]) {
          state[field.name] = items.find(i => i.name === props.data[field.name]) || null;
        } else if (field.defaultValue) {
          state[field.name] = items.find(i => i.name === field.defaultValue) || null;
        } else {
          state[field.name] = null;
        }
      } else if (field.type === 'checkbox') {
        state[field.name] = props.data ? props.data[field.name] : (field.defaultValue || false);
      } else if (field.type === 'relation-dropdown') {
        state[field.name] = props.data && props.data[field.relationName] && props.data[field.relationName][0] ? props.data[field.relationName][0][field.entityName] : null;
      } else {
        state[field.name] = props.data ? props.data[field.name] : (field.defaultValue || null);
      }
    }
  });
};

watch(() => props.fields, () => {
  initForm();
}, { immediate: true, deep: true });

const isFieldRequired = (field) => {
  if (field.required === true) return true;
  if (!field.rules) return false;
  return typeof field.rules === 'string' ? field.rules.includes('required') : (Array.isArray(field.rules) && field.rules.includes('required'));
};

const isFieldEmpty = (field) => {
  const value = state[field.name];
  if (field.type === 'dropdown') return value === null || value === undefined;
  if (field.type === 'checkbox') return value === false;
  return value === null || value === undefined || value === '' || (Array.isArray(value) && value.length === 0);
};

const isFieldValid = (field) => {
  if (!isFieldRequired(field)) return true;
  return !isFieldEmpty(field);
};

const getFieldValidationClass = (field) => {
  if (!isFieldRequired(field)) return '';
  const hasBeenTouched = fieldValidationStatus[field.name]?.touched || fieldValidationStatus[field.name]?.shouldValidate;
  if (hasBeenTouched) {
    return isFieldValid(field) ? '' : 'field-invalid';
  }
  return '';
};

const getDatePickerValidationClass = (field) => `date-picker ${getFieldValidationClass(field)}`;

const onFieldInput = (field) => {
  if (!fieldValidationStatus[field.name]) fieldValidationStatus[field.name] = {};
  fieldValidationStatus[field.name].touched = true;
  v$.value.$touch();
};

const markAllRequiredFieldsForValidation = () => {
  props.fields.forEach(field => {
    if (isFieldRequired(field)) {
      if (!fieldValidationStatus[field.name]) fieldValidationStatus[field.name] = {};
      fieldValidationStatus[field.name].shouldValidate = true;
    }
  });
};

const getData = () => {
  const data = { ...state };
  props.fields.forEach(f => {
    if (f.type === 'relation-dropdown') {
      const relObj = { [f.entityName]: data[f.name] };
      data[f.relationName] = [relObj];
      delete data[f.name];
    }
    if (f.enumName !== undefined) data[f.name] = state[f.name]?.name;
    if (f.type === 'number' && state[f.name] !== null) {
      data[f.name] = Number.isInteger(state[f.name]) ? parseInt(state[f.name]) : parseFloat(state[f.name]);
    }
  });
  return data;
};

const isValid = () => {
  markAllRequiredFieldsForValidation();
  if (v$.value.$invalid) return false;
  for (const field of props.fields) {
    if (isFieldVisible(field) && isFieldRequired(field) && isFieldEmpty(field)) return false;
  }
  return true;
};

const getPlaceHolder = (field) => {
  if (props.formName?.toLowerCase().includes('document')) return '';
  if (field.placeholder && te(field.placeholder)) return t(field.placeholder);
  const baseKeys = [`jaynaApp.baseContract.fields.${field.name}`, `jaynaApp.baseContact.fields.${field.name}`, `jaynaApp.baseRealestate.fields.${field.name}`];
  for (const key of baseKeys) { if (te(key)) return t(key); }
  return field.placeholder || '';
};

const getLabel = (field, formName, sectionConfig) => {
  if (field.label && te(field.label)) return t(field.label);
  const sectionName = sectionConfig?.title?.match(/jaynaApp\.[^.]+\.([^.]+)\.title/)?.[1];
  if (formName && sectionName) {
    const specKey = `jaynaApp.${formName}.${sectionName}.fields.${field.name}`;
    if (te(specKey)) return t(specKey);
  }
  if (sectionName) {
    const entityType = formName?.startsWith('contract') ? 'Contract' : (formName?.startsWith('realestate') ? 'Realestate' : 'Contract');
    const baseKey = `jaynaApp.base${entityType}.${sectionName}.fields.${field.name}`;
    if (te(baseKey)) return t(baseKey);
  }
  return field.label || field.name;
};

defineExpose({ getData, isValid });
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
