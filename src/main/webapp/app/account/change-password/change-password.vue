<template>
  <div class="flex flex-col justify-center sm:px-6 lg:px-8">
    <div class="my-8 sm:mx-auto sm:w-full sm:max-w-md">
      <div class="bg-white py-8 px-4 shadow sm:rounded-lg sm:px-10">
        <div class="row justify-content-center">
          <Alert
            v-if="success"
            type="success"
            messageKey="password.messages.success.message"
          />
          
          <Alert
            v-if="error"
            type="danger"
            headerKey="password.messages.error.header"
            messageKey="password.messages.error.message"
          />
        </div>

        <h1 class="text-center pb-4" v-text="$t('password.title')" />

        <form class="space-y-6" action="#" method="POST">
          <s-input 
            name="currentPassword"
            type="password"
            :placeholder="$t('global.form[\'currentpassword.placeholder\']')"
            :label="$t('global.form[\'currentpassword.label\']')"
            v-model="v$.currentPassword.$model"
            :errors="v$.currentPassword.$errors"
          />

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
            @click.prevent="changePassword()"
            :disabled="v$.$invalid"
          />

        </form>

      </div>
    </div>
  </div>
</template>

<script setup>
import { maxLength, minLength, sameAs } from '@/shared/config/validators';
import api from '@/services/api.service';
import useVuelidate from '@vuelidate/core';
import { mapGetters } from 'vuex';
import { useStore } from 'vuex';
import { ref, reactive, computed } from '@vue/reactivity';

import SInput from '@/components/forms/input.vue';
import Button from '@/components/buttons/button.vue';
import Alert from '@/components/alert.vue';

const store = useStore();

/** data */
const success = ref(null);
const error = ref(null);
const doNotMatch = ref(null);

const state = reactive({
  currentPassword: null,
  newPassword: null,
  confirmPassword: null
});

const rules = computed(() => ({
  currentPassword: {
    minLength: minLength(4),
    maxLength: maxLength(254),
  },
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

/** methods */
const changePassword = () => {
  if (state.newPassword !== state.confirmPassword) {
    error.value = null;
    success.value = null;
    doNotMatch.value = 'ERROR';
  } else {
    doNotMatch.value = null;
    api
      .post('api/account/change-password', {
        currentPassword: state.currentPassword,
        newPassword: state.newPassword,
      })
      .then(() => {
        success.value = 'OK';
        error.value = null;
      })
      .catch(() => {
        success.value = null;
        error.value = 'ERROR';
      });
  }
};

const username = () => {
  return store.getters['account/account']?.login ?? '';
};

</script>
