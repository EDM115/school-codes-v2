import vue from "@vitejs/plugin-vue"
import IconsResolver from "unplugin-icons/resolver"
import Icons from "unplugin-icons/vite"
import Unfonts from "unplugin-fonts/vite"
import Components from "unplugin-vue-components/vite"
import vueDevTools from "vite-plugin-vue-devtools"

import { fileURLToPath, URL } from "node:url"
import { defineConfig } from "vite"
import vuetify, { transformAssetUrls } from "vite-plugin-vuetify"

export default defineConfig({
  clearScreen: false,
  css: {
    preprocessorOptions: {
      sass: {
        api: "modern-compiler"
      }
    },
    preprocessorMaxWorkers: 2
  },
  esbuild: {
    target: "esnext"
  },
  plugins: [
    vue({
      features: { optionsAPI: false },
      template: { transformAssetUrls }
    }),
    vueDevTools({ launchEditor: "code-insiders" }),
    vuetify({
      autoImport: { labs: true },
      styles: {
        configFile: "src/styles/settings.scss"
      }
    }),
    Icons({
      compiler: "vue3"
    }),
    Unfonts({
      injectTo: "head",
      google: {
        families: [
          {
            name: "Fira Code"
          },
          {
            name: "Inter"
          },
          {
            name: "Nunito"
          }
        ]
      }
    }),
    Components({
      collapseSamePrefixes: true,
      directoryAsNamespace: true,
      dts: false,
      extensions: [ "vue" ],
      include: [ /\.vue$/, /\.vue\?vue/ ],
      resolvers: [
        IconsResolver({
          prefix: false
        })
      ],
      version: 3
    })
  ],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url))
    }
  },
  server: {
    host: true,
    port: 5173,
    hmr: {
      host: "localhost"
    }
  }
})
