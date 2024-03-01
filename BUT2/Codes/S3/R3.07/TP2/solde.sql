-- 12)

SET SERVEROUTPUT ON;
DECLARE
    numClientSaisi NUMBER;
    nomCompletClient VARCHAR2(100);
    soldeTotal NUMBER := 0;
    soldeCompte NUMBER;
    EXCEPTION_NUMERO_NEGATIF EXCEPTION;
    CURSOR cur_solde(p_numClient IN NUMBER, p_typeCompte IN VARCHAR2) IS
        SELECT co.solde
        FROM Compte co
            JOIN Appartient ap
            ON co.numCompte = ap.unCompte
        WHERE ap.unClient = p_numClient
        AND co.typeCompte = p_typeCompte;
BEGIN
    DBMS_OUTPUT.PUT_LINE('Veuillez entrer le numéro du client :');
    numClientSaisi := &numClientSaisi;
    IF numClientSaisi < 0 THEN
        RAISE EXCEPTION_NUMERO_NEGATIF;
    END IF;
    SELECT nomClient || ' ' || prenomClient
    INTO nomCompletClient
    FROM Client
    WHERE numClient = numClientSaisi;
    DBMS_OUTPUT.PUT_LINE('Nom du client : ' || nomCompletClient);
    FOR rec_typeCompte IN (
        SELECT DISTINCT typeCompte
        FROM Compte
    ) LOOP
        soldeCompte := 0;
        DBMS_OUTPUT.PUT_LINE('----------------' || rec_typeCompte.typeCompte || '-------');
        FOR rec_solde IN cur_solde(numClientSaisi, rec_typeCompte.typeCompte) LOOP
            DBMS_OUTPUT.PUT_LINE(rec_solde.solde);
            soldeCompte := soldeCompte + rec_solde.solde;
        END LOOP;
        soldeTotal := soldeTotal + soldeCompte;
    END LOOP;
    DBMS_OUTPUT.PUT_LINE('Solde total des comptes : ' || soldeTotal);
EXCEPTION
    WHEN EXCEPTION_NUMERO_NEGATIF THEN
        DBMS_OUTPUT.PUT_LINE('Le numéro client ne doit pas être négatif!');
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('Aucun client ne porte ce numéro!');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Le numéro doit être un nombre!');
END;
