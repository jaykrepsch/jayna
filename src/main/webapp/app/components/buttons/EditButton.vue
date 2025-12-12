<template>
  <button
    class="cursor-pointer inline-flex items-center justify-center rounded-md px-2 py-2 text-sm font-medium text-black hover:text-gray-700 focus:outline-none focus:ring-2 focus:ring-gray-500 focus:ring-offset-2 sm:w-auto"
    @click.prevent="handleClick"
  >
    <PencilIcon class="h-4 w-4" aria-hidden="true" />
  </button>
</template>

<script setup>
import { PencilIcon } from '@heroicons/vue/24/outline';

const emit = defineEmits(['click']);

const handleClick = () => {
  console.log('EditButton clicked!');
  emit('click');
};

</script>