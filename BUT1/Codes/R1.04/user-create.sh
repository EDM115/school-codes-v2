#!/bin/bash

if [[ "$USER" != "root" ]]
then
	echo "Vous devez être root pour exécuter ce script"

	exit 1
fi

read -p "Entrez le login de l'utilisateur : " login

if id "$login" &> /dev/null
then
	echo "L'utilisateur $login existe déjà"

	exit 1
fi

if [[ ! -d "/home/$login" ]]
then
	mkdir "/home/$login"
else
	echo "Le répertoire /home/$login existe déjà"

	exit 1
fi

while true
do
	read -sp "Entrez le mot de passe de l'utilisateur : " pass
	echo
	read -sp "Confirmez le mot de passe de l'utilisateur : " pass_confirm
	echo

	if [[ "$pass" != "$pass_confirm" ]]
	then
		echo "Les mots de passe ne correspondent pas, veuillez réessayer"
	elif [[ ${#pass} -lt 8 ]]
	then
		echo "Le mot de passe doit contenir au moins 8 caractères"
	elif [[ ! "$pass" =~ [0-9] ]]
	then
		echo "Le mot de passe doit contenir au moins un chiffre"
	elif [[ ! "$pass" =~ [A-Z] ]]
	then
		echo "Le mot de passe doit contenir au moins une majuscule"
	elif [[ ! "$pass" =~ [a-z] ]]
	then
		echo "Le mot de passe doit contenir au moins une minuscule"
	else
		break
	fi
done

read -p "Entrez le nom de l'utilisateur : " nom
read -p "Entrez le prénom de l'utilisateur : " prenom

while true
do
	read -p "Entrez l'UID de l'utilisateur : " uid
	if [[ ! "$uid" =~ ^[0-9]+$ ]]
	then
		echo "L'UID doit être un nombre entier positif"
	else
		break
	fi
done

while true
do
	read -p "Entrez le GID de l'utilisateur : " gid
	if [[ ! "$gid" =~ ^[0-9]+$ ]]
	then
		echo "Le GID doit être un nombre entier positif"
	else
		break
	fi
done

read -p "Entrez des commentaires sur l'utilisateur : " commentaires

useradd -c "$commentaires" -d "/home/$login" -g "$gid" -u "$uid" "$login"
echo "$pass" | passwd --stdin $login

if id "$login" &> /dev/null
then
	echo "L'utilisateur $login a été créé avec succès"
else
	echo "Une erreur est survenue lors de la création de l'utilisateur $login"

	exit 1
fi