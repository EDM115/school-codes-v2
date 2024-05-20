import java.util.ArrayList;
/**
 * *
 * le chariot contient les options choisies
 * Seul le chariot peut mémoriser son état dans le mémento et y restaurer un état précédent
 * 
 * */
public class ChariotOption {
  protected ArrayList<OptionVehicule> options = new ArrayList<OptionVehicule>();
  
  /**
   * ajout d’une nouvelle option : le chariot crée un mémento, l’initialise avec son état, 
   * retire les options incompatibles avec cette nouvelle option,
   * procède à l’ajout de cette nouvelle option et renvoie le mémento ainsi créé.
   * Celui-ci sera utilisé par la suite en cas d’annulation de cet ajout et de retour à l’état précédent.
   */
  public Memento ajouteOption(OptionVehicule optionVehicule) {
    Memento resultat = new Memento();
    resultat.setEtat(options);
    options.removeAll(optionVehicule.getOptionsIncompatibles());
    options.add(optionVehicule);
    return resultat;
  }

  // retour a l'etat precedent
  public void annule(Memento memento)  {
    /*MementoImpl mementoImplInstance;
    try
    {
      mementoImplInstance = (MementoImpl)memento;
    }
    catch (ClassCastException e)
    {
      return ;
    }
    */
    options = memento.getEtat();
  }

  public void affiche()
  {
    System.out.println("Contenu du chariot des options");
    for (OptionVehicule option: options)
      option.affiche();
    System.out.println();
  }
}
