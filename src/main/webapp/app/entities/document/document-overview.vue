<template>
  <div>
    <div class="sm:flex sm:items-center">
      <div class="sm:flex-auto">
        <h1 class="text-xl font-semibold text-gray-900">
          {{ $t('jaynaApp.document.home.title') }}
        </h1>
      </div>
      <span
        class="inline-flex items-center justify-center rounded-md border border-transparent bg-green-600 px-2 py-1 text-sm font-medium text-white shadow-sm hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2 sm:w-auto cursor-pointer"
        @click="goCreate()"
      >
        <PlusIconOutline class="h-6 w-6" aria-hidden="true" />
      </span>
    </div>

    <div class="flex flex-col mt-8">
      <TableLayout>
             <template #head>
         <tr>
           <th scope="col" class="text-left px-3 py-3.5 text-sm font-semibold">Nr.</th>
                       <th scope="col" class="text-left px-3 py-3.5 text-sm font-semibold">
              <div>Bezeichnung</div>
              <div class="text-xs font-normal text-gray-500">Typ</div>
            </th>
            <th scope="col" class="text-left px-3 py-3.5 text-sm font-semibold">
              <div>Absender</div>
              <div class="text-xs font-normal text-gray-500">Nummer</div>
            </th>
           <th scope="col" class="text-left px-3 py-3.5 text-sm font-semibold">
             <div>Dokumententyp</div>
             <div class="text-xs font-normal text-gray-500">Größe</div>
           </th>
                                                          <th scope="col" class="text-left px-3 py-3.5 text-sm font-semibold">
             <div>Datum Dokument</div>
             <div class="text-xs font-normal text-gray-500">Datum Upload</div>
           </th>
           <th scope="col" class="text-center px-3 py-3.5 text-sm font-semibold">
             <div class="flex items-center justify-center">
               <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                 <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15.172 7l-6.586 6.586a2 2 0 102.828 2.828l6.414-6.586a4 4 0 00-5.656-5.656l-6.415 6.585a6 6 0 108.486 8.486L20.5 13"></path>
               </svg>
             </div>
             <div class="text-xs font-normal text-gray-500">Anhang</div>
           </th>
           <th scope="col" class="text-right px-3 py-3.5 text-sm font-semibold">Aktionen</th>
         </tr>
       </template>
             <template #body>
         <tr v-for="(doc, index) in documents" :key="doc.id" class="odd:bg-white even:bg-gray-100">
           <td class="px-3 py-4 text-sm font-medium">{{ index + 1 }}</td>
           <td class="px-3 py-4 text-sm">
             <div class="font-medium">{{ getDocumentTitle(doc) }}</div>
             <div class="text-xs text-gray-500">{{ getDocumentTypeName(doc) }}</div>
           </td>
           <td class="px-3 py-4 text-sm">
             <div>{{ getDocumentPartner(doc) }}</div>
             <div class="text-xs text-gray-500">{{ getDocumentNumber(doc) }}</div>
           </td>
           <td class="px-3 py-4 text-sm">
             <div>{{ getFileTypeDisplay(doc.fileType) }}</div>
             <div class="text-xs text-gray-500">{{ formatFileSize(doc.fileSize) }}</div>
           </td>
           <td class="px-3 py-4 text-sm">
             <div>{{ getDocumentDate(doc) }}</div>
                            <div class="text-xs text-gray-500">
                 <span v-if="doc.uploadDate">{{ d(new Date(doc.uploadDate), 'short', store.getters['translation/currentLanguage']) }}</span>
                 <span v-else>-</span>
               </div>
           </td>
           <td class="px-3 py-4 text-sm text-center">
             <div v-if="doc.fileName && doc.fileName.trim() !== ''" class="flex items-center justify-center">
               <svg class="w-5 h-5 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                 <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15.172 7l-6.586 6.586a2 2 0 102.828 2.828l6.414-6.586a4 4 0 00-5.656-5.656l-6.415 6.585a6 6 0 108.486 8.486L20.5 13"></path>
               </svg>
               <!-- Zähler vorübergehend deaktiviert -->
             </div>
             <div v-else class="text-gray-400">-</div>
           </td>
           <td class="text-right px-3 py-4">
             <div class="flex space-x-2 justify-end">
               <OverviewTableEditButton
                 :entity-id="doc.id"
                 entity-name="document"
               />
               <OverviewTableViewButton
                 :entity-id="doc.id"
                 entity-name="document"
               />

             </div>
           </td>
         </tr>
         <tr v-if="!isFetching && documents.length === 0">
           <td class="px-3 py-4 text-sm text-gray-500" colspan="7">{{ $t('jaynaApp.document.home.notFound') }}</td>
         </tr>
       </template>
      </TableLayout>
    </div>
  </div>
  
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useI18n } from 'vue-i18n';
import { useStore } from 'vuex';
import TableLayout from '@/core/layouts/TableLayout.vue';
import DocumentService from '@/services/document.service';
import AlertService from '@/services/alert.service';
import { PlusIcon as PlusIconOutline } from '@heroicons/vue/24/outline';
import OverviewTableEditButton from '@/components/buttons/overview-table-edit-button.vue';
import OverviewTableViewButton from '@/components/buttons/overview-table-view-button.vue';

const router = useRouter();
const { d, t } = useI18n();
const store = useStore();
const documents = ref([]);
const isFetching = ref(false);

const retrieveAllDocuments = () => {
  isFetching.value = true;
  new DocumentService()
    .retrieve({ page: 0, size: 50 }, true)
    .then(res => {
      documents.value = res.data;
    })
    .catch(err => {
      console.error('Error retrieving documents:', err);
      AlertService.showHttpError(this, err.response);
    })
    .finally(() => {
      isFetching.value = false;
    });
};

