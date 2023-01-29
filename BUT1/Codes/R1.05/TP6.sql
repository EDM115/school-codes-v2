-- Question 1 --

a) Arzon[mois] × Baden[activite]
6 tuples, 2 colonnes

b) Arzon{numero > 1}[mois] × Baden[responsable]
6 tuples, 2 colonnes

-- Question 2 --

a) Arzon[mois, activite] * Baden[mois, responsable]
3 tuples, 4 colonnes
b) Arzon * Baden = Arzon[[Arzon.mois = Baden.mois ET Arzon.activite = Baden.activite]]Baden
1 tuples, 7 colonnes
c) Arzon[[Arzon.mois = Baden.mois]]Baden
3 tuples, 7 colonnes
d) (Arzon[[Arzon.activite ! = Baden.activite]]Baden)[Baden.mois]
2 tuples, 1 colonnes

-- Question 3 --

SELECT DISTINCT titre
FROM Film;

TITRE
Marius
80 chasseurs
Les sept nains
Cesar
Le moulin rouge
... 98 tuples

SELECT DISTINCT UPPER(titre)
FROM Film;

UPPER(TITRE)
CESAR
MINUIT A PARIS
LUCY
HUNGER GAMES : L EMBRASEMENT
BONS BAISERS DE RUSSIE
... 95 tuples

Il y a 3 titres en moins avec UPPER (doublons)

-- Question 4 --

SELECT *
FROM Film
WHERE anneeSortie = 2019;

UPPER(TITRE)
ALITA : BATTLE ANGEL
ONCE UPON A TIME ... IN HOLLYWOOD
AD ASTRA
... 3 tuples

-- Question 5 --

SELECT DISTINCT UPPER(titre)
FROM Film
WHERE anneeSortie >= 2017
AND anneeSortie <= 2019;

UPPER(TITRE)
PIRATES DES CARAIBES : LA VENGEANCE DE SALAZAR
MISSION IMPOSSIBLE : FALLOUT
ONCE UPON A TIME ... IN HOLLYWOOD
ALITA : BATTLE ANGEL
AD ASTRA
DUNKERQUE
... 6 tuples

-- Question 6 --

SELECT DISTINCT UPPER(nom)
FROM Personne, Acteur, Film
WHERE Acteur.unActeur = Personne.idPersonne
OR Film.leRealisateur = Personne.idPersonne;

UPPER(NOM)
MORENO
TARKOVSKY
WILLIAMS
ALLEN
ROSS
... 109 tuples

-- Question 7 --

SELECT DISTINCT UPPER(Film.titre)
FROM Film, Personne
WHERE Film.leRealisateur = Personne.idPersonne
AND UPPER(Personne.pays) = 'JAPON';

UPPER(FILM.TITRE)
LES SEPT SAMOURAIS
LA FORTERESSE CACHEE
DERSOU OUZALA
... 3 tuples

-- Question 8 --

SELECT DISTINCT UPPER(Personne.prenom), UPPER(Personne.nom)
FROM Film, Personne
WHERE Film.leRealisateur = Personne.idPersonne
AND UPPER(pays) = 'FRANCE'
AND Film.anneeSortie >= 2014
AND Film.anneeSortie <= 2020;

UPPER(PERSONNE.PRENOM)	UPPER(PERSONNE.NOM)
ZEM	                    ROSCHDY
LUC	                    BESSON
... 2 tuples

-- Question 9 --

SELECT DISTINCT UPPER(Film.titre), UPPER(Film.anneeSortie)
FROM Film, Personne
WHERE Film.leRealisateur = Personne.idPersonne
AND UPPER(Personne.nom) = 'BESSON'
AND UPPER(Personne.prenom) = 'LUC';

UPPER(FILM.TITRE)	                              UPPER(FILM.ANNEESORTIE)
LES AVENTURES EXTRAORDINAIRES D ADELE BLANC SEC	  2010
LE GRAND BLEU	                                  1988
LUCY	                                          2014
... 3 tuples

-- Question 10 --

