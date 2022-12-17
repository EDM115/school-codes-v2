/*
R1.05 - Période 2 - TP5
Ridard Anthony
*/


----------------
-- Exercice 2 --
----------------

-- Q5 : ́Ecrivez les schémas relationnels des tables EnseignantInfo et EtudiantInfo.

/*

EnseignantInfo
	(
	noEnseignant : int (1) ,
	nom : string ,
	prenom1 : string ,
	prenom2 : string
	)
	
EtudiantInfo
	(
	noEtudiant : int (1) ,
	nom : string ,
	prenom : string ,
	promotion : string ,
	groupe : string
	)
	
*/


-- Q6 : Quels sont les prénoms des étudiants ?

-- EtudiantInfo[prenom]

SELECT DISTINCT UPPER(prenom)
FROM EtudiantInfo
;

/*

UPPER(PRENOM)                 
------------------------------
MATHIEU
MATTIS
SAM
LEO
MEWENN
COLIN
FELIX
LIAM

140 lignes sélectionnées. 

*/


-- Q7 : Quels sont les étudiants prénommés “Louis” ?
