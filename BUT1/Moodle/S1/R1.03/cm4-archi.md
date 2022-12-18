# CM4 Architecture - Notes de cours

## J'ai quoi dans un ordinateur ?

- Carte mère
- CPU
- RAM
- Cartes d'extension
- Disques

[Architecture de Von Neumann](https://fr.wikipedia.org/wiki/Architecture_de_von_Neumann) :

- Une unité arithmétique et logique : calcul
- Une unité de contrôle : séquençage
- De la mémoire : volatile (RAM), permanente (disque), contenant à la fois du code et des données
- Des Entrées/Sorties : bus de communication (PCI-e, USB, SATA, Mémoire...), périphériques, monde extérieur

## Parlons du CPU

Un CPU c'est quoi ?

- Un ensemble de cœurs de calcul (1 à ~16)
- De la mémoire cache (différents niveaux plus ou moins partagés entre les cœurs)
- Packagés ensemble
- Soudé ou connecté (via un socket) sur la carte-mère
- (On parle de SoC si le CPU est lui-même packagé avec RAM et périphériques)

Un cœur (core) = un _pipeline_ (chaîne de traitement)

- Lié au temps de propagation/stabilisation du signal en sortie
- Concept : découper la chaîne en plusieurs sous-chaînes plus courtes (exemple Fetch, Decode, Execute)
- La fréquence / vitesse d'horloge = le temps d'une étape du pipeline
- Pourquoi s'arrêter là ?
  - Par exemple 10 sous-chaînes, 10 fois plus de traitement !
  - Jusque 31 chez Intel à une époque, aujourd'hui plutôt une quinzaine mais avec un cadre plus complexe
- La limite : les branchements / le code non-linéaire. Si on a mal exécuté, il faut vider le pipeline et reprendre la bonne chaîne (temps perdu, évidemment)
- Et donc de la prédiction, de la réorganisation (out-of-order) et de l'entremêlage (hyper-threading) pour
  - faire le bon choix le plus souvent possible
  - limiter l'impact d'un mauvais choix
- => Complexité + attaques hardware de la famille [Spectre](<https://fr.wikipedia.org/wiki/Spectre_(vuln%C3%A9rabilit%C3%A9)>)

Évolution :

- Loi de Moore : ~ doublement de la puissance tous les 18 mois
- Jusqu'au début des années 2000 : par amélioration architecturale et augmentation de la fréquence
- Depuis : par amélioration architecturale et augmentation du nombre de cœurs (fréquence stagne)
- => Exécution parallèle nécessaire pour tirer profit des architectures modernes

Quelques éléments qui caractérisent un CPU :

- Marque/Modèle (ce qui implique son architecture)
- Jeu d'instructions (architecture) / nombre de bits (32 ou 64 aujourd'hui)
- Fréquence (rythme d'exécution des instructions)
- Nombre de cœurs physiques
- Nombre de cœurs logiques (hyper-threading)
- Longueur du pipeline

Quelques exemples d'architectures :

- x86(-64) pour PC/Mac, CPUs conçus par Intel et AMD
- ARM (nombreuses versions) pour smartphones, SBC (Raspberry) et Mac modernes (Apple Silicon)
- AVR pour de nombreux Arduino, conçus par Atmel

## Un peu de mémoire

Du plus rapide et plus petit au plus lent et plus gros (on parle de [hiérarchies mémoires](https://fr.wikipedia.org/wiki/Hi%C3%A9rarchie_de_m%C3%A9moire)), avec quelques ordres de grandeur :

- Des registres :
  - À l'intérieur de l'unité de calcul du CPU
  - C'est de là que sont faits les calculs
  - Exemple x64 : 16 registres généraux de 64 bits : 1ko (1 kilooctet = 1024 octets)
- De la mémoire cache :
  - Sur le silicium du CPU mais séparé de l'unité de calcul
  - Plusieurs niveaux de cache (L1 à L3)
  - Quelques MO (jusque 20aine), les plus rapides liée à un cœur, les plus loins partagés (1 mégaoctet = 1024 kilooctets)
  - Vitesse de l'ordre de 0,5-1 To/s
- De la mémoire vive (RAM) :
  - Sur la carte mère, séparé du CPU
  - Quelques Go (environ 4 Go sur un portable de base) (1 gigaoctet = 1024 mégaoctets)
  - Vitesse de l'ordre de 5-50 GO/s
- De la mémoire de stockage (disque dur) :
  - Périphérique branché sur la carte mère (SATA, SAS, ...)
  - Quelques To (1 téraoctet = 1024 gigaoctets)
  - Vitesse de l'ordre de 100 Mo/s (disque dur), 500 Mo/s (SSD)

## Couches d'abstraction / Bas et haut niveau

Découpage en couches :

- Matériel / _Hardware_
- Système d'exploitation / _OS_
- Logiciels / _Software_
- Encapsulation (poupées russes) :
  - Virtualisation (VMWare, Virtualbox, ...) : un logiciel qui simule un matériel
  - Conteneurisation (Docker, LXC, ...) : un logiciel qui simule un système d'exploitation
  - Des langages s'exécutant dans une machine virtuelle dédiée : Java (la JVM), C# (.Net Framework), ...

Similairement des langages :

- Binaire
- Assembleur
- C
- Java, Python
