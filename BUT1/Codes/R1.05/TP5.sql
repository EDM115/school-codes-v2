-- Exercice 1 --

/*
1 -
a) 3	b) 2	c) 4

2 -
a) 2	b) 1	c) 4	d) 0	e) 2

3 -
a) 2	b) 1	c) ERREUR	d) 6

4 -
a) 3	b) 1	c) ERREUR	d) 1	e) 1
*/

-- Exercice 2 --

-- Question 6 --

-- EtudiantInfo[prenom] --
SELECT DISTINCT prenom FROM EtudiantInfo;

-- Question 7 --

-- EtudiantInfo{prenom = 'Louis'} --
SELECT * FROM EtudiantInfo WHERE UPPER(prenom) = 'LOUIS';

-- Question 8 --

-- EtudiantInfo{prenom = 'Louis' OR prenom = 'Pierre'} --
SELECT * FROM EtudiantInfo WHERE UPPER(prenom) = 'CLEMENT' OR UPPER(prenom) = 'PIERRE';

-- Question 9 --

-- EtudiantInfo{prenom = 'X'} --
SELECT * FROM EtudiantInfo WHERE UPPER(prenom) = 'X';

-- Question 10 --

SELECT DISTINCT prenom1, prenom FROM EnseignantInfo, EtudiantInfo;

-- Question 11 --

SELECT DISTINCT nomEns, nomEtu FROM EnseignantInfo, EtudiantInfo WHERE (nomEns = nomEtu);
-- no rows selected

-- Question 12

SELECT DISTINCT prenom1 FROM EnseignantInfo MINUS SELECT DISTINCT prenom FROM EtudiantInfo;
-- Elodie, Helene, Isabelle, Jean Francois, Lucie, Minh Tan, Nicolas, Pascal, Philippe, Regis, Sebastien, Thibault, Xavier

-- Question 13

SELECT nomEns FROM EnseignantInfo WHERE (prenom2 IS NULL);
-- KAmP, Lesueur, LE Lain, LeMaitre

-- Question 14

SELECT nomEtu, prenom FROM EtudiantInfo WHERE (UPPER(nomEtu) LIKE 'A%') AND (UPPER(nomEtu) LIKE '%A%');

-- Question 15

SELECT nomEtu, noEtu FROM EtudiantInfo WHERE (UPPER(nomEtu) LIKE '%A') ORDER BY nomEtu, prenom;

-- Question 16

SELECT nomEtu FROM EtudiantInfo ORDER BY nomEtu, prenom;
