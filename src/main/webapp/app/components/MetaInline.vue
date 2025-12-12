<template>
  <div class="bg-gray-50 rounded-lg p-3 text-sm text-gray-700">
    <div class="flex flex-wrap items-center gap-x-3 gap-y-1">
      <div><span class="font-medium">Erstellt am:</span> <span>{{ formatDate(createdDate) }}</span></div>
      <div class="text-gray-400 select-none">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
      <div><span class="font-medium">Erstellt von:</span> <span>{{ createdBy || 'N/A' }}</span></div>
      <div class="text-gray-400 select-none">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
      <div><span class="font-medium">Zuletzt geändert:</span> <span>{{ formatDate(lastModifiedDate) }}</span></div>
      <div class="text-gray-400 select-none">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
      <div><span class="font-medium">Geändert von:</span> <span>{{ lastModifiedBy || 'N/A' }}</span></div>
    </div>
    <div v-if="formName" class="mt-2 border-t border-gray-200 pt-2">
      <div class="text-gray-700">
        <span class="font-medium">Formdefinition:</span>
        <span class="ml-1">{{ formName }}</span>
      </div>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  createdDate: String,
  createdBy: String,
  lastModifiedDate: String,
  lastModifiedBy: String,
  formName: String,
});

const formatDate = (dateString) => {
  if (!dateString) return 'N/A';
  try {
    const date = new Date(dateString);
    return date.toLocaleDateString('de-DE');
  } catch {
    return 'N/A';
  }
};

</script>

<style scoped>
</style>


