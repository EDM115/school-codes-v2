SET SERVEROUTPUT ON;
DECLARE compte_sans_client NUMBER;
BEGIN
    SELECT COUNT(*)
    INTO compte_sans_client
    FROM Compte
    WHERE numCompte NOT IN (
        SELECT unCompte
        FROM Appartient
    );
    IF compte_sans_client > 0 THEN
        DBMS_OUTPUT.PUT_LINE('Attention, ' || compte_sans_client || ' comptes n''ont pas de client!');
    END IF;
END;

-- b)

SET SERVEROUTPUT ON;
DECLARE
    CURSOR cur_comptes_sans_client IS
        SELECT numCompte
        FROM Compte
        WHERE numCompte NOT IN (
            SELECT unCompte
            FROM Appartient
        );
    compte_sans_client NUMBER;
BEGIN
    OPEN cur_comptes_sans_client;
    LOOP
        FETCH cur_comptes_sans_client
        INTO compte_sans_client;
        EXIT WHEN cur_comptes_sans_client%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('Le compte ' || compte_sans_client || ' n''a pas de client!');
    END LOOP;
    DBMS_OUTPUT.PUT_LINE('Attention, ' || cur_comptes_sans_client%ROWCOUNT || ' comptes n''ont pas de client!');
    CLOSE cur_comptes_sans_client;
END;

-- 9)
-- a)

SET SERVEROUTPUT ON;
DECLARE
    agence_id NUMBER;
    nombre_directeurs NUMBER;
    CURSOR cur_agences IS
        SELECT a.numAgence, COUNT(ag.numAgent) as directeurs
        FROM Agence a
            LEFT JOIN Agent ag
                ON a.numAgence = ag.sonAgence
                AND ag.estDirecteur = 1
        GROUP BY a.numAgence;
BEGIN
    FOR rec IN cur_agences LOOP
        IF rec.directeurs != 1 THEN
            IF rec.directeurs = 0 THEN
                DBMS_OUTPUT.PUT_LINE('L''agence ' || rec.numAgence || ' n''a pas de directeur!');
            ELSE
                DBMS_OUTPUT.PUT_LINE('L''agence ' || rec.numAgence || ' a ' || rec.directeurs || ' directeurs!');
            END IF;
        END IF;
    END LOOP;
END;

-- b)

SET SERVEROUTPUT ON;
DECLARE
    CURSOR cur_directeurs IS
        SELECT ag.sonAgence, ag.salaire as salaire_directeur
        FROM Agent ag
        WHERE ag.estDirecteur = 1;
    probleme_detecte BOOLEAN := FALSE;
    directeur_salaire DECIMAL(6,2);
    max_salaire_agent DECIMAL(6,2);
BEGIN
    FOR rec_directeur IN cur_directeurs LOOP
        SELECT MAX(salaire) INTO max_salaire_agent
        FROM Agent
        WHERE sonAgence = rec_directeur.sonAgence
        AND estDirecteur = 0;
        IF max_salaire_agent IS NOT NULL
        AND max_salaire_agent > rec_directeur.salaire_directeur THEN
            DBMS_OUTPUT.PUT_LINE('Le directeur de l''agence ' || rec_directeur.sonAgence || ' n''est pas le mieux payé!');
            probleme_detecte := TRUE;
        END IF;
    END LOOP;
    IF NOT probleme_detecte THEN
        DBMS_OUTPUT.PUT_LINE('Tout est bon!');
    END IF;
END;
