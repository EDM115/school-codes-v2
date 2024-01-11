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