import base from '@/core/ServiceBase'

export default {
  list (param) {
    return base.post('/comment/list', param)
  },
  delete (ids) {
    return base.post('/comment/delete', {ids})
  },
  config () {
    return base.post('/comment/config')
  },
  save (param) {
    return base.post('/comment/save', param)
  },
  info (param) {
    return base.post('/comment/info', param)
  }
}
