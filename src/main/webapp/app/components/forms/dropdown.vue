<template>
  <Listbox
    as="div"
    v-model="selectedItem"
    :disabled="disabled"
  >
    <ListboxLabel :class="[required ? 'required' : '', 'block text-sm font-medium text-gray-700']">
      {{ label }}
    </ListboxLabel>
    <div class="relative mt-1">
      <ListboxButton
        :class="[
          'relative w-full rounded-md border border-gray-300 bg-white py-2 pl-3 pr-10 text-left shadow-sm focus:border-indigo-500 focus:outline-none focus:ring-1 focus:ring-indigo-500 sm:text-sm disabled:opacity-50',
          disabled ? 'cursor-not-allowed' : 'cursor-pointer',
          validationClass
        ]"
      >
        <span class="block truncate text-gray-900">
          {{ selectedLabel }}
        </span>
        <span class="pointer-events-none absolute inset-y-0 right-0 flex items-center pr-2">
          <ChevronUpDownIcon class="h-5 w-5 text-gray-400" aria-hidden="true" />
        </span>
      </ListboxButton>

      <transition
        leave-active-class="transition ease-in duration-100"
        leave-from-class="opacity-100"
        leave-to-class="opacity-0"
      >
        <ListboxOptions
          class="absolute z-10 mt-1 max-h-96 w-full overflow-auto rounded-md bg-white py-1 text-base shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none sm:text-sm"
        >
          <ListboxOption
            as="template"
            v-for="(item, index) of items"
            :key="index"
            :value="item"
            v-slot="{ active, selected }"
          >
            <li
              :class="[
                active ? 'bg-indigo-600 text-white' : 'text-gray-900',
                'relative cursor-pointer select-none py-2 pl-3 pr-9'
              ]"
            >
              <span
                :class="[
                  selected ? 'font-semibold' : 'font-normal',
                  'block truncate'
                ]"
              >
                {{ getItemLabel(item) }}
              </span>

              <span
                v-if="selected"
                :class="[
                  active ? 'text-white' : 'text-indigo-600',
                  'absolute inset-y-0 right-0 flex items-center pr-4'
                ]"
              >
                <CheckIcon class="h-5 w-5" aria-hidden="true" />
              </span>
            </li>
          </ListboxOption>
        </ListboxOptions>
      </transition>
    </div>
  </Listbox>
</template>

<script setup>
import { ref, computed, watch } from 'vue';
import { useI18n } from 'vue-i18n';
import { Listbox, ListboxButton, ListboxLabel, ListboxOption, ListboxOptions } from '@headlessui/vue';
import { CheckIcon, ChevronUpDownIcon } from '@heroicons/vue/20/solid';

const { t } = useI18n();
const emits = defineEmits(['update:modelValue']);
const props = defineProps({
  modelValue: Object,
  label: String,
  boxLabel: String,
  items: Array,
  translationKey: String,
  displayField: String,
  disabled: Boolean,
  required: {
    type: Boolean,
    default: false
  },
  class: {
    type: String,
    default: ''
  }
});

const selectedItem = ref(props.modelValue || null);

const selectedLabel = computed(() => {
  if (selectedItem.value) {
    return getItemLabel(selectedItem.value);
  }
  return props.boxLabel || t('global.menu.choose');
});

const getItemLabel = (item) => {
  if (!item) return '';
  
  if (props.displayField) {
    return item[props.displayField];
  } else if (item.translationKey) {
    return t(item.translationKey);
  } else if (props.translationKey) {
    return t(props.translationKey);
  } else {
    return item.name || '';
  }
};

// Computed property für Validierungsklasse
const validationClass = computed(() => {
  return props.class || '';
});

// Watch for changes in selectedItem and emit updates
watch(selectedItem, (newValue) => {
  emits('update:modelValue', newValue);
});

// Watch for changes in props.modelValue
watch(() => props.modelValue, (newValue) => {
  selectedItem.value = newValue;
});
</script>

<style scoped>
/* Anzeigefeld Höhe anpassen */
.dropdown-display {
  min-height: 2.5rem !important;
  padding: 0.5rem 0.75rem !important;
  display: flex !important;
  align-items: center !important;
  background-color: #f9fafb !important; /* 50% helleres Grau (bg-gray-50) */
  color: #000000 !important; /* schwarze Schrift */
  border-color: #d1d5db !important; /* border-gray-300 */
  cursor: default !important;
  border: 1px solid #d1d5db !important;
}

/* Container Höhe anpassen */
.relative.mt-1 {
  min-height: 2.5rem !important;
}

/* Schriftfarbe für den Text im Dropdown */
.dropdown-display span {
  color: #000000 !important; /* schwarze Schrift */
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