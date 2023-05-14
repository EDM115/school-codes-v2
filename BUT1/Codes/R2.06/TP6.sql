CREATE TABLE Enseignant (
	idEns VARCHAR(5),
	nomEns VARCHAR(50) NOT NULL,
	prenomEns VARCHAR(50) NOT NULL,
	PRIMARY KEY (idEns)
);

CREATE TABLE GroupeInfo1 (
	idGroupe VARCHAR(1),
	tuteurGroupe VARCHAR(5) NOT NULL,
	PRIMARY KEY (idGroupe),
	FOREIGN KEY (tuteurGroupe) REFERENCES Enseignant(idEns)
);

CREATE TABLE Etudiant (
	idEtud INT(11),
	nomEtud VARCHAR(50) NOT NULL,
	prenomEtud VARCHAR(50) NOT NULL,
	sexe VARCHAR(1) NOT NULL,
	bac VARCHAR(50),
	nomLycee VARCHAR(50),
	depLycee INT(11),
	leGroupeInfo1 VARCHAR(1) NOT NULL,
	parcoursInfo2 VARCHAR(50),
	formationInfo2 VARCHAR(50),
	poursuiteEtudes VARCHAR(50),
	PRIMARY KEY (idEtud),
	FOREIGN KEY (leGroupeInfo1) REFERENCES GroupeInfo1(idGroupe)
);

CREATE TABLE Entreprise (
	idEntreprise INT(11),
	nomEntreprise VARCHAR(50),
	depEntreprise INT(11),
	PRIMARY KEY (idEntreprise)
);

CREATE TABLE Stagiaire (
	etudStagiaire INT(11),
	entrepriseStage INT(11),
	PRIMARY KEY (etudStagiaire, entrepriseStage),
	FOREIGN KEY (etudStagiaire) REFERENCES Etudiant(idEtud),
	FOREIGN KEY (entrepriseStage) REFERENCES Entreprise(idEntreprise)
);

CREATE TABLE Apprenti (
	etudApp INT(11),
	entrepriseApp INT(11),
	tuteurApp VARCHAR(5) NOT NULL,
	PRIMARY KEY (etudApp, entrepriseApp),
	FOREIGN KEY (etudApp) REFERENCES Etudiant(idEtud),
	FOREIGN KEY (entrepriseApp) REFERENCES Entreprise(idEntreprise),
	FOREIGN KEY (tuteurApp) REFERENCES Enseignant(idEns)
);

CREATE TABLE Responsabilite (
	intituleResp VARCHAR(50),
	leResp VARCHAR(10),
	PRIMARY KEY (intituleResp),
	FOREIGN KEY (leResp) REFERENCES Enseignant(idEns)
);

INSERT INTO Responsabilite VALUES 
	('admin', 'LN'),
	('stages', 'JFK'),
	('apprentis', 'PB'),
	('poursuite_etudes', 'RF'),
	('chef_departement', 'JFK'),
	('direction_etudes', 'AR')
;