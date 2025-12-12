<template>
  <div class="document-upload">
    <div class="upload-area" @drop="handleDrop" @dragover="handleDragOver" @dragleave="handleDragLeave">
      <div v-if="!isUploading" class="upload-content">
        <div class="upload-text-container">
          <DocumentIcon class="upload-icon-small" />
          <div class="upload-text-wrapper">
            <span class="upload-text-inline">{{ $t('jaynaApp.document.upload.dragDrop') }}</span>
            <span class="upload-text-subtitle">PDF, DOC, DOCX, JPG, PNG bis 10MB</span>
          </div>
        </div>
        <input
          ref="fileInput"
          type="file"
          multiple
          accept=".pdf,.doc,.docx,.txt,.jpg,.jpeg,.png,.gif"
          @change="handleFileSelect"
          class="file-input"
        />
        <button @click="triggerFileSelect" class="upload-button-compact">
          {{ $t('jaynaApp.document.upload.selectFiles') }}
        </button>
      </div>
      <div v-else class="upload-progress">
        <div class="progress-bar">
          <div class="progress-fill" :style="{ width: uploadProgress + '%' }"></div>
        </div>
        <p>{{ $t('jaynaApp.document.upload.uploading') }} {{ uploadProgress }}%</p>
      </div>
    </div>


  </div>
</template>

<script>
import { DocumentIcon } from '@heroicons/vue/24/outline';
import DocumentService from '@/services/document.service';
import AlertService from '@/services/alert.service';

