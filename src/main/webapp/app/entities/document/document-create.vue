<template>
  <div class="relative">
    <!-- Hauptinhalt -->
    <div class="flex flex-col" :style="showPreview ? { paddingRight: '560px', transition: 'padding-right 0.3s ease-in-out' } : { transition: 'padding-right 0.3s ease-in-out' }">
      <!-- Header-Bereich - volle Breite -->
      <div class="flex items-center justify-between -mt-8 -mx-12 py-8 px-12 bg-gray-100 sticky top-16 z-10" :style="showPreview ? { marginRight: '-560px' } : {}">
        <div class="flex-auto">
          <h1 class="text-xl font-semibold text-gray-900">{{ $t('jaynaApp.document.workflow.step3') }}</h1>
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
            @click.prevent="goBackToAnalyze()"
          />
          <Button
            :text="showPreview ? 'Vorschau ausblenden' : 'Vorschau anzeigen'"
            type="secondary"
            @click="togglePreview"
          />
          <Button
            :text="$t('entity.action.save')"
            type="primary"
            :disabled="isSaving"
            @click="saveDocument"
          />
        </div>
      </div>



      <!-- Kategorie-Abschnitt für Dokumente -->
      <div v-if="selectedMainCategory || selectedGrouping || selectedDocumentType" class="-mx-12 px-12">
        <FormPanel
          title="Kategorie"
          bg-color="bg-white"
        >
          <template v-slot:content>
            <div class="md:grid md:grid-cols-3 md:gap-6">
              <s-input
                name="mainCategory"
                type="text"
                :model-value="getMainCategoryName()"
                :label="$t('jaynaApp.document.select.mainCategory')"
                :disabled="true"
              />
              <s-input
                name="grouping"
                type="text"
                :model-value="getGroupingName()"
                :label="$t('jaynaApp.document.select.grouping')"
                :disabled="true"
              />
              <s-input
                name="documentType"
                type="text"
                :model-value="getDocumentTypeName()"
                :label="$t('jaynaApp.document.select.documentType')"
                :disabled="true"
              />
            </div>
          </template>
        </FormPanel>

        <Divider />
      </div>

      <!-- Abstand zwischen Kategorie und Formular -->
      <div class="h-6"></div>

      <!-- Formular-Inhalt -->
      <div class="-mx-12 px-12">


        <template v-if="formConfig">
          <FormBuilder
            ref="formBuilder"
            :form-config="formConfig"
            :entity-name="'document'"
            :form-name="formName"
          />
        </template>

        <template v-else>
          <!-- Fallback: Altes statisches Formular -->
          <FormPanel :title="$t('document.create.basicInfo')" bg-color="bg-white">
            <template v-slot:content>
              <div class="md:grid md:grid-cols-3 md:gap-6">
                <s-input
                  name="title"
                  type="text"
                  v-model="document.title"
                  :label="$t('jaynaApp.document.title')"
                  :placeholder="$t('document.create.titlePlaceholder')"
                  :required="true"
                />
                <s-input
                  name="description"
                  type="textarea"
                  v-model="document.description"
                  :label="$t('jaynaApp.document.description')"
                  :placeholder="$t('document.create.descriptionPlaceholder')"
                />
              </div>
            </template>
          </FormPanel>
        </template>


      </div>

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
                  <tr v-if="!uploadedFiles || uploadedFiles.length === 0">
                    <td colspan="6" class="px-6 py-4 text-center text-sm text-gray-500">
                      Keine Dateien vorhanden
                    </td>
                  </tr>
                  <tr v-else v-for="(file, index) in uploadedFiles" :key="file.id || file.name" class="hover:bg-gray-50">
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
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
              ref="documentUploadRef"
              :entity-id="createdDocumentId || null"
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

      <!-- Globaler Metadaten-Abschnitt -->
      <div class="mt-4">
        <Divider />
        <div class="bg-white rounded-lg shadow">
          <div class="p-4">
            <div class="flex justify-between items-center cursor-pointer" @click="showMetadata = !showMetadata">
              <h3 class="text-lg font-medium leading-6 text-gray-900">Metadaten</h3>
              <svg 
                class="w-5 h-5 text-gray-500 transition-transform" 
                :class="{ 'rotate-180': showMetadata }"
                fill="none" 
                stroke="currentColor" 
                viewBox="0 0 24 24"
              >
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7"></path>
              </svg>
            </div>
            
            <div v-if="showMetadata" class="mt-4 pt-4 border-t border-gray-200">
              <div class="grid grid-cols-1 md:grid-cols-3 gap-4 text-sm">
                <div class="flex flex-col">
                  <span class="text-gray-500 font-medium">Erstellt am</span>
                  <span class="text-gray-900">
                    {{ new Date().toLocaleDateString('de-DE') }}
                  </span>
                </div>
                <div class="flex flex-col">
                  <span class="text-gray-500 font-medium">Zuletzt geändert</span>
                  <span class="text-gray-900">
                    {{ new Date().toLocaleDateString('de-DE') }}
                  </span>
                </div>
                <div class="flex flex-col">
                  <span class="text-gray-500 font-medium">Geändert von</span>
                  <span class="text-gray-900">Aktueller Benutzer</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Erstellt am Bereich - ganz unten -->
      <div class="mt-4">
        <Divider />
        <div class="bg-gray-50 rounded-lg p-4">
          <div class="text-sm text-gray-600">
            <span class="font-medium">Erstellt am:</span>
            <span class="ml-2">
              {{ new Date().toLocaleDateString('de-DE') }}
            </span>
          </div>
        </div>
      </div>
    
  </div>
