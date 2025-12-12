<template>
  <div :class="'rounded-md bg-' + alertClass + '-50 p-4'">
    <div class="flex">
      <div class="flex-shrink-0">
        <CheckCircleIcon
        v-if="type == 'success'"
          class="h-5 w-5 text-green-400"
          aria-hidden="true" 
        />
        <ExclamationTriangleIcon 
          v-if="type == 'warn'"
          class="h-5 w-5 text-yellow-400"
          aria-hidden="true"
        />
        <XCircleIcon 
          v-if="type == 'danger'"
          class="h-5 w-5 text-red-400"
          aria-hidden="true"
        />
      </div>
      <div class="ml-3">
        <h3
          v-if="headerKey"
          :class="'text-sm font-medium text-' + alertClass + '-800'"
          v-text="$t(headerKey)"
        />
        <div :class="[headerKey ? 'mt-2' : '', 'text-sm text-' + alertClass + '-700']">
          <p v-text="$t(messageKey)" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue';

import { ExclamationTriangleIcon, XCircleIcon, CheckCircleIcon } from '@heroicons/vue/20/solid'

const props = defineProps({
  type: {
    type: String,
    default: 'success'
  },
  headerKey: {
    type: String,
    required: false
  },
  messageKey: {
    type: String,
    required: true
  }
});

const alertClass = computed(() => {
  switch (props.type) {
    case 'success':
      return 'green';
    case 'warn':
      return 'yellow';
    case 'danger':
      return 'red';
    default:
      return 'green';
  }
});
</script>