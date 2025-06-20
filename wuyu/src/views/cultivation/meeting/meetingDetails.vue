<template>
  <div class="app-container">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>会议标题：{{obj.title}}</span>
        <el-button @click="goBack" style="float: right; padding: 3px 0" icon="el-icon-back" type="text">返回</el-button>
      </div>
      <div >添加人：{{obj.teacherName}}</div><br>
      <div >添加时间：{{obj.createDate}}</div><br>
      <div>开展时间：{{obj.launchDate}}</div><br>
      <div>会议概要：{{obj.summary}}</div>
    </el-card>
    <br>
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="content" placeholder="请输入纪要"/>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="saveEdit">记录</el-button>
    </el-form>
    <el-timeline>
      <el-timeline-item
        v-for="(o, index) in tableData"
        :key="index"
        :timestamp="o.createDate">
        {{o.teacherName}}：{{o.content}}
        <el-button v-if="tid===o.tid.toString()" icon="el-icon-delete" style="float: right" size="mini" type="danger" @click="handleDelete(index, o)">删除</el-button>
      </el-timeline-item>
    </el-timeline>
    <div class="pagination">
      <el-pagination background layout="total, prev, pager, next" :current-page="query.pageNo"
                     :page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
    </div>
  </div>
</template>

<script>
import meetingProto from '@/api/cultivationModule/meeting';
import minutesProto from '@/api/cultivationModule/minutes';
export default {
  name: "tp",
  data() {
    return {
      tid: localStorage.teacherId,
      URL_ACTION: '',
      tableData: [],
      id: '',
      content: '',
      obj: {},
      query: {
        pageNo: 1,
        pageSize: 10,
      },
      pageTotal: 0,
    };
  },
  methods: {
    handlePageChange (val) {
      this.query.pageNo = val;
      this.getPage();
    },
    saveEdit(){
      if(this.content === '' || this.content.trim().length === 0){
        this.$message.error('请输入纪要');return;
      }
      minutesProto.insert({content: this.content, mid: this.id, tid: localStorage.teacherId}).then((res) => {
        if(res.code === 200) {
          this.content = '';
          this.query.pageNo = 1;
          this.getPage();
          } else {
          this.$message.error(res.msg);
        }
      })
    },
    handleDelete(index, o) {
      this.$confirm("确定要删除吗 ？", "提示", {
        type: "warning",
      }).then(() => {
        minutesProto.delete({id: o.id}).then((res) => {
          if(res.code === 200){
            this.$message({
              type: 'success',
              message: '已删除'
            });
            this.tableData.splice(index, 1);
          } else {
            this.$notify({
              title: '提示',
              message: res.msg,
              duration: 0
            });
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    getPage(){
      minutesProto.paging(this.query).then((res) => {
        this.tableData = res.list
        this.pageTotal = res.total
      })
    },
    getDetail(){
      meetingProto.details({id: this.id}).then((res) => {
        this.obj = res.data
      })
    },
    goBack() {
      this.$router.go(-1)
    },
  },
  mounted() {
    this.URL_ACTION = process.env.VUE_APP_BASE_API;
    this.id = this.$route.query.id;
    this.query.mid = this.id;
    this.getDetail();
    this.getPage();
  }
}
</script>
