--------------------------------------------------------------------------------------
-- Une agence a forcément un et un seul directeur (dès qu'elle a au moins un agent) --
--------------------------------------------------------------------------------------

CREATE OR REPLACE TRIGGER trig_nbDir
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


----------------------------------------------------------
-- Un employé ne peut pas gagner plus que son directeur --
----------------------------------------------------------

CREATE OR REPLACE TRIGGER trig_salDirAvecCur
AFTER INSERT OR UPDATE ON Agent
FOLLOWS trig_nbDir -- Précise l'ordre d'éxécution des triggers sur la table Agent
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
