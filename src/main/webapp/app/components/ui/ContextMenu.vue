<template>
  <div class="fixed inset-0 z-40" @click="$emit('close')" @contextmenu.prevent></div>
  <div
    class="fixed z-50 bg-white border border-gray-200 rounded-md shadow-lg py-1 w-48"
    :style="{ top: y + 'px', left: x + 'px' }"
    role="menu"
  >
    <button
      v-for="(item, idx) in items"
      :key="idx"
      class="w-full text-left px-3 py-2 text-sm hover:bg-gray-100"
      @click="$emit('select', item)"
    >
      {{ item.label }}
    </button>
  </div>
</template>

<script setup>
import { defineProps } from 'vue';

defineProps({
  items: { type: Array, default: () => [] }, // [{ label, key }]
  x: { type: Number, default: 0 },
  y: { type: Number, default: 0 },
});
</script>


