<template>
    <v-bottom-sheet v-model="isShow">
        <v-card>
            <v-img
                src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSvJT9-fdT4aP4n_nOB23udyfG5hBQvX6dtnA&usqp=CAU"></v-img>

            <v-card-text class="px-4 pb-0 text-h6">
                Chicken Noodle Soup
            </v-card-text>

            <v-card-text class="px-4 text-grey">
                Romaine lettuce, croutons, and parmesan cheese tossed in Caesar dressing.
            </v-card-text>

            <div class="px-4">
                <label class="text-caption">Option</label>
                <br>
                <v-btn size="small" rounded="xl" variant="outlined" class="ma-1">Appetizers</v-btn>
                <v-btn size="small" rounded="xl" variant="outlined" class="ma-1">Soups</v-btn>
                <v-btn size="small" rounded="xl" variant="outlined" class="ma-1">Spaghetti</v-btn>
            </div>

            <v-card-actions>
                <v-card-text>
                    $ 9999
                </v-card-text>
                <v-spacer></v-spacer>
                <v-btn class="mx-0" icon="mdi-minus"></v-btn>
                <label class="mx-3">1</label>
                <v-btn class="mx-0" icon="mdi-plus"></v-btn>
            </v-card-actions>

            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="amber">ADD TO CART</v-btn>
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