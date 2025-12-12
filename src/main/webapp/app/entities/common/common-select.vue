<template>
  <div>
    <div class="flex items-center justify-between -mt-8 -mx-12 py-8 px-12 bg-gray-100 sticky top-16 z-10">
      <div class="flex-auto">
        <h1 class="text-xl font-semibold text-gray-900">{{ getSelectTitle() }}</h1>
      </div>
      <div class="flex flex-row space-x-4">
        <Button
          :text="$t('button.cancel')"
          type="secondary"
          @click.prevent="$router.push('/' + category?.entityName)"
        />
        <Button
          v-if="selectedGroupType"
          :text="$t('button.continue')"
          type="primary"
          @click.prevent="continueProcess()"
        />
      </div>
    </div>
    <FormPanel
      v-if="category?.entityName"
      :title="'Kategorie'"
      bg-color="bg-white"
    >
      <template v-slot:content>
        <div class="md:grid md:grid-cols-3 md:gap-6">
          <InputDropdown
            v-if="!$route.query.subid && category?.entityName"
            v-model="selectedSubCategory"
            :label="$t(`jaynaApp.baseOverview.subCategory`)"
            :box-label="selectedSubCategory?.translationKey ? $t(selectedSubCategory.translationKey) : (selectedSubCategory?.name || $t('global.menu.choose'))"
            :items="subCategories"
            :auto-open="true"
          />

          <InputDropdown
            v-if="!$route.query.subgrpid && selectedSubCategory && category?.entityName"
            v-model="selectedSubCategoryGroup"
            :label="$t(`jaynaApp.baseOverview.subCategoryGroup`)"
            :box-label="selectedSubCategoryGroup?.translationKey ? $t(selectedSubCategoryGroup.translationKey) : (selectedSubCategoryGroup?.name || $t('global.menu.choose'))"
            :items="subCategoryGroups"
            :auto-open="true"
          />

          <InputDropdown
            v-if="selectedSubCategoryGroup && category?.entityName"
            v-model="selectedGroupType"
            :label="$t(`jaynaApp.baseOverview.groupType`)"
            :box-label="selectedGroupType?.translationKey ? $t(selectedGroupType.translationKey) : (selectedGroupType?.name || $t('global.menu.choose'))"
            :items="groupTypes"
            :auto-open="true"
          />
          
          <!-- <SInput
            v-if="customGroupType"
            v-model="inputGroupType"
            :label="$t('jaynaApp.group-type.enter-custom-value')"
            :placeholder="$t('jaynaApp.group-type.enter-custom-value')"
            type="text"
          />
          <Dropdown
            v-else-if="groupTypes.length > 0"
            v-model="selectedGroupType"
            :label="$t(`jaynaApp.${category?.entityName}.groupType`)"
            :box-label="selectedGroupType?.translationKey ? $t(selectedGroupType?.translationKey) :
              selectedGroupType?.name || $t('global.menu.choose')"
            :items="groupTypes"
          /> -->

          <!-- <Dropdown
            v-if="!$route.query.subgrpid && subCategoryGroups.length > 0"
            v-model="selectedSubCategoryGroup"
            :label="$t(`jaynaApp.${category?.entityName}.subCategoryGroup`)"
            :box-label="selectedSubCategoryGroup?.translationKey ? $t(selectedSubCategoryGroup?.translationKey) :
              selectedSubCategoryGroup?.name || $t('global.menu.choose')"
            :items="subCategoryGroups"
          />

          <Dropdown
            v-if="groupTypes.length > 0"
            v-model="selectedGroupType"
            :label="$t(`jaynaApp.${category?.entityName}.groupType`)"
            :box-label="selectedGroupType?.translationKey ? $t(selectedGroupType?.translationKey) :
              selectedGroupType?.name || $t('global.menu.choose')"
            :items="groupTypes"
          /> -->
        </div>
      </template>
    </FormPanel>
  </div>
</template>

<script setup>

import { ref, watch, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useStore } from 'vuex';
import { useI18n } from 'vue-i18n';

import AlertService from '@/services/alert.service';
import CategoryService from '@/services/category.service';
import SubCategoryService from '@/services/sub-category.service';
import SubCategoryGroupService from '@/services/sub-category-group.service';
import GroupTypeService from '@/services/group-type.service';
import FormConfigService from '@/services/form-config.service';

import Button from '@/components/buttons/button.vue';
import Dropdown from '@/components/forms/dropdown.vue';
import InputDropdown from '@/components/forms/InputDropdown.vue';
import SInput from '@/components/forms/input.vue';
import FormPanel from '@/components/formpanel.vue';

const store = useStore();
const route = useRoute();
const router = useRouter();
const { t } = useI18n();

/** data */
const customGroupType = ref(false);
// const inputGroupType = ref(null);
const category = ref(null);
const subCategories = ref([]);
const selectedSubCategory = ref(null);
const subCategoryGroups = ref([]);
const selectedSubCategoryGroup = ref(null);
const groupTypes = ref([]);
const selectedGroupType = ref(null);

