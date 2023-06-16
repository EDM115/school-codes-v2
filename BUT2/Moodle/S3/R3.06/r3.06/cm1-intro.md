CM1 Introduction - Notes de cours
=================================

R3.06
=====

L’objectif de cette ressource est de comprendre l’organisation et le fonctionnement d’un _réseau_ informatique. Cette ressource permettra de découvrir les différentes technologies _matérielles et logicielles_ mises en œuvre dans l’_acheminement_ de données à l’intérieur d’un réseau (_local ou étendu_), de voir par quels types d’applications accéder au réseau.

Savoirs de référence étudiés :
* _Technologies_ des réseaux (piles protocolaires, couche transport, TCP/IP/UDP, DHCP, DNS…)
* _Interconnexion_ de réseaux (par ex.: routage, NAT, filtrage, proxy...)
* _Utilisation_ de services réseaux (côté client)


Parlons un peu d'internet
=========================

Pourquoi ?
* Internet est un réseau de réseaux
* Les réseaux que l'on va voir sont reliés pour former Internet
* Internet n'héberge pas de services en tant que tel, il n'y a pas de "zone de serveurs" : on ne fait qu'aller interagir avec les services hébergés dans un des autres réseaux d'internet.
* Pour comprendre les motivations et technologies des réseaux, il faut les appréhender dans le cadre de leur association via Internet.
* IP :
  * Unification du local et du global dans un même protocole, utilisé autant localement que globalement
  * A remplacé les autres protocoles locaux (IPX, NetBEUI, ...) _parce que_ (?) c'est le protocole global
  * A remplacé les autres justement parce qu'il résout un problème plus large que les réseaux locaux
  * Mutualisation du code, homogénéisation des interactions, connectivité de bout-en-bout sans rupture protocolaire (modulo NAT)


C'est quoi internet, côté réseaux ?
===================================

* Des protocoles pour l'interopérabilité : IP, TCP, UDP, BGP, DNS, SMTP, HTTP, ...
* Des organisations qui orchestrent : IETF, ICANN (dont IANA), RIR RIPE/NANOG/etc.
* Du matériel : des ordinateurs, des routeurs, des câbles/fibres [J. Mau](https://www.iletaitunefoisinternet.fr/post/3-infra-maud/)
* Des logiciels : du firmware de routeur, des services de cœur (BGP, DNS), des serveurs et clients à chaque bout de la chaîne


Comment est structuré internet ?
===========================

Un réseau de réseaux :
* Acentré (**pas de chef, pas de décision unique sans consensus des acteurs indépendants, pas de point de défaillance unique (SPOF)**). *Est-ce toujours aussi vrai ?*
* Structuré autour de la notion d'AS (systèmes autonomes, environ 100 000) qui forment le découpage de premier niveau
* Chaque AS (exemple : RENATER, Orange) est ensuite sous-divisé en interne
* Les AS s'interconnectent entre eux et le protocole BGP assurent la glu entre ces AS
* Un bon thread qui explique cela [ici](https://twitter.com/AtaxyaNetwork/status/1445096685286350849) : chaque AS est une île, BGP sert à construire les ponts, l'objectif étant de permettre de transmettre des messages d'une île à une autre île éloignée, par le passage donc de plusieurs îles et ponts intermédiaires.
* Les communications se font de bout en bout à travers le protocole IP


Panorama du cours
=================

* Introduction (aujourd'hui)
* Piles réseau
* Adressage IP
* Architecture réseau
* Routage
* DNS


Les TODO
========

* Venir en TD avec son laptop. Combien faut-il en prévoir en complément ?
* Télécharger l'[ova de MI-LXC](https://flesueur.irisa.fr/mi-lxc/images/milxc-snster-vm-2.0.0pre1.ova) et installer VirtualBox ou VMWare **avant le premier TD** !
