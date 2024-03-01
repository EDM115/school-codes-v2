-----------------------------------------------------------------------------------
-- Nombre de comptes n'ayant pas de client (Sans curseur)
-----------------------------------------------------------------------------------
SET SERVEROUTPUT ON
DECLARE
    v_numPbs Number;
BEGIN 
	SELECT COUNT(*) INTO v_numPbs from (SELECT numCompte FROM Compte MINUS SELECT unCompte FROM Appartient);
	IF v_numPbs > 0 THEN
        DBMS_OUTPUT.PUT_LINE('Attention, ' || v_numPbs || ' comptes n''ont pas de client!');
	END IF;
END;
/
-----------------------------------------------------------------------------------
-- Liste des comptes n'ayant pas de client et leur nombre (Avec curseur)
-----------------------------------------------------------------------------------
SET SERVEROUTPUT ON
DECLARE
    CURSOR cur IS SELECT numCompte FROM Compte MINUS SELECT unCompte FROM Appartient;
	v_numPbs NUMBER :=0;
BEGIN 
	FOR rec IN cur LOOP
    	DBMS_OUTPUT.PUT_LINE('Le compte ' || rec.numCompte|| ' n''a pas de client!');
		v_numPBs := v_numPBs + 1;
	END LOOP;
	IF v_numPbs > 0 THEN
        DBMS_OUTPUT.PUT_LINE('Attention, ' || v_numPBs || ' comptes n''ont pas de client!');
	END IF;
END;
/
-----------------------------------------------------------------------------------
-- Lister les agences violant la contrainte d'unicité et d'existance d'un directeur
-----------------------------------------------------------------------------------
SET SERVEROUTPUT ON
DECLARE
	CURSOR cur IS
	SELECT numAgence, COUNT(sonAgence) as NBDIR FROM  (SELECT sonAgence
    FROM Agent  WHERE estDirecteur = 1) RIGHT JOIN AGENCE ON sonAgence = numAgence
    GROUP BY numAgence;
BEGIN	
	FOR rec IN cur
		LOOP 
    		IF rec.nbDir = 0 THEN  DBMS_OUTPUT.PUT_LINE('L''agence '|| rec.numAgence || ' n''a pas de directeur!');
			ELSIF rec.nbDir > 1 THEN DBMS_OUTPUT.PUT_LINE('L''agence '|| rec.numAgence || ' a ' || rec.NBDIR || ' directeurs!');
            END IF;
		END LOOP;
END;
/

----------------------------------------------------------
-- Un employé ne peut pas gagner plus que son directeur --
----------------------------------------------------------
SET SERVEROUTPUT ON
DECLARE 
	CURSOR cur IS
		SELECT Dir.sonAgence
		FROM Agent Dir, Agent Emp
		WHERE Dir.sonAgence = Emp.sonAgence
		AND Dir.estDirecteur = 1
		AND Emp.estDirecteur = 0
		GROUP BY Dir.sonAgence, Dir.salaire
		HAVING Dir.salaire < MAX(Emp.salaire);
	v_nbPbs NUMBER := 0;
BEGIN
	FOR rec IN cur
		LOOP 
			DBMS_OUTPUT.PUT_LINE('Problème dans l''agence no '||rec.sonAgence||' !');
			v_nbPbs := v_nbPbs + 1;
		END LOOP;
	IF (v_nbPbs > 0) THEN 
		DBMS_OUTPUT.PUT_LINE('Attention, ' || v_nbPbs || ' agence ont des problèmes!');
	ELSE DBMS_OUTPUT.PUT_LINE('Tout est bon!');
	END IF;
END;
/
----------------------------------------------------------
-- Liste des clients ayant les mêmes noms que leurs agents --
----------------------------------------------------------
DECLARE
	CURSOR cur_agents IS SELECT numAgent, nomAgent FROM AGENT;
    CURSOR cur_clients(p_sonAgent Client.sonAgent%Type) IS SELECT numCLient, nomClient FROM CLIENT WHERE sonAgent=p_sonAgent;
	v_cpt NUMBER := 0;
BEGIN
    FOR v_agent IN cur_agents LOOP
		FOR v_client IN cur_clients(v_agent.numAgent) LOOP
			IF v_agent.nomAgent = v_client.nomClient THEN
				DBMS_OUTPUT.PUT_LINE('LE Client '|| v_client.numClient || 'a le même nom que l''agent '||v_agent.numAgent);
				v_cpt := v_cpt+1;
			END IF;
        END LOOP;
	END LOOP;
	IF v_cpt=0 THEN
		DBMS_OUTPUT.PUT_LINE('Tout est bon!');
	END IF;
END;
/
----------------------------------------------------------------------------------------------------
-- Liste des clients ayant effectué des retraits sur des comptes qui ne leurs appartiennent pas --
----------------------------------------------------------------------------------------------------

DECLARE 
	CURSOR cur_operation IS SELECT numOperation, leCompte, leClient FROM Operation WHERE LOWER(typeOperation)='retrait';
	CURSOR cur_compte(p_compte Compte.numCompte%Type) IS SELECT unClient  FROM Appartient WHERE p_compte= unCompte ;
      v_prob BOOLEAN := FALSE;
	v_trouve BOOLEAN ;
BEGIN
    FOR v_op IN cur_operation LOOP
        v_trouve := FALSE;
    	FOR  v_cpt IN cur_compte(v_op.leCompte) LOOP
    		IF v_cpt.unClient = v_op.leClient THEN
    			v_trouve:=True;
            END IF;
		END LOOP;
		IF not v_trouve THEN
            DBMS_OUTPUT.PUT_LINE('Le client ' || v_op.leClient || ' a effectué un retrait sur un compte qui lui appartient pas' );
			v_prob := True;
        END IF;
	END LOOP;
	IF NOT v_prob THEN
       DBMS_OUTPUT.PUT_LINE('Tout est bon!');
	END IF; 
END;