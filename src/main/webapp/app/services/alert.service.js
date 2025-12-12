import store from "@/store/index";

export default {
  showDangerConfirmation(owner, title, content) {
    store.commit("app/triggerDangerConfirmationModal", {
      owner,
      titleKey: title || "error.modal.title",
      contentKey: content || "error.modal.content",
    });
  },

  hideDangerConfirmation() {
    store.commit("app/clearDangerConfirmationModal");
  },

  showError(instance, message, params) {
    const text = typeof message === 'string' ? message : (message?.message || 'Fehler');
    // Keine Konsolen-Ausgabe mehr
  },

  showSuccess(message) {
    const text = typeof message === 'string' ? message : (message?.message || 'Erfolg');
    // Keine Konsolen-Ausgabe mehr
  },

  showInfo(message) {
    const text = typeof message === 'string' ? message : (message?.message || 'Info');
    // Keine Konsolen-Ausgabe mehr
  },

  showHttpError(instance, httpErrorResponse) {
    const status = httpErrorResponse?.status;
    const dataMsg = httpErrorResponse?.data?.message;
    const fallback = status ? `HTTP ${status}` : 'Unbekannter Fehler';
    this.showError(instance, dataMsg || fallback);
  },
};
