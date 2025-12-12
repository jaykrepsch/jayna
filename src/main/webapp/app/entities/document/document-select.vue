<template>
  <div class="relative">
    <!-- Hauptinhalt -->
    <div class="flex flex-col" :style="showPreview ? { paddingRight: '560px', transition: 'padding-right 0.3s ease-in-out' } : { transition: 'padding-right 0.3s ease-in-out' }">
      <!-- Header-Bereich - volle Breite -->
      <div class="flex items-center justify-between -mt-8 -mx-12 py-8 px-12 bg-gray-100 sticky top-16 z-10" :style="showPreview ? { marginRight: '-560px' } : {}">
        <div class="flex-auto">
          <h1 class="text-xl font-semibold text-gray-900">Dokument auswählen</h1>
        </div>
        <div class="flex flex-row space-x-4">
          <Button
            :text="$t('button.cancel')"
            type="secondary"
            @click.prevent="$router.push('/document')"
          />
          <Button
            :text="showPreview ? 'Vorschau ausblenden' : 'Vorschau anzeigen'"
            type="secondary"
            @click="togglePreview"
          />
          <Button
            text="Schnellanlage"
            type="secondary"
            :disabled="!canContinue"
            @click.prevent="quickCreate()"
            title="Dokument automatisch nach besten Gewissen anlegen"
          >
            <template #icon>
              <BoltIcon class="h-4 w-4" />
            </template>
          </Button>
          <Button
            :text="$t('button.continue')"
            type="primary"
            :disabled="!canContinue"
            @click.prevent="goToAnalyze()"
          />
        </div>
      </div>

      <!-- Workflow Steps -->
      <WorkflowSteps :current-step="1" />

      <!-- Abstand zwischen Workflow Steps und Dokument hochladen -->
      <div class="h-6"></div>
      <Divider />

      <!-- Dokument hochladen (kleiner) -->
      <div class="-mx-12 px-12">
        <div class="flex flex-col space-y-4">
          <div class="flex justify-between items-center">
            <h3 class="text-lg font-medium leading-6 text-gray-900">Dateien</h3>
          </div>
          <!-- Dateien-Liste im Stil der Create-View -->
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
                  <tr v-if="!uploadedFiles || uploadedFiles.length === 0">
                    <td colspan="6" class="px-6 py-4 text-center text-sm text-gray-500">Keine Dateien vorhanden</td>
                  </tr>
                  <tr v-else v-for="(file, index) in uploadedFiles" :key="file.id || file.name" class="hover:bg-gray-50">
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ index + 1 }}</td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">{{ file.fileName || file.name }}</td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ formatFileSize(file.fileSize || file.size) }}</td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ getFileTypeDisplay(file.fileType || file.type) }}</td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ file.uploadDate ? new Date(file.uploadDate).toLocaleDateString('de-DE') : '-' }}</td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                      <div class="flex items-center gap-6">
                        <button @click="handleViewFile(file)" title="Anzeigen">
                          <EyeIcon class="w-4 h-4 text-gray-700 hover:text-gray-900" />
                        </button>
                        <button @click="onFileRemoved(file)" title="Löschen">
                          <TrashIcon class="w-4 h-4 text-red-600 hover:text-red-800" />
                        </button>
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>

          <!-- Upload-Bereich -->
          <div class="mt-4 bg-white p-4 rounded-lg border">
            <DocumentUpload 
              v-model="uploadedFiles"
              @file-uploaded="onFileUploaded" 
              @file-removed="onFileRemoved"
              @update:modelValue="onFilesUpdated"
            />
          </div>
        </div>
      </div>

      <!-- Abstand zwischen Dokument hochladen und Kategorie -->
      <div class="h-6"></div>

      <!-- Kategorie-Vorschlag (falls Dokument hochgeladen wurde) -->
      <Divider class="mt-6" />
      <div v-if="hasUploadedFile" class="-mx-12 px-12">
        <h2 class="text-lg font-semibold text-gray-900 mb-4">Vorgeschlagene Kategorie</h2>
        <div class="bg-white p-4 rounded-lg border">
          <div v-if="hasCategorySuggestion" class="space-y-4">
                          <div class="flex items-center justify-between">
                <div>
                  <p class="text-sm font-bold text-gray-900">
                    {{ suggestedCategory.mainCategory }} → {{ suggestedCategory.grouping }} → {{ suggestedCategory.documentType }}
                  </p>
                  <p class="text-xs text-gray-500 mt-1">
                    Vertrauen: {{ getConfidenceText(suggestedCategory.confidence) }}
                  </p>
                </div>
              <div class="flex space-x-2">
                <Button
                  text="Vorschlag ablehnen"
                  type="secondary"
                  @click="rejectCategorySuggestion()"
                />
                <Button
                  text="Vorschlag annehmen"
                  type="primary"
                  :disabled="!hasCategorySuggestion || suggestedCategory.confidence === 'analyzing'"
                  @click="acceptCategorySuggestion()"
                />
              </div>
            </div>
          </div>
          <div v-else class="text-center py-4 text-gray-500">
            Kein Vorschlag verfügbar
          </div>
        </div>
      </div>

      <!-- Abstand zwischen Kategorie-Vorschlag und manueller Auswahl -->
      <div class="h-6"></div>

      <!-- Manuelle Kategorie-Auswahl -->
      <div class="-mx-12 px-12">
        <h2 class="text-lg font-semibold text-gray-900 mb-6">Kategorie auswählen</h2>
        <div class="bg-white p-6 rounded-lg border">
          <div class="md:grid md:grid-cols-3 md:gap-6">
            <div :class="{ 'confirmed-dropdown': confirmedDropdowns.mainCategory }">
              <InputDropdown
                v-model="selectedMainCategory"
                label="Hauptkategorie"
                :box-label="$t('global.menu.choose')"
                :items="mainCategories"
                :auto-open="true"
              />
            </div>

            <div :class="{ 'confirmed-dropdown': confirmedDropdowns.grouping }">
              <InputDropdown
                v-model="selectedGrouping"
                label="Gruppierung"
                :box-label="$t('global.menu.choose')"
                :items="groupings"
                :auto-open="true"
              />
            </div>

            <div :class="{ 'confirmed-dropdown': confirmedDropdowns.documentType }">
              <InputDropdown
                v-model="selectedDocumentType"
                label="Dokumententyp"
                :box-label="$t('global.menu.choose')"
                :items="documentTypes"
                :auto-open="true"
              />
            </div>
          </div>
        </div>
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
</template>

<script setup>
import { ref, watch, computed, onMounted } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRouter, useRoute } from 'vue-router';

import Button from '@/components/buttons/button.vue';
import InputDropdown from '@/components/forms/InputDropdown.vue';
import DocumentUpload from '@/entities/document/components/document-upload.vue';
import WorkflowSteps from '@/entities/document/components/workflow-steps.vue';
import DocumentPreview from '@/entities/document/components/document-preview.vue';
import { BoltIcon, EyeIcon, TrashIcon } from '@heroicons/vue/24/outline';

// Services für dynamische Kategorie-Ladung
import CategoryService from '@/services/category.service';
import SubCategoryService from '@/services/sub-category.service';
import SubCategoryGroupService from '@/services/sub-category-group.service';
import GroupTypeService from '@/services/group-type.service';
import AlertService from '@/services/alert.service';
import DocumentTempStorageService from '@/services/document-temp-storage.service';
import Divider from '@/components/divider.vue';

