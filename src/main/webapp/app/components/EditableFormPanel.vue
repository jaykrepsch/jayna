<template>
  <div class="md:grid md:grid-cols-9 md:gap-6">
    <div class="md:col-span-2">
      <div class="px-4 sm:px-0">
        <div class="flex flex-row space-x-4">
          <h3
            v-if="config"
            class="text-lg font-medium leading-6 text-gray-900"
          >
            {{ $t(config.title) }}
          </h3>

        </div>
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



const props = defineProps({
  config: {
    type: Object,
    required: true
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