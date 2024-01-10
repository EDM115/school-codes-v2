public class ScenarioBuilder {
  
  public static void main(String[] args){
  
   StandardComputerBuilder scb= new StandardComputerBuilder();
    Computer myStandardComputer = scb.buildComputer();
  //  imrpime les parties de l'ordinateur
   for( String k   : scb.getComputerParts().keySet()) 
      System.out.println("Part : " + k + " Value : " + scb.getComputerParts().get(k));
        
   HighEndComputerBuilder hcb = new HighEndComputerBuilder();
     Computer MyHighComputer =hcb.buildComputer();
    for( String k   : hcb.getComputerParts().keySet()) 
      System.out.println("Part : " + k + " Value : " + hcb.getComputerParts().get(k));
  }
}