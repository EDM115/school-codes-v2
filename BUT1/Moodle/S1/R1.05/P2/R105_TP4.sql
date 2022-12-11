-- ====================================== TP4 exo 1 =========================
/*
Ouvrage ( idOuvrage (1), titre, unAuteur = @Auteur.idAuteur (NN), anneeAchat )
Auteur ( idAuteur (1), nom (NN), prenom, nationalite, anneeNaissance )
Client ( idClient (1), nomClient (NN), adresse )
Emprunt ( [unClient = @Client.idClient, unOuvrage = @Ouvrage.idOuvrage](1), dateEmprunt )
Contraintes:
- anneeAchat ne doit pas dépasser pas 2021
- les attributs idOuvrage, idAuteur, idClient, anneeAchat et anneeNaissance sont de type NUMBER
- l’attribut dateEmprunt est de type DATE
- les autres attributs sont de type VARCHAR2(.)
*/

-- Q1: diagramme UML (pdf)

-- Q2
/*
- Ordre de création: Ouvrage après Auteur, Emprunt après Client et Ouvrage
- Ordre de suppression: inverse
*/

-- Q3: script de création de tables

DROP TABLE Emprunt ;
DROP TABLE Client ;
DROP TABLE Ouvrage ;
DROP TABLE Auteur ;

CREATE TABLE Auteur
	(
	idAuteur NUMBER
		CONSTRAINT pk_Auteur PRIMARY KEY,
	nom VARCHAR2(50)
		CONSTRAINT nn_nom NOT NULL,
	prenom VARCHAR2(50),
	nationalite VARCHAR2(50),
	anneeNaissance NUMBER
	)
;

CREATE TABLE Ouvrage
	(
	idOuvrage NUMBER
		CONSTRAINT pk_Ouvrage PRIMARY KEY,
	titre VARCHAR2(50),
	unAuteur NUMBER
		CONSTRAINT fk_Ouvrage_Auteur REFERENCES Auteur(idAuteur)
		CONSTRAINT nn_unAuteur NOT NULL,
	anneeAchat NUMBER
		CONSTRAINT ck_anneeAchat CHECK(anneeAchat <= 2021)
	)
;

CREATE TABLE Client
	(
	idClient NUMBER
		CONSTRAINT pk_Client PRIMARY KEY,
	nomClient VARCHAR2(50)
		CONSTRAINT nn_nomClient NOT NULL,
	adresse VARCHAR2(50)
	)
;

CREATE TABLE Emprunt
	(
	unClient NUMBER
		CONSTRAINT fk_Emprunt_Client REFERENCES Client(idClient),
	unOuvrage NUMBER
		CONSTRAINT fk_Emprunt_Ouvrage REFERENCES Ouvrage(idOuvrage),
	dateEmprunt DATE,
	--
	CONSTRAINT pk_Emprunt PRIMARY KEY (unClient, unOuvrage)
	)
;


-- Q4: Ajout de contrainte en modifiant la colonne
ALTER TABLE Emprunt MODIFY dateEmprunt DATE CONSTRAINT nn_dateEmprunt NOT NULL;


-- Q5: Insertion de données
INSERT INTO Auteur VALUES(1, 'Hugo', 'Victor', 'France', NULL);
INSERT INTO Auteur VALUES(2, 'Zola', 'Emile', NULL, NULL);

INSERT INTO Ouvrage VALUES(1, 'Les Misérables', 1, 2015);
INSERT INTO Ouvrage VALUES(2, 'Notre‑Dame de Paris', 1, 2010);

INSERT INTO Client VALUES(1, 'Pham', 'Vannes');
INSERT INTO Client VALUES(2, 'Legal', NULL);

INSERT INTO Emprunt VALUES(1,1,TO_DATE('01/11/2022','DD/MM/YYYY'));
INSERT INTO Emprunt VALUES(1,2,TO_DATE('05/11/2022','DD/MM/YYYY'));


-- Q6: Tester la contrainte d'intégrité référentielle de unAuteur (2 methodes)
-- Methode 1
INSERT INTO Ouvrage VALUES(3,'Le Petit Prince',3,2011);
/*
ERROR: ORA-02291: integrity constraint (SQL_VVDBJRTEPMDKTHZPPVEBQTLIG.FK_OUVRAGE_AUTEUR) violated
- parent key not found ORA-06512: at "SYS.DBMS_SQL", line 1721
*/

-- Methode 2
DELETE FROM Auteur WHERE nom = 'Hugo';
/*
-- ERROR: ORA-02292: integrity constraint (SQL_VVDBJRTEPMDKTHZPPVEBQTLIG.FK_OUVRAGE_AUTEUR) violated
- child record found ORA-06512: at "SYS.DBMS_SQL", line 1721
*/


-- Q7: Ajout l'attribut dateRetour et la contrainte dateRetour>dateEmprunt
ALTER TABLE Emprunt ADD dateRetour DATE;

ALTER TABLE Emprunt ADD CONSTRAINT ck_dateRetourEmprunt CHECK(dateRetour > dateRetour);

-- Q8: Mettre à jours l'année de naissance d'un auteur
UPDATE Auteur
SET anneeNaissance = 1802
WHERE nom = 'Hugo';

-- Utiliser SELECT * FROM Auteur; pour vérifier les données mises à jours

