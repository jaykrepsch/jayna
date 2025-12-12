<template>
  <div class="flex flex-col overflow-y-auto border-l border-gray-200 bg-white pt-5 h-full w-full">
    <!-- Sidebar Header -->
    <div class="flex items-center justify-between px-4 pb-4 border-b border-gray-200">
      <h3 class="text-lg font-semibold text-gray-900">
        <span v-if="open">Vorschau</span>
        <span v-else class="sr-only">Vorschau</span>
      </h3>
    </div>

    <!-- Sidebar Content -->
    <div class="mt-5 flex flex-grow flex-col">
      <div class="flex flex-col space-y-1 px-2 pb-4">
        <slot></slot>
      </div>
    </div>

    <!-- Toggle Button (unten, wie bei der linken Sidebar) -->
    <div class="flex justify-end w-full p-4 text-gray-600">
      <ChevronDoubleRightIcon
        v-if="open"
        class="h-8 w-8 rounded-full p-2 hover:bg-gray-200 hover:text-gray-600 cursor-pointer"
        @click="toggleSidebar()"
      />
      <ChevronDoubleLeftIcon
        v-if="!open"
        class="h-8 w-8 rounded-full p-2 hover:bg-gray-200 hover:text-gray-600 cursor-pointer"
        @click="toggleSidebar()"
      />
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { ChevronDoubleLeftIcon, ChevronDoubleRightIcon } from '@heroicons/vue/24/outline';

const props = defineProps({
  initialOpen: {
    type: Boolean,
    default: true
  }
});

const open = ref(props.initialOpen);

const emit = defineEmits(['toggle']);

const toggleSidebar = () => { 
  open.value = !open.value;
  console.log('RightSidebar component - toggleSidebar called, open:', open.value);
  emit('toggle');
};

// Expose methods for parent components
defineExpose({
  toggleSidebar,
  open
});
</script>

<style scoped>
/* Custom scrollbar */
.overflow-y-auto::-webkit-scrollbar {
  width: 8px;
}

.overflow-y-auto::-webkit-scrollbar-track {
  background: #f1f1f1;
}

.overflow-y-auto::-webkit-scrollbar-thumb {
  background: #c1c1c1;
  border-radius: 4px;
}

.overflow-y-auto::-webkit-scrollbar-thumb:hover {
  background: #a8a8a8;
}
</style>
