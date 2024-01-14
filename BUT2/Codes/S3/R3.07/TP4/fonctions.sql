-- 14)

CREATE OR REPLACE FUNCTION fct_estinsuffisant(p_numCompte INT, p_montant DECIMAL)
RETURN INT IS
    v_solde DECIMAL;
BEGIN
    SELECT solde INTO v_solde FROM Compte WHERE numCompte = p_numCompte;
    IF v_solde >= p_montant THEN
        RETURN 1;
    ELSE
        RETURN 0;
    END IF;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 0;
END;
/

-- 15)

CREATE OR REPLACE PROCEDURE proc_ajoutOperation(p_typeOperation VARCHAR, p_montant DECIMAL, p_client INT, p_compte INT) IS
BEGIN
    INSERT INTO Operation(typeOperation, montant, leClient, leCompte)
    VALUES (p_typeOperation, p_montant, p_client, p_compte);

    IF p_typeOperation = 'DEPOT' THEN
        UPDATE Compte SET solde = solde + p_montant WHERE numCompte = p_compte;
    ELSIF p_typeOperation = 'RETRAIT' THEN
        UPDATE Compte SET solde = solde - p_montant WHERE numCompte = p_compte;
    END IF;
END;
/

-- 16)

CREATE OR REPLACE PROCEDURE proc_virement(p_compteSource INT, p_compteDest INT, p_montant DECIMAL, p_client INT) IS
    v_agentNom VARCHAR2(100);
BEGIN
    IF fct_estinsuffisant(p_compteSource, p_montant) = 0 THEN
        RAISE_APPLICATION_ERROR(-20014, 'Le virement est impossible car le solde du compte source est insuffisant !');
    ELSIF p_montant > 1000 THEN
        SELECT nomAgent INTO v_agentNom FROM Agent JOIN Client ON Agent.numAgent = Client.sonAgent WHERE Client.numClient = p_client;
        RAISE_APPLICATION_ERROR(-20015, 'Le virement est impossible car dépassant 1000 euros ! Veuillez contacter l''agent ' || v_agentNom || ':)');
    ELSE
        proc_ajoutOperation('RETRAIT', p_montant, p_client, p_compteSource);
        proc_ajoutOperation('DEPOT', p_montant, p_client, p_compteDest);
        DBMS_OUTPUT.PUT_LINE('Virement effectué.');
    END IF;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20016, 'Erreur lors de la recherche de l''agent.');
END;
/
