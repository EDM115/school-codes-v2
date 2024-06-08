import Unfonts from "unplugin-fonts/vite"
import vue from "@vitejs/plugin-vue"
import vueDevTools from "vite-plugin-vue-devtools"
import vuetify, { transformAssetUrls } from "vite-plugin-vuetify"
import { fileURLToPath, URL } from "node:url"
import { defineConfig } from "vite"

export default defineConfig({
  plugins: [
    Unfonts({
      google: {
        families: [
          "Inter"
        ],
      },
    }),
    vue({
      template: { transformAssetUrls },
    }),
    vueDevTools(),
    vuetify({
      autoImport: true
    })
  ],
  preview: {
    port: 3210,
  },
  resolve: {
    alias: {
      '@': fileURLToPath(new URL("./src", import.meta.url)),
    },
  },
  server: {
    port: 3210,
  },
})
