<template>
    <v-footer app name="footer">

        <v-row justify="center" class="pt-1">
            <v-btn size="small" :ripple="false" stacked variant="text" @click="ShowOrderHistory = true;">
                <v-icon>mdi-history</v-icon>
                <span>注文履歴</span>
            </v-btn>

            <v-btn size="small" :ripple="false" stacked variant="text" @click="ShowCartInfo = true" class="mx-4">
                <v-badge color="error" content="2">
                    <v-icon>mdi-cart-outline</v-icon>
                </v-badge>
                <span>カート</span>
            </v-btn>

            <v-btn size="small" :ripple="false" stacked variant="text" @click.stop="drawer = !drawer">
                <v-icon>mdi-menu</v-icon>
                <span>メニュー</span>
            </v-btn>
        </v-row>

    </v-footer>

    <v-navigation-drawer location="right" v-model="drawer">
        <v-list-item link title="一品料理"></v-list-item>
        <v-list-item link title="焼き物"></v-list-item>
        <v-list-item link title="おつまみ"></v-list-item>
        <v-list-item link title="サラダ"></v-list-item>
        <v-list-item link title="お食事"></v-list-item>
        <v-list-item link title="デザート"></v-list-item>
        <v-list-item link title="ソフトドリンク"></v-list-item>
        <v-list-item link title="アルコール"></v-list-item>
    </v-navigation-drawer>

    <OrderConfirm v-model="ShowOrderConfirm" @closed="ShowOrderConfirm = false" />
    <OrderHistory v-model="ShowOrderHistory" @closed="ShowOrderHistory = false" />
    <CartInfo v-model="ShowCartInfo" @closed="ShowCartInfo = false" />
</template>

<script>
import OrderConfirm from './OrderConfirm.vue'
import OrderHistory from './OrderHistory.vue'
import CartInfo from './CartInfo.vue'

export default {
    components: {
        OrderConfirm,
        OrderHistory,
        CartInfo,
    }, data() {
        return {
            drawer: false,
            ShowOrderConfirm: false,
            ShowOrderHistory: false,
            ShowCartInfo: false,
        }
    }
}
</script>
