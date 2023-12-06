<template>
  <v-layout>
    <OwnerHeader />
    <v-main>
      <v-container fluid class="pa-4">
        <v-card class="mx-auto" elevation="0">
          <v-card class="mx-auto pa-8" max-width="1000" elevation="0">
            <v-row>
              <v-col cols="12" md="3">
                <v-tabs v-model="tab" direction="vertical" color="primary">
                  <v-tab value="option-1">
                    <v-icon start> mdi-account </v-icon>
                    Base
                  </v-tab>
                  <v-tab value="option-2">
                    <v-icon start> mdi-lock </v-icon>
                    Payment
                  </v-tab>
                  <v-tab value="option-3">
                    <v-icon start> mdi-lock </v-icon>
                    Opening Hours
                  </v-tab>
                  <v-tab value="option-4">
                    <v-icon start> mdi-lock </v-icon>
                    Security
                  </v-tab>
                </v-tabs>
              </v-col>
              <v-col cols="12" md="9">
                <v-window v-model="tab">
                  <v-window-item value="option-1">
                    <OwnerRestaurantDetailBase :restaurantId="restaurantId" />
                  </v-window-item>
                  <v-window-item value="option-2">
                    <OwnerRestaurantDetailPayment
                      :restaurantId="restaurantId" />
                  </v-window-item>
                  <v-window-item value="option-3">
                    <OwnerRestaurantDetailOpeningHours
                      :restaurantId="restaurantId" />
                  </v-window-item>
                  <v-window-item value="option-4">
                    <OwnerRestaurantDetailSecurity
                      :restaurantId="restaurantId" />
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
import OwnerHeader from "./common/OwnerHeader.vue";
import OwnerRestaurantDetailBase from "./OwnerRestaurantDetailBase.vue";
import OwnerRestaurantDetailPayment from "./OwnerRestaurantDetailPayment.vue";
import OwnerRestaurantDetailOpeningHours from "./OwnerRestaurantDetailOpeningHours.vue";
import OwnerRestaurantDetailSecurity from "./OwnerRestaurantDetailSecurity.vue";

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
    OwnerHeader,
    OwnerRestaurantDetailBase,
    OwnerRestaurantDetailPayment,
    OwnerRestaurantDetailOpeningHours,
    OwnerRestaurantDetailSecurity,
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
