<template>
    <v-card min-width="300">
        <v-img
            src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSvJT9-fdT4aP4n_nOB23udyfG5hBQvX6dtnA&usqp=CAU"></v-img>

        <v-card-text class="pb-0 text-h6">
            Chicken Noodle Soup
        </v-card-text>

        <v-card-text class="pb-0 text-grey">
            Romaine lettuce, croutons, and parmesan cheese tossed in Caesar dressing.
        </v-card-text>

        <v-card-actions>
            <v-card-text>
                $ 9999
            </v-card-text>
            <v-spacer></v-spacer>
            <v-btn icon="mdi-plus" @click="ShowDetail = true"></v-btn>
        </v-card-actions>
    </v-card>

    <FoodDetail v-model="ShowDetail" @closed="ShowDetail = false;"></FoodDetail>
</template>

<script>
import FoodDetail from './FoodDetail.vue'

export default {
    components: {
        FoodDetail,
    }, data() {
        return {
            ShowDetail: false,
        }
    },
}
</script>
