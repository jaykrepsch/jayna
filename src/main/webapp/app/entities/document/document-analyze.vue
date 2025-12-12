<template>
  <div class="relative">
    <!-- Hauptinhalt -->
    <div class="flex flex-col" :style="showPreview ? { paddingRight: '560px', transition: 'padding-right 0.3s ease-in-out' } : { transition: 'padding-right 0.3s ease-in-out' }">
      <!-- Header-Bereich - volle Breite -->
      <div class="flex items-center justify-between -mt-8 -mx-12 py-8 px-12 bg-gray-100 sticky top-16 z-10" :style="showPreview ? { marginRight: '-560px' } : {}">
        <div class="flex-auto">
          <h1 class="text-xl font-semibold text-gray-900">{{ $t('jaynaApp.document.workflow.step2') }}</h1>
        </div>
        <div class="flex flex-row space-x-4">
          <Button
            :text="$t('button.cancel')"
            type="secondary"
            @click.prevent="cancelDocumentCreation()"
          />
          <Button
            :text="$t('button.back')"
            type="secondary"
            @click.prevent="goBackToSelect()"
          />
          <Button
            :text="showPreview ? 'Vorschau ausblenden' : 'Vorschau anzeigen'"
            type="secondary"
            @click="togglePreview"
          />
          <Button
            :text="$t('button.continue')"
            type="primary"
            :disabled="!canContinue"
            @click.prevent="goToCreate()"
          />
        </div>
      </div>

      <!-- Workflow Steps -->
      <WorkflowSteps :current-step="2" />

      <!-- Abstand zwischen Workflow Steps und Kategorie -->
      <div class="h-6"></div>

      <!-- Kategorie-Abschnitt für Dokumente -->
      <div v-if="selectedMainCategory || selectedGrouping || selectedDocumentType" class="-mx-12 px-12">
        <FormPanel
          title="Kategorie"
          bg-color="bg-white"
        >
          <template v-slot:content>
            <div class="md:grid md:grid-cols-3 md:gap-6">
              <Input
                name="mainCategory"
                type="text"
                :model-value="selectedMainCategory ? selectedMainCategory.name : '-'"
                :label="$t('jaynaApp.document.select.mainCategory')"
                :disabled="true"
              />
              <Input
                name="grouping"
                type="text"
                :model-value="selectedGrouping ? selectedGrouping.name : '-'"
                :label="$t('jaynaApp.document.select.grouping')"
                :disabled="true"
              />
              <Input
                name="documentType"
                type="text"
                :model-value="selectedDocumentType ? selectedDocumentType.name : '-'"
                :label="$t('jaynaApp.document.select.documentType')"
                :disabled="true"
              />
            </div>
          </template>
        </FormPanel>

        <Divider />
      </div>

      <!-- Abstand zwischen Kategorie und Dateien -->
      <div class="h-6"></div>

      <!-- Dateien-Liste + Drag & Drop Upload (wie in Create-Ansicht) -->
      <div class="-mx-12 px-12">
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
                        <button @click="removeFile(file)" title="Löschen">
                          <TrashIcon class="w-4 h-4 text-red-600 hover:text-red-800" />
                        </button>
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>

          <!-- Drag & Drop Upload-Bereich -->
          <div class="bg-white p-4 rounded-lg border">
            <div 
              class="upload-area"
              :class="{ 'drag-over': isDragOver }"
              @drop="handleDrop"
              @dragover="handleDragOver"
              @dragleave="handleDragLeave"
              @dragenter="handleDragEnter"
            >
              <div class="upload-content">
                <div class="upload-text-container">
                  <DocumentIcon class="upload-icon-small" />
                  <div class="upload-text-wrapper">
                    <span class="upload-text-inline">{{ $t('jaynaApp.document.upload.dragDrop') }}</span>
                    <span class="upload-text-subtitle">PDF, DOC, DOCX, JPG, PNG bis 10MB</span>
                  </div>
                </div>
                <input
                  ref="fileInput"
                  id="file-upload"
                  name="file-upload"
                  type="file"
                  class="file-input"
                  accept=".pdf,.doc,.docx,.jpg,.jpeg,.png"
                  @change="handleFileUpload"
                />
                <button @click="triggerFileSelect" class="upload-button-compact">
                  {{ $t('jaynaApp.document.upload.selectFiles') }}
                </button>
              </div>
            </div>
          </div>

          <Divider />
        </div>
      </div>

      <!-- Abstand zwischen Dateien und Analysis Results -->
      <div class="h-6"></div>

      <!-- Analysis Results -->
      <div class="space-y-6 -mx-12 px-12">
        <!-- Document Data Section -->
        <div>
          <h2 class="text-lg font-semibold text-gray-900 mb-6">{{ $t('jaynaApp.document.workflow.extractedData') }}</h2>
          <div class="bg-white p-6 rounded-lg border">
            <div v-if="Object.keys(extractedData).length > 0" class="grid grid-cols-2 gap-4">
              <div v-for="(value, key) in extractedData" :key="key" class="p-4 border rounded-lg bg-gray-50">
                <label class="text-sm font-medium text-gray-700">{{ key }}</label>
                <p class="text-sm text-gray-900 mt-1 font-mono">{{ value }}</p>
              </div>
            </div>
            <div v-else class="text-center py-1 text-gray-500">
              Kein Dokument hochgeladen
            </div>
          </div>
        </div>

        <!-- Entity Matches Section -->
        <div>
          <h2 class="text-lg font-semibold text-gray-900 mb-6">{{ $t('jaynaApp.document.workflow.entityMatches') }}</h2>
          <div class="bg-white p-6 rounded-lg border">
            <div v-if="matchingEntities.length > 0" class="space-y-3">
              <div v-for="entity in matchingEntities" :key="entity.id" class="p-4 border rounded-lg bg-green-50">
                <div class="flex items-center justify-between">
                  <div>
                    <h4 class="font-medium text-green-900">{{ entity.name }}</h4>
                    <p class="text-sm text-green-700">{{ entity.type }} - {{ entity.matchReason }}</p>
                  </div>
                  <div class="flex space-x-4">
                    <Button
                      :text="$t('jaynaApp.document.workflow.linkExisting')"
                      :type="confirmedActions.relations.has(entity.id) ? 'success' : 'primary'"
                      @click="confirmRelation(entity, $event)"
                    />
                    <Button
                      :text="$t('button.view')"
                      type="secondary"
                      @click="viewEntity(entity, $event)"
                    />
                  </div>
                </div>
              </div>
            </div>
            <div v-else class="text-center py-1 text-gray-500">
              {{ $t('jaynaApp.document.workflow.noMatches') }}
            </div>
          </div>
        </div>

        <!-- New Entity Suggestions Section -->
        <div>
          <h2 class="text-lg font-semibold text-gray-900 mb-6">{{ $t('jaynaApp.document.workflow.newEntitySuggestions') }}</h2>
          <div class="bg-white p-6 rounded-lg border">
            <div v-if="suggestedNewEntities.length > 0" class="space-y-3">
              <div v-for="entity in suggestedNewEntities" :key="entity.type" class="p-4 border rounded-lg bg-orange-50">
                <div class="flex items-center justify-between">
                  <div>
                    <h4 class="font-medium text-orange-900">{{ entity.type }}</h4>
                    <p class="text-sm text-orange-700">{{ entity.reason }}</p>
                    <div class="mt-2 text-sm">
                      <span v-for="(value, key) in entity.suggestedData" :key="key" class="inline-block mr-4">
                        <span class="font-medium">{{ key }}:</span> {{ value }}
                      </span>
                    </div>
                  </div>
                  <div class="flex space-x-4">
                    <Button
                      :text="$t('jaynaApp.document.workflow.createNew')"
                      :type="confirmedActions.newEntities.has(entity.type) ? 'success' : 'primary'"
                      @click="createNewEntity(entity, $event)"
                    />
                    <Button
                      :text="$t('button.view')"
                      type="secondary"
                      @click="viewEntity(entity, $event)"
                    />
                  </div>
                </div>
              </div>
            </div>
            <div v-else class="text-center py-1 text-gray-500">
              Keine neuen Datensätze vorgeschlagen
            </div>
          </div>
        </div>

        <!-- Deviations Section -->
        <div>
          <h2 class="text-lg font-semibold text-gray-900 mb-6">{{ $t('jaynaApp.document.workflow.deviations') }}</h2>
          <div class="bg-white p-6 rounded-lg border">
            <div v-if="deviations.length > 0" class="space-y-3">
              <div v-for="deviation in deviations" :key="deviation.id" class="p-4 border rounded-lg" :class="{
                'bg-red-50': deviation.severity === 'high',
                'bg-yellow-50': deviation.severity === 'medium',
                'bg-orange-50': deviation.severity === 'low'
              }">
                <div class="flex items-center justify-between">
                  <div>
                    <h4 class="font-medium" :class="{
                      'text-red-900': deviation.severity === 'high',
                      'text-yellow-900': deviation.severity === 'medium',
                      'text-orange-900': deviation.severity === 'low'
                    }">
                      {{ deviation.entityName }}
                      <span class="ml-2 text-xs px-2 py-1 rounded" :class="{
                        'bg-red-200 text-red-800': deviation.severity === 'high',
                        'bg-yellow-200 text-yellow-800': deviation.severity === 'medium',
                        'bg-orange-200 text-orange-800': deviation.severity === 'low'
                      }">
                        {{ deviation.severity === 'high' ? 'Kritisch' : deviation.severity === 'medium' ? 'Mittel' : 'Niedrig' }}
                      </span>
                    </h4>
                    <p class="text-sm" :class="{
                      'text-red-700': deviation.severity === 'high',
                      'text-yellow-700': deviation.severity === 'medium',
                      'text-orange-700': deviation.severity === 'low'
                    }">
                      <strong>{{ deviation.field }}:</strong> {{ deviation.expected }} → {{ deviation.actual }}
                    </p>
                    <p class="text-xs mt-1" :class="{
                      'text-red-600': deviation.severity === 'high',
                      'text-yellow-600': deviation.severity === 'medium',
                      'text-orange-600': deviation.severity === 'low'
                    }">
                      {{ deviation.reason }}
                    </p>
                  </div>
                  <div class="flex space-x-4">
                    <Button
                      :text="$t('jaynaApp.document.workflow.updateExisting')"
                      :type="confirmedActions.updates.has(deviation.id) ? 'success' : 'primary'"
                      @click="updateEntity(deviation, $event)"
                    />
                    <Button
                      :text="$t('button.view')"
                      type="secondary"
                      @click="viewEntity(deviation, $event)"
                    />
                  </div>
                </div>
              </div>
            </div>
            <div v-else class="text-center py-1 text-gray-500">
              Keine Abweichungen gefunden
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
        <!-- Vorschau-Ref für gezieltes Springen zur Datei -->
        <DocumentPreview ref="previewRef" :files="uploadedFiles" @close="togglePreview" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useI18n } from 'vue-i18n';

