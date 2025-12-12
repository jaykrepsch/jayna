import * as validators from "@vuelidate/validators";
import i18n from "@/shared/config/i18n";

// or import { createI18nMessage } from '@vuelidate/validators'
const { createI18nMessage } = validators;

// extract the `t` helper, should work for both Vue 2 and Vue 3 versions of vue-i18n
const { t } = i18n.global || i18n;

// pass `t` and create your i18n message instance
const withI18nMessage = createI18nMessage({ t });

// wrap each validator.
export const required = withI18nMessage(validators.required);
export const email = withI18nMessage(
  validators.helpers.regex(/^[\w.!#$%&'*+/=?^_`{|}~-]+@[\w-]+(\.[\w-]+)*$|^user@localhost$/)
);
export const login = withI18nMessage(
  validators.helpers.regex(/^[a-z][a-z0-9]*(?:[_.-]?[a-z0-9])*$/)
);
export const sameAs = withI18nMessage(validators.sameAs, {
  withArguments: true,
});
// validators that expect a parameter should have `{ withArguments: true }` passed as a second parameter, to annotate they should be wrapped
export const minLength = withI18nMessage(validators.minLength, {
  withArguments: true,
});
export const maxLength = withI18nMessage(validators.maxLength, {
  withArguments: true,
});
