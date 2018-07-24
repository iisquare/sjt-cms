import base from '@/core/ServiceBase'

export default {
  list (param) {
    return base.post('/setting/list', param)
  },
  delete (ids) {
    return base.post('/setting/delete', {ids})
  },
  save (param) {
    return base.post('/setting/save', param)
  }
}
