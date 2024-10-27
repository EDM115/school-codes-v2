<template>
  <v-container>
    <v-row>
      <v-col>
        <div class="d-flex align-center">
          <p class="mr-2 text-h4">
            <strong>Partie en cours - ID :</strong>
          </p>
          <pre class="mr-2 text-h4">{{ playerStore.getGameId }}</pre>
        </div>
        <p v-if="isMyTurn">
          C'est votre tour de jouer !
        </p>
        <p v-else-if="gameEnded">
          La partie est terminée.
        </p>
        <p v-else>
          En attente du tour de {{ activePlayer }}...
        </p>
      </v-col>
    </v-row>

    <v-row>
      <v-col>
        <h2 class="mb-2">
          Liste des pièces
        </h2>
        <v-list rounded="lg">
          <v-list-item
            v-for="piece in pieces"
            :key="piece"
            :disabled="!isMyTurn || alreadyEnteredOnce || piece === players.find(player => player.playerName === playerStore.getPlayerName)?.currentRoom"
            :class="(!isMyTurn || alreadyEnteredOnce || piece === players.find(player => player.playerName === playerStore.getPlayerName)?.currentRoom) ? 'cursor-default' : 'cursor-pointer'"
            @click="enterRoom(piece)"
          >
            {{ piece }}
          </v-list-item>
        </v-list>

        <h3 class="mt-4 mb-2">
          Joueurs et leurs pièces
        </h3>
        <v-list rounded="lg">
          <v-list-item
            v-for="player in players"
            :key="player.playerName"
          >
            {{ player.playerName }} - Dans {{ player.currentRoom || 'Aucune pièce' }}
          </v-list-item>
        </v-list>
      </v-col>

      <v-col>
        <h2 class="mb-2">
          Logs de jeu
        </h2>
        <v-list rounded="lg">
          <v-list-item
            v-for="log in gameLogs"
            :key="log"
          >
            {{ log }}
          </v-list-item>
        </v-list>
      </v-col>

      <v-col>
        <h2 class="mb-2">
          Actions
        </h2>
        <v-select
          v-model="selectedPiece"
          :items="pieces"
          clearable
          :disabled="!isMyTurn"
          rounded="lg"
          label="Choisir une pièce"
        />
        <v-select
          v-model="selectedArme"
          :items="armes"
          clearable
          :disabled="!isMyTurn"
          rounded="lg"
          label="Choisir une arme"
        />
        <v-select
          v-model="selectedPersonnage"
          :items="personnages"
          clearable
          :disabled="!isMyTurn"
          rounded="lg"
          label="Choisir un personnage"
        />

        <v-col class="d-flex flex-column align-center justify-center">
          <v-row class="d-flex justify-center">
            <v-btn
              :disabled="!isMyTurn || !canMakeHypothesis || alreadyHypotized"
              color="primary"
              class="mb-2"
              @click="makeHypothesis"
            >
              Faire l'hypothèse
            </v-btn>
          </v-row>
          <v-row class="d-flex justify-center">
            <v-btn
              :disabled="!isMyTurn || !canMakeAccusation || alreadyAccused"
              color="primary"
              class="mb-2"
              @click="makeAccusation"
            >
              Faire l'accusation
            </v-btn>
          </v-row>
          <v-row class="d-flex justify-center">
            <v-btn
              :disabled="!isMyTurn"
              color="primary"
              class="mb-2"
              @click="endTurn"
            >
              Terminer le tour
            </v-btn>
          </v-row>
          <v-row
            v-if="playerStore.isHost"
            class="d-flex justify-center"
          >
            <v-btn
              color="error"
              class="mt-2"
              @click="endGame"
            >
              Terminer la Partie
            </v-btn>
          </v-row>
          <v-row
            v-else
            class="d-flex justify-center"
          >
            <v-btn
              color="error"
              class="mt-2"
              @click="leaveGame"
            >
              Quitter la Partie
            </v-btn>
          </v-row>
        </v-col>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ofetch } from "ofetch"
import { computed, onMounted, ref } from "vue"
import { useRouter } from "vue-router"
import { usePlayerStore } from "@/stores/playerStore"
import socket from "@/socket"

const router = useRouter()
const playerStore = usePlayerStore()

const pieces = ref([])
const armes = ref([])
const personnages = ref([])
const players = ref([])
const selectedPiece = ref(null)
const selectedArme = ref(null)
const selectedPersonnage = ref(null)
const gameLogs = ref([])
const isMyTurn = ref(false)
const alreadyEnteredOnce = ref(false)
const alreadyAccused = ref(false)
const alreadyHypotized = ref(false)
const activePlayer = ref("")
const gameEnded = ref(false)

