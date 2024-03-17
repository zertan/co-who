// vite.config.js
import { defineConfig } from 'vite'
import { ViteMinifyPlugin } from 'vite-plugin-minify'
import { nodePolyfills } from 'vite-plugin-node-polyfills'

export default defineConfig({
  build: {
    commonjsOptions: { transformMixedEsModules: true }, // Change
    target: 'esnext' //browsers can handle the latest ES features
  },
  server: {
    hmr: false
  },
  plugins: [
     nodePolyfills({ include: ['global', 'stream']  }),
     ViteMinifyPlugin({})
  ],
})
