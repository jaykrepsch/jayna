import axios from 'axios';

// Einfacher Dev-Fallback, wenn das Backend-Feature abgeschaltet ist oder API 404 liefert
// Mockt nur die Overview-Endpunkte minimal.

let installed = false;

export function installMockFallback() {
  if (installed) return;
  installed = true;
  axios.interceptors.response.use(
    (res) => res,
    (error) => {
      const { config, response } = error || {};
      if (!config || !response) return Promise.reject(error);
      if (response.status === 404 && typeof config.url === 'string') {
        if (config.url.endsWith('/api/overview/kpis')) {
          return Promise.resolve({ data: { contracts: 124, documents: 3421, upcomingAppointments: 7 }, status: 200, statusText: 'OK', headers: {}, config });
        }
        if (config.url.endsWith('/api/overview/cashflow-trend')) {
          return Promise.resolve({ data: { values: [10,20,15,30,18,40,22,35,28,32,26,38] }, status: 200, statusText: 'OK', headers: {}, config });
        }
      }
      return Promise.reject(error);
    }
  );
}


