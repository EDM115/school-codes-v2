-----------------------------------------------------
-- On active l'affichage pour DBMS_OUTPUT.PUT_LINE --
-----------------------------------------------------

SET SERVEROUTPUT ON


---------------------------------------------------------------------------
-- Une augmentation de salaire ne doit pas dépasser 10% et une baisse 8% --
---------------------------------------------------------------------------

UPDATE Agent SET salaire = 4600 WHERE numAgent = 1; -- Pas inséré, le salaire augmente de plus de 10% ;
UPDATE Agent SET salaire = 1500 WHERE numAgent = 1; -- Pas inséré, le salaire diminue de plus de 8% ;
UPDATE Agent SET salaire = 4300 WHERE numAgent = 1 ;

-- Attention à l'ordre de vérification des contraintes :
--    * trigger de lignes BEFORE 
--    * contraintes d'intégrité (clé, existence, unicité et domaine)
--    * trigger de tables AFTER

ALTER TRIGGER trig_modifSalaire DISABLE;
UPDATE Agent SET salaire = 4700 WHERE numAgent = 1 ;

-- On réactive le trigger
ALTER TRIGGER trig_modifSalaire ENABLE;
UPDATE Agent SET salaire = 6000 WHERE numAgent = 1; -- Pas de modification, le salaire augmente de plus de 10% ;


-----------------------------------------------------------------------------------
-- Un client ne peut pas être conseillé par un agent portant le même nom que lui --
-----------------------------------------------------------------------------------
INSERT INTO Client(numClient, nomClient, prenomClient, adClient, dateNaissClient, sonAgent) VALUES (102,'Fontaine','Louis','5 rue de la république, Issy les moulineaux',TO_DATE('01/01/2000','DD/MM/YYYY'),10); -- Pas inséré, même nom que son agent ;
update Agent set nomAgent='Lambert' WHERE numAgent=2; -- Pas modifié, client ayant le même nom
update Agent set nomAgent='Paris' WHERE numAgent=2; 


-----------------------------------------------------------------------------------------------
-- Un client ne doit pas pouvoir effectuer un retrait dont le montant est supérieur au solde --
-----------------------------------------------------------------------------------------------

select * from Compte where numCompte=1;
INSERT INTO Operation(numOperation,typeOperation,montant,leclient,leCompte) VALUES (110,'RETRAIT',1000,1,1); -- Pas inséré, montant de retrait superieur au solde ;
INSERT INTO Operation(numOperation,typeOperation,montant,leclient,leCompte) VALUES (111,'RETRAIT',50,1,1); -- Inséré;
select * from Compte where numCompte=1;  -- solde mis à jour à 0;
INSERT INTO Operation(numOperation,typeOperation,montant,leclient,leCompte) VALUES (112,'DEPOT',500,1,1);
select * from Compte where numCompte=1; -- solde mis à jour à 500;

----------------------------------------------------------------------------------------
-- Un client ne doit pouvoir retirer de l'argent que sur un compte qui lui appartient --
----------------------------------------------------------------------------------------

INSERT INTO Operation(numOperation,dateOperation,typeOperation,montant,leclient,leCompte) VALUES (105,NULL,'RETRAIT',1200,1,2) ;-- Pas inséré,le compte n'appartient pas au client ;

-----------------------------------------------------------------------------------------------
-- Mise à jour du solde --
-----------------------------------------------------------------------------------------------
SELECT solde FROM Compte WHERE numCompte= 1; -- Solde 500
SELECT * FROM Operation WHERE leCompte=1; -- 4 opérations

INSERT INTO Operation(numOperation, TypeOperation, montant, leClient, leCompte) VALUES(200, 'RETRAIT', 10, 2, 1);

SELECT solde FROM Compte WHERE numCompte= 1; -- Solde 490
SELECT * FROM Operation WHERE leCompte=1; --5 opérations

INSERT INTO Operation(numOperation, TypeOperation, montant, leClient, leCompte) VALUES(201, 'DEPOT', 500, 2, 1);

SELECT solde FROM Compte WHERE numCompte= 1; -- Solde 990
SELECT * FROM Operation WHERE leCompte=1; -- 6 opérations

DELETE FROM Operation WHERE numOperation = 201;

SELECT solde FROM Compte WHERE numCompte= 1; --Solde 490
SELECT * FROM Operation WHERE leCompte=1; --5 opérations

DELETE FROM Operation WHERE numOperation = 200;

SELECT solde FROM Compte WHERE numCompte= 1; --Solde 500
SELECT * FROM Operation WHERE leCompte=1; --4 opérations

INSERT INTO Operation(numOperation, TypeOperation, montant, leClient, leCompte) VALUES(200, 'RETRAIT', 10, 2, 1);
INSERT INTO Operation(numOperation, TypeOperation, montant, leClient, leCompte) VALUES(201, 'DEPOT', 500, 2, 1);

SELECT solde FROM Compte WHERE numCompte= 1; -- Solde 990
SELECT * FROM Operation WHERE leCompte=1; --6 opérations

UPDATE Operation SET montant=30, typeOperation = 'DEPOT' WHERE numOperation = 200;

SELECT solde FROM Compte WHERE numCompte= 1; -- Solde 1030
SELECT * FROM Operation WHERE leCompte=1; -- 6 opérations
SELECT solde FROM Compte WHERE numCompte= 14; -- Solde 2500
SELECT * FROM Operation WHERE leCompte=14; -- 0 opérations

UPDATE Operation SET montant=1000, typeOperation = 'RETRAIT', leCompte= 14 WHERE numOperation = 201;

SELECT solde FROM Compte WHERE numCompte= 1; -- Solde 530
SELECT * FROM Operation WHERE leCompte=1; -- 5 opérations
SELECT solde FROM Compte WHERE numCompte= 14; -- Solde 1500
SELECT * FROM Operation WHERE leCompte=14; -- 1 opérations




