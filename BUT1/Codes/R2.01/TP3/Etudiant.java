import java.util.Arrays;

/**
 * This class defines a structure for a student
 *
 * @author EDM115
 */
public class Etudiant {

  /** Student name */
  private String nom;

  /** Bidimentionnal array of the bulletin */
  private double[][] bulletin;

  /** Array of subjects */
  private final String[] MATIERES;

  /** Array of coefficients */
  private final double[] COEFFICIENTS;

  /**
   * The constructor of an Etudiant object
   *
   * @param nom student name
   * @param matieres array of subjects
   * @param coeff array of coefficients
   * @param nbNotes number of grades
   */
  public Etudiant(String nom, String[] matieres, double[] coeff, int nbNotes) {
    if (nom == null) {
      System.err.println("Etudiant : Le nom ne peut pas être null");
      this.MATIERES = new String[0];
      this.COEFFICIENTS = new double[0];
    } else if (matieres == null) {
      System.err.println("Etudiant : Le tableau des matières ne peut pas être null");
      this.MATIERES = new String[0];
      this.COEFFICIENTS = new double[0];
    } else if (coeff == null) {
      System.err.println("Etudiant : Le tableau des coefficients ne peut pas être null");
      this.MATIERES = new String[0];
      this.COEFFICIENTS = new double[0];
    } else if (matieres.length != coeff.length) {
      System.err.println(
          "Etudiant : Le tableau des matières et le tableau des coefficients doivent avoir la même"
              + " taille");
      this.MATIERES = new String[0];
      this.COEFFICIENTS = new double[0];
    } else if (nbNotes < 0) {
      System.err.println("Etudiant : Le nombre de notes doit être supérieur ou égal à 0");
      this.MATIERES = new String[0];
      this.COEFFICIENTS = new double[0];
    } else {
      setNom(nom);
      this.MATIERES = new String[matieres.length];
      for (int i = 0; i < matieres.length; i++) {
        this.MATIERES[i] = matieres[i];
      }
      this.COEFFICIENTS = new double[coeff.length];
      for (int j = 0; j < coeff.length; j++) {
        this.COEFFICIENTS[j] = coeff[j];
      }
      this.bulletin = new double[matieres.length][nbNotes];
      this.initialisation();
    }
  }

  /** Initializes the bulletin with random grades from 0 to 20 */
  private void initialisation() {
    for (int i = 0; i < this.bulletin.length; i++) {
      for (int j = 0; j < this.bulletin[i].length; j++) {
        double val = Math.random() * 20;
        val = Math.round(val * 100d) / 100d;
        this.bulletin[i][j] = val;
      }
    }
  }

  /**
   * Setter of the nom attribute
   *
   * @param nom student name
   */
  public void setNom(String nom) {
    if (nom == null) {
      System.err.println("setNom : Le nom ne peut pas être null");
    } else {
      this.nom = nom;
    }
  }

  /**
   * Getter of the nom attribute
   *
   * @return the student name
   */
  public String getNom() {
    String nom = this.nom;

    return nom;
  }

  /**
   * Getter of the bulletin attribute (utility method used for testing)
   *
   * @return the student name
   */
  public double[][] getBulletin() {
    double[][] bulletin = this.bulletin;

    return bulletin;
  }

  /**
   * Getter of the MATIERES attribute
   *
   * @return the subjects array
   */
  public String[] getMATIERES() {
    String[] matieres = this.MATIERES;

    return matieres;
  }

  /**
   * Getter of the COEFFICIENTS attribute
   *
   * @return the coefficients array
   */
  public double[] getCOEFFICIENTS() {
    double[] coeff = this.COEFFICIENTS;

    return coeff;
  }

  /**
   * Utility method used to get the bulletin in a String format
   *
   * @param bulletin the bulletin
   * @return the bulletin in a String format
   */
  public String betterBulletin(double[][] bulletin) {
    String str = "";

    for (int i = 0; i < this.bulletin.length; i++) {
      str += Arrays.toString(this.bulletin[i]) + "\n";
    }

    return str;
  }

  /**
   * Getter of the number of subjects
   *
   * @return the number of subjects
   */
  public int getNbMatieres() {
    int nb = this.MATIERES.length;

    return nb;
  }

