SAVEPOINT repere;
select numAgent from Agent where sonAgence = 2;
/*
  NUMAGENT
----------
         3
         4
*/
select numClient, sonAgent from Client where sonAgent = 3;
/*
 NUMCLIENT   SONAGENT
---------- ----------
         3          3
        13          3
*/
select numClient, sonAgent  from Client where sonAgent = 4;
/*
NUMCLIENT   SONAGENT
---------- ----------
         4          4
        14          4
*/

DELETE FROM Agence_Agent WHERE numAgent = 3;

select numAgent from Agent where sonAgence = 2;
/*
  NUMAGENT
----------
         4
*/
select numClient, sonAgent  from Client where sonAgent = 3; -- Aucune ligne
select numClient, sonAgent  from Client where sonAgent = 4;
/*
 NUMCLIENT   SONAGENT
---------- ----------
         3          4
         4          4
        13          4
        14          4
*/
ROLLBACK repere;

SAVEPOINT repere;
select numAgent, estDirecteur from Agent where sonAgence = 1;
/*
  NUMAGENT ESTDIRECTEUR
---------- ------------
         1            1
         2            0
         9            0
*/
select numClient, sonAgent from Client where sonAgent = 2;
/*
 NUMCLIENT   SONAGENT
---------- ----------
         2          2
        12          2
*/        
select numClient, sonAgent from Client where sonAgent = 9;
/*
 NUMCLIENT   SONAGENT
---------- ----------
         9          9
        19          9
*/

DELETE FROM Agence_Agent WHERE sonAgence = 1;

select numAgent, estDirecteur from Agent where sonAgence = 1;
/*
  NUMAGENT ESTDIRECTEUR
---------- ------------
         1            1
*/
select numClient, sonAgent from Client where sonAgent = 2; -- Aucune ligne
      
select numClient, sonAgent from Client where sonAgent = 9; -- Aucune ligne

select numClient, sonAgent from Client where sonAgent = 1;
/*
 NUMCLIENT   SONAGENT
---------- ----------
         1          1
         2          1
         9          1
        11          1
        12          1
        19          1
*/
ROLLBACK repere;

SAVEPOINT repere;
select numAgent, estDirecteur from Agent where sonAgence = 1;
/*
  NUMAGENT ESTDIRECTEUR
---------- ------------
         1            1
         2            0
         9            0
*/
DELETE FROM Agence_Agent WHERE numAgent = 1;
select numAgent, estDirecteur from Agent where sonAgence = 1;
/*
  NUMAGENT ESTDIRECTEUR
---------- ------------
         1            1
         2            0
         9            0
*/
ROLLBACK repere;