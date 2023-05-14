import java.util.Arrays;
import java.util.List;

enum Races { // On peut en ajouter d'autres
    Elfe, Hobbit, Homme, Nain
}

enum Classes { // On peut en ajouter d'autres
    Barde, Mage, Paladin, Ranger
}

public class CharacterSheetModel {

    private String CharacterName;
    private String CharacterGender;
    private String CharacterRace;
    private String CharacterClass;

    public CharacterSheetModel(){
        this.initValues();
    }

    private void initValues() {
        this.CharacterName = "";
        this.CharacterGender = "Homme";
        this.CharacterRace = Races.Homme.toString();
        this.CharacterClass = Classes.Mage.toString();
    }
}
