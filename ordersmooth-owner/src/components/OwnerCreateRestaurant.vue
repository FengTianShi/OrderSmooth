<template>
  <v-layout>
    <OwnerHeader />
    <v-main>
      <v-container fluid class="pa-4">
        <v-card class="mx-auto" elevation="0">
          <v-card class="mx-auto pa-8" max-width="1200" elevation="0">
            <h2 class="text-h4 font-weight-black text-orange">
              {{ $t("createRestaurant.title") }}
            </h2>
            <p class="text-body-2 mb-4">
              {{ $t("createRestaurant.subtitle") }}
            </p>
            <v-divider class="my-6"></v-divider>

            <v-form
              validate-on="submit lazy"
              @submit.prevent="createRestaurant">
              <v-row>
                <v-col cols="12" md="4">
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

                  <v-select
                    :label="$t('createRestaurant.lang')"
                    class="mb-2"
                    density="compact"
                    variant="outlined"
                    prepend-inner-icon="mdi-translate"
                    persistent-hint
                    :items="langList"
                    item-title="langName"
                    item-value="langCode"
                    v-model="selectedLang"
                    :rules="[required]" />

                  <v-text-field
                    :label="$t('createRestaurant.restaurantName')"
                    class="mb-2"
                    density="compact"
                    variant="outlined"
                    prepend-inner-icon="mdi-store"
                    persistent-hint
                    maxlength="100"
                    v-model="restaurantName"
                    :rules="[required]" />

                  <v-text-field
                    :label="$t('createRestaurant.restaurantTel')"
                    class="mb-2"
                    density="compact"
                    variant="outlined"
                    prepend-inner-icon="mdi-phone"
                    persistent-hint
                    maxlength="11"
                    v-model="restaurantTel"
                    :rules="[required, positiveIntNum]" />

                  <v-text-field
                    :label="$t('createRestaurant.restaurantPostalCode')"
                    class="mb-2"
                    density="compact"
                    variant="outlined"
                    prepend-inner-icon="mdi-mailbox-outline"
                    persistent-hint
                    maxlength="10"
                    v-model="restaurantPostalCode"
                    :rules="[required, positiveIntNum]" />

                  <v-text-field
                    :label="$t('createRestaurant.restaurantAddress')"
                    class="mb-2"
                    density="compact"
                    variant="outlined"
                    prepend-inner-icon="mdi-store-marker"
                    persistent-hint
                    maxlength="500"
                    v-model="restaurantAddress"
                    :rules="[required]" />

                  <v-textarea
                    :label="$t('createRestaurant.restaurantDescription')"
                    rows="5"
                    class="mb-2"
                    density="compact"
                    variant="outlined"
                    prepend-inner-icon="mdi-text-box-outline"
                    persistent-hint
                    maxlength="2000"
                    v-model="restaurantDescription"
                    :rules="[required]" />
                </v-col>

                <v-col cols="12" md="4">
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
                </v-col>

                <v-col cols="12" md="4">
                  <v-select
                    :label="$t('createRestaurant.dayInWeek')"
                    clearable
                    multiple
                    chips
                    class="mb-2"
                    density="compact"
                    variant="outlined"
                    prepend-inner-icon="mdi-calendar-week"
                    persistent-hint
                    :items="dayInWeekList"
                    item-title="i18n[0].dayInWeekName"
                    item-value="dayInWeekId"
                    v-model="selectedDayInWeek"
                    :rules="[added]" />

                  <v-text-field
                    :label="$t('createRestaurant.openingTime')"
                    placeholder="9:00"
                    class="mb-2"
                    density="compact"
                    variant="outlined"
                    prepend-inner-icon="mdi-clock-time-eight-outline"
                    persistent-hint
                    maxlength="5"
                    v-model="openingTime"
                    :rules="[added]" />

                  <v-text-field
                    :label="$t('createRestaurant.closingTime')"
                    placeholder="20:00"
                    class="mb-2"
                    density="compact"
                    variant="outlined"
                    prepend-inner-icon="mdi-clock-outline"
                    persistent-hint
                    maxlength="5"
                    v-model="closingTime"
                    :rules="[added]" />

                  <v-btn
                    block
                    class="mb-4"
                    height="40"
                    variant="tonal"
                    append-icon="mdi-plus"
                    @click="addOpeningHours">
                    {{ $t("createRestaurant.addTime") }}
                  </v-btn>

                  <v-alert v-model="showAddOpeningHoursError" height="40">
                    <div style="font-size: 12px" class="text-center">
                      <v-icon color="error" left>
                        mdi-alert-circle-outline
                      </v-icon>
                      {{ addOpeningHoursError }}
                    </div>
                  </v-alert>

                  <v-card flat>
                    <v-card-text>
                      <div
                        v-for="(
                          restaurantOpeningHours, dayInWeekId
                        ) in restaurantOpeningHours"
                        :key="dayInWeekId">
                        <p>
                          {{
                            findDayInWeekName(
                              restaurantOpeningHours.dayInWeekId
                            )
                          }}
                        </p>
                        <span
                          v-for="(
                            timeInterval, timeIntervalId
                          ) in restaurantOpeningHours.dayInWeekOpeningHours"
                          :key="timeIntervalId">
                          <v-chip
                            size="small"
                            class="ma-1"
                            @click="
                              removeTimeInterval(dayInWeekId, timeIntervalId)
                            ">
                            {{ timeInterval.openingTime }} ~
                            {{ timeInterval.closingTime }}
                            <v-icon color="error" right style="font-size: 15px">
                              mdi-trash-can-outline
                            </v-icon>
                          </v-chip>
                        </span>
                        <v-divider class="my-3"></v-divider>
                      </div>
                    </v-card-text>
                  </v-card>
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
                  {{ $t("createRestaurant.addRestaurant") }}
                </v-btn>
              </div>
            </v-form>
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
  data: () => ({
    langList: [],
    genreList: [],
    currencyList: [],
    dayInWeekList: [],
    payMethodList: [],

    selectedDayInWeek: [],
    openingTime: "09:00",
    closingTime: "21:00",

    selectedGenre: null,
    selectedLang: null,
    restaurantName: "",
    restaurantTel: "",
    restaurantPostalCode: "",
    restaurantAddress: "",
    restaurantDescription: "",
    selectedCurrency: null,
    selectedPayMethod: [],
    defaultServiceFee: "",
    defaultTax: "",
    isDisplayTax: true,
    isDisplayServiceFee: true,

    restaurantOpeningHours: [],
    addOpeningHoursError: null,
    showAddOpeningHoursError: false,

    loading: false,
  }),
  methods: {
    async createRestaurant(event) {
      const results = await event;

      if (results.valid) {
        this.loading = true;

        await this.$http
          .post(
            "/restaurant",
            {
              genreId: this.selectedGenre,
              restaurantI18n: {
                langCode: this.selectedLang,
                restaurantName: this.restaurantName,
                address: this.restaurantAddress,
                description: this.restaurantDescription,
              },
              tel: this.restaurantTel,
              postalCode: this.restaurantPostalCode,
              currencyId: this.selectedCurrency,
              payMethodIds: this.selectedPayMethod,
              defaultServiceFee: this.defaultServiceFee,
              defaultTax: this.defaultTax,
              isDisplayServiceFee: this.isDisplayServiceFee,
              isDisplayTax: this.isDisplayTax,
              restaurantOpeningHours: this.restaurantOpeningHours,
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
            if (response.status == 201) {
              this.$router.push(`/RestaurantDetail/${response.data}`);
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
    selected(value) {
      return value.length > 0 || this.$t("createRestaurant.selectedError");
    },
    added() {
      return (
        this.restaurantOpeningHours.length > 0 ||
        this.$t("createRestaurant.addedError")
      );
    },
    positiveIntNum(value) {
      const pattern = /^[0-9]*$/;
      return (
        pattern.test(value) ||
        this.$t("createRestaurant.positiveIntNumberError")
      );
    },
    addOpeningHours() {
      // check if selectedDayInWeek is empty
      if (this.selectedDayInWeek.length === 0) {
        this.addOpeningHoursError = this.$t(
          "createRestaurant.dayInWeekRequiredError"
        );
        this.showAddOpeningHoursError = true;
        return;
      }

      // check if openingTime and closingTime is empty
      if (this.openingTime === "" || this.closingTime === "") {
        this.addOpeningHoursError = this.$t(
          "createRestaurant.timeRquiredError"
        );
        this.showAddOpeningHoursError = true;
        return;
      }

      // check if openingTime and closingTime is strict HH:mm format p.s. H:mm is not valid
      const timeRegex = /^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/;
      if (
        !this.openingTime.match(timeRegex) ||
        !this.closingTime.match(timeRegex)
      ) {
        this.addOpeningHoursError = this.$t("createRestaurant.timeFormatError");
        this.showAddOpeningHoursError = true;
        return;
      }

      // check if openingTime is before closingTime
      const openingTime = this.openingTime.split(":");
      const closingTime = this.closingTime.split(":");
      if (
        openingTime[0] > closingTime[0] ||
        (openingTime[0] === closingTime[0] && openingTime[1] >= closingTime[1])
      ) {
        this.addOpeningHoursError = this.$t("createRestaurant.timeRangeError");
        this.showAddOpeningHoursError = true;
        return;
      }

      // check if openingTime and closingTime is not overlap in same day
      if (this.isOverlap()) {
        this.addOpeningHoursError = this.$t(
          "createRestaurant.timeOverlapError"
        );
        this.showAddOpeningHoursError = true;
        return;
      }

      // add new time interval
      if (this.selectedDayInWeek.length > 0) {
        this.selectedDayInWeek.forEach((dayInWeekId) => {
          if (
            this.restaurantOpeningHours.some(
              (item) => item.dayInWeekId === dayInWeekId
            )
          ) {
            this.restaurantOpeningHours[
              this.restaurantOpeningHours.findIndex(
                (item) => item.dayInWeekId === dayInWeekId
              )
            ].dayInWeekOpeningHours.push({
              timeIntervalId:
                this.restaurantOpeningHours[
                  this.restaurantOpeningHours.findIndex(
                    (item) => item.dayInWeekId === dayInWeekId
                  )
                ].dayInWeekOpeningHours.length + 1,
              openingTime: this.openingTime,
              closingTime: this.closingTime,
            });
          } else {
            this.restaurantOpeningHours.push({
              dayInWeekId: dayInWeekId,
              dayInWeekOpeningHours: [
                {
                  timeIntervalId: 1,
                  openingTime: this.openingTime,
                  closingTime: this.closingTime,
                },
              ],
            });
          }
        });
      }

      this.showAddOpeningHoursError = false;

      // sort by dayInWeekId
      this.restaurantOpeningHours.sort((a, b) => {
        return a.dayInWeekId - b.dayInWeekId;
      });

      // sort by timeInterval openingTime
      this.restaurantOpeningHours.forEach((item) => {
        item.dayInWeekOpeningHours.sort((a, b) => {
          const openingTimeA = a.openingTime.split(":");
          const openingTimeB = b.openingTime.split(":");
          if (openingTimeA[0] > openingTimeB[0]) {
            return 1;
          } else if (openingTimeA[0] < openingTimeB[0]) {
            return -1;
          } else {
            if (openingTimeA[1] > openingTimeB[1]) {
              return 1;
            } else if (openingTimeA[1] < openingTimeB[1]) {
              return -1;
            } else {
              return 0;
            }
          }
        });
      });
    },
    isOverlap() {
      var isOverlap = false;
      if (this.restaurantOpeningHours.length > 0) {
        this.restaurantOpeningHours.forEach((item) => {
          if (this.selectedDayInWeek.includes(item.dayInWeekId)) {
            item.dayInWeekOpeningHours.forEach((timeInterval) => {
              const timeIntervalOpeningTime = new Date();
              timeIntervalOpeningTime.setHours(
                Number(timeInterval.openingTime.split(":")[0]),
                Number(timeInterval.openingTime.split(":")[1])
              );
              const timeIntervalClosingTime = new Date();
              timeIntervalClosingTime.setHours(
                Number(timeInterval.closingTime.split(":")[0]),
                Number(timeInterval.closingTime.split(":")[1])
              );
              const newOpeningTime = new Date();
              newOpeningTime.setHours(
                Number(this.openingTime.split(":")[0]),
                Number(this.openingTime.split(":")[1])
              );
              const newClosingTime = new Date();
              newClosingTime.setHours(
                Number(this.closingTime.split(":")[0]),
                Number(this.closingTime.split(":")[1])
              );
              if (
                (newOpeningTime >= timeIntervalOpeningTime &&
                  newOpeningTime <= timeIntervalClosingTime) ||
                (newClosingTime >= timeIntervalOpeningTime &&
                  newClosingTime <= timeIntervalClosingTime)
              ) {
                isOverlap = true;
              }
            });
          }
        });
      }
      return isOverlap;
    },
    removeTimeInterval(dayInWeekId, timeIntervalId) {
      this.restaurantOpeningHours[dayInWeekId].dayInWeekOpeningHours.splice(
        timeIntervalId,
        1
      );
      if (
        this.restaurantOpeningHours[dayInWeekId].dayInWeekOpeningHours
          .length === 0
      ) {
        this.restaurantOpeningHours.splice(dayInWeekId, 1);
      }
    },
    findDayInWeekName(dayInWeekId) {
      switch (dayInWeekId) {
        case 1:
          return this.$t("createRestaurant.monday");
        case 2:
          return this.$t("createRestaurant.tuesday");
        case 3:
          return this.$t("createRestaurant.wednesday");
        case 4:
          return this.$t("createRestaurant.thursday");
        case 5:
          return this.$t("createRestaurant.friday");
        case 6:
          return this.$t("createRestaurant.saturday");
        case 7:
          return this.$t("createRestaurant.sunday");
      }
    },
    currencyItemProps(item) {
      return {
        title: item.currencySymbol + " " + item.currencyName,
        subtitle: item.currencyCode,
      };
    },
  },
  async created() {
    await this.$http
      .get(`/master/language/`, {
        headers: {
          Authorization: `Bearer ${JSON.parse(
            window.localStorage.getItem("owner-token")
          )}`,
        },
      })
      .then((response) => {
        if (response.status === 200) {
          this.langList = response.data;
          if (
            this.langList.some((item) => item.langCode === this.$i18n.locale)
          ) {
            this.selectedLang = this.$i18n.locale;
          }
        }
      })
      .catch((error) => {
        if (error.response.status === 401) {
          this.$router.push("/Signin");
        }
      });

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
        }
      })
      .catch((error) => {
        if (error.response.status === 401) {
          this.$router.push("/Signin");
        }
      });

    await this.$http
      .get(`/master/day-in-week/${this.$i18n.locale}`, {
        headers: {
          Authorization: `Bearer ${JSON.parse(
            window.localStorage.getItem("owner-token")
          )}`,
        },
      })
      .then((response) => {
        if (response.status === 200) {
          this.dayInWeekList = response.data;
        }
      })
      .catch((error) => {
        if (error.response.status === 401) {
          this.$router.push("/Signin");
        }
      });

    await this.$http
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
        }
      })
      .catch((error) => {
        if (error.response.status === 401) {
          this.$router.push("/Signin");
        }
      });
  },
};
</script>
