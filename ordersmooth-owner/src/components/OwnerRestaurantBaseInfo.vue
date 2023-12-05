<template>
  <h2 class="text-h4 font-weight-black text-orange">
    {{ $t("createRestaurant.title") }}
  </h2>
  <v-divider class="my-6"></v-divider>

  <v-form validate-on="submit lazy" @submit.prevent="updateRestaurantBaseInfo">
    <v-row>
      <v-col cols="12" md="8">
        <v-card class="mb-4 pa-2" variant="tonal">
          <v-card-item>
            <v-card-title> Restaurant Name </v-card-title>
            <v-card-subtitle> English </v-card-subtitle>
          </v-card-item>

          <v-card-text>
            <p class="text-grey">Address</p>
            <p>
              Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
              eiusmod tempor incididunt ut labore et dolore magna aliqua.
            </p>
            <br />
            <p class="text-grey">Des</p>
            <p>
              Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
              eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut
              enim ad minim veniam, quis nostrud exercitation ullamco laboris
              nisi ut aliquip ex ea commodo consequat.
            </p>
          </v-card-text>

          <v-card-actions>
            <v-btn color="warning">Edit</v-btn>
            <v-spacer></v-spacer>
            <v-btn color="error">Delete</v-btn>
          </v-card-actions>
        </v-card>

        <v-btn
          class="mb-4"
          block
          color="primary"
          prepend-icon="mdi-translate"
          variant="tonal">
          Add support language
        </v-btn>

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
      </v-col>
      <v-col cols="12" md="4">
        <OwnerUploadRestaurantLogo :restaurantId="restaurantId" />
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
import OwnerUploadRestaurantLogo from "./OwnerUploadRestaurantLogo.vue";

export default {
  props: {
    restaurantId: {
      required: true,
    },
  },
  components: {
    OwnerUploadRestaurantLogo,
  },
  data: () => ({}),
  methods: {
    updateRestaurantBaseInfo() {},
  },
};
</script>
