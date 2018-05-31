import userService from '@/service/user'
import wrapper from '@/core/RequestWrapper'

// initial state
const state = {
  ready: false,
  data: null
}

// getters
const getters = {

}

// actions
const actions = {
  loadConfig ({ commit }) {
    wrapper.tips(userService.login()).then((response) => {
      if (response.code === 0) {
        commit('ready')
        commit('data', response.data)
      }
    })
  }
}

// mutations
const mutations = {
  ready () {
    state.ready = true
  },
  data (state, data) {
    state.data = data
  }
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}
