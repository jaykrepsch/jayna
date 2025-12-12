<template>
  <div>
    <label
      :for="name"
      :class="[required ? 'required' : '', 'block text-sm font-medium text-gray-700']"
    >
      {{ label }}
    </label>
    <div class="relative mt-1 rounded-md shadow-sm">
      <input
        :class="[
          errors.length ? errorClass : '', 
          'block w-full rounded-md pr-10 sm:text-sm',
          type === 'date' ? 'date-input' : '',
          validationClass
        ]"
        aria-invalid="true"
        aria-describedby="email-error"
        :placeholder="placeholder"
        :type="type"
        :value="modelValue"
        :name="name"
        :disabled="disabled"
        @input="$emit('update:modelValue', $event.target.value)"
      />
      <div
        v-show="errors.length"
        class="pointer-events-none absolute inset-y-0 right-0 flex items-center pr-3"
      >
        <ExclamationCircleIcon class="h-5 w-5 text-red-500" aria-hidden="true" />
      </div>
    </div>
    <p
      v-show="errors.length"
      class="mt-2 text-sm text-red-600"
      id="email-error"
    >
      {{ errors[0]?.$message }}
    </p>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { ExclamationCircleIcon } from '@heroicons/vue/20/solid'

const errorClass = ref('border-red-300 text-red-900 placeholder-red-300 focus:border-red-500 focus:outline-none focus:ring-red-500');

const props = defineProps({
  name: {
    type: String,
    default: null
  },
  type: {
    type: String,
    default: 'text'
  },
  modelValue: {
    type: [String, Number],
    default: null
  },
  label: {
    type: String,
    default: null
  },
  placeholder: {
    type: String,
    default: null
  },
  errors: {
    default: () => ([]),
    type: Array
  },
  disabled: {
    type: Boolean,
    default: false
  },
  required: {
    type: Boolean,
    default: false
  },
  class: {
    type: String,
    default: ''
  }
});

const emits = defineEmits(['update:modelValue']);

// Computed property für Validierungsklasse
const validationClass = computed(() => {
  return props.class || '';
});

</script>

<style scoped>
/* Spezifische Styles für Datumsfelder */
.date-input {
  padding-left: 0.75rem !important;
  padding-right: 0.75rem !important;
  min-height: 2.5rem !important;
  background-color: #f9fafb !important; /* 50% helleres Grau (bg-gray-50) */
  color: #111827 !important; /* text-gray-900 */
  border-color: #d1d5db !important; /* border-gray-300 */
}

/* Standard Input Styles */
input {
  padding: 0.5rem 0.75rem;
  border: 1px solid #d1d5db;
  background-color: #f9fafb; /* 50% helleres Grau (bg-gray-50) */
  color: #111827;
  min-height: 2.5rem;
}

/* Disabled Input Styles - Text bleibt schwarz */
input:disabled {
  background-color: #f9fafb !important; /* 50% helleres Grau (bg-gray-50) */
  color: #111827 !important; /* text-gray-900 - schwarz */
  opacity: 1 !important; /* Keine Transparenz */
  cursor: not-allowed;
}

/* Required field indicator */
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
</style>