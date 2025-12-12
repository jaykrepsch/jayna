<template>
  <div v-if="items">
    <Dropdown
      v-model="selected"
      :label="label"
      :box-label="boxLabel"
      :items="items"
      :required="required"
      :display-field="displayField"
      :disabled="loading || disabled"
    />
    <div v-if="loading" class="mt-2 text-sm text-gray-500">
      {{ $t('global.menu.loading') }}
    </div>
    <div v-else-if="!items.length" class="mt-2 text-sm text-gray-500">
      {{ $t('jaynaApp.form-builder.messages.no-entities-available') }}
    </div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue';
import Dropdown from '@/components/forms/dropdown.vue';
import { useGetService } from '@/composables/utils';
import { useI18n } from 'vue-i18n';

const { t } = useI18n();
const items = ref([]);
const service = ref(null);
const loading = ref(false);

const props = defineProps({
  modelValue: Object,
  label: String,
  boxLabel: String,
  translationKey: String,
  displayField: String,
  disabled: Boolean,
  required: {
    type: Boolean,
    default: false
  },
  entityName: {
    type: String,
    required: true
  }
});

const selected = ref(props.modelValue || null);

const loadItems = async () => {
  if (!props.entityName) return;
  
  loading.value = true;
  try {
    const serviceData = await useGetService(props.entityName);
    service.value = serviceData.default;

    const result = await service.value.query({
      page: 0,
      size: 100,
      sort: ['id,desc']
    });

    if (result?.data) {
      items.value = result.data;
    } else {
      
      items.value = [];
    }
  } catch (error) {
    
    items.value = [];
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  loadItems();
});

const emits = defineEmits(['update:modelValue']);

watch(() => props.modelValue, (newValue) => {
  selected.value = newValue;
});

watch(() => selected.value, (newValue) => {
  emits('update:modelValue', newValue);
});
</script>