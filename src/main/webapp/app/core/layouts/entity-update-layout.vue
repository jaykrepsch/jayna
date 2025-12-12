<template>
  <div>
    <div class="flex items-center justify-between -mt-8 -mx-12 py-8 px-12 bg-gray-100 sticky top-16 z-10">
      <div class="flex-auto">
        <h1 class="text-xl font-semibold text-gray-900" v-text="messageKey" />
      </div>
      <div>
        <div class="flex flex-row space-x-4">
          <Button
            :text="$t('button.cancel')"
            :type="'secondary'"
            @click.prevent="$router.go(-1)" 
          />
          <Button
            :text="$t('button.delete')"
            :type="'secondary'"
            @click.prevent="showDangerConfirmationModal = true"
          />
          <Button
            :text="$t('button.save')"
            @click.prevent="$emit('save')"
          />
        </div>
      </div>
    </div>

    <div>
      <slot />
    </div>

    <DangerConfirmation 
      :show="showDangerConfirmationModal"
      :title-key="t(deleteTitleKey)"
      :content-key="t(deleteContentKey)"
      @confirm="showDangerConfirmationModal = false; $emit('delete');"
      @close="showDangerConfirmationModal = false"
    />
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute } from 'vue-router';

import DangerConfirmation from "@/components/modals/danger-confirmation.vue";
import Button from '@/components/buttons/button.vue';

const { t } = useI18n();
const route = useRoute();

defineEmits(['delete', 'save']);

const showDangerConfirmationModal = ref(false);

const props = defineProps({
  messageKey: {
    type: String,
    default: null
  }
});

// Dynamische Übersetzungsschlüssel basierend auf der Route
const entityName = computed(() => {
  const path = route.path;
  if (path.includes('/document/')) return 'document';
  if (path.includes('/contract/')) return 'contract';
  if (path.includes('/contact/')) return 'contact';
  if (path.includes('/realestate/')) return 'realestate';
  if (path.includes('/mobility/')) return 'mobility';
  if (path.includes('/financeaccount/')) return 'financeaccount';
  return 'contract'; // Fallback
});

const deleteTitleKey = computed(() => `jaynaApp.${entityName.value}.messages.delete.title`);
const deleteContentKey = computed(() => `jaynaApp.${entityName.value}.messages.delete.content`);

</script>