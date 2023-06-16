SET SERVEROUTPUT ON

-- on prépare les tests

DELETE FROM Reservation;
DELETE FROM Bateau;
DELETE FROM Emplacement;
DELETE FROM Proprietaire;

INSERT INTO Proprietaire VALUES (1,'Ridard','Anthony','anthony.ridard@univ-ubs.fr');
INSERT INTO Proprietaire VALUES (2,'Pham','Minh-Tan','minh-tan.pham@univ-ubs.fr');
INSERT INTO Proprietaire VALUES (3,'Kerbellec','Goulven','goulven.kerbellec@univ-ubs.fr');

INSERT INTO Bateau VALUES (1,NULL,8,1,NULL);
INSERT INTO Bateau VALUES (2,NULL,9,1,NULL);
INSERT INTO Bateau VALUES (3,NULL,9,2,NULL);
INSERT INTO Bateau VALUES (4,NULL,10,3,NULL);

INSERT INTO Emplacement VALUES (1,12,30);
INSERT INTO Emplacement VALUES (2,10,20);
INSERT INTO Emplacement VALUES (3,10,15);
INSERT INTO Emplacement VALUES (4,10,15);

-- question 1.a

ALTER TABLE Reservation
	ADD CONSTRAINT ck_resa_duree_limitee CHECK(dateFin - dateDebut <= 7);

-- on teste le CHECK

INSERT INTO Reservation VALUES (1,SYSDATE+2,SYSDATE+10,1,2);

/*

ORA-02290: check constraint (RIDARD.CK_RESA_DUREE_LIMITEE) violated

*/

-- question 1.b

CREATE OR REPLACE TRIGGER trig_resa_freq_limitee
AFTER INSERT ON Reservation
DECLARE
	CURSOR cur_resa_freq_limitee IS
		SELECT R1.idReservation resa_1, R2.idReservation resa_2
		FROM Bateau B1, Reservation R1, Reservation R2, Bateau B2
		WHERE B1.idBateau = R1.leBateau
		AND B2.idBateau = R2.leBateau
		AND B1.leProprietaire = B2.leProprietaire
		AND R1.idReservation < R2.idReservation
		AND R1.dateFin + 15 > R2.dateDebut; -- on suppose pour simplifier que R1.dateFin < R2.dateDebut
		
	v_nbPb NUMBER :=0;
BEGIN
	
	FOR v_resa_freq_limitee IN cur_resa_freq_limitee
		LOOP 
			DBMS_OUTPUT.PUT_LINE('Conflit entre '||v_resa_freq_limitee.resa_1||' et '||v_resa_freq_limitee.resa_2||' !');
			v_nbPb := v_nbPb + 1;
		END LOOP;
	
	IF (v_nbPb > 0) THEN
		RAISE_APPLICATION_ERROR(-20002,'Au moins un conflit');
	END IF;
	
END;
/

-- on teste le TRIGGER

INSERT ALL 
	INTO Reservation VALUES (1,SYSDATE+2,SYSDATE+9,1,1)
	INTO Reservation VALUES (2,SYSDATE+11,SYSDATE+15,2,2)
	INTO Reservation VALUES (3,SYSDATE+1,SYSDATE+8,3,3)
	INTO Reservation VALUES (4,SYSDATE+18,SYSDATE+25,3,4)
SELECT * FROM DUAL;

/*

Conflit entre 1 et 2 !
Conflit entre 3 et 4 !
...
ORA-20002: Au moins un conflit
ORA-06512: at "RIDARD.TRIG_RESA_FREQ_LIMITEE", line 21
ORA-04088: error during execution of trigger 'RIDARD.TRIG_RESA_FREQ_LIMITEE'

*/

-- question 2

-- on prépare le test

INSERT ALL 
	INTO Reservation VALUES (1,SYSDATE+2,SYSDATE+5,1,1)
	INTO Reservation VALUES (2,SYSDATE+25,SYSDATE+28,2,2)
	INTO Reservation VALUES (3,SYSDATE+1,SYSDATE+8,3,3)
	INTO Reservation VALUES (4,SYSDATE+24,SYSDATE+26,3,4)
	INTO Reservation VALUES (5,SYSDATE+58,SYSDATE+60,2,3)
SELECT * FROM DUAL;

-- on écrit la procédure anonyme

DECLARE
	CURSOR cur_nbResa IS
	SELECT nomProprietaire, COUNT(idReservation) nbResa
	FROM Proprietaire
		LEFT JOIN Bateau ON idProprietaire = leProprietaire
			LEFT JOIN Reservation ON idBateau = leBateau
	GROUP BY idProprietaire, nomProprietaire
	ORDER BY nomProprietaire;

BEGIN	
	FOR v_nbResa IN cur_nbResa
		LOOP 
			DBMS_OUTPUT.PUT_LINE('Le propriétaire '||v_nbResa.nomProprietaire||' a effectué '||v_nbResa.nbResa||' réservation(s).');
		END LOOP;
END;
/

/*

Le propriÃ©taire Kerbellec a effectuÃ© 0 rÃ©servation(s).
Le propriÃ©taire Pham a effectuÃ© 2 rÃ©servation(s).
Le propriÃ©taire Ridard a effectuÃ© 3 rÃ©servation(s).

*/