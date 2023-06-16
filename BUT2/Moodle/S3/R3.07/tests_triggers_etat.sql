-----------------------------------------------------
-- On active l'affichage pour DBMS_OUTPUT.PUT_LINE --
-----------------------------------------------------

SET SERVEROUTPUT ON


--------------------------------------------------------------------------------------
-- Une agence a forcément un et un seul directeur (dès qu'elle a au moins un agent) --
--------------------------------------------------------------------------------------

-- Remarque : cette contrainte impose l'insertion du directeur en premier

-- On vide les tables

DELETE FROM Appartient;
DELETE FROM Operation;
DELETE FROM Compte;
DELETE FROM Client;
DELETE FROM Agent;
DELETE FROM Agence;

-- On insère trois agences
	
INSERT INTO Agence VALUES(1,'01 00 00 00 01','adresse1');
INSERT INTO Agence VALUES(2,'01 00 00 00 02','adresse2');
INSERT INTO Agence VALUES(3,'01 00 00 00 03','adresse3');

-- On teste l'insertion dans la table Agent

INSERT INTO Agent VALUES(1,'nomAgent1','prenomAgent1',2000,1,1);
INSERT INTO Agent VALUES(2,'nomAgent2','prenomAgent2',2000,1,2);
INSERT INTO Agent VALUES(3,'nomAgent3','prenomAgent3',2000,1,1); -- Ne sera pas inséré (deuxième directeur)
INSERT INTO Agent VALUES(4,'nomAgent4','prenomAgent4',2000,0,1);
INSERT INTO Agent VALUES(5,'nomAgent5','prenomAgent5',2000,1,2); -- Ne sera pas inséré (deuxième directeur)
INSERT INTO Agent VALUES(6,'nomAgent6','prenomAgent6',2000,0,2);

-- On teste la suppression dans la table Agent

DELETE FROM Agent WHERE numAgent = 6;
DELETE FROM Agent WHERE numAgent = 1; -- Ne sera pas supprimé (une agence sans directeur)

-- On teste la mise à jour dans la table Agent

UPDATE Agent SET sonAgence = 3 WHERE numAgent = 2;
UPDATE Agent SET estDirecteur = 0 WHERE numAgent = 2; -- Ne sera pas mis à jour (une agence sans directeur)
UPDATE Agent SET estDirecteur = 1 WHERE numAgent = 4; -- Ne sera pas mis à jour (deuxième directeur)

-- On souhaite maintenant observer le comportement du trigger avec INSERT ALL
-----------------------------------------------------------------------------

-- On vide à nouveau les tables

DELETE FROM Appartient;
DELETE FROM Operation;
DELETE FROM Compte;
DELETE FROM Client;
DELETE FROM Agent;
DELETE FROM Agence;

-- On insère à nouveau trois agences

INSERT INTO Agence VALUES(1,'01 00 00 00 01','adresse1');
INSERT INTO Agence VALUES(2,'01 00 00 00 02','adresse2');
INSERT INTO Agence VALUES(3,'01 00 00 00 03','adresse3');

-- On teste l'insertion dans la table Agent mais avec INSERT ALL cette fois

INSERT ALL
	INTO Agent VALUES(1,'nomAgent1','prenomAgent1',2000,1,1)
	INTO Agent VALUES(2,'nomAgent2','prenomAgent2',2000,1,2)
	INTO Agent VALUES(3,'nomAgent3','prenomAgent3',2000,1,1) -- Ne sera pas inséré (deuxième directeur)
	INTO Agent VALUES(4,'nomAgent4','prenomAgent4',2000,0,1)
	INTO Agent VALUES(5,'nomAgent5','prenomAgent5',2000,1,2) -- Ne sera pas inséré (deuxième directeur)
	INTO Agent VALUES(6,'nomAgent6','prenomAgent6',2000,0,2)
SELECT * FROM DUAL;


----------------------------------------------------------
-- Un employé ne peut pas gagner plus que son directeur --
----------------------------------------------------------

-- On vide les tables

DELETE FROM Agent;
DELETE FROM Agence;

-- On insère trois agences
	
INSERT INTO Agence VALUES(1,'01 00 00 00 01','adresse1');
INSERT INTO Agence VALUES(2,'01 00 00 00 02','adresse2');
INSERT INTO Agence VALUES(3,'01 00 00 00 03','adresse3');

-- On teste l'insertion dans la table Agent

INSERT INTO Agent VALUES(1,'nomAgent1','prenomAgent1',2000,1,1);
INSERT INTO Agent VALUES(2,'nomAgent2','prenomAgent2',2200,1,2);
INSERT INTO Agent VALUES(3,'nomAgent3','prenomAgent3',2100,0,1); -- Ne sera pas inséré (employé mieux payé que son directeur)
INSERT INTO Agent VALUES(4,'nomAgent4','prenomAgent4',1900,0,1);
INSERT INTO Agent VALUES(5,'nomAgent5','prenomAgent5',2300,0,2); -- Ne sera pas inséré (employé mieux payé que son directeur)
INSERT INTO Agent VALUES(6,'nomAgent6','prenomAgent6',2100,0,2);
INSERT INTO Agent VALUES(7,'nomAgent7','prenomAgent7',2000,0,3); -- Ne sera pas inséré (pas de directeur)

-- On teste la mise à jour dans la table Agent

UPDATE Agent SET salaire = 2000 WHERE numAgent = 2; -- Ne sera pas mis à jour
UPDATE Agent SET salaire = salaire + 150 WHERE sonAgence = 1 AND estDirecteur = 0; -- Ne seront pas mis à jour 

-- On souhaite maintenant observer le comportement du trigger avec INSERT ALL
-----------------------------------------------------------------------------

-- On vide à nouveau les tables

DELETE FROM Agent;
DELETE FROM Agence;

-- On insère trois agences
	
INSERT INTO Agence VALUES(1,'01 00 00 00 01','adresse1');
INSERT INTO Agence VALUES(2,'01 00 00 00 02','adresse2');
INSERT INTO Agence VALUES(3,'01 00 00 00 03','adresse3');

-- On teste l'ordre d'éxécution des triggers

INSERT ALL
	INTO Agent VALUES(1,'nomAgent1','prenomAgent1',2000,1,1)
	INTO Agent VALUES(2,'nomAgent2','prenomAgent2',2200,1,2)
	INTO Agent VALUES(3,'nomAgent3','prenomAgent3',2100,0,1) -- Ne sera pas inséré (employé mieux payé que son directeur)
	INTO Agent VALUES(4,'nomAgent4','prenomAgent4',1900,0,1)
	INTO Agent VALUES(5,'nomAgent5','prenomAgent5',2300,0,2) -- Ne sera pas inséré (employé mieux payé que son directeur)
	INTO Agent VALUES(6,'nomAgent6','prenomAgent6',2100,0,2)
	INTO Agent VALUES(7,'nomAgent7','prenomAgent7',2000,0,3) -- Ne sera pas inséré (pas de directeur)
SELECT * FROM DUAL;

-- On teste l'insertion dans la table Agent

INSERT ALL
	INTO Agent VALUES(1,'nomAgent1','prenomAgent1',2000,1,1)
	INTO Agent VALUES(2,'nomAgent2','prenomAgent2',2200,1,2)
	INTO Agent VALUES(3,'nomAgent3','prenomAgent3',2100,0,1) -- Ne sera pas inséré (employé mieux payé que son directeur)
	INTO Agent VALUES(4,'nomAgent4','prenomAgent4',1900,0,1)
	INTO Agent VALUES(5,'nomAgent5','prenomAgent5',2300,0,2) -- Ne sera pas inséré (employé mieux payé que son directeur)
	INTO Agent VALUES(6,'nomAgent6','prenomAgent6',2100,0,2)
	INTO Agent VALUES(7,'nomAgent7','prenomAgent7',2000,1,3)
SELECT * FROM DUAL;