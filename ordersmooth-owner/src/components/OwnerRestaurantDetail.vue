<template>
  <v-layout>
    <OwnerHeader />
    <v-main>
      <v-container fluid class="pa-4">
        <v-card class="mx-auto" elevation="0">
          <v-card class="mx-auto pa-8" max-width="1000" elevation="0">
            <v-row>
              <v-col cols="12" md="3">
                <v-list class="pa-0">
                  <v-list-item
                    v-for="(item, i) in items"
                    :key="i"
                    :value="item"
                    color="primary">
                    <template v-slot:prepend>
                      <v-icon :icon="item.icon"></v-icon>
                    </template>

                    <v-list-item-title v-text="item.text"></v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-col>
              <v-col cols="12" md="9">
                <OwnerRestaurantDetailBase :restaurantId="restaurantId" />
              </v-col>
            </v-row>
          </v-card>
        </v-card>
      </v-container>
    </v-main>
  </v-layout>
</template>

<script>
import OwnerHeader from "./common/OwnerHeader.vue";
import OwnerRestaurantDetailBase from "./OwnerRestaurantDetailBase.vue";

export default {
  components: {
    OwnerHeader,
    OwnerRestaurantDetailBase,
  },
  data: () => ({
    restaurantId: null,
    items: [
      { text: "基本", icon: "mdi-clock" },
      { text: "支付", icon: "mdi-flag" },
      { text: "营业时间", icon: "mdi-flag" },
      { text: "宣传图", icon: "mdi-account" },
      { text: "打印机", icon: "mdi-flag" },
      { text: "安全(服务距离等)", icon: "mdi-flag" },
      { text: "控制(删除 无效化 停业开业)", icon: "mdi-flag" },
    ],
    loading: false,
  }),
  methods: {},
  created() {
    this.restaurantId = this.$route.params.id;
  },
};
</script>
