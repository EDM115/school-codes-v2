package debugging;

public class Bonneteau {
	private Carte[] lesCartes;
	
	public static void main(String[] args) {
		Carte c1 = new Carte("Rouge");
		Carte c2 = new Carte("Verte");
		Carte c3 = new Carte("Verte");
		Carte[] tab = new Carte[3];
		int i = (int)(Math.random() * 3);
		tab[i] = c1;
		tab[(i + 1) % 3] = c2;
		tab[(i + 2) % 3] = c3;
		Bonneteau b = new Bonneteau(tab);
		b.swap(i, ((i + i) % 3));
		b.getUneCarte((5 * i) % 3).swapColor(c3);
		if (b.getUneCarte(0).getColor().equals("Rouge")) {
			System.out.println("Le 1 Gagne bravo !");
		} else {
			System.out.println("Le 1 perd désolé !");
		}
	}
	
	public Bonneteau(Carte[] lesCartes) {
		this.lesCartes = lesCartes;
	}
	
	public void swap(int i, int j) {
		Carte temp = this.lesCartes[i];
		this.lesCartes[i] = this.lesCartes[j];
		this.lesCartes[j] = temp;
	}
	
	public Carte getUneCarte(int k) {
		return this.lesCartes[k];
	}
}
