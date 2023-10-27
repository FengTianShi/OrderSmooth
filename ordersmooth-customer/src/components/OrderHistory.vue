<template>
    <v-bottom-sheet v-model="isShow">
        <v-card>
            <div class="d-flex flex-no-wrap justify-space-between">
                <div>
                    <v-card-text class="pa-4 pb-0 pr-0 font-weight-bold" style="font-size: 16px;">
                        マグロ中とろ
                        <span class="text-green font-weight-bold pl-1" style="font-size: 14px;">調理中</span>
                    </v-card-text>

                    <v-card-text class="pa-4 pb-0 pr-0 pt-1 text-grey" style="font-size: 14px;">
                        生クリーム, ヨグルティ
                    </v-card-text>

                    <v-card-text class="pa-4">
                        <span class="text-red-darken-1 font-weight-bold pr-1">￥2,000</span>
                        <span class="text-grey" style="font-size: 10px;">税込み</span>
                    </v-card-text>
                </div>

                <v-avatar class="ma-3" size="80" rounded="0">
                    <v-img cover class="rounded-lg"
                        src="https://sushi-umi.co.jp/wp-content/uploads/2022/12/DSC_4485.jpg"></v-img>
                </v-avatar>
            </div>

            <v-divider></v-divider>

            <div class="d-flex flex-no-wrap justify-space-between">
                <v-card-text class="font-weight-bold">
                    <span>合計 : </span>
                    <span class="text-red-darken-1">￥2,000</span>
                </v-card-text>
            </div>

            <v-card-actions>
                <v-btn color="amber">お会計</v-btn>
                <v-spacer></v-spacer>
                <v-btn color="grey" @click="close">閉じる</v-btn>
            </v-card-actions>

        </v-card>
    </v-bottom-sheet>
</template>

<script>
import { VBottomSheet } from 'vuetify/labs/VBottomSheet'

export default {
    components: {
        VBottomSheet,
    },
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
