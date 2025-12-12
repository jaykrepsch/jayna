import { createRouter, createWebHistory } from "vue-router";

import store from "@/store/index.js";

import { Authority } from "@/shared/security/authority";
import { getFeatureFlag } from "@/shared/config/featureFlags";
import account from "@/router/account";
import entities from "@/router/entities";
import tiles from "@/router/tiles";
import AccountService from "@/services/account.service";
import LoginService from "@/services/login.service";

const routes = [
  {
    path: "/",
    component: () => import("@/core/layouts/AuthorizedLayout.vue"),
    children: [
      {
        path: "",
        name: "Home",
        component: () => import("@/pages/home.vue"),
        // Home ist sichtbar; Redirect nur optional später über Flag
      },
      // Platzhalter-Routen (ROLE_ADMIN)
      { path: "devices", name: "Devices", component: () => import("@/views/placeholder.vue"), meta: { authorities: [Authority.ADMIN], placeholder: { titleKey: 'jaynaApp.sidebar.devicesStar', ideas: 'Geräteverwaltung, Inventar, QR/Barcodes', bullets: ['Geräte anlegen, kategorisieren', 'Zustand, Kaufdatum, Garantie', 'Dokumente/Belege verknüpfen', 'Wartungsintervalle/Erinnerungen'] } } },
      { path: "items", name: "Items", component: () => import("@/views/placeholder.vue"), meta: { authorities: [Authority.ADMIN], placeholder: { titleKey: 'jaynaApp.sidebar.itemsStar', ideas: 'Gegenstände organisieren', bullets: ['Kategorien/Tags', 'Standort/Box', 'Verleih/Leihstatus', 'Bilder/Dokumente'] } } },
      { path: "clothes", name: "Clothes", component: () => import("@/views/placeholder.vue"), meta: { authorities: [Authority.ADMIN], placeholder: { titleKey: 'jaynaApp.sidebar.clothesStar', ideas: 'Kleidungskatalog', bullets: ['Größen/Farben', 'Saisons/Outfits', 'Zustand/Spendenliste', 'Pflegehinweise'] } } },
      { path: "software", name: "Software", component: () => import("@/views/placeholder.vue"), meta: { authorities: [Authority.ADMIN], placeholder: { titleKey: 'jaynaApp.sidebar.softwareStar', ideas: 'Lizenzen/Softwareverwaltung', bullets: ['Lizenzschlüssel/Accounts', 'Abo-Laufzeiten', 'Gerätezuordnung', 'Sicherheitsstatus'] } } },
      { path: "sports", name: "Sports", component: () => import("@/views/placeholder.vue"), meta: { authorities: [Authority.ADMIN], placeholder: { titleKey: 'jaynaApp.sidebar.sportsStar', ideas: 'Sportgeräte/Training', bullets: ['Geräte/Material', 'Trainingspläne', 'Wartung/Verbrauch', 'Ziele/Statistiken'] } } },
      { path: "travel", name: "Travel", component: () => import("@/views/placeholder.vue"), meta: { authorities: [Authority.ADMIN], placeholder: { titleKey: 'jaynaApp.sidebar.travelStar', ideas: 'Reiseplanung', bullets: ['Reiseziele/Trips', 'Dokumente/Checklisten', 'Buchungen/Kosten', 'Empfehlungen/Erinnerungen'] } } },
      { path: "animals", name: "Animals", component: () => import("@/views/placeholder.vue"), meta: { authorities: [Authority.ADMIN], placeholder: { titleKey: 'jaynaApp.sidebar.animalsStar', ideas: 'Haustiere', bullets: ['Impfungen/Termine', 'Futter/Bestand', 'Versicherungen', 'Dokumente/Impfpass'] } } },
      { path: "damages", name: "Damages", component: () => import("@/views/placeholder.vue"), meta: { authorities: [Authority.ADMIN], placeholder: { titleKey: 'jaynaApp.sidebar.damagesStar', ideas: 'Schadensfälle', bullets: ['Erfassung/Vorgang', 'Dokumente/Bilder', 'Status/Fristen', 'Versicherung/Meldung'] } } },
      { path: "calendar", name: "Calendar", component: () => import("@/views/placeholder.vue"), meta: { authorities: [Authority.ADMIN], placeholder: { titleKey: 'jaynaApp.sidebar.calendarStar', ideas: 'Kalender & Kontakte', bullets: ['Kontakt-Termine', 'Synchronisation', 'Erinnerungen', 'Verknüpfungen'] } } },
      { path: "tasks", name: "Tasks", component: () => import("@/views/placeholder.vue"), meta: { authorities: [Authority.ADMIN], placeholder: { titleKey: 'jaynaApp.sidebar.tasksStar', ideas: 'Aufgabenmanagement', bullets: ['Listen/Boards', 'Fälligkeiten', 'Zuweisungen', 'Verknüpfungen zu Entitäten'] } } },
      { path: "notes", name: "Notes", component: () => import("@/views/placeholder.vue"), meta: { authorities: [Authority.ADMIN], placeholder: { titleKey: 'jaynaApp.sidebar.notesStar', ideas: 'Notizen/Wissensbasis', bullets: ['Markdown/Anhänge', 'Tags/Suche', 'Verknüpfungen', 'Revisionen'] } } },
      { path: "contract-status", name: "ContractStatus", component: () => import("@/views/placeholder.vue"), meta: { authorities: [Authority.ADMIN], placeholder: { titleKey: 'jaynaApp.sidebar.contractStatusStar', ideas: 'Vertragsstatus & Fristen', bullets: ['Laufzeiten/Reminder', 'Statusregeln', 'Dashboards', 'Exports'] } } },
      { path: "data-quality", name: "DataQuality", component: () => import("@/views/placeholder.vue"), meta: { authorities: [Authority.ADMIN], placeholder: { titleKey: 'jaynaApp.sidebar.dataQualityStar', ideas: 'Datenqualität', bullets: ['Vollständigkeit', 'Validierungen', 'Duplikate', 'Automatische Korrekturen'] } } },
      { path: "document-inbox", name: "DocumentInbox", component: () => import("@/views/placeholder.vue"), meta: { authorities: [Authority.ADMIN], placeholder: { titleKey: 'jaynaApp.sidebar.documentInboxStar', ideas: 'Dokumenten-Inbox', bullets: ['Eingang/Import', 'OCR/Erkennung', 'Zuordnung', 'Batch-Aktionen'] } } },
      {
        path: "forbidden",
        name: "Forbidden",
        component: () => import("@/pages/error.vue"),
        meta: { error403: true },
      },

      ...account,
      entities,
      tiles,
      {
        path: "relations-overview",
        name: "RelationsOverview",
        component: () => import("@/views/relations-overview.vue"),
      },
    ],
  },
  {
    path: "/:pathMatch(.*)*",
    name: "NotFound",
    component: () => import("@/pages/404.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior(to, from, savedPosition) {
    return { top: 0, left: 0 };
  },
});

router.beforeEach(async (to, from, next) => {
  store.commit("app/disableLoading");
  LoginService.closeLogin();
  if (!to.matched.length) {
    next("/not-found");
  } else if (to.meta?.authorities?.length > 0) {
    AccountService.hasAnyAuthorityAndCheckAuth(to.meta.authorities).then(
      (value) => {
        if (!value) {
          sessionStorage.setItem("requested-url", to.fullPath);
          next("/forbidden");
        } else {
          next();
        }
      }
    );
  } else {
    // no authorities, so just proceed
    next();
  }
});

export default router;
