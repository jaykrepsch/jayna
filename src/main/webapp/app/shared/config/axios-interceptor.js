import store from "@/store/index";
import router from "@/router/index.js";
import axios from "axios";

const TIMEOUT = 60000;
const MAX_RETRIES = 5;
const RETRY_DELAY = 2000;

const bearerInterceptor = (config) => {
  const token = store.getters["account/token"];
  if (token) {
    if (!config.headers) {
      config.headers = {};
    }
    config.headers.Authorization = `Bearer ${token}`;
  }
  
  // Entferne die SERVER_API_URL-Logik, da der Webpack-Proxy die URLs handhabt
  // if (!config.url.startsWith('http')) {
  //   config.url = `${SERVER_API_URL}${config.url}`;
  // }
  
  config.timeout = TIMEOUT;
  return config;
};

const retryRequest = async (error) => {
  const config = error.config;
  
  if (!config || !config.retry) {
    return Promise.reject(error);
  }

  config.retry -= 1;
  
  if (config.retry === 0) {
    return Promise.reject(error);
  }

  await new Promise(resolve => setTimeout(resolve, RETRY_DELAY));
  return axios(config);
};

const unAuthenticated = (error) => {
  const url = error.response?.config?.url;
  const status = error.status || error.response?.status;

  if (process.env.NODE_ENV === 'development') {
    console.error('API Request failed:', {
      url: url,
      status: status,
      method: error.config?.method,
      data: error.response?.data,
      config: error.config,
      headers: error.config?.headers,
      params: error.config?.params
    });
  }

  if (status === 504 || error.code === 'ECONNABORTED') {
    return retryRequest(error);
  }

  if (status === 401) {
    if (process.env.NODE_ENV === 'development') {
      console.log("Unauthorized!");
    }
    store.commit("account/logout");
    if (!url.endsWith("api/account") && !url.endsWith("api/authenticate")) {
      router.push({ name: "Home" });
      return Promise.reject(error);
    }
    return Promise.reject(error);
  } else if (status === 400) {
    // Prüfe, ob entityName und errorKey in der Antwort vorhanden sind
    const entityName = error.response.data?.entityName;
    const errorKey = error.response.data?.errorKey;
    
    if (entityName && errorKey) {
      store.commit("app/showErrorModal", {
        titleKey: `jaynaApp.${entityName}.error.${errorKey}.title`,
        contentKey: `jaynaApp.${entityName}.error.${errorKey}.message`,
      });
    } else {
      // Fallback für generische 400 Fehler
      store.commit("app/showErrorModal", {
        titleKey: "error.modal.title",
        contentKey: error.response.data?.message || "Ein Fehler ist aufgetreten. Bitte überprüfen Sie Ihre Eingaben.",
      });
    }
    return Promise.reject(error);
  } else if (status === 404) {
    if (process.env.NODE_ENV === 'development') {
      console.error('Resource not found:', {
        url: url,
        method: error.config?.method,
        params: error.config?.params,
        headers: error.config?.headers
      });
    }
    store.commit("app/showErrorModal", {
      titleKey: "error.modal.title",
      contentKey: "Die angeforderte Ressource wurde nicht gefunden.",
    });
    return Promise.reject(error);
  } else if (status === 500) {
    store.commit("app/showErrorModal", {
      titleKey: "error.modal.title",
      contentKey: error.response?.data?.message || "Interner Serverfehler",
    });
    if (process.env.NODE_ENV === 'development') {
      console.log(error);
    }
    return Promise.reject(error);
  }

  return Promise.reject(error);
};

const authenticated = (response) => response;

export { bearerInterceptor, unAuthenticated, authenticated };
