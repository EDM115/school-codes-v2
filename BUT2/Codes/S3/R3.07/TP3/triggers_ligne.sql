-- 2)

SET SERVEROUTPUT ON;
CREATE OR REPLACE TRIGGER trig_modifSalaire
BEFORE UPDATE OF salaire
ON Agent
FOR EACH ROW
DECLARE
    ancien_salaire NUMBER;
    nouveau_salaire NUMBER;
BEGIN
    ancien_salaire := :OLD.salaire;
    nouveau_salaire := :NEW.salaire;

    IF nouveau_salaire > ancien_salaire * 1.10
    OR nouveau_salaire < ancien_salaire * 0.92
    THEN
        RAISE_APPLICATION_ERROR(-20001, 'Modification de salaire non autorisée. Augmentation max de 10% et baisse max de 8%.');
    END IF;
END;

CREATE OR REPLACE TRIGGER trig_nomAgentClient
BEFORE INSERT OR UPDATE
ON Client
FOR EACH ROW
DECLARE
    nom_agent VARCHAR2(100);
BEGIN
    SELECT nomAgent
    INTO nom_agent
    FROM Agent
    WHERE numAgent = :NEW.sonAgent;

    IF nom_agent = :NEW.nomClient THEN
        RAISE_APPLICATION_ERROR(-20002, 'Un client ne peut pas être conseillé par un agent portant le même nom.');
    END IF;
END;

CREATE OR REPLACE TRIGGER trig_retraitSolde
BEFORE INSERT
ON Operation
FOR EACH ROW
WHEN (NEW.typeOperation = 'RETRAIT')
DECLARE
    solde_actuel NUMBER;
BEGIN
    SELECT solde
    INTO solde_actuel
    FROM Compte
    WHERE numCompte = :NEW.leCompte;

    IF :NEW.montant > solde_actuel THEN
        RAISE_APPLICATION_ERROR(-20003, 'Montant de retrait supérieur au solde disponible.');
    ELSE
        UPDATE Compte SET solde = solde_actuel - :NEW.montant
        WHERE numCompte = :NEW.leCompte;
    END IF;
END;

CREATE OR REPLACE TRIGGER trig_retraitCompteClient
BEFORE INSERT
ON Operation
FOR EACH ROW
WHEN (NEW.typeOperation = 'RETRAIT')
DECLARE
    compte_client NUMBER;
BEGIN
    SELECT COUNT(*)
    INTO compte_client
    FROM appartient
    WHERE unCompte = :NEW.leCompte
    AND unClient = :NEW.leclient;

    IF compte_client = 0 THEN
        RAISE_APPLICATION_ERROR(-20004, 'Un client ne peut retirer de l''argent que sur un compte qui lui appartient.');
    END IF;
END;

CREATE OR REPLACE TRIGGER trig_depotSolde
BEFORE INSERT
ON Operation
FOR EACH ROW
WHEN (NEW.typeOperation = 'DEPOT')
DECLARE
    solde_actuel NUMBER;
BEGIN
    SELECT solde
    INTO solde_actuel
    FROM Compte
    WHERE numCompte = :NEW.leCompte;

    UPDATE Compte SET solde = solde_actuel + :NEW.montant
    WHERE numCompte = :NEW.leCompte;
END;
