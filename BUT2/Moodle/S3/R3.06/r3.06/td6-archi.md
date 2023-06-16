TD6 + TD7 Architecture réseau et firewall (3 heures)
====================================================

_Compte-rendu à préparer et déposer en binôme_

Ce TD couvre la segmentation réseau et l'utilisation du firewall IPTables.

Ce TD sera réalisé dans la VM MI-LXC/SNSTER disponible [ici](https://flesueur.irisa.fr/mi-lxc/images/milxc-snster-vm-2.0.0pre1.ova). Avant de lancer la VM, il peut être nécessaire de diminuer la RAM allouée. Par défaut, la VM a 3GO : si vous avez 4GO sur votre machine physique, il vaut mieux diminuer à 2GO, voire 1.5GO pour la VM (la VM devrait fonctionner de manière correcte toujours).

> Si vous êtes sous Windows et que la VM ne fonctionne pas avec VirtualBox, vous pouvez utiliser à la place VMWare Player. Dans ce cas, il faudra cliquer sur "Retry" lors de l'import.

> Le compte-rendu est à déposer en binôme sur Moodle.

Avant de commencer le TP, vous devez lire le chapitre [Netfilter](https://fr.wikibooks.org/wiki/Administration_r%C3%A9seau_sous_Linux/Netfilter) du Wikilivre "Administration Réseau sous Linux". Prêtez une attention particulière au sens des 3 chaînes INPUT, OUTPUT et FORWARD.

> Question 1 : Expliquez ces 3 chaînes INPUT, OUTPUT et FORWARD : à quels types de paquets s'appliquent-elles ?

>Note 1 : Pour les plus aventuriers, il est possible d'utiliser nftables, le successeur d'iptables. Quelques infos de démarrage sont proposées [ici](https://wiki.nftables.org/wiki-nftables/index.php/Simple_rule_management).

>Note 2 : Le TP est prévu sur IPv4, mais l'infrastructure supporte également IPv6. Vous pouvez donc aussi regarder IPv6 si vous le souhaitez.


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


Topologie du routeur
====================

Connectez-vous sur la machine "target-router" : `snster attach target-router`. En effet, lors de ce TP, nous allons travailler sur `target-router` et, plus globalement, sur la segmentation de l'AS Target, notamment parce que cet AS possède déjà quelques machines et services propices à réfléchir sur ce point.

Une fois connectés sur target-router, regardez la configuration réseau avec notamment ses deux interfaces "eth0" et "eth1", par exemple avec `ifconfig` ou `ip addr`. Identifiez laquelle se situe côté interne et laquelle côté externe de l'entreprise et notez l'adresse IP de chacune.

> Question 2 : Quelles sont les interfaces et adresses IP internes et externes ?

Protection de la machine firewall
=================================

Nous allons maintenant mettre en place un firewall sur la machine "target-router" pour découvrir l'usage de l'outil de firewall IPTables. En utilisant la page de manuel d'iptables ou votre lecture préalable, affichez l'ensemble des règles actives. Vous devriez voir quelque chose qui ressemble à :

```
Chain FORWARD (policy DROP 0 packets, 0 bytes)
 pkts bytes target     prot opt in     out     source               destination         
    0     0 ACCEPT     all  --  eth0   eth1    0.0.0.0/0            100.80.1.2          
    0     0 ACCEPT     all  --  eth0   eth1    0.0.0.0/0            0.0.0.0/0            state RELATED,ESTABLISHED
    0     0 ACCEPT     all  --  eth1   eth0    0.0.0.0/0            0.0.0.0/0           

Chain INPUT (policy ACCEPT 0 packets, 0 bytes)
 pkts bytes target     prot opt in     out     source               destination         

Chain OUTPUT (policy ACCEPT 0 packets, 0 bytes)
 pkts bytes target     prot opt in     out     source               destination         
```

Vous voyez donc pour chaque chaîne :

* la "policy" (comportement par défaut) ;
* le nombre de paquets qui ont traversé les règles de la chaîne ;
* le nombre d'octets correspondants.

Pour le moment, peu de règles sont définies ; par la suite, cette commande vous permettra de lister l'ensemble des règles actives dans la table "filter".

Première règle iptables
-----------------------

Vérifiez que vous pouvez vous connecter en SSH sur la machine "target-router" depuis la machine "isp-a-hacker" (`snster attach isp-a-hacker`) :

`ssh root@100.64.0.10`

Nous allons maintenant interdire toutes les connexions sur le port 22 (SSH). Pour cela, il faut interdire dans la chaîne INPUT les paquets TCP sur le port 22 avec la cible DROP.

Appliquez la règle avec la commande iptables sur "target-router". Essayez maintenant de vous connecter en SSH sur votre machine "target-router" depuis la machine "isp-a-hacker". Vous devez constater que la connexion est bien refusée mais que le client SSH met un certain temps à s'en apercevoir.

> Question 3 : Quelle commande IPTables avez-vous tapée ?

> Question 4 : Nous avons ici utilisé l'action DROP et le client SSH met un certain temps à s'apercevoir du refus. Comprenez-vous pourquoi ? Comment changer ce comportement ?


Priorité des règles
-------------------

Un même paquet peut correspondre à plusieurs règles de filtrage, éventuellement contradictoires : Netfilter applique les règles dans l'ordre et choisit systématiquement la première règle correspondant au paquet (attention, certains firewalls procèdent dans le sens contraire tandis que celui de Windows ne prend pas en compte l'ordre...). On parle alors de masquage de règles.

Afin de tester ce comportement, nous allons utiliser les paramètres de filtrage de la [section "Critères" du Wikilivre](https://fr.wikibooks.org/wiki/Administration_r%C3%A9seau_sous_Linux/Netfilter#Crit%C3%A8res).

* Montrez sur un exemple que l'ordre des règles compte. Pour modifier le filtrage, vous aurez besoin de supprimer des règles et d'en ajouter à des endroits spécifiques : référez-vous au manuel d'iptables.
* Mettez en place un jeu de règles autorisant le SSH sur le routeur uniquement depuis le LAN de l'entreprise (testable depuis la machine "target-admin" par exemple).

Dans la pratique, le masquage est souvent utilisé volontairement pour spécifier un cas général peu prioritaire et des cas particuliers plus prioritaires. Évidemment, c'est également source d'erreurs dans ces cas complexes.

> Question 5 : Quelles commandes IPTables avez-vous tapées ?

Modules iptables
================

iptables est extensible par un système de modules. Vous trouverez une description des modules existants dans le manuel de "iptables-extensions".

Comment
-------

Le module `comment`, comme son nom l'indique, permet d'associer un commentaire à une règle afin d'assurer la bonne compréhension par tous des règles en place. Pour utiliser le module :
`iptables -A INPUT -m comment --comment "Ceci est un commentaire" -j...`

Multiport
---------

Le module multiport permet de créer une règle unique correspondant à plusieurs ports (plutôt que plusieurs règles) : `iptables -A INPUT -m multiport -p tcp --dports port1,port2,port3 -j...`

Créez une règle avec multiport autorisant les ports 22 et 53. N'hésitez pas à ajouter un commentaire pour y voir plus clair (plusieurs modules peuvent être utilisés simultanément).

Suivi de connexion ("state")
----------------------------

Netfilter permet le suivi des connexions via le module "state" (firewall _stateful_). Ce module permet d'identifier les nouveaux flux, les flux établis et les flux liés à un autre flux. Ce suivi de connexion permet d'affiner le filtrage de certains protocoles.

Le module "state" définit plusieurs états possibles pour les flux réseau, dont :

* NEW : c'est une nouvelle connexion
* ESTABLISHED : cette connexion est déjà connue (elle est passée par l'état NEW il y a peu de temps)
* RELATED : cette connexion est liée ou dépendante d'une connexion déjà ESTABLISHED. Attention, seul le premier paquet d'une connexion peut être RELATED, les suivants sont ESTABLISHED. Essentiellement utilisé pour le protocole FTP.

Par exemple, la règle déjà existante dans la chaîne FORWARD : `    0     0 ACCEPT     all  --  eth0   eth1    0.0.0.0/0            0.0.0.0/0            state RELATED,ESTABLISHED` signifie que seulement les paquets `RELATED` ou `ESTABLISHED` sont autorisés de `eth0` vers `eth1`, ie, seules les "réponses" peuvent passer dans ce sens.

Pour voir l'effet de la question suivante, tout d'abord bloquez tout en sortie avec cette politique : `iptables -P OUTPUT DROP`

Puis créez une règle pour autoriser, en sortie du firewall, uniquement les réponses à des connexions SSH entrantes (à destination du service SSH sur le firewall).

> Question 6 : Quelles commandes IPTables avez-vous tapées ?


Administration à distance
=========================

Le firewall est généralement configuré à distance, depuis le poste d'un administrateur. Pour reproduire cela, affichez le bureau de la machine target-admin (`snster display target-admin`). Depuis le bureau de cet administrateur, connectez vous en SSH au routeur, comme vu précédemment.

L'un des risques classiques, ensuite, est de scier sa branche pendant la configuration : écrivez via cette connexion SSH une règle qui bloque la connexion SSH d'administration et vous fait ainsi perdre la main sur la configuration du firewall...

> Question 7 : Proposez et exécutez une commande iptables qui bloque votre connexion SSH. Comment parvenez-vous ensuite à vous débloquer ?


Segmentation
============

Spécification
-------------

L'objectif d'une politique de sécurité réseau est de limiter les services accessibles depuis l'extérieur (approche historique) ainsi que de segmenter le réseau interne en zones distinctes (avec autorisations limitées entre ces zones, afin de limiter les risques de propagation automatique/pivot). Une telle politique se définit en trois étapes :

1. Création de zones réseau logiques via des sous-réseaux
2. Identification des services réseau portés, et accédés, par chaque machine
3. Définition des flux réseau autorisés entre zones en fonction des besoins identifiés précédemment. Ceci constitue la "matrice de flux"

**Par défaut, tout doit être interdit puis les services souhaités sont explicitement autorisés !**

> Ci-dessous un exemple de matrice de flux qui pourrait correspondre aux 2 zones initiales (int est la zone interne LAN et ext est la zone externe WAN) (insuffisante, donc, et attention ce n'est pas ça qui est implémenté par l'iptables initial) :
>
> |   src\dst  |      ext           |     int                      |
> |:----------:|:------------------:|:----------------------------:|
> |    ext     |      X             | SMTP(S),IMAP(S),HTTP(S),DNS  |
> |   int      |    tout            |        X                     |


Pour rappel, le réseau de l'entreprise est composé de ces différents éléments (en plus du routeur, qui a le rôle particulier de gérer les échanges entre les zones et que vous pouvez ici ignorer dans la définition du contenu de vos zones) :

| Machine           | Description |
| :-------:         | ----------- |
| target-admin      | Ordinateur de l'administrateur système. Il doit pouvoir administrer tout le parc en SSH. |
| target-sales | Ordinateur du commercial. Il doit pouvoir envoyer des mails, accéder à l'intranet (site web sur target-intranet) et naviguer sur le web. |
| target-dev        | Ordinateur du développeur. Il doit pouvoir envoyer des mails, mettre à jour l'intranet par SSH sur target-intranet et naviguer sur le web. |
| target-dmz        | Ensemble de services à l'interface entre le SI et le reste du monde (DNS, SMTP, IMAP, HTTP) |
| target-ldap       | Authentification centralisée, nécessaire à tous les postes du SI (dont la DMZ), en LDAP (logiciel slapd) |
| target-filer      | Partage de fichiers qui doit être accessible à tous les postes clients internes (partage en SSH) |
| target-intranet   | Applications internes, non accessibles au reste du monde |

Les noms des conteneurs peuvent être affichés avec `snster` (sans paramètres), les machines ne commençant pas par "target-" représentent le "reste du monde" (WAN). Le plan d'adressage peut être affiché avec `snster print`. Vous pouvez utiliser les commandes `netstat -laptn` ou bien `ss -lnptu` qui permettent d'afficher les ports en écoute, et donc les services, sur une machine donnée.

Votre description (matrice de flux sous forme tabulaire avec les machines sources en lignes et destinations en colonnes et services autorisés dans les cases, ou graphique) doit être claire et suffisamment précise pour être non ambiguë : un autre étudiant, avec cette description uniquement, devrait pouvoir refaire _exactement_ la même implémentation avec iptables.

> Question 8 : Décrivez sous forme de tableau (pas des commandes iptables !) une politique de sécurité réseau raisonnable pour le SI complet de l'entreprise.

> La question 8 est longue : il faut explorer le réseau, les machines, les services, comprendre les interactions et proposer une segmentation pertinente. La partie essentielle du TP s'arrête ici.

Implémentation
--------------
Une fois que la politique réseau a été précédemment définie sur le papier (phase de spécification), nous pouvons passer à l'implémentation dans les routeurs (sous-réseaux) et pares-feux (règles de filtrage).

Implémentez votre matrice de flux sur la machine "target-router". Vous aurez besoin de procéder en deux étapes :

* Segmentez le réseau "target" :
	* Éditez `~/mi-lxc/target/group.yml` pour spécifier les interfaces sur le routeur. Il faut ajouter des interfaces sur de nouveaux bridges et découper l'espace 100.80.0.1/16. Enfin, il faut ajouter les interfaces eth2, eth3... ainsi créées à la liste des `asdev` definies dans le template bgprouter de la machine router.
	* Modifiez les adresses des interfaces et les bridges des machines internes dans ce même fichier. Vous devrez aussi mettre à jour les serveurs mentionnés dans les paramètres des templates "ldapclient", "sshfs" et "resolv", soit en remplaçant les noms de serveurs par leurs nouvelles adresses IP, soit en mettant à jour les enregistrements DNS correspondants (fichier `/etc/nsd/target.milxc.zone` sur "target-dmz")
	* Exécutez `snster print` pour visualiser la topologie redéfinie
	* Exécutez `snster stop && snster renet && snster start` pour mettre à jour l'infrastructure déployée
* Implémentez de manière adaptée les commandes iptables sur la machine "target-router" (dans la chaîne FORWARD). Si possible dans un script (qui nettoie les règles au début), en cas d'erreur.

> Question 9 : Décrivez vos interfaces réseaux/leur segment et recopiez vos commandes IPTables.

Contournement de la politique
-----------------------------

Imaginez que vous êtes le développeur et que vous souhaitez fournir un accès au serveur web interne de prototypage "target-intranet" à un
client externe, alors que celui-ci n'est normalement pas accessible de l'externe ! Vous allez créer un tunnel pour contourner la politique de sécurité.
Vous disposez pour cela des machines "target-dev" (votre poste de travail interne) et "isp-a-home" (une machine extérieure, à votre domicile).

Nous allons utiliser l'outil `netcat` pour établir un tunnel très simple.

Connectez-vous sur la machine "isp-a-home". Nous allons commencer par éteindre le service _Apache_ en écoute pour libérer le port 80 qui nous sera utile
puis nous allons écouter les connexions sur le port HTTP (TCP/80).
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

> Question 10 : À l'aide d'un schéma, expliquez ce phénomène.

Il est très difficile de bloquer ou même détecter les tunnels (imaginez un tunnel chiffré par SSH, ou qui mime une apparence de HTTP, etc.)


Bonus FTP
=========

FTP, comme quelques autres protocoles, présente des difficultés particulières pour les firewalls. En effet, la partie contrôle de FTP se passe sur une connexion (et un port) distinct de la partie données. Les firewalls modernes savent créer le lien entre ces deux connexions pour y appliquer un contrôle adapté.

Un serveur FTP est installé sur "target-dmz", configurez le firewall pour permettre son usage (transfert de fichiers) depuis une machine externe au SI. Attention, le FTP demande une connexion avec un utilisateur existant, par exemple debian/debian.

**Votre compte-rendu doit être déposé sur Moodle au format PDF uniquement**
