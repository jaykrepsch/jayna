<template>
  <div class="flex flex-col justify-center sm:px-6 lg:px-8">
    <div class="my-8 sm:mx-auto sm:w-full sm:max-w-md">
      <div class="bg-white py-8 px-4 shadow sm:rounded-lg sm:px-10">
        <div class="row justify-content-center">
          <Alert
            v-if="success"
            type="success"
            headerKey="register.messages.success.header"
            messageKey="register.messages.success.message"
          />
          
          <Alert
            v-if="error"
            type="danger"
            headerKey="register.messages.error.fail.header"
            messageKey="register.messages.error.fail.message"
          />

          <Alert
            v-if="errorUserExists"
            type="danger"
            headerKey="register.messages.error.userexists.header"
            messageKey="register.messages.error.userexists.message"
          />

          <Alert
            v-if="errorEmailExists"
            type="danger"
            headerKey="register.messages.error.emailexists.header"
            messageKey="register.messages.error.emailexists.message"
          />
        </div>

        <h1
          class="text-center pb-4"
          v-text="$t('register.title')"
        />
        <form class="space-y-6" action="#" method="POST">
          <s-input 
            name="login"
            type="text"
            :placeholder="$t('global.form[\'username.placeholder\']')"
            :label="$t('global.form[\'username.label\']')"
            v-model="v$.login.$model"
            :errors="v$.login.$errors"
          />

          <s-input 
            name="email"
            type="email"
            :placeholder="$t('global.form[\'email.placeholder\']')"
            :label="$t('global.form[\'email.label\']')"
            v-model="v$.email.$model"
            :errors="v$.email.$errors"
          />

          <s-input 
            name="password"
            type="password"
            :placeholder="$t('global.form[\'password.placeholder\']')"
            :label="$t('global.form[\'password.label\']')"
            v-model="v$.password.$model"
            :errors="v$.password.$errors"
          />

          <s-input 
            name="password"
            type="password"
            :placeholder="$t('global.form[\'confirmpassword.placeholder\']')"
            :label="$t('global.form[\'confirmpassword.label\']')"
            v-model="v$.confirmPassword.$model"
            :errors="v$.confirmPassword.$errors"
          />

          <div class="text-sm">
            <router-link class="font-medium text-indigo-600 hover:text-indigo-500" :to="{ name: 'ResetPasswordInit' }"
              v-text="$t('login.password.forgot')" />
          </div>

          <Button
            :text="$t('register.form.button')"
            @click.prevent="register()"
            :disabled="v$.$invalid"
          />

        </form>
      </div>
    </div>
  </div>
</template>

<script setup>

import { ref, computed, reactive } from 'vue';
import { email, maxLength, minLength, sameAs } from '@/shared/config/validators';

import loginService from '@/services/login.service';
import registerService from '@/services/register.service';
import { EMAIL_ALREADY_USED_TYPE, LOGIN_ALREADY_USED_TYPE } from '@/constants';

import useVuelidate from '@vuelidate/core';
import { helpers } from '@vuelidate/validators';
import { useStore } from 'vuex';

import SInput from '@/components/forms/input.vue';
import Button from '@/components/buttons/button.vue';
import Alert from '@/components/alert.vue';

const store = useStore();

const loginPattern = helpers.regex('alpha', /^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$/);

/** data */
const state = reactive({
  login: null,
  email: null,
  password: null,
  confirmPassword: null,
  langKey: null
});

const rules = computed(() => ({
  login: {
    minLength: minLength(1),
    maxLength: maxLength(50),
    // pattern: loginPattern,
  },
  email: {
    email,
    minLength: minLength(5),
    maxLength: maxLength(254),
  },
  password: {
    minLength: minLength(4),
    maxLength: maxLength(254),
  },
  confirmPassword: {
    minLength: minLength(4),
    maxLength: maxLength(50),
    sameAsPassword: sameAs(state.password),
  }
}));

const v$ = useVuelidate(rules, state);

const error = ref('');
const errorEmailExists = ref('');
const errorUserExists = ref('');
const success = ref(false);


/** methods */

const register = () => {
  error.value = null;
  errorUserExists.value = null;
  errorEmailExists.value = null;
  state.langKey = store.getters['translation/currentLanguage'];
  registerService
    .processRegistration(state)
    .then(() => {
      success.value = true;
    })
    .catch(error => {
      success.value = null;
      if (error.response && error.response.status === 400 && error.response.data && error.response.data.type === LOGIN_ALREADY_USED_TYPE) {
        errorUserExists.value = 'ERROR';
      } else if (error.response && error.response.status === 400 && error.response.data && error.response.data.type === EMAIL_ALREADY_USED_TYPE) {
        errorEmailExists.value = 'ERROR';
      } else {
        error.value = 'ERROR';
      }
    });
};

const openLogin = () => {
  loginService.openLogin();
};

</script>
