import base from '@/core/ServiceBase'

export default {
  list (param) {
    return base.post('/role/list', param)
  },
  delete (ids) {
    return base.post('/role/delete', {ids})
  },
  config () {
    return base.post('/role/config')
  },
  save (param) {
    return base.post('/role/save', param)
  },
  tree (param) {
    return base.post('/role/tree', param)
  }
}
