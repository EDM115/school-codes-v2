<template>
  <v-container>
    <v-img
      :draggable="false"
      class="mb-4"
      rounded="xl"
      alt="Cluedo Logo"
      src="@/assets/logo.png"
    />

    <v-divider class="my-4" />

    <v-row
      align="center"
      align-content="center"
      justify="center"
      class="mb-15"
    >
      <v-col
        cols="12"
        align="center"
      >
        <h2 class="text-h5 mb-4">
          Bienvenue sur Cluedo - EDM115
        </h2>
        <v-row>
          <v-col>
            <v-text-field
              v-model="playerName"
              clearable
              label="Votre nom"
            />
          </v-col>
          <v-col>
            <v-text-field
              v-model="gameId"
              clearable
              label="Rejoindre une partie avec ID"
            />
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <v-btn @click="createGame">
              Créer une Partie
            </v-btn>
          </v-col>
          <v-col>
            <v-btn @click="joinGame">
              Rejoindre la Partie
            </v-btn>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { uid } from "uid/secure"
import { ref } from "vue"
import { useRouter } from "vue-router"
import { usePlayerStore } from "@/stores/playerStore"
import socket from "@/socket"

const playerName = ref("")
const gameId = ref("")
const router = useRouter()
const playerStore = usePlayerStore()

playerStore.resetPlayerInfo()

const createGame = () => {
  if (!playerName.value.trim()) {
    return alert("Veuillez entrer un nom.")
  }

  const playerId = uid(16)

  socket.emit("createGame", { playerName: playerName.value, playerId }, (response) => {
    playerStore.setPlayerInfo({
      gameId: response.gameId,
      playerId: playerId,
      playerName: playerName.value,
      isHost: true
    })
    navigator.clipboard.writeText(response.gameId)
    router.push({ name: "WaitingRoom", params: { gameId: response.gameId, playerName: playerName.value } })
  })
}

const joinGame = () => {
  if (!gameId.value.trim() || !playerName.value.trim()) {
    return alert("Veuillez entrer un nom et un ID de partie.")
  }

  const playerId = uid(16)

  socket.emit("joinGame", { gameId: gameId.value, playerName: playerName.value, playerId }, (response) => {
    if (response.error) {
      return alert(response.error)
    }
  })

  // Écouter les erreurs venant du backend
  socket.on("error", (errorMessage) => {
    alert(errorMessage)

    if (errorMessage === "La partie n'existe pas.") {
      gameId.value = ""

      return router.push({ name: "Home" })
    }
  })

  socket.on("role", (role) => {
    playerStore.setPlayerInfo({
      gameId: gameId.value,
      playerId: playerId,
      playerName: playerName.value,
      isHost: role === "host"
    })
    router.push({ name: "WaitingRoom", params: { gameId: gameId.value, playerName: playerName.value } })
  })
}
</script>
