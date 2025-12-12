<template>
  <WidgetCard :title="$t('home.warranties.title')" :info="$t('home.warranties.info')" :more-label="$t('home.common.more')" @more="$emit('more')">
    <table class="min-w-full divide-y divide-gray-200">
      <thead class="bg-purple-50">
        <tr>
          <th class="text-left text-sm font-medium text-gray-600 py-2">{{$t('home.warranties.col.item')}}</th>
          <th class="text-left text-sm font-medium text-gray-600 py-2">{{$t('home.warranties.col.until')}}</th>
          <th class="text-left text-sm font-medium text-gray-600 py-2">{{$t('home.warranties.col.type')}}</th>
        </tr>
      </thead>
      <tbody class="divide-y divide-gray-100">
        <tr v-if="items && items.length" v-for="(w, idx) in items" :key="idx">
          <td class="py-2 text-sm">{{ w.item }}</td>
          <td class="py-2 text-sm">{{ w.until }}</td>
          <td class="py-2 text-sm">{{ w.kind }}</td>
        </tr>
        <tr v-else>
          <td colspan="3" class="p-0"><WidgetEmptyState /></td>
        </tr>
      </tbody>
    </table>
  </WidgetCard>
</template>

<script setup>
import WidgetCard from './WidgetCard.vue';
import WidgetEmptyState from './WidgetEmptyState.vue';
import { defineProps } from 'vue';

defineProps({
  items: { type: Array, default: () => [] }, // [{ item, until, kind }]
});
</script>