const formatFileSize = (bytes) => {
  if (!bytes || bytes === 0) return '0 Bytes';
  const k = 1024;
  const sizes = ['Bytes', 'KB', 'MB', 'GB'];
  const i = Math.floor(Math.log(bytes) / Math.log(k));
  return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
};

const getDocumentTypeName = (doc) => {
  // Zeige nur den Dokumententyp (GroupType), nicht die komplette Hierarchie
  if (doc.groupType?.name) {
    // Versuche zuerst eine Übersetzung für den GroupType zu finden
    const translationKey = `jaynaApp.document.type.${doc.groupType.name.toLowerCase().replace(/[^a-z0-9]/g, '-')}`;
    const translation = t(translationKey);
    if (translation !== translationKey) {
      return translation;
    }
    
    // Fallback: Verwende den Namen direkt
    return doc.groupType.name;
  }
  
  // Fallback: Verwende andere Felder
  if (doc.documentType) {
    return doc.documentType;
  }
  if (doc.fileType) {
    return getFileTypeDisplay(doc.fileType);
  }
  
  return 'Dokument';
};

const getFileTypeDisplay = (fileType) => {
  if (!fileType) return '-';
  
  // Mappe Dateitypen auf benutzerfreundliche Namen
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

const getDocumentDate = (doc) => {
  // Versuche ein Datum aus dem Dokument zu extrahieren
  if (doc.documentDate) {
    return d(new Date(doc.documentDate), 'short', store.getters['translation/currentLanguage']);
  }
  if (doc.issueDate) {
    return d(new Date(doc.issueDate), 'short', store.getters['translation/currentLanguage']);
  }
  if (doc.validFrom) {
    return d(new Date(doc.validFrom), 'short', store.getters['translation/currentLanguage']);
  }
  if (doc.lastModified) {
    return d(new Date(doc.lastModified), 'short', store.getters['translation/currentLanguage']);
  }
  if (doc.uploadDate) {
    return d(new Date(doc.uploadDate), 'short', store.getters['translation/currentLanguage']);
  }
  if (doc.createdDate) {
    return d(new Date(doc.createdDate), 'short', store.getters['translation/currentLanguage']);
  }
  
  return '-';
};

const getDocumentTitle = (doc) => {
  // Bezeichnung (label) hat Priorität vor title
  if (doc.label && doc.label.trim() !== '') {
    return doc.label;
  }
  // Falls kein Label, versuche title
  if (doc.title && doc.title.trim() !== '') {
    return doc.title;
  }
  
  // Wenn eine Beschreibung vorhanden ist, verwende sie
  if (doc.description && doc.description.trim() !== '') {
    return doc.description;
  }
  
  // Wenn ein Dateiname vorhanden ist, verwende ihn
  if (doc.fileName && doc.fileName.trim() !== '') {
    return doc.fileName;
  }
  
  // Generiere einen Titel aus der groupType Hierarchie
  if (doc.groupType) {
    const parts = [];
    
    // Füge Category hinzu
    if (doc.groupType.subCategoryGroup?.subCategory?.category?.name) {
      parts.push(doc.groupType.subCategoryGroup.subCategory.category.name);
    }
    
    // Füge SubCategory hinzu
    if (doc.groupType.subCategoryGroup?.subCategory?.name) {
      parts.push(doc.groupType.subCategoryGroup.subCategory.name);
    }
    
    // Füge SubCategoryGroup hinzu
    if (doc.groupType.subCategoryGroup?.name) {
      parts.push(doc.groupType.subCategoryGroup.name);
    }
    
    // Füge GroupType hinzu
    if (doc.groupType.name) {
      parts.push(doc.groupType.name);
    }
    
    if (parts.length > 0) {
      return parts.join(' - ');
    }
  }
  
  // Fallback
  return 'Unbenanntes Dokument';
};

const getDocumentPartner = (doc) => {
  // Versuche verschiedene Partner-Felder
  if (doc.partner && doc.partner.trim() !== '') {
    return doc.partner;
  }
  if (doc.customerNumber && doc.customerNumber.trim() !== '') {
    return doc.customerNumber;
  }
  
  // Fallback basierend auf groupType
  if (doc.groupType?.name === 'Garage') {
    return 'Garagenvermieter';
  }
  if (doc.groupType?.name === 'SEPA-Mandat') {
    return 'Bank';
  }
  if (doc.groupType?.name === 'Zahlungsnachweis') {
    return 'Zahlungsempfänger';
  }
  
  return '-';
};

const getDocumentNumber = (doc) => {
  // Versuche verschiedene Nummer-Felder
  if (doc.documentNumber && doc.documentNumber.trim() !== '') {
    return doc.documentNumber;
  }
  if (doc.contractNumber && doc.contractNumber.trim() !== '') {
    return doc.contractNumber;
  }
  if (doc.realEstateNumber && doc.realEstateNumber.trim() !== '') {
    return doc.realEstateNumber;
  }
  if (doc.financeAccountNumber && doc.financeAccountNumber.trim() !== '') {
    return doc.financeAccountNumber;
  }
  if (doc.mobilityNumber && doc.mobilityNumber.trim() !== '') {
    return doc.mobilityNumber;
  }
  
  // Fallback basierend auf groupType
  if (doc.groupType?.name === 'Garage') {
    return `Garage-${doc.id}`;
  }
  if (doc.groupType?.name === 'SEPA-Mandat') {
    return `SEPA-${doc.id}`;
  }
  if (doc.groupType?.name === 'Zahlungsnachweis') {
    return `ZN-${doc.id}`;
  }
  
  return `Doc-${doc.id}`;
};

const goCreate = () => {
  router.push({ name: 'DocumentSelect' });
};



onMounted(() => {
  retrieveAllDocuments();
});
</script>
