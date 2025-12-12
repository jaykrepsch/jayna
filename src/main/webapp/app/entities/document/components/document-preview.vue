<template>
  <div class="h-full flex flex-col">
    <!-- Header -->
    <div class="flex items-center justify-between mb-4">
      <h2 class="text-lg font-semibold text-gray-900">Dokumenten-Vorschau</h2>
      <button @click="$emit('close')" class="text-gray-600 hover:text-gray-800 rounded-full p-2 hover:bg-gray-200" title="Zuklappen">
        <ChevronDoubleRightIcon class="w-6 h-6" />
      </button>
    </div>

    <!-- Content (scrollt) -->
    <div class="flex-1 min-h-0 flex flex-col pr-2" ref="contentRef">
      <div v-if="!hasFiles" class="text-center text-gray-500 py-12">
        <svg class="w-16 h-16 mx-auto mb-4 text-gray-300" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"></path>
        </svg>
        Keine Dokumente vorhanden
      </div>

      <div v-else class="flex-1 min-h-0 flex flex-col space-y-3">
        <!-- Datei-Vorschau: skaliert auf volle Breite, nur vertikales Scrollen -->
        <div class="bg-gray-50 border rounded-md p-2 flex-1 min-h-0 overflow-y-auto overflow-x-hidden">
          <component :is="previewTag" v-bind="previewAttrs" class="w-full rounded" />
          <div v-if="!canPreview" class="w-full h-full bg-white border rounded flex items-center justify-center text-gray-400">
            <span class="text-sm">Vorschau nicht verfügbar</span>
          </div>
        </div>
      </div>
    </div>

    <!-- Pager unten, fix durch Flex (kein sticky nötig) -->
    <div class="flex-none border-t pt-3 mt-3 bg-white">
      <div class="flex items-center justify-between text-sm">
        <button 
          class="px-2 py-1 text-gray-700 hover:text-gray-900 rounded hover:bg-gray-100 disabled:text-gray-300"
          :disabled="currentPage <= 1"
          @click="prevPage"
          title="Vorherige Datei"
        >
          <ChevronLeftIcon class="w-6 h-6" />
        </button>

        <div class="text-gray-700 select-none font-medium">{{ currentPage }} / {{ totalPages }}</div>

        <div class="flex items-center gap-2">
          <button 
            class="px-2 py-1 text-gray-700 hover:text-gray-900 rounded hover:bg-gray-100 disabled:text-gray-300"
            :disabled="currentPage === 1 || totalPages === 0"
            @click="goFirst"
            title="Zur ersten Datei"
          >
            <ArrowUpCircleIcon class="w-6 h-6" />
          </button>
          <button 
            class="px-2 py-1 text-gray-700 hover:text-gray-900 rounded hover:bg-gray-100 disabled:text-gray-300"
            :disabled="currentPage >= totalPages"
            @click="nextPage"
            title="Nächste Datei"
          >
            <ChevronRightIcon class="w-6 h-6" />
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch, defineExpose } from 'vue';
import { ChevronLeftIcon, ChevronRightIcon, ArrowUpCircleIcon, ChevronDoubleRightIcon } from '@heroicons/vue/24/outline';

const props = defineProps({
  files: { type: Array, default: () => [] }
});

const contentRef = ref(null);
const currentPage = ref(1);

const hasFiles = computed(() => Array.isArray(props.files) && props.files.length > 0);
const totalPages = computed(() => (hasFiles.value ? props.files.length : 0));
const currentFile = computed(() => (hasFiles.value ? props.files[currentPage.value - 1] : null));

const currentFileName = computed(() => {
  const f = currentFile.value;
  return f ? (f.fileName || f.name || 'Unbenannt') : '';
});

// Vorschau-Tag/Attribute bestimmen (PDF/Images)
const fileType = computed(() => currentFile.value?.fileType || currentFile.value?.type || '');
const isImage = computed(() => /^(image\/jpeg|image\/png|image\/gif|image\/webp)$/i.test(fileType.value));
const isPdf = computed(() => /^application\/pdf$/i.test(fileType.value));
const canPreview = computed(() => isImage.value || isPdf.value);

const previewTag = computed(() => {
  if (isImage.value) return 'img';
  if (isPdf.value) return 'embed';
  return 'div';
});

const previewAttrs = computed(() => {
  const src = currentFile.value?.previewUrl;
  if (isImage.value) {
    return src
      ? { alt: currentFileName.value, src, style: 'width: 100%; height: auto; display: block;' }
      : { alt: currentFileName.value, style: 'width: 100%; height: auto; display: block;' };
  }
  if (isPdf.value) {
    const finalSrc = src ? `${src}#view=FitH&zoom=page-width` : undefined;
    return finalSrc
      ? { title: currentFileName.value, type: 'application/pdf', style: 'width: 100%; height: 100%; border: 0;', src: finalSrc }
      : { title: currentFileName.value, type: 'application/pdf', style: 'width: 100%; height: 100%; border: 0;' };
  }
  return {};
});

function prevPage() {
  if (currentPage.value > 1) {
    currentPage.value -= 1;
    scrollTop();
  }
}

function nextPage() {
  if (currentPage.value < totalPages.value) {
    currentPage.value += 1;
    scrollTop();
  }
}

function goFirst() {
  if (totalPages.value > 0) {
    currentPage.value = 1;
    scrollTop();
  }
}

function scrollTop() {
  if (contentRef.value) {
    contentRef.value.scrollTop = 0;
  }
}

// Bei Längenänderung auf Seite 1 springen
watch(() => props.files?.length, () => {
  currentPage.value = 1;
  scrollTop();
});

// Exponiere Helfer zum gezielten Setzen der Seite
function setPageByName(name) {
  if (!Array.isArray(props.files)) return;
  const idx = props.files.findIndex(f => (f.fileName || f.name) === name);
  if (idx >= 0) {
    currentPage.value = idx + 1;
    scrollTop();
  }
}

defineExpose({ setPageByName });
</script>

<style scoped>
</style>


