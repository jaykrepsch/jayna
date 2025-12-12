<template>
  <EntityCreateLayout
    :message-key="$t('global.form.createFormTitle', { entity: $t('jaynaApp.baseContact.base-title') })"
    @cancel="onCancel"
    @save="onSave"
  >
    <FormPanel :title="$t('jaynaApp.baseContact.general.title')" bg-color="bg-white">
      <template #content>
        <div class="md:grid md:grid-cols-3 md:gap-6">
          <s-input
            name="firstName"
            type="text"
            v-model="contact.firstName"
            :label="$t('jaynaApp.baseContact.personalData.fields.firstName')"
          />
          <s-input
            name="surName"
            type="text"
            v-model="contact.surName"
            :label="$t('jaynaApp.baseContact.personalData.fields.lastName')"
          />
        </div>
      </template>
    </FormPanel>

    <Divider />

    <FormPanel :title="$t('jaynaApp.baseContact.address.title')" bg-color="bg-white">
      <template #content>
        <div class="md:grid md:grid-cols-3 md:gap-6">
          <s-input
            name="street"
            type="text"
            v-model="contact.street"
            :label="$t('jaynaApp.baseContact.address.fields.street')"
          />
          <s-input
            name="streetNumber"
            type="text"
            v-model="contact.streetNumber"
            :label="$t('jaynaApp.baseContact.address.fields.houseNumber')"
          />
          <s-input
            name="postalCode"
            type="text"
            v-model="contact.postalCode"
            :label="$t('jaynaApp.baseContact.address.fields.postalCode')"
          />
          <s-input
            name="city"
            type="text"
            v-model="contact.city"
            :label="$t('jaynaApp.baseContact.address.fields.city')"
          />
        </div>
      </template>
    </FormPanel>

    <Divider />

    <FormPanel :title="$t('jaynaApp.baseContact.contactData.title')" bg-color="bg-white">
      <template #content>
        <div class="md:grid md:grid-cols-3 md:gap-6">
          <s-input
            name="phoneCountryCode"
            type="number"
            v-model.number="contact.phoneCountryCode"
            :label="$t('jaynaApp.baseContact.contactData.fields.phoneCountryCode')"
          />
          <s-input
            name="phonePrefix"
            type="number"
            v-model.number="contact.phonePrefix"
            :label="$t('jaynaApp.baseContact.contactData.fields.phonePrefix')"
          />
          <s-input
            name="phoneNumber"
            type="number"
            v-model.number="contact.phoneNumber"
            :label="$t('jaynaApp.baseContact.contactData.fields.phoneNumber')"
          />
        </div>
      </template>
    </FormPanel>
  </EntityCreateLayout>
  
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

import SInput from '@/components/forms/input.vue';
import FormPanel from '@/components/formpanel.vue';
import Divider from '@/components/divider.vue';
import EntityCreateLayout from '@/core/layouts/entity-create-layout.vue';

import ContactService from '@/services/contact.service.js';
import AlertService from '@/services/alert.service';

const router = useRouter();

const contact = ref({
  firstName: '',
  surName: '',
  street: '',
  streetNumber: '',
  postalCode: '',
  city: '',
  phoneCountryCode: null,
  phonePrefix: null,
  phoneNumber: null,
  entityState: 'ACTIVE'
});

const onCancel = () => {
  router.push({ name: 'ContactList' });
};

const onSave = () => {
  const payload = {
    ...contact.value
  };
  
  ContactService
    .create(payload)
    .then(() => {
      router.push({ name: 'ContactList' });
    })
    .catch((err) => {
      AlertService.showHttpError(this, err?.response);
    });
};
</script>

<style scoped>
.contact-create {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}
</style>