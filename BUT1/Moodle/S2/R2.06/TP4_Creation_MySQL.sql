-- USE bd_ridard ;

DROP TABLE ATravaillePour;
DROP TABLE Avion;
DROP TABLE Qualification;
DROP TABLE TypeAvion;
DROP TABLE Pilote;
DROP TABLE Compagnie;

CREATE TABLE Compagnie
	(
	idComp INTEGER,
	nomComp VARCHAR(30),
	pays VARCHAR(20),
	estLowCost INTEGER,

    CONSTRAINT pk_Compagnie PRIMARY KEY (idComp)
	)
;

CREATE TABLE Pilote
	(
	idPilote INTEGER,
	nomPilote VARCHAR(20),
	nbHVol INTEGER,
	compPil INTEGER,

    CONSTRAINT pk_Pilote PRIMARY KEY (idPilote),
	CONSTRAINT fk_Pilote_Compagnie FOREIGN KEY (compPil) REFERENCES Compagnie (idComp)
	)
;

CREATE TABLE TypeAvion
	(
	idTypeAvion VARCHAR(20),
	nbPassagers INTEGER,

    CONSTRAINT pk_TypeAvion PRIMARY KEY (idTypeAvion)
	)
;

CREATE TABLE Qualification
	(
	unPilote INTEGER,
	unTypeAvion VARCHAR(20),

	CONSTRAINT pk_Qualification	PRIMARY KEY (unPilote, unTypeAvion),
    CONSTRAINT fk_Qualification_Pilote FOREIGN KEY (unPilote) REFERENCES Pilote(idPilote),
    CONSTRAINT fk_Qualification_TypeAvion FOREIGN KEY (unTypeAvion) REFERENCES TypeAvion(idTypeAvion)
	)
;

CREATE TABLE Avion 
	(
	idAvion INTEGER,
	leTypeAvion VARCHAR(20),
	compAv INTEGER,
		
    CONSTRAINT pk_Avion	PRIMARY KEY (idAvion),
    CONSTRAINT fk_Avion_TypeAvion FOREIGN KEY (leTypeAvion) REFERENCES TypeAvion(idTypeAvion),
    CONSTRAINT fk_Avion_Compagnie FOREIGN KEY (compAv) REFERENCES Compagnie(idComp)
	)
;

CREATE TABLE ATravaillePour
	(
	lePilote INTEGER,
	laComp INTEGER,

	CONSTRAINT pk_ATravaillePour PRIMARY KEY (lePilote, laComp),
    CONSTRAINT fk_ATravaillePour_Pilote FOREIGN KEY (lePilote) REFERENCES Pilote(idPilote),
    CONSTRAINT fk_ATravaillePour_Compagnie FOREIGN KEY (laComp) REFERENCES Compagnie(idComp)
	)
;
	