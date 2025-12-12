<template>
  <WidgetCard :title="title" :more-label="moreLabel" @more="$emit('more')" @contextmenu.prevent="openMenu">
    <div class="grid grid-cols-3 gap-3">
      <div v-for="(item, idx) in items" :key="idx" class="bg-gray-50 rounded p-3">
        <div class="text-xs text-gray-500">{{ item.label }}</div>
        <div class="text-xl font-semibold">{{ item.value }}</div>
      </div>
    </div>
    <template #actions>
      <div class="flex items-center gap-2">
        <button class="p-1 text-primary-700 hover:text-primary-800" @click="$emit('refresh')" aria-label="Aktualisieren">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" class="w-5 h-5">
            <circle cx="12" cy="12" r="10"/>
            <path d="M8 12a4 4 0 1 0 4-4M12 8V4M12 8l-2 2"/>
          </svg>
        </button>
      </div>
    </template>
  </WidgetCard>
  <ContextMenu v-if="menu.visible" :items="menu.items" :x="menu.x" :y="menu.y" @select="onSelect" @close="menu.visible=false" />
</template>

<script setup>
import { defineProps, defineEmits } from 'vue';
import WidgetCard from './WidgetCard.vue';
import ContextMenu from '@/components/ui/ContextMenu.vue';
import { reactive } from 'vue';

defineProps({
  title: { type: String, default: 'KPIs' },
  moreLabel: { type: String, default: 'Mehr anzeigen' },
  items: { type: Array, default: () => [] }, // [{ label, value }]
});

defineEmits(['more', 'refresh']);

const menu = reactive({ visible: false, x: 0, y: 0, items: [
  { label: 'Aktualisieren', key: 'refresh' },
  { label: 'Ausblenden', key: 'hide' },
]});
const openMenu = (e) => { menu.visible = true; menu.x = e.clientX; menu.y = e.clientY; };
const onSelect = (item) => {
  menu.visible = false;
  if (item.key === 'refresh') return; // handled by header action
};
</script>


