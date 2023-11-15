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
import TestVuex from "../components/TestVuex.vue";

/**
 * 2、配置路由映射关系
 */
const routes = [
  { path: "/", redirect: "/Signin" },
  { path: "/Signin", component: OwnerSignin },
  { path: "/Signup", component: OwnerSignup },
  { path: "/ResetPassword", component: OwnerResetPassword },
  { path: "/Dashboard", component: OwnerDashboard },
  { path: "/TestVuex", component: TestVuex },
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

router.beforeEach(async (to) => {
  var ownerToken = JSON.parse(window.localStorage.getItem("owner-token"));

  if (
    !ownerToken &&
    to.path !== "/Signin" &&
    to.path !== "/Signup" &&
    to.path !== "/ResetPassword"
  ) {
    return "/Signin";
  }
});

export default router;
