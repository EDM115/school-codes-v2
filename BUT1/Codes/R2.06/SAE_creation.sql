DROP TABLE Comptage;
DROP TABLE Compteur;
DROP TABLE DateInfo;
DROP TABLE Quartier;


CREATE TABLE Quartier(
		idQuartier INT, 
		PRIMARY KEY (idQuartier),
		nomQuartier VARCHAR(50),
        longeurPisteVelo FLOAT
);
     
CREATE TABLE Compteur(
		idCompteur INT,
		PRIMARY KEY (idCompteur),
        nomCompteur VARCHAR(50),
        sens VARCHAR(20),
         --   CONSTRAINT ck_sens CHECK(UPPER(sens) IN('NORD','SUD','EST','OUEST')),
		coord_X FLOAT,
        coord_Y FLOAT,
        leQuartier INT,
		FOREIGN KEY (leQuartier) REFERENCES Quartier(idQuartier)
);

CREATE TABLE DateInfo(
		laDate DATE,
		PRIMARY KEY (laDate),
		tempMoy FLOAT,
        jour VARCHAR(10)
            CONSTRAINT ck_jour CHECK(UPPER(jour) IN('LUNDI','MARDI','MERCREDI','JEUDI','VENDREDI','SAMEDI','DIMANCHE')),
		vacances VARCHAR(20)
);

CREATE TABLE Comptage(
		leCompteur INT,
		FOREIGN KEY (leCompteur) REFERENCES Compteur(idCompteur),
        dateComptage DATE,
		FOREIGN KEY (dateComptage) REFERENCES DateInfo(laDate),
		h00 INT,
        h01 INT,
        h02 INT,
        h03 INT,
        h04 INT,
        h05 INT,
        h06 INT,
        h07 INT,
        h08 INT,
        h09 INT,
        h10 INT,
        h11 INT,
        h12 INT,
        h13 INT,
        h14 INT,
        h15 INT,
        h16 INT,
        h17 INT,
        h18 INT,
        h19 INT,
        h20 INT,
        h21 INT,
        h22 INT,
        h23 INT,
        presenceAnomalie VARCHAR(10)
            CONSTRAINT ck_anomalie CHECK(UPPER(presenceAnomalie) IN('FORTE','FAIBLE')),
        
        PRIMARY KEY (leCompteur,dateComptage)
);

