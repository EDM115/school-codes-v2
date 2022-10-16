CM3 Codage des réels - Notes de cours
======================================


Ici, 0.1 + 0.2 = 0.30000000000004 ([détails ici](https://0.30000000000000004.com/)) ([double.c](cm2-nombres-code/double.c))


Virgule fixe
------------

- Un réel = une partie entière et une partie fractionnaire séparés par une ','
- On code par exemple sur 2 octets :
  - 1 octet pour la partie réelle en complément à 2 (entier relatif)
  - 1 octet pour la partie fractionnaire en inverse : 1/2, 1/4, 1/8, 1/16, etc. (était en décimal 1/10, 1/100, 1/1000, etc.)
  - Il n'y a pas 1/10 ! -> On ne peut que s'approcher de 0.1, 0.2 et 0.3...
- Comme on écrit des nombres en décimal et qu'on les approxime avec des 1/2, 1/4, 1/8, etc. :
  - 0.1 n'est pas exactement 0.1
  - 0.2 n'est pas exactement 0.2
  - donc 0.1 + 0.2 n'est pas exactement 0.3
  - Note : un nombre en binaire sera aussi approximé pour repasser en base 10...
- L'approximation n'est pas forcément visible car nous utilisons une précision assez grande mais elle est là
- => Pas de calcul décimal exact en réels, **pas de tests d'égalités** !!!
  - égalités entre entiers seulement
  - au pire test d'écart à la valeur recherchée (mais c'est critiqué, les incertitudes ne se propagent pas d'une bonne manière)
- Utilisé en pratique si pas mieux dispo (micro-contrôleur sans unité à virgule flottante par exemple)


Virgule flottante
-----------------

- Signe (1 bit), exposant, mantisse
- Nombre = signe * mantisse * 2<sup>exposant</sup>
- En C, float (32 bits, peu précis à l'usage) et double (64 bits, plus précis)
- Exemple du double : 1 bit de signe, 11 bits d'exposant, 52 bits de mantisse (norme IEE754)
- Les mêmes approximations que précédemment ! (et donc pas non plus de tests d'égalité !!!)
- Couramment utilisé
- ([floatdouble.c](cm2-nombres-code/floatdouble.c))


Calcul exact (bonus, hors programme)
------------------------------------

Pour aller au-delà de ces limites, il faudra utiliser des biblothèques/logiciels dédiés au calcul, par exemple [SageMath](https://www.sagemath.org/), [Calcium](https://fredrikj.net/calcium/), du décimal codé binaire (DCB) ou encore les [fractions python](https://docs.python.org/3/library/fractions.html) (mais c'est une autre histoire...). Ici, on pourra avoir 0.1 + 0.2 = 3. Les nombres ne sont alors plus représentés comme les types de base vus précédemment qui sont les seuls sur lesquels savent calculer les CPU classiques, mais comme des types construits à plus haut niveau. Points d'attention :
- les calculs sont plus lents, car un calcul de ce type construit impliquera plusieurs calculs sur des types de base au niveau CPU
- les types que vous manipulerez par défaut dans les langages de programmation les plus courants sont uniquement les types de base, efficaces mais donc inexacts.


Annexes
=======

- [Entiers, virgules flottantes ou représentations exotiques ... (Olivier Poncet et Fabien Trégan, DevoxxFR 2022)](https://www.youtube.com/watch?v=1upzDFFIODk). Vidéo de 45 minutes, dont 15 premières minutes sur le programme de cette séance.
