package mediatorpattern;

public class Machine implements Colleague {

 private MachineMediator mediator;
 
 public void setMediator(MachineMediator mediator){
  this.mediator = mediator;
 }
 
 public void start(){
  mediator.open();
 }
 
 public void wash(){
  mediator.wash();
 }
}
