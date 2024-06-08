<template>
  <v-app-bar
    class="px-8"
    color="primary"
    dense
    floating
    rounded="b-xl"
    scroll-behavior="elevate"
  >
    <template #prepend>
      <router-link
        to="/"
        class="flex items-center link-button"
      >
        <v-img
          src="@/assets/sporttrack.jpeg"
          min-height="40"
          max-height="40"
          min-width="40"
          max-width="40"
          rounded="pill"
        />
      </router-link>
    </template>
    <v-app-bar-title>
      <router-link
        to="/users"
        class="link-button"
      >
        <v-btn
          class="mx-2"
          color="background"
          text="Utilisateurs"
        />
      </router-link>
      <router-link
        to="/activities"
        class="link-button"
      >
        <v-btn
          class="mx-2"
          color="background"
          text="Activités"
        />
      </router-link>
    </v-app-bar-title>
    <v-spacer />
    <v-btn
      :prepend-icon="accountIcon"
      :text="accountText"
      @click="handleConnect"
      color="background"
    />
  </v-app-bar>
</template>

<script setup>
import userStore from "@/stores/user"
import { computed, ref, watch } from "vue"
import { useRouter } from "vue-router"

const store = userStore()
const router = useRouter()

const accountIcon = ref("mdi-login")
const accountText = computed(() => (store.isLogged ? "Déconnexion" : "Connexion"))
const connected = computed(() => store.isLogged)

watch(connected, (value) => {
  accountIcon.value = value ? "mdi-logout" : "mdi-login"
})

function handleConnect() {
  if (connected.value) {
    store.logout()
  } else {
    router.push("/login")
  }
}
</script>
