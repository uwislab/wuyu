<template>
  <!--学生信息列表页面-->
  <div>
    <div class="app-container">
      <div class="handle-box">
        <el-form :inline="true" :model="listQuery" size="small" label-width="140px">
        <el-input v-model="listQuery.keyword" placeholder="学生名" class="handle-input mr10"></el-input>
        <el-button type="primary"
                   icon="el-icon-search"
                   @click="searchQuestionnaire()">搜索</el-button></el-form>
        <el-upload
          class="upload-demo"
          action=""
          :on-change="handleInfoChange"
          :show-file-list="false"
          :auto-upload="false">
          <el-button type="primary">上传
            <i class="el-icon-upload el-icon--right"></i>
          </el-button>
        </el-upload>
      </div>
      <el-table ref="studentTable"
                border
                :data="list"
                style="width: 100%"
                @selection-change="handleSelectionChange"
                >
        <el-table-column type="selection" width="60" align="center"></el-table-column>
        <el-table-column label="编号" width="100" align="center">
          <template slot-scope="scope">{{scope.row.id}}</template>
        </el-table-column>
        <el-table-column label="学生姓名" width="200" align="center">
          <template slot-scope="scope">{{scope.row.name}}</template>
        </el-table-column>
        <el-table-column label="性别" width="100"align="center">
          <template slot-scope="scope">{{getSex(scope.row.sex)}}</template>
        </el-table-column>
        <el-table-column label="学号"  align="center">
          <template slot-scope="scope">{{scope.row.snumber}}</template>
        </el-table-column>
        <el-table-column label="班级"  align="center">
          <template slot-scope="scope">{{getClassName(scope.row.classId)}}</template>
        </el-table-column>
        <el-table-column label="操作" width="300" align="center">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="handleUpdate(scope.$index, scope.row)">编辑
            </el-button>
            <el-button
              size="mini"
              @click="handleSelectScore(scope.$index, scope.row)">查看成绩
            </el-button>
            <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)">删除
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
  import  {fetchAllStudentList,fetchAllClassList,deleteStudent,fetchStudentList,addScore} from "@/api/stuinfo";
  export default {
    name: "studentList",
    data() {
      return {
        listQuery: {
          keyword: null,
          pageNum: 1,
          pageSize: 10
        },
        list: null,
        classes: null,
        total: null,
        listLoading: true,
        multipleSelection: [],
        infolist:{
          id: '',
          snumber: '',
          deyu:'',
          zhiyu: '',
          tiyu: '',
          meiyu:'',
          laoyu: '',
          parentEvaluate: '',
          teacherEvaluate:''},
      }
    },
    created() {
/*      let _this = this*/
      this.getList();
      this.getClasses();
    },
    methods:{
      getList(){
        this.listLoading = true;
        fetchStudentList(this.listQuery).then(response =>{
          this.listLoading = false;
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
        this.getList();
      },
      handleCurrentChange(val) {
        this.listQuery.pageNum = val;
        this.getList();
      },
      searchQuestionnaire(){
        this.listQuery.pageNum = 1;
        this.getList();
      },
      handleSelectionChange(val) {
            this.multipleSelection = val;
          },
      handleUpdate(index, row) {
        this.$router.push({path: '/stuinfo/updateStudent', query: {id: row.id}})
      },
      handleSelectScore(index, row) {
        this.$router.push({path: '/stuinfo/selectScore', query: {id: row.id}})
      },
      handleDelete(index, row) {
        this.$confirm('是否要删除该学生', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteStudent(row.id).then(response => {
            this.$message({
              message: '删除成功',
              type: 'success',
              duration: 1000
            });
            this.getList();
          });
        });
      },
      getSex(val) {
        if (val === 0) return "男"
        return "女"
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
      handleInfoChange (file, fileList) {
        this.fileContent = file.raw
        const fileName = file.name
        const fileType = fileName.substring(fileName.lastIndexOf('.') + 1)
        if (this.fileContent) {
          if (fileType === 'xlsx' || fileType === 'xls') {
            this.importfile(this.fileContent)
          } else {
            this.$message({
              type: 'warning',
              message: '附件格式错误，请重新上传！'
            })
          }
        } else {
          this.$message({
            type: 'warning',
            message: '请上传附件！'
          })
        }
      },
      importfile (obj) {
        const reader = new FileReader()
        const _this = this
        reader.readAsArrayBuffer(obj)
        reader.onload = function () {
          const buffer = reader.result
          const bytes = new Uint8Array(buffer)
          const length = bytes.byteLength
          let binary = ''
          for (let i = 0; i < length; i++) {
            binary += String.fromCharCode(bytes[i])
          }
          const XLSX = require('xlsx')
          const wb = XLSX.read(binary, {
            type: 'binary'
          })
          const outdata = XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]])
          // console.log(outdata)
          this.data = [...outdata]
          const arr = []
          let i = 0
          this.data.map(v => {
            const obj = {}
            console.log(v)
            obj.id = v.id
            obj.sNumber = v.snumber
            obj.deyu = v.deyu
            obj.zhiyu = v.zhiyu
            obj.tiyu = v.tiyu
            obj.meiyu = v.meiyu
            obj.laoyu = v.laoyu
            obj.parentEvaluate = v.parentEvaluate
            obj.teacherEvaluate = v.teacherEvaluate
            arr.push(obj)
            for (i;i<arr.length;i++){
              _this.submitinfo(arr[i])
            }
          })
          console.log(arr)

        }
      },
      submitinfo(data){
        addScore(data).then(response => {
          console.log(response);
        });
      }
    },

    // methods: {
    //   getList() {
    //     this.listLoading = true;
    //     fetchAllStudentList(this.listQuery).then(response => {
    //       this.listLoading = false;
    //       this.list = response.data.list;
    //       this.total = response.data.total;
    //       this.totalPage = response.data.totalPage;
    //       this.pageSize = response.data.pageSize;
    //     });
    //   },
    //   handleSelectionChange(val) {
    //     this.multipleSelection = val;
    //   },
    //   handleUpdate(index, row) {
    //     this.$router.push({path: '/sms/updateStudent', query: {id: row.id}})
    //   },
    //   handleDelete(index, row) {
    //     this.$confirm('是否要删除该学生', '提示', {
    //       confirmButtonText: '确定',
    //       cancelButtonText: '取消',
    //       type: 'warning'
    //     }).then(() => {
    //       deleteStudent(row.id).then(response => {
    //         this.$message({
    //           message: '删除成功',
    //           type: 'success',
    //           duration: 1000
    //         });
    //         this.getList();
    //       });
    //     });
    //   },
    //   handleSizeChange(val) {
    //     this.listQuery.pageNum = 1;
    //     this.listQuery.pageSize = val;
    //     this.getList();
    //   },
    //   handleCurrentChange(val) {
    //     this.listQuery.pageNum = val;
    //     this.getList();
    //   },
    //   searchStudentList() {
    //     this.listQuery.pageNum = 1;
    //     this.getList();
    //   },
    //   addStudent() {
    //     this.$router.push({path: '/sms/addStudent'})
    //   },
    //   getClasses() {
    //     fetchAllClassList(this.classes).then(response => {
    //       this.classes = response.data;
    //     });
    //   },
    //   getSex(val) {
    //     if (val === 0) return "男"
    //     return "女"
    //   },
    //   getClassName(id) {
    //     if (!id) {
    //       return null;
    //     }
    //     for (let i = 0, len = this.classes.length; i < len; i++) {
    //       let item = this.classes[i];
    //       if (item.id === id) {
    //         return item.grade + '-' + item.name;
    //       }
    //     }
    //     return null;
    //   }
    // }
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
