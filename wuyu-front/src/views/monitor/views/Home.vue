<template>
  <div class="home">


    <div style="margin-left: 200px">
      <div style="padding: 10px">
        <div style="margin: 10px 0">
          <el-form ref="form" :model="form" label-width="60px">
            <el-form-item >
              输入查询ip：
              <el-input v-model="form.ip">
              </el-input>
            </el-form-item>
            <el-form-item>
              <el-button  @click="searchs">查询</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
      <div   id="amap-main">
      </div>
    </div>

  </div>
</template>
<script>

import request from "../utils/request";
import { lazyAMapApiLoaderInstance } from 'vue-amap';
export default {
  name: "Home",
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

      ip2:[113.309213,23.146569],
      sda:false,
      numberValidateForm: {
        ip: ''
      },
      tableData: [
      ]
    }

  },
  created() {
    this.load()
    this.mounted(this.ip2)
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

        this.sda = true
        this.map = new AMap.Map('amap-main',
          {
            zoom:13,
            center: new AMap.LngLat(ip[0], ip[1])
          }
        );
      });
    },
    searchs(){
      console.log(this.form)
      request.post("http://36.111.68.174:33380/user2/ip3",this.form).then(res =>{
        console.log("进入类")
        console.log(res.data.trueip);
        if (res.data.trueip!=null){
          request.post("/user2/ip4",res.data).then(res2 =>{


            this.mounted(res2.data)

            this.dialogVisible2=true
          })
        }
        else {
          res.data.trueip=res.data.ip

          request.post("http://36.111.68.174:33380/user2/ip4",res.data).then(res2 =>{


            this.mounted(res2.data)

            this.dialogVisible2=true
          })
        }

      })
    }

  }
}
</script>

<style >
#amap-main{
  width:1900px;
  height:700px;
  margin-left: 60%;
  margin-top: 50%;
  transform: translate(-60%,-140%);
}
</style>
