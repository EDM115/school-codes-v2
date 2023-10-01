import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Classe à développer pour mettre en œuvre les stratégies pour bloquer les
 * mobiles afin de laisser passer les autres.
 */
public class Game extends Board {

    /**
     * Constructeur de la classe qui va accueillir les objets mobiles.
     * 
     * @param dim dimension de l'espace graphique.
     */
    Game(Dimension dim) {
        super(dim);
        
        // Ajouter un MouseListener pour détecter les clics de souris
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Component clickedComponent = getComponentAt(e.getPoint());
                if (clickedComponent instanceof Mobile) {
                    Mobile clickedMobile = (Mobile) clickedComponent;
                    if (clickedMobile.isRunning()) {
                        clickedMobile.stopSingle();
                    } else {
                        clickedMobile.startSingle();
                    }
                }
            }
        });
    }

    /**
     * Demande de déplacement d'un objet
     * 
     * @param mobile objet concerné
     * @param pos    nouvelle position souhaité pour l'objet
     */
    public void moveMobile(Mobile mobile, Point pos) {
        // On vérifie d'abord si le mobile peut se déplacer à la position souhaitée
        CollisionType collisionType = canMove(mobile, pos);

        switch (collisionType) {
            case No:
                // Si aucun obstacle, déplacez le mobile
                changeLocation(mobile, pos);
                break;

            case Collision:
                // Si collision temporaire, faites attendre le mobile
                pause(10); // Attendre 10 millisecondes
                moveMobile(mobile, pos); // Réessayer de déplacer
                break;

            case DeadLock:
                // Si blocage, changez la trajectoire du mobile
                mobile.flip(); // Inverse la direction de déplacement
                break;
        }
    }

    /**
     * Tentative de déplacement.
     * Si le déplacement est temporairement impossible, il faudra le faire attendre.
     * 
     * @param mobile objet concerné
     * @param pos    nouvelle position souhaité pour l'objet
     */
    public synchronized void tryMove(Mobile mobile, Point pos) {
        moveMobile(mobile, pos);
    }
}
