import Vue from 'vue'
import Router from 'vue-router'
import DataUtil from '@/utils/data'

Vue.use(Router)

let routes = [{
  path: '/startup',
  name: 'Startup',
  component: () => import(/* webpackChunkName: 'login' */ '@/components/layout/startup')
}, {
  path: '/user/login',
  name: 'UserLogin',
  component: () => import(/* webpackChunkName: 'login' */ '@/components/user/login')
}, {
  path: '/error/403',
  name: 'Error403',
  component: () => import(/* webpackChunkName: 'error' */ '@/components/error/403')
}, {
  path: '/error/404',
  name: 'Error404',
  component: () => import(/* webpackChunkName: 'error' */ '@/components/error/404')
}, {
  path: '/error/500',
  name: 'Error500',
  component: () => import(/* webpackChunkName: 'error' */ '@/components/error/500')
}, {
  path: '/',
  name: 'LayoutMain',
  component: () => import(/* webpackChunkName: 'main' */ '@/components/layout/main'),
  children: [

  ]
}, {
  path: '*',
  name: 'Others',
  redirect: '/error/404'
}]

const router = new Router({ routes })

router.beforeEach((to, from, next) => {
  let user = router.app.$store.state.user
  if (!user.ready) { // 用户状态未同步
    if (to.path === '/startup') {
      next()
    } else {
      next({
        path: '/startup',
        query: { redirect: to.fullPath }
      })
    }
  } else if (DataUtil.empty(user.info)) { // 用户未登陆
    if (to.path === '/user/login') {
      next()
    } else {
      next({
        path: '/user/login',
        query: { redirect: to.fullPath }
      })
    }
  } else { // 用户已登陆
    if (['/startup', '/user/login'].indexOf(to.path) === -1) {
      next()
    } else {
      let url = to.params.redirect
      if (DataUtil.empty(url)) url = '/'
      next(url)
    }
  }
})

export default router
