<template>
  <WidgetCard :title="$t('home.favorites.title')" :info="$t('home.favorites.info')" :more-label="$t('home.common.more')" @more="$emit('more')">
    <ul class="text-sm space-y-2">
      <li v-if="items && items.length" v-for="(fav, idx) in items" :key="idx" class="flex items-center justify-between" @contextmenu.prevent="onRowContextmenu($event, idx)">
        <span class="truncate">{{ fav.label }}</span>
        <button class="text-primary-700 hover:underline" @click="$emit('open', fav)">Öffnen</button>
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
  title: { type: String, default: 'Favoriten' },
  info: { type: String, default: 'Schnellzugriffe auf häufig genutzte Einträge.' },
  moreLabel: { type: String, default: 'Mehr anzeigen' },
  items: { type: Array, default: () => [] }, // [{ label, route/name/id }]
});

const emit = defineEmits(['open', 'remove', 'rename']);

const menu = reactive({ visible: false, x: 0, y: 0, rowIndex: -1, items: [
  { label: 'Favorit entfernen', key: 'remove' },
  { label: 'Umbenennen', key: 'rename' },
  { label: 'Ausblenden', key: 'hide' },
]});
const onRowContextmenu = (e, idx) => { menu.visible = true; menu.x = e.clientX; menu.y = e.clientY; menu.rowIndex = idx; };
const onSelect = (item) => { menu.visible = false; if (item.key === 'remove') emit('remove', menu.rowIndex); if (item.key === 'rename') emit('rename', menu.rowIndex); };
</script>