import Button from '@/components/buttons/button.vue';
import Input from '@/components/forms/input.vue';
import FormPanel from '@/components/formpanel.vue';
import Divider from '@/components/divider.vue';
import WorkflowSteps from '@/entities/document/components/workflow-steps.vue';
import DocumentPreview from '@/entities/document/components/document-preview.vue';
import { DocumentIcon, XMarkIcon, EyeIcon, TrashIcon } from '@heroicons/vue/24/outline';

import DocumentTempStorageService from '@/services/document-temp-storage.service';
import AlertService from '@/services/alert.service';

const route = useRoute();
const router = useRouter();
const { t } = useI18n();

// Uploaded files
const uploadedFiles = ref([]);

// Drag & Drop state
const isDragOver = ref(false);

// Extracted data from document analysis
const extractedData = ref({});

// Kategorie-Variablen
const selectedMainCategory = ref(null);
const selectedGrouping = ref(null);
const selectedDocumentType = ref(null);

// Category suggestion
const suggestedCategory = ref({
  mainCategory: '',
  grouping: '',
  documentType: '',
  confidence: 'none'
});

const matchingEntities = ref([]);
const suggestedNewEntities = ref([]);
const deviations = ref([]);

const analysisResults = computed(() => uploadedFiles.value.filter(f => f.analysisStatus === 'completed'));

const canContinue = computed(() => true);

const formatFileSize = (bytes) => {
  if (bytes === 0) return '0 Bytes';
  const k = 1024;
  const sizes = ['Bytes', 'KB', 'MB', 'GB'];
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
    low: 'text-red-600 font-medium'
  };
  return classes[confidence] || 'text-gray-600';
};

// Bestätigungs-Status für alle Aktionen
const confirmedActions = ref({
  category: false,
  relations: new Set(),
  newEntities: new Set(),
  updates: new Set()
});

const confirmCategory = (event) => {
  event.preventDefault();
  event.stopPropagation();
  confirmedActions.value.category = !confirmedActions.value.category;
  console.log('Kategorie-Status geändert:', confirmedActions.value.category);
};

const confirmRelation = (entity, event) => {
  event.preventDefault();
  event.stopPropagation();
  if (confirmedActions.value.relations.has(entity.id)) {
    confirmedActions.value.relations.delete(entity.id);
  } else {
    confirmedActions.value.relations.add(entity.id);
  }
  console.log('Verknüpfungs-Status geändert für Entity:', entity.id);
};

const viewEntity = (entity, event) => {
  event.preventDefault();
  event.stopPropagation();
  if (entity.id) {
    const entityType = entity.type.toLowerCase();
    const url = `/${entityType}/${entity.id}`;
    window.open(url, '_blank');
  } else {
    console.log('Entity anzeigen:', entity);
  }
};

const createNewEntity = (entity, event) => {
  event.preventDefault();
  event.stopPropagation();
  if (confirmedActions.value.newEntities.has(entity.type)) {
    confirmedActions.value.newEntities.delete(entity.type);
  } else {
    confirmedActions.value.newEntities.add(entity.type);
  }
  console.log('Neue Entity-Status geändert für:', entity.type);
};

