----------------------------
-- Partie 1 sur 10 points --
----------------------------

-- Q1 : diagramme de classes UML (3 points)

-- Q2 : multiplicit� 1..* (0.5 point)

-- Q3

-- a) Contrainte C1 (1 point)
ALTER TABLE Ticket ADD CONSTRAINT ck_dates CHECK (dateCreation < datecloture) ; 

-- b) Contrainte C2 (1 point)
ALTER TABLE Projet ADD CONSTRAINT ck_typeProjet CHECK (typeProjet IN ('CASCADE', 'V', 'AGILE')) ;

-- Q4

-- a) Contrainte C3 : chaque utilisateur a cr�� au moins un ticket (1.5 point)
CREATE OR REPLACE VIEW vue_userSansTicket
AS
SELECT idUser
FROM Utilisateur
MINUS
SELECT lUtilisateur
FROM Ticket;

-- b) Contrainte C4 : un informaticien ne peut pas travailler sur plus que 3 projets � la fois (1.5 point)
CREATE OR REPLACE VIEW vue_infoSurPlus3projets
AS
SELECT unInformaticien
FROM TravailleSur
GROUP BY unInformaticien
HAVING COUNT(unProjet) > 3;

SELECT * FROM vue_infoSurPlus3projets;

-- Q5

-- a) attribut d�rivable (0.5 point)
-- b) Afficher la duree de traitement (1 point)
CREATE OR REPLACE VIEW vue_dureeTraitement
AS
SELECT idTicket, dateCloture - dateCreation dureeEnJours
FROM Ticket;

SELECT * FROM vue_dureeTraitement;


---------------------------------------
-- Q6 sur 10 points (2 par question) --
---------------------------------------

-- a) Afficher les noms des informaticiens ayant cl�tur� les 10 derniers tickets, sans la clause LIMIT.
SELECT * 
FROM
	(
	SELECT nomInf
	FROM Informaticien 
		JOIN Ticket ON idInf = lInformaticien
	WHERE dateCloture IS NOT NULL -- non facultatif � cause du DESC
	ORDER BY dateCloture DESC
	)
WHERE ROWNUM <= 10;

-- /!\ si on met DISTINCT nomInf au lieu de *, on �limine les doublons mais on accepte de "confondre" les homonymes

-- b) Pour chaque utilisateur, afficher les tickets cr��s dans l�ordre d�croissant de la dur�e de traitement.
SELECT lUtilisateur, idTicket
FROM Ticket
    NATURAL JOIN vue_dureeTraitement
WHERE dateCloture IS NOT NULL -- facultatif mais mieux avec
ORDER BY lUtilisateur, dureeEnJours DESC;

--ou, sans la vue : 
SELECT lUtilisateur, idTicket
FROM Ticket
WHERE dateCloture IS NOT NULL -- facultatif mais mieux avec
ORDER BY lUtilisateur, dateCloture - dateCreation DESC;

-- c)Pour chaque informaticien tri� dans l�ordre alphab�tique (du nom), afficher les titres des tickets en
-- cours de traitement du plus ancien au plus r�cent selon la date de cr�ation, �ventuellement rien.
SELECT nomInf, titreTicket
FROM Informaticien
	LEFT JOIN Ticket ON idInf = lInformaticien
WHERE dateCloture IS NULL
ORDER BY nomInf, dateCreation;

-- d) Afficher le nombre d�informaticiens ayant au moins un ticket non clotur�.
SELECT COUNT(DISTINCT lInformaticien)
FROM Ticket
WHERE dateCloture IS NULL;

-- e) Afficher les noms des informaticiens ayant une exp�rience sup�rieure � la moyenne.
SELECT nomInf
FROM Informaticien
WHERE anneesExperience > 
    (
	SELECT AVG(anneesExperience)
	FROM Informaticien
	);


---------------------------------------	
-- Q7 sur 10 points (2 par question) --
---------------------------------------

-- a)Pour chaque projet, afficher le nombre d�informaticiens associ�s
SELECT unProjet, COUNT(unInformaticien)
FROM TravailleSur
GROUP BY unProjet;

-- b)Pour chaque informaticien d�sign� par son nom et son pr�nom, afficher le nombre de tickets cl�tur�s, �ventuellement 0
SELECT nomInf, prenomInf, COUNT(dateCloture)
FROM Informaticien, Ticket
WHERE idInf = Linformaticien (+)
GROUP BY nomInf, prenomInf;

-- c)Pour chaque projet d�sign� par son intitul�, afficher l�exp�rience de l�informaticien le plus exp�riment�.
SELECT intituleProjet, MAX(anneesExperience)
FROM Projet
    JOIN TravailleSur ON idProjet = unProjet
    JOIN Informaticien ON unInformaticien = idInf
GROUP BY idProjet, intituleProjet; -- ne pas p�naliser s'il manque idProjet (l'intitul� devrait �tre une cl�)

-- d)Afficher le(s) utilisateur(s) ayant cr�� le plus grand nombre de tickets.
SELECT lUtilisateur
FROM Ticket
GROUP BY lUtilisateur
HAVING COUNT(idTicket) = 
	(
	SELECT MAX(COUNT(idTicket))
	FROM Ticket
	GROUP BY lUtilisateur
	);

-- e)Afficher les informaticiens travaillant (plut�t que ayant travaill�) sur tous les projets de type AGILE.
SELECT idInf
FROM Informaticien
WHERE NOT EXISTS
	(
	SELECT idProjet
	FROM Projet
	WHERE UPPER(typeProjet) = 'AGILE'
	MINUS
	SELECT unProjet
	FROM TravailleSur
	WHERE unInformaticien = idInf
	);
