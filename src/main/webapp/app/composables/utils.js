const useGetLabel = (field, formName) => {
  if (!field || !formName) return '';
  if (field.label) return field.label;
  return `jaynaApp.${formName}.fields.${field.name}`;
};

const useGetPlaceholder = (field, formName) => {
  if (!field || !formName) return '';
  if (field.placeholder) return field.placeholder;
  return `jaynaApp.${formName}.fields.${field.name}`;
};

const useGetService = (serviceName) => {
  return import(`@/services/${serviceName}.service`);
};

export { useGetLabel, useGetService };