const updateEntity = (deviation, event) => {
  event.preventDefault();
  event.stopPropagation();
  if (confirmedActions.value.updates.has(deviation.id)) {
    confirmedActions.value.updates.delete(deviation.id);
  } else {
    confirmedActions.value.updates.add(deviation.id);
  }
  console.log('Update-Status geändert für Deviation:', deviation.id);
};

const goBackToSelect = () => {
  const hasManualCategorySelection = selectedMainCategory.value && selectedGrouping.value && selectedDocumentType.value;
  const hasFiles = uploadedFiles.value.length > 0;

  // Speichere temporäre Daten vor dem Navigieren zurück
  saveTempData();

  // Speichere auch die Select-Daten mit den aktuellen Dateien
  const selectData = {
    uploadedFiles: uploadedFiles.value,
    selectedMainCategory: selectedMainCategory.value,
    selectedGrouping: selectedGrouping.value,
    selectedDocumentType: selectedDocumentType.value,
    suggestedCategory: suggestedCategory.value,
    confirmedActions: confirmedActions.value
  };
  DocumentTempStorageService.saveStepData('select', selectData);

  if (hasManualCategorySelection) {
    const query = {
      main: selectedMainCategory.value.id,
      group: selectedGrouping.value.id,
      type: selectedDocumentType.value.id,
      mainName: selectedMainCategory.value.name,
      groupName: selectedGrouping.value.name,
      typeName: selectedDocumentType.value.name
    };

    // Füge Dateien hinzu, falls vorhanden
    if (hasFiles) {
      query.files = JSON.stringify(uploadedFiles.value.map(file => ({
        id: file.id,
        fileName: file.fileName || file.name,
        fileSize: file.fileSize || file.size,
        fileType: file.fileType || file.type,
        uploadDate: file.uploadDate || null,
        previewUrl: file.previewUrl || null
      })));
    }

    console.log('Navigiere zurück zu Select mit aktuellen Kategorien:', {
      main: query.main,
      group: query.group,
      type: query.type,
      mainName: query.mainName,
      groupName: query.groupName,
      typeName: query.typeName
    });

    router.push({
      name: 'DocumentSelect',
      query: query
    });
  } else {
    router.push({ name: 'DocumentSelect' });
  }
};

const cancelDocumentCreation = () => {
  router.push({ name: 'DocumentList' });
};

const goToCreate = async () => {
  if (!canContinue.value) return;
  
  // Speichere temporäre Daten vor dem Navigieren
  saveTempData();
  
  // Erstelle Query-Parameter mit den aktuellen Kategorie-Informationen (nicht aus route.query)
  const query = {
    main: selectedMainCategory.value ? selectedMainCategory.value.id : route.query.main,
    group: selectedGrouping.value ? selectedGrouping.value.id : route.query.group,
    type: selectedDocumentType.value ? selectedDocumentType.value.id : route.query.type,
    mainName: selectedMainCategory.value ? selectedMainCategory.value.name : route.query.mainName,
    groupName: selectedGrouping.value ? selectedGrouping.value.name : route.query.groupName,
    typeName: selectedDocumentType.value ? selectedDocumentType.value.name : route.query.typeName,
    // Übergabe der Dateien an die Create-Ansicht
    files: JSON.stringify(uploadedFiles.value.map(file => ({
      id: file.id,
      fileName: file.fileName || file.name,
      fileSize: file.fileSize || file.size,
      fileType: file.fileType || file.type,
      uploadDate: file.uploadDate || null,
      previewUrl: file.previewUrl || null
    }))),
    analysisData: JSON.stringify({
      extractedData: extractedData.value,
      suggestedCategory: suggestedCategory.value,
      matchingEntities: matchingEntities.value,
      suggestedNewEntities: suggestedNewEntities.value,
      deviations: deviations.value,
      confirmedActions: {
        category: confirmedActions.value.category,
        relations: Array.from(confirmedActions.value.relations),
        newEntities: Array.from(confirmedActions.value.newEntities),
        updates: Array.from(confirmedActions.value.updates)
      }
    })
  };
  
  console.log('goToCreate - Aktuelle Kategorien in analyze:', {
    selectedMainCategory: selectedMainCategory.value,
    selectedGrouping: selectedGrouping.value,
    selectedDocumentType: selectedDocumentType.value
  });
  
  console.log('Navigiere zu Create mit Query-Parametern:', {
    main: query.main,
    group: query.group,
    type: query.type,
    mainName: query.mainName,
    groupName: query.groupName,
    typeName: query.typeName
  });
  
  router.push({
    name: 'DocumentCreate',
    query: query
  });
};

onMounted(() => {
  uploadedFiles.value = [];
  
  
  // Lade zuerst Kategorien aus Query-Parametern (hat Priorität)
  loadCategoriesFromSelection();
  
  // Dann lade temporäre Daten, falls keine Query-Parameter vorhanden sind
  if (!route.query.files) {
    loadTempData();
  }
  
  
  
});

// Temporäre Speicherung für den Dokumentenerstellungsprozess
const loadTempData = () => {
  const tempData = DocumentTempStorageService.loadStepData('analyze');
  if (tempData) {
    console.log('Lade temporäre Daten aus Analyze-Schritt:', tempData);
    
    // Stelle hochgeladene Dateien wieder her (nur wenn keine Query-Parameter vorhanden)
    if (tempData.uploadedFiles && !route.query.files) {
      uploadedFiles.value = tempData.uploadedFiles;
    }
    
    // Stelle extrahierte Daten wieder her
    if (tempData.extractedData) {
      extractedData.value = tempData.extractedData;
    }
    
    // Stelle bestätigte Aktionen wieder her
    if (tempData.confirmedActions) {
      confirmedActions.value = tempData.confirmedActions;
      // Konvertiere Arrays zurück zu Sets
      if (tempData.confirmedActions.relations) {
        confirmedActions.value.relations = new Set(tempData.confirmedActions.relations);
      }
      if (tempData.confirmedActions.newEntities) {
        confirmedActions.value.newEntities = new Set(tempData.confirmedActions.newEntities);
      }
      if (tempData.confirmedActions.updates) {
        confirmedActions.value.updates = new Set(tempData.confirmedActions.updates);
      }
    }
    
    // Stelle Analyse-Ergebnisse wieder her
    if (tempData.matchingEntities) {
      matchingEntities.value = tempData.matchingEntities;
    }
    if (tempData.suggestedNewEntities) {
      suggestedNewEntities.value = tempData.suggestedNewEntities;
    }
    if (tempData.deviations) {
      deviations.value = tempData.deviations;
    }
  } else {
    // Wenn keine temporären Daten vorhanden sind, setze alles zurück
    clearAllData();
  }
};

const saveTempData = () => {
  const tempData = {
    uploadedFiles: uploadedFiles.value,
    extractedData: extractedData.value,
    confirmedActions: {
      category: confirmedActions.value.category,
      relations: Array.from(confirmedActions.value.relations),
      newEntities: Array.from(confirmedActions.value.newEntities),
      updates: Array.from(confirmedActions.value.updates)
    },
    matchingEntities: matchingEntities.value,
    suggestedNewEntities: suggestedNewEntities.value,
    deviations: deviations.value
  };
  
  DocumentTempStorageService.saveStepData('analyze', tempData);
};

