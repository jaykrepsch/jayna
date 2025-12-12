<template>
  <div class="md:grid md:grid-cols-9 md:gap-6">
    <div class="md:col-span-2">
      <div class="px-4 sm:px-0">
        <h3
          v-if="title"
          class="text-lg font-medium leading-6 text-gray-900"
        >
          {{ title }}
        </h3>
        <p
          v-if="subTitle"
          class="mt-1 text-sm text-gray-600"
        >
          {{ subTitle }}
        </p>
      </div>
    </div>
    <div class="mt-5 md:col-span-7 md:mt-0">
      <form>
        <div class="shadow sm:rounded-md">
          <div :class="[bgColor, 'space-y-6 px-4 py-5 sm:p-6']">
            <slot name="content" />
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>

defineProps({
  title: {
    type: String,
    default: null
  },
  subTitle: {
    type: String,
    default: null
  },
  bgColor: {
    type: String,
    default: 'bg-white'
  }
});

</script>