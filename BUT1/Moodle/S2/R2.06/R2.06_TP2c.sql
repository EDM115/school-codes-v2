/*
R2.06 - TP2 - Correction
Anthony Ridard
*/

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- Q1 : Pour chaque stagiaire (id), afficher le nom et le département de l'entreprise.

-- Jointure SQL2
SELECT etudStagiaire, nomEntreprise, depEntreprise
FROM Stagiaire
	JOIN Entreprise ON entrepriseStage = idEntreprise
;

-- Jointure relationnelle
SELECT etudstagiaire, nomentreprise, depEntreprise
FROM Stagiaire, Entreprise
WHERE entreprisestage = idEntreprise;

/*

ETUDSTAGIAIRE NOMENTREPRISE                            DEPENTREPRISE
------------- ---------------------------------------- -------------
     21903169 SMURFIT KAPPA FRANCE                                56
     21900272 SOLIDR                                              56
     21901179 SOLIDR                                              56

58 lignes sélectionnées. 

*/

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- Q2 :  Pour chaque stagiaire (nom et prénom), afficher le nom et le département de l'entreprise.

-- Jointure SQL2
SELECT nomEtud, prenomEtud, nomEntreprise, depEntreprise
FROM Etudiant
	JOIN Stagiaire	ON idEtud = etudStagiaire
		JOIN Entreprise ON entrepriseStage = idEntreprise
ORDER BY nomEtud, prenomEtud
;

-- Jointure relationnelle
SELECT nomEtud, prenomEtud, nomEntreprise, depEntreprise
FROM Stagiaire, Etudiant, Entreprise
WHERE idEtud = etudstagiaire
AND entreprisestage = idEntreprise
ORDER BY nomEtud, prenometud;

/*

NOMETUD              PRENOMETUD           NOMENTREPRISE                            DEPENTREPRISE
-------------------- -------------------- ---------------------------------------- -------------
SIMAR                CEDRIC               CGI                                                 56
TRETON               PIERRE               SITIA                                               44
VIAUD                BENJAMIN             SCM VIAUD-FORMAGNE                                  56

58 lignes sélectionnées. 

*/

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- Q3 :  Pour chaque étudiant (id), afficher son entreprise (nom) si elle existe \textbf{et rien sinon}.

-- Jointure SQL2
SELECT idEtud, nomEntreprise
FROM Etudiant
	LEFT JOIN 
		(
		SELECT etudStagiaire AS numEtud, nomEntreprise -- les colonnes de l'union sont celles de la première table
		FROM Stagiaire
			JOIN Entreprise ON entrepriseStage = idEntreprise
		UNION
		SELECT etudApp, nomEntreprise
		FROM Apprenti
			JOIN Entreprise ON entrepriseApp = idEntreprise
		)
	ON idEtud = numEtud
;	

-- Jointure relationnelle
SELECT idEtud,  nomEntreprise
FROM Etudiant,
    (SELECT etudstagiaire idEtudiantEnt, nomentreprise
    FROM Stagiaire, Entreprise
    WHERE identreprise = entreprisestage
    UNION
    SELECT apprenti.etudapp idEtudiantEnt, nomentreprise
    FROM apprenti, Entreprise
    WHERE identreprise = entrepriseapp)
WHERE idEtud = idEtudiantEnt (+)
ORDER BY nomEtud;

/*

    IDETUD NOMENTREPRISE                           
---------- ----------------------------------------
  21902012                                         
  21902502                                         
  21902355                                         
  21902778                                         
  21905324                                         
  21900563                                         
  21906543                                         
  21903180                                         

107 lignes sélectionnées. 

*/

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- Q4 : Afficher les étudiants (nom et prénom) ayant réalisé un stage dans un département différent de celui du lycée.

-- Jointure SQL2
SELECT nomEtud, prenomEtud
FROM Etudiant
	JOIN Stagiaire	ON idEtud = etudStagiaire
		JOIN Entreprise ON entrepriseStage = idEntreprise
WHERE depLycee != depEntreprise
ORDER BY nomEtud, prenomEtud
;

