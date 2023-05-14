package debugging;

public class Carte {
	private String color;
	
	public Carte(String color) {
		super();
		this.color = color;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	public void swapColor(Carte c) {
		String col = this.color;
		this.color = c.getColor();
		c.setColor(col);
	}
}
