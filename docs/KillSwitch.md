Kill‑Switch Übersicht (Feature Flags)

Dieser Leitfaden beschreibt, wie die Übersicht/Funktionen gezielt ein‑/ausgeschaltet werden können – Frontend (UI) und Backend (Spring Boot).

Frontend (UI)

- Flags in `app/shared/config/featureFlags.js`.
- Override via LocalStorage (DevTools):

```js
localStorage.setItem('flags.FEATURE_OVERVIEW', 'true');
localStorage.setItem('flags.FEATURE_OVERVIEW', 'false');
localStorage.removeItem('flags.FEATURE_OVERVIEW');
```

Backend (Spring Boot)

- Property: `feature.overview.enabled` (siehe `src/main/resources/config/application.yml`).
- Start (Windows/Pwsh):

```bash
mvn spring-boot:run "-Dspring-boot.run.profiles=dev" "-Dspring-boot.run.arguments=--feature.overview.enabled=true"
```

Dev‑Fallback (UI)

- Axios‑Interceptor liefert Stub‑Daten, wenn Endpunkte fehlen.
- Datei: `app/services/http-mock-fallback.js` (aktiv in Nicht‑Prod).

Schnellcheck

1) UI‑Flag prüfen: `localStorage.getItem('flags.FEATURE_OVERVIEW')`
2) Backend‑Health: `/api/overview/health`
3) Prozesse ggf. neu starten


