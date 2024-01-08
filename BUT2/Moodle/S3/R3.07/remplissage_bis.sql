DELETE FROM Appartient;
DELETE FROM Operation;
DELETE FROM Compte;
DELETE FROM Client;
ALTER TABLE Agent DISABLE CONSTRAINT fk_Agent_Agence;
ALTER TABLE Agence DISABLE CONSTRAINT fk_Agence_Agent;
DELETE FROM Agent;
DELETE FROM Agence;
ALTER TABLE Agent ENABLE CONSTRAINT fk_Agent_Agence;
ALTER TABLE Agence ENABLE CONSTRAINT fk_Agence_Agent;

-- Pour pouvoir insérer une agence sans directeur, on doit désactiver la contrainte nn_sonDirecteur

ALTER TABLE Agence DISABLE CONSTRAINT nn_sonDirecteur;

-- Insertion de données dans la table Agence
INSERT ALL 
	INTO Agence(numAgence,telAgence,adAgence) VALUES (1, '0123456789', 'Paris')
	INTO Agence(numAgence,telAgence,adAgence) VALUES (2, '0987654321', 'Marseille')
	INTO Agence(numAgence,telAgence,adAgence) VALUES (3, '0234567890', 'Lyon')
	INTO Agence(numAgence,telAgence,adAgence) VALUES (4, '0234567894', 'Vannes')
SELECT * FROM DUAL;

-- Insertion de données dans la table Agent
INSERT ALL 
	INTO Agent(numAgent, nomAgent, prenomAgent, salaire, sonAgence)  VALUES (1, 'Dupont', 'Jean', 4000, 1)
	INTO Agent(numAgent, nomAgent, prenomAgent, salaire, sonAgence)  VALUES (2, 'Martin', 'Marie', 1800,  1)
	INTO Agent(numAgent, nomAgent, prenomAgent, salaire, sonAgence)  VALUES (3, 'Lefevre', 'Malo', 1750,  2)
	INTO Agent(numAgent, nomAgent, prenomAgent, salaire, sonAgence)  VALUES (4, 'Dubois', 'Dorothée', 3000, 2)
	INTO Agent(numAgent, nomAgent, prenomAgent, salaire, sonAgence)  VALUES (5, 'Bertrand', 'François', 2000, 3)
	INTO Agent(numAgent, nomAgent, prenomAgent, salaire, sonAgence)  VALUES (6, 'Leroux', 'Catherine', 1750, 3)
	INTO Agent(numAgent, nomAgent, prenomAgent, salaire, sonAgence)  VALUES (7, 'Morel', 'Luc', 2000, 3)
	INTO Agent(numAgent, nomAgent, prenomAgent, salaire, sonAgence)  VALUES (8, 'Lemoine', 'Isabelle', 1750, 3)
	INTO Agent(numAgent, nomAgent, prenomAgent, salaire, sonAgence)  VALUES (9, 'Girard', 'Thomas', 1850, 1)
	INTO Agent(numAgent, nomAgent, prenomAgent, salaire, sonAgence)  VALUES (10, 'Fontaine', 'Bérangère', 2700, 4)
SELECT * FROM DUAL;

-- L'agent 1 devient directeur de l'agence 1

UPDATE Agence SET sonDirecteur = 1 WHERE numAgence = 1;

-- L'agent 4 devient directeur de l'agence 2

UPDATE Agence SET sonDirecteur = 4 WHERE numAgence = 2;

-- L'agent 7 devient directeur de l'agence 3

UPDATE Agence SET sonDirecteur = 7 WHERE numAgence = 3;

-- L'agent 10 devient directeur de l'agence 4

UPDATE Agence SET sonDirecteur = 10 WHERE numAgence = 4;

-- On peut maintenant réactiver la contrainte nn_sonDirecteur

ALTER TABLE Agence ENABLE CONSTRAINT nn_sonDirecteur;


