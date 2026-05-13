<template>
  <div class="flex flex-grow flex-col overflow-y-auto border-r border-gray-200 bg-white pt-5">
    <Logo 
      :show-logo-text="open"
    />
    <div class="mt-5 flex flex-grow flex-col">
      <nav class="flex flex-col space-y-1 px-2 pb-4">
        <!-- Dashboard-Link oberhalb von Verträge -->
        <router-link 
          :to="{ name: 'Home' }"
          :class="[
            'group flex items-center px-2 py-2 text-sm font-medium rounded-md space-x-4',
            open ? 'text-center' : 'justify-center',
            'text-gray-600 hover:bg-gray-50 hover:text-gray-900'
          ]"
        >
          <span>
            <HomeIcon class="h-5 w-5" aria-hidden="true" />
          </span>
          <span v-if="open" v-text="t('jaynaApp.sidebar.dashboard')" />
        </router-link>
        <Divider />
        <template v-for="category in categories" :key="category.id">
          <template v-if="category.entityName === 'contact'">
            <!-- Platzhalter unter Mobilität (oberhalb Divider zu Kontakte) -->
            <template v-if="isAdmin">
              <router-link
                :to="'/devices'"
                :class="[
                  'group flex items-center px-2 py-2 text-sm font-medium rounded-md space-x-4',
                  open ? 'text-center' : 'justify-center',
                  'text-gray-400 hover:text-gray-500'
                ]"
              >
                <span>
                  <ComputerDesktopIcon class="h-5 w-5" aria-hidden="true" />
                </span>
                <span v-if="open" v-text="t('jaynaApp.sidebar.devicesStar')" />
              </router-link>

              <router-link
                :to="'/items'"
                :class="[
                  'group flex items-center px-2 py-2 text-sm font-medium rounded-md space-x-4',
                  open ? 'text-center' : 'justify-center',
                  'text-gray-400 hover:text-gray-500'
                ]"
              >
                <span>
                  <CubeIcon class="h-5 w-5" aria-hidden="true" />
                </span>
                <span v-if="open" v-text="t('jaynaApp.sidebar.itemsStar')" />
              </router-link>

              <router-link
                :to="'/clothes'"
                :class="[
                  'group flex items-center px-2 py-2 text-sm font-medium rounded-md space-x-4',
                  open ? 'text-center' : 'justify-center',
                  'text-gray-400 hover:text-gray-500'
                ]"
              >
                <span>
                  <ShoppingBagIcon class="h-5 w-5" aria-hidden="true" />
                </span>
                <span v-if="open" v-text="t('jaynaApp.sidebar.clothesStar')" />
              </router-link>

              <router-link
                :to="'/software'"
                :class="[
                  'group flex items-center px-2 py-2 text-sm font-medium rounded-md space-x-4',
                  open ? 'text-center' : 'justify-center',
                  'text-gray-400 hover:text-gray-500'
                ]"
              >
                <span>
                  <CommandLineIcon class="h-5 w-5" aria-hidden="true" />
                </span>
                <span v-if="open" v-text="t('jaynaApp.sidebar.softwareStar')" />
              </router-link>

              <router-link
                :to="'/sports'"
                :class="[
                  'group flex items-center px-2 py-2 text-sm font-medium rounded-md space-x-4',
                  open ? 'text-center' : 'justify-center',
                  'text-gray-400 hover:text-gray-500'
                ]"
              >
                <span>
                  <TrophyIcon class="h-5 w-5" aria-hidden="true" />
                </span>
                <span v-if="open" v-text="t('jaynaApp.sidebar.sportsStar')" />
              </router-link>

              <router-link
                :to="'/travel'"
                :class="[
                  'group flex items-center px-2 py-2 text-sm font-medium rounded-md space-x-4',
                  open ? 'text-center' : 'justify-center',
                  'text-gray-400 hover:text-gray-500'
                ]"
              >
                <span>
                  <GlobeAltIcon class="h-5 w-5" aria-hidden="true" />
                </span>
                <span v-if="open" v-text="t('jaynaApp.sidebar.travelStar')" />
              </router-link>

              <router-link
                :to="'/animals'"
                :class="[
                  'group flex items-center px-2 py-2 text-sm font-medium rounded-md space-x-4',
                  open ? 'text-center' : 'justify-center',
                  'text-gray-400 hover:text-gray-500'
                ]"
              >
                <span>
                  <BugAntIcon class="h-5 w-5" aria-hidden="true" />
                </span>
                <span v-if="open" v-text="t('jaynaApp.sidebar.animalsStar')" />
              </router-link>

              <router-link
                :to="'/damages'"
                :class="[
                  'group flex items-center px-2 py-2 text-sm font-medium rounded-md space-x-4',
                  open ? 'text-center' : 'justify-center',
                  'text-gray-400 hover:text-gray-500'
                ]"
              >
                <span>
                  <ExclamationTriangleIcon class="h-5 w-5" aria-hidden="true" />
                </span>
                <span v-if="open" v-text="t('jaynaApp.sidebar.damagesStar')" />
              </router-link>

              <!-- Über Kontakte: Kontakte Kalender* (oberhalb Kontakte) -->
              <Divider />
              <router-link
                :to="'/calendar'"
                :class="[
                  'group flex items-center px-2 py-2 text-sm font-medium rounded-md space-x-4',
                  open ? 'text-center' : 'justify-center',
                  'text-gray-400 hover:text-gray-500'
                ]"
              >
                <span>
                  <CalendarDaysIcon class="h-5 w-5" aria-hidden="true" />
                </span>
                <span v-if="open" v-text="t('jaynaApp.sidebar.calendarStar')" />
              </router-link>
            </template>
            <template v-else>
              <Divider />
            </template>
          </template>
          <template v-if="category.entityName === 'document'">
            <Divider />
          </template>

          <router-link 
            :to="`/${category.entityName}`"
            @click="selectedCategory = category"
            :class="[
              selectedCategory == category ? 'bg-gray-200 text-gray-900 font-bold' : 'text-gray-600 hover:bg-gray-50 hover:text-gray-900',
              open ? 'text-center' : 'justify-center',
              'group flex items-center px-2 py-2 text-sm font-medium rounded-md space-x-4'
            ]"
          >
            <span>
              <BuildingLibraryIcon 
                v-if="category.entityName === 'realestate'"
                class="h-5 w-5" 
                aria-hidden="true"
              />

              <ClipboardIcon 
                v-if="category.entityName === 'contract'"
                class="h-5 w-5" 
                aria-hidden="true"
              />

              <UserGroupIcon 
                v-if="category.entityName === 'contact'"
                class="h-5 w-5" 
                aria-hidden="true"
              />

              <BanknotesIcon
                v-if="category.entityName === 'financeaccount'"
                class="h-5 w-5" 
                aria-hidden="true"
              />

              <TruckIcon
                v-if="category.entityName === 'mobility'"
                class="h-5 w-5" 
                aria-hidden="true"
              />

              <DocumentIcon
                v-if="category.entityName === 'document'"
                class="h-5 w-5" 
                aria-hidden="true"
              />
            </span>

            <span
              v-if="open"
              v-text="getCategoryDisplayName(category)"
            />
          </router-link>

          
          <!-- Unter Kontakte: Aufgaben*, Notizen* -->
          <template v-if="category.entityName === 'contact' && isAdmin">
            <router-link
              :to="'/tasks'"
              :class="[
                'group flex items-center px-2 py-2 text-sm font-medium rounded-md space-x-4',
                open ? 'text-center' : 'justify-center',
                'text-gray-400 hover:text-gray-500'
              ]"
            >
              <span>
                <ClipboardDocumentCheckIcon class="h-5 w-5" aria-hidden="true" />
              </span>
              <span v-if="open" v-text="t('jaynaApp.sidebar.tasksStar')" />
            </router-link>

            <router-link
              :to="'/notes'"
              :class="[
                'group flex items-center px-2 py-2 text-sm font-medium rounded-md space-x-4',
                open ? 'text-center' : 'justify-center',
                'text-gray-400 hover:text-gray-500'
              ]"
            >
              <span>
                <PencilSquareIcon class="h-5 w-5" aria-hidden="true" />
              </span>
              <span v-if="open" v-text="t('jaynaApp.sidebar.notesStar')" />
            </router-link>
          </template>

          <!-- Unter Dokumente: Vertragsstatus*, Datenqualität*, Dokumenten‑Inbox* -->
          <template v-if="category.entityName === 'document' && isAdmin">
            <router-link
              :to="'/contract-status'"
              :class="[
                'group flex items-center px-2 py-2 text-sm font-medium rounded-md space-x-4',
                open ? 'text-center' : 'justify-center',
                'text-gray-400 hover:text-gray-500'
              ]"
            >
              <span>
                <ClipboardDocumentListIcon class="h-5 w-5" aria-hidden="true" />
              </span>
              <span v-if="open" v-text="t('jaynaApp.sidebar.contractStatusStar')" />
            </router-link>

            <router-link
              :to="'/data-quality'"
              :class="[
                'group flex items-center px-2 py-2 text-sm font-medium rounded-md space-x-4',
                open ? 'text-center' : 'justify-center',
                'text-gray-400 hover:text-gray-500'
              ]"
            >
              <span>
                <ShieldExclamationIcon class="h-5 w-5" aria-hidden="true" />
              </span>
              <span v-if="open" v-text="t('jaynaApp.sidebar.dataQualityStar')" />
            </router-link>

            <router-link
              :to="'/document-inbox'"
              :class="[
                'group flex items-center px-2 py-2 text-sm font-medium rounded-md space-x-4',
                open ? 'text-center' : 'justify-center',
                'text-gray-400 hover:text-gray-500'
              ]"
            >
              <span>
                <InboxIcon class="h-5 w-5" aria-hidden="true" />
              </span>
              <span v-if="open" v-text="t('jaynaApp.sidebar.documentInboxStar')" />
            </router-link>
          </template>

        </template>
      </nav>
      
    </div>
    <div 
      :class="'flex justify-end w-full p-4 text-gray-600'"
    >
        <ChevronDoubleLeftIcon
          v-if="open"
          class="h-8 w-8 rounded-full p-2 hover:bg-gray-200 hover:text-gray-600 cursor-pointer"
          @click="toggleSidebar()"
        />
        <ChevronDoubleRightIcon
          v-if="!open"
          class="h-8 w-8 rounded-full p-2 hover:bg-gray-200 hover:text-gray-600 cursor-pointer"
          @click="toggleSidebar()"
        />

    </div>
  </div>
