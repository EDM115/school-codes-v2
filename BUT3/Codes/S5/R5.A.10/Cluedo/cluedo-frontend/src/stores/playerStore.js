import { defineStore } from "pinia"

export const usePlayerStore = defineStore("player", {
  state: () => ({
    gameId: null,
    playerId: null,
    playerName: "",
    isHost: false
  }),
  getters: {
    getGameId() {
      return this.gameId
    },
    getPlayerId() {
      return this.playerId
    },
    getPlayerName() {
      return this.playerName
    },
    isPlayerHost() {
      return this.isHost
    }
  },
  actions: {
    setPlayerInfo({ gameId, playerId, playerName, isHost }) {
      this.gameId = gameId
      this.playerId = playerId
      this.playerName = playerName
      this.isHost = isHost
    },
    resetPlayerInfo() {
      this.gameId = null
      this.playerId = null
      this.playerName = ""
      this.isHost = false
    }
  }
})
