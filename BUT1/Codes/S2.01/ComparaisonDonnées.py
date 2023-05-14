# Comparaison des données de deux colonnes de deux fichiers CSV, pour trouver quelles données sont présentes uniquement dans le premier, et inversement

import csv

def compare(name1, name2, col1, col2)
	with open(name1, 'r', encoding="utf-8") as csv_file_1, open(name2, 'r', encoding="utf-8") as csv_file_2:

		# Lecture des données du premier fichier CSV
		csv_reader_1 = csv.DictReader(csv_file_1, delimiter=';')
		data_1 = [row[col1] for row in csv_reader_1]

		# Lecture des données du second fichier CSV
		csv_reader_2 = csv.DictReader(csv_file_2, delimiter=';')
		data_2 = [row[col2] for row in csv_reader_2]

		# Comparaison des données
		uniquement_1 = set(data_1) - set(data_2)
		uniquement_2 = set(data_2) - set(data_1)

		# Affichage du résultat
		print("Données présentes uniquement dans le premier fichier CSV :", uniquement_1)
		print("Données présentes uniquement dans le second fichier CSV :", uniquement_2)

compare("data_temperature_2.csv", "data_comptage_nettoyees_v2_2.csv", "Date", "jour")