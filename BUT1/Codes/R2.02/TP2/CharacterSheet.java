import javax.swing.*;
import java.awt.*;

public class CharacterSheet extends JFrame {

    private JPanel panel;

    private CharacterSheetStats characterSheetStats;
    private CharacterSheetToolBar characterSheetToolBar;
    private CharacterSheetStateBar characterSheetStateBar;

    private CharacterSheetListener characterSheetListener;

    public CharacterSheet() {
        this.initComponents();
    }

    private void initComponents() {
        // On crée l'instance de notre listener
        this.characterSheetListener = new CharacterSheetListener(this);

        // On crée notre fenêtre avec le titre, la taille, la taille minimale et le mode de fermeture
        this.setTitle("Fiche personnage");
        this.setSize(700, 400);
        this.setMinimumSize(new Dimension(600, 350));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // On crée notre panel qui contiendra tous les éléments
        this.panel = new JPanel(new BorderLayout());

        // On crée notre barre d'outils et on l'ajoute sur la gauche
        this.characterSheetToolBar = new CharacterSheetToolBar(this.characterSheetListener);
        this.panel.add(this.characterSheetToolBar, BorderLayout.WEST);

        // On crée notre panneau d'informations et on l'ajoute au centre
        this.characterSheetStats = new CharacterSheetStats(this.characterSheetListener);
        this.panel.add(this.characterSheetStats, BorderLayout.CENTER);

        // On crée notre barre d'état et on l'ajoute en bas
        this.characterSheetStateBar = new CharacterSheetStateBar("1.0");
        this.panel.add(this.characterSheetStateBar, BorderLayout.SOUTH);

        // On ajoute le panel à notre fenêtre
        this.add(this.panel);
        this.pack();

        // On centre la fenêtre sur l'écran
        this.setLocationRelativeTo(null);
    }

    public CharacterSheetStats getCharacterSheetStats(){
        return this.characterSheetStats;
    }

    public CharacterSheetToolBar getCharacterSheetToolBar(){
        return this.characterSheetToolBar;
    }

    public CharacterSheetStateBar getCharacterSheetStateBar(){
        return this.characterSheetStateBar;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CharacterSheet().setVisible(true);
            }
        });
    }

}
