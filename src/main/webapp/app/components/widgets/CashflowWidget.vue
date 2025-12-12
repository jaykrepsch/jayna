<template>
  <WidgetCard :title="$t('home.cashflow.title')" :info="$t('home.cashflow.info')" :more-label="$t('home.common.more')" @more="$emit('more')" @contextmenu.prevent="openMenu">
    <div class="h-full flex flex-col">
      <div class="px-4 text-xs text-gray-500 mb-1">Letzte 12 Wochen</div>
      <div class="flex-1 px-4">
        <div class="relative h-28">
          <div class="absolute inset-0 flex items-end gap-1">
            <div
              v-for="(v, i) in values"
              :key="i"
              class="flex-1 bg-purple-300 hover:bg-purple-400 relative"
              :style="{ height: Math.max(2, Math.min(100, Math.abs(v))) + 'px' }"
              @mouseenter="showTip(i, v, $event)"
              @mouseleave="hideTip"
            ></div>
          </div>
          <div v-if="tip.visible" class="absolute -translate-x-1/2 -translate-y-full bg-black text-white text-[10px] px-2 py-1 rounded pointer-events-none" :style="{ left: tip.x + 'px', top: tip.y + 'px' }">
            {{ tip.text }}
          </div>
        </div>
        <div class="flex justify-between text-[10px] text-gray-500 mt-1">
          <span>−11</span>
          <span>−8</span>
          <span>−5</span>
          <span>−2</span>
          <span>0</span>
        </div>
      </div>
    </div>
  </WidgetCard>
  <ContextMenu v-if="menu.visible" :items="menu.items" :x="menu.x" :y="menu.y" @select="onSelect" @close="menu.visible=false" />
</template>

<script setup>
import WidgetCard from './WidgetCard.vue';
import ContextMenu from '@/components/ui/ContextMenu.vue';
import { defineProps, defineEmits, reactive } from 'vue';

defineProps({
  title: { type: String, default: 'Cashflow' },
  info: { type: String, default: 'Trend Einnahmen/Ausgaben.' },
  moreLabel: { type: String, default: 'Mehr anzeigen' },
  values: { type: Array, default: () => [10,20,15,30,18,40,22,35,28,32,26,38] },
});
defineEmits(['more']);

const menu = reactive({ visible: false, x: 0, y: 0, items: [
  { label: 'Ausblenden', key: 'hide' },
]});
const openMenu = (e) => { menu.visible = true; menu.x = e.clientX; menu.y = e.clientY; };
const onSelect = (item) => { menu.visible = false; };

const tip = reactive({ visible: false, x: 0, y: 0, text: '' });
const showTip = (i, v, evt) => {
  const parent = evt.currentTarget.parentElement.getBoundingClientRect();
  const rect = evt.currentTarget.getBoundingClientRect();
  tip.visible = true;
  tip.x = rect.left - parent.left + rect.width / 2;
  tip.y = rect.top - parent.top;
  const sign = v >= 0 ? '+' : '−';
  tip.text = `${sign}${Math.abs(v)}`;
};
const hideTip = () => { tip.visible = false; };
</script>


