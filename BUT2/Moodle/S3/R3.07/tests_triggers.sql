SET SERVEROUTPUT ON

-- Liste des Triggers
SELECT TRIGGER_NAME FROM user_triggers;

-- Mise à jour des directeurs dans la table Agence
--------------------------------------------------
-- L'agent 5 devient directeur de l'agence 3, ce qui ne pose pas de problème
UPDATE Agence SET sonDirecteur = 5 WHERE numAgence = 3;

-- L'agent 8 devient directeur d'une nouvelle agence
INSERT INTO AGENCE(sonDirecteur) VALUES (8);


-----------------------------------------------------------------
-- Un directeur travaille forcément dans l'agence qu'il dirige --
-----------------------------------------------------------------


-- Modification d'un directeur dans la table Agent
---------------------------------------------------

-- L'agence 7 est travaille maintenant dans l'agence 1 ce qui ne pose pas de problème

UPDATE Agent SET sonAgence = 1 WHERE numAgent=7; 

-- Pas de modification, l'agent 5 est directeur de l'agence 3

UPDATE Agent SET sonAgence = 1 WHERE numAgent=5;


---------------------------------------------------------------------------
-- Le directeur d'une agence est mieux payé que les agents de son agence --
---------------------------------------------------------------------------


-- Modification d’un directeur dans la table Agence
---------------------------------------------------

-- L'agent 6 n'est pas le mieux payer dans l'agence 3

UPDATE Agence SET sonDirecteur = 6 WHERE numAgence = 3;


-- Modification du salaire d’un directeur ou d’un employé dans la table Agent
-----------------------------------------------------------------------------

INSERT INTO AGENT VALUES (12, NULL, NULL, 4000, 1);
INSERT INTO AGENT VALUES (13, NULL, NULL, 3900, 1);

-- L'agent 12 qui travaille dans l'agence 1 gagne maintenant 4200 euros alors que le directeur gagne 4000 euros

UPDATE Agent SET salaire = 4200 WHERE numAgent = 12;
	
-- Les agents 12 et 13 sont augmentés de 10%, gagnant ainsi plus que le directeur

UPDATE Agent SET salaire = salaire * 1.1 WHERE numAgent IN (12,13);
    
--Test de Appartient
-----------------------------------------------------------------------------------------------------------------
INSERT INTO Appartient(unClient, unCompte) VALUES(1,8);
INSERT INTO Appartient(unClient, unCompte) VALUES(2,20);
INSERT INTO Appartient(unClient, unCompte) VALUES(1,20);

DELETE FROM Appartient WHERE unCompte=1 AND unClient=1;
DELETE FROM Appartient WHERE unCompte=1 AND unClient=2 --Suppression impossible => compte sans client;

DELETE FROM Appartient WHERE unCompte=20 --Suppression impossible => compte sans client;

UPDATE Appartient SET unCompte = 8 WHERE unClient=2 and unCompte=20;

UPDATE Appartient SET unCompte = 19 WHERE unClient=1 and unCompte=20 --Suppression impossible => compte sans client;

--Test d'insertion d'un compte
-----------------------------------------------------------------------------------------------------------------
INSERT INTO COMPTE(numCompte, solde, typeCompte) VALUES (134, 50, 'EPARGNE'); -- Insertion interdite

--Test d'insertion d'un Compte_Client
-----------------------------------------------------------------------------------------------------------------
INSERT INTO Compte_Client(numCompte, solde, typeCompte, numClient) VALUES (134, 50, 'EPARGNE', 1); -- Insertion interdite

--Test d'insertion par la procédure
-----------------------------------------------------------------------------------------------------------------
EXECUTE inserer_compte(128,50,'EPARGNE',13); 

SELECT * FROM APPARTIENT;