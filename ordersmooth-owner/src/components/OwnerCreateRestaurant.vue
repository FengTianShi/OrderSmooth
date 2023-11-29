<template>
  <v-layout>
    <OwnerHeader />

    <v-main>
      <v-container fluid class="pa-4">
        <v-card class="mx-auto" elevation="0">
          <v-card class="mx-auto pa-8" elevation="0" max-width="1200">
            <h2 class="text-h4 font-weight-black text-orange">店舗を追加!</h2>
            <p class="text-body-2 mb-4">店舗の基本情報を入力してください。</p>
            <v-divider class="mb-6"></v-divider>

            <v-form validate-on="submit lazy" submit.prevent="createRestaurant">
              <v-row>
                <v-col cols="12" md="4">
                  <v-select
                    label="ジャンル"
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
                    label="言語"
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
                    label="店舗名"
                    class="mb-2"
                    density="compact"
                    variant="outlined"
                    prepend-inner-icon="mdi-store"
                    persistent-hint
                    maxlength="100"
                    v-model="restaurantName"
                    :rules="[required]" />

                  <v-text-field
                    label="電話番号"
                    class="mb-2"
                    density="compact"
                    variant="outlined"
                    prepend-inner-icon="mdi-phone"
                    persistent-hint
                    maxlength="50"
                    v-model="restaurantTel"
                    :rules="[required]" />

                  <v-text-field
                    label="郵便番号"
                    class="mb-2"
                    density="compact"
                    variant="outlined"
                    prepend-inner-icon="mdi-mailbox-outline"
                    persistent-hint
                    maxlength="50"
                    v-model="restaurantPost"
                    :rules="[required]" />

                  <v-text-field
                    label="住所"
                    class="mb-2"
                    density="compact"
                    variant="outlined"
                    prepend-inner-icon="mdi-store-marker"
                    persistent-hint
                    maxlength="200"
                    v-model="restaurantAddress"
                    :rules="[required]" />

                  <v-textarea
                    label="店舗紹介文"
                    rows="5"
                    class="mb-2"
                    density="compact"
                    variant="outlined"
                    prepend-inner-icon="mdi-text-box-outline"
                    persistent-hint
                    maxlength="500"
                    v-model="restaurantIntroduction"
                    :rules="[required]" />
                </v-col>

                <v-col cols="12" md="4">
                  <v-select
                    label="通貨"
                    class="mb-2"
                    density="compact"
                    variant="outlined"
                    prepend-inner-icon="mdi-cash-multiple"
                    persistent-hint
                    :items="currencyList"
                    :item-props="currencyItemProps"
                    v-model="selectedCurrency"
                    :rules="[required]" />

                  <v-select
                    label="支払方法"
                    class="mb-2"
                    density="compact"
                    variant="outlined"
                    prepend-inner-icon="mdi-cash-multiple"
                    persistent-hint
                    multiple
                    :items="payMethodList"
                    item-title="i18n[0].payMethodName"
                    item-value="payMethodId"
                    v-model="selectedpayMethod"
                    :rules="[selected]" />

                  <v-text-field
                    label="Service fee"
                    placeholder="%"
                    class="mb-2"
                    density="compact"
                    variant="outlined"
                    prepend-inner-icon="mdi-cash"
                    append-inner-icon="mdi-percent-outline"
                    persistent-hint
                    maxlength="3"
                    v-model="restaurantAddress"
                    :rules="[required]" />

                  <v-text-field
                    label="Tex"
                    placeholder="%"
                    class="mb-2"
                    density="compact"
                    variant="outlined"
                    prepend-inner-icon="mdi-cash"
                    append-inner-icon="mdi-percent-outline"
                    persistent-hint
                    maxlength="3"
                    v-model="restaurantAddress"
                    :rules="[required]" />

                  <v-chip-group filter>
                    <v-chip size="small">表示税金</v-chip>
                    <v-chip size="small">不表示税金</v-chip>
                  </v-chip-group>
                </v-col>

                <v-col cols="12" md="4">
                  <v-select
                    placeholder="曜日"
                    multiple
                    class="mb-2"
                    density="compact"
                    variant="outlined"
                    persistent-hint
                    :items="weekList"
                    item-title="weekName"
                    item-value="weekId"
                    v-model="selectedweek" />

                  <v-text-field
                    placeholder="営業開始時間"
                    class="mb-2"
                    density="compact"
                    variant="outlined"
                    persistent-hint
                    maxlength="3"
                    v-model="restaurantAddress"
                    :rules="[required]" />

                  <v-text-field
                    placeholder="営業終了時間"
                    class="mb-2"
                    density="compact"
                    variant="outlined"
                    persistent-hint
                    maxlength="3"
                    v-model="restaurantAddress"
                    :rules="[required]" />

                  <v-btn
                    block
                    height="40"
                    variant="text"
                    class="mb-4"
                    append-icon="mdi-plus">
                    時間帯追加
                  </v-btn>

                  <div class="text-center mb-6">
                    <div>
                      <span class="mr-1">月曜日</span>
                      <v-chip
                        size="small"
                        v-if="chip"
                        class="ma-1"
                        closable
                        @click:close="chip = false">
                        9:00 ~ 12:00
                      </v-chip>
                    </div>
                    <div>
                      <span class="mr-1">月曜日</span>
                      <v-chip
                        size="small"
                        v-if="chip"
                        class="ma-1"
                        closable
                        @click:close="chip = false">
                        9:00 ~ 12:00
                      </v-chip>
                    </div>
                    <div>
                      <span class="mr-1">月曜日</span>
                      <v-chip
                        size="small"
                        v-if="chip"
                        class="ma-1"
                        closable
                        @click:close="chip = false">
                        9:00 ~ 12:00
                      </v-chip>
                    </div>
                    <div>
                      <span class="mr-1">月曜日</span>
                      <v-chip
                        size="small"
                        v-if="chip"
                        class="ma-1"
                        closable
                        @click:close="chip = false">
                        9:00 ~ 12:00
                      </v-chip>
                    </div>
                    <div>
                      <span class="mr-1">月曜日</span>
                      <v-chip
                        size="small"
                        v-if="chip"
                        class="ma-1"
                        closable
                        @click:close="chip = false">
                        9:00 ~ 12:00
                      </v-chip>
                    </div>
                    <div>
                      <span class="mr-1">月曜日</span>
                      <v-chip
                        size="small"
                        v-if="chip"
                        class="ma-1"
                        closable
                        @click:close="chip = false">
                        9:00 ~ 12:00
                      </v-chip>
                    </div>
                    <div>
                      <span class="mr-1">月曜日</span>
                      <v-chip
                        size="small"
                        v-if="chip"
                        class="ma-1"
                        closable
                        @click:close="chip = false">
                        9:00 ~ 12:00
                      </v-chip>
                    </div>
                  </div>
                </v-col>
              </v-row>

              <v-divider class="mb-6"></v-divider>

              <div class="text-right">
                <v-btn
                  type="submit"
                  color="deep-purple-accent-4"
                  size="large"
                  :disabled="loading"
                  :loading="loading">
                  店舗登録
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
    payMethodList: [],
    weekList: [
      { weekId: 1, weekName: "月曜日" },
      { weekId: 2, weekName: "火曜日" },
      { weekId: 3, weekName: "水曜日" },
      { weekId: 4, weekName: "木曜日" },
      { weekId: 5, weekName: "金曜日" },
      { weekId: 6, weekName: "土曜日" },
      { weekId: 7, weekName: "日曜日" },
    ],
    selectedGenre: null,
    selectedLang: null,
    selectedCurrency: null,
    selectedpayMethod: [],
    restaurantName: "",
    restaurantTel: "",
    restaurantPost: "",
    restaurantAddress: "",
    restaurantIntroduction: "",
    loading: false,
    chip: true,
  }),
  methods: {
    async createRestaurant(event) {
      const results = await event;
      if (results.valid) {
        console.log("OK");
      }
    },
    required(value) {
      return !!value || "必須項目を入力してください";
    },
    selected(value) {
      return value.length > 0 || "必須項目を選択してください";
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
  },
};
</script>
