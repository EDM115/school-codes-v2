import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

/**
 * Crée une activité qui toutes les 10 millisecondes essayera un déplacement du rectangle hérité.
 */
public class Mobile extends ColoredRectangle implements Runnable {

    private Thread thread; // Ajout d'un thread pour le mouvement

    // Ajouter un état pour vérifier si le thread est en cours d'exécution
    private volatile boolean running = false;

    public Mobile(Board board, Color color, Point pos, Dimension dim, Dimension delta) {
        super(board, color, pos, dim, delta);

        // Vérification des collisions lors de la création
        ArrayList<Component> collisions = board.collisions(this, pos);
        if (!collisions.isEmpty()) {
            board.remove(this); // Supprimer le Mobile du panneau s'il y a des collisions
        }
    }

    /**
     * Création d'une tâche pour déplacer l'objet
     */
    public void startSingle() {
        if (thread == null) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    // Ajouter une méthode pour arrêter le thread
    public void stopSingle() {
        running = false;
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
    }

    // Vérifier si le thread est en cours d'exécution
    public boolean isRunning() {
        return running;
    }

    @Override
    public void run() {
        while (running) {
            Point nextPosition = nextPos();
            board.moveMobile(this, nextPosition);
            Board.pause(10);
        }
    }
}
