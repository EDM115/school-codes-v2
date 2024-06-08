<template>
  <v-container>
    <v-row>
      <v-col v-for="(task, index) in orderedTasks" :key="task.id" cols="12" md="4">
        <v-card>
          <v-tooltip :text="task.description" location="top" close-delay="200">
            <template v-slot:activator="{ props }">
              <div v-bind="props">
                <v-card-title>{{ task.description }}</v-card-title>
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
              </div>
            </template>
          </v-tooltip>
          <v-card-actions>
            <v-btn @click="$emit('edit-task', task.id)" color="primary">Modifier</v-btn>
            <v-btn @click="$emit('delete-task', task.id)" color="error">Supprimer</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { formatDate, getPriorityColor, getPriorityIcon, getStatusColor, getStatusIcon } from "@/assets/helpers"
import { useTodoStore } from "@/stores/todoStore"
import { computed } from "vue"

const todoStore = useTodoStore()

defineEmits(["delete-task", "edit-task"])

const orderedTasks = computed(() => {
  return todoStore.filteredTasks.slice().sort((a, b) => new Date(b.creationDate) - new Date(a.creationDate));
})
</script>
