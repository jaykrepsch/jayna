<template>
  <div>
    <div class="flex items-center justify-between -mt-8 -mx-12 py-8 px-12 bg-gray-100 sticky top-16 z-10">
      <div class="flex-auto">
        <h1 class="text-xl font-semibold text-gray-900">{{ messageKey }}</h1>
      </div>
      <div class="flex flex-row space-x-4">
        <Button
          type="secondary"
          :text="$t('entity.action.back')"
          @click.prevent="$router.push(backButtonPath)"
        />
        <Button
          :text="$t('entity.action.edit')"
          @click.prevent="$router.push({ name: editRouterName, params: editRouterParams })"
        />
      </div>
    </div>

    <div>
      <slot />
    </div>



  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute } from 'vue-router';

import Button from '@/components/buttons/button.vue';

const { t } = useI18n();
const route = useRoute();







const props = defineProps({
  messageKey: {
    type: String,
    default: null
  },
  editRouterName: {
    type: String,
    default: null
  },
  editRouterParams: {
    type: Object,
    default: null
  },
  backButtonPath: String
});

// Dynamische Übersetzungsschlüssel basierend auf der Route
const entityName = computed(() => {
  const path = route.path;
  if (path.includes('/contract/')) return 'contract';
  if (path.includes('/contact/')) return 'contact';
  if (path.includes('/realestate/')) return 'realestate';
  if (path.includes('/mobility/')) return 'mobility';
  if (path.includes('/financeaccount/')) return 'financeaccount';
  return 'contract'; // Fallback
});



</script>