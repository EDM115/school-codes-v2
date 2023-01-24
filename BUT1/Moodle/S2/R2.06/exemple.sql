/*
R2.06 - TP2 - 31/01/22
Ridard Anthony - Groupe D
*/

-- Q1 : Afficher les pilotes (nom)

SELECT nomPilote
FROM Pilote
;

/*

NOMPILOTE           
--------------------
Ridard
Naert
Godin
Fleurquin
Pham
Kerbellec
Kamp

7 lignes sélectionnées. 

*/

-- Q2 : Pour chaque pilote (nom), afficher les qualifications

SELECT nomPilote, unTypeAvion
FROM Pilote, Qualification
WHERE idPilote = unPilote
;

/*

NOMPILOTE            UNTYPEAVION         
-------------------- --------------------
Kamp                 B747                

12 lignes sélectionnées. 

*/
 

 
 

