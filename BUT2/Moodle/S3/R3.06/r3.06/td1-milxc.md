TD1 + TD2 Découverte MI-LXC/SNSTER (3h, 2 séances)
===========================================

Ce TP sera réalisé dans la VM MI-LXC/SNSTER disponible [ici](https://flesueur.irisa.fr/mi-lxc/images/milxc-snster-vm-2.0.0pre1.ova). Avant de lancer la VM, il peut être nécessaire de diminuer la RAM allouée. Par défaut, la VM a 3GO : si vous avez 4GO sur votre machine physique, il vaut mieux diminuer à 2GO, voire 1.5GO pour la VM (la VM devrait fonctionner de manière correcte toujours).

Pour vous connecter à la VM, utilisez le compte `root` avec le mot de passe `root`.

MI-LXC simule un internet minimaliste que nous utiliserons tout au long de la matière. Ce TD couvre la découverte de l'environnement existant et les premiers éléments qui seront nécessaires pour l'étendre par la suite.

> Pour les curieux, le code de MI-LXC, qui sert à générer cette VM automatiquement, est disponible avec une procédure d'installation documentée [ici](https://github.com/flesueur/mi-lxc/tree/snster). Attention, nous utilisons ici la branche SNSTER de MI-LXC, qui utilise le framework [SNSTER](https://framagit.org/flesueur/snster) en cours de mise au point.

> La VM peut être affichée en plein écran. Si cela ne fonctionne pas, il faut parfois changer la taille de fenêtre manuellement, en tirant dans l'angle inférieur droit, pour que VirtualBox détecte que le redimensionnement automatique est disponible. Il y a une case adéquate (taille d'écran automatique) dans le menu écran qui doit être cochée. Si rien ne marche, c'est parfois en redémarrant la VM que cela peut se déclencher. Mais il *faut* la VM en grand écran.

> Si vous êtes sous Windows et que la VM ne fonctionne pas avec VirtualBox, vous pouvez utiliser à la place VMWare Player. Dans ce cas, il faudra cliquer sur "Retry" lors de l'import.

> Le compte-rendu est à déposer en binôme sur Moodle avant mercredi 23 novembre soir.

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



Manipulation et navigation dans l'infrastructure pré-existante
==============================================================

Découverte du réseau
--------------------

Vous devez vous connecter à la VM en root/root. MI-LXC est déjà installé et l'infrastructure déployée, il faut avec un terminal aller dans le dossier `/root/mi-lxc`. Commencez par afficher le plan du réseau avec `snster print`. Les rectangles sont les machines et les ovales les switchs réseau. L'infrastructure déployée simule un internet minimaliste :

* un réseau de réseaux indépendants
* chaque réseau propose des services internes et expose des services externes utilisés par d'autres réseaux
* des services globaux nécessaires à l'interconnexion de l'ensemble sont déjà en place (cœur de réseau, racine DNS, ...)

Au milieu du plan, le cœur de réseau est constitué de 2 opérateurs nommés transit-a et transit-b (munis chacun d'une machine et d'un switch). D'autres organisations sont ensuite connectées à ces opérateurs afin de former globalement cet internet. Ces organisations sont classiquement construites avec une machine <organisation>-router en entrée de réseau, un switch interne et d'autres machines branchées sur ce switch interne.

> Question 1 : À partir de l'image de la topologie accessible [ici](https://github.com/flesueur/mi-lxc/blob/master/doc/topologie.png), délimitez les réseaux (les AS) et essayez de préciser leur rôle dans le système global. Vous pouvez vous aider du listing présent dans le fichier [doc/MI-IANA.fr.txt](https://github.com/flesueur/mi-lxc/blob/master/doc/MI-IANA.fr.txt) du dépôt github.


Utilisation
-----------

Pour démarrer l'infrastructure, tapez `snster start`. Ensuite pour vous connecter à une machine :

