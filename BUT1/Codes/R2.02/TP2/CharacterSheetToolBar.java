import javax.swing.*;
import java.awt.*;

public class CharacterSheetToolBar extends JToolBar{

    private JButton nouveauButton;
    private JButton chargerButton;
    private JButton enregistrerButton;
    private JButton enregistrerSousButton;

    public CharacterSheetToolBar(CharacterSheetListener characterSheetListener){
        this.initComponents();
        // On empêche la barre d'outils d'être déplaçable
        this.setFloatable(false);
        this.setRollover(true);

        // On ajoute les boutons au listener
        this.nouveauButton.addActionListener(characterSheetListener);
        this.chargerButton.addActionListener(characterSheetListener);
        this.enregistrerButton.addActionListener(characterSheetListener);
        this.enregistrerSousButton.addActionListener(characterSheetListener);
    }

    private void initComponents() {
        // On ajoute les boutons à un JPanel pour pouvoir les afficher verticalement
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1));
        
        // On crée les boutons avec un label
        this.nouveauButton = new JButton("Nouveau");
        this.chargerButton = new JButton("Charger");
        this.enregistrerButton = new JButton("Enregistrer");
        this.enregistrerSousButton = new JButton("Enregistrer sous");

        // On ajoute un tooltip sur chaque bouton
        this.nouveauButton.setToolTipText("Nouveau personnage");
        this.chargerButton.setToolTipText("Charger un personnage existant");
        this.enregistrerButton.setToolTipText("Enregistrer le personnage");
        this.enregistrerSousButton.setToolTipText("Enregistrer le personnage sous un nouveau nom");

        // On ajoute les boutons au panel
        buttonPanel.add(this.nouveauButton);
        //buttonPanel.add(Box.createVerticalStrut(2)); // espace vertical entre les boutons
        buttonPanel.add(this.chargerButton);
        //buttonPanel.add(Box.createVerticalStrut(2));
        buttonPanel.add(this.enregistrerButton);
        //buttonPanel.add(Box.createVerticalStrut(2));
        buttonPanel.add(this.enregistrerSousButton);

        // On ajoute le JPanel à la barre d'outils
        this.add(buttonPanel);
    }

}
