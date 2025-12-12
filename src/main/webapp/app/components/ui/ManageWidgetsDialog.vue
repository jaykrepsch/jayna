<template>
  <!-- Modal in der Mitte mit Overlay -->
  <div class="fixed inset-0 z-40" @click="$emit('close')">
    <div class="absolute inset-0 bg-black/30"></div>
  </div>
  <div class="fixed z-50 top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 bg-white rounded-lg shadow-xl w-[720px] max-w-[90vw]">
    <div class="px-4 py-3 border-b flex items-center justify-between">
      <h3 class="text-sm font-semibold">Widgets verwalten</h3>
      <button class="text-gray-500" @click="$emit('close')">✕</button>
    </div>
    <div class="max-h-[70vh] overflow-auto p-4">
      <div class="grid grid-cols-2 md:grid-cols-3 gap-3">
        <div v-for="w in allWidgets" :key="w.key" class="flex items-center justify-between rounded border px-3 py-2">
          <div class="text-sm">{{ w.label }}</div>
          <label class="relative inline-flex items-center cursor-pointer">
            <input type="checkbox" class="sr-only peer" :checked="visibleMap[w.key]" @change="toggle(w.key)" />
            <div class="w-11 h-6 bg-gray-200 peer-focus:outline-none rounded-full peer peer-checked:bg-purple-600 transition"></div>
            <div class="absolute left-0.5 top-0.5 bg-white w-5 h-5 rounded-full transition peer-checked:translate-x-5"></div>
          </label>
        </div>
      </div>
    </div>
    <div class="px-4 py-3 border-t flex items-center justify-between">
      <button class="text-sm text-gray-600 hover:underline" @click="showAll">Alle einblenden</button>
      <div class="space-x-2">
        <button class="text-sm text-gray-600 hover:underline" @click="resetOrder">Zurücksetzen</button>
        <button class="text-sm text-purple-700 hover:underline" @click="$emit('close')">Schließen</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits, reactive } from 'vue';

const props = defineProps({
  allWidgets: { type: Array, default: () => [] }, // [{ key, label }]
  visibleMap: { type: Object, default: () => ({}) },
});

const emit = defineEmits(['update:visibleMap', 'resetOrder', 'close']);

const toggle = (key) => {
  const clone = { ...props.visibleMap, [key]: !props.visibleMap[key] };
  emit('update:visibleMap', clone);
};

const showAll = () => {
  const clone = { ...props.visibleMap };
  Object.keys(clone).forEach(k => clone[k] = true);
  emit('update:visibleMap', clone);
};

const resetOrder = () => emit('resetOrder');
</script>


