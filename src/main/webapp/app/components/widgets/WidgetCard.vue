<template>
  <div class="bg-white rounded-lg shadow h-[400px] min-h-[400px] flex flex-col">
    <div class="px-4 py-2 border-b flex items-center justify-between bg-purple-600">
      <div class="flex items-center gap-2">
        <h2 class="text-sm font-semibold text-white">{{ title }}</h2>
        <span v-if="info" class="text-purple-100" :title="info">ⓘ</span>
      </div>
      <div class="flex items-center gap-4 text-white">
        <slot name="actions"></slot>
        <button class="p-1 hover:text-purple-200" @click="$emit('more')" :aria-label="moreLabel">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" class="w-5 h-5">
            <circle cx="12" cy="12" r="10" stroke="currentColor" fill="none"/>
            <path d="M10 8l4 4-4 4" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </button>
      </div>
    </div>
    <div class="flex-1 overflow-hidden">
      <div class="h-full overflow-auto px-4 py-3">
        <slot />
      </div>
    </div>
  </div>
  
</template>

<script setup>
import { defineProps } from 'vue';

const props = defineProps({
  title: { type: String, required: true },
  moreLabel: { type: String, default: 'Mehr anzeigen' },
  info: { type: String, default: '' },
});
</script>


