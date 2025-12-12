<template>
  <div v-if="entityData" class="relative">
              <!-- Hauptinhalt -->
     <div class="flex flex-col" :style="showPreview ? { paddingRight: '560px', transition: 'padding-right 0.3s ease-in-out' } : { transition: 'padding-right 0.3s ease-in-out' }">
       <!-- Header-Bereich - volle Breite -->
       <div class="flex items-center justify-between -mt-8 -mx-12 py-8 px-12 bg-gray-100 sticky top-16 z-10" :style="showPreview ? { marginRight: '-560px' } : {}">
        <div class="flex-auto">
          <h1 class="text-xl font-semibold text-gray-900">{{ getTitle() }}</h1>
        </div>
        <div class="flex flex-row space-x-4">
          <Button
            :text="$t('entity.action.back')"
            type="secondary"
            @click="goBack"
          />
          <Button
            :text="showPreview ? 'Vorschau ausblenden' : 'Vorschau anzeigen'"
            type="secondary"
            @click="togglePreview"
          />
          <Button
            :text="$t('entity.action.edit')"
            @click="goToEdit"
          />
        </div>
      </div>

      <!-- Kategorie-Abschnitt -->
      <div v-if="!isSingleGroupType(category?.entityName)">
        <FormPanel
          :title="getCategoryTitle()"
          bg-color="bg-white"
        >
          <template v-slot:content>
            <div class="md:grid md:grid-cols-3 md:gap-6">
              <s-input
                v-if="!isSelectGroupType(category?.entityName)"
                name="subCategory"
                type="text"
                :model-value="getSubCategoryName()"
                :label="$t(`jaynaApp.baseOverview.subCategory`)"
                :disabled="true"
              />
              <s-input
                v-if="!isSelectGroupType(category?.entityName)"
                name="subCategoryGroup"
                type="text"
                :model-value="getSubCategoryGroupName()"
                :label="$t('jaynaApp.baseOverview.subCategoryGroup')"
                :disabled="true"
              />
              <s-input
                name="groupType"
                type="text"
                :model-value="getGroupTypeName()"
                :label="$t('jaynaApp.baseOverview.groupType')"
                :disabled="true"
              />
            </div>
          </template>
        </FormPanel>

        <Divider />
      </div>

      <FormBuilder
        v-if="formConfig"
        :form-config="formConfig"
        :entity-name="'document'"
        :form-name="generatedFormName"
        :group-type="groupType"
        :data="entityData"
      />

      <!-- Dateien-Abschnitt -->
      <div class="mt-4">
        <Divider />
        <div class="flex flex-col space-y-4">
          <div class="flex justify-between items-center">
            <h3 class="text-lg font-medium leading-6 text-gray-900">Dateien</h3>
          </div>
          
          <!-- Dateien-Liste -->
          <div class="bg-white rounded-lg shadow">
            <div class="overflow-x-auto">
              <table class="min-w-full divide-y divide-gray-200">
                <thead class="bg-gray-50">
                  <tr>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Nr.
                    </th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Dateiname
                    </th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Größe
                    </th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Typ
                    </th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Upload-Datum
                    </th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Aktionen
                    </th>
                  </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200">
                  <tr v-if="uploadedFiles.length === 0">
                    <td colspan="6" class="px-6 py-4 text-center text-sm text-gray-500">
                      Keine Dateien vorhanden
                    </td>
                  </tr>
                  <tr v-for="(file, index) in uploadedFiles" :key="file.fileName" class="hover:bg-gray-50">
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ index + 1 }}</td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                      {{ file.fileName || file.name }}
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                      {{ formatFileSize(file.fileSize || file.size) }}
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                      {{ getFileTypeDisplay(file.fileType || file.type) }}
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                      {{ formatDate(file.uploadDate) }}
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                      <div class="flex items-center gap-6">
                        <button @click="handleViewFile(file)" title="Anzeigen">
                          <svg class="w-4 h-4 text-gray-700 hover:text-gray-900" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.477 0 8.268 2.943 9.542 7-1.274 4.057-5.065 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
                          </svg>
                        </button>
                        <button @click="handleDownloadFile(file)" title="Download">
                          <svg class="w-4 h-4 text-gray-700 hover:text-gray-900" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16v2a2 2 0 002 2h12a2 2 0 002-2v-2M8 12l4 4m0 0l4-4m-4 4V4" />
                          </svg>
                        </button>
                        
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>

      <!-- Kompakter Meta-Abschnitt unten -->
      <div class="mt-4">
        <Divider />
        <MetaInline
          :created-date="entityData.createdDate"
          :created-by="entityData.createdBy"
          :last-modified-date="entityData.lastModifiedDate"
          :last-modified-by="entityData.lastModifiedBy"
          :form-name="generatedFormName || (groupType && groupType.formName) || null"
        />
      </div>
    </div>
    
         <!-- Rechte Sidebar für Vorschau -->
     <div 
       v-if="showPreview" 
       class="fixed right-0 bg-white shadow-lg border-l border-gray-200 z-50 overflow-hidden"
       style="top: 166px; height: calc(100vh - 166px); width: 560px;"
     >
      <div class="p-6 h-full overflow-hidden">
        <DocumentPreview ref="previewRef" :files="uploadedFiles" @close="togglePreview" />
      </div>
    </div>
  </div>
  <div v-else class="d-flex justify-content-center">
    <div class="spinner-border" role="status">
      <span class="visually-hidden">Loading...</span>
    </div>
  </div>
