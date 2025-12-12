<template>
  <div class="flex flex-col justify-center sm:px-6 lg:px-8">
    <div class="my-8 sm:mx-auto sm:w-full sm:max-w-md">
      <div class="bg-white py-8 px-4 shadow sm:rounded-lg sm:px-10">
        <div class="row justify-content-center">
          <Alert
            v-if="success"
            type="success"
            messageKey="settings.messages.success"
          />

          <Alert
            v-if="error"
            type="danger"
            headerKey="settings.messages.error.fail.header"
            messageKey="settings.messages.error.fail.message"
          />
        
          <Alert
            v-if="errorEmailExists"
            type="danger"
            headerKey="settings.messages.error.emailexists.header"
            messageKey="settings.messages.error.emailexists.message"
          />
        </div>

        <h1
          class="text-center"
          v-text="$t('settings.title')"
        />
        
        <form class="space-y-6" action="#" method="POST">
          <s-input 
            name="firstName"
            type="text"
            :placeholder="$t('settings.form[\'firstName.placeholder\']')"
            :label="$t('settings.form[\'firstName.label\']')"
            v-model="v$.firstName.$model"
            :errors="v$.firstName.$errors"
          />

          <s-input 
            name="lastName"
            type="text"
            :placeholder="$t('settings.form[\'lastName.placeholder\']')"
            :label="$t('settings.form[\'lastName.label\']')"
            v-model="v$.lastName.$model"
            :errors="v$.lastName.$errors"
          />

          <s-input 
            name="email"
            type="email"
            :placeholder="$t('global.form[\'email.placeholder\']')"
            :label="$t('global.form[\'email.label\']')"
            v-model="v$.email.$model"
            :errors="v$.email.$errors"
          />

          <Dropdown
            v-model="v$.authorities.$model"
            label="Rolle ( = Abonnement)"
            box-label="global.choose"
            :items="roleItems"
          />

          <Button
            :text="$t('settings.form.button')"
            @click.prevent="save()"
          />
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue';
import { useStore } from 'vuex';
import useVuelidate from '@vuelidate/core';

import { email, maxLength, minLength } from '@/shared/config/validators';
import api from '@/services/api.service';
import { EMAIL_ALREADY_USED_TYPE } from '@/constants';

import SInput from '@/components/forms/input.vue';
import Button from '@/components/buttons/button.vue';
import Alert from '@/components/alert.vue';
import Dropdown from '@/components/forms/dropdown.vue';

import AccountService from '@/services/account.service';

const store = useStore();

const success = ref(false);
const error = ref(false);
const errorEmailExists = ref(false);
const languages = store.getters['translation/languages'] || [];

const settingsAccount = computed(() => { 
  return store.getters['account/account'];
});

const roleItems = [{
  name: 'ROLE_USER_LIGHT'
}, {
  name: 'ROLE_USER_BASIC'
}, {
  name: 'ROLE_USER_PRO'
}];

const state = reactive({
  firstName: settingsAccount.value.firstName,
  lastName: settingsAccount.value.lastName,
  email: settingsAccount.value.email,
  login: settingsAccount.value.login ?? '',
  langKey: settingsAccount.value.langKey,
  authorities: roleItems.find(item => item.name === settingsAccount.value.authorities[0])
});

const rules = computed(() => ({
  email: {
    email,
    minLength: minLength(5),
    maxLength: maxLength(254),
  },
  firstName: {
    minLength: minLength(0),
    maxLength: maxLength(50),
  },
  lastName: {
    minLength: minLength(0),
    maxLength: maxLength(50),
  },
  authorities: {}
}));

const v$ = useVuelidate(rules, state);

const save = () => {
  state.authorities = [state.authorities.name];

  error.value = null;
  errorEmailExists.value = null;
  api
    .post('api/account', state)
    .then(() => {
      error.value = null;
      success.value = 'OK';
      errorEmailExists.value = null;
      
    })
    .catch(error => {
      success.value = null;
      if (error.response && error.response.status === 400 && error.response.data && error.response.data.type === EMAIL_ALREADY_USED_TYPE) {
        errorEmailExists.value = 'ERROR';
      } else {
        error.value = 'ERROR';
      }
    });
};

</script>