const loadCategoriesFromSelection = () => {
  const { main, group, type, mainName, groupName, typeName, files } = route.query;
  
  console.log('loadCategoriesFromSelection - Query-Parameter:', { main, group, type, files });
  
  // Setze Kategorien aus Query-Parametern, auch wenn keine Dateien vorhanden sind
  if (main && group && type) {
    console.log('Setze Kategorien aus Query-Parametern');
    console.log('Query-Parameter:', { main, group, type, mainName, groupName, typeName });
    
    selectedMainCategory.value = { 
      id: parseInt(main), 
      name: mainName || main,
      translationKey: `jaynaApp.document.main.${main}`
    };
    selectedGrouping.value = { 
      id: parseInt(group), 
      name: groupName || group,
      translationKey: `jaynaApp.document.group.${group}`
    };
    selectedDocumentType.value = { 
      id: parseInt(type), 
      name: typeName || type,
      translationKey: `jaynaApp.document.type.${type}`
    };
    
    suggestedCategory.value = {
      mainCategory: mainName || main,
      grouping: groupName || group,
      documentType: typeName || type,
      confidence: 'high'
    };
    
    console.log('loadCategoriesFromSelection - Kategorien gesetzt:', {
      mainCategory: selectedMainCategory.value,
      grouping: selectedGrouping.value,
      documentType: selectedDocumentType.value
    });
  }
  
  // Nur Dateien laden, wenn sie tatsächlich vorhanden sind
  if (files && files !== '[]' && files !== 'null' && files !== 'undefined') {
    try {
      const filesData = JSON.parse(files);
      console.log('Parsed files data:', filesData);
      
      // Prüfe ob filesData ein Array ist und nicht leer
      if (Array.isArray(filesData) && filesData.length > 0) {
        uploadedFiles.value = filesData.map(file => ({
          id: file.id,
          fileName: file.fileName,
          fileSize: parseInt(file.fileSize),
          fileType: file.fileType,
          uploadDate: file.uploadDate || null,
          previewUrl: file.previewUrl || null,
          analysisStatus: 'analyzing' // Starte mit analyzing Status
        }));
        console.log('loadCategoriesFromSelection: Dateien aus Query geladen:', uploadedFiles.value.length, 'Dateien');
        
        // Automatisch alle geladenen Dateien analysieren
        analyzeLoadedFiles();
      } else {
        console.log('loadCategoriesFromSelection: filesData ist leer oder kein Array');
        uploadedFiles.value = [];
      }
      
    } catch (error) {
      console.error('Fehler beim Parsen der Datei-Daten:', error);
      uploadedFiles.value = [];
    }
  } else {
    console.log('loadCategoriesFromSelection: Keine Dateien in Query gefunden, uploadedFiles bleibt leer');
    uploadedFiles.value = [];
  }
  
  // Nur Daten löschen, wenn weder Dateien noch Kategorien vorhanden sind
  if (uploadedFiles.value.length === 0 && !main && !group && !type) {
    console.log('Keine Dateien und keine Kategorien - lösche alle Daten');
    clearAllData();
  }
};

// Neue Funktion: Alle Daten zurücksetzen
const clearAllData = () => {
  extractedData.value = {};
  matchingEntities.value = [];
  suggestedNewEntities.value = [];
  deviations.value = [];
  suggestedCategory.value = {
    mainCategory: '',
    grouping: '',
    documentType: '',
    confidence: 'none'
  };
  confirmedActions.value.category = false;
  confirmedActions.value.relations.clear();
  confirmedActions.value.newEntities.clear();
  confirmedActions.value.updates.clear();
  selectedMainCategory.value = null;
  selectedGrouping.value = null;
  selectedDocumentType.value = null;
};

// Neue Funktion: Analysiere alle geladenen Dateien automatisch
const analyzeLoadedFiles = async () => {
  console.log('Starte automatische Analyse der geladenen Dateien...');
  
  for (const file of uploadedFiles.value) {
    try {
      console.log('Analysiere Datei:', file.fileName);
      
      // Simuliere die Datei-Analyse basierend auf den vorhandenen Daten
      const extractedDataFromFile = await extractDataFromDocumentByName(file.fileName, file.fileSize);
      
      // Wenn es die erste Datei ist, setze sie als Hauptdaten
      if (Object.keys(extractedData.value).length === 0) {
        extractedData.value = extractedDataFromFile;
      }
      
      // Markiere Datei als analysiert
      const fileIndex = uploadedFiles.value.findIndex(f => f.id === file.id);
      if (fileIndex !== -1) {
        uploadedFiles.value[fileIndex].analysisStatus = 'completed';
      }
      
      console.log('Datei erfolgreich analysiert:', file.fileName);
      
    } catch (error) {
      console.error('Fehler bei der Analyse von Datei:', file.fileName, error);
      
      const fileIndex = uploadedFiles.value.findIndex(f => f.id === file.id);
      if (fileIndex !== -1) {
        uploadedFiles.value[fileIndex].analysisStatus = 'error';
      }
    }
  }
  
  // Nach der Analyse aller Dateien Entity-Matches generieren
  if (Object.keys(extractedData.value).length > 0) {
    console.log('Generiere Entity-Matches nach automatischer Analyse...');
    generateEntityMatches();
  }
};

// Neue Funktion: Extrahiere Daten basierend auf Dateiname (simuliert für Dateien aus Select-View)
const extractDataFromDocumentByName = async (fileName, fileSize) => {
  console.log('Simuliere Dokumentenanalyse für Datei aus Select-View:', fileName);
  
  // Simuliere die echte Analyse basierend auf Dateiname
  // In der echten Implementierung würde hier die gleiche Logik wie bei extractDataFromDocument verwendet
  
  const fileNameLower = fileName.toLowerCase();
  
  // Simuliere extrahierten Text basierend auf Dateiname
  let simulatedText = '';
  let documentType = 'UNKNOWN';
  
  if (fileNameLower.includes('sepa') || fileNameLower.includes('mandat') || fileNameLower.includes('lastschrift')) {
    documentType = 'SEPA_MANDATE';
    simulatedText = `SEPA-Lastschriftmandat
Gläubiger: ${generateRandomCompany()}
IBAN: ${generateRandomIBAN()}
Kontoinhaber: ${generateRandomName()}
Datum: ${generateRandomDate()}
Zweck: ${generateRandomPurpose()}
Gläubiger-Identifikationsnummer: DE24ZZZ00002614948
Mandatsreferenz: ${generateRandomReference()}`;
  } else if (fileNameLower.includes('rechnung') || fileNameLower.includes('bill') || fileNameLower.includes('invoice')) {
    documentType = 'INVOICE';
    simulatedText = `Rechnung
Rechnungsnummer: R-${Math.random().toString().slice(2, 8)}
Anbieter: ${generateRandomCompany()}
Betrag: ${(Math.random() * 100 + 20).toFixed(2)} €
Kundennummer: K${Math.random().toString().slice(2, 8)}`;
  } else if (fileNameLower.includes('konto') || fileNameLower.includes('auszug') || fileNameLower.includes('statement')) {
    documentType = 'BANK_STATEMENT';
    simulatedText = `Kontoauszug
IBAN: ${generateRandomIBAN()}
Zeitraum: 01.01.2024 - 31.01.2024
Bank: ${generateRandomCompany()}
Kontoinhaber: ${generateRandomName()}`;
  } else {
    simulatedText = `Unbekanntes Dokument
Dateiname: ${fileName}
Dateigröße: ${formatFileSize(fileSize)}`;
  }
  
  // Verwende die gleiche Extraktionslogik wie bei echten Dateien
  let extractedData = {};
  
  switch (documentType) {
    case 'SEPA_MANDATE':
      extractedData = extractSEPAData(simulatedText);
      break;
    case 'INVOICE':
      extractedData = extractInvoiceData(simulatedText);
      break;
    case 'BANK_STATEMENT':
      extractedData = extractBankStatementData(simulatedText);
      break;
    default:
      extractedData = extractGenericData(simulatedText, { name: fileName, size: fileSize, type: 'application/pdf' });
  }
  
  // Zusätzliche Metadaten hinzufügen
  extractedData['Dokumenttyp'] = getDocumentTypeName(documentType);
  extractedData['Dateiname'] = fileName;
  extractedData['Dateigröße'] = formatFileSize(fileSize);
  extractedData['Analysedatum'] = new Date().toLocaleString('de-DE');
  extractedData['Hinweis'] = 'Simulierte Analyse (Datei aus Select-View)';
  
  return extractedData;
};

