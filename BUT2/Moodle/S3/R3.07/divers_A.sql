SET SERVEROUTPUT ON
DECLARE
    CURSOR cur_compte IS SELECT numCompte FROM Compte;
    CURSOR cur_client(p_compte Compte.numCompte%Type) IS SELECT unClient FROM Appartient WHERE unCompte=p_compte ;
    v_message VARCHAR2(50);
    v_cpt NUMBER;
    v_prob BOOLEAN := FALSE;
BEGIN
    FOR v_cp IN cur_compte LOOP
        v_cpt:=0;
		v_message:='';
        FOR  v_cl IN cur_client(v_cp.numCompte) LOOP
        	v_message := (v_message || v_cl.unClient || ' ');
			v_cpt:= v_cpt+1;
		END LOOP;
        IF v_cpt=0 THEN
            v_prob := True;
            DBMS_OUTPUT.PUT_LINE('Le compte ' || v_cp.numCompte || ' n''a pas de clients');
		ELSIF  v_cpt>=2 THEN
            DBMS_OUTPUT.PUT_LINE('Le compte ' || v_cp.numCompte || ' appartient aux clients: ' || v_message );
        END IF;
	END LOOP;
	IF NOT v_prob THEN
       DBMS_OUTPUT.PUT_LINE('Tout est bon!');
	END IF;
END;
