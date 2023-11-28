<template>
  <v-layout>
    <OwnerHeader />

    <v-main>
      <v-container fluid class="pa-4">
        <v-card class="mx-auto" elevation="0">
          <v-card class="mx-auto pa-8" elevation="0" max-width="1400">
            <h2 class="text-h4 font-weight-black text-orange">店舗を追加!</h2>

            <v-form
              validate-on="submit lazy"
              @submit.prevent="createRestaurant">
              <v-row>
                <v-col cols="12" md="4">
                  <p class="text-body-2 mb-4">
                    店舗の基本情報を入力してください。
                  </p>

                  <v-divider class="mb-6"></v-divider>
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
                  <p class="text-body-2 mb-4">
                    店舗の基本情報を入力してください。
                  </p>
                  <v-divider class="mb-6"></v-divider>
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

                  <p class="text-body-2 mb-4">
                    店舗の基本情報を入力してください。
                  </p>
                  <v-divider class="mb-6"></v-divider>
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

                  <v-switch label="Switch" color="primary" inset></v-switch>
                </v-col>
                <v-col cols="12" md="4">
                  <v-btn block color="primary"> 時間帯追加 </v-btn>
                </v-col>
              </v-row>

              <div class="text-right">
                <v-btn
                  width="200"
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
