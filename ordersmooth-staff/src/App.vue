<template>
  <div class="fixed-top d-flex align-items-center justify-content-center" style="bottom: 0; overflow-y: auto">
    <div style="max-width: 360px;" class="mx-3 my-3">
      <b-img class="my-3" :src="require('./assets/logo.png')" fluid></b-img>
      <b-card>
        <StaffLogin />
      </b-card>
    </div>
  </div>
</template>

<script>
import StaffLogin from './components/StaffLogin.vue'

export default {
  name: 'App',
  components: {
    StaffLogin,
  },
}
</script>

<style></style>
