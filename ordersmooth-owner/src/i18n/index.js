import { createI18n } from "vue-i18n";

import en from "@/i18n/langs/en";
import ja from "@/i18n/langs/ja";
import zhCN from "@/i18n/langs/zh-CN";
import zhTW from "@/i18n/langs/zh-TW";

var lang = localStorage.getItem("lang") || navigator.language || "en";

if (lang.startsWith("en")) {
  lang = "en";
} else if (lang.startsWith("ja")) {
  lang = "ja";
} else if (lang.startsWith("zh")) {
  if (lang == "zh" || lang == "zh-CN") {
    lang = "zh-CN";
  } else {
    lang = "zh-TW";
  }
} else {
  lang = "en";
}

const i18n = new createI18n({
  locale: lang,

  messages: {
    en: en,
    ja: ja,
    "zh-CN": zhCN,
    "zh-TW": zhTW,
  },
});

export default i18n;
