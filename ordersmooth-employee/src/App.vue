<template>
  <v-layout>
    <AppHeader></AppHeader>
    <v-main>
      <v-container fluid class="pa-1">
      </v-container>
    </v-main>
    <AppFooter></AppFooter>
  </v-layout>
</template>

<script>
import AppHeader from './components/AppHeader.vue'
import AppFooter from './components/AppFooter.vue'

export default {
  components: {
    AppHeader,
    AppFooter,
  }
}
</script>