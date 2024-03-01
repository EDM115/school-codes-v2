-- 1)

CREATE OR REPLACE VIEW NonDir_Vue AS
SELECT numAgent, estDirecteur, sonAgence
FROM Agent
WHERE estDirecteur = 0;

SET SERVEROUTPUT ON;
CREATE OR REPLACE TRIGGER trig_miseAJourNonDir_Vue
INSTEAD OF UPDATE ON NonDir_Vue
FOR EACH ROW
DECLARE
    ancien_directeur INT;
BEGIN
    IF :NEW.estDirecteur = 1 THEN
        SELECT numAgent INTO ancien_directeur FROM Agent WHERE estDirecteur = 1 AND sonAgence = :NEW.sonAgence;
    ELSE
        ancien_directeur := NULL;
    END IF;

    IF :NEW.sonAgence != :OLD.sonAgence OR :NEW.numAgent != :OLD.numAgent THEN
        UPDATE Agent 
        SET sonAgence = :NEW.sonAgence, numAgent = :NEW.numAgent 
        WHERE numAgent = :OLD.numAgent;
    END IF;

    IF :NEW.estDirecteur = 1 AND ancien_directeur IS NOT NULL THEN
        UPDATE Agent
        SET estDirecteur = DECODE(numAgent, :NEW.numAgent, 1, ancien_directeur, 0, estDirecteur)
        WHERE numAgent IN (:NEW.numAgent, ancien_directeur);
    END IF;
END;
