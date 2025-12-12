<template>
  <div class="flex flex-col justify-center sm:px-6 lg:px-8">
    <div class="my-8 sm:mx-auto sm:w-full sm:max-w-md">
      <div class="bg-white py-8 px-4 shadow sm:rounded-lg sm:px-10">
        <div class="row justify-content-center">
          <Alert
            v-if="success"
            type="success"
            messageKey="reset.request.messages.success"
          />
          
          <Alert
            v-if="!success"
            type="warn"
            messageKey="reset.request.messages.info"
          />
        </div>

        <h1
          class="text-center pb-4"
          v-text="$t('reset.request.title')"
        />

        <form class="space-y-6" action="#" method="POST">
          <s-input 
            name="email"
            type="email"
            :placeholder="$t('global.form[\'email.placeholder\']')"
            :label="$t('global.form[\'email.label\']')"
            v-model="v$.email.$model"
            :errors="v$.email.$errors"
          />

          <Button
            :text="$t('reset.request.form.button')"
            @click.prevent="requestReset()"
            :disabled="v$.$invalid"
          />

        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue';
import useVuelidate from '@vuelidate/core';
import { email, maxLength, minLength } from '@/shared/config/validators';
import api from "@/services/api.service";

import SInput from '@/components/forms/input.vue';
import Button from '@/components/buttons/button.vue';
import Alert from '@/components/alert.vue';


/** data */
const state = reactive({
  email: null
});

const rules = computed(() => ({
  email: {
    email,
    minLength: minLength(5),
    maxLength: maxLength(254),
  },
}));

const v$ = useVuelidate(rules, state);

const success = ref(false);
const error = ref(null);

/** methods */

const requestReset = () => {
  error.value = null;
  api
    .post('api/account/reset-password/init', state.email, {
      headers: {
        'content-type': 'text/plain',
      },
    })
    .then(() => {
      success.value = true;
    })
    .catch(() => {
      success.value = null;
      error.value = 'ERROR';
    });
}

</script>
