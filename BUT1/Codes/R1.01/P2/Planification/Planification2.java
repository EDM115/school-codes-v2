import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
* 
* @author EDM115
*/
class Planification2 {

	void principal() {
		testAfficherHorairesEtDureeTousTrajets();
		testAfficherHorairesEtDureeTrajets2Gares();
		testChercherCorrespondances();
	}

	void afficherHorairesEtDureeTousTrajets(ArrayList<String> trajets, ArrayList<Integer> horaires) {
		System.out.println("");
		int trajetLen = trajets.size();
		int horairesLen = horaires.size();
		if (trajetLen % 4 != 0 && !trajets.isEmpty()) {
			System.err.println("\u001B[31mtrajets n'est pas correct (il doit manquer une valeur)\u001B[0m");
		} else if (horairesLen % 5 != 0 && !horaires.isEmpty()) {
			System.err.println("\u001B[31mhoraires n'est pas correct (il doit manquer une valeur)\u001B[0m");
		} else {
			int i = 0;
			int j = 0;
			while (i < trajetLen) {
				int num = Integer.parseInt(trajets.get(i));
				int indiceHoraires = 0;
				boolean found = false;
				while (!found && j < horairesLen) {
					int num2 = horaires.get(j);
					if (num2 == num) {
						found = true;
						indiceHoraires = j;
					}
					j += 5;
				}
				if (!found) {
					System.err.println("\u001B[31mLe trajet " + num + " n'existe pas dans la table horaires\u001B[0m");
				} else {
					System.out.println("Train " + trajets.get(i + 1) + " numéro " + num + " :");
					Duree depart = new Duree(horaires.get(indiceHoraires + 1), horaires.get(indiceHoraires + 2), 0);
					Duree arrivee = new Duree(horaires.get(indiceHoraires + 3), horaires.get(indiceHoraires + 4), 0);
					System.out.println("\tDépart de " + trajets.get(i + 2) + " à " + depart.enTexte('H'));
					System.out.println("\tArrivée à " + trajets.get(i + 3) + " à " + arrivee.enTexte('H'));
					arrivee.soustraire(depart);
					System.out.println("\tDurée du trajet - " + arrivee.enTexte('H'));
				}
				i += 4;
			}
		}
	}

	void testAfficherHorairesEtDureeTousTrajets() {
		ArrayList<String> trajets = new ArrayList<String>();
		ArrayList<Integer> horaires = new ArrayList<Integer>();
		System.out.println("");
		System.out.println("\u001B[36mremplirLesCollections(trajets, horaires, ../TrajetsEtHoraires.txt);\u001B[0m");
		remplirLesCollections(trajets, horaires, "../TrajetsEtHoraires.txt");
		System.out.println("");
		System.out.println("\u001B[36mafficherHorairesEtDureeTousTrajets(trajets, horaires);\u001B[0m");
		afficherHorairesEtDureeTousTrajets(trajets, horaires);
	}
	
	void remplirLesCollections(ArrayList<String> trajets, ArrayList<Integer> horaires, String nomFichier) {
		boolean eof = false;
		String str;
		BufferedReader tampon;
		try {
			FileReader file = new FileReader(nomFichier);
			tampon = new BufferedReader(file);
			while (!eof) {
				str = tampon.readLine();
				int i = 0;
				int j = 0;
				boolean trajet = false;
				if (str == null) {
					eof = true;
				} else {
					String[] line = str.split("/");
					if (line.length == 4) {
						trajet = true;
					} else if (line.length == 5) {
						trajet = false;
					} else {
						System.err.println("\u001B[31mLe fichier passé en paramètre n'est pas correct (il doit manquer des valeurs)\u001B[0m");
						eof = true;
					}
					if (trajet && !eof) {
						while (i < line.length) {
							trajets.add(line[i].trim());
							i++;
						}
					} else if (!trajet && !eof) {
						while (j < line.length) {
							horaires.add(Integer.parseInt(line[j].trim()));
							j++;
						}
					}
				}
			}
			tampon.close();
		}
		catch (FileNotFoundException e) {
			System.err.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
		}
		catch (IOException e) {
			System.err.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
		}
	}

	void afficherHorairesEtDureeTrajets2Gares(ArrayList<String> trajets, ArrayList<Integer> horaires, String gareDep, String gareDest) {
		System.out.println("");
		int trajetLen = trajets.size();
		int horairesLen = horaires.size();
		if (trajetLen % 4 != 0 && !trajets.isEmpty()) {
			System.err.println("\u001B[31mtrajets n'est pas correct (il doit manquer une valeur)\u001B[0m");
		} else if (horairesLen % 5 != 0 && !horaires.isEmpty()) {
			System.err.println("\u001B[31mhoraires n'est pas correct (il doit manquer une valeur)\u001B[0m");
		} else {
			int i = 0;
			boolean found = false;
			boolean foundOnce = false;
			while (i < trajetLen) {
				if (trajets.get(i + 2).equals(gareDep) && trajets.get(i + 3).equals(gareDest)) {
					found = true;
					foundOnce = true;
				}
				if (found) {
					int j = 0;
					int indiceHoraires = 0;
					boolean found2 = false;
					int num = Integer.parseInt(trajets.get(i));
					while (!found2 && j < horairesLen) {
						int num2 = horaires.get(j);
						if (num2 == num) {
							found2 = true;
							indiceHoraires = j;
						}
						j += 5;
					}
					if (!found2) {
						System.err.println("\u001B[31mLe trajet " + num + " n'existe pas dans la table horaires\u001B[0m");
					} else {
						System.out.println("Train " + trajets.get(i + 1) + " numéro " + num + " :");
						Duree depart = new Duree(horaires.get(indiceHoraires + 1), horaires.get(indiceHoraires + 2), 0);
						Duree arrivee = new Duree(horaires.get(indiceHoraires + 3), horaires.get(indiceHoraires + 4), 0);
						System.out.println("\tDépart de " + trajets.get(i + 2) + " à " + depart.enTexte('H'));
						System.out.println("\tArrivée à " + trajets.get(i + 3) + " à " + arrivee.enTexte('H'));
						arrivee.soustraire(depart);
						System.out.println("\tDurée du trajet - " + arrivee.enTexte('H'));
					}
				}
				i += 4;
				found = false;
			}
			if (!foundOnce) {
				System.err.println("\u001B[31mLe trajet entre " + gareDep + " et " + gareDest + " n'existe pas dans la table trajets\u001B[0m");
			}
		}
	}

