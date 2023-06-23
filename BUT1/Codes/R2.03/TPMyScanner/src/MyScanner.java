import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

/**
 * MyScanner est une classe qui permet de lire des fichiers ou des entrées clavier
 * Elle mime le comportement de la classe Scanner de Java
 */
public class MyScanner {

	/**
	 * Le buffer de lecture
	 */
	private BufferedReader br;

	/**
	 * Indique si le scanner est fermé
	 */
	private boolean isClosed;

	/**
	 * Constructeur 1 de MyScanner
	 * @param source Le fichier à lire
	 * @throws FileNotFoundException Si le fichier n'existe pas
	 */
	public MyScanner(File source) throws FileNotFoundException {
		if (!source.exists()) {
			throw new FileNotFoundException("Fichier non trouvé");
		}
		FileReader fr = new FileReader(source);
		br = new BufferedReader(fr);
		isClosed = false;
	}

	/**
	 * Constructeur 2 de MyScanner
	 * @param in L'entrée clavier à lire
	 */
	public MyScanner(InputStream in) {
		InputStreamReader isr = new InputStreamReader(in);
		br = new BufferedReader(isr);
		isClosed = false;
	}

	/**
	 * Vérifie que le scanner n'est pas fermé
	 * @throws IllegalStateException Si le scanner est fermé
	 */
	public void close() {
		try {
			if (br != null) {
				br.close();
				isClosed = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Lis la prochaine ligne du fichier
	 * @return La prochaine ligne du fichier
	 * @throws IllegalStateException Lors d'une erreur de lecture
	 * @throws NoSuchElementException Si il n'y a plus de ligne à lire
	 */
	public String nextLine() throws IllegalStateException, NoSuchElementException {
		validateState();
		try {
			String line = br.readLine();
			if (line == null) {
				throw new NoSuchElementException("Plus aucune ligne à lire");
			}
			return line;
		} catch (IOException e) {
			throw new IllegalStateException("Il y a eu une erreur lors de la lecture de la ligne : ", e);
		}
	}

	/**
	 * Vérifie si il y a une prochaine ligne à lire
	 * @return Vrai si il y a une prochaine ligne à lire, faux sinon
	 * @throws IllegalStateException Lors d'une erreur de lecture
	 */
	public boolean hasNextLine() throws IllegalStateException {
		validateState();
		try {
			br.mark(100);
			String line = br.readLine();
			br.reset();
			return (line != null);
		} catch (IOException e) {
			throw new IllegalStateException("Il y a eu une erreur lors de la vérification de la prochaine ligne : ", e);
		}
	}

	/**
	 * Lis le prochain entier du fichier
	 * @return Le prochain entier du fichier
	 * @throws IllegalStateException Lors d'une erreur de lecture
	 * @throws NoSuchElementException Si il n'y a plus d'entier à lire
	 * @throws InputMismatchException Si l'entrée n'est pas un entier
	 */
	public int nextInt() throws IllegalStateException, NoSuchElementException, InputMismatchException {
		validateState();
		try {
			String input = br.readLine();
			if (input == null) {
				throw new NoSuchElementException("Plus d'entrée à lire");
			}
			return Integer.parseInt(input);
		} catch (IOException e) {
			throw new IllegalStateException("Erreur durant la lecture de l'entrée", e);
		} catch (NumberFormatException e) {
			throw new InputMismatchException("L'entrée n'est pas un nombre valide");
		}
	}

	/**
	 * Vérifie si le scanner est ouvert
	 * @throws IllegalStateException Si le scanner est fermé
	 */
	private void validateState() {
		if (isClosed) {
			throw new IllegalStateException("Le scanner est fermé");
		}
	}
}
