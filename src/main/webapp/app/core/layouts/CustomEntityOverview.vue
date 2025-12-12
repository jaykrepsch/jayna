<template>
  <div class="grid grid-cols-9 rounded-md p-4 bg-white w-full text-sm items-top">
    <div class="flex flex-col space-y-2 col-span-2">
      <div>
        <label class="block text-sm font-medium text-gray-700">
          {{ $t(`jaynaApp.${entity.config.groupType.subCategoryGroup.subCategory.category.entityName}.subCategory`) }}:
        </label>
        <div class="mt-1 p-3 bg-gray-50 border border-gray-300 rounded-md">
          {{ getSubCategoryDisplayName(entity.config.groupType.subCategoryGroup.subCategory) }}
        </div>
      </div>
      <div>
        <label class="block text-sm font-medium text-gray-700">
          {{ $t(`jaynaApp.${entity.config.groupType.subCategoryGroup.subCategory.category.entityName}.subCategoryGroup`) }}:
        </label>
        <div class="mt-1 p-3 bg-gray-50 border border-gray-300 rounded-md">
          {{ getSubCategoryGroupDisplayName(entity.config.groupType.subCategoryGroup) }}
        </div>
      </div>
      <div>
        <label class="block text-sm font-medium text-gray-700">
          {{ $t(`jaynaApp.${entity.config.groupType.subCategoryGroup.subCategory.category.entityName}.groupType`) }}:
        </label>
        <div class="mt-1 p-3 bg-gray-50 border border-gray-300 rounded-md">
          {{ getGroupTypeDisplayName(entity.config.groupType) }}
        </div>
      </div>
    </div>
    <div class="col-span-6 grid grid-cols-2">
      <div
        v-for="(field, fieldIndex) of fields"
        :key="fieldIndex"
        class=""
      >
        <div v-if="field.showInOverview">
          <div>
            <label class="block text-sm font-medium text-gray-700">
              {{ field.name }}:
            </label>
            <div class="mt-1 p-3 bg-gray-50 border border-gray-300 rounded-md">
              {{ field.type === 'date' ?  $d(new Date(data[field.name]), 'short', ($store && $store.getters ? $store.getters['translation/currentLanguage'] : 'de')) : data[field.name] }}
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="col-span-1 text-right">
      <OverviewTableEditButton
        :cat-id="entity.config.groupType.subCategoryGroup.subCategory.category.id"
        :sub-cat-id="entity.config.groupType.subCategoryGroup.subCategory.id"
        :sub-cat-grp-id="entity.config.groupType.subCategoryGroup.id"
        :grp-tp-id="entity.config.groupType.id"
        :entity-id="entity.payload.id"
      />
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue';
import { useI18n } from 'vue-i18n';
import OverviewTableEditButton from '@/components/buttons/overview-table-edit-button.vue';

const { t } = useI18n();

const props = defineProps({
  entity: {
    type: Object,
    required: true
  }
});

const data = computed(() => props.entity?.payload);
const fields = computed(() => {
  const result = props.entity.config.parts
    .reduce((acc, val) => {
      acc.push(val.fields);
      return acc;
    }, [])
    .flat()
    .slice(0, 6);

  return result;
});

// Helper functions for displaying translated names
const getSubCategoryDisplayName = (subCategory) => {
  if (!subCategory) return '-';
  
  // Verwende Übersetzungsschlüssel, falls verfügbar
  if (subCategory.translationKey) {
    const translation = t(subCategory.translationKey);
    if (translation !== subCategory.translationKey) {
      return translation;
    }
  }
  
  // Fallback auf name
  return subCategory.name || '-';
};

const getSubCategoryGroupDisplayName = (subCategoryGroup) => {
  if (!subCategoryGroup) return '-';
  
  // Verwende Übersetzungsschlüssel, falls verfügbar
  if (subCategoryGroup.translationKey) {
    const translation = t(subCategoryGroup.translationKey);
    if (translation !== subCategoryGroup.translationKey) {
      return translation;
    }
  }
  
  // Fallback auf name
  return subCategoryGroup.name || '-';
};

const getGroupTypeDisplayName = (groupType) => {
  if (!groupType) return '-';
  
  // Verwende Übersetzungsschlüssel, falls verfügbar
  if (groupType.translationKey) {
    const translation = t(groupType.translationKey);
    if (translation !== groupType.translationKey) {
      return translation;
    }
  }
  
  // Fallback auf name
  return groupType.name || '-';
};

</script>
