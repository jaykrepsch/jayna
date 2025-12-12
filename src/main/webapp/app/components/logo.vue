<template>
  <router-link
    class="flex flex-shrink-0 items-center px-4 cursor-pointer"
    to="/"
  >
    <img class="h-8 w-auto" src="content/images/logo.svg" />
    <span
      v-if="showLogoText"
      class="pl-4 uppercase font-bold"
    >
      {{ $t('global.title') }}
    </span>
  </router-link>
</template>

<script setup>

defineProps({
  showLogoText: true
});

</script>