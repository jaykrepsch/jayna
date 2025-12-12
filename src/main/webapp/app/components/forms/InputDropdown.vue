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
      <div class="relative mt-1 rounded-md shadow-sm">
        <input
          :class="[errors.length ? errorClass : '', 'block w-full rounded-md pr-10 sm:text-sm disabled:opacity-50']"
          aria-invalid="true"
          :placeholder="placeholder"
          type="text"
          :value="inputValue"
          :name="name"
          :disabled="disabled"
          @input="inputValue = $event.target.value; $emit('update:modelValue', $event.target.value)"
        />
        <div 
          v-show="inputValue?.length"
          :class="[items.length ? 'right-6' : 'right-0', 'absolute inset-y-0 flex items-center pr-2 cursor-pointer']"
        >
          <!-- <PlusIcon
            class="h-5 w-5 text-gray-400 rounded-full hover:bg-indigo-500 hover:text-white"
            aria-hidden="true"
            @click="addSubCategoryGroup() "
          /> -->
          <XMarkIcon
            class="h-5 w-5 text-gray-400 rounded-full hover:bg-indigo-500 hover:text-white"
            aria-hidden="true"
            @click="clearInput()"
          />
        </div>
        <div
          v-show="items && items.length > 0"
          class="absolute inset-y-0 right-0 flex items-center pr-2 cursor-pointer"
          @click.prevent="open = !open"
        >
          <ChevronUpDownIcon
            class="h-5 w-5 text-gray-400"
            aria-hidden="true"
          />
        </div>
        <div
          v-show="errors.length"
          class="pointer-events-none absolute inset-y-0 right-0 flex items-center pr-3"
        >
          <ExclamationCircleIcon class="h-5 w-5 text-red-500" aria-hidden="true" />
        </div>
      </div>

      <div v-show="open && items && items.length > 0">
        <transition
          leave-active-class="transition ease-in duration-100"
          leave-from-class="opacity-100"
          leave-to-class="opacity-0"
        >
          <ListboxOptions
            class="absolute z-10 mt-1 max-h-96 w-full overflow-auto rounded-md bg-white py-1 text-base shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none sm:text-sm"
            static
          >
            <ListboxOption
              as="template"
              v-for="(item, index) of items"
              :key="index"
              :value="item"
              @click="clickOption(item)"
              v-slot="{ selected }"
            >
              <li
                class="ui-active:text-white ui-selected:text-white ui-active:bg-indigo-600 ui-selected:bg-indigo-600 text-gray-900 relative cursor-pointer select-none py-2 pl-8 pr-4"
              >
                <span class="ui-active:font-semibold font-normal block truncate">
                  {{ getListLabel(item) }}
                </span>

                <span
                  v-if="selected"
                  :class="['absolute inset-y-0 left-0 flex items-center pl-1.5']"
                >
                  <CheckIcon class="h-5 w-5" aria-hidden="true" />
                </span>

                <span
                  v-if="selected"
                  :class="['absolute inset-y-0 right-2 flex items-center pl-1.5']"
                  @click.prevent="selectedItem = null"
                >
                  <XMarkIcon class="h-5 w-5" aria-hidden="true" />
                </span>
              </li>
            </ListboxOption>
          </ListboxOptions>
        </transition>
      </div>
    </div>
  </Listbox>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue';
import { ExclamationCircleIcon, CheckIcon, ChevronUpDownIcon, XMarkIcon, PlusIcon } from '@heroicons/vue/20/solid'
import { Listbox, ListboxButton, ListboxLabel, ListboxOption, ListboxOptions } from '@headlessui/vue'
import { useI18n } from 'vue-i18n';

