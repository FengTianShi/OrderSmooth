<template>
  <v-row dense>
    <span class="text-h6 font-weight-bold pa-2">おすすめ</span>
  </v-row>

  <v-row dense>
    <v-col cols="6">
      <FoodInfo></FoodInfo>
    </v-col>
    <v-col cols="6">
      <FoodInfo></FoodInfo>
    </v-col>
    <v-col cols="6">
      <FoodInfo></FoodInfo>
    </v-col>
  </v-row>

  <v-row dense>
    <span class="text-h6 font-weight-bold pa-2">お寿司</span>
  </v-row>

  <v-row dense>
    <v-col cols="6">
      <FoodInfo></FoodInfo>
    </v-col>
    <v-col cols="6">
      <FoodInfo></FoodInfo>
    </v-col>
  </v-row>
</template>

<script>
import FoodInfo from "./FoodInfo.vue";

export default {
  components: {
    FoodInfo,
  },
};
</script>
