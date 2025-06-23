<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="query.title" placeholder="会议标题" />
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getPage">查找会议</el-button>
      <el-button type="success" icon="el-icon-plus" @click="handleAdd">添加会议</el-button>
    </el-form>

    <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
      <el-table-column prop="title" label="标题"></el-table-column>
      <el-table-column prop="launchDate" label="开展时间" width="180"></el-table-column>
      <el-table-column prop="createDate" label="添加时间" width="180"></el-table-column>
      <el-table-column label="操作" align="center" fixed="right" width="300">
        <template #default="scope">
          <el-button type="text" icon="el-icon-info" @click="handleDetail(scope.$index, scope.row)">查看</el-button>
          <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button type="text" icon="el-icon-delete" class="red" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination">
      <el-pagination background layout="total, prev, pager, next" :current-page="query.pageNo"
                     :page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
    </div>

    <el-dialog title="添加或编辑" :visible.sync="editVisible" >
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
          <el-input v-model="dataForm.launchDate" type="date"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
            <el-button @click="editVisible = false">取消</el-button>
            <el-button type="primary" @click="saveEdit">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import meetingProto from '@/api/cultivationModule/meeting';
export default {
  name: "tp",
  data() {
    return {
      URL_ACTION: '',
      tableData: [],
      query: {
        pageNo: 1,
        pageSize: 10,
        title: '',
      },
      pageTotal: 0,
      editVisible: false,
      dataForm: {
        id: '',
        target: '',
        title: '',
        summary: '',
        launchDate: '',
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
    handleEdit(index, row) {
      this.dataForm.id = row.id;
      this.dataForm.title = row.title;
      this.dataForm.summary = row.summary;
      this.dataForm.launchDate = row.launchDate;
      this.editVisible = true;
    },
    saveEdit() {
      this.dataForm.tid = localStorage.teacherId;
      this.$refs.dataForm.validate(valid => {
        if (!valid) {
          this.$message.error("请按提示填写表单");
          this.editVisible = true;
          return false;
        } else {
          meetingProto.insert(this.dataForm).then((res) => {
            if(res.code === 200){
              this.editVisible = false;
              if(this.dataForm.id === undefined || this.dataForm.id === null) {
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
    handleDetail(index, row) {
      this.$router.push("meetingDetails?id=" + row.id);
    },
    handleDelete(index, row) {
      this.dataForm.id = row.id
      this.$confirm("确定要删除吗 ？", "提示", {
        type: "warning",
      }).then(() => {
        meetingProto.delete(this.dataForm).then((res) => {
          if(res.code === 200) {
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
    handlePageChange(val) {
      this.query.pageNo = val;
      this.getPage();
    },
    getPage() {
      meetingProto.paging(this.query).then((res) => {
        this.tableData = res.list
        this.pageTotal = res.total
      })
    },
  },
  mounted() {
    this.URL_ACTION = process.env.VUE_APP_BASE_API;
    this.query.tid = localStorage.teacherId;
    this.getPage();
  }
}
</script>

<style scoped>
  .table {
    width: 100%;
    font-size: 14px;
  }
  .red {
    color: #ff0000;
  }
</style>
