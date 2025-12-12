<template>
  <Modal
    :open="open"
    @close="loginService.closeLogin()"
  >
    <template v-slot:content>
      <div>
        <div class="row justify-content-center">
          <Alert
            v-if="authenticationError"
            type="danger"
            headerKey="login.messages.error.authentication.header"
            messageKey="login.messages.error.authentication.message"
          />
        </div>

        <form class="space-y-6" action="#" method="POST">
          <div>
            <label for="login" class="block text-sm font-medium text-gray-700" v-text="$t('login.form.username.label')" />
            <div class="mt-1">
              <input id="login" name="login" type="text" autocomplete="login" required=""
                class="block w-full appearance-none rounded-md border border-gray-300 px-3 py-2 placeholder-gray-400 shadow-sm focus:border-indigo-500 focus:outline-none focus:ring-indigo-500 sm:text-sm"
                v-model="login" />
            </div>
          </div>
        
          <div>
            <label for="password" class="block text-sm font-medium text-gray-700" v-text="$t('login.form.password')" />
            <div class="mt-1">
              <input id="password" name="password" type="password" autocomplete="current-password" required=""
                class="block w-full appearance-none rounded-md border border-gray-300 px-3 py-2 placeholder-gray-400 shadow-sm focus:border-indigo-500 focus:outline-none focus:ring-indigo-500 sm:text-sm"
                v-model="password" />
            </div>
          </div>
        
          <div class="flex flex-col items-left gap-y-3">
            <div class="flex items-center">
              <input id="remember-me" name="remember-me" type="checkbox"
                class="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500" v-model="rememberMe" />
              <label for="remember-me" class="ml-2 block text-sm text-gray-900" v-text="$t('login.form.rememberme')" />
            </div>
        
            <div class="text-sm">
              <router-link class="font-medium text-indigo-600 hover:text-indigo-500" :to="{ name: 'ResetPasswordInit' }"
                v-text="$t('login.password.forgot')" />
            </div>
            <div class="text-sm">
            <!--
              <router-link class="font-medium text-indigo-600 hover:text-indigo-500" :to="{ name: 'Register' }"
                v-text="$t('login.register.link')" />
            -->
            </div>
          </div>
        
          <div class="flex flex-col gap-y-1">
            <Button :text="$t('login.form.button')" @click.prevent="doLogin()" />
            <Button :text="$t('button.cancel')" :type="'secondary'" @click.prevent="loginService.closeLogin()" />
          </div>
        </form>
      </div>
      
    </template>
  </Modal>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useStore } from 'vuex';
import { onBeforeRouteUpdate, useRouter } from 'vue-router';

import { Dialog, DialogPanel, DialogTitle, TransitionChild, TransitionRoot } from '@headlessui/vue'
import { CheckIcon } from '@heroicons/vue/24/outline'

import Modal from '@/components/modals/modal.vue';
import Button from '@/components/buttons/button.vue';
import Alert from '@/components/alert.vue';

import api from '@/services/api.service';
import accountService from '@/services/account.service';
import loginService from '@/services/login.service';

const store = useStore();
const router = useRouter();

const authenticationError = ref(null);
const login = ref(null);
const password = ref(null);
const rememberMe = ref(false);

const open = computed(() => { 
  return store.state.app.showLoginModal;
});

const doLogin = () => {
  
  const data = { username: login.value, password: password.value, rememberMe: rememberMe.value };
  
  api
    .post('api/authenticate', data)
    .then(result => {
      const bearerToken = result.headers.authorization;
      if (bearerToken && bearerToken.slice(0, 7) === 'Bearer ') {
        const jwt = bearerToken.slice(7, bearerToken.length);
        store.commit('account/setToken', jwt);
        // if (rememberMe.value) {
        //   store.commit('account/setToken', jwt);
        //   localStorage.setItem('jhi-authenticationToken', jwt);
        //   sessionStorage.removeItem('jhi-authenticationToken');
        // } else {
        //   sessionStorage.setItem('jhi-authenticationToken', jwt);
        //   localStorage.removeItem('jhi-authenticationToken');
        // }
      }
      authenticationError.value = false;
      loginService.closeLogin();
      accountService.retrieveAccount();
      router.push({ name: 'Home' });
    })
    .catch((error) => {
      authenticationError.value = true;
    });
};

</script>
