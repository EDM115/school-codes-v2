------------------------------
-- Shell / root@localhost --
------------------------------

-- 1 --
-- Connexion en tant que superutilisateur
\connect root@localhost

-- Passage en mode SQL
\sql

-- Vérifier les utilisateurs existants
SELECT user, host FROM mysql.user;

-- Supprimer l'utilisateur EDM115@localhost
DROP USER EDM115@localhost;

-- Supprimer la base de données bd_206
DROP DATABASE bd_206;


-- 2 --
-- Créer la base de données bd_iut
CREATE DATABASE bd_iut;

-- Créer un nouvel utilisateur naert@localhost avec des privilèges DBA complets lors de la création
CREATE USER naert@localhost IDENTIFIED BY 'mdp_naert';
GRANT ALL PRIVILEGES ON *.* TO naert@localhost WITH GRANT OPTION;


-- 3 --
-- Utiliser la base de données bd_iut
USE bd_iut;

-- Charger les scripts de création et remplissage depuis le chemin spécifié
\source "./TP6.sql";
\source "./TP1_Remplissage.sql";


------------------------------------------
-- Workbench / naert@localhost --
------------------------------------------

-- 4 --
-- Créer ou remplacer la vue vue_Etud_Stag_Ent
CREATE OR REPLACE VIEW vue_Etud_Stag_Ent AS
SELECT * FROM Etudiant, Stagiaire, Entreprise
WHERE Etudiant.idEtud = Stagiaire.etudStagiaire
AND Stagiaire.entrepriseStage = Entreprise.idEntreprise
WITH CHECK OPTION;

-- Afficher les résultats de la vue vue_Etud_Stag_Ent
SELECT * FROM vue_Etud_Stag_Ent;

-- Créer ou remplacer la vue vue_Etud_App_Ent
CREATE OR REPLACE VIEW vue_Etud_App_Ent AS
SELECT * FROM Etudiant, Apprenti, Entreprise
WHERE Etudiant.idEtud = Apprenti.etudApp
AND Apprenti.entrepriseApp = Entreprise.idEntreprise
WITH CHECK OPTION;

-- Afficher les résultats de la vue vue_Etud_App_Ent
SELECT * FROM vue_Etud_App_Ent;


-- 5 --
-- Créer les utilisateurs kamp, ridard, baudont, fleurquin avec leurs mots de passe respectifs
CREATE USER kamp@localhost IDENTIFIED BY 'mdp_kamp';
CREATE USER ridard@localhost IDENTIFIED BY 'mdp_ridard';
CREATE USER baudont@localhost IDENTIFIED BY 'mdp_baudont';
CREATE USER fleurquin@localhost IDENTIFIED BY 'mdp_fleurquin';


-- 6 --
-- Créer les rôles bd_iut_lecture et bd_iut_ecriture
CREATE ROLE bd_iut_lecture;
GRANT SELECT ON bd_iut.* TO bd_iut_lecture;

CREATE ROLE bd_iut_ecriture;
GRANT INSERT, UPDATE, DELETE ON bd_iut.* TO bd_iut_ecriture;


-- 7 --
-- Accorder les rôles et les privilèges aux utilisateurs
GRANT bd_iut_lecture TO kamp@localhost;
GRANT bd_iut_ecriture TO kamp@localhost;

GRANT bd_iut_lecture TO ridard@localhost;
GRANT INSERT, UPDATE, DELETE ON bd_iut.Etudiant TO ridard@localhost;
GRANT INSERT, UPDATE, DELETE ON bd_iut.GroupeInfo1 TO ridard@localhost;

GRANT bd_iut_lecture TO baudont@localhost;
GRANT INSERT, UPDATE, DELETE ON bd_iut.vue_Etud_App_Ent TO baudont@localhost;

GRANT bd_iut_lecture TO fleurquin@localhost;
GRANT UPDATE (poursuiteEtudes) ON bd_iut.Etudiant TO fleurquin@localhost;


-- 8 --
-- Afficher les privilèges des utilisateurs
SHOW GRANTS FOR kamp@localhost;
SHOW GRANTS FOR ridard@localhost;
SHOW GRANTS FOR baudont@localhost;
SHOW GRANTS FOR fleurquin@localhost;

-- Afficher les données des tables de la base de données bd_iut

------------------------------------------
-- Workbench / kamp@localhost --
------------------------------------------

SELECT *
FROM bd_iut.Etudiant;

SELECT *
FROM bd_iut.GroupeInfo1;

SELECT *
FROM bd_iut.vue_Etud_App_Ent;

------------------------------------------
-- Workbench / ridard@localhost --
------------------------------------------

SELECT *
FROM bd_iut.Etudiant;

SELECT *
FROM bd_iut.GroupeInfo1;

SELECT *
FROM bd_iut.vue_Etud_App_Ent;

------------------------------------------
-- Workbench / baudont@localhost --
------------------------------------------

SELECT *
FROM bd_iut.Etudiant;

SELECT *
FROM bd_iut.GroupeInfo1;

SELECT *
FROM bd_iut.vue_Etud_App_Ent;

------------------------------------------
-- Workbench / fleurquin@localhost --
------------------------------------------

SELECT *
FROM bd_iut.Etudiant;

SELECT *
FROM bd_iut.GroupeInfo1;

SELECT *
FROM bd_iut.vue_Etud_App_Ent;
