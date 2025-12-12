import api from "@/services/api.service";
import buildPaginationQueryOpts from "@/shared/sort/sorts";

export default {
  find(baseApiUrl, id, queryParams) {
    return new Promise((resolve, reject) => {
      api
        .get(`${baseApiUrl}/${id}`, { params: queryParams })
        .then((res) => {
          resolve(res.data);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },

  get(url, queryParams) {
    return new Promise((resolve, reject) => {
      api
        .get(`${url}`, { params: queryParams })
        .then((res) => {
          resolve(res.data);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },

  findWithRelations(baseApiUrl, id) {
    return new Promise((resolve, reject) => {
      api
        .get(`${baseApiUrl}/${id}`, { params: { relations: true } })
        .then((res) => {
          resolve(res.data);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },

  retrieve(baseApiUrl, paginationQuery) {
    const url = paginationQuery ? `${baseApiUrl}?${buildPaginationQueryOpts(paginationQuery)}` : baseApiUrl;
    return new Promise((resolve, reject) => {
      api
        .get(url)
        .then((res) => {
          let data = [];
          if (res.data && Array.isArray(res.data)) {
            data = res.data;
          } else if (res.data && res.data.content && Array.isArray(res.data.content)) {
            data = res.data.content;
          }
          resolve({ data: data, headers: res.headers });
        })
        .catch((err) => {
          if (process.env.NODE_ENV === 'development') {
            console.error(`Error retrieving data from ${url}:`, err);
          }
          reject(err);
        });
    });
  },

  delete(baseApiUrl, id) {
    return new Promise((resolve, reject) => {
      api
        .delete(`${baseApiUrl}/${id}`)
        .then((res) => {
          resolve(res);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },

  create(baseApiUrl, entity) {
    return new Promise((resolve, reject) => {
      api
        .post(`${baseApiUrl}`, entity)
        .then((res) => {
          resolve(res.data);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },

  update(baseApiUrl, entity) {
    return new Promise((resolve, reject) => {
      api
        .put(`${baseApiUrl}/${entity.id}`, entity)
        .then((res) => {
          resolve(res.data);
        })
        .catch((err) => {
          if (process.env.NODE_ENV === 'development') {
            console.error('Update error details:', {
              url: `${baseApiUrl}/${entity.id}`,
              status: err.response?.status,
              statusText: err.response?.statusText,
              data: err.response?.data,
              entity: entity
            });
          }
          reject(err);
        });
    });
  },

  partialUpdate(baseApiUrl, entity) {
    return new Promise((resolve, reject) => {
      api
        .patch(`${baseApiUrl}/${entity.id}`, entity)
        .then((res) => {
          resolve(res.data);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },

  checkIfUserIsAllowedToAdd(baseApiUrl) {
    return new Promise((resolve, reject) => {
      api
        .get(`${baseApiUrl}/check`)
        .then((res) => {
          // Handle both direct boolean response and wrapped response
          if (typeof res.data === 'boolean') {
            resolve(res.data);
          } else if (res.data && typeof res.data.data === 'boolean') {
            resolve(res.data.data);
          } else {
            resolve(true); // Default to true if response format is unexpected
          }
        })
        .catch((err) => {
          if (process.env.NODE_ENV === 'development') {
            console.error(`Error checking if user is allowed to add:`, err);
          }
          resolve(true); // Default to true on error to allow adding
        });
    });
  },
};
