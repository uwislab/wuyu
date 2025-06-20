<template>
  <div class="navbar">
    <hamburger id="hamburger-container" :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />

    <breadcrumb id="breadcrumb-container" class="breadcrumb-container" />

    <div class="right-menu">
      <template v-if="device!=='mobile'">
        <search id="header-search" class="right-menu-item" />

        <error-log class="errLog-container right-menu-item hover-effect" />

        <screenfull id="screenfull" class="right-menu-item hover-effect" />

        <el-tooltip content="Global Size" effect="dark" placement="bottom">
          <size-select id="size-select" class="right-menu-item hover-effect" />
        </el-tooltip>

      </template>

      <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="click">
        <div class="avatar-wrapper">
          <img :src="selectedAvatar" style="width: 40px;height:40px" class="user-avatar">
          <i class="el-icon-caret-bottom" />
        </div>
        <el-dropdown-menu slot="dropdown">
          <router-link :to="{path:'/Modify/index',query:{username:username,password:password}}">
            <el-dropdown-item>Modify password</el-dropdown-item>
          </router-link>
          <router-link :to="{path:'/Information/index',query:{username:username}}">
            <el-dropdown-item>Personal information</el-dropdown-item>
          </router-link>
          <el-dropdown-item divided @click.native="showAvatarDrawer">
            <span style="display:block;">更换头像</span>
          </el-dropdown-item>
          <el-dropdown-item divided @click.native="logout">
            <span style="display:block;">退出</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <!-- 头像抽屉 -->
    <el-drawer title="选择头像" :visible.sync="drawerVisible" direction="rtl" size="300px">
      <div class="avatar-list">
        <img v-for="avatar in avatarList" alt="" :key="avatar" :src="avatar" @click="selectAvatar(avatar)" class="avatar-item" />
      </div>
    </el-drawer>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import ErrorLog from '@/components/ErrorLog'
import Screenfull from '@/components/Screenfull'
import SizeSelect from '@/components/SizeSelect'
import Search from '@/components/HeaderSearch'

export default {
  data(){
    return{
      username:"",
      password:"",
      avatarList: [
        require('@/assets/img/header_avatar_1.png'),
        require('@/assets/img/header_avatar_2.png'),
        require('@/assets/img/header_avatar_3.png'),
        require('@/assets/img/header_avatar_4.png'),
        // 添加更多头像路径
      ],
      selectedAvatar: '',
      drawerVisible: false
    }
  },
  components: {
    Breadcrumb,
    Hamburger,
    ErrorLog,
    Screenfull,
    SizeSelect,
    Search
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar',
      'device'
    ])
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    async logout() {
      await this.$store.dispatch('user/resetToken') //非接口版本退出登录
      //await this.$store.dispatch('user/logout')
      this.$router.push(`/login?redirect=${this.$route.fullPath}`)
    },
    showAvatarDrawer() {
      this.drawerVisible = true;
    },
    selectAvatar(avatar) {
      this.selectedAvatar = avatar;
      localStorage.setItem('selectedAvatar', avatar); // 保存到本地存储
      this.drawerVisible = false;
    }
  },
  created(){
    // 尝试解决username空值异常
    this.username = this.$route.query.loginForm.username?this.$route.query.loginForm.username:'null';
    this.password = this.$route.query.loginForm.password;
    console.log("navbar的info："+userInfo.username);
    console.log("navbar的query："+this.$route.query)
    this.selectedAvatar = localStorage.getItem('selectedAvatar') || require('@/assets/img/header_avatar_1.png');
  },
  mounted() {
    this.selectedAvatar = localStorage.getItem('selectedAvatar') || require('@/assets/img/header_avatar_1.png');
    console.log('123-----------------------------------'); // 组件挂载后打印 "123"
    console.log(this.selectedAvatar)
  },

}
</script>

<style lang="scss" scoped>
.avatar-item {
  height: 80px;
  width: 80px;
  margin: 20px;
}
.avatar-item:hover {
  cursor: pointer;
}
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color:transparent;

    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background .3s;

        &:hover {
          background: rgba(0, 0, 0, .025)
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
