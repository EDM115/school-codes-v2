#!/bin/bash

if [ ! -e "$1" ]
then
	echo "Le fichier $1 n'existe pas"
else
	if [ -d "$1" ]
	then
		type="répertoire"
	elif [ -f "$1" ]
	then
		type="fichier ordinaire"
	else
		type="autre type de fichier"
	fi

	echo "Le fichier $1 est un $type"

	if [ -r "$1" ]
	then
		eccess="en lecture"
	else
		access="non accessible en lecture"
	fi

	if [ -w "$1" ]
	then
		access="$access en écriture"
	fi

	if [ -x "$1" ]
	then
		access="$access en exécution"
	fi

	echo "$1 est accessible par $(whoami) $access"
fi