const { t } = useI18n();
const router = useRouter();
const route = useRoute();

// Dynamische Kategorie-Daten aus der Datenbank
const mainCategories = ref([]);
const groupings = ref([]);
const documentTypes = ref([]);

// Ausgewählte Kategorien
const selectedMainCategory = ref(null);
const selectedGrouping = ref(null);
const selectedDocumentType = ref(null);
const initializingFromQuery = ref(false);

// Kategorie-Vorschlag basierend auf Dokument-Upload
const suggestedCategory = ref({
  mainCategory: '',
  grouping: '',
  documentType: '',
  confidence: 'none'
});

// Bestätigungs-Status für Kategorie-Vorschlag
const confirmedActions = ref({
  category: false
});

// Status für bestätigte Dropdown-Felder
const confirmedDropdowns = ref({
  mainCategory: false,
  grouping: false,
  documentType: false
});

// Flag um zu verhindern, dass Watcher bei automatischer Befüllung ausgelöst werden
const isAutoFilling = ref(false);

// Track if files were uploaded
const hasUploadedFile = ref(false);
const uploadedFiles = ref([]);

// Vorschau-Funktionalität
const showPreview = ref(false);
const previewRef = ref(null);

const togglePreview = () => {
  showPreview.value = !showPreview.value;
};

// Automatisch Vorschau öffnen wenn Dokument hochgeladen wird
watch(uploadedFiles, (newFiles) => {
  if (newFiles && newFiles.length > 0) {
    showPreview.value = true;
  } else {
    showPreview.value = false;
  }
}, { deep: true });

// Check if we have category suggestion
const hasCategorySuggestion = computed(() => {
  return suggestedCategory.value.mainCategory && 
         suggestedCategory.value.grouping && 
         suggestedCategory.value.documentType &&
         suggestedCategory.value.confidence !== 'none';
});

const canContinue = computed(() => {
  // Wenn es (noch) keine Kategorien in der DB gibt, erlaube Fortfahren mit mindestens einer hochgeladenen Datei
  if (Array.isArray(mainCategories.value) && mainCategories.value.length === 0) {
    return uploadedFiles.value && uploadedFiles.value.length > 0;
  }

  // Bei confidence 'none' kann der Benutzer trotzdem fortfahren, da er manuell wählen kann
  if (suggestedCategory.value.confidence === 'none') {
    return !!(selectedMainCategory.value && selectedGrouping.value && selectedDocumentType.value);
  }

  return !!(selectedMainCategory.value && selectedGrouping.value && selectedDocumentType.value);
});

// Lade Kategorien aus der Datenbank
const loadCategoriesFromDatabase = async () => {
  try {
    // 1) Kategorien laden und "Dokumente" dynamisch ermitteln
    const categories = await CategoryService.retrieve();
    const documentCategory = Array.isArray(categories)
      ? categories.find(c => c.entityName === 'document' || c.translationKey === 'document' || (c.name || '').toLowerCase().includes('dokument'))
      : null;

    if (!documentCategory || !documentCategory.id) {
      console.error('Dokumenten-Kategorie nicht gefunden');
      AlertService.showError('Dokumenten-Kategorie nicht gefunden');
      mainCategories.value = [];
      return;
    }

    // 2) Sub-Kategorien (Hauptkategorien) für die ermittelte Kategorie laden
    const subCategories = await SubCategoryService.findByParentId(documentCategory.id);
    mainCategories.value = (subCategories || []).map(subCat => ({
      id: subCat.id,
      name: subCat.name,
      translationKey: subCat.translationKey
    }));

    // debug: Kategorien geladen
  } catch (error) {
    console.error('Fehler beim Laden der Kategorien:', error);
    AlertService.showError('Fehler beim Laden der Kategorien');
  }
};

// Lade Gruppierungen basierend auf ausgewählter Hauptkategorie
const loadGroupings = async (mainCategoryId) => {
  try {
    const subCategoryGroups = await SubCategoryGroupService.findByParentId(mainCategoryId);
    groupings.value = subCategoryGroups.map(group => ({
      id: group.id,
      name: group.name,
      translationKey: group.translationKey
    }));
    // debug
  } catch (error) {
    console.error('Fehler beim Laden der Gruppierungen:', error);
    groupings.value = [];
  }
};

// Lade Dokumententypen basierend auf ausgewählter Gruppierung
const loadDocumentTypes = async (subCategoryGroupId) => {
  try {
    const groupTypes = await GroupTypeService.findByParentId(subCategoryGroupId);
    documentTypes.value = groupTypes.map(type => ({
      id: type.id,
      name: type.name,
      translationKey: type.translationKey,
      formName: type.formName
    }));
    // debug
  } catch (error) {
    console.error('Fehler beim Laden der Dokumententypen:', error);
    documentTypes.value = [];
  }
};

// Watcher für dynamische Kategorie-Ladung
watch(selectedMainCategory, async (newValue, oldValue) => {
  // debug
  
  if (initializingFromQuery.value || isAutoFilling.value) {
    // debug
    return;
  }
  
  // Nur bei echten Benutzeränderungen (nicht bei initialer Zuweisung von null zu Wert)
  if (oldValue && newValue && oldValue.id === newValue.id) {
    // debug
    return;
  }
  
  selectedGrouping.value = null;
  selectedDocumentType.value = null;
  groupings.value = [];
  documentTypes.value = [];
  
  // Reset Bestätigung bei Änderung
  if (confirmedActions.value.category) {
    // debug
    confirmedActions.value.category = false;
    confirmedDropdowns.value.mainCategory = false;
    confirmedDropdowns.value.grouping = false;
    confirmedDropdowns.value.documentType = false;
  }
  
  if (newValue && newValue.id) {
    await loadGroupings(newValue.id);
  }
  
  // Speichere temporäre Daten bei Änderungen
  saveTempData();
});

watch(selectedGrouping, async (newValue, oldValue) => {
  // debug
  
  if (initializingFromQuery.value || isAutoFilling.value) {
    // debug
    return;
  }
  
  // Nur bei echten Benutzeränderungen (nicht bei initialer Zuweisung von null zu Wert)
  if (oldValue && newValue && oldValue.id === newValue.id) {
    // debug
    return;
  }
  
  selectedDocumentType.value = null;
  documentTypes.value = [];
  
  // Reset Bestätigung bei Änderung
  if (confirmedActions.value.category) {
    // debug
    confirmedActions.value.category = false;
    confirmedDropdowns.value.mainCategory = false;
    confirmedDropdowns.value.grouping = false;
    confirmedDropdowns.value.documentType = false;
  }
  
  if (newValue && newValue.id) {
    await loadDocumentTypes(newValue.id);
  }
  
  // Speichere temporäre Daten bei Änderungen
  saveTempData();
});

