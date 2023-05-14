-- Sélectionner le numéro de la boucle, le libelle et le nombre de total de vélos de la table ComptageVelo où le nombre total de vélos comptabilisé sur une journée est supérieur à 10000 :
SELECT boucle_num, libelle, total
FROM ComptageVelo
WHERE total > 10000;
/*
5 tuples

BOUCLE_NUM   LIBELLE                             TOTAL
677          Pont Willy Brandt vers Malakoff     32924
681          Stalingrad vers est                 10575
786          50 Otages Vers Nord                 19251
786          50 Otages Vers Nord                 40465
786          50 Otages Vers Nord                 12436
*/


-- Sélectionner le nombre total de vélos enregistrés pour le capteur 664 le 10 janvier 2020
SELECT total
FROM ComptageVelo
WHERE boucle_num = 664 AND jour = TO_DATE('10-01-2020', 'DD-MM-YYYY');
/*
1 tuple

TOTAL
1049
*/


-- Sélectionner les libelle qui sont dans la table ComptageVelo ou dans la table GeolocalisationCompteur
SELECT libelle
FROM ComptageVelo
UNION
SELECT libelle
FROM GeolocalisationCompteur;
/*
52 tuples

LIBELLE
50 Otages Vers Nord
50 Otages Vers Sud
Bd Malakoff vers Gare Sud
Bonduelle vers Nord
Bonduelle vers sud
...
*/


-- Sélectionner les libelle qui sont dans la table ComptageVelo mais pas dans la table GeolocalisationCompteur
SELECT libelle
FROM ComptageVelo
MINUS
SELECT libelle
FROM GeolocalisationCompteur;
/*
10 tuples

LIBELLE
Bonduelle vers Nord
Chemin de halage TortiÃ¨re vers Nord
Chemin de halage TortiÃ¨re vers Sud
De Gaulle sortie ClÃ©menceau vers Nord
EntrÃ©e pont Audibert vers Nord
...
*/


-- Sélectionner les noms et les aménagements cyclables des quartiers de nantes qui ont un aménagement cyclable de moins de 8000 mètres
SELECT quartier, amenagement_cyclable
FROM QuartiersNantes, LongueurPistesVelo
WHERE identifiant = code_quartier
AND amenagement_cyclable < 8000;
/*
6 tuples

QUARTIER                AMENAGEMENT_CYCLABLE 
Deravallières - Zola    4403,09
Trentemoult             7854,51939
Hôtel de Ville          4808,84936
Château de Rezé         6371,53907
Pont Rousseau           7628,89349
Blordière               4688,33283
*/


-- Sélectionner les libelles des numéros de boucles et la températue moyenne des boucles qui ont déjà eu une température inférieur à 2 degrés et plus de 3000 vélos comptabilisés sur une journée
SELECT libelle, tMoy
FROM ComptageVelo, Temperature
WHERE jour = laDate
AND tMoy < 2
AND total > 3000;
/*
2 tuples

LIBELLE                TMOY
50 Otages Vers Sud     1.08
50 Otages Vers Nord    1.08
*/


-- Sélectionner le noms et le total des 10 premiers libelles (par ordre croissant du nom) qui ont comptabilisés le plus de cyclistes sur une journée
SELECT *
FROM (
    SELECT libelle, total
    FROM ComptageVelo
    ORDER BY total DESC
)
WHERE ROWNUM <= 10;
/*
10 tuples

LIBELLE                          TOTAL
50 Otages Vers Nord              40465
Pont Willy Brandt vers Malakoff  32924
50 Otages Vers Nord              19251
50 Otages Vers Nord              12436
Stalingrad vers est              10575
...
*/


