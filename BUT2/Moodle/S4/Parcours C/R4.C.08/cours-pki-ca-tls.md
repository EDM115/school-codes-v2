# Notes de cours PKI - CA - TLS

(Notes de cours et références externes)

Infrastructures à clés publiques (PKI)
=======================================

Le rôle d'une PKI est de lier une clé publique à une identité (typiquement, à une chaîne de caractères intelligible comme une URL `www.acme.org` ou une adresse mail `brice@acme.org`). L'obtention de clés publiques est un service orthogonal au service de sécurité rendu par la cryptographie (ie, un même service, le mail chiffré et signé par exemple, peut-être rendu avec une approche type CA avec S/MIME ou une approche toile de confiance avec PGP).

Vous devez lire la [page anglaise de Wikipedia](https://en.wikipedia.org/wiki/Public_key_infrastructure) sur ce sujet, qui présente différentes formes de PKI (autorités de certifications, toile de confiance, SPKI, blockchain). Attention, la page française n'est pas assez détaillée.

Vous devez détailler chacune des différentes formes, avec une attention particulière pour les [CA](https://en.wikipedia.org/wiki/Certificate_authority) et le [Web of trust](https://en.wikipedia.org/wiki/Web_of_trust). La PKI DANE/TLSA est très bien décrite et positionnée dans cet [article](http://www.bortzmeyer.org/6698.html). Vous devez enfin lire les [Criticisms](https://en.wikipedia.org/wiki/Public_key_infrastructure#Criticism) de la page principale (et les détails de PKI security issues with X.509, Breach of Comodo CA, Breach of Diginotar).

> Pour comprendre DANE/TLSA qui repose sur DNSSEC, vous devrez peut-être vous rafraichir la mémoire sur le fonctionnement et les différents acteurs du système DNS (typiquement, notions de _registry_, _registrar_, gestion d'une zone et mécanisme de résolution récursif). Ces points ont normalement déjà été vus en TC mais vous pouvez par exemple lire [Sebsauvage](http://sebsauvage.net/comprendre/dns/) jusque "Dans ce cas, ils sont à la fois registry et registrar.", [Bortzmeyer](http://www.bortzmeyer.org/files/cours-dns-cnam-PRINT.pdf) sections "Le protocole DNS" et "Gouvernance" et/ou d'autres ressources équivalentes.


TLS
===

Dans le TP, nous allons manipuler une CA pour faire du HTTPS (HTTP sur TLS). TLS permet l'authentification mutuelle, une bonne explication [ici](https://tls.ulfheim.net/)

![xkcd](https://imgs.xkcd.com/comics/security.png)