watch(selectedDocumentType, (newValue, oldValue) => {
  // debug
  
  if (initializingFromQuery.value || isAutoFilling.value) {
    // debug
    return;
  }
  
  // Nur bei echten Benutzeränderungen (nicht bei initialer Zuweisung von null zu Wert)
  if (oldValue && newValue && oldValue.id === newValue.id) {
    // debug
    return;
  }
  
  // Reset Bestätigung bei Änderung
  if (confirmedActions.value.category) {
    // debug
    confirmedActions.value.category = false;
    confirmedDropdowns.value.mainCategory = false;
    confirmedDropdowns.value.grouping = false;
    confirmedDropdowns.value.documentType = false;
  }
  
  // Speichere temporäre Daten bei Änderungen
  saveTempData();
});

// Beim Öffnen: aus Query-Parametern initialisieren, damit Zurück aus Analyze Werte wiederherstellt
async function initializeFromQuery() {
  initializingFromQuery.value = true;
  
  const { main, group, type, files } = route.query || {};
  
  // Stelle Dateien wieder her, falls vorhanden
  if (files && files !== '[]' && files !== 'null') {
    try {
      const filesData = JSON.parse(files);
      
      // Prüfe ob filesData ein Array ist und nicht leer
      if (Array.isArray(filesData) && filesData.length > 0) {
        uploadedFiles.value = filesData;
        hasUploadedFile.value = true;
        // debug
        
        // Nur wenn Dateien vorhanden sind, setze auch die Kategorien aus Query-Parametern
        if (main && group && type) {
          // Warte auf das Laden der Hauptkategorien
          await loadCategoriesFromDatabase();
          
          // Setze die ausgewählten Kategorien basierend auf den Query-Parametern
          selectedMainCategory.value = mainCategories.value.find(cat => cat.id === parseInt(main));
          if (selectedMainCategory.value) {
            await loadGroupings(selectedMainCategory.value.id);
            selectedGrouping.value = groupings.value.find(grp => grp.id === parseInt(group));
            if (selectedGrouping.value) {
              await loadDocumentTypes(selectedGrouping.value.id);
              selectedDocumentType.value = documentTypes.value.find(typ => typ.id === parseInt(type));
            }
          }
        }
      } else {
        // debug
        uploadedFiles.value = [];
        hasUploadedFile.value = false;
      }
    } catch (error) {
      console.error('Fehler beim Wiederherstellen der Dateien:', error);
      uploadedFiles.value = [];
      hasUploadedFile.value = false;
    }
  } else {
    // debug
    uploadedFiles.value = [];
    hasUploadedFile.value = false;
    
    // Wenn keine Dateien vorhanden sind, setze auch keine Kategorien voraus
    selectedMainCategory.value = null;
    selectedGrouping.value = null;
    selectedDocumentType.value = null;
  }
  
  initializingFromQuery.value = false;
}

onMounted(async () => {
  await loadCategoriesFromDatabase();
  await initializeFromQuery();
  
  // Lade temporäre Daten nur wenn keine Query-Parameter vorhanden sind
  if (!route.query.files) {
    loadTempData();
  } else {
    // Wenn Dateien aus Query-Parametern geladen wurden (z.B. beim Zurücknavigieren von analyze),
    // starte automatisch eine neue Analyse
    // debug
    setTimeout(() => {
      if (uploadedFiles.value.length > 0) {
        // debug
        // Erstelle ein File-Objekt aus den vorhandenen Daten für die Analyse
        const fileForAnalysis = {
          name: uploadedFiles.value[0].fileName,
          size: uploadedFiles.value[0].fileSize,
          type: uploadedFiles.value[0].fileType || 'application/pdf'
        };
        analyzeDocument(fileForAnalysis);
      }
    }, 100);
  }
});

// Temporäre Speicherung für den Dokumentenerstellungsprozess
const loadTempData = () => {
  const tempData = DocumentTempStorageService.loadStepData('select');
  if (tempData) {
    // debug
    
    // Prüfe ob es sich um ein neues Dokument handelt (keine Query-Parameter)
    const isNewDocument = !route.query.main && !route.query.group && !route.query.type && !route.query.files;
    
    if (isNewDocument) {
      // Bei einem neuen Dokument: Lösche alle temporären Daten
      // debug
      DocumentTempStorageService.clearStepData('select');
      DocumentTempStorageService.clearStepData('analyze');
      DocumentTempStorageService.clearStepData('create');
      
      // Setze alles zurück
      uploadedFiles.value = [];
      hasUploadedFile.value = false;
      selectedMainCategory.value = null;
      selectedGrouping.value = null;
      selectedDocumentType.value = null;
      suggestedCategory.value = {
        mainCategory: '',
        grouping: '',
        documentType: '',
        confidence: 'none'
      };
      confirmedActions.value.category = false;
      return;
    }
    
    // Stelle die ausgewählten Kategorien wieder her (nur wenn keine Query-Parameter vorhanden UND Dateien vorhanden)
    if (tempData.selectedMainCategory && !route.query.main && hasUploadedFile.value) {
      selectedMainCategory.value = tempData.selectedMainCategory;
    }
    if (tempData.selectedGrouping && !route.query.group && hasUploadedFile.value) {
      selectedGrouping.value = tempData.selectedGrouping;
    }
    if (tempData.selectedDocumentType && !route.query.type && hasUploadedFile.value) {
      selectedDocumentType.value = tempData.selectedDocumentType;
    }
    
    // Stelle hochgeladene Dateien wieder her (nur wenn keine Query-Parameter vorhanden)
    if (tempData.uploadedFiles && !route.query.files) {
      uploadedFiles.value = tempData.uploadedFiles;
      hasUploadedFile.value = uploadedFiles.value.length > 0;
    } else if (!route.query.files) {
      // Wenn keine temporären Dateien und keine Query-Parameter vorhanden sind, setze alles zurück
      uploadedFiles.value = [];
      hasUploadedFile.value = false;
      
      // Setze auch die Kategorien zurück
      selectedMainCategory.value = null;
      selectedGrouping.value = null;
      selectedDocumentType.value = null;
    }
    
    // Stelle bestätigte Aktionen wieder her
    if (tempData.confirmedActions) {
      confirmedActions.value = tempData.confirmedActions;
    }
    
    // Stelle Kategorie-Vorschlag wieder her (nur wenn Dateien vorhanden)
    if (tempData.suggestedCategory && hasUploadedFile.value) {
      suggestedCategory.value = tempData.suggestedCategory;
    } else {
      // Setze Kategorie-Vorschlag zurück, wenn keine Dateien vorhanden
      suggestedCategory.value = {
        mainCategory: '',
        grouping: '',
        documentType: '',
        confidence: 'none'
      };
    }
  } else {
    // Wenn keine temporären Daten vorhanden sind, setze alles zurück
    uploadedFiles.value = [];
    hasUploadedFile.value = false;
    selectedMainCategory.value = null;
    selectedGrouping.value = null;
    selectedDocumentType.value = null;
    suggestedCategory.value = {
      mainCategory: '',
      grouping: '',
      documentType: '',
      confidence: 'none'
    };
    confirmedActions.value.category = false;
  }
};

const saveTempData = () => {
  const tempData = {
    selectedMainCategory: selectedMainCategory.value,
    selectedGrouping: selectedGrouping.value,
    selectedDocumentType: selectedDocumentType.value,
    uploadedFiles: uploadedFiles.value,
    confirmedActions: confirmedActions.value,
    suggestedCategory: suggestedCategory.value
  };
  
  DocumentTempStorageService.saveStepData('select', tempData);
};

