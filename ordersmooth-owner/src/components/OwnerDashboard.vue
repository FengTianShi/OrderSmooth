<template>
  Dashboard
  <br />
  <router-link class="text-decoration-none" to="/OwnerRestaurant">
    レストラン管理
  </router-link>
</template>

<script>
export default {
  data: () => ({}),
  created: function () {
    console.log("created");
  },
};
</script>
