const apiKey = "REDACTED"

let more = false;

document.getElementById("submit").addEventListener("click", function() {
  let city = document.getElementById("city").value;
  more = false;
  getWeather(city, "weather");
})

document.getElementById("city").addEventListener("keypress", function(e) {
  if (e.key === "Enter") {
    let city = document.getElementById("city").value;
    more = false;
    getWeather(city, "weather");
  }
})

document.getElementById("more").addEventListener("click", function() {
  let city = document.getElementById("cityName").innerText.split(",")[0];
  more = !more;
  getWeather(city, "advanced");
})

document.getElementById("more").addEventListener("keypress", function(e) {
  if (e.key === "Enter") {
    let city = document.getElementById("cityName").innerText.split(",")[0];
    more = !more;
    getWeather(city, "advanced");
  }
})

document.getElementById("load").style.display = "none";
document.getElementById("moreLoad").style.display = "none";
document.getElementById("unload").style.display = "none";
document.getElementById("moreUnload").style.display = "none";
document.getElementById("error").style.display = "none";

function getWeather(city, mode = "weather") {
  if (mode === "weather") {
    document.getElementById("load").style.display = "block";
    document.getElementById("unload").style.display = "none";
  } else if (mode === "advanced") {
    document.getElementById("moreLoad").style.display = "block";
    document.getElementById("moreUnload").style.display = "none";
  }
  document.getElementById("error").style.display = "none";
  let res = fetch(`https://api.openweathermap.org/data/2.5/weather?q=${encodeURIComponent(city)}&lang=fr&units=metric&appid=${apiKey}`)
  .then(response => response.json())
  .then(data => {
    console.log(data);
    res = data;
    if (mode === "weather") {
      showWeather(data);
      document.getElementById("load").style.display = "none";
      document.getElementById("unload").style.display = "block";
    } else if (mode === "advanced") {
      showAdvancedWeather(data);
      document.getElementById("moreLoad").style.display = "none";
      if (more) {
        document.getElementById("moreUnload").style.display = "block";
        document.getElementById("more").innerText = "Moins d'informations";
      } else {
        document.getElementById("moreUnload").style.display = "none";
        document.getElementById("more").innerText = "Plus d'informations";
      }
    }
  })
  .catch(error => {
    console.error(error)
    document.getElementById("load").style.display = "none";
    document.getElementById("moreLoad").style.display = "none";
    document.getElementById("moreUnload").style.display = "none";
    document.getElementById("error").style.display = "block";
    document.getElementById("err").innerText = res.message;
    more = false;
  })
}

function showWeather(data) {
  document.getElementById("cityName").innerText = data.name + ", " + data.sys.country;
  document.getElementById("temp").innerText = data.main.temp + "°C";
  document.getElementById("icon").className = "wi wi-owm-" + data.weather[0].id;
  document.getElementById("description").innerText = " " + data.weather[0].description.charAt(0).toUpperCase() + data.weather[0].description.slice(1);
}

function showAdvancedWeather(data) {
  document.getElementById("clouds").innerText = data.clouds.all + " %";
  document.getElementById("tempFeel").innerText = data.main.feels_like + "°C";
  document.getElementById("humidity").innerText = data.main.humidity + " %";
  document.getElementById("pressure").innerText = data.main.pressure + " hPa";
  document.getElementById("windSpeed").innerText = data.wind.speed + " m/s";
  document.getElementById("windIcon").className = "wi wi-wind towards-" + data.wind.deg + "-deg";
  document.getElementById("windDeg").innerText = data.wind.deg + "°";
  document.getElementById("sunrise").innerText = new Date(data.sys.sunrise * 1000).toLocaleTimeString();
  document.getElementById("sunset").innerText = new Date(data.sys.sunset * 1000).toLocaleTimeString();
}
