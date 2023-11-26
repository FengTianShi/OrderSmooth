<template>
  <div class="px-4">
    <v-card class="mx-auto pa-8 mt-4" elevation="0" max-width="400">
      <v-img class="mx-auto mb-6 mt-3" :src="require('../assets/logo.png')" />

      <v-form validate-on="submit lazy">
        <v-text-field
          class="mb-2"
          density="compact"
          variant="outlined"
          prepend-inner-icon="mdi-email-outline"
          maxlength="100"
          v-model="ownerEmail"
          :placeholder="$t('signin.mailAddress')"
          :rules="[required, email, legalSignin]" />

        <v-text-field
          class="mb-2"
          density="compact"
          variant="outlined"
          prepend-inner-icon="mdi-lock-outline"
          maxlength="100"
          v-model="ownerPassword"
          :placeholder="$t('signin.password')"
          :append-inner-icon="passwordVisible ? 'mdi-eye-off' : 'mdi-eye'"
          :type="passwordVisible ? 'text' : 'password'"
          :rules="[required]"
          @click:append-inner="passwordVisible = !passwordVisible" />

        <div class="text-caption mb-2">
          {{ $t("signin.forgotPasswordHint") }}
          <router-link class="text-decoration-none" to="/ResetPassword">
            {{ $t("signin.hereLink") }}
          </router-link>
        </div>

        <v-btn
          block
          type="submit"
          color="deep-purple-accent-4"
          size="large"
          :disabled="loading"
          :loading="loading">
          {{ $t("signin.signin") }}
        </v-btn>
      </v-form>
      <v-card-text class="text-center">
        <router-link class="text-decoration-none" to="/Signup">
          {{ $t("signin.registerNow") }}
        </router-link>
      </v-card-text>
    </v-card>
  </div>
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
      return !!value || this.$t("signin.requiredError");
    },
    email(value) {
      const pattern =
        /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return pattern.test(value) || this.$t("signin.emailError");
    },
    async legalSignin() {
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

      return this.$t("signin.legalSigninError");
    },
  },
};
</script>
