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

INSERT INTO Location values ('1','26786372', TO_DATE('20020930','YYYYMMDD'),  TO_DATE('20021030','YYYYMMDD'), 200);
INSERT INTO Location values ('1','34562376', TO_DATE('20220321','YYYYMMDD'),  TO_DATE('20220322','YYYYMMDD'), 300);
INSERT INTO Location values ('3','34562376', TO_DATE('20220320','YYYYMMDD'),  TO_DATE('20220322','YYYYMMDD'), 250);
INSERT INTO Location values ('2','53673526', TO_DATE('20220123','YYYYMMDD'),  TO_DATE('20220130','YYYYMMDD'), 600);




-- Chaque modèle possède au moins un véhicule (C1)

CREATE OR REPLACE VIEW vue_ModeleSansVehicule
AS
    SELECT nomModele
    FROM Modele
    MINUS
    SELECT leModele
    FROM Vehicule
;

    
SELECT *
FROM vue_ModeleSansVehicule
;

-- Un véhicule ne peut pas être loué par deux clients différents le même jour (C3)

CREATE OR REPLACE VIEW vue_loueLeMemeJour
AS
    SELECT DISTINCT l1.unVehicule
    FROM Location l1, Location l2
    WHERE l1.unVehicule = l2.unVehicule
    AND ((l1.datedebut <= l2.datedebut) AND (l1.dateFin >= l2.dateFin))
    AND l1.unclient != l2.unclient
    
;


SELECT *
FROM vue_loueLeMemeJour
;


-- Duree de location
CREATE OR REPLACE VIEW vue_duree
AS
    SELECT unclient, unvehicule, dateFin - datedebut as dureeEnJours
    FROM Location  
;

SELECT *
FROM vue_duree
;

CREATE OR REPLACE VIEW vue_locationWithDuree
AS
    SELECT Location.*, dureeEnJours
    FROM Location, vue_duree
    WHERE location.unclient = vue_duree.unclient
    AND location.unvehicule = vue_duree.unvehicule
;

SELECT *
FROM vue_locationWithDuree
;


-- (a) Afficher le nombre de clients sans email.
SELECT COUNT(*) - COUNT(email)
FROM Client;

-- ou bien 
SELECT COUNT(*)
FROM Client
WHERE email IS NULL ;

-- (b) Afficher le prix moyen d’une location.
SELECT AVG(prix)
FROM Location ;

-- (c) Pour chaque modèle trié dans l’ordre alphabétique, afficher tous les véhicules.
SELECT leModele, immat
FROM Vehicule
ORDER BY leModele ;

-- (d) Pour chaque client désigné par son nom et trié dans l’ordre alphabétique, afficher tous les
-- véhicules loués et les prix de location par ordre décroissant du prix, éventuellement rien.
SELECT nom, unVehicule, prix
FROM Client 
	LEFT JOIN Location ON idClient = unClient
ORDER BY nom, prix DESC ;

-- (e) Pour chaque véhicule loué, afficher le nombre de locations dans l’ordre décroissant.
SELECT unVehicule, COUNT(unClient) nbLoc
FROM Location
GROUP BY unVehicule
ORDER BY nbLoc DESC ;

-- (f) Pour les marques Peugeot, Renault et Volvo, afficher le nombre de modèles.
SELECT marque, COUNT(nomModele)
FROM Modele
WHERE marque IN ('Peugeot','Renault','Volvo')
GROUP BY marque ;

-- (g) Pour chaque client, afficher le nombre de locations, éventuellement 0.
SELECT idClient, COUNT(unVehicule) nbLoc
FROM Client 
	LEFT JOIN Location ON idClient = unClient
GROUP BY idClient ;

-- (h) Pour chaque client désigné par son nom, afficher le prix total des locations.
SELECT nom, SUM(prix) prixTotal
FROM Client 
	JOIN Location ON idClient = unClient
GROUP BY nom ;

-- (i) Afficher le(s) véhicule(s) ayant eu le maximumde locations.
SELECT unVehicule
FROM Location
GROUP BY unVehicule
HAVING COUNT(unClient) =
    (
	SELECT MAX(COUNT(unClient))
    FROM Location
	GROUP BY unVehicule
	) ;

-- (j) Afficher les noms des clients qui ont loué tous les modèles.

-- Pour tester la division
INSERT INTO Vehicule values ('53236289','208');
INSERT INTO Location values ('1','56373287', TO_DATE('20210321','YYYYMMDD'),  TO_DATE('20210322','YYYYMMDD'), 240);
INSERT INTO Location values ('1','63876376', TO_DATE('20190321','YYYYMMDD'),  TO_DATE('20190322','YYYYMMDD'), 260);
INSERT INTO Location values ('1','53673526', TO_DATE('20180321','YYYYMMDD'),  TO_DATE('20180322','YYYYMMDD'), 340);
INSERT INTO Location values ('1','53236289', TO_DATE('20170321','YYYYMMDD'),  TO_DATE('20170322','YYYYMMDD'), 340);

SELECT nom
FROM Client
WHERE NOT EXISTS
    (
	SELECT nomModele
    FROM Modele
    MINUS
    SELECT leModele
    FROM Vehicule 
		JOIN Location ON immat = unVehicule
    WHERE unclient = idClient
	) ;

