<template>
  <WidgetCard :title="$t('home.budgets.title')" :info="$t('home.budgets.info')" :more-label="$t('home.common.more')" @more="$emit('more')" @contextmenu.prevent="openMenu">
    <table class="min-w-full divide-y divide-gray-200">
      <thead class="bg-purple-50">
        <tr>
          <th class="text-left text-sm font-medium text-gray-600 py-2">Kategorie</th>
          <th class="text-left text-sm font-medium text-gray-600 py-2">Budget</th>
          <th class="text-left text-sm font-medium text-gray-600 py-2">Ist</th>
        </tr>
      </thead>
      <tbody class="divide-y divide-gray-100">
        <tr v-if="items && items.length" v-for="(b, idx) in items" :key="idx">
          <td class="py-2 text-sm">{{ b.category }}</td>
          <td class="py-2 text-sm">{{ formatEuro(b.budget) }}</td>
          <td class="py-2 text-sm">
            <div class="flex items-center gap-2">
              <div class="w-28 h-2 bg-gray-200 rounded">
                <div class="h-2 rounded" :class="b.percent > 100 ? 'bg-red-500' : 'bg-green-500'" :style="{ width: Math.min(b.percent, 100) + '%' }"></div>
              </div>
              <span :class="b.percent > 100 ? 'text-red-600' : 'text-gray-700'">{{ formatEuro(b.actual) }}</span>
            </div>
          </td>
        </tr>
        <tr v-else>
          <td colspan="3" class="p-0"><WidgetEmptyState /></td>
        </tr>
      </tbody>
    </table>
    <div class="mt-3 text-xs text-gray-500 flex items-center gap-4">
      <div class="flex items-center gap-1"><span class="inline-block w-3 h-3 bg-green-500 rounded"></span> {{$t('home.budgets.legend.ok')}}</div>
      <div class="flex items-center gap-1"><span class="inline-block w-3 h-3 bg-red-500 rounded"></span> {{$t('home.budgets.legend.over')}}</div>
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
  title: { type: String, default: 'Budgets' },
  info: { type: String, default: 'Monatsbudget im Vergleich zum Ist.' },
  moreLabel: { type: String, default: 'Mehr anzeigen' },
  items: { type: Array, default: () => [] }, // [{ category, budget, actual, percent }]
});
defineEmits(['more']);

const menu = reactive({ visible: false, x: 0, y: 0, items: [
  { label: 'Ausblenden', key: 'hide' },
]});
const openMenu = (e) => { menu.visible = true; menu.x = e.clientX; menu.y = e.clientY; };
const onSelect = (item) => { menu.visible = false; };

const formatEuro = (val) => {
  if (typeof val === 'string') return val;
  try { return new Intl.NumberFormat('de-DE', { style: 'currency', currency: 'EUR', maximumFractionDigits: 0 }).format(val); } catch { return String(val); }
};
</script>


