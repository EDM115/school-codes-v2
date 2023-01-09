/*
TP6 R1.05 - Algèbre relationnelle et Requête SQL Produit and Jointure
*/

-- Le schéma relationnel
/*
Film (code(1),titre, leRealisateur = @Personne[idPersonne], anneeSortie, duree)
Personne(idPersonne(1), nom, prenom, pays, dateNaissance)
Acteur((unActeur = @Personne[idPersonne], unFilm = @Film[code])(1), cachet)
*/

------------------------------------------------------------------------
---------- Q3: Quels sont les titres des films ?
-- En al. rel. Film[titre]
SELECT DISTINCT UPPER(titre)
FROM Film;
-- Reponse: 95 rows selected.
/*
UPPER(TITRE)
CESAR
MINUIT A PARIS
LUCY
HUNGER GAMES : L EMBRASEMENT
BONS BAISERS DE RUSSIE
*/

-- Remarque: si pas de UPPER --> 98 rows selected.

------------------------------------------------------------------------
---------- Q4: Quels sont les films sortis en 2019 ?
-- Al. rel: Film{anneeSortie = 2019}
SELECT *
FROM Film
WHERE anneeSortie = 2019;
/*
Réponse: 3 tuples
CODE	TITRE	LEREALISATEUR	ANNEESORTIE	DUREE
111	Alita : Battle Angel	140	2019	122
112	Once Upon a Time ... in Hollywood	150	2019	161
113	AD ASTRA	160	2019	124
*/


------------------------------------------------------------------------
---------- Q5: Quels sont les titres des films sortis entre 2017 et 2019 ?
-- Al. rel: Film{anneeSortie >= 2017 AND anneeSortie <= 2019 }[titre]
SELECT DISTINCT UPPER(titre)
FROM Film
WHERE anneeSortie >= 2017
AND anneeSortie <= 2019;
/*
Réponse: 6 tuples
PIRATES DES CARAIBES : LA VENGEANCE DE SALAZAR
MISSION IMPOSSIBLE : FALLOUT
ONCE UPON A TIME ... IN HOLLYWOOD
ALITA : BATTLE ANGEL
AD ASTRA
DUNKERQUE
*/

------------------------------------------------------------------------
---------- Q6: Quels sont les noms des personnes qui sont réalisateurs ou acteurs ?
-- On peut découper la question :
-- 6a) Les noms des personnes qui sont acteurs:
-- En al. rel. (Personne[[idPersonne=unActeur]]Acteur)[nom]
SELECT DISTINCT UPPER(nom)
FROM Personne, Acteur
WHERE idPersonne = unActeur;

-- 6b) Les noms des personnes qui sont réalisateurs:
-- En al. rel. (Personne[[idPersonne=leRealisateur]]Film)[nom]
SELECT DISTINCT UPPER(nom)
FROM Personne, Film
WHERE idPersonne = leRealisateur;

-- 6) est l'UNION de 6a et 6b
SELECT DISTINCT UPPER(nom)
FROM Personne, Acteur
WHERE idPersonne = unActeur
UNION
SELECT DISTINCT UPPER(nom)
FROM Personne, Film
WHERE idPersonne = leRealisateur;
-- Réponse: 109 rows selected.
/*
UPPER(NOM)
ABRAMS
ADAMSON
ALLEN
APTED
BARNES
*/
---- Une deuxième solution alternative avec la jointure des 3 tables (plus efficace)
SELECT DISTINCT UPPER(nom)
FROM Personne, Acteur, Film
WHERE idPersonne = unActeur
OR idPersonne = leRealisateur;
-- Réponse: 109 rows selected.
/*
UPPER(NOM)
KUROSAWA
HOWARD
CRAIG
RADCLIFFE
RHAMES
*/

-- On remarque que les 2 solutions donnent le même résultat mais l'ordre des auteurs
-- est différent (pourquoi ?)


------------------------------------------------------------------------
---------- Q7: Quels sont les titres des films réalisés par un japonais (pays='Japon') ?
-- En al. rel. (Personne[[idPersonne = leRealisateur]]Film){pays = 'Japon'}[titre]
SELECT DISTINCT UPPER(titre)
FROM Personne, Film
WHERE idPersonne = leRealisateur
AND UPPER(pays) = 'JAPON';
/*
3 tuples
UPPER(TITRE)
LES SEPT SAMOURAIS
LA FORTERESSE CACHEE
DERSOU OUZALA
*/


------------------------------------------------------------------------
---------- Q8: Quels sont noms et prénoms des réalisateurs français ayant sorti
--des films entre 2014 et 2020 ?
-- (Personne[[idPersonne = leRealisateur]]Film){pays = 'France' AND anneeSortie >= 2014 AND anneeSortie >= 2020}[nom, prenom]
SELECT DISTINCT UPPER(nom), UPPER(prenom)
FROM Personne, Film
WHERE idPersonne = leRealisateur
AND UPPER(pays) = 'FRANCE'
AND anneeSortie >= 2014
AND anneeSortie <= 2020;
/*
2 tuples
UPPER(NOM)	UPPER(PRENOM)
BESSON	LUC
ROSCHDY	ZEM
*/


------------------------------------------------------------------------
---------- Q9: Quels sont les titres et années sorties des films tournés par le réalisateur Luc Besson ?
-- En al. rel. (Personne[[idPersonne = leRealisateur]]Film){nom='Besson', prenom='Luc'}[titre, anneeSortie]
SELECT DISTINCT UPPER(titre), anneeSortie
FROM Film, Personne
WHERE idPersonne = leRealisateur
AND UPPER(nom) = 'BESSON'
AND UPPER(prenom) = 'LUC';
/*
3 tuples
UPPER(TITRE)	ANNEESORTIE
LUCY	2014
LE GRAND BLEU	1988
LES AVENTURES EXTRAORDINAIRES D ADELE BLANC SEC	2010
*/


