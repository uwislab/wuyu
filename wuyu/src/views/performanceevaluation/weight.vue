<template>
    <div class="score">
      <!-- <el-button type="primary" round @click="dialogFormVisible = true">增加权重</el-button> -->
      <el-dialog :title="isEdit ? '更新权重(总权重为1，每项最多可包含两位小数)':'增加权重（总权重为1，每项最多可包含两位小数)'" :visible.sync="dialogFormVisible" :before-close="handleClose">
        <el-form :model="form" ref="form" :rules="rules" label-width="100px">
            <el-form-item label="老师"  required prop="tid" >
              <el-select v-model="form.tid">
                <el-option v-for="item in teachers" :value="item.teacherID" :label="item.username"/>
              </el-select>
            </el-form-item>
            <el-form-item label="德育权重"  required prop="virtueWeight">
              <el-input v-model="form.virtueWeight" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="智育权重"  required prop="intelligenceWeight">
              <el-input v-model="form.intelligenceWeight" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="体育权重"  required prop="sportsWeight">
              <el-input v-model="form.sportsWeight" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="美育权重"  required prop="artWeight">
              <el-input v-model="form.artWeight" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="劳育权重"  required prop="laborWeight">
              <el-input v-model="form.laborWeight" autocomplete="off"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="handleCancle">取 消</el-button>
          <el-button type="primary" @click="handleForm">确 定</el-button>
        </div>
    </el-dialog>
      <el-table
    :data="list"
    style="width: 100%"
    height="600"
    border="1"
    stripe="1"
    >
    <!-- <el-table-column
      prop="id"
      label="ID"
      width="120">
    </el-table-column> -->
    <el-table-column
      prop="teacher.username"
      label="教师名称"
      width="210"
      >
    </el-table-column>
    <el-table-column
      prop="virtueWeight"
      label="德育权重"
      width="210">
    </el-table-column>
    <el-table-column
      prop="intelligenceWeight"
      label="智育权重"
      width="210">
    </el-table-column>
    <el-table-column
      prop="sportsWeight"
      label="体育权重"
      width="210">
    </el-table-column>
    <el-table-column
      prop="artWeight"
      label="美育权重"
      width="210">
    </el-table-column>
    <el-table-column
      prop="laborWeight"
      label="劳育权重"
      width="210">
    </el-table-column>
    
    <!-- <el-table-column
      
      label="操作"
      width="300">
      <template slot-scope="scope">
        <!-- <el-button
        type="primary" icon="el-icon-edit"
          @click.native.prevent="handleUpdate(scope.row)"
          >
          编辑
        </el-button> -->
        <!-- <el-button
        type="danger" icon="el-icon-delete" 
          @click.native.prevent="$event => handleDelete(scope.row.id)"
          >
          删除
        </el-button> -->
      <!-- </template>
    </el-table-column> --> -->
  </el-table>

  <!-- 分页 -->
  <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="[10, 20, 30, 40, 50]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      :hide-on-single-page="true"
      background
      class="pagination"
      >
    </el-pagination>
    </div>
</template>

