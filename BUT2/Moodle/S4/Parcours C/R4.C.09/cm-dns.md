CM DNS - Notes de cours
=======================

Objectif
========

* Annuaire nom d'hôte -> IP (et plus largement identifiant -> ressource)
* Par exemple : `www.univ-ubs.fr -> 51.77.203.125`
* Une immense base de données distribuée, mise à jour en (presque) continu, multi-parties, robuste, venue des années 1980s...
* Un élément d'une conception incroyablement visionnaire et pérenne, pierre angulaire qui a donc traversé les époques d'internet


La structure
============

La structure est arborescente :

* Une racine universelle '.'
* Des nœuds intermédiaires :
  * Des TLD géographiques ('.fr', '.ch', ...)
  * Des TLD non-géographiques ('.com', '.net', ...)
  * Des non-terminaux sous ces TLD ('.eu.org', '.gouv.fr', ...)
  * Des domaines terminaux ('univ-ubs.fr', 'signal.eu.org', 'enseignementsup-recherche.gouv.fr')
* Des noms d'hôtes (ou autres identifiants finaux) : 'www.univ-ubs.fr', 'smtp.enseignementsup-recherche.gouv.fr'
* (la structure est en fait souple, un nœud intermédiaire peut contenir à la fois des identifiants finaux et des sous-domaines)


Les acteurs
===========

* ICANN (gestion des TLD - domaines de premier niveau)
* Registries (AFNIC pour .fr de France, filiale de Verisign pour .tv des îles Tuvalu)
* Registrars (Gandi, OVH, ...)
* Propriétaires de domaines


Éléments techniques
===================

Protocole
---------

* Historiquement UDP, écoute sur le port 53. Progressivement TCP également.
* Protocole binaire (et non texte comme HTTP, pas de netcat !)
* Des outils de dialogue : dig, drill, nslookup

Quelques types d'enregistrements
--------------------------------

| Type | Contenu | Exemple |
| ---- | ------- | ------- |
| A | Enregistrement IPv4 | 1.2.3.4 |
| AAAA | Enregistrement IPv6 | 2001:0db8:0000:85a3:0000:0000:ac1f:8001 |
| CNAME | Alias vers un autre nom | www.acme.org |
| MX | Serveur mail d'entrée (smtp) | smtp.acme.org |
| SRV | Généralisation du MX à d'autres services | _sip._tcp.acme.org |
| NS | Serveur de nom | ns.acme.org |


Exemple de zone DNS
-------------------

```
$TTL	86400
$ORIGIN target.milxc.
@  1D  IN  SOA ns.target.milxc. hostmaster.target.milxc. (
			      2002022401 ; serial
			      3H ; refresh
			      15 ; retry
			      1w ; expire
			      3h ; nxdomain ttl
			     )
      IN  NS     ns.target.milxc.
      IN  MX  10 smtp.target.milxc.
ns    IN  A      100.80.1.2
ns    IN  AAAA   2001:db8:80::1:2
dmz   IN  A      100.80.1.2
dmz   IN  AAAA   2001:db8:80::1:2
smtp  IN  CNAME  dmz
imap  IN  CNAME  dmz
www   IN  CNAME  dmz
```

DNSSEC
------

* Protocole en clair -> espionnage et altération des réponses
* DNSSEC contre l'altération des réponses :
  * Chaque domaine génère une paire de clés asymétriques
  * Chaque enregistrement de la zone est signé (avec la clé privée, donc)
  * La clé publique est enregistrée dans la zone supérieure (et donc signée par la clé privée supérieure)
  * Le client peut donc valider la signature à réception


L'architecture
==============

Serveurs faisant autorité (authoritative servers)
----------------------------------

* Les serveurs racine qui servent `.` :
  * 13 entrées IPv4 + IPv6 (a.root-servers.net à m.root-servers.net)
  * Pour chaque serveur racine, en réalité de nombreux serveurs répartis géographiquement (IP anycast)
  * Chacune des 13 entrées opérée par une organisation distincte (Verisign (US), ICANN (US), RIPE NCC (NL), WIDE (JP), ...)
  * Maintenus synchronisés mais gérés distinctement (résilience organisationnelle et logicielle)
  * Servent la zone racine qui décrit où trouver '.com', '.net', '.fr', etc.
* Les serveurs à chaque sous-niveau :
  * Similaire de servir '.org' et '.eu.org', c'est toujours un enregistrement dans le niveau au-dessus
  * Répondent où trouver dans la zone qui les concerne (ses sous-domaines et ses hôtes)
* Sont interrogés par les résolveurs et non les clients finaux
* Exemples : Bind, NSD, PowerDNS, Knot

Résolveurs - Serveurs de résolution
-----------------------------------

* Sont interrogés par les clients finaux (les OS lors des requêtes par curl, ping, Firefox, ...)
* Interrogent récursivement les serveurs d'autorité
* Exemple : Quelle IPv4 pour `www.univ-ubs.fr` ?
  1. Demande à a.root-servers.org : Qui gère `.fr` ? -> e.ext.nic.fr / 193.176.144.22
  2. Demande à 193.176.144.22 : Qui gère `univ-ubs.fr` ? -> honehe.univ-ubs.fr. / 193.52.48.66
  3. Demande à 193.52.48.66 : Qui est `www.univ-ubs.fr` ? -> 51.77.203.125
* Connaissent donc préalablement les 13 IP des serveurs racine (et la clé publique de la racine pour DNSSEC)
* Fournis par le FAI pour des raisons historiques mais peut être fait localement
* Exemples : Bind, Unbound, Dnsmasq

La robustesse (résistance + passage à l'échelle)
------------------------------------------------

* Grâce à l'architecture par la redondance des serveurs d'autorité
* Grâce au protocole par le mécanisme de cache

Illustration : [TCP/IP Guide](http://www.tcpipguide.com/free/t_DNSNameResolutionProcess-2.htm)

Les usages autour
=================

Filtrage
--------

* Point de passage quasi-obligé, historiquement en clair et classiquement centralisé chez les FAI
* Au niveau État pour la censure (application de blocage administratif de l'orient à l'occident)
* Au niveau organisation pour limiter l'accès internet des employés
* Attention, ce n'est pas une mesure de sécurité car facile à contourner...

Open resolvers
--------------

* Résolveurs classiques : chez le FAI, dans le SI, chez soi
* Pour contourner la censure passive, des open resolvers :
  * Chez Google, Cloudflare, Cisco (à quel prix ?)
  * Chez FDN

DoT / DoH
---------

* Résolveurs ouverts ont encore le problème de l'espionnage des communications entre le client et le résolveur
* DNS-over-TLS et DNS-over-HTTPS pour chiffrer la communication client <-> résolveur
* Mêmes acteurs que précédemment


Bonus
=====

https://jvns.ca/blog/2022/05/10/pages-that-didn-t-make-it-into--how-dns-works-/
https://jvns.ca/blog/2022/02/01/a-dns-resolver-in-80-lines-of-go/
