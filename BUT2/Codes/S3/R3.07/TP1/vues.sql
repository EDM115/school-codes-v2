-- a) la vue fournissant tous les éléments dérivables (attribut et association)

CREATE OR REPLACE VIEW VueElementsDerivables AS
    SELECT 
        c.numClient,
        c.nomClient,
        c.prenomClient,
        c.adClient,
        c.dateNaissClient,
        TRUNC(MONTHS_BETWEEN(SYSDATE, c.dateNaissClient)/12) AS ageClient,
        a.numAgence,
        a.telAgence,
        a.adAgence
    FROM Client c
        JOIN Agent ag ON c.sonAgent = ag.numAgent
        JOIN Agence a ON ag.sonAgence = a.numAgence;
SELECT * FROM VueElementsDerivables;

-- b) la vue détectant les éventuels défauts de surjectivité

CREATE OR REPLACE VIEW VueDefautsSurjectivite AS
    SELECT 'Compte sans Appartenance' AS description, cpt.numCompte
    FROM Compte cpt
        LEFT JOIN Appartient a ON cpt.numCompte = a.unCompte
    WHERE a.unClient IS NULL;
SELECT * FROM VueDefautsSurjectivite;
