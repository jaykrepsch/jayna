<template>
  <div>
    <div class="flex flex-col">
      <div
        v-for="(form, index) in internalFormConfig?.parts"
        :key="index"
        class="flex flex-col space-y-4"
      >
        <EditableFormPanel 
          :config="form"
        >
          <template #content>
            <SimpleFormBuilder
              v-if="form.fields?.length > 0"
              :key="JSON.stringify(form.fields)"
              ref="formRefs"
              :fields="form.fields"
              :form-name="form.title"
              :section-config="form"
              :editable="true"
            />
          </template>

        </EditableFormPanel>
    
        <Divider />
      </div>

      <!-- Verknüpfungen Abschnitt -->
      <div v-if="internalFormConfig?.relations?.length" class="flex flex-col space-y-4">
        <EditableFormPanel 
          :config="{ title: getRelationsTitle() }"
        >
          <template #content>
            <RelationList
              :form-config="internalFormConfig"
              :data="{}"
              :group-type="null"
              :editable="true"
              @add-relationship="showAddRelationshipModal"
              @relation-saved="emit('relation-saved')"
            />
          </template>
        </EditableFormPanel>
      </div>

      <Divider />


    </div>
    <Modal 
      :open="showModal"
      :title="modalTitle"
      @close="closeModal()"
    >
      <template #content>
        <div class="flex flex-col space-y-6">
          <SimpleFormBuilder
            class="pt-4"
            ref="form"
            :fields="modalFields"
          />

          <div class="flex flex-row space-x-6">
            <Button
              type="secondary"
              :text="$t('button.cancel')"
              @click.prevent="closeModal()"
            />
            <Button
              type="primary"
              :text="$t('button.save')"
              @click.prevent="save()"
            />
          </div>
        </div>

      </template>
    </Modal>

    <!-- Modal für Verknüpfungen hinzufügen -->
    <Modal 
      :open="showRelationshipModal"
      @close="closeRelationshipModal()"
    >
      <template #content>
        <div class="mt-3 text-center">
          <DialogTitle as="h3" class="text-center font-semibold leading-6 text-gray-700">{{ $t('entity.titles.choose-relationship') }}</DialogTitle>
          <div class="mt-6 grid grid-cols-2 gap-4">
            <div
              v-for="(entityType, index) in availableEntityTypes"
              :key="index"
              @click="selectRelationType(entityType)"
              :class="[selectedRelationType?.entityName === entityType.entityName ? 'border-indigo-700 text-indigo-700' :
                  'border-gray-300 text-gray-700', 'rounded-lg border-lg border-2 hover:border-indigo-700 hover:text-indigo-700 p-12 flex items-center justify-center cursor-pointer']"
            >
              <div class="text-center">{{ getEntityTitle(entityType.entityName) }}</div>
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
              {{ getNoEntitiesMessage() }}
            </div>
          </div>
        </div>

        <Divider class="mt-5" />

        <div class="flex flex-row gap-x-2 mt-5">
          <Button
            :text="$t('button.cancel')"
            type="secondary"
            @click.prevent="closeRelationshipModal()"
          />
          <Button
            :text="$t('button.save')"
            @click.prevent="saveRelationship()"
          />
        </div>
      </template>
    </Modal>

    <!-- Document Relation Modal -->
    <DocumentRelationModal
      :open="showDocumentRelationModal"
      :source-entity-type="getSourceEntityType()"
      :source-entity-id="props.data?.id"
      :existing-relations="documentRelations"
      @close="showDocumentRelationModal = false"
      @relation-saved="handleDocumentRelationSaved"
    />
  </div>
</template>

<script setup>
import { ref, computed, watch, defineEmits } from 'vue';
import { useI18n } from 'vue-i18n';
import { useStore } from 'vuex';

import Button from '@/components/buttons/button.vue';
import EditableFormPanel from '@/components/EditableFormPanel.vue';
import Modal from '@/components/modals/modal.vue';
import SimpleFormBuilder from '@/components/SimpleFormBuilder.vue';
import AddButton from '@/components/buttons/add-button.vue';
import Divider from '@/components/divider.vue';
import RelationList from '@/components/RelationList.vue';
import Select from '@/components/forms/select.vue';
import { DialogTitle } from '@headlessui/vue';
import { useGetService } from '@/composables/utils';