	void testAfficherHorairesEtDureeTrajets2Gares() {
		ArrayList<String> trajets = new ArrayList<String>();
		ArrayList<Integer> horaires = new ArrayList<Integer>();
		remplirLesCollections(trajets, horaires, "../TrajetsEtHoraires.txt");
		System.out.println("");
		System.out.println("\u001B[36mafficherHorairesEtDureeTrajets2Gares(trajets, horaires, \"Vannes\", \"Redon\")\u001B[0m");
		afficherHorairesEtDureeTrajets2Gares(trajets, horaires, "Vannes", "Redon");
		System.out.println("");
		System.out.println("\u001B[36mafficherHorairesEtDureeTrajets2Gares(trajets, horaires, \"Paris\", \"Marseille\")\u001B[0m");
		afficherHorairesEtDureeTrajets2Gares(trajets, horaires, "Paris", "Marseille");
	}

	String[] obtenirInfosUnTrajet(String idTrajet, ArrayList<String> trajets) {
		System.out.println("");
		String[] infosTrajet = new String[3];
		boolean found = false;
		int trajetLen = trajets.size();

		for (int i = 0; i < trajetLen; i += 4) {
			if (trajets.get(i).equals(idTrajet)) {
				found = true;
				infosTrajet[0] = trajets.get(i + 1);
				infosTrajet[1] = trajets.get(i + 2);
				infosTrajet[2] = trajets.get(i + 3);
			}
		}
		if (!found) {
			System.err.println("\u001B[31mLe trajet " + idTrajet + " n'existe pas dans trajets\u001B[0m");
		}

		return infosTrajet;
	}

	int[] obtenirInfosUnHoraire(String idTrajet, ArrayList<Integer> horaires) {
		System.out.println("");
		int[] infosHoraire = new int[4];
		boolean found = false;
		int horairesLen = horaires.size();

		for (int i = 0; i < horairesLen; i += 5) {
			if (horaires.get(i).equals(Integer.parseInt(idTrajet))) {
				found = true;
				infosHoraire[0] = horaires.get(i + 1);
				infosHoraire[1] = horaires.get(i + 2);
				infosHoraire[2] = horaires.get(i + 3);
				infosHoraire[3] = horaires.get(i + 4);
			}
		}
		if (!found) {
			System.err.println("\u001B[31mLe trajet " + idTrajet + " n'existe pas dans horaires\u001B[0m");
		}

		return infosHoraire;
	}

	ArrayList<String> trouverTousLesTrajets(String gareDep, ArrayList<String> trajets) {
		System.out.println("");
		ArrayList<String> tousLesTrajets = new ArrayList<String>();
		int trajetLen = trajets.size();

		for (int i = 2; i < trajetLen; i += 4) {
			if (trajets.get(i).equals(gareDep)) {
				tousLesTrajets.add(trajets.get(i - 2));
			}
		}

		return tousLesTrajets;
	}

	ArrayList<String> chercherCorrespondances(String gare, Duree heure, ArrayList<String> trajets, ArrayList<Integer> horaires) {
		System.out.println("");
		ArrayList<String> correspondances = new ArrayList<String>();

		ArrayList<String> tousTrajets = trouverTousLesTrajets(gare, trajets);
		int trajetLen = tousTrajets.size();
		if (trajetLen == 0) {
			System.err.println("\u001B[31mIl n'y a pas de trajet partant de " + gare + "\u001B[0m");
		} else {
			for (int i = 0; i < trajetLen; i++) {
				int[] unHoraire = obtenirInfosUnHoraire(tousTrajets.get(i), horaires);
				Duree hDep = new Duree(unHoraire[0], unHoraire[1], 0);
				int comparaison = hDep.compareA(heure);
				if (comparaison == 1) {
					correspondances.add(tousTrajets.get(i));
				}
			}
		}

		return correspondances;
	}

	void testChercherCorrespondances() {
		ArrayList<String> trajets = new ArrayList<String>();
		ArrayList<Integer> horaires = new ArrayList<Integer>();
		remplirLesCollections(trajets, horaires, "../TrajetsEtHoraires.txt");
		System.out.println("");
		System.out.println("\u001B[36mchercherCorrespondances(\"Vannes\", new Duree(10, 0, 0), trajets, horaires)\u001B[0m");
		ArrayList<String> correspondances1 = chercherCorrespondances("Vannes", new Duree(10, 0, 0), trajets, horaires);
		System.out.println(correspondances1);
		System.out.println("");
		System.out.println("\u001B[36mchercherCorrespondances(\"Paris\", new Duree(10, 0, 0), trajets, horaires)\u001B[0m");
		ArrayList<String> correspondances2 = chercherCorrespondances("Paris", new Duree(10, 0, 0), trajets, horaires);
		System.out.println(correspondances2);
	}
}