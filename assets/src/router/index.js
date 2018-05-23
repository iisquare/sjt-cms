import Vue from 'vue'
import Router from 'vue-router'
import UserLogin from '@/components/user/login'
import Error403 from '@/components/error/403'
import Error404 from '@/components/error/404'
import Error500 from '@/components/error/500'
import LayoutMain from '@/components/layout/main'
import DataUtil from '@/utils/data'

Vue.use(Router)

let routes = [{
  path: '/user/login',
  name: 'UserLogin',
  component: UserLogin
}, {
  path: '/error/403',
  name: 'Error403',
  component: Error403
}, {
  path: '/error/404',
  name: 'Error404',
  component: Error404
}, {
  path: '/error/500',
  name: 'Error500',
  component: Error500
}, {
  path: '/',
  name: 'LayoutMain',
  component: LayoutMain,
  children: [

  ]
}]

const router = new Router({ routes })

router.beforeEach((to, from, next) => {
  let userInfo = router.app.$store.state.user.info
  if (to.path === '/user/login') {
    if (DataUtil.empty(userInfo)) {
      next()
    } else {
      let url = to.params.redirect
      if (DataUtil.empty(url)) url = '/'
      next(url)
    }
  } else {
    if (DataUtil.empty(userInfo)) {
      next({
        path: '/user/login',
        query: { redirect: to.fullPath }
      })
    } else {
      next()
    }
  }
})

export default router