--Insertion de données dans la table Client
INSERT ALL 
	INTO Client(numClient, nomClient, prenomClient, adClient, dateNaissClient, sonAgent) VALUES (1, 'Dumas', 'Emma', '1 Rue de la Paix, Paris', TO_DATE('1990-01-01', 'YYYY-MM-DD'), 1)
	INTO Client(numClient, nomClient, prenomClient, adClient, dateNaissClient, sonAgent) VALUES (2, 'Lambert', 'Louis', '25 Avenue du Prado, Marseille', TO_DATE('1985-02-15', 'YYYY-MM-DD'), 2)
	INTO Client(numClient, nomClient, prenomClient, adClient, dateNaissClient, sonAgent) VALUES (3, 'Roux', 'Julie', '10 Rue Bellecour, Lyon', TO_DATE('1988-05-20', 'YYYY-MM-DD'), 3)
	INTO Client(numClient, nomClient, prenomClient, adClient, dateNaissClient, sonAgent) VALUES (4, 'Garnier', 'Alexandre', '5 Place du Capitole, Toulouse', TO_DATE('1992-08-10', 'YYYY-MM-DD'), 4)
	INTO Client(numClient, nomClient, prenomClient, adClient, dateNaissClient, sonAgent) VALUES (5, 'Leroy', 'Sophie', '15 Promenade des Anglais, Nice', TO_DATE('1980-12-05', 'YYYY-MM-DD'), 5)
	INTO Client(numClient, nomClient, prenomClient, adClient, dateNaissClient, sonAgent) VALUES (6, 'Leclerc', 'Victor', '7 Rue de la République, Paris', TO_DATE('1982-04-30', 'YYYY-MM-DD'), 6)
	INTO Client(numClient, nomClient, prenomClient, adClient, dateNaissClient, sonAgent) VALUES (7, 'Roy', 'Elodie', '30 Cours Julien, Marseille', TO_DATE('1995-06-25', 'YYYY-MM-DD'), 7)
	INTO Client(numClient, nomClient, prenomClient, adClient, dateNaissClient, sonAgent) VALUES (8, 'Marie', 'Thomas', '2 Place des Terreaux, Lyon', TO_DATE('1998-03-12', 'YYYY-MM-DD'), 8)
	INTO Client(numClient, nomClient, prenomClient, adClient, dateNaissClient, sonAgent) VALUES (9, 'Lefevre', 'Laura', '40 Rue Saint-Rome, Toulouse', TO_DATE('1987-09-08', 'YYYY-MM-DD'), 9)
	INTO Client(numClient, nomClient, prenomClient, adClient, dateNaissClient, sonAgent) VALUES (10, 'Fournier', 'Antoine', '20 Avenue Jean Médecin, Nice', TO_DATE('1984-11-18', 'YYYY-MM-DD'), 10)
	INTO Client(numClient, nomClient, prenomClient, adClient, dateNaissClient, sonAgent) VALUES (11, 'Petit', 'Camille', '3 Rue de la Roquette, Paris', TO_DATE('1993-07-22', 'YYYY-MM-DD'), 1)
	INTO Client(numClient, nomClient, prenomClient, adClient, dateNaissClient, sonAgent) VALUES (12, 'Garcia', 'Mathieu', '12 Boulevard Longchamp, Marseille', TO_DATE('1989-09-14', 'YYYY-MM-DD'), 2)
	INTO Client(numClient, nomClient, prenomClient, adClient, dateNaissClient, sonAgent) VALUES (13, 'Blanc', 'Eva', '8 Quai Jean Moulin, Lyon', TO_DATE('1986-11-28', 'YYYY-MM-DD'), 3)
	INTO Client(numClient, nomClient, prenomClient, adClient, dateNaissClient, sonAgent) VALUES (14, 'Guillaume', 'Julia', '6 Place Wilson, Toulouse', TO_DATE('1991-04-03', 'YYYY-MM-DD'), 4)
	INTO Client(numClient, nomClient, prenomClient, adClient, dateNaissClient, sonAgent) VALUES (15, 'Fernandez', 'Lucas', '25 Avenue Jean Médecin, Nice', TO_DATE('1997-01-15', 'YYYY-MM-DD'), 5)
	INTO Client(numClient, nomClient, prenomClient, adClient, dateNaissClient, sonAgent) VALUES (16, 'Muller', 'Chloé', '5 Rue de Rivoli, Paris', TO_DATE('1983-08-07', 'YYYY-MM-DD'), 6)
	INTO Client(numClient, nomClient, prenomClient, adClient, dateNaissClient, sonAgent) VALUES (17, 'Legrand', 'Alexis', '18 Quai de Rive Neuve, Marseille', TO_DATE('1996-12-10', 'YYYY-MM-DD'), 7)
	INTO Client(numClient, nomClient, prenomClient, adClient, dateNaissClient, sonAgent) VALUES (18, 'Caron', 'Juliette', '15 Rue de la Bourse, Lyon', TO_DATE('1984-03-25', 'YYYY-MM-DD'), 8)
	INTO Client(numClient, nomClient, prenomClient, adClient, dateNaissClient, sonAgent) VALUES (19, 'Noel', 'Romain', '10 Place du Capitole, Toulouse', TO_DATE('1982-06-18', 'YYYY-MM-DD'), 9)
	INTO Client(numClient, nomClient, prenomClient, adClient, dateNaissClient, sonAgent) VALUES (20, 'Lecomte', 'Inès', '22 Promenade des Anglais, Nice', TO_DATE('1990-10-05', 'YYYY-MM-DD'), 10)