function goToAnalyze() {
  if (!canContinue.value) return;
  
  // debug
  console.log('Selected categories:', {
    main: selectedMainCategory.value,
    group: selectedGrouping.value,
    type: selectedDocumentType.value
  });
  
  const query = {};
  
  // Füge Kategorie-Informationen hinzu, falls vorhanden
  if (selectedMainCategory.value && selectedGrouping.value && selectedDocumentType.value) {
    query.main = selectedMainCategory.value.id;
    query.group = selectedGrouping.value.id;
    query.type = selectedDocumentType.value.id;
    
    // Verwende immer die übersetzten Namen für konsistente Anzeige
    query.mainName = t(selectedMainCategory.value.translationKey) || selectedMainCategory.value.name;
    query.groupName = t(selectedGrouping.value.translationKey) || selectedGrouping.value.name;
    query.typeName = t(selectedDocumentType.value.translationKey) || selectedDocumentType.value.name;
    
    console.log('Query parameters:', {
      main: query.main,
      group: query.group,
      type: query.type,
      mainName: query.mainName,
      groupName: query.groupName,
      typeName: query.typeName
    });
  }
  
  // Füge Dateiinformationen hinzu, falls Dateien hochgeladen wurden
  if (uploadedFiles.value.length > 0) {
    query.files = JSON.stringify(uploadedFiles.value.map(file => ({
      id: file.id,
      fileName: file.name || file.fileName,
      fileSize: file.size || file.fileSize,
      fileType: file.type || file.fileType,
      uploadDate: file.uploadDate || null,
      previewUrl: file.previewUrl || null
    })));
    // debug
  } else {
    // debug
    query.files = JSON.stringify([]);
  }
  
  // Speichere temporäre Daten vor dem Navigieren
  saveTempData();
  
  // debug
  
  router.push({
    name: 'DocumentAnalyze',
    query: query
  });
}

const onFileUploaded = (file) => {
  // debug
  
  // Markiere, dass eine Datei hochgeladen wurde
  hasUploadedFile.value = true;
  
  // Füge Datei zur Liste hinzu, falls sie noch nicht existiert
  const existingFile = uploadedFiles.value.find(f => f.id === file.id);
  if (!existingFile) {
    uploadedFiles.value.push(file);
    // debug
  }
  
  // Starte Dokumentenanalyse mit der ersten Datei (für Kategorie-Vorschlag)
  if (uploadedFiles.value.length === 1) {
    analyzeDocument(file);
  }
  
  // Vorschau auf diese Datei setzen
  showPreview.value = true;
  previewRef.value?.setPageByName(file.fileName || file.name);
  
  // Speichere temporäre Daten nach Datei-Upload
  saveTempData();
};

const onFilesUpdated = (files) => {
  // debug
  // debug
  
  // Wenn Dateien hinzugefügt wurden, markiere dass eine Datei hochgeladen wurde
  if (files.length > 0 && !hasUploadedFile.value) {
    hasUploadedFile.value = true;
    // debug
  }
  
  // Wenn alle Dateien entfernt wurden, setze alles zurück
  if (files.length === 0 && hasUploadedFile.value) {
    hasUploadedFile.value = false;
    // debug
    
    // Setze Kategorie-Vorschlag zurück
    suggestedCategory.value = {
      mainCategory: '',
      grouping: '',
      documentType: '',
      confidence: 'none'
    };
    
    // Setze Bestätigungs-Status zurück
    confirmedActions.value.category = false;
    
    // Setze ausgewählte Kategorien zurück
    selectedMainCategory.value = null;
  }
  
  // Vorschau auf die zuletzt hinzugefügte Datei setzen
  if (files.length > 0) {
    const last = files[files.length - 1];
    showPreview.value = true;
    previewRef.value?.setPageByName(last.fileName || last.name);
  }
  
  // Speichere temporäre Daten nach Datei-Update
  saveTempData();
};

const onFileRemoved = (file) => {
  // debug
  
  // Entferne die Datei aus der Liste
  const index = uploadedFiles.value.findIndex(f => f.id === file.id);
  if (index > -1) {
    uploadedFiles.value.splice(index, 1);
  }
  
  // Wenn alle Dateien entfernt wurden, setze alles zurück
  if (uploadedFiles.value.length === 0) {
    hasUploadedFile.value = false;
    
    // Setze Kategorie-Vorschlag zurück
    suggestedCategory.value = {
      mainCategory: '',
      grouping: '',
      documentType: '',
      confidence: 'none'
    };
    
    // Setze Bestätigungs-Status zurück
    confirmedActions.value.category = false;
    
    // Setze ausgewählte Kategorien zurück
    selectedMainCategory.value = null;
    selectedGrouping.value = null;
    selectedDocumentType.value = null;
    
    // Setze bestätigte Dropdowns zurück
    confirmedDropdowns.value.mainCategory = false;
    confirmedDropdowns.value.grouping = false;
    confirmedDropdowns.value.documentType = false;
    
    // debug
  }
  
  // Speichere temporäre Daten nach Datei-Entfernung
  saveTempData();
};

const analyzeDocument = async (file) => {
  // Zeige Lade-Animation
  suggestedCategory.value = {
    mainCategory: 'Analysiere...',
    grouping: 'Analysiere...',
    documentType: 'Analysiere...',
    confidence: 'analyzing'
  };
  
  try {
    // Simuliere Analyse-Zeit
    await new Promise(resolve => setTimeout(resolve, 2000));
    
    // Erweiterte Dokumentenanalyse basierend auf Dateiname, Typ und Größe
    const analysis = await performDocumentAnalysis(file);
    
    // Setze Vorschlag basierend auf Analyse
    suggestedCategory.value = {
      mainCategory: analysis.mainCategory,
      grouping: analysis.grouping,
      documentType: analysis.documentType,
      confidence: analysis.confidence
    };
    
    // NICHT automatisch bestätigen - der Benutzer soll selbst entscheiden
    // if (analysis.confidence === 'high') {
    //   confirmedActions.value.category = true;
    // }
    
  } catch (error) {
    console.error('Fehler bei der Dokumentenanalyse:', error);
    // Fallback-Vorschlag
    suggestedCategory.value = {
      mainCategory: 'Energie & Versorgung',
      grouping: 'Rechnungen',
      documentType: 'Stromrechnung',
      confidence: 'low'
    };
  }
};

const performDocumentAnalysis = async (file) => {
  // Sicherheitscheck für File-Objekt
  if (!file || !file.name) {
    console.warn('Ungültiges File-Objekt:', file);
    return {
      mainCategory: '',
      grouping: '',
      documentType: '',
      confidence: 'none'
    };
  }
  
  // debug
  
  try {
    // 1. Dokumenttyp direkt basierend auf Dateiname erkennen (robust und schnell)
    const documentType = identifyDocumentTypeFromFileName(file.name);
    // debug
    
    // 2. Simuliere Dokumenteninhalt basierend auf erkanntem Typ
    const documentText = generateSimulatedContent(documentType, file.name);
    // debug
    
    // 3. Kategorie basierend auf erkanntem Dokumenttyp vorschlagen
    const analysis = suggestCategoryFromDocumentType(documentType, documentText);
    
    // debug
    return analysis;
    
  } catch (error) {
    console.error('Fehler bei der Dokumentenanalyse:', error);
    
    // Fallback: Basierend auf Dateiname
    return performFallbackAnalysis(file);
  }
};

