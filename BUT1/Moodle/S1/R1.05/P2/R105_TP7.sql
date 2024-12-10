/*
TD-TP7 R1.05 - Récapitulatif Requêtes
*/

-- =============================== Exo 1 =========================
/*
Schéma relationnel
Ouvrage ( idOuvrage (1), titre, unAuteur = @Auteur.idAuteur (NN), anneeAchat )
Auteur ( idAuteur (1), nom (NN), prenom, nationalite, anneeNaissance )
Client ( idClient (1), nomClient (NN), adresse )
Emprunt ( [unClient = @Client.idClient, unOuvrage = @Ouvrage.idOuvrage](1), dateEmprunt )
*/
-----------------------------------------------------
-- Q1: Quels sont les noms et les prénoms des auteurs japonais qui sont nés avant 1970 ?
-- En Al. Rel. Auteur{nationalite='Japonais' AND anneeNaissance < 1970}[nom,prenom]
SELECT DISTINCT UPPER(nom), UPPER(prenom)
FROM Auteur
WHERE UPPER(nationalite) = 'JAPONAIS'
  AND anneeNaissance < 1970;

-----------------------------------------------------
-- Q2: Quels sont les noms portés à la fois par un auteur et par un client ?
-- En Al. Rel. Auteur[nom] INTERSECT Client[nomClient]
SELECT DISTINCT UPPER(nom) AS leNom  -- le AS est optionnel
FROM Auteur
INTERSECT
SELECT DISTINCT UPPER(nomClient) AS leNom
FROM Client;

-- 2ème solution par jointure
-- En Al. Rel. (Auteur[[nom=nomClient]]Client)[nom]
SELECT DISTINCT UPPER(nom)
FROM Auteur, Client
WHERE UPPER(nom) = UPPER(nomClient);

-----------------------------------------------------
-- Q3: Quels sont les titres des ouvrages écrits par un anglais ?
-- En Al. Rel. (Auteur[[unAuteur=idAuteur]]Ouvrage){nationalite='Anglais'}[titre]
SELECT DISTINCT UPPER(titre)
FROM Auteur, Ouvrage
WHERE unAuteur=idAuteur
  AND UPPER(nationalite) = 'ANGLAIS';

-----------------------------------------------------
-- Q4: Quels sont les noms des clients ayant emprunté l'ouvrage dont le titre est Le Petit Prince' ?
-- ((Client[[idClient = unClient]]Emprunt)[[unOuvrage=idOuvrage]]Ouvrage){titre='Le Petit Prince'}[nomClient]
SELECT DISTINCT UPPER(nomClient)
FROM Client, Emprunt, Ouvrage
WHERE idClient = unClient
AND unOuvrage = idOuvrage
  AND UPPER(titre)) = 'LE PETIT PRINCE';

-----------------------------------------------------
-- Q5: Quels sont identifiants des clients qui n'ont emprunté aucun ouvrage ?
-- En Al. Rel. Client[idClient]-Emprunt[unClient]
SELECT idClient
FROM Client
MINUS
SELECT unClient
FROM Emprunt;

-----------------------------------------------------
-- Q6: Quels sont les titres des 10 premiers ouvrages rangés par ordre alphabétique (des titres) ?
-- cf. Q16 TP5
SELECT UPPER(titre)
FROM (SELECT *
      FROM Ouvrage
      ORDER BY UPPER(titre)
      )
WHERE ROWNUM <=10;

-----------------------------------------------------
-- Q7: Quels sont les noms des auteurs qui ont écrit au moins 2 ouvrages ?
SELECT DISTINCT UPPER(nom)
FROM Auteur, Ouvrage O1, Ouvrage O2
WHERE idAuteur = O1.unAuteur
AND O1.unAuteur = O2.unAuteur
AND O1.idOuvrage != O2.idOuvrage;


-----------------------------------------------------
-- Q8: Quels sont les noms des clients qui ont emprunté les ouvrages écrit par Victor HUGO ?
SELECT DISTINCT UPPER(nomClient)
FROM Client, Emprunt, Ouvrage, Auteur
WHERE idClient = unClient
AND unOuvrage = idOuvrage
AND unAuteur = idAuteur
  AND UPPER(nom) = 'HUGO'
  AND UPPER(prenom) = 'VICTOR';

-----------------------------------------------------
-- Q9: Quels sont les identifiants et les noms des auteurs qui ont écrit les ouvrages empruntés par le client numéro 3 ou le client numéro 5 ?
SELECT idAuteur, nom  -- ici pas besoin UPPER
FROM Emprunt, Ouvrage, Auteur
WHERE idAuteur = unAuteur
AND unOuvrage = idOuvrage
  AND unClient IN (3,5);



-- ================================ Exo2 =========================
/*
Schema relationnel
-- EnseignantInfo(noEns(1), nomEns, prenom1, prenom2)
-- EtudiantInfo(noEtu(1), nomEtu, prenom, promotion, groupe)
*/

-----------------------------------------------------
-----Q10: Les noms des étudiants de 1ère année ayant le même prénom qu'un enseignant ?
-- En Al. Rel. (EtudiantInfo[[prenom=prenom1]]EnseignantInfo){promotion='INFO1'}[nomEtu]
SELECT DISTINCT UPPER(nomEtu)
FROM EtudiantInfo, EnseignantInfo
WHERE UPPER(prenom) = UPPER(prenom1)
  AND UPPER(promotion) = 'INFO1';

