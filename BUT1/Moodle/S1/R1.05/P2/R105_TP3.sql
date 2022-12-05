/*
Schéma relationnel
------------------
Enseignant(nomEns (1), prenomEns, adresse, statut)  
Cycle(num (1), enseignantResponsable = @Enseignant.nomEns (UQ)(NN))
Cours(nomCours (1), volumeH, lEnseignant = @Enseignant.nomEns (NN), leCycle = @Cycle.num (NN)) 
Requiert([cours = @Cours.nomCours, coursRequis = @Cours.nomCours](1))
---------------------
*/

-- Q1: charger et exécuter le script donné sur Moodle

-- Q2: des insertions (au choix)
INSERT INTO Enseignant VALUES ('Pham', 'Minh-Tan', 'Vannes', 'EC');
INSERT INTO Enseignant VALUES ('Ridard', 'Anthony', 'Vannes', 'PRAG');
INSERT INTO Enseignant VALUES ('Berg', 'Paul', NULL, 'DOC');
INSERT INTO Enseignant VALUES ('Fleurquin', 'Regis', NULL, 'EC');

INSERT INTO Cycle VALUES (1, 'Fleurquin');
INSERT INTO Cycle VALUES (2, 'Pham');

INSERT INTO Cours VALUES ('BDD1', 24, 'Fleurquin', 1);
INSERT INTO Cours VALUES ('BDD2', 21, 'Pham', 1);
INSERT INTO Cours VALUES ('MATH', 21, 'Ridard', 2);

INSERT INTO Requiert VALUES ('BDD1', 'BDD2');
INSERT INTO Requiert VALUES ('BDD2', 'MATH');


/*
Pour Live SQL --> Faire SELECT * FROM ... pour visualiser des données 
*/


-- Q3: vidage des tables pour que le script soit bien réexécutable 
--------------------------------------------------------------

DELETE FROM Requiert ;
DELETE FROM Cours ;
DELETE FROM Cycle ;
DELETE FROM Enseignant ;


-- Q4:
-------------------------------------------------

-- test de l'existence de la clé
INSERT INTO Enseignant VALUES (NULL, 'Anthony', 'Vannes', 'PRAG');

-- test de l'unicité de la clé
INSERT INTO Enseignant VALUES ('Pham', 'Alex', NULL, 'PRAG');

-- test d’intégrité la clé étrangère de la table Cycle (on insère un Prof qui n’est pas dans la table Enseignant)
INSERT INTO Cycles VALUES (5, ‘Kerbellec’); 

-- test de l'existence de la clé étrangère de la table Cycle

INSERT INTO Cycle VALUES (3, NULL);

-- test de l'unicité de la clé étrangère de la table Cycle

INSERT INTO Cycle  VALUES (3, 'Fleurquin');



-- Q5:
----------------------------

-- a) on ajoute le CHECK (cela évite de modifier et réexécuter le script de création de tables)

ALTER TABLE Cours
	ADD CONSTRAINT ck_volumeH_positif CHECK(volumeH > 0);

-- b) commande pour tester
	
INSERT INTO Cours VALUES ('SQL', 0, 'Pham', 1);

-- c) on supprime le CHECK pour revenir à l'état initial

ALTER TABLE Cours
	DROP CONSTRAINT ck_volumeH_positif;


-- Q6:
-- Ajouter une colonne dans la table Enseignant
ALTER TABLE Enseignant
	ADD age NUMBER;


-- Supprimer la colonne ajoutée
ALTER TABLE Enseignant
	DROP COLUMN age;


-- Q7:
----------------------------
DELETE FROM Requiert
WHERE cours = 'BDD1'; -- 1 row(s) deleted.

DELETE FROM Enseignant
WHERE prenomEns = 'Minh-Tan'; -- ORA-02292: integrity constraint ? Pourquoi? 

UPDATE Cours
SET volumeH = 30
WHERE nomCours = 'BDD2'; -- 1 row(s) updated.




