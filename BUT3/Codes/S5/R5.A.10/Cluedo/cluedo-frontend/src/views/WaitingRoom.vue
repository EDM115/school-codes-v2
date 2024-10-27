<template>
  <v-container>
    <h2>En attente du démarrage de la partie</h2>
    <div class="d-flex align-center">
      <p class="mr-2 text-h5">
        <strong>ID de la Partie :</strong>
      </p>
      <pre class="mr-2 text-h5">{{ playerStore.getGameId }}</pre>
      <v-tooltip
        location="bottom"
        text="Copier l'ID de partie"
      >
        <template #activator="{ props }">
          <v-btn
            v-bind="props"
            flat
            :icon="mdiContentCopy"
            size="small"
            @click="copyToClipboard(playerStore.getGameId)"
          />
        </template>
      </v-tooltip>
    </div>
    <v-list class="mt-4">
      <v-list-item
        v-for="player in players"
        :key="player.socketId"
      >
        {{ player.playerName }} - {{ player.playerId === hostPlayerId ? 'Host' : 'Player' }}{{ player.playerId === playerStore.getPlayerId ? ' (Vous)' : '' }}
      </v-list-item>
    </v-list>
    <v-divider class="my-4" />

    <v-btn
      v-if="playerStore.isPlayerHost"
      color="primary"
      class="mr-2"
      @click="startGame"
    >
      Démarrer la Partie
    </v-btn>
    <v-btn
      v-if="playerStore.isPlayerHost"
      color="error"
      @click="cancelGame"
    >
      Annuler la Partie
    </v-btn>

    <v-btn
      v-if="!playerStore.isPlayerHost"
      color="error"
      @click="leaveGame"
    >
      Quitter la Partie
    </v-btn>

    <v-snackbar
      v-model="snackbar"
      color="success"
      class="mb-8 text-center"
      :close-delay="3000"
    >
      {{ snackbarMessage }}
    </v-snackbar>
  </v-container>
</template>

<script setup>
import mdiContentCopy from "~icons/mdi/contentCopy"
import { ofetch } from "ofetch"
import { onMounted, ref } from "vue"
import { useRouter } from "vue-router"
import { usePlayerStore } from "@/stores/playerStore"
import socket from "@/socket"

const router = useRouter()
const playerStore = usePlayerStore()

const players = ref([])
const hostPlayerId = ref(null)
const snackbar = ref(false)
const snackbarMessage = ref("")

socket.emit("joinGame", { gameId: playerStore.getGameId, playerName: playerStore.getPlayerName, playerId: playerStore.getPlayerId })

socket.on("playerJoined", (data) => {
  players.value = data.players
  hostPlayerId.value = data.hostPlayerId
})

socket.on("playerLeft", (data) => {
  players.value = data.players
})

socket.on("gameStarted", () => {
  router.push({ name: "Game", params: { gameId: playerStore.getGameId } })
})

// Démarrer la partie en tant que host
const startGame = () => {
  socket.emit("startGame", { gameId: playerStore.getGameId })
}

// Annuler la partie (pour tout le monde)
const cancelGame = () => {
  socket.emit("endGame", { gameId: playerStore.getGameId })
  playerStore.resetPlayerInfo()
  router.push("/")
}

// Quitter la partie en tant que joueur
const leaveGame = () => {
  socket.emit("leaveGame", { gameId: playerStore.getGameId })
  playerStore.resetPlayerInfo()
  router.push("/")
}

const copyToClipboard = (text) => {
  if (!text) {
    return
  }

  navigator.clipboard.writeText(text).then(() => {
    snackbarMessage.value = "ID de partie copié dans le presse-papiers"
    snackbar.value = true
  })
}

onMounted(async () => {
  snackbar.value = true
  snackbarMessage.value = "ID de partie copié dans le presse-papiers"
  try {
    const response = await ofetch(`http://localhost:3000/api/players/${playerStore.getGameId}`)

    players.value = response.players
    hostPlayerId.value = response.host.playerId
  } catch (error) {
    console.error("Erreur lors de la récupération des joueurs :", error)
  }
})
</script>
