package weather;

public class CurrentConditionsDisplay {
  private float temp = 0;
  private float humidity;
  private float pressure;
  
  private WeatherData weatherData; 
  
  public CurrentConditionsDisplay(WeatherData weatherData) {
    this.weatherData = weatherData;
  }
  
  public void weatherChanged() {
    temp =weatherData.getTemperature();
    humidity = weatherData.getHumidity();
    pressure= weatherData.getPressure();
    
    this.display();
  }
  
  
  public void display() {
    System.out.println("Current Conditions = " +
                       " + temperature :" + temp
                         + "/" + "humidity : "+ humidity
                         + "/" + "pressure : "+ pressure);
  }
}