* `snster display isp-a-home` : pour afficher le bureau de la machine isp-a-home qui peut vous servir de navigateur web à l'intérieur du réseau de MI-LXC (en tant qu'utilisateur debian)
* `snster attach target-dmz` : pour obtenir un shell sur la machine target-dmz (en tant qu'utilisateur root)

Toutes les machines ont les deux comptes suivants : debian/debian et root/root (login/mot de passe).

> Question 2 : Depuis la machine isp-a-home, ouvrez un navigateur pour vous connecter à `http://www.target.milxc`. Vous accédez à une page Dokuwiki. Savez-vous sur quel serveur cette page est hébergée ?

> Question 3 : Depuis la machine isp-a-home, ouvrez un navigateur pour vous connecter au site web de l'UBS. Cela fonctionne-t-il ? Cette page est-elle hébergée dans l'infrastructure MI-LXC ?

> Question 4 : Ouvrez un shell sur la machine target-dmz (commande attach, donc). Installez le package nano grâce à l'outil `apt` et vérifiez que vous pouvez maintenant éditer des fichiers avec nano.

> Dans la VM et sur les machines MI-LXC, vous pouvez donc installer des logiciels supplémentaires. Par défaut, vous avez mousepad pour éditer des fichiers de manière graphique.

> Si la souris reste bloquée dans une fenêtre de display, appuyez sur CTRL+SHIFT pour la libérer. Un tuto vidéo de démarrage est proposé [ici](https://mi-lxc.citi-lab.fr/data/media/tuto.mp4) (attention, c'est pour une ancienne version avec des noms de commandes légèrement différents).



Extension de l'infrastructure
=============================

MI-LXC permet le prototypage rapide d'une infrastructure. Nous allons maintenant ajouter un AS à l'infrastructure existante.

Le déroulement va être le suivant :

* Déclarer un numéro d'AS, une plage d'adresses IP et un nom de domaine pour cette nouvelle organisation
* Créer cet AS minimaliste dans MI-LXC
* Ajouter un autre hôte à cet AS
* Modifier ce nouvel hôte

Déclaration d'un nouvel AS
-------------------------------

Le fichier [doc/MI-IANA.fr.txt](https://github.com/flesueur/mi-lxc/blob/snster/doc/MI-IANA.fr.txt) représente l'annuaire de l'IANA. Vous pouvez y trouver un numéro d'AS libre ainsi qu'une plage d'IP libre. Les IPv4 routables sont attribuées dans l'espace 100.64.0.0/10 (réservé au CG-NAT et donc normalement sans risque de conflit local).

Vous pouvez aussi en profiter pour prévoir un nom de domaine en .milxc, qui sera utilisé lors d'une prochaine séance.

**Faîtes valider ce plan par l'enseignant**

> Si vous n'êtes pas très à l'aise avec le choix de numéros d'AS et de plages d'IP, vous pouvez utiliser les paramètres de l'AS 9 "Evil" (cet AS n'est pas présent dans l'infrastructure fournie, ses paramètres sont donc libres).

Création de cet AS dans MI-LXC
------------------------------

Un AS est représenté par un groupe d'hôtes. La première étape est ainsi de déclarer ce nouveau groupe dans le dossier configuration, en créant un nouveau dossier dans `~/mi-lxc/` contenant la description du groupe dans un fichier `group.yml`. Par exemple, le groupe existant "gozilla", proche de vos besoins, est défini de la manière suivante :
* un sous-dossier `~/mi-lxc/gozilla`
* un fichier `~/mi-lxc/gozilla/group.yml` de description des hôtes du groupe (un routeur et une machine d'infrastructure)

Inspirez-vous de ce `group.yml` pour votre nouveau dossier en ne déclarant pour l'instant qu'un seul hôte routeur.

Les _interfaces_ de l'hôte "router" décrivent sa connexion réseau et doivent être ajustées pour votre nouveau groupe. Pour chaque interface, il faut spécifier son bridge, son ipv4 et son ipv6 (optionnelle) de manière statique ici. Dans cet exemple :
  * _transit-a_ est le bridge opéré par l'opérateur Transit-A, s'y connecter permet d'aller vers les autres AS, il faut utiliser une IP libre dans son réseau 100.64.0.1/24 et cette interface sera l'interface externe _eth0_
  * _gozilla-lan_ est le bridge interne de l'organisation gozilla, on y associe une IP de son AS. Son nom doit **impérativement** commencer par le nom du groupe + "-", ici "gozilla-", et ne pas être trop long (max 15 caractères, contrainte de nommage des interfaces réseau niveau noyau). L'interface _eth1_ doit, pour vous, être connectée à un nouveau bridge dédié à votre nouvel AS et avoir une IP de votre nouvelle plage.

Le _template_ bgprouter de l'hôte "router" décrit le paramétrage du routeur. Les champs _asn, asdev, neighbors4, neighbors6_ et _interfaces_ doivent également être ajustés pour votre nouveau groupe :

* _asn_ est le numéro d'AS, tel que déclaré dans `MI-IANA.fr.txt`
* _asdev_ est l'interface réseau qui sera reliée au réseau _interne_ de l'organisation (celle qui a les IP liées à l'AS, ce sera eth1 pour vous comme dans l'exemple)
* _neighbors4_ sont les pairs BGP4 pour le routage IPv4 (au format _IP\_du\_pair as ASN\_du\_pair_)
* _neighbors6_ sont les pairs BGP6 pour le routage IPv6 (optionnel, au format _IP\_du\_pair as ASN\_du\_pair_)

Pour intégrer votre nouvel AS, il faudra donc choisir à quel point de transit le connecter et avec quelle IP. Un `snster print` vous donne une vue générale des connexions et IP utilisées (tant que le YAML est bien formé...).

Une fois ceci défini, un `snster print` pour vérifier la topologie, puis `snster create` permet de créer la machine routeur associée à cet AS (ce sera un Alpine Linux). L'opération create est paresseuse, elle ne crée que les conteneurs non existants et sera donc rapide.

> **DANGER ZONE** On va détruire un conteneur et uniquement un. Si vous faîtes une fausse manipulation, vous risquez de détruire l'infra complète et de mettre ensuite 15 minutes à tout reconstruire, ce n'est pas le but. Donc spécifiez bien le nom du conteneur à détruire et, si vous êtes dans une VM, ça peut être le moment de faire un snapshot...

À ce moment, par contre, le pair BGP (l'autre bout du tunnel BGP, par exemple le conteneur transit-a-router) ne connaît pas encore ce nouveau routeur. Il faut également déclarer ce nouveau pair de l'autre côté du tunnel BGP (ici, ce routeur du groupe "gozilla" est par exemple listé dans les pairs BGP du groupe "transit-a", défini dans le fichier `~/mi-lxc/transit-a/group.yml` : il *faut* mettre à jour la liste des voisins de transit-a-router dans ce fichier !). Il faut ensuite le détruire et le re-générer : `snster destroy transit-a-router` (détruit _uniquement_ le conteneur transit-a-router) puis `snster create` pour le re-générer.

On peut enfin faire un `snster start` et vérifier le bon démarrage.

> Sur le routeur ou ses voisins BGP, les commandes `birdc show route all` et `birdc show protocols` permettent d'inspecter les tables de routage et vérifier l'établissement des sessions BGP.

> Question 5 : Quels ajouts avez-vous fait dans `group.yml` de votre nouveau groupe ?

> Question 6 : Vérifiez les sorties de `birdc show route all` et `birdc show protocols` de chaque côté de la connexion que vous avez rajoutée. Dans protocols, tout doit être "Established". Mettez des screenshots des sorties "protocols" dans votre compte-rendu.


Ajout d'un hôte dans le nouvel AS
--------------------------------------

Nous allons maintenant ajouter un nouvel hôte dans cet AS. Dans le dossier du groupe, nous allons compléter le fichier `group.yml` qui décrit la topologie interne du groupe.

> Si vous explorez les autres groupes, vous verrez qu'en plus du `group.yml`, il y a un sous-dossier par machine pour le provisionning de chacun de ces hôtes. Ce sont les "recettes" de création de chaque hôte pré-existant. Vous n'aurez pas à procéder comme cela de votre côté mais les recettes des autres hôtes pourront parfois vous aider durant de prochaines séances.

En vous inspirant de `~/mi-lxc/gozilla/group.yml`, ajoutez un second hôte à votre groupe. Ce YAML définit :
* qu'il y a un conteneur qui s'appelle infra
* qu'il a une interface réseau branchée sur le bridge _gozilla-lan_ avec les IP spécifiées
* que sa passerelle IPv4 est 100.83.0.1
* qu'il utilise un template "resolv" (nous ne détaillerons pas, il vous faudra utiliser le même) qui désactive le DHCP et fixe le domaine et le serveur DNS

Une fois tout ceci fait, on peut faire `snster print` pour vérifier que le YAML est bien formé et que la topologie est conforme. Un `snster create` créera ce conteneur, puis `snster start` le lancera (inutile d'avoir stoppé les autres avant).

> Question 7 : Quel contenu dans `group.yml` ?


Modification de cet hôte
-----------------------------

Maintenant que cet hôte est créé, nous allons le modifier. Il s'agit d'une Debian basique sur laquelle il est donc possible d'ajouter des paquets, les configurer, etc. Installez le paquet nano et vérifiez son fonctionnement. Ouvrez enfin un display sur cette nouvelle machine et vérifiez que vous pouvez naviguer avec Firefox vers `http://www.target.milxc` et vers un site internet externe à MI-LXC.

Enfin, vous pouvez éteindre proprement MI-LXC en tapant `snster stop` et en arrêtant la VM (proprement également). Vos changements sont persistants : rallumez la VM, redémarrez MI-LXC et vérifiez que nano est toujours disponible sur votre nouvelle machine.

> Question 8 : Montrez avec une capture d'écran que l'installation a bien fonctionné et que vous pouvez bien accéder à 'http://www.target.milxc' depuis votre nouvel hôte.


Organisation pour la suite de la matière
----------------------------------------

L'infrastructure pré-existante suit le paradigme de l'_infrastructure-as-code_, c'est à dire que la topologie, les installations et les configurations sont _programmées_ (les yaml que vous avez manipulé ainsi que les `provision.sh`, par exemple dans `~/mi-lxc/target/dmz/provision.sh`). Cela permet de sauvegarder/versionner les recettes et de facilement regénérer des hôtes en cas de mauvaise manipulation. La contrepartie est de programmer les configurations plutôt que de juste les faire.

A priori, vous n'utiliserez pas ces fonctionnalités (ce n'est en tous cas pas exigé). Vos configurations seront persistantes mais vous ne pourrez donc pas facilement revenir sur des erreurs de manipulation sur les hôtes rajoutés. Au-delà des comptes-rendus, vous avez donc tout intérêt à documenter vos actions.

**Votre compte-rendu doit être déposé sur Moodle avant mercredi 23 novembre soir au format PDF uniquement**