const emit = defineEmits(['relation-saved']);

const props = defineProps({
  formConfig: {
    type: Object,
    required: true
  }
});

const internalFormConfig = ref(props.formConfig);

const store = useStore();
const { t } = useI18n();
const showModal = ref(false);

const modalType = ref(null);
const partIndex = ref(null);
const form = ref(null);
const formRefs = ref([]);


// Verknüpfungs-Modal Variablen
const showRelationshipModal = ref(false);
const selectedRelationType = ref(null);
const availableRelations = ref(null);
const refRelationSelect = ref(null);

const addPartFormConfig = [{
  type: "text",
  name: "title",
  label: "global.form.fields.title",
  placeholder: "global.form.fields.title",
  class: "md:col-span-2"
}];
const addFieldFormConfig = [{
  type: "text",
  name: "name",
  label: "global.form.fields.name",
  placeholder: "global.form.fields.name",
  class: "md:col-span-2"
}, {
  type: "dropdown",
  name: "type",
  label: "global.form.fields.type",
  placeholder: "global.form.fields.type",
  class: "md:col-span-2",
  items: [{
    translationKey: "global.form.datatypes.text",
    type: "text"
  }, {
    translationKey: "global.form.datatypes.number",
    type: "number"
  }, {
    translationKey: "global.form.datatypes.date",
    type: "date"
  }]
}, {
  type: "radio",
  name: "mandatory",
  label: "global.form.fields.mandatory",
  items: [{
    id: true,
    label: "global.label.yes"
  }, {
    id: false,
    label: "global.label.no"
  }],
  class: "md:col-span-2"
}, {
  type: "checkbox",
  name: "showInOverview",
  label: "global.form.fields.show-in-overview",
  class: "md:col-span-2"
}];

const modalTitle = computed(() => {
  if (showModal.value) {
    if (modalType.value === 'part') {
      return t('global.form.addPart');
    } else if (modalType.value === 'field') {
      return t('global.form.addField');
    }
  }
  return null;
});

const modalFields = computed(() => {
  if (modalType.value === 'part') {
    return addPartFormConfig;
  } else if (modalType.value === 'field') {
    return addFieldFormConfig;
  }
  return [];
});

const closeModal = () => {
  showModal.value = false;
  modalType.value = null;
};

const savePart = (data) => {
  if (!internalFormConfig.value.parts) {
    internalFormConfig.value.parts = [];
  }
  internalFormConfig.value.parts.push(data);
};

const deletePart = (index) => {
  internalFormConfig.value.parts.splice(index, 1);
};

const saveField = (data) => {
  if (internalFormConfig.value.parts[data.partIndex].fields) {
    internalFormConfig.value.parts[data.partIndex].fields.push(data.fieldConfig);
  } else {
    internalFormConfig.value.parts[data.partIndex].fields = [data.fieldConfig];
  }
};

const deleteField = (data) => {
  internalFormConfig.value.parts[data.partIndex].fields.splice(data.fieldIndex, 1);
};

const save = () => {
  if (modalType.value === 'part') {
    savePart(form.value.getData());
    // emits('savePart', form.value.getData());
  } else if (modalType.value === 'field') {
    const fieldConfig = {
      name: form.value.getData().name,
      label: form.value.getData().name,
      placeholder: form.value.getData().name,
      type: form.value.getData().type.type,
      disabled: true,
      showInOverview: form.value.getData().showInOverview
    };

    if (form.value.getData().mandatory) {
      fieldConfig.rules = "required"
    }

    saveField({ fieldConfig, partIndex: partIndex.value });
    // emits('saveField', { fieldConfig, partIndex: partIndex.value });
  }

  closeModal();
};

const getFormConfig = () => {
  internalFormConfig.value.parts.forEach(part => {
    part.fields.forEach(field => {
      delete field.disabled;
    })
  });
  return internalFormConfig.value;
};



const getRelationsTitle = () => {
  // Versuche zuerst die spezifische Übersetzung
  const specificKey = `jaynaApp.${internalFormConfig.value?.formTitle || 'baseContract'}.relations-title`;
  const specificTranslation = t(specificKey);
  if (specificTranslation !== specificKey) {
    return specificTranslation;
  }
  
  // Fallback auf Base-Entity
  const baseKey = `jaynaApp.baseContract.relations.title`;
  const baseTranslation = t(baseKey);
  if (baseTranslation !== baseKey) {
    return baseTranslation;
  }
  
  // Finaler Fallback
  return 'Verknüpfungen';
};



