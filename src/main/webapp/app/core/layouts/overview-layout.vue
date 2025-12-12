Dropdown<template>
  <div>
    <div v-if="category">
      <div class="sm:flex sm:items-center">
        <div class="sm:flex-auto">
          <h1 
            class="text-xl font-semibold text-gray-900"
            v-text="$t('jaynaApp.category.' + category.translationKey + '.title')"
          />
        </div>
        <span
          class="inline-flex items-center justify-center rounded-md border border-transparent bg-green-600 px-2 py-1 text-sm font-medium text-white shadow-sm hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2 sm:w-auto cursor-pointer"
          @click="addEntity()"
        >
          <PlusIconOutline class="h-6 w-6" aria-hidden="true" />
        </span>
      </div>
      <div class="flex flex-row">
        <Alert
          v-if="(subEntities?.length === 0)"
          type="warn"
          :messageKey="'jaynaApp.category.' + category.translationKey + '.notFound'"
        />
      </div>
      <div 
        v-if="subEntities?.length > 0"
        class="flex flex-col mt-8"
      >
        <slot name="content" />
      </div>

      <div
        v-if="customEntities?.length"
        class="flex flex-col space-y-4"
      >
        <p class="font-semibold text-xl mt-8">
          Von dir erstellte Gruppierungen und Sparten.
        </p>
        <div 
          v-for="(item, index) of customEntities"
          :key="index"
        >
          <CustomEntityOverview
            :entity="item"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import { PlusIcon as PlusIconOutline } from '@heroicons/vue/24/outline';

import CategoryService from '@/services/category.service';
import GroupTypeService from '@/services/group-type.service';
import AlertService from '@/services/alert.service';
import CustomFormInputService from '@/services/custom-form-input.service';
import Disclosure from '@/components/Disclosure.vue';
import CustomEntityOverview from '@/core/layouts/CustomEntityOverview.vue';

import Alert from '@/components/alert.vue';
import { useStore } from 'vuex';
import { isSingleGroupType, isSelectGroupType } from '@/composables/render-utils';

const route = useRoute();
const router = useRouter();
const store = useStore();

/** data */
const category = ref(null);
const subEntities = ref([]);
const customEntities = ref([]);
const service = ref(null);
const path = ref(route.path.substring(1));

/** methods */
watch(path, () => {
  init();
});

watch(() => route.path, (newPath) => {
  path.value = newPath.substring(1);
}, { immediate: true });

const init = () => { 
  store.commit('app/enableLoading');
  import(`@/services/${path.value}.service`).then((data) => {
    service.value = data.default;
    retrieveAllSubEntities();
  }).catch(err => {
    AlertService.showHttpError(this, err.response);
  });
  CategoryService
    .findByEntityName(path.value)
    .then(res => {
      category.value = res;
    })
    .catch(err => {
      AlertService.showHttpError(this, err.response);
    });
};

const retrieveAllSubEntities = () => {
  store.commit('app/enableLoading');
  
  // Verwende optimierte Methode für Mobility und Contact, normale Methode für andere Entitäten
  const method = (path.value === 'mobility' || path.value === 'contact') ? 'retrieveAllForOverview' : 'retrieveAll';
  
  service.value[method]()
    .then(res => {
      subEntities.value = res.data;
    })
    .catch(err => {
      AlertService.showHttpError(this, err.response);
    })
    .finally(() => {
      store.commit('app/disableLoading');
    });
};

const addEntity = () => {
  store.commit('app/enableLoading');

  service.value
    .checkIfUserIsAllowedToAdd()
    .then(() => {
      // Sonderfall: Kontakte sollen direkt über die einfache Kontaktmaske angelegt werden
      if (category.value?.entityName === 'contact') {
        router.push({ name: 'ContactCreate' });
        return;
      }
      if (isSingleGroupType(category.value?.entityName)) {
        GroupTypeService
          .retrieveByEntityName(category.value?.entityName)
          .then((res) => {
            if (res.data.length) {
              const groupType = res.data[0];
              
              // Validierung der erforderlichen IDs
              if (!groupType?.id || !groupType?.subCategoryGroup?.id || !groupType?.subCategoryGroup?.subCategory?.id) {
                AlertService.showError('Fehler', 'Ungültige Gruppentyp-Hierarchie gefunden.');
                return;
              }
              
              router.push({
                name: 'CategoryCreateWithGroupType', params: {
                  id: category.value?.id,
                  subid: groupType.subCategoryGroup.subCategory.id,
                  subgrpid: groupType.subCategoryGroup.id,
                  grptpid: groupType.id
                }
              });
            }
          })
          .catch(err => {
            
          });
      } else if (isSelectGroupType(category.value?.entityName)) {
        GroupTypeService
          .retrieveByEntityName(category.value?.entityName)
          .then((res) => {
            if (res.data.length) {
              const groupType = res.data[0];
              
              // Validierung der erforderlichen IDs
              if (!groupType?.subCategoryGroup?.id || !groupType?.subCategoryGroup?.subCategory?.id) {
                AlertService.showError('Fehler', 'Ungültige Gruppentyp-Hierarchie gefunden.');
                return;
              }
              
              router.push({
                name: 'CategorySelect', params: { id: category.value?.id }, query: {
                  subid: groupType.subCategoryGroup.subCategory.id,
                  subgrpid: groupType.subCategoryGroup.id
                }
              });
            }
          })
          .catch(err => {
            
          });
      } else {
        router.push('/category/' + category.value?.id + '/select');
      }
    })
    .catch(() => {
    })
    .finally(() => {
      store.commit('app/disableLoading');
    });
};

init();

defineExpose({ category, subEntities, customEntities });

watch(category, () => {
  CustomFormInputService
    .findAllByCategoryId(category.value.id)
    .then(res => {
      customEntities.value = res;
    })
    .catch(() => {});
});
</script>