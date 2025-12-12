import baseService from "./base.service";

const baseApiUrl = "api/null";

export default {
  find(id, queryParams) {
    return baseService.find(baseApiUrl, id, queryParams);
  },

  retrieve(paginationQuery) {
    return baseService.retrieve(baseApiUrl, paginationQuery);
  },

  query(params) {
    return baseService.retrieve(baseApiUrl, params);
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