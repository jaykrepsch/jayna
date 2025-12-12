// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.common with an alias.
import { createApp } from "vue";
import App from "./app.vue";

import store from "@/store/index";
import i18n from "./shared/config/i18n";
import router from "@/router/index";
import { installMockFallback } from '@/services/http-mock-fallback';


// Font Awesome Konfiguration
import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faPlus, faEdit, faTrash, faEye, faSearch, faTimes, faCheck, faExclamationTriangle } from '@fortawesome/free-solid-svg-icons';
import '@fortawesome/fontawesome-svg-core/styles.css';

// Icons zur Bibliothek hinzufügen
library.add(faPlus, faEdit, faTrash, faEye, faSearch, faTimes, faCheck, faExclamationTriangle);

// import "../content/css/tailwind.css";

import "@vuepic/vue-datepicker/dist/main.css";

// Überschreibe DatePicker-Styles für helleren Grau-Hintergrund
const datePickerStyles = `
  .dp__input {
    background-color: #f9fafb !important; /* 50% helleres Grau (bg-gray-50) */
    color: #000000 !important; /* schwarze Schrift */
    border-color: #d1d5db !important;
    border-radius: 0.375rem !important;
    border: 1px solid #d1d5db !important;
    min-height: 2.5rem !important;
    padding: 0.5rem 0.75rem !important; /* Gleichmäßiges Padding für Zentrierung */
    text-align: center !important; /* Text mittig zentrieren */
  }
  
  .dp__main .dp__input {
    background-color: #f9fafb !important; /* 50% helleres Grau (bg-gray-50) */
    color: #000000 !important; /* schwarze Schrift */
    border-color: #d1d5db !important;
    border-radius: 0.375rem !important;
    border: 1px solid #d1d5db !important;
    min-height: 2.5rem !important;
    padding: 0.5rem 0.75rem !important; /* Gleichmäßiges Padding für Zentrierung */
    text-align: center !important; /* Text mittig zentrieren */
  }
  
  .dp__main input.dp__input {
    background-color: #f9fafb !important; /* 50% helleres Grau (bg-gray-50) */
    color: #000000 !important; /* schwarze Schrift */
    border-color: #d1d5db !important;
    border-radius: 0.375rem !important;
    border: 1px solid #d1d5db !important;
    min-height: 2.5rem !important;
    padding: 0.5rem 0.75rem !important; /* Gleichmäßiges Padding für Zentrierung */
    text-align: center !important; /* Text mittig zentrieren */
  }
`;

// Füge die Styles zum DOM hinzu
const styleElement = document.createElement('style');
styleElement.textContent = datePickerStyles;
document.head.appendChild(styleElement);

// Initialize store modules
store.commit("config/init");
store.commit("app/disableLoading");
// Dev: schlanker Mock-Fallback für /api/overview/*
if (process.env.NODE_ENV !== 'production') {
  installMockFallback();
}

// Initialize account if token exists
const token = localStorage.getItem("jhi-authenticationToken") || sessionStorage.getItem("jhi-authenticationToken");
if (token) {
  store.commit("account/setToken", token);
}

// setupAxiosInterceptors(
//   (error) => {
//     const url = error.response?.config?.url;
//     const status = error.status || error.response.status;
//     if (status === 401) {
//       // Store logged out state.
//       store.commit("account/logout");
//       if (!url.endsWith("api/account") && !url.endsWith("api/authenticate")) {
//         // Ask for a new authentication
//         loginService.openLogin();
//         return;
//       }
//     }
//     console.log("Unauthorized!");
//     return Promise.reject(error);
//   },
//   (error) => {
//     console.log("Server error!");
//     return Promise.reject(error);
//   }
// );

const app = createApp(App).use(store).use(router).use(i18n);

// Font Awesome Komponente global registrieren
app.component('font-awesome-icon', FontAwesomeIcon);

app.mount("#app");