  /**
   * Getter of a grade for a specified subject and assignment
   *
   * @param iMatiere the index of the subject
   * @param i the index of the assignment
   * @return the grade
   */
  public double getUneNote(int iMatiere, int i) {
    double note = 0;

    if (iMatiere < 0 || iMatiere >= this.bulletin.length) {
      System.err.println(
          "getUneNote : L'indice de la matière doit être compris entre 0 et "
              + (this.bulletin.length - 1));
    } else if (i < 0 || i >= this.bulletin[iMatiere].length) {
      System.err.println(
          "getUneNote : L'indice de la note doit être compris entre 0 et "
              + (this.bulletin[iMatiere].length - 1));
    } else {
      note = this.bulletin[iMatiere][i];
    }

    return note;
  }

  /**
   * Calculates the average of a subject
   *
   * @param iMatiere the index of a subject
   * @return the average of that subject
   */
  public double moyenneMatiere(int iMatiere) {
    double moyenne = 0;
    int len = this.bulletin[iMatiere].length;

    for (int i = 0; i < len; i++) {
      moyenne += this.bulletin[iMatiere][i];
    }
    moyenne /= len;
    moyenne = Math.round(moyenne * 100d) / 100d;

    return moyenne;
  }

  /**
   * Calculates the global average
   *
   * @return the global average
   */
  public double moyenneGenerale() {
    double moyenne = 0;

    for (int i = 0; i < this.bulletin.length; i++) {
      moyenne += (this.moyenneMatiere(i) * this.COEFFICIENTS[i]);
    }
    moyenne /= this.bulletin.length;
    moyenne = Math.round(moyenne * 100d) / 100d;

    return moyenne;
  }

  /**
   * Returns a string containing the best grade, and the subject where it have been obtained
   *
   * @return La meilleure note est de {grade} obtenue dans {subject}
   */
  public String meilleureNote() {
    String best = "La meilleure note est de ";
    double note = 0;
    String matiere = this.MATIERES[0];
    int indMatiere = 0;

    for (int i = 0; i < this.bulletin.length; i++) {
      for (int j = 0; j < this.bulletin[i].length; j++) {
        double temp = this.bulletin[i][j];
        if (temp > note) {
          note = temp;
          if (indMatiere != i) {
            matiere = this.MATIERES[i];
            indMatiere = i;
          }
        }
      }
    }
    best += note + " obtenue dans " + matiere;

    return best;
  }

  /**
   * From a given Etudiant object, returns basic un-formatted informations about it
   *
   * @return a string with its attributes
   */
  public String toString() {
    String str = "";

    if (this.nom == null
        || this.bulletin == null
        || this.MATIERES == null
        || this.COEFFICIENTS == null) {
      System.err.println("toString : Un des attributs est null");
    } else {
      str +=
          "Nom : "
              + this.nom
              + "\nMatières : "
              + Arrays.toString(this.MATIERES)
              + "\nCoefficients : "
              + Arrays.toString(this.COEFFICIENTS)
              + "\nBulletin : "
              + this.betterBulletin(this.bulletin);
    }

    return str;
  }

  /**
   * From a given Etudiant object, returns informations about it (the complete bulletin as expected
   * to be)
   *
   * @return A stylized bullletin in an array-like appearance, with student name and averages
   */
  public String bulletinGen() {
    String str = "";

    if (this.nom == null
        || this.bulletin == null
        || this.MATIERES == null
        || this.COEFFICIENTS == null) {
      System.err.println("bulletinGen : Un des attributs est null");
    } else {
      str += "Nom : " + this.nom + "\n\n";
      str += "Matières\t";
      for (int i = 0; i < this.bulletin[0].length; i++) {
        str += "Note " + (i + 1) + "\t";
      }
      str += "COEFFICIENT\tMOYENNE\n";
      for (int i = 0; i < this.bulletin.length; i++) {
        str += this.MATIERES[i] + "\t\t";
        for (int j = 0; j < this.bulletin[i].length; j++) {
          str += this.bulletin[i][j] + "\t";
        }
        str += this.COEFFICIENTS[i] + "\t\t";
        str += this.moyenneMatiere(i) + "\n";
      }
      str += "\nMoyenne générale : " + this.moyenneGenerale() + "\n";
    }

    return str;
  }
}
