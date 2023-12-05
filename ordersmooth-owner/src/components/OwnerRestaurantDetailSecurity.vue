<template>
  <h2 class="text-h4 font-weight-black text-orange">
    Restaurant Opening Hours
  </h2>
  <v-divider class="my-6"></v-divider>

  <GoogleMap
  class="mb-6"
    api-key="AIzaSyDiSslqB0Xb6yWHgzIo-W4A_xzyiH1EUJw"
    :language="$i18n.locale"
    style="width: 100%; height: 500px"
    :center="center"
    :zoom="18">
    <Marker :options="{ position: center }" />
  </GoogleMap>

  <v-text-field
    label="Longitude"
    class="mb-2"
    density="compact"
    variant="outlined"
    prepend-inner-icon="mdi-store"
    persistent-hint
    maxlength="100"
    v-model="restaurantLongitude"
    :rules="[required, number]" />

  <v-text-field
    label="Latitude"
    class="mb-2"
    density="compact"
    variant="outlined"
    prepend-inner-icon="mdi-store"
    persistent-hint
    maxlength="100"
    v-model="restaurantLatitude"
    :rules="[required, number]" />

  <v-btn
    block
    class="mb-2"
    color="primary"
    prepend-icon="mdi-location-enter"
    variant="tonal"
    @click="getLocation">
    Get your location
  </v-btn>

  <v-btn
    block
    color="primary"
    prepend-icon="mdi-location-enter"
    variant="tonal"
    @click="setCenter">
    set
  </v-btn>

  <v-divider class="my-6"></v-divider>

<div class="text-right">
  <v-btn
    type="submit"
    color="deep-purple-accent-4"
    size="large"
    :disabled="loading"
    :loading="loading">
    Update
  </v-btn>
</div>
</template>

<script>
import { defineComponent } from "vue";
import { GoogleMap, Marker } from "vue3-google-map";

export default defineComponent({
  components: { GoogleMap, Marker },
  props: {
    restaurantId: {
      required: true,
    },
  },
  data: () => ({
    center: { lat: 35.72215851200829, lng: 139.68860315912076 },
    restaurantLongitude: "",
    restaurantLatitude: "",

    loading: false,
  }),
  methods: {
    getLocation() {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(this.showPosition);
      } else {
        alert("Geolocation is not supported by this browser.");
      }
    },
    setCenter() {
      this.center = {
        lat: this.restaurantLatitude,
        lng: this.restaurantLongitude,
      };
    },
    showPosition(position) {
      this.restaurantLongitude = position.coords.longitude;
      this.restaurantLatitude = position.coords.latitude;
      this.setCenter();
    },
    required(value) {
      return !!value || this.$t("createRestaurant.requiredError");
    },
    number(value) {
      return !isNaN(value) || this.$t("createRestaurant.numberError");
    },
  },
});
</script>
