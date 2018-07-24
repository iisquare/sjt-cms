import base from '@/core/ServiceBase'

export default {
  list (param) {
    return base.post('/article/list', param)
  },
  delete (ids) {
    return base.post('/article/delete', {ids})
  },
  config () {
    return base.post('/article/config')
  },
  save (param) {
    return base.post('/article/save', param)
  }
}
