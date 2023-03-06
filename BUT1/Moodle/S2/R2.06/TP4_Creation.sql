DROP TABLE ATravaillePour;
DROP TABLE Avion;
DROP TABLE Qualification;
DROP TABLE TypeAvion;
DROP TABLE Pilote;
DROP TABLE Compagnie;

CREATE TABLE Compagnie
	(
	idComp NUMBER
		CONSTRAINT pk_Compagnie PRIMARY KEY,
	nomComp VARCHAR2(30),
	pays VARCHAR2(20),
	estLowCost NUMBER
	)
;

CREATE TABLE Pilote
	(
	idPilote NUMBER
		CONSTRAINT pk_Pilote PRIMARY KEY,
	nomPilote VARCHAR2(20),
	nbHVol NUMBER,
	compPil NUMBER
		CONSTRAINT fk_Pilote_Compagnie REFERENCES Compagnie(idComp)
	)
;

CREATE TABLE TypeAvion
	(
	idTypeAvion VARCHAR2(20)
		CONSTRAINT pk_TypeAvion PRIMARY KEY,
	nbPassagers NUMBER
	)
;

CREATE TABLE Qualification
	(
	unPilote NUMBER
		CONSTRAINT fk_Qualification_Pilote REFERENCES Pilote(idPilote),
	unTypeAvion VARCHAR2(20)
		CONSTRAINT fk_Qualification_TypeAvion REFERENCES TypeAvion(idTypeAvion),
	CONSTRAINT pk_Qualification	PRIMARY KEY (unPilote, unTypeAvion)
	)
;

CREATE TABLE Avion 
	(
	idAvion	NUMBER
		CONSTRAINT pk_Avion	PRIMARY KEY,
	leTypeAvion VARCHAR2(20)
		CONSTRAINT fk_Avion_TypeAvion REFERENCES TypeAvion(idTypeAvion),
	compAv NUMBER
		CONSTRAINT fk_Avion_Compagnie REFERENCES Compagnie(idComp)
	)
;

CREATE TABLE ATravaillePour
	(
	lePilote NUMBER
		CONSTRAINT fk_ATravaillePour_Pilote REFERENCES Pilote(idPilote),
	laComp NUMBER
		CONSTRAINT fk_ATravaillePour_Compagnie REFERENCES Compagnie(idComp),
	CONSTRAINT pk_ATravaillePour PRIMARY KEY (lePilote, laComp)
	)
;
	