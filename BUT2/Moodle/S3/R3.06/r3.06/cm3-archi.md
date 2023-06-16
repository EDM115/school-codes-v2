CM3 + CM4 Architecture réseau et firewall - Notes de cours
==========================================================

Programme
=========

* L'objectif de la segmentation
* Quelques exemples d'architecture
* Le filtrage par pare-feu
* Routage, filtrage, NAT, proxy


Il était une fois les réseaux à plat
====================================

* Naturellement, comme on l'a fait en TD : un subnet, tout le monde dedans !
* Sauf que c'est en fait une mauvaise idée pour plein de raisons...
* On va donc sous-diviser en sous-réseaux au niveau IP

Raisons humaines
----------------

* Un réseau s'administre, donc doit se comprendre
* Le cerveau humain a ses limites (sisi)
* Besoin de cartographier, décomposer pour s'y repérer
* Besoin d'une certaine autonomie/indépendance entre les parties pour maîtriser la gestion de chacune au quotidien
* Sous-division en sous-réseaux logiques au niveau IP
* Par exemple géographiques (bâtiments, services, filiales, ...)

Exemple :
* Partant d'un réseau /16
  * public (adresses globales) : 134.214.0.0/16 (allant donc de 134.214.0.0 à 134.214.255.255)
  * ou privé (adresses locales) : 192.168.0.0/16 (allant donc de 192.168.0.0 à 192.168.255.255)
