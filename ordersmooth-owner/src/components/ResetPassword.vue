<template>
  <div class="px-4">
    <v-card class="mx-auto pa-8 mt-4" elevation="0" max-width="400">
      <v-img class="mx-auto mb-6 mt-3" :src="require('../assets/logo.png')" />

      <div v-if="step == 0">
        <v-form validate-on="submit lazy" @submit.prevent="resetPassword">
          <v-text-field
            class="mb-2"
            density="compact"
            variant="outlined"
            prepend-inner-icon="mdi-email-outline"
            maxlength="100"
            v-model="ownerEmail"
            :placeholder="$t('resetPassword.mailAddress')"
            :rules="[required, email]" />

          <v-text-field
            class="mb-2"
            density="compact"
            variant="outlined"
            prepend-inner-icon="mdi-lock-outline"
            maxlength="100"
            v-model="ownerPassword"
            :placeholder="$t('resetPassword.newPassword')"
            :hint="$t('resetPassword.passwordLengthHint')"
            :append-inner-icon="passwordVisible ? 'mdi-eye-off' : 'mdi-eye'"
            :type="passwordVisible ? 'text' : 'password'"
            :rules="[required, minLength]"
            @click:append-inner="passwordVisible = !passwordVisible" />

          <v-text-field
            class="mb-2"
            density="compact"
            variant="outlined"
            prepend-inner-icon="mdi-lock-outline"
            maxlength="100"
            v-model="ownerPasswordConfirm"
            :placeholder="$t('resetPassword.passwordConfirm')"
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
            {{ $t("resetPassword.reset") }}
          </v-btn>

          <v-card-text class="text-center">
            <router-link class="text-decoration-none" to="/Signin">
              {{ $t("resetPassword.backToSignin") }}
            </router-link>
          </v-card-text>
        </v-form>
      </div>

      <div v-if="step == 1">
        <h3 class="text-h7 mb-4 text-center">
          {{ $t("resetPassword.verifyMailTitle") }}
        </h3>

        <div class="text-body-2 text-center">
          <strong>{{ ownerEmail }}</strong>
          <br />
          <p>
            {{ $t("resetPassword.verifyMailSubTitle") }}
          </p>
          <br />
          <p>
            {{ $t("resetPassword.verifyMailContent") }}
          </p>
        </div>

        <v-otp-input
          class="mb-2"
          v-model="otp"
          :disabled="loading"
          :error="otpIncorrect">
        </v-otp-input>

        <div class="text-caption mb-2">
          {{ $t("resetPassword.otpNotSentHint") }}
          <a
            href="#"
            class="text-decoration-none"
            v-if="!resending"
            @click="getToken">
            <strong> {{ $t("resetPassword.resentOtp") }}</strong>
          </a>
          <span v-if="resending" class="text-decoration-none">
            <strong> {{ $t("resetPassword.otpSending") }}</strong>
          </span>
        </div>

        <v-btn
          block
          color="amber"
          size="large"
          :disabled="otp.length < 6 || loading"
          :loading="loading"
          @click="confirmResetPassword">
          {{ $t("resetPassword.verifyOtp") }}
        </v-btn>

        <v-divider class="my-3"></v-divider>

        <v-btn
          block
          color="grey"
          size="large"
          @click="
            step = 0;
            otpIncorrect = false;
          ">
          {{ $t("resetPassword.changeMailAddress") }}
        </v-btn>
      </div>
    </v-card>
  </div>
</template>

<script>
export default {
  data: () => ({
    ownerEmail: "",
    ownerPassword: "",
    ownerPasswordConfirm: "",
    otp: "",
    passwordVisible: false,
    passwordConfirmVisible: false,
    otpIncorrect: false,
    loading: false,
    resending: false,
    step: 0,
  }),
  methods: {
    async getToken() {
      this.loading = true;
      this.resending = true;
      this.otp = "";

      await this.$http
        .post("/owner/reset-password", {
          ownerEmail: this.ownerEmail,
          newPassword: this.ownerPassword,
        })
        .then((response) => {
          if (response.status == 201) {
            window.localStorage.setItem(
              "owner-reset-password-token",
              JSON.stringify(response.data)
            );
            this.step = 1;
          }
        })
        .catch((error) => {
          console.log(error.response);
        });

      this.resending = false;
      this.loading = false;
      this.otpIncorrect = false;
    },
    async resetPassword(event) {
      const results = await event;
      if (results.valid) {
        await this.getToken();
      }
    },
    async confirmResetPassword() {
      this.loading = true;

      var ownerSignToken = JSON.parse(
        window.localStorage.getItem("owner-reset-password-token")
      );

      await this.$http
        .put("/owner/reset-password/confirm", {
          otp: this.otp,
          token: ownerSignToken,
        })
        .then((response) => {
          if (response.status == 204) {
            this.signin();
          }
        })
        .catch((error) => {
          console.log(error.response);
          this.otpIncorrect = true;
        });

      this.loading = false;
    },
    required(value) {
      return !!value || this.$t("resetPassword.requiredError");
    },
    email(value) {
      const pattern =
        /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return pattern.test(value) || this.$t("resetPassword.emailError");
    },
    minLength(value) {
      return value?.length >= 8 || this.$t("resetPassword.passwordLengthError");
    },
    same(value) {
      return (
        value == this.ownerPassword ||
        this.$t("resetPassword.passwordConfirmError")
      );
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
            this.$router.push("/OwnerDashboard");
          }
        })
        .catch((error) => {
          console.log(error.response);
        });
    },
  },
};
</script>
