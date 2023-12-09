<template>
  <h2 class="text-h5 font-weight-black text-primary">
    {{ $t("createRestaurant.title") }}
  </h2>
  <v-divider class="my-6"></v-divider>

  <GoogleMap
    class="mb-8"
    style="width: 100%; height: 500px"
    api-key="AIzaSyDiSslqB0Xb6yWHgzIo-W4A_xzyiH1EUJw"
    :language="$i18n.locale"
    :center="center"
    :zoom="17">
    <Marker :options="{ position: center }" />
  </GoogleMap>

  <v-form validate-on="submit lazy" @submit.prevent="updateRestaurantLocation">
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

    <v-btn
      block
      type="submit"
      color="primary"
      prepend-icon="mdi-crosshairs-gps"
      variant="tonal"
      :disabled="setLoading"
      :loading="setLoading">
      Set
    </v-btn>
  </v-form>

  <v-divider class="my-6"></v-divider>

  <v-form
    validate-on="submit lazy"
    @submit.prevent="updateRestaurantServiceDistance">
    <v-switch
      v-model="isLimitServiceDistance"
      inset
      color="deep-purple-accent-4"
      :label="isLimitServiceDistance ? 'disableLabel' : 'setLabel'"
      @click="updateRestaurantLimitServiceDistance"
      :disabled="alterLoading"
      :loading="alterLoading">
      >
    </v-switch>

    <div v-if="isLimitServiceDistance">
      <v-text-field
        label="Service Distance"
        placeholder="1 ~ 99999m"
        class="mb-2"
        density="compact"
        variant="outlined"
        prepend-inner-icon="mdi-map-marker-radius"
        suffix="m"
        persistent-hint
        maxlength="5"
        v-model="restaurantServiceDistance"
        :rules="[required, positiveIntNum, greaterThanZero]">
        <template v-slot:append>
          <v-btn
            type="submit"
            color="deep-purple-accent-4"
            size="large"
            :disabled="updateLoading"
            :loading="updateLoading">
            Update
          </v-btn>
        </template>
      </v-text-field>
    </div>
  </v-form>

  <v-dialog v-model="updated" max-width="500">
    <v-card class="pa-0">
      <div class="pa-2">
        <span>Info</span>
        <v-btn
          size="x-small"
          variant="text"
          icon="mdi-close"
          class="float-right"
          @click="updated = false" />
      </div>

      <v-divider class="mb-4"></v-divider>

      <p class="py-2 text-center">
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
import { restaurantUtils } from "./common/utils/RestaurantUtils.js";

export default defineComponent({
  mixins: [restaurantUtils],
  components: { GoogleMap, Marker },
  props: {
    restaurantId: {
      required: true,
    },
  },
  data: () => ({
    center: { lat: 0, lng: 0 },
    restaurantDetail: {},
    restaurantLatitude: "",
    restaurantLongitude: "",
    restaurantServiceDistance: "",
    isLimitServiceDistance: false,
    setLoading: false,
    updateLoading: false,
    alterLoading: false,
    updated: false,
  }),
  methods: {
    setLocation() {
      this.center = {
        lat: Number(this.restaurantDetail.restaurantLatitude),
        lng: Number(this.restaurantDetail.restaurantLongitude),
      };
    },
    async updateRestaurantServiceDistance(event) {
      const results = await event;

      if (results.valid) {
        this.updateLoading = true;

        await this.getRestaurantDetail();
        this.restaurantDetail.restaurantServiceDistance =
          this.restaurantServiceDistance;

        await this.updateRestaurant(this.restaurantDetail)
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
      }

      this.updateLoading = false;
    },
    async updateRestaurantLimitServiceDistance() {
      this.alterLoading = true;

      await this.getRestaurantDetail();
      this.restaurantDetail.isLimitServiceDistance =
        this.isLimitServiceDistance;

      await this.updateRestaurant(this.restaurantDetail).catch((error) => {
        if (error.response.status === 401) {
          this.$router.push("/Signin");
        }
      });

      this.alterLoading = false;
    },
    async updateRestaurantLocation(event) {
      const results = await event;

      if (results.valid) {
        this.setLoading = true;

        await this.getRestaurantDetail();
        this.restaurantDetail.restaurantLatitude = this.restaurantLatitude;
        this.restaurantDetail.restaurantLongitude = this.restaurantLongitude;

        await this.updateRestaurant(this.restaurantDetail)
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

        this.setLocation();
      }

      this.setLoading = false;
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
      return (
        !isNaN(value) || this.$t("createRestaurant.positiveIntNumberError")
      );
    },
    positiveIntNum(value) {
      const pattern = /^[0-9]*$/;
      return (
        pattern.test(value) ||
        this.$t("createRestaurant.positiveIntNumberError")
      );
    },
    greaterThanZero(value) {
      return (
        value > 0 || this.$t("createRestaurant.numberGreaterThanZeroError")
      );
    },
  },
  async created() {
    await this.getRestaurantDetail();
    this.restaurantLatitude = this.restaurantDetail.restaurantLatitude;
    this.restaurantLongitude = this.restaurantDetail.restaurantLongitude;
    this.isLimitServiceDistance = this.restaurantDetail.isLimitServiceDistance;
    this.restaurantServiceDistance =
      this.restaurantDetail.restaurantServiceDistance;
    this.setLocation();
  },
});
</script>