-- Jointure relationnelle
SELECT nomEtud, prenomEtud
FROM Stagiaire, Etudiant, Entreprise
WHERE idEtud = etudstagiaire
AND entreprisestage = idEntreprise
AND depEntreprise != depLycee
ORDER BY nomEtud,prenomEtud ;

/*

NOMETUD              PRENOMETUD          
-------------------- --------------------
LAROQUE              ARTHUR              
MEAUZE               BAPTISTE            
MERIC-PONS           MATHIS              
MICHENEAU            TIMOTHE             
MIONET               PIERRE              
QUACH                VALENTIN            
QUINIOU              CHRISTOPHE          
RECOLIN              ANGELE              
VIAUD                BENJAMIN            

31 lignes sélectionnées. 

*/

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- Q5 : Afficher les apprentis (nom et prénom) suivis par Muriel Mannevy.

-- Jointure SQL2
SELECT nomEtud, prenomEtud
FROM Etudiant
	JOIN Apprenti	ON idEtud = etudApp
		JOIN Enseignant ON tuteurApp = idEns
WHERE nomEns = 'Mannevy'
ORDER BY nomEtud, prenomEtud
;

-- Jointure relationnelle
SELECT nomEtud, prenomEtud
FROM Apprenti, Etudiant, Enseignant
WHERE idEtud = etudapp
AND idEns = tuteurapp
AND UPPER(nomEns) = 'MANNEVY'
AND UPPER(prenomEns) = 'MURIEL'
ORDER BY nomEtud,prenomEtud ;

/*

NOMETUD              PRENOMETUD          
-------------------- --------------------
KOENIGS              THEO                
LE BRETON            DAN                 
LE PORS              YANIS               
MADELAINE            DYLAN               
PLOQUIN              NATHAN              
ROUILLIER            JULIEN              
TIRLEMONT            KIERIAN             

7 lignes sélectionnées. 

*/

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- Q6 : Pour chaque tuteur d'apprenti (nom), afficher les apprentis (nom et prénom).

-- Jointure SQL2
SELECT nomEns, nomEtud, prenomEtud
FROM Etudiant
	JOIN Apprenti ON idEtud = etudApp
		JOIN Enseignant ON tuteurApp = idEns
ORDER BY nomEns, nomEtud, prenomEtud
;

-- Jointure relationnelle
SELECT nomEns, nomEtud, prenomEtud
FROM Apprenti, Etudiant, Enseignant
WHERE idEtud = etudapp
AND idEns = tuteurapp
ORDER BY nomEns, nomEtud, prenomEtud ;

/*

NOMENS               NOMETUD              PRENOMETUD          
-------------------- -------------------- --------------------
Tuffigo              DESMONTS             LEO                 
Tuffigo              GUILLOUZO            BENJAMIN            

24 lignes sélectionnées.  

*/

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- Q7 : Afficher les tuteurs (nom) ayant suivi un apprenti en dehors de la Bretagne.

-- Jointure SQL2
SELECT nomEns
FROM Enseignant
	JOIN Apprenti ON idEns = tuteurApp
		JOIN Entreprise ON entrepriseApp = idEntreprise
WHERE depEntreprise NOT IN (22, 29, 35, 56)
ORDER BY nomEns
;

-- Jointure relationnelle
SELECT nomEns
FROM Apprenti, Enseignant, Entreprise
WHERE idEns = tuteurapp
AND identreprise = entrepriseapp
AND depEntreprise NOT IN (56, 35, 29, 22)
ORDER BY nomEns ;

/*

NOMENS              
--------------------
Baudont
Mannevy

*/

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- Q8 : Pour chaque enseignant (nom), afficher les apprentis (nom et prénom) en Bretagne et le département, triés dans l'ordre décroissant du département, s'ils existent \textbf{et rien sinon}.

-- Jointure SQL2
SELECT nomEns, nomEtud, prenomEtud, depEntreprise
FROM Enseignant
	LEFT JOIN 
	(
	SELECT tuteurApp, nomEtud, prenomEtud, depEntreprise
	FROM Etudiant 
		JOIN Apprenti ON idEtud = etudApp
			JOIN Entreprise ON entrepriseApp = idEntreprise
	WHERE depEntreprise IN (22, 29, 35, 56)
	)
	ON idEns = tuteurApp
