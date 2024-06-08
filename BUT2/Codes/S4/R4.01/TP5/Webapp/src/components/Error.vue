<template>
  <v-alert
    v-if="!close"
    color="error"
    closable
    variant="elevated"
    @click:close="close = true"
  >
    <v-btn
      color="warning"
      class="mr-4"
      icon="mdi-information-outline"
      variant="flat"
      @click="more = !more"
    />
    {{ message }}
    <div v-if="more">
      <v-divider class="my-4" />
      {{ issue }}
    </div>
  </v-alert>
</template>

<script setup>
import { ref } from "vue"

defineProps({
  issue: {
    type: String,
    default: ""
  },
  message: {
    type: String,
    required: true
  }
})

const more = ref(false)
const close = ref(false)
</script>

<style scoped>
.v-alert {
  margin: 16px 0;
}
</style>
