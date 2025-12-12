<template>
  <button 
    type="button"
    class="relative block w-full rounded-lg border-2 border-dashed border-gray-300 hover:border-gray-400 p-12 text-center"
    @click="$emit('click')"
    @mouseenter="textCls = 'text-gray-400'"
    @mouseleave="textCls = 'text-gray-300'"
  >
    <svg 
      :class="[textCls, 'mx-auto h-12 w-12']"
      xmlns="http://www.w3.org/2000/svg"
      fill="none"
      viewBox="0 0 24 24"
      stroke-width="1.5"
      stroke="currentColor"
    >
      <path stroke-linecap="round" stroke-linejoin="round" d="M12 9v6m3-3H9m12 0a9 9 0 11-18 0 9 9 0 0118 0z" />
    </svg>
    <span class="mt-2 block text-sm text-gray-600">{{ $t(label) }}</span>
  </button>
</template>

<script setup>

import { ref } from 'vue';

const textCls = ref('text-gray-300');

defineProps({
  label: String
});

defineEmits(['click'])

</script>