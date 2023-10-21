<template>
    <v-bottom-sheet v-model="isShow">
        <v-card>
            <div class="d-flex flex-no-wrap justify-space-between">
                <div>
                    <v-card-text class="pb-0 text-h6">
                        Chicken Noodle Soup
                    </v-card-text>

                    <v-card-text class="pb-0 text-grey">
                        Appetizers , Large Size
                    </v-card-text>

                    <v-card-text class="pb-0">
                        $ 9999
                    </v-card-text>
                </div>

                <v-avatar class="ma-3" size="80" rounded="0">
                    <v-img
                        src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSvJT9-fdT4aP4n_nOB23udyfG5hBQvX6dtnA&usqp=CAU"></v-img>
                </v-avatar>
            </div>

            <v-card-actions>
                <v-btn class="mx-0" icon="mdi-minus"></v-btn>
                <label class="mx-3">1</label>
                <v-btn class="mx-0" icon="mdi-plus"></v-btn>
                <v-spacer></v-spacer>
                <v-btn class="mx-0" color="error" icon="mdi-trash-can-outline"></v-btn>
            </v-card-actions>

            <v-divider></v-divider>

            <div class="d-flex flex-no-wrap justify-space-between">
                <div>
                    <v-card-text class="pb-0 text-h6">
                        Chicken Noodle Soup
                    </v-card-text>

                    <v-card-text class="pb-0 text-grey">
                        Appetizers
                    </v-card-text>

                    <v-card-text class="pb-0">
                        $ 9999
                    </v-card-text>
                </div>

                <v-avatar class="ma-3" size="80" rounded="0">
                    <v-img
                        src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSvJT9-fdT4aP4n_nOB23udyfG5hBQvX6dtnA&usqp=CAU"></v-img>
                </v-avatar>
            </div>

            <v-card-actions>
                <v-btn class="mx-0" icon="mdi-minus"></v-btn>
                <label class="mx-3">1</label>
                <v-btn class="mx-0" icon="mdi-plus"></v-btn>
                <v-spacer></v-spacer>
                <v-btn class="mx-0" color="error" icon="mdi-trash-can-outline"></v-btn>
            </v-card-actions>

            <v-divider></v-divider>

            <div class="d-flex flex-no-wrap justify-space-between">
                <v-card-text>
                    Total : $ 999999
                </v-card-text>
            </div>

            <v-card-actions>
                <v-btn color="error">CLEAR</v-btn>
                <v-spacer></v-spacer>
                <v-btn color="amber" prepend-icon="mdi-room-service-outline">OEDER</v-btn>
                <v-btn color="grey" @click="close">CLOSE</v-btn>
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