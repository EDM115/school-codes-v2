const io = require("socket.io-client");
const fs = require("node:fs");
const neo4j = require("neo4j-driver");
const { performance } = require("perf_hooks");
const { ofetch } = require("ofetch");

const driver = neo4j.driver("bolt://localhost:7687", neo4j.auth.basic("neo4j", "azertyuiop"));
const session = driver.session({ database: "cluedo" });

const socketUrl = "http://localhost:3000";

const timestampFile = "benchmark_timestamps.txt";

const NUM_CONNECTIONS = 5000;
const NUM_ACTIONS = 5000;
const TIMESTAMP_INTERVAL = 50;

function logTimestamp(message) {
  const timestamp = performance.now();
  fs.appendFileSync(timestampFile, `${message}, ${timestamp}\n`);
}

const gameData = [];

// Step 1: Create Socket.IO connections and games
async function createSockets() {
  let sockets = [];

  logTimestamp("Starting Socket.IO connection creation");
  console.log(`Step 1 :\nCreating ${NUM_CONNECTIONS} Socket.IO connections`);

  for (let i = 0; i < NUM_CONNECTIONS; i++) {
    const socket = io(socketUrl);

    socket.on("connect", () => {
      const playerId = `player_${i}`;
      const playerName = `Player_${i}`;

      // Create a new game for every socket connection
      socket.emit("createGame", { playerName, playerId }, (response) => {
        if (response && response.gameId) {
          gameData.push({ gameId: response.gameId, playerId, socket });
          if (gameData.length % TIMESTAMP_INTERVAL === 0) {
            logTimestamp(`Socket.IO connection and game created: ${gameData.length}`);
          }
        } else {
          console.error("Error creating game:", response);
        }
      });
    });

    socket.on("error", (err) => {
      console.error(`Socket.IO error on connection ${i}:`, err);
    });

    // Delay to avoid overloading server
    await new Promise(resolve => setTimeout(resolve, 30));
  }

  logTimestamp("All Socket.IO connections and games created");
  console.log("All Socket.IO connections and games created");
  return sockets;
}

// Step 2: Trigger game events via Socket.IO
async function triggerGameEvents() {
  logTimestamp("Starting game events");
  console.log(`Step 2 :\nTriggering ${NUM_ACTIONS} game events`);

  
  for (let i = 0; i < NUM_ACTIONS; i++) {
    const gameIndex = i % gameData.length;
    const { gameId, playerId, socket } = gameData[gameIndex];
    const gameElements = await ofetch("http://localhost:3000/api/game-elements", { query: { gameId } });

    // Cycle through all game events
    switch (i % 10) {
      case 0: // joinGame
        socket.emit("joinGame", { gameId, playerName: `Player_${i}`, playerId });
        break;
      case 1: // startGame (Start the game if it"s not already started)
        socket.emit("startGame", { gameId });
        break;
      case 2: // startTurn
        socket.emit("startTurn", { gameId });
        break;
      case 3: // endTurn
        socket.emit("endTurn", { gameId });
        break;
      case 4: // enterRoom
        socket.emit("enterRoom", { gameId, room: gameElements.pieces[Math.floor(Math.random() * gameElements.pieces.length)] });
        break;
      case 5: // makeHypothesis
        socket.emit("makeHypothesis", {
          gameId,
          hypothesis: {
            personnage: gameElements.personnages[Math.floor(Math.random() * gameElements.personnages.length)],
            arme: gameElements.armes[Math.floor(Math.random() * gameElements.armes.length)],
            piece: gameElements.pieces[Math.floor(Math.random() * gameElements.pieces.length)]
          }
        });
        break;
      case 6: // makeAccusation
        socket.emit("makeAccusation", {
          gameId,
          accusation: {
            personnage: gameElements.personnages[Math.floor(Math.random() * gameElements.personnages.length)],
            arme: gameElements.armes[Math.floor(Math.random() * gameElements.armes.length)],
            piece: gameElements.pieces[Math.floor(Math.random() * gameElements.pieces.length)]
          }
        });
        break;
      case 7: // leaveGame
        socket.emit("leaveGame", { gameId, playerId });
        break;
      case 8: // endGame
        socket.emit("endGame", { gameId });
        break;
    }

    if (i % TIMESTAMP_INTERVAL === 0) {
      logTimestamp(`Game event triggered: action ${i + 1}`);
    }

    // Slight delay to avoid overloading the WebSocket server
    await new Promise(resolve => setTimeout(resolve, 100));
  }

  logTimestamp("All game events triggered");
  console.log("All game events triggered");
}

