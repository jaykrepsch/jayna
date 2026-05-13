<template>
  <!-- <div class="grid grid-cols-3 gap-4"> -->
  <div>
    <div
      v-for="(form, index) in formConfig.parts"
      :key="index"
      class="mt-10 sm:mt-4"
    >
      <FormPanel
        :title="getTitle(form.title, form)"
        :sub-title="form.subTitle ? $t(form.subTitle) : ''"
      >
        <template v-slot:content>
          <SimpleFormBuilder
            ref="formRefs"
            :fields="form.fields"
            :data="data"
            :form-name="effectiveFormName"
            :section-config="form"
          />
        </template>
      </FormPanel>

      <Divider v-if="index < formConfig.parts.length - 1" />
    </div>

    <div v-if="formConfig?.relations">
      <Divider />

      <div class="flex flex-col space-y-4">
        <h3 class="text-lg font-medium leading-6 text-gray-900">{{ getRelationsTitle() }}</h3>
        <RelationList
          :form-config="formConfig"
          :data="dataWithRelations"
          :editable="true"
          @delete="startDeletionProcess($event)"
          @bidirectional-relation-deleted="handleBidirectionalRelationDeleted"
        />

      </div>


    </div>



    <Modal
        :open="showRelationshipModal"
        @close="closeModal()"
      >
        <template #content>
          <div class="mt-3 text-center">
            <DialogTitle as="h3" class="text-center font-semibold leading-6 text-gray-700">{{ $t('jaynaApp.form-builder.titles.add-relationship') }}</DialogTitle>
            <div class="mt-6 grid grid-cols-2 gap-4">
              <div
                v-for="(relation, index) in formConfig?.relations"
                :key="index"
                @click="selectRelationType(relation)"
                :class="[selectedRelationType?.entityName === relation.entityName ? 'border-indigo-700 text-indigo-700' :
                    'border-gray-300 text-gray-700', 'rounded-lg border-lg border-2 hover:border-indigo-700 hover:text-indigo-700 p-12 flex items-center justify-center cursor-pointer']"
              >
                <div class="text-center">{{ $t(`jaynaApp.base${relation.entityName.charAt(0).toUpperCase() + relation.entityName.slice(1)}.base-title`) }}</div>
              </div>
            </div>
            <div
              v-if="selectedRelationType"
              class="mt-5"
            >
              <Select
                v-if="availableRelations?.length"
                ref="refRelationSelect"
                :data="availableRelations"
                display-field="label"
              />
              <div v-else>
                {{ $t('jaynaApp.form-builder.messages.no-entities-available') }}
              </div>
            </div>
          </div>

          <Divider class="mt-5" />

          <div class="flex flex-row gap-x-2 mt-5">
            <Button
              :text="$t('button.cancel')"
              type="secondary"
              @click.prevent="closeModal()"
            />
            <Button
              :text="$t('button.save')"
              @click.prevent="saveRelationship()"
            />
          </div>
        </template>
      </Modal>

    <DangerConfirmation
      :show="showConfirmationModal"
      :title-key="$t('jaynaApp.form-builder.titles.delete-relationship')"
      :content-key="$t('jaynaApp.form-builder.messages.delete-relationship')"
      @confirm="deleteRelation()"
      @close="showConfirmationModal = false; deletetionData = null"
    />
  </div>
</template>

<script setup>
import { reactive, computed, ref, watch, onMounted, defineEmits } from 'vue';

import FormPanel from '@/components/formpanel.vue';
import SInput from '@/components/forms/input.vue';
import Divider from '@/components/divider.vue';
import STextarea from '@/components/forms/textarea.vue';
import DatePicker from '@vuepic/vue-datepicker';
import Dropdown from '@/components/forms/dropdown.vue';
import Checkbox from '@/components/forms/checkbox.vue';
import AddButton from '@/components/buttons/add-button.vue';
import Modal from '@/components/modals/modal.vue';
import Select from '@/components/forms/select.vue';
import Button from '@/components/buttons/button.vue';
import DangerConfirmation from "@/components/modals/danger-confirmation.vue";
import RelationList from '@/components/RelationList.vue';
import RelationDropdown from '@/components/forms/RelationDropdown.vue';
import SimpleFormBuilder from '@/components/SimpleFormBuilder.vue';