</template>

<script setup>
import { ref, onMounted, watch, nextTick } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useI18n } from 'vue-i18n';

import DocumentService from '@/services/document.service';
import AlertService from '@/services/alert.service';
import DocumentTempStorageService from '@/services/document-temp-storage.service';
import FormBuilder from '@/components/FormBuilder.vue';
import FormPanel from '@/components/formpanel.vue';
import Divider from '@/components/divider.vue';
import Button from '@/components/buttons/button.vue';
import SInput from '@/components/forms/input.vue';
import DocumentUpload from '@/entities/document/components/document-upload.vue';
import WorkflowSteps from '@/entities/document/components/workflow-steps.vue';
import { getCorrectFormName } from '@/composables/form-name-generator';
import DocumentPreview from '@/entities/document/components/document-preview.vue';
import CentralRelationService from '@/services/central-relation.service';

const route = useRoute();
const router = useRouter();
const { t } = useI18n();
const documentUploadRef = ref(null);

// Entkoppelte, lokale Datenstruktur für Dokument-Kategorien (mit echten Datenbank-IDs)
const mainCategories = ref([
  { id: 28, translationKey: 'jaynaApp.document.main.identity-personal-status' },
  { id: 29, translationKey: 'jaynaApp.document.main.work-education' },
  { id: 30, translationKey: 'jaynaApp.document.main.finances-insurance' },
  { id: 31, translationKey: 'jaynaApp.document.main.housing-property' },
  { id: 32, translationKey: 'jaynaApp.document.main.health' },
  { id: 33, translationKey: 'jaynaApp.document.main.communication-everyday' },
  { id: 34, translationKey: 'jaynaApp.document.main.law-administration' },
  { id: 35, translationKey: 'jaynaApp.document.main.energy-utilities' }
]);

const groupingMap = {
  28: [ // identity-personal-status
    { id: 61, translationKey: 'jaynaApp.document.group.ids' },
    { id: 62, translationKey: 'jaynaApp.document.group.civil-status-certificates' },
    { id: 63, translationKey: 'jaynaApp.document.group.directives' }
  ],
  29: [ // work-education
    { id: 64, translationKey: 'jaynaApp.document.group.work-documents' },
    { id: 65, translationKey: 'jaynaApp.document.group.education' },
    { id: 66, translationKey: 'jaynaApp.document.group.application' }
  ],
  30: [ // finances-insurance
    { id: 67, translationKey: 'jaynaApp.document.group.banking' },
    { id: 68, translationKey: 'jaynaApp.document.group.accounting' },
    { id: 69, translationKey: 'jaynaApp.document.group.insurances' }
  ],
  31: [ // housing-property
    { id: 70, translationKey: 'jaynaApp.document.group.rental-docs' },
    { id: 71, translationKey: 'jaynaApp.document.group.real-estate' },
    { id: 72, translationKey: 'jaynaApp.document.group.vehicles' }
  ],
  32: [ // health
    { id: 73, translationKey: 'jaynaApp.document.group.medical-documents' },
    { id: 74, translationKey: 'jaynaApp.document.group.care-support' }
  ],
  33: [ // communication-everyday
    { id: 75, translationKey: 'jaynaApp.document.group.correspondence' },
    { id: 76, translationKey: 'jaynaApp.document.group.protocols' },
    { id: 77, translationKey: 'jaynaApp.document.group.digital-communication' }
  ],
  34: [ // law-administration
    { id: 78, translationKey: 'jaynaApp.document.group.court-documents' },
    { id: 79, translationKey: 'jaynaApp.document.group.contracts-terms' }
  ],
  35: [ // energy-utilities
    { id: 80, translationKey: 'jaynaApp.document.group.invoices' }
  ]
};

