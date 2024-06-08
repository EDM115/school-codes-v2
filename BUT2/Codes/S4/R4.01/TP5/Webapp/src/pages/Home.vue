<template>
  <NavBar />
  <v-container
    fluid
    class="d-flex flex-column align-center justify-center"
    style="height: 100vh"
  >
    <v-row
      align="center"
      justify="center"
      class="text-center"
    >
      <v-col>
        <v-card
          class="mt-4"
          max-width="600"
          rounded="lg"
          variant="elevated"
        >
          <v-img
            src="@/assets/sporttrack.jpeg"
            alt="SportTrack"
            class="logo"
          />
          <v-card-title class="text-h4">SportTrack</v-card-title>
          <v-card-text>
            Bienvenue sur SportTrack, l'appli incroyable qui va vous permettre de suivre vos activités sportives !
          </v-card-text>
        </v-card>
        <v-row
          align="center"
          justify="center"
          class="mt-4"
        >
          <v-col
            v-if="connected"
            class="d-flex justify-center"
          >
            <v-btn
              color="primary"
              class="mx-2"
              text="Utilisateurs"
              @click="$router.push('/users')"
            />
            <v-btn
              color="primary"
              class="mx-2"
              text="Activités"
              @click="$router.push('/activities')"
            />
            <v-btn
              color="secondary"
              class="mx-2"
              text="Déconnexion"
              @click="logout"
            />
          </v-col>
          <v-col
            v-else
            class="d-flex justify-center"
          >
            <v-btn
              color="primary"
              class="mx-2"
              text="Connexion"
              @click="$router.push('/login')"
            />
            <v-btn
              color="primary"
              class="mx-2"
              text="Créer un compte"
              @click="$router.push('/register')"
            />
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import NavBar from "@/components/NavBar.vue"
import userStore from "@/stores/user"
import { computed } from "vue"

const store = userStore()
const connected = computed(() => store.isLogged)

const logout = () => {
  store.logout()
}
</script>
