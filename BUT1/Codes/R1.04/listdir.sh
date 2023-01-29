#!/bin/bash

if [ $# -ne 1 ]
then
	echo "Usage: $0 <répertoire>"

	exit 1
fi

if [ ! -d "$1" ]
then
	echo "$1 n'est pas un répertoire"

	exit 2
fi

echo "####### fichiers dans $1"
for file in "$1/*"
do
	if [ -f "$file" ]
	then
		echo "$file"
	fi
done

echo "####### sous-répertoires dans $1"
for repertory in "$1/*"
do
	if [ -d "$repertory" ]
	then
		echo "$repertory"
	fi
done