import base from '@/core/ServiceBase'

export default {
  list () {
    return base.get('/role/list')
  }
}
