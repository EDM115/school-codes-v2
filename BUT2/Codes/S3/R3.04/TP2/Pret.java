public class Pret {
	private Ouvrage ouv;
	private Client cli;

	public Pret(Ouvrage ouv, Client cli) {
		this.ouv = ouv;
		this.cli = cli;
		ouv.decrementer();
		cli.incrementerNbEmprunts();
	}
}