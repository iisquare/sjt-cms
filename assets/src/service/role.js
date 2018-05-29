import base from '@/core/ServiceBase'

export default {
  list (param) {
    return base.post('/role/list', param)
  },
  delete (ids) {
    return base.post('/role/delete', {ids})
  }
}
