#!/bin/bash

# La variable user contient le nom d'utilisateur
user=$1
# La variable output contient le dossier de destination
output="/tmp/${user}_home_${date +%Y%m%d%H%M}.tar.gz"
# Crée l'archive du home directory
tar -czf $output /home/$user
# Affiche l'output directory
ls -l $output