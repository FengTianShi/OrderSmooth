<template>
  <h2 class="text-h4 font-weight-black text-orange">
    {{ $t("createRestaurant.title") }}
  </h2>
  <v-divider class="my-6"></v-divider>

  <v-form validate-on="submit lazy" @submit.prevent="updateRestaurantBaseInfo">
    <v-row>
      <v-col cols="12" md="8">
        <OwnerRestaurantDetailLang :restaurantId="restaurantId" />

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
          label="WIFI SSID"
          class="mb-2"
          density="compact"
          variant="outlined"
          prepend-inner-icon="mdi-wifi"
          persistent-hint
          maxlength="10"
          v-model="restaurantPostalCode"
          :rules="[required, positiveIntNum]" />

        <v-text-field
          label="WIFI Password"
          class="mb-2"
          density="compact"
          variant="outlined"
          prepend-inner-icon="mdi-wifi-lock"
          persistent-hint
          maxlength="10"
          v-model="restaurantPostalCode"
          :rules="[required, positiveIntNum]" />
      </v-col>
      <v-col cols="12" md="4">
        <OwnerRestaurantDetailLogo :restaurantId="restaurantId" />
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
</template>

<script>
import OwnerRestaurantDetailLogo from "./OwnerRestaurantDetailLogo.vue";
import OwnerRestaurantDetailLang from "./OwnerRestaurantDetailLang.vue";

export default {
  props: {
    restaurantId: {
      required: true,
    },
  },
  components: {
    OwnerRestaurantDetailLogo,
    OwnerRestaurantDetailLang,
  },
  data: () => ({}),
  methods: {
    updateRestaurantBaseInfo() {},
  },
};
</script>
