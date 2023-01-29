#!/bin/bash

if [ $# -ne 2 ]
then
	echo "Erreur : il faut 2 arguments"

	exit 1
else
	CONCAT="$1$2"
	echo "La concaténation de $1 et $2 est : $CONCAT"

	exit 0
fi