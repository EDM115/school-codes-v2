#!/bin/bash

while true
do
	read -p "Entrez une note (q pour quitter) : " note

	if [[ "$note" == "q" || "$note" == "Q" ]]
	then
		break
	fi
	
	if [[ ! "$note" =~ ^[0-9]+$ ]]
	then
		echo "La note doit être un nombre entier"
		continue
	fi

	if [[ $note -ge 16 && $note -le 20 ]]
	then
		echo "Très bien"
	elif [[ $note -ge 14 && $note -le 16 ]]
	then
		echo "Bien"
	elif [[ $note -ge 12 && $note -le 14 ]]
	then
		echo "Assez bien"
	elif [[ $note -ge 10 && $note -le 12 ]]
	then
		echo "Moyen"
	elif [[ $note -ge 0 && $note -le 10 ]]
	then
		echo "Insuffisant"
	else
		echo "La note doit être inférieure à 20"
	fi
done