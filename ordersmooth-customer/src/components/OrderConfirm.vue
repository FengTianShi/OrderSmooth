<template>
    <v-dialog transition="dialog-bottom-transition" max-width="600" v-model="isShow">
        <v-card>
            <v-toolbar color="primary" title="Confirm your Order"></v-toolbar>
            <v-card-text>
                <div>Are you sure ?</div>
            </v-card-text>
            <v-card-actions class="justify-end">
                <v-btn color="amber">Sure</v-btn>
                <v-btn color="grey" @click="close">Not Yet</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>
export default {
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
