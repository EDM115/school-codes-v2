## Fiche de révision - TP1
**Différence entre classification et régression**
- Classification : Catégorise des données (ex: déterminer si un mail est un spam).
- Régression : Prédit une valeur quantitative (ex: estimer le prix d'une maison).

**Différence entre apprentissage supervisé et non supervisé**
- Supervisé : L'algorithme apprend à partir d'exemples étiquetés (ex: images de chats étiquetées "chat").
- Non supervisé : L'algorithme découvre des motifs dans les données sans étiquettes, regroupe souvent les données similaires.

**Utilisation de Scikit-learn.org**
- Exemple : Utiliser scikit-learn pour entraîner un modèle de classification.
- Code : `RandomForestClassifier` est utilisé pour classer des échantillons.
- Prédiction : Le modèle prédit les classes des échantillons fournis. 

**Normalisation des données**

- **Outils utilisés** : `StandardScaler`, `LogisticRegression`, `make_pipeline`, `load_iris`, `train_test_split`, `accuracy_score` de scikit-learn (sklearn).
- **Objectif** : Créer un pipeline pour entraîner un modèle de régression logistique et évaluer sa précision avec l'ensemble de données Iris.

**Étapes** :
1. **Création du pipeline** : 
   - `StandardScaler()` : Mise à l'échelle standard des caractéristiques.
   - `LogisticRegression()` : Modèle de régression logistique pour la classification.
2. **Chargement des données Iris** : 
   - `load_iris(return_X_y=True)` : Charge l'ensemble de données Iris en X (caractéristiques) et y (étiquettes de classe).
3. **Division des données** : 
   - `train_test_split` : Divise les données en ensembles d'entraînement (X_train, y_train) et de test (X_test, y_test).
4. **Entraînement du modèle** : 
   - `pipe.fit(X_train, y_train)` : Ajuste le pipeline aux données d'entraînement.
5. **Évaluation de la précision** :
   - `accuracy_score(pipe.predict(X_test), y_test)` : Calcule la précision du modèle en comparant les prédictions du modèle aux vraies étiquettes.

**Utilisation de scikit-learn**

**1. Classification avec RandomForest**:
- Utilisation de `RandomForestClassifier`.
- Formation sur l'ensemble de données `X` et prédiction sur de nouveaux échantillons.

**2. Mise à l'échelle des données**:
- Utilisation de `StandardScaler` pour normaliser les données.

**3. Pipeline pour la classification**:
- Combinaison de `StandardScaler` et `LogisticRegression` dans un pipeline.
- Formation et évaluation sur l'ensemble de données Iris.

**4. Validation croisée pour la régression**:
- Utilisation de `LinearRegression` avec validation croisée sur un ensemble de données généré.

**5. Optimisation d'hyperparamètres**:
- Utilisation de `RandomizedSearchCV` pour optimiser `RandomForestRegressor`.
- Évaluation sur l'ensemble de données du logement en Californie.

**Résumé**:
Diverses techniques de prétraitement, d'entraînement et d'évaluation des modèles avec scikit-learn sont présentées.

**Améliorations du code avec scikit-learn**

1. **Organisation** : Séparer les démonstrations en fonctions distinctes pour une meilleure lisibilité.
2. **Ensembles de données** : Utiliser des ensembles plus grands pour une démonstration plus significative.
3. **Optimisation** : 
   - Augmenter la portée de `RandomizedSearchCV`.
   - Explorer davantage d'hyperparamètres.
   - Envisager `GridSearchCV` pour une exploration exhaustive.
4. **Validation croisée** : Augmenter le nombre de plis et envisager `StratifiedKFold`.
5. **Évaluation** : Intégrer des métriques supplémentaires (matrice de confusion, rappel, etc.).
6. **Visualisation** : Incorporer des graphiques pour les résultats.
7. **Prétraitement** : Explorer d'autres techniques comme `MinMaxScaler` ou `PCA`.
8. **Gestion des erreurs** : Ajouter des gestionnaires d'exceptions pour la robustesse.
9. **Documentation** : Améliorer les commentaires et documenter chaque fonction.

**Code amélioré** :

Le code a été restructuré pour incorporer les améliorations suggérées :
- Démonstrations séparées en fonctions individuelles.
- Métriques d'évaluation supplémentaires et visualisation pour `LogisticRegression`.
- Validation croisée étendue pour `LinearRegression`.
- Exploration d'hyperparamètres augmentée pour `RandomizedSearchCV`.

**Manipulation de l'ensemble de données Iris avec un pipeline**

1. **Chargement des données** : 
   - Données : `load_iris(return_X_y=True)`
   - Caractéristiques : `X`
   - Étiquettes : `y`

2. **Division des données** : 
   - Entraînement : `X_train`, `y_train`
   - Test : `X_test`, `y_test`
   
3. **Pipeline** :
   - Étapes : `StandardScaler()` (mise à l'échelle) + `LogisticRegression()` (modèle)
   - Entraînement : `pipe.fit(X_train, y_train)`

4. **Évaluation** :
   - Prédiction sur données de test : `pipe.predict(X_test)`
   - Mesure de précision : `accuracy_score()`

**Résumé** :
Le code traite les données Iris en les chargeant, les divisant, les traitant via un pipeline (mise à l'échelle + modèle), puis évalue la précision du modèle sur les données de test.

**Régression linéaire avec validation croisée**

1. **Données** : Génération d'un ensemble de données artificiel avec `make_regression`.
2. **Modèle** : Création d'un modèle de régression linéaire.
3. **Validation croisée** : Évaluation du modèle à l'aide de `cross_validate` (5-fold par défaut).
4. **Score** : Affichage des scores R² pour chaque pli.
5. **Conclusion** : Un score R² élevé suggère un bon ajustement du modèle aux données.

**Régression avec RandomForest et recherche aléatoire**

1. **Données** : Chargement des données "California Housing" et division en ensembles d'entraînement et de test.
2. **Espace des paramètres** : Définition des paramètres à explorer (`n_estimators` et `max_depth`).
3. **Recherche aléatoire** : Utilisation de `RandomizedSearchCV` pour trouver les meilleurs paramètres du modèle `RandomForestRegressor`.
4. **Ajustement** : Ajustement du modèle de recherche aléatoire aux données d'entraînement.
5. **Meilleurs paramètres** : Affichage des paramètres donnant la meilleure performance.
6. **Évaluation** : Score du modèle sur les données de test pour évaluer sa capacité de généralisation.
7. **Conclusion** : Le modèle est ajusté en fonction des meilleurs paramètres trouvés et sa performance est évaluée sur un ensemble de test.




## Concepts Majeurs

1. **Types d'apprentissage en Machine Learning** :
   - **Classification** : Catégorise les données (par exemple : spam ou non-spam).
   - **Régression** : Prédit une valeur continue (par exemple : le prix d'une maison).

2. **Types d'approches d'apprentissage** :
   - **Apprentissage supervisé** : L'algorithme est entraîné sur des données étiquetées.
   - **Apprentissage non supervisé** : L'algorithme découvre des structures ou des motifs dans les données sans étiquettes.

3. **Validation croisée** : Technique pour évaluer les performances d'un modèle en divisant les données en plusieurs sous-ensembles et en testant sur chaque sous-ensemble.

4. **Recherche d'hyperparamètres** : Technique pour trouver les meilleurs paramètres d'un modèle, comme `RandomizedSearchCV`.

5. **Pipelines** : Combinaisons d'étapes de traitement et de modélisation, facilitant la mise à l'échelle, la transformation et l'entraînement.

## Fonctions Python Principales

1. **`load_iris`** : Charge l'ensemble de données Iris.
2. **`train_test_split`** : Divise les données en ensembles d'entraînement et de test.
3. **`RandomForestClassifier`** et **`RandomForestRegressor`** : Modèles basés sur des forêts aléatoires pour la classification et la régression.
4. **`StandardScaler`** : Mise à l'échelle standard des caractéristiques.
5. **`LogisticRegression`** : Modèle de classification.
6. **`LinearRegression`** : Modèle de régression.
7. **`cross_validate`** : Fonction de validation croisée.
8. **`RandomizedSearchCV`** : Recherche aléatoire d'hyperparamètres.
9. **`accuracy_score`**, **`classification_report`**, **`confusion_matrix`** : Métriques d'évaluation.

## À Retenir du TP1

- Une compréhension des différences fondamentales entre la classification et la régression.
- La distinction entre l'apprentissage supervisé et non supervisé.
- La capacité d'utiliser des pipelines pour combiner plusieurs étapes, notamment la mise à l'échelle des données et l'entraînement des modèles.
- L'importance de la validation croisée pour évaluer les performances des modèles.
- L'utilisation de `scikit-learn` pour diverses tâches de Machine Learning, notamment la classification, la régression, et la recherche d'hyperparamètres.
- L'importance de l'évaluation des modèles à l'aide de métriques appropriées pour obtenir un aperçu des performances.

