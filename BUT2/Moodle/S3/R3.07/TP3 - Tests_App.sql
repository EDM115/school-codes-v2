---------------------------
-- Préparation des tests --
---------------------------


-- Vidage des tables
--------------------

DELETE FROM Appartient;
DELETE FROM Operation;
DELETE FROM Compte;
DELETE FROM Client;
DELETE FROM Agent;
DELETE FROM Agence;


-- Réinitialisation des séquences
---------------------------------
	
DROP SEQUENCE seq_Agence;
CREATE SEQUENCE seq_Agence;

DROP SEQUENCE seq_Agent;
CREATE SEQUENCE seq_Agent;

DROP SEQUENCE seq_Client;
CREATE SEQUENCE seq_Client;

DROP SEQUENCE seq_Compte;
CREATE SEQUENCE seq_Compte;

DROP SEQUENCE seq_Operation;
CREATE SEQUENCE seq_Operation;


-- Insertion d'agences
----------------------

INSERT ALL
	INTO Agence VALUES (1, '06.00.00.00.01', 'adresse agence 1')
	INTO Agence VALUES (2, '06.00.00.00.02', 'adresse agence 2')
	INTO Agence VALUES (3, '06.00.00.00.03', 'adresse agence 3')
	INTO Agence VALUES (4, '06.00.00.00.04', 'adresse agence 4')
SELECT * FROM DUAL;


-- Insertion d'agents
---------------------

INSERT ALL
	INTO Agent VALUES (1, 'nom agent 1', 'prenom agent 1', 1555, 0, 1)
	INTO Agent VALUES (2, 'nom agent 2', 'prenom agent 2', 3000, 1, 1)

	INTO Agent VALUES (5, 'nom agent 5', 'prenom agent 5', 4500, 1, 2)
	INTO Agent VALUES (3, 'nom agent 3', 'prenom agent 3', 4000, 0, 2)
	INTO Agent VALUES (4, 'nom agent 4', 'prenom agent 4', 1750, 0, 2)

	INTO Agent VALUES (12, 'nom agent 12', 'prenom agent 12', 2600,1, 3)
	INTO Agent VALUES (7, 'nom agent 7', 'prenom agent 7', 1600, 0, 3)
	INTO Agent VALUES (8, 'nom agent 8', 'prenom agent 8', 1600, 0, 3)
	INTO Agent VALUES (9, 'nom agent 9', 'prenom agent 9', 1600, 0, 3)
	INTO Agent VALUES (10, 'nom agent 10', 'prenom agent 10', 1600, 0, 3)
	INTO Agent VALUES (11, 'nom agent 11', 'prenom agent 11', 1600, 0, 3)
  
	INTO Agent VALUES (6, 'nom agent 6', 'prenom agent 6', 2500, 1, 4)
SELECT * FROM DUAL;


-- Insertion de clients
-----------------------

INSERT ALL
	INTO Client VALUES (1, 'nom client 1', 'prenom client 1', 'adresse client 1', TO_DATE('01/01/1971','DD/MM/YYYY'),1)
	INTO Client VALUES (2, 'nom client 2', 'prenom client 2', 'adresse client 2', TO_DATE('02/02/1971','DD/MM/YYYY'),1)

	INTO Client VALUES (3, 'nom client 3', 'prenom client 3', 'adresse client 3', TO_DATE('03/03/1972','DD/MM/YYYY'),2)

	INTO Client VALUES (4, 'nom client 4', 'prenom client 4', 'adresse client 4', TO_DATE('04/04/1973','DD/MM/YYYY'),3)
	INTO Client VALUES (5, 'nom client 5', 'prenom client 5', 'adresse client 5', TO_DATE('05/05/1973','DD/MM/YYYY'),3)
	INTO Client VALUES (6, 'nom client 6', 'prenom client 6', 'adresse client 6', TO_DATE('06/06/1973','DD/MM/YYYY'),3)

	INTO Client VALUES (7, 'nom client 7', 'prenom client 7', 'adresse client 7', TO_DATE('07/07/1976','DD/MM/YYYY'),6)
	INTO Client VALUES (8, 'nom client 8', 'prenom client 8', 'adresse client 8', TO_DATE('08/08/1976','DD/MM/YYYY'),6)
	INTO Client VALUES (9, 'nom client 9', 'prenom client 9', 'adresse client 9', TO_DATE('09/09/1976','DD/MM/YYYY'),6)

	INTO Client VALUES (10, 'nom client 10', 'prenom client 10', 'adresse client 10', TO_DATE('10/10/1982','DD/MM/YYYY'),12)
	INTO Client VALUES (11, 'nom client 11', 'prenom client 11', 'adresse client 11', TO_DATE('11/11/1982','DD/MM/YYYY'),12)
	INTO Client VALUES (12, 'nom client 12', 'prenom client 12', 'adresse client 12', TO_DATE('12/12/1982','DD/MM/YYYY'),12)
	INTO Client VALUES (13, 'nom client 13', 'prenom client 13', 'adresse client 13', TO_DATE('13/01/1982','DD/MM/YYYY'),12)
