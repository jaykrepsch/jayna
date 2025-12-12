import api from "@/services/api.service";
import store from "@/store/index";
import router from "@/router/index";
import translationService from "@/services/translation.service";

export default {
  init() {
    this.retrieveProfiles();
  },

  retrieveAccount() {
    return new Promise((resolve) => {
      api
        .get("api/account")
        .then((response) => {
          store.commit("account/authenticate");
          const account = response.data;
          if (account) {
            store.commit("account/authenticated", account);
            store.commit("config/init");
            if (
              store && store.getters && store.getters["translation/currentLanguage"] !== account.langKey
            ) {
              store.commit("translation/currentLanguage", account.langKey);
            }
            if (sessionStorage.getItem("requested-url")) {
              router.replace(sessionStorage.getItem("requested-url"));
              sessionStorage.removeItem("requested-url");
            }
          } else {
            store.commit("account/logout");
            if (router.currentRoute.path !== "/") {
              router.push("/");
            }
            sessionStorage.removeItem("requested-url");
          }
          translationService.refreshTranslation(
            store && store.getters ? store.getters["translation/currentLanguage"] : "de"
          );
          resolve(true);
        })
        .catch(() => {
          store.commit("account/logout");
          resolve(false);
        });
    });
  },

  hasAnyAuthorityAndCheckAuth(authorities) {
    if (typeof authorities === "string") {
      authorities = [authorities];
    }

    if (!this.authenticated || !this.userAuthorities) {
      // const token =
      //   localStorage.getItem("jhi-authenticationToken") ||
      //   sessionStorage.getItem("jhi-authenticationToken");
      const token = store && store.getters ? store.getters["account/token"] : null;
      if (
        !(store && store.getters && store.getters["account/account"]) &&
        !(store && store.getters && store.getters["account/logon"]) &&
        token
      ) {
        return this.retrieveAccount().then((resp) => {
          if (resp) {
            return this.checkAuthorities(authorities);
          }
          return Promise.resolve(false);
        });
      }
      return Promise.resolve(false);
    }

    return this.checkAuthorities(authorities);
  },

  authenticated() {
    return store && store.getters ? store.getters["account/authenticated"] : false;
  },

  userAuthorities() {
    return store && store.getters && store.getters["account/account"]
      ? store.getters["account/account"].authorities
      : undefined;
  },

  checkAuthorities(authorities) {
    if (this.userAuthorities) {
      for (const authority of authorities) {
        if (this.userAuthorities()?.includes(authority)) {
          return Promise.resolve(true);
        }
      }
    }
    return Promise.resolve(false);
  },

  retrieveAuthConfig() {
    api
      .get("api/auth-config")
      .then((resp) => {
        if (process.env.NODE_ENV === 'development') {
          console.log(resp.data);
        }
        // store.commit('account/authConfig', resp.data);
      })
      .catch((err) => {
        if (process.env.NODE_ENV === 'development') {
          console.log(err);
        }
      });
  },
};
