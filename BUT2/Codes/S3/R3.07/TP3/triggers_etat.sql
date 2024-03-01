-- 4)

UPDATE Agent
SET sonAgence = 4
WHERE nomAgent = 'Fontaine';

-- 5)

SET SERVEROUTPUT ON;
CREATE OR REPLACE TRIGGER trig_unSeulDirecteur
BEFORE INSERT OR UPDATE OR DELETE
ON Agent
FOR EACH ROW
DECLARE
    nombre_directeurs INT;
BEGIN
    SELECT COUNT(*)
    INTO nombre_directeurs
    FROM Agent
    WHERE estDirecteur = 1
    AND sonAgence = :NEW.sonAgence;

    IF INSERTING OR UPDATING THEN
        IF :NEW.estDirecteur = 1
        AND nombre_directeurs > 0
        THEN
            RAISE_APPLICATION_ERROR(-20005, 'Une agence ne peut avoir qu''un seul directeur.');
        END IF;
    END IF;

    IF DELETING THEN
        IF :OLD.estDirecteur = 1
        AND nombre_directeurs = 1
        THEN
            RAISE_APPLICATION_ERROR(-20006, 'Suppression non autorisée : une agence doit toujours avoir un directeur.');
        END IF;
    END IF;
END;

CREATE OR REPLACE TRIGGER trig_salaireDirecteur
BEFORE INSERT OR UPDATE
ON Agent
FOR EACH ROW
DECLARE
    salaire_directeur NUMBER;
BEGIN
    SELECT salaire
    INTO salaire_directeur
    FROM Agent
    WHERE estDirecteur = 1
    AND sonAgence = :NEW.sonAgence
    AND numAgent != :NEW.numAgent;

    IF :NEW.estDirecteur = 0
    AND :NEW.salaire >= salaire_directeur
    THEN
        RAISE_APPLICATION_ERROR(-20007, 'Un employé ne peut pas gagner plus que son directeur.');
    END IF;
END;
