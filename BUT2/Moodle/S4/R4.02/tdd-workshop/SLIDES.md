---
marp: true
theme: gaia
_class: lead
backgroundColor: #fff
backgroundImage: url('https://marp.app/assets/hero-background.jpg')
---

# Tests unitaires

La technique TDD, les librairies de tests unitaires, la couverture de code.

Mise en pratique

![bg left:40% 80%](./assets/logo.png)

---

# La technique TDD (Test Driven Development)

Le développement piloté par les tests est une technique qui demande l’écriture des tests unitaires avant l’écriture du code.

C’est une technique itérative et incrémentale, le cycle est répété tout au long du développement de l’application et pour chaque incrément technique ou fonctionnel.

---

# Cycle du TDD

- Écrire le test
- Vérifier que le test échoue
- Écrire le code
- Vérifier que le test passe
- Refactoriser le code

![bg right:50% 100%](./assets/tdd.png)

---

# Librairies de tests

![height:200px](./assets/nunit.png) ![height:200px](./assets/jest.png)
![height:300px](./assets/junit.png) ![height:300px](./assets/vitest.png)

---

# La couverture de code

Quatre principales méthodes de couverture de code par les tests :

• Couverture des fonctions : combien de fois une fonction est appelée
• Couverture des instructions : combien de fois une ligne de code est appelée
• Couverture des points de tests : combien de valeurs de variables sont testées
• Couverture des chemins d’exécution : combien de parcours possibles sont testés

---

#

![](./assets/julien-twitter.png)

---

# Couverture de code à MGDIS

Profils `Quality gates` Sonar :

- A+ 90% de couverture
- A > 80% de couverture
- B > 70% de couverture
- C > 60% de couverture

Si couverture inférieure au profil défini => build Gitlab KO 🚨

---

# Démonstration

Démonstration rapide d'un développement en TDD.

```js
function hello() {
  return 'Hello world !
}
```

---

# Mise en pratique

https://bit.ly/4bi1SwJ

![bg right:50% 100%](./assets/bit.ly_4bi1SwJ.png)

Cliquer sur le bouton 

![width:300px](https://camo.githubusercontent.com/95fbab4ac41e62a9f66e6d1d78f8249c418b33f8c7739c4f9c593f953f5362de/68747470733a2f2f676974706f642e696f2f627574746f6e2f6f70656e2d696e2d676974706f642e737667)

---

# Pour aller plus loin

- Tests snapshot
- Écriture des tests assisté par IA

---

# Pour aller plus loin (suite)

Architecture hexagonale ([conférence Devoxx](https://youtu.be/-dXN8wkN0yk?si=zbGllnyK7g1wG_Sg))

Quelques katas pour s'entraîner au TDD :

- http://codingdojo.org/kata/FizzBuzz/
- http://codingdojo.org/kata/StringCalculator/
- http://codingdojo.org/kata/Bowling/
