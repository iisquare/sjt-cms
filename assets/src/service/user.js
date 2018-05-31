import base from '@/core/ServiceBase'

export default {
  list (param) {
    return base.post('/user/list', param)
  },
  delete (ids) {
    return base.post('/user/delete', {ids})
  },
  config () {
    return base.post('/user/config')
  },
  save (param) {
    return base.post('/user/save', param)
  },
  login () {
    return base.post('/user/login')
  },
  logout () {
    return base.post('/user/logout')
  }
}
