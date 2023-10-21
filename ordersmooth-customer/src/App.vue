<template>
  <v-layout>
    <AppHeader></AppHeader>
    <FoodClass></FoodClass>
    <v-main>
      <v-container fluid class="pa-1">
        <FoodList></FoodList>
      </v-container>
    </v-main>
    <AppFooter></AppFooter>
  </v-layout>
</template>

<script>
import AppHeader from './components/AppHeader.vue'
import FoodClass from './components/FoodClass.vue'
import FoodList from './components/FoodList.vue'
import AppFooter from './components/AppFooter.vue'

export default {
  components: {
    AppHeader,
    FoodClass,
    FoodList,
    AppFooter,
  }
}
</script>