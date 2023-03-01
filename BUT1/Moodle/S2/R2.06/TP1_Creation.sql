DROP TABLE Apprenti;
DROP TABLE Stagiaire;
DROP TABLE Entreprise;
DROP TABLE Etudiant;
DROP TABLE GroupeInfo1;
DROP TABLE Enseignant;

CREATE TABLE Enseignant
	(
	idEns VARCHAR2(3)
		CONSTRAINT pk_Enseignant PRIMARY KEY,
	nomEns VARCHAR2(20),
    prenomEns VARCHAR2(20)
	)
;
        
CREATE TABLE GroupeInfo1
	(
	idGroupe CHAR
		CONSTRAINT pk_GroupeInfo1 PRIMARY KEY,
	tuteurGroupe VARCHAR2(3)
		CONSTRAINT fk_GroupeInfo1_Enseignant REFERENCES Enseignant(idEns)
		CONSTRAINT uq_tuteurGroupe UNIQUE
		CONSTRAINT nn_tuteurGroupe NOT NULL
	)
;

CREATE TABLE Etudiant
	(
	idEtud NUMBER
		CONSTRAINT pk_Etudiant PRIMARY KEY,
	nomEtud VARCHAR2(20),
	prenomEtud VARCHAR2(20),
	sexe CHAR,
	bac VARCHAR2(5),
    nomLycee VARCHAR2(50),
    depLycee NUMBER,
    leGroupeInfo1 CHAR
		CONSTRAINT fk_Etudiant_GroupeInfo1 REFERENCES GroupeInfo1(idGroupe)
		CONSTRAINT nn_leGroupeInfo1 NOT NULL,
	parcoursInfo2 VARCHAR2(2),
    formationInfo2 VARCHAR2(3),
    poursuiteEtudes VARCHAR2(30)	
	)
;

CREATE TABLE Entreprise
	(
	idEntreprise NUMBER
		CONSTRAINT pk_Entreprise PRIMARY KEY,
	nomEntreprise VARCHAR2(40),
    depEntreprise NUMBER
	)
;

CREATE TABLE Stagiaire
	(
	etudStagiaire NUMBER
		CONSTRAINT fk_Stagiaire_Etudiant REFERENCES Etudiant(idEtud)
        CONSTRAINT pk_Stagiaire PRIMARY KEY,
	entrepriseStage NUMBER
		CONSTRAINT fk_Stagiaire_Entreprise REFERENCES Entreprise(idEntreprise)
		CONSTRAINT nn_entrepriseStage NOT NULL
	)
;

CREATE TABLE Apprenti
	(
	etudApp NUMBER
		CONSTRAINT fk_Apprenti_Etudiant REFERENCES Etudiant(idEtud)
        CONSTRAINT pk_Apprenti PRIMARY KEY,
	entrepriseApp NUMBER
		CONSTRAINT fk_Apprenti_Entreprise REFERENCES Entreprise(idEntreprise)
        CONSTRAINT nn_entrepriseApp NOT NULL,
    tuteurApp VARCHAR2(3)
        CONSTRAINT fk_Apprenti_Enseignant REFERENCES Enseignant(idEns)
        CONSTRAINT nn_tuteurApp NOT NULL        
	)
;
