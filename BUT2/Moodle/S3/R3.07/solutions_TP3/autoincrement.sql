-- Autoincrement de Agence
DROP SEQUENCE seq_Agence ;
CREATE SEQUENCE seq_Agence ;
CREATE OR REPLACE TRIGGER trig_seq_Agence
BEFORE INSERT ON Agence
FOR EACH ROW
WHEN (NEW. numAgence IS NULL)
BEGIN
LOOP
    SELECT seq_Agence.NEXTVAL INTO :NEW.numAgence FROM DUAL;
    DECLARE
        v_numAgence Agence.numAgence%TYPE;
    BEGIN
        SELECT numAgence INTO  v_numAgence from Agence WHERE numAgence=:NEW.numAgence;
    Exception
        WHEN NO_DATA_FOUND THEN EXIT;
    END;
END LOOP;
END;
/
-- Autoincrement de Compte
DROP SEQUENCE seq_Compte ;
CREATE SEQUENCE seq_Compte ;
CREATE OR REPLACE TRIGGER trig_seq_Compte
BEFORE INSERT ON Compte
FOR EACH ROW
WHEN (NEW.numCompte IS NULL)
BEGIN
LOOP
    SELECT seq_Compte.NEXTVAL INTO :NEW.numCompte FROM DUAL;
    DECLARE
        v_numCompte Compte.numCompte%TYPE;
    BEGIN
        SELECT numCompte INTO  v_numCompte from Compte WHERE numCompte=:NEW.numCompte;
    Exception
        WHEN NO_DATA_FOUND THEN EXIT;
    END;
END LOOP;
END;
/
-- Autoincrement de Agent
DROP SEQUENCE seq_Agent ;
CREATE SEQUENCE seq_Agent ;
CREATE OR REPLACE TRIGGER trig_seq_Agent
BEFORE INSERT ON Agent
FOR EACH ROW
WHEN (NEW.numAgent IS NULL)
BEGIN
LOOP
    SELECT seq_Agent.NEXTVAL INTO :NEW.numAgent FROM DUAL;
    DECLARE
        v_numAgent Agent.numAgent%TYPE;
    BEGIN
        SELECT numAgent INTO  v_numAgent from Agent WHERE numAgent=:NEW.numAgent;
    Exception
        WHEN NO_DATA_FOUND THEN EXIT;
    END;
END LOOP;
END;
/
-- Autoincrement de Operation
DROP SEQUENCE seq_Operation ;
CREATE SEQUENCE seq_Operation ;
CREATE OR REPLACE TRIGGER trig_seq_Operation
BEFORE INSERT ON Operation
FOR EACH ROW
WHEN (NEW.numOperation IS NULL)
BEGIN
LOOP
    SELECT seq_Operation.NEXTVAL INTO :NEW.numOperation FROM DUAL;
    DECLARE
        v_numOperation Operation.numOperation%TYPE;
    BEGIN
        SELECT numOperation INTO  v_numOperation from Operation WHERE numOperation=:NEW.numOperation;
    Exception
        WHEN NO_DATA_FOUND THEN EXIT;
    END;
END LOOP;
END;
/
-- Autoincrement de Client
DROP SEQUENCE seq_Client ;
CREATE SEQUENCE seq_Client ;
CREATE OR REPLACE TRIGGER trig_seq_Client
BEFORE INSERT ON Client
FOR EACH ROW
WHEN (NEW.numClient IS NULL)
BEGIN
LOOP
    SELECT seq_Client.NEXTVAL INTO :NEW.numClient FROM DUAL;
    DECLARE
        v_numClient Client.numClient%TYPE;
    BEGIN
        SELECT numClient INTO  v_numClient from Client WHERE numClient=:NEW.numClient;
    Exception
        WHEN NO_DATA_FOUND THEN EXIT;
    END;
END LOOP;
END;


