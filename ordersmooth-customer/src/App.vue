<template>
  <v-layout>

    <AppHeader></AppHeader>

    <v-main>

      <v-carousel cycle show-arrows="hover" height="240">
        <v-carousel-item cover src="https://sushi-umi.co.jp/wp-content/uploads/2022/12/82179212.jpg">
        </v-carousel-item>
        <v-carousel-item cover src="https://sushi-umi.co.jp/wp-content/uploads/2022/12/e-01.jpg">
        </v-carousel-item>
        <v-carousel-item cover src="https://sushi-umi.co.jp/wp-content/uploads/2022/12/IMG_4616.jpg">
        </v-carousel-item>
      </v-carousel>

      <v-card flat>
        <v-tabs show-arrows>
          <v-tab>おすすめ</v-tab>
          <v-tab>一品料理</v-tab>
          <v-tab>焼き物</v-tab>
          <v-tab>おつまみ</v-tab>
          <v-tab>サラダ</v-tab>
          <v-tab>お食事</v-tab>
          <v-tab>デザート</v-tab>
        </v-tabs>
      </v-card>

      <v-container fluid class="pa-2">
        <FoodList></FoodList>
      </v-container>
    </v-main>

    <AppFooter></AppFooter>

  </v-layout>

  <v-dialog v-model="dialog" persistent width="auto">
    <v-card>
      <v-card-title class="text-h5">
        来店登録お願いいたします
      </v-card-title>

      <v-card-text>
        <v-sheet class="mx-auto">
          <v-select label="既存団体" required></v-select>
          <v-select label="コース" required></v-select>
          <v-text-field label="お名前"></v-text-field>
          <v-text-field label="人数" required></v-text-field>
        </v-sheet>
      </v-card-text>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="green-darken-1" variant="text" @click="dialog = false">
          登録する
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import AppHeader from './components/AppHeader.vue'
import FoodList from './components/FoodList.vue'
import AppFooter from './components/AppFooter.vue'

export default {
  components: {
    AppHeader,
    FoodList,
    AppFooter,
  }, data() {
    return {
      dialog: true,
    }
  },
}
</script>

<style>
#app {
  font-family: 'Noto Sans JP', sans-serif;
}
</style>
