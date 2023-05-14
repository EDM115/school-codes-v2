-- 1 : Créer toutes les vues nécessaires pour gérer ces trois contraintes --

-- Contrainte 1 : Tout compte appartient à au moins un client --

CREATE OR REPLACE VIEW V_CompteSansClient AS
	SELECT numCompte
	FROM Compte
	WHERE numCompte NOT IN (
		SELECT unCompte
		FROM Appartient
	)
;

SELECT *
FROM V_CompteSansClient;

-- 1 Résultat --

NUMCOMPTE
2


-- Contrainte 2 : Une agence a forcément un et un seul directeur --

CREATE OR REPLACE VIEW V_AgenceSansDirecteur AS
	SELECT numAgence
	FROM Agence
	WHERE numAgence NOT IN (
		SELECT sonAgence
		FROM Agent
		WHERE directeur = 1
	)
	OR (
		SELECT COUNT(*)
		FROM Agent
		WHERE directeur = 1
		AND sonAgence = numAgence
	) > 1
;

SELECT *
FROM V_AgenceSansDirecteur;

-- 2 Résultats --

NUMAGENCE
1
2


-- Contrainte 3 : Le directeur d'une agence est mieux payé que les agents de son agence --

CREATE OR REPLACE VIEW V_AgentTropPaye AS
	SELECT numAgent, sonAgence
	FROM Agent
	WHERE directeur = 0
	AND salaire > (
		SELECT MAX(salaire)
		FROM Agent
		WHERE directeur = 1
		AND sonAgence = Agent.sonAgence
	)
;

SELECT *
FROM V_AgentTropPaye;

-- 1 Résultat --

NUMAGENT,	SONAGENCE
6,			3


-- 2 : Créer une vue permettant d'afficher, pour chaque client, son âge et son agence --

CREATE OR REPLACE VIEW V_ClientAgeAgence AS
	SELECT numClient, nomClient, prenomClient, adClient, dateNaissanceClient, sonAgent, sonAgence, ROUND((SYSDATE - dateNaissanceClient)/365) AS age
	FROM Client, Agent
	WHERE sonAgent = numAgent
;

SELECT *
FROM V_ClientAgeAgence;

-- 3 Résultats --

NUMCLIENT,	NOMCLIENT,	PRENOMCLIENT,	ADCLIENT,		DATENAISSANCECLIENT,	SONAGENT,	SONAGENCE,	AGE
1,			Nanou,		Bib,			1 rue Truc,		30-NOV-02,				1,			1,			20
2,			Bidule,		Mach,			1 rue Chose,	30-DEC-97,				2,			1,			25
3,			Bidule,		Mach,			1 rue Chose,	26-NOV-93,				3,			2,			29