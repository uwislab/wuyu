<template>
  <el-container style="height: 100%; ">
    <el-aside width="200px" style="background-color: rgb(238, 241, 246); margin: 0;padding: 0; height: 100%;">
  <Aside />
</el-aside>
<el-container>
  <div>
        <div width="100%" class="btnList">
          <el-input
            placeholder="请输入学生姓名"
            v-model="keywords"
            clearable>
          </el-input>
          <el-button type="success"  id="Excelbtn" @click="exportExcel">导出Excel</el-button>
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
              <a class="operation" href="#" @click="editform(scope.row)">编辑成绩</a>
            </template>
          </el-table-column>
        </el-table>
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
           </el-form-item>
         </el-form>
            <el-button @click="dialogVisi= false">取 消</el-button>
        </el-dialog>

      </div>
</el-container>
  </el-container>


</template>

<script>
import Aside from "@/views/teacher_workspace/Aside"
  import FileSaver from 'file-saver'
  import * as XLSX from 'xlsx'
  import axios from 'axios';
   export default {
        data() {
          return {
            centerDialogVisible:false,
            currentid:'',
            dialogVisible:false,
            dialogVisi:false,
            keywords:"",
            tableData: [],
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
          dateformat(){
            var dt = new Date(this.sizeForm.date)//竖线前的值
            var y = dt.getFullYear()//标准时间得到年，月，日的函数
            var m = (dt.getMonth()+1).toString().padStart(2,'0')//默认从0开始，pad填充字符，前一个参数为填充至2位，
            var d = dt.getDate().toString().padStart(2,'0')//后为填充内容,只有sting可用
            return `${y}-${m}-${d}`// tab上的点，模板格式输出
          }
        },
        components: {
      Aside
    },
        methods:{
          onedit(){

            var newlist =[];
            if((this.editsizeForm.id=='')||(this.editsizeForm.name=='')||(this.editsizeForm.class=='')||(this.editsizeForm.grade=='')||(this.editsizeForm.deyu=='')||(this.editsizeForm.zhiyu=='')||(this.editsizeForm.tiyu=='')||(this.editsizeForm.meiyu=='')||this.editsizeForm.laoyu==''||this.editsizeForm.date=='')
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
            this.editsizeForm.date=row.sexdate;
            this.dialogVisi=true;
          },

          searchstudent(){
            return this.tableData.filter(item=>{//Vue.filter('过滤器的名称'，function(data){})基本格式
            	if (item.studentName.includes(this.keywords)){
            		return item;
            	}
            })
            },
            exportExcel () {
                   /* generate workbook object from table */
                   var wb = XLSX.utils.table_to_book(document.querySelector('#wuyuScore'))
                   /* get binary string as output */
                   var wbout = XLSX.write(wb, { bookType: 'xlsx', bookSST: true, type: 'array' })
                   try {
                       FileSaver.saveAs(new Blob([wbout], { type: 'application/octet-stream' }), '学生五育成绩表.xlsx')
                   } catch (e) { if (typeof console !== 'undefined') console.log(e, wbout) }
                   return wbout
               }
          },
        created(){
            axios.get("http://36.111.68.174:33380/diagnose/allScore").then(
            response =>{
              for(let i=0;i<response.data.length;i++){
                response.data[i].sexdate=response.data[i].sexdate.slice(0,11)
              }
              this.tableData=response.data;
            },
            error =>{
              const h = this.$createElement;
              this.$notify({
                  title: '提示信息',
                  message: '加载失败',
              });
            }
            )
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
