-- TP3

-- Pour chaque tuteur d'apprenti désigné par son nom (trié dans l'ordre alphabétique), afficher le nombre d'apprentis.

SELECT nomEns, COUNT(etudApp) nb_app
FROM Apprenti 
	JOIN Enseignant ON tuteurApp = idEns
GROUP BY nomEns -- on pourrait ajouter idEns en premier en cas d'homonyme
ORDER BY nomEns
;

-- Pour chaque enseignant désigné par son nom, afficher le nombre d'apprentis (éventuellement 0) dans l'ordre décroissant.

SELECT nomEns, COUNT(etudApp) nb_app
FROM Apprenti 
	RIGHT JOIN Enseignant ON tuteurApp = idEns
GROUP BY nomEns
ORDER BY nb_app DESC, nomEns
;

-- Afficher le plus grand nombre d'apprentis suivis par un tuteur.

SELECT MAX(COUNT(etudApp)) max_app
FROM Apprenti 
GROUP BY tuteurApp
;

-- Afficher le(s) tuteur(s) ayant le plus grand nombre d'apprentis.

SELECT tuteurApp
FROM Apprenti
GROUP BY tuteurApp
HAVING COUNT(etudApp) =
	(
	SELECT MAX(COUNT(etudApp))
	FROM Apprenti 
	GROUP BY tuteurApp
	)
;

-- Afficher le nombre moyen d'apprentis par tuteur.

SELECT AVG(COUNT(etudApp)) moy_app
FROM Apprenti 
GROUP BY tuteurApp
;

-- Afficher les tuteurs ayant un nombre d'apprentis strictement supérieur à la moyenne.

SELECT tuteurApp
FROM Apprenti
GROUP BY tuteurApp
HAVING COUNT(etudApp) >
	(
	SELECT AVG(COUNT(etudApp))
	FROM Apprenti 
	GROUP BY tuteurApp
	)
;

-- Pour chaque département, afficher le nombre de lycéens recrutés en première année dans l'ordre décroissant.

SELECT depLycee, COUNT(idEtud) nb_lyceens
FROM Etudiant
WHERE depLycee IS NOT NULL
GROUP BY depLycee
ORDER BY nb_lyceens DESC
;

-- Afficher le département de Bretagne qui a fourni le moins de lycéens recrutés en première année.

SELECT depLycee
FROM Etudiant
WHERE depLycee IN (22, 29, 35, 56)
GROUP BY depLycee
HAVING COUNT(idEtud) =
	(
	SELECT MIN(COUNT(idEtud))
	FROM Etudiant
	WHERE depLycee IN (22, 29, 35, 56)
	GROUP BY depLycee
	)
;

-- Afficher les poursuites d'études concernant au moins 5 étudiants.

SELECT poursuiteEtudes
FROM Etudiant
GROUP BY poursuiteEtudes
HAVING COUNT(idEtud) >= 5
;

-- Afficher la poursuite d'études ayant le maximum d'étudiants apprentis.

SELECT poursuiteEtudes
FROM Apprenti 
	JOIN Etudiant ON etudApp = idEtud
GROUP BY poursuiteEtudes
HAVING COUNT(idEtud) =
	(
	SELECT MAX(COUNT(idEtud))
	FROM Apprenti 
		JOIN Etudiant ON etudApp = idEtud
	GROUP BY poursuiteEtudes
	)
;

-- Afficher les 3 poursuites d'études les plus représentées parmi les étudiants du parcours Développeurs d'Applications (DA).

SELECT *
FROM
	(
	SELECT poursuiteEtudes, COUNT(idEtud) nb_etud
	FROM Etudiant
	WHERE parcoursInfo2 = 'DA'
	AND poursuiteEtudes IS NOT NULL
	GROUP BY poursuiteEtudes
	ORDER BY nb_etud DESC
	)
WHERE ROWNUM <=3
;

 