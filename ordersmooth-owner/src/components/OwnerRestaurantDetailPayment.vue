<template>
  <h2 class="text-h4 font-weight-black text-orange">Restaurant Payment</h2>
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
export default {
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
              restaurantLongitude: this.restaurantDetail.restaurantLongitude,
              restaurantLatitude: this.restaurantDetail.restaurantLatitude,
              restaurantServiceDistance:
                this.restaurantDetail.restaurantServiceDistance,
              currencyId: this.selectedCurrency,
              payMethodIds: this.selectedPayMethod,
              defaultServiceFee: this.defaultServiceFee,
              defaultTax: this.defaultTax,
              isDisplayServiceFee: this.isDisplayServiceFee,
              isDisplayTax: this.isDisplayTax,
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

    await this.$http
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

    await this.$http
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
