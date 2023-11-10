<template>
    <v-bottom-sheet>
        <v-card>
            <div class="d-flex flex-no-wrap justify-space-between">
                <div>
                    <v-card-text class="pa-4 pb-1 pr-0 font-weight-bold" style="font-size: 16px;">
                        マグロ中とろ
                    </v-card-text>

                    <v-card-text class="pa-4 py-1 pr-0 text-grey" style="font-size: 14px;">
                        生クリーム, ヨグルティ生クリーム
                    </v-card-text>

                    <v-card-text class="pa-4 py-1 pt-1">
                        <span class="text-red-darken-1 font-weight-bold pr-1">￥2,000</span>
                        <span class="text-grey" style="font-size: 10px;">税込み</span>
                    </v-card-text>
                </div>

                <v-avatar class="ma-3" size="80" rounded="0">
                    <v-img cover class="rounded-lg"
                        src="https://sushi-umi.co.jp/wp-content/uploads/2022/12/DSC_4485.jpg"></v-img>
                </v-avatar>
            </div>

            <v-card-actions class="pt-0">
                <v-btn class="mx-0" icon="mdi-minus"></v-btn>
                <label class="mx-3">1</label>
                <v-btn class="mx-0" icon="mdi-plus"></v-btn>
                <v-spacer></v-spacer>
                <v-btn class="mx-0" color="error" icon="mdi-trash-can-outline"></v-btn>
            </v-card-actions>

            <v-divider></v-divider>

            <div class="d-flex flex-no-wrap justify-space-between">
                <div>
                    <v-card-text class="pa-4 pb-1 pr-0 font-weight-bold" style="font-size: 16px;">
                        マグロ中とろ
                    </v-card-text>

                    <v-card-text class="pa-4 py-1 pr-0 text-grey" style="font-size: 14px;">
                        生クリーム, ヨグルティ生クリーム
                    </v-card-text>

                    <v-card-text class="pa-4 py-1 pt-1">
                        <span class="text-red-darken-1 font-weight-bold pr-1">￥2,000</span>
                        <span class="text-grey" style="font-size: 10px;">税込み</span>
                    </v-card-text>
                </div>

                <v-avatar class="ma-3" size="80" rounded="0">
                    <v-img cover class="rounded-lg"
                        src="https://sushi-umi.co.jp/wp-content/uploads/2022/12/DSC_4485.jpg"></v-img>
                </v-avatar>
            </div>

            <v-card-actions class="pt-0">
                <v-btn class="mx-0" icon="mdi-minus"></v-btn>
                <label class="mx-3">9</label>
                <v-btn class="mx-0" icon="mdi-plus"></v-btn>
                <v-spacer></v-spacer>
                <v-btn class="mx-0" color="error" icon="mdi-trash-can-outline"></v-btn>
            </v-card-actions>

            <v-divider></v-divider>

            <v-card-text class="font-weight-bold d-flex justify-end">
                合計<v-spacer></v-spacer>￥2,000
            </v-card-text>

            <template v-slot:actions>
                <v-row class="px-3 py-1">
                    <v-col cols="3" class="pa-0 pr-2">
                        <v-btn block variant="tonal" class="font-weight-bold" @click="close">
                            閉じる
                        </v-btn>
                    </v-col>
                    <v-col cols="6" class="pa-0 pr-2">
                        <v-btn block class="flex-grow-1 font-weight-bold" variant="flat" color="amber"
                            prepend-icon="mdi-room-service-outline">
                            注文する
                        </v-btn>
                    </v-col>
                    <v-col cols="3" class="pa-0">
                        <v-btn block variant="flat" color="error" class="font-weight-bold">
                            クリア
                        </v-btn>
                    </v-col>
                </v-row>
            </template>
        </v-card>
    </v-bottom-sheet>
</template>

<script>
import { VBottomSheet } from 'vuetify/labs/VBottomSheet'

export default {
    components: {
        VBottomSheet,
    },
    methods: {
        close() {
            this.$emit('closed');
        }
    }
}
</script>
