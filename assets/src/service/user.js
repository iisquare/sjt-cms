import base from '@/core/ServiceBase'

export default {
  info () {
    return base.request('/user/info')
  }
}
