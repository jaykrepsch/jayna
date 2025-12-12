<template>
  <WidgetCard :title="$t('home.calendar.title')" :more-label="$t('home.common.more')" @more="$emit('more')" @contextmenu.prevent="openMenu">
    <table class="min-w-full divide-y divide-gray-200 table-fixed">
      <thead class="bg-purple-50">
        <tr>
          <th class="text-left text-sm font-medium text-gray-600 py-2">{{$t('home.calendar.col.title')}}</th>
          <th class="text-left text-sm font-medium text-gray-600 py-2">{{$t('home.calendar.col.date')}}</th>
          <th class="text-left text-sm font-medium text-gray-600 py-2">{{$t('home.calendar.col.location')}}</th>
        </tr>
      </thead>
      <tbody class="divide-y divide-gray-100">
        <tr v-for="(e, idx) in (items || []).slice(0,5)" :key="idx" class="h-10">
          <td class="py-2 text-sm truncate">{{ e.title }}</td>
          <td class="py-2 text-sm">{{ e.date }}</td>
          <td class="py-2 text-sm">{{ e.location }}</td>
        </tr>
        <tr v-if="!items || !items.length" class="h-10">
          <td colspan="3" class="p-0"><WidgetEmptyState /></td>
        </tr>
        <tr v-else v-for="i in Math.max(0, 5 - Math.min(5, items.length))" :key="'pad-'+i" class="h-10">
          <td colspan="3"></td>
        </tr>
      </tbody>
    </table>
    <template #actions>
      <div class="flex items-center gap-2">
        <button class="p-1 text-primary-700 hover:text-primary-800" @click="$emit('addEvent')" aria-label="Neu">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" class="w-5 h-5">
            <circle cx="12" cy="12" r="10"/>
            <path d="M12 8v8M8 12h8"/>
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
import WidgetEmptyState from './WidgetEmptyState.vue';
import ContextMenu from '@/components/ui/ContextMenu.vue';
import { reactive } from 'vue';

defineProps({
  title: { type: String, default: 'Kalender' },
  moreLabel: { type: String, default: 'Mehr anzeigen' },
  items: { type: Array, default: () => [] }, // [{ title, date, location }]
});

defineEmits(['more', 'addEvent']);

const menu = reactive({ visible: false, x: 0, y: 0, items: [
  { label: 'Öffnen', key: 'open' },
  { label: 'Neu', key: 'add' },
  { label: 'Ausblenden', key: 'hide' },
]});
const openMenu = (e) => { menu.visible = true; menu.x = e.clientX; menu.y = e.clientY; };
const onSelect = (item) => { menu.visible = false; };
</script>


