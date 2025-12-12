<template>
  <div
    class="relative flex flex-col rounded-lg shadow max-w-lg py-6 px-12 bg-white"
  >
    <div 
      v-if="editable"
      class="absolute right-0 top-0 hidden pr-4 pt-4 sm:block"
    >
      <button 
        type="button"
        class="rounded-md bg-white text-gray-400 hover:text-gray-500"
        @click.prevent="$emit('delete')"
      >
        <span class="sr-only">Close</span>
        <XMarkIcon class="h-6 w-6" aria-hidden="true" />
      </button>
    </div>
    <span>{{ $t(item?.groupType?.translationKey) }}</span>
    <Divider />
    <span>{{ item?.label }}</span>
    <span class="text-gray-400 text-xs mt-3">
      {{ $t('entity.detail.createdDate') + ' ' + $d(new Date(item?.createdDate), 'long', ($store && $store.getters ? $store.getters['translation/currentLanguage'] : 'de')) }}
    </span>

    <Button
      v-if="!editable"
      class="mt-5"
      :text="$t('button.show')"
      @click.prevent="
        $router.push({
          name: 'CategoryView',
          params: {
            id: item?.groupType.subCategoryGroup.subCategory.category.id, 
            subid: item?.groupType.subCategoryGroup.subCategory.id, 
            subgrpid: item?.groupType.subCategoryGroup.id, 
            grptpid: item?.groupType.id, 
            entityid: item?.id
          }
        })"
    />
  </div>
</template>

<script setup>

import Divider from '@/components/divider.vue';
import { computed } from 'vue';
import { XMarkIcon } from '@heroicons/vue/24/outline';
import Button from '@/components/buttons/button.vue';

const props = defineProps({
  editable: {
    type: Boolean,
    default: false
  },
  relationship: Object,
  entityName: String
});

const item = computed(() => props.relationship[props.entityName]);

defineEmits(['delete']);

</script>