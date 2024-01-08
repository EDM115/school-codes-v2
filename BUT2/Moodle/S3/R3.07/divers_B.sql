DECLARE 
   CURSOR cur_client IS SELECT numClient FROM Client;
   CURSOR cur_compte(p_client Client.numClient%TYPE) IS SELECT solde, typeCompte FROM Appartient, Compte WHERE numCompte = unCompte AND unClient = p_client;
   v_s1 Compte.solde%TYPE := 0;
   v_s2 Compte.solde%TYPE := 0;
   v_prob BOOLEAN := False;
BEGIN
   FOR v_cl IN cur_client LOOP
      FOR v_cp IN cur_compte(v_cl.numClient) LOOP
         IF UPPER(v_cp.typeCompte) = 'EPARGNE' THEN
            v_s1 := v_s1 + v_cp.solde;
         ELSE
            v_s2 := v_s2 + v_cp.solde;
         END IF;
      END LOOP;

      IF v_s1 > v_s2 THEN
         DBMS_OUTPUT.PUT_LINE('Le client ' || v_cl.numClient || ' a plus d''argent dans les comptes épargne que dans les comptes courants');
         v_prob := True;
      END IF;

      -- Remettre à zéro les soldes pour le prochain client
      v_s1 := 0;
      v_s2 := 0;
   END LOOP;

   IF NOT v_prob THEN
      DBMS_OUTPUT.PUT_LINE('Tout est bon!');
   END IF;
END;

