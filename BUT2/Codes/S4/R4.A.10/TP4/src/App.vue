<template>
  <v-app>
    <v-main>
      <router-view />
    </v-main>
    <v-fab
      app
      color="primary"
      size="large"
      location="bottom left"
      icon
    >
      <v-icon icon="mdi-filter-variant" />
      <v-speed-dial
        activator="parent"
        location="top center"
        transition="scale-transition"
      >
        <v-btn
          key="1"
          icon
          large
          color="primary"
          @click="openFilterModal('date')"
        >
          <v-icon icon="mdi-sort-clock-descending-outline" />
          <v-tooltip activator="parent" text="Tri par dates"/>
        </v-btn>
        <v-btn
          key="2"
          icon
          large
          color="primary"
          @click="openFilterModal('priority')"
        >
          <v-icon icon="mdi-sort-descending" />
          <v-tooltip activator="parent" text="Tri par priorité"/>
        </v-btn>
        <v-btn
          key="3"
          icon
          large
          color="primary"
          @click="openFilterModal('status')"
        >
          <v-icon icon="mdi-sort-bool-descending-variant" />
          <v-tooltip activator="parent" text="Tri par statut"/>
        </v-btn>
        <v-btn
          key="4"
          icon
          large
          color="primary"
          @click="openFilterModal('reset')"
        >
          <v-icon icon="mdi-filter-variant-remove" />
          <v-tooltip activator="parent" text="Réinitialiser les filtres"/>
        </v-btn>
      </v-speed-dial>
    </v-fab>
    <v-fab
      app
      color="primary"
      size="large"
      location="bottom right"
      icon="mdi-plus"
      @click="openTaskModal"
    />
    <FilterModal ref="filterModal" />
    <TaskModal ref="taskModal" />
  </v-app>
</template>

<script setup>
import FilterModal from "@/components/FilterModal.vue"
import TaskModal from "@/components/TaskModal.vue"
import { ref } from "vue"
import { useTodoStore } from "@/stores/todoStore"

const filterModal = ref(null)
const taskModal = ref(null)
const todoStore = useTodoStore()

const openTaskModal = () => {
  taskModal.value.open()
}

const openFilterModal = (type) => {
  filterModal.value.open(type)
}
</script>

<style>
.v-overlay__scrim {
  background: rgb(var(--v-theme-background));
  opacity: 0.85;
}
</style>
