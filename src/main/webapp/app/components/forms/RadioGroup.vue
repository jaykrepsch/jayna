<template>
  <div>
    <label class="text-sm font-medium text-gray-900">
      {{ label }}
    </label>
    <fieldset class="mt-4">
      <div class="flex items-center space-x-10">
        <div 
          v-for="item in items"
          :key="item.id"
          class="flex items-center"
        >
          <input
            :id="name + item.id"
            :name="name"
            type="radio"
            :checked="item.id === modelValue"
            :disabled="disabled"
            class="h-4 w-4 border-gray-300 text-main-600 focus:ring-main-600"
            @change="$emit('update:modelValue', item.id)"
          >
          <label
            :for="name"
            class="ml-3 block text-sm font-medium leading-6 text-gray-900"
          >
            {{ $t(item.label) }}
          </label>
        </div>
      </div>
    </fieldset>
  </div>
</template>

<script setup>

const props = defineProps({
  name: {
    type: String,
    required: true
  },
  label: {
    type: String,
    required: true
  },
  items: {
    type: Array,
    default: () => []
  },
  modelValue: {
    type: Boolean,
    default: null
  },
  disabled: {
    type: Boolean,
    default: false
  }
});

defineEmits(['update:modelValue']);

</script>