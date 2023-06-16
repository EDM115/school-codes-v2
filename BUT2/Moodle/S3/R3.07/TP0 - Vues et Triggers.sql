/*
Schéma relationnel
------------------

Proprietaire ( idProprietaire (1), nomProprietaire (2), prenomProprietaire (2), emailProprietaire (3) )
Emplacement ( idEmplacement (1), longueurEmplacement (NN), coutJournalier (NN) )
Bateau ( idBateau (1), nomBateau, longueurBateau (NN), leProprietaire = @Proprietaire.idProprietaire (NN), leStationnement = @Emplacement.idEmplacement (UQ) )
Reservation ( idReservation (1), dateDebut (NN), dateFin (NN), leBateau = @Bateau.idBateau (NN), lEmplacement = @Emplacement.idEmplacement (NN) )

Contraintes textuelles
----------------------

- la longueur du bateau ne doit pas dépasser 10 m --> OK
- la syntaxe de l'email doit être valide --> OK
- dateDebut < dateFin --> OK
- coutReservation = coutJournalier x nbJours --> vue à venir
- un emplacement ne peut être réservé pour un bateau dépassant sa longueur --> trigger à venir
- un stationnement doit faire l'objet d'une réservation --> trigger à venir
- un emplacement ne peut être réservé par deux bateaux différents le même jour --> trigger à venir

- chaque propriétaire possède au moins un bateau : Proprietaire[idProprietaire] = Bateau[leProprietaire] --> vue à venir
*/

------------------------------------------------
-- coutReservation = coutJournalier x nbJours --
------------------------------------------------

CREATE OR REPLACE VIEW vue_coutReservation 
AS
	SELECT idReservation, (dateFin-dateDebut)*coutJournalier coutReservation 
	FROM Reservation, Emplacement
	WHERE lEmplacement = idEmplacement
;

----------------------------------------------------
-- chaque propriétaire possède au moins un bateau --
----------------------------------------------------

CREATE OR REPLACE VIEW vue_proprietaireSansBateau
	(
	proprietaireSansBateau
	)
AS
	SELECT idProprietaire
	FROM Proprietaire
	MINUS
	SELECT leProprietaire
	FROM Bateau
;

------------------------------------------------------------------------------
-- un emplacement ne peut être réservé pour un bateau dépassant sa longueur --
------------------------------------------------------------------------------

CREATE OR REPLACE TRIGGER trig_batInfEmpl
BEFORE INSERT ON Reservation
FOR EACH ROW
DECLARE
	v_longueurBateau Bateau.longueurBateau%TYPE;
	v_longueurEmplacement Emplacement.longueurEmplacement%TYPE;
BEGIN

	SELECT longueurBateau INTO v_longueurBateau
	FROM Bateau
	WHERE idBateau = :NEW.leBateau;
	
	SELECT longueurEmplacement INTO v_longueurEmplacement
	FROM Emplacement
	WHERE idEmplacement = :NEW.lEmplacement;

    IF (v_longueurBateau > v_longueurEmplacement) THEN
		RAISE_APPLICATION_ERROR(-20000,'La longueur du bateau est plus grande que celle de l''emplacement !');
	END IF;
	
END;
/

-----------------------------------------------------------
-- un stationnement doit faire l'objet d'une réservation --
-----------------------------------------------------------

CREATE OR REPLACE TRIGGER trig_reservationStat
BEFORE UPDATE ON Bateau
FOR EACH ROW
WHEN (NEW.leStationnement IS NOT NULL)
DECLARE	
	v_nbReservationStat NUMBER;
BEGIN

	SELECT COUNT(*) INTO v_nbReservationStat
	FROM Reservation
	WHERE leBateau = :NEW.idBateau
	AND lEmplacement = :NEW.leStationnement
	AND SYSDATE BETWEEN dateDebut AND dateFin;

    IF (v_nbReservationStat = 0) THEN
		RAISE_APPLICATION_ERROR(-20001,'La réservation n''a pas été effectuée');
	END IF;
	
END;
/

----------------------------------------------------------------------------------
-- un emplacement ne peut être réservé par deux bateaux différents le même jour --
----------------------------------------------------------------------------------

CREATE OR REPLACE TRIGGER trig_resMemeEmpl
AFTER INSERT ON Reservation
DECLARE
	v_nbPb NUMBER;
BEGIN
	
	SELECT COUNT(*) INTO v_nbPb
	FROM Reservation R1, Reservation R2
	WHERE R1.lEmplacement = R2.lEmplacement
	AND R1.idReservation != R2.idReservation
	AND R1.dateFin > R2.dateDebut;

    IF (v_nbPb > 0) THEN
		RAISE_APPLICATION_ERROR(-20002,'Un emplacement ne peut être réservé par deux bateaux différents le même jour !');
	END IF;
	
END;
/