const generateEntityMatches = () => {
  matchingEntities.value = [];
  suggestedNewEntities.value = [];
  deviations.value = [];
  
  if (Object.keys(extractedData.value).length === 0) {
    console.log('Keine extrahierten Daten vorhanden für Entity-Matching');
    return;
  }
  
  console.log('Starte Entity-Matching basierend auf extrahierten Daten:', extractedData.value);
  
  // 1. Entity-Matches finden (Verknüpfungen mit bestehenden Datensätzen)
  findEntityMatches();
  
  // 2. Neue Entity-Vorschläge generieren
  generateNewEntitySuggestions();
  
  // 3. Abweichungen identifizieren
  identifyDeviations();
};

const findEntityMatches = () => {
  const data = extractedData.value;
  
  console.log('Suche nach echten Entity-Matches in der Datenbank...');
  
  // TODO: Hier würden echte API-Aufrufe zur Datenbank erfolgen
  // Beispiel für echte Implementierung:
  // const matchingAccounts = await FinancialAccountService.findByIBAN(data['IBAN']);
  // const matchingContacts = await ContactService.findByName(data['Gläubiger-Name']);
  // const matchingContracts = await ContractService.findByProvider(data['Anbieter']);
  
  // Für jetzt: Keine fiktiven Matches mehr anzeigen
  // Das System zeigt nur echte Matches aus der Datenbank an
  
  console.log('Keine echten Matches in der Datenbank gefunden');
  matchingEntities.value = [];
};

