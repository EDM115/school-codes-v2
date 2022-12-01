-- ====================================== TP1 exo 1 =========================
/*
Enseignant( nomEns (1), prenomEns, adresse, statut )
Cycle( num (1), enseignantResponsable = @Enseignant.nomEns (UQ)(NN) )
Cours( nomCours (1), volumeH, lEnseignant = @Enseignant.nomEns (NN), leCycle = @Cycle.num (NN) )
Requiert( [ cours = @Cours.nomCours, coursRequis = @Cours.nomCours ](1) )
*/

-- Q3: On commence par supprimer toutes les tables avec DROP TABLE (dans le bon ordre) pour que le script soit reexécutable !
-- Lors de la première exécution, vous aurez un message d'erreur (pourquoi ?), mais ce n'est pas gênant.

DROP TABLE Requiert ;
DROP TABLE Cours ;
DROP TABLE Cycle ;
DROP TABLE Enseignant ;

CREATE TABLE Enseignant
	(
	nomEns VARCHAR2(20)
		CONSTRAINT pk_Enseignant PRIMARY KEY,

	prenomEns VARCHAR2(20),

	adresse VARCHAR2(40),

	statut VARCHAR2(20)
	)
;

CREATE TABLE Cycle
	(
	num NUMBER
		CONSTRAINT pk_Cycle PRIMARY KEY,

	enseignantResponsable VARCHAR2(20) -- même type que "nomEns" (pourquoi ?)
		CONSTRAINT fk_Cycle_Enseignant REFERENCES Enseignant(nomEns)
		CONSTRAINT uq_enseignantResponsable UNIQUE
		CONSTRAINT nn_enseignantResponsable NOT NULL
	)
;

CREATE TABLE Cours
	(
	nomCours VARCHAR2(20)
		CONSTRAINT pk_Cours PRIMARY KEY,	
	volumeH NUMBER,
	lEnseignant VARCHAR2(20)
		CONSTRAINT fk_Cours_Enseignant REFERENCES Enseignant(nomEns)
		CONSTRAINT nn_lEnseignant NOT NULL,	
	leCycle NUMBER
		CONSTRAINT fk_Cours_Cycle REFERENCES Cycle(num)	
		CONSTRAINT nn_leCycle NOT NULL
	)
;

CREATE TABLE Requiert
	(
	cours VARCHAR2(20)
		CONSTRAINT fk_Requiert_Cours REFERENCES Cours(nomCours),

	coursRequis VARCHAR2(20)
		CONSTRAINT fk_Requiert_CoursRequis REFERENCES Cours(nomCours), -- on ne peut pas se contenter du nom "fk_Requiert_Cours" (pourquoi ?)

	-- contrainte de table (multi-attributs)
	CONSTRAINT pk_Requiert PRIMARY KEY (cours, coursRequis)
	)
;

-- ====================================== TP2 exo 2 =========================
/*
Proprietaire ( idProprietaire (1), nomProprietaire (2), prenomProprietaire (2), emailProprietaire (NN)(UQ) )
Emplacement ( idEmplacement (1), longueurEmplacement (NN), coutJournalier (NN) )
Bateau ( idBateau (1), nomBateau, longueurBateau (NN), leProprietaire = @Proprietaire.idProprietaire (NN), leStationnement = @Emplacement.idEmplacement (UQ) )
Reservation ( idReservation (1), dateDebut (NN), dateFin (NN), leBateau = @Bateau.idBateau (NN), lEmplacement = @Emplacement.idEmplacement (NN) )
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
		CONSTRAINT ck_emailProprietaire CHECK(emailProprietaire LIKE '%_@%_.%_'), -- question 5c) on pourrait être plus précis avec REGEXP_LIKE
	CONSTRAINT uq_nom_prenom UNIQUE(nomProprietaire,prenomProprietaire)
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
		CONSTRAINT ck_longueurBateau CHECK(longueurBateau <= 20), -- question 5a)

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

	CONSTRAINT ck_dateDebutInfdateFin CHECK(dateDebut < dateFin) -- question 5b)
	)
;

/* Q6: Pour éviter la modification des scripts de création, il faut utiliser ALTER TABLE ... ADD CONSTRAINT... pour l'ajout des contraintes
On peut aussi enlever des contraintes non souhaitées par ALTER TABLE ... DROP CONSTRAINT
*/

-- Exemple d'ajout d'une contrainte d'attribut: le coutJournalier doit être strictement positif
ALTER TABLE Emplacement
ADD CONSTRAINT ck_coutJournalier_positif CHECK(coutJournalier > 0);


-- Exemple de suppression d'une contrainte
ALTER TABLE Emplacement
DROP CONSTRAINT ck_dateDebutInfdateFin;

-- Exemple de rajout de la contrainte dateDebut < dateFin
ALTER TABLE Reservation
ADD CONSTRAINT ck_dateDebutInfdateFin CHECK(dateDebut < dateFin);
