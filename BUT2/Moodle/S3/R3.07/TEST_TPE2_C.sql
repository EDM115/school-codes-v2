SAVEPOINT repere;
select * from Appartient where unClient = 1;
/*  
  UNCOMPTE   UNCLIENT
---------- ----------
         1          1
        13          1
        17          1
*/
select * from Appartient where unClient = 2;
/*  
  UNCOMPTE   UNCLIENT
---------- ----------
         1          2
        14          2
        18          2
*/
select count(*) from Compte;
/*
  COUNT(*)
----------
        20
*/
DELETE FROM Client_Compte WHERE numClient IN (1,2);
select count(*) from Compte;
/*
  COUNT(*)
----------
        15
*/
select * from Appartient where unClient = 1; -- Aucune ligne
select * from Appartient where unClient = 2; -- Aucune ligne
ROLLBACK repere;

SAVEPOINT repere;
select * from Appartient where unClient = 1;
/*  
  UNCOMPTE   UNCLIENT
---------- ----------
         1          1
        13          1
        17          1
*/
select * from Appartient where unClient = 2;
/*  
  UNCOMPTE   UNCLIENT
---------- ----------
         1          2
        14          2
        18          2
*/
select count(*) from Compte;
/*
  COUNT(*)
----------
        20
*/
DELETE FROM Client_Compte WHERE numClient = 1;
select count(*) from Compte;
/*
  COUNT(*)
----------
        18
*/
select * from Appartient where unClient = 1; -- Aucune ligne
select * from Appartient where unClient = 2; 
/*  
  UNCOMPTE   UNCLIENT
---------- ----------
         1          2
        14          2
        18          2
*/
ROLLBACK repere;

SAVEPOINT repere;
select * from Appartient where unCompte= 1;
/*
  UNCOMPTE   UNCLIENT
---------- ----------
         1          1
         1          2
*/
select count(*) from Compte;
/*
  COUNT(*)
----------
        20
*/
DELETE FROM Client_Compte WHERE numCompte = 1;
select count(*) from Compte;
/*
  COUNT(*)
----------
        19
*/
select * from Appartient where unCompte= 1; -- Aucune ligne
ROLLBACK repere;