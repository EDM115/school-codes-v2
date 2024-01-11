CREATE OR REPLACE VIEW Compte_Client AS ( SELECT Compte.*, unClient as numClient from Compte LEFT JOIN Appartient ON numCompte = unCompte);
SELECT * FROM Compte_Client;
INSERT INTO Compte_Client VALUES (21, 50, 'EPARGNE', 1); -- Pas possible car la vue est basée sur 2 tables
DELETE FROM Compte_Client WHERE numClient IS NULL  -- Pas possible car la vue est basée sur 2 tables ;
UPDATE Compte_Client SET numClient = 1 WHERE numClient IS NULL  -- Pas possible car la vue est basée sur 2 tables ;