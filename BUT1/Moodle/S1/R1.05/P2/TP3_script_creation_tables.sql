-- ======================== Script de création de tables pour le TP3 (cf. TP2, exercice 1)
/* 
Enseignant( nomEns (1), prenomEns, adresse, statut )  
Cycle( num (1), enseignantResponsable = @Enseignant.nomEns (UQ)(NN) )
Cours( nomCours (1), volumeH, lEnseignant = @Enseignant.nomEns (NN), leCycle = @Cycle.num (NN) ) 
Requiert( [ cours = @Cours.nomCours, coursRequis = @Cours.nomCours ](1) )
*/


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
	enseignantResponsable VARCHAR2(20) 
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
		CONSTRAINT fk_Cours_Enseignant REFERENCES Enseignant(nomEns),	
	leCycle NUMBER
		CONSTRAINT fk_Cours_Cycle REFERENCES Cycle(num)	
	)
;

CREATE TABLE Requiert
	(
	cours VARCHAR2(20)
		CONSTRAINT fk_Requiert_Cours REFERENCES Cours(nomCours), 
	coursRequis VARCHAR2(20)
		CONSTRAINT fk_Requiert_CoursRequis REFERENCES Cours(nomCours), 
	CONSTRAINT pk_Requiert PRIMARY KEY (cours, coursRequis)
	)
;

