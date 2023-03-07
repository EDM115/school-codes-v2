-- Exercice 2 --

-- Q1 : Pour chaque stagiaire (id), afficher le nom et le département de l'entreprise --

SELECT etudStagiaire, nomEntreprise, depEntreprise
FROM Entreprise, Stagiaire
WHERE Entreprise.idEntreprise = Stagiaire.entrepriseStage
ORDER BY etudStagiaire;

-- 58 Résultats : --

ETUDSTAGIAIRE,	NOMENTREPRISE,			DEPENTREPRISE
21702527,		SCM VIAUD-FORMAGNE,		56
21802862,		CGI,					56
21900078,		ACCENTURE,				75
21900144,		IRISA, EQUIPE OBELIX,	56
21900169,		EXOTIC ANIMALS,			56
...				...						...


-- Q2 : Pour chaque stagiaire (nom et prénom), afficher le nom et le département de l'entreprise --

SELECT nomEtud, prenomEtud, nomEntreprise, depEntreprise
FROM Etudiant, Entreprise, Stagiaire
WHERE Etudiant.idEtud = Stagiaire.etudStagiaire
AND Entreprise.idEntreprise = Stagiaire.entrepriseStage
ORDER BY nomEtud, prenomEtud;

-- 58 Résultats : --

NOMETUD,	PRENOMETUD,	NOMENTREPRISE,					DEPENTREPRISE
AUVRAY,		ALEXANDRE,	INRAE ET FN3PT,					75
BEAUCLAIR,	ADRIEN,		NIRYO,							59
BERNIER,	ALLAN,		ADAPEI56,						56
BLIVET,		LAURELINE,	CNRS,							35
BOIVENT,	PIERRE,		DEPARTEMENT DILLE-ET-VILAINE,	35
...			...			...								...


-- Q3 : Pour chaque étudiant (id), afficher son entreprise (nom) si elle existe et rien sinon --

SELECT DISTINCT idEtud, UPPER(nomEntreprise)
FROM Etudiant
	LEFT JOIN Stagiaire ON Etudiant.idEtud = Stagiaire.etudStagiaire
		LEFT JOIN Apprenti ON Etudiant.idEtud = Apprenti.etudApp
			LEFT JOIN Entreprise ON Stagiaire.entrepriseStage = Entreprise.idEntreprise
				OR Apprenti.entrepriseApp = Entreprise.idEntreprise
				ORDER BY idEtud;

-- 107 Résultats : --

IDETUD,		NOMENTREPRISE
21702527,	SCM VIAUD-FORMAGNE
21802862,	CGI
21807519,
21900078,	ACCENTURE
21900144,	IRISA, EQUIPE OBELIX
...			...


-- Q4 : Afficher les étudiants (nom et prénom) ayant réalisé un stage dans un département différent de celui du lycée --

SELECT nomEtud, prenomEtud
FROM Etudiant, Entreprise, Stagiaire
WHERE Etudiant.idEtud = Stagiaire.etudStagiaire
AND Entreprise.idEntreprise = Stagiaire.entrepriseStage
AND depLycee <> depEntreprise
ORDER BY nomEtud, prenomEtud;

-- 31 Résultats : --

NOMETUD,	PRENOMETUD
AUVRAY,		ALEXANDRE
BEAUCLAIR,	ADRIEN
BERNIER,	ALLAN
BONNET,		BENJAMIN
BRAUD,		ANTOINE
...			...


-- Q5 : Afficher les apprentis (nom et prénom) suivis par Muriel Mannevy --

SELECT nomEtud, prenomEtud
FROM Etudiant, Apprenti
WHERE Etudiant.idEtud = Apprenti.etudApp
AND tuteurApp LIKE 'MM'
ORDER BY nomEtud, prenomEtud;

-- 7 Résultats --

NOMETUD,	PRENOMETUD
KOENIGS,	THEO
LE BRETON,	DAN
LE PORS,	YANIS
MADELAINE,	DYLAN
PLOQUIN,	NATHAN
ROUILLIER,	JULIEN
TIRLEMONT,	KIERIAN


-- Q6 : Pour chaque tuteur d'apprenti (nom), afficher les apprentis (nom et prénom) --

SELECT nomEns, nomEtud, prenomEtud
FROM Enseignant, Etudiant, Apprenti
WHERE Enseignant.idEns = Apprenti.tuteurApp
AND Etudiant.idEtud = Apprenti.etudApp
ORDER BY nomEtud, prenomEtud;

-- 24 Résultats : --

NOMENS,		NOMETUD,		PRENOMETUD
Tuffigo,	ADAM,			ANTOINE
Baudont,	BREIT HOARAU,	EMELINE
Lefevre,	CARN,			YOHAN
Lefevre,	CASTELLA,		MATEO
Baudont,	CLOAREC,		THOMAS
...			...				...


-- Q7 : Afficher les tuteurs (nom) ayant suivi un apprenti en dehors de la Bretagne --

