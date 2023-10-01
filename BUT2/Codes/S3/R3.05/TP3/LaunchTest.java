import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Environnement du test du TP4.
 */
public class LaunchTest {

	/** Taille initiale du jeu */
	static public Dimension gameDim = new Dimension(400, 200);
	/** Taille des rectangles */
	static public Dimension mobileDim = new Dimension(50, 10);

	/**
	 * Point d'entrée du test.
	 * 
	 * @param arg ne sera pas utilisé.
	 */
	static public void main(String[] arg) {
		Game game = new Game(gameDim);
		testComponent("Game", game);

		int PW = gameDim.width, PH = gameDim.height;

		Mobile blueMobile = createAndAddMobile(game, Color.BLUE, new Point(10, PH / 2), mobileDim, new Dimension(-1, 1));
		Mobile greenMobile = createAndAddMobile(game, Color.GREEN, new Point(PW / 2, 10), mobileDim, new Dimension(1, 1));
		Mobile redMobile = createAndAddMobile(game, Color.RED, new Point(PW - 10, PH / 2), mobileDim, new Dimension(-1, -1));
		Mobile yellowMobile = createAndAddMobile(game, Color.YELLOW, new Point(PW / 2, PH - 10), mobileDim, new Dimension(1, -1));
		
		if (blueMobile != null) blueMobile.startSingle();
		if (greenMobile != null) greenMobile.startSingle();
		if (redMobile != null) redMobile.startSingle();
		if (yellowMobile != null) yellowMobile.startSingle();
	}

	/**
	 * Créer une fenêtre pour tester un composant graphique.
	 * 
	 * @param title     le titre de la fenêtre.
	 * @param component le composant à tester.
	 */
	static public final void testComponent(final String title,
			final Component component) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame jFrame = new JFrame(title);
				jFrame.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						System.exit(0);
					}
				});
				jFrame.getContentPane().add(component, BorderLayout.CENTER);
				jFrame.pack();
				jFrame.setLocationRelativeTo(null);
				jFrame.setVisible(true);
			}
		});
	}

	static private Mobile createAndAddMobile(Game game, Color color, Point pos, Dimension dim, Dimension delta) {
        Mobile mobile = new Mobile(game, color, pos, dim, delta);
        if (game.isAncestorOf(mobile)) {
            return mobile;
        }
        return null; // Si le Mobile n'a pas été ajouté en raison de collisions
    }
}
