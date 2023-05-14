package utilitaire;

import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Classe RWFile, permet de lire et d'écrire dans un fichier
 */
public class RWFile {
	/**
	 * Default constructor
	 */
	public RWFile() {
		super();
	}

	/**
	 * Cette méthode lit les données d'un fichier texte , dont le nom est passé en paramètre, et les met dans un ArrayList qui est retourné en résultat. Pour cela elle lit le fichier ligne par ligne
	 * @param filename le nom du fichier à lire
	 * @return un ArrayList contenant les lignes du fichier
	 */ 
	public static ArrayList<String> readFile(String filename) {
		if (filename == null || filename.length() == 0) {
			System.out.println("readFile(" + filename + ") : Nom de fichier invalide");
			return null;
		}

		ArrayList<String> liste = new ArrayList<String>();

		try {
			Scanner sc = new Scanner(new FileReader(filename));
			while (sc.hasNextLine()) {
				liste.add(sc.nextLine());
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("readFile(" + filename + ") : Fichier non trouvé");
		}

		return liste;
	}

	/**
	 * Cette méthode écrit les données d'un ArrayList dans un fichier texte , dont le nom est passé en paramètre. Pour cela elle parcourt l'ArrayList et écrit chaque élément dans le fichier
	 * @param liste l'ArrayList à écrire
	 * @param fileName le nom du fichier à écrire
	 * @throws FileNotFoundException si le fichier n'est pas trouvé
	 */
	public static void writeFile(ArrayList<String> liste, String fileName) throws FileNotFoundException {
		if (fileName == null || fileName.length() == 0) {
			System.out.println("writeFile(" + fileName + ") : Nom de fichier invalide");
			return;
		}

		if (liste == null) {
			System.out.println("writeFile(" + fileName + ") : Liste invalide");
			return;
		}

		PrintWriter pw = new PrintWriter(fileName);
		for (String s : liste) {
			pw.println(s);
		}
		pw.close();
	}
}