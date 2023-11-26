<template>
  <v-layout>
    <OwnerHeader />

    <v-main>
      <v-container fluid class="pa-4">
        <v-card class="mx-auto" elevation="0">
          <v-tabs
            v-model="tab"
            color="deep-purple-accent-4"
            align-tabs="center">
            <v-tab value="1">基本情報</v-tab>
            <v-tab value="2">営業時間</v-tab>
            <v-tab value="3">メディア</v-tab>
          </v-tabs>
          <v-window v-model="tab">
            <v-window-item value="1">
              <v-card class="mx-auto pa-8" elevation="0" max-width="600">
                <v-form validate-on="submit lazy" @submit.prevent="">
                  <v-select
                    label="ジャンル"
                    placeholder="ジャンル"
                    class="mb-2"
                    density="compact"
                    variant="outlined"
                    prepend-inner-icon="mdi-store"
                    hint="www.example.com/page"
                    persistent-hint
                    :items="[
                      'California',
                      'Colorado',
                      'Florida',
                      'Georgia',
                      'Texas',
                      'Wyoming',
                    ]" />

                  <v-text-field
                    maxlength="100"
                    label="店舗名"
                    placeholder="店舗名"
                    class="mb-2"
                    density="compact"
                    variant="outlined"
                    prepend-inner-icon="mdi-store" />

                  <v-file-input
                    class="mb-2"
                    density="compact"
                    variant="outlined"
                    label="店舗LOGO"
                    prepend-icon=""
                    prepend-inner-icon="mdi-image" />

                  <v-file-input
                    class="mb-2"
                    density="compact"
                    variant="outlined"
                    label="店舗宣伝図"
                    prepend-icon=""
                    prepend-inner-icon="mdi-image" />

                  <v-text-field
                    v-model="ownerName"
                    maxlength="100"
                    class="mb-2"
                    width="600"
                    density="compact"
                    variant="outlined"
                    placeholder="電話番号"
                    :rules="[required]" />

                  <v-text-field
                    v-model="ownerName"
                    maxlength="100"
                    class="mb-2"
                    width="600"
                    density="compact"
                    variant="outlined"
                    placeholder="郵便番号"
                    :rules="[required]" />

                  <v-text-field
                    maxlength="100"
                    class="mb-2"
                    density="compact"
                    prepend-inner-icon="mdi-store-marker"
                    variant="outlined"
                    label="アクセス"
                    placeholder="アクセス" />

                  <v-textarea
                    rows="3"
                    v-model="ownerName"
                    maxlength="100"
                    class="mb-2"
                    density="compact"
                    variant="outlined"
                    label="店舗紹介文"
                    placeholder="店舗紹介文"
                    :rules="[required]" />

                  <div class="text-right">
                    <v-btn
                      width="230"
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
            </v-window-item>
            <v-window-item value="2"> 123 </v-window-item>
            <v-window-item value="3">
              <OwnerSignin></OwnerSignin>
            </v-window-item>
          </v-window>
        </v-card>
      </v-container>
    </v-main>
  </v-layout>
</template>

<script>
import OwnerHeader from "./common/OwnerHeader.vue";
import OwnerSignin from "./OwnerSignin.vue";

export default {
  data: () => ({
    tab: null,
  }),
  components: {
    OwnerHeader,
    OwnerSignin,
  },
  created: function () {},
  methods: {},
};
</script>
