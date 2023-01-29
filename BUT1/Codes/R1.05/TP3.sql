-- Question 2 --

DELETE FROM Requiert;
DELETE FROM Cours;
DELETE FROM Cycle;
DELETE FROM Enseignant;

INSERT INTO Enseignant VALUES ('Pham', 'Minh-Tan', 'Vietnam', 'Prof');
INSERT INTO Enseignant VALUES ('Adam', 'Michel', 'Prison', 'Condamné');
INSERT INTO Enseignant VALUES ('Kamp', 'Jean-François', 'Vannes', 'ex-Directeur');
INSERT INTO Enseignant VALUES ('Baudon', 'Pascal', 'Vannes', 'accro au NoCode');
INSERT INTO Enseignant VALUES ('Tonin', 'Philippe', 'Sud', 'Prof');

INSERT INTO Cycle VALUES (1, 'Kamp');
INSERT INTO Cycle VALUES (2, 'Baudon');
INSERT INTO Cycle VALUES (3, 'Pham');

INSERT INTO Cours VALUES ('BDD', 1.5, 'Pham', 1);
INSERT INTO Cours VALUES ('Anglais', 3, 'Tonin', 1);
INSERT INTO Cours VALUES ('Dev', 7, 'Kamp', 1);	
INSERT INTO Cours VALUES ('GPO', 3, 'Baudon', 2);
INSERT INTO Cours VALUES ('SQL', 3, 'Pham', 2);

INSERT INTO Requiert VALUES ('SQL', 'BDD');
INSERT INTO Requiert VALUES ('Dev', 'Anglais');

-- Question 3 --

/*
Vider les tables dans le bon ordre :
DELETE FROM Requiert;
DELETE FROM Cours;
DELETE FROM Cycle;
DELETE FROM Enseignant;
*/

-- Question 4 --

/*
a) 
INSERT INTO Enseignant VALUES (NULL, NULL, NULL, NULL);
                               *
ERROR at line 46:
ORA-01400: cannot insert NULL into ("EDM115"."ENSEIGNANT"."NOMENS")

b)
INSERT INTO Enseignant VALUES ('Pham', 'Minh-Tan', 'Vietnam', 'Prof');
INSERT INTO Enseignant VALUES ('Pham', 'Pareil', 'Test', 'Test');
*
ERROR at line 55:
ORA-00001: unique constraint (EDM115.PK_ENSEIGNANT) violated

c)
INSERT INTO Cycle VALUES (4, 'Michel');
*
ERROR at line 61:
ORA-02291: integrity constraint (EDM115.FK_CYCLE_ENSEIGNANT) violated - parent

INSERT INTO Cycle VALUES (5, 'Tonin');
INSERT INTO Cycle VALUES (5, 'Tonin');
*
ERROR at line 67:
ORA-00001: unique constraint (EDM115.PK_CYCLE) violated
*/

-- Question 5 --

-- c)
ALTER TABLE Cours DROP CONSTRAINT ck_volumeH;

-- a)
ALTER TABLE Cours ADD CONSTRAINT ck_volumeH CHECK (volumeH > 0);

/*
b)
INSERT INTO Cours VALUES ('Test', -1, 'Pham', 1);
*
ERROR at line 77:
ORA-02290: check constraint (EDM115.CK_VOLUMEH) violated
*/

-- Question 6 --

ALTER TABLE Enseignant ADD Matiere VARCHAR2(20);
INSERT INTO Enseignant (nomEns, Matiere) VALUES ('test', 'Test de 20 caractère');
ALTER TABLE Enseignant MODIFY Matiere VARCHAR2(50);
ALTER TABLE Enseignant DROP COLUMN Matiere;

-- Question 7 --

UPDATE Enseignant SET Matiere = 'Un nom de matière plus long que 20' WHERE (Matiere = 'Test de 20 caractère');
DELETE FROM Enseignant WHERE (nomEns != '');