import { DialogTitle } from '@headlessui/vue'

import { required } from '@/shared/config/validators';

import useVuelidate from '@vuelidate/core';
import { useStore } from 'vuex';
import { useGetLabel, useGetService } from '@/composables/utils';
import { useI18n } from 'vue-i18n';
import i18n from '@/shared/config/i18n';
import api from '@/services/api.service';


const { t, te } = useI18n();
const store = useStore();
const emit = defineEmits(['relation-saved']);

const props = defineProps({
  formConfig: Object,
  entityName: String,
  formName: String,
  data: Object,
  groupType: Object
});



const formRefs = ref([]);
const modalFormName = ref(null);
const showFormModal = ref(false);
const formParts = reactive([]);
const relations = reactive({});
const showConfirmationModal = ref(false);
const service = ref(null);
const v$ = useVuelidate();
let deletetionData = {};
let refreshKey = ref(0);
const showRelationshipModal = ref(false);

const selectedRelationType = ref(null);
const availableRelations = ref(null);
const refRelationSelect = ref(null);
const documentRelations = ref([]);

// Initialisiere die Beziehungen
onMounted(() => {
  initializeRelations();
});

// Watch für Änderungen in props.data
watch(() => props.data, () => {
  initializeRelations();
}, { deep: true });

const initializeRelations = () => {
  if (props.formConfig?.relations) {
    for (const relation of props.formConfig.relations) {
      relations[relation.relationName] = props.data && props.data[relation.relationName] ? props.data[relation.relationName] : [];
    }
  }
};

const getRelationConfigByEntityName = (entityName) => {
  const config = props.formConfig?.relations.find(i => i.entityName === entityName);
  return config;
};

const startDeletionProcess = (event) => {
  deletetionData.relationship = event.relation;
  deletetionData.relationConfig = event.relationConfig;
  showConfirmationModal.value = true;
};

const handleBidirectionalRelationDeleted = (relationId) => {
  
  
  
  // Entferne die Verknüpfung aus allen relevanten Listen
  if (props.formConfig?.relations) {
    for (const relationConfig of props.formConfig.relations) {
      if (relations[relationConfig.relationName]) {
        relations[relationConfig.relationName] = relations[relationConfig.relationName].filter(
          rel => rel.id !== relationId
        );
      }
    }
  }
  
  // Lade die Verknüpfungen neu, um UI zu aktualisieren
  loadRelations();
};

