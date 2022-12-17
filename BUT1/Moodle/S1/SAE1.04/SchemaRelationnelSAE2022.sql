-- =================== SAE S104 Partie 2 =========================
-- Schéma relationnel --
/*
Filiere(
	nomFiliere (1),
	estSelective (NN)
)
Mention (
	intituleMention (1),
	uneFiliere=@Filiere(nomFiliere) (1)
)
Eleve(
	numeroINE (1),
	nom (2),
	prenom (2),
	age,
	estBoursier,
	voie
)
Etablissement(
	numEtablissement (1),
	estPrive
)
Voeu(
	numeroVoeu (1),
	resultat (NN),
	dateAppel,
	dateAcceptation,
	unEleve=@Eleve(numeroINE) (NN),
	unEtablissementVoeu=@Etablissement(numEtablissement) (NN),
	(uneMentionVoeu,uneFiliereVoeu) = @Mention(intituleMention,uneFiliere) (NN)
)
Proposition(
	unEtablissementP = @Etablissement(numEtablissement) (1),
	(uneMentionP,uneFiliereP) = @Mention(intituleMention,uneFiliere) (1)
)

-- Contraintes textuelles:
1) DOM(voie) = {'Générale','Technologique','Professionnelle'}
2) DOM(resultat) = {'Non Classé','Classé Non Appelé','Classé Appelé'}

-- Remarque: pour déclarer les attributs de type Boolean, on utilise
le type NUMBER avec une vérification de valeur (0 ou 1)
*/
