--------------------------------------------------------------------------------------
-- Une agence a forcément un et un seul directeur (dès qu'elle a au moins un agent) --
--------------------------------------------------------------------------------------

CREATE OR REPLACE TRIGGER trig_nbDirAvecCur
AFTER INSERT OR UPDATE OR DELETE ON Agent
DECLARE 
	CURSOR cur_agencesAvecPb IS
		SELECT sonAgence
		FROM Agent
		MINUS
		SELECT sonAgence
		FROM Agent
		WHERE estDirecteur = 1
		GROUP BY sonAgence
		HAVING COUNT(*) = 1;
	v_nbPbs NUMBER := 0;
BEGIN

	FOR v_agencesAvecPb IN cur_agencesAvecPb -- La variable v_agencesAvecPb est automatiquement déclarée avec le bon "type ligne"
		LOOP 
			DBMS_OUTPUT.PUT_LINE('Problème dans l''agence no '||v_agencesAvecPb.sonAgence||' !');
			v_nbPbs := v_nbPbs + 1;
		END LOOP;
	
	IF (v_nbPbs > 0) THEN 
		RAISE_APPLICATION_ERROR(-20004,'La table Agent pose problème');
	END IF;
	
END;
/

-- On supprime un des deux triggers pour le test

DROP TRIGGER trig_nbDirSansCur;
--DROP TRIGGER trig_nbDirAvecCur;

/*

ProblÃ¨me dans l'agence no 1 !
ProblÃ¨me dans l'agence no 2 !
...
ORA-20004: La table Agent pose problÃ¨me
ORA-06512: at "RIDARD.TRIG_NBDIRAVECCUR", line 21
ORA-04088: error during execution of trigger 'RIDARD.TRIG_NBDIRAVECCUR'

*/


----------------------------------------------------------
-- Un employé ne peut pas gagner plus que son directeur --
----------------------------------------------------------

CREATE OR REPLACE TRIGGER trig_salDirAvecCur
AFTER INSERT OR UPDATE ON Agent
FOLLOWS trig_nbDirAvecCur -- Précise l'ordre d'éxécution des triggers sur la table Agent
--FOLLOWS trig_nbDirSansCur -- Précise l'ordre d'éxécution des triggers sur la table Agent
DECLARE 
	CURSOR cur_agencesAvecPb IS
		SELECT Dir.sonAgence
		FROM Agent Dir, Agent Emp
		WHERE Dir.sonAgence = Emp.sonAgence
		AND Dir.estDirecteur = 1
		AND Emp.estDirecteur = 0
		GROUP BY Dir.sonAgence, Dir.salaire
		HAVING Dir.salaire < MAX(Emp.salaire);
	v_nbPbs NUMBER := 0;
BEGIN

	FOR v_agencesAvecPb IN cur_agencesAvecPb
		LOOP 
			DBMS_OUTPUT.PUT_LINE('Problème dans l''agence no '||v_agencesAvecPb.sonAgence||' !');
			v_nbPbs := v_nbPbs + 1;
		END LOOP;
	
	IF (v_nbPbs > 0) THEN 
		RAISE_APPLICATION_ERROR(-20004,'La table Agent pose problème');
	END IF;
	
END;
/

/*

ProblÃ¨me dans l'agence no 3 !


Erreur commençant à la ligne: 125 de la commande -
INSERT ALL
	INTO Agent VALUES(1,'nomAgent1','prenomAgent1',2000,1,1)
	INTO Agent VALUES(2,'nomAgent2','prenomAgent2',2200,1,2)
	INTO Agent VALUES(3,'nomAgent3','prenomAgent3',2100,0,1) -- Ne sera pas insÃ©rÃ© (employÃ© mieux payÃ© que son directeur)
	INTO Agent VALUES(4,'nomAgent4','prenomAgent4',1900,0,1)
	INTO Agent VALUES(5,'nomAgent5','prenomAgent5',2300,0,2) -- Ne sera pas insÃ©rÃ© (employÃ© mieux payÃ© que son directeur)
	INTO Agent VALUES(6,'nomAgent6','prenomAgent6',2100,0,2)
	INTO Agent VALUES(7,'nomAgent7','prenomAgent7',2000,0,3) -- Ne sera pas insÃ©rÃ© (pas de directeur)
SELECT * FROM DUAL
Rapport d'erreur -
ORA-20004: La table Agent pose problÃ¨me
ORA-06512: at "RIDARD.TRIG_NBDIRAVECCUR", line 21
ORA-04088: error during execution of trigger 'RIDARD.TRIG_NBDIRAVECCUR'

*/

/*

ProblÃ¨me dans l'agence no 1 !
ProblÃ¨me dans l'agence no 2 !


Erreur commençant à la ligne: 137 de la commande -
INSERT ALL
	INTO Agent VALUES(1,'nomAgent1','prenomAgent1',2000,1,1)
	INTO Agent VALUES(2,'nomAgent2','prenomAgent2',2200,1,2)
	INTO Agent VALUES(3,'nomAgent3','prenomAgent3',2100,0,1) -- Ne sera pas insÃ©rÃ© (employÃ© mieux payÃ© que son directeur)
	INTO Agent VALUES(4,'nomAgent4','prenomAgent4',1900,0,1)
	INTO Agent VALUES(5,'nomAgent5','prenomAgent5',2300,0,2) -- Ne sera pas insÃ©rÃ© (employÃ© mieux payÃ© que son directeur)
	INTO Agent VALUES(6,'nomAgent6','prenomAgent6',2100,0,2)
	INTO Agent VALUES(7,'nomAgent7','prenomAgent7',2000,1,3)
SELECT * FROM DUAL
Rapport d'erreur -
ORA-20004: La table Agent pose problÃ¨me
ORA-06512: at "RIDARD.TRIG_SALDIRAVECCUR", line 20
ORA-04088: error during execution of trigger 'RIDARD.TRIG_SALDIRAVECCUR'

*/


------------------------------------------------------------------
-- Lister les 5 derniers retraits du plus récent au plus ancien --
------------------------------------------------------------------

DECLARE
	CURSOR cur_cinqDerniersRetraits IS
	SELECT *
	FROM
		(
		SELECT numOperation, dateOperation
		FROM Operation
		WHERE typeOperation = 'RETRAIT'
		ORDER BY dateOperation DESC
		)
	WHERE ROWNUM <= 5
	ORDER BY dateOperation DESC;

BEGIN	
	FOR v_cinqDerniersRetraits IN cur_cinqDerniersRetraits
		LOOP 
			DBMS_OUTPUT.PUT_LINE('L''opération '||v_cinqDerniersRetraits.numOperation||' a été effectuée le '||v_cinqDerniersRetraits.dateOperation||'.');
		END LOOP;
END;
/

/*

L'opÃ©ration 12 a Ã©tÃ© effectuÃ©e le 02/12/22.
L'opÃ©ration 5 a Ã©tÃ© effectuÃ©e le 01/12/22.
L'opÃ©ration 6 a Ã©tÃ© effectuÃ©e le 01/12/22.
L'opÃ©ration 1 a Ã©tÃ© effectuÃ©e le 30/11/22.
L'opÃ©ration 8 a Ã©tÃ© effectuÃ©e le 30/11/22.

*/


-------------------------------------------------------------------------------------------------
-- Pour chaque agence, indiquer le nombre de clients ne possédant pas encore de compte épargne --
-------------------------------------------------------------------------------------------------

DECLARE
	CURSOR cur_nbClSansEpargne IS
	SELECT sonAgence, COUNT(*) nbCl
	FROM Agent, 
		(
		SELECT numClient, sonAgent
		FROM Client
		MINUS
		SELECT unClient, sonAgent
		FROM Appartient, Compte, Client
		WHERE unCompte = numCompte
		AND unClient = numClient
		AND typeCompte = 'EPARGNE'
		)
	WHERE sonAgent = numAgent
	GROUP BY sonAgence
	;

BEGIN	
	FOR v_nbClSansEpargne IN cur_nbClSansEpargne
		LOOP 
			DBMS_OUTPUT.PUT_LINE('L''agence '||v_nbClSansEpargne.sonAgence||' compte '||v_nbClSansEpargne.nbCl||' client(s) sans compte épargne.');
		END LOOP;
END;
/

/*

L'agence 1 compte 1 client(s) sans compte Ã©pargne.
L'agence 2 compte 2 client(s) sans compte Ã©pargne.
L'agence 4 compte 2 client(s) sans compte Ã©pargne.
L'agence 3 compte 2 client(s) sans compte Ã©pargne.

*/