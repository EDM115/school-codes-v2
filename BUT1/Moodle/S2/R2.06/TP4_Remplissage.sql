DELETE FROM ATravaillePour;
DELETE FROM Avion;
DELETE FROM Qualification;
DELETE FROM TypeAvion;
DELETE FROM Pilote;
DELETE FROM Compagnie;

--------------------------------------------------------
--  Table COMPAGNIE
--------------------------------------------------------

Insert into COMPAGNIE (IDCOMP,NOMCOMP,PAYS,ESTLOWCOST) values (1,'Air France','France',0);
Insert into COMPAGNIE (IDCOMP,NOMCOMP,PAYS,ESTLOWCOST) values (2,'Corsair International','France',0);
Insert into COMPAGNIE (IDCOMP,NOMCOMP,PAYS,ESTLOWCOST) values (3,'EasyJet','Angleterre',1);
Insert into COMPAGNIE (IDCOMP,NOMCOMP,PAYS,ESTLOWCOST) values (4,'American Airlines','Etats-Unis',0);
Insert into COMPAGNIE (IDCOMP,NOMCOMP,PAYS,ESTLOWCOST) values (5,'Ryanair','Irelande',1);

--------------------------------------------------------
--  Table PILOTE
--------------------------------------------------------

Insert into PILOTE (IDPILOTE,NOMPILOTE,NBHVOL,COMPPIL) values (1,'Ridard',1500,1);
Insert into PILOTE (IDPILOTE,NOMPILOTE,NBHVOL,COMPPIL) values (2,'Naert',450,3);
Insert into PILOTE (IDPILOTE,NOMPILOTE,NBHVOL,COMPPIL) values (3,'Godin',450,5);
Insert into PILOTE (IDPILOTE,NOMPILOTE,NBHVOL,COMPPIL) values (4,'Fleurquin',3000,1);
Insert into PILOTE (IDPILOTE,NOMPILOTE,NBHVOL,COMPPIL) values (5,'Pham',900,4);
Insert into PILOTE (IDPILOTE,NOMPILOTE,NBHVOL,COMPPIL) values (6,'Kerbellec',900,null);
Insert into PILOTE (IDPILOTE,NOMPILOTE,NBHVOL,COMPPIL) values (7,'Kamp',3000,4);

--------------------------------------------------------
--  Table TYPEAVION
--------------------------------------------------------

Insert into TYPEAVION (IDTYPEAVION,NBPASSAGERS) values ('A320',174);
Insert into TYPEAVION (IDTYPEAVION,NBPASSAGERS) values ('A350',324);
Insert into TYPEAVION (IDTYPEAVION,NBPASSAGERS) values ('B747',279);

--------------------------------------------------------
--  Table QUALIFICATION
--------------------------------------------------------

Insert into QUALIFICATION (UNPILOTE,UNTYPEAVION) values (1,'A320');
Insert into QUALIFICATION (UNPILOTE,UNTYPEAVION) values (1,'A350');
Insert into QUALIFICATION (UNPILOTE,UNTYPEAVION) values (2,'A320');
Insert into QUALIFICATION (UNPILOTE,UNTYPEAVION) values (2,'B747');
Insert into QUALIFICATION (UNPILOTE,UNTYPEAVION) values (3,'A320');
Insert into QUALIFICATION (UNPILOTE,UNTYPEAVION) values (4,'A320');
Insert into QUALIFICATION (UNPILOTE,UNTYPEAVION) values (4,'A350');
Insert into QUALIFICATION (UNPILOTE,UNTYPEAVION) values (4,'B747');
Insert into QUALIFICATION (UNPILOTE,UNTYPEAVION) values (5,'A350');
Insert into QUALIFICATION (UNPILOTE,UNTYPEAVION) values (5,'A320');
Insert into QUALIFICATION (UNPILOTE,UNTYPEAVION) values (7,'A350');
Insert into QUALIFICATION (UNPILOTE,UNTYPEAVION) values (7,'B747');

--------------------------------------------------------
-- Table AVION
--------------------------------------------------------

Insert into AVION (IDAVION,LETYPEAVION,COMPAV) values (1,'A320',1);
Insert into AVION (IDAVION,LETYPEAVION,COMPAV) values (2,'A320',3);
Insert into AVION (IDAVION,LETYPEAVION,COMPAV) values (3,'A350',1);
Insert into AVION (IDAVION,LETYPEAVION,COMPAV) values (4,'A320',2);
Insert into AVION (IDAVION,LETYPEAVION,COMPAV) values (5,'B747',1);
Insert into AVION (IDAVION,LETYPEAVION,COMPAV) values (6,'A350',4);
Insert into AVION (IDAVION,LETYPEAVION,COMPAV) values (7,'B747',4);
Insert into AVION (IDAVION,LETYPEAVION,COMPAV) values (8,'A320',5);
Insert into AVION (IDAVION,LETYPEAVION,COMPAV) values (9,'A320',5);

--------------------------------------------------------
--  Table ATRAVAILLEPOUR
--------------------------------------------------------

Insert into ATRAVAILLEPOUR (LEPILOTE,LACOMP) values (4,1);
Insert into ATRAVAILLEPOUR (LEPILOTE,LACOMP) values (4,2);
Insert into ATRAVAILLEPOUR (LEPILOTE,LACOMP) values (4,3);
Insert into ATRAVAILLEPOUR (LEPILOTE,LACOMP) values (4,4);
Insert into ATRAVAILLEPOUR (LEPILOTE,LACOMP) values (4,5);
Insert into ATRAVAILLEPOUR (LEPILOTE,LACOMP) values (1,1);
Insert into ATRAVAILLEPOUR (LEPILOTE,LACOMP) values (1,2);
Insert into ATRAVAILLEPOUR (LEPILOTE,LACOMP) values (1,3);
Insert into ATRAVAILLEPOUR (LEPILOTE,LACOMP) values (1,5);
Insert into ATRAVAILLEPOUR (LEPILOTE,LACOMP) values (2,3);
Insert into ATRAVAILLEPOUR (LEPILOTE,LACOMP) values (2,5);
Insert into ATRAVAILLEPOUR (LEPILOTE,LACOMP) values (3,5);
Insert into ATRAVAILLEPOUR (LEPILOTE,LACOMP) values (5,4);
Insert into ATRAVAILLEPOUR (LEPILOTE,LACOMP) values (5,3);
Insert into ATRAVAILLEPOUR (LEPILOTE,LACOMP) values (7,4);
Insert into ATRAVAILLEPOUR (LEPILOTE,LACOMP) values (7,1);