const typeMap = {
  // A. Identität & Personenstand
  61: [ // ids
    { id: 262, translationKey: 'jaynaApp.document.type.identity-card' },
    { id: 263, translationKey: 'jaynaApp.document.type.passport' },
    { id: 264, translationKey: 'jaynaApp.document.type.drivers-license' },
    { id: 265, translationKey: 'jaynaApp.document.type.disability-id' },
    { id: 266, translationKey: 'jaynaApp.document.type.residence-permit' }
  ],
  62: [ // civil-status-certificates
    { id: 267, translationKey: 'jaynaApp.document.type.birth-certificate' },
    { id: 268, translationKey: 'jaynaApp.document.type.marriage-certificate' },
    { id: 269, translationKey: 'jaynaApp.document.type.divorce-decree' }
  ],
  63: [ // directives
    { id: 270, translationKey: 'jaynaApp.document.type.will' },
    { id: 271, translationKey: 'jaynaApp.document.type.patient-directive' },
    { id: 272, translationKey: 'jaynaApp.document.type.power-of-attorney' }
  ],

  // B. Arbeit & Bildung
  64: [ // work-documents
    { id: 273, translationKey: 'jaynaApp.document.type.employment-contract' },
    { id: 274, translationKey: 'jaynaApp.document.type.termination' },
    { id: 275, translationKey: 'jaynaApp.document.type.warning' },
    { id: 276, translationKey: 'jaynaApp.document.type.payslip' },
    { id: 277, translationKey: 'jaynaApp.document.type.income-tax-certificate' }
  ],
  65: [ // education
    { id: 278, translationKey: 'jaynaApp.document.type.certificate' },
    { id: 279, translationKey: 'jaynaApp.document.type.intermediate-certificate' },
    { id: 280, translationKey: 'jaynaApp.document.type.final-certificate' },
    { id: 281, translationKey: 'jaynaApp.document.type.study-certificate' },
    { id: 282, translationKey: 'jaynaApp.document.type.university-degree' }
  ],
  66: [ // application
    { id: 283, translationKey: 'jaynaApp.document.type.cv' },
    { id: 284, translationKey: 'jaynaApp.document.type.cover-letter' },
    { id: 285, translationKey: 'jaynaApp.document.type.references' }
  ],

  // C. Finanzen & Versicherungen
  67: [ // banking
    { id: 286, translationKey: 'jaynaApp.document.type.account-statement' },
    { id: 287, translationKey: 'jaynaApp.document.type.loan-contract' },
    { id: 288, translationKey: 'jaynaApp.document.type.credit-card-statement' },
    { id: 289, translationKey: 'jaynaApp.document.type.sepa-mandate' }
  ],
  68: [ // accounting
    { id: 290, translationKey: 'jaynaApp.document.type.invoice' },
    { id: 291, translationKey: 'jaynaApp.document.type.receipt' },
    { id: 292, translationKey: 'jaynaApp.document.type.quotation' },
    { id: 293, translationKey: 'jaynaApp.document.type.dunning-letter' },
    { id: 294, translationKey: 'jaynaApp.document.type.payment-proof' }
  ],
  69: [ // insurances
    { id: 295, translationKey: 'jaynaApp.document.type.insurance-policy' },
    { id: 296, translationKey: 'jaynaApp.document.type.claim-report' },
    { id: 297, translationKey: 'jaynaApp.document.type.benefit-statement' }
  ],

  // D. Wohnen & Eigentum
  70: [ // rental-docs
    { id: 298, translationKey: 'jaynaApp.document.type.rental-contract' },
    { id: 299, translationKey: 'jaynaApp.document.type.utility-bill' },
    { id: 300, translationKey: 'jaynaApp.document.type.handover-report' }
  ],
  71: [ // real-estate
    { id: 301, translationKey: 'jaynaApp.document.type.building-plan' },
    { id: 302, translationKey: 'jaynaApp.document.type.building-permit' },
    { id: 303, translationKey: 'jaynaApp.document.type.purchase-contract-house' }
  ],
  72: [ // vehicles
    { id: 304, translationKey: 'jaynaApp.document.type.vehicle-title' },
    { id: 305, translationKey: 'jaynaApp.document.type.vehicle-registration' },
    { id: 306, translationKey: 'jaynaApp.document.type.tuv-certificate' },
    { id: 307, translationKey: 'jaynaApp.document.type.maintenance-record' },
    { id: 308, translationKey: 'jaynaApp.document.type.workshop-invoice' }
  ],

  // E. Gesundheit
  50: [ // medical-documents
    { id: 309, translationKey: 'jaynaApp.document.type.vaccination-card' },
    { id: 310, translationKey: 'jaynaApp.document.type.medical-report' },
    { id: 311, translationKey: 'jaynaApp.document.type.lab-result' },
    { id: 312, translationKey: 'jaynaApp.document.type.prescription' },
    { id: 313, translationKey: 'jaynaApp.document.type.xray-images' }
  ],
  51: [ // care-support
    { id: 314, translationKey: 'jaynaApp.document.type.care-level-notice' },
    { id: 315, translationKey: 'jaynaApp.document.type.guardianship-certificate' }
  ],

  // F. Kommunikation & Alltagsdokumente
  60: [ // correspondence
    { id: 316, translationKey: 'jaynaApp.document.type.invitation' },
    { id: 317, translationKey: 'jaynaApp.document.type.greeting-card' },
    { id: 318, translationKey: 'jaynaApp.document.type.thank-you-letter' },
    { id: 319, translationKey: 'jaynaApp.document.type.general-correspondence' }
  ],
  61: [ // protocols
    { id: 320, translationKey: 'jaynaApp.document.type.meeting-minutes' },
    { id: 321, translationKey: 'jaynaApp.document.type.club-minutes' }
  ],
  62: [ // digital-communication
    { id: 322, translationKey: 'jaynaApp.document.type.email-printout' },
    { id: 323, translationKey: 'jaynaApp.document.type.chat-log' },
    { id: 324, translationKey: 'jaynaApp.document.type.social-media-post' }
  ],

  // G. Recht & Verwaltung
  70: [ // court-documents
    { id: 325, translationKey: 'jaynaApp.document.type.judgment' },
    { id: 326, translationKey: 'jaynaApp.document.type.summons' },
    { id: 327, translationKey: 'jaynaApp.document.type.penalty-order' },
    { id: 328, translationKey: 'jaynaApp.document.type.enforcement-order' }
  ],
  71: [ // contracts-terms
    { id: 329, translationKey: 'jaynaApp.document.type.terms-and-conditions' },
    { id: 330, translationKey: 'jaynaApp.document.type.contract-terms' },
    { id: 331, translationKey: 'jaynaApp.document.type.privacy-notice' }
  ],

  // H. Energie & Versorgung
  80: [ // invoices
    { id: 332, translationKey: 'jaynaApp.document.type.electricity-bill' },
    { id: 333, translationKey: 'jaynaApp.document.type.water-bill' },
    { id: 334, translationKey: 'jaynaApp.document.type.gas-bill' },
    { id: 335, translationKey: 'jaynaApp.document.type.internet-phone-bill' }
  ]
};

const document = ref({
  title: '',
  description: '',
  documentNumber: '',
  customerNumber: '',
  partner: '',
  status: 'DRAFT',
  isPublic: false,
  contractNumber: '',
  realEstateNumber: '',
  financeAccountNumber: '',
  mobilityNumber: '',
  entityState: 'ACTIVE'
});

