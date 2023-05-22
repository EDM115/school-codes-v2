DROP TABLE Comptage;
DROP TABLE Compteur;
DROP TABLE DateInfo;
DROP TABLE Quartier;


CREATE TABLE Quartier(
		idQuartier NUMBER 
            CONSTRAINT pk_Quartier PRIMARY KEY,
		nomQuartier VARCHAR2(50),
        longeurPisteVelo FLOAT
);
     
CREATE TABLE Compteur(
		idCompteur NUMBER
            CONSTRAINT pk_Compteur PRIMARY KEY,
        nomCompteur VARCHAR2(50),
        sens VARCHAR2(20),
         --   CONSTRAINT ck_sens CHECK(UPPER(sens) IN('NORD','SUD','EST','OUEST')),
		coord_X FLOAT,
        coord_Y FLOAT,
        leQuartier NUMBER
            CONSTRAINT fk_Quartier_Compteur REFERENCEs Quartier(idQuartier)
);

CREATE TABLE DateInfo(
		laDate DATE
            CONSTRAINT pk_DateInfo PRIMARY KEY,
		tempMoy FLOAT,
        jour VARCHAR2(10)
            CONSTRAINT ck_jour CHECK(UPPER(jour) IN('LUNDI','MARDI','MERCREDI','JEUDI','VENDREDI','SAMEDI','DIMANCHE')),
		vacances VARCHAR2(20)
);

CREATE TABLE Comptage(
		leCompteur NUMBER
            CONSTRAINT fk_Comptage_Compteur REFERENCEs Compteur(idCompteur),
        dateComptage DATE
            CONSTRAINT fk_Comptage_dateInfo REFERENCEs DateInfo(laDate),
		h00 NUMBER,
        h01 NUMBER,
        h02 NUMBER,
        h03 NUMBER,
        h04 NUMBER,
        h05 NUMBER,
        h06 NUMBER,
        h07 NUMBER,
        h08 NUMBER,
        h09 NUMBER,
        h10 NUMBER,
        h11 NUMBER,
        h12 NUMBER,
        h13 NUMBER,
        h14 NUMBER,
        h15 NUMBER,
        h16 NUMBER,
        h17 NUMBER,
        h18 NUMBER,
        h19 NUMBER,
        h20 NUMBER,
        h21 NUMBER,
        h22 NUMBER,
        h23 NUMBER,
        presenceAnomalie VARCHAR2(10)
            CONSTRAINT ck_anomalie CHECK(UPPER(presenceAnomalie) IN('FORTE','FAIBLE')),
        
        CONSTRAINT pk_Comptage PRIMARY KEY (leCompteur,dateComptage)
);
