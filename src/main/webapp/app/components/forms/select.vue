<template>
  <Listbox as="div" v-model="selected">
    <!-- <ListboxLabel
      class="block text-sm font-medium leading-6 text-gray-900"
    >
      Label
    </ListboxLabel> -->
    <div class="relative mt-2">
      <ListboxButton
        class="relative w-full cursor-pointer rounded-md bg-white py-1.5 pl-3 pr-10 text-left text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 focus:outline-none focus:ring-2 focus:ring-indigo-600 sm:text-sm sm:leading-6"
      >
        <span class="block truncate">
          <span v-if="selected">
            <span>
              {{ selected.groupType?.translationKey ? $t(selected.groupType.translationKey) + ', ' + selected[displayField] : selected[displayField] }}
            </span>
          </span>
          <span v-else>
            {{ $t('global.menu.choose') }}
          </span>
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
            v-for="item in data"
            :key="item.id"
            :value="item"
            v-slot="{ active, selected }"
          >
            <li
              :class="[active ? 'bg-indigo-600 text-white' : 'text-gray-900', 'text-left relative cursor-pointer select-none py-2 pl-3 pr-9']"
            >
              <span 
                :class="[selected ? 'font-semibold' : 'font-normal', 'block truncate']"
              >
                <div>
                  <span>
                    {{ item.groupType?.translationKey ? $t(item.groupType.translationKey) + ', ' + item[displayField] : item[displayField] }}
                  </span>
                </div>
              </span>

              <span v-if="selected"
                :class="[active ? 'text-white' : 'text-indigo-600', 'absolute inset-y-0 right-0 flex items-center pr-4']">
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
import { ref } from 'vue'
import { Listbox, ListboxButton, ListboxLabel, ListboxOption, ListboxOptions } from '@headlessui/vue'
import { CheckIcon, ChevronUpDownIcon } from '@heroicons/vue/20/solid'

defineProps({
  data: Array,
  displayField: String
});

const selected = ref(null)

const clear = () => { 
  selected.value = null;
};

defineExpose({ clear, selected });

</script>

<style scoped>
/* Standard Select Button Styles */
:deep(.relative.w-full.cursor-pointer.rounded-md.bg-white.py-1.5.pl-3.pr-10.text-left.text-gray-900.shadow-sm.ring-1.ring-inset.ring-gray-300.focus\\:outline-none.focus\\:ring-2.focus\\:ring-indigo-600.sm\\:text-sm.sm\\:leading-6) {
  background-color: #f9fafb !important; /* 50% helleres Grau (bg-gray-50) */
  color: #000000 !important; /* schwarze Schrift */
  border-color: #d1d5db !important; /* border-gray-300 */
  min-height: 2.5rem !important;
  padding: 0.5rem 0.75rem !important;
  border: 1px solid #d1d5db !important;
}
</style>