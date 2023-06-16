/*
Schéma relationnel
------------------

Proprietaire ( idProprietaire (1), nomProprietaire (2), prenomProprietaire (2), emailProprietaire (3) )
Emplacement ( idEmplacement (1), longueurEmplacement (NN), coutJournalier (NN) )
Bateau ( idBateau (1), nomBateau, longueurBateau (NN), leProprietaire = @Proprietaire.idProprietaire (NN), leStationnement = @Emplacement.idEmplacement (UQ) )
Reservation ( idReservation (1), dateDebut (NN), dateFin (NN), leBateau = @Bateau.idBateau (NN), lEmplacement = @Emplacement.idEmplacement (NN) )

Contraintes textuelles
----------------------

- la longueur du bateau ne doit pas dépasser 10 m --> OK
- la syntaxe de l'email doit être valide --> OK
- dateDebut < dateFin --> OK
- coutReservation = coutJournalier x nbJours --> vue à venir
- un emplacement ne peut être réservé pour un bateau dépassant sa longueur --> trigger à venir
- un stationnement doit faire l'objet d'une réservation --> trigger à venir
- un emplacement ne peut être réservé par deux bateaux différents le même jour --> trigger à venir

- chaque propriétaire possède au moins un bateau : Proprietaire[idProprietaire] = Bateau[leProprietaire] --> vue à venir
*/

DROP TABLE Reservation;
DROP TABLE Bateau;
DROP TABLE Emplacement;
DROP TABLE Proprietaire;

CREATE TABLE Proprietaire
	(
	idProprietaire NUMBER(4,0) -- entiers de 0 à 9999
		CONSTRAINT pk_Proprietaire PRIMARY KEY,
	
	nomProprietaire VARCHAR2(50)
		CONSTRAINT nn_nomProprietaire NOT NULL,
	
	prenomProprietaire VARCHAR2(50)
		CONSTRAINT nn_prenomProprietaire NOT NULL,
	
	emailProprietaire VARCHAR2(50)
		CONSTRAINT nn_emailProprietaire NOT NULL
		CONSTRAINT uq_emailProprietaire UNIQUE
		CONSTRAINT ck_emailProprietaire CHECK(emailProprietaire LIKE '%_@%_.%_'), -- on pourrait être plus précis avec REGEXP_LIKE
	
	CONSTRAINT uq_nomP_prenomP UNIQUE(nomProprietaire, prenomProprietaire)
	)
;

CREATE TABLE Emplacement
	(
	idEmplacement NUMBER(4,0)
		CONSTRAINT pk_Emplacement PRIMARY KEY,
	
	longueurEmplacement NUMBER(2,0)
		CONSTRAINT nn_longueurEmplacement NOT NULL,
		
	coutJournalier NUMBER(2,0)
		CONSTRAINT nn_coutJournalier NOT NULL
	)
;

CREATE TABLE Bateau
	(
	idBateau NUMBER(4)
		CONSTRAINT pk_Bateau PRIMARY KEY,
	
	nomBateau VARCHAR2(50),
	
	longueurBateau NUMBER(2)
		CONSTRAINT nn_longueurBateau NOT NULL
		CONSTRAINT ck_longueurBateau CHECK(longueurBateau <= 10),
	
	leProprietaire NUMBER(4)
		CONSTRAINT fk_Bateau_Proprietaire REFERENCES Proprietaire(idProprietaire)
		CONSTRAINT nn_leProprietaire NOT NULL,
	
	leStationnement NUMBER(4)
		CONSTRAINT fk_Bateau_Emplacement REFERENCES Emplacement(idEmplacement)
		CONSTRAINT uq_leStationnement UNIQUE
	)
;

CREATE TABLE Reservation
	(
	idReservation NUMBER(4)
		CONSTRAINT pk_Reservation PRIMARY KEY,
	
	dateDebut DATE
		CONSTRAINT nn_DateDebut NOT NULL,
	
	dateFin DATE
		CONSTRAINT nn_DateFin NOT NULL,
	
	leBateau NUMBER(4)
		CONSTRAINT fk_Reservation_Bateau REFERENCES Bateau(idBateau)
		CONSTRAINT nn_leBateau NOT NULL,
	
	lEmplacement NUMBER(4)
		CONSTRAINT fk_Reservation_Emplacement REFERENCES Emplacement(idEmplacement)
		CONSTRAINT nn_lEmplacement NOT NULL,
		
	CONSTRAINT ck_dateDebutInfdateFin CHECK(dateDebut < dateFin)
	)
;

	

			