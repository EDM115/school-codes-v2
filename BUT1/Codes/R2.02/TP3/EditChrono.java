import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Cette classe permet de gérer le chronomètre
public class EditChrono {
    private static Timer timer;
    private static int seconds;
    private static int minutes;
    private static boolean isRunning;

    public static void main(String[] args) {
        // Le chronomètre est géré par la classe ShowChrono
    }

    // Démarre le chronomètre
    public static void startChronometer() {
        // Si le chronomètre n'est pas déjà démarré
        if (!isRunning) {
            isRunning = true;
            seconds = 0;
            minutes = 0;
            // On crée un timer qui se déclenche toutes les secondes
            timer = new Timer(1000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    seconds++;
                    if (seconds == 60) {
                        seconds = 0;
                        minutes++;
                    }
                    // On met à jour l'affichage du chronomètre
                    String time = String.format("%02d:%02d", minutes, seconds);
                    ShowChrono.updateTime(time);
                }
            });
            timer.start();
        }
    }

    // Arrête le chronomètre
    public static void stopChronometer() {
        if (isRunning) {
            isRunning = false;
            timer.stop();
        }
    }

    // Remet le chronomètre à zéro
    public static void resetChronometer() {
        stopChronometer();
        seconds = 0;
        minutes = 0;
        String time = String.format("%02d:%02d", minutes, seconds);
        ShowChrono.updateTime(time);
    }

    public static boolean isChronometerRunning() {
        return isRunning;
    }
}
