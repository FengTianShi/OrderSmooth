import {
  createRouter,
  createWebHistory,
  // createWebHashHistory,
} from "vue-router";

// 1、引入组件
import OwnerSignin from "../components/OwnerSignin.vue";
import OwnerSignup from "../components/OwnerSignup.vue";
import ResetPassword from "../components/ResetPassword.vue";

import OwnerDashboard from "../components/OwnerDashboard.vue";
import RestaurantDetail from "../components/RestaurantDetail.vue";
import CreateRestaurant from "../components/CreateRestaurant.vue";

/**
 * 2、配置路由映射关系
 */
const routes = [
  { path: "/", redirect: "/Signin" },
  { path: "/Signin", component: OwnerSignin },
  { path: "/Signup", component: OwnerSignup },
  { path: "/ResetPassword", component: ResetPassword },
  { path: "/OwnerDashboard", component: OwnerDashboard },
  { path: "/CreateRestaurant", component: CreateRestaurant },
  { path: "/RestaurantDetail/:id", component: RestaurantDetail },
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
  if (
    to.path !== "/Signin" &&
    to.path !== "/Signup" &&
    to.path !== "/ResetPassword"
  ) {
    let isAuthenticated = false;

    await axios
      .get(`/owner/is-signin`, {
        headers: {
          Authorization: `Bearer ${JSON.parse(
            window.localStorage.getItem("owner-token")
          )}`,
        },
      })
      .then((response) => {
        if (response.status === 200) {
          isAuthenticated = true;
        }
      })
      .catch((error) => {
        console.log(error);
        isAuthenticated = false;
      });

    if (!isAuthenticated) {
      return "/Signin";
    }
  }
});

export default router;
