/**
* Essai de la configuration R1.01
*/

class AlgoCalcul {
	
	void principal() {
		int var1;
		int var2;
		
		var1 = SimpleInput.getInt("val1 = ");
		var2 = SimpleInput.getInt("val2 = ");
		
		var1 = 25 * var1 + 5 * var2;
		System.out.println(var1);
	}
}