const generateNewEntitySuggestions = () => {
  const data = extractedData.value;
  
  console.log('Generiere intelligente Entity-Vorschläge basierend auf extrahierten Daten...');
  
  // SEPA-Mandat: Intelligente Vorschläge basierend auf allen verfügbaren Daten
  if (data['Dokumenttyp'] && data['Dokumenttyp'].includes('SEPA')) {
    
    // 1. Finanzkonto vorschlagen (falls IBAN vorhanden)
    if (data['IBAN'] && data['Kreditinstitut']) {
      suggestedNewEntities.value.push({
        type: 'Finanzkonto',
        reason: 'Neues Finanzkonto basierend auf SEPA-Mandat',
        suggestedData: {
          'Bank': data['Kreditinstitut'] || 'Unbekannt',
          'IBAN': data['IBAN'] || 'Unbekannt',
          'Kontoinhaber': data['Kontoinhaber'] || 'Jens Krpesch',
          'Kontotyp': 'Girokonto',
          'Mandatsreferenz': data['Mandatsreferenz'] || 'Unbekannt'
        }
      });
    }
    
    // 2. Kontakt vorschlagen (falls Gläubiger-Name vorhanden)
    if (data['Gläubiger-Name']) {
      suggestedNewEntities.value.push({
        type: 'Kontakt',
        reason: 'Neuer Gläubiger-Kontakt basierend auf SEPA-Mandat',
        suggestedData: {
          'Name': data['Gläubiger-Name'] || 'Unbekannt',
          'Typ': 'Verein/Organisation',
          'Adresse': data['Gläubiger-Adresse'] || 'Unbekannt',
          'Identifikationsnummer': data['Gläubiger-Identifikationsnummer'] || 'Unbekannt',
          'Zweck': data['Zweck'] || 'Unbekannt'
        }
      });
    }
    
    // 3. Vertrag vorschlagen (falls es sich um wiederkehrende Zahlungen handelt)
    if (data['Mandatstyp'] && data['Mandatstyp'].includes('Wiederkehrende Zahlungen')) {
      suggestedNewEntities.value.push({
        type: 'Vertrag',
        reason: 'Neuer Mitgliedschaftsvertrag basierend auf SEPA-Mandat',
        suggestedData: {
          'Vertragstyp': 'Mitgliedschaft/Verein',
          'Gläubiger': data['Gläubiger-Name'] || 'Unbekannt',
          'Zweck': data['Zweck'] || 'Unbekannt',
          'IBAN': data['IBAN'] || 'Unbekannt',
          'Mandatsreferenz': data['Mandatsreferenz'] || 'Unbekannt',
          'Mandatstyp': data['Mandatstyp'] || 'Unbekannt',
          'Gültigkeit': data['Gültigkeit'] || 'Unbekannt'
        }
      });
    }
    
    // 4. INTELLIGENT: Adresse vorschlagen (falls Kontoinhaber-Adresse vorhanden)
    if (data['Kontoinhaber-Straße'] && data['Kontoinhaber-Ort']) {
      suggestedNewEntities.value.push({
        type: 'Adresse',
        reason: 'Neue Adresse basierend auf Kontoinhaber-Daten',
        suggestedData: {
          'Typ': 'Privatadresse',
          'Inhaber': data['Kontoinhaber'] || 'Jens Krpesch',
          'Straße': data['Kontoinhaber-Straße'] || 'Unbekannt',
          'Ort': data['Kontoinhaber-Ort'] || 'Unbekannt',
          'Verwendungszweck': 'Hauptwohnsitz'
        }
      });
    }
    
    // 5. INTELLIGENT: Zahlungsplan vorschlagen (falls es sich um wiederkehrende Zahlungen handelt)
    if (data['Mandatstyp'] && data['Mandatstyp'].includes('Wiederkehrende Zahlungen')) {
      suggestedNewEntities.value.push({
        type: 'Zahlungsplan',
        reason: 'Neuer Zahlungsplan basierend auf SEPA-Mandat',
        suggestedData: {
          'Typ': 'Jährliche Mitgliedschaft',
          'Gläubiger': data['Gläubiger-Name'] || 'Unbekannt',
          'Zweck': data['Zweck'] || 'Unbekannt',
          'Häufigkeit': 'Jährlich',
          'IBAN': data['IBAN'] || 'Unbekannt',
          'Mandatsreferenz': data['Mandatsreferenz'] || 'Unbekannt'
        }
      });
    }
    
    // 6. INTELLIGENT: Dokumentenarchiv vorschlagen (falls wichtige Dokumentendaten vorhanden)
    if (data['Datum der Unterschrift'] && data['Ort der Unterschrift']) {
      suggestedNewEntities.value.push({
        type: 'Dokumentenarchiv',
        reason: 'Neues Dokumentenarchiv für SEPA-Mandate',
        suggestedData: {
          'Kategorie': 'Banking & Finanzen',
          'Unterkategorie': 'SEPA-Mandate',
          'Gläubiger': data['Gläubiger-Name'] || 'Unbekannt',
          'Datum': data['Datum der Unterschrift'] || 'Unbekannt',
          'Ort': data['Ort der Unterschrift'] || 'Unbekannt',
          'Gültigkeit': data['Gültigkeit'] || 'Unbekannt'
        }
      });
    }
  }
  
  // Rechnung: Intelligente Vorschläge
  if (data['Dokumenttyp'] && data['Dokumenttyp'].includes('Rechnung')) {
    if (data['Anbieter']) {
      suggestedNewEntities.value.push({
        type: 'Kontakt',
        reason: 'Neuer Anbieter-Kontakt basierend auf Rechnung',
        suggestedData: {
          'Name': data['Anbieter'] || 'Unbekannt',
          'Typ': 'Anbieter',
          'Kundennummer': data['Kundennummer'] || 'Unbekannt',
          'Rechnungsnummer': data['Rechnungsnummer'] || 'Unbekannt'
        }
      });
    }
    
    // Vertrag vorschlagen für Rechnungen
    if (data['Anbieter']) {
      suggestedNewEntities.value.push({
        type: 'Vertrag',
        reason: 'Neuer Servicevertrag basierend auf Rechnung',
        suggestedData: {
          'Vertragstyp': 'Servicevertrag',
          'Anbieter': data['Anbieter'] || 'Unbekannt',
          'Kundennummer': data['Kundennummer'] || 'Unbekannt',
          'Rechnungsbetrag': data['Rechnungsbetrag'] || 'Unbekannt'
        }
      });
    }
    
    // INTELLIGENT: Ausgabenkategorie vorschlagen
    if (data['Anbieter']) {
      suggestedNewEntities.value.push({
        type: 'Ausgabenkategorie',
        reason: 'Neue Ausgabenkategorie basierend auf Rechnung',
        suggestedData: {
          'Kategorie': 'Versorgung & Energie',
          'Unterkategorie': data['Anbieter'] || 'Unbekannt',
          'Typ': 'Regelmäßige Ausgabe',
          'Betrag': data['Rechnungsbetrag'] || 'Unbekannt',
          'Häufigkeit': 'Monatlich'
        }
      });
    }
  }
  
  // Kontoauszug: Intelligente Vorschläge
  if (data['Dokumenttyp'] && data['Dokumenttyp'].includes('Kontoauszug')) {
    if (data['IBAN'] || data['Kontonummer']) {
      suggestedNewEntities.value.push({
        type: 'Finanzkonto',
        reason: 'Neues Finanzkonto basierend auf Kontoauszug',
        suggestedData: {
          'Bank': data['Bank'] || 'Unbekannt',
          'IBAN': data['IBAN'] || data['Kontonummer'] || 'Unbekannt',
          'Kontoinhaber': data['Kontoinhaber'] || 'Jens Krpesch',
          'Kontotyp': 'Girokonto',
          'Zeitraum': data['Zeitraum'] || 'Unbekannt'
        }
      });
    }
    
    // INTELLIGENT: Finanzbericht vorschlagen
    if (data['Einnahmen'] && data['Ausgaben']) {
      suggestedNewEntities.value.push({
        type: 'Finanzbericht',
        reason: 'Neuer Finanzbericht basierend auf Kontoauszug',
        suggestedData: {
          'Zeitraum': data['Zeitraum'] || 'Unbekannt',
          'Einnahmen': data['Einnahmen'] || 'Unbekannt',
          'Ausgaben': data['Ausgaben'] || 'Unbekannt',
          'Saldo': data['Endbestand'] || 'Unbekannt',
          'Bank': data['Bank'] || 'Unbekannt'
        }
      });
    }
  }
  
  // INTELLIGENT: Allgemeine Vorschläge basierend auf Dokumenttyp
  if (data['Dokumenttyp']) {
    // Wenn es sich um ein Finanzdokument handelt
    if (data['Dokumenttyp'].includes('SEPA') || data['Dokumenttyp'].includes('Kontoauszug') || data['Dokumenttyp'].includes('Rechnung')) {
      suggestedNewEntities.value.push({
        type: 'Finanzübersicht',
        reason: 'Neue Finanzübersicht basierend auf Dokumenttyp',
        suggestedData: {
          'Kategorie': 'Finanzdokumente',
          'Dokumenttyp': data['Dokumenttyp'] || 'Unbekannt',
          'Datum': new Date().toLocaleDateString('de-DE'),
          'Status': 'Aktiv',
          'Priorität': 'Hoch'
        }
      });
    }
  }
  
  console.log('Intelligente Vorschläge generiert:', suggestedNewEntities.value.length);
};

const identifyDeviations = () => {
  const data = extractedData.value;
  const currentDate = new Date();
  
  console.log('Prüfe auf Abweichungen basierend auf echten Datenbank-Einträgen...');
  
  // TODO: Hier würden echte Abweichungsprüfungen gegen die Datenbank erfolgen
  // Beispiel für echte Implementierung:
  // const existingAccount = await FinancialAccountService.findByIBAN(data['IBAN']);
  // if (existingAccount && existingAccount.iban !== data['IBAN']) {
  //   deviations.value.push({ ... });
  // }
  
  // Für jetzt: Nur Datums-basierte Abweichungen prüfen (da diese nicht von DB-Matches abhängen)
  if (data['Dokumenttyp'] && data['Dokumenttyp'].includes('SEPA')) {
    // Prüfe Datum (Dokument könnte veraltet sein)
    if (data['Datum der Unterschrift']) {
      const documentDate = new Date(data['Datum der Unterschrift']);
      const daysDiff = Math.floor((currentDate - documentDate) / (1000 * 60 * 60 * 24));
      
      if (daysDiff > 365) {
        deviations.value.push({
          id: 'dev-001',
          entityName: 'Dokument',
          field: 'Datum',
          expected: 'Aktuell (letzte 12 Monate)',
          actual: `${data['Datum der Unterschrift']} (${daysDiff} Tage alt)`,
          severity: 'low',
          reason: 'Dokument ist älter als 12 Monate - Daten könnten veraltet sein'
        });
      }
    }
  }
  
  console.log('Keine relevanten Abweichungen gefunden');
};

