/*

Agent (numAgent (1), nomAgent NN, prenomAgent NN, salaire NN, sonAgence = @Agence(numAgence) NN)
Agence (numAgence (1), telAgence, adAgence, sonDirecteur = @Agent(numAgent) NN UQ)
Client (numClient (1), nomClient NN, prenomClient NN, adClient, dateNaissanceClient, sonAgent = @Agent(numAgent) NN)
Compte (numCompte (1), solde, typeCompte)
Operation (numOperation (1), dateOperation, typeOperation, montant, leClient = @Client(numClient) NN, leCompte = @Compte(numCompte) NN)
Appartient (unCompte = @Compte(numCompte)(1), unClient = @Client(numClient)(1))

*/


--------------------------
-- Fonction estDebiteur --
--------------------------

CREATE OR REPLACE FUNCTION fct_estDebiteur
	(
	p_numCompte Compte.numCompte%TYPE
	) 
	RETURN NUMBER 
IS
v_result NUMBER := 0;
v_solde NUMBER; 
BEGIN

	SELECT solde INTO v_solde 
	FROM Compte 
	WHERE numCompte = p_numCompte;
	
	IF (v_solde < 0) THEN 
		v_result := 1;
	END IF;
	
	RETURN v_result; 
	
EXCEPTION

	WHEN NO_DATA_FOUND THEN 
		DBMS_OUTPUT.PUT_LINE ('Attention, le compte '||p_numCompte||' n''existe pas');
		
END;
/


------------------------------
-- Procédure ajoutOperation --
------------------------------

CREATE OR REPLACE PROCEDURE proc_ajoutOperation
	(
	p_typeOperation Operation.typeOperation%TYPE,
	p_montant Operation.montant%TYPE,
	p_leClient Operation.leClient%TYPE,
	p_leCompte Operation.leCompte%TYPE
	)
IS
BEGIN 
 
	INSERT INTO Operation (typeOperation, montant, leClient, leCompte) 
		VALUES (p_typeOperation, p_montant, p_leClient, p_leCompte);

	IF (p_typeOperation = 'RETRAIT') THEN
		UPDATE Compte
			SET solde = (solde - p_montant)
			WHERE numCompte = p_leCompte;
	ELSE
		UPDATE Compte 
			SET solde = (solde + p_montant)
			WHERE numCompte = p_leCompte;
	END IF;
	
END;
/ 


------------------------
-- Procédure virement --
------------------------

CREATE OR REPLACE PROCEDURE proc_virement
	(
	p_numCompteSource Compte.numCompte%TYPE,
	p_numCompteBut Compte.numCompte%TYPE,
	p_montant Operation.montant%TYPE,
	p_numClientSource Client.numClient%TYPE
	)
IS
BEGIN

	IF fct_estDebiteur(p_numCompteSource) = 1 THEN
		RAISE_APPLICATION_ERROR(-20001, 'Le virement est impossible car le compte source est débiteur !');
	ELSE 
		proc_ajoutOperation ('RETRAIT', p_montant , p_numClientSource, p_numCompteSource);
		proc_ajoutOperation ('DEPOT', p_montant , p_numClientSource, p_numCompteBut);
	END IF;
END;
/ 


