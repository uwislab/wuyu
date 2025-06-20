<template>
  <!--<span>活动视图</span>-->
  <div>
    <div class="app-container">
      <el-table ref="activityTable"
                border
                :data="list"
                style="width: 100%">
        <!--@selection-change="handleSelectionChange"-->

        <!--<el-table-column type="selection" width="60" align="center"></el-table-column>-->
        <el-table-column label="编号" width="100" align="center">
          <template slot-scope="scope">{{scope.row.id}}</template>
        </el-table-column>
        <el-table-column label="学号" width="200" align="center">
          <template slot-scope="scope">{{scope.row.snumber}}</template>
        </el-table-column>
        <el-table-column label="姓名" width="200" align="center">
          <template slot-scope="scope">{{scope.row.name}}</template>
        </el-table-column>
        <el-table-column label="学习习惯"   align="center">
          <template slot-scope="scope">{{handleStudy(scope.row.examJoinScore)}}</template>
        </el-table-column>
        <el-table-column label="总体表现"   align="center">
          <template slot-scope="scope">{{handlePer(scope.row.teacherscore+scope.row.parentscore)}}</template>
        </el-table-column>
        <el-table-column label="日常生活"  align="center">
          <template slot-scope="scope">{{"教师评价："+scope.row.teacherremark+","+"家长评价："+scope.row.parentremark}}</template>
        </el-table-column>
        <!--<el-table-column label="操作" width="200" align="center">-->
          <!--<template slot-scope="scope">-->
            <!--<el-button-->
              <!--size="mini"-->
              <!--@click="handleUpdate(scope.$index, scope.row)">学生活动详情-->
            <!--</el-button>-->
            <!--<el-button-->
              <!--size="mini"-->
              <!--type="danger"-->
              <!--@click="handleDelete(scope.$index, scope.row)">删除-->
            <!--</el-button>-->
          <!--</template>-->
        <!--</el-table-column>-->
      </el-table>
      <div class="pagination-container">
        <el-pagination
          background
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          layout="total, sizes,prev, pager, next,jumper"
          :page-size="listQuery.pageSize"
          :page-sizes="[5,10,20]"
          :current-page.sync="listQuery.pageNum"
          :total="total">
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
  import  {getScore,fetchAllStudentList,fetchAllClassList,deleteStudent,fetchStudentList,addScore} from "../../api/stuinfo";
  import {fetchAllViewList} from '../../api/classview'

  export default {
    name: "information",
    data() {
      return {
        listQuery: {
          keyword: null,
          pageNum: 1,
          pageSize: 10
        },
        list:null,
        score:null,
        total: null
      }
    },
    created() {
      this.getViewList();
    },

    methods:{
      getViewList(){
        fetchAllViewList(this.listQurey).then(response =>{
          console.log(response);
          this.list = response.data;
          this.total = response.data.total;
          this.totalPage = response.data.totalPage;
          this.pageSize = response.data.pageSize;
        })
      },
      handleSizeChange(val) {
        this.listQuery.pageNum = 1;
        this.listQuery.pageSize = val;
        this.getViewList();
      },
      handleCurrentChange(val) {
        this.listQuery.pageNum = val;
        this.getViewList();
      },
      handleStudy(val){
        if ( val < 60) return "学习习惯较差"
        if ( val <70) return "学习习惯一般"
        if ( val <80) return "学习习惯较好"
        if ( val <90) return "学习习惯好"
         return "学习习惯很好"
      },
      handlePer(val){
        if (val < 4) return "表现一般"
        if (val < 6) return "表现合格"
        if (val < 8) return "表现良好"
        return "表现很好"
      },
      getStuScore(id){
        getScore(id)
      }
    },
  }
</script>

<style scoped>
  .pagination-container {
    display: inline-block;
    float: right;
    margin-top: 20px;
  }
  .handle-box {
    margin-bottom: 20px;
  }

  .handle-select {
    width: 120px;
  }

  .handle-input {
    width: 300px;
    display: inline-block;
  }
  .table {
    width: 100%;
    font-size: 14px;
  }
  .red {
    color: #ff0000;
  }
  .mr10 {
    margin-right: 10px;
  }
  .table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
  }
</style>
