<template>
  <v-dialog v-model="isOpen" max-width="600px" transition="dialog-bottom-transition">
    <v-card class="pa-4">
      <v-card-title>{{ isEditMode ? "Modifier" : "Ajouter" }} une tâche</v-card-title>
      <v-btn
        icon="mdi-close"
        color="error"
        class="position-absolute top-4 right-0"
        @click="close"
      />
      <v-card-text>
        <TaskForm :task="task" @close-modal="close" />
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script setup>
import TaskForm from "@/components/TaskForm.vue"
import { ref } from "vue"

const isOpen = ref(false)
const task = ref(null)
const isEditMode = ref(false)

const open = (taskToEdit = null) => {
  if (taskToEdit) {
    task.value = { ...taskToEdit }
    isEditMode.value = true
  } else {
    task.value = null
    isEditMode.value = false
  }
  isOpen.value = true
}

const close = () => {
  isOpen.value = false
}

defineExpose({ open, close })
</script>

<style scoped>
.v-overlay {
  backdrop-filter: blur(5px);
}
</style>
