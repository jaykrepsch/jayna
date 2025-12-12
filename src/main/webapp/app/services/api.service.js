import axios from "axios";

import {
  bearerInterceptor,
  unAuthenticated,
  authenticated,
} from "@/shared/config/axios-interceptor";

const client = axios.create({
  baseURL: SERVER_API_URL,
  timeout: 30000,
  headers: {
    'Content-Type': 'application/json',
    'Accept': 'application/json'
  }
});

client.interceptors.request.use((config) => {
  config.retry = 3; // Anzahl der Wiederholungsversuche
  return bearerInterceptor(config);
});

client.interceptors.response.use(authenticated, unAuthenticated);

export default client;