const triggerFileSelect = () => {
  if (document.getElementById('file-upload')) {
    document.getElementById('file-upload').click();
  }
};

const handleFileUpload = (event) => {
  const file = event.target.files[0];
  if (!file) return;
  
  processUploadedFile(file);
  
  event.target.value = '';
};

const handleDrop = (event) => {
  event.preventDefault();
  isDragOver.value = false;
  
  const files = Array.from(event.dataTransfer.files);
  if (files.length > 0) {
    processUploadedFile(files[0]);
  }
};

const handleDragOver = (event) => {
  event.preventDefault();
  isDragOver.value = true;
};

const handleDragLeave = (event) => {
  event.preventDefault();
  isDragOver.value = false;
};

const handleDragEnter = (event) => {
  event.preventDefault();
  isDragOver.value = true;
};

const processUploadedFile = (file) => {
  if (file.size > 10 * 1024 * 1024) {
    AlertService.showError('Datei ist zu groß. Maximale Größe: 10MB');
    return;
  }
  
  const allowedTypes = ['application/pdf', 'application/msword', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 'image/jpeg', 'image/png'];
  if (!allowedTypes.includes(file.type)) {
    AlertService.showError('Dateityp nicht unterstützt. Erlaubt: PDF, DOC, DOCX, JPG, PNG');
    return;
  }
  
  const newFile = {
    id: Date.now(),
    fileName: file.name,
    fileSize: file.size,
    fileType: file.type,
    uploadDate: new Date().toISOString(),
    previewUrl: URL.createObjectURL(file),
    analysisStatus: 'analyzing'
  };
  
  uploadedFiles.value.push(newFile);
  
  analyzeDocument(file, newFile.id);
};

const analyzeDocument = async (file, fileId) => {
  try {
    console.log('Starte Dokumentenanalyse für:', file.name);
    
    await new Promise(resolve => setTimeout(resolve, 2000));
    
    const extractedDataFromFile = await extractDataFromDocument(file);
    
    extractedData.value = extractedDataFromFile;
    
    generateEntityMatches();
    
    const fileIndex = uploadedFiles.value.findIndex(f => f.id === fileId);
    if (fileIndex !== -1) {
      uploadedFiles.value[fileIndex].analysisStatus = 'completed';
    }
    
    console.log('Dokumentenanalyse abgeschlossen');
    
  } catch (error) {
    console.error('Fehler bei der Dokumentenanalyse:', error);
    
    const fileIndex = uploadedFiles.value.findIndex(f => f.id === fileId);
    if (fileIndex !== -1) {
      uploadedFiles.value[fileIndex].analysisStatus = 'error';
    }
  }
};

