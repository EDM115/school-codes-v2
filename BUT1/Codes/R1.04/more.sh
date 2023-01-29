#!/bin/bash

if [[ $# -ne 1 ]]
then
	echo "Usage: $0 <répertoire>"

	exit 1
fi

for file in "$1"/*
do
	if file "$file" | grep -q "text"
	then
		read -p "Voulez-vous visualiser le fichier $file ? [o/n] " answer
		if [[ "$answer" == "o" || "$answer" == "O" ]]
		then
			more "$file"
		fi
	fi
done