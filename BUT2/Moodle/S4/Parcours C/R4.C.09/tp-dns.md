TP DNS
======

_Compte-rendu à préparer et déposer en binôme_

Ce TP couvre la configuration et l'utilisation du DNS, à la fois côté serveurs d'autorité et de résolution.

Ce TD sera réalisé dans la VM MI-LXC disponible [ici](https://flesueur.irisa.fr/mi-lxc/images/milxc-snster-vm-2.1.0.ova). Avant de lancer la VM, il peut être nécessaire de diminuer la RAM allouée. Par défaut, la VM a 3GO : si vous avez 4GO sur votre machine physique, il vaut mieux diminuer à 2GO, voire 1.5GO pour la VM (la VM devrait fonctionner de manière correcte toujours).

> Si vous êtes sous Windows et que la VM ne fonctionne pas avec VirtualBox, vous pouvez utiliser à la place VMWare Player. Dans ce cas, il faudra cliquer sur "Retry" lors de l'import puis installer le paquet open-vm-tools-desktop dans la VM pour profiter du redimensionnement automatique du bureau (`apt install open-vm-tools-desktop` dans un shell).


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


DNSVIZ
===========

Depuis un navigateur quelconque (dans MI-LXC, dans la VM, ou sur l'hôte), allez consulter le site [https://dnsviz.net/](https://dnsviz.net/). Étudiez les noms suivants (on se contentera, sauf mention contraire, de la vue "DNSSEC" qui permet la visualisation de la chaîne DNS en plus de DNSSEC) et notez les réponses aux questions dans votre compte-rendu (question 1) :

* univ-ubs.fr
	* La zone est-elle signée (intégrité DNSSEC) ?
	* La zone est-elle bien servie par plusieurs serveurs de noms (NS) distincts ? Ces serveurs de noms sont-ils géographiquement/administrativement bien séparés ? (vous pouvez obtenir leurs IP dans l'onglet "Servers", puis regarder leurs organisations/AS sur le site [https://ipinfo.io/](https://ipinfo.io/) par exemple)
	* À quelle adresse IPv4 doivent être envoyés les mails à destination de 'contact@univ-ubs.fr' (enregistrement MX) ?
* www.univ-ubs.fr
	* L'entrée est-elle signée DNSSEC ?
	* À quelle adresse IPv4 faut-il se connecter pour accéder à 'www.univ-ubs.fr' ?
* www.google.com
	* L'entrée est-elle signée DNSSEC ?
	* Trouvez une adresse IPv6 correspondant à ce nom
* www.elysee.fr
	* Cet enregistrement contient-il certaines alertes (_warnings_) ?
	* Cet enregistrement redirige vers une autre zone. Avec quelques recherches (le nom de domaine ciblé, wikipedia, etc.), vers quelle entreprise cela redirige-t-il ? Dans quel pays est-elle basée ?
* www.ssi.gouv.fr
	* Voyez-vous la zone parente 'gouv.fr' ?
* www.enisa.europa.eu
	* L'ENISA (organisme sécurité numérique EU) est-il dans une démarche d'utilisation de DNSSEC ?
	* Est-ce bien réalisé ?
* www.bortzmeyer.org et www.cloudflare.net
	* L'entrée est-elle bien signée ?
	* Les serveurs DNS sont-ils bien répartis dans des AS différents ?
* www.whitehouse.gov et www.fbi.gov
	* Ces domaines sont-ils simples ?
	* Vous semblent-ils pour autant relativement bien construits et sécurisés ?
* www.netflix.com et www.banque-france.fr
 	* Non, rien, pas de question ici...



Focus sur le DNS dans MI-LXC
============================

Pour acquérir un nom de domaine, une organisation va s'adresser à un _registrar_ (Gandi, OVH, etc.) et en obtenir la gestion (pour une dizaine d'euros par an sur les extensions classiques). Ce registrar va ensuite enregistrer vos données auprès du _registry_ de l'extension du domaine (l'AFNIC pour .fr par exemple). C'est le registry qui est responsable de servir la zone DNS (l'AFNIC sert la zone '.fr'). Et c'est donc le serveur DNS du registry qui est pointé par la zone parente (par exemple la zone racine).

