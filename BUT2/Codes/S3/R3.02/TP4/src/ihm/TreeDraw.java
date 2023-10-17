package ihm;

import java.awt.*;
import javax.swing.*;
import datastruct.BinaryTreeTable;

/**
 * Class allowing to display a binary tree in a graphical window
 * @param <E> the type of the keys
 * @param <T> the type of the data
 * @see BinaryTreeTable
 * @see TreeDrawing
 */
public class TreeDraw<E extends Comparable<E>, T> extends JFrame {
	/**
	 * The Object that will draw the tree
	 */
	private TreeDrawing<E, T> td;

	/**
	 * Constructor that instantiates a graphical Canvas object in which the entire contents of the tree passed as a parameter will be displayed
	 * @param node The node of the tree from which the display is made (i.e. root if we want to display the tree in its entirety)
	 */
	public TreeDraw(BinaryTreeTable<E, T>.Node node) {
		this.td = new TreeDrawing<>(node);
	}
	
	/**
	 * Add the Canvas object to the graphical window
	 */
	public void drawTree() {
		Container pane;

		setTitle("Display Trees");
		setSize(800, 400);
		pane = getContentPane();
		pane.setLayout(new GridLayout(1, 1));

		JPanel treedrawing = new JPanel();

		treedrawing.setLayout(new GridLayout(1, 1));
		treedrawing.add(this.td);                
		pane.add(treedrawing);
		setVisible(true);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
