<template>
  <div class="sss" style="height: 100%">
    <el-menu default-active="2" class="el-menu-vertical-demo" :default-openeds="openeds" @open="handleOpen" @close="handleClose" style="height: 100%">
      <el-submenu index="1">
        <template slot="title"><i class="el-icon-message"></i>系统管理</template>
        <el-menu-item-group>
          <el-menu-item index="1-4-1" @click="table">监控表单</el-menu-item>
        </el-menu-item-group>

        <el-menu-item-group>
          <el-menu-item index="1-4-2" @click="map" >监控地图</el-menu-item>
        </el-menu-item-group>

        <el-menu-item-group>
          <el-menu-item index="1-4-3" @click="stage">宝塔界面</el-menu-item>
        </el-menu-item-group>

        <el-submenu index="1-4">
          <template slot="title">监控图表</template>
          <el-menu-item index="1-4-1" @click="stage2">监控内存图表</el-menu-item>
          <el-menu-item index="1-4-2" @click="stage3">监控磁盘图表</el-menu-item>
        </el-submenu>

      </el-submenu>
    </el-menu>

  </div>
</template>

<script>

export default {
  name: "Aside",
  data(){
    return{
      url :'www.baidu.com',
      openeds: ['1'],
      uniqueOpened: false
    };

  },
  methods:{
    table(){
      this.$router.push("/Home2")
    },
    map(){
      this.$router.push("/Home")
    },
    stage(){

      window.location.href="http://47.93.178.174:8888/cf67739d";//当前标签页
      window.open("http://47.93.178.174:8888/cf67739d");//打开一个新的标签页
    },
    stage2(){

      this.$router.push("/Home4")

    }

    ,
    stage3(){

      this.$router.push("/Home5")

    }

  }

}
</script>

<style scoped>
.sss{
  position: fixed;
  height: 100%;
}


</style>
