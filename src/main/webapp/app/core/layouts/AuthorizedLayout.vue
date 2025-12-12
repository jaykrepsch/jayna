<template>
  <div class="h-screen">
    <div
      v-if="authenticated"
      :class="sidebarClass"
    >
      <Sidebar 
        @toggle="toggleSidebar()"
      />
    </div>

    <div :class="mainContainerClass">
      <Navbar 
        :sidebar-open="sidebarOpen"
      />

      <main 
        class="flex-1 pt-24 pb-10 md:px-12 sm:px-6"
      >
        <LoadingSpinner v-show="loading" />
        <router-view v-show="!loading" />
      </main>
    
      <Footer />
    </div>

    <LoginForm />

    <DangerConfirmation />

    <ErrorModal />
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useStore } from 'vuex';

import {
  Dialog,
  DialogPanel,
  Menu,
  MenuButton,
  MenuItem,
  MenuItems,
  TransitionChild,
  TransitionRoot,
} from '@headlessui/vue'
import {
  CalendarIcon,
  ChartBarIcon,
  FolderIcon,
  HomeIcon,
  InboxIcon,
  UsersIcon,
  XMarkIcon
} from '@heroicons/vue/24/outline'

import TranslationService from '@/services/translation.service';
import AccountService from '@/services/account.service';

import LoadingSpinner from '@/components/LoadingSpinner.vue';
import { Authority } from "@/shared/security/authority";

import Navbar from '@/core/navigation/navbar.vue';
import Sidebar from '@/core/navigation/sidebar.vue';
import Footer from '@/core/navigation/footer.vue';

import LoginForm from '@/account/login-form/login-form.vue';
import DangerConfirmation from '@/components/modals/danger-confirmation.vue';
import ErrorModal from '@/components/modals/ErrorModal.vue';

const store = useStore();
const sidebarOpen = ref(true);

const toggleSidebar = () => {
  sidebarOpen.value = !sidebarOpen.value;
};

const authenticated = computed(() => {
  return store && store.getters ? store.getters['account/authenticated'] : (store && store.state && store.state.account ? store.state.account.authenticated : false);
});

const loading = computed(() => {
  return store && store.getters ? store.getters['app/loading'] : (store && store.state && store.state.app ? store.state.app.loading : false);
});

const mainContainerClass = computed(() => {
  let clz = 'flex flex-col h-full';
  if (authenticated.value) {
    if (sidebarOpen.value) {
      clz += ' md:pl-64';
    } else {
      clz += ' md:pl-16';
    }
  }
  return clz;
});

const sidebarClass = computed(() => {
  let clz = 'hidden md:fixed md:inset-y-0 md:flex md:flex-col';
  if (sidebarOpen.value) {
    clz += ' md:w-64';
  } else {
    clz += ' md:w-16';
  }
  return clz;
});

onMounted(() => {
  if (authenticated.value) {
    store.commit('config/init');
    AccountService.hasAnyAuthorityAndCheckAuth(store.state.account.userIdentity?.authorities[0]);
  }

  TranslationService.refreshTranslation('de');
  AccountService.retrieveAccount();
});
</script>