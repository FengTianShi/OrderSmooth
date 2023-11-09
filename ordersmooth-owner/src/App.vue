<template>
  <div class="fixed-top d-flex align-items-center justify-content-center" style="bottom: 0; overflow-y: auto">
    <div style="max-width: 360px;" class="mx-3 my-3">
      <b-img class="my-3" :src="require('./assets/logo.png')" fluid></b-img>
      <b-card v-if="isShowLogin">
        <OwnerLogin />
        <hr>
        <b-button block variant="warning" @click="isShowLogin = false">登録</b-button>
      </b-card>
      <b-card v-if="!isShowLogin">
        <OwnerSignup @showLogin="isShowLogin = true" />
      </b-card>
    </div>
  </div>
</template>

<script>
import OwnerLogin from './components/OwnerLogin.vue'
import OwnerSignup from './components/OwnerSignup.vue'

export default {
  name: 'App',
  components: {
    OwnerLogin,
    OwnerSignup,
  },
  data() {
    return {
      isShowLogin: true,
    }
  }
}
</script>

<style></style>
