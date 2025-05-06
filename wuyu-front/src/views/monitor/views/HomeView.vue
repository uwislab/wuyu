<template>
  <div class="home">
    <div style="padding: 10px">
      <div style="margin: 10px 0">
        <el-button type="primary"@click="add"> 新增</el-button>
        <el-button type="primary"> 导入</el-button>
        <el-button type="primary"> 导出</el-button>

      </div>
      <div style="margin: 100px 0">
        <el-input v-model="search" placeholder="请输入内容" style="width: 20%" ></el-input>
        <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
      </div>


      <el-table
        :data="tableData"
        border
        stripe
        style="width: 100%">
        <el-table-column
          prop="id"
          label="ID"
          sortable>
        </el-table-column>
        <el-table-column
          prop="username"
          label="用户名">
        </el-table-column>
        <el-table-column
          prop="nickName"
          label="昵称">
        </el-table-column>

        <el-table-column
          prop="age"
          label="年龄">
        </el-table-column>

        <el-table-column
          prop="sex"
          label="性别">
        </el-table-column>

        <el-table-column
          prop="address"
          label="地址">
        </el-table-column>

        <el-table-column
          fixed="right"
          label="操作"
          width="100">
          <template scope="scope">
            <el-button @click="handleEdit(scope.row)"  size="small">编辑</el-button>


            <el-popover
              ref="popover5"
              placement="top"
              width="160"
            >
              <p>这是一段内容这是一段内容确定删除吗？</p>
              <div style="text-align: right; margin: 0">
                <el-button size="mini" type="text" @click="visible2 = true">取消</el-button>
                <el-button type="primary" size="mini" @click="handleDelete(scope.row.id)">确定</el-button>
              </div>
            </el-popover>

            <el-button v-popover:popover5 type="small" @show="handleDelete(scope.row.id)">删除</el-button>



          </template>
        </el-table-column>


      </el-table>
      <div style="margin: 10px 0">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[2, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>

        <el-dialog
          title="提示"
          :visible.sync="dialogVisible"
          size="tiny">
          <el-form :model="form" label-width="120px">

            <el-form-item label="用户名">
              <el-input v-model="form.username" style="width: 80%"></el-input>
            </el-form-item>

            <el-form-item label="昵称">
              <el-input v-model="form.nickName" style="width: 80%"></el-input>
            </el-form-item>

            <el-form-item label="年龄">
              <el-input v-model="form.age" style="width: 80%"></el-input>
            </el-form-item>

            <el-form-item label="性别">


              <el-radio v-model="form.sex" label="男">男</el-radio>
              <el-radio v-model="form.sex" label="女">女</el-radio>
              <el-radio v-model="form.sex" label="未知">未知</el-radio>

            </el-form-item>

            <el-form-item label="地址">
              <el-input type="textarea" v-model="form.address" style="width: 80%"></el-input>
            </el-form-item>



          </el-form>
          <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="save">确 定</el-button>
  </span>
        </el-dialog>




      </div>



    </div>
  </div>
</template>

<script>
// @ is an alias to /src


import request from "../utils/request";

export default {
  name: 'HomeView',
  data(){
    return{
      form:{},
      search:'',
      dialogVisible:false,
      currentPage:1,
      pageSize:10,
      total:0,
      tableData: [

      ]
    }


  },
  created() {
    this.load()
  },
  methods:{
    load(){
      request.get("http://36.111.68.174:33380/user",{
        params:{
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search:this.search
        }

      }).then(res =>{
        console.log("这里是")
        console.log(res.data.records)
        this.tableData=res.data.records
        this.total=res.data.total


      })

    },
    add(){
      this.dialogVisible=true
      this.form={}

    },
    save(){
      if(this.form.id){//更新

        request.put("http://36.111.68.174:33380/user",this.form).then(res =>{
          console.log(res)

          if(res.code==='0'){
            this.$message({
              type:"success",
              message:"更新成功"
            })

          }else {

            this.$message({
              type:"success",
              message:res.msg
            })
          }
          this.load()
          this.dialogVisible=false

        })

      }else {//新增
        request.post("http://36.111.68.174:33380/user",this.form).then(res =>{
          console.log(res)
          if(res.code==='0'){
            this.$message({
              type:"success",
              message:"新增成功"
            })

          }else {

            this.$message({
              type:"success",
              message:res.msg
            })
          }
          this.load()
          this.dialogVisible=false
        })


      }

    },
    handleDelete(id){
      console.log(id)
      request.delete("http://36.111.68.174:33380/user/"+id).then(res =>{



        if(res.code==='0'){
          this.$message({
            type:"success",
            message:"删除成功"
          })

        }else {

          this.$message({
            type:"error",
            message:res.msg
          })
        }
        this.load()





      })

    },
    handleEdit(row){

      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible=true

    },
    handleSizeChange(pageSize){//改变当前每页的个数触发
      this.pageSize=pageSize
      this.load()

    },
    handleCurrentChange(pageNum){//改变当前页码触发
      this.currentPage=pageNum
      this.load()

    }



  }

}
</script>
