CREATE TABLE Agence (
    numAgence INT PRIMARY KEY,
    telAgence VARCHAR(50),
    adAgence VARCHAR(50)
);

CREATE TABLE Agent (
    numAgent INT PRIMARY KEY,
    nomAgent VARCHAR(50),
    prenomAgent VARCHAR(50),
    salaire DECIMAL(6,2) CHECK (salaire >= 1709.28),
    estDirecteur NUMBER(1,0),
    sonAgence INT REFERENCES Agence(numAgence)
);

CREATE TABLE Compte (
    numCompte INT PRIMARY KEY,
    solde REAL,
    typeCompte VARCHAR(50) CHECK (typeCompte IN ('COURANT', 'EPARGNE'))
);

CREATE TABLE Client (
    numClient INT PRIMARY KEY,
    nomClient VARCHAR(50),
    prenomClient VARCHAR(50),
    adClient VARCHAR(50),
    dateNaissClient DATE,
    sonAgent INT REFERENCES Agent(numAgent)
);

CREATE TABLE Operation (
    numOperation INT PRIMARY KEY,
    dateOperation DATE DEFAULT SYSDATE,
    typeOperation VARCHAR(50) CHECK (typeOperation IN ('RETRAIT', 'DEPOT')),
    montant REAL CHECK (montant > 0),
    leCompte INT REFERENCES Compte(numCompte),
    leClient INT REFERENCES Client(numClient)
);

CREATE TABLE Appartient (
    unClient INT,
    unCompte INT,
    PRIMARY KEY (unClient, unCompte),
    FOREIGN KEY (unClient) REFERENCES Client(numClient),
    FOREIGN KEY (unCompte) REFERENCES Compte(numCompte)
);
