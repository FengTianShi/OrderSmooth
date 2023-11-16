<template>
  <OwnerHeader />
  Owner Restaurant MGT
  <OwnerFooter />
</template>

<script>
import OwnerHeader from "./common/OwnerHeader.vue";
import OwnerFooter from "./common/OwnerFooter.vue";

export default {
  data: () => ({}),
  components: {
    OwnerHeader,
    OwnerFooter,
  },
  created: function () {
    console.log("created");
  },
  methods: {},
};
</script>