MI-LXC fournit une racine alternative qui, en plus des TLD classiques (.com, .net, .fr, etc.), intègre le TLD interne .milxc. Cette racine alternative est fournie par 2 serveurs racines spécifiques présents dans l'infrastructure MI-LXC, dans les AS root-o et root-p. Les résolveurs présents dans MI-LXC interrogent ces racines alternatives et non les racines officielles (de A à M).

> Question 2 : Dans la machine root-o-rootns, ouvrez le fichier /etc/nsd/root.zone :
>
>* Recopiez/capturez les lignes mentionnant le TLD .fr (elles commencent par 'fr.'). Trouvez ensuite (plus loin dans le fichier) les IP qui correspondent aux 'NS' (nameservers) mentionnés pour ce '.fr'.
>* Recopiez/capturez les 3 lignes correspondant au TLD .milxc. À quelle machine appartiennent les IP mentionnées sur ces lignes ?

Le TLD .milxc est ensuite opéré par le registry simulé dans l'AS milxc.

> Question 3 : Dans la machine milxc-ns, ouvrez le fichier /etc/nsd/milxc.zone et recopiez/capturez les 3 lignes mentionnant le domaine target.milxc. À quelle machine appartiennent les IP mentionnées sur ces lignes ?


Résolution en ligne de commande
===============================

Résolution manuelle
-------------------

`dig` est un outil en ligne de commande pour interroger les DNS. Il s'utilise de la façon suivante :

```
dig @80.67.169.12 www.univ-ubs.fr      # Interroge le serveur (résolveur) 80.67.169.12 pour résoudre le nom www.univ-ubs.fr
```

Le serveur racine alternatif O de MI-LXC est accessible à l'IP 100.100.0.10.

Nous allons faire la résolution manuellement depuis la machine `isp-a-home`. Commencez par démarrer wireshark sur cette machine (écoute sur eth0 puis filtre "dns" pour ne voir que les paquets qui nous intéressent). En utilisant dig depuis la machine `isp-a-home` et en interrogeant successivement chaque nœud de l'arbre DNS depuis cette racine 100.100.0.10, résolvez le nom d'hôte `www.target.milxc`. À chaque étape, retrouvez les requêtes et les réponses dans Wireshark : dans la fenêtre d'analyse (zone du milieu), dépliez la partie DNS jusqu'à trouver le contenu décodé des questions et réponses DNS.

> Question 4 : Notez le chemin de résolution vers ce nom d'hôte, les questions posées et reçues par dig auprès des différents serveurs faisant autorité, jusqu'à obtenir l'IPv4 souhaitée.

Interrogation d'un résolveur
----------------------------

Évidemment, un service intermédiaire peut s'occuper de faire cette résolution récursive automatiquement. Un résolveur ouvert offrant ce service est disponible sur la machine `opendns-resolver`, son adresse IP est `100.100.100.100`. Connectez-vous à cette machine, démarrez-y Wireshark et filtrez les paquets DNS.

Interrogez ensuite ce résolveur ouvert depuis la machine `isp-a-home` pour résoudre en un seul appel `www.target.milxc`.

> Question 5 : Notez la question posée par dig et la réponse obtenue. Retrouvez l'ensemble de l'échange dans Wireshark sur `opendns-resolver` (les messages échangés avec `isp-a-home` et les messages échangés avec les serveurs faisant autorité) et décrivez cet enchaînement de messages (explication rédigée).

