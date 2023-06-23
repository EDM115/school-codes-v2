public class Operations {
	public int additionne(int premier, int second) {
		int ret = premier + second;
		return ret;
	}

	public double calculeRacineCarree (double val) throws ArithmeticException {
		double ret = Math.sqrt(val);
		if (Double.isNaN(ret)) {
			throw new ArithmeticException("Racine carrée d'un nombre négatif");
		}
		return ret;
	}
}