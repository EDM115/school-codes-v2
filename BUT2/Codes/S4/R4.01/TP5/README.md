# R4.01 - TP Microservices

### Ce que j'ai réussi à développer et résumé de l'application

Cette application a été dévelopée en 3 parties :
- `Models` : dévelopée en Java, représente les activités et les utilisateurs
- `Controllers` : développée en Java, permet de gérer les activités et les utilisateurs ainsi que de proposer une API
- `Webapp` : dévelopée en Javascript (Vue.js), permet d'afficher les activités et les utilisateurs, ainsi que de se créer un compte et de se connecter

**Models** :
- Nom de package : `fr.ubs.sporttrack.models`
- Classes :
  - `activity.Activity` : représente une activité
  - `activity.Data` : représente les données d'une activité
  - `activity.JSONFileReader` : permet de lire le fichier JSON contenant les activités
  - `activity.JSONFileWriter` : permet d'écrire dans le fichier JSON contenant les activités
  - `user.User` : représente un utilisateur
  - `user.JSONFileReaderUser` : permet de lire le fichier JSON contenant les utilisateurs
  - `user.JSONFileWriterUser` : permet d'écrire dans le fichier JSON contenant les utilisateurs
- Ressources :
  - `activity_data.json` : fichier JSON contenant les activités
  - `user_data.json` : fichier JSON contenant les utilisateurs
- `pom.xml` : fichier Maven, dépendances vers `org.json.json` et `jakarta.validation.jakarta.validation-api`

Cette partie de l'app est capable de lire et d'écrire dans les fichiers JSON contenant les activités et les utilisateurs. Elle modélise les utilisateurs par un trio `name`, `email` et `password` et les activités par une `description`, une `date`, une `distance`, une `freq_min`, une `freq_max` et un tableau de `data`, qui contiennent les données de l'activité (`time`, `cardio_frequency`, `latitude`, `longitude` et `altitude`). Les activités et les utilisateurs possèdent des classes différentes pour la lecture et l'écriture dans les fichiers JSON car ils ont une structure différente.

**Controllers** :
- Nom de package : `fr.ubs.sporttrack`
- Classes :
  - `SportTrackApp` : classe principale, permet de lancer l'application
  - `config.WebConfig` : permet d'accepter les requêtes CORS
  - `controller.ActivityController` : permet de gérer les activités
  - `controller.UserController` : permet de gérer les utilisateurs
- Ressources :
  - `activity_data.json` : fichier JSON contenant les activités
  - `user_data.json` : fichier JSON contenant les utilisateurs
- `pom.xml` : fichier Maven, dépendances vers `junit.junit`, `org.springframework.boot.spring-boot-starter-web`, `fr.ubs.sporttrack.models`, `org.springdoc.springdoc-openapi-starter-webmvc-ui`, `io.swagger.core.v3.swagger-annotations` et `org.hibernate.validator.hibernate-validator`

Cette partie de l'app est capable de gérer les activités et les utilisateurs. Elle propose une API REST pour les activités et les utilisateurs. Elle utilise les classes du package `models` pour lire et écrire dans les fichiers JSON contenant les activités et les utilisateurs. Elle utilise également les annotations de validation d'Hibernate pour valider les données des activités et des utilisateurs et utilise Swagger pour documenter l'API (http://localhost:8080/openapi/swagger-ui/index.html).

> Routes possibles (`http://localhost:8080`) :
> - `GET /activities` : Liste toutes les activités
> - `GET /activities/{keyword}` : Filtre les activités par un mot-clé dans leur description
> - `POST /activities` : Crée une nouvelle activité. Accepte et produit un JSON décrivant une activité
> - `GET /users` : Liste tous les utilisateurs
> - `GET /users/{email}` : Récupère un utilisateur par son email
> - `POST /users/register` : Crée un nouvel utilisateur. Accepte et produit un JSON décrivant un utilisateur
> - `POST /users/login` : Connecte un utilisateur. Accepte et produit un JSON décrivant un utilisateur

**Webapp** :
- `src`
  - `assets` : contient l'image de l'app et le CSS
  - `components` : contient les composants
    - `Error` : permet d'afficher une erreur (mauvaise requête vers l'API)
    - `NavBar` : permet d'afficher la barre de navigation en haut de chaque page
  - `pages` : contient les pages
    - `Activity` : permet d'afficher les activités
    - `Home` : permet d'afficher la page d'accueil
    - `Login` : permet de se connecter
    - `Register` : permet de se créer un compte
    - `Users` : permet d'afficher les utilisateurs
  - `router/router.js` : contient les routes Vue.js
  - `stores/user.js` : contient le store pour gérer la connexion des utilisateurs
  - `App.vue` : composant principal
  - `main.js` : crée l'appli