</template>

<script setup>

import { ref, computed, watch, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useI18n } from 'vue-i18n';
import { useStore } from 'vuex';

import AlertService from '@/services/alert.service';
import DocumentService from '@/services/document.service';
import MetaInline from '@/components/MetaInline.vue';
import FormBuilder from '@/components/FormBuilder.vue';
// import EntityDetailsLayout from "@/core/layouts/entity-details-layout.vue";
import { getCorrectFormName } from '@/composables/form-name-generator';
import { isSingleGroupType, isSelectGroupType } from '@/composables/render-utils';
import SInput from '@/components/forms/input.vue';
import FormPanel from '@/components/formpanel.vue';
import Divider from '@/components/divider.vue';
import Button from '@/components/buttons/button.vue';
import DocumentPreview from '@/entities/document/components/document-preview.vue';

/** data */
const { t, te } = useI18n();
const route = useRoute();
const router = useRouter();
const store = useStore();
const formConfig = ref(null);
const entityData = ref(null);
const documentService = new DocumentService();

const category = ref(null);
const subCategory = ref(null);
const subCategoryGroup = ref(null);
const groupType = ref(null);
const generatedFormName = ref(null);
const showPreview = ref(false);
const uploadedFiles = ref([]);
const previewRef = ref(null);

const editRouterParams = computed(() => {
  return {
    entityid: entityData.value?.id
  }
});

const handleRelationSaved = (updatedEntity) => {
  entityData.value = updatedEntity;
  
  if (updatedEntity.groupType) {
    groupType.value = updatedEntity.groupType;
    subCategoryGroup.value = updatedEntity.groupType.subCategoryGroup;
    subCategory.value = updatedEntity.groupType.subCategoryGroup?.subCategory;
    category.value = updatedEntity.groupType.subCategoryGroup?.subCategory?.category;
  }
};

// Hilfsfunktionen für die Anzeige der Feldwerte
const getSubCategoryDisplayName = (subCategory) => {
  if (!subCategory) return '-';
  if (subCategory.translationKey) {
    const translation = t(subCategory.translationKey);
    if (translation !== subCategory.translationKey) return translation;
  }
  return subCategory.name || '-';
};

const getSubCategoryGroupDisplayName = (subCategoryGroup) => {
  if (!subCategoryGroup) return '-';
  if (subCategoryGroup.translationKey) {
    const translation = t(subCategoryGroup.translationKey);
    if (translation !== subCategoryGroup.translationKey) return translation;
  }
  return subCategoryGroup.name || '-';
};

const getGroupTypeDisplayName = (groupType) => {
  if (!groupType) return '-';
  if (groupType.translationKey) {
    const translation = t(groupType.translationKey);
    if (translation !== groupType.translationKey) return translation;
  }
  return groupType.name || '-';
};

