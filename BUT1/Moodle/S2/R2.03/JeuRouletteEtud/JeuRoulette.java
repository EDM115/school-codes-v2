/**
  * Jeu de la roulette : le joueur parie sur 1 case de la roulette de Casino.
  * Ensuite la roulette tourne et si la bille tombe sur la case choisie par le joueur, le jeu s'arrête.
  */
public class JeuRoulette {

	// Usage : java JeuRoulette <nomJou> <nbCases>
	public static void main ( String[] args ) {

		// variables locales
		int choix, hasard, nbEssais, nbCases;
		double reel;
		Roulette roul = null;
		String nomJou = null;
		
		// nom du joueur
		nomJou = args[0];
		
		// nombre de cases de la roulette
		nbCases = Integer.parseInt ( args[1] );

		// ToDo : modifier le code pour capturer les exceptions lancées par le constructeur de Roulette.
		// Ces exceptions détectent les cas d'erreurs (nomJou erroné ou nbCases erroné).
		roul = new Roulette ( nomJou, nbCases );
		
		// le pari du joueur
		reel = Math.random() * nbCases;
		reel++;
		choix = (int) reel;
		System.out.println ( "\nLe joueur " + roul.getNomJou() + " a parie sur le : " +  choix );
		hasard = 0;
		nbEssais = 0;

		// ToDo : boucle automatique de jeu et sortir dès que la bille tombe sur la bonne case (sur laquelle le joueur a parié).
		// 1. appeler tournerRoulette de la classe Roulette
		// 2. capturer obligatoirement l'exception "Aleatoire" et récupérer l'entier aléatoire de l'objet (de type Aleatoire) avec l'accesseur
		//		et afficher "La roulette est tombee sur le : <hasard>"
		// 3. comparer cet entier aléatoire avec <choix> du joueur
		// 4. si égalité alors :
		//		- afficher "Joueur <nomJou> GAGNE en <nbEssais> tentative(s)"
		//		- sortir de la boucle
		// 5. si pas égalité alors :
		//		- afficher "Joueur <nomJou> PERDU..."
		//		- continuer à boucler

	}
}
		
