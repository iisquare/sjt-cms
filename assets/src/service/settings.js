import base from '@/core/ServiceBase'

export default {
  list () {
    return base.get('/settings/list')
  }
}
