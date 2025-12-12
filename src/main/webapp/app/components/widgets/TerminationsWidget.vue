<template>
  <WidgetCard :title="$t('home.terminations.title')" :more-label="$t('home.common.more')" @more="$emit('more')" @contextmenu.prevent="openMenu">
    <table class="min-w-full divide-y divide-gray-200">
      <thead class="bg-purple-50">
        <tr>
          <th class="text-left text-sm font-medium text-gray-600 py-2">{{$t('home.terminations.col.contract')}}</th>
          <th class="text-left text-sm font-medium text-gray-600 py-2">{{$t('home.terminations.col.notice')}}</th>
          <th class="text-left text-sm font-medium text-gray-600 py-2">{{$t('home.terminations.col.date')}}</th>
        </tr>
      </thead>
      <tbody class="divide-y divide-gray-100">
        <tr v-if="items && items.length" v-for="(it, idx) in items" :key="idx">
          <td class="py-2 text-sm">{{ it.contract }}</td>
          <td class="py-2 text-sm">{{ it.notice }}</td>
          <td class="py-2 text-sm">{{ it.date }}</td>
        </tr>
        <tr v-else>
          <td colspan="3" class="p-0"><WidgetEmptyState /></td>
        </tr>
      </tbody>
    </table>
  </WidgetCard>
  <ContextMenu v-if="menu.visible" :items="menu.items" :x="menu.x" :y="menu.y" @select="onSelect" @close="menu.visible=false" />
</template>

<script setup>
import WidgetCard from './WidgetCard.vue';
import WidgetEmptyState from './WidgetEmptyState.vue';
import ContextMenu from '@/components/ui/ContextMenu.vue';
import { defineProps, defineEmits, reactive } from 'vue';

defineProps({
  title: { type: String, default: 'Kündigungstermine' },
  moreLabel: { type: String, default: 'Mehr anzeigen' },
  items: { type: Array, default: () => [] }, // [{ contract, notice, date }]
});

defineEmits(['more']);

const menu = reactive({ visible: false, x: 0, y: 0, items: [
  { label: 'Erinnerung setzen', key: 'remind' },
  { label: 'Ausblenden', key: 'hide' },
]});
const openMenu = (e) => { menu.visible = true; menu.x = e.clientX; menu.y = e.clientY; };
const onSelect = (item) => { menu.visible = false; };
</script>


