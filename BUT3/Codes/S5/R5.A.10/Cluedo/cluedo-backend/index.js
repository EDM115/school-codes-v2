const express = require("express")
const cors = require("cors")
const http = require("http")
const { uid } = require("uid/secure")
const { Server } = require("socket.io")
const gameRoutes = require("./routes/game")
const { games, shuffleArray } = require("./services/gameStore")
const { getSession } = require("./services/neo4jService")

const app = express()
app.use(cors())
app.use(express.json())
app.use("/api", gameRoutes)

const server = http.createServer(app)
const io = new Server(server, { cors: { origin: "*" } })

function distributeCards(players, allPieces, allWeapons, allCharacters) {
  return new Promise((resolve, reject) => {
    try {
      // Créer un pool complet de toutes les cartes
      let allCards = [...allPieces, ...allWeapons, ...allCharacters]

      // Mélanger les cartes
      allCards = shuffleArray(allCards)

      // Retirer les 3 cartes secrètes (solution du meurtre)
      const secretSolution = {
        piece: allPieces[Math.floor(Math.random() * allPieces.length)],
        arme: allWeapons[Math.floor(Math.random() * allWeapons.length)],
        personnage: allCharacters[Math.floor(Math.random() * allCharacters.length)]
      }

      // Filtrer les cartes du pool global pour retirer celles de la solution
      allCards = allCards.filter(card => 
        card !== secretSolution.piece && 
        card !== secretSolution.arme && 
        card !== secretSolution.personnage
      )

      // Répartir les cartes entre les joueurs
      const playerCards = players.map(() => [])  // Un tableau de cartes pour chaque joueur
      let playerIndex = 0

      // Répartir les cartes mélangées
      allCards.forEach(card => {
        playerCards[playerIndex].push(card)
        playerIndex = (playerIndex + 1) % players.length  // Tourner entre les joueurs
      })

      // Retourner les cartes attribuées à chaque joueur et la solution secrète
      resolve({ playerCards, secretSolution })
    } catch (error) {
      reject(error)
    }
  })
}

