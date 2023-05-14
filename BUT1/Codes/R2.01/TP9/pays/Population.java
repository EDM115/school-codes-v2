package pays;

import java.util.ArrayList;
import java.util.HashMap;

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
	 * @param popArray La population
	 * @param areaArray La surface
	 */
	public Population(ArrayList<String> popArray, ArrayList<String> areaArray) {
		if (popArray == null || areaArray == null) {
			System.err.println("\u001B[31mPopulation() : Invalid arguments\u001B[0m");
		} else {
			// On instancie les HashMap
		}
		this.listePays = new ArrayList<Pays>();
		this.popMap = new HashMap<String, Double>();
		this.areaMap = new HashMap<String, Double>();
	}

	/**
	 * Extrait le pays d'une ligne non formatée
	 * @param line La ligne
	 * @return Le pays formaté
	 */
	public String extractCountry(String line) {
		if (line == null) {
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
		if (line == null) {
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
}