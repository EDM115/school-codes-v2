SELECT * FROM Compte WHERE numCompte=19;
DELETE FROM Compte WHERE numCompte = 19;
-- Suppression impossible => Violation de contrainte d'integrité. Il existe des associations dans Appartient
SELECT * FROM Appartient WHERE UnCompte=19;
SELECT * FROM Compte WHERE numCompte=19;
ROLLBACK;