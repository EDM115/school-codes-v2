import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.NoSuchElementException;

/**
 * Classe de test de la classe MyScanner
 */
public class TestMyScanner {

	/**
	 * Le point d'entrée du programme
	 * @param args Les arguments de la ligne de commande
	 */
	public static void main(String[] args) {
		File file = new File("../data/test.txt");
		File emptyFile = new File("../data/empty.txt");
		File intFile = new File("../data/int.txt");

		System.out.println("\t\t\u001B[33mTests :\u001B[0m");
		testConstructeur(file);
		testNextLine(file);
		testClose(file);
		testHasNextLine(file);
		testNextInt(intFile, emptyFile);
		testFromSystemIn();
	}

	/**
	 * Teste le constructeur de MyScanner
	 * @param file Le fichier à lire
	 */
	public static void testConstructeur(File file) {
		System.out.println("");
		System.out.println("\u001B[36m*** testConstructeur()\u001B[0m");

		// Cas normaux
		try {
			MyScanner scanner = new MyScanner(file);
			System.out.println("\u001B[32mOK\u001B[0m");
			scanner.close();
		} catch (FileNotFoundException e) {
			System.err.println("\u001B[31mErreur : " + e.getMessage() + "\u001B[0m");
		}

		// Cas d'erreur
		try {
			File nonExistingFile = new File("");
			MyScanner scanner = new MyScanner(nonExistingFile);
			System.err.println("\u001B[31mErreur : Le constructeur n'a pas levé d'exception pour un fichier introuvable\u001B[0m");
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("\u001B[32mCas d'erreur réussi : " + e.getMessage() + "\u001B[0m");
		}
	}

	/**
	 * Teste la méthode nextLine() de MyScanner
	 * @param file Le fichier à lire
	 */
	public static void testNextLine(File file) {
		System.out.println("");
		System.out.println("\u001B[36m*** testNextLine()\u001B[0m");

		// Cas normaux
		try {
			MyScanner scanner = new MyScanner(file);
			String line;
			line = scanner.nextLine();
			System.out.println("\u001B[32mLigne lue : " + line + "\u001B[0m");
			line = scanner.nextLine();
			System.out.println("\u001B[32mLigne lue : " + line + "\u001B[0m");
			scanner.close();
		} catch (FileNotFoundException e) {
			System.err.println("\u001B[31mErreur : " + e.getMessage() + "\u001B[0m");
		}

		// Cas d'erreur
		try {
			File emptyFile = new File("../data/empty.txt");
			MyScanner scanner = new MyScanner(emptyFile);
			String line = scanner.nextLine();
			System.err.println("\u001B[31mErreur : La méthode nextLine() n'a pas levé l'exception NoSuchElementException pour un fichier vide\u001B[0m");
			scanner.close();
		} catch (FileNotFoundException e) {
			System.err.println("\u001B[31mErreur : " + e.getMessage() + "\u001B[0m");
		} catch (NoSuchElementException e) {
			System.out.println("\u001B[32mCas d'erreur réussi : " + e.getMessage() + "\u001B[0m");
		}
	}

	/**
	 * Teste la méthode close() de MyScanner
	 * @param file Le fichier à lire
	 */
	public static void testClose(File file) {
		System.out.println("");
		System.out.println("\u001B[36m*** testClose()\u001B[0m");

		// Cas normal
		try {
			MyScanner scanner = new MyScanner(file);
			scanner.close();
			System.out.println("\u001B[32mOK\u001B[0m");
		} catch (FileNotFoundException e) {
			System.err.println("\u001B[31mErreur : " + e.getMessage() + "\u001B[0m");
		}
	}

