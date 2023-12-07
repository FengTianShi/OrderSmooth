<template>
  <h2 class="text-h5 font-weight-black text-primary">
    Restaurant Opening Hours
  </h2>
  <v-divider class="my-6"></v-divider>

  <v-form
    validate-on="submit lazy"
    @submit.prevent="updateRestaurantOpeningHours">
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
      :label="$t('createRestaurant.openTime')"
      placeholder="9:00"
      class="mb-2"
      density="compact"
      variant="outlined"
      prepend-inner-icon="mdi-clock-time-eight-outline"
      persistent-hint
      maxlength="5"
      v-model="openTime"
      :rules="[added]" />

    <v-text-field
      :label="$t('createRestaurant.closeTime')"
      placeholder="20:00"
      class="mb-2"
      density="compact"
      variant="outlined"
      prepend-inner-icon="mdi-clock-outline"
      persistent-hint
      maxlength="5"
      v-model="closeTime"
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
        <v-icon color="error" left> mdi-alert-circle-outline </v-icon>
        {{ addOpeningHoursError }}
      </div>
    </v-alert>

    <v-card flat>
      <v-card-text>
        <div
          class="mb-6"
          v-for="(
            restaurantOpeningHours, dayInWeekId
          ) in restaurantOpeningHours"
          :key="dayInWeekId">
          <p>
            {{ findDayInWeekName(restaurantOpeningHours.dayInWeekId) }}
          </p>
          <span
            v-for="(
              timeInterval, timeIntervalId
            ) in restaurantOpeningHours.dayInWeekOpeningHours"
            :key="timeIntervalId">
            <v-chip
              size="small"
              class="ma-1"
              @click="removeTimeInterval(dayInWeekId, timeIntervalId)">
              {{ timeInterval.openTime }} ~
              {{ timeInterval.closeTime }}
              <v-icon color="error" right style="font-size: 15px">
                mdi-trash-can-outline
              </v-icon>
            </v-chip>
          </span>
        </div>
      </v-card-text>
    </v-card>

    <v-divider class="mb-6"></v-divider>

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

    restaurantOpeningHours: [],
    dayInWeekList: [],
    selectedDayInWeek: [],
    openTime: "09:00",
    closeTime: "21:00",

    addOpeningHoursError: null,
    showAddOpeningHoursError: false,

    loading: false,
    updated: false,
  }),
  methods: {
    async updateRestaurantOpeningHours(event) {
      const results = await event;

      if (results.valid) {
        this.loading = true;

        this.restaurantDetail.openingHours = [];
        this.restaurantOpeningHours.forEach((item) => {
          item.dayInWeekOpeningHours.forEach((timeInterval) => {
            this.restaurantDetail.openingHours.push({
              dayInWeekId: item.dayInWeekId,
              openTime: timeInterval.openTime,
              closeTime: timeInterval.closeTime,
            });
          });
        });

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

      this.loading = false;
    },
    added() {
      return (
        this.restaurantOpeningHours.length > 0 ||
        this.$t("createRestaurant.addedError")
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

      // check if openTime and closeTime is empty
      if (this.openTime === "" || this.closeTime === "") {
        this.addOpeningHoursError = this.$t(
          "createRestaurant.timeRquiredError"
        );
        this.showAddOpeningHoursError = true;
        return;
      }

      // check if openTime and closeTime is strict HH:mm format p.s. H:mm is not valid
      const timeRegex = /^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/;
      if (!this.openTime.match(timeRegex) || !this.closeTime.match(timeRegex)) {
        this.addOpeningHoursError = this.$t("createRestaurant.timeFormatError");
        this.showAddOpeningHoursError = true;
        return;
      }

      // check if openTime is before closeTime
      const openTime = this.openTime.split(":");
      const closeTime = this.closeTime.split(":");
      if (
        openTime[0] > closeTime[0] ||
        (openTime[0] === closeTime[0] && openTime[1] >= closeTime[1])
      ) {
        this.addOpeningHoursError = this.$t("createRestaurant.timeRangeError");
        this.showAddOpeningHoursError = true;
        return;
      }

      // check if openTime and closeTime is not overlap in same day
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
              openTime: this.openTime,
              closeTime: this.closeTime,
            });
          } else {
            this.restaurantOpeningHours.push({
              dayInWeekId: dayInWeekId,
              dayInWeekOpeningHours: [
                {
                  timeIntervalId: 1,
                  openTime: this.openTime,
                  closeTime: this.closeTime,
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

      // sort by timeInterval openTime
      this.restaurantOpeningHours.forEach((item) => {
        item.dayInWeekOpeningHours.sort((a, b) => {
          const openTimeA = a.openTime.split(":");
          const openTimeB = b.openTime.split(":");
          if (openTimeA[0] > openTimeB[0]) {
            return 1;
          } else if (openTimeA[0] < openTimeB[0]) {
            return -1;
          } else {
            if (openTimeA[1] > openTimeB[1]) {
              return 1;
            } else if (openTimeA[1] < openTimeB[1]) {
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
              const timeIntervalopenTime = new Date();
              timeIntervalopenTime.setHours(
                Number(timeInterval.openTime.split(":")[0]),
                Number(timeInterval.openTime.split(":")[1])
              );
              const timeIntervalcloseTime = new Date();
              timeIntervalcloseTime.setHours(
                Number(timeInterval.closeTime.split(":")[0]),
                Number(timeInterval.closeTime.split(":")[1])
              );
              const newopenTime = new Date();
              newopenTime.setHours(
                Number(this.openTime.split(":")[0]),
                Number(this.openTime.split(":")[1])
              );
              const newcloseTime = new Date();
              newcloseTime.setHours(
                Number(this.closeTime.split(":")[0]),
                Number(this.closeTime.split(":")[1])
              );
              if (
                (newopenTime >= timeIntervalopenTime &&
                  newopenTime <= timeIntervalcloseTime) ||
                (newcloseTime >= timeIntervalopenTime &&
                  newcloseTime <= timeIntervalcloseTime)
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
    this.$http
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

    await this.getRestaurantDetail();

    this.restaurantDetail.openingHours.forEach((item) => {
      if (
        this.restaurantOpeningHours.some(
          (restaurantOpeningHours) =>
            restaurantOpeningHours.dayInWeekId === item.dayInWeekId
        )
      ) {
        this.restaurantOpeningHours[
          this.restaurantOpeningHours.findIndex(
            (restaurantOpeningHours) =>
              restaurantOpeningHours.dayInWeekId === item.dayInWeekId
          )
        ].dayInWeekOpeningHours.push({
          timeIntervalId:
            this.restaurantOpeningHours[
              this.restaurantOpeningHours.findIndex(
                (restaurantOpeningHours) =>
                  restaurantOpeningHours.dayInWeekId === item.dayInWeekId
              )
            ].dayInWeekOpeningHours.length + 1,
          openTime: item.openTime.slice(0, 5),
          closeTime: item.closeTime.slice(0, 5),
        });
      } else {
        this.restaurantOpeningHours.push({
          dayInWeekId: item.dayInWeekId,
          dayInWeekOpeningHours: [
            {
              timeIntervalId: 1,
              openTime: item.openTime.slice(0, 5),
              closeTime: item.closeTime.slice(0, 5),
            },
          ],
        });
      }
    });
  },
};
</script>