ORDER BY nomEns, depEntreprise DESC
;

-- Jointure relationnelle
SELECT nomEns, nomEtud, prenomEtud, depEntreprise
FROM Enseignant, 
    (SELECT nomEns nomEnsTutBret, nomEtud, prenomEtud, depEntreprise
    FROM Apprenti, Enseignant, Entreprise, Etudiant
    WHERE idEns = tuteurapp
    AND idEtud = etudapp
    AND identreprise = entrepriseapp
    AND depEntreprise IN (56, 35, 29, 22))
WHERE nomEns = nomEnsTutBret (+)
ORDER BY nomEns, depEntreprise DESC ;

/*

NOMENS               NOMETUD              PRENOMETUD           DEPENTREPRISE
-------------------- -------------------- -------------------- -------------
Tuffigo              GUILLOUZO            BENJAMIN                        35
Volin                                                                       

35 lignes sélectionnées. 

*/

-- On peut éviter la jointure (externe) avec la sous-requête

SELECT nomEns, nomEtud, prenomEtud, depEntreprise
FROM Enseignant
	LEFT JOIN Apprenti ON idEns = tuteurApp
		LEFT JOIN Etudiant ON etudApp = idEtud
			LEFT JOIN Entreprise ON entrepriseApp = idEntreprise
WHERE depEntreprise IN (22, 29, 35, 56)	 OR depEntreprise IS NULL
ORDER BY nomEns, depEntreprise DESC
;

/*

NOMENS               NOMETUD              PRENOMETUD           DEPENTREPRISE
-------------------- -------------------- -------------------- -------------
Tuffigo              DESMONTS             LEO                             35
Volin                                                                       

35 lignes sélectionnées. 

*/

-- attention, un seul LEFT ne suffit pas !

SELECT nomEns, nomEtud, prenomEtud, depEntreprise
FROM Enseignant
	LEFT JOIN Apprenti ON idEns = tuteurApp
		JOIN Etudiant ON etudApp = idEtud
			JOIN Entreprise ON entrepriseApp = idEntreprise
WHERE depEntreprise IN (22, 29, 35, 56)	OR depEntreprise IS NULL
ORDER BY nomEns, depEntreprise DESC
;

/*

NOMENS               NOMETUD              PRENOMETUD           DEPENTREPRISE
-------------------- -------------------- -------------------- -------------
Mannevy              LE PORS              YANIS                           56
Mannevy              LE BRETON            DAN                             56
Mannevy              MADELAINE            DYLAN                           56
Mannevy              PLOQUIN              NATHAN                          35
Mannevy              KOENIGS              THEO                            35
Mannevy              TIRLEMONT            KIERIAN                         35
Ridard               FAUGERON             LUCAS                           56
Ridard               HERARD               THIBAULT                        35
Tuffigo              ADAM                 ANTOINE                         56
Tuffigo              GUILLOUZO            BENJAMIN                        35
Tuffigo              DESMONTS             LEO                             35

22 lignes sélectionnées. 

*/

-- attention, on ne peut pas se passer du "OR depEntreprise IS NULL" !

SELECT nomEns, nomEtud, prenomEtud, depEntreprise
FROM Enseignant
	LEFT JOIN Apprenti ON idEns = tuteurApp
		LEFT JOIN Etudiant ON etudApp = idEtud
			LEFT JOIN Entreprise ON entrepriseApp = idEntreprise
WHERE depEntreprise IN (22, 29, 35, 56)
ORDER BY nomEns, depEntreprise DESC
;

/*

NOMENS               NOMETUD              PRENOMETUD           DEPENTREPRISE
-------------------- -------------------- -------------------- -------------
Mannevy              LE PORS              YANIS                           56
Mannevy              LE BRETON            DAN                             56
Mannevy              MADELAINE            DYLAN                           56
Mannevy              PLOQUIN              NATHAN                          35
Mannevy              KOENIGS              THEO                            35
Mannevy              TIRLEMONT            KIERIAN                         35
Ridard               FAUGERON             LUCAS                           56
Ridard               HERARD               THIBAULT                        35
Tuffigo              ADAM                 ANTOINE                         56
Tuffigo              GUILLOUZO            BENJAMIN                        35
Tuffigo              DESMONTS             LEO                             35

22 lignes sélectionnées. 

*/

