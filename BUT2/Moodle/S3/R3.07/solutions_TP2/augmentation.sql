SET SERVEROUTPUT ON
DECLARE    
    CURSOR cur IS SELECT salaire, estDirecteur FROM Agent FOR UPDATE OF salaire;
    v_bonus Agent.salaire%TYPE;
BEGIN
    	FOR rec IN cur LOOP
    		CASE
    			WHEN  rec.estDirecteur=1 THEN v_bonus := 0.05*rec.salaire;
				ELSE v_bonus := 0.01*rec.salaire;
			END CASE;
			UPDATE Agent set salaire = salaire + v_bonus WHERE CURRENT OF cur; 
		END LOOP;
END;