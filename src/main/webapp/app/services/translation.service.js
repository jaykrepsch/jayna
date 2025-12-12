import axios from "axios";
import store from "@/store/index";
import i18n from "@/shared/config/i18n";
import dayjs from "dayjs";

export default {
  refreshTranslation(newLanguage) {
    let currentLanguage = store && store.getters ? store.getters["translation/currentLanguage"] : "de";
    currentLanguage = newLanguage ? newLanguage : "de";

    if (i18n && !i18n.global.messages[currentLanguage]) {
      i18n.global.setLocaleMessage(currentLanguage, {});
      axios.get(`i18n/${currentLanguage}.json?_=${I18N_HASH}`).then((res) => {
        if (res.data) {
          i18n.global.setLocaleMessage(currentLanguage, res.data);
          this.setLocale(currentLanguage);
        }
      });
    } else if (i18n) {
      this.setLocale(currentLanguage);
    }
  },

  setLocale(lang) {
    dayjs.locale(lang);
    i18n.global.locale = lang;
    store.commit("translation/currentLanguage", lang);
    axios.defaults.headers.common["Accept-Language"] = lang;
    document.querySelector("html").setAttribute("lang", lang);
  },
};
