TP1 Encodage binaire vers texte (1h30)
==============================

Nous partitionnons classiquement en informatique les fichiers entre fichiers textes et fichiers binaires. Les fichiers texte contiennent uniquement des caractères (essentiellement des lettres minuscules, majuscules, chiffres et ponctuations, on parle souvent de _texte brut_) ; les fichiers binaires sont le reste des fichiers (ie, ceux qui ne sont pas du texte). Comme, évidemment, tout ceci n'est composé que de 0 et de 1, la réalité est un peu plus complexe mais cette simplification nous suffira pour aujourd'hui.

Dans ce TP, vous allez concevoir et analyser un système d'encodage du binaire vers du "texte", ainsi que le décodage associé.

> Si vous souhaitez approfondir les notions après le TP, les pages Wikipedia associées : [fichier texte](https://fr.wikipedia.org/wiki/Fichier_texte), [fichier binaire](https://fr.wikipedia.org/wiki/Fichier_binaire), [conversion binaire/texte](https://fr.wikipedia.org/wiki/Conversion_du_binaire_en_texte).

> Ce TP est à réaliser en binôme. Un compte-rendu devra être déposé sur Moodle, en format PDF uniquement. Un seul compte-rendu par binôme suffira (pensez bien à mettre les 2 noms sur le document !)

Mais pourquoi donc ?
====================

Nous avons vu en cours l'exemple du mail (format MIME). L'usage est en fait plus général. D'une donnée binaire (rentrent dans ce cadre : une photo, un son, une vidéo, un document de traitement de texte, un programme, ...), il peut être nécessaire de la transmettre sur un canal qui a été conçu pour faire communiquer des humain·es, et n'est donc adapté qu'à échanger du _texte brut_ : un e-mail, un document papier, ...

Le second objectif est de faire un premier pas dans la logique des encodages/décodages que vous allez souvent rencontrer en informatique : il faut comprendre que c'est notre solution pour représenter (et donc stocker/transmettre) des informations plus expressives que '0' et '1'; et il faut aussi découvrir qu'il existe généralement plusieurs encodages utilisés pour un même problème (souvent incompatibles).

Encodage
========

Votre ensemble de départ sera des bits, ce qui est l'élément de base utilisé par un ordinateur.

> Question 1 : Définissez un ensemble d'arrivée parmi des caractères texte. Un caractère permettra d'encoder un groupe de bits.
> Conseil : Choisissez un ensemble de taille d'une puissance de 2 (4, 8, 16, 32 ou 64 éléments)
> Contraintes : Choisissez un ensemble de taille supérieure à 2 (pas juste 0 et 1), composé uniquement de caractères textuels et différent des représentations classiques de l'octal (chiffres de 0 à 7) et de l'hexadécimal (0 à FF).

> Question 2 : Définissez le mécanisme permettant de passer d'une suite de bits de votre ensemble d'entrée à une suite de caractères de votre ensemble d'arrivée.

Transmission sur un canal texte
===============================

Encodez à la main un fichier binaire court de quelques octets, écrivez cet encodage binaire sur un papier et donnez le à un autre binôme du groupe. Arrangez-vous pour que chaque binôme reçoive un message.

> Rappel : un octet est composé de 8 bits, votre fichier d'entrée sera donc composé d'un multiple de 8 bits. Votre encodage doit pouvoir gérer un nombre d'octets quelconques en entrée, quitte à rajouter du _bourrage_ qui devra(it) être clairement reconnaissable.

Continuez ensuite sur la [Partie 2](tp1-encodage-p2.md)
