<template>
  <el-menu  :default-openeds="['1']"
    :default-active="1"
    style="height: 100%;"
    background-color="rgb(48,65,86)"
    text-color="#fff"
    router
    :unique-opened=true
    @open="handleMenuOpen">
    <div style="height: 60px; line-height: 60px; text-align: center;">
      <b style="color: white;">教师工作台</b>
    </div>
    <el-menu-item index="/teacher_workspace/teacherWork"><i class="el-icon-s-grid"></i>活动事项</el-menu-item>
    <el-menu-item index="/teacher_workspace/index"><i class="el-icon-reading"></i>工作事项</el-menu-item>
    <el-menu-item index="/teacher_workspace/teacherInfo"><i class="el-icon-user" ></i>个人信息</el-menu-item>
    <el-menu-item index="/teacher_workspace/passwordChange"><i class="el-icon-key"></i>修改密码</el-menu-item>

    <!-- 基础数据一级菜单 -->
    <el-submenu @open="handleBasicDataOpen" index="4">
      <template slot="title">
        <i class="el-icon-reading"></i>
        基础数据
      </template>
      <el-menu-item index="/teacher_workspace/classInfo">
        <i class="el-icon-s-order"></i>
        班级信息
      </el-menu-item>
      <el-menu-item index="/teacher_workspace/studentInfo">
        <i class="el-icon-s-data"></i>学生信息
      </el-menu-item>
      <el-menu-item index="/teacher_workspace/teacherInfo">
        <i class="el-icon-reading"></i>教师信息
      </el-menu-item>
      <el-menu-item index="/teacher_workspace/sportScore">
        <i class="el-icon-s-order"></i>体育成绩
      </el-menu-item>
      <el-menu-item index="/teacher_workspace/deploy">
        <i class="el-icon-s-operation"></i>基础数据运维
      </el-menu-item>
    </el-submenu>

    <!-- 数据展示一级菜单 -->
    <el-submenu ref="dataDisplayMenu" index="5">
      <template slot="title">
        <i class="el-icon-reading"></i>
        数据展示
      </template>
      <el-menu-item index="/teacher_workspace/demonstrate/xxzl">
        <i class="el-icon-s-order"></i>
        学校总览
      </el-menu-item>
      <el-menu-item index="/teacher_workspace/demonstrate/wydc">
        <i class="el-icon-s-data"></i>五育达成
      </el-menu-item>
      <el-menu-item index="/teacher_workspace/demonstrate/njqk">
        <i class="el-icon-reading"></i>年级情况
      </el-menu-item>
      <el-menu-item index="/teacher_workspace/demonstrate/njcz">
        <i class="el-icon-s-order"></i>年级成长
      </el-menu-item>
      <el-menu-item index="/teacher_workspace/demonstrate/yxbz">
        <i class="el-icon-s-operation"></i>优秀表彰
      </el-menu-item>
      <el-menu-item index="/teacher_workspace/demonstrate/bigDataIndx">
        <i class="el-icon-s-operation"></i>中控大屏
      </el-menu-item>
    </el-submenu>

    <el-submenu ref="dataDisplayMenu" index="6">
      <template slot="title">
        <i class="el-icon-reading"></i>
        五育诊断
      </template>
      <el-menu-item index="/teacher_workspace/diagnose/FiveupChart">
        <i class="el-icon-s-order"></i>
        总体预览
      </el-menu-item>
      <el-menu-item index="/teacher_workspace/diagnose/Di-results">
        <i class="el-icon-s-data"></i>总体诊断
      </el-menu-item>
      <el-menu-item index="/teacher_workspace/diagnose/FiveupManagement">
        <i class="el-icon-reading"></i>五育成绩管理
      </el-menu-item>
      <el-menu-item index="/teacher_workspace/diagnose/Kanalysis">
        <i class="el-icon-s-order"></i>智育诊断
      </el-menu-item>
      <el-menu-item index="/teacher_workspace/diagnose/SportsD">
        <i class="el-icon-s-operation"></i>体育诊断
      </el-menu-item>
      <el-menu-item index="/teacher_workspace/diagnose/Stugoal">
        <i class="el-icon-s-operation"></i>学生计划评价
      </el-menu-item>
    </el-submenu>
    </el-menu>
</template>

<script>
export default {
    name: "Aside",
    methods: {
        handleBasicDataOpen() {
            if (this.$refs.dataDisplayMenu && this.$refs.dataDisplayMenu.hovering) {
                this.$refs.dataDisplayMenu.handleSubMenuLeave();
            }
        }
}
}
</script>
