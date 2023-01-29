-- Exercice 1 --

DROP TABLE Requiert;
DROP TABLE Cours;
DROP TABLE Cycle;
DROP TABLE Enseignant;

CREATE TABLE Enseignant(
	nomEns VARCHAR2(50)
		CONSTRAINT pk_Enseignant PRIMARY KEY,
	prenomEns VARCHAR2(50),
	adresse VARCHAR2(50),
	status VARCHAR2(50)
);

CREATE TABLE Cycle(
	num NUMBER
		CONSTRAINT pk_Cycle PRIMARY KEY,
	enseignantResponsable VARCHAR2(50)
		CONSTRAINT fk_Cycle_Enseignant REFERENCES Enseignant(nomEns)
		CONSTRAINT nn_enseignantResponsable NOT NULL
		CONSTRAINT un_enseignantResponsable UNIQUE
);

CREATE TABLE Cours(
	nomCours VARCHAR(50)
		CONSTRAINT pk_Cours PRIMARY KEY,
	volumeH NUMBER,
	leCycle NUMBER
		CONSTRAINT fk_Cours_Cycle REFERENCES Cycle(num)
		CONSTRAINT nn_leCycle NOT NULL,
	lEnseignant VARCHAR2(50)
		CONSTRAINT fk_Cours_Enseignant REFERENCES Enseignant(nomEns)
		CONSTRAINT nn_lEnseignant NOT NULL
);

CREATE TABLE Requiert(
	cours VARCHAR2(50)
		CONSTRAINT fk_Requiert_Cours REFERENCES Cours(nomCours),
	coursRequis VARCHAR2(50)
		CONSTRAINT fk_Requiert_CoursRequis REFERENCES Cours(nomCours),
	CONSTRAINT pk_Requiert PRIMARY KEY(cours, coursRequis)
);

-- Exercice 2 --

DROP TABLE Reservation;
DROP TABLE Emplacement;
DROP TABLE Bateau;
DROP TABLE Proprietaire;

CREATE TABLE Proprietaire(
	idProprietaire NUMBER
		CONSTRAINT pk_Proprietaire PRIMARY KEY,
	nomProprietaire VARCHAR2(50)
		CONSTRAINT nn_nomProprietaire NOT NULL,
	prenomProprietaire VARCHAR2(50)
		CONSTRAINT nn_prenomProprietaire NOT NULL,
	emailProprietaire VARCHAR2(50)
		CONSTRAINT nn_emailProprietaire NOT NULL
		CONSTRAINT un_emailProprietaire UNIQUE
		CONSTRAINT ck_emailProprietaire CHECK (emailProprietaire LIKE '%_@%_.%_')
);

CREATE TABLE Emplacement(
	idEmplacement NUMBER
		CONSTRAINT pk_Emplacement PRIMARY KEY,
	longueurEmplacement NUMBER
		CONSTRAINT nn_longueurEmplacement NOT NULL,
	coutJournalier NUMBER
		CONSTRAINT nn_coutJournalier NOT NULL
);

CREATE TABLE Bateau(
	idBateau NUMBER
		CONSTRAINT pk_Bateau PRIMARY KEY,
	nomBateau VARCHAR2(50),
	longueurBateau NUMBER
		CONSTRAINT nn_longueurBateau NOT NULL
		CONSTRAINT ck_longueurBateauMin CHECK (longueurBateau < 20),
	leProprietaire NUMBER
		CONSTRAINT fk_Bateau_Proprietaire REFERENCES Proprietaire(idProprietaire)
		CONSTRAINT nn_leProprietaire NOT NULL,
	leStationnement NUMBER
		CONSTRAINT fk_Bateau_Emplacement REFERENCES Emplacement(idEmplacement)
		CONSTRAINT un_leStationnement UNIQUE
);

CREATE TABLE Reservation(
	idReservation NUMBER
		CONSTRAINT pk_Reservation PRIMARY KEY,
	dateDebut DATE
		CONSTRAINT nn_dateDebut NOT NULL,
	dateFin DATE
		CONSTRAINT nn_dateFin NOT NULL,
	leBateau NUMBER
		CONSTRAINT fk_Reservation_Bateau REFERENCES Bateau(idBateau)
		CONSTRAINT nn_leBateau NOT NULL,
	lEmplacement NUMBER
		CONSTRAINT fk_Reservation_Emplacement REFERENCES Emplacement(idEmplacement)
		CONSTRAINT nn_lEmplacement NOT NULL,
	CONSTRAINT ck_date CHECK (dateDebut < dateFin)
);