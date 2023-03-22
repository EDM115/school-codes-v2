DROP TABLE Location ;
DROP TABLE Vehicule ;
DROP TABLE Modele ;
DROP TABLE Client ;

CREATE TABLE Client
(
    idClient NUMBER
        CONSTRAINT pk_Client PRIMARY KEY ,
    nom VARCHAR2( 30 )
        CONSTRAINT nn_nom NOT NULL,
    email VARCHAR2( 30 )
) ;
CREATE TABLE Modele
(
    nomModele VARCHAR2( 30 )
        CONSTRAINT pk_Modele PRIMARY KEY ,
    marque VARCHAR2( 30 )
        CONSTRAINT nn_marque NOT NULL
) ;

CREATE TABLE Vehicule
(
    immat VARCHAR2( 10 )
        CONSTRAINT pk_Vehicule PRIMARY KEY ,
    leModele VARCHAR2( 30 )
        CONSTRAINT fk_Vehicule_Modele REFERENCES Modele (nomModele)
        CONSTRAINT nn_leModele NOT NULL
) ;

CREATE TABLE Location
(
    unClient NUMBER
        CONSTRAINT fk_Location_Client REFERENCES Client ( idClient ) ,
    unVehicule VARCHAR2( 10 )
        CONSTRAINT fk_Location_Vehicule REFERENCES Vehicule (immat) ,
    dateDebut DATE,
    dateFin DATE,
    prix NUMBER,
        CONSTRAINT pk_Location PRIMARY KEY ( unClient , unVehicule )
) ;

ALTER TABLE Location ADD CONSTRAINT ck_dates CHECK (dateDebut <= dateFin);

INSERT INTO Client values (1,'Naert','lucie.naert@univ-ubs.fr');
INSERT INTO Client values (2,'Fleurquin','regis.fleurquin@univ-ubs.fr');
INSERT INTO Client values (3,'Ridard','anthony.ridard@univ-ubs.fr');
INSERT INTO Client values (4,'Godin','thibault.godin@univ-ubs.fr');
INSERT INTO Client values (5,'Pham',null);

INSERT INTO Modele values ('Modus','Renault');
INSERT INTO Modele values ('308','Peugeot');
INSERT INTO Modele values ('C4','Citroen');
INSERT INTO Modele values ('208','Peugeot');

INSERT INTO Vehicule values ('34562376','Modus');
INSERT INTO Vehicule values ('56373287','Modus');
INSERT INTO Vehicule values ('26786372','308');
INSERT INTO Vehicule values ('63876376','C4');
INSERT INTO Vehicule values ('53673526','C4');
INSERT INTO Vehicule values ('53236289','208');

INSERT INTO Location values ('1','26786372', TO_DATE('20020930','YYYYMMDD'),  TO_DATE('20021030','YYYYMMDD'), 200);
INSERT INTO Location values ('1','34562376', TO_DATE('20220321','YYYYMMDD'),  TO_DATE('20220322','YYYYMMDD'), 300);
INSERT INTO Location values ('3','34562376', TO_DATE('20220320','YYYYMMDD'),  TO_DATE('20220322','YYYYMMDD'), 250);
INSERT INTO Location values ('2','53673526', TO_DATE('20220123','YYYYMMDD'),  TO_DATE('20220130','YYYYMMDD'), 600);
INSERT INTO Location values ('1','56373287', TO_DATE('20210321','YYYYMMDD'),  TO_DATE('20210322','YYYYMMDD'), 240);
INSERT INTO Location values ('1','63876376', TO_DATE('20190321','YYYYMMDD'),  TO_DATE('20190322','YYYYMMDD'), 260);
INSERT INTO Location values ('1','53673526', TO_DATE('20180321','YYYYMMDD'),  TO_DATE('20180322','YYYYMMDD'), 340);
INSERT INTO Location values ('1','53236289', TO_DATE('20170321','YYYYMMDD'),  TO_DATE('20170322','YYYYMMDD'), 340);

