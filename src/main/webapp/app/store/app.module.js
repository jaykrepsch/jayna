export default {
  namespaced: true,
  state: () => ({
    loading: false,
    showLoginModal: false,
    showErrorModal: false,
    errorModalConfig: {
      titleKey: "error.modal.title",
      contentKey: "error.modal.content",
    },
    dangerConfirmation: {
      show: false,
      confirm: false,
      owner: null,
      titleKey: null,
      contentKey: null,
    },
    saveButtonEnabled: false,
  }),
  getters: {
    loading: (state) => state.loading,
    showLoginModal: (state) => state.showLoginModal,
    showErrorModal: (state) => state.showErrorModal,
    errorModalTitle: (state) => state.errorModalConfig.titleKey,
    errorModalContent: (state) => state.errorModalConfig.contentKey,
    showDangerConfirmationModal: (state) =>
      state.dangerConfirmation?.show || false,
    dangerConfirmationOwner: (state) => state.dangerConfirmation?.owner,
    dangerConfirmationConfirm: (state) => state.dangerConfirmation?.confirm,
    saveButtonEnabled: (state) => state.saveButtonEnabled,
  },
  mutations: {
    enableLoading(state) {
      state.loading = true;
    },
    disableLoading(state) {
      state.loading = false;
    },
    openLoginModal(state) {
      state.showLoginModal = true;
    },
    hideLoginModal(state) {
      state.showLoginModal = false;
    },
    showErrorModal(state, config) {
      state.showErrorModal = true;
      state.errorModalConfig.titleKey = config.titleKey;
      state.errorModalConfig.contentKey = config.contentKey;
    },
    hideErrorModal(state) {
      state.showErrorModal = false;
      state.errorModalConfig.titleKey = "error.modal.title";
      state.errorModalConfig.contentKey = "error.modal.content";
    },
    showDangerConfirmationModal(state, data) {
      state.dangerConfirmation.show = true;
      state.dangerConfirmation.owner = data.owner;
      state.dangerConfirmation.titleKey = data.titleKey;
      state.dangerConfirmation.contentKey = data.contentKey;
    },
    hideDangerConfirmationModal(state) {
      state.dangerConfirmation.show = false;
    },
    confirmDangerConfirmation(state) {
      state.dangerConfirmation.confirm = true;
    },
    showConfirmationModal(state, data) {
      state.confirmationModal = data;
    },
    enableSaveButton(state) {
      state.saveButtonEnabled = true;
    },
    disableSaveButton(state) {
      state.saveButtonEnabled = false;
    },
  },
};
