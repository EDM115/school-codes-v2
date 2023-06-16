-------------------------------------------------
-- Tout compte appartient à au moins un client --
-------------------------------------------------

-- Vue

CREATE OR REPLACE VIEW vue_auMoinsUnClient
	(
	compte_sans_client
	)
AS
	SELECT numCompte
	FROM Compte
	MINUS
	SELECT unCompte
	FROM Appartient
;

-- Test

DELETE FROM Appartient;
DELETE FROM Operation;
DELETE FROM Compte;
DELETE FROM Client;
DELETE FROM Agent;
DELETE FROM Agence;

INSERT INTO Agence VALUES(1,'01 00 00 00 01','adresse1');

INSERT INTO Agent VALUES(1,'nomAgent1','prenomAgent1',2500,1,1);

INSERT INTO Client VALUES(1, 'nomClient1', 'prenomClient1', 'adClient1', '22/03/1979', 1);

INSERT INTO Compte VALUES(1, 1000, 'COURANT');
INSERT INTO Compte VALUES(2, 1000, 'COURANT');

INSERT INTO Appartient VALUES(1,1);

SELECT *
FROM vue_auMoinsUnClient;


----------------------------------------------------------------------------
-- Gestion des éléments dérivés : âge du client et agence dont il dépend) --
----------------------------------------------------------------------------

-- Vue

CREATE OR REPLACE VIEW vue_infosClient
	(
	num_Client,
	son_Age,
	son_Agence
	)
AS
	SELECT numClient, ROUND((SYSDATE - dateNaissClient)/365), sonAgence
	FROM Client, Agent
	WHERE sonAgent = numAgent
;

-- Test

DELETE FROM Appartient;
DELETE FROM Operation;
DELETE FROM Compte;
DELETE FROM Client;
DELETE FROM Agent;
DELETE FROM Agence;

INSERT INTO Agence VALUES(1,'01 00 00 00 01','adresse1');

INSERT INTO Agent VALUES(1,'nomAgent1','prenomAgent1',2500,1,1);

INSERT INTO Client VALUES(1, 'nomClient1', 'prenomClient1', 'adClient1', '22/03/1979', 1);

SELECT *
FROM vue_infosClient;
