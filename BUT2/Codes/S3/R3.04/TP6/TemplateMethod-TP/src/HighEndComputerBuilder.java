public class HighEndComputerBuilder extends ComputerBuilder {
    @Override
    protected void addMotherboard() {
        computerParts.put("Motherboard", "High-end Motherboard");
        motherboardSetupStatus.add("Screwing the high-end motherboard to the case.");
        motherboardSetupStatus.add("Plugging in the power supply connectors.");
    }

    @Override
    protected void addProcessor() {
        computerParts.put("Processor", "High-end Processor");
    }
}
