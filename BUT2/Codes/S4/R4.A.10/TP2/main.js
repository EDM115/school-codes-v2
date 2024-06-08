$(document).ready(function() {
  let more = false;
  $("#load, #moreLoad").hide();
  $("#unload, #moreUnload, #error").hide();

  $("#submit").on("click", function() {
    if ($("#city").val().trim() !== "") {
      getWeather($("#city").val().trim(), "weather");
      getForecast($("#city").val().trim());
    }
  });

  $("#city").on("keypress", function(e) {
    if (e.key === "Enter" && $("#city").val().trim() !== "") {
      getWeather($("#city").val().trim(), "weather");
      getForecast($("#city").val().trim());
    }
  });

  $("#more").on("click keypress", function(e) {
    if (e.type === "click" || e.key === "Enter") {
      toggleMoreInfo();
    }
  });

  function getWeather(city, mode) {
    let url = `https://api.openweathermap.org/data/2.5/weather?q=${encodeURIComponent(city)}&lang=fr&units=metric&appid=REDACTED`;
    $.ajax({
      url: url,
      type: "GET",
      dataType: "json",
      beforeSend: function() {
        if (mode === "weather") {
          $("#load").show();
        } else {
          $("#moreLoad").show();
        }
        $("#unload, #moreUnload, #error").hide();
      },
      success: function(data) {
        if (mode === "weather") {
          showWeather(data);
        } else {
          showAdvancedWeather(data);
        }
      },
      error: function(response) {
        $("#error").show().find("#err").text(response.responseJSON.message);
      },
      complete: function() {
        $("#load, #moreLoad").hide();
          if (more && mode === "advanced") {
            $("#moreUnload").show();
          } else {
            $("#unload").show();
          }
      }
    });
  }

  function getForecast(city) {
    let url = `https://api.openweathermap.org/data/2.5/forecast?q=${encodeURIComponent(city)}&lang=fr&units=metric&appid=ee07e2bf337034f905cde0bdedae3db8`;
    $.ajax({
      url: url,
      type: "GET",
      dataType: "json",
      beforeSend: function() {
        $("#moreLoad").show();
      },
      success: function(data) {
        displayForecast(data);
      },
      error: function(response) {
        $("#error").show().find("#err").text(response.responseJSON.message);
      },
      complete: function() {
        $("#moreLoad").hide();
      }
    });
  }
  
  function displayForecast(data) {
    let forecastContainer = $("#forecastContainer");
    forecastContainer.empty();
    for (let i = 0; i < data.list.length; i += 8) {
      let dayData = data.list[i];
      let card = `<div class="ui card">
                    <div class="content">
                      <div class="header">${new Date(dayData.dt_txt).toLocaleDateString()}</div>
                      <div class="meta">${dayData.weather[0].description}</div>
                      <div class="description">
                        Température : ${dayData.main.temp_max}°C / ${dayData.main.temp_min}°C
                        <br>
                        Humidité : ${dayData.main.humidity}%
                      </div>
                    </div>
                    <div class="extra content">
                      <i class="wi wi-owm-${dayData.weather[0].id}"></i>
                    </div>
                  </div>`;
      forecastContainer.append(card);
    }
  }  

  function showWeather(data) {
    $("#cityName").text(`${data.name}, ${data.sys.country}`);
    $("#temp").text(`${data.main.temp}°C`);
    $("#icon").attr("class", `wi wi-owm-${data.weather[0].id}`);
    $("#description").text(` ${data.weather[0].description.charAt(0).toUpperCase() + data.weather[0].description.slice(1)}`);
  }

  function showAdvancedWeather(data) {
    $("#clouds").text(`${data.clouds.all} %`);
    $("#tempFeel").text(`${data.main.feels_like}°C`);
    $("#humidity").text(`${data.main.humidity} %`);
    $("#pressure").text(`${data.main.pressure} hPa`);
    $("#windSpeed").text(`${data.wind.speed} m/s`);
    $("#windIcon").attr("class", `wi wi-wind towards-${data.wind.deg}-deg`);
    $("#windDeg").text(`${data.wind.deg}°`);
    $("#sunrise").text(new Date(data.sys.sunrise * 1000).toLocaleTimeString());
    $("#sunset").text(new Date(data.sys.sunset * 1000).toLocaleTimeString());
  }

  function toggleMoreInfo() {
    more = !more;
    let city = $("#cityName").text().split(",")[0];
    if (more) {
      getWeather(city, "advanced");
      $("#unload").show();
      $("#more").text("Moins d'informations");
    } else {
      $("#moreUnload").hide();
      $("#more").text("Plus d'informations");
  }
  }
});
