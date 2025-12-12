<template>
  <div>
    <label
      for="search"
      class="block text-sm font-medium text-gray-700"
      v-text="label"
    />
    <div class="relative mt-1 rounded-md shadow-sm">
      <input
        class="'block w-full rounded-md pr-10 sm:text-sm disabled:opacity-50"
        aria-invalid="true"
        aria-describedby="email-error"
        :placeholder="placeholder"
        type="text"
        name="search"
        :value="modelValue"
        @input="$emit('update:modelValue', $event.target.value)"
      />
      <div
        v-if="loading"
        class="pointer-events-none absolute inset-y-0 right-0 flex items-center pr-3"
      >
        <spinner
          class="mr-2"
          height="h-5"
          width="w-5"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import Spinner from '@/components/spinners/Spinner.vue';

const props = defineProps({
  modelValue: {
    type: [String, Number],
    default: null
  },
  label: {
    type: String,
    default: null
  },
  placeholder: {
    type: String,
    default: null
  },
  loading: Boolean
});

const emits = defineEmits(['update:modelValue']);

</script>