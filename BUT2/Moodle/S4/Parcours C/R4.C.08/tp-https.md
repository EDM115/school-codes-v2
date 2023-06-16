# TP : Autorités de certification, authentification mutuelle et HTTPS

Ce TP présente le modèle des autorités de certification et l'applique au protocole HTTPS.

Ce TD sera réalisé dans la VM MI-LXC/SNSTER disponible [ici](https://flesueur.irisa.fr/mi-lxc/images/milxc-snster-vm-2.1.0.ova). Avant de lancer la VM, il peut être nécessaire de diminuer la RAM allouée. Par défaut, la VM a 3GO : si vous avez 4GO sur votre machine physique, il vaut mieux diminuer à 2GO, voire 1.5GO pour la VM (la VM devrait fonctionner de manière correcte toujours).

Pour vous connecter à la VM, utilisez le compte `root` avec le mot de passe `root`.

L'infrastructure déployée simule plusieurs postes dont le SI de l'entreprise _target_ (routeur, serveur web, poste admin), le SI de l'autorité de certification _mica_, un AS d'attaquant _ecorp_ et quelques autres servant à l'intégration de l'ensemble.

**L'objectif du TP est de permettre à la machine isp-a-home de naviguer de manière sécurisée sur le site `www.target.milxc` (hébergé sur la machine target-dmz) et à ce site de faire une authentification forte de ses clients.**

> Pour les curieux, le code de MI-LXC, qui sert à générer cette VM automatiquement, est disponible avec une procédure d'installation documentée [ici](https://github.com/flesueur/mi-lxc/tree/snster). Attention, nous utilisons ici la branche SNSTER de MI-LXC, qui utilise le framework [SNSTER](https://framagit.org/flesueur/snster) en cours de mise au point.

> Si vous êtes sous Windows et que la VM ne fonctionne pas avec VirtualBox, vous pouvez utiliser à la place VMWare Player. Dans ce cas, il faudra cliquer sur "Retry" lors de l'import.

> Le compte-rendu est à déposer en binôme sur Moodle. Il n'y a pas de questions explicites, votre compte-rendu doit décrire vos manipulations et apporter le contexte nécessaire à toutes les étapes réalisées.



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

> Si la souris reste bloquée dans une fenêtre virtuelle, appuyez sur CTRL+SHIFT pour la libérer.

> Dans la VM et sur les machines MI-LXC, vous pouvez installer des logiciels supplémentaires. Par défaut, vous avez mousepad pour éditer des fichiers de manière graphique. La VM peut être affichée en plein écran. Si cela ne fonctionne pas, il faut parfois changer la taille de fenêtre manuellement, en tirant dans l'angle inférieur droit, pour que VirtualBox détecte que le redimensionnement automatique est disponible. Il y a une case adéquate (taille d'écran automatique) dans le menu écran qui doit être cochée. Si rien ne marche, c'est parfois en redémarrant la VM que cela peut se déclencher. Mais il *faut* la VM en plein écran.


Connexion en clair
==================

Depuis la machine isp-a-home, ouvrez un navigateur pour vous connecter à `http://www.target.milxc`. Vous accédez à une page Dokuwiki, qui est bien la page attendue.

Nous allons maintenant attaquer depuis l'AS ecorp cette communication en clair, non sécurisée, entre isp-a-home et target-dmz. L'objectif est que le navigateur, lorsqu'il souhaite se connecter à l'URL `http://www.target.milxc`, arrive en fait sur la machine ecorp-infra. Deux pistes peuvent être explorées :

* Attaque DNS qui, via le registrar, consisterait à altérer l'enregistrement DNS pour target.milxc dans la zone du TLD .milxc. Sur la machine milxc-ns :
	* Altération de `/etc/nsd/milxc.zone` pour diriger les requêtes DNS pour `target.milxc` vers 100.81.0.2 (appartenant à ecorp)
	* Puis `service nsd restart` (le DNS de ecorp est déjà configuré pour répondre aux requêtes pour target.milxc)
