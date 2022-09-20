CM1 Codage des caractères - Notes de cours
==========================================

Introduction
============

- Des décodages étranges
- Aperçu dans gedit des 3 fichiers, c'est le même texte
- cat des 3 fichiers, des caractères mauvais
- iconv -f iso8859-15 encodage-utf8.txt donne un autre décodage
- iconv -f utf-8 encodage-iso8859.txt retourne une erreur
- Mais alors, c'est la même chose ou pas la même chose ???
- Aperçu avec hexdump -C / hd du contenu brut :
  - Pareil sur l'essentiel
  - Différences là où sont les caractères latins (non US/ASCII)
  - Fichier UTF8 plus gros...

Des octets...
=============

- Tout n'est que binaire
- Un élément central : l'octet, 8 bits
- Compris donc entre 0 et 2<sup>8</sup> = 255 (décimal) = 0xFF (hexadécimal)
- (héritage par convention historique de ce pack de 8 bits comme groupe de base)

La notion de codage
===================

- Puisque tout est binaire, pas de lettres fondamentalement
- On peut associer une chaîne binaire à une lettre -> codage
- Plusieurs conventions, c'est une _interprétation_

ASCII
=====

- Table ASCII, étude
- Limites : caractères accentués, cédilles, idéogrammes, cyrilliques, ...

ISO, CP/Windows
===============

- Table ISO8859-15
- Table Windows1252
- Même jeu de caractères (en gros), mais pas les mêmes tables (exemple œ)...
- Alors avec des jeux de caractères différents...

Unicode
=======

- Une synthèse universelle, un répertoire de l'ensemble des glyphes
- Un procédé en deux étapes :
  - notion de point de code unicode -> une valeur
  - notion d'encodage de cette valeur -> UTF-8, UTF-16, ...
- En pratique souvent UTF-8 (qui a le bon goût d'être rétro-compatible avec l'ASCII)
- Un caractère peut prendre plusieurs octets, attention à la confusion caractère/octet et chaîne de caractères/tableau d'octets !
- (Le monde, aujourd'hui, est globalement UTF-8 et donc vos applications devront gérer des chaînes UTF-8 et non des tableaux d'octets)

_La rétro-compatibilité est très souvent présente sur les technologies qui rencontrent du succès, car c'est ce qui permet leur adoption progressive, sans incompatibilité temporaire, dans un système numérique mondial aux évolutions non synchronisées_

Exemple du mail
===============

- On s'écrit du texte
- Ce texte est encodé en binaire
- Le binaire est transmis
- Le binaire est décodé chez le destinataire
- Du texte apparaît à l'écran


Panorama du cours
=================

Déroulé :
- Codage des caractères
- Codage des entiers et réels
- Architecture d'un ordinateur
- Calcul booléen
- Histoire de l'informatique et des ordinateurs
- Histoire du libre, de l'open-source et du captif

Évaluation :
* 1/3 contrôle continu sur les comptes-rendus de TP
* 2/3 examen
