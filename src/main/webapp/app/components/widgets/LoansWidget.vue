<template>
  <WidgetCard :title="$t('home.loans.title')" :more-label="$t('home.common.more')" @more="$emit('more')" @contextmenu.prevent="openMenu">
    <div class="space-y-4">
      <div>
        <div class="text-xs text-gray-500 mb-1">{{ $t('home.loans.section.lent') }}</div>
        <table class="min-w-full divide-y divide-gray-200">
          <thead class="bg-purple-50">
            <tr>
              <th class="text-left text-sm font-medium text-gray-600 py-2">{{$t('home.loans.col.item')}}</th>
              <th class="text-left text-sm font-medium text-gray-600 py-2">{{$t('home.loans.col.to')}}</th>
              <th class="text-left text-sm font-medium text-gray-600 py-2">{{$t('home.loans.col.return')}}</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-gray-100">
            <tr v-if="items && items.length" v-for="(l, idx) in items" :key="'lent-'+idx">
              <td class="py-2 text-sm">{{ l.item }}</td>
              <td class="py-2 text-sm">{{ l.to }}</td>
              <td class="py-2 text-sm" :class="isOverdue(l.returnBy) ? 'text-red-600 font-medium' : ''">{{ l.returnBy }}</td>
            </tr>
            <tr v-else>
              <td colspan="3" class="p-0"><WidgetEmptyState /></td>
            </tr>
          </tbody>
        </table>
      </div>

      <div>
        <div class="text-xs text-gray-500 mb-1">{{ $t('home.loans.section.borrowed') }}</div>
        <table class="min-w-full divide-y divide-gray-200">
          <thead class="bg-purple-50">
            <tr>
              <th class="text-left text-sm font-medium text-gray-600 py-2">{{$t('home.loans.col.item')}}</th>
              <th class="text-left text-sm font-medium text-gray-600 py-2">{{$t('home.loans.col.from')}}</th>
              <th class="text-left text-sm font-medium text-gray-600 py-2">{{$t('home.loans.col.return')}}</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-gray-100">
            <tr v-if="borrowed && borrowed.length" v-for="(b, idx) in borrowed" :key="'borrowed-'+idx">
              <td class="py-2 text-sm">{{ b.item }}</td>
              <td class="py-2 text-sm">{{ b.from }}</td>
              <td class="py-2 text-sm" :class="isOverdue(b.returnBy) ? 'text-red-600 font-medium' : ''">{{ b.returnBy }}</td>
            </tr>
            <tr v-else>
              <td colspan="3" class="p-0"><WidgetEmptyState /></td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </WidgetCard>
  <ContextMenu v-if="menu.visible" :items="menu.items" :x="menu.x" :y="menu.y" @select="onSelect" @close="menu.visible=false" />
</template>

<script setup>
import WidgetCard from './WidgetCard.vue';
import WidgetEmptyState from './WidgetEmptyState.vue';
import ContextMenu from '@/components/ui/ContextMenu.vue';
import { defineProps, defineEmits, reactive } from 'vue';

defineProps({
  title: { type: String, default: 'Verliehene Sachen' },
  moreLabel: { type: String, default: 'Mehr anzeigen' },
  items: { type: Array, default: () => [] }, // Lent: [{ item, to, returnBy }]
  borrowed: { type: Array, default: () => [] }, // Borrowed: [{ item, from, returnBy }]
});

defineEmits(['more']);

const menu = reactive({ visible: false, x: 0, y: 0, items: [
  { label: 'Erinnern', key: 'remind' },
  { label: 'Ausblenden', key: 'hide' },
]});
const openMenu = (e) => { menu.visible = true; menu.x = e.clientX; menu.y = e.clientY; };
const onSelect = (item) => { menu.visible = false; };

const isOverdue = (dateStr) => {
  // sehr einfache Prüfung; erwartet Format DD.MM.YY oder DD.MM.YYYY
  if (!dateStr) return false;
  const parts = (''+dateStr).split('.');
  if (parts.length < 3) return false;
  let [d, m, y] = parts;
  d = parseInt(d, 10); m = parseInt(m, 10) - 1; y = parseInt(y, 10);
  if (y < 100) y += 2000;
  const due = new Date(y, m, d, 23, 59, 59);
  return Date.now() > due.getTime();
};
</script>


