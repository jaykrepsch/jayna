<template>
  <div
    v-if="config"
    class="flex flex-col space-y-6"
  >
    <EntityCreateLayout
      message-key="global.form.updateFormTitle"
      @cancel="$router.go(-1)"
      @save="saveForm()"
      class="flex flex-col space-y-6"
    >
      <FormPanel
        :title="$t(`jaynaApp.tiles.my-space.edit-config`)"
        bg-color="bg-white"
      >
        <template v-slot:content>
          <div class="md:grid md:grid-cols-3 md:gap-6">
            <s-input
              name="subCategoryGroup"
              type="text"
              v-model="config.groupType.subCategoryGroup.name"
              :label="$t(`jaynaApp.${config.groupType.subCategoryGroup.subCategory.category.entityName}.subCategoryGroup`)"
              :disabled="config.groupType.subCategoryGroup.creationType === 'STATIC' ? true : false"
            />
            <s-input
              name="subCategory"
              type="text"
              v-model="config.groupType.name"
              :label="$t(`jaynaApp.${config.groupType.subCategoryGroup.subCategory.category.entityName}.groupType`)"
            />
          </div>
        </template>
      </FormPanel>

      <EditableFormBuilder
        ref="formBuilder"
        :form-config="config"
      />
    </EntityCreateLayout>

  </div>
</template>

<script setup>

import { ref, computed } from 'vue';
import FormConfigService from '@/services/form-config.service';
import { useRouter, useRoute } from 'vue-router';

import SInput from '@/components/forms/input.vue';
import FormPanel from '@/components/formpanel.vue';
import EditableFormBuilder from '@/components/EditableFormBuilder.vue';
import EntityCreateLayout from '@/core/layouts/entity-create-layout.vue';

const router = useRouter();
const route = useRoute();
const config = ref(null);
const formBuilder = ref(null);

const configId = computed(() => route.params.id);

const saveForm = () => {
  const data = Object.assign(formBuilder.value?.getFormConfig(), { groupType: groupType.value });

  FormConfigService
    .create(data)
    .then(() => {
      router.push({
        name: 'CategoryCreateWithGroupType', params: {
          id: category.value.id,
          subid: groupType.value.subCategoryGroup.subCategory.id,
          subgrpid: groupType.value.subCategoryGroup.id,
          grptpid: groupType.value.id
        }
      });
    })
    .catch(() => {});
};

FormConfigService
  .find(configId.value)
  .then(res => {
    config.value = res;
  })
  .catch(() => {});



</script>
