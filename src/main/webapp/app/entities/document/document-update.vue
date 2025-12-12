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
            :text="'Abbrechen'"
            type="secondary"
            @click="cancelEdit"
          />
          <Button :text="$t('entity.action.delete')" type="secondary" @click="showDeleteConfirm = true" />
          
          <Button
            :text="showPreview ? 'Vorschau ausblenden' : 'Vorschau anzeigen'"
            type="secondary"
            @click="togglePreview"
          />
          <Button :text="$t('entity.action.save')" type="primary" @click="saveForm" />
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

             <!-- FormBuilder für spezifische Dokumentenfelder -->
               <!-- Debug-Info -->
        <div v-if="!formConfig" class="bg-yellow-50 border border-yellow-200 rounded-lg p-4 mb-4">
          <p class="text-yellow-800">Formular wird geladen…</p>
        </div>
       
                        <FormBuilder
           v-if="formConfig"
           :key="formRenderKey"
           ref="formBuilder"
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
                     <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Nr.</th>
                     <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Dateiname</th>
                     <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Größe</th>
                     <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Typ</th>
                     <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Upload-Datum</th>
                     <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Aktionen</th>
                   </tr>
                 </thead>
                 <tbody class="bg-white divide-y divide-gray-200">
                   <tr v-if="uploadedFiles.length === 0">
                     <td colspan="5" class="px-6 py-4 text-center text-sm text-gray-500">
                       Keine Dateien vorhanden
                     </td>
                   </tr>
                   <tr v-for="(file, index) in uploadedFiles" :key="file.id || file.name" class="hover:bg-gray-50">
                     <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                       {{ index + 1 }}
                     </td>
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
                         <button @click="handleFileRemoved(file)" title="Löschen">
                           <svg class="w-4 h-4 text-red-600 hover:text-red-800" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                             <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6M1 7h22M8 7V5a2 2 0 012-2h4a2 2 0 012 2v2" />
                           </svg>
                         </button>
                       </div>
                     </td>
                   </tr>
                 </tbody>
               </table>
             </div>
           </div>
           
           <!-- Upload-Bereich direkt in den Dateien-Abschnitt integriert -->
           <div class="mt-4">
             <DocumentUpload
               :entity-id="entityData?.id"
               :model-value="uploadedFiles"
               :deferred="true"
               @update:model-value="uploadedFiles = $event"
               @file-uploaded="handleFileUploaded"
               @file-removed="handleFileRemoved"
               @download-file="handleDownloadFile"
               @view-file="handleViewFile"
             />
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
    
    <!-- Bestätigungsfenster für Löschen -->
    <DangerConfirmation
      :show="showDeleteConfirm"
      title-key="jaynaApp.document.delete.title"
      content-key="jaynaApp.document.delete.question"
      @close="showDeleteConfirm = false"
      @confirm="confirmDelete"
    >
      <template #default>
        Sind Sie sicher, dass Sie das Dokument löschen möchten?
      </template>
    </DangerConfirmation>
  </div>
</template>

<script setup>

import { ref, computed, watch, onUnmounted, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useI18n } from 'vue-i18n';
import { useStore } from 'vuex';

import AlertService from '@/services/alert.service';
import DocumentService from '@/services/document.service';
import DetailViewBuilder from '@/components/DetailViewBuilder.vue';
import EntityDetailsLayout from "@/core/layouts/entity-details-layout.vue";
import { getCorrectFormName } from '@/composables/form-name-generator';
import { isSingleGroupType, isSelectGroupType } from '@/composables/render-utils';
import SInput from '@/components/forms/input.vue';
import FormPanel from '@/components/formpanel.vue';
import Divider from '@/components/divider.vue';
import Button from '@/components/buttons/button.vue';
import DangerConfirmation from '@/components/modals/danger-confirmation.vue';
import DocumentPreview from '@/entities/document/components/document-preview.vue';
import MetaInline from '@/components/MetaInline.vue';

import EntityUpdateLayout from '@/core/layouts/entity-update-layout.vue';
import { extractEnglishNameFromTranslationKey } from '@/composables/entity-helper';
import FormBuilder from '@/components/FormBuilder.vue';
import DocumentUpload from '@/entities/document/components/document-upload.vue';

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
const formBuilder = ref(null);
const showPreview = ref(false);
const showDeleteConfirm = ref(false);
const showMetadata = ref(false);

