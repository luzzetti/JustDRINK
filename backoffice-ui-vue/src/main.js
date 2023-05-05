import '@fortawesome/fontawesome-free/css/all.css'
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

import 'vuetify/dist/vuetify.min.css'
import { createVuetify } from 'vuetify'
import { components, directives } from 'vuetify/dist/vuetify'
import { aliases, fa } from 'vuetify/iconsets/fa'

const vuetify = createVuetify({
  components,
  directives,
  icons: {
    defaultSet: 'fa',
    aliases,
    sets: {
      fa,
    }
  },
})

const app = createApp(App)

app.use(router)
app.use(vuetify)
app.mount('#app')
