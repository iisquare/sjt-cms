import base from '@/core/ServiceBase'

export default {
  list (param) {
    return base.post('/menu/list', param)
  },
  delete (ids) {
    return base.post('/menu/delete', {ids})
  },
  config () {
    return base.post('/menu/config')
  },
  save (param) {
    return base.post('/menu/save', param)
  }
}