// Echte Text-Extraktion aus verschiedenen Dateitypen
const extractTextFromFile = async (file) => {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    
    reader.onload = async (event) => {
      try {
        const arrayBuffer = event.target.result;
        let text = '';
        
        if (file.type === 'application/pdf') {
          // PDF Text-Extraktion
          text = await extractTextFromPDF(arrayBuffer);
        } else if (file.type.startsWith('image/')) {
          // OCR für Bilder
          text = await performOCR(arrayBuffer);
        } else if (file.type.includes('word') || file.type.includes('document')) {
          // Word-Dokumente
          text = await extractTextFromWord(arrayBuffer);
        } else {
          // Fallback: Als Text lesen
          text = new TextDecoder().decode(arrayBuffer);
        }
        
        resolve(text);
      } catch (error) {
        reject(error);
      }
    };
    
    reader.onerror = () => reject(new Error('Fehler beim Lesen der Datei'));
    reader.readAsArrayBuffer(file);
  });
};

// PDF Text-Extraktion (simuliert - in Produktion würde man eine echte PDF-Bibliothek verwenden)
const extractTextFromPDF = async (arrayBuffer) => {
  // Simuliere PDF-Extraktion
  await new Promise(resolve => setTimeout(resolve, 1000));
  
  // In der echten Implementierung würde hier eine PDF-Bibliothek wie pdf.js verwendet
  // Für jetzt: Simuliere verschiedene Dokumenttypen basierend auf zufälliger Auswahl
  const documentTypes = ['SEPA_MANDATE', 'MEDICAL_INVOICE', 'ENERGY_INVOICE', 'INVOICE'];
  const randomType = documentTypes[Math.floor(Math.random() * documentTypes.length)];
  
  switch (randomType) {
    case 'SEPA_MANDATE':
      return `SEPA-Lastschriftmandat
Gläubiger: Kerbeoldies Mainz-Laubenheim e.V.
IBAN: DE89370400440532013000
Kontoinhaber: Jens Krpesch
Datum: 15.08.2024
Zweck: Jahresbeitrag
Gläubiger-Identifikationsnummer: DE24ZZZ00002614948
Mandatsreferenz: Jahresbeitrag - Mitglieds-Nr.`;
      
    case 'MEDICAL_INVOICE':
      return `Arztbrief an die Krankenkasse
Sehr geehrte Damen und Herren,
hiermit bestätige ich die Behandlung von Herrn Jens Krpesch.
Diagnose: Rückenschmerzen
Behandlung: Physiotherapie
Patient: Jens Krpesch
Datum: 15.08.2024
Arzt: Dr. Müller
Praxis: Orthopädische Praxis Mainz`;
      
    case 'ENERGY_INVOICE':
      return `Stromrechnung
Rechnungsnummer: R-123456
Anbieter: Stadtwerke Mainz
Betrag: 89,50 €
Kundennummer: K123456
Rechnungsdatum: 15.08.2024
Zahlungsziel: 14 Tage
Verbrauch: 450 kWh`;
      
    case 'INVOICE':
      return `Rechnung
Rechnungsnummer: R-789012
Anbieter: Büroservice GmbH
Betrag: 125,00 €
Kundennummer: K789012
Rechnungsdatum: 15.08.2024
Zahlungsziel: 14 Tage
Leistung: Büromaterial`;
      
    default:
      return `Unbekanntes Dokument
Dateiname: Dokument.pdf
Dateigröße: ${Math.floor(Math.random() * 1000000)} Bytes`;
  }
};

// OCR für Bilder (simuliert - in Produktion würde man eine echte OCR-API verwenden)
const performOCR = async (arrayBuffer) => {
  // Simuliere OCR-Verarbeitung
  await new Promise(resolve => setTimeout(resolve, 2000));
  
  // In der echten Implementierung würde hier eine OCR-API wie Tesseract oder Google Vision verwendet
  return `SEPA-Mandat
Gläubiger-Identifikationsnummer: DE24ZZZ00002614948
Mandatsreferenz: ${generateRandomReference()}
Kontoinhaber: ${generateRandomName()}
IBAN: ${generateRandomIBAN()}
Zweck: ${generateRandomPurpose()}`;
};

// Word-Dokument Extraktion (simuliert)
const extractTextFromWord = async (arrayBuffer) => {
  await new Promise(resolve => setTimeout(resolve, 500));
  return `Vertragsdokument
Vertragspartner: ${generateRandomCompany()}
Vertragsnummer: ${generateRandomContractNumber()}
Laufzeit: ${generateRandomDuration()}`;
};

// Robuste Dokumenttyp-Erkennung basierend auf Dateiname
const identifyDocumentTypeFromFileName = (fileName) => {
  const lowerFileName = fileName.toLowerCase();
  
  console.log('Analysiere Dateiname für Typ-Erkennung:', lowerFileName);
  
  // SEPA-Mandate (sehr spezifisch)
  if (lowerFileName.includes('sepa') || lowerFileName.includes('mandat') || 
      lowerFileName.includes('lastschrift') || lowerFileName.includes('einzugsermächtigung') ||
      lowerFileName.includes('dauerauftrag') || lowerFileName.includes('jahresbeitrag')) {
    console.log('SEPA-Mandat erkannt aus Dateiname');
    return 'SEPA_MANDATE';
  }
  
  // Arztrechnungen und medizinische Dokumente
  if (lowerFileName.includes('arzt') || lowerFileName.includes('doctor') || 
      lowerFileName.includes('praxis') || lowerFileName.includes('klinik') ||
      lowerFileName.includes('krankenhaus') || lowerFileName.includes('hospital') ||
      lowerFileName.includes('apotheke') || lowerFileName.includes('pharmacy') ||
      lowerFileName.includes('medizin') || lowerFileName.includes('behandlung') ||
      lowerFileName.includes('diagnose') || lowerFileName.includes('diagnosis')) {
    console.log('Medizinisches Dokument erkannt aus Dateiname');
    return 'MEDICAL_INVOICE';
  }
  
  // Energie-Rechnungen
  if (lowerFileName.includes('strom') || lowerFileName.includes('electricity') ||
      lowerFileName.includes('gas') || lowerFileName.includes('heizung') ||
      lowerFileName.includes('wasser') || lowerFileName.includes('energie')) {
    console.log('Energie-Rechnung erkannt aus Dateiname');
    return 'ENERGY_INVOICE';
  }
  
  // Internet/Telefon-Rechnungen
  if (lowerFileName.includes('internet') || lowerFileName.includes('telefon') ||
      lowerFileName.includes('dsl') || lowerFileName.includes('telekom') ||
      lowerFileName.includes('vodafone') || lowerFileName.includes('o2')) {
    console.log('Internet/Telefon-Rechnung erkannt aus Dateiname');
    return 'TELECOM_INVOICE';
  }
  
  // Versicherungsrechnungen
  if (lowerFileName.includes('versicherung') || lowerFileName.includes('insurance') ||
      lowerFileName.includes('police') || lowerFileName.includes('prämie')) {
    console.log('Versicherungsrechnung erkannt aus Dateiname');
    return 'INSURANCE_INVOICE';
  }
  
  // Bankrechnungen
  if (lowerFileName.includes('bank') || lowerFileName.includes('konto') ||
      lowerFileName.includes('girokonto') || lowerFileName.includes('sparkasse') ||
      lowerFileName.includes('volksbank') || lowerFileName.includes('commerzbank')) {
    console.log('Bankrechnung erkannt aus Dateiname');
    return 'BANK_INVOICE';
  }
  
  // Kontoauszüge
  if (lowerFileName.includes('kontoauszug') || lowerFileName.includes('auszug') ||
      lowerFileName.includes('statement')) {
    console.log('Kontoauszug erkannt aus Dateiname');
    return 'BANK_STATEMENT';
  }
  
  // Verträge
  if (lowerFileName.includes('vertrag') || lowerFileName.includes('contract')) {
    console.log('Vertrag erkannt aus Dateiname');
    return 'CONTRACT';
  }
  
  // Allgemeine Rechnungen
  if (lowerFileName.includes('rechnung') || lowerFileName.includes('invoice') ||
      lowerFileName.includes('bill')) {
    console.log('Allgemeine Rechnung erkannt aus Dateiname');
    return 'INVOICE';
  }
  
  // Für IMG_ Dateien (Fotos) - versuche basierend auf Dateigröße und Typ zu erraten
  if (lowerFileName.startsWith('img_') || lowerFileName.startsWith('photo_') || 
      lowerFileName.startsWith('scan_') || lowerFileName.startsWith('dokument_')) {
    console.log('IMG/Scan-Datei erkannt - verwende Standard-Kategorisierung');
    return 'INVOICE'; // Standard für unbekannte Dokumente
  }
  
  console.log('Unbekannter Dokumenttyp aus Dateiname');
  return 'UNKNOWN';
};

