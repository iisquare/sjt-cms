import base from '@/core/ServiceBase'

export default {
  config () {
    return base.get('/user/config')
  },
  login () {
    return base.post('/user/login')
  }
}
