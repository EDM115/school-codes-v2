// Plus d'informations sur http://r350.merciol.fr
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

/**
   Classe à développer pour mettre en œuvre les stratégies pour bloquer les mobiles afin de laisser passer les autres.

*/
public class Game extends Board {

    // ========================================
    /**
       Constructeur de la classe qui va accueillir les objets mobiles.
       @param dim dimension de l'espace graphique.
    */
    Game (Dimension dim) {
	super (dim);
    }

    // ========================================
    /**
       Demande de déplacement d'un objet
       @param mobile objet concerné
       @param pos nouvelle position souhaité pour l'objet
    */
    public void moveMobile (Mobile mobile, Point pos) {
//TODO
    }

    // ========================================
    /**
       Tentative de déplacement. 
       Si le déplacement est temporairement impossible, il faudra le faire attendre.
       @param mobile objet concerné
       @param pos nouvelle position souhaité pour l'objet
    */
    public synchronized void tryMove (Mobile mobile, Point pos) {
//TODO
    }

    // ========================================
}
