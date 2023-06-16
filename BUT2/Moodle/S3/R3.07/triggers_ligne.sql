---------------------------------------------------------------------------
-- Une augmentation de salaire ne doit pas dépasser 10% et une baisse 8% --
---------------------------------------------------------------------------

CREATE OR REPLACE TRIGGER trig_modifSalaire
BEFORE UPDATE ON Agent
FOR EACH ROW
WHEN (NEW.salaire != OLD.salaire)
BEGIN
	IF (:NEW.salaire > 1.1*:OLD.salaire) THEN
		RAISE_APPLICATION_ERROR(-20004,'L''augmentation de salaire dépasse 10%');
	END IF;
	IF (:NEW.salaire < 0.92*:OLD.salaire) THEN
		RAISE_APPLICATION_ERROR(-20005,'La diminution de salaire dépasse 8%');
	END IF;
END;
/


-----------------------------------------------------------------------------------
-- Un client ne peut pas être conseillé par un agent portant le même nom que lui --
-----------------------------------------------------------------------------------

CREATE OR REPLACE TRIGGER trig_nomsIdentiques
BEFORE INSERT OR UPDATE ON Client
FOR EACH ROW 
DECLARE
	v_nomAgent Agent.nomAgent%TYPE;
BEGIN
	SELECT nomAgent INTO v_nomAgent
	FROM Agent
	WHERE numAgent = :NEW.sonAgent;
	
	IF (:NEW.nomClient = v_nomAgent) THEN
		RAISE_APPLICATION_ERROR(-20006,'Le client est conseillé par un agent portant le même nom que lui');
	END IF;
END;
/


-----------------------------------------------------------------------------------------------
-- Un client ne doit pas pouvoir effectuer un retrait dont le montant est supérieur au solde --
-----------------------------------------------------------------------------------------------

CREATE OR REPLACE TRIGGER trig_montantRetrait
BEFORE INSERT OR UPDATE ON Operation
FOR EACH ROW
WHEN (NEW.typeOperation = 'RETRAIT')
DECLARE
	v_solde Compte.solde%TYPE;
BEGIN
	SELECT solde INTO v_solde
	FROM Compte
	WHERE numCompte = :NEW.leCompte;
	
	IF (:NEW.montant > v_solde) THEN
		RAISE_APPLICATION_ERROR(-20007,'Le montant du retrait est supérieur au solde');
	END IF;
END;
/


----------------------------------------------------------------------------------------
-- Un client ne doit pouvoir retirer de l'argent que sur un compte qui lui appartient --
----------------------------------------------------------------------------------------

CREATE OR REPLACE TRIGGER trig_compteRetrait
BEFORE INSERT OR UPDATE ON Operation
FOR EACH ROW 
WHEN (NEW.typeOperation = 'RETRAIT')
DECLARE
	v_nbPbs NUMBER;
BEGIN
	SELECT COUNT(*) INTO v_nbPbs
	FROM Appartient
	WHERE unCompte = :NEW.leCompte
	AND unClient = :NEW.leClient;
	
	IF (v_nbPbs = 0) THEN
		RAISE_APPLICATION_ERROR(-20008,'Le compte n''appartient pas au client');
	END IF;
END;
/