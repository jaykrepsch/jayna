import store from "@/store/index";

export default {
  openLogin() {
    store.commit("app/openLoginModal");
  },
  closeLogin() {
    store.commit("app/hideLoginModal");
  },
};
