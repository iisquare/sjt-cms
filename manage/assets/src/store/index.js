import Vue from 'vue'
import Vuex from 'vuex'
import user from './modules/user'
import icon from './modules/icon'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    user,
    icon
  }
})
