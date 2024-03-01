/*

Schéma relationnel :
--------------------
Agence(numAgence (1), telAgence, adAgence)
Agent(numAgent (1), nomAgent, prenomAgent, salaire, estDirecteur, sonAgence=@Agence.numAgence NN)
Client(numClient (1), nomClient, prenomClient, adClient, dateNaissClient, sonAgent=@Agent.numAgent NN)
Compte(numCompte (1), solde, typeCompte)
Operation(numOperation (1), dateOperation, typeOperation, montant, leClient=@Client.numClient NN, leCompte=@Compte.numCompte NN)
Appartient([unCompte=@Compte.numCompte, unClient=@Client.numClient] (1))


Contraintes n’apparaissant pas dans le SR mais gérées par le LDD :
------------------------------------------------------------------
- Le montant d'une opération est toujours positif
- Le type d'opération est soit RETRAIT soit DEPOT
- Le type de compte considéré est soit COURANT, soit EPARGNE
- Par défaut, la date d'opération est la date du jour courant
- Aucun salaire ne doit être inférieur au Smic (1554 euros brut)

*/

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
		
	telAgence VARCHAR2(50),
	
	adAgence VARCHAR2(50)
	);
	
CREATE TABLE Agent
	(
	numAgent NUMBER
		CONSTRAINT pk_Agent PRIMARY KEY,
		
	nomAgent VARCHAR2(50),
	
	prenomAgent VARCHAR2(50),
	
	salaire NUMBER(6,2)
		CONSTRAINT ck_salaire CHECK (salaire >= 1709.28)
        -- CONSTRAINT ck_variation_salaire CHECK (salaire <= 1.1 * salaire AND salaire >= 0.92 * salaire), (incorrect)
        ,
	    estDirecteur NUMBER(1)
		CONSTRAINT ck_estDirecteur CHECK (estDirecteur = 0 OR estDirecteur = 1),
		
	sonAgence NUMBER
		CONSTRAINT fk_Agent_Agence REFERENCES Agence(numAgence)
		CONSTRAINT nn_sonAgence NOT NULL
	);
	
CREATE TABLE Client
	(
	numClient NUMBER
		CONSTRAINT pk_Client PRIMARY KEY,
		
	nomClient VARCHAR2(50),
	
	prenomClient VARCHAR2(50),
	
	adClient VARCHAR2(50),
	
	dateNaissClient DATE,
	
	sonAgent NUMBER
		CONSTRAINT fk_Client_Agent REFERENCES Agent(numAgent)
		CONSTRAINT nn_sonAgent NOT NULL
	);
	
CREATE TABLE Compte
	(
	numCompte NUMBER
		CONSTRAINT pk_Compte PRIMARY KEY,
	
	solde BINARY_FLOAT,
	
	typeCompte VARCHAR2(50)
		CONSTRAINT ck_typeCompte CHECK (UPPER(typeCompte) = 'COURANT' OR UPPER(typeCompte) = 'EPARGNE')
	);
	
CREATE TABLE Operation
	(
	numOperation NUMBER
		CONSTRAINT pk_Operation PRIMARY KEY,
	
	dateOperation DATE DEFAULT SYSDATE,
	
	typeOperation VARCHAR2(50)
		CONSTRAINT ck_typeOperation CHECK (UPPER(typeOperation) = 'RETRAIT' OR UPPER(typeOperation) = 'DEPOT'),
	
	montant BINARY_FLOAT
		CONSTRAINT ck_montant CHECK (montant >= 0),
	
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