SELECT DISTINCT(nomEns)
FROM Enseignant, Apprenti, Entreprise
WHERE Enseignant.idEns = Apprenti.tuteurApp
AND Entreprise.idEntreprise = Apprenti.entrepriseApp
AND depEntreprise NOT IN (29, 22, 35, 56)
ORDER BY nomEns;

-- 2 Résultats : --

NOMENS
Baudont
Mannevy


-- Q8 : Pour chaque enseignant (nom), afficher les apprentis (nom et prénom) en Bretagne et le département, triés dans l'ordre décroissant du département, s'ils existent et rien sinon --

SELECT DISTINCT UPPER(nomEns), UPPER(nomEtud), UPPER(prenomEtud), depEntreprise
FROM Enseignant 
	LEFT JOIN Apprenti ON idEns = tuteurApp
		LEFT JOIN Entreprise ON idEntreprise = entrepriseApp
			LEFT JOIN Etudiant ON idEtud = etudApp
				WHERE depEntreprise IN (22,29,35,56)
				OR depEntreprise IS NULL
				ORDER BY UPPER(nomEns), depEntreprise DESC;

-- 35 Résultats : --

UPPER(NOMENS),	UPPER(NOMETUD),	UPPER(PRENOMETUD),	DEPENTREPRISE
ADAM,
BAUDONT,		BREIT HOARAU,	EMELINE,			56
BAUDONT,		GARIN-HAMELINE,	GILDAS,				56
BAUDONT,		SUARD,			MAEL,				56
BAUDONT,		CLOAREC,		THOMAS,				56
...				...				...					...


-- Q9 : Afficher les étudiants (nom et prénom) qui ont travaillé dans le Morbihan et qui ont poursuivi leurs études à l'ENSIBS --

SELECT UPPER(nomEtud), UPPER(prenomEtud)
FROM (
	SELECT DISTINCT nomEtud, prenomEtud
	FROM Etudiant, Stagiaire, Entreprise
	WHERE etudStagiaire = idEtud
	AND entrepriseStage = idEntreprise
	AND depEntreprise = 56
	AND UPPER(poursuiteEtudes) LIKE '%ENSIBS%'
	UNION
	SELECT DISTINCT nomEtud, prenomEtud
	FROM Etudiant, Apprenti, Entreprise
	WHERE etudApp = idEtud
	AND entrepriseApp = idEntreprise
	AND depEntreprise = 56
	AND UPPER(poursuiteEtudes) LIKE '%ENSIBS%'
)
ORDER BY UPPER(nomEtud), UPPER(prenomEtud);

-- 5 Résultats : --

UPPER(NOMETUD),	UPPER(PRENOMETUD)
GARIN-HAMELINE,	GILDAS
GUENNEC,		PAUL
LE CHENADEC,	SARAH
LE GARREC,		VINCENT
PEDRON,			MATISSE


-- Q10 : Afficher les enseignants (nom) à la fois tuteur de groupe en Info1 et tuteur d'apprenti --

SELECT DISTINCT(nomEns)
FROM Enseignant, Apprenti, GroupeInfo1
WHERE Enseignant.idEns = Apprenti.tuteurApp
AND Enseignant.idEns = GroupeInfo1.tuteurGroupe;

-- 1 Résultat : --

NOMENS
Tuffigo


-- Q11 : Afficher le nombre d'étudiants venant d'un lycée breton --

SELECT COUNT(*)
FROM Etudiant
WHERE depLycee IN (29, 22, 35, 56);

-- 1 Résultat : --

COUNT(*)
81


-- Q12 : Afficher le nombre de poursuites d'études renseignées --

SELECT COUNT(*)
FROM Etudiant
WHERE poursuiteEtudes IS NOT NULL;

-- 1 Résultat : --

COUNT(*)
74


-- Q13 : Afficher le nombre de tuteurs pour l'ensemble des apprentis --

SELECT COUNT(DISTINCT tuteurApp)
FROM Apprenti;

-- 1 Résultat : --

COUNT(DISTINCTTUTEURAPP)
7


-- Q14 : Afficher le nombre d'apprentis suivis par Pascal Baudont dans le Morbihan --

SELECT COUNT(*)
FROM Apprenti, Entreprise
WHERE tuteurApp LIKE 'PB'
AND Entreprise.idEntreprise = Apprenti.entrepriseApp
AND depEntreprise = 56;

-- 1 Résultat : --

COUNT(*)
4


-- Q15 : Afficher la proportion d'étudiants passés en deuxième année --

SELECT COUNT(parcoursInfo2) / COUNT(leGroupeInfo1)
FROM Etudiant;

-- 1 Résultat : --

COUNT(PARCOURSINFO2)/COUNT(LEGROUPEINFO1)
0.7663551401869158878504672897196261682243


-- Q16 : Afficher la proportion d'enseignants ayant suivi un apprenti --

SELECT COUNT(DISTINCT(tuteurApp)) / COUNT(DISTINCT(idEns))
FROM Enseignant, Apprenti;

-- 1 Résultat : --

COUNT(DISTINCT(TUTEURAPP))/COUNT(DISTINCT(IDENS))
0.35
