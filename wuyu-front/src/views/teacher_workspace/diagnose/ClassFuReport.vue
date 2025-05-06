<template>
  <el-container style="height: 100%; ">
    <el-aside width="200px" style="background-color: rgb(238, 241, 246); margin: 0;padding: 0; height: 100%;">
  <Aside />
</el-aside>
<el-container>
  <div class="maindiv clearfix">
    <div class="contentbox clearfix">
      <!--  体育评价量表的echart饼图  -->
      <div id="left">
        <div id="table_title">成绩详情表</div>
        <div id="search_block">
          <input></input>
        </div>
        <el-table id="class_fu_report" stripe border>
          <el-table-column label="序号" type="index" align="center" width="100"></el-table-column>
          <el-table-column label="学生姓名" align="center"></el-table-column>
          <el-table-column label="学号" align="center"></el-table-column>
          <el-table-column label="成绩" align="center" width="150"></el-table-column>
        </el-table>
      </div>
      <div id="right">
        <div id="b_left"></div>
        <div id="b_right"></div>
      </div>
    </div>
  </div></el-container>
  </el-container>

</template>

<script>
import Aside from "@/views/teacher_workspace/Aside"
export default {
  name: "ClassFuReport",
  components: {
      Aside
    },
}

</script>
<style scoped>
  #left {
    width: 50%;
    height: 100%;
    border: solid 1px #000000;
    float: left;
  }
  #right {
    width: 50%;
    height: 100%;
    border: solid 1px #000000;
    float: left;
  }
</style>