- `index.html` : fichier HTML de base pour charger l'app Vue.js
- `package.json` : fichier NPM, dépendances vers `vue`, `vue-router`, `vuetify`, `cookiejs`, `vite`, `ofetch` et `pinia`
- `vite.config.js` : configuration de Vite

Cette partie de l'app est capable d'afficher les activités et les utilisateurs, de se créer un compte et de se connecter. Elle utilise Vue.js pour afficher les pages et les composants, Vuetify pour le design,Vue Router pour les routes, Pinia pour le store, ofetch pour les requêtes vers l'API et CookieJS pour les cookies.  
Points intéressants :
- L'intégralité des changements de page se fait en soft reload, sans rechargement de la page
- Le bouton en haut à droite permet de vérifier à tout moment si l'on est connecté, et permets également de se connecter/déconnecter
- Les pages `Activity` et `Users` ne sont pas accessibles si l'on n'est pas connecté
- L'utilisateur est directement connecté après s'être créé un compte
- L'app prends correctement en charge les erreurs de l'API (essayez d'arrêter l'API après vous être connecté pour voir l'erreur)
- La session utilisateur est stockée dans des cookies et est récupérée de manière dynamique dans un store (affichage : `http://localhost:3030/__devtools__/` -> Pinia -> Store -> users -> state -> user)
- Le logo de l'app dans la barre de navigation permet de revenir à la page d'accueil

> Routes possibles (`http://localhost:3030`) :
> - `/` : Page d'accueil
> - `/activities` : Page des activités
> - `/users` : Page des utilisateurs
> - `/login` : Page de connexion
> - `/register` : Page de création de compte

### Installation/Utilisation

```bash
cd Models
mvn clean install
cd ../Controllers
mvn clean install
cd ../Webapp
npm install
```

```bash
cd ../Controllers
mvn spring-boot:run
cd ../Webapp
npm run dev
```

### Tests

- Récupération des activités
```bash
curl -LX GET http://localhost:8080/activities
```
> [{"date":"03/01/2023","description":"IUT  RU","distance":770,"freqMin":98,"freqMax":103,"data":[{"time":"13:00:00","cardioFrequency":99,"latitude":47.644794,"longitude":-2.776605,"altitude":18.0},{"time":"13:00:05","cardioFrequency":100,"latitude":47.64687,"longitude":-2.778911,"altitude":18.0},{"time":"13:00:10","cardioFrequency":102,"latitude":47.6462,"longitude":-2.78022,"altitude":18.0}]},{"date":"2024-04-04","description":"Morning Run in Paris","distance":5000,"freqMin":110,"freqMax":160,"data":[{"time":"12:00:00","cardioFrequency":120,"latitude":48.8566,"longitude":2.3522,"altitude":35.0}]},{"date":"2024-04-05","description":"Morning Run in Marseille","distance":5000,"freqMin":110,"freqMax":160,"data":[{"time":"12:00:00","cardioFrequency":120,"latitude":48.8566,"longitude":2.3522,"altitude":35.0}]}]
- Filtrage des activités
```bash
curl -LX GET http://localhost:8080/activities/paris
```
> [{"date":"2024-04-04","description":"Morning Run in Paris","distance":5000,"freqMin":110,"freqMax":160,"data":[{"time":"12:00:00","cardioFrequency":120,"latitude":48.8566,"longitude":2.3522,"altitude":35.0}]}]
- Création d'une activité
```bash
curl -LX POST http://localhost:8080/activities/ -H 'Content-Type: application/json' -d '{"date":"2024-04-06","description":"Morning Run in Lyon","distance":5000,"freqMin":110,"freqMax":160,"data":[{"time":"12:00:00","cardioFrequency":120,"latitude":48.8566,"longitude":2.3522,"altitude":35.0}]}'
```
> {"date":"2024-04-06","description":"Morning Run in Lyon","distance":5000,"freqMin":110,"freqMax":160,"data":[{"time":"12:00:00","cardioFrequency":120,"latitude":48.8566,"longitude":2.3522,"altitude":35.0}]}
- Récupération des utilisateurs
```bash
curl -LX GET http://localhost:8080/users
```
> [{"name":"Admin","email":"admin@test.com","password":"password"},{"name":"User","email":"a@b.c","password":"azertyuiop"}]
> - Filtrage des utilisateurs
```bash
curl -LX GET http://localhost:8080/users/a@b.c
```
> {"name":"User","email":"a@b.c","password":"azertyuiop"}
- Création d'un utilisateur
```bash
curl -LX POST http://localhost:8080/users/register -H 'Content-Type: application/json' -d '{"name":"Test","email":"x@y.z","password":"spring_boot"}'
```
> {"name":"Test","email":"x@y.z","password":"spring_boot"}
- Connexion d'un utilisateur
```bash
curl -LX POST http://localhost:8080/users/login -H 'Content-Type: application/json' -d '{"email":"x@y.z","password":"spring_boot"}'
```
> {"name":"Test","email":"x@y.z","password":"spring_boot"}
