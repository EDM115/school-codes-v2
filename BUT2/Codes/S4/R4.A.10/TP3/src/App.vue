<template>
  <v-app>
    <v-container>
      <v-text-field v-model="city" label="Ville" outlined/>
      <v-btn @click="fetchWeather" color="primary">Prévisions météo</v-btn>
      
      <v-skeleton-loader v-if="loading" class="mx-auto" height="200px"/>
      <v-card v-else-if="weather" class="my-4">
        <v-card-title>{{ weather.name }}, {{ weather.sys.country }}</v-card-title>
        <v-card-subtitle><v-icon :icon="`wi wi-owm-${weather.weather[0].id}`"/> {{ weather.weather[0].description }}</v-card-subtitle>
        <v-card-text>
          <div>Température : {{ weather.main.temp }} °C</div>
          <div v-if="showDetails">
            <div>Humidité : {{ weather.main.humidity }}%</div>
            <div>Vent : {{ weather.wind.speed }} m/s, direction : {{ weather.wind.deg }}°</div>
            <div>Pression : {{ weather.main.pressure }} hPa</div>
            <div>Température ressentie : {{ weather.main.feels_like }} °C</div>
            <div>Nuageosité : {{ weather.clouds.all }}%</div>
          </div>
        </v-card-text>
        <v-btn @click="showDetails = !showDetails">
          {{ showDetails ? 'Cacher' : 'Voir' }} les détails
        </v-btn>
      </v-card>

      <v-row v-if="forecast">
        <v-col cols="12" sm="6" md="4" v-for="(item, index) in filteredForecast" :key="index">
          <v-card>
            <v-card-title>
              {{ new Date(item.dt_txt).toLocaleDateString('fr-FR') }}
            </v-card-title>
            <v-card-subtitle>
              <v-icon :icon="`wi wi-owm-${item.weather[0].id}`"/> {{ item.weather[0].description }}
            </v-card-subtitle>
            <v-card-text>
              <div>Temp. Max : {{ item.main.temp_max }} °C</div>
              <div>Temp. Min : {{ item.main.temp_min }} °C</div>
              <div>Pression : {{ item.main.pressure }} hPa</div>
              <div>Humidité : {{ item.main.humidity }}%</div>
              <div>Vent : {{ item.wind.speed }} m/s, direction : {{ item.wind.deg }}°</div>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-app>
</template>

<script setup>
import { computed, ref } from 'vue'

const city = ref('')
const forecast = ref(null)
const loading = ref(false);
const showDetails = ref(false)
const weather = ref(null)

const filteredForecast = computed(() => {
  return forecast.value ? forecast.value.list.filter((_, index) => index % 8 === 0) : [];
});

async function fetchWeather() {
  loading.value = true;
  try {
    await fetchCurrentWeather();
    await fetchForecast();
  } catch (error) {
    console.error("Erreur de récupération des données :", error);
    weather.value = null;
    forecast.value = null;
    alert('Erreur lors de la récupération des données : ' + error.message);
  } finally {
    loading.value = false;
  }
}

async function fetchCurrentWeather() {
  const response = await fetch(`https://api.openweathermap.org/data/2.5/weather?q=${city.value}&units=metric&lang=fr&appid=REDACTED`)
  const data = await response.json()
  console.log("weather", data)
  if (data.cod === '404') {
    throw new Error(data.message);
  }
  weather.value = data
}

async function fetchForecast() {
  const response = await fetch(`https://api.openweathermap.org/data/2.5/forecast?q=${city.value}&units=metric&lang=fr&appid=ee07e2bf337034f905cde0bdedae3db8`)
  const data = await response.json()
  console.log("forecast", data)
  if (data.cod === '404') {
    throw new Error(data.message);
  }
  forecast.value = data
}
</script>
