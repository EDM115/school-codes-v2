--------------------------------------------------------------------------------------
-- Une agence a forcément un et un seul directeur (dès qu'elle a au moins un agent) --
--------------------------------------------------------------------------------------

CREATE OR REPLACE TRIGGER trig_nbDirSansCur
AFTER INSERT OR UPDATE OR DELETE ON Agent
DECLARE 
	v_nbPbs NUMBER;
BEGIN

	SELECT COUNT(*) INTO v_nbPbs
	FROM
		(
		SELECT sonAgence
		FROM Agent
		MINUS
		SELECT sonAgence
		FROM Agent
		WHERE estDirecteur = 1
		GROUP BY sonAgence
		HAVING COUNT(*) = 1
		);	
		
	IF (v_nbPbs > 0) THEN 
		RAISE_APPLICATION_ERROR(-20010,'On compte '||v_nbPbs||' problèmes !');
	END IF;
	
END;
/

/*

C'est astucieux d'utiliser SUM ici :

SELECT COUNT(*) INTO v_nbPbs
FROM
	(
	SELECT sonAgence
	FROM Agent
	GROUP BY sonAgence
	HAVING SUM(estDirecteur) != 1
	)
;	

*/


----------------------------------------------------------
-- Un employé ne peut pas gagner plus que son directeur --
----------------------------------------------------------

CREATE OR REPLACE TRIGGER trig_salDirSansCur
AFTER INSERT OR UPDATE ON Agent
FOLLOWS trig_nbDirSansCur -- Précise l'ordre d'éxécution des triggers sur la table Agent
DECLARE 
	v_nbPbs NUMBER := 0;
BEGIN

	SELECT COUNT(*) INTO v_nbPbs
	FROM 
		(
		SELECT Dir.sonAgence
		FROM Agent Dir, Agent Emp
		WHERE Dir.sonAgence = Emp.sonAgence
		AND Dir.estDirecteur = 1
		AND Emp.estDirecteur = 0
		GROUP BY Dir.sonAgence, Dir.salaire
		HAVING Dir.salaire < MAX(Emp.salaire)
		);	
	
	IF (v_nbPbs > 0) THEN 
		RAISE_APPLICATION_ERROR(-20004,'On compte '||v_nbPbs||' problèmes !');
	END IF;
	
END;
/