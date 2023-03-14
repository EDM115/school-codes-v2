INSERT INTO Agence values (1,'0101010101','1 rue des Magnolias');
INSERT INTO Agence values (2,'0202020101','2 rue des Fleurs');
INSERT INTO Agence values (3,'0302020101','18 place de Grenelle');

INSERT INTO Agent values (1,'Bob','Marcel', 1500, 1, 1);
INSERT INTO Agent values (2,'Naert','Lulu', 1483, 1, 1);
INSERT INTO Agent values (3,'Baba','Matou', 2200, 0, 2);
INSERT INTO Agent values (4,'Bobby','Tar', 2100, 0, 2);
INSERT INTO Agent values (5,'Vincent','Vincent', 3200, 1, 3);
INSERT INTO Agent values (6,'Truc','Truc', 3400, 0, 3);

--Client(numClient(1), nomClient, prenomClient, adClient, dateNaissanceClient, sonAgent=@Agent.numAgent NN)
INSERT INTO Client values (1,'Nanou','Bib', '1 rue Truc', TO_DATE('20021130','YYYYMMDD'), 1);
INSERT INTO Client values (2,'Bidule','Mach', '1 rue Chose', TO_DATE('19971230','YYYYMMDD'), 2);
INSERT INTO Client values (3,'Bidule','Mach', '1 rue Chose', TO_DATE('19931126','YYYYMMDD'), 3);
--Compte(numCompte(1), solde, typeCompte)
INSERT INTO Compte values (1, 1500,'COURANT');
INSERT INTO Compte values (2, 30000,'EPARGNE');

--Operation(numOperation(1), dateOperation, typeOperation, montant, leClient=@Client.numClient NN, leCompte=@Compte.numCompte NN)

--Appartient((unCompte=@Compte.numCompte, unClient=@Client.numClient)(1))
DELETE FROM Appartient;
INSERT INTO Appartient values (1,1);
INSERT INTO Appartient values (1,2);