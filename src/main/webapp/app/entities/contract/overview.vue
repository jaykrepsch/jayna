<template>
  <div>
    <OverviewLayout
      ref="layout"
    >
      <template #content>
        <TableLayout>
          <template #head>
            <tr>
              <th scope="col"></th>
              <th scope="col" class="text-left px-3 py-3.5 text-sm font-semibold">
                {{ $t('jaynaApp.baseOverview.subCategory') }} <br>
                {{ $t('jaynaApp.baseOverview.subCategoryGroup') }} <br>
                {{ $t('jaynaApp.baseOverview.groupType') }}
              </th>
              <th scope="col" class="text-left px-3 py-3.5 text-sm font-semibold">
                {{ $t('jaynaApp.baseOverview.contractor') }} <br>
                {{ $t('jaynaApp.baseOverview.contractOwner') }} <br>
                {{ $t('jaynaApp.baseOverview.contractUser') }}
              </th>
              <th scope="col" class="text-left px-3 py-3.5 text-sm font-semibold">
                {{ $t('jaynaApp.baseOverview.contractNumber') }} <br>
                {{ $t('jaynaApp.baseOverview.startDate') }} <br>
                {{ $t('jaynaApp.baseOverview.endDate') }}
              </th>
              <th scope="col" class="text-left px-3 py-3.5 text-sm font-semibold">
                {{ $t('jaynaApp.baseOverview.payment') }} <br>
                {{ $t('jaynaApp.baseOverview.paymentType') }} <br>
                {{ $t('jaynaApp.baseOverview.payer') }}
              </th>
              <th scope="col" class="text-left px-3 py-3.5 text-sm font-semibold max-w-column-40">
                {{ $t('jaynaApp.baseOverview.label') }} <br>
                {{ $t('jaynaApp.baseOverview.comment') }} <br>
                {{ $t('jaynaApp.baseContract.general.fields.entityState') }}
              </th>
              <th scope="col" class="text-left px-3 py-3.5 text-sm font-semibold"></th>
            </tr>
          </template>
          <template #body>
            <tr
              v-for="(subEntity, index) in layout?.subEntities || []" :key="index"
              class="odd:bg-white even:bg-gray-100"
            >
              <td class="pl-4 pr-3 py-4 text-sm">
                {{ index + 1 }}
              </td>
              <td class="px-3 py-4 text-sm">
                {{ getSubCategoryDisplayName(subEntity) }} <br>
                {{ getSubCategoryGroupDisplayName(subEntity) }} <br>
                {{ getGroupTypeDisplayName(subEntity) }}
              </td>
              <td class="px-3 py-4 text-sm">
                {{ subEntity?.contractor || '-' }} <br>
                {{ subEntity?.contractOwner || '-' }} <br>
                {{ subEntity?.contractUser || '-' }}
              </td>
              <td class="px-3 py-4 text-sm">
                {{ subEntity?.contractNumber || '-' }} <br>
                <div v-if="subEntity?.startDate">
                  {{ $d(new Date(subEntity.startDate), 'short', $store.getters['translation/currentLanguage']) }}
                </div>
                <div v-else>-</div>
                <div v-if="subEntity?.endDate">
                  {{ $d(new Date(subEntity.endDate), 'short', $store.getters['translation/currentLanguage']) }}
                </div>
                <div v-else>-</div>
              </td>
              <td class="px-3 py-4 text-sm">
                {{ subEntity?.paymentPattern ? $t('enums.' + subEntity.paymentPattern) : '-' }} <br>
                {{ subEntity?.paymentType ? $t('enums.' + subEntity.paymentType) : '-' }} <br>
                {{ subEntity?.payer || '-' }}
              </td>
              <td class="px-3 py-4 text-sm max-w-column-40 truncate">
                {{ subEntity?.label || '-' }} <br>
                {{ subEntity?.comment || '-' }} <br>
                {{ subEntity?.entityState ? $t('enums.' + subEntity.entityState) : '-' }}
              </td>
      
              <td class="text-right px-3 py-4">
                <div class="flex space-x-2 justify-end">
                  <OverviewTableEditButton 
                    :cat-id="layout?.category?.id"
                    :sub-cat-id="getSubCategoryId(subEntity)"
                    :sub-cat-grp-id="getSubCategoryGroupId(subEntity)"
                    :grp-tp-id="getGroupTypeId(subEntity)"
                    :entity-id="subEntity?.id"
                    entity-name="contract"
                  />
                  <OverviewTableViewButton 
                    :cat-id="layout?.category?.id"
                    :sub-cat-id="getSubCategoryId(subEntity)"
                    :sub-cat-grp-id="getSubCategoryGroupId(subEntity)"
                    :grp-tp-id="getGroupTypeId(subEntity)"
                    :entity-id="subEntity?.id"
                    entity-name="contract"
                  />
                </div>
              </td>
            </tr>
          </template>
        </TableLayout>
      </template>
    </OverviewLayout>
  </div>
  
</template>

<script setup>

import { ref, watch } from 'vue';
import { useI18n } from 'vue-i18n';
import OverviewLayout from '@/core/layouts/overview-layout.vue';
import TableLayout from '@/core/layouts/TableLayout.vue';
import OverviewTableEditButton from '@/components/buttons/overview-table-edit-button.vue';
import OverviewTableViewButton from '@/components/buttons/overview-table-view-button.vue';

const { t } = useI18n();
const layout = ref(null);

// Verbesserte Helper-Funktionen für Kategorie-Anzeige (wie in view.vue)
const getSubCategoryDisplayName = (subEntity) => {
  if (!subEntity?.groupType?.subCategoryGroup?.subCategory) {
    return '-';
  }
  
  const subCategory = subEntity.groupType.subCategoryGroup.subCategory;
  
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

const getSubCategoryGroupDisplayName = (subEntity) => {
  if (!subEntity?.groupType?.subCategoryGroup) {
    return '-';
  }
  
  const subCategoryGroup = subEntity.groupType.subCategoryGroup;
  
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

const getGroupTypeDisplayName = (subEntity) => {
  if (!subEntity?.groupType) {
    return '-';
  }
  
  const groupType = subEntity.groupType;
  
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

// Verbesserte ID-Extraktion (wie in view.vue)
const getSubCategoryId = (subEntity) => {
  return subEntity?.groupType?.subCategoryGroup?.subCategory?.id || null;
};

const getSubCategoryGroupId = (subEntity) => {
  return subEntity?.groupType?.subCategoryGroup?.id || null;
};

const getGroupTypeId = (subEntity) => {
  return subEntity?.groupType?.id || null;
};

</script>
