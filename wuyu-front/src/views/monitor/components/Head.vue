<template xmlns:display="http://www.w3.org/1999/xhtml">
  <div style="height: 50px; line-height: 50px; border-bottom: 1px solid #ccc; display: flex">
    <div style="width: 200px; padding-left: 30px; font-weight: bold; color: blue">后台管理</div>
    <div style="flex: 1"></div>
    <div style="width: 100px">
      <el-dropdown>
       <span class="el-dropdown-link">
           下拉菜单<i class="el-icon-caret-bottom el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <div @click="rea">
            <el-dropdown-item >密码显示</el-dropdown-item>
          </div>

        </el-dropdown-menu>
      </el-dropdown>
    </div>


  </div>
</template>

<script>
export default {
  name: "Head",


  data(){
    return{
      dialogVisible:false
    }

  },
  methods: {
    rea() {

      this.$alert("账号: wxzkopk8\n"+""+ "\n密码: f498e955", '宝塔密码', {
        confirmButtonText: '确定',

      });
    }
  }
}
</script>

<style scoped>

</style>
