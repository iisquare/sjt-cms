import base from '@/core/ServiceBase'

export default {
  list (param) {
    return base.post('/upload/list', param)
  },
  delete (ids) {
    return base.post('/upload/delete', {ids})
  },
  config () {
    return base.post('/upload/config')
  },
  save (param) {
    return base.post('/upload/save', param)
  }
}
