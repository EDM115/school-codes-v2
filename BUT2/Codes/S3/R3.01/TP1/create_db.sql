-- Script de création de table - SportTrack - Lussandre Lederrey | Nathan Basol - 08/09/2023

-- Création de la table User
CREATE TABLE User(
    adresse_electronique VARCHAR PRIMARY KEY NOT NULL,
    nom VARCHAR(40) NOT NULL,
    prenom VARCHAR(40) NOT NULL,
    date_naissance DATE NOT NULL CHECK (date_naissance BETWEEN '1900-01-01' AND CURRENT_DATE),
    sexe INTEGER NOT NULL CHECK (sexe IN (0, 1)),
    taille INTEGER NOT NULL CHECK (taille BETWEEN 10 AND 250),
    poids INTEGER NOT NULL CHECK (poids BETWEEN 1 AND 500),
    mot_de_passe VARCHAR NOT NULL CHECK (LENGTH(mot_de_passe) > 8 AND
                                         mot_de_passe GLOB '*[A-Z]*' AND 
                                         mot_de_passe GLOB '*[^a-zA-Z0-9]*')
);

-- Création de la table Activity
CREATE TABLE Activity(
    id_activity INTEGER PRIMARY KEY NOT NULL,
    date DATE NOT NULL,
    description VARCHAR,
    duree INTEGER NOT NULL CHECK (duree BETWEEN 0 AND 24),
    distance INTEGER CHECK (distance BETWEEN 0 AND 5000),
    freq_card_min INTEGER CHECK (freq_card_min BETWEEN 10 AND 250),
    freq_card_max INTEGER CHECK (freq_card_max BETWEEN 10 AND 250),
    freq_card_moy INTEGER CHECK (freq_card_moy BETWEEN 10 AND 250),
    un_user VARCHAR REFERENCES User(adresse_electronique)
);

-- Création de la table Data
CREATE TABLE Data(
    id_data INTEGER PRIMARY KEY NOT NULL,
    heure_debut VARCHAR NOT NULL CHECK (heure_debut BETWEEN '00:00' AND '23:59'),
    freq_card INTEGER CHECK (freq_card BETWEEN 10 AND 250),
    altitude INTEGER CHECK (altitude BETWEEN -100 AND 10000),
    latitude DOUBLE CHECK (latitude BETWEEN -90 AND 90),
    longitude DOUBLE CHECK (longitude BETWEEN -180 AND 180),
    une_activite INTEGER REFERENCES Activity(id_activity)
);
