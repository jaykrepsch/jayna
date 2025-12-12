import ConfigService from "@/services/config.service";

export default {
  namespaced: true,
  state: {
    paymentPattern: null,
    paymentType: null,
    entityState: null,
    designTypeClass: null,
    heatingType: null,
    roofType: null,
    direction: null,
    gender: null,
    title: null,
    maritalStatus: null,
    rank: null,
    communicationType: null,
    salutation: null,
    addressType: null,
    propertyType: null,
    buildingType: null,
    coverageType: null,
  },
  getters: {
    paymentPattern: (state) => state.paymentPattern,
    paymentType: (state) => state.paymentType,
    entityState: (state) => state.entityState,
    designTypeClass: (state) => state.designTypeClass,
    heatingType: (state) => state.heatingType,
    roofType: (state) => state.roofType,
    direction: (state) => state.direction,
    gender: (state) => state.gender,
    title: (state) => state.title,
    maritalStatus: (state) => state.maritalStatus,
    rank: (state) => state.rank,
    communicationType: (state) => state.communicationType,
    salutation: (state) => state.salutation,
    addressType: (state) => state.addressType,
    propertyType: (state) => state.propertyType,
    buildingType: (state) => state.buildingType,
    coverageType: (state) => state.coverageType,
  },
  mutations: {
    init(state) {
      const toLowerCase = (str) => {
        return str.charAt(0).toLowerCase() + str.slice(1);
      };

      ConfigService.getEnums().then((response) => {
        response.data.forEach((item) => {
          const itemName = toLowerCase(item.name);
          state[itemName] = item.items;
        });
      }).catch(() => {
      });
    },
  },
};
