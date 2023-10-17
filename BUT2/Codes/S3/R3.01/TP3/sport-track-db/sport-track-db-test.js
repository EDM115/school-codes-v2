const fs = require('fs');

var db = require('./sport-track-db').db;
var user_dao = require('./sport-track-db').user_dao;
var activity_dao = require('./sport-track-db').activity_dao;
var activity_entry_dao = require('./sport-track-db').activity_entry_dao;

let rawData = fs.readFileSync('test.json');
let testData = JSON.parse(rawData);

function convertDate(date) {
    let parts = date.split("/");
    return `${parts[2]}-${parts[1]}-${parts[0]}`;
}

var user = [
    "test@test.com", "NomTest", "PrenomTest", "1990-01-01", 1, 175, 70, "Password123!"
];
user_dao.insert(user, function(err) {
    if (err) {
        console.error("Erreur lors de l'insertion:", err.message);
    } else {
        console.log("Insertion réussie.");

        var updatedUser = ["UpdatedNom", "UpdatedPrenom", "1992-02-02", 0, 170, 65, "Updated123!"];
        user_dao.update("test@test.com", updatedUser, function(err) {
            if (err) {
                console.error("Erreur lors de la mise à jour:", err.message);
            } else {
                console.log("Mise à jour réussie.");

                user_dao.findAll(function(err, users) {
                    if (err) {
                        console.error("Erreur lors de la consultation:", err.message);
                    } else {
                        console.log("Utilisateurs trouvés:");
                        users.forEach(user => {
                            console.log(user);
                        });

                        user_dao.delete("test@test.com", function(err) {
                            if (err) {
                                console.error("Erreur lors de la suppression:", err.message);
                            } else {
                                console.log("Suppression de l'utilisateur réussie.");
                            }
                            db.close();
                        });
                    }
                });
            }
        });
    }
});

var activity = [convertDate(testData.activity.date), testData.activity.description, null, null, null, null, null, "test@test.com"];
activity_dao.insert(activity, function(err) {
    if (err) {
        console.error("Erreur lors de l'insertion de l'activité:", err.message);
        return;
    }
    console.log("Insertion de l'activité réussie.");

    activity_dao.findByUser("test@test.com", function(err, activities) {
        if (err) {
            console.error("Erreur lors de la recherche de l'activité:", err.message);
            return;
        }

        var activityId = activities[0].id_activity;

        testData.data.forEach(data => {
            var entry = [data.time, data.cardio_frequency, data.altitude, data.latitude, data.longitude, activityId];
            activity_entry_dao.insert(entry, function(err) {
                if (err) {
                    console.error("Erreur lors de l'insertion de l'entrée d'activité:", err.message);
                } else {
                    console.log("Insertion de l'entrée d'activité réussie.");
                }
            });
        });

        activity_entry_dao.findByActivity(activityId, function(err, entries) {
            if (err) {
                console.error("Erreur lors de la recherche des entrées d'activité:", err.message);
            } else {
                console.log("Entrées d'activité trouvées:");
                entries.forEach(entry => {
                    console.log(entry);
                });
            }
        });

        activity_dao.findByKey(activityId, function(err, activity) {
            if (err) {
                console.error("Erreur lors de la recherche de l'activité:", err.message);
            } else {
                console.log("Activité trouvée:");
                console.log(activity);
            }
        });

        activity_entry_dao.delete(activityId, function(err) {
            if (err) {
                console.error("Erreur lors de la suppression des entrées d'activité:", err.message);
            } else {
                console.log("Suppression des entrées d'activité réussie.");
            }
        });

        activity_dao.delete(activityId, function(err) {
            if (err) {
                console.error("Erreur lors de la suppression de l'activité:", err.message);
            } else {
                console.log("Suppression de l'activité réussie.");
            }
        });
    });
});
