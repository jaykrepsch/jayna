<template>
  <div>
    <div class="row">
      <div class="col-md-3">
        <span class="hipster img-fluid rounded"></span>
      </div>
      <div class="col-md-9">
        <h1 v-text="$t('error.title')" />

        <div v-if="errorMessage">
          <div class="alert alert-danger">{{ errorMessage }}</div>
        </div>
        <div v-if="error403" class="alert alert-danger" v-text="$t('error.http.403')" />
        <div v-if="error404" class="alert alert-warning" v-text="$t('error.http.404')" />
      </div>
    </div>
  </div>
</template>

<script>
import loginService from '@/services/login.service';

export default {
  data() { 
    return {
      errorMessage: null,
      error403: false,
      error404: false,
    }
  },

  beforeRouteEnter(to, from, next) {
    next(vm => {
      let errorMessage = null;
      let error403 = false;
      let error404 = false;

      if (to.meta.errorMessage) {
        errorMessage = to.meta.errorMessage;
      }

      if (to.meta.error403) {
        error403 = to.meta.error403;
      }

      if (to.meta.error404) {
        error404 = to.meta.error404;
      }

      vm.init(errorMessage, error403, error404);
    });
  },

  methods: {
    init(errorMessage = null, error403 = false, error404 = false) {
      this.errorMessage = errorMessage;
      this.error403 = error403;
      this.error404 = error404;
  
      if (!this.$store.getters['account/authenticated'] && this.error403) {
        loginService.openLogin();
      }
    }
  }
}
</script>
