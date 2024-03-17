/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./public/index.html",
    "./public/js/*.{js,ts,jsx,tsx,mjs}",
    "./public/js/**/*.{js,ts,jsx,tsx,mjs}",
    "./node_modules/flowbite/**/*.js"
  ],
  theme: {
    extend: {},
  },
  plugins: [
    require('flowbite/plugin')({
      charts: false,
      forms: true,
      
  })
  ]
}
