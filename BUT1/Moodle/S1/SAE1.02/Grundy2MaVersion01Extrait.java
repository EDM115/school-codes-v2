
class Grundy2MaVersion01 {

	ArrayList<ArrayList<Integer>> posPerdantes = new ArrayList<ArrayList<Integer>>();
	
	/**
     * Méthode RECURSIVE qui indique si la configuration (du jeu actuel ou jeu d'essai) est perdante
     * 
     * @param jeu plateau de jeu actuel (l'état du jeu à un certain moment au cours de la partie)
     * @return vrai si la configuration (du jeu) est perdante, faux sinon
     */
    boolean estPerdante(ArrayList<Integer> jeu) {
	
        boolean ret = true; // par défaut la configuration est perdante
		
        if (jeu == null) {
            System.err.println("estPerdante(): le paramètre jeu est null");
        }
		
		else {
		
			// si il n'y a plus que des tas de 1 ou 2 allumettes dans le plateau de jeu
			// alors la situation est forcément perdante (ret=true) = FIN de la récursivité
            if ( !estPossible(jeu) ) {
                ret = true;
            }
			
			// si le plateau de jeu (passé en paramètre) est CONNU comme étant PERDANT
			// alors on retourne immédiatement "true" et on arrête la récursivité (donc on gagne du temps !)
			else if ( estConnuePerdante ( jeu ) ) {
				ret = true;
			}
			
			// sinon il faut poursuivre la décomposition des tas en créant le jeu d'essai suivant
			else {
			
				// création d'un jeu d'essais qui va examiner toutes les décompositions
				// possibles à partir de jeu
                ArrayList<Integer> essai = new ArrayList<Integer>(); // size = 0
				
				// toute première décomposition : enlever 1 allumette au premier tas qui possède
				// au moins 3 allumettes, ligne = -1 signifie qu'il n'y a plus de tas d'au moins 3 allumettes
                int ligne = premier(jeu, essai);
				
                while ( (ligne != -1) && ret) {
					// mise en oeuvre de la règle numéro1
					// Une situation (ou position) est dite perdante pour la machine (ou le joueur, peu importe) si et seulement si TOUTES 
					// ses décompositions possibles (c-à-d TOUTES les actions qui consistent à décomposer un tas en 2 tas inégaux) sont 
					// TOUTES gagnantes pour l'adversaire.
					// Si UNE SEULE décomposition (à partir du jeu) est perdante (pour l'adversaire) alors la configuration n'EST PAS perdante.
					// Ici l'appel à "estPerdante" est RECURSIF.
                    if (estPerdante(essai) == true) {					
                        ret = false;
						
                    } else {
						// génère la configuration d'essai suivante (c'est-à-dire UNE décomposition possible)
						// à partir du jeu, si ligne = -1 il n'y a plus de décomposition possible
                        ligne = suivant(jeu, essai, ligne);
                    }
                }
				
				// si la configuration "essai" est perdante (par exemple [4] ou [3, 3] ou [3, 3, 4]) alors elle est 
				// ajoutée dans le tableau des positions perdantes
				if ( ret ) posPerdantes.add ( essai );
            }
        }
		
        return ret;
    }
	
    /**
     * Détermine si la configuration est connue comme perdante dans posPerdantes
     * 
     * @param jeu le plateau de jeu
     * @return vrai si le jeu est dans posPerdantes
     */
    boolean estConnuePerdante ( ArrayList<Integer> jeu ) {
        // création d'une copie de jeu triée sans 1, ni 2
        ArrayList<Integer> copie = normalise(jeu);
        boolean ret = false;
        int i = 0;
        while ( !ret && i < posPerdantes.size() ) {
            if (sontIdentiques(copie, posPerdantes.get(i))) {
                ret = true;
            }
            i++;
        }
        return ret;
    }
	
	//[........]
}
