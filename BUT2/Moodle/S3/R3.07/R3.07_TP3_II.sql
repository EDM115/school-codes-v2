/*

Agent (numAgent (1), nomAgent NN, prenomAgent NN, salaire NN, sonAgence = @Agence(numAgence) NN)
Agence (numAgence (1), telAgence, adAgence, sonDirecteur = @Agent(numAgent) NN UQ)
Client (numClient (1), nomClient NN, prenomClient NN, adClient, dateNaissanceClient, sonAgent = @Agent(numAgent) NN)
Compte (numCompte (1), solde, typeCompte)
Operation (numOperation (1), dateOperation, typeOperation, montant, leClient = @Client(numClient) NN, leCompte = @Compte(numCompte) NN)
Appartient (unCompte = @Compte(numCompte)(1), unClient = @Client(numClient)(1))

*/



-----------------------------------------------------------------
-- Un directeur travaille forcément dans l'agence qu'il dirige --
-----------------------------------------------------------------


-- Modification d'un directeur dans la table Agence
---------------------------------------------------

CREATE OR REPLACE TRIGGER trig_Agence_agDir
BEFORE UPDATE ON Agence
FOR EACH ROW
WHEN (NEW.sonDirecteur != OLD.sonDirecteur)
DECLARE 
	v_sonAgence Agent.sonAgence%TYPE;
BEGIN

	SELECT sonAgence INTO v_sonAgence
	FROM Agent
	WHERE numAgent = :NEW.sonDirecteur;
	
	IF (v_sonAgence != :NEW.numAgence) THEN
		RAISE_APPLICATION_ERROR(-20004,'Ce directeur ne travaille pas dans cette agence !');
	END IF;
	
END;
/


-- Modification de l'agence d'un directeur dans la table Agent
--------------------------------------------------------------

CREATE OR REPLACE TRIGGER trig_Agent_agDir
BEFORE UPDATE ON Agent
FOR EACH ROW
WHEN (NEW.sonAgence != OLD.sonAgence)
DECLARE 
	v_sonDirecteur Agence.sonDirecteur%TYPE;
BEGIN

	SELECT sonDirecteur INTO v_sonDirecteur
	FROM Agence
	WHERE sonDirecteur = :NEW.numAgent;
	
	RAISE_APPLICATION_ERROR(-20004,'Le directeur '||v_sonDirecteur||' ne peut pas changer d''agence !');
	
	EXCEPTION
	
		WHEN NO_DATA_FOUND THEN
			DBMS_OUTPUT.PUT_LINE('Cet agent n''est pas un directeur donc il peut, a priori, changer d''agence.');
	
END;
/
	

	
---------------------------------------------------------------------------
-- Le directeur d'une agence est mieux payé que les agents de son agence --
---------------------------------------------------------------------------


-- Modification d’un directeur dans la table Agence
---------------------------------------------------

CREATE OR REPLACE TRIGGER trig_Agence_salDir
BEFORE UPDATE ON Agence
--FOLLOWS trig_Agence_agDir -- Précise l'ordre d'éxécution des triggers sur la table Agence
FOR EACH ROW
WHEN (NEW.sonDirecteur != OLD.sonDirecteur)
DECLARE
	CURSOR cur_employesMieuxPayes IS
		SELECT LesEmp.numAgent
		FROM Agent LeDir, Agent LesEmp
		WHERE LeDir.sonAgence = LesEmp.sonAgence
		AND LeDir.numAgent = :NEW.sonDirecteur
		AND LeDir.salaire < LesEmp.salaire;
	v_nbPbs NUMBER := 0;
BEGIN

	FOR v_employesMieuxPayes IN cur_employesMieuxPayes
		LOOP 
			DBMS_OUTPUT.PUT_LINE('Attention, l''agent '||v_employesMieuxPayes.numAgent||' sera mieux payé que son directeur !');
			v_nbPbs := v_nbPbs + 1;
		END LOOP;
	
	IF (v_nbPbs > 0) THEN 
		RAISE_APPLICATION_ERROR(-20004,'Cette modification ne respecte pas la contrainte portant sur le salaire d''un directeur');
	END IF;
	
END;	
/


-- Modification dans la table Agent (agence d'un employé, mais aussi salaire d'un directeur ou d'un employé)
------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE TRIGGER trig_Agent_salDir
AFTER UPDATE ON Agent
DECLARE 
	CURSOR cur_salDirAvecPb IS
		SELECT numAgence, sonDirecteur, Emp.numAgent
		FROM Agence, Agent Dir, Agent Emp
		WHERE sonDirecteur = Dir.numAgent
		AND Dir.sonAgence = Emp.sonAgence
		AND Dir.salaire < Emp.salaire;
	v_nbPbs NUMBER := 0; 
BEGIN

	FOR v_salDirAvecPb IN cur_salDirAvecPb
		LOOP 
			DBMS_OUTPUT.PUT_LINE('Attention, le directeur '||v_salDirAvecPb.sonDirecteur||' de l''agence '||v_salDirAvecPb.numAgence||' sera moins bien payé que l''agent '||v_salDirAvecPb.numAgent);
			v_nbPbs := v_nbPbs + 1;
		END LOOP;
	
	IF (v_nbPbs > 0) THEN 
		RAISE_APPLICATION_ERROR(-20005,'Cette modification ne respecte pas la contrainte portant sur le salaire d''un directeur');
	END IF;
	
END;	 
/  	
