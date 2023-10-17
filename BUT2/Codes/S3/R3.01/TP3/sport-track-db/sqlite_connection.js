const sqlite3 = require('sqlite3').verbose();

let db = new sqlite3.Database('./sport_track.db', (err) => {
    if (err) {
        console.error('Erreur lors de la connexion à la base de données :', err.message);
        process.exit(1);
    }
    console.log('Connecté à la base de données sport_track.db');
});

module.exports = db;