</template>

<script setup>

import { ref, watch, computed } from 'vue';
import { useI18n } from 'vue-i18n';

import { useRoute } from 'vue-router';
import { useStore } from 'vuex';

import CategoryService from '@/services/category.service';
import AlertService from '@/services/alert.service';

import Logo from '@/components/logo.vue';
import Divider from '@/components/divider.vue';

import { Authority } from '@/shared/security/authority';

import { ChevronDoubleLeftIcon, ChevronDoubleRightIcon, BuildingLibraryIcon, ClipboardIcon, UserGroupIcon, BugAntIcon, BanknotesIcon, TruckIcon, DocumentIcon, ComputerDesktopIcon, CubeIcon, ShoppingBagIcon, CommandLineIcon, TrophyIcon, GlobeAltIcon, ExclamationTriangleIcon, CalendarDaysIcon, ClipboardDocumentCheckIcon, PencilSquareIcon, ClipboardDocumentListIcon, ShieldExclamationIcon, InboxIcon, HomeIcon } from '@heroicons/vue/24/outline';

const { t, te } = useI18n();

const route = useRoute();
const store = useStore();

const selectedCategory = ref(null);
const categories = ref([]);
const open = ref(true);

const isAdmin = computed(() => {
  const account = store && store.getters ? store.getters['account/account'] : null;
  const authorities = account && account.authorities ? account.authorities : [];
  return Array.isArray(authorities) && (authorities.includes(Authority.ADMIN) || authorities.includes('ROLE_USER_LIGHT'));
});