io.on("connection", (socket) => {
  console.log(`Un utilisateur est connecté (${socket.id})`)

  // Création d'une partie par le host
  socket.on("createGame", async (data, callback) => {
    const gameId = uid(8)

    const session = getSession()
    try {
      await session.run(`
        CREATE (g:Game {id: $gameId, status: "in_progress", created_at: timestamp()})
      `, { gameId })
      await session.run(`
        MATCH (g:Game {id: $gameId}) 
        CREATE (p:Player {id: $playerId, name: $playerName})-[:PART_OF]->(g)
      `, { playerId: data.playerId, playerName: data.playerName, gameId })
    } catch (error) {
      console.error("Error creating game in Neo4j :", error)
    } finally {
      session.close()
    }

    // Créer une nouvelle partie avec le host et une liste vide de joueurs
    games[gameId] = {
      host: { playerId: data.playerId, playerName: data.playerName, socketId: socket.id },
      players: [{ playerId: data.playerId, playerName: data.playerName, socketId: socket.id }],
      gameHasStarted: false,
      activePlayerIndex: 0
    }

    socket.join(gameId)
    callback({ gameId, role: "host" })
    io.to(gameId).emit("playerJoined", { players: games[gameId].players, hostId: socket.id, hostPlayerId: games[gameId].host.playerId })
    console.log(`Nouvelle partie créée avec l'ID ${gameId} par ${data.playerName}`)
  })

  // Rejoindre une partie existante
  socket.on("joinGame", async (data) => {
    const game = games[data.gameId]

    if (!game) {
      socket.emit("error", "La partie n'existe pas.")
      console.log(`La partie ${data.gameId} n'existe pas.`)
      return
    }

    if (game.gameHasStarted) {
      socket.emit("error", "La partie a déjà commencé.")
      console.log(`La partie ${data.gameId} a déjà commencé.`)
      return
    }

    // Vérifier si le joueur n'est pas déjà dans la partie
    const playerExists = game.players.some(p => p.socketId === socket.id)
    if (!playerExists) {
      game.players.push({ playerId: data.playerId, playerName: data.playerName, socketId: socket.id })
      socket.join(data.gameId)

      const session = getSession()
      try {
        await session.run(`
          MATCH (g:Game {id: $gameId})
          CREATE (p:Player {id: $playerId, name: $playerName})-[:PART_OF]->(g)
        `, { playerId: data.playerId, playerName: data.playerName, gameId: data.gameId })
      } catch (error) {
        console.error("Error adding player to Neo4j :", error)
      } finally {
        session.close()
      }

      io.to(data.gameId).emit("playerJoined", { players: game.players, hostId: game.host.socketId, hostPlayerId: game.host.playerId })
      console.log(`${data.playerName} a rejoint la partie ${data.gameId}`)
    }

    socket.emit("role", socket.id === game.host.socketId ? "host" : "player")
  })

  // Début du tour
  socket.on("startTurn", async (data) => {
    const game = games[data.gameId]
    const activePlayer = game.players[game.activePlayerIndex]

    const session = getSession()
    try {
      await session.run(`
        MATCH (p:Player {id: $playerId})-[:PART_OF]->(g:Game {id: $gameId})
        CREATE (a:Action {type: "start_turn", details: $details, timestamp: timestamp()})-[:DONE_BY]->(p)-[:IN]->(g)
      `, {
        playerId: activePlayer.playerId,
        gameId: data.gameId,
        details: "Started turn"
      })
    } catch (error) {
      console.error("Error logging start turn in Neo4j :", error)
    } finally {
      session.close()
    }

    io.to(activePlayer.socketId).emit("yourTurn")
    console.log(`C'est au tour de ${activePlayer.playerName} dans la partie ${data.gameId}`)
  })

  const nextTurn = async (gameId) => {
    const game = games[gameId]

    game.players.forEach(player => player.hasMoved = false)
    
    // Passer au joueur suivant
    const oldPlayer = game.players[game.activePlayerIndex]
    game.activePlayerIndex = (game.activePlayerIndex + 1) % game.players.length
    const activePlayer = game.players[game.activePlayerIndex]

    const session = getSession()
    try {
      await session.run(`
        MATCH (p:Player {id: $oldPlayerId})-[:PART_OF]->(g:Game {id: $gameId})
        CREATE (a:Action {type: "end_turn", details: $details, timestamp: timestamp()})-[:DONE_BY]->(p)-[:IN]->(g)
      `, {
        oldPlayerId: oldPlayer.playerId,
        gameId,
        details: "Ended turn"
      })
    } catch (error) {
      console.error("Error logging end turn in Neo4j :", error)
    } finally {
      session.close()
    }
  
    // Envoyer le tour au joueur actif et notifier les autres
    io.to(activePlayer.socketId).emit("yourTurn") // Notifier le joueur actif
    io.to(gameId).emit("updateTurn", { activePlayer: activePlayer.playerName }) // Notifier tous les joueurs
    io.to(gameId).emit("logAction", {
      message: `${oldPlayer.playerName} a terminé son tour. C'est à ${activePlayer.playerName} de jouer.`
    })
    console.log(`${oldPlayer.playerName} a terminé son tour. C'est à ${activePlayer.playerName} de jouer dans la partie ${gameId}`)
  }

  // Fin du tour et passage au joueur suivant
  socket.on("endTurn", (data) => {
    nextTurn(data.gameId)
  })

  // Démarrer la partie par le host
  socket.on("startGame", async (data) => {
    const game = games[data.gameId]

    if (!game || game.host.socketId !== socket.id) {
      socket.emit("error", "Vous n'êtes pas le host de cette partie.")
      console.log("Tentative de démarrage de partie par un joueur non autorisé dans la partie", data.gameId)
      return
    }

    game.gameHasStarted = true
    game.activePlayerIndex = 0

    const session = getSession()
    try {
      // Récupérer les pièces, armes, et personnages depuis Neo4j
      const piecesResult = await session.run(`MATCH (p:Piece) RETURN p.nom AS name`)
      let allPieces = piecesResult.records.map(record => record.get('name'))

      const weaponsResult = await session.run(`MATCH (a:Arme) RETURN a.nom AS name`)
      let allWeapons = weaponsResult.records.map(record => record.get('name'))

      const charactersResult = await session.run(`MATCH (p:Personnage) RETURN p.nom AS name`)
      let allCharacters = charactersResult.records.map(record => record.get('name'))

      allPieces = shuffleArray(allPieces)
      allWeapons = shuffleArray(allWeapons)
      allCharacters = shuffleArray(allCharacters)

      // Vérification que nous avons bien récupéré des cartes
      if (allPieces.length === 0 || allWeapons.length === 0 || allCharacters.length === 0) {
        socket.emit("error", "Impossible de démarrer la partie, les cartes ne sont pas disponibles.")
        console.log("Impossible de démarrer la partie, les cartes ne sont pas disponibles pour la partie", data.gameId)
        return
      }

      // Assigner chaque joueur à une pièce aléatoire au début
      game.players.forEach(player => {
        const randomPiece = allPieces[Math.floor(Math.random() * allPieces.length)]
        player.currentRoom = randomPiece
      })
      io.to(data.gameId).emit("updatePlayersRooms", {
        players: game.players.map(p => ({ playerName: p.playerName, currentRoom: p.currentRoom }))
      })

      // Distribuer les cartes aux joueurs et obtenir la solution secrète
      const { playerCards, secretSolution } = await distributeCards(game.players, allPieces, allWeapons, allCharacters)
      game.players.forEach((player, index) => {
        player.cards = playerCards[index]
      })
      game.solution = secretSolution
      console.log("Solution secrète : ", secretSolution)
      
      // Commence le tour du premier joueur (toujours le host)
      const activePlayer = game.players[game.activePlayerIndex]
      io.to(data.gameId).emit("gameStarted")
      io.to(activePlayer.socketId).emit("yourTurn") // Notifier le host qu'il commence
      io.to(data.gameId).emit("updateTurn", { activePlayer: activePlayer.playerName }) // Notifier tous les joueurs

      await session.run(`MATCH (g:Game {id: $gameId}) SET g.started_at = timestamp()`, { gameId: data.gameId })
      for (let player of game.players) {
        await session.run(`
          MATCH (p:Player {id: $playerId})-[:PART_OF]->(g:Game {id: $gameId})
          SET p.cards = $cards
        `, { playerId: player.playerId, gameId: data.gameId, cards: player.cards.map(card => JSON.stringify(card)) })
      }
      await session.run(`
        MATCH (g:Game {id: $gameId})
        SET g.solution = $solution
      `, { gameId: data.gameId, solution: JSON.stringify(secretSolution) })
    } catch (error) {
      console.error("Erreur lors de la récupération des cartes depuis Neo4j : ", error)
      socket.emit("error", "Erreur lors de la récupération des cartes.")
    } finally {
      session.close()
    }
  })

  // Lorsqu'un joueur se déplace dans une pièce
  socket.on("enterRoom", async (data) => {
    const game = games[data.gameId]
    const player = game.players.find(p => p.socketId === socket.id)

    if (player.hasMoved) {
      socket.emit("error", "Vous ne pouvez vous déplacer qu'une seule fois par tour.")
      console.log(`${player.playerName} a déjà bougé dans la partie ${data.gameId}`)
      return
    }

    player.currentRoom = data.room
    player.hasMoved = true

    const session = getSession()
    try {
      await session.run(`
        MATCH (p:Player {id: $playerId})-[:PART_OF]->(g:Game {id: $gameId})
        CREATE (a:Action {type: "move", details: $details, timestamp: timestamp()})-[:DONE_BY]->(p)-[:IN]->(g)
      `, {
        playerId: player.playerId,
        gameId: data.gameId,
        details: `Moved to ${data.room}`
      })
    } catch (error) {
      console.error("Error logging move in Neo4j:", error)
    } finally {
      session.close()
    }

    // Diffuser l'action de déplacement à tous les joueurs
    io.to(data.gameId).emit("logAction", {
      message: `${player.playerName} est entré dans ${data.room}`
    })
    io.to(data.gameId).emit("updatePlayersRooms", {
      players: game.players.map(p => ({ playerName: p.playerName, currentRoom: p.currentRoom }))
    })
    console.log(`${player.playerName} est entré dans ${data.room} dans la partie ${data.gameId}`)
  })

  // Annuler la partie
  socket.on("endGame", async (data) => {
    const game = games[data.gameId]

    if (!game || game.host.socketId !== socket.id) {
      socket.emit("error", "Vous n'êtes pas le host de cette partie.")
      console.log("Tentative d'annulation de partie par un joueur non autorisé dans la partie", data.gameId)
      return
    }

    const session = getSession()
    try {
      await session.run(`
        MATCH (a:Action)-[:IN]->(g:Game {id: $gameId})
        DETACH DELETE a
      `, { gameId: data.gameId })

      await session.run(`
        MATCH (p:Player)-[:PART_OF]->(g:Game {id: $gameId})
        DETACH DELETE p
      `, { gameId: data.gameId })

      await session.run(`
        MATCH (g:Game {id: $gameId})
        DETACH DELETE g
      `, { gameId: data.gameId })

      await session.run(`
        MATCH (a:Action)
        WHERE NOT (a)--()
        DETACH DELETE a
      `)
    } catch (error) {
      console.error("Error deleting game from Neo4j :", error)
    } finally {
      session.close()
    }

    io.to(data.gameId).emit("gameEnded", { message: "Partie annulée par le host" })
    io.to(data.gameId).emit("logAction", {
      message: "La partie a été terminée par l'hôte."
    })
    console.log(`La partie ${data.gameId} a été annulée par ${game.host.playerName}`)

    // Supprimer la partie après l'annulation
    delete games[data.gameId]
  })

  // Lorsqu'un joueur fait une hypothèse
  socket.on("makeHypothesis", async (data) => {
    const game = games[data.gameId]
    const currentPlayer = game.players.find(p => p.socketId === socket.id)
    const hypothesis = data.hypothesis
    const solution = game.solution
    let refuterFound = false

    if (!solution) {
      console.error(`No solution found for game ${data.gameId}`)
      socket.emit("error", "Aucune solution trouvée pour cette partie.")
      return
    }

    io.to(data.gameId).emit("logAction", {
      message: `${currentPlayer.playerName} a fait une hypothèse : ${hypothesis.personnage} avec ${hypothesis.arme} dans ${hypothesis.piece}`
    })
    console.log(`${currentPlayer.playerName} a fait une hypothèse : ${hypothesis.personnage} avec ${hypothesis.arme} dans ${hypothesis.piece} dans la partie ${data.gameId}`)

    const session = getSession()
    try {
      await session.run(`
        MATCH (p:Player {id: $playerId})-[:PART_OF]->(g:Game {id: $gameId})
        CREATE (a:Action {type: "hypothesis", details: $details, timestamp: timestamp()})-[:DONE_BY]->(p)-[:IN]->(g)
      `, {
        playerId: currentPlayer.playerId,
        gameId: data.gameId,
        details: `Hypothesis: ${hypothesis.personnage}, ${hypothesis.arme}, ${hypothesis.piece}`
      })
    } catch (error) {
      console.error("Error logging hypothesis in Neo4j :", error)
    } finally {
      session.close()
    }

    // Parcourir les joueurs dans l'ordre pour trouver un réfuteur
    for (let player of game.players) {
      if (player.socketId !== socket.id) {
        if (!player.cards || !Array.isArray(player.cards)) {
          console.error(`Le joueur ${player.playerName} ne possède pas de cartes dans la partie ${data.gameId}`)
          continue  // Passer au joueur suivant s'il n'a pas de cartes
        }

        // Vérifier si ce joueur possède une des cartes
        const refutableCards = player.cards.filter(card => 
          card.personnage === hypothesis.personnage || 
          card.arme === hypothesis.arme || 
          card.piece === hypothesis.piece
        )
        
        if (refutableCards.length > 0) {
          // Montrer une carte réfutée au joueur ayant fait l'hypothèse
          socket.to(player.socketId).emit("refuteHypothesis", { card: refutableCards[0], refuter: player.playerName })
          socket.emit("refutationResult", { refuter: player.playerName, card: refutableCards[0] })
          refuterFound = true
          console.log(`Le joueur ${player.playerName} a réfuté l'hypothèse de ${currentPlayer.playerName} avec la carte ${refutableCards[0].nom} dans la partie ${data.gameId}`)
          break
        }
      }
    }

    if (!refuterFound) {
      socket.emit("noRefutation", { message: "Personne ne peut réfuter cette hypothèse." })
      console.log(`Personne ne peut réfuter l'hypothèse de ${currentPlayer.playerName} dans la partie ${data.gameId}`)

      const armeCorrecte = hypothesis.arme === solution.arme
      const personnageCorrect = hypothesis.personnage === solution.personnage
      const pieceCorrecte = hypothesis.piece === solution.piece
      const hypotheseCorrecte = armeCorrecte && personnageCorrect && pieceCorrecte

      socket.emit("hypothesisResult", { arme: armeCorrecte, personnage: personnageCorrect, piece: pieceCorrecte, hypothese: hypotheseCorrecte, player: currentPlayer.playerId })
    }
  })

  // Lorsqu'un joueur fait une accusation
  socket.on("makeAccusation", async (data) => {
    const game = games[data.gameId]
    const accusation = data.accusation
    const solution = game.solution // Cartes secrètes (personnage, arme, pièce)
    const currentPlayer = game.players.find(p => p.socketId === socket.id)

    if (!solution) {
      console.error(`No solution found for game ${data.gameId}`)
      socket.emit("error", "Aucune solution trouvée pour cette partie.")
      return
    }

    // Diffuser l'accusation à tous les joueurs
    io.to(data.gameId).emit("logAction", {
      message: `${currentPlayer.playerName} a fait une accusation : ${accusation.personnage} avec ${accusation.arme} dans ${accusation.piece}`
    })

    console.log(`${currentPlayer.playerName} a fait une accusation : ${accusation.personnage} avec ${accusation.arme} dans ${accusation.piece} dans la partie ${data.gameId}`)

    const session = getSession()
    try {
      await session.run(`
        MATCH (p:Player {id: $playerId})-[:PART_OF]->(g:Game {id: $gameId})
        CREATE (a:Action {type: "accusation", details: $details, timestamp: timestamp()})-[:DONE_BY]->(p)-[:IN]->(g)
      `, {
        playerId: currentPlayer.playerId,
        gameId: data.gameId,
        details: `Accusation: ${accusation.personnage}, ${accusation.arme}, ${accusation.piece}`
      })
    } catch (error) {
      console.error("Error logging accusation in Neo4j :", error)
    } finally {
      session.close()
    }

    let points = 0

    // Vérifier chaque élément de l'accusation
    if (accusation.personnage === solution.personnage) points += 5
    if (accusation.arme === solution.arme) points += 5
    if (accusation.piece === solution.piece) points += 5

    currentPlayer.score = (currentPlayer.score || 0) + points

    // Accusation correcte
    if (points === 15) {
      currentPlayer.score = (currentPlayer.score || 0) + 10 // 10 points pour une accusation correcte

      // Informer tout le monde que le joueur a trouvé la solution
      io.to(data.gameId).emit("logAction", {
        message: `${currentPlayer.playerName} a trouvé la solution : ${accusation.personnage} avec ${accusation.arme} dans ${accusation.piece} !`
      })

      // Informer uniquement le joueur que l'accusation est correcte et son score final
      socket.emit("accusationResult", { success: true, points: currentPlayer.score })

      // Terminer la partie et informer tout le monde
      io.to(data.gameId).emit("gameEnded", {
        message: `${currentPlayer.playerName} a remporté la partie en trouvant la solution.`
      })

      // affichage des scores de tous les joueurs
      const gameScores = game.players.map(p => ({ playerName: p.playerName, score: p.score || 0 }))
      io.to(data.gameId).emit("gameScores", {
        message: `Scores finaux : ${gameScores.map(p => `${p.playerName} : ${p.score}`).join(", ")}`
      })

      let session = getSession()
      try {
        for (let player of game.players) {
          await session.run(`
            MATCH (p:Player {id: $playerId})-[:PART_OF]->(g:Game {id: $gameId})
            SET p.score = $score
          `, { playerId: player.playerId, gameId: data.gameId, score: player.score || 0 })
        }
      } catch (error) {
        console.error("Error updating scores in Neo4j :", error)
      } finally {
        session.close()
      }
    } else {
      // Accusation incorrecte, points partiels
      currentPlayer.score = (currentPlayer.score || 0) + points

      // Informer le joueur du résultat partiel
      socket.emit("accusationResult", { success: false, points: currentPlayer.score })

      const session = getSession()
      try {
        await session.run(`
          MATCH (p:Player {id: $playerId})-[:PART_OF]->(g:Game {id: $gameId})
          SET p.score = $score
        `, { playerId: currentPlayer.playerId, gameId: data.gameId, score: currentPlayer.score || 0 })
      } catch (error) {
        console.error("Error updating score in Neo4j :", error)
      } finally {
        session.close()
      }

      // Diffuser à tous que l'accusation était incorrecte
      io.to(data.gameId).emit("logAction", {
        message: `${currentPlayer.playerName} a fait une accusation incorrecte.`
      })
    }
  })

  // Quitter la partie
  socket.on("leaveGame", async (data) => {
    const game = games[data.gameId]

    if (!game) {
      console.log(`La partie ${data.gameId} n'existe pas.`)
      return
    }

    // Supprimer le joueur de la liste
    game.players = game.players.filter((p) => p.socketId !== socket.id)
    io.to(data.gameId).emit("playerLeft", { players: game.players })
    console.log(`Socket ID ${socket.id} a quitté la partie ${data.gameId}`)

    let session = getSession()
    try {
      await session.run(`
        MATCH (p:Player {id: $playerId})-[:PART_OF]->(g:Game {id: $gameId})
        DETACH DELETE p
      `, { playerId: data.playerId, gameId: data.gameId })

      await session.run(`
        MATCH (a:Action)
        WHERE NOT (a)--()
        DETACH DELETE a
      `)
    } catch (error) {
      console.error("Error deleting player from Neo4j :", error)
    } finally {
      session.close()
    }

    // Si tous les joueurs sont partis, supprimer la partie
    if (game.players.length === 0) {
      session = getSession()
      try {
        await session.run(`
          MATCH (a:Action)-[:IN]->(g:Game {id: $gameId})
          DETACH DELETE a
        `, { gameId: data.gameId })

        await session.run(`
          MATCH (p:Player)-[:PART_OF]->(g:Game {id: $gameId})
          DETACH DELETE p
        `, { gameId: data.gameId })

        await session.run(`
          MATCH (g:Game {id: $gameId})
          DETACH DELETE g
        `, { gameId: data.gameId })

        await session.run(`
          MATCH (a:Action)
          WHERE NOT (a)--()
          DETACH DELETE a
        `)
      } catch (error) {
        console.error("Error deleting game from Neo4j :", error)
      } finally {
        session.close()
      }

      delete games[data.gameId]
    }
  })

  // Gérer la déconnexion d'un utilisateur
  socket.on("disconnect", async () => {
    console.log(`Un utilisateur est déconnecté (${socket.id})`)

    // Chercher si l'utilisateur était dans une partie
    for (const gameId in games) {
      const game = games[gameId]
      game.players = game.players.filter(p => p.socketId !== socket.id)
      io.to(gameId).emit("playerLeft", { players: game.players })

      let session = getSession()
      try {
        await session.run(`
          MATCH (p:Player {socketId: $socketId})-[:PART_OF]->(g:Game {id: $gameId})
          DETACH DELETE p
        `, { socketId: socket.id, gameId })

        await session.run(`
          MATCH (a:Action)
          WHERE NOT (a)--()
          DETACH DELETE a
        `)
      } catch (error) {
        console.error("Error deleting player from Neo4j :", error)
      } finally {
        session.close()
      }

      // Si le host se déconnecte, annuler la partie
      if (game.host.socketId === socket.id) {
        io.to(gameId).emit("gameEnded", { message: "Le host a quitté la partie." })
        console.log(`Le host de la partie ${gameId} a quitté la partie`)

        session = getSession()
        try {
          await session.run(`
            MATCH (a:Action)-[:IN]->(g:Game {id: $gameId})
            DETACH DELETE a
          `, { gameId: data.gameId })

          await session.run(`
            MATCH (p:Player)-[:PART_OF]->(g:Game {id: $gameId})
            DETACH DELETE p
          `, { gameId: data.gameId })

          await session.run(`
            MATCH (g:Game {id: $gameId})
            DETACH DELETE g
          `, { gameId: data.gameId })

          await session.run(`
            MATCH (a:Action)
            WHERE NOT (a)--()
            DETACH DELETE a
          `)
        } catch (error) {
          console.error("Error deleting game from Neo4j :", error)
        } finally {
          session.close()
        }

        delete games[gameId]
      }

      // Si plus aucun joueur, supprimer la partie
      if (game.players.length === 0) {
        session = getSession()
        try {
          await session.run(`
            MATCH (a:Action)-[:IN]->(g:Game {id: $gameId})
            DETACH DELETE a
          `, { gameId: data.gameId })

          await session.run(`
            MATCH (p:Player)-[:PART_OF]->(g:Game {id: $gameId})
            DETACH DELETE p
          `, { gameId: data.gameId })

          await session.run(`
            MATCH (g:Game {id: $gameId})
            DETACH DELETE g
          `, { gameId: data.gameId })

          await session.run(`
            MATCH (a:Action)
            WHERE NOT (a)--()
            DETACH DELETE a
          `)
        } catch (error) {
          console.error("Error deleting game from Neo4j :", error)
        } finally {
          session.close()
        }

        delete games[gameId]
      }
    }
  })
})

server.listen(3000, "0.0.0.0", () => {
  console.log("Le serveur tourne sur le port 3000")
})
