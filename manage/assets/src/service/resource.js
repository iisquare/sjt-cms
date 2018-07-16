import base from '@/core/ServiceBase'

export default {
  list (param) {
    return base.post('/resource/list', param)
  },
  delete (ids) {
    return base.post('/resource/delete', {ids})
  },
  config () {
    return base.post('/resource/config')
  },
  save (param) {
    return base.post('/resource/save', param)
  }
}
