<template>
  <div>
    <label 
      :for="name"
      :class="[required ? 'required' : '', 'block text-sm font-medium text-gray-700']"
    >
      {{ label }}
    </label>
    <div class="mt-1">
      <textarea
        rows="4"
        name="comment"
        :class="[
          'block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm',
          validationClass
        ]"
        :value="modelValue"
        :placeholder="placeholder"
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
import { computed } from 'vue';
import { ExclamationCircleIcon } from '@heroicons/vue/20/solid'

const props = defineProps({
  name: {
    type: String,
    default: null
  },
  label: {
    type: String,
    default: null
  },
  modelValue: {
    type: [String, Number],
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
  required: {
    type: Boolean,
    default: false
  },
  disabled: {
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
/* Standard Textarea Styles */
textarea {
  padding: 0.5rem 0.75rem;
  border: 1px solid #d1d5db;
  background-color: #f9fafb; /* 50% helleres Grau (bg-gray-50) */
  color: #111827;
  min-height: 2.5rem;
}

/* Disabled Textarea Styles */
textarea:disabled {
  background-color: #f3f4f6 !important; /* bg-gray-100 - dunkleres Grau für Detail-Ansicht */
  color: #111827 !important;
  border-color: #d1d5db !important;
  cursor: not-allowed !important;
  opacity: 1 !important;
  pointer-events: none;
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