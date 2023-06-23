/**
  * Jeu de la roulette : le joueur parie sur 1 case de la roulette de Casino
  * Ensuite la roulette tourne et si la bille tombe sur la case choisie par le joueur, le jeu s'arrête
  */
public class JeuRoulette {

	/**
	 * Méthode principale, usage : java JeuRoulette nomJou nbCases
	 * @param args nomJou et nbCases, respectivement le nom du joueur et le nombre de cases de la roulette
	 */
	public static void main(String[] args) {

		// variables locales
		int choix, hasard, nbEssais, nbCases;
		double reel;
		Roulette roul = null;
		String nomJou = null;

		// nom du joueur
		nomJou = args[0];

		// nombre de cases de la roulette
		nbCases = Integer.parseInt(args[1]);

		// Ces exceptions détectent les cas d'erreurs (nomJou erroné ou nbCases erroné)
		try {
			roul = new Roulette(nomJou, nbCases);
		} catch (RuntimeException e) {
			System.err.println("Erreur : " + e.getMessage() + "\n nomJou erroné ou nbCases erroné");
			System.exit(1);
		}

		// le pari du joueur
		reel = Math.random() * nbCases;
		reel++;
		choix = (int)reel;
		System.out.println("\nLe joueur " + roul.getNomJou() + " a parié sur le : " +  choix);
		hasard = 0;
		nbEssais = 0;

		// 1. appeler tournerRoulette de la classe Roulette
		// 2. capturer obligatoirement l'exception "Aleatoire" et récupérer l'entier aléatoire de l'objet (de type Aleatoire) avec l'accesseur et afficher "La roulette est tombée sur le : <hasard>"
		// 3. comparer cet entier aléatoire avec <choix> du joueur
		// 4. si égalité alors :
		//		- afficher "Joueur <nomJou> GAGNE en <nbEssais> tentative(s)"
		//		- sortir de la boucle
		// 5. si pas égalité alors :
		//		- afficher "Joueur <nomJou> PERDU..."
		//		- continuer à boucler

		try {
			roul.tournerRoulette();
		} catch (Aleatoire e) {
			hasard = e.getAleat();
			System.out.println("La roulette est tombée sur le : " + hasard);
		}

		while (hasard != choix) {
			System.out.println("Joueur " + roul.getNomJou() + " PERDU...");
			try {
				roul.tournerRoulette();
			} catch (Aleatoire e) {
				hasard = e.getAleat();
				System.out.println("La roulette est tombée sur le : " + hasard);
			}
			nbEssais++;
		}
		System.out.println("Joueur " + roul.getNomJou() + " GAGNE en " + nbEssais + " tentative(s)");
	}

	/**
	 * Constructeur par défaut
	 */
	public JeuRoulette() {
		super();
	}
}
		