const deleteRelation = async () => {
  showConfirmationModal.value = false;
  
  try {
    // Prüfe, ob die notwendigen Daten vorhanden sind
    if (!deletetionData.relationship) {
      return;
    }
    
    // Wenn relationConfig fehlt, versuche es zu rekonstruieren
    let relationConfig = deletetionData.relationConfig;
    if (!relationConfig && props.formConfig?.relations) {
      // Versuche die relationConfig basierend auf der relationship zu finden
      for (const config of props.formConfig.relations) {
        if (deletetionData.relationship[config.entityName]) {
          relationConfig = config;
          break;
        }
      }
    }
    
    if (!relationConfig) {
      // Trotzdem versuchen zu löschen, indem wir die Entity aus der relationship extrahieren
      const entityName = Object.keys(deletetionData.relationship).find(key => 
        key !== 'id' && key !== 'relationConfig' && typeof deletetionData.relationship[key] === 'object'
      );
      
      if (entityName) {
        relationConfig = { entityName };
      } else {
        return;
      }
    }
    
    // Sende Löschanfrage an das Backend
    const payload = {
      entityName: relationConfig.entityName,
      relationData: deletetionData.relationship
    };
    
    
    
    // Bestimme die Entity-Art basierend auf dem Form-Namen oder der Route
    const entityType = getEntityTypeFromFormName(effectiveFormName.value);
    const entityName = entityType.toLowerCase();
    
    // Korrigiere die URL für die verschiedenen Entity-Typen
    let apiUrl;
    switch (entityName) {
      case 'realestate':
        apiUrl = `/api/realestates/${props.data.id}/relate`;
        break;
      case 'financeaccount':
        apiUrl = `/api/financeaccounts/${props.data.id}/relate`;
        break;
      default:
        apiUrl = `/api/${entityName}s/${props.data.id}/relate`;
    }
    
    
    
    try {
      const response = await api.delete(apiUrl, { data: payload });
    } catch (error) {
      
    }
    
    // Bei Backend-Fehlern trotzdem die Verknüpfung aus der lokalen Liste entfernen
    if (deletetionData.relationship && deletetionData.relationship.id) {
      
      
      // Entferne die Verknüpfung aus allen relevanten Listen
      if (props.formConfig?.relations) {
        for (const relationConfig of props.formConfig.relations) {
          if (relations[relationConfig.relationName]) {
            relations[relationConfig.relationName] = relations[relationConfig.relationName].filter(
              rel => rel.id !== deletetionData.relationship.id
            );
          }
        }
      }
    }
    
    // Lade die Verknüpfungen neu
    try {
      await loadRelations();
    } catch (reloadError) {
      
    }
    
    // Emit event für UI-Update
    emit('relation-saved', {});
  } catch (error) {
    
    // Bei Backend-Fehlern trotzdem die Verknüpfung aus der lokalen Liste entfernen
    if (deletetionData.relationship && deletetionData.relationship.id) {
      
      
      // Entferne die Verknüpfung aus allen relevanten Listen
      if (props.formConfig?.relations) {
        for (const relationConfig of props.formConfig.relations) {
          if (relations[relationConfig.relationName]) {
            relations[relationConfig.relationName] = relations[relationConfig.relationName].filter(
              rel => rel.id !== deletetionData.relationship.id
            );
          }
        }
      }
    }
    
    // Auch bei Fehler die Verknüpfungen neu laden, um UI zu aktualisieren
    try {
      await loadRelations();
    } catch (reloadError) {
      
    }
    
    // Optional: Zeige dem Nutzer eine Fehlermeldung an
  }
};



const getData = (() => {
  let data = {};

  try {
    if (!formRefs.value || formRefs.value.length === 0) {
      console.warn('No form references found');
      return data;
    }

    formRefs.value.forEach((form, index) => {
      if (form && typeof form.getData === 'function') {
        const formData = form.getData();
        console.log(`Form ${index} data:`, formData);
        data = Object.assign(data, formData);
      } else {
        console.warn(`Form ${index} is not valid or getData method not found`);
      }
    });

    // Verknüpfungen NICHT in die Daten einfügen, da sie separat verwaltet werden
    // Die Verknüpfungen werden über die /relate API verwaltet

    // ID und groupType übernehmen, falls vorhanden
    if (props.data && props.data.id) {
      data.id = props.data.id;
    }
    if (props.groupType) {
      data.groupType = props.groupType;
    }

    console.log('Final getData result:', data);
    return data;
  } catch (error) {
    console.error('Error in getData:', error);
    return data;
  }
});

const isValid = () => {
  try {
    if (!formRefs.value || formRefs.value.length === 0) {
      console.warn('No form references found for validation');
      return false;
    }

    for (let i = 0; i < formRefs.value.length; i++) {
      const form = formRefs.value[i];
      if (form && typeof form.isValid === 'function') {
        if (!form.isValid()) {
          console.log(`Form ${i} validation failed`);
          return false;
        }
      } else {
        console.warn(`Form ${i} is not valid or isValid method not found`);
      }
    }

    console.log('All forms validation passed');
    return true;
  } catch (error) {
    console.error('Error in form validation:', error);
    return false;
  }
};

const hasRelationships = computed(() => {
  refreshKey.value;
  for (const relation of props.formConfig.relations) {
    if (relations[relation.relationName]?.length > 0) {
      return true;
    }
  }
  return false;
});

const closeModal = () => {
  showRelationshipModal.value = false;
  selectedRelationType.value = null;
};