// Generiere simulierten Inhalt basierend auf erkanntem Dokumenttyp
const generateSimulatedContent = (documentType, fileName) => {
  console.log('Generiere simulierten Inhalt für Typ:', documentType);
  
  switch (documentType) {
    case 'SEPA_MANDATE':
      return `SEPA-Lastschriftmandat
Gläubiger: Kerbeoldies Mainz-Laubenheim e.V.
IBAN: DE89370400440532013000
Kontoinhaber: Jens Krpesch
Datum: 15.08.2024
Zweck: Jahresbeitrag
Gläubiger-Identifikationsnummer: DE24ZZZ00002614948
Mandatsreferenz: Jahresbeitrag - Mitglieds-Nr.`;
      
    case 'MEDICAL_INVOICE':
      return `Arztbrief an die Krankenkasse
Sehr geehrte Damen und Herren,
hiermit bestätige ich die Behandlung von Herrn Jens Krpesch.
Diagnose: Rückenschmerzen
Behandlung: Physiotherapie
Patient: Jens Krpesch
Datum: 15.08.2024
Arzt: Dr. Müller
Praxis: Orthopädische Praxis Mainz`;
      
    case 'ENERGY_INVOICE':
      return `Stromrechnung
Rechnungsnummer: R-123456
Anbieter: Stadtwerke Mainz
Betrag: 89,50 €
Kundennummer: K123456
Rechnungsdatum: 15.08.2024
Zahlungsziel: 14 Tage
Verbrauch: 450 kWh`;
      
    case 'TELECOM_INVOICE':
      return `Telekom-Rechnung
Rechnungsnummer: T-789012
Anbieter: Deutsche Telekom
Betrag: 45,00 €
Kundennummer: K789012
Rechnungsdatum: 15.08.2024
Zahlungsziel: 14 Tage
Leistung: DSL 100 Mbit/s`;
      
    case 'INSURANCE_INVOICE':
      return `Versicherungsrechnung
Rechnungsnummer: V-345678
Anbieter: Allianz Versicherung
Betrag: 120,00 €
Kundennummer: K345678
Rechnungsdatum: 15.08.2024
Zahlungsziel: 14 Tage
Leistung: Haftpflichtversicherung`;
      
    case 'BANK_INVOICE':
      return `Bankrechnung
Rechnungsnummer: B-901234
Anbieter: Sparkasse Mainz
Betrag: 5,90 €
Kundennummer: K901234
Rechnungsdatum: 15.08.2024
Zahlungsziel: 14 Tage
Leistung: Kontoführungsgebühren`;
      
    case 'BANK_STATEMENT':
      return `Kontoauszug
IBAN: DE89370400440532013000
Zeitraum: 01.01.2024 - 31.01.2024
Bank: Sparkasse Mainz
Kontoinhaber: Jens Krpesch
Anfangsbestand: 1.250,00 €
Endbestand: 1.180,50 €`;
      
    case 'CONTRACT':
      return `Vertragsdokument
Vertragspartner: Büroservice GmbH
Vertragsnummer: CTR-567890
Laufzeit: 12 Monate
Vertragsbeginn: 01.01.2024
Vertragsende: 31.12.2024`;
      
    case 'INVOICE':
    default:
      return `Rechnung
Rechnungsnummer: R-789012
Anbieter: Büroservice GmbH
Betrag: 125,00 €
Kundennummer: K789012
Rechnungsdatum: 15.08.2024
Zahlungsziel: 14 Tage
Leistung: Büromaterial`;
  }
};

// Kategorie-Vorschlag basierend auf erkanntem Dokumenttyp
const suggestCategoryFromDocumentType = (documentType, documentText) => {
  const analysis = {
    mainCategory: '',
    grouping: '',
    documentType: '',
    confidence: 'none'
  };
  
  switch (documentType) {
    case 'SEPA_MANDATE':
      analysis.mainCategory = 'Finanzen & Versicherungen';
      analysis.grouping = 'Bankwesen';
      analysis.documentType = 'SEPA-Mandat';
      analysis.confidence = 'high';
      break;
      
    case 'MEDICAL_INVOICE':
      analysis.mainCategory = 'Gesundheit & Medizin';
      analysis.grouping = 'Rechnungen';
      analysis.documentType = 'Arztrechnung';
      analysis.confidence = 'high';
      break;
      
    case 'ENERGY_INVOICE':
      analysis.mainCategory = 'Energie & Versorgung';
      analysis.grouping = 'Rechnungen';
      analysis.documentType = 'Energierechnung';
      analysis.confidence = 'high';
      break;
      
    case 'TELECOM_INVOICE':
      analysis.mainCategory = 'Energie & Versorgung';
      analysis.grouping = 'Rechnungen';
      analysis.documentType = 'Internet/Telefon-Rechnung';
      analysis.confidence = 'high';
      break;
      
    case 'INSURANCE_INVOICE':
      analysis.mainCategory = 'Finanzen & Versicherungen';
      analysis.grouping = 'Versicherungen';
      analysis.documentType = 'Versicherungsrechnung';
      analysis.confidence = 'high';
      break;
      
    case 'BANK_INVOICE':
      analysis.mainCategory = 'Finanzen & Versicherungen';
      analysis.grouping = 'Bankwesen';
      analysis.documentType = 'Bankrechnung';
      analysis.confidence = 'high';
      break;
      
    case 'INVOICE':
      analysis.mainCategory = 'Finanzen & Versicherungen';
      analysis.grouping = 'Buchhaltung';
      analysis.documentType = 'Rechnung';
      analysis.confidence = 'high';
      break;
      
    case 'BANK_STATEMENT':
      analysis.mainCategory = 'Finanzen & Versicherungen';
      analysis.grouping = 'Bankwesen';
      analysis.documentType = 'Kontoauszug';
      analysis.confidence = 'high';
      break;
      
    case 'CONTRACT':
      analysis.mainCategory = 'Finanzen & Versicherungen';
      analysis.grouping = 'Verträge';
      analysis.documentType = 'Vertrag';
      analysis.confidence = 'high';
      break;
      
    default:
      analysis.confidence = 'none';
  }
  
  return analysis;
};

