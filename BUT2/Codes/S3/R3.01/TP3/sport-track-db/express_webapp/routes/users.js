var express = require("express");
var router = express.Router();
var user_dao = require("sport-track-db").user_dao;

router.get("/create", function(req, res, next) {
    res.render("create", { title: "Ajouter un utilisateur" });
});

router.post("/create", function(req, res, next) {
    var userData = [
        req.body.adresse_electronique, req.body.nom, req.body.prenom, req.body.date_naissance, req.body.sexe, req.body.taille, req.body.poids, req.body.mot_de_passe
    ];
    user_dao.findByKey(req.body.adresse_electronique, function(err, user) {
        if (user) {
            return res.render("create", { error: "L'email existe déjà", title: "Ajouter un utilisateur - Erreur" });
        }
        user_dao.insert(userData, function(err) {
            if (err) {
                return next(err);
            }
            if (req.body.sexe == 0) {
                req.body.sexe = "Homme";
            } else {
                req.body.sexe = "Femme";
            }
            res.render("user_created", { email: req.body.adresse_electronique, nom: req.body.nom, prenom: req.body.prenom, date_naissance: req.body.date_naissance, sexe: req.body.sexe, taille: req.body.taille, poids: req.body.poids, mot_de_passe: req.body.mot_de_passe, title: "Utilisateur ajouté" });
        });
    });
});

router.get("/update", function(req, res, next) {
    if (req.session.user == undefined || req.session.user == null) {
        return res.render("login", { error: "Non connecté", title: "Éditer un utilisateur - Erreur" });
    }
    user_dao.findByKey(req.session.user[0], function(err, user) {
        if (user) {
            return res.render("update", { user: req.session.user, title: "Éditer un utilisateur" });
        } else {
            return res.render("login", { error: "L'email n'existe pas", title: "Éditer un utilisateur - Erreur" });
        }
    });
});

router.post("/update", function(req, res, next) {
    var updatedData = [
        req.body.nom, req.body.prenom, req.body.date_naissance, req.body.sexe, req.body.taille, req.body.poids, req.body.mot_de_passe
    ];
    user_dao.findByKey(req.body.adresse_electronique, function(err, user) {
        if (!user) {
            return res.render("update", { error: "L'utilisateur n'existe pas", title: "Éditer un utilisateur - Erreur" });
        }
    });
    user_dao.update(req.body.adresse_electronique, updatedData, function(err) {
        if (err) {
            return next(err);
        }
        try {
            if (req.body.sexe == 0) {
                req.body.sexe = "Homme";
            } else {
                req.body.sexe = "Femme";
            }
            res.render("user_updated", { email: req.body.adresse_electronique, nom: req.body.nom, prenom: req.body.prenom, date_naissance: req.body.date_naissance, sexe: req.body.sexe, taille: req.body.taille, poids: req.body.poids, mot_de_passe: req.body.mot_de_passe, title: "Utilisateur édité" });
        } catch (error) {
            if (error.code === "ERR_HTTP_HEADERS_SENT") {
                console.log("Headers already sent");
            } else {
                console.log("Error : " + error);
                console.log(error.stack);
            }
        }
    });
});

router.get("/login", function(req, res, next) {
    res.render("login", { title: "Connexion" });
});

router.post("/login", function(req, res, next) {
    user_dao.findByKey(req.body.email, function(err, user) {
        if (err) {
            return next(err);
        }
        if (!user) {
            return res.render("login", { error: "Email incorrect", title: "Connexion - Erreur" });
        }
        if (req.body.password !== user.mot_de_passe) {
            return res.render("login", { error: "Mot de passe incorrect", title: "Connexion - Erreur" });
        }
        userArray = [user.adresse_electronique, user.nom, user.prenom, user.date_naissance, user.sexe, user.taille, user.poids, user.mot_de_passe];
        req.session.user = userArray;
        res.render("user_connected", { user: req.session.user, title: "Utilisateur connecté" });
    });
});

router.get("/logout", function(req, res, next) {
    req.session.user = null;
    res.render("logout", { title: "Utilisateur déconnecté" });
});

module.exports = router;
