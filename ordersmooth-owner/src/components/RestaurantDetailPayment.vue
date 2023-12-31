<template>
  <h2 class="text-h5 font-weight-black text-primary">Restaurant Payment</h2>
  <v-divider class="my-6"></v-divider>

  <v-form validate-on="submit lazy" @submit.prevent="updateRestaurantPayment">
    <v-select
      :label="$t('createRestaurant.currency')"
      class="mb-2"
      density="compact"
      variant="outlined"
      prepend-inner-icon="mdi-cash-multiple"
      persistent-hint
      :items="currencyList"
      :item-props="currencyItemProps"
      item-value="currencyId"
      v-model="selectedCurrency"
      :rules="[required]" />

    <v-select
      :label="$t('createRestaurant.payMethod')"
      class="mb-2"
      density="compact"
      variant="outlined"
      prepend-inner-icon="mdi-credit-card"
      persistent-hint
      multiple
      chips
      :items="payMethodList"
      item-title="i18n[0].payMethodName"
      item-value="payMethodId"
      v-model="selectedPayMethod"
      :rules="[selected]" />

    <v-text-field
      :label="$t('createRestaurant.defaultServiceFee')"
      placeholder="0 ~ 99"
      class="mb-2"
      density="compact"
      variant="outlined"
      append-inner-icon="mdi-percent-outline"
      persistent-hint
      maxlength="2"
      v-model="defaultServiceFee"
      :rules="[required, positiveIntNum]" />

    <v-text-field
      :label="$t('createRestaurant.defaultTax')"
      placeholder="0 ~ 99"
      class="mb-2"
      density="compact"
      variant="outlined"
      append-inner-icon="mdi-percent-outline"
      persistent-hint
      maxlength="2"
      v-model="defaultTax"
      :rules="[required, positiveIntNum]" />

    <v-checkbox
      hide-details
      density="compact"
      :label="$t('createRestaurant.isDisplayServiceFee')"
      v-model="isDisplayTax">
    </v-checkbox>
    <v-checkbox
      hide-details
      density="compact"
      :label="$t('createRestaurant.isDisplayTax')"
      v-model="isDisplayServiceFee">
    </v-checkbox>

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
import { restaurantUtils } from "./common/utils/RestaurantUtils.js";

export default {
  mixins: [restaurantUtils],
  props: {
    restaurantId: {
      required: true,
    },
  },
  data: () => ({
    restaurantDetail: {},

    currencyList: [],
    selectedCurrency: null,

    payMethodList: [],
    selectedPayMethod: [],

    defaultServiceFee: "",
    defaultTax: "",
    isDisplayServiceFee: false,
    isDisplayTax: false,

    loading: false,
    updated: false,
  }),
  methods: {
    async updateRestaurantPayment(event) {
      const results = await event;

      if (results.valid) {
        this.loading = true;

        await this.getRestaurantDetail();
        this.restaurantDetail.currencyId = this.selectedCurrency;
        this.restaurantDetail.payMethods = this.selectedPayMethod.map(
          (item) => {
            return {
              payMethodId: item,
            };
          }
        );
        this.restaurantDetail.defaultServiceFee = this.defaultServiceFee;
        this.restaurantDetail.defaultTax = this.defaultTax;
        this.restaurantDetail.isDisplayServiceFee = this.isDisplayServiceFee;
        this.restaurantDetail.isDisplayTax = this.isDisplayTax;

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
    currencyItemProps(item) {
      return {
        title: item.currencySymbol + " " + item.currencyName,
        subtitle: item.currencyCode,
      };
    },
    required(value) {
      return !!value || this.$t("createRestaurant.requiredError");
    },
    selected(value) {
      return value.length > 0 || this.$t("createRestaurant.selectedError");
    },
    positiveIntNum(value) {
      const pattern = /^[0-9]*$/;
      return (
        pattern.test(value) ||
        this.$t("createRestaurant.positiveIntNumberError")
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
      .get(`/master/currency/`, {
        headers: {
          Authorization: `Bearer ${JSON.parse(
            window.localStorage.getItem("owner-token")
          )}`,
        },
      })
      .then((response) => {
        if (response.status === 200) {
          this.currencyList = response.data;
          this.selectedCurrency = this.restaurantDetail.currencyId;
        }
      })
      .catch((error) => {
        if (error.response.status === 401) {
          this.$router.push("/Signin");
        }
      });

    this.$http
      .get(`/master/pay-method/${this.$i18n.locale}`, {
        headers: {
          Authorization: `Bearer ${JSON.parse(
            window.localStorage.getItem("owner-token")
          )}`,
        },
      })
      .then((response) => {
        if (response.status === 200) {
          this.payMethodList = response.data;
          this.selectedPayMethod = this.restaurantDetail.payMethods.map(
            (item) => item.payMethodId
          );
        }
      })
      .catch((error) => {
        if (error.response.status === 401) {
          this.$router.push("/Signin");
        }
      });

    this.defaultServiceFee = this.restaurantDetail.defaultServiceFee;
    this.defaultTax = this.restaurantDetail.defaultTax;
    this.isDisplayServiceFee = this.restaurantDetail.isDisplayServiceFee;
    this.isDisplayTax = this.restaurantDetail.isDisplayTax;
  },
};
</script>
