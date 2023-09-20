// Plus d'informations sur http://r350.merciol.fr

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.JPanel;
      
/**
   classe qui va dessiner les rectangles colorés.
   Elle contient en particulier une liste de toutes les collisions d'un rectangle avec un autre.

   Vous n'avez pas besoin de modifier cette classe. 
*/

@SuppressWarnings ("serial")
public abstract class Board extends JPanel {

    // ========================================
    /**
       Constructeur qui créé l'espace où sont dessiné les rectangles
       @param dim dimension au départ de l'espace
    */
    public Board (Dimension dim) {
	super (null);
	setMinimumSize (dim);
	setPreferredSize (dim);
    }

    // ========================================
    /**
       Indication sur les type de collisions :<ul>
       <li>No : sans collision</li>
       <li>Collision : temporaire (qui peut être résolue en attendant que la place soit libre)</li>
       <li>DeadLock : impossibilité de mouvement qui impose de changer la trajectoire</li>
       </ul>
    */
    public enum CollisionType { No, Collision, DeadLock; }

    // ========================================
    /**
       Liste les composants en collision avec un autre élément.
    */
    Hashtable<Component, ArrayList<Component>> allCollisions =
	new Hashtable<Component, ArrayList<Component>> ();

    // ========================================
    /**
       Calcul des collisions pour un élément.
       @param mobile objet étudier
       @param pos nouvelle position souhaité pour l'objet
       @return liste des autres objets en collision avec celui fourni en paramètre
    */
    public ArrayList<Component> collisions (Component mobile, Point pos) {
	ArrayList<Component> result = new ArrayList<Component> ();
	Rectangle newRect = new Rectangle (pos, mobile.getSize ());
	for (Component component : getComponents ()) {
	    if (component == mobile)
		continue;
	    if (component.getBounds ().intersects (newRect))
		result.add (component);
	}
	return result;
    }

    // ========================================
    /**
       Estimation des possibilités de mouvement d'un objet
       @param mobile objet étudier
       @param pos nouvelle position souhaité pour l'objet
       @return type de collision (ou pas)
    */
    public CollisionType canMove (Component mobile, Point pos) {
	ArrayList<Component> collisions = collisions (mobile, pos);
	if (collisions.size () == 0) {
	    allCollisions.remove (mobile);
	    return CollisionType.No;
	}
	allCollisions.put (mobile, collisions);
	for (Component component : getComponents ()) {
	    ArrayList<Component> constraints = allCollisions.get (component);
	    if (constraints != null && constraints.contains (mobile))
		return CollisionType.DeadLock;
	}
	return CollisionType.Collision;
    }

    // ========================================
    /**
       Change les coordonnées d'un objet et le redessine
       @param mobile objet concerné
       @param pos nouvelle position libre
    */
    public void changeLocation (Component mobile, Point pos) {
	invalidate ();
	mobile.setLocation (pos);
	validate ();
    }

    // ========================================
    /**
       Méthode mutualiser permettant d'attendre un nombre de millisecondes attendu.

       @param millis nombre de millisecondes à attendre.
    */
    static public void pause (int millis) {
	try {
	    Thread.sleep (millis);
	} catch (InterruptedException e) {
	}
    }

    // ========================================
    /**
       Demande le déplacement d'un objet vers une nouvelle position.
       En cas d'inter-blocage, la nouvelle position est abandonnée.
       @param mobile objet concerné
       @param pos nouvelle position souhaité pour l'objet
    */
    public abstract void moveMobile (Mobile mobile, Point pos);

    // ========================================
}
