<template>
  <div class="space-y-4">
    <div class="flex items-center justify-between">
      <h1 class="text-lg font-semibold">Analytics (Prototyp)</h1>
      <div class="text-sm text-gray-500">Diese Seite ist noch nicht eingebunden.</div>
    </div>

    <div class="grid grid-cols-1 gap-4 md:grid-cols-2 xl:grid-cols-3">
      <WidgetCard title="KPIs" @more="noop">
        <div class="grid grid-cols-3 gap-3">
          <div class="bg-gray-50 rounded p-3">
            <div class="text-xs text-gray-500">Verträge</div>
            <div class="text-xl font-semibold">—</div>
          </div>
          <div class="bg-gray-50 rounded p-3">
            <div class="text-xs text-gray-500">Dokumente</div>
            <div class="text-xl font-semibold">—</div>
          </div>
          <div class="bg-gray-50 rounded p-3">
            <div class="text-xs text-gray-500">Ausgaben (Monat)</div>
            <div class="text-xl font-semibold">—</div>
          </div>
        </div>
      </WidgetCard>

      <WidgetCard title="Zeitverlauf" @more="noop">
        <div class="h-40 flex items-center justify-center text-gray-400">
          Platzhalter für Chart
        </div>
      </WidgetCard>

      <WidgetCard title="Top Kategorien" @more="noop">
        <ul class="list-disc list-inside text-sm">
          <li>—</li>
          <li>—</li>
          <li>—</li>
        </ul>
      </WidgetCard>
    </div>
  </div>
</template>

<script setup>
import WidgetCard from '@/components/widgets/WidgetCard.vue';

const noop = () => {};
</script>


