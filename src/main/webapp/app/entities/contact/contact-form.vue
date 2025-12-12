<template>
  <div class="contact-form">
    <form @submit.prevent="onSubmit">
      <div class="form-group">
        <label for="firstName">Vorname</label>
        <input
          id="firstName"
          v-model="form.firstName"
          type="text"
          class="form-control"
          required
        />
      </div>
      
      <div class="form-group">
        <label for="surName">Nachname</label>
        <input
          id="surName"
          v-model="form.surName"
          type="text"
          class="form-control"
          required
        />
      </div>
      
      <div class="form-group">
        <label for="email">E-Mail</label>
        <input
          id="email"
          v-model="form.email"
          type="email"
          class="form-control"
          required
        />
      </div>
      
      <div class="form-actions">
        <button type="submit" class="btn btn-primary">
          {{ isUpdating ? 'Aktualisieren' : 'Erstellen' }}
        </button>
        <button type="button" class="btn btn-secondary" @click="$emit('cancel')">
          Abbrechen
        </button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  name: 'ContactForm',
  props: {
    contact: {
      type: Object,
      default: () => ({}),
    },
    isUpdating: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      form: {
        firstName: this.contact.firstName || '',
        surName: this.contact.surName || '',
        email: this.contact.email || '',
      },
    };
  },
  methods: {
    onSubmit() {
      this.$emit('save', { ...this.form });
    },
  },
};
</script>

<style scoped>
.contact-form {
  max-width: 500px;
  margin: 0 auto;
}

.form-group {
  margin-bottom: 1rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: bold;
}

.form-control {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.form-actions {
  margin-top: 1rem;
  display: flex;
  gap: 0.5rem;
}

.btn {
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn-primary {
  background-color: #007bff;
  color: white;
}

.btn-secondary {
  background-color: #6c757d;
  color: white;
}
</style> 