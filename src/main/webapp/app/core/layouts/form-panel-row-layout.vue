<template>
  <div class="row border border-gray-500 rounded-md p-4">
    <div class="md:grid md:grid-cols-2 md:gap-6">
      <div class="md:col-span-1 md:space-y-4">
        <div class="font-medium subpixel-antialiased">
          <slot name="title" />
        </div>
      </div>
      <div class="md:col-span-1 md:space-y-4">
        <div>
          <slot name="content" />
        </div>
      </div>
    </div>
  </div>
</template>
