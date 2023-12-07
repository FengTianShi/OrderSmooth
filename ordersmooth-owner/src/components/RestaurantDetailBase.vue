<template>
  <h2 class="text-h5 font-weight-black text-primary">
    {{ $t("createRestaurant.title") }}
  </h2>
  <v-divider class="my-6"></v-divider>

  <v-form validate-on="submit lazy" @submit.prevent="updateRestaurantBase">
    <v-row>
      <v-col cols="12" md="8">
        <RestaurantDetailLang :restaurantId="restaurantId" />

        <v-divider class="my-6"></v-divider>

        <v-select
          :label="$t('createRestaurant.genre')"
          class="mb-2"
          density="compact"
          variant="outlined"
          prepend-inner-icon="mdi-apps"
          persistent-hint
          :items="genreList"
          item-title="i18n[0].genreName"
          item-value="genreId"
          v-model="selectedGenre"
          :rules="[required]" />

        <v-text-field
          :label="$t('createRestaurant.restaurantTel')"
          class="mb-2"
          density="compact"
          variant="outlined"
          prepend-inner-icon="mdi-phone"
          persistent-hint
          maxlength="15"
          v-model="restaurantTel"
          :rules="[required, positiveIntNum]" />

        <v-text-field
          :label="$t('createRestaurant.restaurantPostalCode')"
          class="mb-2"
          density="compact"
          variant="outlined"
          prepend-inner-icon="mdi-mailbox-outline"
          persistent-hint
          maxlength="15"
          v-model="restaurantPostalCode"
          :rules="[required, positiveIntNum]" />

        <v-text-field
          label="WIFI SSID"
          class="mb-2"
          density="compact"
          variant="outlined"
          prepend-inner-icon="mdi-wifi"
          persistent-hint
          maxlength="100"
          v-model="wifiSsid"
          :rules="[pair]" />

        <v-text-field
          label="WIFI Password"
          class="mb-2"
          density="compact"
          variant="outlined"
          prepend-inner-icon="mdi-wifi-lock"
          persistent-hint
          maxlength="100"
          v-model="wifiPassword"
          :rules="[pair]" />
      </v-col>
      <v-col cols="12" md="4">
        <RestaurantDetailLogo :restaurantId="restaurantId" />
      </v-col>
    </v-row>

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

  <v-dialog v-model="updated" max-width="500">
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
import RestaurantDetailLogo from "./RestaurantDetailLogo.vue";
import RestaurantDetailLang from "./RestaurantDetailLang.vue";
import { restaurantUtils } from "./common/utils/RestaurantUtils.js";

export default {
  mixins: [restaurantUtils],
  props: {
    restaurantId: {
      required: true,
    },
  },
  components: {
    RestaurantDetailLogo,
    RestaurantDetailLang,
  },
  data: () => ({
    restaurantDetail: {},
    genreList: [],
    selectedGenre: null,
    restaurantTel: "",
    restaurantPostalCode: "",
    wifiSsid: "",
    wifiPassword: "",
    loading: false,
    updated: false,
  }),
  methods: {
    async updateRestaurantBase(event) {
      const results = await event;

      if (results.valid) {
        this.loading = true;

        await this.getRestaurantDetail();
        this.restaurantDetail.genreId = this.selectedGenre;
        this.restaurantDetail.restaurantTel = this.restaurantTel;
        this.restaurantDetail.restaurantPostalCode = this.restaurantPostalCode;
        this.restaurantDetail.wifiSsid = this.wifiSsid;
        this.restaurantDetail.wifiPassword = this.wifiPassword;

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

        this.loading = false;
      }
    },
    required(value) {
      return !!value || this.$t("createRestaurant.requiredError");
    },
    positiveIntNum(value) {
      const pattern = /^[0-9]*$/;
      return (
        pattern.test(value) ||
        this.$t("createRestaurant.positiveIntNumberError")
      );
    },
    pair() {
      return (
        (!!this.wifiPassword && !!this.wifiSsid) ||
        (!this.wifiPassword && !this.wifiSsid) ||
        this.$t("createRestaurant.wifiSsidPasswordPairError")
      );
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
  },
  async created() {
    await this.getRestaurantDetail();

    this.$http
      .get(`/master/restaurant-genre/${this.$i18n.locale}`, {
        headers: {
          Authorization: `Bearer ${JSON.parse(
            window.localStorage.getItem("owner-token")
          )}`,
        },
      })
      .then((response) => {
        if (response.status === 200) {
          this.genreList = response.data;

          this.genreList.forEach((genre) => {
            if (genre.genreId === this.restaurantDetail.genreId) {
              this.selectedGenre = genre.genreId;
            }
          });
        }
      })
      .catch((error) => {
        if (error.response.status === 401) {
          this.$router.push("/Signin");
        }
      });

    this.restaurantTel = this.restaurantDetail.restaurantTel;
    this.restaurantPostalCode = this.restaurantDetail.restaurantPostalCode;
    this.wifiSsid = this.restaurantDetail.wifiSsid;
    this.wifiPassword = this.restaurantDetail.wifiPassword;
  },
};
</script>