/** methods */
const clearSubCategoryGroups = () => {
  subCategoryGroups.value = [];
  selectedSubCategoryGroup.value = null;
  clearGroupTypes();
};

const loadSubCategoryGroups = () => {
  if (!selectedSubCategory.value?.id) return;
  
  SubCategoryGroupService
    .findByParentId(selectedSubCategory.value.id)
    .then(res => {
      subCategoryGroups.value = res;
      
    })
    .catch(err => {
      AlertService.showHttpError(this, err.response);
    });
};

const clearGroupTypes = () => {
  groupTypes.value = [];
  selectedGroupType.value = null;
};

const loadGroupTypes = () => {
  if (!selectedSubCategoryGroup.value?.id) return;

  GroupTypeService
    .findByParentId(selectedSubCategoryGroup.value.id)
    .then(res => {
      groupTypes.value = res;
      
    })
    .catch(err => {
      AlertService.showHttpError(this, err.response);
    });
};

const routeNext = (routeName, groupType) => {
  let params = {
    id: category.value.id,
    subid: groupType.subCategoryGroup.subCategory.id,
    subgrpid: groupType.subCategoryGroup.id,
    grptpid: groupType.id
  };
  
  router.push({
    name: routeName, 
    params: params
  });
};

const continueProcess = () => {
  
  if (selectedGroupType.value && selectedGroupType.value.id) {
    if (selectedGroupType.value.creationType === 'CUSTOM') {
      FormConfigService
        .get(`/api/group-types/${selectedGroupType.value.id}/form-configs`)
        .then(res => {
          routeNext('CategoryCreateWithGroupType', selectedGroupType.value);
        })
        .catch(err => {
          routeNext('CreateForm', selectedGroupType.value);
        });
    } else {
      // Wenn eine Form-Definition vorhanden ist, zur CreateForm weiterleiten
      if (selectedGroupType.value.formName) {
        routeNext('CreateForm', selectedGroupType.value);
      } else {
        routeNext('CategoryCreateWithGroupType', selectedGroupType.value);
      }
    }
  } else {
    if (selectedSubCategoryGroup.value && selectedSubCategoryGroup.value.id) {
      const groupType = {
        subCategoryGroup: selectedSubCategoryGroup.value,
        name: selectedGroupType.value
      };
      GroupTypeService
        .create(groupType)
        .then((groupTypeRes) => {
          routeNext('CreateForm', groupTypeRes);
        })
    .catch(() => {});
    } else {
      const subCategoryGroup = {
        subCategory: selectedSubCategory.value,
        name: selectedSubCategoryGroup.value
      };
      SubCategoryGroupService
        .create(subCategoryGroup)
        .then((subCategoryGroupRes) => {
          const groupType = {
            subCategoryGroup: subCategoryGroupRes,
            name: selectedGroupType.value
          };

          GroupTypeService
            .create(groupType)
            .then((groupTypeRes) => {
              routeNext('CreateForm', groupTypeRes);
            })
            .catch(() => {});

        })
        .catch((err) => {
          
        });
    }
  }
};

/** watchers */
watch(selectedSubCategory, (newValue, oldValue) => {
  
  clearSubCategoryGroups();
  if (newValue) {
    loadSubCategoryGroups();
  }
});

watch(selectedSubCategoryGroup, (newValue, oldValue) => {
  
  clearGroupTypes();
  if (newValue) {
    if (!(typeof newValue === 'string' || newValue instanceof String)) {
      loadGroupTypes();
    }
  }
});

watch(selectedGroupType, (newValue, oldValue) => {
  
  if (newValue) {
    if (typeof newValue === 'string' || newValue instanceof String) {
      customGroupType.value = true;
    }
    // Automatische Weiterleitung entfernt - Benutzer muss manuell auf "Weiter" klicken
  }
});

CategoryService
  .find(parseInt(route.params.id))
  .then(res => {
    category.value = res;
    
    // Wenn es eine Kontakt-Kategorie ist, direkt zum Kontaktformular weiterleiten
    if (category.value?.entityName === 'contact') {
      router.push({
        name: 'ContactCreate'
      });
      return;
    }
    
    // Sonst Unterkategorien laden
    return SubCategoryService.findByParentId(parseInt(route.params.id));
  })
  .then(res => {
    if (res) {
    subCategories.value = res;
    }
  })
  .catch(err => {
    AlertService.showHttpError(this, err.response);
  });


if (route.query.subid) {
  selectedSubCategory.value = {
    id: parseInt(route.query.subid)
  }
  if (route.query.subgrpid) {
    selectedSubCategoryGroup.value = {
      id: parseInt(route.query.subgrpid)
    }
  }
}

function getSelectTitle() {
  if (category.value?.entityName) {
    return t('global.form.createFormTitle', { entity: t('jaynaApp.baseOverview.category.title') });
  }
  return t('global.form.createFormTitle', { entity: '' });
}

</script>
