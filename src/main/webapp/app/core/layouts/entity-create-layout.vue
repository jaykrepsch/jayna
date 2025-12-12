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
            type="secondary"
            @click.prevent="$emit('cancel')"
          />
          <Button
            :text="$t('button.save')"
            @click.prevent="$emit('save')"
          />
        </div>
      </div>
    </div>
    
    <slot />
  </div>
</template>

<script setup>

import Button from '@/components/buttons/button.vue';

defineEmits(['save', 'cancel', 'edit-form']);

const props = defineProps({
  messageKey: {
    type: String,
    default: null
  },
  showEditFormButton: {
    type: Boolean,
    default: false
  }
});

</script>