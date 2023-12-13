-----------------------------------------------------
-- On active l'affichage pour DBMS_OUTPUT.PUT_LINE --
-----------------------------------------------------

SET SERVEROUTPUT ON;

--------------------------------------------------------------------------------------
-- Une agence a forcément un et un seul directeur --
--------------------------------------------------------------------------------------

-- Remarque : cette contrainte impose l'insertion du directeur en premier

-- On teste l'insertion dans la table Agent
INSERT INTO Agence(numAgence,telAgence,adAgence) VALUES (100, '0123456789', 'Issy');
INSERT INTO Agence(numAgence,telAgence,adAgence) VALUES (101, '0123456789', 'Lyon');

INSERT INTO Agent(numAgent, nomAgent, prenomAgent, salaire, estDirecteur, sonAgence) VALUES(100,'Chevet','Anatole',2000,1,1); -- Ne sera pas inséré (deuxième directeur);
INSERT INTO Agent(numAgent, nomAgent, prenomAgent, salaire, estDirecteur, sonAgence) VALUES(200,'Fouquet','Damien',2000,0,4);
INSERT INTO Agent(numAgent, nomAgent, prenomAgent, salaire, estDirecteur, sonAgence) VALUES(300,'Faria','Jean',2000,0,4);
INSERT INTO Agent(numAgent, nomAgent, prenomAgent, salaire, estDirecteur, sonAgence) VALUES(400,'Charles','Michèle',4000,1,4); -- Ne sera pas inséré (deuxième directeur);
INSERT INTO Agent(numAgent, nomAgent, prenomAgent, salaire, estDirecteur, sonAgence) VALUES(500,'De Santa','Michel',2000,0,100); -- Ne sera pas inséré (pas de directeur);
INSERT INTO Agent(numAgent, nomAgent, prenomAgent, salaire, estDirecteur, sonAgence) VALUES(500,'De Santa','Michel',4000,1,101);
INSERT INTO Agent(numAgent, nomAgent, prenomAgent, salaire, estDirecteur, sonAgence) VALUES(600,'De Santa','Marcel',2000,0,101);

-- On teste la suppression dans la table Agent

DELETE FROM Agent WHERE numAgent = 200;
DELETE FROM Agent WHERE numAgent = 500; -- Ne sera pas supprimé (une agence sans directeur);

-- On teste la mise à jour dans la table Agent

UPDATE Agent SET sonAgence = 3 WHERE numAgent = 2;
UPDATE Agent SET estDirecteur = 0 WHERE numAgent = 500; -- Ne sera pas mis à jour (une agence sans directeur);
UPDATE Agent SET estDirecteur = 1 WHERE numAgent = 300; -- Ne sera pas mis à jour (deuxième directeur);


----------------------------------------------------------
-- Un employé ne peut pas gagner plus que son directeur --
----------------------------------------------------------

-- On teste la mise à jour dans la table Agent

UPDATE Agent SET salaire = 2100 WHERE numAgent = 5; -- Ne sera pas mis à jour;
UPDATE Agent SET salaire = salaire + 150 WHERE sonAgence = 3 AND estDirecteur = 0; -- Ne seront pas mis à jour ;

-- On souhaite maintenant observer le comportement du trigger avec INSERT ALL
-----------------------------------------------------------------------------

INSERT ALL
	INTO Agent(numAgent, nomAgent, prenomAgent, salaire, estDirecteur, sonAgence)     
	VALUES(36,'Belmouhcine','Abdelbadie',5000,0,1) -- Ne sera pas inséré (employé mieux payé que son directeur)
        INTO Agent(numAgent, nomAgent, prenomAgent, salaire, estDirecteur, sonAgence) 
        VALUES(45,'Le Brazidec','Albert',1900,0,1) 
	INTO Agent(numAgent, nomAgent, prenomAgent, salaire, estDirecteur, sonAgence)
	VALUES(50,'Benoit','Thome',6000,0,2) -- Ne sera pas inséré (employé mieux payé que son directeur)
SELECT * FROM DUAL; -- Rien n'est inséré ??
