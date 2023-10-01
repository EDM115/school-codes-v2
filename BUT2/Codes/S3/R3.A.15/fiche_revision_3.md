<div align="center">

## Fiche de révision - TP3
### Lussandre Lederrey - Mathieu Stephan - Allan Maccrez - Nathan Basol

</div>

---

## DBSCAN (Density-Based Spatial Clustering of Applications with Noise)

### **Qu'est-ce que DBSCAN ?**
DBSCAN est un algorithme de clustering basé sur la densité des données. Il ne nécessite pas de spécifier le nombre de clusters à l'avance et génère des clusters basés sur la densité des données.

### **Points-clés :**
1. **Échantillons centraux** : Zones de haute densité identifiées en utilisant les paramètres `eps` et `min_samples`.
2. **Échantillons de bordure** : Points proches d'un échantillon central mais qui ne sont pas des échantillons centraux.
3. **Bruit** : Points qui ne sont ni des échantillons centraux ni des échantillons de bordure.

### **Définitions connexes :**
- **Échantillon central** : Un point avec au moins `min_samples` points dans son voisinage `eps`.
- **Échantillon de bordure** : Un point qui est proche d'un échantillon central mais qui n'est pas un échantillon central.
- **Bruit** : Points non classés.

### **Exemple :**
Dans un ensemble de données avec des points formant deux formes de croissant, DBSCAN identifie les deux formes comme clusters distincts et les points éloignés comme du "bruit".

---

## HDBSCAN (Hierarchical Density-Based Spatial Clustering of Applications with Noise)

### **Qu'est-ce que HDBSCAN ?**
HDBSCAN est une extension de DBSCAN qui explore toutes les échelles de densité possibles. Il est capable de détecter des clusters de densités variables.

### **Points-clés :**
1. **Distance de base** : Distance d'un échantillon à son `min_samples`-ième voisin le plus proche.
2. **Distance de portée mutuelle** : Mesure de distance entre deux points prenant en compte leurs distances de base respectives.
3. **Graphe de portée mutuelle** : Graphe construit en utilisant la distance de portée mutuelle comme mesure de distance.

### **Définitions connexes :**
- **Distance de base** : Distance d'un point à son `min_samples`-ième voisin le plus proche.
- **Distance de portée mutuelle** : Distance maximale entre deux points considérant leur distance de base respective.
- **Graphe de portée mutuelle** : Graph construit avec la distance de portée mutuelle.

### **Exemple :**
Dans un jeu de données avec deux "lunes", alors que DBSCAN pourrait mal identifier les clusters si la densité des points varie, HDBSCAN détecte robustement ces structures sans supposer une densité homogène.

---

**Note** : Les algorithmes basés sur la densité, comme DBSCAN et HDBSCAN, sont particulièrement utiles pour les ensembles de données ayant des formes complexes ou des densités variables. Ils peuvent capturer des structures non linéaires que d'autres méthodes, comme k-means, pourraient ne pas détecter.

---

## BIRCH (Balanced Iterative Reducing and Clustering using Hierarchies)

### **Qu'est-ce que BIRCH ?**
BIRCH est un algorithme de clustering conçu pour traiter efficacement de grands ensembles de données.

### **Points-clés :**
1. **Seuil (Threshold)** : Contrôle la compacité des clusters. Plus la valeur est élevée, plus les clusters sont compacts.
2. **Nombre de clusters (n_clusters)** : Définit le nombre de clusters souhaité ou laisse l'algorithme déterminer la meilleure valeur.

### **Utilisation :**
- **Préparation des données** : Assurez-vous que vos données sont compatibles avec scikit-learn.
- **Créer une instance BIRCH** : Définissez les hyperparamètres nécessaires.
- **Ajustement à vos données** : Utilisez la méthode `fit()`.
- **Obtenir les étiquettes de regroupement** : Utilisez la méthode `predict()` pour obtenir les étiquettes de cluster.

### **Résumé :**
BIRCH est un algorithme de clustering adapté aux grands ensembles de données. Il peut être une alternative efficace à des méthodes comme K-Means et peut également générer une hiérarchie de clusters.

---

## Spectral Clustering

### **Qu'est-ce que le Spectral Clustering ?**
Le Spectral Clustering est une technique de clustering qui utilise la transformation spectrale des données, en particulier lorsque les méthodes traditionnelles ne fonctionnent pas bien à cause de la structure complexe des données.

### **Points-clés :**
1. **Transformation spectrale** : Les données sont transformées en une représentation spectrale.
2. **Affinité** : Mesure les similarités entre les points. Par exemple, l'affinité `nearest_neighbors` est couramment utilisée.

### **Utilisation :**
- **Préparation des données** : Générez ou préparez votre ensemble de données.
- **Créer une instance SpectralClustering** : Définissez les hyperparamètres nécessaires.
- **Ajustement à vos données** : Utilisez la méthode `fit_predict()` pour obtenir les étiquettes de cluster.

