<template>
  <div>
    <div
      v-if="configs?.length"
      class="flex flex-col space-y-6"
    >
      <div class="sm:flex sm:items-center">
        <div class="sm:flex-auto">
          <h1 class="text-xl font-semibold text-gray-900">
            {{ $t('jaynaApp.tiles.table.header') }}
          </h1>
        </div>
      </div>
      <TableLayout>
        <template #head>
          <tr>
            <th scope="col"></th>
            <th scope="col" class="text-left px-3 py-3.5 text-sm font-semibold">
              {{ $t(`jaynaApp.tiles.table.subCategoryGroup`) }}
            </th>
            <th scope="col" class="text-left px-3 py-3.5 text-sm font-semibold">
              {{ $t(`jaynaApp.tiles.table.groupType`) }}
            </th>
            <th scope="col" class="text-left px-3 py-3.5 text-sm font-semibold">
              {{ $t(`jaynaApp.tiles.table.hasFormConfig`) }}
            </th>
            <th scope="col" class="text-left px-3 py-3.5 text-sm font-semibold"></th>
          </tr>
        </template>
        <template #body>
          <tr
            v-for="(config, index) of configs"
            :key="index"
            class="odd:bg-white even:bg-gray-100"
          >
            <td class="pl-4 pr-3 py-4 text-sm">
              {{ index + 1 }}
            </td>
            <td class="px-3 py-4 text-sm">
              {{ config.groupType.subCategoryGroup.name }}
            </td>
            <td class="px-3 py-4 text-sm">
              {{ config.groupType.name }}
            </td>
            <td class="px-3 py-4 text-sm">
              {{ hasFormConfig(config) ? $t('global.label.yes') : $t('global.label.no') }}
            </td>
            <td class="text-right px-3 py-4">
              <EditButton
                @click="hasFormConfig(config) ? $router.push({ name: 'EditCustomGroupType', params: { id: config.id } }) : routeToCreateForm(config.groupType)"
              />
            </td>
          </tr>
        </template>
      </TableLayout>
    </div>
    <div v-else>
      <p>{{ $t('jaynaApp.tiles.my-space.no-configs') }}</p>
    </div>
  </div>
</template>

<script setup>

import { ref } from 'vue';
import { useRouter } from 'vue-router';

import FormConfigService from '@/services/form-config.service';
import EditButton from '@/components/buttons/EditButton.vue';
import TableLayout from '@/core/layouts/TableLayout.vue';

const configs = ref(null);
const router = useRouter();

FormConfigService
  .retrieve()
  .then(res => {
    configs.value = res.data;
  })
  .catch(() => {});

const editConfig = (config) => {
  
};

const hasFormConfig = (config) => {
  return config.id && config.parts.length;
}

const routeToCreateForm = (groupType) => {
  router.push({
    name: 'CreateForm', params: {
      id: groupType.subCategoryGroup.subCategory.category.id,
      subid: groupType.subCategoryGroup.subCategory.id,
      subgrpid: groupType.subCategoryGroup.id,
      grptpid: groupType.id
    }
  });
};

</script>