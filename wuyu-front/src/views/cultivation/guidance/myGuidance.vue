<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="query.fileName" placeholder="文件名称"/>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="loadData">查询</el-button>
      <el-button type="success" icon="el-icon-plus" @click="handleAdd">上传文件</el-button>
    </el-form>

    <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
      <el-table-column prop="fileName" label="文件名称"></el-table-column>
      <el-table-column prop="teacherName" label="上传者"></el-table-column>
      <el-table-column prop="createDate" label="上传时间" width="180"></el-table-column>
      <el-table-column label="下载" align="center">
        <template #default="scope">
          <a :href="URL_ACTION + scope.row.fileLocation" :download="scope.row.fileName">点击下载</a>
        </template>
      </el-table-column>
      <el-table-column label="引用" align="center">
        <template #default="scope">
          <p v-if="scope.row.gid !== ''">
            <el-tag type="info">是</el-tag>
          </p>
          <p v-if="scope.row.gid === ''">
            <el-tag type="info">否</el-tag>
          </p>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" fixed="right" width="300">
        <template #default="scope">
          <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑名称</el-button>
          <el-button type="text" icon="el-icon-delete" class="red" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination">
      <el-pagination background layout="total, prev, pager, next" :current-page="query.pageNo"
                     :page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
    </div>
      <el-dialog title="上传文件" :visible.sync="editVisible" >
          <el-form label-width="120px" :model="dataForm" :rules="formRule" ref="dataForm">
            <el-form-item label="上传文件（PDF 或 Word）" prop="fileLocation">
              <el-upload drag :before-upload="beforeUploadFile" :show-file-list="false"
                         :action=FILE_ACTION :on-success="handleFileSuccess" accept=".pdf,.doc,.docx" :multiple="false">
                <i class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>
          </el-form>
          <template #footer>
            <span class="dialog-footer"><el-button @click="editVisible=false">取消</el-button></span>
          </template>
      </el-dialog>
    </div>
</template>

<script>
import guidanceProto from '@/api/cultivationModule/guidance';
export default {
  name: "tp",
  data() {
    return {
      URL_ACTION:'',
      FILE_ACTION:'',
      tableData: [],
      query :{
        pageNo: 1,
        pageSize: 10,
        tid: localStorage.teacherId,
        fileName: '',
      },
      pageTotal: 0,
      editVisible: false,
      dataForm :{
        id: '',
        fileLocation: '',
      },
      formRule: {
        url: [{required: true, message: '不能为空', trigger: 'blur'}],
      },
    };
  },
  methods:{
    loadData() {
      guidanceProto.paging(this.query).then((res) => {
        console.log(res)
        this.tableData = res.list
        this.pageTotal = res.total
      })
    },
    beforeUploadFile(file){
      console.log(file)
      if (file.type !== 'application/pdf' && file.type !== 'application/msword' &&
        file.type !== 'application/vnd.openxmlformats-officedocument.wordprocessingml.document') {

        this.$message.error("上传的文件只能是 PDF 或者 Word 文件");
        return false;
      }
      if (file.size / 1024 / 1024 > 2) {
        this.$message.error("上传的文件大小不能超过 2 MB");
        return false;
      }
      this.$message.info("上传中，请稍等");
    },
    handleFileSuccess(res) {
      if(res.code === 200){
        this.dataForm.url = res.content;
        this.$message.success("已上传");
        this.editVisible = false;
        this.query.pageNo = 1;
        this.query.fileName = '';
        this.loadData();
      } else {
        this.$message.error("上传失败");
      }
    },
    handlePageChange(val) {
      this.query.pageNo = val;
      this.loadData();
    },
    handleAdd(){
      this.dataForm = {}
      this.editVisible = true;
    },
    handleEdit(index, row) {
      let id = row.id;
      this.$prompt('请输入文件名称', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then(({value}) => {
        if(value === null || value === '' || value.trim().length === 0){
          this.$message.error("请输入文件名称");
          return;
        }
        guidanceProto.insert({id: id, fileName: value}).then((res) => {
          if(res.code === 200) {
            this.loadData();
          } else {this.$message.error('编辑失败')}
        })
      }).catch(() => {
        this.$message({type: 'info', message: '取消输入'});
      });
    },
    handleDelete(index, row) {
      this.dataForm.id = row.id
      this.$confirm("确定要删除吗 ？", "提示", {type: "warning"}).then(() => {
        guidanceProto.delete(this.dataForm).then((res) => {
          if(res.code === 200) {
            this.$message({type: 'success', message: '已删除'});
            this.tableData.splice(index, 1);
          } else {
            this.$notify({title: '提示', message: res.msg, duration: 0});
          }
        })
      }).catch(() => {
        this.$message({type: 'info', message: '已取消'});
      });
    },
  },
  mounted() {
    console.log(localStorage.teacherId)
    this.URL_ACTION = process.env.VUE_APP_BASE_API;
    this.FILE_ACTION = process.env.VUE_APP_BASE_API + '/api/guidance/upload?tid=' + localStorage.teacherId;
    this.loadData();
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
