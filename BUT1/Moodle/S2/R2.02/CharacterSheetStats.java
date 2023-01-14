import javax.swing.*;

public class CharacterSheetStats extends JPanel {

    // On a besoin de ce blankLabel pour "passer" des cellules du GridLayout
    private JLabel blankLabel;

    // Les labels
    // TODO


    // Les champs de texte
    // TODO

    // Les boutons radios
    // TODO

    // Les combobox
    // TODO

    // Model
    private CharacterSheetModel characterSheetModel;

    public CharacterSheetStats(CharacterSheetListener characterSheetListener) {
        // TODO
    }

    private void initComponents() {
        this.blankLabel = new JLabel("");

        // Ici on ajoute le nom du personnage
        // TIPS : JLabel - JTextField
        // TODO

        // Ici on ajoute les boutons radio H/F (attention, on ne veux pas qu'ils fassent toute la largeur)
        // TIPS : faire un JPanel qui regroupe les deux boutons, ne pas oublier ButtonGroup
        // TODO


        // Ici on ajoute la sélection de race, on souhaite utilisé l'énumeration Races
        // TIPS : Enum.values() permet de récupérer les valeurs
        // TODO

    }
}
