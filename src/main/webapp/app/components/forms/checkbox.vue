<template>
  <div class="relative flex items-start">
    <div class="flex h-6 items-center">
      <input 
        :name="name"
        :value="modelValue"
        :disabled="disabled"
        @change="$emit('update:modelValue', $event.target.checked)"
        aria-describedby="comments-description"
        type="checkbox" 
        class="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-600"
      />
    </div>
    <div class="ml-3 text-sm leading-6">
      <label :for="name" class="text-gray-900">{{ label }}</label>
    </div>
  </div>
</template>

<script setup>

//@input="$emit('update:modelValue', $event.target.value)"

defineProps({
  name: String,
  label: String,
  modelValue: Boolean,
  disabled: {
    type: Boolean,
    default: false
  }
});

</script>