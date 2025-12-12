import api from "@/services/api.service";

const baseApiUrl = "api/sub-category-groups";

export default {
  find(id) {
    return new Promise((resolve, reject) => {
      if (!id || id === 'undefined') {
        reject(new Error('Invalid ID provided'));
        return;
      }
      api
        .get(`${baseApiUrl}/${id}`)
        .then((res) => {
          resolve(res.data);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },

  findByParentId(parentId) {
    return new Promise((resolve, reject) => {
      api
        .get(`${baseApiUrl}`, {
          params: { parentId },
        })
        .then((res) => {
          resolve(res.data);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },

  retrieve() {
    return new Promise((resolve, reject) => {
      api
        .get(baseApiUrl)
        .then((res) => {
          resolve(res);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },

  delete(id) {
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

  create(entity) {
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

  update(entity) {
    return new Promise((resolve, reject) => {
      api
        .put(`${baseApiUrl}/${entity.id}`, entity)
        .then((res) => {
          resolve(res.data);
        })
        .catch((err) => {
          reject(err);
        });
    });
  },

  partialUpdate(entity) {
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
};
