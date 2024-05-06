# TP: Construire le référentiel d'exigence


L’objectif du TP est de comprendre la méthodologie de définition des besoins pour créer et tester une application. Le Tp permet de créer un référentiel d’exigences puis les cas de tests associés.
Le livrable de ce TP est un référentiel de test présentant toutes les exigences et les cas de tests. Un modèle vide est disponible [referentiel.xlsx](exemple/Referentiel.xlsx).
Vous pouvez choisir un autre format mais celui ci a le mérite d'être simple.


## Méthodologie :

L’application demandée par le client consiste à gérer les ressources humaines d’une entreprise.
Sur la base du cahier des charges ci-dessous, vous devrez établir le référentiel d’exigences ainsi que les cas de tests. Ce référentiel est le document unique qui sert à lister st suivre l’ensemble des tests qui seront réalisés.


Chaque fonctionnalité devra faire l’objet d’une exigence qui aura ses cas de tests :

L’exigence devra présenter les éléments suivant :
-	Code exigence (tag unique qui permettra de faire référence dans tout type de document associé au projet)
-	Exigence (présentation de ce que doit faire la fonctionnalité)
-	Criticité (niveau de risque si cette fonctionnalité venait à manquer - important/Moyen/Faible)
-	Catégorie (norme iso9126 : extrait suivant : Aptitude fonctionnelle / performance/
interopérabilité / sécurité)

Chaque exigence porte ses cas de tests en présentant les éléments suivants :
-	N cas de tests (tag unique qui permettra de faire référence dans tout type de document associé au projet)
-	Cas de test : libellé court qui explique pourquoi ce cas est différent des autres
-	Action/Donnée (Séquence permettant de présenter l’enchainement des interactions et le jeu de donnée associé)
-	Point de contrôle (résultat attendu, quel est l’élément à contrôler permettant de justifier le résultat de tests)

Quelques recommandations pour construire votre référentiel :
1.	Pour faciliter la construction de ce référentiel, il est souvent plus simple de démarrer par les cas de test puis en regroupant certains cas de test, vous retrouverez les quelques exigences. 
2.	Il faut toujours démarrer par le cas de test nominal, c’est une bonne pratique également lorsque vous implémenterez ces cas dans les outils de test. En effet si votre test aux limites par exemple est en erreur, vous ne saurez pas si la fonctionnalité de base est en succès et vous donnerez dans ce cas une information erronée au développeur en charge de résoudre l’anomalie.
3.	Appuyez-vous uniquement sur le cahier des charges pour construire le cas de test, en effet vous pourriez être tenté de construire votre référentiel à partir de l’application… sauf que l’application contient des erreurs et des écarts par rapport à l’attendu qui faudra d’ailleurs mettre en évidence dans vos tests automatisés.
4.	Vous devez pensez aux tests nominaux, aux limites et les cas de test amenant une situation en erreur. N’oubliez pas de préciser différents cas de figure validant la saisie de l’utilisateur dans les champs.
5.	Pour construire ce référentiel d’exigence vous vous appuierez sur un jeu de donnée qui permet de mettre en évidence les différents cas de test pour valider et mettre en évidence les défaillances de l’application. 



## Cahier des charges
Le client demande une application de gestion des ressources humaines permettant de saisir les salariés de l’entreprise avec 2 informations essentielles, à savoir le salaire et le nombre de point de la personne.
Le nombre de point est un entier sélectionné entre -10 et +10. L’application doit être accessible depuis un navigateur sur le réseau de l’entreprise. 5 personnes (agents) aux ressources humaines accèdent à cette information par l’url qui leur a été fournie. Il n’est pas nécessaire de gérer les droits sur cette application.
Lorsque l’utilisateur initialise son application, il visualise l’ensemble des salariés avec les informations saisies, dans l’ordre de modification. La dernière modification apportée est en haut du tableau. Il est à noter que l’application doit être performante et répondre quel que soit le nombre d’utilisateurs et l’action en moins de 3s. Il y aura maximum 30 personnes à utiliser l’application.
Chaque salarié est identifié avec un matricule unique, saisi par l’agent au ressources humaines.
Un matricule ne peut jamais être vide, mais l’agent peut enregistrer un matricule sans même fournir aucune autre information.
Les salaires sont des entiers. A chaque fois qu’un agent effectue une action (Ajout, suppression, ou modification à d’un salarié un message d’information s’affiche en vert en cas de succès et en rouge en cas d’erreur, précisant d’ailleurs l’origine de l’erreur.

Pour modifier un salarié, l’agent commencera à le rechercher. Les recherches se font par le nom uniquement.
L’agent peut supprimer sans acquittement un salarié à partir de la liste des salariés disponible dans le tableau. 




