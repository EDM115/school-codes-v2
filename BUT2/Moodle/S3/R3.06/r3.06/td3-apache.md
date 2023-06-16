TD3 + TD4 + TD5 Apache/Tunnels/CMS (4h30, 3 séances)
============================================

_Compte-rendu à préparer et déposer en binôme_

Ce TD couvre la configuration et l'utilisation d'un serveur HTTP Apache, les tunnels ainsi que l'installation d'un CMS Wordpress. Vous utiliserez Wireshark tout au long de ce TD pour analyser les échanges réseau.

Ce TD sera réalisé dans la VM MI-LXC/SNSTER disponible [ici](https://flesueur.irisa.fr/mi-lxc/images/milxc-snster-vm-2.0.0pre1.ova). Avant de lancer la VM, il peut être nécessaire de diminuer la RAM allouée. Par défaut, la VM a 3GO : si vous avez 4GO sur votre machine physique, il vaut mieux diminuer à 2GO, voire 1.5GO pour la VM (la VM devrait fonctionner de manière correcte toujours).

> Si vous êtes sous Windows et que la VM ne fonctionne pas avec VirtualBox, vous pouvez utiliser à la place VMWare Player. Dans ce cas, il faudra cliquer sur "Retry" lors de l'import.

> Le compte-rendu est à déposer en binôme sur Moodle.


Cheat sheet
===========

Voici un petit résumé des commandes dont vous aurez besoin :

| Commande | Description | Utilisation |
| -------- | ----------- | ----------- |
| print    | Génère la cartographie du réseau | snster print |
| attach   | Permet d'avoir un shell sur une machine | snster attach target-sales |
| display  | Lance un affichage sur la machine cible | snster display target-sales |
| start    | Lance la construction du bac à sable    | snster start |
| stop     | Éteint la plateforme pédagogique        | snster stop |

Rappel: Vous devez être dans le répertoire `/root/mi-lxc/` pour exécuter ces commandes.


Conception
==========

Vous allez étudier un serveur LAMP :
* Linux
* Apache
* MariaDB (MySQL)
* PHP

> Question 1 : Choisissez un serveur pour installer/configurer cette pile LAMP (par exemple votre machine infra ou gozilla-infra). Choisissez une machine pour tester l'utilisation en tant que client (par exemple isp-a-home). Quelles sont les adresses IP de ces deux machines ?


Apache - Serveur HTTP
=====================

Un serveur Apache est déjà installé par défaut. Validez que la connexion à l'Apache du serveur retenu dans la question 1 fonctionne bien avec Firefox depuis le poste client choisi.

Wireshark
---------

Étudiez la connexion avec Wireshark depuis le poste client (en session graphique "display"). Dans wireshark, lancez la capture sur eth0, puis vous pouvez utiliser le filtre "http" pour n'afficher que ce qui a trait au HTTP.

> Question 2 : Décrivez en quelques lignes la connexion HTTP entre le client et le serveur (les quelques échanges ou une capture d'écran de la zone wireshark montrant cet échange)

Connectez-vous ensuite avec deux logiciels clients en ligne de commande (cherchez sur internet comment faire) :

* curl ou wget d'une part
* netcat (binaire "nc", à lancer avec l'option -C) d'autre part

> Question 3 : Quelles commandes ont permis d'obtenir l'index ? À quoi sert l'option -C de netcat ?


Ajout de pages HTML
-----------------------

La racine du serveur est dans le dossier `/var/www/html`, c'est-à-dire que le contenu de ce dossier est celui servi par le serveur Apache. Retrouvez le fichier d'index que vous aviez précédemment reçu côté client.

Créez un sous-dossier dans `/var/www/html` et ajoutez-y un second fichier html. Récupérez ce nouveau fichier depuis un client.


Contrôle d'accès
----------------

Nous allons maintenant contrôler l'accès au sous-dossier créé (par login/mot de passe).

Vous devez tout d'abord créer un fichier `.htpasswd` qui va contenir les couples login/mot de passe autorisés. Vous pouvez par exemple le placer dans un dossier (à créer) `/etc/apache2/htpasswd/` (pour des raisons de sécurité, on évitera de le placer dans un dossier servi par Apache comme `/var/www/html/...`). Vous utiliserez pour cela la commande `htpasswd` en faisant attention à utiliser la fonction de hachage bcrypt (une option à htpasswd) au lieu du MD5 utilisé par défaut (Explication [ici](https://nakedsecurity.sophos.com/2013/11/20/serious-security-how-to-store-your-users-passwords-safely/), pas à lire en TD mais pour les curieux).

> Question 4 : Quelles commandes avez-vous tapées ? Quel est votre fichier .htpasswd résultat ?

Vous devez ensuite spécifier quel dossier doit être protégé. Vous trouverez le nécessaire dans la [documentation officielle](https://httpd.apache.org/docs/2.4/fr/howto/auth.html), à parcourir jusqu'à la section "Autorisation d'accès à plusieurs personnes" (incluse). Lisez attentivement la partie sur les prérequis, votre fichier de configuration apache2 est `/etc/apache2/apache2.conf` (et non `httpd.conf` comme mentionné à certains endroits de cette documentation).

Vous devez réaliser ce contrôle de 2 façons distinctes :
* D'abord en intégrant directement les directives `Auth*` dans une section `<Directory>...</Directory>`, par exemple en vous inspirant de la section concernant `/var/www/` dans le fichier de configuration `/etc/apache2/apache2.conf`. Attention à bien exécuter un `systemctl reload apache2` pour prendre en compte les changements dans les `.conf`
* Ensuite en intégrant un `AllowOverride` dans la section concernant ce même répertoire puis en plaçant un fichier `.htaccess` dans le dossier `/var/www` (AllowOverride est décrit dans la partie "Les prérequis" de la page de documentation officielle)

 Validez chaque fonctionnement en vérifiant que l'authentification est bien demandée au client lors d'une connexion graphique avec Firefox.

> Question 5 : Quelles modifications/ajouts avez-vous fait ?

> Question 6 : Retrouvez dans Wireshark la phase d'authentification.


Tunnels
=======

En utilisant des tunnels, vous allez voir comment cacher une connexion (par exemple HTTP) dans une autre connexion (par exemple SSH)

SSH
---

L'outil ssh permet de réaliser des tunnels avec ses options -L (Local) et -R (Remote). Deux exemples :
* `ssh -L 8080:192.168.1.2:80 192.168.2.4`:
  * La machine locale ouvre une connexion SSH vers la machine 192.168.2.4
  * La machine locale ouvre le port 8080 en écoute
  * Tout ce qui entre localement sur ce port 8080 emprunte le tunnel SSH jusqu'à 192.168.2.4 puis la machine 192.168.2.4 route ces paquets vers 192.168.1.2 sur le port 80
* `ssh -R 8080:192.168.1.2:80 192.168.2.4` est symétrique :
  * La machine locale ouvre une connexion SSH vers la machine 192.168.2.4
  * La machine 192.168.2.4 ouvre le port 8080 en écoute
  * Tout ce qui entre sur 192.168.2.4 sur ce port 8080 emprunte le tunnel SSH jusqu'au client SSH puis ce client SSH route ces paquets vers 192.168.1.2 sur le port 80

Vous allez mettre en place deux tunnels SSH, chacun depuis target-dev vers isp-a-home :
* Dans le premier, vous utiliserez -L pour qu'un `curl localhost:8080` exécuté sur target-dev récupère la page sur le serveur web (port 80) de 100.81.0.2 (un site externe dont on aurait souhaité interdire l'accès depuis target)
* Dans le second, vous utiliserez -R pour qu'un `curl localhost:8080` exécuté sur isp-a-home récupère la page sur le serveur web (port 80) de 100.80.0.5 (l'intranet de target)

> Question 7 : Recopiez les commandes ssh exécutées.

> Question 8 : Utilisez Wireshark (avec le filtre ssh ou http) pour afficher les paquets SSH entre target-dev et isp-a-home et les paquets HTTP vers 100.81.0.2 et 100.80.0.5.

> [Doc externe](https://iximiuz.com/en/posts/ssh-tunnels/)

Netcat
------

Imaginez que vous êtes le développeur et que vous souhaitez fournir un accès au serveur web interne de prototypage "target-intranet" à un client externe, alors que celui-ci n'est normalement pas accessible de l'externe ! Vous allez créer un tunnel pour contourner la politique de sécurité. Vous disposez pour cela des machines "target-dev" (votre poste de travail interne) et "isp-a-home" (une machine extérieure, à votre domicile).

Nous allons utiliser l'outil `netcat` pour établir un tunnel très simple.

Connectez-vous sur la machine "isp-a-home". Nous allons commencer par éteindre le service _Apache_ en écoute pour libérer le port 80 qui nous sera utile puis nous allons écouter les connexions sur le port HTTP (TCP/80).
```bash
service apache2 stop
while true; do nc -v -l -p 80 -c "nc -l -p 8080"; done
```

Enfin, côté "target-dev", nous mettons en place la connexion sortante vers la machine distante:
```bash
while true; do nc -v 100.120.0.3 80 -c "nc 100.80.0.5 80"; sleep 2; done
```

>Pour rappel :
>* 100.120.0.3 = isp-a-home
>* 100.80.0.5 = target-intranet

Testez avec la machine "isp-a-hacker" que vous pouvez bien accéder au serveur intranet depuis l'externe sans aucun contrôle via l'URL `http://100.120.0.3:8080`

> Question 9 : À l'aide d'un schéma, expliquez ce tunnel.

> Question 10 : Retrouvez-le dans Wireshark

Il est très difficile de bloquer ou même détecter les tunnels (tunnel chiffré par SSH, ou qui mime une apparence de HTTP, etc.)


PHP (Bonus)
===

Ajoutez un fichier PHP (un simple hello world) dans le dossier `/var/www/html` puis récupérez-le depuis un client : que constatez-vous ?

Le paquet PHP pour Apache est libapache2-mod-php. Installez-le et validez l'interprétation de votre fichier PHP.

> Question 11 : Faîtes une capture d'écran de votre code source PHP et du résultat dans un navigateur.


MariaDB (Bonus)
=======

MariaDB est un fork de MySQL que nous allons utiliser ici, avec PHPMyAdmin pour aider l'administration (création de bases, d'utilisateurs, etc.).

Installez d'abord mariadb-server. Pour finaliser l'installation, exécutez `mysql_secure_installation`, qui permettra de mieux configurer MariaDB et de spécifier un mot de passe root (par défaut vide et non autorisé).

Installez ensuite phpmyadmin (faîtes bien cela *après* avoir installé mariadb-server, dans un autre apt-get, sinon il y aura une erreur). Lors de l'installation de phpmyadmin, vous aurez à répondre à quelques questions : utilisez la fonctionnalité dbconfig, notez le mot de passe que vous choisirez pour l'utilisateur phpmyadmin et activez la configuration pour apache2.

> Question 12 : Faîtes une capture d'écran de PHPMyAdmin connecté en tant que root


WordPress (Bonus)
=========

Enfin, nous allons installer le CMS WordPress. Un CMS (Content Management System) permet de gérer du contenu. WordPress est un projet libre qui va s'exécuter sur notre pile LAMP.

Vous pouvez procéder de deux façons différentes :

* Installer le paquet debian "wordpress", présent dans les dépôts, puis suivre la documentation associée sur le [wiki Debian](https://wiki.debian.org/WordPress) (configuration apache, création de la base de données, configuration wordpress);
* Ou suivre la documentation du site [WordPress](https://fr.wordpress.org/txt-install/) (téléchargement et extraction d'un tar.gz, configuration apache, création de la base de données, configuration wordpress).

Enfin, installez un thème, un plugin et écrivez un premier billet

> Question 13 : Faîtes une capture d'écran de votre WordPress fonctionnel

> Question 14 : Comme tout logiciel (que ce soit un binaire compilé depuis un code source C comme le serveur HTTPD Apache ou un code source PHP interprété à l'exécution), WordPress doit être tenu à jour afin d'installer les correctifs de sécurité. Pour chacune des méthodes d'installation, comment se passera ce mécanisme de mise à jour ? Pouvez-vous trouver des avantages et inconvénients à ces méthodes ?

**Votre compte-rendu doit être déposé sur Moodle au format PDF uniquement**
