var express = require("express");
var router = express.Router();
var multer  = require("multer");
var storage = multer.memoryStorage();
var upload = multer({ storage: storage });
var activity_dao = require("sport-track-db").activity_dao;
var activity_entry_dao = require("sport-track-db").activity_entry_dao;
var user_dao = require("sport-track-db").user_dao;

router.get("/upload", function(req, res, next) {
    if (req.session.user == undefined || req.session.user == null) {
        return res.render("login", { error: "Non connecté", title: "Ajouter un fichier d'activité - Erreur" });
    }
    user_dao.findByKey(req.session.user[0], function(err, user) {
        if (user) {
            return res.render("upload", { title: "Ajouter un fichier d'activité" });
        } else {
            return res.render("login", { error: "L'email n'existe pas", title: "Ajouter un fichier d'activité - Erreur" });
        }
    });
});

router.post("/upload", upload.single("file"), function(req, res) {
    if (!req.file) {
        return res.render("upload", { title: "Ajouter un fichier d'activité - Erreur", error: "Aucun fichier transmis" });
    }
    if (req.session.user == undefined || req.session.user == null) {
        return res.render("login", { error: "Non connecté", title: "Ajouter un fichier d'activité - Erreur" });
    }
    user_dao.findByKey(req.session.user[0], function(err, user) {
        if (user) {
            var jsonData;
            try {
                jsonData = JSON.parse(req.file.buffer.toString("utf8"));
            } catch (err) {
                return res.render("upload", { title: "Ajouter un fichier d'activité - Erreur", error: "Format JSON invalide" });
            }
        
            var activityData = jsonData.activity;
            if (activityData.date == undefined || activityData.description == undefined || activityData.date == '' || activityData.description == '') {
                return res.render("upload", { title: "Ajouter un fichier d'activité - Erreur", error: "Données d'activité invalides" });
            }
            var dateRegex = /^\d{2}\/\d{2}\/\d{4}$/;
            if (!dateRegex.test(activityData.date)) {
                return res.render("upload", { title: "Ajouter un fichier d'activité - Erreur", error: "Format de date invalide" });
            }
        
            var date = activityData.date.split('/').reverse().join('-');
        
            activity_dao.count(function(err, count) {
                if (err) {
                    console.Erreur(err);
                    return res.render("upload", { title: "Ajouter un fichier d'activité - Erreur", error: "Erreur durant le comptage des activités" });
                }
        
                var activityId = count["COUNT(*)"] + 1;
        
                activity_dao.insert([activityId, date, activityData.description, 1, 1, 1, 1, 1, req.session.user[0]], async function(err) {
                    if (err) {
                        return res.render("upload", { title: "Ajouter un fichier d'activité - Erreur", error: "Erreur durant l'insertion des données" });
                    }
        
                    var allData = jsonData.data;
                    for (const [index, data] of allData.entries()) {
                        if (data.time == undefined || data.cardio_frequency == undefined || data.altitude == undefined || data.latitude == undefined || data.longitude == undefined || data.time == '' || data.cardio_frequency == '' || data.altitude == '' || data.latitude == '' || data.longitude == '') {
                            return res.render("upload", { title: "Ajouter un fichier d'activité - Erreur", error: "Données invalides" });
                        }
                        var timeRegex = /^\d{2}:\d{2}:\d{2}$/;
                        if (!timeRegex.test(data.time)) {
                            return res.render("upload", { title: "Ajouter un fichier d'activité - Erreur", error: "Format de temps invalide" });
                        }
        
                        await new Promise((resolve, reject) => {
                            activity_entry_dao.count(function(err, entryCount) {
                                if (err) {
                                    console.Erreur(err);
                                    return res.render("upload", { title: "Ajouter un fichier d'activité - Erreur", error: "Erreur durant le comptage des données" });
                                }
                                
                                var activityEntryId = entryCount["COUNT(*)"] + 1;
        
                                activity_entry_dao.insert([activityEntryId, data.time, data.cardio_frequency, data.altitude, data.latitude, data.longitude, activityId], function(err) {
                                    if (err) {
                                        reject();
                                        return res.render("upload", { title: "Ajouter un fichier d'activité - Erreur", error: "Erreur durant l'insertion des données" });
                                    }
        
                                    if (index === allData.length - 1) {
                                        resolve();
                                        var newData = JSON.stringify(jsonData);
                                        res.render("data_uploaded", { title: "Fichier d'activité ajouté", data: newData });
                                    } else {
                                        resolve();
                                    }
                                });
                            });
                        });
                    }
                });
            });
        } else {
            return res.render("login", { error: "L'email n'existe pas", title: "Ajouter un fichier d'activité - Erreur" });
        }
    });
});

router.get("/activities", function(req, res, next) {
    if (req.session.user == undefined || req.session.user == null) {
        return res.render("login", { error: "Non connecté", title: "Liste des activités - Erreur" });
    }
    activity_dao.findByUser(req.session.user[0], function(err, activities) {
        if (err) {
            console.Erreur(err);
            return res.render("activities", { error: "Erreur durant la récupération des activités", title: "Liste des activités - Erreur" });
        }
        if (activities) {
            activity_entry_dao.findAll(function(err, data) {
                if (err) {
                    console.Erreur(err);
                    return res.render("activities", { error: "Erreur durant la récupération des données", title: "Liste des activités - Erreur" });
                }
                return res.render("activities", {
                    title: "Liste des activités",
                    activities: activities,
                    data: data
                });
            });
        } else {
            return res.render("login", { error: "L'email n'existe pas", title: "Liste des activités - Erreur" });
        }
    });
});

module.exports = router;
