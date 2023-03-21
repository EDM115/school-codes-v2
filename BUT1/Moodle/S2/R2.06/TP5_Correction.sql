-- Tout compte appartient à au moins un client (à ajouter au diagramme de classes)

CREATE OR REPLACE VIEW vue_CompteSansClient
AS
SELECT numCompte
FROM Compte
MINUS
SELECT unCompte
FROM Appartient    
;

SELECT *
FROM vue_CompteSansClient
;


-- Une agence a forcément un et un seul directeur

-- Avec une réunion

CREATE OR REPLACE VIEW vue_AgenceIllegale
(id_Agence)
AS
(
SELECT sonAgence
FROM Agent
WHERE directeur = 1
GROUP BY sonAgence
HAVING COUNT(sonAgence) >= 2
) 
-- On a pris les agences avec trop de directeurs
UNION
-- On prend les agences sans directeur
(
SELECT numAgence
FROM Agence
MINUS
SELECT sonAgence
FROM Agent
WHERE directeur = 1
)   
;

SELECT *
FROM vue_AgenceIllegale
;


-- Le directeur d’une agence est mieux payé que les agents de son agence

--Bonne requete
CREATE OR REPLACE VIEW vue_AgentTropPaye
(id_Agent, id_Agence)
AS
SELECT Ag.numAgent, Ag.sonAgence
FROM Agent Dir, Agent Ag
WHERE Dir.directeur = 1
AND Ag.directeur = 0 
AND Dir.sonAgence = Ag.sonAgence
AND Dir.salaire < Ag.salaire
;

SELECT *
FROM vue_AgentTropPaye
;


-- âge et agence du client

CREATE OR REPLACE VIEW vue_InfoClient
(id_Client, ageClient, agenceClient)
AS
SELECT numClient, FLOOR(MONTHS_BETWEEN(SYSDATE, dateNaissanceClient) / 12), sonAgence
FROM Client, Agent
WHERE sonAgent = numAgent
;
    
SELECT *
FROM vue_InfoClient
;

CREATE OR REPLACE VIEW vue_Client_Complete
AS
SELECT numClient, nomClient, prenomClient, adClient, DateNaissanceClient, sonAgent, ageClient, agenceClient
FROM Client
    JOIN vue_InfoClient ON numClient = id_Client
;

SELECT *
FROM vue_Client_Complete
;
