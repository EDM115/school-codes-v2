## Fiche de révision - TP2 - Lussandre Lederrey

---

<div align="center">

# Fiche de Révision

</div>

### 1. Algorithme Naïf Bayes:

- **Définition**: Modèle probabiliste utilisé en apprentissage automatique pour la classification, basé sur le théorème de Bayes.
  
#### Variantes :

- **GaussianNB (Naive Bayes Gaussien)** : 
  - Pour des données continues basées sur la distribution gaussienne.
  - Formule : 
    \[
    P(x_i \mid y) = \frac{1}{\sqrt{2\pi\sigma^2_y}} \exp\left(-\frac{(x_i - \mu_y)^2}{2\sigma^2_y}\right)
    \]
  - Termes clés : 
    - \( P(x_i \mid y) \): Probabilité conditionnelle de la caractéristique \( x_i \) sachant la classe y.
    - \( \mu_y \): Moyenne des valeurs de la caractéristique \( x_i \) pour la classe y.
    - \( \sigma^2_y \): Variance des valeurs de la caractéristique \( x_i \) pour la classe y.

- **BernoulliNB (Naive Bayes de Bernoulli)** :
  - Pour des données binaires basées sur la distribution de Bernoulli.
  - Formule : 
    \[
    P(x_i \mid y) = P(x_i = 1 \mid y) x_i + (1 - P(x_i = 1 \mid y)) (1 - x_i)
    \]
  - Termes clés :
    - \( P(x_i \mid y) \): Probabilité conditionnelle que \( x_i \) soit présente (valeur 1) sachant la classe y.
    - \( P(x_i=1 \mid y) \) & \( P(x_i=0 \mid y) \): Probabilités que la caractéristique \( x_i \) soit égale à 1 ou 0 sachant la classe y.

### 2. Algorithme LogisticRegression:

- **Définition**: Algorithme utilisé pour résoudre des problèmes de classification. Il est basé sur la fonction logistique.

- **Processus**:
  1. Importation des bibliothèques.
  2. Génération de données synthétiques.
  3. Séparation des données.
  4. Création et entraînement du modèle.
  5. Prédictions et évaluation du modèle.
  6. Visualisation des résultats.

### 3. Support Vector Machine (Machine à vecteurs de support):

- **Définition**: Algorithme d'apprentissage supervisé utilisé pour la classification ou la régression. Il trouve un hyperplan qui sépare au mieux les données de deux classes.

- **Termes clés**:
  - **Classification supervisée**: Apprendre à partir d'un ensemble de données étiqueté.
  - **Hyperplan**: Frontière qui sépare les données de deux classes dans SVM.
  - **Marge**: Distance entre l'hyperplan et les points les plus proches de chaque classe.
  - **Vecteurs de support**: Points de données les plus proches de l'hyperplan.

---

<div align="center">

# Notions importantes :

</div>

1. **Algorithme Naïf Bayes**: Un modèle probabiliste basé sur le théorème de Bayes, utilisé pour la classification.
2. **GaussianNB**: Variante de Naive Bayes pour des données continues basées sur la distribution gaussienne.
3. **BernoulliNB**: Variante de Naive Bayes pour des données binaires basées sur la distribution de Bernoulli.
4. **Logistic Regression**: Algorithme basé sur la fonction logistique, utilisé pour résoudre des problèmes de classification.
5. **Support Vector Machine (SVM)**: Algorithme qui trouve un hyperplan pour séparer les données de deux classes.
6. **Classification supervisée**: Technique où l'algorithme apprend à partir d'un ensemble de données étiqueté.
7. **Hyperplan**: Frontière qui sépare les données dans SVM.
8. **Marge**: Distance entre l'hyperplan et les points les plus proches de chaque classe en SVM.
9. **Vecteurs de support**: Points essentiels pour définir l'hyperplan optimal dans SVM.

---