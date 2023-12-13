DELETE FROM Appartient;
DELETE FROM Operation;
DELETE FROM Compte;
DELETE FROM Client;
DELETE FROM Agent;
DELETE FROM Agence;


-- Insertion de données dans la table Agence
INSERT ALL 
	INTO Agence(numAgence,telAgence,adAgence) VALUES (1, '0123456789', 'Paris')
	INTO Agence(numAgence,telAgence,adAgence) VALUES (2, '0987654321', 'Marseille')
	INTO Agence(numAgence,telAgence,adAgence) VALUES (3, '0234567890', 'Lyon')
	INTO Agence(numAgence,telAgence,adAgence) VALUES (4, '0234567894', 'Vannes')
SELECT * FROM DUAL;

-- Insertion de données dans la table Agent
INSERT ALL 
	INTO Agent(numAgent, nomAgent, prenomAgent, salaire, estDirecteur, sonAgence)  VALUES (1, 'Dupont', 'Jean', 4000, 1, 1)
	INTO Agent(numAgent, nomAgent, prenomAgent, salaire, estDirecteur, sonAgence)  VALUES (2, 'Martin', 'Marie', 1800, 0, 1)
	INTO Agent(numAgent, nomAgent, prenomAgent, salaire, estDirecteur, sonAgence)  VALUES (3, 'Lefevre', 'Malo', 1750, 0, 2)
	INTO Agent(numAgent, nomAgent, prenomAgent, salaire, estDirecteur, sonAgence)  VALUES (4, 'Dubois', 'Dorothée', 3000, 1, 2)
	INTO Agent(numAgent, nomAgent, prenomAgent, salaire, estDirecteur, sonAgence)  VALUES (5, 'Bertrand', 'François', 2000, 0, 3)
	INTO Agent(numAgent, nomAgent, prenomAgent, salaire, estDirecteur, sonAgence)  VALUES (6, 'Leroux', 'Catherine', 1750, 0, 3)
	INTO Agent(numAgent, nomAgent, prenomAgent, salaire, estDirecteur, sonAgence)  VALUES (7, 'Morel', 'Luc', 2000, 1, 3)
	INTO Agent(numAgent, nomAgent, prenomAgent, salaire, estDirecteur, sonAgence)  VALUES (8, 'Lemoine', 'Isabelle', 1750, 0, 3)
	INTO Agent(numAgent, nomAgent, prenomAgent, salaire, estDirecteur, sonAgence)  VALUES (9, 'Girard', 'Thomas', 1850, 0, 1)
	INTO Agent(numAgent, nomAgent, prenomAgent, salaire, estDirecteur, sonAgence)  VALUES (10, 'Fontaine', 'Bérangère', 2700, 1, 4)
SELECT * FROM DUAL;

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
INSERT ALL
	INTO Compte(numCompte, solde, typeCompte) VALUES (1, 50, 'COURANT')
	INTO Compte(numCompte, solde, typeCompte) VALUES (2, 13000, 'EPARGNE')
	INTO Compte(numCompte, solde, typeCompte) VALUES (3, 1000, 'COURANT')
	INTO Compte(numCompte, solde, typeCompte) VALUES (4, 500, 'COURANT')
	INTO Compte(numCompte, solde, typeCompte) VALUES (5, 100000, 'EPARGNE')
	INTO Compte(numCompte, solde, typeCompte) VALUES (6, 50, 'EPARGNE')
	INTO Compte(numCompte, solde, typeCompte) VALUES (7, 500, 'COURANT')
	INTO Compte(numCompte, solde, typeCompte) VALUES (8, 4000, 'COURANT')
	INTO Compte(numCompte, solde, typeCompte) VALUES (9, 500, 'COURANT')
	INTO Compte(numCompte, solde, typeCompte) VALUES (10, 14000, 'EPARGNE')
	INTO Compte(numCompte, solde, typeCompte) VALUES (11, 2000, 'COURANT')
	INTO Compte(numCompte, solde, typeCompte) VALUES (12, 8500, 'EPARGNE')
	INTO Compte(numCompte, solde, typeCompte) VALUES (13, 18500, 'EPARGNE')
	INTO Compte(numCompte, solde, typeCompte) VALUES (14, 2500, 'COURANT')
	INTO Compte(numCompte, solde, typeCompte) VALUES (15, 85000, 'EPARGNE')
	INTO Compte(numCompte, solde, typeCompte) VALUES (16, 100000, 'EPARGNE')
	INTO Compte(numCompte, solde, typeCompte) VALUES (17, 3000, 'COURANT')
	INTO Compte(numCompte, solde, typeCompte) VALUES (18, 1000000, 'EPARGNE')
	INTO Compte(numCompte, solde, typeCompte) VALUES (19, 1000000, 'COURANT')
	INTO Compte(numCompte, solde, typeCompte) VALUES (20, 1, 'EPARGNE')
SELECT * FROM DUAL;

-- Insertion de données dans la table Appartient
INSERT ALL
	INTO Appartient(unCompte,unClient) VALUES (1,1)
	INTO Appartient(unCompte,unClient) VALUES (1,2)
	INTO Appartient(unCompte,unClient) VALUES (2,3)
	INTO Appartient(unCompte,unClient) VALUES (3,4)
	INTO Appartient(unCompte,unClient) VALUES (4,5)
	INTO Appartient(unCompte,unClient) VALUES (5,6)
	INTO Appartient(unCompte,unClient) VALUES (6,7)
	INTO Appartient(unCompte,unClient) VALUES (7,8)
	INTO Appartient(unCompte,unClient) VALUES (9,10)
	INTO Appartient(unCompte,unClient) VALUES (10,11)
	INTO Appartient(unCompte,unClient) VALUES (11,12)
	INTO Appartient(unCompte,unClient) VALUES (12,13)
	INTO Appartient(unCompte,unClient) VALUES (13,1)
	INTO Appartient(unCompte,unClient) VALUES (14,2)
	INTO Appartient(unCompte,unClient) VALUES (15,3)
	INTO Appartient(unCompte,unClient) VALUES (16,13)
	INTO Appartient(unCompte,unClient) VALUES (17,1)
	INTO Appartient(unCompte,unClient) VALUES (18,2)
	INTO Appartient(unCompte,unClient) VALUES (19,3)
SELECT * FROM DUAL;
/
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