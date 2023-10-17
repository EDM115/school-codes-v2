var db = require('./sqlite_connection');
var cd = require('./calcul_distance');

function calculateDurationInMinutes(min_time, max_time) {
    var [minHours, minMinutes, minSeconds] = min_time.split(':').map(Number);
    var [maxHours, maxMinutes, maxSeconds] = max_time.split(':').map(Number);

    var minTimeMinutes = minHours * 60 + minMinutes;
    var maxTimeMinutes = maxHours * 60 + maxMinutes;
    var dureeMinutes;

    if (minTimeMinutes > maxTimeMinutes) {
        dureeMinutes = ((24 * 60) - minTimeMinutes) + maxTimeMinutes;
    } else {
        dureeMinutes = maxTimeMinutes - minTimeMinutes;
    }

    if (dureeMinutes === 0 && (maxSeconds - minSeconds) > 0) {
        dureeMinutes = 1;
    }
    
    return Math.max(dureeMinutes, 1);
}

var ActivityEntryDAO = function() {
    this.insert = function(values, callback) {
        var sqlFind = `SELECT * FROM Data WHERE une_activite = ? ORDER BY id_data ASC LIMIT 1`;
        db.get(sqlFind, [values[6]], function(err, previousData) {
            if (err) return callback(err);
    
            if (previousData) {
                var distance = cd.calculDistance2PointsGPS(
                    previousData.latitude, previousData.longitude,
                    values[4], values[5]
                );
    
                var sqlUpdateActivity = `UPDATE Activity SET distance = ? WHERE id_activity = ?`;
                db.run(sqlUpdateActivity, [distance, values[6]], function(err) {
                    if (err) return callback(err);
                });
            }
    
            var sqlInsert = `INSERT INTO Data(id_data, heure_debut, freq_card, altitude, latitude, longitude, une_activite) VALUES (?, ?, ?, ?, ?, ?, ?)`;
            
            db.run(sqlInsert, values, function(err) {
                if (err) return callback(err);
                
                var sqlDuration = `SELECT MIN(heure_debut) as min_time, MAX(heure_debut) as max_time FROM Data WHERE une_activite = ?`;
                db.get(sqlDuration, [values[6]], function(err, timeData) {
                    if (err) return callback(err);
                    
                    var duree = calculateDurationInMinutes(timeData.min_time, timeData.max_time);
            
                    var sqlUpdateActivity = `UPDATE Activity SET duree = ? WHERE id_activity = ?`;
                    db.run(sqlUpdateActivity, [duree, values[6]], callback);
                });
            });
        });
    };    

    this.update = function(key, values, callback) {
        var sqlFind = `SELECT * FROM Data WHERE une_activite = ? AND id_data != ? ORDER BY id_data ASC LIMIT 1`;
        db.get(sqlFind, [values[6], key], function(err, previousData) {
            if (err) return callback(err);
    
            if (previousData) {
                var distance = cd.calculDistance2PointsGPS(
                    previousData.latitude, previousData.longitude,
                    values[4], values[5]
                );
    
                var sqlUpdateActivity = `UPDATE Activity SET distance = ? WHERE id_activity = ?`;
                db.run(sqlUpdateActivity, [distance, values[6]], function(err) {
                    if (err) return callback(err);
                });
            }
    
            var sqlUpdate = `UPDATE Data SET heure_debut = ?, freq_card = ?, altitude = ?, latitude = ?, longitude = ?, une_activite = ? WHERE id_data = ?`;
            values.push(key);
            
            db.run(sqlUpdate, values, function(err) {
                if (err) return callback(err);
                
                var sqlDuration = `SELECT MIN(heure_debut) as min_time, MAX(heure_debut) as max_time FROM Data WHERE une_activite = ?`;
                db.get(sqlDuration, [values[6]], function(err, timeData) {
                    if (err) return callback(err);
                    
                    var duree = calculateDurationInMinutes(timeData.min_time, timeData.max_time);
                    
                    var sqlUpdateActivityDuration = `UPDATE Activity SET duree = ? WHERE id_activity = ?`;
                    db.run(sqlUpdateActivityDuration, [duree, values[6]], callback);
                });
            });
        });
    };
    

    this.delete = function(key, callback) {
        var sql = `DELETE FROM Data WHERE id_data = ?`;
        db.run(sql, [key], callback);
    };

    this.findAll = function(callback) {
        var sql = `SELECT * FROM Data`;
        db.all(sql, [], callback);
    };

    this.findByActivity = function(activityId, callback) {
        var sql = `SELECT * FROM Data WHERE une_activite = ?`;
        db.all(sql, [activityId], callback);
    };

    this.count = function(callback) {
        var sql = `SELECT COUNT(*) FROM Data`;
        db.get(sql, [], callback);
    };
};

var dao = new ActivityEntryDAO();
module.exports = dao;
