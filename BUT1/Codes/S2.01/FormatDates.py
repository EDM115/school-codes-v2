# Remplacement des dates au format YYYY-MM-DD vers un format DD/MM/YYYY pour plus de cohérence entre les tables

import csv
import re

liste = []

# lecture du fichier csv
def lire_fichier_csv(name):
	with open(name, 'r') as fichier:
		reader = csv.reader(fichier)
		for ligne in reader:
			liste.append(ligne)
	return liste

# remplacement des chaines de caractères
def remplacer_chaine():
	for i in range(len(liste)):
		liste[i][0] = re.sub(r"(\d{4})-(\d{2})-(\d{2})", r"\3/\2/\1", liste[i][0])
	return liste

# réécriture des données dans le fichier csv
def ecrire_fichier_csv(name):
	with open(name, 'w') as fichier:
		writer = csv.writer(fichier)
		for ligne in liste:
			writer.writerow(ligne)

# fonction principale
def main(name):
	lire_fichier_csv(name)
	remplacer_chaine()
	ecrire_fichier_csv(name)

# call
main("data_temperature.csv")