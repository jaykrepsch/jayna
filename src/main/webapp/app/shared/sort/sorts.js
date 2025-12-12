export default function buildPaginationQueryOpts(paginationQuery) {
  if (!paginationQuery) {
    return "";
  }

  const parts = [];

  // Filters
  if (paginationQuery.filter) {
    for (const idx of Object.keys(paginationQuery.filter)) {
      const queryConfig = paginationQuery.filter[idx];
      parts.push(`${queryConfig.fieldName}.${queryConfig.condition}=${queryConfig.value}`);
    }
  }

  // Sorts
  if (paginationQuery.sort) {
    for (const idx of Object.keys(paginationQuery.sort)) {
      parts.push(`sort=${paginationQuery.sort[idx]}`);
    }
  }

  // Page and size (include 0)
  if (paginationQuery.page !== undefined && paginationQuery.page !== null) {
    parts.push(`page=${paginationQuery.page}`);
  }
  if (paginationQuery.size !== undefined && paginationQuery.size !== null) {
    parts.push(`size=${paginationQuery.size}`);
  }

  return parts.join('&');
}
