TD1 Codage des caractères
=========================

Durant tout ce TD, vous serez amenés à faire des conversions entre binaire et hexadécimal. Une [table de conversion](https://s1.studylibfr.com/store/data/010003331_1-54d947926cabfe926721c69b3c39ea25.png) sera probablement le plus rapide, les programmes calculatrice permettent souvent cela également.

ASCII
=====

La table ASCII est facilement trouvable sur internet, par exemple [ici](https://fr.wikipedia.org/wiki/American_Standard_Code_for_Information_Interchange#Table_des_128_caract%C3%A8res_ASCII).

> Question 1 : Encodez en hexadécimal et binaire la chaîne "IUT-Vannes".

> Question 2 : Décodez la suite hexadécimale 0x49 0x6E 0x66 0x6F.


ISO-8859-15
=======

La table ISO-8859-15 peut être trouvée [ici](https://fr.wikipedia.org/wiki/ISO/CEI_8859-15#Caract%C3%A8res_support%C3%A9s).

> Question 3 : Encodez en hexadécimal et binaire la chaîne "Vénète".

> Question 4 : Décodez la suite hexadécimale 0x76 0xE9 0x6C 0x6F.

> Question 5 : Décodez cette même suite hexadécimale mais en utilisant la table grecque [ISO8859-7](https://fr.wikipedia.org/wiki/ISO/CEI_8859-7). Qu'en déduisez-vous sur ce message que vous enverriez à un grec ?


UTF-8
=====

Comme vu en cours, l'encodage UTF-8 passe par 2 étapes :
- Trouver le point de code unicode correspondant à la lettre
- Encoder ce point de code en UTF-8

> Question 6 : Trouvez les points de code pour la chaîne "cœur". Depuis la [table des caractères](https://fr.wikipedia.org/wiki/Table_des_caract%C3%A8res_Unicode), vous aurez besoin d'explorer le plan multilingue de base puis le latin de base et le latin étendu A.

Il faut ensuite encoder ces points de code. Pour cela, la [description](https://fr.wikipedia.org/wiki/UTF-8#Description) sur la page Wikipedia vous sera d'une grande aide, en particulier le tableau "Définition du nombre d'octets utilisés dans le codage". Les informations qui seront importantes pour notre exercice :
- Pour tout ce qui est entre 0x00 et 0x7F (première ligne du tableau), ce qui correspond aux points de code des caractères simples qui étaient dans la table ASCII, l'encodage se fera sur un unique octet dont le premier bit vaudra 0. C'est identique à un codage ASCII, ce qui explique la rétro-compatibilité UTF-8/ASCII
- Pour tout ce qui sera entre 0x80 et 0x7FF (deuxième ligne du tableau et dans laquelle entrent les caractères spéciaux issus de la question 6), l'encodage se fera sur 2 octets :
  - le premier octet commence par 110 (et a donc 5 bits de "libres") et le second commence par 10 (et a donc 6 bits de libres)
  - les 2 octets ont donc en tout 11 bits de libres
  - votre point de code va s'écrire, en binaire, séquentiellement sur ces 11 bits (avec donc des bits à 0 au début de ces 11 si besoin)

> Question 7 : Encodez ces points de code en une suite hexadécimale UTF-8.

> Question 8 : Décodez la suite hexadécimale 0xC3 0xAE 0x6C 0x65.

> Question 9 : Décodez 0x76 0xE9 0x6C 0x6F (question 4) en UTF-8. Est-ce une suite valide ?

> Question 10 : Décodez la suite hexadécimale 0xC3 0xAE 0x6C 0x65 (question 8) en ISO-8859-15.

> Question 11 (bonus) : Encodez les points de code de la question 6 en UTF-16.

> Question 12 (bonus) : Trouvez comment convertir des minuscules en majuscules. Faîtes le code dans le langage de votre choix.




Quelques points à retenir
=========================

- Une même suite d'octets peut être interprétée différemment suivant le décodage utilisé par le destinataire
- Unicode/UTF-8 ont permis d'harmoniser ces interprétations à travers le monde et sont aujourd'hui l'encodage de référence, au prix d'une complexité supérieure et d'un encodage potentiellement sur plusieurs octets.
- Une confusion persiste à cause de l'historique entre la notion de caractère et d'octet. Pendant très longtemps, un caractère valait un octet et, par exemple, le type "char" du langage C est bien l’abréviation de "character" mais signifie en réalité un octet. Vous retrouverez probablement cette simplification dans certains cours ;). Comme nous l'avons vu, ceci reste vrai si on se limite aux caractères ASCII (qu'ils soient encodés en ASCII ou en UTF-8, ils restent sur un octet) mais pas pour les caractères latins qui, en UTF-8, occuperont plusieurs octets. Le langage Java, par exemple, a des types Character et String (chaîne de caractères) bien distincts de la notion d'octet et utilisant justement nativement Unicode.
