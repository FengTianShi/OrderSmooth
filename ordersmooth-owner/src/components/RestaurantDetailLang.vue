<template>
  <v-card
    v-for="(langInfo, key) in restaurantDetail.i18ns"
    :key="key"
    class="mb-4 pa-2"
    variant="tonal">
    <v-card-item>
      <v-card-title> {{ langInfo.restaurantName }} </v-card-title>
      <v-card-subtitle> {{ getLangeName(langInfo.langCode) }} </v-card-subtitle>
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

        <v-card class="pa-4 pb-0" flat>
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
              :rules="[required, langDuplicate]" />
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
            class="mb-2"
            rows="5"
            density="compact"
            variant="outlined"
            prepend-inner-icon="mdi-text-box-outline"
            persistent-hint
            maxlength="2000"
            v-model="restaurantDescription"
            :rules="[required]" />
        </v-card>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="error" @click="(edit = false), resetForm()">
            Cancel
          </v-btn>
          <v-btn
            color="primary"
            type="submit"
            :loading="loading"
            :disabled="loading">
            Save
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-form>
  </v-dialog>

  <v-dialog persistent v-model="deleteConfirm" max-width="500">
    <v-card class="pa-4">
      <v-row>
        <v-col cols="10">
          <p>Confirm</p>
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

      <p class="py-4 text-center">
        <v-icon color="warning" size="28">mdi-alert-circle</v-icon>
        Confirm your delete
      </p>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="error" @click="(deleteConfirm = false), resetForm()">
          Cancel
        </v-btn>
        <v-btn
          color="error"
          :loading="loading"
          :disabled="loading"
          @click="deleteLang()">
          Delete
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>

  <v-dialog v-model="deleteError" max-width="500">
    <v-card class="pa-4">
      <v-row>
        <v-col cols="10">
          <p>Error</p>
        </v-col>
        <v-col cols="2" class="text-right">
          <v-btn
            icon="mdi-close"
            variant="text"
            size="x-small"
            @click="deleteError = false" />
        </v-col>
      </v-row>

      <v-divider class="my-2"></v-divider>

      <p class="py-4 text-center">
        <v-icon color="error" size="28">mdi-alert-circle</v-icon>
        you can't delete language when it's only one
      </p>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="error" @click="deleteError = false"> Close </v-btn>
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

    langList: [],
    selectedLang: null,

    selectedLangKey: null,

    edit: false,
    restaurantName: "",
    restaurantAddress: "",
    restaurantDescription: "",

    deleteConfirm: false,
    deleteError: false,

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
      this.selectedLangKey = key;
      this.restaurantName = this.restaurantDetail.i18ns[key].restaurantName;
      this.restaurantAddress =
        this.restaurantDetail.i18ns[key].restaurantAddress;
      this.restaurantDescription =
        this.restaurantDetail.i18ns[key].restaurantDescription;
    },
    async saveLang(event) {
      const results = await event;
      if (results.valid) {
        this.loading = true;

        if (this.selectedLangKey === -1) {
          this.restaurantDetail.i18ns.push({
            langCode: this.selectedLang,
            restaurantName: this.restaurantName,
            restaurantAddress: this.restaurantAddress,
            restaurantDescription: this.restaurantDescription,
          });
        } else {
          this.restaurantDetail.i18ns[this.selectedLangKey].restaurantName =
            this.restaurantName;
          this.restaurantDetail.i18ns[this.selectedLangKey].restaurantAddress =
            this.restaurantAddress;
          this.restaurantDetail.i18ns[
            this.selectedLangKey
          ].restaurantDescription = this.restaurantDescription;
        }

        await this.updateRestaurantLang();

        this.loading = false;
        this.edit = false;
        this.resetForm();
      }
    },
    deleteLangConfirm(key) {
      if (this.restaurantDetail.i18ns.length === 1) {
        this.deleteError = true;
        return;
      }
      this.selectedLangKey = key;
      this.deleteConfirm = true;
    },
    async deleteLang() {
      this.loading = true;

      this.restaurantDetail.i18ns.splice(this.selectedLangKey, 1);
      await this.updateRestaurantLang();

      this.loading = false;
      this.deleteConfirm = false;
      this.resetForm();
    },
    required(value) {
      return !!value || this.$t("createRestaurant.requiredError");
    },
    langDuplicate(value) {
      return (
        !this.restaurantDetail.i18ns.some(
          (item) =>
            item.langCode === value && item.langCode !== this.selectedLangKey
        ) || "Duplicate lang code"
      );
    },
    getLangeName(langCode) {
      const lang = this.langList.find((item) => item.langCode === langCode);
      return lang ? lang.langName : "";
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
    getLangMaster() {
      this.$http
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
    async updateRestaurantLang() {
      await this.updateRestaurant(this.restaurantDetail).catch((error) => {
        if (error.response.status === 401) {
          this.$router.push("/Signin");
        }
      });
    },
  },
  async created() {
    this.getLangMaster();
    await this.getRestaurantDetail();
  },
};
</script>
