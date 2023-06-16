# Installation Oracle SQL sur Linux avec Docker

Détails de l'image sur [Docker Hub](https://hub.docker.com/r/gvenzl/oracle-xe)

## Etapes d'installation

### Télécharger l'image

```bash
sudo docker pull docker.io/gvenzl/oracle-xe:11
```

### Lancer Oracle SQL

```bash
sudo docker run -d --name oraclesql -p 1521:1521 -e ORACLE_PASSWORD=password gvenzl/oracle-xe:11
```

Changer le mot de passe dans le champ `ORACLE_PASSWORD`.

Le port sur lequel Oracle SQL écoute est précisé avec `-p` (valeur de gauche)

### Arrêter le conteneur

```bash
sudo docker stop oraclesql
```

Si on ne supprime pas le conteneur, on peut le relancer simplement avec `sudo docker start oraclesql`

### Supprimer le conteneur 

```bash
sudo docker container rm -f oraclesql
```

## Attention

Le conteneur prend beaucoup de place (3.25 GB pour l'image seule)

Les données ne sont pas sauvegardées entre deux lancement du conteneur.
Bien conserver les script de création et d'insertion des données.
(On peut normalement mettre en place la persistance des données, mais je n'ai jamais réussi à le faire fonctionner)

L'argument à ajouter est `-v oracle-volume:/opt/oracle/oradata`
(`oracle-volume` est le répertoire local dans lequel les données seront stockées)
