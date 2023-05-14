------------------------------------------
-- Shell / naert@localhost --
------------------------------------------

-- 9 --

-- Vérifier la création de la base de données bd_iut
SHOW DATABASES LIKE 'bd_iut';

-- Vérifier la création de l'utilisateur naert@localhost avec les privilèges DBA complets
SELECT user, host
FROM mysql.user
WHERE user = 'naert'
AND host = 'localhost';

-- Vérifier la présence des vues vue_Etud_Stag_Ent et vue_Etud_App_Ent
SHOW FULL TABLES
IN bd_iut
WHERE TABLE_TYPE
LIKE 'VIEW';

SELECT *
FROM vue_Etud_Stag_Ent;

SELECT *
FROM vue_Etud_App_Ent;

-- Vérifier les utilisateurs créés
SELECT user, host
FROM mysql.user
WHERE user IN (
	'kamp',
	'ridard',
	'baudont',
	'fleurquin'
)
AND host = 'localhost';

-- Vérifier les rôles créés
SELECT *
FROM mysql.role_mapping
WHERE ROLE_NAME
IN (
	'bd_iut_lecture',
	'bd_iut_ecriture'
);

-- Vérifier les privilèges accordés aux utilisateurs
SHOW GRANTS FOR kamp@localhost;
SHOW GRANTS FOR ridard@localhost;
SHOW GRANTS FOR baudont@localhost;
SHOW GRANTS FOR fleurquin@localhost;

-- Vérifier l'accès aux données pour chaque utilisateur en fonction des privilèges accordés

\connect kamp@localhost
\sql
USE bd_iut;
SELECT * FROM Etudiant;
SELECT * FROM GroupeInfo1;
SELECT * FROM vue_Etud_App_Ent;

\connect ridard@localhost
\sql
USE bd_iut;
SELECT * FROM Etudiant;
SELECT * FROM GroupeInfo1;
SELECT * FROM vue_Etud_App_Ent;

\connect baudont@localhost
\sql
USE bd_iut;
SELECT * FROM Etudiant;
SELECT * FROM GroupeInfo1;
SELECT * FROM vue_Etud_App_Ent;

\connect fleurquin@localhost
\sql
USE bd_iut;
SELECT * FROM Etudiant;
SELECT * FROM GroupeInfo1;
SELECT * FROM vue_Etud_App_Ent;


-- 10 --

-- On peut utiliser des fichiers de configuration pour gérer les connections de manière plus flexible
--  Exemple :
-- (fichier config.cnf)

-- Puis on se connecte avec
mysql --defaults-file=config.cnf -h localhost --defaults-group-suffix={username}

-- par exemple mysql --defaults-file=config.cnf -h localhost --defaults-group-suffix=kamp