> Remarque : si vous ne voyez pas de messages entre le résolveur et *plusieurs* serveurs faisant autorité (situés à différents niveaux de l'arbre), c'est que le résolveur avait déjà la réponse dans son cache. Patientez 30 secondes puis recommencez (pour des raisons pédagogiques, les durées de vie des caches DNS dans MI-LXC [TTL - Time To Live] sont volontairement très courtes).

Enregistrement d'un nom de domaine dans MI-LXC
========================================================

Enfin, dans cette partie, vous allez simuler l'enregistrement d'un nouveau domaine en .milxc, par exemple "iutva.milxc". La zone "iutva.milxc" sera servie par la machine `iutva-infra` et contiendra un enregistrement pour le nom "www.iutva.milxc".

> Si vous avez un autre AS prêt des TP précédents, vous pouvez l'utiliser à la place de iutva.

(Re)mise en place de iutva-infra
--------------------------------

La correction était [ici](https://moodle.univ-ubs.fr/course/view.php?id=8447).


Ajout d'un nouveau domaine
--------------------------

Vous allez d'abord simuler l'enregistrement du domaine "iutva.milxc". Vous jouerez ici le rôle du registry.

> Question 6 : Dans quelle zone va devoir être réalisé cet enregistrement du _domaine_ iutva.milxc ? Sur quelle machine ? Aidez-vous de vos réponses aux questions 2 et 3 (iutva.milxc va prendre une place similaire à target.milxc) et faîtes valider ce point.

Connectez-vous sur cette machine et éditez le fichier de zone pour ajouter ce "iutva.milxc". Inspirez-vous évidemment des modèles déjà présents dans ce fichier, en mettant à jour les adresses IP pour pointer vers la machine sur laquelle vous hébergerez la zone "iutva.milxc" : la machine `iutva-infra`. Enfin, relancez le serveur NSD (en root : `service nsd restart`) et assurez-vous qu'il n'y a pas d'erreur signalée dans les logs (`journalctl -xe`).

Avec dig depuis la machine `isp-a-home`, vérifiez que le serveur répond bien la réponse attendue pour "iutva.milxc".

> Question 7 : Quelles lignes avez vous ajoutées au fichier de zone ?

Configuration et création d'une zone DNS
----------------------------------------

Vous allez maintenant installer un serveur faisant autorité sur la machine `iutva-infra`. Pour installer NSD, tapez la commande `apt install nsd`.

La configuration de NSD se situe dans le dossier `/etc/nsd/`. Vous aurez 2 fichiers à créer :

* `/etc/nsd/nsd.conf.d/server.conf` pour la configuration générale (tous les fichiers `*.conf` du dossier `/etc/nsd/nsd.conf.d/` sont inclus lors du lancement)
* `/etc/nsd/iutva.milxc.zone` pour le contenu de la zone "iutva.milxc" (ce fichier sera mentionné dans `server.conf`)

Dans `server.conf`, vous déclarez quelle zone vous servez et où trouver son contenu :
```
zone:
	name: "iutva.milxc."
	zonefile: "/etc/nsd/iutva.milxc.zone"
```

Dans `iutva.milxc.zone`, vous déclarez le contenu de la zone. Vous trouverez un exemple dans le cours [ici](https://git.kaz.bzh/francois.lesueur/M3102/src/branch/master/cm3-dns.md#exemple-de-zone-dns), à réfléchir et adapter (recherches sur internet encouragées). Pour l'instant, vous aurez besoin uniquement des types d'enregistrement SOA (Start Of Authority, paramètres généraux), NS, A, et CNAME. Votre zone doit déclarer :

* votre NS
* un champ A pour le nom "infra.iutva.milxc" pointant vers l'IP de la machine `iutva-infra`
* un champ CNAME pour le nom "www.iutva.milxc" comme alias vers "infra.iutva.milxc"

Relancez le serveur NSD et vérifiez l'absence d'erreurs dans les logs (`journalctl -xe`). Validez le fonctionnement avec dig depuis `isp-a-home`. Lorsque tout fonctionne, vous devez pouvoir vous connecter à l'URL "http://www.iutva.milxc" avec Firefox depuis la machine `isp-a-home` (si vous avez bien installé le serveur web sur `iutva-infra`...)

> Question 8 : Recopiez votre fichier de zone `iutva.milxc.zone` dans votre compte-rendu.

Réplication du DNS
------------------

Afin d'obtenir un système robuste et conforme aux recommandations standards, il faut ensuite dupliquer cette zone sur un serveur secondaire, indépendant, qui serait aussi annoncé dans la zone parente .milxc. Ce serveur secondaire sera une réplique automatique du serveur principal. Vous pourrez par exemple utiliser target-dmz comme serveur secondaire.

Les mots-clés sont NSD (votre logiciel serveur DNS), primary/master pour votre serveur principal, secondary/slave pour votre serveur secondaire. Il existe de nombreuses documentations, par exemple [ici](https://www.virtualinfra.online/post/nsd-slave-master/) ou [là](https://ethitter.com/2016/01/authoritative-dns-slave-nsd-debian-wheezy/). À vous de trouver celle qui vous convient !

> Question 9 : Décrivez votre mise en place de la réplication DNS.



**Votre compte-rendu doit être déposé sur Moodle au format PDF uniquement, un dépôt par binôme.**
