<template>
  <div class="px-4">
    <v-card class="mx-auto pa-8 mt-4" elevation="0" max-width="400">
      <v-img class="mx-auto mb-6 mt-3" :src="require('../assets/logo.png')" />

      <div v-if="step == 0">
        <v-form validate-on="submit lazy" @submit.prevent="signup">
          <v-text-field
            class="mb-2"
            density="compact"
            variant="outlined"
            prepend-inner-icon="mdi-account"
            maxlength="100"
            v-model="ownerName"
            :placeholder="$t('signup.name')"
            :rules="[required]" />

          <v-text-field
            class="mb-2"
            density="compact"
            variant="outlined"
            prepend-inner-icon="mdi-email-outline"
            maxlength="100"
            v-model="ownerEmail"
            :placeholder="$t('signup.mailAddress')"
            :rules="[required, email, unique]" />

          <v-text-field
            class="mb-2"
            density="compact"
            variant="outlined"
            prepend-inner-icon="mdi-lock-outline"
            maxlength="100"
            v-model="ownerPassword"
            :placeholder="$t('signup.password')"
            :hint="$t('signup.passwordLengthHint')"
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
            :placeholder="$t('signup.passwordConfirm')"
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
            {{ $t("signup.signup") }}
          </v-btn>

          <v-card-text class="text-center">
            <router-link class="text-decoration-none" to="/Signin">
              {{ $t("signup.backToSignin") }}
            </router-link>
          </v-card-text>
        </v-form>
      </div>

      <div v-if="step == 1">
        <h3 class="text-h7 mb-4 text-center">
          {{ $t("signup.verifyMailTitle") }}
        </h3>

        <div class="text-body-2 text-center">
          <strong>{{ ownerEmail }}</strong>
          <br />
          <p>
            {{ $t("signup.verifyMailSubTitle") }}
          </p>
          <br />
          <p>
            {{ $t("signup.verifyMailContent") }}
          </p>
        </div>

        <v-otp-input
          class="mb-2"
          v-model="otp"
          :disabled="loading"
          :error="otpIncorrect" />

        <div class="text-caption mb-2">
          {{ $t("signup.otpNotSentHint") }}
          <a
            href="#"
            class="text-decoration-none"
            v-if="!resending"
            @click="getToken">
            <strong>{{ $t("signup.resentOtp") }}</strong>
          </a>
          <span v-if="resending" class="text-decoration-none">
            <strong>{{ $t("signup.otpSending") }}</strong>
          </span>
        </div>

        <v-btn
          block
          color="amber"
          size="large"
          :disabled="otp.length < 6 || loading"
          :loading="loading"
          @click="confirmSignup">
          {{ $t("signup.verifyOtp") }}
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
          {{ $t("signup.changeMailAddress") }}
        </v-btn>
      </div>
    </v-card>
  </div>
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
    resending: false,
    step: 0,
  }),
  methods: {
    async getToken() {
      this.loading = true;
      this.resending = true;
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
    required(value) {
      return !!value || this.$t("signup.requiredError");
    },
    email(value) {
      const pattern =
        /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return pattern.test(value) || this.$t("signup.emailError");
    },
    async unique(value) {
      var unique = false;
      await this.$http.get(`/owner/is-exists/${value}`).then((response) => {
        if (!response.data) {
          unique = true;
        }
      });
      return unique || this.$t("signup.mailUniqueError");
    },
    minLength(value) {
      return value?.length >= 8 || this.$t("signup.passwordLengthError");
    },
    same(value) {
      return (
        value == this.ownerPassword || this.$t("signup.passwordConfirmError")
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
