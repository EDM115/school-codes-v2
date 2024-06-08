import "@mdi/font/css/materialdesignicons.css"
import "@/assets/style.css"
import "vuetify/styles"
import App from "@/App.vue"
import router from "@/router/index.js"
import { createPinia } from "pinia"
import { createApp } from "vue"
import { createVuetify } from "vuetify"

const pinia = createPinia()
const vuetify = createVuetify({
  theme: {
    defaultTheme: "dark",
    themes: {
      dark: {
        colors: {
          accent: "#F647AA",
          background: "#16181E",
          error: "#FF4242",
          info: "#55D0EB",
          "on-primary": "#16181E",
          "on-secondary": "#16181E",
          primary: "#FF9B2F",
          secondary: "#A362FF",
          success: "#2EF660",
          surface: "#282A36",
          text: "#F8F8F2",
          warning: "#E3F240",
        },
        dark: true,
      },
    },
  },
})

createApp(App).use(vuetify).use(pinia).use(router).mount("#app")
