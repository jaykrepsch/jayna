<template>
  <div>
    <EntitySelectLayout
      :message-key="getTitle()"
      :back-button-path="getBackButtonPath()"
      @cancel="$router.go(-1)"
    >
      <div class="row">
        <div class="col-md-12">
          <div class="card">
            <div class="card-header">
              <h4>{{ $t('jaynaApp.contact.select.title') }}</h4>
            </div>
            <div class="card-body">
              <div class="row">
                <div class="col-md-6">
                  <div class="form-group">
                    <label>{{ $t('jaynaApp.contact.select.search') }}</label>
                    <input
                      v-model="searchTerm"
                      type="text"
                      class="form-control"
                      :placeholder="$t('jaynaApp.contact.select.searchPlaceholder')"
                      @input="filterContacts"
                    />
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="form-group">
                    <label>{{ $t('jaynaApp.contact.select.filter') }}</label>
                    <select v-model="selectedFilter" class="form-control" @change="filterContacts">
                      <option value="">{{ $t('jaynaApp.contact.select.allContacts') }}</option>
                      <option value="active">{{ $t('jaynaApp.contact.select.activeContacts') }}</option>
                      <option value="inactive">{{ $t('jaynaApp.contact.select.inactiveContacts') }}</option>
                    </select>
                  </div>
                </div>
              </div>
              
              <div class="table-responsive mt-3">
                <table class="table table-striped">
                  <thead>
                    <tr>
                      <th>{{ $t('jaynaApp.contact.select.name') }}</th>
                      <th>{{ $t('jaynaApp.contact.select.email') }}</th>
                      <th>{{ $t('jaynaApp.contact.select.phone') }}</th>
                      <th>{{ $t('jaynaApp.contact.select.status') }}</th>
                      <th>{{ $t('jaynaApp.contact.select.actions') }}</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="contact in filteredContacts" :key="contact.id">
                      <td>{{ contact.label || contact.name }}</td>
                      <td>{{ contact.email }}</td>
                      <td>{{ contact.phone }}</td>
                      <td>
                        <span :class="getStatusClass(contact.entityState)">
                          {{ getStatusText(contact.entityState) }}
                        </span>
                      </td>
                      <td>
                        <button
                          class="btn btn-primary btn-sm"
                          @click="selectContact(contact)"
                        >
                          {{ $t('jaynaApp.contact.select.select') }}
                        </button>
                        <button
                          class="btn btn-info btn-sm ms-1"
                          @click="viewContact(contact)"
                        >
                          {{ $t('jaynaApp.contact.select.view') }}
                        </button>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
              
              <div v-if="filteredContacts.length === 0" class="text-center mt-3">
                <p>{{ $t('jaynaApp.contact.select.noContacts') }}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </EntitySelectLayout>
  </div>
</template>

<script setup>

import { ref, computed, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import AlertService from '@/services/alert.service';
import ContactService from '@/services/contact.service';

import EntitySelectLayout from '@/core/layouts/entity-select-layout.vue';
import { useI18n } from 'vue-i18n';

/** data */
const { t } = useI18n();
const route = useRoute();
const router = useRouter();

const contacts = ref([]);
const filteredContacts = ref([]);
const searchTerm = ref('');
const selectedFilter = ref('');
const loading = ref(false);

const loadContacts = async () => {
  try {
    loading.value = true;
    const response = await ContactService.retrieveAll();
    contacts.value = response || [];
    filteredContacts.value = [...contacts.value];
  } catch (error) {
    console.error('Fehler beim Laden der Kontakte:', error);
    AlertService.showError('Fehler beim Laden der Kontakte');
  } finally {
    loading.value = false;
  }
};

const filterContacts = () => {
  let filtered = [...contacts.value];
  
  // Filter nach Suchbegriff
  if (searchTerm.value) {
    const term = searchTerm.value.toLowerCase();
    filtered = filtered.filter(contact => 
      (contact.label && contact.label.toLowerCase().includes(term)) ||
      (contact.name && contact.name.toLowerCase().includes(term)) ||
      (contact.email && contact.email.toLowerCase().includes(term)) ||
      (contact.phone && contact.phone.toLowerCase().includes(term))
    );
  }
  
  // Filter nach Status
  if (selectedFilter.value) {
    filtered = filtered.filter(contact => {
      if (selectedFilter.value === 'active') {
        return contact.entityState === 'ACTIVE';
      } else if (selectedFilter.value === 'inactive') {
        return contact.entityState === 'INACTIVE';
      }
      return true;
    });
  }
  
  filteredContacts.value = filtered;
};

const selectContact = (contact) => {
  // Emit event oder navigiere zurück mit ausgewähltem Kontakt
  if (route.query.callback) {
    router.push({
      path: route.query.callback,
      query: { 
        selectedContactId: contact.id,
        selectedContactName: contact.label || contact.name
      }
    });
  } else {
    // Fallback: Navigiere zur Kontakt-Detailansicht
    router.push({
      name: 'ContactView',
      params: { entityid: contact.id }
    });
  }
};

const viewContact = (contact) => {
  router.push({
    name: 'ContactView',
    params: { entityid: contact.id }
  });
};

const getStatusClass = (status) => {
  switch (status) {
    case 'ACTIVE':
      return 'badge bg-success';
    case 'INACTIVE':
      return 'badge bg-secondary';
    default:
      return 'badge bg-warning';
  }
};

const getStatusText = (status) => {
  switch (status) {
    case 'ACTIVE':
      return t('jaynaApp.contact.status.active');
    case 'INACTIVE':
      return t('jaynaApp.contact.status.inactive');
    default:
      return t('jaynaApp.contact.status.unknown');
  }
};

const getTitle = () => {
  return 'jaynaApp.contact.select.title';
};

const getBackButtonPath = () => {
  return route.query.backPath || '/contact';
};

onMounted(() => {
  loadContacts();
});

</script>

<style scoped>
.badge {
  font-size: 0.8em;
}
</style>
