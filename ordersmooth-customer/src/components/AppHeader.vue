<template>
    <v-app-bar elevation="0">
        <v-btn size="small" prepend-icon="mdi-menu" stacked @click.stop="drawer = !drawer">
            Menu
        </v-btn>
        <v-app-bar-title>Savory Elegance</v-app-bar-title>
        <v-btn icon="mdi-translate"></v-btn>
    </v-app-bar>

    <v-navigation-drawer v-model="drawer">
        <v-list-item title="Service"></v-list-item>
        <v-divider></v-divider>
        <v-list-item link title="List Item 1"></v-list-item>
        <v-list-item link title="List Item 2"></v-list-item>
        <v-list-item link title="List Item 3"></v-list-item>
        <v-list-item title="MENU"></v-list-item>
        <v-divider></v-divider>
        <v-list-item link title="List Item 1"></v-list-item>
        <v-list-item link title="List Item 2"></v-list-item>
        <v-list-item link title="List Item 3"></v-list-item>
        <v-list-item link title="List Item 1"></v-list-item>
        <v-list-item link title="List Item 2"></v-list-item>
        <v-list-item link title="List Item 3"></v-list-item>
        <v-list-item link title="List Item 1"></v-list-item>
        <v-list-item link title="List Item 2"></v-list-item>
        <v-list-item link title="List Item 3"></v-list-item>
    </v-navigation-drawer>
</template>

<script>

export default {
    data: () => ({
        drawer: false,
    }),
    watch: {
        group() {
            this.drawer = false
        }
    }
}
</script>
