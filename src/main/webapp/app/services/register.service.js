import api from "@/services/api.service";

export default {
  processRegistration(account) {
    return api.post("api/register", account);
  },
};
