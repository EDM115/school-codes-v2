<template>
  <NavBar />
  <v-container
    fluid
    class="d-flex flex-column align-center justify-center"
    style="height: 100vh"
  >
    <v-card
      class="pa-4"
      max-width="400"
    >
      <h2 class="text-center">Inscription</h2>
      <Error
        v-if="errorMessage"
        :issue="issueMessage"
        :message="errorMessage"
      />
      <v-form @submit.prevent="register">
        <v-text-field
          v-model="name"
          label="Nom"
          prepend-inner-icon="mdi-account"
        />
        <v-text-field
          v-model="email"
          label="Email"
          prepend-inner-icon="mdi-at"
        />
        <v-text-field
          v-model="password"
          type="password"
          label="Mot de passe"
          prepend-inner-icon="mdi-key-variant"
        />
        <v-row class="d-flex justify-center mt-4">
          <v-col class="d-flex justify-center">
            <v-btn
              type="submit"
              color="primary"
              class="mx-2"
              text="S'inscrire"
            />
            <v-btn
              type="button"
              color="secondary"
              class="mx-2"
              text="Se connecter"
              @click="$router.push('/login')"
            />
          </v-col>
        </v-row>
      </v-form>
    </v-card>
  </v-container>
</template>

<script setup>
import Error from "@/components/Error.vue"
import NavBar from "@/components/NavBar.vue"
import userStore from "@/stores/user"
import { ofetch } from "ofetch"
import { ref } from "vue"
import { useRouter } from "vue-router"

const email = ref("")
const errorMessage = ref('')
const issueMessage = ref('')
const name = ref("")
const password = ref("")
const router = useRouter()
const store = userStore()

const register = async () => {
  try {
    const response = await ofetch("http://localhost:8080/users/register", {
      method: "POST",
      body: {
        name: name.value,
        email: email.value,
        password: password.value
      }
    })
    console.log(response)
    if (response) {
      store.login(response)
      router.push("/activities")
    } else {
      errorMessage.value = "Erreur lors de l'inscription"
    }
  } catch (error) {
    errorMessage.value = "Erreur lors de l'inscription"
    issueMessage.value = error
  }
}
</script>

<style scoped>
.v-text-field, .v-btn {
  margin-bottom: 8px;
  margin-top: 8px;
}
</style>
