SET SERVEROUT ON
ACCEPT client_id PROMPT 'Entrer le numéro client:' ;

DECLARE
    v_client_id CLIENT.numClient%TYPE;
    v_nom_client VARCHAR2(50);
    v_solde_total NUMBER(10,2) := 0;
	v_solde NUMBER(10,2) := 0;
    excep_neg EXCEPTION;
	CURSOR cur_emp(p_numC CLIENT.numClient%Type, p_typeC COMPTE.typeCompte%TYPE default 'EPARGNE') IS SELECT solde FROM APPARTIENT JOIN COMPTE ON UnCompte=numCompte WHERE unClient= p_numC AND typeCompte=p_typeC;
BEGIN
    -- Convertir l'entrée utilisateur en nombre
    v_client_id := TO_NUMBER('&client_id');

	IF v_client_id<0 THEN
        RAISE excep_neg;
	ELSE
        -- Récupérer le nom du client
        SELECT nomClient || ' ' || prenomClient INTO v_nom_client FROM Client WHERE numClient = v_client_id;
         -- Afficher le nom complet du client
    	DBMS_OUTPUT.PUT_LINE('Nom du client : ' || v_nom_client);
        -- Récuperer les types de comptes
        FOR rec_type IN (SELECT DISTINCT typeCompte FROM compte) LOOP
            DBMS_OUTPUT.PUT_LINE('----------------' || rec_type.typeCompte || '----------------');
			FOR rec_compte IN cur_emp(v_client_id, rec_type.typeCompte) LOOP
                v_solde := rec_compte.solde;
                DBMS_OUTPUT.PUT_LINE(v_solde);
                v_solde_total := v_solde + v_solde_total;
			END LOOP;
        END LOOP;
    DBMS_OUTPUT.PUT_LINE('Solde total des comptes : ' || v_solde_total);
    END IF;
	EXCEPTION 
        WHEN excep_neg THEN DBMS_OUTPUT.PUT_LINE('Le numéro client ne doit pas être négatif!');
 		WHEN NO_DATA_FOUND THEN DBMS_OUTPUT.PUT_LINE('Aucun client ne porte ce numéro!');
		WHEN VALUE_ERROR THEN DBMS_OUTPUT.PUT_LINE('Le numéro doit être un nombre!');
		WHEN OTHERS THEN DBMS_OUTPUT.PUT_LINE('Il y a un problème!');
END;
