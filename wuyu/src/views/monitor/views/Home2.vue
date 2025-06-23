<template>
  <div class="home">

    <div style="margin-left: 200px">
      <div style="padding: 10px">
        <div style="margin: 10px 0">
          <el-button type="primary" @click="add2"> 新增</el-button>
          <el-button  class="ssss2" type="primary" @click="load">查询</el-button>
          <el-input v-model="search" placeholder="请输入内容" style="width: 300px; " class="ssss" ></el-input>

          <el-dialog
            title="提示"
            :visible.sync="dialogVisible"
            size="tiny">
            <el-form :model="form" label-width="120px">
              <el-form-item >
                登录名字：
                <el-input v-model="form.username" ></el-input>
              </el-form-item>

              <el-form-item >
                登录密码：
                <el-input  prefix-icon="el-icon-search" v-model="form.password" ></el-input>
              </el-form-item>

              <el-form-item >
                ip地址：

                <el-input  prefix-icon="el-icon-search" v-model="form.ip" ></el-input>
              </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="add">确 定</el-button>
  </span>
          </el-dialog>

        </div>
        <div>
          <el-table
            :data="tableData"
            border
            stripe
            style="width: 100%">

            <el-table-column
              prop="ip"
              label="ip">
            </el-table-column>

            <el-table-column
              prop="cpu"
              label="cpu用户">
            </el-table-column>
            <el-table-column
              prop="memtoatal"
              label="内存使用情况总计">
            </el-table-column>

            <el-table-column
              prop="memused"
              label="内存使用情况已使用">
            </el-table-column>

            <el-table-column
              prop="memfree"
              label="内存使用情况空闲">
            </el-table-column>

            <el-table-column
              prop="memsave"
              label="内存使用情况缓存">
            </el-table-column>


            <el-table-column
              prop="swaptotal"
              label="系统磁盘状态大小"
              sortable>
            </el-table-column>

            <el-table-column
              prop="swapused"
              label="系统磁盘状态已使用"
              sortable>
            </el-table-column>

            <el-table-column
              prop="swapfree"
              label="系统磁盘状态空闲"
              sortable>
            </el-table-column>

            <el-table-column
              fixed="right"
              label="操作"
              width="100">
              <template scope="scope">
                <el-button @click="searchs(scope.row)"  size="small">查看ip所在地址</el-button>
                <el-popover
                  ref="popover5"
                  placement="top"
                  width="160"
                >
                  <p>这是一段内容这是一段内容确定删除吗？</p>
                  <div style="text-align: right; margin: 0">
                    <el-button size="mini" type="text" @click="visible2 = false">取消</el-button>
                    <el-button type="primary" size="mini" @click="handleDelete(scope.row)">确定</el-button>
                  </div>
                </el-popover>

                <el-button v-popover:popover5 type="small" @show="handleDelete(scope.row)">删除</el-button>

              </template>

            </el-table-column>

          </el-table>

          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[2, 10, 20]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
          </el-pagination>
        </div>

        <el-dialog
          title="地图"
          :visible.sync="dialogVisible2"
          customClass="customWidth" style="width: 2000px" >
          <div   id="amap-main">
          </div>
        </el-dialog>
      </div>
    </div>

  </div>
</template>

<style>
.customWidth{
  width:80%;
  height:80%;
  margin-left: 1%;

}
</style>

<script>

import request from "../utils/request";
import { lazyAMapApiLoaderInstance } from 'vue-amap';
import {} from '../utils/canvas-nest'


export default {
  name: "Home2",
  data(){
    const self = this;
    return{
      form:{},
      search:'',
      total:0,
      dialogVisible:false,
      currentPage:1,
      dialogVisible2:false,
      pageSize:10,
      ip:"",
      sda:false,
      tableData: [
      ]

    }

  },
  created() {

    this.load()

  },
  methods:{

    load(){

      request.get("http://36.111.68.174:33380/user2",{
        params:{
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search:this.search
        }
      }).then(res =>{
        console.log("这是")
        console.log(res.data.records)
        this.tableData=res.data.records
      })
    },

    mounted(ip) {

      lazyAMapApiLoaderInstance.load().then(() => {

        console.log(ip[0])
        console.log(ip[1])

        this.sda = true

        this.map = new AMap.Map('amap-main', {center: new AMap.LngLat(ip[0], ip[1])});

      });

    },

    add(){

      request.post("http://36.111.68.174:33380/user2",this.form).then(res =>{

        if (res.data==null){

          console.log(res)

        }else {

          this.tableData=res.data.records

        }

        this.load()

      })

    },
    handleDelete(ip){
      console.log(ip.id)
      request.post("http://36.111.68.174:33380/user2/ip2",ip).then(res =>{

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

    handleSizeChange(pageSize){//改变当前每页的个数触发

      this.pageSize=pageSize
      this.load()

    },
    handleCurrentChange(pageNum){//改变当前页码触发

      this.currentPage=pageNum
      this.load()

    },
    add2(){

      this.dialogVisible=true
      this.form={}

    },searchs(p){

      request.post("http://36.111.68.174:33380/user2/ip",p).then(res =>{

        this.mounted(res.data)
        this.dialogVisible2=true

      })

    }

  }
}

</script>

<style>
#amap-main{
  width:1580px;
  height:800px;
  margin-left: 60%;
  margin-top: 50%;
  transform: translate(-60%,-100%);
}
</style>