SELECT * FROM DUAL;


-- Insertion de données dans la table Compte
Execute inserer_compte(1, 50, 'COURANT',1);
Execute inserer_compte(2, 13000, 'EPARGNE',3);
Execute inserer_compte(3, 1000, 'COURANT',4);
Execute inserer_compte(4, 500, 'COURANT',5);
Execute inserer_compte(5, 100000, 'EPARGNE',6);
Execute inserer_compte(6, 50, 'EPARGNE',7);
Execute inserer_compte(7, 500, 'COURANT',8);
Execute inserer_compte(8, 4000, 'COURANT',20);
Execute inserer_compte(9, 500, 'COURANT',10);
Execute inserer_compte(10, 14000, 'EPARGNE',11);
Execute inserer_compte(11, 2000, 'COURANT',12);
Execute inserer_compte(12, 8500, 'EPARGNE',13);
Execute inserer_compte(13, 18500, 'EPARGNE',1);
Execute inserer_compte(14, 2500, 'COURANT',2);
Execute inserer_compte(15, 85000, 'EPARGNE',3);
Execute inserer_compte(16, 100000, 'EPARGNE',13);
Execute inserer_compte(17, 3000, 'COURANT',1);
Execute inserer_compte(18, 1000000, 'EPARGNE',2);
Execute inserer_compte(19, 1000000, 'COURANT',3);
Execute inserer_compte(20, 1, 'EPARGNE',20);

-- Insertion de données dans la table Appartient
INSERT ALL
	INTO Appartient(unCompte,unClient) VALUES (1,2)
SELECT * FROM DUAL;


-- Insertion de données dans la table Opération
INSERT INTO Operation(numOperation,dateOperation,typeOperation,montant,leclient,leCompte) VALUES (1, SYSDATE-2,'RETRAIT', 10, 2, 1);
INSERT INTO Operation(numOperation,dateOperation,typeOperation,montant,leclient,leCompte) VALUES (2, SYSDATE-3,'RETRAIT', 3000, 3, 2) ;
INSERT INTO Operation(numOperation,dateOperation,typeOperation,montant,leclient,leCompte) VALUES (3, SYSDATE-4,'DEPOT', 50, 3, 3) ;
INSERT INTO Operation(numOperation,dateOperation,typeOperation,montant,leclient,leCompte) VALUES (4, SYSDATE-4,'DEPOT', 200, 4, 4) ;
INSERT INTO Operation(numOperation,dateOperation,typeOperation,montant,leclient,leCompte) VALUES (5, SYSDATE-1, 'RETRAIT', 10000, 6, 5);
INSERT INTO Operation(numOperation,dateOperation,typeOperation,montant,leclient,leCompte) VALUES (6, SYSDATE-1, 'RETRAIT', 40, 7, 6);
INSERT INTO Operation(numOperation,dateOperation,typeOperation,montant,leclient,leCompte) VALUES (7, SYSDATE-1, 'DEPOT', 10000, 7, 7) ;
INSERT INTO Operation(numOperation,dateOperation,typeOperation,montant,leclient,leCompte) VALUES (8, SYSDATE-2, 'RETRAIT', 100, 9, 8) ;
INSERT INTO Operation(numOperation,dateOperation,typeOperation,montant,leclient,leCompte) VALUES (9, SYSDATE-2, 'DEPOT', 400, 9, 9) ;
INSERT INTO Operation(numOperation,dateOperation,typeOperation,montant,leclient,leCompte) VALUES (10, SYSDATE-3, 'DEPOT', 500, 10, 10) ;
INSERT INTO Operation(numOperation,dateOperation,typeOperation,montant,leclient,leCompte) VALUES (11, SYSDATE-3, 'RETRAIT', 600, 12, 11);
INSERT INTO Operation(numOperation,dateOperation,typeOperation,montant,leclient,leCompte) VALUES (12, SYSDATE-5,'DEPOT', 5000, 2, 1);
INSERT INTO Operation(numOperation, typeOperation, montant, leClient, leCompte) VALUES (13, 'RETRAIT', 700, 13, 12);
INSERT INTO Operation (numOperation, typeOperation, montant, leClient, leCompte) VALUES (14, 'DEPOT', 750, 13, 13) ;
