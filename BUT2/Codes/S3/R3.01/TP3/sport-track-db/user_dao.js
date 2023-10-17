var db = require('./sqlite_connection');

var UserDAO = function() {
    this.insert = function(values, callback) {
        const sql = `
            INSERT INTO User(adresse_electronique, nom, prenom, date_naissance, sexe, taille, poids, mot_de_passe)
            VALUES(?, ?, ?, ?, ?, ?, ?, ?)
        `;
        db.run(sql, values, function(err) {
            callback(err);
        });
    };

    this.update = function(key, values, callback) {
        const sql = `
            UPDATE User 
            SET nom = ?, prenom = ?, date_naissance = ?, sexe = ?, taille = ?, poids = ?, mot_de_passe = ?
            WHERE adresse_electronique = ?
        `;
        values.push(key);
        console.log(values);
        db.run(sql, values, function(err) {
            callback(err);
        });
    };

    this.delete = function(key, callback) {
        const sql = 'DELETE FROM User WHERE adresse_electronique = ?';
        db.run(sql, key, function(err) {
            callback(err);
        });
    };

    this.findAll = function(callback) {
        const sql = 'SELECT * FROM User';
        db.all(sql, [], function(err, rows) {
            callback(err, rows);
        });
    };

    this.findByKey = function(key, callback) {
        const sql = 'SELECT * FROM User WHERE adresse_electronique = ?';
        db.get(sql, key, function(err, row) {
            callback(err, row);
        });
    };
};

var dao = new UserDAO();
module.exports = dao;
