package mediatorpattern;

public class Button implements Colleague {
 
 private MachineMediator mediator;
 
 public void setMediator(MachineMediator mediator){
  this.mediator = mediator;
 }
 
 public void press(){
  System.out.println("Button pressed.");
  mediator.start();
 }

}
