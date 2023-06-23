-- Q1 --

SELECT *
FROM Compteur
WHERE leQuartier IS NULL;

6 résultats :

idCompteur,	nomCompteur,	sens,			coord_X, 	coord_Y, 	leQuartier
89,		Coteaux ,	Ouest,			47.21, 		-1.77, 		
699,		Coteaux ,	Est, 			47.20, 		-1.77, 		
889,		Stade ,		Ouest, 			47.15, 		-1.69, 		
907,		Stade ,		Est, 			47.15, 		-1.69, 		
1031,		VN751A ,	St Leger les Vignes, 	47.14, 		-1.71, 		
1032,		VN ,		Sucé sur Erdre, 	47.31, 		-1.54, 		


-- Q2 --

SELECT *
FROM Compteur
WHERE UPPER(sens) NOT IN ('NORD', 'SUD', 'EST', 'OUEST');

5 résultats :

idCompteur	nomCompteur		sens			coord_X	coord_Y	leQuartier
676		Pont Willy Brandt 	Beaulieu	47.21	-1.54	5
677		Pont Willy Brandt 	Malakoff	47.21	-1.54	5
679		Bd Malakoff 		Gare Sud		47.21	-1.54	5
1031		VN751A 			St Leger les Vignes	47.14	-1.71	
1032		VN 			Sucé sur Erdre		47.31	-1.54	


-- Q3 --

SELECT nomCompteur, COUNT(DISTINCT idCompteur) AS nombreCompteurs, COUNT(DISTINCT UPPER(sens)) AS nombreSensDistincts
FROM Compteur
GROUP BY nomCompteur;

29 résultats :

nomCompteur			nombreCompteurs	nombreSensDistincts
50 Otages 			4		2
Avenue de la Libération 	2		2
Bd Malakoff 			1		1
Bonduelle 			2		2
Calvaire 			2		2
Ceineray 			2		2
Chemin de halage Tortière 	2		2
Coteaux 			2		2
De Gaulle 			1		1
De Gaulle sortie Clémenceau 	1		1
Entrée pont Audibert 		2		2
Guy Mollet 			2		2
Madeleine 			2		2
Magellan 			4		2
Philippot 			2		2
Pont Anne de Bretagne 		2		2
Pont Audibert 			1		1
Pont de Pirmil 			2		1
Pont de Pornic 			2		2
Pont Haudaudine 		2		2
Pont Tabarly 			4		2
Pont Willy Brandt 		2		2
Promenade de Bellevue 		2		2
Sorinières 			2		2
Stade 				2		2
Stalingrad 			2		2
Van Iseghem 			2		2
VN 				1		1
VN751A 				1		1


-- Q4 --

SELECT Quartier.nomQuartier, COUNT(Compteur.idCompteur) AS nombreCompteurs
FROM Quartier
LEFT JOIN Compteur ON Quartier.idQuartier = Compteur.leQuartier
GROUP BY Quartier.nomQuartier
ORDER BY nombreCompteurs DESC;

18 résultats :

nomQuartier				nombreCompteurs
Centre Ville				22
Malakoff - Saint-Donatien		9
Ile de Nantes				6
Hauts Pavés - Saint Félix		4
Nantes Nord				2
Doulon - Bottière			2
Nantes Sud				2
Pont Rousseau				2
Ragon					2
Dervallières - Zola			1
Bellevue - Chantenay - Sainte Anne	0
Breil - Barberie			0
Nantes Erdre				0
Trentemoult				0
Hôtel de Ville				0
Château de Rezé				0
La Houssais				0
Blordière				0


-- Q5 --

SELECT
    Quartier.nomQuartier,
    COUNT(Compteur.idCompteur) AS nombreCompteurs
FROM
    Quartier
LEFT JOIN
    Compteur ON Quartier.idQuartier = Compteur.leQuartier
GROUP BY
    Quartier.nomQuartier
HAVING
    COUNT(Compteur.idCompteur) = 0
    OR COUNT(Compteur.idCompteur) = (
        SELECT
            MIN(compteurs)
        FROM
            (SELECT
                COUNT(idCompteur) AS compteurs
            FROM
                Compteur
            GROUP BY
                leQuartier) AS subquery
    );


9 résultats :

