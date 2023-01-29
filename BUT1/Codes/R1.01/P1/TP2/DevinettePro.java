/**
* L'utilisateur donne un nombre entre 0 et 1000, et le programme doit le deviner
* @author EDM115
*/
class DevinettePro {
	void principal() {
		int nombre = SimpleInput.getInt("Entrez un nombre entre 0 et 1000 : ");
		while (nombre < 0 || nombre > 1000) {
			nombre = SimpleInput.getInt("On a dit entre 0 et 1000 !");
		}

		int borneMin = 0;
		int borneMax = 1000;
		int guess = 500;
		char answer = '!';
		while (answer != '=') {
			if ((borneMax - borneMin) == 2) {
				guess = borneMax - 1;
				break;
			}
			answer = SimpleInput.getChar("Est-ce que c'est " + guess + " ?\n+ : non, c'est plus grand\t- : non, c'est plus petit\t= : c'est bon !\n");
			while (answer != '+' && answer != '-' && answer != '=') {
				answer = SimpleInput.getChar("Je n'ai pas compris. Utilisez la norme suivante :\n+ : non, c'est plus grand\t- : non, c'est plus petit\t= : c'est bon !\n");
			}
			while (answer == '=' && guess != nombre) {
				answer = SimpleInput.getChar("Tu es un menteur !\n+ : non, c'est plus grand\t- : non, c'est plus petit\n");
			}
			if (answer != '=' && guess == nombre) {
				System.out.println("Tu es un menteur !\tLe nombre est bien " + guess);
				return;
			}
			if (answer == '+') {
				borneMin = guess;
				guess = borneMin + ((borneMax - borneMin) / 2);
			}
			if (answer == '-') {
				borneMax = guess;
				guess = borneMax - ((borneMax - borneMin) / 2);
			}
		}
		System.out.println("J'ai trouvé ! Il s'agit de " + guess);
	}
}
