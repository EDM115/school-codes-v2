-------------------------------------------------
-- Tout compte appartient à au moins un client --
-------------------------------------------------

-- Vue

CREATE OR REPLACE VIEW vue_auMoinsUnClient
	(
	compte_sans_client
	)
AS
	SELECT numCompte
	FROM Compte
	MINUS
	SELECT unCompte
	FROM Appartient
;

-- Test

SELECT * FROM vue_auMoinsUnClient;


----------------------------------------------------------------------------
-- Gestion des éléments dérivés : âge du client et agence dont il dépend) --
----------------------------------------------------------------------------

-- Vue

CREATE OR REPLACE VIEW vue_infosClient
	(
	num_Client,
	son_Age,
	son_Agence
	)
AS
	SELECT numClient, ROUND((SYSDATE - dateNaissClient)/365), sonAgence
	FROM Client, Agent
	WHERE sonAgent = numAgent
;

-- Test

SELECT * FROM vue_infosClient;