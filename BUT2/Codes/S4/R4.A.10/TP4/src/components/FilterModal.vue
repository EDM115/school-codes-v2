<template>
  <v-dialog v-model="isOpen" max-width="600px" transition="dialog-bottom-transition">
    <v-card v-if="filterType === 'reset'">
      <v-card-title>Réinitialiser les filtres</v-card-title>
      <v-card-text>
        Êtes-vous sûr de vouloir réinitialiser les filtres ?
      </v-card-text>
      <v-card-actions>
        <v-btn color="secondary" @click="close">Annuler</v-btn>
        <v-btn color="primary" @click="resetFilters">Réinitialiser</v-btn>
      </v-card-actions>
    </v-card>
    <v-card v-else-if="filterType === 'date'">
      <v-card-title>Filtrer par dates</v-card-title>
      <v-card-text>
        <v-menu
          v-model="startDateMenu"
          :close-on-content-click="false"
          transition="scale-transition"
          offset-y
          full-width
          max-width="290px"
          min-width="290px"
        >
          <template #activator="{ props }">
            <v-text-field
              v-model="selectedStartDate"
              label="Date de début"
              readonly
              clearable
              v-bind="props"
              required
            ></v-text-field>
          </template>
          <v-date-picker v-model="selectedStartDate" @input="startDateMenu = false"></v-date-picker>
        </v-menu>
        <v-menu
          v-model="endDateMenu"
          :close-on-content-click="false"
          transition="scale-transition"
          offset-y
          full-width
          max-width="290px"
          min-width="290px"
        >
          <template #activator="{ props }">
            <v-text-field
              v-model="selectedEndDate"
              label="Date de fin"
              readonly
              clearable
              v-bind="props"
              required
            ></v-text-field>
          </template>
          <v-date-picker v-model="selectedEndDate" @input="endDateMenu = false"></v-date-picker>
        </v-menu>
      </v-card-text>
      <v-card-actions>
        <v-btn color="secondary" @click="close">Annuler</v-btn>
        <v-btn color="primary" @click="applyDateFilter">Appliquer</v-btn>
      </v-card-actions>
    </v-card>

    <v-card v-else>
      <v-card-title>Filtrer par {{ filterType === "priority" ? "priorité" : "statut" }}</v-card-title>
      <v-card-text>
        <v-select
          v-if="filterType === 'priority'"
          v-model="selectedPriorities"
          :items="priorityItems"
          multiple
          chips
          clearable
          label="Sélectionnez les priorités"
        ></v-select>
        <v-select
          v-if="filterType === 'status'"
          v-model="selectedStatuses"
          :items="statusItems"
          multiple
          chips
          clearable
          label="Sélectionnez les statuts"
        ></v-select>
      </v-card-text>
      <v-card-actions>
        <v-btn color="secondary" @click="close">Annuler</v-btn>
        <v-btn color="primary" @click="applyFilter">Appliquer</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { ref } from "vue"
import { useTodoStore } from "@/stores/todoStore"

const isOpen = ref(false)
const filterType = ref('')
const selectedStartDate = ref(null)
const selectedEndDate = ref(null)
const startDateMenu = ref(false)
const endDateMenu = ref(false)
const selectedStatuses = ref([])
const selectedPriorities = ref([])
const todoStore = useTodoStore()

const priorityItems = ["Haute", "Moyenne", "Basse"]
const statusItems = ["À faire", "En cours", "Terminée"]

const open = (type) => {
  filterType.value = type
  isOpen.value = true
}

const close = () => {
  isOpen.value = false
}

const applyDateFilter = () => {
  if (selectedStartDate.value) {
    selectedStartDate.value = new Date(selectedStartDate.value).toISOString()
  }
  if (selectedEndDate.value) {
    selectedEndDate.value = new Date(selectedEndDate.value).toISOString()
  }
  todoStore.filterTasksByDate(selectedStartDate.value, selectedEndDate.value)
  close()
}

const applyFilter = () => {
  if (filterType.value === 'priority') {
    todoStore.filterTasksByPriorities(selectedPriorities.value)
  } else if (filterType.value === 'status') {
    todoStore.filterTasksByStatuses(selectedStatuses.value)
  }
  close()
}

const resetFilters = () => {
  selectedStartDate.value = null
  selectedEndDate.value = null
  selectedStatuses.value = []
  selectedPriorities.value = []
  todoStore.resetFilters()
  close()
}

defineExpose({ open })
</script>

<style scoped>
.v-overlay {
  backdrop-filter: blur(5px);
}
</style>
