var db = require('./sport-track-db').db;

function clearTable(tableName, callback) {
    db.run(`DELETE FROM ${tableName}`, [], function(err) {
        if (err) {
            console.error(`Erreur lors de la suppression des données de la table ${tableName}:`, err.message);
            callback(err);
        } else {
            console.log(`Toutes les entrées de la table ${tableName} ont été supprimées.`);
            callback(null);
        }
    });
}

clearTable('Data', function(err) {
    if (!err) {
        clearTable('Activity', function(err) {
            if (!err) {
                clearTable('User', function(err) {
                    if (!err) {
                        console.log("La base de données a été nettoyée avec succès.");
                        db.close();
                    }
                });
            }
        });
    }
});
