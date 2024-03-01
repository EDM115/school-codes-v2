SET SERVEROUTPUT ON;
DECLARE
    CURSOR cursor_plus_de_deux_clients IS
        SELECT c.numCompte, COUNT(a.unClient) AS nb_clients
        FROM Compte c
        	JOIN Appartient a
			ON c.numCompte = a.unCompte
        GROUP BY c.numCompte
        HAVING COUNT(a.unClient) > 2;
    CURSOR cursor_sans_clients IS
        SELECT numCompte
        FROM Compte
        WHERE numCompte NOT IN (
			SELECT unCompte
			FROM Appartient
		);
    compte_num VARCHAR2(20);
    compte_clients NUMBER;
    aucun_compte_condition BOOLEAN := TRUE;
BEGIN
    OPEN cursor_plus_de_deux_clients;
    LOOP
        FETCH cursor_plus_de_deux_clients
		INTO compte_num, compte_clients;
        EXIT WHEN cursor_plus_de_deux_clients%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('Le compte ' || compte_num || ' appartient aux clients : ' || compte_clients);
        aucun_compte_condition := FALSE;
    END LOOP;
    CLOSE cursor_plus_de_deux_clients;
    OPEN cursor_sans_clients;
    LOOP
        FETCH cursor_sans_clients
		INTO compte_num;
        EXIT WHEN cursor_sans_clients%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('Le compte ' || compte_num || ' n''a pas de client');
        aucun_compte_condition := FALSE;
    END LOOP;
    CLOSE cursor_sans_clients;
    IF aucun_compte_condition THEN
        DBMS_OUTPUT.PUT_LINE('Tout est bon !');
    END IF;
END;
