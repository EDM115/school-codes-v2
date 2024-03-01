-- 1)

-- a)

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

-- b)

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
