/**
 * This class defines a structure for a promotion of students
 *
 * @author EDM115
 */
public class Promotion {

  /** Name of the promotion */
  private String nom;

  /** Array of students */
  private Etudiant[] liste;

  /**
   * The constructor of a Promotion object
   *
   * @param nom name of the promotion
   * @param liste array of students
   */
  public Promotion(String nom, Etudiant[] liste) {
    if (nom == null) {
      System.err.println("Promotion : Le nom ne peut pas être null");
    } else if (liste == null) {
      System.err.println("Promotion : La liste des étudiants ne peut pas être null");
    } else {
      setNom(nom);
      this.liste = new Etudiant[liste.length];
      for (int i = 0; i < liste.length; i++) {
        this.liste[i] = liste[i];
      }
    }
  }

  /**
   * Getter of the name of the promotion
   *
   * @return the name of the promotion
   */
  public String getNom() {
    String name = this.nom;

    return name;
  }

  /**
   * Setter of the name of the promotion
   *
   * @param nom the name of the promotion
   */
  public void setNom(String nom) {
    if (nom == null) {
      System.err.println("setNom : Le nom ne peut pas être null");
    } else {
      this.nom = nom;
    }
  }

  /**
   * Calculates the average of the promotion
   *
   * @return the average of the promotion
   */
  public double moyenne() {
    double moyenne = -1;

    if (this.liste.length < 0) {
      System.err.println("moyenne : La liste des étudiants est vide");
    } else {
      moyenne = 0;
      double somme = 0;
      int nbEtudiants = 0;
      for (int i = 0; i < this.liste.length; i++) {
        somme += this.liste[i].moyenneGenerale();
        nbEtudiants++;
      }
      moyenne = somme / nbEtudiants;
      moyenne = Math.round(moyenne * 100d) / 100d;
    }

    return moyenne;
  }

  /**
   * Calculates the maximum average of the promotion
   *
   * @return the maximum average of the promotion
   */
  public double moyenneMax() {
    double moyenneMax = -1;

    if (this.liste.length < 0) {
      System.err.println("moyenneMax : La liste des étudiants est vide");
    } else {
      moyenneMax = 0;
      for (int i = 0; i < this.liste.length; i++) {
        if (this.liste[i].moyenneGenerale() > moyenneMax) {
          moyenneMax = this.liste[i].moyenneGenerale();
        }
      }
    }

    return moyenneMax;
  }

  /**
   * Calculates the minimum average of the promotion
   *
   * @return the minimum average of the promotion
   */
  public double moyenneMin() {
    double moyenneMin = -1;

    if (this.liste.length < 0) {
      System.err.println("moyenneMin : La liste des étudiants est vide");
    } else {
      moyenneMin = 20;
      for (int i = 0; i < this.liste.length; i++) {
        if (this.liste[i].moyenneGenerale() < moyenneMin) {
          moyenneMin = this.liste[i].moyenneGenerale();
        }
      }
    }

    return moyenneMin;
  }

  /**
   * Calculates the average of a subject of the promotion
   *
   * @param i the index of the subject
   * @return the average of the subject
   */
  public double moyenneMatiere(int i) {
    double moyenneMatiere = -1;

    if (this.liste.length < 0) {
      System.err.println("moyenneMatiere : La liste des étudiants est vide");
    } else if (i < 0 || i > this.liste[0].getMATIERES().length) {
      System.err.println("moyenneMatiere : L'indice de la matière est incorrect");
    } else {
      moyenneMatiere = 0;
      double somme = 0;
      int nbEtudiants = 0;
      for (int j = 0; j < this.liste.length; j++) {
        somme += this.liste[j].moyenneMatiere(i);
        nbEtudiants++;
      }
      moyenneMatiere = somme / nbEtudiants;
      moyenneMatiere = Math.round(moyenneMatiere * 100d) / 100d;
    }

    return moyenneMatiere;
  }

  /**
   * Returns the student with the maximum average of the promotion
   *
   * @return the student with the maximum average of the promotion
   */
  public Etudiant getMajor() {
    Etudiant major = null;
    double moyenneMax = 0;

    for (int i = 0; i < this.liste.length; i++) {
      if (this.liste[i].moyenneGenerale() > moyenneMax) {
        moyenneMax = this.liste[i].moyenneGenerale();
        major = this.liste[i];
      }
    }

    return major;
  }

  /**
   * Search a student in the promotion by his name and returns the corresponding Etudiant object
   *
   * @param nom the name of the student
   * @return the Etudiant object corresponding to nom
   */
  public Etudiant getEtudiant(String nom) {
    Etudiant etudiant = null;

    if (nom == null) {
      System.err.println("getEtudiant : Le nom ne peut pas être null");
    } else {
      boolean found = false;
      int i = 0;

      while (!found && i < this.liste.length) {
        if (this.liste[i].getNom().equals(nom)) {
          etudiant = this.liste[i];
          found = true;
        }
        i++;
      }
    }

    return etudiant;
  }

  /**
   * Returns a string representation of the object
   *
   * @return a string representation of the object
   */
  public String toString() {
    String str =
        "Promotion "
            + this.nom
            + " : "
            + this.liste.length
            + " étudiants\nMoyenne générale : "
            + moyenne();

    return str;
  }
}
