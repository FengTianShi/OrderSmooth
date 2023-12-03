<template>
  <v-layout>
    <OwnerHeader />
    <v-main>
      <v-container fluid class="pa-4">
        <v-card class="mx-auto" elevation="0">
          <v-card class="mx-auto pa-8" max-width="800" elevation="0">
            {{ $route.params.id }}
          </v-card>
        </v-card>
      </v-container>
    </v-main>
  </v-layout>
</template>

<script>
import OwnerHeader from "./common/OwnerHeader.vue";

export default {
  components: {
    OwnerHeader,
  },
};
</script>
