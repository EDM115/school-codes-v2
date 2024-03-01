-- 3)

ALTER TABLE Agent
DROP COLUMN estDirecteur;

ALTER TABLE Agence
ADD sonDirecteur INT;

ALTER TABLE Agence
ADD CONSTRAINT fk_sonDirecteur FOREIGN KEY (sonDirecteur) REFERENCES Agent(numAgent);

-- 4)

ALTER TABLE Agence
MODIFY sonDirecteur INT NOT NULL;

ALTER TABLE Agence
ADD CONSTRAINT uc_sonDirecteur UNIQUE (sonDirecteur);
