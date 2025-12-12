/** @type {import('tailwindcss').Config} */

const defaultTheme = require("tailwindcss/defaultTheme");

module.exports = {
  content: ["./src/main/webapp/index.html", "./src/main/webapp/app/**/*.vue"],
  safelist: ["w-3","h-3","w-4","h-4","w-5","h-5"],
  theme: {
    extend: {
      fontFamily: {
        sans: ["Inter var", ...defaultTheme.fontFamily.sans],
      },
      colors: {
        my_color: "#4dcb7a",
      },
    },
  },
  plugins: [
    require("@tailwindcss/forms"),
    require("@headlessui/tailwindcss")({ prefix: "ui" }),
  ],
};
