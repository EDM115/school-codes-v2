--- Tables ---

CREATE TABLE Compagnie
	(
	idComp NUMBER
		CONSTRAINT pk_Compagnie PRIMARY KEY,
	nomComp VARCHAR2(30),
	pays VARCHAR2(20),
	estLowCost NUMBER
	)
;

CREATE TABLE Pilote
	(
	idPilote NUMBER
		CONSTRAINT pk_Pilote PRIMARY KEY,
	nomPilote VARCHAR2(20),
	nbHVol NUMBER,
	compPil NUMBER
		CONSTRAINT fk_Pilote_Compagnie REFERENCES Compagnie(idComp)
	)
;

CREATE TABLE TypeAvion
	(
	idTypeAvion VARCHAR2(20)
		CONSTRAINT pk_TypeAvion PRIMARY KEY,
	nbPassagers NUMBER
	)
;

CREATE TABLE Qualification
	(
	unPilote NUMBER
		CONSTRAINT fk_Qualification_Pilote REFERENCES Pilote(idPilote),
	unTypeAvion VARCHAR2(20)
		CONSTRAINT fk_Qualification_TypeAvion REFERENCES TypeAvion(idTypeAvion),
	CONSTRAINT pk_Qualification	PRIMARY KEY (unPilote, unTypeAvion)
	)
;

CREATE TABLE Avion 
	(
	idAvion	NUMBER
		CONSTRAINT pk_Avion	PRIMARY KEY,
	leTypeAvion VARCHAR2(20)
		CONSTRAINT fk_Avion_TypeAvion REFERENCES TypeAvion(idTypeAvion),
	compAv NUMBER
		CONSTRAINT fk_Avion_Compagnie REFERENCES Compagnie(idComp)
	)
;

CREATE TABLE ATravaillePour
	(
	lePilote NUMBER
		CONSTRAINT fk_ATravaillePour_Pilote REFERENCES Pilote(idPilote),
	laComp NUMBER
		CONSTRAINT fk_ATravaillePour_Compagnie REFERENCES Compagnie(idComp),
	CONSTRAINT pk_ATravaillePour PRIMARY KEY (lePilote, laComp)
	)
;


--- Exercices ---

--- Divisions : Afficher, d'abord avec NOT EXISTS (et MINUS), puis avec un regroupement ---

-- 1 : les noms des pilotes ayant travaillé pour toutes les compagnies --

-- Avec NOT EXISTS et MINUS --

SELECT nomPilote
FROM Pilote
WHERE NOT EXISTS (
	SELECT idComp
	FROM Compagnie
	MINUS
	SELECT laComp
	FROM ATravaillePour
	WHERE lePilote = idPilote
);

-- Avec un regroupement --

SELECT nomPilote
FROM Pilote
	LEFT JOIN ATravaillePour ON idPilote = lePilote
		LEFT JOIN Compagnie ON laComp = idComp
GROUP BY idPilote, nomPilote
HAVING COUNT(DISTINCT idComp) = (
	SELECT COUNT(*) 
	FROM Compagnie
);

-- 1 Résultat --

NOMPILOTE
Fleurquin


-- 2 : les noms des pilotes ayant travaillé pour toutes les compagnies low cost --

-- Avec NOT EXISTS et MINUS --

SELECT nomPilote
FROM Pilote
WHERE NOT EXISTS (
	SELECT idComp
	FROM Compagnie
	WHERE estLowCost = 1
	MINUS
	SELECT laComp
	FROM ATravaillePour
		LEFT JOIN Compagnie ON laComp = idComp
	WHERE lePilote = idPilote
		AND estLowCost = 1
);

-- Avec un regroupement --

SELECT nomPilote
FROM Pilote
	LEFT JOIN ATravaillePour ON idPilote = lePilote
		LEFT JOIN Compagnie ON laComp = idComp
WHERE estLowCost = 1
GROUP BY idPilote, nomPilote
HAVING COUNT(DISTINCT idComp) = (
	SELECT COUNT(*) 
	FROM Compagnie
	WHERE estLowCost = 1
);

-- 3 Résultats --

NOMPILOTE
Ridard
Naert
Fleurquin


-- 3 : les noms des pilotes ayant travaillé pour exactement toutes les compagnies low cost --

-- Avec NOT EXISTS et MINUS --

