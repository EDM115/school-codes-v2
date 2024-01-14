-- 8)
-- a)

SET SERVEROUTPUT ON;
DECLARE
    CURSOR cur_retraits IS
        SELECT numOperation, dateOperation
        FROM (
            SELECT numOperation, dateOperation
            FROM Operation
            WHERE typeOperation = 'RETRAIT'
            ORDER BY dateOperation DESC
        )
        WHERE ROWNUM <= 5;
BEGIN
    FOR rec IN cur_retraits LOOP
        DBMS_OUTPUT.PUT_LINE('L''opération ' || rec.numOperation || ' a été effectuée le ' || TO_CHAR(rec.dateOperation, 'DD-MON-YY') || '.');
    END LOOP;
END;


-- b)

SET SERVEROUTPUT ON;
DECLARE
    CURSOR cur_agences IS
        SELECT a.numAgence, COUNT(
            DISTINCT CASE
                WHEN co.typeCompte <> 'EPARGNE'
                OR co.typeCompte IS NULL
                THEN c.numClient
            END
        ) as countClients
        FROM Agence a
            LEFT JOIN Agent ag
                ON a.numAgence = ag.sonAgence
            LEFT JOIN Client c
                ON ag.numAgent = c.sonAgent
            LEFT JOIN Appartient ap
                ON c.numClient = ap.unClient
            LEFT JOIN Compte co
                ON ap.unCompte = co.numCompte
                AND co.typeCompte = 'EPARGNE'
        GROUP BY a.numAgence;
BEGIN
    FOR rec IN cur_agences LOOP
        DBMS_OUTPUT.PUT_LINE('L''agence ' || rec.numAgence || ' compte ' || rec.countClients || ' client(s) sans compte épargne.');
    END LOOP;
END;
