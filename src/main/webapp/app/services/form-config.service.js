import baseService from "./base.service";

const baseApiUrl = "api/form-configs";

export default {
  find(id, queryParams) {
    return baseService.find(baseApiUrl, id, queryParams);
  },

  get(url, queryParams) {
    return baseService.get(url, queryParams);
  },

  retrieve(paginationQuery) {
    return baseService.retrieve(baseApiUrl, paginationQuery);
  },

  delete(id) {
    return baseService.delete(baseApiUrl, id);
  },

  create(entity) {
    return baseService.create(baseApiUrl, entity);
  },

  update(entity) {
    return baseService.update(baseApiUrl, entity);
  },

  partialUpdate(entity) {
    return baseService.partialUpdate(baseApiUrl, entity);
  },

  checkIfUserIsAllowedToAdd() {
    return baseService.checkIfUserIsAllowedToAdd(baseApiUrl);
  },
};
