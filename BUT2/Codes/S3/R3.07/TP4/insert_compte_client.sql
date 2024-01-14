-- 12)

CREATE OR REPLACE PROCEDURE insert_compte_client(p_numCompte INT, p_typeCompte VARCHAR, p_solde DECIMAL, p_numClient INT) AS
BEGIN
    INSERT INTO Compte(numCompte, typeCompte, solde)
    VALUES (p_numCompte, p_typeCompte, p_solde);

    INSERT INTO Appartient(unClient, unCompte)
    VALUES (p_numClient, p_numCompte);
END;
/
