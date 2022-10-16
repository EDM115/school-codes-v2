CM2 Codage des entiers - Notes de cours
=======================================

Quizz : Combien vaut 0.1 + 0.2 ?
- 0
- -0
- 0.3
- 0.30000000000004

Aujourd'hui on va répondre à cette question...


Encore et toujours du binaire
=============================

- Élément de base : binaire
- Regroupés en octets (8 bits)
- Qu'on peut regrouper encore (par exemple 4 octets = 32 bits, 8 octets = 64 bits)
- Sur 4 octets par exemple, 2<sup>32</sup> possibilités, ça ne va pas nous permettre de calculer jusqu'à l'infini...
  - 2 bits : 2 valeurs (0 et 1)
  - 8 bits : 256 valeurs (de 0 à 255) -> une partie d'IPv4, une table de caractères
  - 16 bits : 65 536 valeurs (de 0 à 65 535)
  - 32 bits : 4 294 967 296 valeurs -> le nombre d'IPv4, la limite à 4GB de RAM des machines/OS 32 bits

La notion de type
=================

- Langage typé ou non, chaque donnée (nombre) est une interprétation d'un code binaire par rapport à un type
  - Langage typé (fortement) ≃ le développeur explicite les types
  - Langage non typé (faiblement typé) ≃ le développeur n'explicite pas les types
  - Mais les types sont toujours là en-dessous !!!
  - Pour rire un peu : [c'est le bazar](https://fr.wikipedia.org/wiki/Typage_fort)

- Les types de base : ceux du C, ceux qui sont compris par les microprocesseurs, donc calcul natif et donc rapide
  - int (pour les entiers)
  - float, double (pour les réels)
  - char (pour les... octets !)
- Les types construits : on les construit à la main par composition de ces types de base
  - les nombres complexes
  - les grands entiers (de taille non limitée)
  - les coordonnées d'un point
  - ...

- Les types de taille fixe (dont les types de base)
- Les types de taille variable/infinie (des types construits, les nombres que l'on écrit au crayon sur une feuille !)

- Mapper cet infini habituel sur nos feuilles vers un ordinateur : KO !

Les entiers
===========

Ici, 0.1 + 0.2 = 0 ([int.c](cm2-nombres-code/int.c))

Représentation des entiers positifs
-----------------------------------

- Changement de base "simple" (TD2)
- 155<sub>10</sub> -> 1001 1011<sub>2</sub> (0x9B)

Exemple simple d'addition :
- 155 + 3 = 158
- 1001 1011 + 0000 0011 = 1001 1110

Mais le débordement ([char.c](cm2-nombres-code/char.c)) :
- 155 + 155 = 310
- **MAIS** 1001 1011 + 1001 1011 = 1 0011 0110 -> 0011 0110 = 54 (= 310 - 256)


Représentation des entiers relatifs
-----------------------------------

- Valeur absolue signée
  - Un bit de signe puis la valeur absolue
  - Sur 1 octet : 1 bit de signe, 7 bits de valeur
  - 1001 1011 -> -27 ;-)
  - Convention d'interprétation du binaire par le type...
  - 0000 0000 -> 0, 1000 000 -> -0 => 0.1 + 0.2 == -0
  - Pas très pratique...

- Complément à 2
  - Un seul 0 : 0000 0000
  - 1 -> 0000 0001
  - -1 -> 1111 1111
  - 127 -> 0111 1111
  - -128 -> 1000 0000
  - Pratique (et utilisé pour les types entiers) car :
    - un seul 0
    - les opérations sont identiques à celles pour un entier non signé
  - Détails [ici](https://fr.wikipedia.org/wiki/Compl%C3%A9ment_%C3%A0_deux)

- Débordement ([char2.c](cm2-nombres-code/char2.c)) :
  - 127 + 1 = 128
  - 0111 1111 + 0000 0001 = 1000 0000 -> -128
