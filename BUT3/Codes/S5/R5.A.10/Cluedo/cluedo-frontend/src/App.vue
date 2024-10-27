<template>
  <v-app>
    <v-main>
      <v-app-bar rounded>
        <template #prepend>
          <a
            class="text-decoration-none"
            style="color: inherit;"
            href="/"
          >
            <v-app-bar-nav-icon :icon="mdiHomeOutline" />
          </a>
        </template>

        <a
          class="text-decoration-none"
          style="color: inherit;"
          href="/"
        >
          <v-app-bar-title>Cluedo - EDM115</v-app-bar-title>
        </a>

        <template #append />
      </v-app-bar>

      <RouterView />
    </v-main>
  </v-app>
</template>

<script setup>
import mdiHomeOutline from "~icons/mdi/homeOutline"
</script>