// Fallback-Analyse basierend auf Dateiname (wenn echte Analyse fehlschlägt)
const performFallbackAnalysis = (file) => {
  const fileName = file.name.toLowerCase();
  const fileExtension = file.name.split('.').pop().toLowerCase();
  const fileSize = file.size || 0;
  
  console.log('Fallback-Analyse basierend auf Dateiname:', fileName);
  
  const analysis = {
    mainCategory: '',
    grouping: '',
    documentType: '',
    confidence: 'none'
  };
  
  // SEPA-Mandate
  if (fileName.includes('sepa') || fileName.includes('mandat') || fileName.includes('lastschrift')) {
    analysis.mainCategory = 'Finanzen & Versicherungen';
    analysis.grouping = 'Bankwesen';
    analysis.documentType = 'SEPA-Mandat';
    analysis.confidence = 'medium';
  }
  // Arztrechnungen
  else if (fileName.includes('arzt') || fileName.includes('doctor') || fileName.includes('praxis') || 
           fileName.includes('klinik') || fileName.includes('krankenhaus') || fileName.includes('hospital') ||
           fileName.includes('apotheke') || fileName.includes('pharmacy') || fileName.includes('medizin')) {
    analysis.mainCategory = 'Gesundheit & Medizin';
    analysis.grouping = 'Rechnungen';
    analysis.documentType = 'Arztrechnung';
    analysis.confidence = 'medium';
  }
  // Energie-Rechnungen
  else if (fileName.includes('strom') || fileName.includes('gas') || fileName.includes('wasser') ||
           fileName.includes('energie') || fileName.includes('heizung')) {
    analysis.mainCategory = 'Energie & Versorgung';
    analysis.grouping = 'Rechnungen';
    analysis.documentType = 'Energierechnung';
    analysis.confidence = 'medium';
  }
  // Internet/Telefon-Rechnungen
  else if (fileName.includes('internet') || fileName.includes('telefon') || fileName.includes('dsl')) {
    analysis.mainCategory = 'Energie & Versorgung';
    analysis.grouping = 'Rechnungen';
    analysis.documentType = 'Internet/Telefon-Rechnung';
    analysis.confidence = 'medium';
  }
  // Allgemeine Rechnungen
  else if (fileName.includes('rechnung') || fileName.includes('invoice') || fileName.includes('bill')) {
    analysis.mainCategory = 'Finanzen & Versicherungen';
    analysis.grouping = 'Buchhaltung';
    analysis.documentType = 'Rechnung';
    analysis.confidence = 'medium';
  }
  // Kontoauszüge
  else if (fileName.includes('konto') || fileName.includes('auszug')) {
    analysis.mainCategory = 'Finanzen & Versicherungen';
    analysis.grouping = 'Bankwesen';
    analysis.documentType = 'Kontoauszug';
    analysis.confidence = 'medium';
  }
  // IMG_ Dateien (Fotos/Scans) - Standard-Kategorisierung
  else if (fileName.startsWith('img_') || fileName.startsWith('photo_') || 
           fileName.startsWith('scan_') || fileName.startsWith('dokument_')) {
    analysis.mainCategory = 'Finanzen & Versicherungen';
    analysis.grouping = 'Buchhaltung';
    analysis.documentType = 'Rechnung';
    analysis.confidence = 'low';
  }
  // PDF-Dateien ohne spezifischen Namen - vorsichtiger Vorschlag
  else if (fileExtension === 'pdf' && fileSize > 0) {
    analysis.mainCategory = 'Finanzen & Versicherungen';
    analysis.grouping = 'Buchhaltung';
    analysis.documentType = 'Rechnung';
    analysis.confidence = 'low';
  }
  
  return analysis;
};

// Hilfsfunktionen für zufällige Daten (für Demo-Zwecke)
const generateRandomCompany = () => {
  const companies = ['Sparkasse Mainz', 'Volksbank Rheinhessen', 'Commerzbank', 'Deutsche Bank', 'Postbank'];
  return companies[Math.floor(Math.random() * companies.length)];
};

const generateRandomIBAN = () => {
  const banks = ['DE89370400440532013000', 'DE27100777770209299700', 'DE12500105170648489890'];
  return banks[Math.floor(Math.random() * banks.length)];
};

const generateRandomName = () => {
  const names = ['Max Mustermann', 'Anna Schmidt', 'Peter Müller', 'Lisa Weber', 'Thomas Fischer'];
  return names[Math.floor(Math.random() * names.length)];
};

const generateRandomDate = () => {
  const dates = ['15.08.2024', '22.09.2024', '03.10.2024', '18.11.2024', '05.12.2024'];
  return dates[Math.floor(Math.random() * dates.length)];
};

const generateRandomPurpose = () => {
  const purposes = ['Mitgliedsbeitrag', 'Versicherungsprämie', 'Miete', 'Stromrechnung', 'Internet'];
  return purposes[Math.floor(Math.random() * purposes.length)];
};

const generateRandomReference = () => {
  return 'REF-' + Math.random().toString(36).substr(2, 8).toUpperCase();
};

const generateRandomContractNumber = () => {
  return 'CTR-' + Math.random().toString(36).substr(2, 6).toUpperCase();
};

const generateRandomDuration = () => {
  const durations = ['12 Monate', '24 Monate', 'Unbefristet', '6 Monate'];
  return durations[Math.floor(Math.random() * durations.length)];
};

