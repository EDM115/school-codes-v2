<template>
  <NavBar />
  <div class="activity-list-container">
    <h1>Liste des Activités</h1>
    <Error
      v-if="errorMessage"
      :issue="issueMessage"
      :message="errorMessage"
    />
    <v-skeleton-loader
      v-if="loading"
      :loading="loading"
      type="table-tbody"
      class="mx-auto"
    />
    <v-data-table
      v-else
      :headers="headers"
      :items="activities"
      item-value="description"
      class="elevation-1"
    >
      <template v-slot:item.data="{ item }">
        <v-list>
          <v-list-item v-for="(dataPoint, dpIndex) in item.data" :key="dpIndex">
            {{ dataPoint.time }} - Lat : {{ dataPoint.latitude }}, Long : {{ dataPoint.longitude }}, Alt : {{ dataPoint.altitude }}, Fréquence : {{ dataPoint.cardioFrequency }}
          </v-list-item>
        </v-list>
      </template>
    </v-data-table>
    <div class="link-container">
      <router-link
        to="/users"
        class="link-button"
      >
        <v-btn
          color="primary"
          text="Voir les utilisateurs"
        />
      </router-link>
      <v-btn
        color="secondary"
        class="link-button"
        text="Déconnexion"
        @click="logout"
      />
    </div>
  </div>
</template>

<script setup>
import Error from "@/components/Error.vue"
import NavBar from "@/components/NavBar.vue"
import userStore from "@/stores/user"
import { ofetch } from "ofetch"
import { ref, onMounted } from "vue"
import { useRouter } from "vue-router"

const activities = ref([])
const errorMessage = ref('')
const issueMessage = ref('')
const loading = ref(true)
const router = useRouter()
const store = userStore()

const headers = [
  { title: "Description", value: "description" },
  { title: "Date", value: "date" },
  { title: "Distance", value: "distance" },
  { title: "Fréquence Min", value: "freqMin" },
  { title: "Fréquence Max", value: "freqMax" },
  /* {
    title: "Données supplémentaires",
    align: "center",
    children: [
      { title: "Time", value: "data.time" },
      { title: "Latitude", value: "data.latitude" },
      { title: "Longitude", value: "data.longitude" },
      { title: "Altitude", value: "data.altitude" },
      { title: "Cardio Frequency", value: "data.cardioFrequency" }
    ]
  } */
  { title: "Données supplémentaires", value: "data", sortable: false }
]

onMounted(async () => {
  try {
    const response = await ofetch("http://localhost:8080/activities")
    await new Promise((resolve) => setTimeout(resolve, 1250))
    if (response) {
      activities.value = response
    } else {
      errorMessage.value = "Échec de la récupération des activités"
    }
  } catch (error) {
    errorMessage.value = "Échec de la récupération des activités"
    issueMessage.value = error
  } finally {
    loading.value = false
  }
})

const logout = () => {
  store.logout()
  router.push("/")
}
</script>