const isSaving = ref(false);
const createdDocumentId = ref(null);
const documentService = new DocumentService();
const formConfig = ref(null);
const formName = ref(null);
const formBuilder = ref(null);

const selectedMainCategory = ref(null);
const selectedGrouping = ref(null);
const selectedDocumentType = ref(null);

const showPreview = ref(false);
const showMetadata = ref(false);
const uploadedFiles = ref([]);
const previewRef = ref(null);

// Hilfsfunktionen für die Anzeige der Feldwerte
const getMainCategoryName = () => {
  if (!selectedMainCategory.value) return '-';
  // Prüfe ob name oder translationKey vorhanden ist
  if (selectedMainCategory.value.name) {
    return selectedMainCategory.value.name;
  }
  if (selectedMainCategory.value.translationKey) {
    return t(selectedMainCategory.value.translationKey);
  }
  return selectedMainCategory.value.id || '-';
};

const getGroupingName = () => {
  if (!selectedGrouping.value) return '-';
  // Prüfe ob name oder translationKey vorhanden ist
  if (selectedGrouping.value.name) {
    return selectedGrouping.value.name;
  }
  if (selectedGrouping.value.translationKey) {
    return t(selectedGrouping.value.translationKey);
  }
  return selectedGrouping.value.id || '-';
};

const getDocumentTypeName = () => {
  if (!selectedDocumentType.value) return '-';
  // Prüfe ob name oder translationKey vorhanden ist
  if (selectedDocumentType.value.name) {
    return selectedDocumentType.value.name;
  }
  if (selectedDocumentType.value.translationKey) {
    return t(selectedDocumentType.value.translationKey);
  }
  return selectedDocumentType.value.id || '-';
};

const DEBUG_DOC_CREATE = false;

// Neue Funktion: Lade Form-Definition basierend auf aktuellen Kategorien
const loadFormDefinition = async () => {
  if (!selectedMainCategory.value || !selectedGrouping.value || !selectedDocumentType.value) {
    if (DEBUG_DOC_CREATE) console.log('Keine vollständigen Kategorien vorhanden für Form-Definition');
    return;
  }
  
  if (DEBUG_DOC_CREATE) console.log('Lade Form-Definition für Kategorien:', {
    main: selectedMainCategory.value,
    group: selectedGrouping.value,
    type: selectedDocumentType.value
  });
  
  // Verwende zentrale Logik zur Form-Namenermittlung
  let formNameToLoad = null;
  try {
    formNameToLoad = await getCorrectFormName(
      {
        name: selectedDocumentType.value.name,
        translationKey: selectedDocumentType.value.translationKey,
        formName: selectedDocumentType.value.formName || null,
        entityName: 'document'
      },
      'document'
    );
  } catch (e) {
    if (DEBUG_DOC_CREATE) console.warn('getCorrectFormName Fehler:', e);
  }
  
  if (!formNameToLoad) {
    if (DEBUG_DOC_CREATE) console.warn('Kein passender Formname gefunden, Fallback auf document-basic');
    formNameToLoad = 'document-basic';
  }
  
  if (DEBUG_DOC_CREATE) console.log('Versuche Form-Definition zu laden:', formNameToLoad);
  
  try {
    const module = await import(`@/shared/form-definitions/document/${formNameToLoad}.json`);
    formConfig.value = module.default || module;
    formName.value = formNameToLoad;
    if (DEBUG_DOC_CREATE) console.log('Form-Definition erfolgreich geladen:', formNameToLoad, formConfig.value);
  } catch (error) {
    if (DEBUG_DOC_CREATE) console.error('Fehler beim Laden der Form-Definition:', error);
    // Fallback: Lade Standard-Form
    try {
      const defaultModule = await import('@/shared/form-definitions/document/document-basic.json');
      formConfig.value = defaultModule.default || defaultModule;
      formName.value = 'document-basic';
      if (DEBUG_DOC_CREATE) console.log('Fallback: Standard-Form geladen');
    } catch (fallbackError) {
      if (DEBUG_DOC_CREATE) console.error('Fehler beim Laden der Standard-Form:', fallbackError);
    }
  }
};

