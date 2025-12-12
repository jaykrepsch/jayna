/**
 * Service für die temporäre Speicherung von Dokumentendaten während des Erstellungsprozesses
 */
export default class DocumentTempStorageService {
  static STORAGE_KEY = 'document_creation_temp_data';
  static SESSION_KEY = 'document_creation_session';

  /**
   * Speichert temporäre Daten für den Dokumentenerstellungsprozess
   */
  static saveTempData(data) {
    try {
      const tempData = {
        ...data,
        timestamp: Date.now(),
        sessionId: this.getSessionId()
      };
      
      localStorage.setItem(this.STORAGE_KEY, JSON.stringify(tempData));
      console.log('Temporäre Dokumentendaten gespeichert:', tempData);
    } catch (error) {
      console.error('Fehler beim Speichern der temporären Daten:', error);
    }
  }

  /**
   * Lädt temporäre Daten für den Dokumentenerstellungsprozess
   */
  static loadTempData() {
    try {
      const stored = localStorage.getItem(this.STORAGE_KEY);
      if (!stored) return null;

      const tempData = JSON.parse(stored);
      const sessionId = this.getSessionId();

      // Prüfe ob die Daten zur aktuellen Session gehören
      if (tempData.sessionId !== sessionId) {
        console.log('Temporäre Daten gehören zu einer anderen Session, werden gelöscht');
        this.clearTempData();
        return null;
      }

      // Prüfe ob die Daten zu alt sind (24 Stunden)
      const maxAge = 24 * 60 * 60 * 1000; // 24 Stunden in Millisekunden
      if (Date.now() - tempData.timestamp > maxAge) {
        console.log('Temporäre Daten sind zu alt, werden gelöscht');
        this.clearTempData();
        return null;
      }

      console.log('Temporäre Dokumentendaten geladen:', tempData);
      return tempData;
    } catch (error) {
      console.error('Fehler beim Laden der temporären Daten:', error);
      return null;
    }
  }

  /**
   * Löscht temporäre Daten
   */
  static clearTempData() {
    try {
      localStorage.removeItem(this.STORAGE_KEY);
      console.log('Temporäre Dokumentendaten gelöscht');
    } catch (error) {
      console.error('Fehler beim Löschen der temporären Daten:', error);
    }
  }

  /**
   * Generiert eine Session-ID für den aktuellen Erstellungsprozess
   */
  static getSessionId() {
    let sessionId = sessionStorage.getItem(this.SESSION_KEY);
    if (!sessionId) {
      sessionId = 'doc_session_' + Date.now() + '_' + Math.random().toString(36).substr(2, 9);
      sessionStorage.setItem(this.SESSION_KEY, sessionId);
    }
    return sessionId;
  }

  /**
   * Löscht die Session-ID (wird beim Abbrechen oder erfolgreichem Speichern aufgerufen)
   */
  static clearSession() {
    try {
      sessionStorage.removeItem(this.SESSION_KEY);
      console.log('Dokumentenerstellungs-Session gelöscht');
    } catch (error) {
      console.error('Fehler beim Löschen der Session:', error);
    }
  }

  /**
   * Speichert Daten für einen spezifischen Schritt
   */
  static saveStepData(step, data) {
    const tempData = this.loadTempData() || {};
    tempData[step] = {
      ...data,
      timestamp: Date.now()
    };
    this.saveTempData(tempData);
  }

  /**
   * Lädt Daten für einen spezifischen Schritt
   */
  static loadStepData(step) {
    const tempData = this.loadTempData();
    return tempData?.[step] || null;
  }

  /**
   * Prüft ob temporäre Daten vorhanden sind
   */
  static hasTempData() {
    return this.loadTempData() !== null;
  }

  /**
   * Löscht Daten für einen spezifischen Schritt
   */
  static clearStepData(step) {
    const tempData = this.loadTempData();
    if (tempData && tempData[step]) {
      delete tempData[step];
      this.saveTempData(tempData);
      console.log(`Temporäre Daten für Schritt '${step}' gelöscht`);
    }
  }
}
