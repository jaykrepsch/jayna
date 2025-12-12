<template>
  <div class="workflow-steps -mx-12 px-12">
    <h2 class="text-lg font-semibold text-gray-900 mb-6">Workflow-Schritte</h2>
    <div class="bg-white p-6 rounded-lg border">
      <div class="flex items-center justify-start space-x-8">
        <!-- Schritt 1: Dokument auswählen -->
        <div class="flex items-center space-x-4">
          <div 
            class="w-8 h-8 rounded-full flex items-center justify-center text-sm font-medium border-2"
            :style="{
              'min-width': '32px',
              'min-height': '32px',
              'display': 'flex',
              'align-items': 'center',
              'justify-content': 'center',
              'background-color': currentStep === 1 ? '#2563eb' : currentStep > 1 ? '#16a34a' : '#e5e7eb',
              'border-color': currentStep === 1 ? '#2563eb' : currentStep > 1 ? '#16a34a' : '#d1d5db'
            }"
          >
                         <span 
               class="font-medium"
               :style="{ 'color': currentStep === 1 ? '#ffffff' : currentStep > 1 ? '#ffffff' : '#374151' }"
             >{{ currentStep > 1 ? '✓' : '1' }}</span>
          </div>
          <span 
            :class="[
              'text-sm font-medium',
              currentStep === 1 ? 'text-blue-600' : 
              currentStep > 1 ? 'text-green-600' : 'text-gray-500'
            ]"
          >
            Dokument auswählen
          </span>
        </div>

        <!-- Pfeil -->
        <div class="w-8 h-1 bg-gray-300"></div>

        <!-- Schritt 2: Dokument analysieren -->
        <div class="flex items-center space-x-4">
          <div 
            class="w-8 h-8 rounded-full flex items-center justify-center text-sm font-medium border-2"
            :style="{
              'min-width': '32px',
              'min-height': '32px',
              'display': 'flex',
              'align-items': 'center',
              'justify-content': 'center',
              'background-color': currentStep === 2 ? '#2563eb' : currentStep > 2 ? '#16a34a' : '#e5e7eb',
              'border-color': currentStep === 2 ? '#2563eb' : currentStep > 2 ? '#16a34a' : '#d1d5db'
            }"
          >
                         <span 
               class="font-medium"
               :style="{ 'color': currentStep === 2 ? '#ffffff' : currentStep > 2 ? '#ffffff' : '#374151' }"
             >{{ currentStep > 2 ? '✓' : '2' }}</span>
          </div>
          <span 
            :class="[
              'text-sm font-medium',
              currentStep === 2 ? 'text-blue-600' : 
              currentStep > 2 ? 'text-green-600' : 'text-gray-500'
            ]"
          >
            Dokument analysieren
          </span>
        </div>

        <!-- Pfeil -->
        <div class="w-8 h-1 bg-gray-300"></div>

        <!-- Schritt 3: Dokument erstellen -->
        <div class="flex items-center space-x-4">
          <div 
            class="w-8 h-8 rounded-full flex items-center justify-center text-sm font-medium border-2"
            :style="{
              'min-width': '32px',
              'min-height': '32px',
              'display': 'flex',
              'align-items': 'center',
              'justify-content': 'center',
              'background-color': currentStep === 3 ? '#2563eb' : currentStep > 3 ? '#16a34a' : '#e5e7eb',
              'border-color': currentStep === 3 ? '#2563eb' : currentStep > 3 ? '#16a34a' : '#d1d5db'
            }"
          >
            <span 
              class="font-medium"
              :style="{ 'color': currentStep === 3 ? '#ffffff' : currentStep > 3 ? '#ffffff' : '#374151' }"
            >3</span>
          </div>
          <span 
            :class="[
              'text-sm font-medium',
              currentStep === 3 ? 'text-blue-600' : 
              currentStep > 3 ? 'text-green-600' : 'text-gray-500'
            ]"
          >
            Dokument erstellen
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  currentStep: {
    type: Number,
    required: true,
    default: 1
  }
});
</script>
