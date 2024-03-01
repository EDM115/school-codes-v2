-- 5)

CREATE OR REPLACE TRIGGER trig_Agence_agDir
AFTER INSERT OR UPDATE OF sonDirecteur
ON Agence
FOR EACH ROW
BEGIN
    UPDATE Agent
    SET sonAgence = :NEW.numAgence
    WHERE numAgent = :NEW.sonDirecteur;
END;

CREATE OR REPLACE TRIGGER trig_Agent_agDir
BEFORE UPDATE OF sonAgence
ON Agent
FOR EACH ROW
DECLARE
    v_numDirecteur INT;
BEGIN
    SELECT sonDirecteur
	INTO v_numDirecteur
	FROM Agence
	WHERE numAgence = :NEW.sonAgence;

    IF :NEW.numAgent = v_numDirecteur THEN
        RAISE_APPLICATION_ERROR(-20008, 'Un directeur ne peut pas changer d''agence.');
    END IF;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        NULL;
    WHEN TOO_MANY_ROWS THEN
        RAISE_APPLICATION_ERROR(-20009, 'Incohérence des données : plusieurs directeurs trouvés pour la même agence.');
END;

-- 6)

CREATE OR REPLACE TRIGGER trig_Agence_salDir
AFTER UPDATE OF sonDirecteur
ON Agence
FOR EACH ROW
DECLARE
    salaire_directeur NUMBER;
    max_salaire_agent NUMBER;
BEGIN
    SELECT salaire
	INTO salaire_directeur
	FROM Agent
	WHERE numAgent = :NEW.sonDirecteur;

    SELECT MAX(salaire)
	INTO max_salaire_agent
	FROM Agent
	WHERE sonAgence = :NEW.numAgence
	AND numAgent != :NEW.sonDirecteur;

    IF salaire_directeur <= max_salaire_agent THEN
        RAISE_APPLICATION_ERROR(-20009, 'Le salaire du directeur doit être supérieur à celui des agents.');
    END IF;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        NULL;
END;

CREATE OR REPLACE TRIGGER trig_Agent_salDir
AFTER INSERT OR UPDATE OF salaire
ON Agent
FOR EACH ROW
DECLARE
    salaire_directeur NUMBER;
BEGIN
    SELECT salaire
	INTO salaire_directeur
	FROM Agent
	WHERE numAgent = (
		SELECT sonDirecteur
		FROM Agence
		WHERE numAgence = :NEW.sonAgence
	);
    IF :NEW.salaire > salaire_directeur THEN
        RAISE_APPLICATION_ERROR(-20010, 'Un agent ne peut pas gagner plus que le directeur de son agence.');
    END IF;
END;

-- 8)

SET SERVEROUTPUT ON;
DECLARE
    v_count INTEGER;
BEGIN
    SELECT COUNT(*)
    INTO v_count
    FROM Client c
        JOIN Agent a
        ON c.sonAgent = a.numAgent
    WHERE c.nomClient = a.nomAgent;

    FOR rec IN (
        SELECT c.nomClient, c.prenomClient, a.nomAgent, a.prenomAgent
        FROM Client c
            JOIN Agent a
            ON c.sonAgent = a.numAgent
        WHERE c.nomClient = a.nomAgent
    )
    LOOP
        DBMS_OUTPUT.PUT_LINE('Client ' || rec.prenomClient || ' ' || rec.nomClient || ' partage le même nom avec l''agent ' || rec.prenomAgent || ' ' || rec.nomAgent);
        v_count := v_count + 1;
    END LOOP;
    IF v_count = 0 THEN
        DBMS_OUTPUT.PUT_LINE('Tout est bon !');
    END IF;
END;

SET SERVEROUTPUT ON;
BEGIN
    FOR rec IN (
        SELECT o.numOperation, o.leClient, o.leCompte
        FROM Operation o
            JOIN Appartient a
            ON o.leCompte = a.unCompte
        WHERE o.typeOperation = 'RETRAIT'
        AND o.leClient != a.unClient
    )
    LOOP
        DBMS_OUTPUT.PUT_LINE('Le client ' || rec.leClient || ' a effectué un retrait sur un compte qui ne lui appartient pas (Opération ' || rec.numOperation || ').');
    END LOOP;
END;

SET SERVEROUTPUT ON;
DECLARE
    agence_id NUMBER;
    directeur_id NUMBER;
BEGIN
    FOR rec IN (SELECT numAgence, sonDirecteur FROM Agence)
    LOOP
        SELECT numAgent INTO directeur_id FROM Agent WHERE numAgent = rec.sonDirecteur AND sonAgence = rec.numAgence;
        IF directeur_id IS NULL THEN
            DBMS_OUTPUT.PUT_LINE('L''agence ' || rec.numAgence || ' n''a pas de directeur ou son directeur ne travaille pas dans cette agence!');
        END IF;
    END LOOP;
END;
