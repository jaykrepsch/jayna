export const isSingleGroupType = (entityName) => {
  return entityName === "contact";
};

export const isSelectGroupType = (entityName) => {
  return entityName === "contact";
};

export const getFormConfig = (groupType) => {
  if (!groupType) return null;
  
  return {
    formTitle: groupType.label,
    parts: groupType.formParts || []
  };
};
