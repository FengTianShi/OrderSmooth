<template>
  <h2 class="text-h4 font-weight-black text-orange">
    Restaurant Opening Hours
  </h2>
  <v-divider class="my-6"></v-divider>
</template>

<script>
export default {
  props: {
    restaurantId: {
      required: true,
    },
  },
  data: () => ({
    restaurantDetail: {},

    loading: false,
    updated: false,
  }),
  methods: {},
  async created() {},
};
</script>
