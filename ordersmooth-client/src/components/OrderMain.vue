<template>
    <v-layout>
        <OrderHeader></OrderHeader>
        <HeadMenu></HeadMenu>
        <v-main>
            <v-container fluid class="pa-1">
                <FoodList></FoodList>
            </v-container>
        </v-main>
        <OrderFooter></OrderFooter>
    </v-layout>
</template>

<script>
import OrderHeader from './OrderHeader.vue'
import HeadMenu from './HeadMenu.vue'
import FoodList from './FoodList.vue'
import OrderFooter from './OrderFooter.vue'

export default {
    components: {
        OrderHeader,
        HeadMenu,
        FoodList,
        OrderFooter,
    }
}
</script>