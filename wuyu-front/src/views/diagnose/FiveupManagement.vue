<template>
  <div>
    <div width="100%" class="btnList">

      <el-input
        placeholder="请输入学生姓名"
        v-model="keywords"
        clearable>
      </el-input>
      <div>
        <el-button type="primary"  id="Excelbtn" @click="showAdd()">新增</el-button>
        <el-button type="success"  id="Excelbtn" @click="exportExcel">导出Excel</el-button>
      </div>


    </div>

    <el-table
      id="wuyuScore"
      :data="searchstudent()"
      style="width: 100%">
      <el-table-column
        prop="studentId"
        label="学号"
        width="100">
      </el-table-column>
      <el-table-column
        prop="studentName"
        label="姓名"
        width="100">
      </el-table-column>
      <el-table-column
        prop="studentClassNumber"
        label="班级"
        width="100">
      </el-table-column>
      <el-table-column
        prop="studentGrade"
        label="年级"
        width="100">
      </el-table-column>
      <el-table-column
        prop="sdeyu"
        label="德育"
        width="100">
      </el-table-column>
      <el-table-column
        prop="szhiyu"
        label="智育"
        width="100">
      </el-table-column>
      <el-table-column
        prop="stiyu"
        label="体育"
        width="100">
      </el-table-column>
      <el-table-column
        prop="smeiyu"
        label="美育"
        width="100">
      </el-table-column>
      <el-table-column
        prop="slaoyu"
        label="劳育"
        width="100">
      </el-table-column>
      <el-table-column
        prop="sexdate"
        label="评分日期"
        width="100">
      </el-table-column>
      <el-table-column
        label="操作">
        <template slot-scope="scope">
          <el-button type="primary" @click="editform(scope.row)">编辑成绩</el-button>
          <!--              <a class="operation" href="#" @click="editform(scope.row)">编辑成绩</a>-->
          <el-button type="danger" @click="handleDelete(scope.row)">删除成绩</el-button>
        </template>
      </el-table-column>

    </el-table>


    <!-- 分页组件 -->
    <div style="display: flex; justify-content: center;">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[5, 10, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next"
        :total="total">
      </el-pagination>
    </div>
    <!-- 导出用的 -->
    <div v-show="false">
      <el-table

        id="wuyuScore1"
        :data="this.exportData"
        :visible="false"
        style="width: 100%">
        <el-table-column
          prop="studentId"
          label="学号"
          width="100">
        </el-table-column>
        <el-table-column
          prop="studentName"
          label="姓名"
          width="100">
        </el-table-column>
        <el-table-column
          prop="studentClassNumber"
          label="班级"
          width="100">
        </el-table-column>
        <el-table-column
          prop="studentGrade"
          label="年级"
          width="100">
        </el-table-column>
        <el-table-column
          prop="sdeyu"
          label="德育"
          width="100">
        </el-table-column>
        <el-table-column
          prop="szhiyu"
          label="智育"
          width="100">
        </el-table-column>
        <el-table-column
          prop="stiyu"
          label="体育"
          width="100">
        </el-table-column>
        <el-table-column
          prop="smeiyu"
          label="美育"
          width="100">
        </el-table-column>
        <el-table-column
          prop="slaoyu"
          label="劳育"
          width="100">
        </el-table-column>
        <el-table-column
          prop="sexdate"
          label="评分日期"
          width="100">
        </el-table-column>


      </el-table>
    </div>
    <!-- 弹窗-->

    <el-dialog
      title="修改学生成绩"
      :visible.sync="dialogVisi"
      width="30%"
    >
      <el-form  ref="form" :model="editsizeForm" label-width="80px" size="large" >
        <el-form-item label="德育">
          <el-input v-model="editsizeForm.deyu"></el-input>
        </el-form-item>
        <el-form-item label="智育">
          <el-input v-model="editsizeForm.zhiyu"></el-input>
        </el-form-item>
        <el-form-item label="体育">
          <el-input v-model="editsizeForm.tiyu"></el-input>
        </el-form-item>
        <el-form-item label="美育">
          <el-input v-model="editsizeForm.meiyu"></el-input>
        </el-form-item>
        <el-form-item label="劳育">
          <el-input v-model="editsizeForm.laoyu"></el-input>
        </el-form-item>

        <el-form-item size="large">
          <el-button type="primary" @click="onedit">修改</el-button>
          <el-button @click="dialogVisi= false">取 消</el-button>
        </el-form-item>

      </el-form>

    </el-dialog>

    <el-dialog
      title="新增学生成绩"
      :visible.sync="add"
      width="30%"
    >
      <el-form  ref="form" :model="addForm" label-width="80px" size="large" >
        <el-form-item label="学号">
          <el-input v-model="addForm.id"></el-input>
        </el-form-item>
        <el-form-item label="德育">
          <el-input v-model="addForm.deyu"></el-input>
        </el-form-item>
        <el-form-item label="智育">
          <el-input v-model="addForm.zhiyu"></el-input>
        </el-form-item>
        <el-form-item label="体育">
          <el-input v-model="addForm.tiyu"></el-input>
        </el-form-item>
        <el-form-item label="美育">
          <el-input v-model="addForm.meiyu"></el-input>
        </el-form-item>
        <el-form-item label="劳育">
          <el-input v-model="addForm.laoyu"></el-input>
        </el-form-item>

        <el-form-item size="large">
          <el-button type="primary" @click="addScore">添加</el-button>
          <el-button @click="add= false">取 消</el-button>
        </el-form-item>

      </el-form>

    </el-dialog>

  </div>

</template>

<script>
import FileSaver from 'file-saver'
import * as XLSX from 'xlsx'
import axios from 'axios';
import {addScore} from "@/api/stuinfo";
export default {
  data() {
    return {
      // 当前页码
      currentPage: 1,
      // 每页显示的记录数
      pageSize: 10,
      // 总记录数，用于计算总页数
      total: 0,
      centerDialogVisible:false,
      currentid:'',
      add:false,
      dialogVisible:false,
      dialogVisi:false,
      keywords:"",
      tableData: [],
      exportData:[],
      addForm:{
        id:'',
        name: '',
        class:'',
        grade:'',
        deyu:'',
        zhiyu:'',
        tiyu:'',
        meiyu:'',
        laoyu:'',
      },

      sizeForm: {
        id:'',
        name: '',
        class:'',
        grade:'',
        deyu:'',
        zhiyu:'',
        tiyu:'',
        meiyu:'',
        laoyu:'',
        date: ''
      },
      editsizeForm: {
        name: '',
        class:'',
        grade:'',
        deyu:'',
        zhiyu:'',
        tiyu:'',
        meiyu:'',
        laoyu:'',
      }
    }
  },
  computed:{

  },
  methods:{
    handleDelete(row) {
      this.$confirm('此操作将永久删除条目, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$message({

          type: 'success',
          message: '删除成功!'
        });
        axios.get("http://36.111.68.174:33380/diagnose/deletestudentscore",{
          params:{
            id:row.studentId
          }
        }).then(
          response =>{

            this.fetchData()
          },
          error =>{
            const h = this.$createElement;
            this.$notify({
              title: '提示信息',
              message: '失败',
            });
          }
        )
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });}
    ,
    showAdd(){
      this.add=true;
    },

    addScore(){
      if((this.addForm.id=='')||(this.addForm.deyu=='')||(this.addForm.zhiyu=='')||(this.addForm.tiyu=='')||(this.addForm.meiyu=='')||this.addForm.laoyu=='')
      {
        const h = this.$createElement;
        this.$notify({
          title: '提示信息',
          message: '请把表单填写完整!',
        });
        return;
      }
      axios.get("http://36.111.68.174:33380/diagnose/insertstudentscore",{
        params:{
          id:this.addForm.id,
          deyu:this.addForm.deyu,
          zhiyu:this.addForm.zhiyu,
          tiyu:this.addForm.tiyu,
          meiyu:this.addForm.meiyu,
          laoyu:this.addForm.laoyu,
        }
      }).then(
        response =>{
          const h = this.$createElement;
          this.$notify({
            title: '提示信息',
            message: response.data,
          });
          this.fetchData()
        },
        error =>{
          const h = this.$createElement;
          this.$notify({
            title: '提示信息',
            message: '失败',
          });
        }
      )
      this.add= false;
    },
    fetchData() {
      axios.get("http://36.111.68.174:33380/diagnose/getAllScore").then(
        response => {
          for (let i = 0; i < response.data.length; i++) {
            console.log(this.dateformat(parseInt(response.data[i].sexdate)));
            response.data[i].sexdate = this.dateformat(parseInt(response.data[i].sexdate));
            response.data[i].sexdate = response.data[i].sexdate.slice(0, 11);
          }
          this.tableData = response.data;
          this.exportData=response.data;
          console.log(this.exportData)
          this.total = this.tableData.length;
        },
        error => {
          const h = this.$createElement;
          this.$notify({
            title: '提示信息',
            message: '加载失败',
          });
        }
      );
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.currentPage = 1;
    },
    // 处理当前页码改变的事件
    handleCurrentChange(val) {
      this.currentPage = val;
    },
    dateformat(timestamp){
      const date = new Date(timestamp);

      // 获取年份、月份、日期
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0'); // 月份是从0开始的，所以要加1
      const day = String(date.getDate()).padStart(2, '0');

      // 返回格式化后的日期字符串
      return `${year}-${month}-${day}`;
    },
    onedit(){

      var newlist =[];
      if((this.editsizeForm.id=='')||(this.editsizeForm.name=='')||(this.editsizeForm.class=='')||(this.editsizeForm.grade=='')||(this.editsizeForm.deyu=='')||(this.editsizeForm.zhiyu=='')||(this.editsizeForm.tiyu=='')||(this.editsizeForm.meiyu=='')||this.editsizeForm.laoyu=='')
      {
        const h = this.$createElement;
        this.$notify({
          title: '提示信息',
          message: '请把表单填写完整!',
        });
        return;
      }
      axios.get("http://36.111.68.174:33380/diagnose/updatestudentscore",{
        params:{
          id:this.currentid,
          deyu:this.editsizeForm.deyu,
          zhiyu:this.editsizeForm.zhiyu,
          tiyu:this.editsizeForm.tiyu,
          meiyu:this.editsizeForm.meiyu,
          laoyu:this.editsizeForm.laoyu,
        }
      }).then(
        response =>{
          const h = this.$createElement;
          this.$notify({
            title: '提示信息',
            message: '修改成功!',
          });
          this.fetchData()
        },
        error =>{
          const h = this.$createElement;
          this.$notify({
            title: '提示信息',
            message: '失败',
          });
        }
      )

      this.dialogVisi= false;
    },
    editform(row){
      /* 将当前行的id保存下来*/
      this.currentid=row.studentId;

      this.editsizeForm.id=row.studentId;
      this.editsizeForm.name=row.studentName;
      this.editsizeForm.class=row.studentClassNumber;
      this.editsizeForm.grade=row.studentGrade;
      this.editsizeForm.deyu=row.sdeyu;
      this.editsizeForm.zhiyu=row.szhiyu;
      this.editsizeForm.tiyu=row.stiyu;
      this.editsizeForm.meiyu=row.smeiyu;
      this.editsizeForm.laoyu=row.slaoyu;
      this.dialogVisi=true;
    },

    searchstudent(){
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = startIndex + this.pageSize;
      return this.tableData.filter((item, index) => {
        if (item.studentName.includes(this.keywords)) {
          return index >= startIndex && index < endIndex;
        }
      });
    },

    // 创建一个新的数组，用于存储要导出的数据，包含表头
    exportExcel () {
      /* generate workbook object from table */
      var wb = XLSX.utils.table_to_book(document.querySelector('#wuyuScore1'))
      /* get binary string as output */
      var wbout = XLSX.write(wb, { bookType: 'xlsx', bookSST: true, type: 'array' })
      try {
        FileSaver.saveAs(new Blob([wbout], { type: 'application/octet-stream' }), '学生五育成绩表.xlsx')
      } catch (e) { if (typeof console !== 'undefined') console.log(e, wbout) }
      return wbout
    }
  }
  ,
  created(){
    this.fetchData()
  }
}
</script>

<style scoped>
.el-input{
  width:200px;
}
.operation{
  display:block
}
a:hover{
  color:blue;
}
#Excelbtn{
  margin-left:500px;
}
</style>
