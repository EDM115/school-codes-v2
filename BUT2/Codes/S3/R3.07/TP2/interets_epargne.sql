-- 11)

SET SERVEROUTPUT ON;
DECLARE
    CURSOR cur_comptes_epargne IS
        SELECT numCompte, solde
        FROM Compte
        WHERE typeCompte = 'EPARGNE';
    CURSOR cur_update_solde(p_numCompte IN Compte.numCompte%TYPE, p_nouveauSolde IN Compte.solde%TYPE) IS
        SELECT numCompte
        FROM Compte
        WHERE numCompte = p_numCompte;
    rec_compte_epargne cur_comptes_epargne%ROWTYPE;
    nouveauSolde REAL;
BEGIN
    OPEN cur_comptes_epargne;
    LOOP
        FETCH cur_comptes_epargne
        INTO rec_compte_epargne;
        EXIT WHEN cur_comptes_epargne%NOTFOUND;
        IF rec_compte_epargne.solde >= 100000 THEN
            nouveauSolde := rec_compte_epargne.solde * 1.05;
        ELSIF rec_compte_epargne.solde BETWEEN 10000 AND 99999.99 THEN
            nouveauSolde := rec_compte_epargne.solde * 1.03;
        ELSE
            nouveauSolde := rec_compte_epargne.solde * 1.01;
        END IF;
        FOR rec_update IN cur_update_solde(rec_compte_epargne.numCompte, nouveauSolde) LOOP
            UPDATE Compte
            SET solde = nouveauSolde
            WHERE numCompte = rec_update.numCompte;
        END LOOP;
    END LOOP;
    CLOSE cur_comptes_epargne;
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Mise à jour des soldes des comptes d''épargne effectuée.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erreur lors de la mise à jour des soldes : ' || SQLERRM);
        ROLLBACK;
END;
