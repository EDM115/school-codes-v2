# TP - Construire son premier test fonctionnel automatisé

Dans ce TP, on cherche à vérifier que l'application graphique respecte les besoins du clients. On va donc enregistrer des scénarios utilisateur qui permettront de détecter les erreurs mais aussi de valider les fonctionnalités corrects (elles doivent le rester).

Pour corriger les bugs, les développeurs seront ensuite amené à retravailler sur l'application. Pour les testeurs, l'automate permettra de rapidement revalider l'application sans intervention humaine. 

Aujourd'hui, nous utiliserons l'outil Selenium Ide qui permet de réaliser d'enregistrer des séquences d'actions (clic, saisie clavier), d'y ajouter des points de contrôles (présence d'un message d'erreur, ajout d'un utilisateur dans un tableau) et de les rejouer à volonté afin d'assurer la non regression de l'application sous test.

![Copie d'écran de l'application RhTest](img/screenshot.png)

## Pré requis

 1. Démarrer l'application RhTest
 2. Disposer du référentiel d'exigences
 3. Installer le plugin Chrome ou Firefox Selenium IDE depuis la page https://www.selenium.dev/selenium-ide/

 Une fois Selenium Ide  installé, l'outil est disponible dans les barres d'outils de  Chrome ou Firefox sur l'icone 

 ![Lancer Selenium Ide](img/selenium-ide128.png)

## Présentation de Selenium Ide

Selenium Ide est un outil simple et facile d’utilisation qui permet de capturer un scénario utilisateur puis de le rejouer.
Il permet de comprendre rapidement l'intérêt des tests fonctionnels automatisés.

Une fois enregistrés, ces tests peuvent être sauvegardés au format .side par défaut. D'autres outils plus évolués permettent de prendre en charge des mangages Java, PHP, Javascript, etc. Les tests peuvent aussi être joués sur différents navigateurs Chrome, Firefox, ou sur les mobiles. Les tests pourront d'ailleurs être exécuté en mode ligne de commande et inséré sans une CI (https://www.seleniumhq.org/selenium-ideimg/en/introduction/command-line-runner/)

Selenium Ide respecte le standard Selenium. l existe différents projets qui utilisent la technologie Selenium : 
 - SideeX qui permet de comprendre les tests automatisés, 
 - [http://www.seleniumhq.org/projects/remote-control/](Selenium RC) qui permet de piloter différents navigateurs,
 - [http://www.protractortest.org](Protractor) pour tester les applications AngularJS,
 - et même des applications en ligne, comme [https://www.browserstack.com](BrowserStack), qui permettent aux développeurs web d'accéder à des fermes de navigateurs.


### Interface Graphique de Selenium Ide

![Installation Selenium IDE](img/Selenium-ExempleJeu.png)

Une fois démarré, l'interface Selenium IDE se décompose en plusieurs sections

#### Barre d'outils

![Actions Selenium IDE](img/Selenium-Outils.png)

La barre d'outils contient des boutons qui permettent de contrôler l'exécution des cas de tests et notamment la rapidité d'exécution (il es parfois nécessaire de jouer sur la rapidité d'exécution).

#### Volets Cas de Test

![Actions Selenium](img/Selenium_CasDeTest.png)

Dans ce volet, on organisera les cas de tests en fonction du référentiel d'exigences

#### Volets Détail du Cas de Test

![Detail Selenium](img/Selenium_DetailCasDeTest.png)

Ce volet permet de détailler chacune des actions de test et des points de contrôle.  En éxécution il permet également de suivre chacune des étapes de test et leur résultat.

#### Volets Résultats et log

![Detail Selenium](img/Selenium-Result.png)

Ce volet permet de consolider le nombre de test passant ou en échec. Le détail de l'éxécution se trouve quanà un lui dans un journal d'exécution en bas de la fenêtre SideeX.

## Travail à réaliser

 - Automatiser les exigences critiques d'abord,
 - Nommer les cas de test en fonction des codes d'exigences
 - Suivre le réferentiel d'exigence pour : 
   - enregistrer la séquence utilisateur
   - ajouter un point de contrôle
 - Rejouer le test de façon unitaire pour vérifier qu'il retourne le résultat attendu (OK si fonctionnalité correctement implémentée, KO si différence avec réferentiel exigence)  

Une fois que l'ensemble des exigences sont enregistrées, rejouer la séquence complète.



## Pour aller plus loin

En plus des commandes disponibles via l'enregistrement, Selenium peut utiliser les méthodes suivantes :

 - `open` : ouvre une page à l'aide d'une URL.
 - `click` : effectue une opération de clic
 - 'wait for element present', attent la présence de l'élément sur la page.
 - `verify` : Effectue une vérification (non bloquante)  sur la page.
 - `Assert` : Effectue une vérification et arrete le scénario en cas d'échec.
 

D'autres commandes sont également disponibles sur le site:  https://www.selenium.dev/selenium-ideimg/en/api/commands. 

A noter que dans le navigateur vous trouverez également les commandes contextuelles disponibles:
![Assertion et vérification contextuelles](img/Selenium-assert.png)


Ceci permet de faciliter la mise en place de point de contrôle. Attention toutefois à cette méthode qui semble rapide et pourtant peut engendrer des problème de qualité. En effet l'enregistrement des proints de controles présume que le logiciel mis sous test et en bon état de fonctionnement, ce qui n'est pas le cas dans notre cas de figure...
