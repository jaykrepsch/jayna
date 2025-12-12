<template>
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
            {{ $t('jaynaApp.baseOverview.street') }} <br>
            {{ $t('jaynaApp.baseOverview.city') }} <br>
            {{ $t('jaynaApp.baseOverview.country') }}
          </th>

          <th scope="col" class="text-left px-3 py-3.5 text-sm font-semibold">
            {{ $t('jaynaApp.baseOverview.purchaseDate') }} <br>
            {{ $t('jaynaApp.baseOverview.yearBuilt') }} <br>
            {{ $t('jaynaApp.baseOverview.squareMeters') }}
          </th>

          <th scope="col" class="text-left px-3 py-3.5 text-sm font-semibold">
            {{ $t('jaynaApp.baseOverview.purchasePrice') }} <br>
            {{ $t('jaynaApp.baseOverview.currentValue') }} <br>
            {{ $t('jaynaApp.baseOverview.rooms') }}
          </th>

          <th scope="col" class="text-left px-3 py-3.5 text-sm font-semibold max-w-column-32">
            {{ $t('jaynaApp.baseOverview.label') }} <br>
            {{ $t('jaynaApp.baseOverview.comment') }} <br>
            {{ $t('jaynaApp.baseContract.general.fields.entityState') }}
          </th>

          <th scope="col" class="text-left px-3 py-3.5 text-sm font-semibold"></th>
        </tr>
      </template>
      <template #body>
        <tr
          v-for="(subEntity, index) in layout?.subEntities" :key="index"
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
            {{ (subEntity.street || '') + ' ' + (subEntity.streetNumber || '') }} <br>
            {{ (subEntity.postalCode || '') + ' ' + (subEntity.city || '') }} <br>
            {{ (subEntity.state || '') + ', ' + (subEntity.country || '') }}
          </td>

          <td class="px-3 py-4 text-sm">
            {{ subEntity.purchaseDate || '-' }} <br>
            {{ subEntity.yearBuilt || '-' }} <br>
            {{ subEntity.squareMeters || '-' }}
          </td>

          <td class="px-3 py-4 text-sm">
            {{ subEntity.purchasePrice || '-' }} <br>
            {{ subEntity.currentValue || '-' }} <br>
            {{ subEntity.rooms || '-' }}
          </td>

          <td class="px-3 py-4 text-sm max-w-column-32">
            {{ subEntity.label || '-' }} <br>
            {{ subEntity.comment || '-' }} <br>
            {{ subEntity.entityState ? $t('enums.' + subEntity.entityState) : '-' }}
          </td>
  
          <td class="text-right px-3 py-4">
            <div class="flex space-x-2 justify-end">
              <OverviewTableEditButton
                :cat-id="layout?.category?.id"
                :sub-cat-id="getSubCategoryId(subEntity)"
                :sub-cat-grp-id="getSubCategoryGroupId(subEntity)"
                :grp-tp-id="getGroupTypeId(subEntity)"
                :entity-id="subEntity.id"
                entity-name="realestate"
              />
              <OverviewTableViewButton
                :cat-id="layout?.category?.id"
                :sub-cat-id="getSubCategoryId(subEntity)"
                :sub-cat-grp-id="getSubCategoryGroupId(subEntity)"
                :grp-tp-id="getGroupTypeId(subEntity)"
                :entity-id="subEntity.id"
                entity-name="realestate"
              />
            </div>
          </td>
          </tr>
        </template>
      </TableLayout>
    </template>
  </OverviewLayout>
</template>

<script setup>

import { ref } from 'vue';
import { useI18n } from 'vue-i18n';
import OverviewLayout from '@/core/layouts/overview-layout.vue';
import TableLayout from '@/core/layouts/TableLayout.vue';
import OverviewTableEditButton from '@/components/buttons/overview-table-edit-button.vue';
import OverviewTableViewButton from '@/components/buttons/overview-table-view-button.vue';

const { t } = useI18n();
const layout = ref(null);

// Verbesserte Helper-Funktionen für Kategorie-Anzeige (wie in view.vue)
const getSubCategoryDisplayName = (subEntity) => {
  if (!subEntity?.groupType?.subCategoryGroup?.subCategory) return '-';
  
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
  if (!subEntity?.groupType?.subCategoryGroup) return '-';
  
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
  if (!subEntity?.groupType) return '-';
  
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
