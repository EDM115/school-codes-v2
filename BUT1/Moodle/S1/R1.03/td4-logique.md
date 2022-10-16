TD4 Logique booléenne
=====================

Transistors
-----------

Les informations nécessaires pour ces questions se trouvent sur [cette page](https://fr.wikibooks.org/wiki/Fonctionnement_d%27un_ordinateur/Les_transistors_et_portes_logiques).

> Question 0 : Faîtes une table de correspondance français/anglais pour ET, OU, NAND, NOR, XOR, NON

> Question 1 : Retrouvez et recopiez la décomposition en transistors des portes NON, NAND et NOR.

> Question 2 : Avec par exemple des couleurs, représentez sur ces schémas les différentes combinaisons d'entrées et les états intermédiaires associés. Par exemple, si la couleur verte correspond aux entrées A=0 et B=1, écrivez en vert sur chaque fil la valeur présente 0 ou 1.

À partir d'ici nous utiliserons les symboles des portes logique plutôt que le détail des transistors.

> Question 3 : En vous inspirant du plan transistor de ET et OU de la page Wikibooks, concevez les portes ET et OU à partir des symboles correspondant aux portes déjà décrites NON, NAND et NOR.


Tables de vérité
----------------

Vérifiez la construction précédente du ET à partir des tables de vérité.

> Question 4 : Écrivez les tables de vérité de NAND, de NON et vérifiez que leur composition donne bien ce qui est attendu pour ET.



Expressions booléennes
----------------------

> Question 5 : Parenthésez selon les priorités
> - a + b . c + ¬ a . d
> - a . a + a . ¬ a + ¬ a


> Question 6 : Simplifiez les expressions suivantes
> - a . a + a . ¬ a + ¬ a
> - (a + (b + ¬b) + (c.¬c)).a


> Question 7 : Distribuez au maximum
> - (a + b).(c + ¬d).¬(a+b)
> - ¬(a.b).c


> Question 8 : Factorisez au maximum
> - a.c + a.b + a.¬d
> - ¬a.c + ¬b.c


> Question 9 : Tracez sous forme d'arbre (un nœud = une fonction logique)
> - a + b . c + ¬ a . d
> - a . a + a . ¬ a + ¬ a


Tables de Karnaugh
------------------

Les [tables de Karnaugh](https://fr.wikipedia.org/wiki/Table_de_Karnaugh) permettent de simplifier les expressions booléennes complexes.

> Question 10 : Simplifiez l'expression (¬a.b.c) + (¬a.b.(¬c+d)) + (¬a.b.¬d) + a.b.d + a.b.c + a.b.(¬c+¬d) + ¬c.¬d.¬(a+¬b) + ¬a.¬b.¬c.¬d + ¬a.¬b.¬c.d en utilisant une table de Karnaugh
