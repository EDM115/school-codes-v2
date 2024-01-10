package weather;

/*
 * Cette classe fait des statistiques sur les donneees climatiques donnees
 * par un objetde type WeatherData
 */

public class StatisticsDisplay {
  private float maxTemp = 0; // temperature maximale de la saison
  private float minTemp = 10; // temperature minimale de la saison
  private float tempSum= 0;
  private int numReadings;
  
  private WeatherData weatherData; 
  
  public StatisticsDisplay(WeatherData weatherData) {
    this.weatherData = weatherData;
  }
  
  /**
   * collects the temperature data from the WeatherData
   */
  public void collectData() {
    float temp =weatherData.getTemperature() ;
    tempSum += temp;
    numReadings++;
    if (temp > maxTemp) {
      maxTemp = temp;
    }
    if (temp < minTemp) {
      minTemp = temp;
    }
    this.display();
  }
  
  
  public void display() {
    System.out.println("Avg/Max/Min temperature = " +
                       " + (tempSum / numReadings)"
                         + "/" + maxTemp + "/" + minTemp);
  }
}