### **Résumé :**
Le Spectral Clustering transforme les données en une représentation spectrale avant d'appliquer des techniques de clustering. Il est efficace pour des ensembles de données avec des structures non linéaires ou complexes.

---

**Note** : Tant BIRCH que le Spectral Clustering offrent des moyens d'aborder le clustering lorsque les données ont des structures complexes. Alors que BIRCH est adapté aux grands ensembles de données, le Spectral Clustering est particulièrement utile pour les données non linéaires.

---

## K-Means

### **Qu'est-ce que K-Means ?**
L'algorithme K-Means est une méthode de clustering non supervisée qui divise un ensemble de données en clusters (groupes) basés sur la similarité des points de données.

### **Points-clés :**
1. **Nombre de clusters (k)** : Le choix du nombre de clusters est crucial pour obtenir des résultats pertinents.
2. **Centroids** : Points de référence dans l'espace des données. L'initialisation aléatoire peut affecter la convergence.
3. **Convergence** : Répétez l'affectation des points aux clusters et le recalcul des centroids jusqu'à convergence.

### **Utilisation :**
- **Initialisation** : Sélectionnez k et initialisez les centroids.
- **Affectation des points** : Attribuez chaque point au cluster dont le centroid est le plus proche.
- **Mise à jour des centroids** : Calculez le centroid de chaque cluster en prenant la moyenne des points.

### **Résumé :**
K-Means est un algorithme de clustering largement utilisé qui nécessite la spécification du nombre de clusters. Il est efficace, mais sensible à l'initialisation des centroids et peut converger vers des minima locaux.

---

## Mean Shift

### **Qu'est-ce que Mean Shift ?**
Mean Shift est un algorithme de clustering non supervisé qui identifie les modes (maxima locaux) d'une distribution de données. Il ne nécessite pas la spécification préalable du nombre de clusters.

### **Points-clés :**
1. **Fonction de noyau** : Utilisée pour calculer la densité des points autour d'un point de départ. Influence les résultats.
2. **Bande passante** : Détermine la taille de la région autour du point de départ pour calculer la densité.
3. **Convergence** : L'algorithme se déplace vers des zones de densité plus élevée jusqu'à ce qu'il converge.

### **Utilisation :**
- **Point de départ** : Sélectionnez un point dans l'espace des données.
- **Calcul de densité** : Utilisez une fonction de noyau pour estimer la densité autour du point.
- **Déplacement** : Déplacez le point de départ vers des zones de densité plus élevée.

### **Résumé :**
Mean Shift est une technique de clustering qui trouve les modes d'une distribution de données. Il est particulièrement utile pour les distributions complexes où le nombre de clusters n'est pas connu à l'avance. Cependant, il peut être intensif en calcul pour de grands ensembles de données.

---

## OPTICS

### **Qu'est-ce que OPTICS ?**
L'algorithme OPTICS est une méthode de clustering non supervisée conçue pour identifier les structures de clustering, même lorsque les clusters ont des formes et des densités variées.

### **Points-clés :**
1. **MinPts et ε** : MinPts est le nombre minimum de voisins pour former un cluster, et ε est la distance maximale pour définir un voisin.
2. **Ordonnancement des points** : Les points sont triés par distance pour faciliter l'expansion des clusters.
3. **Dendrogramme** : Une représentation hiérarchique des clusters est créée.

### **Utilisation :**
- **Calcul des distances** : Une matrice de distances entre tous les points est générée.
- **Sélection d'un point** : Un point non visité est choisi aléatoirement.
- **Expansion du cluster** : Les points proches sont ajoutés au cluster.

### **Résumé :**
OPTICS est un algorithme de clustering qui prend en compte la densité et la forme des clusters pour identifier des structures complexes dans les données. Il génère un ordre de points qui facilite l'identification de la structure de clustering.

---

## Affinity Propagation

### **Qu'est-ce que Affinity Propagation ?**
Affinity Propagation est une technique de clustering qui identifie automatiquement les exemples les plus représentatifs comme centres de cluster, sans nécessiter la spécification préalable du nombre de clusters.

### **Points-clés :**
1. **Calcul des affinités** : Une matrice d'affinité est créée en calculant les similitudes entre chaque paire de points.
2. **Responsabilités et disponibilités** : Ces deux mesures déterminent quels points peuvent devenir des centres de clusters.
3. **Exemplars** : Les points qui ont à la fois une forte responsabilité et une forte disponibilité sont considérés comme des exemplars ou des centres de clusters.

### **Utilisation :**
- **Attribution des clusters** : Les points sont attribués à des clusters en fonction de leur affinité avec les exemplars.
- **Avantages et inconvénients** : L'algorithme est capable de gérer des clusters de différentes tailles et formes, mais il peut être sensible aux paramètres et lent sur de grands ensembles de données.

### **Résumé :**
Affinity Propagation est un algorithme de clustering qui identifie les centres de cluster en fonction de la notion d'affinité entre les points de données. Il est particulièrement utile pour des ensembles de données complexes où le nombre de clusters n'est pas connu à l'avance.

---
