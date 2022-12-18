# Décodage

> Question 3 : Le destinataire sait-il décoder votre message ?

_(Pas) Bravo, vous venez de créer plusieurs systèmes d'encodage différents et non compatibles, comme l'ont fait les différents acteurs de l'informatique avec le temps !_

> Question 4 : Écrivez une spécification pour décoder.

Transmettez maintenant au destinataire, sur papier et sans discussion de vive voix ensuite, cette spécification.

> Si le décodage est réussi, bravo, vous avez écrit une spécification suffisamment claire et complète pour votre interlocuteur. L'écriture de spécifications complètes et non ambiguës est un exercice difficile que doit savoir faire un développeur.

# Efficacité

Lors d'un transfert du texte par un moyen électronique, les caractères seront au final ré-encodés en binaire. Nous considérerons que chaque caractère occupera 1 octet (ce qui, en pratique, sera le cas en général si vous avez choisi des caractères "simples").

> Question 5 : Pour un fichier original binaire de x octets, combien d'octets occupera sa conversion texte selon votre mécanisme ?

# Standardisation

Comme vous l'avez constaté, la communication, au cœur du numérique, ne sera possible qu'avec des normes communes : des standards. Ces standards vont des protocoles réseaux de tous niveaux (une carte wifi communique avec un point d'accès selon un standard défini, un navigateur web communique en HTTP avec un serveur web), au format de fichiers (traitement de texte, tableur) en passant par la représentation des nombres (entiers, réels) ou l'ordre des octets (big-endian / little-endian).

Il est donc indispensable pour tous les acteurs (autant matériels que logiciels) de se mettre d'accord sur des standards qui sont soit construit collectivement soit des standards de fait. Vous en rencontrerez de nombreux et cette standardisation implique de spécifier complètement : on trouve ainsi des choix justifiés tout autant que des choix arbitraires (le seul intérêt dans ce cas est d'être tous d'accord). Et évidemment, bien souvent, plusieurs standards pour un même objectif : ce n'est pas (que) de la mauvaise volonté mais un manque de concertation/connaissance comme vous pouvez maintenant le comprendre.

> Question 6 : Proposez collectivement un unique standard d'encodage/décodage entre vous.

![standards](https://imgs.xkcd.com/comics/standards.png)
_[https://xkcd.com/927/](https://xkcd.com/927/)_

# Base64

L'encodage binaire->texte le plus couramment utilisé est le Base64, dont vous trouverez la description [ici](https://fr.wikipedia.org/wiki/Base64).

> Question 7 : Refaîtes l'encodage et le décodage de votre message en Base64 (avec le détail des étapes, pas un outil en ligne)

> Question 8 : Quelle est l'efficacité de Base64 ?

# Base45 et Base85 (Bonus)

Les encodages [Base45](https://billatnapier.medium.com/so-what-is-base-45-and-where-is-it-used-1ab53279d705) et [Base85](https://fr.wikipedia.org/wiki/Ascii85) sont des alternatives au Base64. Base45 est par exemple utilisé pour l'encodage de binaire dans des QR-codes (qui ne représentent que du texte) et Base85 se retrouve dans le format PDF. Il y a donc 45 ou 85 symboles, ce qui n'est pas une puissance de 2. La conversion oblige à réaliser des changements de base par division euclidienne.

> Question 9 : Refaîtes l'encodage et le décodage de votre message en Base45 et/ou en Base85 (avec le détail des étapes, pas un outil en ligne)

# Compte-rendu

Votre compte-rendu sera évalué autant sur le fond que sur la forme.

Il doit contenir les réponses aux questions, le déroulé de votre travail ainsi que les descriptions de votre système d'encodage (symboles, procédure d'encodage, procédure de décodage) et du système d'encodage choisi collectivement (symboles, procédure d'encodage, procédure de décodage). Une attention particulière doit être apportée à la clarté des explications.

Il doit être bien présenté et structuré. Il doit impérativement mentionner, sur la première page, le nom de la matière, le numéro et nom du TP ainsi que les **noms, prénoms et groupe** des 2 membres du binôme. Tout prénom/nom absent des documents déposés recevra 0 au TP.

Le compte-rendu doit être déposé sur Moodle au format PDF uniquement. Un dépôt par binôme suffit.
