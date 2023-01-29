-- Question 2 --

/*
Création : Auteur, Ouvrage, Client, Emprunt
Destruction : Emprunt, Client, Ouvrage, Auteur
*/

-- Question 3 --

CREATE TABLE Auteur(
	idAuteur NUMBER
		CONSTRAINT pk_Auteur PRIMARY KEY,
	nom VARCHAR2(50)
		CONSTRAINT nn_nomAuteur NOT NULL,
	prenom VARCHAR2(50),
	nationalite VARCHAR2(50),
	anneeNaissance NUMBER
);

CREATE TABLE Ouvrage(
	idOuvrage NUMBER
		CONSTRAINT pk_Ouvrage PRIMARY KEY,
	titre VARCHAR2(50),
	unAuteur NUMBER
		CONSTRAINT fk_Ouvrage_Auteur REFERENCES Auteur(idAuteur)
		CONSTRAINT nn_unAuteur NOT NULL,
	anneeAchat NUMBER
		CONSTRAINT ck_anneeAchat_2021 CHECK (anneeAchat < 2022)
);

CREATE TABLE Client(
	idClient NUMBER
		CONSTRAINT pk_Client PRIMARY KEY,
	nomClient VARCHAR2(50)
		CONSTRAINT nn_nomClient NOT NULL,
	adresse VARCHAR2(50)
);

CREATE TABLE Emprunt(
	unClient NUMBER
		CONSTRAINT fk_Emprunt_Client REFERENCES Client(idClient),
	unOuvrage NUMBER
		CONSTRAINT fk_Emprunt_Ouvrage REFERENCES Ouvrage(idOuvrage),
	CONSTRAINT pk_Emprunt PRIMARY KEY (unClient, unOuvrage),
	dateEmprunt DATE
);

-- Question 4 --

ALTER TABLE Emprunt MODIFY (dateEmprunt NOT NULL);

-- Question 5 --

INSERT INTO Auteur VALUES (1, 'Dumas', 'Alexandre', 'Français', 1802);
INSERT INTO Auteur VALUES (2, 'Hugo', 'Victor', 'Français', 1802);
INSERT INTO Auteur VALUES (3, 'Verne', 'Jules', 'Français', 1828);

INSERT INTO Ouvrage VALUES (1, 'Les Trois Mousquetaires', 1, 2004);
INSERT INTO Ouvrage VALUES (2, 'Le Comte de Monte-Cristo', 1, 2008);
INSERT INTO Ouvrage VALUES (3, 'Les Misérables', 2, 2010);
INSERT INTO Ouvrage VALUES (4, 'Vingt Mille Lieues Sous les Mers', 3, 2012);

INSERT INTO Client VALUES (1, 'Dupont', 'Paris');
INSERT INTO Client VALUES (2, 'Durand', 'Marseille');
INSERT INTO Client VALUES (3, 'Martin', 'Lyon');

INSERT INTO Emprunt VALUES (1, 1, TO_DATE('01/01/2021', 'DD/MM/YYYY'));
INSERT INTO Emprunt VALUES (2, 2, TO_DATE('05/08/2021', 'DD/MM/YYYY'));
INSERT INTO Emprunt VALUES (3, 3, TO_DATE('13/12/2021', 'DD/MM/YYYY'));

-- Question 6 --

INSERT INTO Ouvrage VALUES (5, 'Le Tour du Monde en 80 Jours', 4, 2015);
/*
ERROR at line 80:
ORA-02291: integrity constraint (EDM115.FK_OUVRAGE_AUTEUR) violated - parent key not found
*/
DELETE FROM Ouvrage WHERE unAuteur = 4;
/*
0 row deleted.
Commit complete.
*/

-- Question 7 --

ALTER TABLE Emprunt ADD dateRetour DATE;
ALTER TABLE Emprunt MODIFY (dateRetour NOT NULL);
ALTER TABLE Emprunt ADD CONSTRAINT ck_dateRetour CHECK (dateRetour > dateEmprunt);

-- Question 8 --

UPDATE Auteur SET anneeNaissance = 1803 WHERE idAuteur = 1;

-- Question 9 --

/*
Concession(idConc(1), nomConc, capital)
Constructeur(idConst(1), nomConst(NN))
Client(idClient(1), nomClient(2), prenomClient(2))
Voiture(immat(1), modele(NN), couleur, laConcession = @Concession.idConc, leConstructeur = @Constructeur.idConst(NN), leClient = @Client.idClient)
Travail([uneConc = @Concession.idConc, unConst = @Constructeur.idConst](1))
Assurance([unConstructeur = @Construction.idConst, unClient = @Client.idClient](1), dateContrat)

Contraintes textuelles :
- Voiture[leClient] = Client[idClient]
-un constructeur doit avoir au moins 2 concessions
- Travail[uneConc] = Concession[idConc]
- Assurance[unClient] = Client[idClient]
*/

