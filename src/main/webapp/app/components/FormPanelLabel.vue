<template>
  <div>
    <div 
      v-if="hasMultipleTranslations()"
      class="grid grid-cols-2"
    >
      <span :class="[isRequired ? 'required' : '']">{{ getMultiTranslation('-p1') }}</span>
      <span :class="[isRequired ? 'required' : '']">{{ getMultiTranslation('-p2') }}:</span>
    </div>
    <div v-else>
      <span :class="[isRequired ? 'required' : '']">{{ getLabel() }}:</span>
    </div>
  </div>
</template>

<script setup>

import { useGetLabel } from '@/composables/utils';
import { useI18n } from 'vue-i18n';
import { computed } from 'vue'

const { t, te } = useI18n();

const props = defineProps({
  field: {
    type: Object,
    required: true
  }, 
  formName: {
    type: String,
    required: true
  }
});

const isRequired = computed(() => {
  // Prüfe, ob das Feld als Pflichtfeld markiert ist
  return props.field?.rules?.includes('required');
});

const hasMultipleTranslations = () => {
  // Prüfe form-spezifische Übersetzungen
  if (te(`jaynaApp.${props.formName}.fields.${props.field.name}-p1`) && te(`jaynaApp.${props.formName}.fields.${props.field.name}-p2`)) {
    return true;
  }
  
  // Prüfe Base-Übersetzungen für alle Entitäten
  const baseKeys = [
    'baseContract',
    'baseContact', 
    'baseRealestate',
    'baseMobility',
    'baseFinanceaccount'
  ];
  
  for (const baseKey of baseKeys) {
    if (te(`jaynaApp.${baseKey}.fields.${props.field.name}-p1`) && te(`jaynaApp.${baseKey}.fields.${props.field.name}-p2`)) {
      return true;
    }
  }
  
  return false;
};

const getLabel = () => {
  // Prüfe zuerst, ob eine spezifische Übersetzung für das Formular existiert
  const formSpecificKey = `jaynaApp.${props.formName}.fields.${props.field.name}`;
  if (te(formSpecificKey)) {
    return t(formSpecificKey);
  }
  
  // Prüfe dann Base-Übersetzungen für alle Entitäten
  const baseKeys = [
    `jaynaApp.baseContract.fields.${props.field.name}`,
    `jaynaApp.baseContact.fields.${props.field.name}`,
    `jaynaApp.baseRealestate.fields.${props.field.name}`,
    `jaynaApp.baseMobility.fields.${props.field.name}`,
    `jaynaApp.baseFinanceaccount.fields.${props.field.name}`
  ];
  
  for (const baseKey of baseKeys) {
    if (te(baseKey)) {
      return t(baseKey);
    }
  }
  
  // Prüfe, ob das Feld eine eigene label-Eigenschaft hat
  if (props.field.label) {
    return props.field.label;
  }
  
  // Fallback: Verwende den Feldnamen
  return props.field.name;
}

const getMultiTranslation = (suffix) => {
  // Prüfe form-spezifische Übersetzung
  const formSpecificKey = `jaynaApp.${props.formName}.fields.${props.field.name}${suffix}`;
  if (te(formSpecificKey)) {
    return t(formSpecificKey);
  }
  
  // Prüfe Base-Übersetzungen für alle Entitäten
  const baseKeys = [
    'baseContract',
    'baseContact', 
    'baseRealestate',
    'baseMobility',
    'baseFinanceaccount'
  ];
  
  for (const baseKey of baseKeys) {
    const baseKeyFull = `jaynaApp.${baseKey}.fields.${props.field.name}${suffix}`;
    if (te(baseKeyFull)) {
      return t(baseKeyFull);
    }
  }
  
  return props.field.name + suffix;
}

</script>

<style scoped>
.required::after {
  content: " *";
  color: #e53e3e; /* rot */
  font-weight: bold;
}
</style>