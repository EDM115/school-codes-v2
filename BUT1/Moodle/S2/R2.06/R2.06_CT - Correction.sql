----------------------------
-- Partie 1 sur 10 points --
----------------------------

-- Q1 : diagramme de classes UML (3 points)

-- Q2 : multiplicité 1..* (0.5 point)

-- Q3

-- a) Contrainte C1 (1 point)
ALTER TABLE Ticket ADD CONSTRAINT ck_dates CHECK (dateCreation < datecloture) ; 

-- b) Contrainte C2 (1 point)
ALTER TABLE Projet ADD CONSTRAINT ck_typeProjet CHECK (typeProjet IN ('CASCADE', 'V', 'AGILE')) ;

-- Q4

-- a) Contrainte C3 : chaque utilisateur a créé au moins un ticket (1.5 point)
CREATE OR REPLACE VIEW vue_userSansTicket
AS
SELECT idUser
FROM Utilisateur
MINUS
SELECT lUtilisateur
FROM Ticket;

-- b) Contrainte C4 : un informaticien ne peut pas travailler sur plus que 3 projets à la fois (1.5 point)
CREATE OR REPLACE VIEW vue_infoSurPlus3projets
AS
SELECT unInformaticien
FROM TravailleSur
GROUP BY unInformaticien
HAVING COUNT(unProjet) > 3;

SELECT * FROM vue_infoSurPlus3projets;

-- Q5

-- a) attribut dérivable (0.5 point)
-- b) Afficher la duree de traitement (1 point)
CREATE OR REPLACE VIEW vue_dureeTraitement
AS
SELECT idTicket, dateCloture - dateCreation dureeEnJours
FROM Ticket;

SELECT * FROM vue_dureeTraitement;


---------------------------------------
-- Q6 sur 10 points (2 par question) --
---------------------------------------

-- a) Afficher les noms des informaticiens ayant clôturé les 10 derniers tickets, sans la clause LIMIT.
SELECT * 
FROM
	(
	SELECT nomInf
	FROM Informaticien 
		JOIN Ticket ON idInf = lInformaticien
	WHERE dateCloture IS NOT NULL -- non facultatif à cause du DESC
	ORDER BY dateCloture DESC
	)
WHERE ROWNUM <= 10;

-- /!\ si on met DISTINCT nomInf au lieu de *, on élimine les doublons mais on accepte de "confondre" les homonymes

-- b) Pour chaque utilisateur, afficher les tickets créés dans l’ordre décroissant de la durée de traitement.
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

-- c)Pour chaque informaticien trié dans l’ordre alphabétique (du nom), afficher les titres des tickets en
-- cours de traitement du plus ancien au plus récent selon la date de création, éventuellement rien.
SELECT nomInf, titreTicket
FROM Informaticien
	LEFT JOIN Ticket ON idInf = lInformaticien
WHERE dateCloture IS NULL
ORDER BY nomInf, dateCreation;

-- d) Afficher le nombre d’informaticiens ayant au moins un ticket non cloturé.
SELECT COUNT(DISTINCT lInformaticien)
FROM Ticket
WHERE dateCloture IS NULL;

-- e) Afficher les noms des informaticiens ayant une expérience supérieure à la moyenne.
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

-- a)Pour chaque projet, afficher le nombre d’informaticiens associés
SELECT unProjet, COUNT(unInformaticien)
FROM TravailleSur
GROUP BY unProjet;

-- b)Pour chaque informaticien désigné par son nom et son prénom, afficher le nombre de tickets clôturés, éventuellement 0
SELECT nomInf, prenomInf, COUNT(dateCloture)
FROM Informaticien, Ticket
WHERE idInf = Linformaticien (+)
GROUP BY nomInf, prenomInf;

-- c)Pour chaque projet désigné par son intitulé, afficher l’expérience de l’informaticien le plus expérimenté.
SELECT intituleProjet, MAX(anneesExperience)
FROM Projet
    JOIN TravailleSur ON idProjet = unProjet
    JOIN Informaticien ON unInformaticien = idInf
GROUP BY idProjet, intituleProjet; -- ne pas pénaliser s'il manque idProjet (l'intitulé devrait être une clé)

-- d)Afficher le(s) utilisateur(s) ayant créé le plus grand nombre de tickets.
SELECT lUtilisateur
FROM Ticket
GROUP BY lUtilisateur
HAVING COUNT(idTicket) = 
	(
	SELECT MAX(COUNT(idTicket))
	FROM Ticket
	GROUP BY lUtilisateur
	);

-- e)Afficher les informaticiens travaillant (plutôt que ayant travaillé) sur tous les projets de type AGILE.
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
