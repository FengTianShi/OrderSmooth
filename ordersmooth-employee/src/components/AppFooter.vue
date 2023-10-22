<template>
    <v-app-bar location="bottom">
        <div class="ml-3 text-caption">
            Total :<br>$ 9999999
        </div>

        <v-spacer></v-spacer>

        <v-btn size="small" stacked @click="ShowReceiptInfo = true">
            <v-icon icon="mdi-receipt-clock-outline"></v-icon>
            Receipt
        </v-btn>

        <v-btn size="small" stacked @click="ShowCartInfo = true">
            <v-badge color="error" content="2">
                <v-icon icon="mdi-cart-outline"></v-icon>
            </v-badge>
            Cart
        </v-btn>

        <v-btn size="small" stacked @click="ShowOrderConfirm = true">
            <v-icon icon="mdi-room-service-outline"></v-icon>
            Order
        </v-btn>
    </v-app-bar>

    <ReceiptInfo v-model="ShowReceiptInfo" @closed="ShowReceiptInfo = false" />
    <CartInfo v-model="ShowCartInfo" @closed="ShowCartInfo = false" />
    <OrderConfirm v-model="ShowOrderConfirm" @closed="ShowOrderConfirm = false" />
</template>

<script>

export default {
    components: {
    }, data() {
        return {
        }
    }
}
</script>
