<template>
  <div class="app-container">

    <!-- 搜索表单 -->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="searchObj.activityName" placeholder="活动名称" />
      </el-form-item>
      <el-form-item>
        <el-select v-model="searchObj.activityState"
                   placeholder="活动状态"
                   clearable
                   @clear="getList">
          <el-option label="待开始" value="0"></el-option>
          <el-option label="待评分" value="1"></el-option>
          <el-option label="已完成" value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select v-model="searchObj.grade"
                   placeholder="活动年级"
                   clearable
                   @clear="getList">
          <el-option label="一年级" value="1"></el-option>
          <el-option label="二年级" value="2"></el-option>
          <el-option label="三年级" value="3"></el-option>
          <el-option label="四年级" value="4"></el-option>
          <el-option label="五年级" value="5"></el-option>
          <el-option label="六年级" value="6"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select v-model="searchObj.classList"
                   multiple
                   filterable
                   placeholder="活动班级"
                   clearable
                   @clear="getList">
            <el-option
                v-for="item in classList"
                :key="item.classId"
                :label="item.className"
                :value="item.classId">
            </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select v-model="searchObj.activityType"
                   placeholder="活动类型"
                   clearable
                   @clear="getList">
          <el-option label="班级活动" value="0"></el-option>
          <el-option label="年级活动" value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
    </el-form>

    <!-- 展示表格 -->
    <el-table :data="tableData" stripestyle="width:100%" >
      <el-table-column type="index" width="50" label="序号" />
      <el-table-column prop="activityName" label="活动名称" />
      <el-table-column prop="activityId" label="活动编号" />
      <el-table-column prop="startTime" label="活动时间" />
      <el-table-column prop="activityType" label="活动类别" />
      <el-table-column prop="activityAspect" label="评价量表" />
      <el-table-column prop="activityState" label="活动状态" width="100">
        <template slot-scope="scope">
          <el-tag
            :type="handleTagType(scope.$index, scope.row)"
            disable-transitions>{{scope.row.activityState}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="280" align="center">
       <template slot-scope="scope">
         <el-button type="primary" size="mini" @click="jumpToScore(scope.row.activityId, scope.row.activityAspect,scope.row.activityScore, scope.row.teacherComment)">活动打分</el-button>
         <!-- <el-button type="primary" size="mini" @click="jumpToDetail(scope.$index, scope.row)">详情</el-button> -->
         <el-button type="danger" style="margin-left:5px" size="mini" @click="handleDelete(scope.row.activityId)">删除</el-button>
       </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      background
      :current-page="page"
      layout="prev, pager, next"
      style="padding: 30px 0; text-align: center;"
      :total="total"
      @current-change="getList">
    </el-pagination>
  </div>
</template>

<script>
import { getActivityList, deleteActivity } from '@/api/activityModule/activity';
import { getClassInfo } from '@/api/activityModule/common';
export default {
  data(){
    return {
      limit: 10,
      searchObj: {
        activityName: '',
        activityState: '',
        grade: '',
        classList: '',
        activityType: '',
        activityAspect: '',
      }, // 条件搜索对象
      currentPage: 1, //当前页
      page: 1, //分页组件页码初始化
      tableData:[],
      total: 20,
      classList:{},
      activityStateMapping: {
        0: '待开始',
        1: '待评分',
        2: '已完成'
      }
    }
  },
  created(){
    getClassInfo(this.searchObj.grade).then(res => {
      this.classList = res.data.classList;//班级下拉列表
      this.getList();
    })
  },
  watch: {
    'searchObj.grade'(newVal, oldVal) {
      if (newVal !== oldVal) {
        this.getClassInfoFunction(newVal); // 当值变化时调用getList方法
      }
    },
  },
  methods: {
    getClassInfoFunction(grade) {
      getClassInfo(grade).then(res => {
        this.classList = res.data.classList;//班级下拉列表
      })
    },
    // 时间格式
    formatDateTime(date){
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        m = m < 10 ? ('0' + m) : m;
        var d = date.getDate();
        d = d < 10 ? ('0' + d) : d;
        var h = date.getHours();
        h=h < 10 ? ('0' + h) : h;
        var minute = date.getMinutes();
        minute = minute < 10 ? ('0' + minute) : minute;
        var second=date.getSeconds();
        second=second < 10 ? ('0' + second) : second;
        return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;
    },
    getList(page = 1){
      this.currentPage = page;
      const payload = {
        pageSize: 10,
        pageNum: this.currentPage,
        ...this.searchObj,
      }
      getActivityList(payload).then(res => {
        console.log(res)
        this.total = res.data.total;
        this.tableData = res.data.list.map(item => {
          return {
            ...item,
            activityType: item.activityType == 0 ? '班级活动' : '年级活动',
            activityState: this.activityStateMapping[item.activityState],
            startTime: this.formatDateTime(new Date(item.startTimeDate)),
          }
        })
      }).catch(err => {console.log("获取数据异常");
      });
    },
    //跳转到具体活动细节页面
    jumpToDetail(index, row){
      this.$router.push({ path: "/activityModule/activityDetail", query: { activityId: row.activityId }})
      // this.$router.push({ name: 'activityDetail', params: { activityId: row.activityId }})
    },

    //跳转到具体活动打分页面
    jumpToScore(activityId, activityAspect,activityScore,teacherComment){
      sessionStorage.setItem("activityId",JSON.stringify(activityId))//存选择的当前活动ID
      sessionStorage.setItem("activityAspect",JSON.stringify(activityAspect))//存选择的当前量化表ID
      sessionStorage.setItem("activityScore",JSON.stringify(activityScore))//存选择的当前量化表ID
      sessionStorage.setItem("activityScore",JSON.stringify(activityScore))//存选择的当前量化表ID
      sessionStorage.setItem("teacherComment",JSON.stringify(teacherComment))//存选择的当前量化表ID
      this.$router.push({ path: "/activityModule/activityScore" })
      // this.$router.push({name: 'activityScore' })
    },

    handleDelete(activityId){
      deleteActivity({activityId}).then(res => {
        this.getList();
        this.currentPage = 1;
        this.page = 1;
      })
    },
    handleTagType(index, row){
      if(row.activityState == '已完成'){
        return 'success';
      }else if(row.activityState == '待开始'){
        return 'primary';
      }else{
        return 'warning';
      }
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
