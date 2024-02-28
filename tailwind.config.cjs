/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./out/co_who/*.{js,ts,jsx,tsx,mjs}",
    "./out/co_who/**/*.{js,ts,jsx,tsx,mjs}",
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
