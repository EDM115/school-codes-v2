# TD5 Additionneur et bascule

# Additionneur

Nous allons concevoir un additionneur à 2 bits, en plusieurs étapes.

## 1/2 additionneur

Le 1/2 additionneur fait l'addition et expose la retenue en sortie, mais ne gère pas de retenue en entrée. Il a donc 2 entrées a et b à additionner, et 2 sorties s (somme) et c (carry, retenue).

> Question 1 : Écrivez les tables de vérité souhaitées pour s et c en fonction de a et b. Une fois écrites, à quelles fonctions logiques connues correspondent ces deux tables ?

> Question 2 : Tracez, avec des portes logiques, le schéma de ce demi-additionneur.

## Additionneur 1 bit

L'additionneur 1 bit permet de gérer la retenue en entrée. Il a donc 3 entrées a, b et c<sub>in</sub> (retenue en entrée), et 2 sorties s et c<sub>out</sub>.

> Question 3 : Écrivez les tables de vérité souhaitées pour s et c<sub>out</sub> en fonction de a, b et c<sub>in</sub>. Une fois écrites, à quelles fonctions logiques connues correspondent ces tables ?

> Question 4 : Tracez, avec des portes logiques, le schéma de cet additionneur. Indice : Pour un additionneur, il faut 2 demi-additionneurs en cascade + un OU pour gérer la retenue.

## Additionneur 2 bits

L'additionneur 2 bits permet d'additionner 2 nombres sur 2 bits, sans retenue en entrée (mais donc une retenue intermédiaire + une sortie matérialisant qu'il y a eu débordement, ie, une retenue finale). Il a donc 4 entrées a<sub>0</sub>, a<sub>1</sub>, b<sub>0</sub> et b<sub>1</sub>, et 3 sorties s<sub>0</sub>, s<sub>1</sub> et c (débordement)

> Question 5 : Tracez, avec des portes logiques, le schéma de cet additionneur 2 bits.

# Bascules

La bascule est un élément permettant de conserver un état transitoire, par exemple pour faire une mémoire. Vous trouverez la description du verrou RS [ici](<https://fr.wikipedia.org/wiki/Bascule_(circuit_logique)#Verrou_RS_avec_porte_NON-OU>), avec des portes NOR ou NAND.

> Question 6 : Avec des couleurs, représentez sur les schémas en portes logiques les différentes combinaisons d'entrées et les états intermédiaires associés. Par exemple, si la couleur verte correspond aux entrées S=0 et R=1, écrivez en vert sur chaque fil la valeur présente 0 ou 1.

> Question 7 (bonus) : Faîtes de même pour d'autres bascules (verrou D, bascule D, ...)
