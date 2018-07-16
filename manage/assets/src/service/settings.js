import base from '@/core/ServiceBase'

export default {
  list (param) {
    return base.post('/settings/list', param)
  },
  delete (ids) {
    return base.post('/settings/delete', {ids})
  },
  save (param) {
    return base.post('/settings/save', param)
  }
}
