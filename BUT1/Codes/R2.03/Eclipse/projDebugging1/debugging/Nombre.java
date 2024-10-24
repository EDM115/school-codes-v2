package debugging;

public class Nombre {
	
	private int base;
	private int exposant;
	private int rang;
	
	public Nombre(int base, int exposant, int rg) {
		super();
		this.base = base;
		this.exposant = exposant;
		this.rang = rg;
	}
	
	public int calcul1() {
		int ret = 1;
		int terme = 1;
		
		for (int i = 1; i <= this.rang; i++) {
			terme = terme *  this.exposant;
			ret = ret + terme;
		}
		
		return ret;
	}
	
	public long calcul2(int p) {
		long ret = 1;
		
		for (int i = 0; i < p; i++) {
			ret = ret * this.base;
		}
		
		return ret;
	}
}