import { createApp } from "vue";
import App from "./App.vue";

// axios
import axios from "axios";
axios.defaults.baseURL = "http://192.168.0.17:80";
// axios.defaults.baseURL = "http://localhost:8081";

// vue-router
import router from "./router";

// vuex
// import store from "./store";

// vue-i18n
import i18n from "./i18n";

// vuetify
import "vuetify/styles";
import { createVuetify } from "vuetify";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";

const defaultTheme = localStorage.getItem("theme") || "light";
const vuetify = createVuetify({
  components,
  directives,
  theme: { defaultTheme },
});

// icon
import "@mdi/font/css/materialdesignicons.css";

const app = createApp(App);
app.config.globalProperties.$http = axios;
app.use(router).use(i18n).use(vuetify).mount("#app");
