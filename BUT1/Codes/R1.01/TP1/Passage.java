/**
* Indique si un utilisateur passe dans la classe supérieure
* @author L.Lederrey
*/

class Passage {
	void principal() {
		int passage = 0;
		int passage2 = 0;
		float ue1 = SimpleInput.getInt("Note en UE1 : ");
		if (ue1 >= 10) {
			passage += 1;
		} else if (ue1 >= 8) {
			passage2 += 1;
		}
		float ue2 = SimpleInput.getInt("Note en UE2 : ");
		if (ue2 >= 10) {
			passage += 1;
		} else if (ue2 >= 8) {
			passage2 += 1;
		}
		float ue3 = SimpleInput.getInt("Note en UE3 : ");
		if (ue3 >= 10) {
			passage += 1;
		} else if (ue3 >= 8) {
			passage2 += 1;
		}
		float ue4 = SimpleInput.getInt("Note en UE4 : ");
		if (ue4 >= 10) {
			passage += 1;
		} else if (ue4 >= 8) {
			passage2 += 1;
		}
		float ue5 = SimpleInput.getInt("Note en UE5 : ");
		if (ue5 >= 10) {
			passage += 1;
		} else if (ue5 >= 8) {
			passage2 += 1;
		}
		float ue6 = SimpleInput.getInt("Note en UE6 : ");
		if (ue6 >= 10) {
			passage += 1;
		} else if (ue6 >= 8) {
			passage2 += 1;
		}

		float moyenne = (ue1 + ue2 + ue3 + ue4 + ue5 + ue6) / 6;
		
		if (moyenne >= 8 && passage >= 4) {
			System.out.println("Vous passez en deuxième année avec une moyenne de " + moyenne);
		} else {
			System.out.println("Vous ne passez pas en deuxième année. Votre moyenne est de " + moyenne);
		}
		System.out.println(passage2 + " UE ont une moyenne entre 8 et 10");
	}
}
