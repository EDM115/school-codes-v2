<template>
  <NavBar />
  <div class="user-list-container">
    <h1>Liste des Utilisateurs</h1>
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
      :items="users"
      class="elevation-1"
    />
    <div class="link-container">
      <router-link
        to="/activities"
        class="link-button"
      >
        <v-btn
          color="primary"
          text="Voir les activités"
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
import NavBar from "@/components/NavBar.vue"
import userStore from "@/stores/user"
import { ofetch } from "ofetch"
import { ref, onMounted } from "vue"
import { useRouter } from "vue-router"

const errorMessage = ref('')
const issueMessage = ref('')
const router = useRouter()
const store = userStore()
const users = ref([])
const loading = ref(true)

const headers = [
  { title: "Nom", value: "name" },
  { title: "Email", value: "email" },
  { title: "Mot de passe", value: "password" }
]

onMounted(async () => {
  try {
    const response = await ofetch("http://localhost:8080/users")
    await new Promise((resolve) => setTimeout(resolve, 1250))
    if (response) {
      users.value = response
    } else {
      errorMessage.value = "Erreur lors de la récupération des utilisateurs"
    }
  } catch (error) {
    errorMessage.value = "Erreur lors de la récupération des utilisateurs"
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
