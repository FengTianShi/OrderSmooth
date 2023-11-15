<template>
  <v-card class="mx-auto pa-8" elevation="0" max-width="400">
    <v-img class="mx-auto my-6" :src="require('../assets/logo.png')"></v-img>

    <v-form validate-on="submit lazy">
      <v-text-field
        class="mb-2"
        v-model="ownerEmail"
        density="compact"
        variant="outlined"
        placeholder="メールアドレス"
        prepend-inner-icon="mdi-email-outline"
        maxlength="100"
        :rules="[required, email, legalOwner]" />

      <v-text-field
        class="mb-2"
        v-model="ownerPassword"
        density="compact"
        variant="outlined"
        placeholder="パスワード"
        prepend-inner-icon="mdi-lock-outline"
        maxlength="100"
        :append-inner-icon="passwordVisible ? 'mdi-eye-off' : 'mdi-eye'"
        :type="passwordVisible ? 'text' : 'password'"
        :rules="[required]"
        @click:append-inner="passwordVisible = !passwordVisible" />

      <div class="text-caption mb-2">
        パスワードをお忘れた場合は
        <router-link class="text-decoration-none" to="/ResetPassword">
          こちら
        </router-link>
      </div>

      <v-btn
        block
        type="submit"
        color="black"
        size="large"
        :disabled="loading"
        :loading="loading">
        ログイン
      </v-btn>
    </v-form>
    <v-card-text class="text-center">
      <router-link class="text-decoration-none" to="/Signup">
        今すぐ登録
      </router-link>
    </v-card-text>
  </v-card>
</template>

<script>
export default {
  data: () => ({
    ownerEmail: "",
    ownerPassword: "",
    passwordVisible: false,
    loading: false,
  }),
  methods: {
    required(value) {
      return !!value || "必須項目を入力してください";
    },
    email(value) {
      const pattern =
        /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return pattern.test(value) || "メールアドレスを正しく入力してください";
    },
    async legalOwner() {
      this.loading = true;

      if (!this.ownerPassword) {
        this.loading = false;
        return true;
      }

      await this.$http
        .post("/owner/signin", {
          ownerEmail: this.ownerEmail,
          ownerPassword: this.ownerPassword,
        })
        .then((response) => {
          if (response.status == 201) {
            window.localStorage.setItem(
              "owner-token",
              JSON.stringify(response.data)
            );
            this.$router.push("/Dashboard");
          }
        })
        .catch((error) => {
          console.log(error.response);
        });

      this.loading = false;

      return "メールアドレスもしくはパスワードが間違っています";
    },
  },
};
</script>
