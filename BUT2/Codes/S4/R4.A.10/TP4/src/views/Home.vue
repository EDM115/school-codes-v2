<template>
  <v-container>
    <v-row class="d-flex justify-center">
      <v-col cols="12" class="text-center">
        <v-card variant="text">
          <v-card-title>ToDo</v-card-title>
          <v-img
            src="@/assets/todo_logo.png"
            rounded="xl"
            height="250"
            width="250"
            max-height="250"
            max-width="250"
            class="mx-auto"
          />
        </v-card>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12">
        <v-card variant="text">
          <v-card-title>Tâches : {{ taskCount }}</v-card-title>
          <p class="px-4">
            Vous pouvez afficher et supprimer vos tâches en cliquant sur les boutons ci-dessous. Utilisez le bouton + pour en ajouter de nouvelles, et le bouton filtre pour filtrer les tâches.
          </p>
          <v-card-actions v-if="taskCount > 0">
            <v-btn color="primary" @click="showUpTasks">{{ displayTasksText }}</v-btn>
            <v-btn color="error" @click="openDeleteModal(null, true)">Supprimer toutes les tâches</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
    <v-divider class="my-4" />
    <v-row>
      <template v-if="isLoading">
        <v-col v-for="n in taskCount" :key="n" cols="12" md="4">
          <v-skeleton-loader type="card" />
        </v-col>
      </template>
    </v-row>
    <TaskList v-if="displayTasks" @delete-task="openDeleteModal" @edit-task="openEditModal" />
    <DeleteModal ref="deleteModal" @delete-all="deleteAllTasks" @delete-task="deleteTask" />
    <TaskModal ref="taskModal" />
  </v-container>
</template>

<script setup>
import DeleteModal from "@/components/DeleteModal.vue"
import TaskList from "@/components/TaskList.vue"
import TaskModal from "@/components/TaskModal.vue"
import { useTodoStore } from "@/stores/todoStore"
import { computed, ref } from "vue"

const deleteModal = ref(null)
const displayTasks = ref(false)
const displayTasksText = computed(() => displayTasks.value ? "Masquer les tâches" : "Afficher les tâches")
const taskModal = ref(null)
const isLoading = ref(false)

const todoStore = useTodoStore()
const taskCount = computed(() => todoStore.taskCount)

const openDeleteModal = (id = null, all = false) => {
  deleteModal.value.open(id, all)
}

const openEditModal = (id) => {
  taskModal.value.open(todoStore.getTask(id))
}

const deleteAllTasks = () => {
  todoStore.deleteTasks()
}

const deleteTask = (id) => {
  todoStore.removeTask(id)
}

const showUpTasks = () => {
  if (displayTasks.value) {
    isLoading.value = false
    displayTasks.value = false
    return
  }
  isLoading.value = true
  setTimeout(() => {
    isLoading.value = false
    displayTasks.value = true
  }, 800)
}
</script>
