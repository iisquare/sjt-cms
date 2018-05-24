import userService from '@/service/user'
import wrapper from '@/core/RequestWrapper'

// initial state
const state = {
  ready: false,
  info: null
}

// getters
const getters = {
  info: state => state.info
}

// actions
const actions = {
  init ({ commit }) {
    wrapper.tips(userService.info()).then((response) => {
      if (response.code === 0) {
        commit('ready')
        commit('setInfo', response.data)
      }
    })
  }
}

// mutations
const mutations = {
  ready () {
    state.ready = true
  },
  setInfo (state, info) {
    state.info = info
  }
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}
