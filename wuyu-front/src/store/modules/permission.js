import { asyncRoutes, constantRoutes } from '@/router'

/**
 * Use meta.role to determine if the current user has permission
 * @param roles
 * @param route
 */
function hasPermission(roles, route) {
  // 关键菜单详细日志 - 不管路径是什么，都输出关键路由的调试信息
  if (route.path === "/pression" || route.path === "/teacher" || route.path === "/user" || route.path === "/school-overview") {
    console.log('==== 关键菜单权限检查 ====');
    console.log('路由路径:', route.path);
    console.log('路由配置:', route);
    console.log('当前角色:', roles);
    
    // 如果没有meta或role属性，默认允许所有人访问
    if (!route.meta || !route.meta.role) {
      console.log('路由没有meta或role设置，默认允许所有人访问');
      return true;
    }
    
    const hasAccess = roles.some(role => {
      const roleId = parseInt(role, 10);
      const result = route.meta.role.includes(roleId);
      console.log(`角色 ${role}(${roleId}) 是否可访问路由 ${route.path}: ${result}`);
      return result;
    });
    
    console.log('最终访问权限:', hasAccess);
    console.log('====================');
    return hasAccess;
  }
  
  // 如果路由没有设置meta.role，则默认任何人都可以访问
  if (!route.meta || !route.meta.role) {
    return true;
  }
  
  // 特殊调试信息，针对关键路由
  if (route.path === "/pression" || route.path === "/teacher" || route.path === "/user") {
    console.log('====== 权限检查 ======');
    console.log('路由路径:', route.path);
    console.log('路由标题:', route.meta.title);
    console.log('需要角色:', route.meta.role);
    console.log('当前角色:', roles);
    
    const hasAccess = roles.some(role => {
      const roleId = parseInt(role, 10);
      const result = route.meta.role.includes(roleId);
      console.log(`角色 ${role}(${roleId}) 是否有权限: ${result}`);
      return result;
    });
    
    console.log('最终访问权限:', hasAccess);
    console.log('====================');
    return hasAccess;
  }
  
  // 检查用户角色是否有权限访问此路由
  return roles.some(role => {
    const roleId = parseInt(role, 10);
    // 检查roleId是否包含在路由允许的角色列表中
    return route.meta.role.includes(roleId);
  });
}

/**
 * 通过递归过滤异步路由表
 * @param routes asyncRoutes
 * @param roles
 */
export function filterAsyncRoutes(routes, roles) {
  const res = []
  //循环每一个路由
  routes.forEach(route => {
    const tmp = { ...route }
    //判断是否有权限
    if (hasPermission(roles, tmp)) {
      //判断是否有下限
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, roles)
      }
      res.push(tmp)
    } else {
      console.log('路由权限不足，被过滤掉：', tmp.path, '所需角色：', tmp.meta && tmp.meta.role ? tmp.meta.role : '无角色要求', '当前角色：', roles)
    }
  })
  console.log('过滤后的路由:', res.map(r => r.path))
  return res
}

const state = {
  routes: [],
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    //把过滤出来有权限的路由议添加到不需要权限的路由去
    state.routes = constantRoutes.concat(routes)
  }
}

const actions = {
  generateRoutes({ commit }, roles) {
    return new Promise(resolve => {
      //存的是有权限的路由，是一个数组
      let accessedRoutes

      //这里可以写自己的拦截逻辑
      if (roles.includes('admin')) {
        //admin所有左边路由都可以查看
        accessedRoutes = asyncRoutes || []
      } else {
        //通过所属的角色去过滤路由，生成新的路由表
        accessedRoutes = filterAsyncRoutes(asyncRoutes, roles)
      }

      commit('SET_ROUTES', accessedRoutes)
      resolve(accessedRoutes)
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions,
}
