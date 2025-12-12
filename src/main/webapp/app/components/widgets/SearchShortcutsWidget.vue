<template>
  <WidgetCard :title="$t('home.searchShortcuts.title')" :info="$t('home.searchShortcuts.info')" :more-label="$t('home.common.more')" @more="$emit('more')" @contextmenu.prevent="openMenu">
    <ul class="text-sm space-y-2">
      <li v-if="items && items.length" v-for="(s, idx) in items" :key="idx" class="flex items-center justify-between">
        <span class="truncate">{{ s.label }}</span>
        <button class="text-primary-700 hover:underline" @click="$emit('open', s)">{{$t('home.searchShortcuts.open')}}</button>
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
import { defineProps, defineEmits, reactive } from 'vue';

defineProps({
  items: { type: Array, default: () => [] }, // [{ label, queryKey }]
});

defineEmits(['open', 'more']);

const menu = reactive({ visible: false, x: 0, y: 0, items: [
  { label: 'Ausblenden', key: 'hide' },
]});
const openMenu = (e) => { menu.visible = true; menu.x = e.clientX; menu.y = e.clientY; };
const onSelect = (item) => { menu.visible = false; };
</script>


