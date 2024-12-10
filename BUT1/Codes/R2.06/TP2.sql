-- Exercice 2 --

-- Q1 : Pour chaque stagiaire (id), afficher le nom et le département de l'entreprise --

SELECT etudStagiaire, nomEntreprise, depEntreprise
FROM Entreprise, Stagiaire
WHERE Entreprise.idEntreprise = Stagiaire.entrepriseStage
ORDER BY etudStagiaire;

-- 58 Résultats : --


-- Q2 : Pour chaque stagiaire (nom et prénom), afficher le nom et le département de l'entreprise --

SELECT nomEtud, prenomEtud, nomEntreprise, depEntreprise
FROM Etudiant, Entreprise, Stagiaire
WHERE Etudiant.idEtud = Stagiaire.etudStagiaire
AND Entreprise.idEntreprise = Stagiaire.entrepriseStage
ORDER BY nomEtud, prenomEtud;

-- 58 Résultats : --						...


-- Q3 : Pour chaque étudiant (id), afficher son entreprise (nom) si elle existe et rien sinon --

SELECT DISTINCT idEtud, UPPER(nomEntreprise)
FROM Etudiant
	LEFT JOIN Stagiaire ON Etudiant.idEtud = Stagiaire.etudStagiaire
		LEFT JOIN Apprenti ON Etudiant.idEtud = Apprenti.etudApp
			LEFT JOIN Entreprise ON Stagiaire.entrepriseStage = Entreprise.idEntreprise
				OR Apprenti.entrepriseApp = Entreprise.idEntreprise
				ORDER BY idEtud;

-- 107 Résultats : --


-- Q4 : Afficher les étudiants (nom et prénom) ayant réalisé un stage dans un département différent de celui du lycée --

SELECT nomEtud, prenomEtud
FROM Etudiant, Entreprise, Stagiaire
WHERE Etudiant.idEtud = Stagiaire.etudStagiaire
AND Entreprise.idEntreprise = Stagiaire.entrepriseStage
AND depLycee <> depEntreprise
ORDER BY nomEtud, prenomEtud;

-- 31 Résultats : --


-- Q5 : Afficher les apprentis (nom et prénom) suivis par Muriel Mannevy --

SELECT nomEtud, prenomEtud
FROM Etudiant, Apprenti
WHERE Etudiant.idEtud = Apprenti.etudApp
AND tuteurApp LIKE 'MM'
ORDER BY nomEtud, prenomEtud;

-- 7 Résultats --


-- Q6 : Pour chaque tuteur d'apprenti (nom), afficher les apprentis (nom et prénom) --

SELECT nomEns, nomEtud, prenomEtud
FROM Enseignant, Etudiant, Apprenti
WHERE Enseignant.idEns = Apprenti.tuteurApp
AND Etudiant.idEtud = Apprenti.etudApp
ORDER BY nomEtud, prenomEtud;

-- 24 Résultats : --


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
