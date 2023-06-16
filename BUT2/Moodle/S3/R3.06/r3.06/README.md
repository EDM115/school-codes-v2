BUT2 Info - R3.06 - Architecture des réseaux
============================================

_François Lesueur ([francois.lesueur@univ-ubs.fr](mailto:francois.lesueur@univ-ubs.fr))_

L’objectif de cette ressource est de comprendre l’organisation et le fonctionnement d’un réseau informatique. Cette ressource permettra de découvrir les différentes technologies matérielles et logicielles mises en œuvre dans l’acheminement de données à l’intérieur d’un réseau (local ou étendu), de voir par quels types d’applications accéder au réseau.

Savoirs de référence étudiés :
* Technologies des réseaux (piles protocolaires, couche transport, TCP/IP/UDP, DHCP, DNS…)
* Interconnexion de réseaux (par ex.: routage, NAT, filtrage, proxy...)
* Utilisation de services réseaux (côté client)

La matière se déroule sur la période 2 et est composée de :
* 7 séances de cours (CM) de 45 minutes (en promo entière)
* 7 séances de travaux dirigés (TD) de 1h30 (en groupe TD)

Une large part des séances pratiques sera réalisée sur la plateforme MI-LXC (https://github.com/flesueur/mi-lxc) (plus précisément sa branche [SNSTER](https://github.com/flesueur/mi-lxc/tree/snster), méfiez-vous donc des docs pas forcément toujours cohérentes entre les branches), pour laquelle il faudra télécharger une VM Virtualbox **avant** le TD1 "Découverte de MI-LXC" : [.ova à télécharger ici](https://flesueur.irisa.fr/mi-lxc/images/milxc-snster-vm-2.0.0pre1.ova), nous utiliserons la version 2.0.0pre1. Il faudra arriver en séance avec Virtualbox installé et le .ova de MI-LXC déjà téléchargé, l'installation et la découverte de la VM seront ensuite le programme des séances TD1 et TD2.

Sur ce dépôt, vous trouverez les notes de cours ainsi que les sujets des TD/TP.

Programme
=========

Le programme prévisionnel est le suivant :
* S44 :
  * [CM1](cm1-intro.md) : Introduction et périmètre du cours
* S45 :
  * [TD1](td1-milxc.md) : Découverte de MI-LXC 1/2
* S46 :
  * [TD2](td1-milxc.md) : Découverte de MI-LXC 2/2
  * [CM2](cm2-piles.md) : Piles et protocoles de transport
* S47 :
  * [TD3](td3-apache.md) : Apache/Tunnels/CMS 1/3
  * [CM3](cm3-archi.md) : Architecture réseau 1/3
* S48 :
  * [TD4](td3-apache.md) : Apache/Tunnels/CMS 2/3
  * [CM4](cm3-archi.md) : Architecture réseau 2/3
* S49 :
  * [TD5](td3-apache.md) : Apache/Tunnels/CMS 3/3
  * [CM5](cm3-archi.md) : Architecture réseau 3/3
* S50 :
  * [TD6](td6-archi.md) : Segmentation réseau 1/2
  * [CM6](cm6-dns.md) : DNS
* S01 :
  * [TD7](td6-archi.md) : Segmentation réseau 2/2
  * CM7 : Révisions, questions/réponses


Évaluation
==========

L'évaluation sera composée de :
* une note de contrôle continu (1/3, à partir des comptes-rendus demandés);
* une note d'examen final (2/3).
