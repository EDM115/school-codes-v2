#!/bin/bash

if [[ $1 =~ ^[0-9]+$ ]]
then
	if id -u "$1" &> /dev/null
	then
		uid=$(id -u "$1")
		echo "Le UID de l'utilisateur $1 est $uid"
	else
		echo "L'utilisateur $1 n'existe pas"
	fi
else
	if id "$1" &> /dev/null
	then
		uid=$(id -u "$1")
		echo "Le UID de l'utilisateur $1 est $uid"
	else
		echo "L'utilisateur $1 n'existe pas"
	fi
fi