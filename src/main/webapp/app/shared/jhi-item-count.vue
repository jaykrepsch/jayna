<template>
  <div class="info jhi-item-count">
    <!-- eslint-disable vue/no-parsing-error -->
    <span v-if="i18nEnabled" v-text="$t('global.item-count', { first, second, total })" />
    <span v-if="!i18nEnabled">
      Showing {{ (page - 1) * itemsPerPage === 0 ? 1 : (page - 1) * itemsPerPage + 1 }} -
      {{ page * itemsPerPage < total ? page * itemsPerPage : total }}
      of {{ total }} items.
    </span>
    <!--eslint-enable-->
  </div>
</template>

<script>

export default {
  props: {
    page: {
      type: number,
      required: true
    },
    total: {
      type: number,
      required: true
    },
    itemsPerPage: {
      type: number,
      required: true
    }
  },
  data() { 
    return {
      i18nEnabled: true
    }
  },

  methods: {
    first() {
      return (this.page - 1) * this.itemsPerPage === 0 ? 1 : (this.page - 1) * this.itemsPerPage + 1;
    },

    second() {
      return this.page * this.itemsPerPage < this.total ? this.page * this.itemsPerPage : this.total;
    }
  }
}
</script>
