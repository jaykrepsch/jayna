import api from "./api.service";

export default {
  activateAccount(key) {
    return api.get(`api/activate?key=${key}`);
  },
};