nomQuartier				nombreCompteurs
Bellevue - Chantenay - Sainte Anne	0
Dervallières - Zola			1
Breil - Barberie			0
Nantes Erdre				0
Trentemoult				0
Hôtel de Ville				0
Château de Rezé				0
La Houssais				0
Blordière				0


-- Q6 --

SELECT vacances, COUNT(laDate) AS nombreDates
FROM DateInfo
GROUP BY vacances
ORDER BY nombreDates DESC;

7 résultats :

vacances	nombreDates
		737
Ete		172
Noel		54
Hiver		48
Printemps	48
Toussaint	48
Ascension	13


-- Q7 --

SELECT
    CASE
        WHEN MONTH(laDate) BETWEEN 1 AND 3 THEN 'Hiver'
        WHEN MONTH(laDate) BETWEEN 4 AND 6 THEN 'Printemps'
        WHEN MONTH(laDate) BETWEEN 7 AND 9 THEN 'Été'
        WHEN MONTH(laDate) BETWEEN 10 AND 12 THEN 'Automne'
    END AS saison,
    MIN(tempMoy) AS temperatureMin,
    MAX(tempMoy) AS temperatureMax,
    AVG(tempMoy) AS temperatureMoyenne
FROM
    DateInfo
WHERE
    YEAR(laDate) = 2022
GROUP BY
    saison;

4 résulats :

saison		temperatureMin	temperatureMax	temperatureMoyenne
Hiver		-0.33		14.32		8.097111
Printemps	4.08		30.10		16.302418
Été		11.30		30.95		21.124111
Automne		-1.65		20.52		11.253261


-- Q8 --

SELECT
    jour,
    SUM(h00 + h01 + h02 + h03 + h04 + h05 + h06 + h07 + h08 + h09 + h10 + h11 + h12 + h13 + h14 + h15 + h16 + h17 + h18 + h19 + h20 + h21 + h22 + h23) AS nombreVelo
FROM
    DateInfo
JOIN
    Comptage ON DateInfo.laDate = Comptage.dateComptage
WHERE
    YEAR(DateInfo.laDate) = 2021
    AND DateInfo.vacance IS NULL
GROUP BY
    jour
ORDER BY
    FIELD(jour, 'LUNDI', 'MARDI', 'MERCREDI', 'JEUDI', 'VENDREDI', 'SAMEDI', 'DIMANCHE');

7 résultats :

jour		nombreVelo
lundi		1211870
mardi		1497800
mercredi	1445596
jeudi		1405851
vendredi	1352214
samedi		704997
dimanche	535463



-- Q9 --

SELECT
    jour,
    SUM(h00 + h01 + h02 + h03 + h04 + h05 + h06 + h07 + h08 + h09 + h10 + h11 + h12 + h13 + h14 + h15 + h16 + h17 + h18 + h19 + h20 + h21 + h22 + h23) AS nombreVelo
FROM
    DateInfo
JOIN
    Comptage ON DateInfo.laDate = Comptage.dateComptage
WHERE
    YEAR(DateInfo.laDate) = 2021
    AND DateInfo.vacances IS NOT NULL
GROUP BY
    jour
ORDER BY
    FIELD(jour, 'LUNDI', 'MARDI', 'MERCREDI', 'JEUDI', 'VENDREDI', 'SAMEDI', 'DIMANCHE');

7 résultats :

jour		nombreVelo
lundi		482623
mardi		562378
mercredi	502387
jeudi		558841
vendredi	520222
samedi		440249
dimanche	372248


-- Q10 --

SELECT
    Quartier.nomQuartier,
    COUNT(Comptage.presenceAnomalie) AS nombreAnomaliesFortes
FROM
    Quartier
LEFT JOIN
    Compteur ON Quartier.idQuartier = Compteur.leQuartier
LEFT JOIN
    Comptage ON Compteur.idCompteur = Comptage.leCompteur
WHERE
    Comptage.presenceAnomalie = 'FORTE'
GROUP BY
    Quartier.nomQuartier;

9 résultats :

nomQuartier			nombreAnomaliesFortes
Centre Ville			496
Dervallières - Zola		117
Hauts Pavés - Saint Félix	42
Malakoff - Saint-Donatien	794
Ile de Nantes			103
Nantes Nord			24
Nantes Sud			16
Pont Rousseau			74
Ragon				46

