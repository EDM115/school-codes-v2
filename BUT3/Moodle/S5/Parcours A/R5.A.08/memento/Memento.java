import java.util.ArrayList;

/**
 * Memento memorise les etats du chariot
*/
public class Memento {
  protected ArrayList<OptionVehicule> options = new ArrayList<OptionVehicule>();

  public void setEtat(ArrayList<OptionVehicule> options)
  {
    this.options.clear();
    this.options.addAll(options);
  }

  public ArrayList<OptionVehicule> getEtat()
  {
    return options;
  }
}
