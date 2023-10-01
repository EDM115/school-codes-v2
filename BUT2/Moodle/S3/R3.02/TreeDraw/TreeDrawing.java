package ihm;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

import datastruct.BinaryTreeTable;

class TreeDrawing<E extends Comparable<E>, T> extends Canvas {
	
	BinaryTreeTable<E, T>.Node myTree;
	static final int RADIUS = 10;

	TreeDrawing(BinaryTreeTable<E, T>.Node node){
		
		this.myTree = node;		
	}

	@Override
	public void paint(Graphics g) {
				
		if ( this.myTree != null ) {
				
			drawSubtree(g,myTree,(int)(getWidth()/2),getWidth(),1);
		}
	}

	public void drawSubtree(Graphics g,BinaryTreeTable<E, T>.Node tree, int x, int width,int level){
		
		g.setColor(Color.black);
		g.fillOval(x-RADIUS,(level*20)-RADIUS,RADIUS*2,RADIUS*2);
		g.setColor(Color.yellow);
		int w = g.getFontMetrics().stringWidth(tree.getLabel());
		int h = g.getFontMetrics().getHeight();
		g.drawString(tree.getLabel(),x-(w/2),(level*20)+(h/4));
		g.setColor(Color.gray);
		
		if (tree.getLeft()!=null){
			g.drawLine(x-RADIUS,level*20,x-(width/4),(level+1)*20);
			drawSubtree(g,tree.getLeft(),x-(width/4),(width/2),level+1);
		}
		
		if (tree.getRight()!=null){
			g.drawLine(x+RADIUS,level*20,x+(width/4),(level+1)*20);
			drawSubtree(g, tree.getRight(), x+(width/4), (int)(width/2), level+1);
		}
	}
	
}