const getEntityTypeFromFormName = () => {
  // Bestimme den Entity-Typ basierend auf dem Form-Titel
  const formTitle = internalFormConfig.value?.formTitle || '';
  
  if (formTitle.includes('document')) return 'document';
  if (formTitle.includes('contract')) return 'contract';
  if (formTitle.includes('contact')) return 'contact';
  if (formTitle.includes('realestate')) return 'realestate';
  if (formTitle.includes('mobility')) return 'mobility';
  if (formTitle.includes('financeaccount')) return 'financeaccount';
  
  return 'document'; // Fallback für Dokumente
};





const getSourceEntityType = () => {
  // Bestimme den Entity-Typ basierend auf dem Form-Namen
  const formName = internalFormConfig.value?.formTitle || '';
  if (formName.includes('document')) return 'document';
  if (formName.includes('contract')) return 'contract';
  if (formName.includes('contact')) return 'contact';
  if (formName.includes('realestate')) return 'realestate';
  if (formName.includes('financeaccount')) return 'financeaccount';
  if (formName.includes('mobility')) return 'mobility';
  return 'document'; // Fallback
};

// Alle verfügbaren Entitätstypen
const availableEntityTypes = computed(() => {
  return [
    { entityName: "contact", relationName: "contactRelations" },
    { entityName: "contract", relationName: "contractRelations" },
    { entityName: "realestate", relationName: "realestateRelations" },
    { entityName: "mobility", relationName: "mobilityRelations" },
    { entityName: "financeaccount", relationName: "financeaccountRelations" }
  ];
});

// Hilfsfunktion für Entitätstitel
const getEntityTitle = (entityName) => {
  // Direktes Mapping für Entitätstitel
  const titleMap = {
    'contact': 'Kontakt',
    'contract': 'Vertrag',
    'realestate': 'Immobilie',
    'mobility': 'Mobilität',
    'financeaccount': 'Finanzkonto'
  };
  
  return titleMap[entityName] || entityName;
};

// Hilfsfunktion für "Keine Entitäten verfügbar" Nachricht
const getNoEntitiesMessage = () => {
  if (!selectedRelationType.value) {
    return 'Keine Entitäten verfügbar';
  }
  
  const entityTitle = getEntityTitle(selectedRelationType.value.entityName);
  return `Keine ${entityTitle}en vorhanden`;
};

const selectRelationType = async (relation) => {
  selectedRelationType.value = relation;
  availableRelations.value = null;

  try {
    const service = await useGetService(relation.entityName);
    if (!service?.default) {
      return;
    }
    if (typeof service.default.query !== 'function') {
      return;
    }

    const result = await service.default.query({
      page: 0,
      size: 100,
      sort: ['id,desc']
    });

    if (result?.data) {
      availableRelations.value = result.data;
    } else {
      availableRelations.value = [];
    }
  } catch (error) {
    
    availableRelations.value = [];
  }
};

const showAddRelationshipModal = () => {
  showRelationshipModal.value = true;
  selectedRelationType.value = null;
};

const closeRelationshipModal = () => {
  showRelationshipModal.value = false;
  selectedRelationType.value = null;
};

const saveRelationship = async () => {
  if (!selectedRelationType.value || !refRelationSelect.value?.selected) {
    
    return;
  }

  try {
    const selectedRelation = refRelationSelect.value.selected;
    const relationConfig = internalFormConfig.value?.relations.find(e => e.entityName === selectedRelationType.value.entityName);
    
    if (!relationConfig) {
      return;
    }

    // Hier würde die Verknüpfung gespeichert werden
    // Verknüpfung hinzugefügt
    
    closeRelationshipModal();
  } catch (error) {
  }
};

const hasFields = (parts) => {
  let retVal = false;

  parts.forEach(part => {
    if (part.fields?.length) {
      retVal = true;
    }
  })

  return retVal;
};

watch(() => props.formConfig, (newConfig) => {
  internalFormConfig.value = newConfig;
}, { deep: true });

// Store-Commits und Watcher für Button-Validierung entfernt - Button soll immer aktiv sein

defineExpose({ getFormConfig })

</script>