* Attaque BGP qui consisterait à dérouter les paquets à destination de l'AS target vers l'AS ecorp (un [exemple de BGP hijacking réel en 2020](https://radar.qrator.net/blog/as1221-hijacking-266asns), une [doc annexe](https://www.orange-business.com/fr/blogs/securite/actualites/detournement-de-trafic-internet-via-protocole-bgp-les-bases-partie-13), [ici](https://www.coinbase.com/blog/celer-bridge-incident-analysis) et [là](https://medium.com/s2wblog/post-mortem-of-klayswap-incident-through-bgp-hijacking-en-3ed7e33de600)) :
	* Sur la machine ecorp-router : prendre une IP de l'AS target qui déclenchera l'annonce du réseau en BGP (`ifconfig eth1:0 100.80.1.1 netmask 255.255.255.0`)
	* Sur la machine ecorp-infra : prendre l'IP de `www.target.milxc` (`ifconfig eth0:0 100.80.1.2 netmask 255.255.255.0`)

Nous constatons ainsi le cas d'attaque que nous souhaitons détecter : un utilisateur sur isp-a-home qui, en tapant l'URL `www.target.milxc`, arrive en fait sur un autre service que celui attendu. Remettez le système en bon ordre de marche pour continuer (pour DNS, remettre la bonne IP 100.80.1.2 ; pour BGP, désactivez l'interface eth1:0 sur ecorp-router `ifconfig eth1:0 down`).

> Question 1 : Décrivez l'attaque (ou les) que vous avez réalisée (commandes, effets, raisons)

> Le site [Is BGP safe yet?](https://isbgpsafeyet.com/), opéré par Cloudflare, décrit de manière très claire ces attaques BGP, et donne une synthèse de l'état actuel de la sécurité de BGP et du déploiement de RPKI, contre-mesure à ces attaques BGP.

Création d'une CA
=================

Pour sécuriser les communications vers `www.target.milxc`, nous allons créer, déployer et utiliser une CA. Cette CA sera hébergée dans l'AS mica et manipulée sur la machine mica-infra (son nom DNS dans l'infra sera `www.mica.milxc`). Nous utiliserons le protocole ACME (celui de Let's Encrypt) pour l'opération de la CA (challenges, émission des certificats) via la suite d'outils de Smallstep.

Dans un premier temps, il faut initialiser une nouvelle CA en tant que root (création d'une paire de clés, d'un certificat racine, etc.) ([doc](https://smallstep.com/docs/step-ca/getting-started)) :

	# step ca init                  <- le # dénote une commande shell à taper en root

Pour cette initialisation, les paramètres ont peu d'importance (l'essentiel est la création du matériel cryptographique) et les choix suggérés par défaut à chaque question seront suffisants. Il faut juste faire attention aux questions :
* Faire une installation "Standalone"
* "What DNS names or IP addresses would you like to add to your new CA?", à laquelle il faut répondre "www.mica.milxc"
* "What address will your new CA listen at?", à laquelle il faut bien répondre ":443" (comme suggéré par défaut) et non "127.0.0.1:8443" (comme suggéré dans la doc liée précédemment). La CA doit, dans notre cas, écouter sur l'interface réseau externe et non sur localhost pour permettre l'émission du certificat de target-dmz dans la partie suivante.
* "What do you want your password to be?", à laquelle il est préférable de choisir un mot de passe vous-mêmes

Dans un second temps, il faut activer le protocole ACME pour cette CA ([doc](https://smallstep.com/docs/tutorials/acme-challenge), le protocole ACME est responsable des défis/réponse pour la génération automatique des certificats) : <!-- (https://smallstep.com/blog/private-acme-server/)-->

	# step ca provisioner add acme --type ACME

Il faut démarrer le serveur de la CA (il doit rester actif pour la suite du TP) :

	# step-ca .step/config/ca.json

> La commande step-ca est bloquante, soit vous la mettez en arrière plan avec Ctrl+z puis `bg`, soit vous ouvrez ensuite un autre terminal. Ne la lancez pas avec un &, elle doit en effet demander au lancement le mot de passe de la clé privée, ce qu'elle ne pourrait pas faire lancée avec un &.

Vous aurez besoin du certificat public de la racine par la suite. Le plus simple est de le diffuser par le site web de la CA comme suit. Extrayez-le grâce à la commande suivante :

	# step ca root root.crt         <- ceci extrait le certificat racine vers le fichier root.crt

Puis copiez-le vers `/var/www/html` (avec les droits permettant sa lecture par le serveur web, donc `chmod 644 /var/www/html/root.crt`). Il sera ainsi accessible depuis toutes les autres machines par l'URL `http://www.mica.milxc/root.crt`.



> Si, après avoir affiché à l'écran un document chiffré (par exemple avec la commande `cat`), votre terminal affiche de mauvais caractères, utilisez la combinaison de touches `Ctrl+v, Ctrl+o` pour retrouver un affichage fonctionnel (ou tapez `reset`).

> Pour reprendre la configuration à 0, il faut supprimer le dossier `/root/.step` sur la machine mica-infra

> Question 2 : Décrivez la création de la CA


Intégration de la CA à l'écosystème HTTPS
=========================================

Pour que la CA soit opérationnelle, il faut qu'elle soit reconnue par les clients HTTPS, ie, les navigateurs web (Firefox ici). Cette reconnaissance, dans le cas d'une CA globale, passe par l'intégration du certificat racine dans le magasin de certificats fourni avec le navigateur, donc par l'éditeur de ce navigateur.

> En entreprise, on rencontre souvent une CA locale qui est ajoutée localement au magasin de certificats. Dans ce TP, nous étudions le fonctionnement général de HTTPS global à travers le monde et non un déploiement à l'échelle locale.

Vous devez pour cela :
* Passer le filtre des éditeurs de navigateurs et les convaincre de reconnaître votre CA. Il s'agit bien évidemment d'une opération complexe, longue, coûteuse et rare. Ici, nous la simulerons chez l'éditeur de navigateur Gozilla. La machine `gozilla-infra` peut intégrer un certificat préalablement téléchargé au trousseau par défaut avec la commande `addcatofox.sh <certificate>` . Une fois cette commande exécutée, la distribution du navigateur (ou de ses mises à jour) intégrera ce nouveau certificat.
* Déclencher la mise à jour du navigateur par le client, en exécutant `updatefox.sh` en tant que root sur la machine `isp-a-home`

La nouvelle CA est ainsi devenue une CA par défaut, reconnue globalement. Vérifiez, après avoir redémarré Firefox, que vous la retrouvez bien dans le magasin de certiticats de Firefox.

> Question 3 : Décrivez l'intégration de la CA


Certification du serveur target-dmz
==================================

Sur l'AS target, vous disposez du serveur target-dmz sur lequel il faut déployer du matériel cryptographique pour faire du HTTPS. Vous devrez notamment :

* Générer une paire de clés et obtenir le certificat correspondant depuis la CA MICA, les clés arrivent dans `/etc/letsencrypt/live/www.target.milxc/` (les erreurs de certbot de type "InsecureRequestWarning" peuvent être ignorées, il faut par contre vérifier que son message final confirme bien la création des clés attendues). Durant cette étape, le serveur MICA va transmettre à certbot des défis (sous forme de chaîne aléatoire) puis va venir les requêter via le nom d'hôte demandé dans le certificat. Si cette requête fonctionne, cela montre que le client certbot est bien situé sur le serveur pour lequel il demande un certificat :

		# certbot certonly -n --apache -d www.target.milxc --server https://www.mica.milxc/acme/acme/directory

* Configurer le matériel cryptographique de ce nouveau site dans le fichier `/etc/apache2/sites-enabled/default-ssl.conf` (vous devrez utiliser la chaîne complète de certificats depuis la racine, c'est-à-dire `fullchain.pem`, et la clé `privkey.pem`).
* Vous devez redémarrer le serveur apache2 après vos modifications : `service apache2 restart`

Connectez-vous maintenant en HTTPS depuis `isp-a-home` (si vous aviez ajouté une exception de sécurité à un moment du TP, retirez-la avant). Tout doit se dérouler sans alerte, visualisez le certificat reçu. (Vous arrivez sur une page par défaut, le dokuwiki est accessible à l'URL `https://www.target.milxc/dokuwiki/`)


> Question 4 : Décrivez la certification de target-dmz


Attaques sur un serveur HTTPS
=============================

Attaque sur la connexion au serveur
-----------------------------------

Refaites l'attaque du début (DNS ou BGP) et vérifiez que la connexion depuis isp-a-home, lorsqu'elle est routée vers le serveur attaquant, génère bien une alerte de sécurité.


Quelle est d'habitude votre réaction face à ce genre d'alerte ? Que pouvons nous en conclure sur la protection et le risque restant avec HTTPS ?


Attaque lors de la création du certificat
-------------------------------

En reprenant les attaques du début, obtenez depuis ecorp-infra un certificat bien signé par MICA lié à l'URL `www.target.milxc`. Ces attaques DNS/BGP vont vous permettre de vous faire passer pour Target auprès de mica, lors de la phase de création du certificat.

Validez la réussite en vous connectant depuis isp-a-home vers ce faux serveur, maintenant sans alerte de sécurité.

> Question 5 : Décrivez les attaques réalisées


Authentification mutuelle
=========================

Mettez en place une authentification forte des clients par le serveur au moyen de certificats clients.

Déroulé général :
* Côté serveur (donc target-dmz), vous devez limiter l'accès aux seuls clients détenteurs d'un certificat valide. Dans `/etc/apache2/sites-enabled/default-ssl.conf` :
	* Paramétrez la directive [SSLCACertificateFile](https://httpd.apache.org/docs/2.4/fr/mod/mod_ssl.html#sslcacertificatefile) en obtenant et spécifiant le crt de la CA (root.crt, pas le crt de ce serveur www.target.milxc !)
	* Décommentez les directive [SSLVerifyClient require](https://httpd.apache.org/docs/2.4/fr/mod/mod_ssl.html#sslverifyclient) et [SSLVerifyDepth](https://httpd.apache.org/docs/2.4/fr/mod/mod_ssl.html#sslverifydepth) pour forcer l'authentification des clients
* Validez depuis isp-a-home que l'accès TLS à `https://www.target.milxc` vous est bien refusé
* Générez un certificat client sur la machine `mica-infra`. Il faut faire un `step ca certificate "VotreNomÀCertifier" client.crt client.key` et utiliser le provisioner par défaut JWK (pas le ACME). Si le programme `step-ca` a été quitté, il faut le relancer préalablement (`step-ca .step/config/ca.json`)
* Packagez ensemble ce certificat et cette clé client avec `openssl pkcs12 -export -in client.crt -inkey client.key -out client.p12`
* Le client (la machine isp-a-home) doit récupérer ce client.p12 et l'importer dans Firefox (Préférences -> Sécurité -> Certificats -> Mes certificats -> Importer)
* Validez que l'accès est maintenant autorisé


> Question 6 : Décrivez la mise en place de l'authentification mutuelle


Révocation
========

> Dans Smallstep, la révocation par CRL/OCSP n'est pas (encore) gérée. À la place, les certificats ont par défaut une durée courte (24h) et doivent être renouvelés automatiquement, ce qui limite largement le besoin de révocation explicite. Ce positionnement et toutes les limites de la révocation explicite sont très bien expliqués par les auteurs [ici](https://smallstep.com/blog/passive-revocation/)
