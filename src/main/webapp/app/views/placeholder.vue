<template>
  <div class="max-w-4xl mx-auto space-y-6">
    <h1 class="text-2xl font-semibold text-gray-900" v-text="title" />
    <div class="prose max-w-none">
      <p>{{ ideasIntro }}</p>
      <ul class="list-disc pl-6 mt-4">
        <li v-for="(b, idx) in bullets" :key="idx">{{ b }}</li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute } from 'vue-router';

const { t } = useI18n();
const route = useRoute();

const titleKey = computed(() => route.meta?.placeholder?.titleKey);
const ideasText = computed(() => route.meta?.placeholder?.ideas || '');
const bullets = computed(() => route.meta?.placeholder?.bullets || []);

const title = computed(() => {
  const raw = titleKey.value ? t(titleKey.value) : '';
  const noStar = raw.replace(/\*$/, '');
  return `Hier entsteht ${noStar}`;
});

const ideasIntro = computed(() => `Ideen: ${ideasText.value}`);
</script>


