<template>
  <div class="p-4">
    <h3 class="text-lg font-semibold text-gray-900 mb-4">Dokument-Vorschau</h3>
    
    <!-- Test Content -->
    <div class="space-y-4">
      <div class="bg-blue-50 p-4 rounded-lg">
        <h4 class="font-medium text-blue-900 mb-2">Test Vorschau</h4>
        <p class="text-blue-700 text-sm">Diese Vorschau funktioniert!</p>
      </div>
      
      <!-- Document Info -->
      <div v-if="documentData" class="bg-gray-50 p-4 rounded-lg">
        <h4 class="font-medium text-gray-900 mb-2">Dokument-Informationen</h4>
        <div class="space-y-2 text-sm">
          <div class="flex justify-between">
            <span class="text-gray-600">ID:</span>
            <span class="font-medium">{{ documentData.id || 'N/A' }}</span>
          </div>
          <div class="flex justify-between">
            <span class="text-gray-600">Titel:</span>
            <span class="font-medium">{{ documentData.title || 'N/A' }}</span>
          </div>
          <div class="flex justify-between">
            <span class="text-gray-600">Beschreibung:</span>
            <span class="font-medium">{{ documentData.description || 'N/A' }}</span>
          </div>
          <div class="flex justify-between">
            <span class="text-gray-600">Dokumentnummer:</span>
            <span class="font-medium">{{ documentData.documentNumber || 'N/A' }}</span>
          </div>
          <div class="flex justify-between">
            <span class="text-gray-600">Partner:</span>
            <span class="font-medium">{{ documentData.partner || 'N/A' }}</span>
          </div>
        </div>
      </div>
      
      <!-- File Info -->
      <div v-if="documentData?.fileName" class="bg-green-50 p-4 rounded-lg">
        <h4 class="font-medium text-green-900 mb-2">Datei-Informationen</h4>
        <div class="space-y-2 text-sm">
          <div class="flex justify-between">
            <span class="text-green-700">Dateiname:</span>
            <span class="font-medium">{{ documentData.fileName }}</span>
          </div>
          <div v-if="documentData.fileType" class="flex justify-between">
            <span class="text-green-700">Typ:</span>
            <span class="font-medium">{{ getFileTypeDisplay(documentData.fileType) }}</span>
          </div>
        </div>
      </div>
      
      <!-- Download Button -->
      <button 
        @click="downloadDocument"
        class="w-full bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700 transition-colors"
      >
        Dokument herunterladen
      </button>
    </div>
  </div>
</template>

<script setup>
import { onMounted } from 'vue';

const props = defineProps({
  documentData: {
    type: Object,
    required: true
  }
});

const getFileTypeDisplay = (fileType) => {
  if (!fileType) return '-';
  
  const typeMap = {
    'application/pdf': 'PDF',
    'text/plain': 'Text',
    'image/jpeg': 'JPEG',
    'image/png': 'PNG',
    'application/msword': 'Word',
    'application/vnd.openxmlformats-officedocument.wordprocessingml.document': 'Word',
    'application/vnd.ms-excel': 'Excel',
    'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet': 'Excel',
    'message/rfc822': 'E-Mail',
    'text/html': 'HTML'
  };
  
  return typeMap[fileType] || fileType;
};

const downloadDocument = () => {
  console.log('Download document:', props.documentData?.fileName);
  alert('Download-Funktionalität wird implementiert');
};

onMounted(() => {
  console.log('DocumentPreview mounted with data:', props.documentData);
});
</script>
