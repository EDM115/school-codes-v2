import java.util.*;
public class Catalogue
{
  protected List<Vehicule> vehiculesStock = 
    new ArrayList<Vehicule>();
  protected List<CommandeSolder> commandes =
    new ArrayList<CommandeSolder>();

  public void lanceCommandeSolder(CommandeSolder commande)
  {
    commandes.add(0, commande);
    commande.solde(vehiculesStock);
  }

  public void annuleCommandeSolder(int ordre)
  {
    commandes.get(ordre).annule();
  }

  public void retablitCommandeSolder(int ordre)
  {
    commandes.get(ordre).retablit();
  }

  public void ajoute(Vehicule vehicule)
  {
	  vehiculesStock.add(vehicule);
  }

  public void affiche()
  {
    for (Vehicule vehicule: vehiculesStock)
      vehicule.affiche();
  }
}
