TD2 Codage des entiers
======================

Les trois systèmes utilisés ici (entiers positifs, complément à 1, complément à 2) sont décrits avec les détails suffisants sur la [page Wikipedia dédiée](https://fr.wikipedia.org/wiki/Syst%C3%A8me_binaire). Jetez-y un œil très rapide en début de séance (un parcours du plan, pas une lecture) puis revenez-y au moment nécessaire.

L'objectif de ce TD est de faire les calculs, vous ne devez donc pas utiliser de conversion automatique (par une calculatrice ou un site web) lors des passages binaire <-> décimal.

Table de conversion binaire <-> hexadécimal
-------------------------------------------

Construisez sur votre feuille un tableau de conversion binaire <-> hexadécimal allant de 0x0 à 0xF. Cela sera très utile pour la suite.

| Hexa | Bin  |
| --- | ---- |
| 0x0 | 0000 |
| 0x1 | 0001 |
| ... | ...  |
| 0xF | 1111 |

(Il est évidemment facile de trouver une telle table sur internet mais c'est l'occasion de valider votre compréhension du binaire/hexa, faîtes donc l'exercice sans chercher de modèle)

Entiers positifs
----------------

Les entiers positifs s'encodent en changeant leur base directement du décimal au binaire. C'est par exemple le type "unsigned char" en C, pour un entier non signé sur un seul octet.

> Question 1 : Convertissez en binaire puis en hexadécimal sur un octet le nombre 138. Réalisez le calcul en décomposant en puissances de 2.

> Question 2 : Convertissez en décimal le binaire 0110 1110 en décomposant en somme de puissances de 2.

> Question 3 : Convertissez en binaire puis décimal l'hexadécimal 0x6B, toujours avec les détails de calcul.

> Question 4 : Quelle est la plus grande valeur représentable sur un octet ? Pourquoi ?


Complément à 1 (Non mentionné en cours)
--------------

Le complément à 1 va, ici, nous servir d'étape intermédiaire pour construire le complément à 2. Il s'agit simplement d'inverser tous les bits de la représentation binaire.

> Question 5 : Quel est le complément à 1 sur un octet, en binaire puis en hexadécimal, de 0x6B ?


Complément à 2
--------------

Le complément à 2, comme décrit sur la page Wikipedia liée en haut de sujet, permet de représenter les entiers négatifs en faisant :
- complément à 1 de la valeur absolue
- puis +1 sur le binaire

Avec cette norme de codage, les entiers positifs restent représentés comme dans les questions 1 à 4. Le premier bit indique le signe : 0 pour + et 1 pour -.

Le complément à 2 est la façon usuelle de représenter les entiers relatifs en mémoire. C'est par exemple comme ça qu'est représenté en mémoire le type "char" en C, pour un entier signé sur un seul octet (ainsi que tous les autres types d'entiers sur plus d'un octet évidemment).

> Question 6 : Quel est, en binaire puis en hexadécimal, la représentation en complément à 2 de -79 (toujours sur 1 octet) ?

> Question 7 : Faîtes la somme, en calcul binaire, des représentations binaires de 79 + 78, avec un format de sortie sur un octet. Convertissez le résultat en entier base 10 codé en complément à 2. Que constatez-vous ?

> Question 8 : Représentez en hexadécimal sur un octet 79, -79, -1

> Question 9 : Quelles sont les plus grandes valeurs positives et négatives représentables sur un octet ?

> Question 10 : Additionnez en binaire les nombres représentés en compléments à 2 de 66 et -79. Que pouvez-vous dire du résultat ?
