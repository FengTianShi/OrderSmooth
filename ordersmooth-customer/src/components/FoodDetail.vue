<template>
    <v-bottom-sheet>
        <v-card>
            <v-img cover height="240" src="https://sushi-umi.co.jp/wp-content/uploads/2022/12/DSC_4485.jpg"></v-img>

            <v-card-title class="px-4 py-2 font-weight-bold">
                お寿司
            </v-card-title>

            <v-card-text class="px-4 py-2 text-grey" style="font-size: 14px;">
                日本のケーキの定番、クリスマスケーキでもおなじみのイチゴのショートケーキ!
            </v-card-text>

            <v-sheet elevation="0" class="px-4 py-1">
                <v-card-text class="pa-0 py-1 font-weight-bold" style="font-size: 14px;">
                    必須項目
                </v-card-text>
                <v-chip-group mandatory column>
                    <v-chip filter style="font-size: 12px;">
                        生クリーム
                    </v-chip>
                    <v-chip filter style="font-size: 12px;">
                        ￥50 ヨグルティ
                    </v-chip>
                </v-chip-group>
            </v-sheet>

            <v-sheet elevation="0" class="px-4 py-1">
                <v-card-text class="pa-0 py-1 font-weight-bold" style="font-size: 14px;">
                    フルーツ
                </v-card-text>
                <v-chip-group multiple column>
                    <v-chip filter style="font-size: 12px;">
                        ￥50 イチゴ
                    </v-chip>
                    <v-chip filter style="font-size: 12px;">
                        ￥50 チョコレート
                    </v-chip>
                    <v-chip filter style="font-size: 12px;">
                        ￥50 ブルーベリー
                    </v-chip>
                    <v-chip filter style="font-size: 12px;">
                        ￥120 クリーム
                    </v-chip>
                </v-chip-group>
            </v-sheet>

            <v-card-actions>
                <v-card-text class="pa-2">
                    <span class="text-red-darken-1 font-weight-bold pr-1">￥2,000</span>
                    <span class="text-grey" style="font-size: 10px;">税込み</span>
                </v-card-text>
                <v-spacer></v-spacer>
                <v-btn class="mx-0" icon="mdi-minus"></v-btn>
                <label class="mx-3">1</label>
                <v-btn class="mx-0" icon="mdi-plus"></v-btn>
            </v-card-actions>

            <template v-slot:actions>
                <v-row class="px-3 py-1">
                    <v-col cols="4" class="pa-0 pr-2">
                        <v-btn block variant="tonal" class="font-weight-bold" @click="close">
                            閉じる
                        </v-btn>
                    </v-col>
                    <v-col cols="8" class="pa-0">
                        <v-btn block class="flex-grow-1 font-weight-bold" variant="flat" color="amber"
                            prepend-icon="mdi-cart-outline">
                            カートに入れる
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
        VBottomSheet
    },
    methods: {
        close() {
            this.$emit('closed');
        }
    }
}
</script>
