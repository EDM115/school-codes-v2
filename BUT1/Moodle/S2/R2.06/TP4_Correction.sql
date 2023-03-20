---------
-- TP4 --
---------

-------------------------
-- Partie 1 : division --
-------------------------

-- 1. Afficher les noms des pilotes ayant travaillé pour toutes les compagnies.
    
-- NOT EXISTS

SELECT nomPilote
FROM Pilote
WHERE NOT EXISTS
    (
	SELECT idComp
    FROM Compagnie
    MINUS
    SELECT laComp
    FROM ATravaillePour
    WHERE lePilote = idPilote
	)
;

-- Regroupement (avec jointure SQL2)

SELECT nomPilote
FROM Pilote
    JOIN ATravaillePour ON idPilote = lePilote
GROUP BY nomPilote
HAVING COUNT(*) >= -- ou HAVING COUNT(laComp) si on veut "optimiser" la requête grâce aux INDEX (cf. BUT 2)
    (
	SELECT COUNT(idComp)
    FROM Compagnie
	)
;

-- Regroupement (avec jointure relationnelle)  
  
SELECT nomPilote 
FROM Pilote,aTravaillePour 
WHERE lePilote = idPilote
GROUP BY nomPilote 
HAVING COUNT(*) >= 
    (SELECT COUNT(*)
    FROM Compagnie)
;


-- 2. Afficher les noms des pilotes ayant travaillé pour toutes les compagnies low cost.

-- NOT EXISTS

SELECT nomPilote
FROM Pilote
WHERE NOT EXISTS
    (
	SELECT idComp
    FROM Compagnie
    WHERE estLowCost = 1
    MINUS
    SELECT laComp
    FROM ATravaillePour
    WHERE lePilote = idPilote
	)
;

-- Regroupement

SELECT nomPilote 
FROM Pilote, ATravaillePour, Compagnie 
WHERE idPilote = lePilote --
	AND laComp = idComp 
AND estLowCost = 1
GROUP BY nomPilote 
HAVING COUNT(*) >= 
    (
	SELECT COUNT(*) 
    FROM Compagnie
    WHERE estLowCost = 1
	)
;


-- 3. Afficher les noms des pilotes ayant travaillé pour exactement toutes les compagnies low cost.

-- NOT EXISTS

SELECT nomPilote 
FROM Pilote 
WHERE NOT EXISTS 
    (
	SELECT idComp 
    FROM Compagnie 
    WHERE estLowCost = 1
    MINUS
    SELECT laComp 
    FROM ATravaillePour 
    WHERE lePilote = idPilote
	)
AND NOT EXISTS 
    (
	SELECT laComp 
    FROM ATravaillePour 
    WHERE lePilote = idPilote
    MINUS
    SELECT idComp 
    FROM Compagnie 
    WHERE estLowCost = 1
	)
;

-- Regroupement

SELECT nomPilote
FROM Pilote, ATravaillePour, Compagnie
WHERE idPilote = lePilote
	AND laComp = idComp 
AND estLowCost = 1
GROUP BY nomPilote 
HAVING COUNT(*) >= 
    (
	SELECT COUNT(*) 
    FROM Compagnie 
    WHERE estLowCost = 1
	)
