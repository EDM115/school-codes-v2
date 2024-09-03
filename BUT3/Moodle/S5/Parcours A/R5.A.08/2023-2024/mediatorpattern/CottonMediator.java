package mediatorpattern;

public class CottonMediator implements MachineMediator{
 
 private final Machine machine;
 private final Heater heater;
 private final Motor motor;
 private final Sensor sensor;
 private final SoilRemoval soilRemoval;
 private final Valve valve;
 
 public CottonMediator(Machine machine,Heater heater,Motor motor,Sensor sensor,SoilRemoval soilRemoval,Valve valve){
  this.machine = machine;
  this.heater = heater;
  this.motor = motor;
  this.sensor = sensor;
  this.soilRemoval = soilRemoval;
  this.valve = valve;
  
  System.out.println(".........................Setting up for COTTON program.........................");
 }

 public void start() {
  machine.start();
 }

 public void wash() {
  motor.startMotor();
  motor.rotateDrum(700);
  System.out.println("Adding detergent");
  soilRemoval.low();
  System.out.println("Adding softener");
 }

 
 public void open() {
  valve.open();
 }

 public void closed() {
  valve.closed();
 }
 
 public void on() {
  heater.on(40);
 }

 public void off() {
  heater.off();
 }

 public boolean checkTemperature(int temp) {
  return sensor.checkTemperature(temp);
 }

}
