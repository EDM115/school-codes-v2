public class ListBorrowers 
{
	
/** Attributs de la classe **/

private ArrayList theBorrowers;
private final String NOM_FICH = new String ("Borrowers.txt");
	
// L'unique instance de la classe
									private static ListBorrowers theListB = null;
	
	/** Constructeur privé !! **/	
private ListBorrowers ( ) {
		
		this.theBorrowers = new ArrayList<Borrower>();
}
	
public static ListBorrowers getSingleton() {
		  
  if ( theListB == null ) { theListB = new ListBorrowers(); }
		  
	  return theListB;
	}
		
	/**
	  * Ajoute un nouvel adhérent dans la liste.
	  * Cette méthode vérifie si l'adhérent est déjà présent.
	  * @param theBorrower le nouvel adhérent ajouté
	  * @return vrai si l'ajout dans la table est réussi, faux si un adhérent avec une même clé existe déjà dans la table
	  */
	public boolean addNewBorrower ( Borrower theB ) {
		
boolean ret = true;
		
// Test si borrower déjà présent		
if ( this.theBorrowers.contains ( theB ) ) { ret = false; }
		  
		else { this.theBorrowers.add( theB ); }
		
		return ret;
}
	
	/**
	  * Méthode qui transforme TOUT le tableau en une chaîne de
	  * caractères affichable ou imprimable dans un fichier
	  */
	  
	public String toString () {
	
		String ret = new String();
		
							for ( int i=0; i<this.theBorrowers.size(); i++ ) {
			
	ret = new String ( ret + this.theBorrowers.get(i).toString() + "\n" );
		}
		
	return ret;
	}
		
	/**
	  * Ecrit l'ensemble des adhérents dans le fichier texte.
	  * Une ligne du fichier = tous les champs d'un adhérent
	  * séparés par le caractère "&".
	  * @return true si l'opération est réussie
	  */		
public boolean writeListToDisk() {
		
		PrintWriter out;
		boolean ret = true;
		
		// Certains constructeurs peuvent lancer des exceptions
try {
			// Construction du flux de sortie qui a comme destination le fichier texte NOM_FICH
					out = new PrintWriter ( new BufferedWriter(new FileWriter(NOM_FICH)) );
			
// Ecriture de TOUTE la table des adhérents dans le fichier
												out.print( this.toString() );
			
			// Une fois l'écriture du tableau terminée, on ferme le fichier
			out.close();
}
			
// On capture les éventuelles exceptions
		catch (Exception e) {
			// On affiche l'erreur à l'écran
												System.out.println ( "Erreur écriture ListBorrowers " + e.getMessage() );
ret = false;
		}
		
		return ret;
	}			
		  
						}
	  
