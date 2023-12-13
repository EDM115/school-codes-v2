-----------------------------------------------------
-- On active l'affichage pour DBMS_OUTPUT.PUT_LINE --
-----------------------------------------------------

SET SERVEROUTPUT ON;


---------------------------------------------------------------------------
-- Une augmentation de salaire ne doit pas dépasser 10% et une baisse 8% --
---------------------------------------------------------------------------

UPDATE Agent SET salaire = 4600 WHERE numAgent = 1; -- Pas inséré, le salaire augmente de plus de 10% ;
UPDATE Agent SET salaire = 1500 WHERE numAgent = 1; -- Pas inséré, le salaire diminue de plus de 8% ;
UPDATE Agent SET salaire = 4300 WHERE numAgent = 1;

-- Attention à l'ordre de vérification des contraintes :
--    * trigger de lignes BEFORE 
--    * contraintes d'intégrité (clé, existence, unicité et domaine)
--    * trigger de tables AFTER

ALTER TRIGGER trig_modifSalaire DISABLE;
UPDATE Agent SET salaire = 4700 WHERE numAgent = 1;

-- On réactive le trigger
ALTER TRIGGER trig_modifSalaire ENABLE;
UPDATE Agent SET salaire = 6000 WHERE numAgent = 1; -- Pas de modification, le salaire augmente de plus de 10% ;


-----------------------------------------------------------------------------------
-- Un client ne peut pas être conseillé par un agent portant le même nom que lui --
-----------------------------------------------------------------------------------
INSERT INTO Client(numClient, nomClient, prenomClient, adClient, dateNaissClient, sonAgent) VALUES (102,'Fontaine','Louis','5 rue de la république, Issy les moulineaux',TO_DATE('01/01/2000','DD/MM/YYYY'),10); -- Pas inséré, même nom que son agent;
update Agent set nomAgent='Lambert' WHERE numAgent=2; -- Pas modifié, client ayant le même nom
update Agent set nomAgent='Paris' WHERE numAgent=2; 


-----------------------------------------------------------------------------------------------
-- Un client ne doit pas pouvoir effectuer un retrait dont le montant est supérieur au solde --
-----------------------------------------------------------------------------------------------

select * from Compte where numCompte=1;
INSERT INTO Operation(numOperation,dateOperation,typeOperation,montant,leclient,leCompte) VALUES (110,NULL,'RETRAIT',1000,1,1); -- Pas inséré, montant de retrait superieur au solde;
INSERT INTO Operation(numOperation,dateOperation,typeOperation,montant,leclient,leCompte) VALUES (111,NULL,'RETRAIT',50,1,1); -- Inséré;
select * from Compte where numCompte=1;  -- solde mis à jour à 0;
INSERT INTO Operation(numOperation,dateOperation,typeOperation,montant,leclient,leCompte) VALUES (112,NULL,'DEPOT',500,1,1);
select * from Compte where numCompte=1; -- solde mis à jour à 500;

----------------------------------------------------------------------------------------
-- Un client ne doit pouvoir retirer de l'argent que sur un compte qui lui appartient --
----------------------------------------------------------------------------------------

INSERT INTO Operation(numOperation,dateOperation,typeOperation,montant,leclient,leCompte) VALUES (105,NULL,'RETRAIT',1200,1,2) ;-- Pas inséré,le compte n'appartient pas au client ;
