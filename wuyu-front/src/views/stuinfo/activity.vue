<template>
<!--<span>活动视图</span>-->
  <div>
    <div class="app-container">
      <div class="handle-box">
        <span>班级1</span>
      </div>
    <el-table
      ref="activityTable"
      border
      :data="list"
      style="width: 100%">
        <el-table-column label="活动编号" width="100" align="center">
          <template slot-scope="scope">{{scope.row.id}}</template>
        </el-table-column>
          <el-table-column label="班级"  align="center">
        <template slot-scope="scope">{{scope.row.classId}}</template>
          </el-table-column>
        <el-table-column label="活动名" width="300" align="center">
          <template slot-scope="scope">{{scope.row.theme}}</template>
        </el-table-column>
        <el-table-column label="活动内容" align="center">
          <template slot-scope="scope">{{scope.row.themecontent}}</template>
        </el-table-column>
        <el-table-column label="活动类型"  width="200" align="center">
          <template slot-scope="scope">{{scope.row.themetype}}</template>
        </el-table-column>
        <el-table-column label="教师平均分" width="200" align="center">
         <template slot-scope="scope">{{scope.row.teacherscore}}</template>
        </el-table-column>
        <el-table-column label="家长平均分" width="200" align="center">
          <template slot-scope="scope">{{scope.row.parentscore}}</template>
        </el-table-column>
        <el-table-column label="操作" width="200" align="center">
          <template slot-scope="scope">
            <el-button
            size="mini"
            @click="handleActinfo(scope.$index, scope.row)">学生活动详情
            </el-button>
          </template>
        </el-table-column>
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
  import { fetchAllViewList, deleteView, updateView, fetchViewList } from "../../api/classview" ;
  export default {
    name: "activity",
    data(){
      return {
        listQurey:{
          keyword: null,
          pageNum: 1,
          pageSize: 10
        },
        list: null,
        total: null
      }
    },
    created() {
      this.getViewList();
    },
    methods:{
      getViewList(){
        fetchAllViewList(this.listQuery).then(response =>{
          console.log(response);
          this.list = response.data.data.list;
          this.total = response.data.data.total;
          this.totalPage = response.data.data.totalPage;
          this.pageSize = response.data.data.pageSize;
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
    }
  }
//   export default {
//     name: "activity",
//       data() {
//         return {
//           listQuery: {
//             keyword: null,
//             pageNum: 1,
//             pageSize: 10
//           },
//           list: null,
//           total: null,
//         }
//       },
//     created() {
//       this.getViewList();
//     },
//     methods:{
//       getViewList(){
//         fetchAllViewList(this.listQuery).then(response =>{
//         console.log(response);
//         this.list = response.data.data.list;
//         this.total = response.data.data.total;
//         this.totalPage = response.data.data.totalPage;
//         this.pageSize = response.data.data.pageSize;
//        })
//       },
//       handleSizeChange(val) {
//         this.listQuery.pageNum = 1;
//         this.listQuery.pageSize = val;
//         this.getViewList();
//       },
//       handleCurrentChange(val) {
//         this.listQuery.pageNum = val;
//         this.getViewList();
//       },
//       handleActinfo(index,row){
//         this.$router.push({path:'/classview/actinfo',query:{id:row.id}})
//       }
// }
// }
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
