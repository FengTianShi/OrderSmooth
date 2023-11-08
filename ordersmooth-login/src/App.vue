<template>
  <div class="fixed-top d-flex align-items-center justify-content-center" style="bottom: 0; overflow-y: auto">
    <div style="max-width: 360px;" class="mx-3 my-3">

      <b-img class="my-3" :src="require('./assets/ordersmooth-middle.png')" fluid></b-img>

      <b-card no-body>
        <b-tabs card justified>
          <b-tab title="オーナー" active>
            <OwnerLogin />
          </b-tab>

          <b-tab title="スタッフ">
            <StaffLogin />
          </b-tab>
        </b-tabs>
      </b-card>

    </div>
  </div>
</template>

<script>
import OwnerLogin from './components/OwnerLogin.vue'
import StaffLogin from './components/StaffLogin.vue'

export default {
  name: 'App',
  components: {
    OwnerLogin,
    StaffLogin
  }
}
</script>

<style></style>
