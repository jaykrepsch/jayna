import api from "./api.service";

const baseApiUrl = "api/config";

export default {
  getEnumValues(name) {
    return api.get(`${baseApiUrl}/enums/${name}`);
  },

  getEnums() {
    return api.get(`${baseApiUrl}/enums`);
  },
};
