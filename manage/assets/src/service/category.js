import base from '@/core/ServiceBase'

export default {
  list (param) {
    return base.post('/category/list', param)
  },
  delete (ids) {
    return base.post('/category/delete', {ids})
  },
  config () {
    return base.post('/category/config')
  },
  save (param) {
    return base.post('/category/save', param)
  }
}