SELECT DISTINCT Personne.nom, Personne.prenom
FROM Personne
MINUS
SELECT DISTINCT Personne.nom, Personne.prenom
FROM Personne, Film
WHERE Film.leRealisateur = Personne.idPersonne;

NOM	       PRENOM
Bardot	   Brigitte
Barnes	   Ben
Blanchett  Kate
Bloom	   Orlando
Blunt	   Groom
... 68 tuples

-- Question 11 --

SELECT DISTINCT UPPER(Personne.nom),UPPER(Personne.prenom)
FROM Personne, Acteur
WHERE Acteur.unActeur = Personne.idPersonne
AND Acteur.cachet < 10000;

UPPER(PERSONNE.NOM)	  UPPER(PERSONNE.PRENOM)
CAPRIOLO	          LEONARDO
NEIGE	              BLANCHE
MARCEAU	              SOPHIE
RAIMU	  
KUROSAWA	          AKIRA
... 10 tuples

-- Question 12 --

SELECT DISTINCT F1.titre, F2.titre
FROM Film F1, Film F2
WHERE F1.anneeSortie = F2.anneeSortie;

TITRE								TITRE
Permis de tuer						La forteresse cachee
Cesar								Cesar
Vivre et laisser mourir				Le moulin rouge
La route de Maggersfontein			Le moulin rouge
Le moulin rouge						La route de Maggersfontein
Harry Potter et l ordre du phenix	This glen is mine
Tempête dans les bourdons			Tempête dans les bourdons
Le vent soufflera deux fois			Le vent soufflera deux fois
Les Sept Samourais					Les Sept Samourais
Le moulin rouge						Dersou Ouzala
... 246 tuples

-- Question 13 --

SELECT DISTINCT UPPER(Personne.nom), UPPER(Personne.prenom)
FROM Acteur, Personne, Film
WHERE Acteur.unActeur = Personne.idPersonne
AND Acteur.unFilm = Film.code
AND UPPER(Film.titre) = 'HUNGER GAMES';

UPPER(PERSONNE.NOM)	  UPPER(PERSONNE.PRENOM)
LAWRENCE	          JENNIFER
HUTCHERSON	          JOSHUA
HEMSWORTH	          LIAM
... 3 tuples

-- Question 14 --

SELECT DISTINCT UPPER(Film.titre)
FROM Acteur, Film, Personne
WHERE Acteur.unActeur = Personne.idPersonne
AND Acteur.unFilm = Film.code
AND UPPER(Personne.nom) = 'CRUISE'
AND UPPER(Personne.prenom) = 'TOM';

UPPER(FILM.TITRE)
MISSION IMPOSSIBLE : FALLOUT
MISSION IMPOSSIBLE
OBLIVION
MISSION IMPOSSIBLE 3
MISSION IMPOSSIBLE : ROGUE NATION
MISSION IMPOSSIBLE : PROTOCOLE FANTOME
LIONS ET AGNEAUX
MISSION IMPOSSIBLE 2
... 8 tuples

-- Question 15 --

SELECT DISTINCT UPPER(P1.prenom), UPPER(P1.nom)
FROM Acteur, Personne P1, Personne P2, Film
WHERE Acteur.unFilm = Film.code
AND Film.leRealisateur = P2.idPersonne
AND Acteur.unActeur = P1.idPersonne
AND UPPER(P2.prenom) = 'WOODY'
AND UPPER(P2.nom) = 'ALLEN';

UPPER(P1.PRENOM)	UPPER(P1.NOM)
KATE	            BLANCHETT
GAD	                ELMALEH
MARION	            COTTILARD
PENELOPE	        CRUZ
WOODY	            ALLEN
... 5 tuples

-- Question 16 --

SELECT DISTINCT UPPER(Film.titre)
FROM Personne, Film, Acteur
WHERE Acteur.unFilm = Film.code
AND Acteur.unActeur = Personne.idPersonne
AND Film.leRealisateur = Acteur.unActeur;

UPPER(FILM.TITRE)
BUCK
TO ROME WITH LOVE
LA FORTERESSE CACHEE
80 CHASSEURS
LE GRAND BLEU
LA DENT BLEUE
... 6 tuples