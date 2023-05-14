import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EditCalendar implements KeyListener {

    private Calendar calendar;
    private ShowCalendar showCalendar;

	// Le constructeur de la classe EditCalendar
    public EditCalendar(Calendar calendar, ShowCalendar showCalendar) {
        this.calendar = calendar;
        this.showCalendar = showCalendar;
        this.showCalendar.addKeyListener(this);
    }

	// On écoute les touches du clavier et on change de jour en fonction de la touche
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			calendar.previousDay();
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			calendar.nextDay();
		}
		showCalendar.updateDateLabels();
	}


    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}
