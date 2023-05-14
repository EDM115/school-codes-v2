package pays;

import java.util.ArrayList;
import java.util.HashMap;

import utilitaire.RWFile;

/**
 * Classe Population, permet de manipuler des Pays
 */
public class Population {
	
	/**
	 * ArrayList de Pays
	 */
	ArrayList<Pays> listePays;

	/**
	 * Représente la population
	 */
	HashMap<String, Double> popMap;

	/**
	 * Représente la surface
	 */
	HashMap<String, Double> areaMap;

	/**
	 * Constructeur de Population
	 * @param popFile La population
	 * @param areaFile La surface
	 */
	public Population(String popFile, String areaFile) {
		if (popFile == null || popFile.length() == 0 || areaFile == null || areaFile.length() == 0) {
			System.err.println("\u001B[31mPopulation() : Invalid arguments\u001B[0m");
		} else {
			initialisePopMap(popFile);
			initialiseAreaMap(areaFile);
		}
		this.listePays = new ArrayList<Pays>();
		initialiseListePays();
	}

	/**
	 * Extrait le pays d'une ligne non formatée
	 * @param line La ligne
	 * @return Le pays formaté
	 */
	public String extractCountry(String line) {
		if (line == null || line.length() == 0) {
			System.err.println("\u001B[31mextractCountry() : Invalid arguments\u001B[0m");
			return "";
		} else {
			char c = line.charAt(0);
			int i = 0;
			while (!Character.isDigit(c) && i < line.length()) {
				i++;
				c = line.charAt(i);
			}
			return line.substring(0, i - 1).trim();
		}
	}

	/**
	 * Extrait la valeur d'une ligne non formatée
	 * @param line La ligne
	 * @return La valeur formatée
	 */
	public double extractValue(String line) {
		if (line == null || line.length() == 0) {
			System.err.println("\u001B[31mextractValue() : Invalid arguments\u001B[0m");
			return 0;
		} else {
			char c = line.charAt(0);
			int i = 0;
			while (!Character.isDigit(c) && i < line.length()) {
				i++;
				c = line.charAt(i);
			}
			return Double.parseDouble(line.substring(i).trim());
		}
	}

	/**
	 * Tranforme une ArrayList en HashMap
	 * @param liste L'ArrayList
	 * @return L'HashMap
	 */
	public HashMap<String,Double> asMap(ArrayList<String> liste) {
		if (liste == null) {
			System.err.println("\u001B[31masMap() : Invalid arguments\u001B[0m");
			return null;
		} else {
			HashMap<String,Double> map = new HashMap<String,Double>();
			for (String line : liste) {
				map.put(extractCountry(line), extractValue(line));
			}
			return map;
		}
	}

	/**
	 * Initialise la HashMap popMap
	 * @param popFile Le fichier de population
	 */
	private void initialisePopMap(String popFile) {
		if (popFile == null || popFile.length() == 0) {
			System.err.println("\u001B[31minitialisePopMap() : Invalid arguments\u001B[0m");
		} else {
			ArrayList<String> popArray = RWFile.readFile(popFile);
			this.popMap = asMap(popArray);
		}
	}

	/**
	 * Initialise la HashMap areaMap
	 * @param areaFile Le fichier de surface
	 */
	private void initialiseAreaMap(String areaFile) {
		if (areaFile == null || areaFile.length() == 0) {
			System.err.println("\u001B[31minitialiseAreaMap() : Invalid arguments\u001B[0m");
		} else {
			ArrayList<String> areaArray = RWFile.readFile(areaFile);
			this.areaMap = asMap(areaArray);
		}
	}

	/**
	 * Initialise la liste de pays
	 */
	private void initialiseListePays() {
		if (popMap == null || areaMap == null) {
			System.err.println("\u001B[31minitialiseListePays() : HashMaps empty\u001B[0m");
		} else if (popMap.size() != areaMap.size()) {
			System.err.println("\u001B[31minitialiseListePays() : HashMaps siezs doesn't match\u001B[0m");
		} else {
			for (String key : popMap.keySet()) {
				if (key == null || key.length() == 0) {
					System.err.println("\u001B[31minitialiseListePays() : A blank line have been encountered\u001B[0m");
				} else {
					if (areaMap.containsKey(key)) {
						listePays.add(new Pays(key, areaMap.get(key), popMap.get(key)));
					} else {
						listePays.add(new Pays(key, 0, popMap.get(key)));
					}
				}
			}
		}
	}
}