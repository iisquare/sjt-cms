import Vue from 'vue'
import Router from 'vue-router'
import DataUtil from '@/utils/data'

Vue.use(Router)

let routes = [{
  path: '/startup',
  name: '欢迎使用',
  component: () => import(/* webpackChunkName: 'login' */ '@/components/layout/startup')
}, {
  path: '/user/login',
  name: '用户登录',
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
  component: () => import(/* webpackChunkName: 'main' */ '@/components/layout/main'),
  children: [{
    path: '/',
    name: '后台首页',
    component: () => import(/* webpackChunkName: 'error' */ '@/components/layout/welcome')
  }, {
    path: '/user/index',
    name: '用户管理',
    component: () => import(/* webpackChunkName: 'error' */ '@/components/user/index')
  }, {
    path: '/user/password',
    name: '修改密码',
    component: () => import(/* webpackChunkName: 'error' */ '@/components/user/password')
  }, {
    path: '/user/info',
    name: '个人信息',
    component: () => import(/* webpackChunkName: 'error' */ '@/components/user/info')
  }, {
    path: '/role/index',
    name: '角色管理',
    component: () => import(/* webpackChunkName: 'error' */ '@/components/role/index')
  }, {
    path: '/menu/index',
    name: '菜单管理',
    component: () => import(/* webpackChunkName: 'error' */ '@/components/menu/index')
  }, {
    path: '/resource/index',
    name: '资源管理',
    component: () => import(/* webpackChunkName: 'error' */ '@/components/resource/index')
  }, {
    path: '/setting/index',
    name: '配置管理',
    component: () => import(/* webpackChunkName: 'error' */ '@/components/setting/index')
  }, {
    path: '/upload/index',
    name: '文件管理',
    component: () => import(/* webpackChunkName: 'error' */ '@/components/upload/index')
  }, {
    path: '/category/index',
    name: '文章栏目',
    component: () => import(/* webpackChunkName: 'error' */ '@/components/category/index')
  }]
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
  } else if (DataUtil.empty(user.data) || DataUtil.empty(user.data.info)) { // 用户未登陆
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
