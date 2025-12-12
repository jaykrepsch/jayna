<template>
  <div class="shadow sm:overflow-hidden sm:rounded-md">
    <div class="space-y-6 bg-white px-4 py-5 sm:p-6">
      <div class="grid grid-cols-3 gap-6">
        <slot name="content" />
      </div>
    </div>
  </div>
</template>