<template>
  <v-card class="mx-auto pa-8" elevation="0" max-width="400">
    <v-img class="mx-auto my-6" :src="require('../assets/logo.png')"></v-img>

    <div v-if="signupStep == 0">
      <v-form
        validate-on="submit lazy"
        @submit.prevent="createOwnerSignSession">
        <v-text-field
          v-model="ownerName"
          density="compact"
          placeholder="お名前"
          prepend-inner-icon="mdi-account"
          variant="outlined"
          class="mb-2"
          maxlength="100"
          :rules="[required]" />

        <v-text-field
          v-model="ownerEmail"
          density="compact"
          placeholder="メールアドレス"
          prepend-inner-icon="mdi-email-outline"
          variant="outlined"
          class="mb-2"
          maxlength="100"
          :rules="[required, email, notDuplicate]" />

        <v-text-field
          v-model="ownerPassword"
          density="compact"
          placeholder="パスワード"
          prepend-inner-icon="mdi-lock-outline"
          variant="outlined"
          class="mb-2"
          :append-inner-icon="passwordVisible ? 'mdi-eye-off' : 'mdi-eye'"
          :type="passwordVisible ? 'text' : 'password'"
          @click:append-inner="passwordVisible = !passwordVisible"
          :rules="[required, minLength]"
          maxlength="100"
          hint="パスワードは8桁以上入力ください" />

        <v-text-field
          v-model="ownerPasswordVerify"
          density="compact"
          placeholder="パスワード确认"
          prepend-inner-icon="mdi-lock-outline"
          variant="outlined"
          :append-inner-icon="passwordVerifyVisible ? 'mdi-eye-off' : 'mdi-eye'"
          :type="passwordVerifyVisible ? 'text' : 'password'"
          @click:append-inner="passwordVerifyVisible = !passwordVerifyVisible"
          :rules="[required, samePassword]"
          class="mb-2"
          maxlength="100" />

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
      <h3 class="text-h6 mb-4 text-center">Verify Your Account</h3>

      <div class="text-body-2 text-center">
        {{ ownerEmail }}
        <br />
        チェック、入力
      </div>

      <v-otp-input
        :loading="loading"
        v-model="verifyCode"
        class="mb-2"
        :error="verifyCodeIncorrect"></v-otp-input>

      <div class="text-caption mb-2">
        Didn't receive the code?
        <a href="#" @click.prevent="verifyCode = ''">Resend</a>
      </div>

      <v-btn
        block
        :disabled="verifyCode.length < 6 || loading"
        color="amber"
        size="large"
        :loading="loading"
        @click="sendVerifyCode">
        送信
      </v-btn>

      <v-divider class="my-3"></v-divider>

      <v-btn block color="grey" size="large" @click="signupStep = 0">
        修正
      </v-btn>
    </div>

    <div v-if="signupStep == 2">
      <h3 class="text-h6 mb-4 text-center">OK</h3>
    </div>
  </v-card>
</template>

<script>
export default {
  data: () => ({
    ownerName: "",
    ownerEmail: "",
    ownerPassword: "",
    ownerPasswordVerify: "",
    verifyCode: "",
    verifyCodeIncorrect: false,
    passwordVisible: false,
    passwordVerifyVisible: false,
    loading: false,
    signupStep: 0,
  }),
  methods: {
    async createOwnerSignSession(event) {
      this.loading = true;
      const results = await event;
      if (results.valid) {
        await this.$http
          .post("/owner/signup-session", {
            ownerName: this.ownerName,
            ownerEmail: this.ownerEmail,
            ownerPassword: this.ownerPassword,
          })
          .then((response) => {
            if (response.data.code == 201) {
              // var ownerSignUpSession = {
              //     "ownerName": this.ownerName,
              //     "ownerEmail": this.ownerEmail,
              //     "ownerPassword": this.ownerPassword
              // };
              // window.localStorage.setItem("owner-signup-session", JSON.stringify(ownerSignUpSession));
              this.signupStep = 1;
            }
          });
      }
      this.loading = false;
    },
    required(value) {
      return !!value || "ご入力ください";
    },
    email(value) {
      const pattern =
        /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return pattern.test(value) || "メールアドレスを正しく入力してください";
    },
    async notDuplicate(value) {
      var isDuplicate = true;
      var ownerEmail = value;
      await this.$http.get(`/owner-exists/${ownerEmail}`).then((response) => {
        if (!response.data) {
          isDuplicate = false;
        }
      });
      return !isDuplicate || "メールアドレスは既に登録されています";
    },
    minLength(value) {
      return value?.length >= 8 || "パスワードは8桁以上入力ください";
    },
    samePassword(value) {
      return value == this.ownerPassword || "パスワードは一致しません";
    },
    async sendVerifyCode() {
      this.loading = true;
      await this.$http
        .post("/owner", {
          verifyCode: this.verifyCode,
        })
        .then((response) => {
          console.log(response.data.code);
          if (response.data.code == 201) {
            this.signupStep = 2;
          } else {
            this.verifyCodeIncorrect = true;
          }
        });
      this.loading = false;
    },
  },
  // created: function () {
  //     var ownerSignUpSession = JSON.parse(window.localStorage.getItem("owner-signup-session"));
  //     if (ownerSignUpSession) {
  //         this.ownerName = ownerSignUpSession.ownerId;
  //         this.ownerEmail = ownerSignUpSession.ownerEmail;
  //         this.ownerPassword = ownerSignUpSession.ownerPassword;
  //     }
  //     window.localStorage.removeItem("owner-signup-session");
  // }
};
</script>
