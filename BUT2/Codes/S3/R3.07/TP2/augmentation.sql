-- 10)

SET SERVEROUTPUT ON;
DECLARE
    CURSOR cur_agents IS
        SELECT numAgent, salaire, estDirecteur
        FROM Agent;
    rec_agent cur_agents%ROWTYPE;
BEGIN
    OPEN cur_agents;
    LOOP
        FETCH cur_agents
        INTO rec_agent;
        EXIT WHEN cur_agents%NOTFOUND;
        IF rec_agent.estDirecteur = 1 THEN
            rec_agent.salaire := rec_agent.salaire * 1.05;
        ELSE
            rec_agent.salaire := rec_agent.salaire * 1.01;
        END IF;
        UPDATE Agent
        SET salaire = rec_agent.salaire
        WHERE numAgent = rec_agent.numAgent;
    END LOOP;
    CLOSE cur_agents;
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Mise à jour des salaires effectuée.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erreur lors de la mise à jour des salaires : ' || SQLERRM);
        ROLLBACK;
END;
