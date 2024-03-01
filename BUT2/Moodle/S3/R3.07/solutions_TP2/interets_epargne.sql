SET SERVEROUTPUT ON
DECLARE    
    CURSOR cur1 IS SELECT unClient, SUM(solde) as solde_global FROM Appartient, Compte WHERE numCompte = unCompte AND typeCompte = 'EPARGNE' GROUP BY unClient;
    CURSOR cur2(p_numClient Client.numClient%Type) IS SELECT solde FROM Compte, Appartient, Client WHERE unClient=numClient AND unClient= p_numClient AND numCompte = unCompte and typeCompte = 'EPARGNE' FOR UPDATE OF solde;
    v_interet Compte.solde%TYPE;
BEGIN
    	FOR rec1 IN cur1 LOOP
    		FOR rec2 IN cur2(rec1.unClient) LOOP
    			CASE
    				WHEN  rec1.solde_global>=100000 THEN v_interet := 0.05;
					WHEN rec1.solde_global >= 10000 THEN v_interet := 0.03;
					ELSE v_interet := 0.01;
				END CASE;
			UPDATE Compte set solde = solde + solde*v_interet WHERE CURRENT OF cur2; 
			END LOOP;
		END LOOP;
END;
