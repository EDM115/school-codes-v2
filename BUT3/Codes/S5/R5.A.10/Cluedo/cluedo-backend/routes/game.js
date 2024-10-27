const express = require("express")
const { createGame, makeMove, getGameStatus, getGameState, getGameElements } = require("../controllers/gameController")
const router = express.Router()
const { games } = require("../services/gameStore")

router.post("/game", createGame) // Créer une nouvelle partie
router.post("/move", makeMove) // Faire un mouvement ou une hypothèse
router.get("/game/:id", getGameStatus) // Obtenir le statut d'une partie
router.get("/game-state/:id", getGameState) // Obtenir l'état actuel de la partie
router.get("/game-elements", getGameElements) // Récupérer toutes les pièces, armes et personnages depuis la base de données
router.get("/players/:gameId", (req, res) => {
  const gameId = req.params.gameId
  const game = games[gameId]

  if (!game) {
    return res.status(404).json({ error: "La partie n'existe pas." })
  }

  res.json({ players: game.players, host: game.host })
})

module.exports = router