const getSubCategoryName = () => {
  return getSubCategoryDisplayName(subCategory.value);
};

const getSubCategoryGroupName = () => {
  return getSubCategoryGroupDisplayName(subCategoryGroup.value);
};

const getGroupTypeName = () => {
  return getGroupTypeDisplayName(groupType.value);
};

const getCategoryTitle = () => {
  return 'Kategorie';
};

const loadEntityData = async () => {
  if (route.params.entityid) {
    const entityId = parseInt(route.params.entityid);
    if (isNaN(entityId) || entityId <= 0) {
      AlertService.showError('Ungültige Entity-ID');
      return;
    }
    
    try {
      // Verwende DocumentService mit relations=true
      const response = await documentService.find(entityId, true);
      
      if (!response || !response.id) {
        throw new Error('Entity not found');
      }
      
      entityData.value = response;
      await refreshFilesFromServer();
      if (uploadedFiles.value && uploadedFiles.value.length > 0) {
        try {
          await handleViewFile(uploadedFiles.value[0]);
        } catch (e) {
          showPreview.value = true;
        }
      } else {
        showPreview.value = false;
      }
       
      if (response.groupType) {
        groupType.value = response.groupType;
        
        // Set category hierarchy with fallbacks
        if (response.groupType.subCategoryGroup) {
          subCategoryGroup.value = response.groupType.subCategoryGroup;
          
          if (response.groupType.subCategoryGroup.subCategory) {
            subCategory.value = response.groupType.subCategoryGroup.subCategory;
            
            if (response.groupType.subCategoryGroup.subCategory.category) {
              category.value = response.groupType.subCategoryGroup.subCategory.category;
            }
          }
        }
        
        // Verwende die intelligente getCorrectFormName Funktion
        const formNameToLoad = await getCorrectFormName(groupType.value, 'document');
        
        if (formNameToLoad) {
          groupType.value.formName = formNameToLoad;
          generatedFormName.value = formNameToLoad;
          const formData = await import(`@/shared/form-definitions/document/${formNameToLoad}.json`);
          formConfig.value = formData.default || formData;
        } else {
          const fallbackFormData = await import(`@/shared/form-definitions/document/document-basic.json`);
          formConfig.value = fallbackFormData.default || fallbackFormData;
        }
      } else {
        // Verwende Fallback-Formulardefinition für Dokumente
        const formData = await import(`@/shared/form-definitions/document/document-basic.json`);
        formConfig.value = formData.default || formData;
      }
    } catch (err) {
      console.error('Error loading entity data:', err);
      AlertService.showHttpError(this, err.response);
    }
  }
};

// Props definieren
const props = defineProps({
  entityid: {
    type: [String, Number],
    required: false
  }
});

// Normalisiert Namen für Datei-Namenskonvention (kebab-case)
const normalizeName = (name, type = 'default') => {
  if (!name) return '';
  
  // Konvertiere zu kebab-case und normalisiere
  return name
    .toLowerCase()
    .replace(/\s+/g, '-') // Leerzeichen zu Bindestrichen
    .replace(/[^a-z0-9-]/g, '') // Entferne Sonderzeichen
    .replace(/-+/g, '-') // Mehrere Bindestriche zu einem
    .replace(/^-|-$/g, ''); // Entferne führende/abschließende Bindestriche
};

onMounted(() => {
  loadEntityData();
});

// Watch für Route-Änderungen, um neue Entity-Daten zu laden
watch(() => route.params.entityid, (newEntityId, oldEntityId) => {
  if (newEntityId && newEntityId !== oldEntityId) {
    loadEntityData();
  }
});

const getTitle = () => {
  if (!groupType.value) return 'Dokument';
  
  // Verwende spezifischen Titel aus der Formulardefinition, falls verfügbar
  if (groupType.value?.formName) {
    const specificTitleKey = `jaynaApp.${groupType.value.formName}.viewTitle`;
    const specificTitle = t(specificTitleKey);
    if (specificTitle !== specificTitleKey) {
      return specificTitle;
    }
  }
  
  // Fallback: Verwende nur den GroupType-Namen für den Titel
  return groupType.value.name || 'Dokument';
};

