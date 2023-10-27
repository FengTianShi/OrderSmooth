<template>
    <v-card @click="ShowDetail = true">
        <v-img cover height="140" src="https://sushi-umi.co.jp/wp-content/uploads/2022/12/DSC_4485.jpg">
        </v-img>

        <v-card-title class="pa-2 py-1" style="font-size: 14px;">
            マグロ中とろ
        </v-card-title>

        <v-card-text class="pa-2 pb-4">
            <span class="text-red-darken-1 font-weight-bold pr-1">￥2000</span>
            <span class="text-grey" style="font-size: 10px;">税込み</span>
        </v-card-text>
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
