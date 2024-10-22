## Objectif de la ressource
Cette ressource donne quelques rappels sur la complexité des algorithmes (surtout temporelle) puis introduit la notion de qualité du logiciel avec comme l'une des voies d'obtention la théorie de la mesure sur le code. Les métriques OO seront en particulier introduites. Enfin, les outils d'audit de code et de mesure de performance sont abordés.

## Séance 1 : Qualité théorique et complexité temporelle des algorithmes
La première séance présente l'organisation de la ressource. Elle se concentre ensuite sur des rappels de cours concernant la notion de complexité des algorithmes (surtout temporelle). Une partie pratique (séance 2) concerne la simulation d'algorithme de complexités temporelles variées.

## Séance 2 : Qualité du logiciel - Normes et métriques de code
Compte-rendu d'analyse de l'outil JArchitect et Jenkins  
À réaliser solo ou en binôme (bien préciser les deux noms de famille sur le compte-rendu)  
Format PDF  
Regarder 2 à 5 fonctionnalités sur chaque outil

## Séances 3 et 4 : Analyse de la qualité de code avec SonarQube
Regarder de près les différentes métriques et comprendre comment elles sont calculées. Quel regard critique avez-vous sur certaines ?  
Documentation disponible sur les règles analysées par SonarQube (par langage, techno, ...) : https://rules.sonarsource.com/  
Comprendre c'est qu'est une dette technique (technical debt) via Sonar : https://www.sonarsource.com/learn/technical-debt/  

Exercice :
1. Analyser un projet que vous avez réalisé dans le passé.
2. Corriger les éventuelles erreurs signalées par SonarQube.  

Remettre les captures d’écran (onglet Overview) avant et après correction.

## Séance 7 : Tests de charge pour mesurer les performances et la disponibilité d'une application
Cette application est constituée de deux microservices (gestion des utilisateurs et gestion des localisations) orchestrés avec Docker Compose (pas d'API Gateway). Chacun des microservices est accompagné de son serveur de base de données.  
Pour démarrer l'application, décompresser l'archive, aller dans le dossier app, puis taper : docker compose up  
L'app démarre deux serveur Web (modules Spring Boot) sur les ports 8081 et 8082.   
L'endpoint qui donne accès à tous les users est disponible ici :  
http://localhost:8081/api/users
