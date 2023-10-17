<div align="center">

# Fiche de révision - TP4
### Lussandre Lederrey - Mathieu Stephan - Allan Maccrez - Nathan Basol

</div>

---

# Régression Isotonique

## Qu'est-ce que la Régression Isotonique?
La Régression Isotonique modélise des relations monotones entre les caractéristiques d'entrée et la variable cible.

## Points-clés
- Monotonie
- Flexibilité

## Définitions connexes
- **Monotonie** : Relation croissante ou décroissante.
- **Flexibilité** : Peut capturer des relations non linéaires.

## Exemple concret d'utilisation
Identification des tendances monotones dans les données en bioinformatique.

---

# Régression à Noyau (Kernel Ridge Regression)

## Qu'est-ce que la Régression à Noyau?
Extension de la régression linéaire qui utilise des noyaux pour projeter les caractéristiques dans un espace de dimension supérieure.

## Points-clés
- Flexibilité
- Noyaux
- Hyperparamètres

## Définitions connexes
- **Noyaux** : Mesurent la similarité entre les données.
- **Hyperparamètres** : Paramètres tels que le coefficient de régularisation et le paramètre du noyau.

## Exemple concret d'utilisation
Modélisation de relations complexes et non linéaires dans des domaines comme l'apprentissage automatique et la science des données.

---

# Régression Polynomiale

## Qu'est-ce que la Régression Polynomiale?
Modélise la relation entre une variable indépendante et une variable dépendante sous forme de polynôme.

## Points-clés
- Modélisation non linéaire
- Ordre du polynôme

## Définitions connexes
- **Ordre du Polynôme** : Détermine la complexité du modèle.

## Exemple concret d'utilisation
Utilisée quand les relations entre les variables sont complexes et non linéaires, par exemple, en prédisant la croissance des cultures basée sur divers facteurs environnementaux.

---

# Régression Linéaire

## Qu'est-ce que la Régression Linéaire?
Modélise la relation linéaire entre une ou plusieurs variables indépendantes et une variable dépendante.

## Points-clés
- Modélisation de la relation
- Entraînement du modèle
- Prédiction et évaluation

## Définitions connexes
- **Coefficient de détermination ($R^2$)** : Mesure la variance expliquée par le modèle.

## Exemple concret d'utilisation
Prévision des ventes en fonction des dépenses publicitaires.

---

# Régression avec Techniques de Régularisation

## Qu'est-ce que la Régression avec Techniques de Régularisation?
Utilisation de la régularisation pour éviter le surajustement dans les modèles de régression.

## Points-clés
- Régression Ridge
- Régression Lasso

## Définitions connexes
- **Surajustement** : Le modèle apprend le bruit des données d'entraînement.
- **Pénalité** : Réduit la liberté du modèle.

## Exemple concret d'utilisation
Prédiction des prix des logements tout en évitant le surajustement grâce à la régularisation.

---

### Régression Ridge

#### Qu'est-ce que c'est ?
Modifie la régression linéaire en ajoutant une pénalité sur la magnitude des coefficients.

#### Points-clés
- Pénalité L2

#### Définitions connexes
- **Régularisation L2** : Pénalise la somme des carrés des coefficients.

#### Exemple concret d'utilisation
Prévision des prix des maisons en minimisant l'impact des caractéristiques multicollinéaires.

---

### Régression Lasso

#### Qu'est-ce que c'est ?
Semblable à Ridge mais peut réduire certains coefficients à zéro, permettant la sélection de caractéristiques.

#### Points-clés
- Pénalité L1

#### Définitions connexes
- **Régularisation L1** : Pénalise la somme des valeurs absolues des coefficients.

#### Exemple concret d'utilisation
Sélection des caractéristiques pertinentes dans un modèle prédictif pour la prévision des ventes.
