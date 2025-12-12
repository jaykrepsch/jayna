<template>
  <font-awesome-icon :icon="currentOrder === fieldName ? (reverse ? 'sort-down' : 'sort-up') : 'sort'"> </font-awesome-icon>
</template>

<script>

export default {
  props: {
    currentOrder: {
      type: String,
      required: true
    },
    fieldName: {
      type: String,
      required: true
    },
    revers: {
      type: Boolean,
      required: true
    }
  }
}

</script>