-- ====================================== TP4 exo 2 =========================
-- Q9: Schéma relationnel
/*
Concession ( idConc (1), nomConc, capital )
Constructeur ( idConst (1), nomConst (NN) )
Client (idClient (1), nomClient(2), prenomClient(2) )
Voiture (idVoiture (1), modele (NN), couleur, laConcession = @Concession.idConc,
leConstructeur = Constructeur.idConst (NN) , leClient = @Client.idClient )
Travail ([uneConc = @Concession.idConc, unConst = @Constructeur.idConst] (1))
Assurance ( [unConstructeur = @Constructeur.idConst, unClient = @Client.idClient](1), dateContrat )

- Contraintes textuelles:
+ Voiture[leClient] = Client[idClient] (Un client doit acheter au moins 1 voiture)
+ Assurance[unClient] = Client[idClient] (Un client doit être assuré par au moins un constructeur)
+ Travail[uneConc] = Concession[idConc] (Une concession travaille avec au moin 1 constructeur)
+ Un constructeur travaille avec au moins 2 concessions
*/

-- Q10 : Script de création des tables
/*
- Remarques: les 4 contraintes textuelles ne peuvent pas être prises en compte par le script
de création de tables. Elles vont être gérées par les vues (à voir en S2)
*/

DROP TABLE Assurance;
DROP TABLE Travail;
DROP TABLE Voiture;
DROP TABLE Client;
DROP TABLE Constructeur;
DROP TABLE Concession;

CREATE TABLE Concession(
	idConc VARCHAR2(50)
		CONSTRAINT pk_Concession PRIMARY KEY,
	nomConc VARCHAR2(50),
	capital NUMBER
);

CREATE TABLE Constructeur(
	idConst VARCHAR2(50)
		CONSTRAINT pk_Constructeur PRIMARY KEY,
	nomConst VARCHAR2(50)
		CONSTRAINT nn_nomConst NOT NULL
);

CREATE TABLE Client(
	idClient VARCHAR2(50)
		CONSTRAINT pk_Client PRIMARY KEY,
	nomClient VARCHAR2(50)
		CONSTRAINT nn_nomClient NOT NULL,
	prenomClient VARCHAR2(50)
		CONSTRAINT nn_prenomClient NOT NULL,
	CONSTRAINT uq_nomClient_prenomClient UNIQUE(nomClient,prenomClient)
);

CREATE TABLE Voiture(
	idVoiture VARCHAR2(50)
		CONSTRAINT pk_Voiture PRIMARY KEY,
	modele VARCHAR2(50)
		CONSTRAINT nn_modele NOT NULL,
	couleur VARCHAR2(50),
	laConcession VARCHAR2(50)
		CONSTRAINT fk_Voiture_Concession REFERENCES Concession(idConc),
	leConstructeur VARCHAR(50)
		CONSTRAINT fk_Voiture_Constructeur REFERENCES Constructeur(idConst)
		CONSTRAINT nn_leConstructeur NOT NULL,
	leClient VARCHAR2(50)
		CONSTRAINT fk_Voiture_Client REFERENCES Client(idClient)
);


CREATE TABLE Travail(
	uneConc VARCHAR2(50)
		CONSTRAINT fk_Travail_Concession REFERENCES Concession(idConc),
	unConst VARCHAR(50)
		CONSTRAINT fk_Travail_Constructeur REFERENCES Constructeur(idConst),
	CONSTRAINT pk_Travail PRIMARY KEY (uneConc,unConst)
);

CREATE TABLE Assurance(
	unConstructeur VARCHAR2(50)
		CONSTRAINT fk_Assurance_Constructeur REFERENCES Constructeur(idConst),
	unClient VARCHAR(50)
		CONSTRAINT fk_Assurance_Client REFERENCES Client(idClient),
	dateContrat DATE,
	CONSTRAINT pk_Assurance PRIMARY KEY (unConstructeur,unClient)
);

-- Visualiser les tables créées (Live SQL notamment)
SELECT * FROM Concession;
SELECT * FROM Constructeur;
SELECT * FROM Client;
SELECT * FROM Voiture;
SELECT * FROM Travail;
SELECT * FROM Assurance;


-- Q11: Ajouter la contrainte dateContrat NOT NULL
ALTER TABLE Assurance MODIFY dateContrat DATE CONSTRAINT nn_dateContrat NOT NULL;


-- autre méthode
ALTER TABLE Assurance DROP CONSTRAINT nn_dateContrat;
ALTER TABLE Assurance ADD CONSTRAINT nn_dateContrat CHECK(dateContrat IS NOT NULL);
-- ne marche pas pour UNIQUE


-- Q12: Ajout les adresses mail des clients
ALTER TABLE Client ADD emailClient VARCHAR2(50) CONSTRAINT ck_email CHECK (emailClient LIKE '%_@%_.%_');

-- Q13: Insertion de 2 tuples dans la table Voiture
-- il faut insérer les données dans Concession, Constructeur et Client (contrainte d'intégrité référentielle)
DELETE FROM Voiture;
DELETE FROM Client;
DELETE FROM Constructeur;
DELETE FROM Concession;


INSERT INTO Concession VALUES('conc001', 'Concession 1', NULL);
INSERT INTO Concession VALUES('conc002', 'Concession 2', 100000);

INSERT INTO Constructeur VALUES('const001', 'PEUGEOT');
INSERT INTO Constructeur VALUES('const002', 'RENAULT');

INSERT INTO Client VALUES('client001', 'Pham', 'Alex', NULL);
INSERT INTO Client VALUES('client002', 'Pham', 'Mary', NULL);

INSERT INTO Voiture VALUES('FR007AA', '208', 'bleu', NULL, 'const001', 'client001');
INSERT INTO Voiture VALUES('FR007BB', '2008', NULL, 'conc001', 'const001', NULL);

-- Q14: Pour ceux qui avancent bien !
