<template>
  <div v-if="!item.hidden && checkSpecialRoutes(item)">
    <template v-if="hasOneShowingChild(item.children,item) && (!onlyOneChild.children||onlyOneChild.noShowingChildren)&&!item.alwaysShow">
      <app-link v-if="onlyOneChild.meta" :to="resolvePath(onlyOneChild.path)">
        <el-menu-item :index="resolvePath(onlyOneChild.path)" :class="{'submenu-title-noDropdown':!isNest}">
          <item :icon="onlyOneChild.meta.icon||(item.meta&&item.meta.icon)" :title="onlyOneChild.meta.title" />
        </el-menu-item>
      </app-link>
    </template>

    <el-submenu v-else ref="subMenu" :index="resolvePath(item.path)" popper-append-to-body>
      <template slot="title">
        <item v-if="item.meta" :icon="item.meta && item.meta.icon" :title="item.meta.title" />
      </template>
      <sidebar-item
        v-for="child in item.children"
        :key="child.path"
        :is-nest="true"
        :item="child"
        :base-path="resolvePath(child.path)"
        class="nest-menu"
      />
    </el-submenu>
  </div>
</template>

<script>
import path from 'path'
import { isExternal } from '@/utils/validate'
import Item from './Item'
import AppLink from './Link'
import FixiOSBug from './FixiOSBug'
import { mapGetters } from 'vuex'

export default {
  name: 'SidebarItem',
  components: { Item, AppLink },
  mixins: [FixiOSBug],
  props: {
    // route object
    item: {
      type: Object,
      required: true
    },
    isNest: {
      type: Boolean,
      default: false
    },
    basePath: {
      type: String,
      default: ''
    }
  },
  data() {
    // To fix https://github.com/PanJiaChen/vue-admin-template/issues/237
    // TODO: refactor with render function
    this.onlyOneChild = null
    return {}
  },
  computed: {
    ...mapGetters([
      'roles'
    ])
  },
  methods: {
    // 特别检查一些关键路由，确保教务角色不能访问
    checkSpecialRoutes(item) {
      // 获取用户角色
      const userRoles = this.roles;
      console.log('当前路由:', item.path, '当前角色:', userRoles);
      
      // 检查是否为教务角色或admin角色（可能是教务但识别错误）
      const isJiaowu = userRoles.includes('1');
      const isAdmin = userRoles.includes('admin');
      
      // 这些路由对教务角色应该是禁止的
      const restrictedRoutes = ['/pression', '/teacher', '/user', '/school-overview'];
      
      // 如果是教务角色，阻止访问受限路由
      if (isJiaowu && restrictedRoutes.includes(item.path)) {
        console.log('教务角色阻止访问路由:', item.path);
        return false;
      }
      
      // 如果是admin角色但可能是教务的错误识别，检查页面URL或localStorage中的特殊标记
      if (isAdmin && restrictedRoutes.includes(item.path)) {
        // 从URL或cookie中确认是否是教务用户
        const isActuallyJiaowu = this.checkIfActuallyJiaowu();
        if (isActuallyJiaowu) {
          console.log('Admin被识别为教务，阻止访问路由:', item.path);
          return false;
        }
      }
      
      return true;
    },
    
    // 额外检查是否实际上是教务角色（通过URL、本地存储或其他方式）
    checkIfActuallyJiaowu() {
      // 从URL参数中获取角色信息
      const urlParams = new URLSearchParams(window.location.search);
      if (urlParams.get('role') === 'jiaowu') {
        return true;
      }
      
      // 从localStorage中获取用户名信息
      const username = localStorage.getItem('username');
      if (username && (username.includes('教务') || username.includes('jiaowu'))) {
        return true;
      }
      
      // 还可以根据项目实际情况添加其他判断方法
      
      // 紧急修复：如果是特定的用户名，直接认为是教务（可以根据实际情况配置）
      // 这里根据实际的教务用户名来配置
      const jiaowuUsernames = ['教务1', 'jiaowu', 'jw_user', 'jiaowu_admin'];
      const currentUsername = localStorage.getItem('username') || '';
      if (jiaowuUsernames.some(name => currentUsername.includes(name))) {
        return true;
      }
      
      return false;
    },
    hasOneShowingChild(children = [], parent) {
      const showingChildren = children.filter(item => {
        if (item.hidden) {
          return false
        } else {
          // Temp set(will be used if only has one showing child)
          this.onlyOneChild = item
          return true
        }
      })

      // When there is only one child router, the child router is displayed by default
      if (showingChildren.length === 1) {
        return true
      }

      // Show parent if there are no child router to display
      if (showingChildren.length === 0) {
        this.onlyOneChild = { ... parent, path: '', noShowingChildren: true }
        return true
      }

      return false
    },
    resolvePath(routePath) {
      if (isExternal(routePath)) {
        return routePath
      }
      if (isExternal(this.basePath)) {
        return this.basePath
      }
      return path.resolve(this.basePath, routePath)
    }
  }
}
</script>
