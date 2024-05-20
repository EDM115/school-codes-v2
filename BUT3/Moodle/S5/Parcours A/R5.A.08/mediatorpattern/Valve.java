package mediatorpattern;

public class Valve implements Colleague {

 private MachineMediator mediator;
 
 public void setMediator(MachineMediator mediator){
  this.mediator = mediator;
 }
 
 public void open(){
  System.out.println("Valve is opened...");
  System.out.println("Filling water...");
  mediator.closed();
 }
 
 public void closed(){
  System.out.println("Valve is closed...");
  mediator.on();
 }
}
