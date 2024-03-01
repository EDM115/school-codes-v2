-- 4) Liste des clients ayant effectué des retraits de plus de 1000 euros

SELECT DISTINCT cl.nomClient, cl.prenomClient, op.montant
FROM Client cl
JOIN Operation op ON cl.numClient = op.leClient
WHERE op.typeOperation = 'RETRAIT'
    AND op.montant > 1000;

-- 5) Clients n'ayant pas de compte auprès de l'agence gérée par Monsieur Dupont

SELECT DISTINCT C.numClient, C.nomClient, C.prenomClient
FROM Client C
    JOIN Agent A ON C.sonAgent = A.numAgent
    JOIN Agence AG ON A.sonAgence = AG.numAgence
    LEFT JOIN Appartient AP ON C.numClient = AP.unClient
    LEFT JOIN Compte CO ON AP.unCompte = CO.numCompte
WHERE AG.numAgence NOT IN (
    SELECT AG.numAgence 
    FROM Agence AG 
        JOIN Agent A ON AG.numAgence = A.sonAgence 
    WHERE A.nomAgent = 'Dupont'
)
OR CO.numCompte IS NULL
ORDER BY C.numClient;

-- 6) Agences avec le nombre total d'agents

SELECT a.numAgence, a.adAgence, COUNT(ag.numAgent) AS NombreAgents
FROM Agence a
    LEFT JOIN Agent ag ON a.numAgence = ag.sonAgence
GROUP BY a.numAgence, a.adAgence;

-- 7) Directeur le mieux payé

SELECT nomAgent, prenomAgent, salaire AS SalaireMax
FROM Agent
WHERE estDirecteur = 1
    AND salaire = (
        SELECT MAX(salaire)
        FROM Agent
        WHERE estDirecteur = 1
    );

-- 8) Nombre d'agents pour chaque directeur, ordre décroissant

SELECT D.nomAgent AS DIRECTEUR, COUNT(A.numAgent) AS NOMBREAGENTS
FROM Agent D
    LEFT JOIN Agent A ON D.sonAgence = A.sonAgence AND D.numAgent != A.numAgent
WHERE D.estDirecteur = 1
GROUP BY D.numAgent, D.nomAgent
ORDER BY NOMBREAGENTS DESC, DIRECTEUR;

-- 9) Agents gérant le plus grand nombre de comptes épargnes

SELECT A.nomAgent, COUNT(CO.numCompte) AS nbCompte
FROM Agent A
    LEFT JOIN Client C ON A.numAgent = C.sonAgent
    LEFT JOIN Appartient AP ON C.numClient = AP.unClient
    LEFT JOIN Compte CO ON AP.unCompte = CO.numCompte
WHERE CO.typeCompte = 'EPARGNE'
GROUP BY A.nomAgent
HAVING COUNT(CO.numCompte) = (
    SELECT MAX(CountComptes)
    FROM (
        SELECT COUNT(CO.numCompte) AS CountComptes
        FROM Agent A
            LEFT JOIN Client C ON A.numAgent = C.sonAgent
            LEFT JOIN Appartient AP ON C.numClient = AP.unClient
            LEFT JOIN Compte CO ON AP.unCompte = CO.numCompte
        WHERE CO.typeCompte = 'EPARGNE'
        GROUP BY A.nomAgent
    )
);

-- 10) Les 5 derniers retraits du plus récent au plus ancien

SELECT *
FROM (
    SELECT * FROM Operation
    WHERE typeOperation = 'RETRAIT'
    ORDER BY dateOperation DESC
)
WHERE ROWNUM <= 5;

-- 11) Nombre de clients sans compte épargne par agence

SELECT AG.numAgence, COUNT(DISTINCT C.numClient) AS NombreClientsSansEpargne
FROM Agence AG
    LEFT JOIN Agent A ON AG.numAgence = A.sonAgence
    LEFT JOIN Client C ON A.numAgent = C.sonAgent
WHERE C.numClient NOT IN (
    SELECT DISTINCT C.numClient
    FROM Client C
        JOIN Appartient AP ON C.numClient = AP.unClient
        JOIN Compte CO ON AP.unCompte = CO.numCompte
    WHERE CO.typeCompte = 'EPARGNE'
)
OR C.numClient IS NULL
GROUP BY AG.numAgence
ORDER BY AG.numAgence;

-- 12) Clients ayant effectué des opérations sur tous types de comptes

SELECT cl.nomClient, cl.prenomClient
FROM Client cl
    JOIN Operation op ON cl.numClient = op.leClient
    JOIN Compte co ON op.leCompte = co.numCompte
GROUP BY cl.nomClient, cl.prenomClient
HAVING COUNT(DISTINCT co.typeCompte) = (SELECT COUNT(DISTINCT typeCompte) FROM Compte);
