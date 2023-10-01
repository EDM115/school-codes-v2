import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JLabel;

/**
 * Rectangle de couleur affiché dans un panneau. Les rectangles ont comme
 * caractéristiques :
 * <ul>
 * <li>une couleur</li>
 * <li>une position</li>
 * <li>une taille</li>
 * <li>une vitesse (un déplacement élémentaire par unité de temps)</li>
 * </ul>
 * Un rectangle est immobile par défaut (même si une vitesse est indiquée).
 * Ce sera à vous d'en faire un objet mobile et autonome dans la classe dérivée
 * "Mobile".
 * 
 */

@SuppressWarnings("serial")
public class ColoredRectangle extends JLabel {
    /** Panneau dans lequel évolue l'objet. */
    protected Board board;
    /** Couleur de l'objet. */
    protected Color color;
    /** Vitesse de l'objet (en pixels par 10 millisecondes). */
    protected Dimension delta;

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);
    private final int id;

    /**
     * Constructeur d'un rectangle coloré.
     * 
     * @param board Panneau dans lequel évolua l'objet
     * @param color Couleur de l'objet
     * @param pos   position initiale de l'objet
     * @param dim   dimension de l'objet
     * @param delta vitesse de l'objet (en pixels par 10 millisecondes)
     */
    public ColoredRectangle(Board board, Color color,
            Point pos, Dimension dim,
            Dimension delta) {
        this.board = board;
        this.color = color;
        setLocation(pos);
        setSize(dim);
        board.add(this);
        this.delta = delta;
        this.id = ID_GENERATOR.getAndIncrement();
    }

    /**
     * Inverse la direction de déplacement (horizontalement et verticalement).
     */
    public void flip() {
        delta.width = -delta.width;
        delta.height = -delta.height;
    }

    public int getId() {
        return id;
    }

    /**
     * proposition de nouvelle position en fonction de la vitesse et des dimension
     * du panneau.
     * 
     * @return une proposition de nouvelle position.
     */
    public Point nextPos() {
        int nx = getX() + delta.width;
        int ny = getY() + delta.height;
        if ((nx < 0 && delta.width < 0) ||
                (nx + getWidth() > board.getWidth() && delta.width > 0)) {
            delta.width = -delta.width;
        }
        if ((ny < 0 && delta.height < 0) ||
                (ny + getHeight() > board.getHeight() && delta.height > 0)) {
            delta.height = -delta.height;
        }
        return new Point(getX() + delta.width,
                getY() + delta.height);
    }

    /**
     * Méthode permettant de dessiner un rectangle coloré.
     * 
     * @param g référence vers le contexte graphique
     */
    public void paint(Graphics g) {
        Toolkit.getDefaultToolkit().sync();
        Graphics2D g2 = (Graphics2D) g;
        g2.setBackground(color);
        g2.clearRect(0, 0, getWidth(), getHeight());
    }
}
