const colors = require('tailwindcss/colors')

/** @type {import('tailwindcss').Config} */
export default {
  content: process.env.NODE_ENV == 'production' ? ["./public/js/main.js","./public.index.html","./node_modules/flowbite/**/*.{js,jsx,ts,tsx}", "./public/js/cljs-runtime/community.codo*.{js,jsx,ts,tsx}", "./node_modules/flowbite-datepicker/dist/**/*.js"] : ["./node_modules/flowbite/**/*.{js,jsx,ts,tsx}", "./public/js/cljs-runtime/co_who*.{js,jsx,ts,tsx}", "./public/js/cljs-runtime/*flowbite*"],

  theme: {
    fontFamily: {
      sans: ['Montserrat', 'sans-serif'],
      serif: ['Merriweather', 'serif'],
    },
    extend: {
      colors: {
        'gray-600': '#1E1E25',
        'gray-700': '#101014',
        'gray-800': '#080708',
        'blue-700': '#4FA1FF',
      },

    },
  },
  plugins: [
    require('flowbite/plugin')({
      charts: false,
      forms: true,
      
  })
  ]
}
