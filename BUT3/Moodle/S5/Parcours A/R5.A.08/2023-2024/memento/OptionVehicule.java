import java.util.ArrayList;
public class OptionVehicule {
  protected String nom;
  protected ArrayList<OptionVehicule> optionsIncompatibles = new ArrayList<OptionVehicule>();

  public OptionVehicule(String nom){
    this.nom = nom;
  }

  public void ajouteOptionIncompatible(OptionVehicule optionIncompatible) {
    if (!optionsIncompatibles.contains(optionIncompatible)) {
      optionsIncompatibles.add(optionIncompatible);
      optionIncompatible.ajouteOptionIncompatible(this);
    }
  }

  public ArrayList<OptionVehicule> getOptionsIncompatibles() {
    return optionsIncompatibles;
  }

  public void affiche() {
    System.out.println("option : " + nom);
  }
}
