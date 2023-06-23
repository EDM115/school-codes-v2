import utilitaire.*;

import java.util.Arrays;
import java.util.ArrayList;

/**
 * Classe de test de RWFile
 */
public class ScenarioRWFile {
	/**
	 * Contient les tests
	 */
	public ScenarioRWFile() {
		System.out.println("\t\t\u001B[33mTests de RWFile :\u001B[0m");
		System.out.println("");

		System.out.println("\u001B[36m*** readFile()\u001B[0m");
		ArrayList<String> liste = RWFile.readFile("../data/worldpop.txt");
		System.out.println("readFile(\"../data/worldpop.txt\") : " + liste.toString());
		ArrayList<String> liste2 = RWFile.readFile("");
		System.out.println("readFile(\"\") : " + liste2);

		System.out.println("");
		System.out.println("\u001B[36m*** writeFile()\u001B[0m");
		ArrayList<String> liste3 = new ArrayList<String>(Arrays.asList("Hello", "World"));
		try {
			RWFile.writeFile(liste3, "../data/test.txt");
			System.out.println("writeFile([\"Hello\", \"World\"], \"../data/test.txt\") : OK");
		} catch (Exception e) {
			System.out.println("writeFile([\"Hello\", \"World\"], \"../data/test.txt\") : " + e.getMessage());
		}
	}

	/**
	 * Point d'entrée du programme
	 * @param args Les arguments
	 */
	public static void main(String[] args) {
		new ScenarioRWFile();
	}
}
