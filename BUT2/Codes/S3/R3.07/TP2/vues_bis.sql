-- 6)

CREATE OR REPLACE VIEW Compte_Client AS
SELECT c.numCompte, c.typeCompte, c.solde, a.unClient
FROM Compte c
    LEFT JOIN Appartient a ON c.numCompte = a.unCompte;
SELECT * FROM Compte_Client;
