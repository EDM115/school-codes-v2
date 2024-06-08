<template>
  <v-form @submit.prevent="submitTask">
    <v-textarea
      v-model="description"
      label="Description"
      auto-grow
      no-resize
      rows="1"
      required
      :error-messages="descriptionErrors"
    ></v-textarea>
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
          v-model="startDate"
          label="Date de début"
          readonly
          v-bind="props"
          required
          :error-messages="date1Errors"
        ></v-text-field>
      </template>
      <v-date-picker v-model="startDate" @input="startDateMenu = false"></v-date-picker>
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
          v-model="endDate"
          label="Date de fin"
          readonly
          v-bind="props"
          required
          :error-messages="date2Errors"
        ></v-text-field>
      </template>
      <v-date-picker v-model="endDate" @input="endDateMenu = false"></v-date-picker>
    </v-menu>
    <v-radio-group v-model="status" :error-messages="statusErrors" row>
      <v-radio label="À faire" value="À faire"><v-icon color="indigo" icon="mdi-checkbox-blank-circle-outline" /></v-radio>
      <v-radio label="En cours" value="En cours"><v-icon color="yellow" icon="mdi-progress-check" /></v-radio>
      <v-radio label="Terminée" value="Terminée"><v-icon color="green" icon="mdi-checkbox-marked-circle-outline" /></v-radio>
    </v-radio-group>
    <v-radio-group v-model="priority" :error-messages="priorityErrors" row>
      <v-radio label="Haute" value="Haute"><v-icon color="red" icon="mdi-arrow-up-bold-circle-outline" /></v-radio>
      <v-radio label="Moyenne" value="Moyenne"><v-icon color="orange" icon="mdi-arrow-up-drop-circle-outline" /></v-radio>
      <v-radio label="Basse" value="Basse"><v-icon color="cyan" icon="mdi-arrow-down-drop-circle-outline" /></v-radio>
    </v-radio-group>
    <v-btn type="submit" color="primary" :disabled="canSubmit">{{ isEditMode ? "Modifier" : "Ajouter" }} la tâche</v-btn>
  </v-form>
</template>

<script setup>
import { useTodoStore } from "@/stores/todoStore"
import { ref, computed, watch } from "vue"

const todoStore = useTodoStore()

const description = ref('')
const startDate = ref(null)
const endDate = ref(null)
const status = ref('')
const priority = ref('')
const startDateMenu = ref(false)
const endDateMenu = ref(false)

const emit = defineEmits(["close-modal"])
const props = defineProps(["task"])

const isEditMode = computed(() => !!props.task)

watch(props.task, (newTask) => {
  if (newTask) {
    description.value = newTask.description
    startDate.value = newTask.startDate
    endDate.value = newTask.endDate
    status.value = newTask.status
    priority.value = newTask.priority
  }
}, { immediate: true })

const descriptionErrors = computed(() => !description.value ? ["Description requise"] : [])
const date1Errors = computed(() => !startDate.value ? ["Date de début requise"] : [])
const date2Errors = computed(() => (!endDate.value || (startDate.value && endDate.value && new Date(startDate.value) > new Date(endDate.value))) ? ["Date de fin requise et la date de début doit être antérieure à la date de fin"] : [])
const statusErrors = computed(() => !status.value ? ["Statut requis"] : [])
const priorityErrors = computed(() => !priority.value ? ["Priorité requise"] : [])

const canSubmit = computed(() => {
  return (descriptionErrors.value.length || date1Errors.value.length || date2Errors.value.length || statusErrors.value.length || priorityErrors.value.length) > 0
})

const submitTask = () => {
  if (!description.value || !startDate.value || !endDate.value || !status.value || !priority.value) {
    return
  }

  const task = {
    description: description.value,
    startDate: new Date(startDate.value).toISOString(),
    endDate: new Date(endDate.value).toISOString(),
    status: status.value,
    priority: priority.value,
  }

  if (isEditMode.value) {
    todoStore.updateTask(props.task.id, task)
  } else {
    todoStore.addTask(task)
  }

  closeTaskModal()
  description.value = ''
  startDate.value = null
  endDate.value = null
  status.value = ''
  priority.value = ''
}

const closeTaskModal = () => {
  emit("close-modal")
}
</script>

<style>
.v-label {
  padding-left: 10px !important;
}

.v-messages {
  margin-bottom: 10px !important;
}
</style>
