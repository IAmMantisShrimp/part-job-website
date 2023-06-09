import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Element from 'element-ui'
import axios from 'axios'
import "./axios"
import "element-ui/lib/theme-chalk/index.css"
import 'font-awesome/css/font-awesome.min.css'

Vue.prototype.$axios = axios //
Vue.use(Element)

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