-- retour sur la solution avec des jointures externes relationnelles

SELECT nomEns,nomEtud, prenomEtud, depEntreprise
FROM Enseignant, Apprenti, Etudiant, Entreprise
WHERE idEns = tuteurApp(+)
	AND etudApp = idEtud(+)
    AND entrepriseApp = idEntreprise(+)   
AND (depEntreprise IN (56, 22, 29, 35) OR depEntreprise IS NULL)
ORDER BY nomEns, depEntreprise DESC
; 

-- attention, il ne faut pas oublier les parenthèses !

SELECT nomEns,nomEtud, prenomEtud, depEntreprise
FROM Enseignant, Apprenti, Etudiant, Entreprise
WHERE idEns = tuteurApp(+)
	AND etudApp = idEtud(+)
    AND entrepriseApp = idEntreprise(+)   
AND depEntreprise IN (56, 22, 29, 35) OR depEntreprise IS NULL
ORDER BY nomEns, depEntreprise DESC
; 

/*

Rapport d'erreur -
Erreur SQL : ORA-01719: outer join operator (+) not allowed in operand of OR or IN
01719. 00000 -  "outer join operator (+) not allowed in operand of OR or IN"
*Cause:    An outer join appears in an or clause.
*Action:   If A and B are predicates, to get the effect of (A(+) or B),
           try (select where (A(+) and not B)) union all (select where (B)).

*/

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- Q9 : Afficher les étudiants (nom et prénom)  qui ont travaillé dans le Morbihan et qui ont poursuivi leurs études à l'ENSIBS.

SELECT nomEtud, prenomEtud
FROM Etudiant
	LEFT JOIN Apprenti ON idEtud = etudApp
		LEFT JOIN Stagiaire ON idEtud = etudStagiaire
			JOIN Entreprise ON (idEntreprise = entrepriseApp OR idEntreprise = entrepriseStage)
WHERE depEntreprise = 56
AND UPPER(poursuiteEtudes) LIKE '%ENSIBS%'
ORDER BY nomEtud, prenomEtud
;

/*



*/

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- Q10 : Afficher les enseignants (nom) à la fois tuteur de groupe en Info1 et tuteur d'apprenti.

SELECT DISTINCT nomEns
FROM Enseignant, Apprenti, GroupeInfo1
WHERE idEns = tuteurApp
AND idEns = tuteurGroupe
;

/*



*/

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- Q11 : Afficher le nombre d'étudiants venant d'un lycée breton.

SELECT COUNT(*)
FROM Etudiant
WHERE depLycee IN (56, 35, 22, 29)
;

/*



*/

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- Q12 : Afficher le nombre de poursuites d'études renseignées.

SELECT COUNT(poursuiteEtudes)
FROM Etudiant
;

/*



*/

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- Q13 : Afficher le nombre de tuteurs pour l'ensemble des apprentis.

SELECT COUNT(DISTINCT tuteurApp)
FROM Apprenti
;

/*



*/

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- Q14 : Afficher le nombre d'apprentis suivis par Pascal Baudont dans le Morbihan.

SELECT COUNT(*)
FROM Apprenti, Enseignant, Entreprise
WHERE idEns = tuteurApp 
	AND entrepriseApp = idEntreprise
AND UPPER(nomEns) = 'BAUDONT'
AND UPPER(prenomEns) = 'PASCAL'
AND depEntreprise = 56
;

/*



*/

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- Q15 : Afficher la proportion d'étudiants passés en deuxième année.

SELECT COUNT(parcoursInfo2)/COUNT(*) propEtudDeuxAns
FROM Etudiant
;

/*



*/

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- Q16 : Afficher la proportion d'enseignants ayant suivi un apprenti. 

SELECT COUNT(DISTINCT TuteurApp)/COUNT(DISTINCT idEns) propEnsAvecApp
FROM Enseignant, Apprenti
;

/*



*/

