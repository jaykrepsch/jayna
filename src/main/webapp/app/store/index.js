import { createStore } from "vuex";
import account from "@/store/account.module";
import translation from "@/store/translation.module";
import config from "@/store/config.module";
import app from "@/store/app.module";
import VuexPersistence from "vuex-persist";

const vuexLocal = new VuexPersistence({
  storage: window.localStorage,
});

const store = createStore({
  state: {},
  mutations: {},
  actions: {},
  modules: {
    account,
    translation,
    app,
    config,
  },
  plugins: [vuexLocal.plugin],
});

export default store;