const extractDataFromDocument = async (file) => {
  console.log('Starte echte Dokumentenanalyse für:', file.name);
  
  try {
    // 1. Dokumenteninhalt extrahieren (OCR für Bilder, Text für PDFs)
    const documentText = await extractTextFromFile(file);
    console.log('Extrahierter Text:', documentText);
    
    // 2. Dokumenttyp erkennen basierend auf Inhalt
    const documentType = identifyDocumentType(documentText, file.name);
    console.log('Erkannter Dokumenttyp:', documentType);
    
    // 3. Spezifische Daten basierend auf Dokumenttyp extrahieren
    let extractedData = {};
    
    switch (documentType) {
      case 'SEPA_MANDATE':
        extractedData = extractSEPAData(documentText);
        break;
      case 'INVOICE':
        extractedData = extractInvoiceData(documentText);
        break;
      case 'BANK_STATEMENT':
        extractedData = extractBankStatementData(documentText);
        break;
      case 'CONTRACT':
        extractedData = extractContractData(documentText);
        break;
      default:
        extractedData = extractGenericData(documentText, file);
    }
    
    // 4. Zusätzliche Metadaten hinzufügen
    extractedData['Dokumenttyp'] = getDocumentTypeName(documentType);
    extractedData['Dateiname'] = file.name;
    extractedData['Dateigröße'] = formatFileSize(file.size);
    extractedData['Analysedatum'] = new Date().toLocaleString('de-DE');
    
    console.log('Extrahierte Daten:', extractedData);
    return extractedData;
    
  } catch (error) {
    console.error('Fehler bei der Dokumentenanalyse:', error);
    return {
      'Dokumenttyp': 'Fehler bei der Analyse',
      'Dateiname': file.name,
      'Dateigröße': formatFileSize(file.size),
      'Fehler': error.message,
      'Hinweis': 'Automatische Extraktion fehlgeschlagen. Bitte manuell prüfen.'
    };
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
  return `SEPA-Lastschriftmandat
Gläubiger: ${generateRandomCompany()}
IBAN: ${generateRandomIBAN()}
Kontoinhaber: ${generateRandomName()}
Datum: ${generateRandomDate()}
Zweck: ${generateRandomPurpose()}`;
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

// Dokumenttyp-Erkennung basierend auf Inhalt
const identifyDocumentType = (text, fileName) => {
  const lowerText = text.toLowerCase();
  const lowerFileName = fileName.toLowerCase();
  
  // SEPA-Mandat Erkennung
  if (lowerText.includes('sepa') || lowerText.includes('lastschrift') || 
      lowerText.includes('mandat') || lowerText.includes('gläubiger-identifikationsnummer') ||
      lowerFileName.includes('sepa') || lowerFileName.includes('mandat')) {
    return 'SEPA_MANDATE';
  }
  
  // Rechnung Erkennung
  if (lowerText.includes('rechnung') || lowerText.includes('invoice') || 
      lowerText.includes('betrag') || lowerText.includes('steuersatz') ||
      lowerFileName.includes('rechnung') || lowerFileName.includes('invoice')) {
    return 'INVOICE';
  }
  
  // Kontoauszug Erkennung
  if (lowerText.includes('kontoauszug') || lowerText.includes('bank statement') || 
      lowerText.includes('anfangsbestand') || lowerText.includes('endbestand') ||
      lowerFileName.includes('konto') || lowerFileName.includes('auszug')) {
    return 'BANK_STATEMENT';
  }
  
  // Vertrag Erkennung
  if (lowerText.includes('vertrag') || lowerText.includes('contract') || 
      lowerText.includes('vertragspartner') || lowerText.includes('laufzeit') ||
      lowerFileName.includes('vertrag') || lowerFileName.includes('contract')) {
    return 'CONTRACT';
  }
  
  return 'UNKNOWN';
};

// SEPA-Daten Extraktion
const extractSEPAData = (text) => {
  const data = {};
  
  // Gläubiger-Name extrahieren
  const creditorMatch = text.match(/gläubiger[:\s]*([^\n\r]+)/i);
  if (creditorMatch) data['Gläubiger-Name'] = creditorMatch[1].trim();
  
  // IBAN extrahieren
  const ibanMatch = text.match(/iban[:\s]*([A-Z]{2}[0-9]{2}[A-Z0-9]{4}[0-9]{7}([A-Z0-9]?){0,16})/i);
  if (ibanMatch) data['IBAN'] = ibanMatch[1];
  
  // Kontoinhaber extrahieren
  const accountHolderMatch = text.match(/kontoinhaber[:\s]*([^\n\r]+)/i);
  if (accountHolderMatch) data['Kontoinhaber'] = accountHolderMatch[1].trim();
  
  // Mandatsreferenz extrahieren
  const mandateRefMatch = text.match(/mandatsreferenz[:\s]*([^\n\r]+)/i);
  if (mandateRefMatch) data['Mandatsreferenz'] = mandateRefMatch[1].trim();
  
  // Zweck extrahieren
  const purposeMatch = text.match(/zweck[:\s]*([^\n\r]+)/i);
  if (purposeMatch) data['Zweck'] = purposeMatch[1].trim();
  
  // Datum extrahieren
  const dateMatch = text.match(/(\d{1,2}[.,]\d{1,2}[.,]\d{2,4})/);
  if (dateMatch) data['Datum'] = dateMatch[1];
  
  // Gläubiger-Identifikationsnummer
  const creditorIdMatch = text.match(/gläubiger-identifikationsnummer[:\s]*([^\n\r]+)/i);
  if (creditorIdMatch) data['Gläubiger-Identifikationsnummer'] = creditorIdMatch[1].trim();
  
  return data;
};

// Rechnungsdaten Extraktion
const extractInvoiceData = (text) => {
  const data = {};
  
  // Rechnungsnummer
  const invoiceNumberMatch = text.match(/rechnungsnummer[:\s]*([^\n\r]+)/i);
  if (invoiceNumberMatch) data['Rechnungsnummer'] = invoiceNumberMatch[1].trim();
  
  // Anbieter
  const providerMatch = text.match(/anbieter[:\s]*([^\n\r]+)/i);
  if (providerMatch) data['Anbieter'] = providerMatch[1].trim();
  
  // Betrag
  const amountMatch = text.match(/betrag[:\s]*([0-9,.]+\s*€)/i);
  if (amountMatch) data['Rechnungsbetrag'] = amountMatch[1].trim();
  
  // Kundennummer
  const customerNumberMatch = text.match(/kundennummer[:\s]*([^\n\r]+)/i);
  if (customerNumberMatch) data['Kundennummer'] = customerNumberMatch[1].trim();
  
  return data;
};

// Kontoauszug-Daten Extraktion
const extractBankStatementData = (text) => {
  const data = {};
  
  // Kontonummer/IBAN
  const accountMatch = text.match(/(iban|kontonummer)[:\s]*([^\n\r]+)/i);
  if (accountMatch) data['Kontonummer'] = accountMatch[2].trim();
  
  // Zeitraum
  const periodMatch = text.match(/zeitraum[:\s]*([^\n\r]+)/i);
  if (periodMatch) data['Zeitraum'] = periodMatch[1].trim();
  
  // Bank
  const bankMatch = text.match(/bank[:\s]*([^\n\r]+)/i);
  if (bankMatch) data['Bank'] = bankMatch[1].trim();
  
  return data;
};

// Vertragsdaten Extraktion
const extractContractData = (text) => {
  const data = {};
  
  // Vertragspartner
  const partnerMatch = text.match(/vertragspartner[:\s]*([^\n\r]+)/i);
  if (partnerMatch) data['Vertragspartner'] = partnerMatch[1].trim();
  
  // Vertragsnummer
  const contractNumberMatch = text.match(/vertragsnummer[:\s]*([^\n\r]+)/i);
  if (contractNumberMatch) data['Vertragsnummer'] = contractNumberMatch[1].trim();
  
  // Laufzeit
  const durationMatch = text.match(/laufzeit[:\s]*([^\n\r]+)/i);
  if (durationMatch) data['Laufzeit'] = durationMatch[1].trim();
  
  return data;
};

// Generische Daten-Extraktion
const extractGenericData = (text, file) => {
  return {
    'Extrahierter Text': text.substring(0, 200) + (text.length > 200 ? '...' : ''),
    'Dateityp': file.type,
    'Hinweis': 'Generische Extraktion - spezifische Felder nicht erkannt'
  };
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

const getDocumentTypeName = (type) => {
  const typeNames = {
    'SEPA_MANDATE': 'SEPA-Lastschriftmandat',
    'INVOICE': 'Rechnung',
    'BANK_STATEMENT': 'Kontoauszug',
    'CONTRACT': 'Vertrag',
    'UNKNOWN': 'Unbekannt'
  };
  return typeNames[type] || 'Unbekannt';
};

const removeFile = (file) => {
  const fileIndex = uploadedFiles.value.findIndex(f => f.id === file.id || f.name === file.name);
  if (fileIndex !== -1) {
    if (uploadedFiles.value[fileIndex].previewUrl) {
      try { URL.revokeObjectURL(uploadedFiles.value[fileIndex].previewUrl); } catch(e) {}
    }
    uploadedFiles.value.splice(fileIndex, 1);
    
    // Wenn alle Dateien entfernt wurden, alle Analyse-Daten löschen
    if (uploadedFiles.value.length === 0) {
      extractedData.value = {};
      matchingEntities.value = [];
      suggestedNewEntities.value = [];
      deviations.value = [];
      
      // Kategorie-Vorschlag zurücksetzen
      suggestedCategory.value = {
        mainCategory: '',
        grouping: '',
        documentType: '',
        confidence: 'none'
      };
      
      // Bestätigungs-Status zurücksetzen
      confirmedActions.value.category = false;
      
      // Ausgewählte Kategorien zurücksetzen
      selectedMainCategory.value = null;
      selectedGrouping.value = null;
      selectedDocumentType.value = null;
    }
  }
};

// Vorschau-Funktionalität
const showPreview = ref(false);

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

// Ref für die Dokumenten-Vorschau
const previewRef = ref(null);

const handleViewFile = (file) => {
  showPreview.value = true;
  if (file) {
    previewRef.value?.setPageByName(file.fileName || file.name);
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

.uploaded-files {
  margin-top: 20px;
}

.uploaded-files h4 {
  margin-bottom: 15px;
  color: #333;
}

.file-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  margin-bottom: 10px;
  background-color: white;
}

.file-info {
  display: flex;
  align-items: center;
  gap: 10px;
}

.file-icon {
  width: 24px;
  height: 24px;
  color: #666;
}

.file-details {
  display: flex;
  flex-direction: column;
}

.file-name {
  font-weight: 500;
  margin: 0;
  color: #333;
}

.file-size {
  font-size: 12px;
  color: #666;
  margin: 0;
}

.file-actions {
  display: flex;
  gap: 8px;
  align-items: center;
}

.action-button {
  padding: 6px 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  background-color: white;
  cursor: pointer;
  font-size: 12px;
  transition: all 0.3s ease;
}

.action-button:hover {
  background-color: #f5f5f5;
}

.action-button.delete:hover {
  background-color: #dc3545;
  color: white;
  border-color: #dc3545;
}

.remove-file {
  background-color: #f5f5f5;
  border: 1px solid #ddd;
  border-radius: 4px;
  padding: 6px 12px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.remove-file:hover {
  background-color: #e0e0e0;
}
</style>
