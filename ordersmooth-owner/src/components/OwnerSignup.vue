<template>
  <v-card class="mx-auto pa-8" elevation="0" max-width="400">
    <v-img class="mx-auto my-6" :src="require('../assets/logo.png')"></v-img>

    <div v-if="signupStep == 0">
      <v-form validate-on="submit lazy" @submit.prevent="signup">
        <v-text-field
          v-model="ownerName"
          maxlength="100"
          class="mb-2"
          density="compact"
          variant="outlined"
          placeholder="お名前"
          prepend-inner-icon="mdi-account"
          :rules="[required]" />

        <v-text-field
          v-model="ownerEmail"
          maxlength="100"
          class="mb-2"
          density="compact"
          variant="outlined"
          placeholder="メールアドレス"
          prepend-inner-icon="mdi-email-outline"
          :rules="[required, email, unique]" />

        <v-text-field
          v-model="ownerPassword"
          maxlength="100"
          class="mb-2"
          density="compact"
          variant="outlined"
          placeholder="パスワード"
          hint="パスワードは8桁以上入力ください"
          prepend-inner-icon="mdi-lock-outline"
          :append-inner-icon="passwordVisible ? 'mdi-eye-off' : 'mdi-eye'"
          :type="passwordVisible ? 'text' : 'password'"
          :rules="[required, minLength]"
          @click:append-inner="passwordVisible = !passwordVisible" />

        <v-text-field
          v-model="ownerPasswordConfirm"
          maxlength="100"
          class="mb-2"
          density="compact"
          variant="outlined"
          placeholder="パスワード确认"
          prepend-inner-icon="mdi-lock-outline"
          :append-inner-icon="
            passwordConfirmVisible ? 'mdi-eye-off' : 'mdi-eye'
          "
          :type="passwordConfirmVisible ? 'text' : 'password'"
          :rules="[required, same]"
          @click:append-inner="
            passwordConfirmVisible = !passwordConfirmVisible
          " />

        <v-btn
          block
          type="submit"
          color="amber"
          size="large"
          :disabled="loading"
          :loading="loading">
          登録
        </v-btn>
      </v-form>
    </div>

    <div v-if="signupStep == 1">
      <h3 class="text-h7 mb-4 text-center">メールアドレスを認証してください</h3>

      <div class="text-body-2 text-center">
        <strong>{{ ownerEmail }}</strong>
        <br />
        <p>宛に認証コードが送信されました</p>
        <br />
        <p>
          メール受信箱を確認し、下記に認証コードを入力してあなたのメールアドレスを認証してください
        </p>
      </div>

      <v-otp-input
        v-model="otp"
        class="mb-2"
        :disabled="loading"
        :error="otpIncorrect">
      </v-otp-input>

      <div class="text-caption mb-2">
        認証コードを受信していませんか?
        <a
          v-if="!loading"
          class="text-decoration-none"
          href="#"
          @click="getToken">
          <strong>認証コードを再送</strong>
        </a>
        <span v-if="loading" class="text-decoration-none">
          <strong>送信中</strong>
        </span>
      </div>

      <v-btn
        block
        color="amber"
        size="large"
        :disabled="otp.length < 6 || loading"
        :loading="loading"
        @click="confirmSignup">
        認証
      </v-btn>

      <v-divider class="my-3"></v-divider>

      <v-btn block color="grey" size="large" @click="signupStep = 0">
        メールアドレスを変更
      </v-btn>
    </div>
  </v-card>
</template>

<script>
export default {
  data: () => ({
    ownerName: "",
    ownerEmail: "",
    ownerPassword: "",
    ownerPasswordConfirm: "",
    otp: "",
    passwordVisible: false,
    passwordConfirmVisible: false,
    otpIncorrect: false,
    loading: false,
    signupStep: 0,
  }),
  methods: {
    async getToken() {
      this.loading = true;
      this.otp = "";
      await this.$http
        .post("/owner/signup", {
          ownerName: this.ownerName,
          ownerEmail: this.ownerEmail,
          ownerPassword: this.ownerPassword,
        })
        .then((response) => {
          if (response.status == 201) {
            window.localStorage.setItem(
              "owner-signup-token",
              JSON.stringify(response.data)
            );
            this.signupStep = 1;
          }
        })
        .catch((error) => {
          console.log(error.response);
        });
      this.loading = false;
    },
    async signup(event) {
      const results = await event;
      if (results.valid) {
        await this.getToken();
      }
    },
    async confirmSignup() {
      this.loading = true;
      var ownerSignToken = JSON.parse(
        window.localStorage.getItem("owner-signup-token")
      );
      await this.$http
        .post("/owner/signup/confirm", {
          otp: this.otp,
          token: ownerSignToken,
        })
        .then((response) => {
          if (response.status == 201) {
            this.signin();
          }
        })
        .catch((error) => {
          console.log(error.response);
          this.otpIncorrect = true;
        });
      this.loading = false;
    },
    async unique(value) {
      var unique = false;
      await this.$http.get(`/owner/${value}`).then((response) => {
        if (!response.data) {
          unique = true;
        }
      });
      return unique || "メールアドレスは既に登録されています";
    },
    required(value) {
      return !!value || "必須項目を入力してください";
    },
    email(value) {
      const pattern =
        /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return pattern.test(value) || "メールアドレスを正しく入力してください";
    },
    minLength(value) {
      return value?.length >= 8 || "パスワードは8桁以上入力ください";
    },
    same(value) {
      return value == this.ownerPassword || "パスワードは一致しません";
    },
    async signin() {
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
    },
  },
};
</script>
