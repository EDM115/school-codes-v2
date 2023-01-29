/*
SAE création d'une base de donnée partie 2
conversion diagramme de classe uml vers shéma relationnel


Eleve(numeroINE(1), nom(2), prenom(2), age, estBoursier, voie)

Etablissement(numEtablissement(1), estPrive)

Filiere(nomFiliere(1), estSelective(NN))

Mention([intituleMention(NN), leNomFiliere = @Filiere.nomFiliere](1))

Voeu(numeroVoeu(1), resultat(NN), dateAppel, dateAcceptation, leNumeroINE = @Eleve.numeroINE(NN), leNumEtablissement = @Etablissement.numEtablissement(NN), lIntitule = @Mention.(intituleMention, leNomFiliere)(NN))

Proposition([estProposé = @Mention.(intituleMention, leNomFiliere), propose = @Etablissement.numEtablissement](1))

Contraintes textuelles :

Voeu
dom(resultat) = {Non Classé, Classé Non Appelé, Classé Appelé}

Eleve
dom(voie) = {Générale, Technologique, Professionnelle}
*/
