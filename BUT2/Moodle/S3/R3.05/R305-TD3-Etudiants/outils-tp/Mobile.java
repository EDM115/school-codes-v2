// Plus d'informations sur http://r350.merciol.fr
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

/**
   Classe à écrire.

   Vous devez mettre en place une activité qui toutes les 10 millisecondes essayera un déplacement du rectangle hérité.
*/

@SuppressWarnings ("serial")
public class Mobile extends ColoredRectangle {

    // ========================================
    /**
       Constructeur d'un objet mobile dans le panneau.
       @param board panneau dans lequel sera dessiné le mobile
       @param color couleur du mobile
       @param pos position initiale du mobile
       @param dim dimension du mobile
       @param delta vitesse de déplacement (nombre de pixels par 10 millisecondes)
    */
    public Mobile (Board board, Color color,
		   Point pos, Dimension dim,
		   Dimension delta) {
	super (board, color, pos, dim, delta);
//TODO
    }

    // ========================================
    /** 
	Création d'une tâche pour déplacer l'objet
    */
    public void startSingle () {
//TODO
    }

    // ========================================
}