const getBackButtonPath = () => {
  return '/document';
};

const getEditRouterName = () => {
  return 'DocumentUpdate';
};

const togglePreview = () => {
  showPreview.value = !showPreview.value;
};

const goBack = () => {
  router.push(getBackButtonPath());
};

const goToEdit = () => {
  router.push({ name: getEditRouterName(), params: editRouterParams.value });
};

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

const formatFileSize = (bytes) => {
  if (!bytes) return '0 B';
  const k = 1024;
  const sizes = ['B', 'KB', 'MB', 'GB'];
  const i = Math.floor(Math.log(bytes) / Math.log(k));
  return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
};

const formatDate = (dateString) => {
  if (!dateString) return '-';
  const date = new Date(dateString);
  return date.toLocaleDateString('de-DE');
};

const downloadDocument = async () => {
  if (!entityData.value || !entityData.value.id) {
    AlertService.showError('Dokument nicht gefunden');
    return;
  }

  try {
    const response = await documentService.download(entityData.value.id);
    const url = window.URL.createObjectURL(new Blob([response]));
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', entityData.value.fileName || 'document');
    document.body.appendChild(link);
    link.click();
    link.remove();
    window.URL.revokeObjectURL(url);
    AlertService.showSuccess('Dokument erfolgreich heruntergeladen');
  } catch (error) {
    console.error('Download error:', error);
    AlertService.showError('Fehler beim Herunterladen des Dokuments: ' + (error.response?.data?.message || error.message));
  }
};

const handleViewFile = async (file) => {
  showPreview.value = true;
  try {
    const data = file.fileName
      ? await documentService.downloadFile(entityData.value.id, file.fileName)
      : await documentService.download(entityData.value.id);
    const blob = new Blob([data], { type: file.fileType || 'application/pdf' });
    const url = URL.createObjectURL(blob);
    uploadedFiles.value = uploadedFiles.value.map(f =>
      (f.fileName === (file.fileName || file.name)) ? { ...f, previewUrl: url } : f
    );
    previewRef.value?.setPageByName(file.fileName || file.name);
  } catch {}
};

const handleDownloadFile = async (file) => {
  try {
    const response = file.fileName
      ? await documentService.downloadFile(entityData.value.id, file.fileName)
      : await documentService.download(entityData.value.id);
    const url = window.URL.createObjectURL(new Blob([response]));
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', file.fileName || file.name || 'document');
    document.body.appendChild(link);
    link.click();
    link.remove();
    window.URL.revokeObjectURL(url);
    AlertService.showSuccess('Dokument erfolgreich heruntergeladen');
  } catch (error) {
    AlertService.showError('Fehler beim Herunterladen des Dokuments');
  }
};

// Datei für die Vorschau laden und Blob-URL setzen
const buildPreviewFromEntity = async () => {
  if (!entityData.value?.id || !entityData.value?.fileName) return;
  try {
    const data = await documentService.download(entityData.value.id);
    const blob = new Blob([data], { type: entityData.value.fileType || 'application/pdf' });
    const url = URL.createObjectURL(blob);
    if (!uploadedFiles.value?.length) {
      uploadedFiles.value = [{
        id: entityData.value.id,
        fileName: entityData.value.fileName,
        fileType: entityData.value.fileType,
        fileSize: entityData.value.fileSize,
        uploadDate: entityData.value.createdDate,
        previewUrl: url
      }];
    } else {
      uploadedFiles.value = [{ ...uploadedFiles.value[0], previewUrl: url }];
    }
  } catch (e) {
    // Vorschau optional
  }
};

async function refreshFilesFromServer() {
  if (!entityData.value?.id) return;
  try {
    const list = await documentService.listFiles(entityData.value.id);
    uploadedFiles.value = (list || []).map(f => ({
      id: `${entityData.value.id}-${f.fileName}`,
      fileName: f.fileName,
      fileType: f.mimeType,
      fileSize: f.size,
      uploadDate: f.uploadedAt,
      name: f.fileName
    }));
  } catch {}
}

</script>
