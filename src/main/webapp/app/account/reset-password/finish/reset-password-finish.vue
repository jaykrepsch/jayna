<template>
  <div class="flex flex-col justify-center sm:px-6 lg:px-8">
    <div class="my-8 sm:mx-auto sm:w-full sm:max-w-md">
      <div class="bg-white py-8 px-4 shadow sm:rounded-lg sm:px-10">
        <div class="row justify-content-center">
          <Alert
            v-if="keyMissing"
            type="danger"
            messageKey="reset.finish.messages.keymissing"
          />
          
          <Alert
            v-if="success"
            type="success"
            messageKey="reset.finish.messages.success"
          />

          <Alert
            v-if="doNotMatch"
            type="danger"
            messageKey="global.messages.error.dontmatch"
          />

          <Alert
            v-if="(!success && !keyMissing)"
            type="warn"
            messageKey="reset.finish.messages.info"
          />
        </div>

        <h1
          v-if="!success"
          class="text-center pb-4"
          v-text="$t('reset.request.title')"
        />
        <form 
          v-if="!success"
          class="space-y-6"
          action="#"
          method="POST"
        >

          <s-input 
            name="newPassword"
            type="password"
            :placeholder="$t('global.form[\'password.placeholder\']')"
            :label="$t('global.form[\'password.label\']')"
            v-model="v$.newPassword.$model"
            :errors="v$.newPassword.$errors"
          />

          <s-input 
            name="confirmPassword"
            type="password"
            :placeholder="$t('global.form[\'confirmpassword.placeholder\']')"
            :label="$t('global.form[\'confirmpassword.label\']')"
            v-model="v$.confirmPassword.$model"
            :errors="v$.confirmPassword.$errors"
          />

          <Button
            :text="$t('password.form.button')"
            @click.prevent="finishReset()"
            :disabled="v$.$invalid"
          />

        </form>
        <div
          v-else
        >
          <Button
            :text="$t('global.messages.info.authenticated.link')"
            :size="'md'"
            @click="openLogin()"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onBeforeMount } from 'vue';
import useVuelidate from '@vuelidate/core';
import { useRoute } from 'vue-router';

import api from "@/services/api.service";
import { maxLength, minLength, sameAs } from '@/shared/config/validators';
import LoginService from '@/services/login.service';

import SInput from '@/components/forms/input.vue';
import Button from '@/components/buttons/button.vue';
import Alert from '@/components/alert.vue';

const route = useRoute();

/** data */
const state = reactive({
  newPassword: null,
  confirmPassword: null
});

const rules = computed(() => ({
  newPassword: {
    minLength: minLength(4),
    maxLength: maxLength(254),
  },
  confirmPassword: {
    minLength: minLength(4),
    maxLength: maxLength(50),
    sameAsPassword: sameAs(state.newPassword),
  },
}));

const v$ = useVuelidate(rules, state);

const doNotMatch = ref(null);
const success = ref(null);
const error = ref(null);
const keyMissing = ref(false);
const key = ref(null);

/** methods */
onBeforeMount(() => { 
  if (route?.query?.key !== undefined) {
    key.value = route.query.key;
  }
  keyMissing.value = !key.value;
});

const finishReset = () => {
  doNotMatch.value = null;
  success.value = null;
  error.value = null;
  if (state.newPassword !== state.confirmPassword) {
    doNotMatch.value = 'ERROR';
  } else {
    api
      .post('api/account/reset-password/finish', { key: key.value, newPassword: state.newPassword })
      .then(() => {
        success.value = 'OK';
      })
      .catch(() => {
        success.value = null;
        error.value = 'ERROR';
      });
  }  
};

const openLogin = () => {
  LoginService.openLogin();
};

</script>
