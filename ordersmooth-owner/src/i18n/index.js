import { createI18n } from "vue-i18n";

import zhCN from "@/i18n/langs/zh-CN";
import en from "@/i18n/langs/en";
import ja from "@/i18n/langs/ja";

const i18n = new createI18n({
  locale: localStorage.getItem("lang") || navigator.language || "en",

  messages: {
    en: en,
    "en-US": en,
    "en-AU": en,
    "en-BZ": en,
    "en-CA": en,
    "en-CB": en,
    "en-GB": en,
    "en-IE": en,
    "en-JM": en,
    "en-NZ": en,
    "en-PH": en,
    "en-TT": en,
    "en-ZA": en,
    "en-ZW": en,
    ja: ja,
    "ja-JP": ja,
    zh: zhCN,
    "zh-CN": zhCN,
  },
});

export default i18n;
