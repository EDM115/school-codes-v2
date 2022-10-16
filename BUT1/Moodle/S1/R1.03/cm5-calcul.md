CM5 Calcul booléen - Notes de cours
==========================================


Pourquoi ?
==========

Le calcul informatique se base sur des transistors. Avec des transistors, on va voir qu'on sait faire des fonctions logiques NAND, NON, NOR. Et à partir de ces constructions, on sait construire ET et OU. Et à partir de ET et OU, on sait calculer, par exemple additionner des nombres. Avec tout ça, le calcul booléen sert de base à l'informatique.


Transistors et portes
=====================

Le principe du transistor :
- 3 pattes
- Source, Drain, Grille
- Source = positif, Drain = négatif, Grille = contrôle
- Version N : ouvert si contrôle vaut 0, fermé si contrôle vaut 1 (Version P = le contraire)
- Fermé = le courant passe entre S et D

Une porte logique :
- Un assemblage de transistors
- Une fonction de plus haut niveau
- Exemple de la porte NON : ![NON](https://upload.wikimedia.org/wikipedia/commons/thumb/f/f2/Porte_NON_fabriqu%C3%A9e_avec_des_transistors_CMOS._01.jpg/220px-Porte_NON_fabriqu%C3%A9e_avec_des_transistors_CMOS._01.jpg)
- NAND, NOR, ET, OU, ... ![OU](https://upload.wikimedia.org/wikipedia/commons/thumb/5/5e/CMOS_OR.svg/440px-CMOS_OR.svg.png)

Avec des transistors, on a créé l'implémentation matérielle de la logique booléenne (ET, OU, NON, etc.)

Détails [ici](https://fr.wikibooks.org/wiki/Fonctionnement_d%27un_ordinateur/Les_transistors_et_portes_logiques).


Logique booléenne
=================

Des opérateurs de base :
- Conjonction ET (., ^, &, &&) ![ET](https://upload.wikimedia.org/wikipedia/commons/thumb/4/46/And.svg/128px-And.svg.png)
- Disjonction OU (+, v, |, ||) ![OU](https://upload.wikimedia.org/wikipedia/commons/thumb/4/4c/Or-gate-en.svg/128px-Or-gate-en.svg.png)
- Négation NON (¬, -, !) ![NON](https://upload.wikimedia.org/wikipedia/commons/thumb/9/9f/Not-gate-en.svg/128px-Not-gate-en.svg.png)
- Tables de vérité

Des compositions :
- Ou exclusif XOR ![XOR](https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Xor-gate-en.svg/128px-Xor-gate-en.svg.png)
- NAND ![NAND](https://upload.wikimedia.org/wikipedia/commons/thumb/5/58/Nand-gate-en.svg/128px-Nand-gate-en.svg.png)
- NOR ![NOR](https://upload.wikimedia.org/wikipedia/commons/thumb/9/94/Nor-gate-en.svg/128px-Nor-gate-en.svg.png)

Des règles :
- Associativité
- Commutativité
- Distributivité
- Idempotence
- Éléments neutres et absorbants
- Priorité : NON > ET > OU

Théorème de De Morgan :
- ¬(a+b) = ¬a . ¬b
- ¬(a.b) = ¬a + ¬b

Détails [ici](https://fr.wikipedia.org/wiki/Alg%C3%A8bre_de_Boole_(logique))

Usages
======

Calcul
------

On peut calculer !
- XOR = additionneur 1 bit (mais sans la retenue)
- En TD, on fera un additionneur

Opérations logiques
-------------------

Conditionnelle avec &&, || : paresseux ET "synthétiques"

Opérations bit à bit
--------------------

- Opérations avec &, |, !
- Notion de masque réseau
