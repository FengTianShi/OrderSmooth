import {
  createRouter,
  createWebHistory,
  // createWebHashHistory,
} from "vue-router";

// 1、引入组件
import OwnerSignin from "../components/OwnerSignin.vue";
import OwnerSignup from "../components/OwnerSignup.vue";
import OwnerResetPassword from "../components/OwnerResetPassword.vue";

import OwnerDashboard from "../components/OwnerDashboard.vue";
import OwnerRestaurantDetail from "../components/OwnerRestaurantDetail.vue";
import OwnerCreateRestaurant from "../components/OwnerCreateRestaurant.vue";
import OwnerUpdateRestaurant from "../components/OwnerUpdateRestaurant.vue";

/**
 * 2、配置路由映射关系
 */
const routes = [
  { path: "/", redirect: "/Signin" },
  { path: "/Signin", component: OwnerSignin },
  { path: "/Signup", component: OwnerSignup },
  { path: "/ResetPassword", component: OwnerResetPassword },
  { path: "/Dashboard", component: OwnerDashboard },
  { path: "/CreateRestaurant", component: OwnerCreateRestaurant },
  { path: "/UpdateRestaurant", component: OwnerUpdateRestaurant },
  { path: "/RestaurantDetail/:id", component: OwnerRestaurantDetail },
];

// 3、创建一个路由的对象
const router = createRouter({
  // 指定模式
  /**
   * createWebHashHistory 带 # 号
   * createWebHistory 不带 # 号
   */
  history: createWebHistory(),
  // 下面这个 可以写成ES6的简写 routers
  routes: routes,
});

import axios from "axios";

router.beforeEach(async (to) => {
  var isAuthenticated = false;

  var ownerToken = JSON.parse(window.localStorage.getItem("owner-token"));
  if (ownerToken) {
    await axios
      .get(`/owner/is-signin`, {
        headers: { Authorization: `Bearer ${ownerToken}` },
      })
      .then((response) => {
        if (response.status === 200) {
          isAuthenticated = true;
        }
      })
      .catch((error) => {
        console.log(error);
      });
  }

  if (
    !isAuthenticated &&
    to.path !== "/Signin" &&
    to.path !== "/Signup" &&
    to.path !== "/ResetPassword"
  ) {
    return "/Signin";
  }
});

export default router;
