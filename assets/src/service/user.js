import base from '@/service/base'

export default {
  info () {
    return base.request('/user/info')
  }
}