-- Question 10 --

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
		CONSTRAINT uq_nomClient UNIQUE
		CONSTRAINT nn_nomClient NOT NULL,
	prenomClient VARCHAR2(50)
		CONSTRAINT uq_prenomClient UNIQUE
		CONSTRAINT nn_prenomClient NOT NULL
);

CREATE TABLE Voiture(
	immat VARCHAR2(50)
		CONSTRAINT pk_Voiture PRIMARY KEY,
	modele VARCHAR2(50)
		CONSTRAINT nn_modele NOT NULL,
	couleur VARCHAR2(50),
	laConcession VARCHAR2(50)
		CONSTRAINT fk_Voiture_Concession REFERENCES Concession(idConc),
	leConstructeur VARCHAR2(50)
		CONSTRAINT fk_Voiture_Constructeur REFERENCES Constructeur(idConst)
		CONSTRAINT nn_leConstructeur NOT NULL,
	leClient VARCHAR2(50)
		CONSTRAINT fk_Voiture_Client REFERENCES Client(idClient)
);

CREATE TABLE Travail(
	uneConc VARCHAR2(50)
		CONSTRAINT fk_Travail_Concession REFERENCES Concession(idConc),
	unConst VARCHAR2(50)
		CONSTRAINT fk_Travail_Constructeur REFERENCES Constructeur(idConst),
	CONSTRAINT pk_Travail PRIMARY KEY (uneConc, unConst)
);

CREATE TABLE Assurance(
	unConstructeur VARCHAR2(50)
		CONSTRAINT fk_Assurance_Constructeur REFERENCES Constructeur(idConst),
	unClient VARCHAR2(50)
		CONSTRAINT fk_Assurance_Client REFERENCES Client(idClient),
	CONSTRAINT pk_Assurance PRIMARY KEY (unConstructeur, unCLient),
	dateContrat DATE
);

-- Contraintes qui ne peuvent pas être prise en compte : un constructeur doit avoir au moins 2 concessions

-- Question 11 --

ALTER TABLE Assurance MODIFY dateContrat DATE CONSTRAINT nn_dateContrat NOT NULL;

-- Question 12 --

ALTER TABLE Client ADD email VARCHAR2(50);
ALTER TABLE Client ADD CONSTRAINT ck_email CHECK (email LIKE '%_@%_.%_');

-- Question 13 --

INSERT INTO Concession VALUES ('1', 'Peugeot Vannes', 1000000);
INSERT INTO Concession VALUES ('2', 'Peugeot Lorient', 2000000);
INSERT INTO Concession VALUES ('3', 'Citroen', 3000000);

INSERT INTO Constructeur VALUES ('1', 'Peugeot');
INSERT INTO Constructeur VALUES ('2', 'Citroen');

INSERT INTO Client VALUES ('1', 'Dupont', 'Jean', 'jean.dupont@gmail.com');
INSERT INTO Client VALUES ('2', 'Durand', 'Paul', 'paul.durand@gmail.com');

INSERT INTO Voiture VALUES ('ab775pg', '205', 'Rouge', 2, 1, 1);
INSERT INTO Voiture VALUES ('ek889zf', 'C5', 'Bleu', 3, 2, 2);

-- Question 14 : A partir des tables remplies, effectuer des tester de contraintes d'intégrité référentielle à votre choix (avec INSERT INTO et avec DELETE FROM). Ensuite, proposer des commandes SQL pour la modification de table avec ALTER TABLE et la modification des données avec UPDATE --

/*
INSERT INTO Voiture VALUES ('ee568pg', 'Porsche', 'Marron', '4', '8', '6');
*
ERROR at line 212:
ORA-02291: integrity constraint (EDM115.FK_VOITURE_CLIENT) violated - parent key not found
*/


/*
DELETE FROM Concession WHERE (idConc = '1');
*
ERROR at line 220:
ORA-02292: integrity constraint (EDM115.FK_VOITURE_CONCESSION) violated - child record found
*/

ALTER TABLE Client ADD ville VARCHAR2(50);
ALTER TABLE Voiture ADD prix NUMBER;
ALTER TABLE Client DROP COLUMN ville;
UPDATE Voiture SET couleur = 'Vert' WHERE immat = 'ab775pg';
UPDATE Concession SET nomConc = 'Citroen Rennes', capital = 3005000 WHERE idConc = '3';
UPDATE Constructeur SET nomConst = 'PSA' WHERE idConst = '1';