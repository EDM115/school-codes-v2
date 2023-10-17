package ihm;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

import datastruct.BinaryTreeTable;

/**
 * Class allowing to display a binary tree in a graphical window
 * @param <E> the type of the keys
 * @param <T> the type of the data
 */
class TreeDrawing<E extends Comparable<E>, T> extends Canvas {
	/**
	 * The node of the tree from which the display is made (i.e. root if we want to display the tree in its entirety)
	 */
	BinaryTreeTable<E, T>.Node myTree;

	/**
	 * The radius of the circles representing the nodes
	 */
	static final int RADIUS = 10;

	/**
	 * Constructor that instantiates a graphical Canvas object in which the entire contents of the tree passed as a parameter will be displayed
	 * @param node The node of the tree from which the display is made (i.e. root if we want to display the tree in its entirety)
	 */
	TreeDrawing(BinaryTreeTable<E, T>.Node node) {
		this.myTree = node;		
	}

	/**
	 * Method that draws the tree in the graphical window
	 * @param g the Graphics object
	 */
	@Override
	public void paint(Graphics g) {
		if (this.myTree != null) {
			drawSubtree(g, myTree, (int)(getWidth()/2), getWidth(), 1);
		}
	}

	/**
	 * Method that draws a subtree in the graphical window
	 * @param g the Graphics object
	 * @param tree the root of the subtree to draw
	 * @param x the x coordinate of the root of the subtree
	 * @param width the width of the subtree
	 * @param level the level of the subtree
	 */
	public void drawSubtree(Graphics g, BinaryTreeTable<E, T>.Node tree, int x, int width, int level) {
		g.setColor(Color.black);
		g.fillOval(x-RADIUS, (level*20)-RADIUS, RADIUS*2, RADIUS*2);
		g.setColor(Color.yellow);

		int w = g.getFontMetrics().stringWidth(tree.getLabel());
		int h = g.getFontMetrics().getHeight();

		g.drawString(tree.getLabel(), x-(w/2), (level*20)+(h/4));
		g.setColor(Color.gray);
		
		if (tree.getLeft() != null) {
			g.drawLine(x-RADIUS, level*20, x-(width/4), (level+1)*20);
			drawSubtree(g, tree.getLeft(), x-(width/4), (width/2), level+1);
		}
		
		if (tree.getRight()!=null) {
			g.drawLine(x+RADIUS, level*20, x+(width/4), (level+1)*20);
			drawSubtree(g, tree.getRight(), x+(width/4), (int)(width/2), level+1);
		}
	}
}
