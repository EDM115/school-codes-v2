import javax.swing.*;
import java.awt.*;

public class CharacterSheetStateBar extends JPanel{

    // Les deux labels de notre barre d'état
    // le label de message et le label de version
    private JLabel messageLabel;
    private JLabel versionLabel;

    public CharacterSheetStateBar(String version){
        this.initComponents(version);
    }

    private void initComponents(String version) {
        this.messageLabel = new JLabel("Ready");
        this.versionLabel = new JLabel(version);

        // On crée un espace vide de 10 pixels à gauche et à droite des labels pour pas que ça soit collé au bord
        this.messageLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        this.versionLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        // On ajoute les labels à la barre d'état, le message à gauche et la version à droite
        this.setLayout(new BorderLayout());
        this.add(this.messageLabel, BorderLayout.WEST);
        this.add(this.versionLabel, BorderLayout.EAST);
    }

    // Méthode permettant de changer le message de la barre d'état
    public void setMessage(String message) {
        this.messageLabel.setText(message);
    }

}