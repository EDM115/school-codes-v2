SET SERVEROUTPUT ON
ACCEPT client_id PROMPT 'Veuillez saisir le numéro du client : '

DECLARE
    v_client_id NUMBER;
    v_nom_client VARCHAR2(50);
    v_solde_total NUMBER := 0;
BEGIN
    -- Convertir l'entrée utilisateur en nombre
    v_client_id := &client_id;

    -- Récupérer le nom du client
    SELECT nomClient || ' ' || prenomClient INTO v_nom_client
    FROM Client
    WHERE numClient = v_client_id;

    -- Calculer le solde total des comptes du client
    SELECT NVL(SUM(solde), 0) INTO v_solde_total
    FROM Appartient A JOIN Compte C on A.unCompte= C.numCompte
    WHERE A.unClient = v_client_id;

    -- Afficher le nom complet du client et le solde total des comptes
    DBMS_OUTPUT.PUT_LINE('Nom du client : ' || v_nom_client);
    DBMS_OUTPUT.PUT_LINE('Solde total des comptes : ' || v_solde_total);
END;
