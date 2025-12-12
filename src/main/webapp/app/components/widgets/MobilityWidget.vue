<template>
  <WidgetCard :title="$t('home.mobility.title')" :info="$t('home.mobility.info')" :more-label="$t('home.common.more')" @more="$emit('more')">
    <ul class="text-sm space-y-2">
      <li v-if="items && items.length" v-for="(m, idx) in items" :key="idx" class="flex items-center justify-between">
        <div class="truncate">
          <span class="font-medium">{{ m.vehicle }}</span>
          <span class="ml-2 text-gray-500">{{ m.km }} km</span>
        </div>
        <div class="text-right text-xs text-gray-600">
          <div>HU: {{ m.hu }}</div>
          <div>Service: {{ m.service }}</div>
          <div>Vers.: {{ m.insurance }}</div>
        </div>
      </li>
      <li v-else>
        <WidgetEmptyState />
      </li>
    </ul>
  </WidgetCard>
</template>

<script setup>
import WidgetCard from './WidgetCard.vue';
import WidgetEmptyState from './WidgetEmptyState.vue';
import { defineProps } from 'vue';

defineProps({
  items: { type: Array, default: () => [] }, // [{ vehicle, km, hu, service, insurance }]
});
</script>



