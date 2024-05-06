# TP: Initiation au TDD

L’objectif du TP est de découvrir la programmation en mode TDD (Test Driven Development).

Le développement piloté par les tests est une méthode itérative de développement qui consiste à écrire les tests des fonctionnalités avant de les coder.

Les étapes sont les suivantes :

- Écriture du test
- Le test échoue (🔴)
- Écriture de la fonctionnalité
- Le test passe (🟢)
- Refactorisation du code

Les avantages de cette méthode sont nombreux :

- S'assurer que le code fonctionne correctement en testant individuellement chaque fonctionnalité
- Détecter les bugs au plus tôt dans le processus de développement
- S'assurer que le code correspond aux attendus fonctionnels
- Maintenir une bonne qualité de code
- Faciliter les évolutions du code

## Pré-requis

Depuis le terminal, aller dans le répertoire `apps/rhapi` puis lancer la commande suivante :

```sh
cd apps/rhapi

npm i
```

Puis lancer la suite de test existante :

```sh
npm run test
```

Le framework de test `Vitest` va se mettre en mode watcher.

Toute modification de code ou ajout de test relancera la suite de tests.

## Exercices

**Apporter les améliorations suivantes à l'API en appliquant les principes de la programmation TDD.**

Les tests seront à écrire dans le fichier `apps/rhapi/src/employee/employee.service.spec.ts`.

### Exo 1 : Contrôler la présence du nom

La méthode `getByName` de la classe `EmployeeService` ne vérifie pas le nom passé en paramètre.

Ajouter un contrôle pour s'assurer que le nom envoyé n'est pas vide.

Si le paramètre envoyé est vide, retourner le message suivant :

> Le nom est obligatoire

**Tips : vous pouvez utiliser la méthode `assert` de Node.JS pour ce genre de contrôle.**

```ts
function foo(arg: string) {
  assert(arg, 'arg is missing'); // Throw une exception si arg est absent
}
```

### Exo 2 : Ajouter un contrôle sur le nom et le prénom

La classe `EmployeeService` ne contrôle pas que les champs nom et prénom ne contiennent pas de caractères numériques.

Ajouter ce contrôle sur la création d'employés (méthode `add`).

**Tips : exemple de méthode pour vérifier la présence de caractères numériques dans une chaîne :**

```ts
function containsNumbers(str: string) {
  return /\d/.test(str);
}
```

Si le nom de l'employée contient un ou plusieurs caractères numériques, retourner le message suivant :

> Le nom doit contenir uniquement des caractères alphabétiques

Si le prénom de l'employée contient un ou plusieurs caractères numériques, retourner le message suivant :

> Le prénom doit contenir uniquement des caractères alphabétiques

Appliquer le même contrôle sur la modification d'employés (méthode `update`).

### Exo 3 : Ajouter la récupération d'un employé par matricule

La classe `EmployeeService` possède actuellement une méthode `getByName` utilisée par la recherche côté UI.

Cette route retourne volontairement un tableau pour retourner tous les employés avec le nom recherché (ex: tous les DUPOND).

Implémenter une nouvelle méthode `getById` permettant de récupérer un usager en particulier à partir de son matricule.

Cette méthode devra prendre un id en entrée et retourner un employé en sortie.

Si l'employé n'est pas trouvé, retourner le message suivant :

> L'employé {id} n'a pas été trouvé

Si la méthode est appelée sans id, retourner le message suivant :

> Le matricule est obligatoire

### Exo bonus

Dans l'application rhfront `apps/rhfront`, ajouter un autre champ de recherche par matricule qui appellera notre nouvelle méthode `getById`.

Pour cela toujours en appliquant le TDD :

- Mettre en place la route d'API dans la couche controller
- Compléter la documentation swagger `app/rhapi/resources/swagger.json`

Les tests de la couche controller sont des tests d'intégration et utilise la librairie [supertest](https://github.com/ladjs/supertest) pour monter l'API et pouvoir tester les routes de notre API.

Une fois l'API en place :

- Ajouter un nouveau champ de recherche dans l'application rhfront (`apps/rhfront/src/App.vue`)
- Brancher les appels à la nouvelle route d'API dans le fichier `apps/rhfront/src/services/employee.service.js`

## Ressources

- [Slides cours](https://ziggornif.github.io/tdd-workshop/)
