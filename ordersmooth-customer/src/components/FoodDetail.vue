<template>
    <v-bottom-sheet v-model="isShow">
        <v-card>
            <v-img cover height="240" src="https://sushi-umi.co.jp/wp-content/uploads/2022/12/DSC_4485.jpg"></v-img>

            <v-card-title class="px-4 py-1 font-weight-bold">
                ショートケーキ
            </v-card-title>

            <v-card-text class="px-4 py-1 text-grey" style="font-size: 14px;">
                日本のケーキの定番、クリスマスケーキでもおなじみのイチゴのショートケーキ!
            </v-card-text>

            <v-sheet elevation="0" class="px-4 py-1">
                <v-card-text class="pa-1 font-weight-bold" style="font-size: 13px;">
                    必須項目
                </v-card-text>
                <v-chip-group mandatory selected-class="text-deep-orange">
                    <v-chip style="font-size: 12px;">
                        生クリーム
                    </v-chip>
                    <v-chip style="font-size: 12px;">
                        ￥50 ヨグルティ
                    </v-chip>
                </v-chip-group>
            </v-sheet>

            <v-sheet elevation="0" class="px-4 py-1">
                <v-card-text class="pa-1 font-weight-bold" style="font-size: 13px;">
                    フルーツ
                </v-card-text>
                <v-chip-group multiple selected-class="text-deep-orange">
                    <v-chip style="font-size: 12px;">
                        イチゴ
                    </v-chip>
                    <v-chip style="font-size: 12px;">
                        ￥50 チョコレート
                    </v-chip>
                    <v-chip style="font-size: 12px;">
                        ￥50 ブルーベリー
                    </v-chip>
                    <v-chip style="font-size: 12px;">
                        ￥120 クリーム
                    </v-chip>
                </v-chip-group>
            </v-sheet>

            <v-card-actions>
                <v-card-text class="pa-2">
                    <span class="text-red-darken-1 font-weight-bold pr-1" style="font-size: 16px;">￥2000</span>
                    <span class="text-grey" style="font-size: 10px;">税込み</span>
                </v-card-text>
                <v-spacer></v-spacer>
                <v-btn class="mx-0" icon="mdi-minus"></v-btn>
                <label class="mx-3">1</label>
                <v-btn class="mx-0" icon="mdi-plus"></v-btn>
            </v-card-actions>

            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn prepend-icon="mdi-cart-outline" color="amber">カートに入れる</v-btn>
                <v-btn color="grey" @click="close">閉じる</v-btn>
            </v-card-actions>
        </v-card>
    </v-bottom-sheet>
</template>

<script>
import { VBottomSheet } from 'vuetify/labs/VBottomSheet'

export default {
    components: { VBottomSheet },
    props: {
        value: {
            type: Boolean,
            default: false
        }
    },
    data() {
        return {
            isShow: this.value,
        }
    },
    watch: {
        value(newValue) {
            this.isShow = newValue;
        },
        isShow(newValue) {
            if (newValue !== this.value) {
                this.$emit('input', newValue);
            }
        }
    },
    methods: {
        close() {
            this.$emit('closed');
        }
    }
}
</script>
