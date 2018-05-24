// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import Vue from 'vue'
import store from './store'
import App from './App'
import router from './router'
import 'font-awesome/css/font-awesome.min.css'

Vue.use(ElementUI)
Vue.config.productionTip = false

store.dispatch('user/init')

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})