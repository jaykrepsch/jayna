import api from "@/services/api.service";

const baseApiUrl = "api/users";

export default {
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
};
