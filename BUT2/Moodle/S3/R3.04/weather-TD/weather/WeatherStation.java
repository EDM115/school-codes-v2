package weather;


public class WeatherStation {
  public static void main(String[] args) {
    WeatherData weatherData = new WeatherData();
    CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay(weatherData);
    
    weatherData.setMeasurements(12, 65, 1010);
    currentDisplay.weatherChanged();
    weatherData.setMeasurements(25, 40, 1030);
    currentDisplay.weatherChanged();
    weatherData.setMeasurements(3, 90, 980);
    currentDisplay.weatherChanged();
  }
}