const canMakeAccusation = computed(() => selectedPiece.value && selectedArme.value && selectedPersonnage.value)
const canMakeHypothesis = computed(() => selectedPiece.value && selectedArme.value && selectedPersonnage.value)

const endTurn = () => {
  socket.emit("endTurn", { gameId: playerStore.getGameId })
  isMyTurn.value = false
}

socket.on("updateTurn", (data) => {
  activePlayer.value = data.activePlayer
  isMyTurn.value = data.activePlayer === playerStore.getPlayerName
  alreadyEnteredOnce.value = false
  alreadyAccused.value = false
  alreadyHypotized.value = false
})

const enterRoom = (room) => {
  socket.emit("enterRoom", { gameId: playerStore.getGameId, room })
  alreadyEnteredOnce.value = true
}

// Récupérer les éléments de jeu (pièces, armes, personnages) depuis l'API
const getGameElements = async () => {
  try {
    const response = await ofetch("http://localhost:3000/api/game-elements", { query: { gameId: playerStore.getGameId } })

    pieces.value = response.pieces
    armes.value = response.armes
    personnages.value = response.personnages
    players.value = response.players
  } catch (error) {
    console.error("Erreur lors de la récupération des éléments du jeu : ", error)
  }
}

const makeHypothesis = () => {
  if (canMakeHypothesis.value) {
    const hypothesis = {
      player: playerStore.getPlayerName,
      personnage: selectedPersonnage.value,
      arme: selectedArme.value,
      piece: selectedPiece.value
    }

    alreadyHypotized.value = true
    socket.emit("makeHypothesis", { gameId: playerStore.getGameId, hypothesis })
  }
}

// Faire une accusation
const makeAccusation = () => {
  if (canMakeAccusation.value) {
    const accusation = {
      player: playerStore.getPlayerName,
      personnage: selectedPersonnage.value,
      arme: selectedArme.value,
      piece: selectedPiece.value
    }

    alreadyAccused.value = true
    socket.emit("makeAccusation", { gameId: playerStore.getGameId, accusation })
  }
}

// Terminer la partie en tant que host
const endGame = () => {
  socket.emit("endGame", { gameId: playerStore.getGameId })
  router.push("/")
}

// Quitter la partie
const leaveGame = () => {
  socket.emit("leaveGame", { gameId: playerStore.getGameId })
  router.push("/")
}

socket.on("playerJoined", (data) => {
  gameLogs.value.push(`${data.playerName} a rejoint la partie.`)
})

socket.on("yourTurn", () => {
  isMyTurn.value = true
})

socket.on("updateTurn", (data) => {
  activePlayer.value = data.activePlayer
})

socket.on("updatePlayersRooms", (data) => {
  players.value = data.players
})

socket.on("refuteHypothesis", (data) => {
  gameLogs.value.push(`Votre hypothèse a été réfutée par ${data.refuter} avec la carte ${data.card}`)
})

socket.on("noRefutation", () => {
  gameLogs.value.push("Aucun joueur n'a pu réfuter votre hypothèse.")
})

socket.on("hypothesisResult", (data) => {
  if (data.player === playerStore.getPlayerId) {
    gameLogs.value.push(`Résultats de l'hypothèse : ${data.hypothese ? "Correcte" : "Incorrecte"}. Détails : Arme : ${data.arme ? "Correct" : "Incorrect"}, Personnage : ${data.personnage ? "Correct" : "Incorrect"}, Pièce : ${data.piece ? "Correct" : "Incorrect"}`)
  }
})

socket.on("playerMove", (move) => {
  gameLogs.value.push(`${move.playerName} s'est déplacé dans ${move.piece}`)
  isMyTurn.value = false
})

socket.on("gameEnded", (data) => {
  gameLogs.value.push(data.message)
  canMakeAccusation.value = false
  canMakeHypothesis.value = false
  isMyTurn.value = false
  alreadyEnteredOnce.value = true
  alreadyAccused.value = true
  alreadyHypotized.value = true
  gameEnded.value = true
})

socket.on("gameScores", (data) => {
  gameLogs.value.push(data.message)
})

socket.on("logAction", (data) => {
  gameLogs.value.push(data.message)
})

// Initialisation des pièces de jeu
onMounted(async () => {
  await getGameElements()
})
</script>

<style>
.game-board {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}
</style>
