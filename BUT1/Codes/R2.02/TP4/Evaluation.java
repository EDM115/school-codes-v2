import java.io.Serializable;

public class Evaluation implements Serializable {
	private int coeff;
	private int note;

	public Evaluation(int coeff, int note) throws IllegalArgumentException {
		if (coeff < 0 || coeff > 10) {
			throw new IllegalArgumentException("Evaluation() : Le coefficient doit être compris entre 0 et 20");
		} else if (note < 0 || note > 20) {
			throw new IllegalArgumentException("Evaluation() : La note doit être comprise entre 0 et 20");
		} else {
			this.coeff = coeff;
			this.note = note;
		}
	}

	public int getCoeff() {
		return this.coeff;
	}

	public int getNote() {
		return this.note;
	}
}
