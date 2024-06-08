import { createApp } from 'vue'
import 'vuetify/styles'
import './weather-icons/css/weather-icons.min.css'
import { createVuetify } from 'vuetify'
import App from './App.vue'

const vuetify = createVuetify({
  theme: {
    defaultTheme: 'dark',
  }, 
})

createApp(App).use(vuetify).mount('#app')
