<template>
  <v-layout>
    <OwnerHeader />

    <v-main>
      <v-container fluid class="pa-3">
        <v-form validate-on="submit lazy" @submit.prevent="">
          <v-card class="mx-auto pa-8 ma-3" elevation="2" max-width="1000">
            <v-row>
              <v-col class="pa-1" cols="12" md="12">
                <p class="text-h6">店舗追加</p>
              </v-col>
            </v-row>

            <v-row>
              <v-col class="pa-1" cols="12" md="12">
                <v-divider class="my-6"></v-divider>
              </v-col>
            </v-row>

            <v-row>
              <v-col class="pa-1" cols="12" md="3">
                <v-select
                  class="mb-6"
                  density="compact"
                  variant="outlined"
                  multiple
                  label="言語を選択"
                  hint="多言語を対応する"
                  persistent-hint
                  v-model="selectedLang"
                  :items="lang"></v-select>
              </v-col>
            </v-row>

            <v-row>
              <v-col class="pa-1" cols="12" md="12">
                <v-card class="mx-auto mb-8" elevation="1">
                  <v-card-item class="bg-grey-lighten-2">
                    <p>日本語</p>
                    <template v-slot:append>
                      <v-btn
                        size="small"
                        variant="text"
                        icon="mdi-close"></v-btn>
                    </template>
                  </v-card-item>

                  <div class="pa-8 pb-4">
                    <v-row>
                      <v-col class="pa-1" cols="12" md="4">
                        <v-text-field
                          label="店舗名"
                          maxlength="100"
                          class="mb-2"
                          density="compact"
                          variant="outlined"
                          prepend-inner-icon="mdi-store"
                          placeholder="店舗名" />
                      </v-col>

                      <v-col class="pa-1" cols="12" md="8">
                        <v-text-field
                          maxlength="100"
                          class="mb-2"
                          density="compact"
                          prepend-inner-icon="mdi-store-marker"
                          variant="outlined"
                          label="アクセス"
                          placeholder="アクセス" />
                      </v-col>
                    </v-row>

                    <v-row>
                      <v-col class="pa-1" cols="12" md="12">
                        <v-textarea
                          rows="2"
                          v-model="ownerName"
                          maxlength="100"
                          class="mb-2"
                          density="compact"
                          variant="outlined"
                          label="店舗紹介文"
                          placeholder="店舗紹介文"
                          :rules="[required]" />
                      </v-col>
                    </v-row>
                  </div>
                </v-card>
              </v-col>
            </v-row>

            <v-row>
              <v-col class="pa-1" cols="12" md="12">
                <v-card class="mx-auto mb-8" elevation="1">
                  <v-card-item class="bg-grey-lighten-2">
                    <p>English</p>
                    <template v-slot:append>
                      <v-btn
                        size="small"
                        variant="text"
                        icon="mdi-close"></v-btn>
                    </template>
                  </v-card-item>

                  <div class="pa-8 pb-4">
                    <v-row>
                      <v-col class="pa-1" cols="12" md="4">
                        <v-text-field
                          label="店舗名"
                          maxlength="100"
                          class="mb-2"
                          density="compact"
                          variant="outlined"
                          prepend-inner-icon="mdi-store"
                          placeholder="店舗名" />
                      </v-col>

                      <v-col class="pa-1" cols="12" md="8">
                        <v-text-field
                          maxlength="100"
                          class="mb-2"
                          density="compact"
                          prepend-inner-icon="mdi-store-marker"
                          variant="outlined"
                          label="アクセス"
                          placeholder="アクセス" />
                      </v-col>
                    </v-row>

                    <v-row>
                      <v-col class="pa-1" cols="12" md="12">
                        <v-textarea
                          rows="2"
                          v-model="ownerName"
                          maxlength="100"
                          class="mb-2"
                          density="compact"
                          variant="outlined"
                          label="店舗紹介文"
                          placeholder="店舗紹介文"
                          :rules="[required]" />
                      </v-col>
                    </v-row>
                  </div>
                </v-card>
              </v-col>
            </v-row>

            <v-row>
              <v-col class="pa-1" cols="12" md="12">
                <v-select
                  class="mb-2"
                  density="compact"
                  variant="outlined"
                  label="ジャンル"
                  placeholder="ジャンル"
                  :items="[
                    'California',
                    'Colorado',
                    'Florida',
                    'Georgia',
                    'Texas',
                    'Wyoming',
                  ]"></v-select>
              </v-col>
            </v-row>

            <v-row>
              <v-col class="pa-1" cols="12" md="6">
                <v-text-field
                  v-model="ownerName"
                  maxlength="100"
                  class="mb-2"
                  width="600"
                  density="compact"
                  variant="outlined"
                  placeholder="郵便番号"
                  :rules="[required]" />
              </v-col>
              <v-col class="pa-1" cols="12" md="6">
                <v-text-field
                  v-model="ownerName"
                  maxlength="100"
                  class="mb-2"
                  width="600"
                  density="compact"
                  variant="outlined"
                  placeholder="電話番号"
                  :rules="[required]" />
              </v-col>
            </v-row>

            <v-row>
              <v-col class="pa-1" cols="12" md="6">
                <v-file-input
                  class="mb-2"
                  density="compact"
                  variant="outlined"
                  label="店舗LOGO"
                  prepend-icon=""
                  prepend-inner-icon="mdi-image"></v-file-input>
              </v-col>

              <v-col class="pa-1" cols="12" md="6">
                <v-file-input
                  class="mb-2"
                  density="compact"
                  variant="outlined"
                  label="店舗宣伝図"
                  prepend-icon=""
                  prepend-inner-icon="mdi-image"></v-file-input>
              </v-col>
            </v-row>

            <v-row>
              <v-col class="pa-1" cols="12" md="12">
                <v-btn
                  size="small"
                  variant="text"
                  icon="mdi-plus"
                  class="mb-2"></v-btn>
              </v-col>
            </v-row>

            <v-row>
              <v-col class="pa-1" cols="12" md="4">
                <v-text-field
                  v-model="ownerName"
                  maxlength="100"
                  class="mb-2"
                  width="600"
                  density="compact"
                  variant="outlined"
                  placeholder="サービス開始時間"
                  :rules="[required]" />
              </v-col>
              <v-col class="pa-1" cols="12" md="4">
                <v-text-field
                  v-model="ownerName"
                  maxlength="100"
                  class="mb-2"
                  width="600"
                  density="compact"
                  variant="outlined"
                  placeholder="サービス終了時間"
                  :rules="[required]" />
              </v-col>
              <v-col class="pa-1" cols="12" md="4">
                <v-btn size="small" variant="text" icon="mdi-close"></v-btn>
              </v-col>
            </v-row>

            <v-row>
              <v-col class="pa-1" cols="12" md="4">
                <v-text-field
                  v-model="ownerName"
                  maxlength="100"
                  class="mb-2"
                  width="600"
                  density="compact"
                  variant="outlined"
                  placeholder="サービス開始時間"
                  :rules="[required]" />
              </v-col>

              <v-col class="pa-1" cols="12" md="4">
                <v-text-field
                  v-model="ownerName"
                  maxlength="100"
                  class="mb-2"
                  width="600"
                  density="compact"
                  variant="outlined"
                  placeholder="サービス終了時間"
                  :rules="[required]" />
              </v-col>
              <v-col class="pa-1" cols="12" md="4">
                <v-btn size="small" variant="text" icon="mdi-close"></v-btn>
              </v-col>
            </v-row>

            <v-row>
              <v-col class="pa-1" cols="12" md="4">
                <v-text-field
                  v-model="ownerName"
                  maxlength="100"
                  class="mb-2"
                  width="600"
                  density="compact"
                  variant="outlined"
                  placeholder="位置情報 (緯度)  例35.6812362"
                  :rules="[required]" />
              </v-col>

              <v-col class="pa-1" cols="12" md="4">
                <v-text-field
                  v-model="ownerName"
                  maxlength="100"
                  class="mb-2"
                  width="600"
                  density="compact"
                  variant="outlined"
                  placeholder="位置情報 (緯度) 例139.7671248"
                  :rules="[required]" />
              </v-col>

              <v-col class="pa-1" cols="12" md="4">
                <v-text-field
                  v-model="ownerName"
                  maxlength="100"
                  class="mb-2"
                  width="600"
                  density="compact"
                  variant="outlined"
                  placeholder="位置情報 (緯度) 例139.7671248"
                  :rules="[required]" />
              </v-col>

              <v-col class="pa-1" cols="12" md="4">
                <v-text-field
                  v-model="ownerName"
                  maxlength="100"
                  class="mb-2"
                  width="600"
                  density="compact"
                  variant="outlined"
                  placeholder="サービス距離"
                  :rules="[required]" />
              </v-col>
            </v-row>

            <v-row>
              <v-col class="pa-1" cols="12" md="4">
                <v-text-field
                  v-model="ownerName"
                  maxlength="100"
                  class="mb-2"
                  width="600"
                  density="compact"
                  variant="outlined"
                  placeholder="定休日"
                  :rules="[required]" />
              </v-col>
            </v-row>

            <v-row>
              <v-col class="pa-1" cols="12" md="4">
                <v-text-field
                  v-model="ownerName"
                  maxlength="100"
                  class="mb-2"
                  width="600"
                  density="compact"
                  variant="outlined"
                  placeholder="プリンター連携"
                  :rules="[required]" />
              </v-col>
            </v-row>

            <v-row>
              <v-col class="pa-1" cols="12" md="3">
                <v-text-field
                  v-model="ownerName"
                  maxlength="100"
                  class="mb-2"
                  width="600"
                  density="compact"
                  variant="outlined"
                  placeholder="通貨単位"
                  :rules="[required]" />
              </v-col>

              <v-col class="pa-1" cols="12" md="3">
                <v-text-field
                  v-model="ownerName"
                  maxlength="100"
                  class="mb-2"
                  width="600"
                  density="compact"
                  variant="outlined"
                  placeholder="金額表示"
                  :rules="[required]" />
              </v-col>
            </v-row>

            <v-row>
              <v-col class="pa-1" cols="12" md="4">
                <v-text-field
                  v-model="ownerName"
                  maxlength="100"
                  class="mb-2"
                  width="600"
                  density="compact"
                  variant="outlined"
                  placeholder="サービス料"
                  :rules="[required]" />
              </v-col>

              <v-col class="pa-1" cols="12" md="4">
                <v-text-field
                  v-model="ownerName"
                  maxlength="100"
                  class="mb-2"
                  width="600"
                  density="compact"
                  variant="outlined"
                  placeholder="支払方法"
                  :rules="[required]" />
              </v-col>
            </v-row>

            <v-row>
              <v-col class="pa-1" cols="12" md="4">
                <v-text-field
                  v-model="ownerName"
                  maxlength="100"
                  class="mb-2"
                  width="600"
                  density="compact"
                  variant="outlined"
                  placeholder="Wifi名"
                  :rules="[required]" />
              </v-col>

              <v-col class="pa-1" cols="12" md="4">
                <v-text-field
                  v-model="ownerName"
                  maxlength="100"
                  class="mb-2"
                  width="600"
                  density="compact"
                  variant="outlined"
                  placeholder="Wifiパスワード"
                  :rules="[required]" />
              </v-col>
            </v-row>

            <v-row>
              <v-col class="pa-1" cols="12" md="3">
                <v-btn
                  block
                  type="submit"
                  color="info"
                  size="large"
                  :disabled="loading"
                  :loading="loading">
                  登録
                </v-btn>
              </v-col>
            </v-row>
          </v-card>
        </v-form>
      </v-container>
    </v-main>
  </v-layout>
</template>

<script>
import OwnerHeader from "./common/OwnerHeader.vue";

export default {
  data: () => ({
    items: [
      {
        title: "Dashboard",
        disabled: false,
        href: "breadcrumbs_dashboard",
      },
      {
        title: "Link 1",
        disabled: false,
        href: "breadcrumbs_link_1",
      },
      {
        title: "Link 2",
        disabled: true,
        href: "breadcrumbs_link_2",
      },
    ],
  }),
  components: {
    OwnerHeader,
  },
  created: function () {},
  methods: {},
};
</script>
