import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import swal from 'sweetalert';
window.Swal = swal;
window.axios = require('axios')
createApp(App).use(router).mount('#app')