	/**
	 * Teste la méthode hasNextLine() de MyScanner
	 * @param file Le fichier à lire
	 */
	public static void testHasNextLine(File file) {
		System.out.println("");
		System.out.println("\u001B[36m*** testHasNextLine()\u001B[0m");

		// Cas normal
		try {
			MyScanner scanner = new MyScanner(file);
			boolean hasNextLine = scanner.hasNextLine();
			System.out.println("\u001B[32mLa méthode hasNextLine a renvoyé : " + hasNextLine + "\u001B[0m");
			scanner.close();
		} catch (FileNotFoundException e) {
			System.err.println("\u001B[31mErreur : " + e.getMessage() + "\u001B[0m");
		}
	}

	/**
	 * Teste la méthode nextInt() de MyScanner
	 * @param file Le fichier à lire
	 * @param emptyFile Le fichier vide
	 */
	public static void testNextInt(File file, File emptyFile) {
		System.out.println("");
		System.out.println("\u001B[36m*** testNextInt()\u001B[0m");

		// Cas normal
		try {
			MyScanner scanner = new MyScanner(file);
			int number = scanner.nextInt();
			System.out.println("\u001B[32mEntier lu : " + number + "\u001B[0m");
			scanner.close();
		} catch (FileNotFoundException e) {
			System.err.println("\u001B[31mErreur : " + e.getMessage() + "\u001B[0m");
		} catch (NoSuchElementException e) {
			System.err.println("\u001B[31mErreur : " + e.getMessage() + "\u001B[0m");
		} catch (IllegalStateException e) {
			System.err.println("\u001B[31mErreur : " + e.getMessage() + "\u001B[0m");
		}

		// Cas d'erreur
		try {
			MyScanner scanner = new MyScanner(emptyFile);
			int number = scanner.nextInt();
			System.err.println("\u001B[31mErreur : nextInt() n'a pas lancé l'exception NoSuchElementException et a lu l'entier : " + number + "\u001B[0m");
			scanner.close();
		} catch (FileNotFoundException e) {
			System.err.println("\u001B[31mErreur : " + e.getMessage() + "\u001B[0m");
		} catch (NoSuchElementException e) {
			System.out.println("\u001B[32mCas d'erreur réussi : " + e.getMessage() + "\u001B[0m");
		} catch (IllegalStateException e) {
			System.err.println("\u001B[31mErreur : " + e.getMessage() + "\u001B[0m");
		}
	}

	/**
	 * Teste MyScanner avec System.in
	 */
	public static void testFromSystemIn() {
		System.out.println("");
		System.out.println("\u001B[36m*** testFromSystemIn()\u001B[0m");

		// Cas normal
		try {
			String input = "42"; // Entrée simulée : l'utilisateur entre "42"
			InputStream in = new ByteArrayInputStream(input.getBytes());
			MyScanner scanner = new MyScanner(in);
			int number = scanner.nextInt();
			System.out.println("\u001B[32mEntier lu : " + number + "\u001B[0m");
			scanner.close();
		} catch (NoSuchElementException e) {
			System.err.println("\u001B[31mErreur : " + e.getMessage() + "\u001B[0m");
		} catch (IllegalStateException e) {
			System.err.println("\u001B[31mErreur : " + e.getMessage() + "\u001B[0m");
		}

		// Cas d'erreur
		try {
			String input = ""; // Entrée simulée : l'utilisateur entre une valeur vide
			InputStream in = new ByteArrayInputStream(input.getBytes());
			MyScanner scanner = new MyScanner(in);
			int number = scanner.nextInt();
			System.err.println("nextInt() n'a pas lancé l'exception NoSuchElementException/IllegalStateException et a lu l'entier : " + number);
			scanner.close();
		} catch (NoSuchElementException e) {
			System.out.println("\u001B[32mCas d'erreur réussi : " + e.getMessage() + "\u001B[0m");
		} catch (IllegalStateException e) {
			System.out.println("\u001B[32mCas d'erreur réussi : " + e.getMessage() + "\u001B[0m");
		}
	}

	/**
	 * Constructeur par défaut
	 */
	public TestMyScanner() {
		super();
	}
}
