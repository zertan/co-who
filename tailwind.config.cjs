const colors = require('tailwindcss/colors')

/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./public/index.html",
    "./public/js/*.{js,ts,jsx,tsx,mjs}",
    "./public/js/**/*.{js,ts,jsx,tsx,mjs}",
    "./node_modules/flowbite/**/*.js"
  ],
  theme: {
  colors: {
      gray: colors.coolGray,
      blue: colors.lightBlue,
      red: colors.rose,
      pink: colors.fuchsia,
    },
    fontFamily: {
      sans: ['Graphik', 'sans-serif'],
      serif: ['Merriweather', 'serif'],
    },
    extend: {},
  },
  plugins: [
    require('flowbite/plugin')({
      charts: false,
      forms: true,
      
  })
  ]
}