const loadFormDefinitionFromQuery = async () => {
  try {
    const { main, group, type, mainName, groupName, typeName, analysisData, files } = route.query || {};
    if (DEBUG_DOC_CREATE) console.log('Query-Parameter:', { main, group, type, mainName, groupName, typeName, files });
    
    // Lade Dateien aus Query-Parametern, falls vorhanden
    if (files && files !== '[]' && files !== 'null' && files !== 'undefined') {
      try {
        const filesData = JSON.parse(files);
        if (DEBUG_DOC_CREATE) console.log('Parsed files data:', filesData);
        
        if (Array.isArray(filesData) && filesData.length > 0) {
          uploadedFiles.value = filesData.map(file => ({
            id: file.id,
            fileName: file.fileName,
            fileSize: parseInt(file.fileSize),
            fileType: file.fileType,
            uploadDate: file.uploadDate || null,
            previewUrl: file.previewUrl || null
          }));
          if (DEBUG_DOC_CREATE) console.log('Dateien aus Query geladen:', uploadedFiles.value.length, 'Dateien');
          
          // Setze Vorschau auf true, wenn Dateien vorhanden sind
          showPreview.value = true;
        }
      } catch (error) {
        if (DEBUG_DOC_CREATE) console.error('Fehler beim Parsen der Datei-Daten:', error);
      }
    }
    
    if (!main || !group || !type) {
      if (DEBUG_DOC_CREATE) console.log('Keine vollständigen Query-Parameter vorhanden');
      return;
    }
    
    // Setze die ausgewählten Kategorien mithilfe der ID-Maps (korrekte TranslationKeys)
    if (DEBUG_DOC_CREATE) console.log('Lade Kategorien aus Query-Parametern:', { main, group, type, mainName, groupName, typeName });

    const mainId = parseInt(main);
    const groupId = parseInt(group);
    const typeId = parseInt(type);

    const mainDef = mainCategories.value.find(m => m.id === mainId) || null;
    const groupingDefs = groupingMap[mainDef?.id] || [];
    const groupDef = groupingDefs.find(g => g.id === groupId) || null;
    const typeDefs = typeMap[groupDef?.id] || [];
    const typeDef = typeDefs.find(td => td.id === typeId) || null;

    selectedMainCategory.value = {
      id: mainId,
      name: mainName || (mainDef ? t(mainDef.translationKey) : String(main)),
      translationKey: mainDef?.translationKey || null
    };

    selectedGrouping.value = {
      id: groupId,
      name: groupName || (groupDef ? t(groupDef.translationKey) : String(group)),
      translationKey: groupDef?.translationKey || null
    };

    selectedDocumentType.value = {
      id: typeId,
      name: typeName || (typeDef ? t(typeDef.translationKey) : String(type)),
      translationKey: typeDef?.translationKey || null
    };
    
    if (DEBUG_DOC_CREATE) console.log('Kategorien in create gesetzt:', {
      mainCategory: selectedMainCategory.value,
      grouping: selectedGrouping.value,
      documentType: selectedDocumentType.value
    });
    
    // Lade die entsprechende Form-Definition basierend auf den Kategorien
    await loadFormDefinition();
    
    // Lade extrahierte Daten aus der Analyse, falls vorhanden
    if (analysisData) {
      try {
        const parsedAnalysisData = JSON.parse(analysisData);
        if (parsedAnalysisData.extractedData) {
          // Fülle die Formularfelder mit den extrahierten Daten
          populateFormWithExtractedData(parsedAnalysisData.extractedData);
        }
      } catch (error) {
        if (DEBUG_DOC_CREATE) console.error('Fehler beim Parsen der Analyse-Daten:', error);
      }
    }
  } catch (err) {
    // Kein Form gefunden -> Fallback (statisches Formular bleibt sichtbar)
  }
};

const populateFormWithExtractedData = (extractedData) => {
  // Mappe die extrahierten Daten auf die Formularfelder
  const fieldMappings = {
    'Dokumenttyp': 'documentType',
    'Gläubiger-Name': 'creditorName',
    'Gläubiger-Adresse': 'creditorAddress',
    'Gläubiger-Identifikationsnummer': 'creditorIdentifier',
    'Mandatsreferenz': 'mandateReference',
    'Kontoinhaber': 'debtorName',
    'Kontoinhaber-Straße': 'debtorStreet',
    'Kontoinhaber-Ort': 'debtorCity',
    'Kreditinstitut': 'bankName',
    'IBAN': 'iban',
    'Mandatstyp': 'mandateType',
    'Zweck': 'purpose',
    'Ort der Unterschrift': 'signaturePlace',
    'Datum der Unterschrift': 'signatureDate',
    'Unterschrift vorhanden': 'hasSignature',
    'Rückerstattungsfrist': 'refundPeriod',
    'Autorisierungstext': 'authorizationText',
    'Bankanweisung': 'bankInstruction'
  };
  
  // Fülle die Formularfelder
  Object.entries(extractedData).forEach(([key, value]) => {
    const fieldName = fieldMappings[key];
    if (fieldName && formConfig.value) {
      // Setze den Wert im Formular
      if (formConfig.value.setFieldValue) {
        formConfig.value.setFieldValue(fieldName, value);
      }
    }
  });
  
  // Setze auch die Standard-Dokumentfelder
  if (extractedData['Dokumenttyp']) {
    document.value.title = extractedData['Dokumenttyp'];
  }
  if (extractedData['Zweck']) {
    document.value.description = `SEPA-Mandat für: ${extractedData['Zweck']}`;
  }
};



