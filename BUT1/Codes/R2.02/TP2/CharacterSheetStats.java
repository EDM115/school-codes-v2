import javax.swing.*;
import java.awt.*;

public class CharacterSheetStats extends JPanel {

    // Les labels
    private JLabel nameLabel;
    private JLabel raceLabel;
    private JLabel classLabel;
    private JLabel genderLabel;

    // Les champs de texte
    private JTextField nameTextField;

    // Les combobox
    private JComboBox<String> raceComboBox;
    private JComboBox<String> classComboBox;

    // Les boutons radios
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;

    // Model
    private CharacterSheetModel characterSheetModel;

    public CharacterSheetStats(CharacterSheetListener characterSheetListener) {
        initComponents();
        this.characterSheetModel = new CharacterSheetModel();
    }

    private void initComponents() {
        // On utilise un GridLayout avec 2 colonnes
        setLayout(new GridLayout(0, 2, 2, 2));

        // Ici on ajoute le nom du personnage
        // TIPS : JLabel - JTextField
        this.nameLabel = new JLabel("Nom :");
        this.nameTextField = new JTextField();
        this.add(this.nameLabel);
        this.add(this.nameTextField);

        // Ici on ajoute la race du personnage
        // TIPS : JComboBox
        this.raceLabel = new JLabel("Race :");
        // Création d'un tableau de String contenant les noms des races
        String[] raceNames = new String[Races.values().length];
        int i = 0;
        for (Races race : Races.values()) {
            raceNames[i] = race.toString();
            i++;
        }
        // Instanciation de la JComboBox
        this.raceComboBox = new JComboBox<>(raceNames);
        this.add(this.raceLabel);
        this.add(this.raceComboBox);

        // Ici on ajoute la classe du personnage
        // TIPS : JComboBox
        this.classLabel = new JLabel("Classe :");
        // Création d'un tableau de String contenant les noms des classes
        String[] classNames = new String[Classes.values().length];
        i = 0;
        for (Classes classe : Classes.values()) {
            classNames[i] = classe.toString();
            i++;
        }
        // Instanciation de la JComboBox
        this.classComboBox = new JComboBox<>(classNames);
        this.add(this.classLabel);
        this.add(this.classComboBox);

        // Ici on ajoute les boutons radio H/F (attention, on ne veux pas qu'ils fassent toute la largeur)
        // TIPS : faire un JPanel qui regroupe les deux boutons, ne pas oublier ButtonGroup
        this.genderLabel = new JLabel("Genre :");
        this.maleRadioButton = new JRadioButton("H");
        this.femaleRadioButton = new JRadioButton("F");
        ButtonGroup genderButtonGroup = new ButtonGroup();
        JPanel genderPanel = new JPanel(new GridLayout(1, 2));
        genderPanel.add(this.maleRadioButton);
        genderPanel.add(this.femaleRadioButton);
        genderButtonGroup.add(this.maleRadioButton);
        genderButtonGroup.add(this.femaleRadioButton);
        this.add(this.genderLabel);
        this.add(genderPanel);

        // On fixe la taille des labels pour qu'ils aient tous la même taille
        this.nameLabel.setPreferredSize(this.genderLabel.getPreferredSize());
        this.raceLabel.setPreferredSize(this.genderLabel.getPreferredSize());
        this.classLabel.setPreferredSize(this.genderLabel.getPreferredSize());
    }
}
