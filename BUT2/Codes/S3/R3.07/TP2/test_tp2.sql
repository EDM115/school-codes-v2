-- 1)

DELETE FROM Compte
WHERE numCompte = 19;

/*
Erreur commençant à la ligne: 1 de la commande -
DELETE FROM Compte
WHERE numCompte = 19
Rapport d'erreur -
ORA-02292: integrity constraint (EDM115.SYS_C007522) violated - child record found
*/

-- 3)

DELETE FROM Compte
WHERE numCompte = 19;
ROLLBACK;

-- 7)
-- a)

INSERT INTO Compte_Client (numCompte, typeCompte, solde, unClient)
VALUES (21, 'EPARGNE', 50, 1);

/*
Erreur à la ligne de commande: 1 Colonne: 28
Rapport d'erreur -
Erreur SQL : ORA-01779: cannot modify a column which maps to a non key-preserved table
01779. 00000 -  "cannot modify a column which maps to a non key-preserved table"
*Cause:    An attempt was made to insert or update columns of a join view which
           map to a non-key-preserved table.
*Action:   Modify the underlying base tables directly.
*/

-- b)

DELETE FROM Compte_Client
WHERE unClient IS NULL;

/*
Erreur à la ligne de commande: 1 Colonne: 13
Rapport d'erreur -
Erreur SQL : ORA-01752: cannot delete from view without exactly one key-preserved table
01752. 00000 -  "cannot delete from view without exactly one key-preserved table"
*Cause:    The deleted table had
           - no key-preserved tables,
           - more than one key-preserved table, or
           - the key-preserved table was an unmerged view.
*Action:   Redefine the view or delete it from the underlying base tables.
*/

-- c)

UPDATE Compte_Client SET unClient = 1
WHERE unClient IS NULL;

/*
Erreur à la ligne de commande: 1 Colonne: 26
Rapport d'erreur -
Erreur SQL : ORA-01779: cannot modify a column which maps to a non key-preserved table
01779. 00000 -  "cannot modify a column which maps to a non key-preserved table"
*Cause:    An attempt was made to insert or update columns of a join view which
           map to a non-key-preserved table.
*Action:   Modify the underlying base tables directly.
*/
