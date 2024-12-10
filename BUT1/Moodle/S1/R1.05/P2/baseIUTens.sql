DROP TABLE EnseignantInfo;
DROP TABLE EtudiantInfo;

CREATE TABLE EnseignantInfo(
		noEns NUMBER(4)
			CONSTRAINT pk_EnseignantInfo PRIMARY KEY,
		nomEns VARCHAR2(20),
		prenom1 VARCHAR2(20),
		prenom2 VARCHAR2(20)
	)
;

INSERT INTO EnseignantInfo VALUES(1,'KAmP','Jean Francois',NULL);
INSERT INTO EnseignantInfo VALUES(2,'BEAuDONT','Pascal','Alexis');
INSERT INTO EnseignantInfo VALUES(3,'GODIN','Thibault','hubert');
INSERT INTO EnseignantInfo VALUES(4,'Lesueur','Francois',NULL);
INSERT INTO EnseignantInfo VALUES(5,'TONIN','Philippe','Fabien');
INSERT INTO EnseignantInfo VALUES(6,'ROIRAND','Xavier','francois');
INSERT INTO EnseignantInfo VALUES(7,'LE SOmMER','Nicolas','Florian');
INSERT INTO EnseignantInfo VALUES(8,'MErCIOL','Francois','hubert');
INSERT INTO EnseignantInfo VALUES(9,'LE Lain','Matthieu',NULL);
INSERT INTO EnseignantInfo VALUES(10,'LEFEVRE','Sebastien','francois');
INSERT INTO EnseignantInfo VALUES(11,'BORNE','Isabelle','lucie');
INSERT INTO EnseignantInfo VALUES(12,'PHAM','Minh Tan','luc');
INSERT INTO EnseignantInfo VALUES(13,'FLEURQUIN','Regis','francois');
INSERT INTO EnseignantInfo VALUES(14,'NAeRT','Lucie','violette');
INSERT INTO EnseignantInfo VALUES(15,'RIDaRD','Anthony','damien');
INSERT INTO EnseignantInfo VALUES(16,'LeMaitre','Elodie',NULL);
INSERT INTO EnseignantInfo VALUES(17,'TUFFIGOT','Helene','coralie');
INSERT INTO EnseignantInfo VALUES(18,'Joucla','Philippe','luc');
INSERT INTO EnseignantInfo VALUES(19,'Kerbellec','Goulven','luc');
INSERT INTO EnseignantInfo VALUES(20,'PHAM','Minh Tan','luc');


CREATE TABLE EtudiantInfo(
		noEtu NUMBER(4)
			CONSTRAINT pk_EtudiantInfo PRIMARY KEY,
		nomEtu VARCHAR2(40),
		prenom VARCHAR2(30),
		promotion varchar(10),
		groupe VARCHAR2(5)
	)
;

INSERT INTO EtudiantInfo VALUES(1,'Test','Test','INFO1','A');