export default {
  name: 'DocumentUpload',
  components: {
    DocumentIcon
  },
  watch: {
    entityId(newVal) {
      if (newVal) {
        // Sobald eine ID vorhanden ist, gepufferte Dateien hochladen
        this.uploadBufferedFiles();
      }
    }
  },
  props: {
    entityId: {
      type: Number,
      required: false
    },
    entityType: {
      type: String,
      required: false
    },
    modelValue: {
      type: Array,
      default: () => []
    },
    deferred: {
      type: Boolean,
      default: false
    },
    persistInSession: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      isUploading: false,
      uploadProgress: 0,
      documentService: new DocumentService(),
      bufferedFiles: [] // speichert Original-Dateiobjekte bis eine entityId vorhanden ist
    };
  },
  computed: {
    uploadedFiles: {
      get() {
        return this.modelValue;
      },
      set(value) {
        this.$emit('update:modelValue', value);
      }
    }
  },
  methods: {
    triggerFileSelect() {
      if (this.$refs.fileInput) {
        this.$refs.fileInput.click();
      }
    },

    handleFileSelect(event) {
      const files = Array.from(event.target.files);
      this.uploadFiles(files);
    },

    handleDrop(event) {
      event.preventDefault();
      const files = Array.from(event.dataTransfer.files);
      this.uploadFiles(files);
    },

    handleDragOver(event) {
      event.preventDefault();
      event.currentTarget.classList.add('drag-over');
    },

    handleDragLeave(event) {
      event.preventDefault();
      event.currentTarget.classList.remove('drag-over');
    },

    async uploadFiles(files) {
      this.isUploading = true;
      this.uploadProgress = 0;

      for (let i = 0; i < files.length; i++) {
        const file = files[i];
        
        // Validierung
        if (file.size > 10 * 1024 * 1024) {
          AlertService.showError(`Datei ${file.name} ist zu groß. Maximale Größe: 10MB`);
          continue;
        }

        const allowedTypes = [
          'application/pdf',
          'application/msword',
          'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
          'image/jpeg',
          'image/png',
          'text/plain'
        ];
        
        if (!allowedTypes.includes(file.type)) {
          AlertService.showError(`Dateityp ${file.type} wird nicht unterstützt`);
          continue;
        }

        try {
          if (this.entityId && !this.deferred) {
            // Direkt an Server hochladen
            const result = await this.documentService.upload(this.entityId, file);
            this.uploadedFiles.push(result);
            this.$emit('file-uploaded', result);
            AlertService.showSuccess(`Datei ${file.name} erfolgreich hochgeladen`);
          } else {
            // Puffer speichern und UI mit Mock füllen
            const tmpId = Date.now() + i;
            const mockResult = {
              id: `tmp-${tmpId}`,
              fileName: file.name,
              fileSize: file.size,
              fileType: file.type,
              uploadDate: new Date().toISOString(),
              name: file.name,
              size: file.size,
              type: file.type,
              previewUrl: URL.createObjectURL(file),
              __rawFile: file
            };
            this.bufferedFiles.push({ tmpId, file });
            // Optional: Persistenz im sessionStorage nur wenn explizit erlaubt
            if (this.persistInSession) {
              try {
                const reader = new FileReader();
                reader.onload = () => {
                  try {
                    const entry = { name: file.name, type: file.type, dataUrl: reader.result };
                    const key = 'jayna_doc_buffer';
                    const arr = JSON.parse(sessionStorage.getItem(key) || '[]');
                    // Begrenze Gesamtdaten grob, um QuotaExceeded zu vermeiden (~3MB)
                    const approxSize = (arr.reduce((s, e) => s + (e.dataUrl?.length || 0), 0) + (entry.dataUrl?.length || 0));
                    if (approxSize < 3 * 1024 * 1024) {
                      arr.push(entry);
                      sessionStorage.setItem(key, JSON.stringify(arr));
                    }
                  } catch {}
                };
                reader.readAsDataURL(file);
              } catch {}
            }
            this.uploadedFiles.push(mockResult);
            // Bei deferred den Roh-File im Event mitgeben, damit der Parent später wirklich hochlädt
            this.$emit('file-uploaded', { ...mockResult, __rawFile: file });
          }
        } catch (error) {
          console.error('Upload error:', error);
          AlertService.showError(`Fehler beim Hochladen von ${file.name}: ${error.message}`);
        }

        this.uploadProgress = ((i + 1) / files.length) * 100;
      }

      this.isUploading = false;
      this.uploadProgress = 0;
      if (this.$refs.fileInput) {
        this.$refs.fileInput.value = '';
      }
    },

    async uploadBufferedFiles() {
      // Lädt alle im Create-Workflow gepufferten Dateien hoch, sobald eine entityId verfügbar ist
      if (!this.entityId || this.bufferedFiles.length === 0) return;
      for (let j = 0; j < this.bufferedFiles.length; j++) {
        const { tmpId, file } = this.bufferedFiles[j];
        try {
          const result = await this.documentService.upload(this.entityId, file);
          // Ersetze Mock-Eintrag durch Serverresultat
          const idx = this.uploadedFiles.findIndex(f => (f.id === tmpId) || (f.fileName === file.name && f.fileSize === file.size));
          if (idx !== -1) {
            this.uploadedFiles.splice(idx, 1, result);
          } else {
            this.uploadedFiles.push(result);
          }
          this.$emit('file-uploaded', result);
        } catch (e) {
          console.error('Buffered upload failed:', e);
          AlertService.showError(`Fehler beim Hochladen von ${file.name}: ${e.message}`);
        }
      }
      this.bufferedFiles = [];
      // Entferne auch den sessionStorage-Puffer nach erfolgreichem Upload
      try { sessionStorage.removeItem('jayna_doc_buffer'); } catch {}
    },

    formatFileSize(bytes) {
      if (!bytes || bytes === 0) return '0 Bytes';
      const k = 1024;
      const sizes = ['Bytes', 'KB', 'MB', 'GB'];
      const i = Math.floor(Math.log(bytes) / Math.log(k));
      return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
    },

    viewFile(file) {
      this.$emit('view-file', file);
    },

    removeFile(file) {
      const index = this.uploadedFiles.indexOf(file);
      if (index > -1) {
        if (file.previewUrl) {
          try { URL.revokeObjectURL(file.previewUrl); } catch(e) {}
        }
        this.uploadedFiles.splice(index, 1);
        // Entferne auch aus Puffer
        this.bufferedFiles = this.bufferedFiles.filter(entry => entry.tmpId !== file.id);
        this.$emit('file-removed', file);
      }
    },

    downloadFile(file) {
      this.$emit('download-file', file);
    },

    formatDate(dateString) {
      if (!dateString) return '';
      const date = new Date(dateString);
      return date.toLocaleDateString('de-DE');
    }
  }
};
</script>

<style scoped>
.document-upload {
  margin: 20px 0;
}

.upload-area {
  border: 2px dashed #ccc;
  border-radius: 8px;
  padding: 12px 16px;
  text-align: center;
  transition: all 0.3s ease;
  background-color: #f9f9f9;
}

.upload-area.drag-over {
  border-color: #007bff;
  background-color: #e3f2fd;
}

.upload-content {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 12px;
}

.upload-text-container {
  display: flex;
  align-items: center;
  gap: 8px;
}

.upload-icon-small {
  width: 20px;
  height: 20px;
  color: #666;
}

.upload-text-wrapper {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.upload-text-inline {
  font-size: 14px;
  font-weight: 600;
  color: #333;
  margin: 0;
}

.upload-text-subtitle {
  font-size: 12px;
  font-weight: 400;
  color: #666;
  margin: 0;
  margin-top: 2px;
}

.file-input {
  display: none;
}

.upload-button-compact {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 8px 16px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  font-weight: 500;
  transition: background-color 0.3s ease;
}

.upload-button-compact:hover {
  background-color: #0056b3;
}

.upload-progress {
  padding: 20px;
}

.progress-bar {
  width: 100%;
  height: 8px;
  background-color: #e9ecef;
  border-radius: 4px;
  overflow: hidden;
  margin-bottom: 10px;
}

.progress-fill {
  height: 100%;
  background-color: #007bff;
  transition: width 0.3s ease;
}


</style>