/*
Réponse: 1 tuple
UPPER(NOMETU)
RONARCH
*/

-----------------------------------------------------
-----Q11: Les noms des étudiants qui ont le même prénom que l'enseignant identifié par 9 ?
-- En Al. Rel. (EtudiantInfo[[prenom=prenom1]]EnseignantInfo){noEns=9}[nomEtu]
SELECT DISTINCT UPPER(nomEtu)
FROM EtudiantInfo, EnseignantInfo
WHERE UPPER(prenom) = UPPER(prenom1)
  AND noEns = 9;

/*
Réponse: 1 tuple
UPPER(NOMETU)
HYEANS
*/

-----------------------------------------------------
-----Q12: Les noms des enseignants qui ont le même prénom que l'enseignant identifié par 4 ?
-- Solution par Auto-jointure
-- En Al. Rel. (EnseignantInfo E1[[E1.prenom1=E2.prenom1]]Enseignant E2){E2.noEns=4 AND E1.noEns != 4}[E1.nomEns]
SELECT DISTINCT UPPER(E1.nomEns)
FROM EnseignantInfo E1, EnseignantInfo E2
WHERE UPPER(E1.prenom1) = UPPER(E2.prenom1)
AND E1.noEns != E2.noEns
  AND E2.noEns = 4;
-- Attention: il faut que les 2 enseignants ne soient pas la même personne
/*
Réponse: 1 tuple
UPPER(E1.NOMENS)
MERCIOL
*/

-----------------------------------------------------
-----Q13: Les noms et prenoms des étudiants dont le nom est porté par un autre ?
-- Solution par Auto-jointure
-- En Al. Rel. (EtudiantInfo E1[[E1.nomEtu=E2.nomEtu]]EtudiantInfo E2){E1.noEtu != E2.noEtu}[E1.nomEtu, E1.prenom]
SELECT DISTINCT UPPER(E1.nomEtu), UPPER(E1.prenom)
FROM EtudiantInfo E1, EtudiantInfo E2
WHERE UPPER(E1.nomEtu) = UPPER(E2.nomEtu)
AND E1.noEtu != E2.noEtu;
/*
Réponse: 6 tuples
UPPER(E1.NOMETU)	UPPER(E1.PRENOM)
X	THOMAS
X	ANTHONY
X	ENZO
X	JACK
X	THEO
X	LOUANNE
*/

-----------------------------------------------------
-----Q14: Les noms des enseignants dont le prénom n'est porté par aucun autre enseignant?
-- Solution par auto-jointure + différence
-- En Al. rel. EnseignantInfo[nomEns] - (EnseignantInfo E1[[E1.prenom1=E2.prenom1]]Enseignant E2){E1.noEns != E2.noEns}[E1.nomEns]
SELECT DISTINCT UPPER(nomEns)
FROM EnseignantInfo
MINUS
SELECT DISTINCT UPPER(E1.nomEns)
FROM EnseignantInfo E1, EnseignantInfo E2
WHERE UPPER(E1.prenom1) = UPPER(E2.prenom1)
AND E1.noEns != E2.noEns;
/*
Réponse: 14 tuples
UPPER(NOMENS)
BEAUDONT
BORNE
FLEURQUIN
GODIN
KAMP
*/

-----------------------------------------------------
-----Q15: Les 5 premiers prénoms par ordre alphabétique portés par les étudiants ?
SELECT *
FROM (SELECT DISTINCT UPPER(prenom) AS leprenom
      FROM EtudiantInfo
      ORDER BY UPPER(prenom)
     )
WHERE ROWNUM <= 5 ;

/*
Réponse: 5 tuples
LEPRENOM
X
X
X
X
X
*/

-----------------------------------------------------
-----Q16: Les 5 premiers prenoms commençant par 'S' et par ordre alphabetique portés par
-- les étudiants ou les enseignants ?
SELECT *
FROM (SELECT DISTINCT leprenom
      FROM (SELECT DISTINCT UPPER(prenom) AS leprenom
            FROM EtudiantInfo
            WHERE UPPER(prenom) LIKE 'S%'
            UNION
            SELECT DISTINCT UPPER(prenom1) AS leprenom
            FROM EnseignantInfo
            WHERE UPPER(prenom1) LIKE 'S%')
      ORDER BY leprenom )
WHERE ROWNUM <= 5 ;

/*
Réponse: 5 tupels
LEPRENOM
X
X
X
X
X
*/

-----------------------------------------------------
-----Q17: Les 5 derniers prénoms des étudiants ordonnés par ordre alphabétique des prénoms ?
SELECT *
FROM (SELECT DISTINCT UPPER(prenom) AS leprenom
      FROM EtudiantInfo
      ORDER BY UPPER(prenom) DESC )
WHERE ROWNUM <= 5 ;

/*
LEPRENOM
X
X
X
X
X
5 rows selected.
*/

-----------------------------------------------------
-----Q18: Les prénoms qui existent dans au moins 2 groupes de la prémière année ?
SELECT DISTINCT UPPER(E1.prenom)
FROM EtudiantInfo E1, EtudiantInfo E2
WHERE UPPER(E1.prenom) = UPPER(E2.prenom)
AND UPPER(E1.groupe) != UPPER(E2.groupe)
  AND E1.promotion = 'INFO1'
  AND E2.promotion = 'INFO1';
/*
Réponse: 11 tuples
UPPER(E1.PRENOM)
X
X
X
X
X
*/

-----------------------------------------------------
-----Q19: Pour ceux qui avancent bien !
