import { Authority } from "@/shared/security/authority";
import Tiles from "@/tiles/Tiles.vue";
import { getFeatureFlag } from "@/shared/config/featureFlags";

export default {
  path: "/",
  component: Tiles,
  children: [
    {
      path: "custom-grouptypes",
      name: "CustomGroupTypeList",
      component: () => import("@/tiles/CustomGroupTypeList.vue"),
      meta: {
        type: "tile",
      },
      beforeEnter: (to, from, next) => {
        if (getFeatureFlag('FEATURE_TILE_CUSTOM_GROUPTYPES', false)) {
          next();
        } else {
          next('/');
        }
      }
    },
    {
      path: "custom-grouptypes/:id/edit",
      name: "EditCustomGroupType",
      component: () => import("@/tiles/EditCustomGroupType.vue"),
    },
  ],
  meta: {
    authorities: [
      Authority.USER_LIGHT,
      Authority.USER_BASIC,
      Authority.USER_PRO,
      Authority.BUSINESS_LIGHT,
      Authority.BUSINESS_BASIC,
      Authority.BUSINESS_PRO,
    ],
  },
};
