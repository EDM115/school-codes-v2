# TD : Stockage et authentification par mot de passe

Ce TD présente le stockage et l'authentification par mot de passe côté serveur. Il s'agit typiquement du problème rencontré par une application web qui souhaite authentifier ses utilisateurs, ou de ce qui est mis en œuvre dans une base de mots de passes système (/etc/shadow ou LDAP). Pour cela, l'application va stocker en base les comptes existants ainsi que le moyen de les authentifier. Comme il n'est évidemment pas souhaitable de stocker les mots de passe des utilisateurs en clair, nous allons analyser comment résoudre ce problème.

> Le compte-rendu est à déposer en binôme sur Moodle au format PDF uniquement.


Contexte général
================

Le scénario d'attaque est une exfiltration des fichiers de l'application (dont le fichier de la base de données ou le fichier `/etc/shadow` par exemple). Vous pouvez consulter une liste non exhaustive de bases ayant été dérobées sur le site [HaveIBeenPwned](https://haveibeenpwned.com/PwnedWebsites) pour vous rendre compte de l'étendue du problème. Dans ce cadre, nous posons les points suivants :

* Le serveur est déjà compromis, l'obtention d'un compte valide sur ce serveur n'a pas d'intérêt pour l'attaquant.
* Les victimes potentielles sont les utilisateurs du site qui y ont enregistré un compte. En effet, un attaquant pourrait alors essayer de se connecter en leur nom sur des services tiers grâce aux informations récupérées.

Pour limiter ce risque, deux approches complémentaires doivent être mises en place :

1. Le serveur doit compliquer autant que possible la tâche de l'attaquant qui a volé la base en maximisant le temps nécessaire pour obtenir des informations valides à partir de la base (objet de ce TD).
2. Les utilisateurs, n'ayant pas la possibilité de connaître les contre-mesures mises en place par le serveur, doivent limiter l'impact de cette compromission en utilisant des mots de passes différents, idéalement un pour chaque site (ce qui passe souvent par un gestionnaire de mots de passe, non abordé dans ce TD).

Ces deux mesures sont bien complémentaires car il est du devoir de chaque site de protéger les mots de passes des utilisateurs n'appliquant pas les meilleures pratiques et de chaque utilisateur de protéger au mieux de ses capacités ses mots de passes. Dans le cadre de ce TD, nous analysons la mesure (1), à appliquer côté serveur.


Squelette de code fourni
========================

Vous devez télécharger le squelette de code [ici](td-passwords-files). Vous pourriez avoir besoin d'installer la bibliothèque python PyCryptodome (de préférence, et nécessaire avec Python 3.8) ou PyCrypto (dépréciée, mais a priori fonctionnelle jusque Python 3.7). Par exemple avec pip3 pour avoir PyCryptodome uniquement (les deux ne peuvent pas coexister sur le système) :

```
pip3 uninstall PyCrypto
pip3 install -U PyCryptodome
```

* `toolbox.py` est la bibliothèque contenant la boîte à outils, il peut être intéressant d'aller la consulter mais elle n'est pas à modifier ;
* `skeleton.py` contient le programme à écrire.

En début de TD, vous devez lancer `./skeleton.py init` pour créer votre espace de travail. Cette commande, que vous pourrez rappeler plus tard, génère les fichiers suivants (lisibles) dans le sous-dossier `files` :

* `plain` contient la base login/password en clair
* `enc` contient la base chiffrée en AES (la clé, volée également car nécessairement accessible à proximité sauf cas particulier est dans `enckey`)
* `sha` contient la base hashée avec SHA256
* `saltedsha` contient la base hashée/salée avec SHA256
* `pbkdf2` contient la base avec n itérations de hash salé (PBKDF2)

Vous pouvez visualiser tous ces fichiers dans un éditeur de texte classique et analyser les différents champs présents. Vous pouvez ensuite exécuter `./skeleton.py` (sans argument) qui affichera, pour chaque schéma de stockage :

* le contenu de la base stockée
* le résultat d'un test unitaire d'authentification (doit toujours être vrai)
* l'appel (chronométré) à une fonction pour casser la base (fonctions non implémentées dans le squelette fourni)

Attention : le squelette de base s'exécute avec succès et rapidement, mais les fonctions de cassage de mot de passe ne sont pas implémentées (ce sera votre travail) ! Pour chaque schéma, ce sera à vous de compléter la fonction ad hoc de `skeleton.py` en suivant les conseils donnés en commentaires, afin qu'elle affiche des mots de passe retrouvés lors de l'exécution.


Analyse des différents schémas
==============================

Pour chaque schéma (clair `plain`, chiffré `enc`, hash `sha`, hash salé `saltedsha`, hash salé coûteux `pbkdf2`), vous devez :

* analyser le processus de l'ajout d'un compte et de la vérification d'un mot de passe
* proposer la procédure de récupération pour un utilisateur qui a perdu son mot de passe
* évaluer l'information révélée directement par la base de mots de passe
* évaluer le coût de cassage d'un mot de passe isolé
* évaluer le coût de cassage de la base entière
* implémenter la fonction pour casser la base



Pour approfondir
================

**Les schémas vus dans ce TD, de manière similaire à ce que nous avons vu côté RSA, sont simplifiés pour comprendre le principe (textbook)**. Pour référence plus précise, vous pouvez ensuite consulter (et garder) :

* [SOPHOS : Serious Security: How to store your users’ passwords safely](https://nakedsecurity.sophos.com/2013/11/20/serious-security-how-to-store-your-users-passwords-safely/)
* [OWASP : Password Storage Cheat Sheet](https://www.owasp.org/index.php/Password_Storage_Cheat_Sheet)
* [Micode : 30 000 MOTS DE PASSE CRACKÉS EN 5 MINUTES ! (vidéo)](https://youtu.be/_1ONcmFUOxE)
* [Des exemples de dictionnaires](https://weakpass.com/wordlist)
