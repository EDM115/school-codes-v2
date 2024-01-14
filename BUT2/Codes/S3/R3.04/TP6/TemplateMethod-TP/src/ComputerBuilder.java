import java.util.ArrayList;
import java.util.HashMap;

public abstract class ComputerBuilder {
    protected HashMap<String, String> computerParts = new HashMap<>();
    protected ArrayList<String> motherboardSetupStatus = new ArrayList<>();

    public final Computer buildComputer() {
        this.addMotherboard();
        this.setupMotherboard();
        this.addProcessor();
        return new Computer(computerParts);
    }

    protected abstract void addMotherboard();

    protected abstract void addProcessor();

    protected void setupMotherboard() {
        for (String e : motherboardSetupStatus) {
            System.out.println(e);
        }
    }

    public HashMap<String, String> getComputerParts() {
        return computerParts;
    }
}
