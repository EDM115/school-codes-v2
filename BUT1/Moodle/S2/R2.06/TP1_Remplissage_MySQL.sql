DELETE FROM Responsabilite;
DELETE FROM Apprenti;
DELETE FROM Stagiaire;
DELETE FROM Entreprise;
DELETE FROM Etudiant;
DELETE FROM GroupeInfo1;
DELETE FROM Enseignant;

-- Table Enseignant

INSERT INTO Enseignant VALUES 
('MA','Adam','Michel'),
('PB','Baudont','Pascal'),
('IB','Borne','Isabelle'),
('RF','Fleurquin','Regis'),
 ('TG','Godin','Thibault'),
 ('PJ','Joucla','Philippe'),
 ('GK','Kerbellec','Goulven'),
 ('MLL','Le Lain','Matthieu'),
 ('NLS','Le Sommer','Nicolas'),
 ('SL','Lefevre','Sebastien'),
 ('FL','Lesueur','Francois'),
 ('MM','Mannevy','Muriel'),
 ('FME','Merciol','Francois'),
 ('LN','Naert','Lucie'),
 ('MTP','Pham','Minh-Tan'),
 ('AR','Ridard','Anthony'),
 ('XR','Roirand','Xavier'),
 ('HT','Tuffigo','Helene'),
 ('MV','Volin','Manon'),
 ('JFK','Kamp','Jean-Francois');

--  Table GroupeInfo1

INSERT INTO GroupeInfo1 VALUES 
 ('A','TG'),
 ('B','HT'),
 ('C','PJ'),
 ('D','MA');

--  Table ETUDIANT

INSERT INTO ETUDIANT VALUES 
 (12345678,'TEST','TEST','M','S','LYCEE GENERAL ET TECHNOLOGIQUE X',22,'B','IA','App',null);

--  ENTREPRISE

INSERT INTO ENTREPRISE VALUES
 (1,'ACCENTURE',75),
 (2,'ADAPEI56',56),
 (3,'ADM',56),
 (4,'ALADOM',35),
 (5,'ANVERGUR',56),
 (6,'APRAS',35),
 (7,'AWAHOTO',35),
 (8,'BIOTHOT',56),
 (9,'BYSTAMP',56),
 (10,'CADIOU INDUSTRIE',29),
 (11,'CAISSE DEPARGNE LOIRE DROME ARDECHE',42),
 (12,'CGI',56),
 (13,'CGI',35),
 (14,'CMB',29),
 (15,'CNRS',35),
 (16,'COLLEGE SACRE COEUR',56),
 (17,'CONSEIL DEPARTEMENTAL DU MORBIHAN',56),
 (18,'CREDIT AGRICOLE',56),
 (19,'DAVID SYSTEMS GMBH',null),
 (20,'DAWIZZ',56),
 (21,'DCODE-IT',27),
 (22,'DEPARTEMENT DE LA VENDEE',85),
 (23,'DEPARTEMENT DILLE-ET-VILAINE',35),
 (24,'DIOTAL SAS',56),
 (25,'DGFIP',35),
 (26,'DIRISI',29),
 (27,'ÉCOLES DE SAINT-CYR COËTQUIDAN',56),
 (28,'ENSIBS',56),
 (29,'ERCII',56),
 (30,'EXOTIC ANIMALS',56),
 (31,'GROUPE ROCHER',35),
 (32,'HIPPOCAD',77),
 (33,'HORIZON SYSTEMES DINFORMATION',60),
 (34,'IDEMIA',95),
 (35,'IFREMER',29),
 (36,'INPIXAL',35),
 (37,'INRAE ET FN3PT',75),
 (38,'IOLE SOLUTIONS',56),
 (39,'IRISA, EQUIPE OBELIX',56),
 (40,'JEXLPROD',56),
 (41,'JULIANA MULTIMEDIA',56),
 (42,'KARROAD',65),
 (43,'LA BIDOUILLERIE',35),
 (44,'LACTALIS',53),
 (45,'LEAD OFF',56),
 (46,'LETSEAT',87),
 (47,'LUNION DES FORGERONS',91),
 (48,'MAIRIE DE SÉNÉ',56),
 (49,'MENBAT',56),
 (50,'MONSTOCK',51),
 (51,'NAVAL GROUP',83),
 (52,'NIJI',44),
 (53,'NIRYO',59),
 (54,'NOVOSYS INGENIERIE',88),
 (55,'OPTIC PERFORMANCE',56),
 (56,'ORANGE CYBERDÉFENSE',92),
 (57,'OUEST BOISSON',56),
 (58,'PLASTEOL',56),
 (59,'PORT DE PÊCHE KEROMAN',56),
 (60,'SCM VIAUD-FORMAGNE',56),
 (61,'SEM LORIENT KEROMAN',56),
 (62,'SIB',35),
 (63,'SITIA',44),
 (64,'SMARTMOOV',35),
 (65,'SMURFIT KAPPA FRANCE',56),
 (66,'SOLIDR',56),
 (67,'UBS',56),
 (68,'VIDEOR INFORMATIQUE',56),
 (69,'WINNOVE MEDICAL',35),
 (70,'WISEBAND',85),
 (71,'YOGOKO',35);


--  Table STAGIAIRE

INSERT INTO STAGIAIRE  VALUES 
 (12345678,60);

--  Table APPRENTI

INSERT INTO APPRENTI  VALUES
 (12345678,71,'MM');

 -- Table Responsabilite
 INSERT INTO Responsabilite VALUES 
 ('admin','LN'),
 ('stages','JFK'),
 ('apprentis','PB'),
 ('poursuite_etudes','RF'),
 ('chef_dept','JFK'),
 ('direction_edudes','AR');

 