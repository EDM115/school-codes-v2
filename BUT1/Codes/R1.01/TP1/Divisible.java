/**
* Teste si une valeur est divisible par 2/3/4/5
* @author EDM115
*/

class Divisible {
	void principal() {
		int divOrNot = SimpleInput.getInt("Entrez un entier : ");
		
		boolean divBy2 = divOrNot % 2 == 0;
		boolean divBy3 = divOrNot % 3 == 0;
		boolean divBy4 = divOrNot % 4 == 0;
		boolean divBy5 = divOrNot % 5 == 0;
		
		if (divBy2) {
			System.out.println(divOrNot + " est divisible par 2");
		}
		if (divBy3) {
			System.out.println(divOrNot + " est divisible par 3");
		}
		if (divBy4) {
			System.out.println(divOrNot + " est divisible par 4");
		}
		if (divBy5) {
			System.out.println(divOrNot + " est divisible par 5");
		}
		if ((!divBy2) && (!divBy3) && (!divBy4) && (!divBy5)) {
			System.out.println(divOrNot + " n'est divisible ni par 2, ni 3, ni 4, ni 5");
		}
	}
}
