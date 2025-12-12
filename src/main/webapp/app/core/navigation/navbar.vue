<template>
  <div :class="navbarMainClass">
    <Logo
      v-if="!authenticated"
    />
    <button
      type="button"
      class="border-r border-gray-200 px-4 text-gray-500 focus:outline-none focus:ring-2 focus:ring-inset focus:ring-indigo-500 md:hidden"
      @click="sidebarOpen = true"
    >
      <span class="sr-only">Open sidebar</span>
      <Bars3BottomLeftIcon class="h-6 w-6" aria-hidden="true" />
    </button>
    <div class="flex flex-1 justify-end px-4">
      <div class="ml-4 flex items-center md:ml-6">
        <Menu 
          as="div"
          class="relative ml-3"
        >
          <div>
            <MenuButton class="flex max-w-xs items-center rounded-full bg-white text-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2">
              <span class="inline-block h-8 w-8 overflow-hidden rounded-full bg-gray-100">
                <svg class="h-full w-full text-gray-300" fill="currentColor" viewBox="0 0 24 24">
                  <path d="M24 20.993V24H0v-2.996A14.977 14.977 0 0112.004 15c4.904 0 9.26 2.354 11.996 5.993zM16.002 8.999a4 4 0 11-8 0 4 4 0 018 0z" />
                </svg>
              </span>
            </MenuButton>
          </div>
          <transition
            enter-active-class="transition ease-out duration-100"
            enter-from-class="transform opacity-0 scale-95"
            enter-to-class="transform opacity-100 scale-100"
            leave-active-class="transition ease-in duration-75"
            leave-from-class="transform opacity-100 scale-100"
            leave-to-class="transform opacity-0 scale-95"
          >
            <MenuItems
              class="z-20 absolute right-0 z-10 mt-2 w-48 origin-top-right rounded-md bg-white py-1 shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none"
            >
              <MenuItem
                v-for="item in menuItems" 
                :key="item.name"
                v-slot="{ active }"
              >
                <span
                  :class="[active ? 'bg-gray-100' : '', 'block px-4 py-2 text-sm text-gray-700 uppercase cursor-pointer']"
                  v-text="$t(item.textKey)"
                  @click="item.action"
                />
              </MenuItem>
            </MenuItems>
          </transition>
        </Menu>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useStore } from 'vuex';
import { useRoute, useRouter } from 'vue-router';

import {
  Bars3BottomLeftIcon,
  BellIcon,
  UserCircleIcon,
  XMarkIcon,
} from '@heroicons/vue/24/outline'

import {
  Menu,
  MenuButton,
  MenuItem,
  MenuItems
} from '@headlessui/vue'

import loginService from '@/services/login.service';
import accountService from '@/services/account.service';
import translationService from '@/services/translation.service';

import Logo from '@/components/logo.vue';

const store = useStore();
const router = useRouter();
const route = useRoute();

translationService.refreshTranslation(store?.getters ? store.getters['translation/currentLanguage'] : 'de');

/** data */

const currentLanguage = ref(store?.getters ? store.getters['translation/currentLanguage'] : 'de');
const languages = ref(store?.getters ? store.getters['translation/languages'] : {});
const hasAnyAuthorityValues = ref({});
const unauthenticatedItems = [{
  name: 'login',
  action: () => loginService.openLogin(),
  textKey: 'global.menu.account.login'
}, {
  name: 'register',
  action: () => router.push({ name: 'Register' }),
  textKey: 'global.menu.account.register'
}];

const authenticatedItems = [{
  name: 'settings',
  action: () => router.push({ name: 'Settings' }),
  textKey: 'global.menu.account.settings'
}, {
  name: 'changePassword',
  action: () => router.push({ name: 'ChangePassword' }),
  textKey: 'global.menu.account.password'
}, {
  name: 'logout',
  action: () => logout(),
  textKey: 'global.menu.account.logout'
  }];


/** computed properties */
const authenticated = computed(() => { 
  return store?.getters ? store.getters['account/authenticated'] : false;
});

const openAPIEnabled = computed(() => {
  return store?.getters && store.getters['account/activeProfiles'] ? store.getters['account/activeProfiles'].indexOf('api-docs') > -1 : false;
});

const menuItems = computed(() => { 
  if (authenticated.value) {
    return authenticatedItems;
  } else {
    return unauthenticatedItems;
  }
});

/** methods */
const subIsActive = (input) => {
  const paths = Array.isArray(input) ? input : [input];
  return paths.some(path => {
    return route.path.indexOf(path) === 0; // current path starts with this path string
  });
};

const changeLanguage = (newLanguage) => {
  translationService.refreshTranslation(newLanguage);
};

const isActiveLanguage = (key) => {
  return store?.getters ? key === store.getters['translation/currentLanguage'] : key === 'de';
};

const logout = () => {
  localStorage.removeItem('jhi-authenticationToken');
  sessionStorage.removeItem('jhi-authenticationToken');
  store.commit('account/logout');
  if (route.path !== '/') {
    return router.push('/');
  }
  return Promise.resolve(router.currentRoute);
};

const openLogin = () => {
  loginService.openLogin();
};

const hasAnyAuthority = (authorities) => {
  accountService
    .hasAnyAuthorityAndCheckAuth(authorities)
    .then(value => {
      if (hasAnyAuthorityValues.value[authorities] !== value) {
        hasAnyAuthorityValues.value = { ...hasAnyAuthorityValues.value, [authorities]: value };
      }
    });
  return hasAnyAuthorityValues.value[authorities] ?? false;
};

const inProduction = () => {
  return store?.getters && store.getters['account/activeProfiles'] ? store.getters['account/activeProfiles'].indexOf('prod') > -1 : false;
};

const props = defineProps({
  sidebarOpen: {
    type: Boolean,
    default: true
  }
});

const navbarMainClass = computed(() => {
  let clz = 'flex h-16 flex-shrink-0 bg-white shadow fixed top-0 right-0 z-20';
  if (authenticated.value) {
    if (props.sidebarOpen) {
      clz += ' md:left-64';
    } else {
      clz += ' md:left-16';
    }
  } else {
    clz += ' md:left-0';
  }
  return clz;
});

</script>

<style scoped>
</style>
