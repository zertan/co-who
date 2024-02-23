/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./out/js/src/main/*.{js,ts,jsx,tsx,mjs}",
    "./out/js/src/main/**/*.{js,ts,jsx,tsx,mjs}",
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