const loadRelations = async () => {
  if (!props.formConfig?.relations || !props.data?.id) {
    return;
  }

  try {
    const serviceModule = await useGetService(props.entityName || getSourceEntityType());
    const resolvedService = serviceModule?.default;
    if (!resolvedService || typeof resolvedService.find !== 'function') {
      return;
    }

    const result = await resolvedService.find(props.data.id, { relations: true });

    const contractData = result || result?.data;

    if (contractData) {
      props.formConfig.relations.forEach(relation => {
        const relationData = contractData[relation.relationName];
        if (relationData && Array.isArray(relationData) && relationData.length > 0) {
          relations[relation.relationName] = relationData;
        } else {
          relations[relation.relationName] = [];
        }
      });
    } else {
      props.formConfig.relations.forEach(relation => {
        relations[relation.relationName] = [];
      });
    }
  } catch (error) {
    props.formConfig?.relations?.forEach(relation => {
      relations[relation.relationName] = [];
    });
  }
};

const selectRelationType = async (relation) => {
  selectedRelationType.value = relation;
  availableRelations.value = null;

  try {
    const service = await useGetService(relation.entityName);
    if (!service?.default) {
      availableRelations.value = [];
      return;
    }
    if (typeof service.default.query !== 'function') {
      availableRelations.value = [];
      return;
    }

    const result = await service.default.query({
      page: 0,
      size: 1000,
      sort: ['id,desc']
    });

    if (result?.data) {
      // Filtere den aktuellen Datensatz aus, damit er nicht mit sich selbst verknüpft werden kann
      availableRelations.value = result.data.filter(item => item.id !== props.data?.id);
    } else {
      availableRelations.value = [];
    }
  } catch (error) {
    
    // Fallback: leere Liste
    availableRelations.value = [];
  }
};

const saveRelationship = async () => {
  if (!selectedRelationType.value || !refRelationSelect.value?.selected) {
    console.warn('Keine Verknüpfung ausgewählt');
    return;
  }

  try {
    const selectedRelation = refRelationSelect.value.selected;
    const relationConfig = props.formConfig?.relations.find(e => e.entityName === selectedRelationType.value.entityName);

    if (!relationConfig) {
      return;
    }

    // Erstelle das Payload für das Backend. Die Struktur hängt davon ab,
    // wie die Join-Tabelle im Backend aufgebaut ist. In unserem Fall
    // (z.B. ContractRealestate) enthält sie ein Feld 'realestate'.
    const relationData = {
      [selectedRelationType.value.entityName]: { id: selectedRelation.id }
    };

    const newRelationPayload = {
      entityName: selectedRelationType.value.entityName,
      relationData: relationData
    };
    
    

    // Bestimme die Entity-Art basierend auf dem Form-Namen oder der Route
    const entityType = getEntityTypeFromFormName(effectiveFormName.value);
    const entityName = entityType.toLowerCase();
    
    // Korrigiere die URL für die verschiedenen Entity-Typen
    let apiUrl;
    switch (entityName) {
      case 'realestate':
        apiUrl = `/api/realestates/${props.data.id}/relate`;
        break;
      case 'financeaccount':
        apiUrl = `/api/financeaccounts/${props.data.id}/relate`;
        break;
      default:
        apiUrl = `/api/${entityName}s/${props.data.id}/relate`;
    }
    
    const result = await api.post(apiUrl, newRelationPayload);

    if (result && result.data) {
      // Backend sendet den gesamten Vertrag mit allen Relationen zurück
      Object.keys(relations).forEach(key => {
        relations[key] = result.data[key] || [];
      });
      // Informiere die Elternkomponente über die erfolgreiche Speicherung und übergib die neuen Daten
      emit('relation-saved', result.data);
    }

    closeModal();
    refreshKey.value++;
  } catch (error) {
  }
};