SELECT nomPilote
FROM Pilote
WHERE NOT EXISTS (
	SELECT idComp
	FROM Compagnie
	WHERE estLowCost = 1
	MINUS
	SELECT laComp
	FROM ATravaillePour
	WHERE lePilote = idPilote   
)
AND NOT EXISTS (
	SELECT laComp
	FROM ATravaillePour
	WHERE lePilote = idPilote
	MINUS
	SELECT idComp
	FROM Compagnie
	WHERE estLowCost = 1
);

-- Avec un regroupement --

SELECT nomPilote
FROM Pilote
	LEFT JOIN ATravaillePour ON idPilote = lePilote
		LEFT JOIN Compagnie ON laComp = idComp
WHERE estLowCost = 1
GROUP BY idPilote, nomPilote
HAVING COUNT(DISTINCT idComp) = (
	SELECT COUNT(*) 
	FROM Compagnie
	WHERE estLowCost = 1
)
INTERSECT
SELECT nomPilote
FROM Pilote
	LEFT JOIN ATravaillePour ON idPilote = lePilote
		LEFT JOIN Compagnie ON laComp = idComp
GROUP BY idPilote, nomPilote
HAVING COUNT(DISTINCT idComp) = (
	SELECT COUNT(*) 
	FROM Compagnie
	WHERE estLowCost = 1
);

-- 1 Résultat --

NOMPILOTE
Naert


--- Fonctions de groupe ---

-- 1 : Afficher le nombre de compagnies low cost (de deux manières différentes) --

-- Avec COUNT --

SELECT COUNT(*)
FROM Compagnie
WHERE estLowCost = 1;

-- Avec SUM --

SELECT SUM(estLowCost)
FROM Compagnie;

-- 2 Résultats --

SUM(ESTLOWCOST)
2


-- 2 : Afficher le nombre d'avions appartenant à Air France --

SELECT COUNT(*)	
FROM Avion, Compagnie
WHERE idComp = compAv
AND UPPER(nomComp) = 'AIR FRANCE';

-- 1 Résultat --

COUNT(*)
3


-- 3 : Afficher le nombre de type d'avions appartenant à Ryanair --

SELECT COUNT(DISTINCT leTypeAvion)
FROM Avion, Compagnie
WHERE idComp = compAv
AND UPPER(nomComp) LIKE 'RYANAIR';

-- 1 Résultat --

COUNT(DISTINCTLETYPEAVION)
1


-- 4 : Afficher le nombre total de passagers que peut transporter Air France --

SELECT SUM(nbPassagers)
FROM Avion, Compagnie, TypeAvion
WHERE idComp = compAv
AND idTypeAvion = leTypeAvion
AND UPPER(nomComp) LIKE 'AIR FRANCE';

-- 1 Résultat --

SUM(NBPASSAGERS)
777


-- 5 : Afficher le type d'avion ayant la capacité maximale --

SELECT idTypeAvion
FROM TypeAvion
WHERE nbPassagers = (
	SELECT MAX(nbPassagers)
	FROM TypeAvion
);

-- 1 Résultat --

IDTYPEAVION
A350


-- 6 : Afficher les compagnies (nom) ayant le type d'avion de capacité maximale --

SELECT nomComp
FROM Compagnie, Avion, TypeAvion
WHERE idComp = compAv
AND idTypeAvion = leTypeAvion
AND idTypeAvion = (
	SELECT idTypeAvion
	FROM TypeAvion
	WHERE nbPassagers = (
		SELECT MAX(nbPassagers)
		FROM TypeAvion
	)
);

-- 2 Résultats --

NOMCOMP
Air France
American Airlines


--- Regroupement ---

-- 1 : Afficher le(s) pilote(s) ayant au moins deux qualifications --

SELECT unPilote
FROM Qualification
GROUP BY unPilote
HAVING COUNT(unTypeAvion) >= 2;

-- 5 Résultats --

UNPILOTE
1
2
4
5
7


-- 2 : Afficher le(s) pilote(s) ayant le plus grand nombre de qualifications --

SELECT unPilote
FROM Qualification
GROUP BY unPilote
HAVING COUNT(unTypeAvion) = (
	SELECT MAX(nbQualif)
	FROM (
		SELECT unPilote, COUNT(unTypeAvion) AS nbQualif
		FROM Qualification
		GROUP BY unPilote
	)
);

-- 1 Résultat --

UNPILOTE
4


-- 3 : Afficher le(s) pilote(s) ayant le plus petit nombre de qualifications, éventuellement 0 --

