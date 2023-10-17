var db = require('./sqlite_connection');

var ActivityDAO = function() {
    this.insert = function(values, callback) {
        var sql = `INSERT INTO Activity(id_activity, date, description, duree, distance, freq_card_min, freq_card_max, freq_card_moy, un_user) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)`;
        db.run(sql, values, callback);
    };

    this.update = function(key, values, callback) {
        var sql = `UPDATE Activity SET date = ?, description = ?, duree = ?, distance = ?, freq_card_min = ?, freq_card_max = ?, freq_card_moy = ? WHERE id_activity = ?`;
        values.push(key);
        db.run(sql, values, callback);
    };

    this.delete = function(key, callback) {
        var sql = `DELETE FROM Activity WHERE id_activity = ?`;
        db.run(sql, [key], callback);
    };

    this.findAll = function(callback) {
        var sql = `SELECT * FROM Activity`;
        db.all(sql, [], callback);
    };

    this.findByKey = function(key, callback) {
        var sql = `SELECT * FROM Activity WHERE id_activity = ?`;
        db.get(sql, [key], callback);
    };

    this.findByUser = function(userEmail, callback) {
        var sql = `SELECT * FROM Activity WHERE un_user = ?`;
        db.all(sql, [userEmail], callback);
    };

    this.count = function(callback) {
        var sql = `SELECT COUNT(*) FROM Activity`;
        db.get(sql, [], callback);
    };
};

var dao = new ActivityDAO();
module.exports = dao;
