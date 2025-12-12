import { Authority } from "@/shared/security/authority";
import entities from "@/entities/entities.vue";

export default {
  path: "/",
  component: entities,
  children: [
    {
      path: "contract/view/:entityid",
      name: "ContractView",
      component: () => import("@/entities/common/common-view.vue"),
      props: true,
    },
    {
      path: "contact/view/:entityid",
      name: "ContactView",
      component: () => import("@/entities/contact/contact-view.vue"),
      props: true,
    },
    {
      path: "realestate/view/:entityid",
      name: "RealEstateView",
      component: () => import("@/entities/common/common-view.vue"),
      props: true,
    },
    {
      path: "mobility/view/:entityid",
      name: "MobilityView",
      component: () => import("@/entities/common/common-view.vue"),
      props: true,
    },
    {
      path: "financeaccount/view/:entityid",
      name: "FinanceAccountView",
      component: () => import("@/entities/common/common-view.vue"),
      props: true,
    },
    {
      path: "contract",
      name: "ContractList",
      component: () => import("@/entities/contract/overview.vue"),
    },
    {
      path: "realestate",
      name: "RealEstateList",
      component: () => import("@/entities/realestate/overview.vue"),
    },
    {
      path: "contact",
      name: "ContactList",
      component: () => import("@/entities/contact/contact-overview.vue"),
    },
    {
      path: "contact/create",
      name: "ContactCreate",
      component: () => import("@/entities/contact/contact-create.vue"),
    },
    {
      path: "financeaccount",
      name: "FinanceAccountList",
      component: () => import("@/entities/financeaccount/overview.vue"),
    },
    {
      path: "mobility",
      name: "MobilityList",
      component: () => import("@/entities/mobility/overview.vue"),
    },
    {
      path: "document",
      name: "DocumentList",
      component: () => import("@/entities/document/document-overview.vue"),
    },
    {
      path: "document/view/:entityid",
      name: "DocumentView",
      component: () => import("@/entities/document/document-view.vue"),
      props: true,
    },
    {
      path: "document/update/:entityid",
      name: "DocumentUpdate",
      component: () => import("@/entities/document/document-update.vue"),
      props: true,
    },
    {
      path: "document/create",
      name: "DocumentCreate",
      component: () => import("@/entities/document/document-create.vue"),
    },
    {
      path: "document/select",
      name: "DocumentSelect",
      component: () => import("@/entities/document/document-select.vue"),
    },
    {
      path: "document/analyze",
      name: "DocumentAnalyze",
      component: () => import("@/entities/document/document-analyze.vue"),
    },
    {
      path: "category/:id/select",
      name: "CategorySelect",
      component: () => import("@/entities/common/common-select.vue"),
    },
    {
      path: "category/:id/contact/create",
      name: "CategoryContactCreate",
      component: () => import("@/entities/common/common-create-form.vue"),
      props: true,
    },
    {
      path: "category/:id/sub-category/:subid",
      name: "CategoryCreate",
      component: () => import("@/entities/common/common-create.vue"),
      props: true,
    },
    {
      path: "category/:id/sub-category/:subid/sub-category-group/:subgrpid/group-type/:grptpid/create-form",
      name: "CreateForm",
      component: () => import("@/entities/common/common-create-form.vue"),
      props: true,
    },
    {
      path: "category/:id/sub-category/:subid/sub-category-group/:subgrpid/group-type/:grptpid/create",
      name: "CategoryCreateWithGroupType",
      component: () => import("@/entities/common/common-create.vue"),
      props: true,
    },
    {
      path: "category/:id/sub-category/:subid/sub-category-group/:subgrpid/group-type/:grptpid/update/:entityid",
      name: "CategoryUpdate",
      component: () => import("@/entities/common/common-update-container.vue"),
    },
    {
      path: "contract/update/:entityid",
      name: "ContractUpdate",
      component: () => import("@/entities/common/common-update.vue"),
      props: true,
    },
    {
      path: "contact/update/:entityid",
      name: "ContactUpdate",
      component: () => import("@/entities/contact/contact-update.vue"),
      props: true,
    },
    {
      path: "realestate/update/:entityid",
      name: "RealEstateUpdate",
      component: () => import("@/entities/common/common-update.vue"),
      props: true,
    },
    {
      path: "mobility/update/:entityid",
      name: "MobilityUpdate",
      component: () => import("@/entities/common/common-update.vue"),
      props: true,
    },
    {
      path: "financeaccount/update/:entityid",
      name: "FinanceAccountUpdate",
      component: () => import("@/entities/common/common-update.vue"),
      props: true,
    },
    {
      path: "category/:id/sub-category/:subid/sub-category-group/:subgrpid/group-type/:grptpid/view/:entityid",
      name: "CategoryView",
      component: () => import("@/entities/common/common-view-container.vue"),
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
