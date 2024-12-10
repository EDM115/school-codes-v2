-- ====================================== Exo 1 =========================
/* Q1: Projection
a) Arzon[mois] --> 3 tuples
mars
novembre
janvier
b) Baden[jour,mois] --> 2 tuples
lundi - octobre
lundi - novembre
c) Arzon[numero*numero,mois]  --> 4 tuples
1 - mars
0 - novembre
4 - novembre
9 - janvier
-- --------
/* Q2: Restrictions:
a) Arzon{numero > 1} --> 2
b) Arzon{numero > 0 ET activite = 'velo'}  --> 1
c) Arzon{numero > 1 ~~OU~~ numero < 0} --> 4
d) Arzon{numero > 1}{numero < 0} --> 0
e) Arzon{(numero > 1 OU numero < 0) ET mois != 'novembre'} --> 2
*/
-- -----------
/* Q3: Restrictions et projections:
a) Arzon{numero > 1}[mois,activite] --> 2
b) Arzon{mois < 'm'}[numero] --> 1
c) Arzon[numero]{mois > 'f'} --> ERREUR
d) Arzon[numero,mois]{mois > 'f'}  --> 6
*/
-- ------------
/* Q4 Unions, intersections et différences :
a) Arzon[activite] UNIION Baden[activite] --> 3
b) Arzon[mois] INTERSECT Baden[mois] --> 1 (novembre)
c) (Arzon[mois] INTERSECT Baden[mois])[activite] --> ERREUR
d) (Arzon[mois,activite] INTERSECT Baden[mois,activite])[activite] --> 1 (velo)
e) Arzon{numero < 0}[mois,activite] \ Baden[mois,activite] --> 1 (mars,velo)
*/
-- ====================================== Exo 2 =========================

/*
- Exécutez le script baseIUTEns.sql
- Essayer de tout selectionner pour observer les données dans les 2 tables
- Sur LiveSQL, il faut aller dans Actions --> Maximum Rows Preferences
  --> Maximum Rows To Query = 5000 (pour afficher tous les tuples)
*/
SELECT * FROM EnseignantInfo;
SELECT * FROM EtudiantInfo;

------------------------------------------------------------------------
-------------------------------- Question 5: Schema relationnel
-- EnseignantInfo(noEns(1), nomEns, prenom1, prenom2)
-- EtudiantInfo(noEtu(1), nomEtu, prenom, promotion, groupe)

------------------------------------------------------------------------
-------------------------------- Question 6: Projection
-- Al. Rel: EtudiantInfo[prenom]
SELECT DISTINCT prenom
FROM EtudiantInfo;
-- Réponse: 142 rows selected.
-- (mais on voit des doublons (Clement et CleMENT), (Damien et DaMIEN)
-- Il faut utiliser la fonction UPPER pour les attributs VARCHAR2 (éviter des doublons)
SELECT DISTINCT UPPER(prenom)
FROM EtudiantInfo;
-- Réponse: 140 rows selected.


------------------------------------------------------------------------
-------------------------------- Question 7: Restriction
-- Al. Rel: EtudiantInfo{prenom = 'Louis'}
SELECT *
FROM EtudiantInfo
WHERE UPPER(prenom) = 'LOUIS';
/*
Réponse: 1 tuple
4	X	Louis	INFO1	D
*/

------------------------------------------------------------------------
-------------------------------- Question 8: Restriction
-- Al. Rel: EtudiantInfo{prenom = 'Clement' OR prenom='Pierre'}
SELECT *
FROM EtudiantInfo
WHERE UPPER(prenom) = 'CLEMENT'
OR UPPER(prenom) = 'PIERRE';
-- Réponse: 4 rows selected.
/*
9	X	Clement	INFO1	A
45	X	Clement	INFO1	A
148	X	CLeMENT	INFO2	B
155	X	Clement	INFO2	B
*/


------------------------------------------------------------------------
-------------------------------- Question 9: Restriction
-- Al. Rel: EtudiantInfo{prenom = 'mon prenom' AND promotion='INFO2'}
SELECT *
FROM EtudiantInfo
WHERE UPPER(prenom) = 'CLEMENT'
AND promotion = 'INFO2';
-- Réponse: 2 tuples (si je m'appelle Clement)
/*
148	X	CLeMENT	INFO2	B
155	X	Clement	INFO2	B
*/


------------------------------------------------------------------------
------------------------------- Question 10: Union
-- Al. Rel: EtudiantInfo[prenom] UNION EnseignantInfo[prenom1]
SELECT DISTINCT UPPER(prenom)
FROM EtudiantInfo
UNION
SELECT DISTINCT UPPER(prenom1)
FROM EnseignantInfo;
-- Réponse: 153 rows selected.
/*
...
*/

------------------------------------------------------------------------
------------------------------- Question 11: Intersection
-- Al. Rel: EtudiantInfo[nomEtu] INTERSECT EnseignantInfo[nomEns]
SELECT DISTINCT UPPER(nomEtu)
FROM EtudiantInfo
INTERSECT
SELECT DISTINCT UPPER(nomEns)
FROM EnseignantInfo;
-- Réponse: 1 tuple
-- LEMAITRE


------------------------------------------------------------------------
------------------------------- Question 12: Difference
-- Al. Rel: EnseignantInfo[prenom1]\EtudiantInfo[prenom]
SELECT DISTINCT UPPER(prenom1)
FROM EnseignantInfo
MINUS
SELECT DISTINCT UPPER(prenom)
FROM EtudiantInfo;
-- Réponse: 13 rows selected.
/*
ELODIE
HELENE
ISABELLE
JEAN FRANCOIS
LUCIE
*/


-----------------------------------------------------
-----Q13: Les noms des enseignants dont le deuxième prénom n'est pas renseigné ?
-- En Al. Rel. Enseignant{prenom=^}[nomEns]
SELECT DISTINCT UPPER(nomEns)
FROM EnseignantInfo
WHERE prenom2 IS NULL;
-- 4 rows selected.
/*
KAMP
LESUEUR
LEMAITRE
LE LAIN
*/


-----------------------------------------------------
-----Q14: Les noms et prénoms des étudiants dont le nom commence par 'A' et contient
-- une deuxième fois la lettre 'A' ?
-- En Al. Rel. EtudiantInfo{nomEtu LIKE 'A%A%'}[nomEtu,prenom]
SELECT DISTINCT UPPER(nomEtu), UPPER(prenom)
FROM EtudiantInfo
WHERE UPPER(nomEtu) LIKE 'A%A%';
-- 2 rows selected.
/*
...
*/



-----------------------------------------------------
-----Q15: Les numéros et noms des étudiants ordonnés par ordre alphabétique des noms et
-- dont le nom se termine par 'A' ?
-- En Al. Rel. EtudiantInfo{nomEtu LIKE '%A'}(nomEtu>)[noEtu,nomEtu]
SELECT noEtu, nomEtu
FROM (SELECT *
      FROM EtudiantInfo
      WHERE UPPER(nomEtu) LIKE '%A'
      ORDER BY UPPER(nomEtu)
);
-- 4 rows selected.
/*
118	X
25	X
136	X
86	X
*/

-----------------------------------------------------
-----Q16: Les 10 premiers noms des étudiants ordonnés par ordre alphabétique
-- En Al. Rel. EtudiantInfo{ROWNUM < 11}(nomEtu>)[nomEtu]
SELECT UPPER(nomEtu)
FROM (SELECT *
      FROM EtudiantInfo
      ORDER BY UPPER(nomEtu))
WHERE ROWNUM < 11;
-- 10 rows selected.
/*
...
*/
