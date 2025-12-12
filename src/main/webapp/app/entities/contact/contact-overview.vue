<template>
  <OverviewLayout
    ref="layout"
  >
    <template #content>
      
      <TableLayout>
        <template #head>
        <tr>
          <th scope="col"></th>
          <th scope="col" class="text-left px-3 py-3.5 text-sm font-semibold">Nachname, Vorname</th>
          <th scope="col" class="text-left px-3 py-3.5 text-sm font-semibold">Straße Hausnummer, PLZ Ort</th>
          <th scope="col" class="text-left px-3 py-3.5 text-sm font-semibold">Telefonnummer</th>
          <th scope="col" class="text-left px-3 py-3.5 text-sm font-semibold">Bezeichnung</th>
          <th scope="col" class="text-left px-3 py-3.5 text-sm font-semibold">Beschreibung</th>
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
            {{ formatName(subEntity) }}
          </td>

          <td class="px-3 py-4 text-sm">
            {{ formatAddressCompact(subEntity) }}
          </td>

          <td class="px-3 py-4 text-sm">
            {{ formatPhone(subEntity) }}
          </td>

          <td class="px-3 py-4 text-sm">
            {{ subEntity?.label || '-' }}
          </td>

          <td class="px-3 py-4 text-sm max-w-column-32 truncate">
            {{ subEntity?.comment || '-' }}
          </td>
    
          <td class="text-right px-3 py-4">
            <div class="flex space-x-2 justify-end">
              <OverviewTableEditButton 
                :cat-id="layout?.category?.id"
                :sub-cat-id="getSubCategoryId(subEntity)"
                :sub-cat-grp-id="getSubCategoryGroupId(subEntity)"
                :grp-tp-id="getGroupTypeId(subEntity)"
                :entity-id="subEntity?.id"
                entity-name="contact"
              />
              <OverviewTableViewButton 
                :cat-id="layout?.category?.id"
                :sub-cat-id="getSubCategoryId(subEntity)"
                :sub-cat-grp-id="getSubCategoryGroupId(subEntity)"
                :grp-tp-id="getGroupTypeId(subEntity)"
                :entity-id="subEntity?.id"
                entity-name="contact"
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
import { useRouter } from 'vue-router';

const { t } = useI18n();
const layout = ref(null);
const router = useRouter();

// Navigation erfolgt direkt über router-link im Template

const getSubCategoryId = (subEntity) => {
  return subEntity?.groupType?.subCategoryGroup?.subCategory?.id || null;
};

const getSubCategoryGroupId = (subEntity) => {
  return subEntity?.groupType?.subCategoryGroup?.id || null;
};

const getGroupTypeId = (subEntity) => {
  return subEntity?.groupType?.id || null;
};

// Formatter gemäß gewünschter Spalten
const formatName = (subEntity) => {
  if (!subEntity) return '-';
  const last = subEntity.surName || '';
  const first = subEntity.firstName || '';
  const value = [last, first].filter(Boolean).join(', ');
  return value || '-';
};

const formatAddressCompact = (subEntity) => {
  if (!subEntity) return '-';
  const street = [subEntity.street || '', subEntity.streetNumber || ''].filter(Boolean).join(' ');
  const city = [subEntity.postalCode ? `PLZ ${subEntity.postalCode}` : '', subEntity.city || ''].filter(Boolean).join(' ');
  const parts = [street, city].filter(Boolean);
  return parts.length ? parts.join(', ') : '-';
};

const formatPhone = (subEntity) => {
  if (!subEntity) return '-';
  const phone = [
    subEntity.phoneCountryCode ? `+${subEntity.phoneCountryCode}` : '',
    subEntity.phonePrefix || '',
    subEntity.phoneNumber || ''
  ].filter(Boolean).join(' ');
  return phone || '-';
};

</script> 