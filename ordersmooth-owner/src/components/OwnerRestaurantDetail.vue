<template>
  <v-layout>
    <OwnerHeader />
    <v-main>
      <v-container fluid class="pa-4">
        <v-card class="mx-auto" elevation="0">
          <v-card class="mx-auto pa-8" max-width="1000" elevation="0">
            <v-row>
              <v-col cols="12" md="3">
                <v-list class="pa-0">
                  <v-list-item
                    v-for="(item, i) in items"
                    :key="i"
                    :value="item"
                    color="primary">
                    <template v-slot:prepend>
                      <v-icon :icon="item.icon"></v-icon>
                    </template>

                    <v-list-item-title v-text="item.text"></v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-col>
              <v-col cols="12" md="9">
                <h2 class="text-h4 font-weight-black text-orange">
                  {{ $t("createRestaurant.title") }}
                </h2>
                <p class="text-body-2 mb-4">
                  {{ $t("createRestaurant.subtitle") }}
                </p>
                <v-divider class="my-6"></v-divider>

                <v-row>
                  <v-col cols="12" md="8">
                    <!-- <v-select
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
                      :rules="[required]" /> -->
                  </v-col>
                  <v-col cols="12" md="4">
                    <OwnerUploadRestaurantLogo
                      logoSrc="https://cdn.vuetifyjs.com/images/john.jpg" />
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
              </v-col>
            </v-row>
          </v-card>
        </v-card>
      </v-container>
    </v-main>
  </v-layout>
</template>

<script>
import OwnerHeader from "./common/OwnerHeader.vue";
import OwnerUploadRestaurantLogo from "./OwnerUploadRestaurantLogo.vue";

export default {
  components: {
    OwnerHeader,
    OwnerUploadRestaurantLogo,
  },
  data: () => ({
    items: [
      { text: "基本", icon: "mdi-clock" },
      { text: "多语言", icon: "mdi-clock" },
      { text: "营业时间", icon: "mdi-flag" },
      { text: "媒体", icon: "mdi-account" },
      { text: "支付", icon: "mdi-flag" },
      { text: "打印机", icon: "mdi-flag" },
      { text: "安全", icon: "mdi-flag" },
      { text: "控制", icon: "mdi-flag" },
    ],
    loading: false,
  }),
};
</script>
