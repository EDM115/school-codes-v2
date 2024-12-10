package weather;

public class CurrentConditionsDisplay implements Observer {
	private float temp = 0;
	private float humidity;
	private float pressure;	
	private WeatherData weatherData; 

	public CurrentConditionsDisplay(WeatherData weatherData) {
		this.weatherData = weatherData;
	}
	
	public void weatherChanged() {
		temp = weatherData.getTemperature();
		humidity = weatherData.getHumidity();
		pressure = weatherData.getPressure();
		
		this.display();
	}

	public void update(float temperature, float humidity, float pressure) {
		this.temp = temperature;
		this.humidity = humidity;
		this.pressure = pressure;
		display();
	}

	public void display() {
		System.out.println("Current Conditions = " + " + temperature :" + temp + "/" + "humidity : "+ humidity + "/" + "pressure : "+ pressure);
	}
}
