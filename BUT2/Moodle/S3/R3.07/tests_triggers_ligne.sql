-----------------------------------------------------
-- On active l'affichage pour DBMS_OUTPUT.PUT_LINE --
-----------------------------------------------------

SET SERVEROUTPUT ON


---------------------------------------------------------------------------
-- Une augmentation de salaire ne doit pas dépasser 10% et une baisse 8% --
---------------------------------------------------------------------------

DELETE FROM Appartient;
DELETE FROM Operation;
DELETE FROM Compte;
DELETE FROM Client;
DELETE FROM Agent;
DELETE FROM Agence;

INSERT INTO Agence VALUES(1,'01 00 00 00 01','adresse1');

INSERT INTO Agent VALUES(1,'nomAgent1','prenomAgent1',2000,1,1);
UPDATE Agent SET salaire = 2300 WHERE numAgent = 1 ;
UPDATE Agent SET salaire = 1500 WHERE numAgent = 1 ;

-- Attention à l'ordre de vérification des contraintes :
--    * trigger de lignes BEFORE 
--    * contraintes d'intégrité (clé, existence, unicité et domaine)
--    * trigger de tables AFTER

ALTER TRIGGER trig_modifSalaire DISABLE;
UPDATE Agent SET salaire = 1500 WHERE numAgent = 1 ;

-- On réactive le trigger
ALTER TRIGGER trig_modifSalaire ENABLE;
UPDATE Agent SET salaire = 1500 WHERE numAgent = 1 ;


-----------------------------------------------------------------------------------
-- Un client ne peut pas être conseillé par un agent portant le même nom que lui --
-----------------------------------------------------------------------------------

DELETE FROM Appartient;
DELETE FROM Operation;
DELETE FROM Compte;
DELETE FROM Client;
DELETE FROM Agent;
DELETE FROM Agence;

INSERT INTO Agence VALUES(1,'01 00 00 00 01','adresse1');
INSERT INTO Agent VALUES(1,'nom1','prenomAgent1',2000,1,1);
INSERT INTO Client VALUES (1,'nom1','prenomClient1','adresseCient1',TO_DATE('01/01/2000','DD/MM/YYYY'),1);


-----------------------------------------------------------------------------------------------
-- Un client ne doit pas pouvoir effectuer un retrait dont le montant est supérieur au solde --
-----------------------------------------------------------------------------------------------

DELETE FROM Appartient;
DELETE FROM Operation;
DELETE FROM Compte;
DELETE FROM Client;
DELETE FROM Agent;
DELETE FROM Agence;

INSERT INTO Agence VALUES(1,'01 00 00 00 01','adresse1');
INSERT INTO Agent VALUES(1,'nomAgent1','prenomAgent1',2000,1,1);
INSERT INTO Client VALUES (1,'nomClient1','prenomClient1','adresseCient1',TO_DATE('01/01/2000','DD/MM/YYYY'),1);
INSERT INTO Compte VALUES (01,1000,'COURANT');
INSERT INTO Appartient VALUES (01,1);

INSERT INTO Operation VALUES (1,NULL,'RETRAIT',1200,1,01);


----------------------------------------------------------------------------------------
-- Un client ne doit pouvoir retirer de l'argent que sur un compte qui lui appartient --
----------------------------------------------------------------------------------------

DELETE FROM Appartient;
DELETE FROM Operation;
DELETE FROM Compte;
DELETE FROM Client;
DELETE FROM Agent;
DELETE FROM Agence;

INSERT INTO Agence VALUES(1,'01 00 00 00 01','adresse1');
INSERT INTO Agent VALUES(1,'nomAgent1','prenomAgent1',2000,1,1);
INSERT INTO Client VALUES (1,'nomClient1','prenomClient1','adresseCient1',TO_DATE('01/01/2000','DD/MM/YYYY'),1);
INSERT INTO Compte VALUES (01,1000,'COURANT');
INSERT INTO Compte VALUES (02,NULL,'EPARGNE');

INSERT INTO Appartient VALUES (01,1);
INSERT INTO Operation VALUES (1,NULL,'RETRAIT',200,1,02);