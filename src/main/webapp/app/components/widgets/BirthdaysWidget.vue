<template>
  <WidgetCard :title="$t('home.birthdays.title')" :more-label="$t('home.common.more')" @more="$emit('more')" @contextmenu.prevent="openMenu">
    <table class="min-w-full divide-y divide-gray-200 table-fixed">
      <thead class="bg-purple-50">
        <tr>
          <th class="text-left text-sm font-medium text-gray-600 py-2">{{$t('home.birthdays.col.name')}}</th>
          <th class="text-left text-sm font-medium text-gray-600 py-2">{{$t('home.birthdays.col.date')}}</th>
          <th class="text-left text-sm font-medium text-gray-600 py-2">{{$t('home.birthdays.col.age')}}</th>
        </tr>
      </thead>
      <tbody class="divide-y divide-gray-100">
        <tr v-for="(b, idx) in (items || []).slice(0,5)" :key="idx" :class="['h-10', { 'font-semibold': b?.isRound }]">
          <td class="py-2 text-sm truncate">{{ b?.name }}</td>
          <td class="py-2 text-sm">{{ formatBirthday(b?.date) }}</td>
          <td class="py-2 text-sm">{{ b?.age }}</td>
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
        <!-- kein spezieller Header-Action nötig; Platzhalter für zukünftige Funktionen -->
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

const props = defineProps({
  title: { type: String, default: 'Geburtstage' },
  moreLabel: { type: String, default: 'Mehr anzeigen' },
  items: { type: Array, default: () => [] }, // [{ name, date, age, isRound }]
});

defineEmits(['more', 'addReminder']);

const formatBirthday = (raw) => {
  // Erwartet z.B. '20.01.1954' oder '20.01.' / '20.01.54'
  if (!raw) return '';
  const parts = String(raw).split('.');
  // dd.MM.yy oder dd.MM.yyyy
  if (parts.length >= 3) {
    const year = parts[2];
    const yy = year.length === 4 ? year.slice(2) : year.padStart(2, '0').slice(-2);
    return `${parts[0].padStart(2, '0')}.${parts[1].padStart(2, '0')}.${yy}`;
  }
  // dd.MM.
  if (parts.length === 2 || (parts.length === 3 && parts[2] === '')) {
    return `${parts[0].padStart(2, '0')}.${parts[1].padStart(2, '0')}.`;
  }
  return raw;
}

const menu = reactive({ visible: false, x: 0, y: 0, items: [
  { label: 'Kontakt öffnen', key: 'open' },
  { label: 'Ausblenden', key: 'hide' },
]});
const openMenu = (e) => { menu.visible = true; menu.x = e.clientX; menu.y = e.clientY; };
const onSelect = (item) => { menu.visible = false; };
</script>