* Découpage en
  * 255 réseaux /24 : 192.168.0.0/24, 192.168.1.0/24, 192.168.2.0/24, ..., 192.168.255.0/24
  * ou 16 réseaux /20 : 192.168.0.0/20, 192.168.16.0/20, 192.168.32.0/20, ... (oui, c'est déjà plus compliqué !)
  * ou ...
  * _Les séparations sur les octets (les '.'), c'est le plus simple ; mais la notation CIDR permet les frontières plus fines_


Raisons réseau
--------------

* La couche "Liaison", typiquement Ethernet en-dessous d'IP, n'a pas une scalabilité infinie
* Broadcast ARP, tables ARP (hôtes, switchs)
* IP fait aussi du broadcast, sur cette notion de segment Ethernet
* Tout ceci est du trafic réseau "non-métier", coût bande passante et calcul
* Besoin de structurer/factoriser la notion de route avec du routage logique : IP


Exemple :

* Ethernet, adresses MAC hétérogènes, une table complète, non adapté à un grand nombre d'identifiants
* IP, addresses IP homogènes, notion de route unique vers un ensemble de machines, passe à l'échelle du nombre d'identifiants


Raisons sécurité
----------------

* La raison directrice aujourd'hui (à mon sens). Pourquoi ?
  * Parce que les autres raisons sont déjà rentrées dans les mœurs
  * Parce qu'elle va plus loin et donc, en quelque sorte, inclue les besoins des précédentes
  * Parce que je suis biaisé !?
* Limiter la propagation d'une attaque -> Avoir des points de limitation et de contrôle
* Principe du moindre privilège (des utilisateurs, des machines) -> Limiter les accès
* Segmentation fonctionnelle selon les rôles/les besoins de réseau/service des machines
* Et il s'avère que tout ceci se conçoit au niveau IP
* L'objectif est ainsi de brider les possibilités d'un attaquant par les équipements de confiance au niveau réseau

Exemples de "patrons de segmentation"
=====================================

La segmentation va avoir pour but de découper son SI en zones isolées afin de :

* Diminuer la surface d'attaque sur chaque zone
  * Une zone est un ensemble homogène (en terme de sensibilité, d'exposition, de type de service, ...)
  * Des règles générales limitent l'accessibilité de l'ensemble des machines
* Compliquer le passage d'une zone à l'autre
  * Les zones ont des expositions différentes
  * Les zones ont des sensibilités différentes
  * Souvent, exposition <> sensibilité
  * Prévenir la compromission d'une zone sensible à partir d'une zone exposée
* Un bon guide de l'ANSSI [ici](https://www.ssi.gouv.fr/administration/guide/definition-dune-architecture-de-passerelle-dinterconnexion-securisee/).

![Schéma](archi.jpg)

Modèle historique (**proscrit** mais on le trouve encore...)
------------------------------------------------------------

Modèle périmétrique à 3 zones pour limiter la surface d'attaque (les services/machines exposés) :

* WAN : le monde extérieur
* DMZ : zone tampon, perdable sans mettre en péril le SI, les services très exposés ouverts sur le WAN (exposés => sensibilité la plus faible possible)
* LAN : les services internes, les postes de travail, tout à plat

Flux :

* WAN -> DMZ : spécifiques
* WAN -> LAN : aucun !
* DMZ -> LAN : aucun !
* LAN -> DMZ, DMZ -> WAN : un peu de filtre mais c'est le sens des communications attendues
* (évidemment les réponses passent dans l'autre sens)

Par contre, une fois mis un pied dans le réseau, c'est fini... Ex ransomware ou [Paf TV5Monde](https://www.sstic.org/2017/presentation/2017_cloture/)


Modèle moderne : segmentation
-----------------------------

* Parfois appelé le modèle de l'aéroport (des zones publiques, des contrôles successifs, chaque zone plus sécurisée demande des contrôles supplémentaires)
* Généralisation du concept de DMZ à n segments
* Considérer que l'attaquant est ou sera là, le limiter dans ses actions une fois entré
* Une logique de dissocier l'exposition de la sensibilité (les zones exposées peu sensibles, les zones sensibles peu exposées)
* Un élément de réponse face aux attaques à n sauts, aux ransomwares, ...

Exemple :

* WAN : le monde extérieur
* Des zones de serveurs :
  * DMZ externe : les services ouverts sur l'extérieur
  * DMZ interne : les services internes
* Des zones de postes de travail, liées aux besoins métiers :
  * Des zones très peu privilégiées (secrétariat, compta, DIRECTION)
  * Des zones de développeurs (qui déploient du code sur des sites interne par exemple)
  * ...
* Une zone de postes particuliers : les administrateurs, très privilégiés, donc on limite au maximum l'exposition (minimum de postes, règles strictes)

Entre chaque paire de zones :
* Limiter les flux
* S'assurer de grader les zones / empêcher les passages vers du plus privilégié
* Toujours ouvert à l'intérieur d'une même zone, pas de contrôle ici.


Vers le zero-trust ?
--------------------

* Trouver le bon compromis entre la maintenabilité (pas trop de zones) et la précision (une zone par machine)
* La mode va vers le zero-trust, mais c'est aujourd'hui plus une philosophie et/ou du marketing qu'une réalité
* Aucun accès par défaut (donc, quelque part, pas de zones), ouverture de chaque flux sur droits spécifiques à chaque fois (SSO+++)
* On imagine vite la complexité de mise en place
* Ex Google


Cas particuliers
----------------

* Authentification centralisée :
  * Comment la rendre accessible à la DMZ ? Elle n'est pas perdable et est sensible !
  * Proxy ou miroir poussé depuis l'interne

* VPN :
  * Chemin WAN -> DMZ externe -> zone interne, c'est mal !
  * Vrai casse-tête en particulier avec toutes les ouvertures post-confinement
  * Nécessaire, à réfléchir et limiter par utilisateur (un comptable ne doit pas avoir le même accès qu'un développeur)
  * Dur !



Quel outillage technique ?
==========================

(V)LAN
------

* LAN (physique) premier élément :
  * Un brin réseau Ethernet
  * Les LAN sont reliés par des routeurs au niveau IP -> Filtrage !
* La séparation physique est coûteuse et peu souple :
  * Un grand LAN unique
  * Des VLAN administrés
  * Un VLAN spécifié pour chaque prise réseau
  * On revient sur la logique du LAN


Pare-feu (_Firewall_)
---------------------

* Ouverture d'une liste de services contrôlés, diminution de la surface d'attaque, matrice de flux
* Couche IP
* Simplification (donc avec perte) : 1 service = 1 port
* Système de règles
* Une règle : (Interface_src, IP_src, Port_src, IP_dst, Port_dst, Interface_dst) (essentiellement)
* Filtre uniquement entre des (V)LAN, lorsque le routage IP doit être fait
* Exemple de règle
* Ex [iptables](https://fr.wikipedia.org/wiki/Iptables)/[nftables](https://fr.wikipedia.org/wiki/Nftables)


NAT
---

* Des adresses IP privées/non routables sur Internet, des adresses IP publiques/routables sur Internet (RFC 1918). Plages privées :
  * 10/8, 172.16/12, 192.168/16
  * fd00::/8
* Router = Rediriger sur la bonne patte réseau sans modifier les sources/destinations
* Si une patte est sur une plage privée, la réponse sera non routable. NAT !

Le NAT/PAT (Network/Port Address Translation) s'occupe de modifier les ports/adresses à la volée au niveau du routeur :
* D'un côté des adresses privées, de l'autre des adresses publiques
* Communication initiée de public vers privé, impasse : le routeur ne peut pas savoir à qui c'est destiné
* Communication initiée de privé vers public, translation d'adresse :
  * Paquet interne : IPsrc, PORTsrc, IPdst, PORTdst (avec IPsrc privée, IPdst publique)
  * Au NAT :
    * Substitution de IPsrc par IProuter : la réponse reviendra vers le routeur
    * (Substitution du PORTsrc pour un libre)
    * Enregistrement des paramètres de la communication sur le routeur NAT
  * Paquet externe : IProuter, PORTrouter, IPdst, PORTdst (avec IProuter publique)
  * La destination répond vers IProuter
  * Le routeur avait enregistré les paramètres à l'aller et sait re-substituer au retour

Intérêts du NAT :
* Utiliser moins d'adresses IP publiques (rares et chères en IPv4)
* Masquer l'architecture interne
* Firewall par effet de bord (puisqu'entrée impossible)

Proxy
-----

Proxy "classique", un relais applicatif vers l'extérieur :
* Ajout d'un point de passage
* Exemple HTTP : au lieu de se connecter au site distant, on se connecte au proxy qui fait la requête pour nous
* Intérêts : mise en cache, journalisation/surveillance

Reverse proxy, un relais applicatif pour répartir la charge sur plusieurs serveurs :
* Ajout d'un point de passage vers l'infra interne
* Plusieurs serveurs applicatifs
* Le proxy reçoit les connexions et les répartit vers les serveurs applicatifs
