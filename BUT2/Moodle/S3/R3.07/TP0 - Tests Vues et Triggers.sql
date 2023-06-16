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

-----------------------
-- Vidage des tables --
-----------------------

DELETE FROM Reservation;
DELETE FROM Bateau;
DELETE FROM Emplacement;
DELETE FROM Proprietaire;

------------------------------------------------
-- coutReservation = coutJournalier x nbJours --
------------------------------------------------

INSERT INTO Proprietaire VALUES (1,'Ridard','Anthony','anthony.ridard@univ-ubs.fr');
INSERT INTO Bateau VALUES (1,NULL,8,1,NULL);
INSERT INTO Emplacement VALUES (1,10,30);
INSERT INTO Reservation VALUES (1,TO_DATE('18/02/2020','DD/MM/YYYY'),TO_DATE('28/02/2020','DD/MM/YYYY'),1,1);

SELECT *
FROM vue_coutReservation;

----------------------------------------------------
-- chaque propriétaire possède au moins un bateau --
----------------------------------------------------

INSERT INTO Proprietaire VALUES (2,'Kerbellec','Goulven','goulven.kerbellec@univ-ubs.fr');

SELECT *
FROM vue_proprietaireSansBateau;

------------------------------------------------------------------------------
-- un emplacement ne peut être réservé pour un bateau dépassant sa longueur --
------------------------------------------------------------------------------

INSERT INTO Emplacement VALUES (2,6,15);
INSERT INTO Reservation VALUES (2,TO_DATE('18/02/2020','DD/MM/YYYY'),TO_DATE('28/02/2020','DD/MM/YYYY'),1,2);

-----------------------------------------------------------
-- un stationnement doit faire l'objet d'une réservation --
-----------------------------------------------------------

UPDATE Bateau
SET leStationnement = 1
WHERE idBateau = 1;

----------------------------------------------------------------------------------
-- un emplacement ne peut être réservé par deux bateaux différents le même jour --
----------------------------------------------------------------------------------

INSERT INTO Bateau VALUES (2,NULL,9,2,NULL);
INSERT INTO Reservation VALUES (2,TO_DATE('10/02/2020','DD/MM/YYYY'),TO_DATE('20/02/2020','DD/MM/YYYY'),2,1);