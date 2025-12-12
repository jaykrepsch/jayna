"use strict";
const path = require("path");
const { merge } = require("webpack-merge");
const { VueLoaderPlugin } = require("vue-loader");
const CopyWebpackPlugin = require("copy-webpack-plugin");
const HtmlWebpackPlugin = require("html-webpack-plugin");
const { hashElement } = require("folder-hash");
const MergeJsonWebpackPlugin = require("merge-jsons-webpack-plugin");

const webpack = require("webpack");
// const VueI18nPlugin = require("@intlify/unplugin-vue-i18n/webpack");

const { DefinePlugin, EnvironmentPlugin } = require("webpack");
const { vueLoaderConfig } = require("./vue.utils");
const config = require("./config");

function resolve(dir = "") {
  return path.join(__dirname, "..", dir);
}

module.exports = async (env, options) => {
  const development = options.mode === "development";
  const languagesHash = await hashElement(resolve("src/main/webapp/i18n"), {
    algo: "md5",
    encoding: "hex",
    files: { include: ["**/*.json"] },
  });

  return merge(
    {
          // Minimiere Dev-Logging von webpack intern
          infrastructureLogging: { level: 'error' },
          stats: 'errors-only',
      // transpileDependencies: ["vuex-persist"],
      mode: options.mode,
      context: resolve(),
      entry: {
        app: "./src/main/webapp/app/main.js",
      },
      output: {
        path: resolve("target/classes/static/"),
      },
      resolve: {
        extensions: [".js", ".vue", ".json"],
        alias: {
          vue: "@vue/runtime-dom",
          "@": resolve("src/main/webapp/app"),
        },
      },
      devServer: {
        static: {
          directory: "./target/classes/static/",
        },
        port: 9000,
        client: {
          logging: 'none',
          overlay: false,
          progress: false,
        },
        hot: true,
        setupMiddlewares: (middlewares, devServer) => {
          if (!devServer) return middlewares;
          // Entferne den Stub für /api/documents, damit echte Daten vom Backend kommen
          return middlewares;
        },
        proxy: [
          {
            context: ['/api', '/services', '/management', '/v3/api-docs', '/h2-console', '/auth'],
            target: 'http://127.0.0.1:8080',
            changeOrigin: true,
            secure: false,
            timeout: 120000,
          },
        ],
        historyApiFallback: true,
      },
      cache: {
        // 1. Set cache type to filesystem
        type: "filesystem",
        cacheDirectory: resolve("target/webpack"),
        buildDependencies: {
          // 2. Add your config as buildDependency to get cache invalidation on config change
          config: [
            __filename,
            path.resolve(__dirname, "config.js"),
            path.resolve(__dirname, "vue.utils.js"),
            path.resolve(
              __dirname,
              `webpack.${development ? "dev" : "prod"}.js`
            ),
            path.resolve(__dirname, "../.postcssrc.js"),
          ],
        },
      },
      module: {
        rules: [
          {
            test: /\.vue$/,
            loader: "vue-loader",
            options: vueLoaderConfig(!development),
          },
          {
            resourceQuery: /blockType=i18n/,
            type: "javascript/auto",
            loader: "@intlify/vue-i18n-loader",
          },
          {
            test: /\.(png|jpe?g|gif|svg|mp4|webm|ogg|mp3|wav|flac|aac|woff2?|eot|ttf|otf)/,
            type: "asset/resource",
          },
        ],
      },
      plugins: [
        // new EnvironmentPlugin({
        //   // Required by vuelidate https://github.com/vuelidate/vuelidate/issues/365
        //   BUILD: 'web',
        // }),
        // VueI18nPlugin({
        //   /* options */
        //   // locale messages resourece pre-compile option
        //   include: path.resolve(__dirname, "./src/main/webapp/i18n/**"),
        // }),
        new webpack.DefinePlugin({
          __VUE_OPTIONS_API__: true,
          __VUE_PROD_DEVTOOLS__: false,
          __VUE_PROD_HYDRATION_MISMATCH_DETAILS__: false,
        }),
        new DefinePlugin({
          I18N_HASH: JSON.stringify(languagesHash.hash),
          VERSION: JSON.stringify(config.version),
          SERVER_API_URL: JSON.stringify(config.serverApiUrl),
        }),
        new HtmlWebpackPlugin({
          base: "/",
          template: "./src/main/webapp/index.html",
        }),
        new VueLoaderPlugin(),
        new CopyWebpackPlugin({
          patterns: [
            {
              // https://github.com/swagger-api/swagger-ui/blob/v4.6.1/swagger-ui-dist-package/README.md
              context: require("swagger-ui-dist").getAbsoluteFSPath(),
              from: "*.{js,css,html,png}",
              to: "swagger-ui/",
              globOptions: { ignore: ["**/index.html"] },
            },
            {
              from: require.resolve("axios"),
              to: "swagger-ui/",
            },
            { from: "./src/main/webapp/swagger-ui/", to: "swagger-ui/" },
            { from: "./src/main/webapp/content/", to: "content/" },
            { from: "./src/main/webapp/favicon.ico", to: "favicon.ico" },
            {
              from: "./src/main/webapp/manifest.webapp",
              to: "manifest.webapp",
            },
            // jhipster-needle-add-assets-to-webpack - JHipster will add/remove third-party resources in this array
            { from: "./src/main/webapp/robots.txt", to: "robots.txt" },
          ],
        }),
        // new MergeJsonWebpackPlugin({
        //   output: {
        //     groupBy: [
        //       {
        //         pattern: "./src/main/webapp/i18n/de/**/*.json",
        //         fileName: "./i18n/de.json",
        //         transform: (parsedJson, filename) => {
        //           // Verwende die Datei direkt, ohne sie in jaynaApp zu wrappen
        //           return parsedJson;
        //         }
        //       },
        //       // jhipster-needle-i18n-language-webpack - JHipster will add/remove languages in this array
        //     ],
        //   },
        // }),
      ],
    },
    await require(`./webpack.${development ? "dev" : "prod"}`)(env, options)
    // jhipster-needle-add-webpack-config - JHipster will add custom config
  );
};
