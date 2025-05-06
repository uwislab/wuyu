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
          <template slot-scope="scope">{{getClassName(scope.row.classId)}}</template>
        </el-table-column>
        <el-table-column label="学生名"  align="center">
          <template slot-scope="scope">{{scope.row.name}}</template>
        </el-table-column>
        <el-table-column label="活动名" width="200" align="center">
          <template slot-scope="scope">{{scope.row.theme}}</template>
        </el-table-column>
        <el-table-column label="活动内容" align="center">
          <template slot-scope="scope">{{scope.row.themecontent}}</template>
        </el-table-column>
        <el-table-column label="活动类型"  width="200" align="center">
          <template slot-scope="scope">{{scope.row.themetype}}</template>
        </el-table-column>
        <el-table-column label="教师评分" width="100" align="center">
          <template slot-scope="scope">{{scope.row.teacherscore}}</template>
        </el-table-column>
        <el-table-column label="家长评分" width="100" align="center">
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
      <!--<div class="pagination-container">-->
        <!--<el-pagination-->
          <!--background-->
          <!--@size-change="handleSizeChange"-->
          <!--@current-change="handleCurrentChange"-->
          <!--layout="total, sizes,prev, pager, next,jumper"-->
          <!--:page-size="listQuery.pageSize"-->
          <!--:page-sizes="[5,10,20]"-->
          <!--:current-page.sync="listQuery.pageNum"-->
          <!--:total="total">-->
        <!--</el-pagination>-->
      <!--</div>-->
    </div>
  </div>
</template>

<script>
  import { fetchAllViewList, deleteView ,updateView ,fetchViewList} from '../../api/classview'
  import {fetchAllClassList} from '../../api/stuinfo'
  export default {
    name: 'activities',
    data(){
      return {
        listQuery: {
          keyword: null,
          pageNum: 1,
          pageSize: 10
        },
        list: null,
        total: null,
        classes: null
      }
    },
    created() {
      this.getViewList();
      this.getClasses();
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
    handleActinfo(index, row){
      this.$router.push({path: '/classview/actinfo', query: {id: row.id}})
    },

      getClasses() {
        fetchAllClassList(this.classes).then(response => {
          this.classes = response.data;
        });
      },
      getClassName(id) {
        if (!id) {
          return null;
        }
        for (let i = 0, len = this.classes.length; i < len; i++) {
          let item = this.classes[i];
          if (item.id === id) {
            return item.grade + '-' + item.name;
          }
        }
        return null;
      },
    }
  }
</script>

<style scoped>

</style>