const confirmCategory = async (event) => {
  event.preventDefault();
  event.stopPropagation();
  console.log('confirmCategory clicked, current state:', confirmedActions.value.category);
  
  // Setze explizit auf true
  confirmedActions.value.category = true;
  
  // Force reactivity update
  confirmedActions.value = { ...confirmedActions.value };
  
  console.log('confirmCategory new state:', confirmedActions.value.category);
  
  if (confirmedActions.value.category) {
    console.log('Setting dropdowns as confirmed');
    // Markiere alle Dropdown-Felder als bestätigt
    confirmedDropdowns.value.mainCategory = true;
    confirmedDropdowns.value.grouping = true;
    confirmedDropdowns.value.documentType = true;
    
    // Setze Flag für automatische Befüllung
    isAutoFilling.value = true;
    console.log('isAutoFilling set to true');
    
    // Kategorie bestätigt - fülle die Felder automatisch aus
    console.log('Vorgeschlagene Kategorie:', suggestedCategory.value);
    
    // Finde Hauptkategorie basierend auf dem Namen
    const suggestedMainCategory = mainCategories.value.find(cat => 
      cat.name === suggestedCategory.value.mainCategory || 
      t(cat.translationKey) === suggestedCategory.value.mainCategory
    );
    
    if (suggestedMainCategory) {
      console.log('Gefundene Hauptkategorie:', suggestedMainCategory);
      selectedMainCategory.value = suggestedMainCategory;
      
      // Lade Gruppierungen für diese Hauptkategorie
      await loadGroupings(suggestedMainCategory.id);
      
      // Finde Gruppierung basierend auf dem Namen
      const suggestedGrouping = groupings.value.find(grp => 
        grp.name === suggestedCategory.value.grouping || 
        t(grp.translationKey) === suggestedCategory.value.grouping
      );
      
      if (suggestedGrouping) {
        console.log('Gefundene Gruppierung:', suggestedGrouping);
        selectedGrouping.value = suggestedGrouping;
        
        // Lade Dokumententypen für diese Gruppierung
        await loadDocumentTypes(suggestedGrouping.id);
        
        // Finde Dokumententyp basierend auf dem Namen
        const suggestedDocumentType = documentTypes.value.find(typ => 
          typ.name === suggestedCategory.value.documentType || 
          t(typ.translationKey) === suggestedCategory.value.documentType
        );
        
        if (suggestedDocumentType) {
          console.log('Gefundener Dokumententyp:', suggestedDocumentType);
          selectedDocumentType.value = suggestedDocumentType;
        } else {
          console.warn('Dokumententyp nicht gefunden:', suggestedCategory.value.documentType);
        }
      } else {
        console.warn('Gruppierung nicht gefunden:', suggestedCategory.value.grouping);
      }
    } else {
      console.warn('Hauptkategorie nicht gefunden:', suggestedCategory.value.mainCategory);
    }
  }
  
  // Reset Flag nach automatischer Befüllung - mit Verzögerung um Watcher zu vermeiden
  setTimeout(() => {
    isAutoFilling.value = false;
    console.log('isAutoFilling set to false');
  }, 100);
  
  console.log('Kategorie-Status geändert:', confirmedActions.value.category);
};



// Hilfsfunktionen für Dateiliste im Select-Schritt
const formatFileSize = (bytes) => {
  if (!bytes) return '0 B';
  const k = 1024;
  const sizes = ['B', 'KB', 'MB', 'GB'];
  const i = Math.floor(Math.log(bytes) / Math.log(k));
  return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
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

const getConfidenceClass = (confidence) => {
  const classes = {
    high: 'text-green-600 font-medium',
    medium: 'text-yellow-600 font-medium',
    low: 'text-red-600 font-medium',
    analyzing: 'text-blue-600 font-medium',
    none: 'text-gray-600 font-medium'
  };
  return classes[confidence] || 'text-gray-600';
};

const getConfidenceText = (confidence) => {
  switch (confidence) {
    case 'high':
      return 'Hoch';
    case 'medium':
      return 'Mittel';
    case 'low':
      return 'Niedrig';
    case 'analyzing':
      return 'Analysiere...';
    default:
      return 'Kein Vertrauen';
  }
};

const acceptCategorySuggestion = async () => {
  console.log('Kategorie-Vorschlag akzeptiert');
  // Guard: Nur übernehmen, wenn ein gültiger Vorschlag vorliegt
  if (!hasCategorySuggestion.value || suggestedCategory.value.confidence === 'analyzing') {
    AlertService.showInfo('Analyse läuft – Vorschlag noch nicht verfügbar.');
    return;
  }
  confirmedActions.value.category = true;
  confirmedDropdowns.value.mainCategory = true;
  confirmedDropdowns.value.grouping = true;
  confirmedDropdowns.value.documentType = true;
  isAutoFilling.value = true;

  try {
    // 1. Finde und setze Hauptkategorie
    const suggestedMainCategory = mainCategories.value.find(cat => 
      cat.name === suggestedCategory.value.mainCategory || 
      t(cat.translationKey) === suggestedCategory.value.mainCategory
    );
    
    if (suggestedMainCategory) {
      console.log('Setze Hauptkategorie:', suggestedMainCategory.name);
      selectedMainCategory.value = suggestedMainCategory;
      
      // 2. Lade Gruppierungen und warte darauf
      await loadGroupings(suggestedMainCategory.id);
      
      // 3. Finde und setze Gruppierung
      const suggestedGrouping = groupings.value.find(grp => 
        grp.name === suggestedCategory.value.grouping || 
        t(grp.translationKey) === suggestedCategory.value.grouping
      );
      
      if (suggestedGrouping) {
        console.log('Setze Gruppierung:', suggestedGrouping.name);
        selectedGrouping.value = suggestedGrouping;
        
        // 4. Lade Dokumententypen und warte darauf
        await loadDocumentTypes(suggestedGrouping.id);
        
        // 5. Finde und setze Dokumententyp
        const suggestedDocumentType = documentTypes.value.find(typ => 
          typ.name === suggestedCategory.value.documentType || 
          t(typ.translationKey) === suggestedCategory.value.documentType
        );
        
        if (suggestedDocumentType) {
          console.log('Setze Dokumententyp:', suggestedDocumentType.name);
          selectedDocumentType.value = suggestedDocumentType;
        } else {
          console.warn('Dokumententyp nicht gefunden:', suggestedCategory.value.documentType);
        }
      } else {
        console.warn('Gruppierung nicht gefunden:', suggestedCategory.value.grouping);
      }
    } else {
      console.warn('Hauptkategorie nicht gefunden:', suggestedCategory.value.mainCategory);
    }
  } catch (error) {
    console.error('Fehler beim Akzeptieren des Kategorie-Vorschlags:', error);
  }

  // Reset Flag nach automatischer Befüllung - mit Verzögerung um Watcher zu vermeiden
  setTimeout(() => {
    isAutoFilling.value = false;
  }, 100);
};

const rejectCategorySuggestion = () => {
  console.log('Kategorie-Vorschlag abgelehnt');
  confirmedActions.value.category = false;
  confirmedDropdowns.value.mainCategory = false;
  confirmedDropdowns.value.grouping = false;
  confirmedDropdowns.value.documentType = false;
  isAutoFilling.value = false;
  suggestedCategory.value = {
    mainCategory: '',
    grouping: '',
    documentType: '',
    confidence: 'none'
  };
};

const quickCreate = () => {
  // TODO: Implementierung der Schnellanlage
  // Hier wird das Dokument automatisch nach besten Gewissen angelegt
  console.log('Schnellanlage gestartet');
};

const handleViewFile = (file) => {
  showPreview.value = true;
  if (file) {
    previewRef.value?.setPageByName(file.fileName || file.name);
  }
};
</script>

<style scoped>
.confirmed-dropdown {
  position: relative;
}

/* Blaue Hervorhebung für bestätigte Dropdown-Felder */
.confirmed-dropdown :deep(input) {
  border-color: #3b82f6; /* blue-500 */
  background-color: #eff6ff; /* blue-50 */
}

.confirmed-dropdown :deep(input):focus {
  border-color: #2563eb; /* blue-600 */
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1); /* blue-500 with opacity */
}

/* Spezielle Styles für den Häkchen-Button */
.checkmark-button {
  min-width: 40px !important;
  height: 40px !important;
  font-size: 18px !important;
  font-weight: bold !important;
  display: flex !important;
  align-items: center !important;
  justify-content: center !important;
}
</style>


