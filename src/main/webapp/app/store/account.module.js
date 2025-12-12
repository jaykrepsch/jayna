export default {
  namespaced: true,
  state: () => ({
    token: null,
    logon: false,
    userIdentity: null,
    authenticated: false,
    ribbonOnProfiles: "",
    activeProfiles: "",
    authConfig: null,
  }),
  getters: {
    token: (state) => state.token,
    logon: (state) => state.logon,
    account: (state) => state.userIdentity,
    authenticated: (state) => state.authenticated,
    activeProfiles: (state) => state.activeProfiles,
    ribbonOnProfiles: (state) => state.ribbonOnProfiles,
    authConfig: (state) => state.authConfig,
  },
  mutations: {
    authenticate(state) {
      state.logon = true;
    },
    authenticated(state, identity) {
      state.userIdentity = identity;
      state.authenticated = true;
      state.logon = false;
    },
    logout(state) {
      state.userIdentity = null;
      state.authenticated = false;
      state.logon = false;
    },
    setActiveProfiles(state, profile) {
      state.activeProfiles = profile;
    },
    setRibbonOnProfiles(state, ribbon) {
      state.ribbonOnProfiles = ribbon;
    },
    setToken(state, token) {
      state.token = token;
    },
    setAuthConfig(state, authConfig) {
      state.authConfig = authConfig;
    },
  },
};
