import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

//白名单
const whiteList = ['/login', '/auth-redirect'] // no redirect whitelist

//路由拦截
router.beforeEach(async(to, from, next) => {
  // start progress bar
  NProgress.start()
  // debugger
  if(to.path==='/answer'|| to.path=='/answersucess'){
    next();
    return;
}

  //设置目标页面的title(从目标路由的meta中获取title)
  document.title = getPageTitle(to.meta.title)

  // 获取store里面的登录令牌，有令牌，表示可以登录
  //从cookies中获取token
  const hasToken = getToken()

   //判断token是否存在
  if (hasToken) {
    if (to.path === '/login') {
      // 如果有登录，并且目标路径是 /login，路由到首页
      next({ path: '/' })
      NProgress.done() // hack: https://github.com/PanJiaChen/vue-element-admin/pull/2939
    } else {
      // determine whether the user has obtained his permission roles through getInfo
      // 从vuex中获取权限
      const hasRoles = store.getters.roles && store.getters.roles.length > 0
      // 如果存在就放行
      if (hasRoles) {
        next()
      } else {
        try {
          // get user info
          // note: roles must be a object array! such as: ['admin'] or ,['developer','editor']
          // 派发user/getInfo action, 获取当前用户的角色信息
          // roles 必须是一个数组
          // 获取用户的权限信息 存到vuex里面
          const { roles } = await store.dispatch('user/getInfo')

          // generate accessible routes map based on roles
          //根据用户的角色信息，派发到permission/generateRoutes action. 生成动态路由表
          const accessRoutes = await store.dispatch('permission/generateRoutes', roles)

          // dynamically add accessible routes
          // 挂载动态路由，添加到路由
          router.addRoutes(accessRoutes)

          // hack method to ensure that addRoutes is complete
          // set the replace: true, so the navigation will not leave a history record
          next({ ...to, replace: true })
        } catch (error) {
          // remove token and go to login page to re-login
          // 清空token
          await store.dispatch('user/resetToken')
          Message.error(error || 'Has Error')
          //跳转登录
          next(`/login?redirect=${to.path}`)
          NProgress.done()
        }
      }
    }
  } else {
    /* has no token*/
    //如果token不存在，判断是否存在白名单中
    if (whiteList.indexOf(to.path) !== -1) {
      // in the free login whitelist, go directly
      next()
    } else {
      // other pages that do not have permission to access are redirected to the login page.
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