<script>
import request from "@/utils/re_request"
import { Message } from 'element-ui';
export default {
    name:'weight',
    data() {
        // var validateScore = (rule, value, callback) => {
        //   var numReg = /^[0-9]*$/
        //   var numRe = new RegExp(numReg)
        //   if(!numRe.test(value)){
        //     callback(new Error('请正确输入分数！'))
        //   }else if(value < 0 || value > 1){
        //     callback(new Error('成绩输入范围为 0~1 '))
        //   }else{
        //     callback()
        //   }
        // }
        var validateScore = (rule, value, callback) => {
          if(value < 0 || value > 1){
            callback(new Error('成绩输入范围为 0~1 '))
          }else{
            callback()
          }
        }
        return {    
          list: [
        { teacher: { username: "孙川" }, virtueWeight: 0.2, intelligenceWeight: 0.3, sportsWeight: 0.1, artWeight: 0.2, laborWeight: 0.2 },
        { teacher: { username: "唐维启" }, virtueWeight: 0.3, intelligenceWeight: 0.2, sportsWeight: 0.1, artWeight: 0.1, laborWeight: 0.3 },
        { teacher: { username: "张五" }, virtueWeight: 0.1, intelligenceWeight: 0.4, sportsWeight: 0.2, artWeight: 0.1, laborWeight: 0.2 },
        { teacher: { username: "陈一" }, virtueWeight: 0.3, intelligenceWeight: 0.1, sportsWeight: 0.2, artWeight: 0.2, laborWeight: 0.2 },
        { teacher: { username: "微生蓓" }, virtueWeight: 0.1, intelligenceWeight: 0.3, sportsWeight: 0.2, artWeight: 0.2, laborWeight: 0.2 },
        { teacher: { username: "百里中茂" }, virtueWeight: 0.2, intelligenceWeight: 0.1, sportsWeight: 0.3, artWeight: 0.2, laborWeight: 0.2 },
        { teacher: { username: "孙二" }, virtueWeight: 0.1, intelligenceWeight: 0.2, sportsWeight: 0.3, artWeight: 0.1, laborWeight: 0.3 },
        { teacher: { username: "小小王" }, virtueWeight: 0.2, intelligenceWeight: 0.2, sportsWeight: 0.2, artWeight: 0.2, laborWeight: 0.2 },
        { teacher: { username: "左凡" }, virtueWeight: 0.1, intelligenceWeight: 0.3, sportsWeight: 0.2, artWeight: 0.2, laborWeight: 0.2 },
        { teacher: { username: "李江平" }, virtueWeight: 0.2, intelligenceWeight: 0.2, sportsWeight: 0.1, artWeight: 0.2, laborWeight: 0.3 },
        { teacher: { username: "小小李" }, virtueWeight: 0.3, intelligenceWeight: 0.1, sportsWeight: 0.2, artWeight: 0.2, laborWeight: 0.2 },
        { teacher: { username: "袁鑫" }, virtueWeight: 0.2, intelligenceWeight: 0.2, sportsWeight: 0.3, artWeight: 0.1, laborWeight: 0.2 },
        { teacher: { username: "李四" }, virtueWeight: 0.1, intelligenceWeight: 0.2, sportsWeight: 0.3, artWeight: 0.2, laborWeight: 0.2 },
        { teacher: { username: "牢李" }, virtueWeight: 0.3, intelligenceWeight: 0.2, sportsWeight: 0.1, artWeight: 0.2, laborWeight: 0.2 }
      ],
            currentPage: 1,//当前页
            pageSize: 10,//每页显示记录数
            total: 0,//总记录数
            dialogFormVisible:false, // 表单是否显示
            teachers:[], // 教师数据,
            teachersFilter:[],// 教师过滤数据
            form:{ // 表单
              virtueWeight:[1],
              intelligenceWeight:'0.2',
              sportsWeight:'0.3',
              artWeight:'0.4',
              laborWeight:'0.1',
              tid:'1',
            },
            // 是否是更新操作
            isEdit:false, 
            // 操作的id
            operationId:0,

            rules:
            {
              virtueWeight:[{required:true,  validator: validateScore, trigger:'blur'}],
              intelligenceWeight:[{required:true, validator: validateScore, trigger:'blur'}],
              sportsWeight:[{required:true,  validator: validateScore, trigger: 'blur'}],
              intelligence:[{required:true,  validator: validateScore, trigger: 'blur'}],
              artWeight:[{required:true,  validator: validateScore, trigger: 'blur'}],
              laborWeight:[{required:true,  validator: validateScore, trigger: 'blur'}],
            },

        }
    },
    methods: {
        // 获取老师设置的权重数据
        getData() {
            request.post('/weight/list', {
              'currentPage': this.currentPage,
              'pageSize':this.pageSize
            }).then(
                response => {
                    if(response.code === 600) {
                        this.list = response.data
                        this.total = response.count
                        // Message.success(response.msg)
                    }
                }
            )
        },
        // 当前页变化
        handleCurrentChange(currentPage) {
          this.currentPage = currentPage
          this.getData()
        },
        // 页面大小更新
        handleSizeChange(pageSize) {
          this.currentPage = 1
          this.pageSize = pageSize
          this.getData()
        },
        // 获取未设置科目权重的老师数据
        getTeacherData() {
          request.get('/teacher/weight').then(
            response => {
              if(response.code === 600) {
                this.teachers = response.data
            }
          }
          )
        },
        // 处理增加或者编辑逻辑
        handleForm() {
          //  验证德智体美劳是否总权重为1
          if(this.form.virtueWeight * 1 + this.form.intelligenceWeight * 1 + this.form.sportsWeight * 1 + 
          this.form.artWeight * 1 + this.form.laborWeight * 1  !== 1) {
                Message.error("所得权重之和不为1！")
                return 
          }
          // 关闭对话框
          this.dialogFormVisible = false
          // 根据isEdit处理逻辑
          // 后端处理
          if(this.isEdit) {
            request.put('/weight/update', {
              id: this.operationId,
              ...this.form
            }).then(
              response => {
                if(response.code === 600) {
                  // 前端更新
                  this.getData()
                  Message.success(response.msg)
                }
              }
            )
          } else {
            request.post('/weight/add', {
              ...this.form
            }).then(
              response => {
                if(response.code === 600) {
                  // 前端更新
                  // 数据更新
                  this.getData()
                  // 教师更新
                  this.getTeacherData()
                  Message.success(response.msg)
                }
              }
            )
          }
          // 重置form和isEdit
          // 清空表单中的值

        //   for(let key in this.form) {
        //     this.form[key] = '';
        //   }

          this.$refs["form"].resetFields()
          this.isEdit = false
          
        },
        // 处理权重数据更新
        handleUpdate(item) {
          // 将isEdit设置为true方便对话框确定时的逻辑处理
          this.isEdit = true
          // 开启对话框
          this.dialogFormVisible = true

          // 填充数据
          this.$nextTick(()=>{
		    //在这里进行对form表单的赋值
            for(let key in item) {
              if(this.form.hasOwnProperty(key)) {
                this.form[key] = item[key];
              }
            }
            this.form.tid = item.teacher.teacherID
            this.operationId = item.id
	        })

        },
        // 处理从下方的取消 确定关闭对话框
        handleCancle() {
          // 关闭对话框
          this.dialogFormVisible = false

          // 清空表单及初始化isEdit
          this.$refs["form"].resetFields()
          this.isEdit = false
        },
        // 处理从右上角的的×关闭对话框
        handleClose(done){
          this.$confirm('确认关闭？')
          .then(_ => {
            this.handleCancle()
            done();
          })
          .catch(_ => {});
        },
        // 处理删除数据的逻辑
        handleDelete(id) {
          this.$confirm('确认删除吗？')
          .then(_ => {
            request.delete('/weight/delete', {
              data:{
                'id': id
              }
            }).then(
              response => {
                if(response.code === 600) {
                  this.getData()
                  Message.success(response.msg)
                }
              }
            )
          })
          .catch(_ => {});
        }
    },
    mounted(){
        this.getData()
        this.getTeacherData()
    },
}
</script>

<style>
  .score{
    margin: 30px 50px 0 50px;
  }

  .system{
    margin-left: 20px;
  }

  .pagination {
    margin-left: 30%;
    font-size: 14px;
  }
</style>