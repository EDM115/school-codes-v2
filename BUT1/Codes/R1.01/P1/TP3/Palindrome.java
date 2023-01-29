/**
 * Teste si la String saisie est un palindrome
 * @author EDM115
 **/
class Palindrome {
	void principal() {
		String mot = SimpleInput.getString("Saisissez un mot : ");
		int i = 0;
		int j = mot.length() - 1;
		char a;
		char b;
		boolean palindrome = true;
		
		// Pas la peine de tester si j % 2 == 0 puisque Java garde la partie entière
		if (j > 1) {
			int limit = j / 2;
			while (i <= limit && palindrome) {
				a = mot.charAt(i);
				b = mot.charAt(j);
				if (a != b) {
					palindrome = false;
				}
				i++;
				j--;
			}
		}
		if (palindrome) {
			System.out.println(mot + " est un palindrome");
		} else {
			System.out.println(mot + " n'est pas un palindrome");
		}
	}
}