SELECT unPilote
FROM (
	SELECT idPilote AS unPilote, COUNT(unTypeAvion) AS nbQualifications
	FROM Pilote
		LEFT JOIN Qualification ON idPilote = unPilote
	GROUP BY idPilote
)
WHERE nbQualifications = (
	SELECT MIN(nbQualifications)
	FROM (
		SELECT COUNT(unTypeAvion) AS nbQualifications
		FROM Pilote
			LEFT JOIN Qualification ON idPilote = unPilote
		GROUP BY idPilote
	)
);

-- 1 Résultat --

UNPILOTE
6


-- 4 : Pour chaque compagnie désignée par son nom (triée dans l'ordre alphabétique), afficher le nombre d'heures de vol moyen des pilotes --

SELECT nomComp, AVG(nbHVol) AS heuresVolMoyennes
FROM Compagnie
	LEFT JOIN Pilote ON idComp = compPil
GROUP BY nomComp
ORDER BY nomComp ASC;

-- 5 Résultats --	

NOMCOMP,				HEURESVOLMOYENNES
Air France,				2250
American Airlines,		1950
Corsair International,	
EasyJet,				450
Ryanair,				450


-- 5 : Afficher la compagnie ayant le plus grand nombre de places --

SELECT idComp
FROM Compagnie
	LEFT JOIN Avion ON idComp = compAv
		LEFT JOIN TypeAvion ON idTypeAvion = leTypeAvion
GROUP BY idComp
HAVING SUM(nbPassagers) = (
	SELECT MAX(nbPlaces)
	FROM (
		SELECT nomComp, SUM(nbPassagers) AS nbPlaces
		FROM Compagnie
			LEFT JOIN Avion ON idComp = compAv
				LEFT JOIN TypeAvion ON idTypeAvion = leTypeAvion
		GROUP BY nomComp
	)
);

-- 1 Résultat --

IDCOMP
1


--- Division ---

-- 1 : Afficher les noms des compagnies possédant tous les types d'Airbus --

-- Avec NOT EXISTS et MINUS --

SELECT nomComp
FROM Compagnie
WHERE NOT EXISTS (
	SELECT leTypeAvion
	FROM Avion
	WHERE leTypeAvion LIKE 'A%'
	MINUS
	SELECT leTypeAvion
	FROM Avion
	WHERE compAv = idComp
);

-- Avec un regroupement --

SELECT nomComp
FROM Compagnie
WHERE idComp IN (
	SELECT compAv
	FROM Avion
	WHERE leTypeAvion LIKE 'A%'
	GROUP BY compAv
	HAVING COUNT(DISTINCT leTypeAvion) = (
		SELECT COUNT(*)
		FROM TypeAvion
		WHERE idTypeAvion LIKE 'A%'
	)
);

-- 1 Résultat --

NOMCOMP
Air France


-- 2 : Afficher les noms des compagnies possédant exactement tous les types d'Airbus --

-- Avec NOT EXISTS et MINUS --

SELECT nomComp
FROM Compagnie
WHERE NOT EXISTS (
	SELECT leTypeAvion
	FROM Avion
	WHERE leTypeAvion LIKE 'A%'
	MINUS
	SELECT leTypeAvion
	FROM Avion
	WHERE compAv = idComp
)
AND NOT EXISTS (
	SELECT leTypeAvion
	FROM Avion
	WHERE compAv = idComp
	MINUS
	SELECT leTypeAvion
	FROM Avion
	WHERE leTypeAvion LIKE 'A%'
);

-- Avec un regroupement --

SELECT nomComp
FROM Compagnie
WHERE idComp IN (
	SELECT compAv
	FROM Avion
	WHERE leTypeAvion LIKE 'A%'
	GROUP BY compAv
	HAVING COUNT(DISTINCT leTypeAvion) = (
		SELECT COUNT(*)
		FROM TypeAvion
		WHERE idTypeAvion LIKE 'A%'
	)
)
INTERSECT
SELECT nomComp
FROM Compagnie
WHERE idComp IN (
	SELECT compAv
	FROM Avion
	GROUP BY compAv
	HAVING COUNT(DISTINCT leTypeAvion) = (
		SELECT COUNT(*)
		FROM TypeAvion
		WHERE idTypeAvion LIKE 'A%'
	)
);

-- 0 Résultat --

no rows selected