const retrieveCategories = () => {
  CategoryService
    .retrieve()
    .then(
      res => {
        // Gewünschte Reihenfolge: Verträge, Immobilien, Finanzkonten, Mobilität, Kontakte, Dokumente
        const desiredOrder = ['contract', 'realestate', 'financeaccount', 'mobility', 'contact', 'document'];
        const rank = Object.fromEntries(desiredOrder.map((name, index) => [name, index]));
        categories.value = [...res].sort((a, b) => {
          const ra = rank[a.entityName] ?? Number.MAX_SAFE_INTEGER;
          const rb = rank[b.entityName] ?? Number.MAX_SAFE_INTEGER;
          if (ra !== rb) return ra - rb;
          // Fallback stabile Sortierung nach Name, falls gleicher Rang
          return (a.name || '').localeCompare(b.name || '');
        });
      },
      err => {
        AlertService.showHttpError(this, err.response);
      }
    );
};

const hasContact = computed(() => categories.value.some(c => c.entityName === 'contact'));

const emit = defineEmits(['toggle']);;

const toggleSidebar = () => { 
  open.value = !open.value;
  emit('toggle');
};

// Funktion zur Bestimmung des Anzeigenamens einer Kategorie
const getCategoryDisplayName = (category) => {
  // Versuche zuerst den Übersetzungsschlüssel zu verwenden
  if (category.translationKey) {
    const translationKey = `jaynaApp.category.${category.translationKey}.title`;
    if (te(translationKey)) {
      return t(translationKey);
    }
  }
  
  // Fallback: Versuche es mit dem entityName als Übersetzungsschlüssel
  const entityTranslationKey = `jaynaApp.category.${category.entityName}.title`;
  if (te(entityTranslationKey)) {
    return t(entityTranslationKey);
  }
  
  // Fallback: Verwende den Namen aus der Datenbank
  return category.name;
};

/** watchers */

watch(() => route.path, () => {
  if (!route.params.id) {
    selectedCategory.value = categories.value.find(c => {
      return route.path.endsWith(c.entityName);
    });
  } else {
    selectedCategory.value = categories.value.find(c => {
      return c.id === parseInt(route.params.id);
    });
  }
});

retrieveCategories();

</script>
