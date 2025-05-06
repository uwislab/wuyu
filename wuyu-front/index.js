import Vue from 'vue'
import Router from "vue-router"

Vue.use(Router)

/* Layout */
import Layout from "@/layout"

/**
 * 固定路由
 * 没有权限要求的基页
 * 所有角色均可以访问
 */
export const constantRoutes = [
  {
    path: "/redirect",
    component: Layout,
    hidden: true,
    children: [
      {
        path: "/redirect/:path(.*)",
        component: () => import("@/views/redirect/index")
      }
    ]
  },
  {
    path: "/login",
    component: () => import("@/views/login/index"),
    hidden: true
  },
  {
    path: "/auth-redirect",
    component: () => import("@/views/login/auth-redirect"),
    hidden: true
  },
  {
    path: "/404",
    component: () => import("@/views/error-page/404"),
    hidden: true
  },
  {
    path: "/401",
    component: () => import("@/views/error-page/401"),
    hidden: true
  },
  {
    path: "/",
    component: Layout,
    redirect: "/dashboard",
    children: [
      {
        path: "dashboard",
        component: () => import("@/views/questionnaire/mainpage"),
        name: "主页",
        meta: { title: "主页", icon: "tab", affix: true }
      },

    ]
  }
]

/**
 * 异步路由
 * 需要根据用户角色动态加载的路由
 */
export const asyncRoutes = [
  {
    path: "/questionnaire",
    component: Layout,
    redirect: "/questionnaire/list",
    meta: { title: "问卷管理", icon: "dashboard", affix: true, roles: ["教师", "班主任", "教务处", "校长"] },
    children: [
      {
        path: "list",
        component: () => import("@/views/questionnaire/list"),
        name: "问卷列表",
        meta: { title: "问卷列表", affix: true, roles: ["教师", "班主任", "教务处", "校长"] }
      },
      {
        path: "questionadd",
        component: () => import("@/views/questionnaire/questionadd"),
        name: "创建问卷",
        meta: { title: "创建问卷", affix: true, roles: ["教师", "班主任", "教务处", "校长"] }
      },
    ]
  },
  {
    path: "/student",
    component: Layout,
    redirect: "/student/list",
    meta: { title: "学生管理", icon: "dashboard", roles: ["班主任", "教务处", "校长"] },
    children: [
      {
        path: "list",
        component: () => import("@/views/student/list"),
        name: "学生信息",
        meta: { title: "学生信息", affix: true, roles: ["班主任", "教务处", "校长"] }
      },
      {
        path: "score/:id",
        component: () => import("@/views/student/score"),
        name: "五育评分",
        meta: { title: "五育评分", affix: true, roles: ["班主任", "教务处", "校长"] },
        hidden: true
      }
    ]
  },
  {
    path: "/class",
    component: Layout,
    redirect: "/class/one?id=1",
    meta: { title: "班级信息", icon: "dashboard", roles: ["班主任", "教务处", "校长"] },
    children: [
      {
        path: "one",
        query: {
          id: 1
        },
        component: () => import("@/views/class/one"),
        name: "班级信息",
        meta: { title: "一年级", affix: true, roles: ["班主任", "教务处", "校长"] }
      },
      {
        path: "two",
        query: {
          id: 2
        },
        component: () => import("@/views/class/two"),
        name: "班级信息",
        meta: { title: "二年级", affix: true, roles: ["班主任", "教务处", "校长"] }
      },
      {
        path: "three",
        component: () => import("@/views/class/three"),
        name: "班级信息",
        meta: { title: "三年级", affix: true, roles: ["班主任", "教务处", "校长"] }
      },
      {
        path: "four",
        component: () => import("@/views/class/four"),
        name: "班级信息",
        meta: { title: "四年级", affix: true, roles: ["班主任", "教务处", "校长"] }
      },
      {
        path: "five",
        component: () => import("@/views/class/five"),
        name: "班级信息",
        meta: { title: "五年级", affix: true, roles: ["班主任", "教务处", "校长"] }
      },
      {
        path: "six",
        component: () => import("@/views/class/six"),
        name: "班级信息",
        meta: { title: "六年级", affix: true, roles: ["班主任", "教务处", "校长"] }
      }
    ]
  },

  {
    path: "/activity",
    component: Layout,
    redirect: "/activity/list",
    meta: { title: "培养策划", icon: "dashboard", roles: ["班主任", "教务处", "校长"] },
    children: [
      {
        path: "index",
        component: () => import("@/views/activity/index"),
        name: "培养总览",
        meta: { title: "培养总览", affix: true, roles: [班主任", "教务处", "校长"] }
      },
      {
        path: "add",
        component: () => import("@/views/activity/add"),
        name: "发布活动",
        meta: { title: "发布活动", affix: true, roles: ["班主任", "教务处", "校长"] }
      },
      {
        path: "list",
        component: () => import("@/views/activity/list"),
        name: "活动管理",
        meta: { title: "活动管理", affix: true, roles: ["班主任", "教务处", "校长"] }
      }
    ]
  }
];

const createRouter = () => {

  var router = new Router({
    scrollBehavior: () => ({ y: 0 }),
    routes: constantRoutes
  });

  router.beforeEach((to, from, next) => {
    var permissionType = window.localStorage.setItem("PermissionType");
    var requiredType = to.meta.roles;

    if (requiredType) {
      if (requiredType.includes(permissionType)) {
        next();
      } else {
        next({path: "/login"});
      }
    } else {
      next();
    }
  });

  return router;
}

const router = createRouter();

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter();
  router.matcher = newRouter.matcher; // reset router
}

export default router;
