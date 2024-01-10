package weather;


public class WeatherData {

 private float temperature;
 private float humidity;
 private float pressure;

 public WeatherData() {
  
 }


 public void setMeasurements(float temperature, float humidity,
  float pressure) {
  this.temperature = temperature;
  this.humidity = humidity;
  this.pressure = pressure;
 
 }
  
  
 public float getTemperature() {
  return temperature;
 }
  
 public float getHumidity() {
  return humidity;
 }
  
 public float getPressure() {
  return pressure;
 }
}
