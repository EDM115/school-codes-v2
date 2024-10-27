const { getSession, closeSession } = require("../services/neo4jService")
const { games, shuffleArray } = require("../services/gameStore")

// Créer une nouvelle partie
const createGame = async (req, res) => {
  const session = getSession()
  try {
    // Implémente la logique pour créer une partie dans Neo4j
    await session.run(`
      CREATE (g:Partie {id: randomUUID(), status: "en cours"})
      RETURN g
    `)
    res.status(200).send({ message: "Partie créée" })
  } catch (error) {
    res.status(500).send({ error: "Erreur lors de la création de la partie" })
  } finally {
    closeSession(session)
  }
}

/// Démarrer une partie avec la solution aléatoire
const startGame = async (req, res) => {
  const session = getSession()
  try {
    // Tirage aléatoire des cartes secrètes
    const solution = await session.run(`
      MATCH (p:Personnage), (a:Arme), (l:Piece)
      WITH p, a, l
      RETURN p.nom AS personnage, a.nom AS arme, l.nom AS piece
      ORDER BY rand() LIMIT 1
    `)

    const { personnage, arme, piece } = solution.records[0].toObject()
    
    // Stocker la solution dans la partie
    const gameId = req.body.gameId
    await session.run(`
      MATCH (g:Partie {id: $gameId})
      SET g.solution = {personnage: $personnage, arme: $arme, piece: $piece}
    `, { gameId, personnage, arme, piece })

    // Distribution des cartes aux joueurs
    const remainingCards = await session.run(`
      MATCH (p:Personnage), (a:Arme), (l:Piece)
      WHERE p.nom <> $personnage AND a.nom <> $arme AND l.nom <> $piece
      RETURN p.nom AS personnage, a.nom AS arme, l.nom AS piece
    `, { personnage, arme, piece })

    // Logique de distribution à chaque joueur
    const players = req.body.players // Liste des joueurs
    const cards = remainingCards.records
    distributeCardsToPlayers(players, cards)

    res.status(200).send({ message: "Partie démarrée", solution: { personnage, arme, piece } })
  } catch (error) {
    res.status(500).send({ error: "Erreur lors du démarrage de la partie" })
  } finally {
    closeSession(session)
  }
}

// Distribuer les cartes aux joueurs
const distributeCardsToPlayers = (players, cards) => {
  const shuffledCards = shuffle(cards)
  const numPlayers = players.length
  const numCardsPerPlayer = Math.floor(shuffledCards.length / numPlayers)

  for (let i = 0; i < numPlayers; i++) {
    const playerCards = shuffledCards.slice(i * numCardsPerPlayer, (i + 1) * numCardsPerPlayer)
    players[i].cards = playerCards
  }
}

// Calcul des scores à la fin de la partie
const endGame = async (req, res) => {
  const session = getSession()
  try {
    const result = await session.run(`
      MATCH (g:Partie {id: $gameId})
      RETURN g.solution AS solution
    `, { gameId: req.body.gameId })

    const solution = result.records[0].get("solution")
    
    // Récupérer les accusations des joueurs et calculer les scores
    const scores = await session.run(`
      MATCH (a:Accusation)-[:FAITE_DANS]->(g:Partie {id: $gameId})
      RETURN a.joueur AS player, 
        CASE WHEN a.piece = $solution.piece THEN 50 ELSE 0 END + 
        CASE WHEN a.arme = $solution.arme THEN 50 ELSE 0 END + 
        CASE WHEN a.personnage = $solution.personnage THEN 100 ELSE 0 END AS score
    `, { gameId: req.body.gameId, solution })

    res.status(200).send({ scores: scores.records.map(record => record.toObject()) })
  } catch (error) {
    res.status(500).send({ error: "Erreur lors du calcul des scores" })
  } finally {
    closeSession(session)
  }
}

// Faire un mouvement ou une hypothèse
const makeMove = async (req, res) => {
  const session = getSession()
  const { playerId, moveType, moveDetails } = req.body
  try {
    // Gère le mouvement ou l'hypothèse selon les règles du jeu
    await session.run(`
      MATCH (p:Partie {id: $gameId})
      RETURN p
    `, { gameId: req.body.gameId })

    res.status(200).send({ message: "Mouvement effectué" })
  } catch (error) {
    res.status(500).send({ error: "Erreur lors du mouvement" })
  } finally {
    closeSession(session)
  }
}

// Obtenir le statut d'une partie
const getGameStatus = async (req, res) => {
  const session = getSession()
  try {
    const gameId = req.params.id
    const result = await session.run(`MATCH (g:Partie {id: $gameId}) RETURN g`, { gameId })
    res.status(200).send(result.records[0])
  } catch (error) {
    res.status(500).send({ error: "Erreur lors de la récupération du statut de la partie" })
  } finally {
    closeSession(session)
  }
}

// Récupérer toutes les pièces, armes et personnages depuis la base de données
const getGameElements = async (req, res) => {
  const session = getSession()
  try {
    // Récupération des pièces
    const piecesResult = await session.run(`MATCH (p:Piece) RETURN p.nom AS name`)
    let pieces = piecesResult.records.map(record => record.get('name'))

    // Récupération des armes
    const armesResult = await session.run(`MATCH (a:Arme) RETURN a.nom AS name`)
    let armes = armesResult.records.map(record => record.get('name'))

    // Récupération des personnages
    const personnagesResult = await session.run(`MATCH (p:Personnage) RETURN p.nom AS name`)
    let personnages = personnagesResult.records.map(record => record.get('name'))

    pieces = shuffleArray(pieces)
    armes = shuffleArray(armes)
    personnages = shuffleArray(personnages)

    // Envoyer toutes les données au frontend
    res.status(200).json({
      pieces,
      armes,
      personnages,
      players: games[req.query.gameId].players
    })
  } catch (error) {
    console.error("Erreur lors de la récupération des éléments du jeu:", error)
    res.status(500).send({ error: "Erreur lors de la récupération des éléments du jeu" })
  } finally {
    closeSession(session)
  }
}

// Fetch the current game state including players, actions, and game elements
const getGameState = async (req, res) => {
  const session = getSession()
  try {
    const gameId = req.params.id

    // Fetch players, actions, and game elements
    const gameResult = await session.run(`
      MATCH (g:Game {id: $gameId})<-[:PART_OF]-(p:Player)
      OPTIONAL MATCH (p)-[:DONE_BY]->(a:Action)
      RETURN g, p, collect(a) AS actions
    `, { gameId })

    const records = gameResult.records.map(record => ({
      game: record.get("g").properties,
      player: record.get("p").properties,
      actions: record.get("actions").map(action => action.properties)
    }))

    res.status(200).json(records)
  } catch (error) {
    console.error("Error fetching game state:", error)
    res.status(500).json({ error: "Error fetching game state" })
  } finally {
    session.close()
  }
}

module.exports = {
  createGame,
  makeMove,
  getGameStatus,
  getGameState,
  startGame,
  endGame,
  getGameElements
}
