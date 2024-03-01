------------------------------------------------------------------
-- Lister les 5 derniers retraits du plus récent au plus ancien --
------------------------------------------------------------------
SET SERVEROUTPUT ON
DECLARE
	CURSOR cur_cinqDerniersRetraits IS
	SELECT *
	FROM
		(
		SELECT numOperation, dateOperation
		FROM Operation
		WHERE typeOperation = 'RETRAIT'
		ORDER BY dateOperation DESC
		)
	WHERE ROWNUM <= 5;
	--ORDER BY dateOperation DESC;
BEGIN	
	FOR v_cinqDerniersRetraits IN cur_cinqDerniersRetraits
		LOOP 
			DBMS_OUTPUT.PUT_LINE('L''opération '||v_cinqDerniersRetraits.numOperation||' a été effectuée le '||v_cinqDerniersRetraits.dateOperation||'.');
		END LOOP;
END;
/


-------------------------------------------------------------------------------------------------
-- Pour chaque agence, indiquer le nombre de clients ne possédant pas encore de compte épargne --
-------------------------------------------------------------------------------------------------
SET SERVEROUTPUT ON
DECLARE
	CURSOR cur_nbClSansEpargne IS
	SELECT numAgence, COUNT(sonAgence) nbCl
    FROM Agent 
    JOIN (
    	SELECT numClient, sonAgent
    	FROM Client
    	MINUS
    	SELECT unClient, sonAgent
    	FROM Appartient, Compte, Client
    	WHERE unCompte = numCompte
    	AND unClient = numClient
    	AND typeCompte = 'EPARGNE'
    	) ON sonAgent = numAgent
    RIGHT JOIN AGENCE ON sonAgence=numAgence
    GROUP BY numAgence;

BEGIN	
	FOR v_nbClSansEpargne IN cur_nbClSansEpargne
		LOOP 
			DBMS_OUTPUT.PUT_LINE('L''agence '||v_nbClSansEpargne.numAgence||' compte '||v_nbClSansEpargne.nbCl||' client(s) sans compte épargne.');
		END LOOP;
END;
/