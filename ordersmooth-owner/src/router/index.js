import {
  createRouter,
  createWebHistory,
  // createWebHashHistory,
} from "vue-router";

// 1、引入组件
import OwnerLogin from "../components/OwnerLogin.vue";
import OwnerSignup from "../components/OwnerSignup.vue";
import OwnerResetPassword from "../components/OwnerResetPassword.vue";
import OwnerDashboard from "../components/OwnerDashboard.vue";
import TestVuex from "../components/TestVuex.vue";

/**
 * 2、配置路由映射关系
 */
const routes = [
  { path: "/", redirect: "/Login" },
  { path: "/Login", component: OwnerLogin },
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

import axios from "axios";

router.beforeEach(async (to) => {
  var isAuthenticated = false;
  var ownerSession = JSON.parse(window.localStorage.getItem("owner-session"));

  if (ownerSession) {
    var ownerId = ownerSession.ownerId;
    var sessionToken = ownerSession.sessionToken;

    await axios
      .get(`/owner/session/${ownerId}/${sessionToken}`)
      .then((response) => {
        if (response.data) {
          isAuthenticated = true;
        }
      });
  }

  if (
    !isAuthenticated &&
    to.path !== "/Login" &&
    to.path !== "/Signup" &&
    to.path !== "/ResetPassword"
  ) {
    return "/Login";
  }
});

export default router;
