<template>
  <div class="flex flex-col justify-center sm:px-6 lg:px-8">
    <div class="my-8 sm:mx-auto sm:w-full sm:max-w-md">
      <div class="bg-white py-8 px-4 shadow sm:rounded-lg sm:px-10">
        <h1
          class="text-center pb-4"
          v-text="$t('activate.title')"
        />
        <div v-if="success">
          <Alert type="success" headerKey="activate.messages.success.header"
            messageKey="activate.messages.success.message" />
          <Button :text="$t('button.login')" @click.prevent="openLogin()" />
        </div>

        <div v-if="error">
          <Alert type="danger" headerKey="activate.messages.error.header"
            messageKey="activate.messages.error.message" />

          <Button :text="$t('button.register')" @click.prevent="$router.push({name: 'Register'})" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>

import { onMounted, ref, watch } from 'vue';
import { useRoute } from 'vue-router';

import loginService from '@/services/login.service';
import activateService from '@/services/activate.service';

import Alert from '@/components/alert.vue';
import Button from '@/components/buttons/button.vue';

const route = useRoute();

const success = ref(false);
const error = ref(false);



watch(
  () => route.query.key,
  () => (key) => init(key)
);

onMounted(() => { 
  init(route.query.key);
});

const init = (key) => {
  activateService
    .activateAccount(key)
    .then(
      () => {
        success.value = true;
        error.value = false;
      },
      () => {
        error.value = true;
        success.value = false;
      }
    );
};

const openLogin = () => {
  loginService.openLogin();
}
</script>