const uploadedFiles = ref([]);
const previewRef = ref(null);
const objectUrls = new Set();
const isEditing = ref(false);
const originalEntity = ref(null);
const originalFiles = ref([]);
const pendingNewFiles = ref([]); // für deferred Upload (Roh-Files)
const pendingDeletedFiles = ref([]); // für deferred Delete (Dateinamen)
const formRenderKey = ref(0);

// Lade vorhandene Dateien beim Laden der Entity-Daten
const loadUploadedFiles = () => {
  if (entityData.value?.fileName) {
    uploadedFiles.value = [{
      id: entityData.value.id,
      fileName: entityData.value.fileName,
      fileType: entityData.value.fileType,
      fileSize: entityData.value.fileSize,
      uploadDate: entityData.value.createdDate,
      name: entityData.value.fileName
    }];
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
      console.log('Loading entity data for ID:', entityId);
      // Verwende DocumentService mit relations=true
      const response = await documentService.find(entityId, true);
      
      if (!response || !response.id) {
        throw new Error('Entity not found');
      }
      
      console.log('Entity data loaded:', response);
      entityData.value = response;
      // Server-Dateiliste laden (Mehrfachdateien)
      await refreshFilesFromServer();
      // Originalzustand für Abbrechen sichern
      originalEntity.value = JSON.parse(JSON.stringify(entityData.value));
      originalFiles.value = JSON.parse(JSON.stringify(uploadedFiles.value));
      formRenderKey.value++;
      
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
        console.log('Getting correct form name for groupType:', groupType.value);
        const formNameToLoad = await getCorrectFormName(groupType.value, 'document');
        console.log('Form name to load:', formNameToLoad);
        
        if (formNameToLoad) {
          groupType.value.formName = formNameToLoad;
          generatedFormName.value = formNameToLoad;
          try {
            const formData = await import(`@/shared/form-definitions/document/${formNameToLoad}.json`);
            formConfig.value = formData.default || formData;
          } catch (importError) {
            const fallbackFormData = await import(`@/shared/form-definitions/document/document-basic.json`);
            formConfig.value = fallbackFormData.default || fallbackFormData;
          }
        } else {
          const fallbackFormData = await import(`@/shared/form-definitions/document/document-basic.json`);
          formConfig.value = fallbackFormData.default || fallbackFormData;
        }
      } else {
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

const cancelEdit = async () => {
  // Zustand vollständig zurücksetzen (Form + Dateien)
  if (originalEntity.value) entityData.value = JSON.parse(JSON.stringify(originalEntity.value));
  if (originalFiles.value) uploadedFiles.value = JSON.parse(JSON.stringify(originalFiles.value));
  pendingNewFiles.value = [];
  pendingDeletedFiles.value = [];
  // Formular neu rendern, damit Eingabefelder zurückspringen
  formRenderKey.value++;
  AlertService.showInfo('Änderungen verworfen');
  // Zur Ansicht des Dokuments navigieren
  try {
    const path = getBackButtonPath();
    if (path) router.push(path);
  } catch {}
};

const saveForm = async () => {
  if (!formBuilder.value) {
    AlertService.showError('Formular nicht verfügbar');
    return;
  }

  try {
    const formData = await formBuilder.value.getData();
    
    // Aktualisiere die Entity-Daten mit den Formulardaten
    Object.assign(entityData.value, formData);
    
    // Speichere die aktualisierten Daten
    const updatedEntity = await documentService.update(entityData.value);
    // Erst Löschungen anwenden, dann neue Uploads
    if (pendingDeletedFiles.value.length && updatedEntity?.id) {
      for (const name of pendingDeletedFiles.value) {
        try { await documentService.deleteFileByName(updatedEntity.id, name); } catch {}
      }
    }
    if (pendingNewFiles.value.length && updatedEntity?.id) {
      for (const f of pendingNewFiles.value) {
        try { await documentService.upload(updatedEntity.id, f); } catch {}
      }
    }
    // Server-Dateiliste neu laden und Base-State aktualisieren
    await refreshFilesFromServer();
    originalEntity.value = JSON.parse(JSON.stringify(updatedEntity));
    originalFiles.value = JSON.parse(JSON.stringify(uploadedFiles.value));
    pendingNewFiles.value = [];
    pendingDeletedFiles.value = [];
    
    AlertService.showSuccess('Dokument erfolgreich aktualisiert');
    
    // Zur Übersicht navigieren
    router.push({ name: 'DocumentList' });
  } catch (error) {
    console.error('Fehler beim Speichern:', error);
    AlertService.showError('Fehler beim Speichern des Dokuments');
  }
};

const deleteDocument = () => {
  showDeleteConfirm.value = true;
};

const confirmDelete = async () => {
  if (!entityData.value || !entityData.value.id) {
    AlertService.showError('Dokument nicht gefunden');
    return;
  }

  try {
    console.log('Lösche Dokument mit ID:', entityData.value.id);
    const result = await documentService.delete(entityData.value.id);
    console.log('Löschen erfolgreich:', result);
    
    AlertService.showSuccess('Dokument erfolgreich gelöscht');
    
    // Navigiere zur Dokumente-Übersicht
    router.push({ name: 'DocumentList' });
  } catch (error) {
    console.error('Fehler beim Löschen:', error);
    console.error('Error response:', error.response);
    console.error('Error message:', error.message);
    
    let errorMessage = 'Fehler beim Löschen des Dokuments';
    if (error.response?.data?.message) {
      errorMessage += ': ' + error.response.data.message;
    } else if (error.message) {
      errorMessage += ': ' + error.message;
    }
    
    AlertService.showError(errorMessage);
  } finally {
    showDeleteConfirm.value = false;
  }
};

const cancelDelete = () => {
  showDeleteConfirm.value = false;
};

const togglePreview = () => {
  showPreview.value = !showPreview.value;
};

const handleFileUploaded = async (uploadedFile) => {
  if (!uploadedFile) return;
  // deferred: nicht sofort hochladen, Roh-Datei merken
  if (uploadedFile.__rawFile) {
    pendingNewFiles.value.push(uploadedFile.__rawFile);
  }
  // Vorschau offen halten
  showPreview.value = true;
};

const handleFileRemoved = async (file) => {
  // Nur lokal entfernen; serverseitig erst bei Speichern
  const idx = uploadedFiles.value.findIndex(f => (f.id === file.id) || (f.fileName === file.fileName));
  if (idx !== -1) uploadedFiles.value.splice(idx, 1);
  if (file?.fileName) pendingDeletedFiles.value.push(file.fileName);
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
    AlertService.showSuccess('Datei erfolgreich heruntergeladen');
  } catch (error) {
    console.error('Download error:', error);
    AlertService.showError('Fehler beim Herunterladen der Datei: ' + (error.response?.data?.message || error.message));
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
    // Vorschau-URL nur für die gewählte Datei setzen, Liste beibehalten
    uploadedFiles.value = uploadedFiles.value.map(f =>
      (f.fileName === (file.fileName || file.name)) ? { ...f, previewUrl: url } : f
    );
    // Seite in der Vorschau auf die gewählte Datei setzen
    previewRef.value?.setPageByName(file.fileName || file.name);
  } catch {}
};

const handleAddRelation = () => {
  // Hier können Sie die Logik für das Hinzufügen einer Verknüpfung implementieren
  console.log('Verknüpfung hinzufügen');
  AlertService.showInfo('Verknüpfung hinzufügen - Funktion wird implementiert');
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

const refreshFilesFromServer = async () => {
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
    if (uploadedFiles.value.length > 0) {
      try {
        await handleViewFile(uploadedFiles.value[0]);
      } catch (e) {
        showPreview.value = true;
      }
    }
  } catch {}
};

const goBack = () => {
  router.go(-1);
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

// Vorschau-Datei als Blob laden und URL setzen
const buildPreviewFromEntity = async () => {
  uploadedFiles.value = [];
  if (!entityData.value?.id || !entityData.value?.fileName) return;
  try {
    const data = await documentService.download(entityData.value.id);
    const blob = new Blob([data], { type: entityData.value.fileType || 'application/pdf' });
    const url = URL.createObjectURL(blob);
    objectUrls.add(url);
    uploadedFiles.value = [{
      id: entityData.value.id,
      fileName: entityData.value.fileName,
      fileType: entityData.value.fileType,
      fileSize: entityData.value.fileSize,
      uploadDate: entityData.value.createdDate,
      previewUrl: url
    }];
  } catch (e) {
    // Vorschau optional
  }
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
  if (!groupType.value) return 'Dokument bearbeiten';
  
  // Verwende spezifischen Titel aus der Formulardefinition, falls verfügbar
  if (groupType.value?.formName) {
    const specificTitleKey = `jaynaApp.${groupType.value.formName}.updateTitle`;
    const specificTitle = t(specificTitleKey);
    if (specificTitle !== specificTitleKey) {
      return specificTitle;
    }
  }
  
  // Fallback: Verwende nur den GroupType-Namen für den Titel
  return `${groupType.value.name || 'Dokument'} bearbeiten`;
};

const getBackButtonPath = () => {
  return `/document/view/${route.params.entityid}`;
};

</script>