const saveDocument = async () => {
  isSaving.value = true;
  try {
    let payload = { 
    ...document.value,
    // Stelle sicher, dass alle wichtigen Felder vorhanden sind
    title: document.value.title || '',
    description: document.value.description || '',
    documentNumber: document.value.documentNumber || '',
    customerNumber: document.value.customerNumber || '',
    partner: document.value.partner || '',
    contractNumber: document.value.contractNumber || '',
    realEstateNumber: document.value.realEstateNumber || '',
    financeAccountNumber: document.value.financeAccountNumber || '',
    mobilityNumber: document.value.mobilityNumber || ''
  };
    
    // Setze Standardwerte für erforderliche Felder
    payload.entityState = payload.entityState || 'ACTIVE';
    payload.status = payload.status || 'DRAFT';
    payload.isPublic = payload.isPublic || false;
    
    // Füge die Kategorie-Informationen hinzu (immer, nicht nur wenn formConfig existiert)
    if (selectedMainCategory.value) {
      payload.mainCategory = selectedMainCategory.value.id;
    }
    if (selectedGrouping.value) {
      payload.grouping = selectedGrouping.value.id;
    }
    if (selectedDocumentType.value) {
      payload.documentType = selectedDocumentType.value.id;
      // WICHTIG: Verwende die tatsächliche ID des ausgewählten Dokumenttyps als GroupType-ID
      payload.groupType = {
        id: parseInt(selectedDocumentType.value.id)
      };
      console.log('saveDocument - groupType ID gesetzt:', payload.groupType.id);
    }
    
    // Hole die Formulardaten aus dem FormBuilder
    if (formBuilder.value && typeof formBuilder.value.getData === 'function') {
      try {
        const formData = await formBuilder.value.getData();
        console.log('Formulardaten vom FormBuilder:', formData);
        
        // Konvertiere label zu title, falls vorhanden
        if (formData.label && !formData.title) {
          formData.title = formData.label;
        }
        
        payload = { ...payload, ...formData };
      } catch (error) {
        console.error('Fehler beim Abrufen der Formulardaten:', error);
      }
    } else {
      console.warn('FormBuilder nicht verfügbar oder getData Funktion nicht gefunden - verwende Fallback');
      // Fallback: Verwende die document.value Daten
      payload = { ...payload, ...document.value };
    }
    const createdDocument = await documentService.create(payload);
    createdDocumentId.value = createdDocument?.id || createdDocumentId.value;
    await nextTick();
    
    // Nach erfolgreicher Dokumentenerstellung: ggf. gepufferte Dateien hochladen und warten
    try {
      if (documentUploadRef?.value && typeof documentUploadRef.value.uploadBufferedFiles === 'function') {
        await documentUploadRef.value.uploadBufferedFiles();
      }
      // Fallback: auch sessionStorage-Puffer (Base64) hochladen, falls vorhanden
      try {
        const key = 'jayna_doc_buffer';
        const arr = JSON.parse(sessionStorage.getItem(key) || '[]');
        for (const entry of arr) {
          try {
            const res = await fetch(entry.dataUrl);
            const blob = await res.blob();
            const file = new File([blob], entry.name || 'upload', { type: entry.type || 'application/octet-stream' });
            await documentService.upload(createdDocumentId.value, file);
          } catch {}
        }
        sessionStorage.removeItem(key);
      } catch {}
    } catch {}

    // Zusätzliche Absicherung: vorhandene __rawFile-Referenzen direkt hochladen
    try {
      if (createdDocumentId.value && Array.isArray(uploadedFiles.value)) {
        for (const f of uploadedFiles.value) {
          if (f && f.__rawFile instanceof File) {
            try {
              await documentService.upload(createdDocumentId.value, f.__rawFile);
            } catch {}
          }
        }
      }
    } catch {}

    // Zusätzlich: Dateien aus dem Select/Analyze-Flow (nur Blob-URLs) nachträglich hochladen
    try {
      if (createdDocumentId.value && Array.isArray(uploadedFiles.value)) {
        for (const f of uploadedFiles.value) {
          if (f && f.previewUrl && (!f.id || typeof f.id !== 'number')) {
            try {
              const resp = await fetch(f.previewUrl);
              const blob = await resp.blob();
              const fileName = f.fileName || f.name || 'upload.pdf';
              const mime = f.fileType || blob.type || 'application/octet-stream';
              const file = new File([blob], fileName, { type: mime });
              await documentService.upload(createdDocumentId.value, file);
            } catch (e) {
              // einzelner Upload darf die Gesamtspeicherung nicht verhindern
              console.error('Upload from previewUrl failed:', e);
            }
          }
        }
      }
    } catch {}

    // Verifikation: Prüfe, ob Dateien serverseitig vorhanden sind
    try {
      if (createdDocumentId.value) {
        const filesAfter = await documentService.listFiles(createdDocumentId.value);
        if (!filesAfter || filesAfter.length === 0) {
          AlertService.showError('Achtung: Dateien wurden nicht hochgeladen. Bitte erneut versuchen.');
        }
      }
    } catch {}
    
    // Nach erfolgreicher Dokumentenerstellung die bestätigten Aktionen ausführen
    await executeConfirmedActions(createdDocument);
    
    // Lösche temporäre Daten nach erfolgreichem Speichern
    DocumentTempStorageService.clearTempData();
    DocumentTempStorageService.clearSession();
    
    AlertService.showSuccess('Dokument erfolgreich erstellt');
    // Zur Übersicht navigieren
    router.push({ name: 'DocumentList' });
  } catch (error) {
    console.error('Error saving document:', error);
    AlertService.showError('Fehler beim Speichern des Dokuments: ' + (error.response?.data?.message || error.message));
  } finally {
    isSaving.value = false;
  }
};

// Neue Funktion zur Ausführung der bestätigten Aktionen
const executeConfirmedActions = async (createdDocument) => {
  try {
    // Lade die analysisData aus den Query-Parametern
    const analysisDataStr = route.query.analysisData;
    if (!analysisDataStr) {
      console.log('Keine analysisData gefunden, überspringe Aktionen');
      return;
    }
    
    const analysisData = JSON.parse(analysisDataStr);
    const { confirmedActions, matchingEntities, suggestedNewEntities, deviations } = analysisData;
    
    console.log('Führe bestätigte Aktionen aus für Dokument:', createdDocument.id);
    
    // 1. Verknüpfungen erstellen
    if (confirmedActions.relations && confirmedActions.relations.length > 0) {
      console.log('Erstelle Verknüpfungen:', confirmedActions.relations);
      for (const entityId of confirmedActions.relations) {
        const entity = matchingEntities.find(e => e.id === entityId);
        if (entity) {
          await createRelation(createdDocument, entity);
        }
      }
    }
    
    // 2. Neue Entities anlegen (hier würde die Logik für das Erstellen neuer Entitäten stehen)
    if (confirmedActions.newEntities && confirmedActions.newEntities.length > 0) {
      console.log('Erstelle neue Entitäten:', confirmedActions.newEntities);
      for (const entityType of confirmedActions.newEntities) {
        const entity = suggestedNewEntities.find(e => e.type === entityType);
        if (entity) {
          console.log('Neuen Datensatz anlegen:', entity);
          // Hier würde die Logik zum Erstellen neuer Entitäten stehen
          // await createNewEntity(entity);
        }
      }
    }
    
    // 3. Bestehende Entities aktualisieren (hier würde die Logik für Updates stehen)
    if (confirmedActions.updates && confirmedActions.updates.length > 0) {
      console.log('Aktualisiere bestehende Entitäten:', confirmedActions.updates);
      for (const deviationId of confirmedActions.updates) {
        const deviation = deviations.find(d => d.id === deviationId);
        if (deviation) {
          console.log('Entity aktualisieren:', deviation);
          // Hier würde die Logik zum Aktualisieren bestehender Entitäten stehen
          // await updateEntity(deviation);
        }
      }
    }
    
    console.log('Alle bestätigten Aktionen erfolgreich ausgeführt');
  } catch (error) {
    console.error('Fehler beim Ausführen der bestätigten Aktionen:', error);
    AlertService.showError('Fehler beim Ausführen der bestätigten Aktionen: ' + error.message);
  }
};

