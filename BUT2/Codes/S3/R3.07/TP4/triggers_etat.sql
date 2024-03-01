-- 9)

CREATE OR REPLACE TRIGGER trig_prevent_delete_appartient
BEFORE DELETE ON Appartient
FOR EACH ROW
DECLARE
    compte_client_count INT;
BEGIN
    SELECT COUNT(*)
    INTO compte_client_count
    FROM Appartient
    WHERE unCompte = :OLD.unCompte;

    IF compte_client_count <= 1 THEN
        RAISE_APPLICATION_ERROR(-20011, 'Un compte doit avoir au moins un client.');
    END IF;
END;

CREATE OR REPLACE TRIGGER trig_prevent_update_appartient
BEFORE UPDATE ON Appartient
FOR EACH ROW
DECLARE
    compte_client_count INT;
BEGIN
    SELECT COUNT(*)
    INTO compte_client_count
    FROM Appartient
    WHERE unCompte = :OLD.unCompte;

    IF compte_client_count = 1 AND :NEW.unCompte != :OLD.unCompte THEN
        RAISE_APPLICATION_ERROR(-20012, 'Modification non autorisée. Un compte doit avoir au moins un client.');
    END IF;
END;

-- 10)

CREATE OR REPLACE TRIGGER trig_prevent_insert_compte
BEFORE INSERT ON Compte
FOR EACH ROW
BEGIN
    RAISE_APPLICATION_ERROR(-20013, 'Insertion directe dans la table Compte interdite.');
END;
