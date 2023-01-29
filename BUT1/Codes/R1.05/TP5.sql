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
-- Jean Gauthier Matheo Melen Matthys Dorian Mateo Ronan Michael Marius Romain Nael Matteo Peter Samuel Baki Ewen Gwenvael Paul-Antoine Alice Lenny Jean-Loup Elise Ayoub Sophie Mattis Benjamin Stevan Efflam William Jack Flavien Augustin Deborah Alan Youenn Yohann Anatole Ilies Theo Clemence Thomas Lussandre Jules Serge Yanis Enzo Mewenn Nolann Yann-Mael Elvin Liam Elouann Andy Paul Lucas Swann Achraf Oscar Elouan Mathieu Titouan Matis Nolan Florian Corentin Asaiah Foidjou Gabin Mathis Simon Eliott Briac Guillaume Malo Mael Amelie Jonathan Jeremy Sam Anthony Eve-Anne Arwen DaMIen Timothee Raphael Louis Nathan Clement Esteban Camille Baptiste Axel Melia Neo Gabriel Guyaume Louanne Aymeric Goulven Tobias Matthieu CLeMENT Jessica Francois Tom Tanguy Evy Noe Hugo Gatien Charlie Tristan Brieuc Erwan Damien Ludovic Leo Chadene Colin Felix Anna Pierre-Jean Samy Awen Alexandre Sylvain Theophile Alexis Maxime Gianni Julien Gwendal Allan Dewi Louis-Marie Killian Matiss Loris Ervan Hamza Emy --

-- Question 7 --

-- EtudiantInfo{prenom = 'Louis'} --
SELECT * FROM EtudiantInfo WHERE UPPER(prenom) = 'LOUIS';
-- 4, BARRIAC, Louis, INFO1, D --

-- Question 8 --

-- EtudiantInfo{prenom = 'Louis' OR prenom = 'Pierre'} --
SELECT * FROM EtudiantInfo WHERE UPPER(prenom) = 'CLEMENT' OR UPPER(prenom) = 'PIERRE';
/*
9, BELLIER, Clement, INFO1, A
45, HOUSSIN--VONTHRON, Clement, INFO1, A
148, MAILLET, CLeMENT, INFO2, B
155, MONFORT, Clement, INFO2, B
*/

-- Question 9 --

-- EtudiantInfo{prenom = 'Lussandre'} --
SELECT * FROM EtudiantInfo WHERE UPPER(prenom) = 'LUSSANDRE';
-- 56,LE DERREY,Lussandre,INFO1,A

-- Question 10 --

SELECT DISTINCT prenom1, prenom FROM EnseignantInfo, EtudiantInfo;
-- PRENOM1,PRENOM Jean Francois,Deborah Jean Francois,Louis Jean Francois,Jean Jean Francois,Gauthier Jean Francois,Yohann Jean Francois,Swann Jean Francois,Theo Jean Francois,Maxime Jean Francois,Gianni Jean Francois,Melen Jean Francois,Damien Jean Francois,Dorian Jean Francois,Ludovic Jean Francois,Aymeric Jean Francois,Samuel Jean Francois,Baki Jean Francois,Pierre-Jean Jean Francois,Andy Pascal,Clement Pascal,Alexis Pascal,Gianni Pascal,Matheo Pascal,Lussandre Pascal,Neo Pascal,Damien Pascal,Jonathan Pascal,Aymeric Pascal,Marius Pascal,Loris Pascal,William Pascal,Tom Pascal,DaMIen Pascal,Andy Pascal,Augustin Thibault,Alan Thibault,Jean Thibault,Alexandre Thibault,Noe Thibault,Paul Thibault,Gauthier Thibault,Melia Thibault,Matheo Thibault,Julien Thibault,Guillaume Thibault,Damien Thibault,Dorian Thibault,Elise Thibault,Elouan Thibault,Sophie Thibault,Mattis Thibault,Benjamin Thibault,Anthony Thibault,Enzo Thibault,Samuel Francois,Samy Francois,Corentin Francois,Alexandre Francois,Paul-Antoine Francois,Sylvain Francois,Foidjou Francois,Melia [2 414 rows]

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
-- AGBOGBO (BASSON),Deborah  AIME,Raphael  ANNASRI,Samy  ANTONIO,Emy  AUGER,Mateo

-- Question 15

SELECT nomEtu, noEtu FROM EtudiantInfo WHERE (UPPER(nomEtu) LIKE '%A') ORDER BY nomEtu, prenom;
-- COSTAMAGNA,118  DA ROCHA,25  JOMAA,136  PINTO DA SILVA,86

-- Question 16

SELECT nomEtu FROM EtudiantInfo ORDER BY nomEtu, prenom;
-- AGBOGBO (BASSON), AIME, ANNASRI, ANTONIO, AUGER, BARRIAC, BASOL, BATARD, BEATRIX, BELBEOCH