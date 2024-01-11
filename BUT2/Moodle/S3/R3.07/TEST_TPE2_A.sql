SAVEPOINT repere;
INSERT INTO Agent(numAgent, estDirecteur, sonAgence, salaire)  VALUES(50, 0, 1, 4000);
INSERT INTO Agent(numAgent, estDirecteur, sonAgence, salaire)  VALUES(51, 0, 1, 4000);
SELECT numAgent, sonAgence, salaire FROM Agent WHERE estDirecteur=1;
/*
  NUMAGENT  SONAGENCE    SALAIRE
---------- ---------- ----------
         1          1       4000
         4          2       3000
         7          3       2000
        10          4       2700
*/
SELECT numAgent, salaire FROM Agent WHERE sonAgence=1;
/*
  NUMAGENT    SALAIRE
---------- ----------
        50       4000
        51       4000
         1       4000
         2       1800
         9       1850
*/
UPDATE NonDir_Vue SET estDirecteur = 1 WHERE numAgent IN (51, 50);
SELECT numAgent, sonAgence, salaire FROM Agent WHERE estDirecteur=1;
/*
      NUMAGENT  SONAGENCE    SALAIRE
    ---------- ---------- ----------
        51          1       4000
         4          2       3000
         7          3       2000
        10          4       2700

*/
ROLLBACK repere;

SAVEPOINT repere;
INSERT INTO Agent(numAgent, estDirecteur, sonAgence, salaire)  VALUES(52, 0, 2, 3000);
SELECT numAgent, sonAgence, salaire FROM Agent WHERE estDirecteur=1;
/*
  NUMAGENT  SONAGENCE    SALAIRE
---------- ---------- ----------
         1          1       4000
         4          2       3000
         7          3       2000
        10          4       2700
*/
SELECT numAgent, salaire FROM Agent WHERE sonAgence=2;
/*
  NUMAGENT    SALAIRE
---------- ----------
        52       3000
         3       1750
         4       3000
*/
UPDATE NonDir_Vue SET numAgent= 53, estDirecteur=1 WHERE numAgent = 52;
SELECT numAgent, sonAgence, salaire FROM Agent WHERE estDirecteur=1;
/*
  NUMAGENT  SONAGENCE    SALAIRE
---------- ---------- ----------
        53          2       3000
         1          1       4000
         7          3       2000
        10          4       2700
*/
ROLLBACK repere;

SAVEPOINT repere;
INSERT INTO Agent(numAgent, estDirecteur, sonAgence, salaire)  VALUES(54, 0, 1, 3000);
SELECT numAgent, sonAgence, salaire FROM Agent WHERE estDirecteur=1;
/*
  NUMAGENT  SONAGENCE    SALAIRE
---------- ---------- ----------
         1          1       4000
         4          2       3000
         7          3       2000
        10          4       2700
*/
SELECT numAgent, salaire FROM Agent WHERE sonAgence=2;
/*
  NUMAGENT    SALAIRE
---------- ----------
         3       1750
         4       3000
*/
SELECT numAgent, salaire FROM Agent WHERE sonAgence=1;
/*
  NUMAGENT    SALAIRE
---------- ----------
        54       3000
         1       4000
         2       1800
         9       1850
*/
UPDATE NonDir_Vue SET numAgent= 55, estDirecteur=1, sonAgence=2 WHERE numAgent = 54;

SELECT numAgent, sonAgence, salaire FROM Agent WHERE estDirecteur=1;
/*
  NUMAGENT  SONAGENCE    SALAIRE
---------- ---------- ----------
        55          2       3000
         1          1       4000
         7          3       2000
        10          4       2700
*/
ROLLBACK repere;