------------------------------------------------------------------------
---------- Q10: Quels sont les noms et prénoms des personnes qui ne sont pas réalisateurs ?
-- Il faut chercher les réalisateurs puis on fait la différence Personne - réalisateurs
-- Personne[nom, prenom] - (Personne[[idPersonne=leRealisateur]]Film)[nom, prenom]
SELECT DISTINCT UPPER(nom), UPPER(prenom)
FROM Personne
MINUS
SELECT DISTINCT UPPER(nom), UPPER(prenom)
FROM Personne, Film
WHERE idPersonne = leRealisateur;
-- Réponse: 68 rows selected.
/*
UPPER(NOM)	UPPER(PRENOM)
BARDOT	BRIGITTE
BARNES	BEN
BLANCHETT	KATE
BLOOM	ORLANDO
BLUNT	GROOM
*/

------------------------------------------------------------------------
---------- Q11: Noms et prénoms des acteurs ayant un cachet moins de 10000 ?
-- En al. rel. (Personne[[idPersonne=unActeur]]Acteur){cachet<10000}[nom, prenom]
SELECT DISTINCT UPPER(nom), UPPER(prenom)
FROM Personne, Acteur
WHERE idPersonne = unActeur
AND cachet < 10000;
-- Réponse: 10 rows selected.
/*
UPPER(NOM)	UPPER(PRENOM)
CAPRIOLO	LEONARDO
NEIGE	BLANCHE
MARCEAU	SOPHIE
RAIMU
KUROSAWA	AKIRA
*/

------------------------------------------------------------------------
---------- Q12: Les titres des 2 films qui sortent dans la même année ?
SELECT DISTINCT UPPER(F1.titre), UPPER(F2.titre)
FROM Film F1, Film F2
WHERE F1.anneeSortie = F2.anneeSortie
AND UPPER(F1.titre) < UPPER(F2.titre) ;
-- Réponse: 72 rows selected.
/*
UPPER(F1.TITRE)	UPPER(F2.TITRE)
BONS BAISERS DE RUSSIE	MISSION IMPOSSIBLE
CARTEL	HUNGER GAMES
BLUE JASMINE	HUNGER GAMES : L EMBRASEMENT
LE VENT SOUFFLERA DEUX FOIS	VIVRE ET LAISSER MOURIR
HARRY POTTER ET LA CHAMBRE DES SECRETS	MEURS UN AUTRE JOUR
*/
-- Remarque si on écrit UPPER(F1.titre) != UPPER(F2.titre) --> on obtient 2 fois
---- pour chaque coupe donc 144 tuples.


------------------------------------------------------------------------
---------- Q13: Les noms et prénoms des acteurs ayant joué dans le film 'Hunger Games' ?
SELECT DISTINCT UPPER(nom), UPPER(prenom)
FROM Film, Personne, Acteur
WHERE idPersonne = unActeur
AND unFilm = code
AND UPPER(titre) = 'HUNGER GAMES' ;
/*
Reponse: 3 tuples
UPPER(NOM)	UPPER(PRENOM)
LAWRENCE	JENNIFER
HUTCHERSON	JOSHUA
HEMSWORTH	LIAM
*/


------------------------------------------------------------------------
---------- Q14: Les titres des films dans lesquels joué l'acteur Tom Cruise ?
SELECT DISTINCT UPPER(titre)
FROM Film, Personne, Acteur
WHERE idPersonne = unActeur
AND unFilm = code
AND UPPER(nom) = 'CRUISE'
AND UPPER(prenom) = 'TOM';
-- Réponse: 8 rows selected.
/*
UPPER(TITRE)
MISSION IMPOSSIBLE : FALLOUT
MISSION IMPOSSIBLE
OBLIVION
MISSION IMPOSSIBLE 3
MISSION IMPOSSIBLE : ROGUE NATION
*/

------------------------------------------------------------------------
---------- Q15: Les noms et prénoms des acteurs qui ont joué dans des films avec Woody Allen comme réalisateur ?
-- On a besoin de joindre des 3 tables
-- On a besoin d'une personne P1 (acteur) et une autre personne P2 (Réalisateur)
SELECT DISTINCT UPPER(P1.nom), UPPER(P1.prenom)
FROM Film, Acteur, Personne P1, Personne P2
WHERE P1.idPersonne = unActeur
AND P2.idPersonne = leRealisateur
AND unFilm = code
AND UPPER(P2.nom) = 'ALLEN'
AND UPPER(P2.prenom) = 'WOODY' ;
/*
5 tuples
UPPER(P1.NOM)	UPPER(P1.PRENOM)
COTTILARD	MARION
ELMALEH	GAD
BLANCHETT	KATE
ALLEN	WOODY
CRUZ	PENELOPE
*/

------------------------------------------------------------------------
---------- Q16: Les titres des films où le réalisateur est aussi acteur ?
SELECT DISTINCT UPPER(titre)
FROM Film, Personne, Acteur
WHERE idPersonne = unActeur
AND idPersonne = leRealisateur
AND unFilm = code;
-- Réponse : 6 rows selected.
/*
UPPER(TITRE)
BUCK
TO ROME WITH LOVE
LA FORTERESSE CACHEE
80 CHASSEURS
LE GRAND BLEU
LA DENT BLEUE
*/

------------------------------------------------------------------------
---------- Q17: pour ce qui avancent bien
