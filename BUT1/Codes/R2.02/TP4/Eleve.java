import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Eleve implements Serializable {
    private String nom;
    private String prenom;
    private ArrayList<Evaluation> evaluations;
    
    public Eleve(String nom, String prenom) throws IllegalArgumentException {
		if (nom == null || nom.isEmpty() || prenom == null || prenom.isEmpty()) {
			throw new IllegalArgumentException("Eleve() : Le nom et le prénom ne peuvent pas être vides");
		}
        this.nom = nom;
        this.prenom = prenom;
        this.evaluations = new ArrayList<Evaluation>();
    }
    
    public String getNom() {
        return nom;
    }
    
    public String getPrenom() {
        return prenom;
    }
    
    public void ajouterEvaluation(Evaluation evaluation) throws IllegalArgumentException {
		if (evaluation == null) {
			throw new IllegalArgumentException("ajouterEvaluation() : L'évaluation ne peut pas être nulle");
		}
        evaluations.add(evaluation);
    }
    
    public double calculerMoyenne() {
        if (evaluations.isEmpty()) {
            return 0;
        }
        
        int totalCoefficients = 0;
        int totalNotes = 0;
        
        for (Evaluation evaluation : evaluations) {
            totalCoefficients += evaluation.getCoeff();
            totalNotes += evaluation.getNote() * evaluation.getCoeff();
        }
        
        return (double) totalNotes / totalCoefficients;
    }

    /*
     * Partie A
     * 
     public void sauvegarder(String nomFichier) throws IllegalArgumentException {
         if (nomFichier == null || nomFichier.isEmpty()) {
             throw new IllegalArgumentException("sauvegarder() : Le nom du fichier ne peut pas être vide");
         }
         try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomFichier))) {
             writer.write(nom + "," + prenom + "\n");
             for (Evaluation evaluation : evaluations) {
                 writer.write(evaluation.getCoeff() + "," + evaluation.getNote() + "\n");
             }
             System.out.println("Données sauvegardées dans le fichier : " + nomFichier);
         } catch (IOException e) {
             System.out.println("Erreur lors de la sauvegarde dans le fichier : " + nomFichier);
             e.printStackTrace();
         }
     }
     
     public void charger(String nomFichier) throws IllegalArgumentException, FileNotFoundException {
         if (nomFichier == null || nomFichier.isEmpty()) {
             throw new IllegalArgumentException("charger() : Le nom du fichier ne peut pas être vide");
         }
         evaluations.clear(); // Efface les évaluations existantes
         
         try (BufferedReader reader = new BufferedReader(new FileReader(nomFichier))) {
             String line;
             // Lire la première ligne pour obtenir le nom et le prénom de l'élève
             if ((line = reader.readLine()) != null) {
                 String[] data = line.split(",");
                 if (data.length == 2) {
                     nom = data[0];
                     prenom = data[1];
                 }
             }
             // Lire les évaluations
             while ((line = reader.readLine()) != null) {
                 String[] data = line.split(",");
                 if (data.length == 2) {
                     int coefficient = Integer.parseInt(data[0]);
                     int note = Integer.parseInt(data[1]);
                     Evaluation evaluation = new Evaluation(coefficient, note);
                     evaluations.add(evaluation);
                 }
             }
             // Recalculer la moyenne
             double moyenne = calculerMoyenne();
             System.out.println("Moyenne recalculée : " + moyenne);
             System.out.println("Données chargées à partir du fichier : " + nomFichier);
         } catch (FileNotFoundException e) {
             System.out.println("Le fichier n'a pas été trouvé : " + nomFichier);
             throw e;
         } catch (IOException e) {
             System.out.println("Erreur lors de la lecture du fichier : " + nomFichier);
             e.printStackTrace();
         }
     }
     */


    /*
     * Partie B
     * 
     public void sauvegarder(String nomFichier) throws IllegalArgumentException {
         if (nomFichier == null || nomFichier.isEmpty()) {
             throw new IllegalArgumentException("sauvegarder() : Le nom du fichier ne peut pas être vide");
         }
         try (DataOutputStream output = new DataOutputStream(new FileOutputStream(nomFichier))) {
             output.writeUTF(nom);
             output.writeUTF(prenom);
             output.writeInt(evaluations.size());
             for (Evaluation evaluation : evaluations) {
                 output.writeInt(evaluation.getCoeff());
                 output.writeInt(evaluation.getNote());
             }
             output.writeDouble(calculerMoyenne());
             System.out.println("Données sauvegardées dans le fichier : " + nomFichier);
             output.close();
         } catch (IOException e) {
             System.out.println("Erreur lors de la sauvegarde dans le fichier : " + nomFichier);
             e.printStackTrace();
         }
     }
 
     public void charger(String nomFichier) throws IllegalArgumentException, IOException {
         if (nomFichier == null || nomFichier.isEmpty()) {
             throw new IllegalArgumentException("charger() : Le nom du fichier ne peut pas être vide");
         }
         evaluations.clear(); // Efface les évaluations existantes
 
         try (DataInputStream input = new DataInputStream(new FileInputStream(nomFichier))) {
             nom = input.readUTF();
             prenom = input.readUTF();
             int evalSize = input.readInt();
             for (int i = 0; i < evalSize; i++) {
                 int coefficient = input.readInt();
                 int note = input.readInt();
                 Evaluation evaluation = new Evaluation(coefficient, note);
                 evaluations.add(evaluation);
             }
             double moyenne = input.readDouble();
             System.out.println("Moyenne recalculée : " + moyenne);
             System.out.println("Données chargées à partir du fichier : " + nomFichier);
             input.close();
         } catch (IOException e) {
             System.out.println("Erreur lors de la lecture du fichier : " + nomFichier);
             e.printStackTrace();
             throw e;
         }
     }
     */

    /*
     * Partie C
     */

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject(); // Sérialisation par défaut des champs non transitoires
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); // Désérialisation par défaut des champs non transitoires
        // Recalcul de la moyenne
        double moyenne = calculerMoyenne();
        System.out.println("Moyenne recalculée : " + moyenne);
    }

    public void sauvegarder(String nomFichier) throws IllegalArgumentException {
        if (nomFichier == null || nomFichier.isEmpty()) {
            throw new IllegalArgumentException("sauvegarder() : Le nom du fichier ne peut pas être vide");
        }
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(nomFichier))) {
            outputStream.writeObject(this); // Écriture de l'objet Eleve
            System.out.println("Données sauvegardées dans le fichier : " + nomFichier);
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde dans le fichier : " + nomFichier);
            e.printStackTrace();
        }
    }

    public void charger(String nomFichier) throws IllegalArgumentException, FileNotFoundException {
        if (nomFichier == null || nomFichier.isEmpty()) {
            throw new IllegalArgumentException("charger() : Le nom du fichier ne peut pas être vide");
        }
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(nomFichier))) {
            Eleve eleve = (Eleve) inputStream.readObject(); // Lecture de l'objet Eleve
            this.nom = eleve.nom;
            this.prenom = eleve.prenom;
            this.evaluations = eleve.evaluations;
            System.out.println("Données chargées à partir du fichier : " + nomFichier);
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier : " + nomFichier);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur : Classe non trouvée lors de la lecture du fichier : " + nomFichier);
            e.printStackTrace();
        }
    }

    private static void okayMessage(String message) {
        System.out.println("\u001B[32m" + message + "\u001B[0m");
    }

    private static void errorMessage(String message) {
        System.out.println("\u001B[31m" + message + "\u001B[0m");
    }

    public static void main(String[] args) {
        Eleve eleve = new Eleve("Tintin", "Milou");

        // Test d'ajout d'évaluations
        eleve.ajouterEvaluation(new Evaluation(2, 15));
        eleve.ajouterEvaluation(new Evaluation(1, 18));
        eleve.ajouterEvaluation(new Evaluation(3, 12));

        // Sauvegarder les données de l'élève dans un fichier
        try {
            eleve.sauvegarder("donnees_eleve.txt");
            okayMessage("Données sauvegardées dans le fichier : donnees_eleve.txt");
        } catch (IllegalArgumentException e) {
            errorMessage("Erreur lors de la sauvegarde dans le fichier : donnees_eleve.txt");
            e.printStackTrace();
        }

        // Charger les données de l'élève à partir d'un fichier
        try {
            eleve.charger("donnees_eleve.txt");
            okayMessage("Données chargées à partir du fichier : donnees_eleve.txt");
        } catch (IllegalArgumentException e) {
            errorMessage("Erreur lors de la lecture du fichier : donnees_eleve.txt");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            errorMessage("Erreur : le fichier donnees_eleve.txt n'existe pas");
        }/* catch (IOException e) {
            errorMessage("Erreur lors de la lecture du fichier : donnees_eleve.txt");
            e.printStackTrace();
        }*/

        // Calcul de la moyenne
        double moyenne = eleve.calculerMoyenne();
        System.out.println("Moyenne de l'élève " + eleve.getNom() + " " + eleve.getPrenom() + ": " + moyenne);
        
        // Test d'une lecture de fichier inexistant
        try {
            eleve.charger("fichier_inexistant.txt");
            errorMessage("Erreur : le fichier fichier_inexistant.txt existe");
        } catch (IllegalArgumentException e) {
            errorMessage("Erreur lors de la lecture du fichier : fichier_inexistant.txt");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            okayMessage("Erreur : le fichier fichier_inexistant.txt n'existe pas");
        }/* catch (IOException e) {
            errorMessage("Erreur lors de la lecture du fichier : fichier_inexistant.txt");
            e.printStackTrace();
        }*/

		// Test avec un élève ayant des paramètres vides/null
		try { 
			Eleve eleve2 = new Eleve("", null);
		} catch (IllegalArgumentException e) {
			errorMessage("Erreur lors de la création de l'élève");
			e.printStackTrace();
		}
    }
}