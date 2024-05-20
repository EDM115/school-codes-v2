package mediatorpattern;

public class DenimMediator implements MachineMediator{

 private final Machine machine;
 private final Heater heater;
 private final Motor motor;
 private final Sensor sensor;
 private final SoilRemoval soilRemoval;
 private final Valve valve;

 public DenimMediator(Machine machine,Heater heater,Motor motor,Sensor sensor,SoilRemoval soilRemoval,Valve valve){
  this.machine = machine;
  this.heater = heater;
  this.motor = motor;
  this.sensor = sensor;
  this.soilRemoval = soilRemoval;
  this.valve = valve;

  System.out.println(".........................Setting up for DENIM program.........................");
 }

 public void start() {
  machine.start();
 }

 public void wash() {
  motor.startMotor();
  motor.rotateDrum(1400);
  System.out.println("Adding detergent");
  soilRemoval.medium();
  System.out.println("No softener is required");
 }

 public void open() {
  valve.open();
 }

 public void closed() {
  valve.closed();
 }

 public void on() {
  heater.on(30);
 }
 
 public void off() {
  heater.off();
 }

 public boolean checkTemperature(int temp) {
  return sensor.checkTemperature(temp);
 }
 
}
