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
  init ({ commit }, {$axios}) {
    $axios.get('/user/info').then(function (response) {
      commit('ready')
      console.log(response)
      commit('setInfo', {
        name: 'admin'
      })
    }).catch(function (error) {
      console.log(error)
    })
  },
  destroy ({ commit }) {
    commit('setInfo', null)
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
