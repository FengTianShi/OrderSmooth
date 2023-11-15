<template>
  <v-card class="mx-auto pa-8" elevation="0" max-width="400">
    <v-img class="mx-auto my-6" :src="require('../assets/logo.png')"></v-img>

    <div v-if="signupStep == 0">
      <v-text-field
        density="compact"
        placeholder="メールアドレス"
        prepend-inner-icon="mdi-email-outline"
        variant="outlined" />

      <v-text-field
        density="compact"
        placeholder="パスワード"
        prepend-inner-icon="mdi-lock-outline"
        variant="outlined"
        :append-inner-icon="passwordVisible ? 'mdi-eye-off' : 'mdi-eye'"
        :type="passwordVisible ? 'text' : 'password'"
        @click:append-inner="passwordVisible = !passwordVisible">
      </v-text-field>

      <v-text-field
        density="compact"
        placeholder="パスワード"
        prepend-inner-icon="mdi-lock-outline"
        variant="outlined"
        :append-inner-icon="passwordVisible ? 'mdi-eye-off' : 'mdi-eye'"
        :type="passwordVisible ? 'text' : 'password'"
        @click:append-inner="passwordVisible = !passwordVisible">
      </v-text-field>

      <v-btn block color="amber" size="large" @click="signupStep = 1">
        送信
      </v-btn>

      <v-divider class="my-3"></v-divider>

      <v-btn
        block
        color="grey"
        size="large"
        @click="this.$router.push('/Signin')">
        ログインに戻る
      </v-btn>
    </div>

    <div v-if="signupStep == 1">
      <v-otp-input></v-otp-input>

      <v-btn block color="amber" size="large"> 送信 </v-btn>

      <v-divider class="my-3"></v-divider>

      <v-btn block color="grey" size="large" @click="signupStep = 0">
        修正
      </v-btn>
    </div>
  </v-card>
</template>

<script>
export default {
  data: () => ({
    // isAuthenticated: false,
    passwordVisible: false,
    signupStep: 0,
  }),
};
</script>