const props = defineProps({
  name: {
    type: String,
    default: null
  },
  modelValue: {
    type: [String, Object],
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
  items: {
    default: () => ([]),
    type: Array
  },
  autoOpen: {
    type: Boolean,
    default: false
  },
  displayField: {
    type: String,
    default: null
  },
  translationKey: {
    type: String,
    default: null
  },
  boxLabel: {
    type: String,
    default: null
  }
});

const errorClass = ref('border-red-300 text-red-900 placeholder-red-300 focus:border-red-500 focus:outline-none focus:ring-red-500');
const { t } = useI18n();
const open = ref(false);
const inputValue = ref(null);

// Auto-open dropdown if autoOpen prop is true
watch(() => props.autoOpen, (newValue) => {
  if (newValue && props.items && props.items.length > 0) {
    open.value = true;
  }
}, { immediate: true });

// Also watch for items changes to open dropdown when items are loaded
watch(() => props.items, (newItems) => {
  if (props.autoOpen && newItems && newItems.length > 0) {
    open.value = true;
  }
}, { immediate: true });

// Initial open for autoOpen dropdowns
onMounted(() => {
  if (props.autoOpen && props.items && props.items.length > 0) {
    open.value = true;
  }
});

const emits = defineEmits(['update:modelValue', 'add', 'delete']);

// Vorab-Deklaration als Function (hoisted), damit Watcher sie sofort nutzen kann
function getListLabel(item) {
  if (!item) return '';
  if (props.displayField) {
    return item[props.displayField];
  } else if (item.translationKey) {
    const translation = t(item.translationKey);
    if (translation !== item.translationKey) {
      return translation;
    }
    return removeAsterisks(item.name);
  } else if (props.translationKey) {
    return t(props.translationKey);
  } else if (item.name) {
    const possibleKeys = [
      `jaynaApp.contract.sub-category.${item.name}`,
      `jaynaApp.contract.subCategory.${item.name}`,
      `jaynaApp.sub-category.${item.name}`,
      `jaynaApp.subCategory.${item.name}`,
      `jaynaApp.contract.sub-category.${item.name.toLowerCase()}`,
      `jaynaApp.contract.subCategory.${item.name.toLowerCase()}`,
      `jaynaApp.contract.sub-category.${item.name.replace(/-/g, '')}`,
      `jaynaApp.contract.subCategory.${item.name.replace(/-/g, '')}`,
      `jaynaApp.contract.sub-category.${item.name.replace(/_/g, '-')}`,
      `jaynaApp.contract.subCategory.${item.name.replace(/_/g, '-')}`,
      item.name,
      item.name.toLowerCase(),
      item.name.replace(/-/g, ''),
      item.name.replace(/_/g, '-')
    ];
    for (const key of possibleKeys) {
      const translation = t(key);
      if (translation !== key) {
        return translation;
      }
    }
    const directTranslation = t(item.name);
    if (directTranslation !== item.name) {
      return directTranslation;
    }
    return removeAsterisks(item.name);
  } else if (item.id) {
    const translation = t(item.id);
    if (translation !== item.id) {
      return translation;
    }
    return item.id;
  } else {
    return '';
  }
}

const selectedItem = ref(props.modelValue || null);

// Watch for modelValue changes to update selectedItem
watch(() => props.modelValue, (newValue) => {
  selectedItem.value = newValue;
  if (newValue) {
    const label = getListLabel(newValue);
    inputValue.value = label;
  } else {
    inputValue.value = null;
  }
}, { immediate: true });

const selectedLabel = computed(() => {
  if (selectedItem.value) {
    if (props.displayField) {
      return selectedItem.value[props.displayField];
    } else if (selectedItem.value.translationKey) {
      return t(selectedItem.value.translationKey);
    } else if (props.translationKey) {
      return t(props.translationKey);
    } else if (selectedItem.value.name) {
      return removeAsterisks(selectedItem.value.name);
    } else if (selectedItem.value.id) {
      // Spezielle Behandlung für Dokumenten-Select: Versuche es mit der ID als Übersetzungsschlüssel
      const translation = t(selectedItem.value.id);
      if (translation !== selectedItem.value.id) {
        return translation;
      }
      return selectedItem.value.id;
    }
  }
  return props.boxLabel ? t(props.boxLabel) : t('global.menu.choose');
});

// getListLabel ist oben als Function deklariert

const removeAsterisks = (name) => {
  if (!name) return '';
  
  return name.replace(/\*.*?\*/g, '').trim();
};

const clickOption = (item) => {
  inputValue.value = item.name;
  selectedItem.value = item;
  emits('update:modelValue', item);
  // Keep dropdown open if autoOpen is enabled
  if (props.autoOpen) {
    open.value = true;
  }
};

const clearInput = () => {
  inputValue.value = null;
  selectedItem.value = null;
  emits('update:modelValue', null);
  emits('delete');
};

const addSubCategoryGroup = () => {
  emits('add');
};

</script>