<template>
  <h2 class="text-h4 font-weight-black text-orange">
    Restaurant Opening Hours
  </h2>
  <v-divider class="my-6"></v-divider>

  <p>desdesdesdesdesdesdesdesdesdesdesdesdesdesdesdesdes</p>

  <v-switch
    v-model="set"
    inset
    hide-details
    color="deep-purple-accent-4"
    :label="set ? 'disableLabel' : 'setLabel'"
    @click="disableLocation">
  </v-switch>

  <div v-if="set">
    <GoogleMap
      class="mb-8"
      api-key="AIzaSyDiSslqB0Xb6yWHgzIo-W4A_xzyiH1EUJw"
      :language="$i18n.locale"
      style="width: 100%; height: 500px"
      :center="center"
      :zoom="17">
      <Marker :options="{ position: center }" />
    </GoogleMap>

    <v-form validate-on="submit lazy" @submit.prevent="updateLocation">
      <v-text-field
        label="Latitude"
        class="mb-2"
        density="compact"
        variant="outlined"
        prepend-inner-icon="mdi-map-marker"
        persistent-hint
        maxlength="100"
        v-model="restaurantLatitude"
        :rules="[required, number]" />

      <v-text-field
        label="Longitude"
        class="mb-2"
        density="compact"
        variant="outlined"
        prepend-inner-icon="mdi-map-marker"
        persistent-hint
        maxlength="100"
        v-model="restaurantLongitude"
        :rules="[required, number]" />

      <v-text-field
        label="Service Distance"
        placeholder="1 ~ 99999m"
        class="mb-2"
        density="compact"
        variant="outlined"
        prepend-inner-icon="mdi-map-marker-distance"
        suffix="m"
        persistent-hint
        maxlength="5"
        v-model="serviceDistance"
        :rules="[required, number, greaterThanZero]" />

      <v-btn
        block
        class="mb-2"
        color="primary"
        prepend-icon="mdi-crosshairs-gps"
        variant="tonal"
        @click="getLocation">
        Get your location
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
    </v-form>
  </div>

  <v-dialog v-model="updated" max-width="400">
    <v-card class="pa-4">
      <v-row>
        <v-col cols="10">
          <p>Info</p>
        </v-col>
        <v-col cols="2" class="text-right">
          <v-btn
            icon="mdi-close"
            variant="text"
            size="x-small"
            @click="updated = false" />
        </v-col>
      </v-row>

      <v-divider class="my-2"></v-divider>

      <p class="py-4 text-center">
        <v-icon color="success" size="28">mdi-check-circle</v-icon>
        Update success
      </p>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="error" @click="updated = false"> Close </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
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
    set: false,
    center: { lat: 64.14205911108951, lng: -21.927207981014266 },
    restaurantLongitude: "",
    restaurantLatitude: "",
    serviceDistance: "",
    loading: false,
    updated: false,
  }),
  methods: {
    setLocation() {
      this.center = {
        lat: Number(this.restaurantLatitude),
        lng: Number(this.restaurantLongitude),
      };
    },
    getLocation() {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition((position) => {
          this.restaurantLatitude = position.coords.latitude;
          this.restaurantLongitude = position.coords.longitude;
          this.setLocation();
        });
      } else {
        alert("Geolocation is not supported by this browser.");
      }
    },
    async updateLocation(event) {
      const results = await event;
      if (results.valid) {
        this.setLocation();
        await this.updateRestaurant();
      }
    },
    async disableLocation() {
      if (this.set) {
        await this.updateRestaurant();
      } else {
        this.serviceDistance = "";
      }
    },
    async updateRestaurant() {
      this.loading = true;

      await this.getRestaurantDetail();

      await this.$http
        .put(
          `/restaurant/${this.restaurantDetail.restaurantId}`,
          {
            genreId: this.restaurantDetail.genreId,
            restaurantI18ns: this.restaurantDetail.i18n.map((item) => {
              return {
                langCode: item.langCode,
                restaurantName: item.restaurantName,
                address: item.restaurantAddress,
                description: item.restaurantDescription,
              };
            }),
            tel: this.restaurantDetail.restaurantTel,
            postalCode: this.restaurantDetail.restaurantPostalCode,
            restaurantLongitude: this.set ? this.restaurantLongitude : null,
            restaurantLatitude: this.set ? this.restaurantLatitude : null,
            restaurantServiceDistance: this.set ? this.serviceDistance : 0,
            currencyId: this.restaurantDetail.currencyId,
            payMethodIds: this.restaurantDetail.payMethods.map(
              (item) => item.payMethodId
            ),
            defaultServiceFee: this.restaurantDetail.defaultServiceFee,
            defaultTax: this.restaurantDetail.defaultTax,
            isDisplayServiceFee: this.restaurantDetail.isDisplayServiceFee,
            isDisplayTax: this.restaurantDetail.isDisplayTax,
            wifiSsid: this.restaurantDetail.wifiSsid,
            wifiPassword: this.restaurantDetail.wifiPassword,
            restaurantOpeningHours: this.restaurantDetail.openingHours.map(
              (item) => {
                return {
                  dayInWeekId: item.dayInWeekId,
                  dayInWeekOpeningHours: [
                    {
                      openingTime: item.openTime.slice(0, 5),
                      closingTime: item.closeTime.slice(0, 5),
                    },
                  ],
                };
              }
            ),
          },
          {
            headers: {
              Authorization: `Bearer ${JSON.parse(
                window.localStorage.getItem("owner-token")
              )}`,
            },
          }
        )
        .then((response) => {
          if (response.status == 204) {
            this.updated = true;
          }
        })
        .catch((error) => {
          if (error.response.status === 401) {
            this.$router.push("/Signin");
          }
        });

      this.loading = false;
    },
    async getRestaurantDetail() {
      await this.$http
        .get(`/restaurant/${this.restaurantId}`, {
          headers: {
            Authorization: `Bearer ${JSON.parse(
              window.localStorage.getItem("owner-token")
            )}`,
          },
        })
        .then((response) => {
          if (response.status === 200) {
            this.restaurantDetail = response.data;
          }
        })
        .catch((error) => {
          if (error.response.status === 401) {
            this.$router.push("/Signin");
          }
        });
    },
    required(value) {
      return !!value || this.$t("createRestaurant.requiredError");
    },
    number(value) {
      return !isNaN(value) || this.$t("createRestaurant.numberError");
    },
    greaterThanZero(value) {
      return (
        value > 0 || this.$t("createRestaurant.numberGreaterThanZeroError")
      );
    },
  },
  async created() {
    await this.getRestaurantDetail();

    if (this.restaurantDetail.restaurantServiceDistance === null) {
      return;
    }

    this.restaurantLatitude = this.restaurantDetail.restaurantLatitude;
    this.restaurantLongitude = this.restaurantDetail.restaurantLongitude;
    this.setLocation();

    if (this.restaurantDetail.restaurantServiceDistance !== 0) {
      this.serviceDistance = this.restaurantDetail.restaurantServiceDistance;
      this.set = true;
    }
  },
});
</script>
