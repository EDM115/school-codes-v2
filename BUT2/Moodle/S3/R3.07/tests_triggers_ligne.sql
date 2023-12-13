-----------------------------------------------------
-- On active l'affichage pour DBMS_OUTPUT.PUT_LINE --
-----------------------------------------------------

SET SERVEROUTPUT ON


---------------------------------------------------------------------------
-- Une augmentation de salaire ne doit pas dépasser 10% et une baisse 8% --
---------------------------------------------------------------------------

UPDATE Agent SET salaire = 4600 WHERE numAgent = 1 ;
UPDATE Agent SET salaire = 1500 WHERE numAgent = 1 ;
UPDATE Agent SET salaire = 4300 WHERE numAgent = 1 ;

-- Attention à l'ordre de vérification des contraintes :
--    * trigger de lignes BEFORE 
--    * contraintes d'intégrité (clé, existence, unicité et domaine)
--    * trigger de tables AFTER

ALTER TRIGGER trig_modifSalaire DISABLE;
UPDATE Agent SET salaire = 4700 WHERE numAgent = 1 ;

-- On réactive le trigger
ALTER TRIGGER trig_modifSalaire ENABLE;
UPDATE Agent SET salaire = 6000 WHERE numAgent = 1 ;


-----------------------------------------------------------------------------------
-- Un client ne peut pas être conseillé par un agent portant le même nom que lui --
-----------------------------------------------------------------------------------
select * from Agent;
INSERT INTO Client VALUES (102,'Fontaine','Louis','5 rue de la république, Issy les moulineaux',TO_DATE('01/01/2000','DD/MM/YYYY'),10);


-----------------------------------------------------------------------------------------------
-- Un client ne doit pas pouvoir effectuer un retrait dont le montant est supérieur au solde --
-----------------------------------------------------------------------------------------------

select * from Compte where numCompte=1;
INSERT INTO Operation VALUES (110,NULL,'RETRAIT',1000,1,1);
INSERT INTO Operation VALUES (110,NULL,'RETRAIT',50,1,1);
select * from Compte where numCompte=1;

----------------------------------------------------------------------------------------
-- Un client ne doit pouvoir retirer de l'argent que sur un compte qui lui appartient --
----------------------------------------------------------------------------------------

INSERT INTO Operation VALUES (105,NULL,'RETRAIT',1200,1,2);