const getTitle = (title, formSection = null) => {
  if (!title) return '';
  
  // Versuche die Übersetzung direkt
  const translation = t(title);
  
  // Wenn die Übersetzung nicht dem Schlüssel entspricht, wurde sie gefunden
  if (translation !== title) {
    return translation;
  }
  
  // Prüfe Base-Entity-Übersetzung für Abschnittstitel
  if (title.includes('base')) {
    // Bestimme die Base-Entity basierend auf dem Form-Namen
    const entityType = getEntityTypeFromFormName(effectiveFormName.value);
    const baseEntityKey = title.replace('baseContract', `base${entityType}`);
    const baseEntityTranslation = t(baseEntityKey);
    if (baseEntityTranslation !== baseEntityKey) {
      return baseEntityTranslation;
    }
  }
  
  // Fallback: Verwende den Titel selbst
  return title;
};

// Hilfsfunktion um Entity-Typ aus Form-Namen zu extrahieren
const getEntityTypeFromFormName = (formName) => {
  if (formName.startsWith('contract-')) return 'Contract';
  if (formName.startsWith('contact-')) return 'Contact';
  if (formName.startsWith('mobility-')) return 'Mobility';
  if (formName.startsWith('realestate-')) return 'Realestate';
  if (formName.startsWith('financeaccount-')) return 'Financeaccount';
  if (formName.startsWith('document-')) return 'Document';
  return 'Contract'; // Fallback
};

// Fallback für formName, falls nicht gesetzt
const effectiveFormName = computed(() => {
  let formName = '';
  
  // Falls formName ein Objekt ist, nimm die Eigenschaft 'formName'
  if (typeof props.formName === 'object' && props.formName !== null) {
    formName = props.formName.formName || '';
  }
  // Falls formName ein String ist, verwende es
  else if (typeof props.formName === 'string' && props.formName) {
    formName = props.formName;
  }
  // IGNORIERE groupType.formName aus der Datenbank - verwende nur die Hierarchie-basierte Logik
  // Der formName sollte bereits korrekt durch die view.vue/update.vue gesetzt sein
  // Fallback auf entityName
  else if (props.entityName) {
    formName = props.entityName;
  }
  
  // Debug-Log entfernt - formName wird jetzt korrekt durch Hierarchie-Logik gesetzt
  
  // Stelle sicher, dass wir einen String haben
  return typeof formName === 'string' ? formName : '';
});

// Kombiniere data mit relations für die RelationList
const dataWithRelations = computed(() => {
  if (!props.data) return {};
  
  const combinedData = { ...props.data };
  
  // Füge die relations zu den Daten hinzu
  Object.keys(relations).forEach(key => {
    combinedData[key] = relations[key];
  });
  
  return combinedData;
});

const getRelationsTitle = () => {
  // Versuche zuerst die spezifische Übersetzung
  const specificKey = `jaynaApp.${effectiveFormName.value}.relations-title`;
  const specificTranslation = t(specificKey);
  if (specificTranslation !== specificKey) {
    return specificTranslation;
  }
  
  // Fallback auf Base-Entity
  const entityType = getEntityTypeFromFormName(effectiveFormName.value);
  const baseKey = `jaynaApp.base${entityType}.relations.title`;
  const baseTranslation = t(baseKey);
  if (baseTranslation !== baseKey) {
    return baseTranslation;
  }
  
  // Finaler Fallback
  return 'Verknüpfungen';
};





const getSourceEntityType = () => {
  // Bestimme den Entity-Typ basierend auf dem Form-Namen
  const formName = effectiveFormName.value;
  if (formName.startsWith('document-')) return 'document';
  if (formName.startsWith('contract-')) return 'contract';
  if (formName.startsWith('contact-')) return 'contact';
  if (formName.startsWith('realestate-')) return 'realestate';
  if (formName.startsWith('financeaccount-')) return 'financeaccount';
  if (formName.startsWith('mobility-')) return 'mobility';
  return 'document'; // Fallback
};



defineExpose({ getData, isValid });

// Reaktiver Watcher für Validierung entfernt - Button soll immer aktiv sein

onMounted(async () => {
  await loadRelations();
});

</script>

<style>
.required::after {
  content: "*";
}

.date-picker {
  font-size: 0.875rem;
}

</style>
