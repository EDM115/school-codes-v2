-- Insérer une activité
INSERT INTO Activity(date, description, duree)
VALUES ('2022-09-01', 'IUT -> RU', 1);

-- Insérer les données pour l'activité précédente
INSERT INTO Data(heure_debut, freq_card, latitude, longitude, altitude, une_activite)
VALUES ('13:00:00', 99, 47.644795, -2.776605, 18, last_insert_rowid()),
       ('13:00:05', 100, 47.646870, -2.778911, 18, last_insert_rowid()),
       ('13:00:10', 102, 47.646197, -2.780220, 18, last_insert_rowid()),
       ('13:00:15', 100, 47.646992, -2.781068, 17, last_insert_rowid()),
       ('13:00:20', 98, 47.647867, -2.781744, 16, last_insert_rowid()),
       ('13:00:25', 103, 47.648510, -2.780145, 16, last_insert_rowid());
