-- Liste des clients ayant effectué des retraits d’un montant supérieur à 1000 euros

SELECT C.nomClient, C.prenomClient
FROM Client C
JOIN Operation O ON C.numClient = O.leClient
WHERE O.typeOperation = 'RETRAIT' AND O.montant > 1000;

-- Qui sont les clients n'ayant pas de compte auprès de l'agence gérée par Monsieur Dupont ?

SELECT C.nomClient, C.prenomClient
FROM Client C
JOIN Agent A ON C.sonAgent = A.numAgent
JOIN Agence Ag ON A.sonAgence = Ag.numAgence
WHERE Ag.numAgence NOT IN (
    SELECT sonAgence
    FROM Agent
    WHERE UPPER(nomAgent) = 'DUPONT'
)
UNION
SELECT nomClient, prenomClient FROM CLIENT WHERE NOT EXISTS (select NULL from Appartient where unclient=numclient);

-- Liste des agences avec le nombre total d'agents, même celles qui n'ont pas d'agents.

SELECT A.numAgence, A.adAgence, COUNT(Ag.numAgent) AS NombreAgents
FROM Agence A
LEFT JOIN Agent Ag ON A.numAgence = Ag.sonAgence
GROUP BY A.numAgence, A.adAgence;

-- Quel est le directeur le mieux payé ?

SELECT numAgent, nomagent, prenomagent
FROM Agent
WHERE estDirecteur = 1
AND salaire = 
	(
	SELECT MAX(salaire)
	FROM Agent
	WHERE estDirecteur = 1
	);
    
-- Pour chaque directeur, le nombre d’agents (ordre décroissant).

SELECT AgDir.numAgent, AgDir.nomAgent, AgDir.prenomAgent, COUNT(*)-1 as nb_Agents
FROM Agent AgDir, Agent Ag
WHERE AgDir.sonAgence = Ag.sonAgence
AND AgDir.estDirecteur = 1
GROUP BY AgDir.numAgent, AgDir.nomAgent, AgDir.prenomAgent
ORDER BY nb_Agents DESC;

-- Quels sont les agents qui gèrent le plus grand nombre de comptes épargnes ?

SELECT numAgent, nomAgent, COUNT(*) as NB_epargnes
FROM Agent, Client, Appartient, Compte
WHERE numAgent = sonAgent
AND numClient = unClient
AND unCompte = numCompte
AND typeCompte = 'EPARGNE'
GROUP BY numAgent,nomAgent 
HAVING COUNT(*) = 
	(
	SELECT MAX(COUNT(*))
	FROM Agent, Client, Appartient, Compte
	WHERE numAgent = sonAgent
	AND numClient = unClient
	AND unCompte = numCompte
	AND typeCompte = 'EPARGNE'
	GROUP BY numAgent
	);
    
-- Liste des 5 derniers retraits du plus récent au plus ancien.

SELECT *
FROM
	(
	SELECT numOperation, dateOperation
	FROM Operation
	WHERE typeOperation = 'RETRAIT'
	ORDER BY dateOperation DESC
	)
WHERE ROWNUM <= 5
--ORDER BY dateOperation DESC
;

-- Pour chaque agence, le nombre de clients ne possédant pas encore de compte épargne.

SELECT numAgence, COUNT(sonAgence) nb_Clients_Sans_Epargne
FROM Agent 
JOIN (
	SELECT numClient, sonAgent
	FROM Client
	MINUS
	SELECT unClient, sonAgent
	FROM Appartient, Compte, Client
	WHERE unCompte = numCompte
	AND unClient = numClient
	AND typeCompte = 'EPARGNE'
	) ON sonAgent = numAgent
RIGHT JOIN AGENCE ON sonAgence=numAgence
GROUP BY numAgence
;

-- Quels sont les clients ayant effectué des opérations sur tous types de comptes ?

SELECT C.numClient, C.nomClient, C.prenomClient
FROM Client C
WHERE NOT EXISTS (
    SELECT typeCompte
    FROM Compte
    WHERE typeCompte NOT IN (
        SELECT DISTINCT typeCompte
        FROM Operation O
        JOIN Compte Co ON O.leCompte = Co.numCompte
        WHERE C.numClient = O.leClient
    )
);

-- OU

SELECT C.numClient, C.nomClient, C.prenomClient
FROM Client C
WHERE NOT EXISTS (
    SELECT typeCompte
    FROM Compte
    WHERE typeCompte NOT IN (
        SELECT DISTINCT typeCompte
        FROM Operation O
        JOIN Compte Co ON O.leCompte = Co.numCompte
    	JOIN Appartient A ON A.unCompte = Co.numCompte	
        WHERE C.numClient = A.unClient
    )
);

-- OU

SELECT numClient, nomClient, prenomClient FROM Client, Operation, Compte 
WHERE numClient = leclient AND numCompte = lecompte 
    GROUP BY numClient, nomClient, prenomClient 
    HAVING COUNT(DISTINCT typeCompte) = (SELECT COUNT(DISTINCT typeCompte) FROM Compte); 
