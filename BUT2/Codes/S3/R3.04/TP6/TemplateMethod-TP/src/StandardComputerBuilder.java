public class StandardComputerBuilder extends ComputerBuilder {
    @Override
    protected void addMotherboard() {
        computerParts.put("Motherboard", "Standard Motherboard");
        motherboardSetupStatus.add("Screwing the standard motherboard to the case.");
        motherboardSetupStatus.add("Plugging in the power supply connectors.");
    }

    @Override
    protected void addProcessor() {
        computerParts.put("Processor", "Standard Processor");
    }
}
