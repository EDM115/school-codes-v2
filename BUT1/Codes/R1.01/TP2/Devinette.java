/**
* Calcule un nombre entre 0 et 100, et laisse l'utilisateur deviner ce nombre
* @author EDM115
*/
class Devinette {
	void principal() {
		int random = (int)(Math.random() * 100);
		int guess = SimpleInput.getInt("Devine un nombre entre 0 et 100\n");
		int essais = 0;

		while (guess != random) {
			essais++;
			if (guess < 0 || guess >= 100) {
				guess = SimpleInput.getInt("On a dit entre 0 et 100 !");
			}
			if (guess < random) {
				guess = SimpleInput.getInt("Plus grand ! Essaie encore : ");
			}
			else if (guess > random) {
				guess = SimpleInput.getInt("Plus petit ! Essaie encore : ");
			}
		}
		System.out.println("Bravo, tu as trouvé ! Tu a du faire " + essais + " essais pour trouver " + random);
	}
}
