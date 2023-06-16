-- Quel est le directeur le mieux payé ?

SELECT numAgent
FROM Agent
WHERE estDirecteur = 1
AND salaire = 
	(
	SELECT MAX(salaire)
	FROM Agent
	WHERE estDirecteur = 1
	)
;


-- Pour chaque directeur, indiquer (ordre décroissant) le nombre d’agents.

SELECT AgDir.numAgent, COUNT(*)-1 nb_Agents
FROM Agent AgDir, Agent Ag
WHERE AgDir.sonAgence = Ag.sonAgence
AND AgDir.estDirecteur = 1
GROUP BY AgDir.numAgent
ORDER BY nb_Agents DESC
;


-- Quels sont les agents qui gèrent le plus grand nombre de comptes épargnes ?

SELECT numAgent
FROM Agent, Client, Appartient, Compte
WHERE numAgent = sonAgent
AND numClient = unClient
AND unCompte = numCompte
AND typeCompte = 'EPARGNE'
GROUP BY numAgent
HAVING COUNT(*) = 
	(
	SELECT MAX(COUNT(*))
	FROM Agent, Client, Appartient, Compte
	WHERE numAgent = sonAgent
	AND numClient = unClient
	AND unCompte = numCompte
	AND typeCompte = 'EPARGNE'
	GROUP BY numAgent
	)	
;


-- Lister les 5 derniers retraits du plus récent au plus ancien.

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


-- Pour chaque agence, indiquer le nombre de clients ne possédant pas encore de compte épargne.

SELECT sonAgence, COUNT(*) nb_Clients_Sans_Epargne
FROM Agent, 
	(
	SELECT numClient, sonAgent
	FROM Client
	MINUS
	SELECT unClient, sonAgent
	FROM Appartient, Compte, Client
	WHERE unCompte = numCompte
	AND unClient = numClient
	AND typeCompte = 'EPARGNE'
	)
WHERE sonAgent = numAgent
GROUP BY sonAgence
;