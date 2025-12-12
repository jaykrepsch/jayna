<template>
  <button
    :class="[buttonSize, 
             buttonType, 
             'flex justify-center items-center rounded-md border border-transparent font-medium shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2 uppercase',
             disabled ? 'opacity-50 cursor-not-allowed' : 'cursor-pointer',
             props.class
             ]"
    :disabled="disabled"
  >
    <slot>{{ text }}</slot>
  </button>
</template>

<script setup>

import { computed } from 'vue';

const props = defineProps({
  text: {
    type: String,
    required: true
  }, 
  size: {
    type: String,
    default: 'md'
  },
  type: {
    type: String,
    default: 'primary'
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

const buttonType = computed(() => { 
  switch (props.type) {
    case 'primary':
      return 'bg-indigo-600 text-white hover:bg-indigo-700';
    case 'secondary':
      return 'bg-indigo-100 text-indigo-700 hover:bg-indigo-200';
    case 'success':
      return 'bg-green-600 text-white hover:bg-green-700';
    case 'dark-blue':
      return 'bg-blue-800 text-white hover:bg-blue-900';
    default:
      return 'bg-indigo-600 text-white hover:bg-indigo-700';
  }
});

const buttonSize = computed(() => { 
  // Spezielle Größe für Häkchen-Button
  if (props.text === '✓') {
    return 'px-3 py-2 text-lg font-bold';
  }
  
  switch (props.size) {
    case 'xs':
      return 'px-2.5 py-1.5 text-xs';
    case 'sm':
      return 'px-3 py-2 text-sm';
    case 'md':
      return 'px-4 py-2 text-sm';
    case 'lg':
      return 'px-4 py-2 text-base';
    case 'xl':
      return 'px-6 py-3 text-base';
    default:
      return 'px-4 py-2 text-sm';
  }
});

</script>