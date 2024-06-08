import "@mdi/font/css/materialdesignicons.css"
import "@/assets/style.css"
import "vuetify/styles"
import App from "@/App.vue"
import router from "@/router/router"
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
          accent: "#FF79C6",
          background: "#282A36",
          error: "#FF5555",
          info: "#8BE9FD",
          "on-primary": "#282A36",
          "on-secondary": "#282A36",
          primary: "#FFB86C",
          secondary: "#BD93F9",
          success: "#50FA7B",
          text: "#F8F8F2",
          warning: "#F1FA8C",
        },
        dark: true,
      },
    },
  },
})

createApp(App).use(vuetify).use(pinia).use(router).mount("#app")
