import baseService from "./base.service";

const baseApiUrl = "api/mobilities";

export default {
  find(id, queryParams) {
    // Stelle sicher, dass relations=true gesetzt ist, um Verknüpfungen zu laden
    const params = { relations: true, ...queryParams };
    return baseService.find(baseApiUrl, id, params);
  },

  retrieve(paginationQuery) {
    return baseService.retrieve(baseApiUrl, paginationQuery);
  },

  retrieveAll() {
    return baseService.retrieve(baseApiUrl + "/all");
  },

  retrieveAllForOverview() {
    return baseService.retrieve(baseApiUrl + "/overview");
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
