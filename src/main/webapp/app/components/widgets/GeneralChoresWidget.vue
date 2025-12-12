<template>
  <WidgetCard :title="$t('home.chores.title')" :info="$t('home.chores.info')" :more-label="$t('home.common.more')" @more="$emit('more')" @contextmenu.prevent="openMenu">
    <ul class="text-sm space-y-2">
      <li v-if="items && items.length" v-for="(t, idx) in items" :key="idx" class="flex items-center justify-between">
        <div class="truncate">
          <span class="font-medium">{{ t.title }}</span>
          <span class="ml-2 text-gray-500">{{ t.date }}</span>
        </div>
        <span :class="t.urgent ? 'text-red-600' : 'text-gray-500'">{{ t.category }}</span>
      </li>
      <li v-else>
        <WidgetEmptyState />
      </li>
    </ul>
  </WidgetCard>
  <ContextMenu v-if="menu.visible" :items="menu.items" :x="menu.x" :y="menu.y" @select="onSelect" @close="menu.visible=false" />
</template>

<script setup>
import WidgetCard from './WidgetCard.vue';
import WidgetEmptyState from './WidgetEmptyState.vue';
import ContextMenu from '@/components/ui/ContextMenu.vue';
import { defineProps, reactive } from 'vue';

defineProps({
  items: { type: Array, default: () => [] }, // [{ title, date, category, urgent }]
});

const menu = reactive({ visible: false, x: 0, y: 0, items: [
  { label: 'Ausblenden', key: 'hide' },
]});
const openMenu = (e) => { menu.visible = true; menu.x = e.clientX; menu.y = e.clientY; };
const onSelect = (item) => { menu.visible = false; };
</script>



