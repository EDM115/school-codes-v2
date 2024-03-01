SET SERVEROUTPUT ON;

DECLARE
    v_numClient INT;
    v_fullName VARCHAR2(100);
    v_totalBalance NUMBER(12, 2) := 0;
BEGIN

    DBMS_OUTPUT.PUT('Entrez le numéro du client : ');
    DBMS_OUTPUT.GET_LINE(v_numClient);

    SELECT nomClient || ' ' || prenomClient
    INTO v_fullName
    FROM Client
    WHERE numClient = v_numClient;

    SELECT NVL(SUM(solde), 0)
    INTO v_totalBalance
    FROM Compte
    WHERE numCompte IN (SELECT unCompte FROM Appartient WHERE unClient = v_numClient);

    DBMS_OUTPUT.PUT_LINE('Client : ' || v_fullName);
    DBMS_OUTPUT.PUT_LINE('Solde total : ' || v_totalBalance);
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('Client avec le numéro ' || v_numClient || ' non trouvé.');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Une erreur s\'est produite : ' || SQLERRM);
END;
/
