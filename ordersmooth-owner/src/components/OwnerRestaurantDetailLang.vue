<template>
  {{ restaurantDetail.i18n }}
  {{ selectedLangKey }}

  <v-card
    class="mb-4 pa-2"
    variant="tonal"
    v-for="(langInfo, key) in restaurantDetail.i18n"
    :key="key">
    <v-card-item>
      <v-card-title> {{ langInfo.restaurantName }} </v-card-title>
      <v-card-subtitle> {{ langInfo.langCode }} </v-card-subtitle>
    </v-card-item>

    <v-card-text>
      <p class="text-grey">Address</p>
      <p>
        {{ langInfo.restaurantAddress }}
      </p>
      <br />
      <p class="text-grey">Des</p>
      <p>
        {{ langInfo.restaurantDescription }}
      </p>
    </v-card-text>

    <v-card-actions>
      <v-btn color="warning" @click="(edit = true), editLang(key)">Edit</v-btn>
      <v-spacer></v-spacer>
      <v-btn color="error" @click="deleteLangConfirm(key)">Delete</v-btn>
    </v-card-actions>
  </v-card>

  <v-btn
    class="mb-4"
    block
    color="primary"
    prepend-icon="mdi-translate"
    variant="tonal"
    @click="addLang">
    Add support language
  </v-btn>

  <v-dialog persistent v-model="edit" max-width="500">
    <v-form validate-on="submit lazy" @submit.prevent="saveLang">
      <v-card class="pa-4">
        <v-row>
          <v-col cols="10">
            <p>Edit</p>
          </v-col>
          <v-col cols="2" class="text-right">
            <v-btn
              icon="mdi-close"
              variant="text"
              size="x-small"
              @click="(edit = false), resetForm()" />
          </v-col>
        </v-row>

        <v-divider class="my-2"></v-divider>

        <v-card class="pa-4">
          <div v-if="selectedLangKey === -1">
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
              :rules="[required, langCodeDuplicate]" />
          </div>

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
            density="compact"
            variant="outlined"
            prepend-inner-icon="mdi-text-box-outline"
            persistent-hint
            maxlength="2000"
            v-model="restaurantDescription"
            :rules="[required]" />
        </v-card>

        <v-divider class="mb-2"></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="error" @click="(edit = false), resetForm()">
            Cancel
          </v-btn>
          <v-btn color="primary" type="submit">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-form>
  </v-dialog>

  <v-dialog persistent v-model="deleteConfirm" max-width="500">
    <v-card class="pa-4">
      <v-row>
        <v-col cols="10">
          <p>Are you Sure?</p>
        </v-col>
        <v-col cols="2" class="text-right">
          <v-btn
            icon="mdi-close"
            variant="text"
            size="x-small"
            @click="(deleteConfirm = false), resetForm()" />
        </v-col>
      </v-row>

      <v-divider class="my-2"></v-divider>

      <p>Are you Sure?</p>

      <v-divider class="mb-2"></v-divider>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="error" @click="(deleteConfirm = false), resetForm()">
          Cancel
        </v-btn>
        <v-btn color="error" @click="deleteLang()">Delete</v-btn>
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

    langList: [],
    selectedLang: null,

    selectedLangKey: null,

    edit: false,
    restaurantName: "",
    restaurantAddress: "",
    restaurantDescription: "",

    deleteConfirm: false,

    loading: false,
  }),
  methods: {
    resetForm() {
      this.restaurantName = "";
      this.restaurantAddress = "";
      this.restaurantDescription = "";
    },
    addLang() {
      this.edit = true;
      this.selectedLangKey = -1;
    },
    editLang(key) {
      this.restaurantName = this.restaurantDetail.i18n[key].restaurantName;
      this.restaurantAddress =
        this.restaurantDetail.i18n[key].restaurantAddress;
      this.restaurantDescription =
        this.restaurantDetail.i18n[key].restaurantDescription;
      this.selectedLangKey = key;
    },
    async saveLang(event) {
      const results = await event;
      if (results.valid) {
        this.loading = true;

        if (this.selectedLangKey === -1) {
          this.restaurantDetail.i18n.push({
            langCode: this.selectedLang,
            restaurantName: this.restaurantName,
            restaurantAddress: this.restaurantAddress,
            restaurantDescription: this.restaurantDescription,
          });
        } else {
          this.restaurantDetail.i18n[this.selectedLangKey].restaurantName =
            this.restaurantName;
          this.restaurantDetail.i18n[this.selectedLangKey].restaurantAddress =
            this.restaurantAddress;
          this.restaurantDetail.i18n[
            this.selectedLangKey
          ].restaurantDescription = this.restaurantDescription;
        }

        await this.updateRestaurantDetail();

        this.loading = false;
        this.edit = false;
        this.resetForm();
      }
    },
    deleteLangConfirm(key) {
      this.deleteConfirm = true;
      this.selectedLangKey = key;
    },
    async deleteLang() {
      this.loading = true;
      this.restaurantDetail.i18n.splice(this.selectedLangKey, 1);
      await this.updateRestaurantDetail();
      this.loading = false;
      this.deleteConfirm = false;
      this.resetForm();
    },
    required(value) {
      return !!value || this.$t("createRestaurant.requiredError");
    },
    langCodeDuplicate(value) {
      return (
        !this.restaurantDetail.i18n.some(
          (item) =>
            item.langCode === value && item.langCode !== this.selectedLangKey
        ) || "Duplicate lang code"
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
    async getLangMaster() {
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
    },
    async updateRestaurantDetail() {
      console.log(this.restaurantDetail.i18n);
    },
  },
  created() {
    this.getLangMaster();
    this.getRestaurantDetail();
  },
};
</script>
