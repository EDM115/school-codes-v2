import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class CharacterSheetListener implements ActionListener {

    private CharacterSheet characterSheet;

    public CharacterSheetListener(CharacterSheet characterSheet) {
        this.characterSheet = characterSheet;
    }

    public void actionPerformed(ActionEvent event) {
        // On récupère la source de l'évènement
        Object source = event.getSource();

        // On vérifie que la source est bien un bouton
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            // On récupère le texte du bouton
            String buttonName = button.getText();

            // On récupère la barre d'état de la fiche de personnage
            CharacterSheetStateBar characterSheetStateBar = this.characterSheet.getCharacterSheetStateBar();

            // On change le message de la barre d'état en fonction du bouton cliqué
            switch (buttonName) {
                case "Nouveau":
                    characterSheetStateBar.setMessage("Action : Création d'une nouvelle fiche de personnage");
                    break;
                case "Charger":
                    characterSheetStateBar.setMessage("Action : Ouverture d'une fiche de personnage");
                    break;
                case "Enregistrer":
                    characterSheetStateBar.setMessage("Action : Enregistrement de la fiche de personnage");
                    break;
                case "Enregistrer sous":
                    characterSheetStateBar.setMessage("Action : Enregistrement dans un nouveau fichier de la fiche de personnage");
                    break;
                default:
                    break;
            }
        }
    }

}
