TD3 Codage des réels
====================

Virgule fixe
------------

Soit une convention en virgule fixe avec une partie entière sur 1 octet codée en complément à 2 et une partie fractionnaire sur 1 octet également.

Rappel : la partie fractionnaire représente 2<sup>-1</sup> = 1/2, 2<sup>-2</sup> = 1/4, 2<sup>-3</sup> = 1/8, etc.

> Question 1 : Que vaut en base 10 la chaîne binaire 1010 0110 / 1100 0011 ?

> Question 2 : Encodez en binaire le nombre 18,375. Est-ce une conversion exacte ?

> Question 3 : Encodez en binaire le nombre 78,74. Est-ce une conversion exacte ?

> Question 4 : Additionnez les représentations binaires de 18,375 + 78,74. Comment devez-vous procéder ?

> Bilan : la virgule fixe permet de calculer sur les réels à partir d'opérations sur les entiers.


Virgule flottante
-----------------

Adoptons la convention suivante pour la virgule flottante sur 2 octets :
* 1 bit de signe
* 5 bits d'exposant
* 10 bits de mantisse

Il s'agit de la convention [IEEE 754 Half Precision](https://en.wikipedia.org/wiki/Half-precision_floating-point_format). Un guide est disponible [ici](https://fr.wikihow.com/convertir-un-nombre-d%C3%A9cimal-au-format-binaire-IEEE-754) pour l'encodage. Attention le guide est illustré avec un encodage en simple précision sur 32 bits, il faut donc l'adapter. L'offset à utiliser sur l'exposant ici vaut 15.

> Question 5 : Que vaut en base 10 la chaîne binaire 1110 0110 / 1100 0011 ? Attention :
> - la mantisse à utiliser pour le calcul commence par un bit à 1 implicite, ie, il faut rajouter un bit à 1 à gauche des 10 bits de mantisse encodés, qui devient ainsi "1,(la partie encodée sur 10 bits)".
> - l'exposant est encodé avec l'offset de 15, il faut donc soustraire 15 au binaire encodé pour avoir l'exposant du calcul (cet exposant est le nombre de décalages de la virgule depuis la notation scientifique)

> Question 6 : Encodez en virgule flottante le nombre 8576,25. Décomposez en plusieurs étapes :
> - Représentation binaire de la partie entière, de la partie fractionnaire
> - Calcul de l'exposant permettant de recaler en notation scientifique
> - Encodage de l'exposant en lui ajoutant l'offset de 15

> Question 7 : Est-ce une conversion exacte ? Redécodez en base 10 le nombre précédemment encodé

> Question 8 : Encodez le nombre 3,12

> Question 9 : Redécodez en base 10

> Question 10 : Additionnez, en virgule flottante binaire, les représentations de 3,12 + 8576,25, puis convertissez le résultat en décimal. Comment devez-vous procéder ? Que pensez-vous du résultat ?

> Question 11 : Sans faire de calcul/conversion supplémentaire, quelle est la représentation en virgule flottante de 8576 + 0,36 ? Son affichage décimal ?

> Question 12 (Bonus) : Quelles sont les plus grandes et plus petites valeurs dans cette convention (exprimez les en décimal ainsi que leur encodage binaire)

> Bilan : la virgule flottante nécessite une unité de calcul spécifique.