// Hilfsfunktion zum Erstellen von Verknüpfungen
const createRelation = async (document, entity) => {
  try {
    // Für jetzt unterstützen wir nur Contact-Verknüpfungen
    if (entity.type !== 'contact') {
      console.warn(`Verknüpfungstyp ${entity.type} wird noch nicht unterstützt`);
      return;
    }
    
    const relationConfig = CentralRelationService.getRelationConfig('document', entity.type);
    if (!relationConfig) {
      console.warn(`Keine Verknüpfungskonfiguration gefunden für document-${entity.type}`);
      return;
    }
    
    // Erstelle das Payload für die Verknüpfung
    const relationData = {
      [entity.type]: { id: entity.id }
    };
    
    const newRelationPayload = {
      entityName: entity.type,
      relationData: relationData
    };
    
    // API-Call zum Erstellen der Verknüpfung
    const api = await import('@/shared/config/axios');
    const result = await api.default.post(`/api/documents/${document.id}/relate`, newRelationPayload);
    
    if (result && result.data) {
      console.log(`Verknüpfung erfolgreich erstellt zwischen Dokument ${document.id} und ${entity.type} ${entity.id}`);
    }
  } catch (error) {
    console.error(`Fehler beim Erstellen der Verknüpfung mit ${entity.type} ${entity.id}:`, error);
    throw error;
  }
};

const goBackToAnalyze = () => {
  // Speichere temporäre Daten vor dem Zurückgehen
  saveTempData();
  
  // Zurück zur Analyze-Ansicht mit aktualisierten Dateien (inkl. previewUrl)
  const q = { ...route.query };
  if (uploadedFiles.value && uploadedFiles.value.length > 0) {
    q.files = JSON.stringify(uploadedFiles.value.map(file => ({
      id: file.id,
      fileName: file.fileName || file.name,
      fileSize: file.fileSize || file.size,
      fileType: file.fileType || file.type,
      uploadDate: file.uploadDate || null,
      previewUrl: file.previewUrl || null
    })));
  }
  router.push({ name: 'DocumentAnalyze', query: q });
};

// Temporäre Speicherung für den Dokumentenerstellungsprozess
const saveTempData = () => {
  const tempData = {
    document: document.value,
    selectedMainCategory: selectedMainCategory.value,
    selectedGrouping: selectedGrouping.value,
    selectedDocumentType: selectedDocumentType.value,
    formConfig: formConfig.value,
    formName: formName.value,
    uploadedFiles: uploadedFiles.value
  };
  
  DocumentTempStorageService.saveStepData('create', tempData);
};

const loadTempData = () => {
  const tempData = DocumentTempStorageService.loadStepData('create');
  if (tempData) {
    console.log('Lade temporäre Daten aus Create-Schritt:', tempData);
    
    // Stelle Dokumentendaten wieder her
    if (tempData.document) {
      document.value = tempData.document;
    }
    
    // Stelle Kategorien wieder her
    if (tempData.selectedMainCategory) {
      selectedMainCategory.value = tempData.selectedMainCategory;
    }
    if (tempData.selectedGrouping) {
      selectedGrouping.value = tempData.selectedGrouping;
    }
    if (tempData.selectedDocumentType) {
      selectedDocumentType.value = tempData.selectedDocumentType;
    }
    
    // Stelle Formular-Konfiguration wieder her
    if (tempData.formConfig) {
      formConfig.value = tempData.formConfig;
    }
    if (tempData.formName) {
      formName.value = tempData.formName;
    }
    
    // Stelle hochgeladene Dateien wieder her
    if (tempData.uploadedFiles) {
      uploadedFiles.value = tempData.uploadedFiles;
      console.log('Dateien aus temporären Daten geladen:', uploadedFiles.value.length, 'Dateien');
    }
  }
};

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

