// ESLint Flat Config (v9+) für Vue 3
// Hinweis: TypeScript-Dateien werden vorerst ignoriert, bis TS-Parser/Plugin ergänzt wird.

const pluginVue = require('eslint-plugin-vue');

module.exports = [
  {
    ignores: [
      'node_modules/**',
      'target/**',
      'build/**',
      'dist/**',
      'coverage/**',
      '**/*.ts',
    ],
  },
  ...pluginVue.configs['flat/strongly-recommended'],
  {
    files: ['**/*.js', '**/*.vue'],
    languageOptions: {
      ecmaVersion: 2022,
      sourceType: 'module',
      globals: {
        window: 'readonly',
        document: 'readonly',
        console: 'readonly',
      },
    },
    rules: {
      'vue/multi-word-component-names': 'off',
      'vue/html-self-closing': 'off',
      'vue/singleline-html-element-content-newline': 'off',
      'vue/multiline-html-element-content-newline': 'off',
      'vue/max-attributes-per-line': 'off',
      'vue/html-indent': 'off',
      'vue/v-slot-style': 'off',
      'vue/first-attribute-linebreak': 'off',
      'vue/component-definition-name-casing': 'off',
      'vue/require-default-prop': 'off',
      'vue/prefer-import-from-vue': 'off',
      'vue/no-v-text-v-html-on-component': 'off',
      'vue/no-mutating-props': 'off',
      'vue/require-prop-type-constructor': 'off',
      'vue/no-unused-vars': 'off',
      'vue/attribute-hyphenation': 'off',
      'vue/v-on-event-hyphenation': 'off',
      'vue/html-closing-bracket-newline': 'off',
      'vue/one-component-per-file': 'off',
      'vue/require-explicit-emits': 'off',
      'vue/no-template-shadow': 'off',
      'vue/no-multi-spaces': 'off',
    },
  },
];


