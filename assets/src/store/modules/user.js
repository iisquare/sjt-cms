// initial state
const state = {
  info: null
}

// getters
const getters = {
  info: state => state.info
}

// actions
const actions = {
  init ({ commit }) {
    commit('setInfo', {
      name: 'admin'
    })
  },
  destroy ({ commit }) {
    commit('setInfo', null)
  }
}

// mutations
const mutations = {
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
