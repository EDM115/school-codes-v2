DROP TABLE Appartient;
DROP TABLE Operation;
DROP TABLE Compte;
DROP TABLE Client;
DROP TABLE Agent;
DROP TABLE Agence;

CREATE TABLE Agence
	(
	numAgence NUMBER
	CONSTRAINT pk_Agence PRIMARY KEY,
	
	telAgence VARCHAR2(30),
	
	adAgence VARCHAR2(30)
	);
	
CREATE TABLE Agent
	(
	numAgent NUMBER
	CONSTRAINT pk_Agent PRIMARY KEY,
	
	nomAgent VARCHAR2(30),
	
	prenomAgent VARCHAR2(30),
	
	salaire NUMBER
	CONSTRAINT ck_salaire CHECK(salaire>=1480),
	
	directeur NUMBER
	CONSTRAINT ck_directeur CHECK(directeur=0 OR directeur=1),
	
	sonAgence NUMBER
	CONSTRAINT fk_Agent_Agence REFERENCES Agence(numAgence)
	CONSTRAINT nn_sonAgence NOT NULL
	);
	
CREATE TABLE Client
	(
	numClient NUMBER
	CONSTRAINT pk_Client PRIMARY KEY,
	
	nomClient VARCHAR2(30)
	CONSTRAINT nn_nomClient NOT NULL,
	
	prenomClient VARCHAR2(30)
	CONSTRAINT nn_prenomClient NOT NULL,
	
	adClient VARCHAR2(30),
	
	dateNaissanceClient DATE,
	
	sonAgent NUMBER
	CONSTRAINT fk_Client_Agent REFERENCES Agent(numAgent)
	CONSTRAINT nn_sonAgent NOT NULL
	);
	
CREATE TABLE Compte
	(
	numCompte NUMBER
	CONSTRAINT pk_Compte PRIMARY KEY,
	
	solde NUMBER,
	
	typeCompte VARCHAR2(30)
	CONSTRAINT nn_typeCompte NOT NULL
	CONSTRAINT ck_typeCompte CHECK(typeCompte='COURANT' OR typeCompte='EPARGNE')
	);
	
CREATE TABLE Operation
	(
	numOperation NUMBER
	CONSTRAINT pk_Operation PRIMARY KEY,
	
	dateOperation DATE DEFAULT SYSDATE,
	
	typeOperation VARCHAR2(30)
	CONSTRAINT ck_typeOperation CHECK(typeOperation='RETRAIT' OR typeOperation='DEPOT'),
	
	montant NUMBER
	CONSTRAINT ck_montant CHECK(montant>=0),
	
	leClient NUMBER
	CONSTRAINT fk_Operation_Client REFERENCES Client(numClient)
	CONSTRAINT nn_leClient NOT NULL,
	
	leCompte NUMBER
	CONSTRAINT fk_Operation_Compte REFERENCES Compte(numCompte)
	CONSTRAINT nn_leCompte NOT NULL
	);
	
CREATE TABLE Appartient
	(
	unCompte NUMBER
	CONSTRAINT fk_Appartient_Compte REFERENCES Compte(numCompte),
	
	unClient NUMBER
	CONSTRAINT fk_Appartient_Client REFERENCES Client(numClient),
	
	CONSTRAINT pk_Appartient PRIMARY KEY (unCompte,unClient)
	);