-- On a pris les pilotes ayant travaillé pour toutes les compagnies low cost (mais possiblement d'autres aussi)
INTERSECT
-- On prend les pilotes qui ont travaillé pour exactement le bon nombre de compagnies (autant que de low cost)
-- mais pas forcément pour des compagnies low cost
SELECT nomPilote 
FROM Pilote, aTravaillePour 
WHERE idPilote = lePilote
GROUP BY nomPilote 
HAVING COUNT(*) = 
	(
	SELECT COUNT(*) 
    FROM Compagnie 
    WHERE estLowCost = 1
	)	
;

 
------------------------------------------------
-- Partie 2 : révisions - fonctions de groupe --    
------------------------------------------------

-- 1. Afficher le nombre de compagnies low cost (de deux manières différentes).

SELECT COUNT(*)
FROM Compagnie
WHERE estLowCost = 1
;

SELECT SUM(estLowCost)
FROM Compagnie
;


-- 2. Afficher le nombre d’avions appartenant à Air France.

SELECT COUNT(*)
FROM Avion, Compagnie
WHERE compAv = idComp
AND UPPER(nomComp) = 'AIR FRANCE'
;


-- 3. Afficher le nombre de type d’avions appartenant à Ryanair.

SELECT COUNT(DISTINCT leTypeAvion)
FROM Avion, Compagnie
WHERE compAv = idComp
AND UPPER(nomComp) = 'RYANAIR'
;


-- 4. Afficher le nombre total de passagers que peut transporter Air France.

SELECT SUM(nbPassagers)
FROM Compagnie, Avion, typeAvion
WHERE idComp = compAv
	AND leTypeAvion = idTypeAvion
AND UPPER(nomComp) = 'AIR FRANCE'
;


-- 5. Afficher le type d'avion ayant la capacité maximale.

SELECT idTypeAvion
FROM TypeAvion
WHERE nbPassagers =
    (
	SELECT MAX(nbPassagers)
    FROM TypeAvion
	)
;
    
	
-- 6. Afficher les compagnies (nom) ayant le type d'avion de capacité maximale.

SELECT DISTINCT nomComp
FROM Compagnie, Avion
WHERE idComp = compAv
AND leTypeAvion IN 
    (
	SELECT idTypeAvion
	FROM TypeAvion
	WHERE nbPassagers =
		(
		SELECT MAX(nbPassagers)
		FROM TypeAvion
		)
	)
;


-----------------------------------------       
-- Partie 3 : révisions - regroupement --
-----------------------------------------

-- 1. Afficher le(s) pilote(s) ayant au moins deux qualifications.

SELECT unPilote
FROM Qualification
GROUP BY unPilote
HAVING COUNT(unTypeAvion) >= 2
;


-- 2. Afficher le(s) pilote(s) ayant le plus grand nombre de qualifications.

SELECT unPilote
FROM Qualification
GROUP BY unPilote
HAVING COUNT(unTypeAvion) = 
    (
    SELECT MAX(COUNT(unTypeAvion))
    FROM Qualification
    GROUP BY unPilote
    )
;


-- 3. Afficher le(s) pilote(s) ayant le plus petit nombre de qualifications, éventuellement 0.
-- On considère le 0 pour "enrichir" le corrigé même si ce n'est pas explicitement demandé !

-- Jointure (externe) relationnelle

SELECT idPilote
FROM Pilote, Qualification
WHERE idPilote = unPilote(+)
GROUP BY idPilote
HAVING COUNT(unTypeAvion) = 
    (
    SELECT MIN(COUNT(unTypeAvion))
    FROM Pilote, Qualification
    WHERE idPilote = unPilote(+)
    GROUP BY idPilote
    )
;

-- Jointure (externe) SQL 2

SELECT idPilote
FROM Pilote
    LEFT JOIN Qualification ON idPilote = unPilote
GROUP BY idPilote
HAVING COUNT(unTypeAvion) = 
    (
    SELECT MIN(COUNT(unTypeAvion))
    FROM Pilote
        LEFT JOIN Qualification ON idPilote = unPilote
    GROUP BY idPilote
    )
;

-- 4. Pour chaque compagnie désignée par son nom (triée dans l’ordre alphabétique), afficher le nombre d’heures de vol moyen des pilotes.
-- On considère, là encore, les compagnies sans pilote avec une jointure externe...

-- Jointure (externe) relationnelle
SELECT nomComp, AVG(nbHVol)
FROM Compagnie, Pilote
WHERE idComp = compPil(+)
GROUP BY nomComp
ORDER BY nomComp
;

-- Jointure (externe) SQL 2
SELECT nomComp, AVG(nbHVol)
FROM Compagnie
    LEFT JOIN Pilote ON idComp = compPil
GROUP BY nomComp
ORDER BY nomComp
;

-- 5. Afficher la compagnie ayant le plus grand nombre de places.

-- Places par compagnie
SELECT compAv, SUM(nbPassagers)
FROM Avion, TypeAvion
WHERE leTypeAvion = idTypeAvion
GROUP BY compAv
;

-- Réponse à la question
SELECT compAv
FROM Avion, TypeAvion
WHERE leTypeAvion = idTypeAvion
GROUP BY compAv
HAVING SUM(nbPassagers) =
    (
    SELECT MAX(SUM(nbPassagers))
    FROM Avion, TypeAvion
    WHERE leTypeAvion = idTypeAvion
	GROUP BY compAv
    )
;


-------------------------------------
-- Partie 4 : révisions - division --
-------------------------------------

-- 1. Afficher les noms des compagnies possédant tous les types d’Airbus.

-- NOT EXISTS

SELECT nomComp
FROM Compagnie
WHERE NOT EXISTS
    (
    SELECT idTypeAvion
    FROM TypeAvion
    WHERE idTypeAvion LIKE 'A%'
    MINUS
    SELECT leTypeAvion
    FROM Avion
    WHERE compAv = idComp
    )
;

-- Regroupement

SELECT nomComp
FROM Compagnie, Avion
WHERE idComp = compAv
AND leTypeAvion LIKE 'A%'
GROUP BY nomComp
HAVING COUNT(DISTINCT leTypeAvion) >=
    (
    SELECT COUNT(idTypeAvion)
    FROM TypeAvion
    WHERE idTypeAvion LIKE 'A%'
    )
;


-- 2. les noms des compagnies possédant exactement tous les types d’Airbus.

-- NOT EXISTS
SELECT nomComp
FROM Compagnie
WHERE NOT EXISTS
    (
    SELECT idTypeAvion
    FROM TypeAvion
    WHERE idTypeAvion LIKE 'A%'
    MINUS
    SELECT leTypeAvion
    FROM Avion
    WHERE compAv = idComp
    )
AND NOT EXISTS
    (
    SELECT leTypeAvion
    FROM Avion
    WHERE compAv = idComp
    MINUS
    SELECT idTypeAvion
    FROM TypeAvion
    WHERE idTypeAvion LIKE 'A%'
    )
;

-- Regroupement

SELECT nomComp
FROM Compagnie, Avion
WHERE idComp = compAv
AND leTypeAvion LIKE 'A%'
GROUP BY nomComp
HAVING COUNT(DISTINCT leTypeAvion) >=
    (
    SELECT COUNT(idTypeAvion)
    FROM TypeAvion
    WHERE idTypeAvion LIKE 'A%'
    )
-- on a pris les compagnies possédant tous les types d'Airbus (mais possiblement d'autres aussi) 
INTERSECT
-- on prend les compagnies ayant le bon nombre de types d'avions (autant que les types d'Airbus)
-- mais pas forcément un type d'Airbus
SELECT nomComp
FROM Compagnie, Avion
WHERE idComp = compAv -- on a retiré la condition "A%"
GROUP BY nomComp
HAVING COUNT(DISTINCT leTypeAvion) =
    (
    SELECT COUNT(idTypeAvion)
    FROM TypeAvion
    WHERE idTypeAvion LIKE 'A%'
    )
;
  
 