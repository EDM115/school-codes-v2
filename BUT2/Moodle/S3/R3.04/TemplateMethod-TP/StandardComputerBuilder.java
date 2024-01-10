import java.util.HashMap;
import java.util.ArrayList;

public class StandardComputerBuilder {
    private HashMap<String, String> computerParts= new HashMap<String, String>();
    private ArrayList<String> motherboardSetupStatus = new ArrayList<String>();
   
      public Computer buildComputer() {
        this.addMotherboard();
        this.setupMotherboard();
        this.addProcessor();
        return new Computer(computerParts);
    }
   
    public HashMap<String, String> getComputerParts(){
        return computerParts;
    }
    
    public void addMotherboard() {
        computerParts.put("Motherboard", "Standard Motherboard");
    }
    
    public void setupMotherboard() {
        motherboardSetupStatus.add(
          "Screwing the standard motherboard to the case.");
        motherboardSetupStatus.add(
          "Pluging in the power supply connectors.");
      for (String e : motherboardSetupStatus)
          System.out.println(e);
    }
    
    public void addProcessor() {
        computerParts.put("Processor", "Standard Processor");
    }
}
