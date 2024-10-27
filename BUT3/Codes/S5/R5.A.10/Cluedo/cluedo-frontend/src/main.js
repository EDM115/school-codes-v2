const originalWarn = console.warn

console.warn = (message, ...optionalParams) => {
  if (![
    "Invalid prop: type check failed for prop \"icon\"",
    "Invalid prop: type check failed for prop \"editIcon\""
  ].some((warning) => message.includes(warning))) {
    originalWarn.apply(console, [ message, ...optionalParams ])
  }
}

import "./styles/settings.scss"
import "vuetify/styles"

import App from "./App.vue"
import router from "./router"

import { createApp } from "vue"
import { createPinia } from "pinia"
import { createVuetify } from "vuetify"
import { aliases, mdi } from "vuetify/iconsets/mdi-svg"
import { fr } from "vuetify/locale"

const app = createApp(App)

app.use(createPinia())
app.use(createVuetify({
  icons: {
    aliases,
    defaultSet: "mdi",
    sets: { mdi }
  },
  locale: {
    locale: "fr",
    messages: { fr }
  },
  ssr: false,
  theme: {
    defaultTheme: "dark",
    themes: {
      dark: {
        colors: {
          accent: "#BD93F9",
          background: "#00040E",
          error: "#FF5555",
          info: "#8BE9FD",
          primary: "#FFB86C",
          secondary: "#50FA7B",
          success: "#50FA7B",
          text: "#F8F8F2",
          warning: "#FF79C6"
        },
        dark: true
      }
    }
  }
}))
app.use(router)

app.mount("#app")