SELECT * FROM DUAL;


-- Insertion de comptes
-----------------------

INSERT ALL
	INTO Compte VALUES (1, -50, 'COURANT')
	INTO Compte VALUES (2, 13000, 'EPARGNE')
	INTO Compte VALUES (3, 1000, 'COURANT')
	INTO Compte VALUES (4, 500, 'COURANT')
	INTO Compte VALUES (5, 100000, 'EPARGNE')
	INTO Compte VALUES (6, 50, 'EPARGNE')
	INTO Compte VALUES (7, 500, 'COURANT')
	INTO Compte VALUES (8, 4000, 'COURANT')
	INTO Compte VALUES (9, 500, 'COURANT')
	INTO Compte VALUES (10, 14000, 'EPARGNE')
	INTO Compte VALUES (11, 2000, 'COURANT')
	INTO Compte VALUES (12, 8500, 'EPARGNE')
	INTO Compte VALUES (13, 18500, 'EPARGNE')
	INTO Compte VALUES (14, 2500, 'COURANT')
	INTO Compte VALUES (15, 85000, 'EPARGNE')
SELECT * FROM DUAL;


-- Insertion dans Appartient
----------------------------

INSERT ALL
	INTO Appartient VALUES (1,1)
	INTO Appartient VALUES (1,2)
	INTO Appartient VALUES (2,3)
	INTO Appartient VALUES (3,4)
	INTO Appartient VALUES (4,5)
	INTO Appartient VALUES (5,6)
	INTO Appartient VALUES (6,7)
	INTO Appartient VALUES (7,8)
	INTO Appartient VALUES (8,9)
	INTO Appartient VALUES (9,10)
	INTO Appartient VALUES (10,11)
	INTO Appartient VALUES (11,12)
	INTO Appartient VALUES (12,13)
	INTO Appartient VALUES (13,1)
	INTO Appartient VALUES (14,2)
	INTO Appartient VALUES (15,3)
SELECT * FROM DUAL;


-----------
-- Tests --
-----------


-- fonction estDebiteur

SELECT fct_estDebiteur(1)
FROM DUAL
;


-- un virement valide

SELECT numcompte, solde
FROM Compte
WHERE numcompte IN (2,3)
;

-- les paramètres sont respectivement le compte "source" débité, le compte "but" crédité, le montant et le client (débité) réalisant le virement
EXECUTE proc_virement(2, 3, 100, 3) ;

SELECT numcompte, solde
FROM Compte
WHERE numcompte IN (2,3)
;

SELECT *
FROM Operation
;


-- un virement à partir d'un  compte débiteur

EXECUTE proc_virement(1, 2, 100, 1) ;


-- un virement à partir d'un compte qui n'a pas un solde suffisant

EXECUTE proc_virement(6, 3, 100, 7) ;


-- un virement à partir d'un compte qui n'appartient pas au client émetteur

EXECUTE proc_virement(2, 3, 100, 6) ;