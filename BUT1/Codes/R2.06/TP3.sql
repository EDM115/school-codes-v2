Schéma relationnel :

Enseignant(idEns:str (1), nomEns:str (NN), prenomEns:str (NN))
GroupeInfo1(idGroupe:str (1), tuteurGroupe:str @Enseignant.idEns (1))
Etudiant(idEtud:int (1), nomEtud:str (NN), prenomEtud:str (NN), sexe:str (NN), bac:str, nomLycee:str, depLycee:int, leGroupeInfo1:str (NN) @GroupeInfo1.idGroupe, parcoursInfo:str, formationInfo:str, poursuiteEtudes:str)
Entreprise(idEntreprise:int (1), nomEntreprise:str, depEntreprise:int)
Stagiaire(etudStagiaire:int (1) @Etudiant.idEtud, entrepriseStage:int (1) @Entreprise.idEntreprise)
Apprenti(etudApp:int (1) @Etudiant.idEtud, entrepriseApp:int (1) @Entreprise.idEntreprise, tuteurApp:str @Enseignant.idEns (NN))

-- Q1 : Pour chaque tuteur d'apprenti désigné par son nom (trié dans l'ordre alphabétique), afficher le nombre d'apprentis --

SELECT nomEns, COUNT(*)
FROM Enseignant, Apprenti
WHERE Enseignant.idEns = Apprenti.tuteurApp
GROUP BY nomEns
ORDER BY nomEns;

-- 7 Résultats : --

NOMENS,		COUNT(*)
Baudont,	6
Fleurquin,	2
Kamp,		2
Lefevre,	2
Mannevy,	7
Ridard,		2
Tuffigo,	3


-- Q2 : Pour chaque enseignant désigné par son nom, afficher le nombre d'apprentis (éventuellement 0) dans l'ordre décroissant. Si il y n'y a pas d'apprentis, il faut afficher 0 --

SELECT nomEns, COUNT(etudApp) nbApprentis
FROM Enseignant
	LEFT JOIN Apprenti
		ON Enseignant.idEns = Apprenti.tuteurApp
GROUP BY nomEns
ORDER BY nbApprentis DESC;

-- 20 Résultats : --

NOMENS,		NBAPPRENTIS
Mannevy,	7
Baudont,	6
Tuffigo,	3
Kamp,		2
Ridard,		2
Lefevre,	2
Fleurquin,	2
Borne,		0
Kerbellec,	0
Adam,		0
Joucla,		0
Volin,		0
Pham,		0
Naert,		0
Godin,		0
Roirand,	0
Lesueur,	0
Le Lain,	0
Merciol,	0
Le Sommer,	0


-- Q3 : Afficher le plus grand nombre d'apprentis suivis par un tuteur --

SELECT MAX(nbApprentis)
FROM (
	SELECT nomEns, COUNT(etudApp) nbApprentis
	FROM Enseignant
		LEFT JOIN Apprenti
			ON Enseignant.idEns = Apprenti.tuteurApp
	GROUP BY nomEns
);

-- 1 Résultat : --

MAX(NBAPPRENTIS)
7


-- Q4 : Afficher le(s) tuteur(s) ayant le plus grand nombre d'apprentis --

SELECT tuteurApp
FROM Apprenti
GROUP BY tuteurApp
HAVING COUNT(*) = (
	SELECT MAX(nbApprentis)
	FROM (
		SELECT nomEns, COUNT(etudApp) nbApprentis
		FROM Enseignant
			LEFT JOIN Apprenti
				ON Enseignant.idEns = Apprenti.tuteurApp
		GROUP BY nomEns
	)
);

-- 1 Résultat : --

TUTEURAPP
MM


-- Q5 : Afficher le nombre moyen d'apprentis par tuteur --

SELECT COUNT(etudApp)/COUNT(DISTINCT tuteurApp)
FROM Apprenti;

-- 1 Résultat : --

COUNT(ETUDAPP)/COUNT(DISTINCTTUTEURAPP)
3.42857142857142857142857142857142857143


-- Q6 : Afficher les tuteurs ayant un nombre d'apprentis strictement supérieur à la moyenne --

SELECT tuteurApp
FROM Apprenti
GROUP BY tuteurApp
HAVING COUNT(*) > (
	SELECT COUNT(etudApp)/COUNT(DISTINCT tuteurApp)
	FROM Apprenti
);

-- 2 Résultats : --

TUTEURAPP
PB
MM


-- Q7 : Pour chaque département, afficher le nombre de lycéens recrutés en première année dans l'ordre décroissant --

SELECT depLycee, COUNT(*)
FROM Etudiant
WHERE nomLycee IS NOT NULL
AND depLycee IS NOT NULL
GROUP BY depLycee
ORDER BY COUNT(*) DESC;

-- 16 Résultats : --

DEPLYCEE,	COUNT(*)
56,			34
35,			25
29,			19
44,			7
50,			3
22,			3
60,			2
72,			2
85,			2
90,			1
69,			1
37,			1
95,			1
86,			1
49,			1
53,			1


-- Q8 : Afficher le département de Bretagne qui a fourni le moins de lycéens recrutés en première année --

SELECT depLycee
FROM Etudiant
WHERE nomLycee IS NOT NULL
AND depLycee IS NOT NULL
AND depLycee IN (22, 29, 35, 56)
GROUP BY depLycee
HAVING COUNT(*) = (
	SELECT MIN(nbLyceens)
	FROM (
		SELECT depLycee, COUNT(*) nbLyceens
		FROM Etudiant
		WHERE nomLycee IS NOT NULL
		AND depLycee IS NOT NULL
		AND depLycee IN (22, 29, 35, 56)
		GROUP BY depLycee
	)
);

-- 1 Résultat : --

DEPLYCEE
22


-- Q9 : Afficher les poursuites d'études concernant au moins 5 étudiants --

SELECT poursuiteEtudes
FROM Etudiant
GROUP BY poursuiteEtudes
HAVING COUNT(*) >= 5;

-- 5 Résultats : --

POURSUITEETUDES

Licence info Vannes
ENSIBS cyberdéfense
Travail
Licence Pro Vannes Delice


-- Q10 : Afficher la poursuite d'études ayant le maximum d'étudiants apprentis --

SELECT poursuiteEtudes
FROM Etudiant, Apprenti
WHERE Etudiant.idEtud = Apprenti.etudApp
GROUP BY poursuiteEtudes
HAVING COUNT(*) = (
	SELECT MAX(nbEtudiants)
	FROM (
		SELECT poursuiteEtudes, COUNT(*) nbEtudiants
		FROM Etudiant, Apprenti
		WHERE Etudiant.idEtud = Apprenti.etudApp
		GROUP BY poursuiteEtudes
	)
);

-- 1 Résultat : --

POURSUITEETUDES
ENSIBS cyberdéfense


-- Q11 : Afficher les 3 poursuites d'études les plus représentées parmi les étudiants du parcours Développeurs d'Applications (DA) --

SELECT *
FROM (
	SELECT poursuiteEtudes, COUNT(poursuiteEtudes) AS nb_etud
	FROM Etudiant
	WHERE parcoursInfo2 = 'DA'
	GROUP BY poursuiteEtudes
	ORDER BY COUNT(poursuiteEtudes) DESC
)
WHERE ROWNUM <= 3;

-- 3 Résultats : --

POURSUITEETUDES,			NB_ETUD
Licence info Vannes,		5
Licence Pro Vannes Delice,	5
Travail,					3
