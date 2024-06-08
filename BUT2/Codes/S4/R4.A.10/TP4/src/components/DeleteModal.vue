<template>
  <v-dialog v-model="isOpen" max-width="600px" transition="dialog-bottom-transition">
    <v-card>
      <v-card-title>Suppression de tâche{{ deleteAll ? "s" : "" }}</v-card-title>
      <v-card-text>
        Êtes-vous sûr de vouloir supprimer {{ deleteAll ? "toutes les tâches" : "cette tâche" }} ?
        <v-card v-if="!deleteAll" class="mt-4">
          <p class="font-weight-medium my-2 mx-4">
            {{ task.description }}
          </p>
          <v-card-subtitle>
            {{ formatDate(task.startDate) }} - {{ formatDate(task.endDate) }}
          </v-card-subtitle>
          <v-card-text>
            Statut :
            <v-icon :color="getStatusColor(task.status)">{{ getStatusIcon(task.status) }}</v-icon>
            {{ task.status }}
            <br />
            Priorité :
            <v-icon :color="getPriorityColor(task.priority)">{{ getPriorityIcon(task.priority) }}</v-icon>
            {{ task.priority }}
          </v-card-text>
        </v-card>
      </v-card-text>
      <v-card-actions>
        <v-btn color="secondary" text @click="close">Non</v-btn>
        <v-btn color="primary" text @click="confirmDelete">Oui</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { formatDate, getPriorityColor, getPriorityIcon, getStatusColor, getStatusIcon } from "@/assets/helpers"
import { useTodoStore } from "@/stores/todoStore"
import { ref } from "vue"

const todoStore = useTodoStore()

const isOpen = ref(false)
const task = ref(null)
const taskId = ref(null)
const deleteAll = ref(false)

const emit = defineEmits(["delete-task", "delete-all"])

const open = (id = null, all = false) => {
  taskId.value = id
  deleteAll.value = all
  isOpen.value = true
  if (!deleteAll.value) {
    task.value = todoStore.getTask(taskId.value)
  }
}

const close = () => {
  isOpen.value = false
}

const confirmDelete = () => {
  if (deleteAll.value) {
    emit("delete-all")
  } else {
    emit("delete-task", taskId.value)
  }
  close()
}

defineExpose({ open })
</script>

<style scoped>
.v-overlay {
  backdrop-filter: blur(5px);
}
</style>
