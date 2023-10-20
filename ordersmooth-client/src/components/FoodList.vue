<template>
    <v-row dense>
        <v-col>
            <FoodInfo></FoodInfo>
        </v-col>
        <v-col>
            <FoodInfo></FoodInfo>
        </v-col>
        <v-col>
            <FoodInfo></FoodInfo>
        </v-col>
        <v-col>
            <FoodInfo></FoodInfo>
        </v-col>
        <v-col>
            <FoodInfo></FoodInfo>
        </v-col>
        <v-col>
            <FoodInfo></FoodInfo>
        </v-col>
        <v-col>
            <FoodInfo></FoodInfo>
        </v-col>
    </v-row>
</template>

<script>
import FoodInfo from './FoodInfo.vue'

export default {
    components: {
        FoodInfo,
    }
}
</script>