// Step 3: Close all Socket.IO connections
async function closeSockets() {
  logTimestamp("Closing Socket.IO connections");
  console.log(`Step 3 :\nClosing ${gameData.length} Socket.IO connections`);

  for (const { socket } of gameData) {
    socket.disconnect();
  }

  logTimestamp("All Socket.IO connections closed");
  console.log("All Socket.IO connections closed");
}

// Step 4: Insert records (players, games, etc.)
async function insertRecords() {
  logTimestamp("Starting data insertion");
  console.log(`Step 4 :\nInserting ${NUM_ACTIONS} records`);

  for (let i = 0; i < NUM_ACTIONS; i++) {
    const gameId = `game_${i}`;
    const playerId = `player_${i}`;

    await session.run(`
      CREATE (g:Game {id: $gameId, name: "Benchmark Game ${i}"})
      CREATE (p:Player {id: $playerId, name: "Benchmark Player ${i}"})-[:PART_OF]->(g)
    `, { gameId, playerId });

    if (i % TIMESTAMP_INTERVAL === 0) {
      logTimestamp(`Inserted game and player: ${i + 1}`);
    }
  }

  logTimestamp("All data inserted");
  console.log("All data inserted");
}

// Step 5: Modify records (hypotheses, moves, accusations)
async function modifyRecords() {
  logTimestamp("Starting data modification");
  console.log(`Step 5 :\nModifying ${NUM_ACTIONS} records`);

  for (let i = 0; i < NUM_ACTIONS; i++) {
    const playerId = `player_${i}`;

    await session.run(`
      MATCH (p:Player {id: $playerId})-[:PART_OF]->(g:Game)
      SET p.hypothesis = "Modified Hypothesis", p.move = "Modified Move", p.accusation = "Modified Accusation"
    `, { playerId });

    if (i % TIMESTAMP_INTERVAL === 0) {
      logTimestamp(`Modified player: ${i + 1}`);
    }
  }

  logTimestamp("All data modified");
  console.log("All data modified");
}

// Step 6: Delete records (not global deletion, record-by-record)
async function deleteRecords() {
  logTimestamp("Starting data deletion");
  console.log(`Step 6 :\nDeleting ${NUM_ACTIONS} records`);

  for (let i = 0; i < NUM_ACTIONS; i++) {
    const playerId = `player_${i}`;
    const gameId = `game_${i}`;

    await session.run(`
      MATCH (p:Player {id: $playerId})-[:PART_OF]->(g:Game {id: $gameId})
      DETACH DELETE p, g
    `, { playerId, gameId });

    if (i % TIMESTAMP_INTERVAL === 0) {
      logTimestamp(`Deleted player and game: ${i + 1}`);
    }
  }

  logTimestamp("All data deleted");
  console.log("All data deleted");
}

// Step 7: Run benchmark
async function runBenchmark() {
  // Clean up any previous benchmark logs
  fs.writeFileSync(timestampFile, "Benchmark Timestamps\n");
  console.log("Benchmark started");

  // 1. Create Socket.IO connections and games
  await createSockets();

  // 2. Trigger game events
  await triggerGameEvents();

  // 3. Close WebSockets
  await closeSockets();

  // 4. Insert 1 million records
  await insertRecords();

  // 5. Modify 1 million records
  await modifyRecords();

  // 6. Delete all inserted records
  await deleteRecords();

  logTimestamp("Benchmark completed");
  console.log("Benchmark completed");
  await session.close();
  await driver.close();
}

// Run the benchmark
runBenchmark().catch(err => {
  console.error("Error running benchmark :", err);
});
