-- 1)

CREATE OR REPLACE TRIGGER trig_Insert_Compte_Client
INSTEAD OF INSERT ON Compte_Client
FOR EACH ROW
BEGIN
    INSERT INTO Compte (numCompte, typeCompte, solde)
    VALUES (:NEW.numCompte, :NEW.typeCompte, :NEW.solde);

    IF :NEW.unClient IS NOT NULL THEN
        INSERT INTO Appartient (unClient, unCompte)
        VALUES (:NEW.unClient, :NEW.numCompte);
    END IF;
END;

-- 2)

INSERT INTO Compte_Client (numCompte, typeCompte, solde, unClient)
VALUES (21, 'EPARGNE', 50, 1);
