-- 7)

SET SERVEROUTPUT ON;
DROP SEQUENCE seq_Agence;
CREATE SEQUENCE seq_Agence
START WITH 1
INCREMENT BY 1
NOMAXVALUE;

CREATE OR REPLACE TRIGGER trig_seq_Agence
BEFORE INSERT
ON Agence
FOR EACH ROW
WHEN (NEW.numAgence IS NULL)
BEGIN
    SELECT seq_Agence.NEXTVAL
    INTO :NEW.numAgence
    FROM DUAL;
END;

-- 8)

INSERT INTO Agence(numAgence) VALUES(NULL);

/*
Erreur commençant à la ligne: 2 de la commande -
INSERT INTO Agence(numAgence) VALUES(NULL)
Rapport d'erreur -
ORA-00001: unique constraint (EDM115.SYS_C007523) violated
*/

-- 9)

SET SERVEROUTPUT ON;
CREATE SEQUENCE seq_Agent
START WITH 1
INCREMENT BY 1
NOMAXVALUE;

CREATE OR REPLACE TRIGGER trig_seq_Agent
BEFORE INSERT
ON Agent
FOR EACH ROW
WHEN (NEW.numAgent IS NULL)
BEGIN
    SELECT seq_Agent.NEXTVAL
    INTO :NEW.numAgent
    FROM DUAL;
END;

CREATE SEQUENCE seq_Client
START WITH 1
INCREMENT BY 1
NOMAXVALUE;

CREATE OR REPLACE TRIGGER trig_seq_Client
BEFORE INSERT
ON Client
FOR EACH ROW
WHEN (NEW.numClient IS NULL)
BEGIN
    SELECT seq_Client.NEXTVAL
    INTO :NEW.numClient
    FROM DUAL;
END;

CREATE SEQUENCE seq_Compte
START WITH 1
INCREMENT BY 1
NOMAXVALUE;

CREATE OR REPLACE TRIGGER trig_seq_Compte
BEFORE INSERT
ON Compte
FOR EACH ROW
WHEN (NEW.numCompte IS NULL)
BEGIN
    SELECT seq_Compte.NEXTVAL
    INTO :NEW.numCompte
    FROM DUAL;
END;

CREATE SEQUENCE seq_Operation
START WITH 1
INCREMENT BY 1
NOMAXVALUE;

CREATE OR REPLACE TRIGGER trig_seq_Operation
BEFORE INSERT
ON Operation
FOR EACH ROW
WHEN (NEW.numOperation IS NULL)
BEGIN
    SELECT seq_Operation.NEXTVAL
    INTO :NEW.numOperation
    FROM DUAL;
END;
