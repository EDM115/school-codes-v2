CM2 Piles et protocoles de transport - Notes de cours
=====================================================

Pile OSI, pile TCP/IP
=====================

Modèles
-------

![OSI/TCP](https://upload.wikimedia.org/wikipedia/commons/7/7e/Comparaison_des_mod%C3%A8les_OSI_et_TCP_IP.png){width=50%}

[Pile OSI](https://en.wikipedia.org/wiki/OSI_model) :
* Fin des années 70
* Modèle à 7 couches adapté à un objectif des 70s
* Isolation totale entre les couches

[Pile TCP/IP](https://en.wikipedia.org/wiki/OSI_model#Comparison_with_TCP/IP_model) :
* Dans les 80s
* Modèle à 4 couches (+ exemples) :
  * the scope of the software application - HTTP
  * the host-to-host transport path - TCP
  * the internetworking range - IP
  * the scope of the direct links to other nodes on the local network - Ethernet
* Quelques interactions entre des couches

Les critiques du modèle OSI / de la vision en couches :
* [Wikipedia](https://fr.wikipedia.org/wiki/Mod%C3%A8le_OSI#Le_monde_IP_et_le_mod%C3%A8le_OSI) : "S'il y a bien une correspondance grossière entre les protocoles de la pile IP et les couches du modèle, on ne peut pas considérer que la pile IP soit vraiment compatible avec le modèle OSI. En particulier, la séparation des couches dans la pile IP est nettement plus approximative. En voici 2 illustrations."
* [Wikipedia](https://en.wikipedia.org/wiki/Internet_protocol_suite#Comparison_of_TCP/IP_and_OSI_layering) : "The IETF has repeatedly stated that Internet Protocol and architecture development is not intended to be OSI-compliant. RFC 3439, referring to the internet architecture, contains a section entitled: "Layering Considered Harmful". [...] The OSI protocol suite that was specified as part of the OSI project was considered by many as too complicated and inefficient, and to a large extent unimplementable", "the OSI model contains idiosyncrasies not found in later systems such as the IP stack in modern Internet"
* Fin du game...
* C'est _juste_ un guide de compréhension, une abstraction


Encapsulation
-------------

* L'encapsulation est le principe à retenir !!!
* Une couche = un objectif :
  * Un switch ethernet ne se préoccupe *que* de l'en-tête ethernet
  * Un routeur IP ne se préoccupe *que* de l'en-tête IP (presque)
  * Un OS ne se préoccupe *que* de l'en-tête transport (presque)
  * Une application ne se préoccupe *que* de la partie application
* La logique de l'encapsulation de protocoles :
  * Dans le "bon" sens : encapsuler dans une couche de niveau plus bas (usage général), exemple
  * Dans l'autre sens : encapsuler dans une couche de niveau plus haut (boucle, exemple VPN), exemple

Schéma : ![Encapsulation](https://upload.wikimedia.org/wikipedia/commons/thumb/3/3b/UDP_encapsulation.svg/1920px-UDP_encapsulation.svg.png){width=60%}

Un peu de Wireshark
===================

Explorons sur target-router, une requête HTTP permet de voir :
* De l'Ethernet
* De l'IP
* Du TCP, UDP
* Du BGP, HTTP, DNS


Internet, le routage, les services, les couches
===============================================

De manière *très* synthétique :
* Chaque lien physique entre 2 machines/routeurs (fibre, câble, sans-fil) : un protocole couche 2
* Internet c'est la connectivité IP (Internet Protocol) globale couche 3 de bout en bout
* Au dessus d'IP, on retrouve classiquement TCP et UDP :
  * Au-dessus de TCP : HTTP, SMTP, ...
  * Au-dessus d'UDP : DNS, WebRTC et autres protocoles audio/vidéo, ...
* (ICMP, qui véhicule notamment les paquets ping, est de même niveau que IP)
