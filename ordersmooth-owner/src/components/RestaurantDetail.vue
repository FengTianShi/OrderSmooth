<template>
  <v-layout>
    <AppHeader />
    <v-main>
      <v-container fluid class="pa-4">
        <v-card class="mx-auto" elevation="0">
          <v-card class="mx-auto pa-8" max-width="1000" elevation="0">
            <v-row>
              <v-col cols="12" md="3">
                <v-tabs v-model="tab" direction="vertical" color="primary">
                  <v-tab value="option-1">
                    <v-icon start>mdi-store </v-icon>
                    Base
                  </v-tab>
                  <v-tab value="option-2">
                    <v-icon start> mdi-credit-card </v-icon>
                    Payment
                  </v-tab>
                  <v-tab value="option-3">
                    <v-icon start> mdi-map-marker </v-icon>
                    Location
                  </v-tab>
                  <v-tab value="option-4">
                    <v-icon start> mdi-clock-time-nine </v-icon>
                    Opening Hours
                  </v-tab>
                  <v-tab value="option-5">
                    <v-icon start> mdi-image</v-icon>
                    Images
                  </v-tab>
                  <v-tab value="option-6">
                    <v-icon start> mdi-printer </v-icon>
                    Printer
                  </v-tab>
                </v-tabs>
              </v-col>
              <v-col cols="12" md="9">
                <v-window v-model="tab">
                  <v-window-item value="option-1">
                    <RestaurantDetailBase :restaurantId="restaurantId" />
                  </v-window-item>
                  <v-window-item value="option-2">
                    <RestaurantDetailPayment :restaurantId="restaurantId" />
                  </v-window-item>
                  <v-window-item value="option-3">
                    <RestaurantDetailLocation :restaurantId="restaurantId" />
                  </v-window-item>
                  <v-window-item value="option-4">
                    <RestaurantDetailOpeningHours
                      :restaurantId="restaurantId" />
                  </v-window-item>
                  <v-window-item value="option-5">
                    <RestaurantDetailImage :restaurantId="restaurantId" />
                  </v-window-item>
                </v-window>
              </v-col>
            </v-row>
          </v-card>
        </v-card>
      </v-container>
    </v-main>
  </v-layout>
</template>

<script>
import AppHeader from "./common/AppHeader.vue";
import RestaurantDetailBase from "./RestaurantDetailBase.vue";
import RestaurantDetailLocation from "./RestaurantDetailLocation.vue";
import RestaurantDetailPayment from "./RestaurantDetailPayment.vue";
import RestaurantDetailOpeningHours from "./RestaurantDetailOpeningHours.vue";
import RestaurantDetailImage from "./RestaurantDetailImage.vue";

// { text: "Base", icon: "mdi-clock" },
// { text: "Payment", icon: "mdi-flag" },
// { text: "Opening Hours", icon: "mdi-flag" },
// { text: "SNS", icon: "mdi-account" },
// { text: "Images", icon: "mdi-account" },
// { text: "Printer", icon: "mdi-flag" },
// { text: "Security", icon: "mdi-flag" },
// { text: "Control", icon: "mdi-flag" },

export default {
  components: {
    AppHeader,
    RestaurantDetailBase,
    RestaurantDetailPayment,
    RestaurantDetailLocation,
    RestaurantDetailOpeningHours,
    RestaurantDetailImage,
  },
  data: () => ({
    restaurantId: null,
    tab: "option-1",
    loading: false,
  }),
  methods: {},
  created() {
    this.restaurantId = this.$route.params.id;
  },
};
</script>
