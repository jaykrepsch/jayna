<template>
  <div class="document-files">
    <div class="files-header flex items-center justify-between mb-4">
      <h3 class="text-lg font-medium text-gray-900">Dateien ({{ files.length }})</h3>
      <button 
        @click="showUpload = !showUpload"
        class="bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700 transition-colors"
      >
        {{ showUpload ? 'Upload ausblenden' : 'Datei hinzufügen' }}
      </button>
    </div>

    <!-- Upload-Bereich -->
    <div v-if="showUpload" class="upload-section mb-6">
      <DocumentUpload
        :entity-id="entityId"
        :model-value="files"
        @update:model-value="updateFiles"
        @file-uploaded="handleFileUploaded"
        @file-removed="handleFileRemoved"
        @download-file="handleDownloadFile"
      />
    </div>

    <!-- Dateien-Liste -->
    <div v-if="files.length > 0" class="files-list">
      <div class="space-y-3">
        <div 
          v-for="(file, index) in files" 
          :key="file.id || file.name" 
          class="file-item bg-white border border-gray-200 rounded-lg p-4 flex items-center justify-between hover:bg-gray-50 transition-colors"
        >
          <div class="file-info flex items-center space-x-3">
            <div class="file-icon">
              <DocumentIcon class="w-5 h-5 text-gray-400" />
            </div>
            <div class="file-details">
              <span class="file-name font-medium text-gray-900">{{ file.fileName || file.name }}</span>
              <div class="file-meta text-sm text-gray-500">
                <span>{{ formatFileSize(file.fileSize || file.size) }}</span>
                <span v-if="file.uploadDate" class="ml-2">{{ formatDate(file.uploadDate) }}</span>
              </div>
            </div>
          </div>
          <div class="file-actions flex items-center space-x-2">
            <button 
              @click="handleViewFile(file)" 
              class="action-button bg-green-600 text-white px-3 py-1 rounded text-sm hover:bg-green-700 transition-colors"
            >
              Anzeigen
            </button>
            <button 
              @click="handleDownloadFile(file)" 
              class="action-button bg-blue-600 text-white px-3 py-1 rounded text-sm hover:bg-blue-700 transition-colors"
            >
              Herunterladen
            </button>
            <button 
              @click="handleFileRemoved(file)" 
              class="action-button bg-red-600 text-white px-3 py-1 rounded text-sm hover:bg-red-700 transition-colors"
            >
              Löschen
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Leerer Zustand -->
    <div v-else class="empty-state text-center py-12">
      <DocumentIcon class="w-16 h-16 text-gray-300 mx-auto mb-4" />
      <h3 class="text-lg font-medium text-gray-900 mb-2">Keine Dateien vorhanden</h3>
      <p class="text-gray-500 mb-4">Fügen Sie Dateien zu diesem Dokumenten-Datensatz hinzu.</p>
      <button 
        @click="showUpload = true"
        class="bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700 transition-colors"
      >
        Erste Datei hinzufügen
      </button>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue';
import { DocumentIcon } from '@heroicons/vue/24/outline';
import DocumentUpload from './document-upload.vue';
import AlertService from '@/services/alert.service';

export default {
  name: 'DocumentFiles',
  components: {
    DocumentIcon,
    DocumentUpload
  },
  props: {
    entityId: {
      type: Number,
      required: false
    },
    modelValue: {
      type: Array,
      default: () => []
    }
  },
  emits: ['update:modelValue', 'file-uploaded', 'file-removed', 'download-file', 'view-file'],
  setup(props, { emit }) {
    const files = ref(props.modelValue);
    const showUpload = ref(false);

    const updateFiles = (newFiles) => {
      files.value = newFiles;
      emit('update:modelValue', newFiles);
    };

    const handleFileUploaded = (file) => {
      emit('file-uploaded', file);
      showUpload.value = false; // Upload-Bereich ausblenden nach erfolgreichem Upload
    };

    const handleFileRemoved = (file) => {
      emit('file-removed', file);
    };

    const handleDownloadFile = (file) => {
      emit('download-file', file);
    };

    const handleViewFile = (file) => {
      emit('view-file', file);
    };

    const formatFileSize = (bytes) => {
      if (!bytes) return '0 B';
      const k = 1024;
      const sizes = ['B', 'KB', 'MB', 'GB'];
      const i = Math.floor(Math.log(bytes) / Math.log(k));
      return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
    };

    const formatDate = (dateString) => {
      if (!dateString) return '';
      const date = new Date(dateString);
      return date.toLocaleDateString('de-DE');
    };

    return {
      files,
      showUpload,
      updateFiles,
      handleFileUploaded,
      handleFileRemoved,
      handleDownloadFile,
      formatFileSize,
      formatDate
    };
  }
};
</script>

<style scoped>
.document-files {
  @apply w-full;
}

.files-header {
  @apply border-b border-gray-200 pb-4;
}

.upload-section {
  @apply border border-gray-200 rounded-lg p-4 bg-gray-50;
}

.files-list {
  @apply space-y-3;
}

.file-item {
  @apply transition-all duration-200;
}

.file-item:hover {
  @apply shadow-sm;
}

.action-button {
  @apply transition-colors duration-200;
}
</style>