// Hilfsfunktion zur Ermittlung der korrekten groupType ID basierend auf der Auswahl
const getGroupTypeIdForSelection = () => {
  if (!selectedMainCategory.value || !selectedGrouping.value || !selectedDocumentType.value) {
    console.log('getGroupTypeIdForSelection: Fehlende Kategorie-Daten');
    return null;
  }

  console.log('getGroupTypeIdForSelection - Ausgewählte Kategorien:', {
    mainCategory: selectedMainCategory.value,
    grouping: selectedGrouping.value,
    documentType: selectedDocumentType.value
  });

  // Direkte Mapping basierend auf den tatsächlichen groupType IDs aus der Datenbank
  const groupTypeMapping = {
    // SEPA-Mandat spezifisch
    'sepa-mandates': 289,
    'SEPA-Mandat': 289,
    
    // Rechnung
    'invoices': 290,
    'Rechnung': 290,
    
    // Zahlungsnachweis
    'payment-proofs': 294,
    'Zahlungsnachweis': 294,
    
    // Kontoauszug
    'account-statements': 286,
    'Kontoauszug': 286,
    
    // Kreditkartenabrechnung
    'credit-card-statements': 288,
    'Kreditkartenabrechnung': 288,
    
    // Kreditvertrag
    'loan-contracts': 287,
    'Kreditvertrag': 287,
    
    // Quittung
    'receipts': 291,
    'Quittung': 291,
    
    // Angebot
    'quotations': 292,
    'Angebot': 292,
    
    // Mahnung
    'dunning-letters': 293,
    'Mahnung': 293,
    
    // Versicherungspolice
    'insurance-policies': 295,
    'Versicherungspolice': 295,
    
    // Schadensmeldung
    'claim-reports': 296,
    'Schadensmeldung': 296,
    
    // Leistungsbescheid
    'benefit-statements': 297,
    'Leistungsbescheid': 297,
    
    // Gasrechnung
    'gas-bills': 334,
    'Gasrechnung': 334,
    
    // Stromrechnung
    'electricity-bills': 332,
    'Stromrechnung': 332,
    
    // Wasserrechnung
    'water-bills': 333,
    'Wasserrechnung': 333,
    
    // Internet/Telefonrechnung
    'internet-phone-bills': 335,
    'Internet/Telefonrechnung': 335,
    
    // Fahrzeugbrief
    'vehicle-titles': 304,
    'Fahrzeugbrief': 304,
    
    // Fahrzeugschein
    'vehicle-registrations': 305,
    'Fahrzeugschein': 305,
    
    // TÜV-Bescheinigung
    'tuv-certificates': 306,
    'TÜV-Bescheinigung': 306,
    
    // Wartungsheft
    'maintenance-records': 307,
    'Wartungsheft': 307,
    
    // Werkstattrechnung
    'workshop-invoices': 308,
    'Werkstattrechnung': 308
  };

  // Versuche verschiedene Schlüssel zu verwenden
  const possibleKeys = [
    selectedDocumentType.value.name,
    selectedDocumentType.value.translationKey?.split('.').pop(),
    selectedDocumentType.value.id?.toString()
  ];

  console.log('getGroupTypeIdForSelection - Mögliche Schlüssel:', possibleKeys);

  for (const key of possibleKeys) {
    if (key && groupTypeMapping[key]) {
      console.log(`getGroupTypeIdForSelection - Gefunden für Schlüssel "${key}": ${groupTypeMapping[key]}`);
      return groupTypeMapping[key];
    }
  }

  // Fallback: Verwende eine Standard-Dokument groupType ID
  console.warn('getGroupTypeIdForSelection - Keine spezifische groupType ID gefunden, verwende Fallback 294');
  return 294; // Standard-Dokument groupType ID
};

// Hilfsfunktion zur Ermittlung des korrekten Formnamens basierend auf der Auswahl
const getFormNameForSelection = () => {
  if (!selectedMainCategory.value || !selectedGrouping.value || !selectedDocumentType.value) {
    return 'document';
  }

  const mainCategoryName = selectedMainCategory.value.translationKey.split('.').pop();
  const groupingName = selectedGrouping.value.translationKey.split('.').pop();
  const documentTypeName = selectedDocumentType.value.translationKey.split('.').pop();

  return `document-${mainCategoryName}-${groupingName}-${documentTypeName}`;
};

const handleAddRelation = () => {
  // TODO: Implementierung für Verknüpfung hinzufügen in Create-Ansicht
  AlertService.showInfo('Verknüpfungen können erst nach dem Speichern des Dokuments hinzugefügt werden.');
};

const handleFileUploaded = (uploadedFile) => {
  if (uploadedFile) {
    showPreview.value = true;
    AlertService.showSuccess('Datei erfolgreich hochgeladen');
  }
};

const handleFileRemoved = (file) => {
  try {
    documentUploadRef.value?.removeFile(file);
    AlertService.showSuccess('Datei erfolgreich entfernt');
  } catch {}
};

const handleDownloadFile = async (file) => {
  try {
    // In der Create-Ansicht können wir noch nicht herunterladen
    AlertService.showInfo('Dateien können erst nach dem Speichern des Dokuments heruntergeladen werden.');
  } catch (error) {
    console.error('Download error:', error);
    AlertService.showError('Fehler beim Herunterladen der Datei');
  }
};

const handleViewFile = (file) => {
  showPreview.value = true;
  if (file) {
    previewRef.value?.setPageByName(file.fileName || file.name);
  }
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

const cancelDocumentCreation = () => {
  // Lösche temporäre Daten beim Abbrechen
  DocumentTempStorageService.clearTempData();
  DocumentTempStorageService.clearSession();
  
  // Direkt zur Dokumente-Übersicht zurückkehren
  router.push({ name: 'DocumentList' });
};

onMounted(() => {
  
  
  // Lade zuerst Kategorien aus Query-Parametern (hat Priorität)
  loadFormDefinitionFromQuery();
  
  // Dann lade temporäre Daten, falls keine Query-Parameter vorhanden sind
  if (!route.query.main || !route.query.group || !route.query.type) {
    
    loadTempData();
  } else {
    
  }
});
</script>


