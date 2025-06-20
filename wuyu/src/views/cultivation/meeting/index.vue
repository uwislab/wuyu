<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="query.title" placeholder="会议标题"/>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getPage">查找会议</el-button>
      <el-button type="success" icon="el-icon-plus" @click="handleAdd">添加会议</el-button>
      <el-button type="warning" icon="el-icon-plus" @click="handleStatistics">数据统计</el-button>
    </el-form>

    <el-card class="box-card" v-for="o in tableData" style="margin-bottom: 10px">
      <div slot="header" class="clearfix">
        <span>{{o.title}}</span>
        <el-button style="float: right; padding: 3px 0" icon="el-icon-info" type="text" @click="handleDetail(o)">查看</el-button>
      </div>
      <div >
        添加人：{{o.teacherName}}<br><br>
        开展日期：{{o.launchDate}}
      </div>
    </el-card>

    <div class="pagination">
      <el-pagination background layout="total,prev,pager,next" :current-page="query.pageNo"
                     :page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
    </div>

    <el-dialog title="添加" :visible.sync="editVisible">
      <el-form label-width="120px" :model="dataForm" :rules="formRule" ref="dataForm">
        <el-form-item label="标题" prop="title">
          <el-input v-model="dataForm.title"></el-input>
        </el-form-item>
        <el-form-item label="目标" prop="target">
          <el-select v-model="dataForm.target">
            <el-option label="一年级" value="一年级">一年级</el-option>
            <el-option label="二年级" value="二年级">二年级</el-option>
            <el-option label="三年级" value="三年级">三年级</el-option>
            <el-option label="四年级" value="四年级">四年级</el-option>
            <el-option label="五年级" value="五年级">五年级</el-option>
            <el-option label="六年级" value="六年级">六年级</el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="概要" prop="summary">
          <el-input v-model="dataForm.summary" type="textarea"></el-input>
        </el-form-item>
        <el-form-item label="日期" prop="launchDate">
          <el-date-picker v-model="dataForm.launchDate" type="month" placeholder="选择月"></el-date-picker>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="editVisible=false">取消</el-button>
          <el-button type="primary" @click="handleSave">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import meetingApi from '@/api/cultivationModule/meeting';

export default {
  name: "tp",
  data() {
    return {
      URL_ACTION: '',
      tableData: [],
      query :{
        pageNo: 1,
        pageSize: 10,
        title: '',
      },
      pageTotal: 0,
      editVisible: false,
      dataForm :{
        id: '',
        target: '',
        title: '',
        summary: '',
        launchDate: ''
      },
        formRule: {
          title: [{required: true, message: '不能为空', trigger: 'blur'}],
          target: [{required: true, message: '不能为空', trigger: 'blur'}],
          launchDate: [{required: true, message: '不能为空', trigger: 'blur'}],
        },
      };
    },
    methods:{
      handleAdd() {
        this.dataForm = {};
        this.editVisible = true;
      },
      handleSave() {
        this.dataForm.tid = localStorage.teacherId;
        this.$refs.dataForm.validate(valid => {
          if (!valid) {
            this.$message.error("请按提示填写表单");
            this.editVisible = true;
            return false;
          } else {
            meetingApi.insert(this.dataForm).then((res) => {
              if(res.code === 200){
                this.editVisible = false;
                if(this.dataForm.id === undefined || this.dataForm.id === null){
                  this.query.pageNo = 1;
                }
                this.editVisible = false;
                this.getPage();
              } else {
                this.$message.error(res.msg);
              }
            })
          }
        })
      },
      handleDetail(o) {
        this.$router.push("meetingDetails?id=" + o.id);
      },
      handlePageChange(val) {
        this.query.pageNo = val;
        this.getPage();
      },
      getPage() {
        meetingApi.paging(this.query).then((res) => {
          this.tableData = res.list
          this.pageTotal = res.total
        })
      },
      handleStatistics() {
        this.$router.push("dataStatistics");
      }
    },
    mounted() {
      this.URL_ACTION = process.env.VUE_APP_BASE_API;
      this.getPage();
    }
  }
</script>
