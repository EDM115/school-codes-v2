import javax.swing.*;

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
        // TODO
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