-- Pour chaque numero (dans l'ordre croissant), afficher la valeur de la probabilité de présence d'anomalie et rien sinon
SELECT DISTINCT numero, probabilite_presence_anomalie
FROM GeolocalisationCompteur
    LEFT JOIN ComptageVelo ON numero = boucle_num
ORDER BY numero;
/*
140 tuples

NUMERO      PROBABILITE_PRESENCE_ANOMALIE
664         Faible
664         Forte
664 
666         Faible
666         Forte
...
*/


-- Pour chaque quartiers de nantes (identifié par son nom et trié par ordre croissant), afficher les observations et rien sinon
SELECT DISTINCT quartier, observations
FROM QuartiersNantes
    LEFT JOIN GeolocalisationCompteur ON idQuartier = identifiant
ORDER BY quartier;
/*
18 tuples

QUARTIER                                            OBSERVATIONS
Bellevue - Chantenay - Sainte Anne
Blordière
Breil - Barberie
Centre Ville
Château de Rezé
*/


-- Afficher le nombre de fois que le compteur 664 a comptabilisé plus de 1500 vélos en une journée
SELECT COUNT(*)
FROM ComptageVelo
WHERE boucle_num = 664
AND total > 1000;
/*
COUNT(*)
5
*/


-- Afficher le nombre de quartiers qui ont un aménagement cyclable de plus de 10000 mètres
SELECT COUNT(*)
FROM LongueurPistesVelo
WHERE amenagement_cyclable > 10000;
/*
COUNT(*)
11
*/


-- Pour chaque numéro de boucle ordonné par ordre croissant, afficher le nombre de jours où le nombre de vélos comptabilisé en une journée est supérieur à 1500
SELECT boucle_num, COUNT(*) nbVelos
FROM ComptageVelo
WHERE total > 1500
GROUP BY boucle_num
ORDER BY boucle_num;
/*
20 tuples

BOUCLE_NUM    NBVELOS
664           5
667           675
672           16
677           1
679           1
...
*/


-- Pour chaque jour de la semaine (dans l'ordre croissant), afficher le nombre de fois que la probabilité de présence d'anomalie vaut 'forte'
SELECT jour_de_la_semaine, COUNT(probabilite_presence_anomalie) anomalieForte
FROM COMPTAGEVELO
WHERE UPPER(probabilite_presence_anomalie) LIKE '%FORTE%'
GROUP BY jour_de_la_semaine
ORDER BY jour_de_la_semaine;
/*
7 tuples

JOUR_DE_LA_SEMAINE      ANOMALIEFORTE
1                       241
2                       238
3                       233
4                       225
5                       247
6                       255
7                       280
*/


-- Afficher le numéro et le total du numéro de boucle qui a le record de nombre de vélos comptabilisé en une journée
SELECT boucle_num, MAX(total) nbVelos
FROM ComptageVelo
GROUP BY boucle_num
HAVING MAX(total) = (
    SELECT MAX(total)
    FROM ComptageVelo
);
/*
1 tuple

BOUCLE_NUM    NBVELOS
786           40465
*/


-- Afficher le jour de la semaine qui a le plus de fois une probabilité de présence d'anomalie 'forte'
SELECT jour_de_la_semaine
FROM COMPTAGEVELO
WHERE UPPER(probabilite_presence_anomalie) LIKE '%FORTE%'
GROUP BY jour_de_la_semaine
HAVING COUNT(probabilite_presence_anomalie) = (
    SELECT MAX(nbAnomalie)
    FROM (
        SELECT jour_de_la_semaine, COUNT(probabilite_presence_anomalie) nbAnomalie
        FROM COMPTAGEVELO
        WHERE UPPER(probabilite_presence_anomalie) LIKE '%FORTE%'
        GROUP BY jour_de_la_semaine
    )
);
/*
1 tuple

JOUR_DE_LA_SEMAINE
7
*/


-- afficher les libelle de boucle (dans l'ordre alphabétique) qui ne sont pas associés à un quartier
SELECT DISTINCT libelle
FROM GeolocalisationCompteur
WHERE EXISTS (
    SELECT idQuartier
    FROM GeolocalisationCompteur
    MINUS
    SELECT identifiant
    FROM QuartiersNantes
    WHERE idQuartier = identifiant
)
ORDER BY libelle;
/*
42 tuples

LIBELLE
avenue de la Libération vers Nord
avenue de la Libération vers Sud
Bd Malakoff vers Gare Sud
Bonduelle vers sud
Calvaire vers Est
...
*/


-- Afficher le libelle des boucles 664, 665, 666 et 667
SELECT DISTINCT libelle
FROM ComptageVelo
WHERE boucle_num IN (664, 665, 666, 667);
/*
4 tuples

LIBELLE
Bonduelle vers Nord
EntrÃ©e pont Audibert vers Nord
Bonduelle vers sud
Pont Audibert vers Sud
*/


-- Afficher les libelle de boucle (dans l'ordre alphabétique) qui ne sont pas associés à un quartier
SELECT DISTINCT libelle
FROM GeolocalisationCompteur
WHERE NOT EXISTS (
    SELECT idQuartier
    FROM QuartiersNantes
    WHERE idQuartier = identifiant
)
ORDER BY libelle;
/*
0 tuples
*/