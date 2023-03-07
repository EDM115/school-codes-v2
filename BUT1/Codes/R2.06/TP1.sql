-- Exercice 1 --

-- Q1 --

Enseignant(idEns:str (1), nomEns:str (NN), prenomEns:str (NN))
GroupeInfo1(idGroupe:str (1), tuteurGroupe:str @Enseignant.idEns (1))
Etudiant(idEtud:int (1), nomEtud:str (NN), prenomEtud:str (NN), sexe:str (NN), bac:str, nomLycee:str, depLycee:int, leGroupeInfo1:str (NN) @GroupeInfo1.idGroupe, parcoursInfo:str, formationInfo:str, poursuiteEtudes:str)
Entreprise(idEntreprise:int (1), nomEntreprise:str, depEntreprise:int)
Stagiaire(etudStagiaire:int (1) @Etudiant.idEtud, entrepriseStage:int (1) @Entreprise.idEntreprise)
Apprenti(etudApp:int (1) @Etudiant.idEtud, entrepriseApp:int (1) @Entreprise.idEntreprise, tuteurApp:str @Enseignant.idEns (NN))

-- Q3 --

CREATE TABLE Enseignant (
	idEns VARCHAR(5)
		CONSTRAINT pk_Enseignant PRIMARY KEY,
	nomEns VARCHAR(50)
		CONSTRAINT nn_nomEns NOT NULL,
	prenomEns VARCHAR(50)
		CONSTRAINT nn_prenomEns NOT NULL
);

CREATE TABLE GroupeInfo1 (
	idGroupe VARCHAR(1)
		CONSTRAINT pk_GroupeInfo1 PRIMARY KEY,
	tuteurGroupe VARCHAR(5)
		CONSTRAINT fk_tuteurGroupe REFERENCES Enseignant(idEns)
		CONSTRAINT nn_tuteurGroupe NOT NULL
);

CREATE TABLE Etudiant (
	idEtud NUMBER
		CONSTRAINT pk_Etudiant PRIMARY KEY,
	nomEtud VARCHAR(50)
		CONSTRAINT nn_nomEtud NOT NULL,
	prenomEtud VARCHAR(50)
		CONSTRAINT nn_prenomEtud NOT NULL,
	sexe VARCHAR(1)
		CONSTRAINT nn_sexe NOT NULL,
	bac VARCHAR(50),
	nomLycee VARCHAR(50),
	depLycee NUMBER,
	leGroupeInfo1 VARCHAR(1)
		CONSTRAINT fk_leGroupeInfo1 REFERENCES GroupeInfo1(idGroupe)
		CONSTRAINT nn_leGroupeInfo1 NOT NULL,
	parcoursInfo2 VARCHAR(50),
	formationInfo2 VARCHAR(50),
	poursuiteEtudes VARCHAR(50)
);

CREATE TABLE Entreprise (
	idEntreprise NUMBER
		CONSTRAINT pk_Entreprise PRIMARY KEY,
	nomEntreprise VARCHAR(50),
	depEntreprise NUMBER
);

CREATE TABLE Stagiaire (
	etudStagiaire NUMBER
		CONSTRAINT fk_etudStagiaire REFERENCES Etudiant(idEtud),
	entrepriseStage NUMBER
		CONSTRAINT fk_entrepriseStage REFERENCES Entreprise(idEntreprise),
	CONSTRAINT pk_Stagiaire PRIMARY KEY (etudStagiaire, entrepriseStage)
);

CREATE TABLE Apprenti (
	etudApp NUMBER
		CONSTRAINT fk_etudApp REFERENCES Etudiant(idEtud),
	entrepriseApp NUMBER
		CONSTRAINT fk_entrepriseApp REFERENCES Entreprise(idEntreprise),
	tuteurApp VARCHAR(5)
		CONSTRAINT fk_tuteurApp REFERENCES Enseignant(idEns)
		CONSTRAINT nn_tuteurApp NOT NULL,
	CONSTRAINT pk_Apprenti PRIMARY KEY (etudApp, entrepriseApp)
);

