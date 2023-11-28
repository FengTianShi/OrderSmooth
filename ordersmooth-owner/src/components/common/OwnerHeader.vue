<template>
  <v-app-bar>
    <v-app-bar-nav-icon variant="text" @click.stop="drawer = !drawer">
    </v-app-bar-nav-icon>

    <v-img
      height="45"
      max-width="170"
      :src="require('../../assets/logo-only-content.png')"></v-img>

    <v-spacer></v-spacer>

    <v-menu min-width="200px" rounded>
      <template v-slot:activator="{ props }">
        <v-btn icon v-bind="props">
          <v-avatar color="#3f63af">
            <span class="text-h5">{{ user.initials }}</span>
          </v-avatar>
        </v-btn>
      </template>

      <v-card>
        <v-card-text>
          <div class="mx-auto text-center">
            <v-avatar color="#3f63af">
              <span class="text-h5">{{ user.initials }}</span>
            </v-avatar>
            <h3>{{ user.fullName }}</h3>
            <p class="text-caption mt-1">
              {{ user.email }}
            </p>
            <v-divider class="my-3"></v-divider>
            <v-btn rounded variant="text">アカウント情報</v-btn>
            <v-divider class="my-3"></v-divider>
            <v-btn rounded variant="text">パスワード変更</v-btn>
            <v-divider class="my-3"></v-divider>
            <v-btn rounded variant="text">ログアウト</v-btn>
          </div>
        </v-card-text>
      </v-card>
    </v-menu>

    <v-menu min-width="200px" rounded>
      <template v-slot:activator="{ props }">
        <v-btn icon="mdi-translate" v-bind="props"></v-btn>
      </template>

      <v-card>
        <v-card-text>
          <div class="mx-auto text-center">
            <v-btn rounded variant="text" @click="changeLang('en')">
              English
            </v-btn>
            <v-divider class="my-3"></v-divider>
            <v-btn rounded variant="text" @click="changeLang('ja')">
              日本語
            </v-btn>
            <v-divider class="my-3"></v-divider>
            <v-btn rounded variant="text" @click="changeLang('zh-CN')">
              简体中文
            </v-btn>
            <v-divider class="my-3"></v-divider>
            <v-btn rounded variant="text" @click="changeLang('zh-TW')">
              繁体中文
            </v-btn>
            <v-divider class="my-3"></v-divider>
            <v-btn size="small" disabled rounded variant="text">
              Coming Soon
            </v-btn>
          </div>
        </v-card-text>
      </v-card>
    </v-menu>
  </v-app-bar>

  <v-navigation-drawer v-model="drawer" temporary width="350">
    <v-list nav>
      <div class="pa-2">
        <v-text-field
          density="compact"
          variant="outlined"
          hide-details
          placeholder="店舗検索"
          prepend-inner-icon="mdi-magnify"
          single-line>
        </v-text-field>
      </div>

      <div class="pa-2">
        <v-btn color="amber" prepend-icon="mdi-store-plus-outline" block
          >店舗追加</v-btn
        >
      </div>

      <v-list-group>
        <template v-slot:activator="{ props }">
          <v-list-item v-bind="props" prepend-icon="mdi-store-outline">
            海味 新宿店
            <p style="font-size: 14px" class="text-green">営業中</p>
            <!-- <span style="font-size: 14px" class="text-warning">営業停止</span> -->
          </v-list-item>
        </template>

        <v-list-item prepend-icon="mdi-store-edit-outline" value="test1">
          店舗情報
        </v-list-item>

        <v-list-item prepend-icon="mdi-silverware-variant" value="a">
          コース管理
        </v-list-item>
        <v-list-item prepend-icon="mdi-menu" value="b">
          メニュー管理
        </v-list-item>
        <v-list-item prepend-icon="mdi-food" value="c"> 商品管理 </v-list-item>
        <v-list-item prepend-icon="mdi-account-multiple" value="e">
          スタッフ管理
        </v-list-item>
        <v-list-item prepend-icon="mdi-table-chair" value="d">
          テーブル管理
        </v-list-item>
      </v-list-group>

      <v-list-item prepend-icon="mdi-history" value="z">
        歴史データ
      </v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>

<script>
export default {
  data: () => ({
    drawer: false,
    user: {
      initials: "馮",
      fullName: "馮 天時",
      email: "futenji923@gmail.com",
    },
    admins: [
      ["Management", "mdi-account-multiple-outline"],
      ["Settings", "mdi-cog-outline"],
    ],
    cruds: [
      ["Create", "mdi-plus-outline"],
      ["Read", "mdi-file-outline"],
      ["Update", "mdi-update"],
      ["Delete", "mdi-delete"],
    ],
  }),
  created: function () {},
  methods: {
    changeLang: function (lang) {
      this.$i18n.locale = lang;
      localStorage.setItem("lang", lang);
    },
  },
};
</script>
