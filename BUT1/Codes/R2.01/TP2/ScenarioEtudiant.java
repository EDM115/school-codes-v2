import java.util.Arrays;

/**
 * Tests the Etudiant class
 * @author EDM115
 */
public class ScenarioEtudiant {
	/**
	 * Main method
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		System.out.println("\t\u001B[33mTests Etudiant :\u001B[0m");

		System.out.println("");
		System.out.println("\u001B[36m*** Etudiant()\u001B[0m");
		String[] matieres1 = {"Math", "NSI", "Arts"};
		double[] coeff1 = {1.5, 2, 1};
		String nom1 = "Nathan";
		int nb1 = 6;
		Etudiant etudiant1 = new Etudiant(nom1, matieres1, coeff1, nb1);
		System.out.println("Etudiant(" + nom1 + ", " + Arrays.toString(matieres1) + ", " + Arrays.toString(coeff1) + ", " + nb1 + ") = \n" + etudiant1.toString());
		System.out.println("");

		String[] matieres2 = {"SVT", "Java", "Repos"};
		double[] coeff2 = {1, 2.5, 0};
		String nom2 = "Ronflex";
		int nb2 = 5;
		Etudiant etudiant2 = new Etudiant(nom2, matieres2, coeff2, nb2);
		System.out.println("Etudiant(" + nom2 + ", " + Arrays.toString(matieres2) + ", " + Arrays.toString(coeff2) + ", " + nb2 + ") = \n" + etudiant2.toString());
		System.out.println("");

		String[] matieres3 = {"Jeux", "Repas", "Sieste"};
		double[] coeff3 = {1.8, 0.5, 0.2};
		String nom3 = "Test";
		int nb3 = 2;
		Etudiant etudiant3 = new Etudiant(nom3, matieres3, coeff3, nb3);
		System.out.println("Etudiant(" + nom3 + ", " + Arrays.toString(matieres3) + ", " + Arrays.toString(coeff3) + ", " + nb3 + ") = \n" + etudiant3.toString());

		System.out.println("");
		System.out.println("\u001B[36m*** getNom()\u001B[0m");
		System.out.println("Nathan -> getNom() = " + etudiant1.getNom());
		System.out.println("Ronflex -> getNom() = " + etudiant2.getNom());	
		System.out.println("Test -> getNom() = " + etudiant3.getNom());

		System.out.println("");
		System.out.println("\u001B[36m*** setNom()\u001B[0m");
		nom1 = "Java";
		etudiant1.setNom(nom1);
		System.out.println("Nathan -> setNom(Java) = " + etudiant1.getNom());
		nom3 = "VraiNom";
		etudiant3.setNom(nom3);
		System.out.println("Test -> setNom(VraiNom) = " + etudiant3.getNom());

		System.out.println("");
		System.out.println("\u001B[36m*** getNbMatieres()\u001B[0m");
		System.out.println("Math, NSI, Arts -> getNbMatieres() = " + etudiant1.getNbMatieres());

		System.out.println("");
		System.out.println("\u001B[36m*** getUneNote()\u001B[0m");
		System.out.println(Arrays.toString(etudiant1.getBulletin()[1]) + " -> getUneNote(1, 3) = " + etudiant1.getUneNote(1, 3));
		System.out.println(Arrays.toString(etudiant1.getBulletin()[2]) + " -> getUneNote(2, 3) = " + etudiant1.getUneNote(2, 3));
		System.out.println("etudiant1.getBulletin()[3] -> getUneNote(3, 3) = " + etudiant1.getUneNote(3, 3));
		System.out.println(Arrays.toString(etudiant1.getBulletin()[0]) + " -> getUneNote(0, -1) = " + etudiant1.getUneNote(0, -1));

		System.out.println("");
		System.out.println("\u001B[36m*** moyenneMatiere()\u001B[0m");
		System.out.println(Arrays.toString(etudiant1.getBulletin()[0]) + " -> moyenneMatiere(0) = " + etudiant1.moyenneMatiere(0));
		System.out.println(Arrays.toString(etudiant2.getBulletin()[1]) + " -> moyenneMatiere(1) = " + etudiant2.moyenneMatiere(1));
		System.out.println(Arrays.toString(etudiant3.getBulletin()[2]) + " -> moyenneMatiere(2) = " + etudiant3.moyenneMatiere(2));

		System.out.println("");
		System.out.println("\u001B[36m*** moyenneGenerale()\u001B[0m");
		System.out.println(etudiant1.betterBulletin(etudiant1.getBulletin()) + " -> moyenneGenerale() = " + etudiant1.moyenneGenerale());
		System.out.println(etudiant2.betterBulletin(etudiant2.getBulletin()) + " -> moyenneGenerale() = " + etudiant2.moyenneGenerale());
		System.out.println(etudiant3.betterBulletin(etudiant3.getBulletin()) + " -> moyenneGenerale() = " + etudiant3.moyenneGenerale());

		System.out.println("");
		System.out.println("\u001B[36m*** meilleureNote()\u001B[0m");
		System.out.println(etudiant1.betterBulletin(etudiant1.getBulletin()) + " -> meilleureNote() = " + etudiant1.meilleureNote());
		System.out.println(etudiant2.betterBulletin(etudiant2.getBulletin()) + " -> meilleureNote() = " + etudiant2.meilleureNote());
		System.out.println(etudiant3.betterBulletin(etudiant3.getBulletin()) + " -> meilleureNote() = " + etudiant3.meilleureNote());

		System.out.println("");
		System.out.println("\u001B[36m*** toString()\u001B[0m");
		System.out.println("Etudiant(" + nom1 + ", " + Arrays.toString(matieres1) + ", " + Arrays.toString(coeff1) + ", " + nb1 + ").toString() = \n" + etudiant1.toString());
		System.out.println("Etudiant(" + nom2 + ", " + Arrays.toString(matieres2) + ", " + Arrays.toString(coeff2) + ", " + nb2 + ").toString() = \n" + etudiant2.toString());
		System.out.println("Etudiant(" + nom3 + ", " + Arrays.toString(matieres3) + ", " + Arrays.toString(coeff3) + ", " + nb3 + ").toString() = \n" + etudiant3.toString());

		System.out.println("");
		System.out.println("\u001B[36m*** bulletinGen()\u001B[0m");
		System.out.println("Etudiant(" + nom1 + ", " + Arrays.toString(matieres1) + ", " + Arrays.toString(coeff1) + ", " + nb1 + ").bulletinGen() = \n" + etudiant1.bulletinGen());
		System.out.println("Etudiant(" + nom2 + ", " + Arrays.toString(matieres2) + ", " + Arrays.toString(coeff2) + ", " + nb2 + ").bulletinGen() = \n" + etudiant2.bulletinGen());
		System.out.println("Etudiant(" + nom3 + ", " + Arrays.toString(matieres3) + ", " + Arrays.toString(coeff3) + ", " + nb3 + ").bulletinGen() = \n" + etudiant3.bulletinGen());

		System.out.println("");
	}
}