<template>
  <v-layout>
    <OwnerHeader />

    <v-main>
      <v-container fluid class="pa-4">
        <v-card class="mx-auto" elevation="0">
          <v-card class="mx-auto pa-8" elevation="0" max-width="600">
            <h2 class="text-h4 font-weight-black text-orange">店舗を追加!</h2>
            <p class="text-body-2 mb-4">店舗の基本情報を入力してください。</p>

            <v-divider class="mb-6"></v-divider>

            <v-form
              validate-on="submit lazy"
              @submit.prevent="createRestaurant">
              <v-select
                label="ジャンル"
                placeholder="ジャンル"
                class="mb-2"
                density="compact"
                variant="outlined"
                prepend-inner-icon="mdi-apps"
                persistent-hint
                :items="genreList"
                item-title="genreName"
                item-value="genreId"
                v-model="selectedGenre"
                :rules="[required]" />

              <v-select
                label="デフォルト言語"
                placeholder="ジャンル"
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
                placeholder="店舗名"
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
                placeholder="電話番号"
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
                placeholder="郵便番号"
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
                placeholder="住所"
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
                placeholder="店舗紹介文"
                rows="5"
                class="mb-2"
                density="compact"
                variant="outlined"
                prepend-inner-icon="mdi-text-box-outline"
                persistent-hint
                maxlength="500"
                v-model="restaurantIntroduction"
                :rules="[required]" />

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
    genreList: [],
    langList: [],
    selectedGenre: null,
    selectedLang: null,
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
  },
};
</script>
