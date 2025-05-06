<template>
  <div>
    <el-row>
      <el-carousel :interval="4000" type="card" height="280px">
        <el-carousel-item v-for="item in imagebox" :key="item.idView">
          <img :src="item.idView" class="image">
        </el-carousel-item>
      </el-carousel>
    </el-row>
    <el-row :gutter="50" style="margin-bottom: 120px; flex-wrap: wrap; display:flex;" >
      <el-col :span="12">
        <el-card style="color: #606266; min-width: 100%; height: 100%; margin-right: 20px;">

          <h1>最新一次的数据更新时间<div style="color: #F56C6C; margin-top: 20px; text-align: center">{{result_serve.updatatime}}</div></h1>
          <div style="color: #606266">
            <h4>数据库会定期自动更新数据</h4>
            <h4>更新时间为每周星期天凌晨2点</h4>
          </div>
          <el-col style="text-align: center; margin-top: 30px">
            <el-button type="primary" @click="handle">数据预处理与更新设置</el-button>
          </el-col>
          <el-col style="text-align: center; margin-top: 30px">
            <img src="./Images/大数据1.jpg">
          </el-col>
        </el-card>
      </el-col>
      <el-col :span="12">
        <div v-loading="loading"
             element-loading-text="拼命更新中"
             element-loading-spinner="el-icon-loading"
             element-loading-background="rgba(0, 0, 0, 0.8)"
             :data="result_serve.result"
             style="width: 100%">
          <el-card style="color: #606266; min-width: 100%; height: 100%; margin-right: 20px; text-align: center">
            <el-row style="margin-top: 20px">
              <div style="margin-bottom: 40px; color: #67C23A">
                <h1>根据数据库数据更新</h1>
              </div>
              <el-col>
                <el-button type="primary" @click="submitForm">手动数据更新</el-button>
                <p>{{result_serve.result}}</p>
              </el-col>
            </el-row>

          </el-card>
          <el-card style="margin-top: 10px">
            <el-row style="margin-top: 20px">
              <div style="margin-bottom: 40px; color: #67C23A; text-align: center">
                <h1>导入excel表格更新</h1>
              </div>
              <div class="app-container">
                <el-form label-width="120px">
                  <el-form-item label="信息描述">

                    <el-button type="primary" size="small" @click="createexcel('ruleForm')"><i class="el-icon-download"/> 导出学生五育成绩模板</el-button>

                  </el-form-item>
                  <el-form-item label="选择Excel文件">
                    <el-upload
                      ref="upload"
                      :auto-upload="false"
                      :disabled="importBtnDisabled"
                      :limit="1"
                      action="http://36.111.68.174:33380/excel/upload"
                      :on-change="onChange"
                      :on-success="fileUploadSuccess"
                      :on-error="fileUploadError"
                      name="file"
                      accept=".xls,.xlsx">
                      <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                      <el-button
                        :loading="loading_excel"
                        style="margin-left: 10px;"
                        size="small"
                        type="success"
                        @click="submitUpload"
                        :disabled="disable_up">{{fileUploadBtnText}}</el-button>
                      <div slot="tip" class="el-upload__tip">只能上传xls/xlsx文件</div>
                    </el-upload>
                  </el-form-item>
                </el-form>
              </div>
            </el-row>
          </el-card>
        </div>

      </el-col>
    </el-row>



  </div>
</template>

<script>
import * as echarts from 'echarts';
// import axios from "@/plugins/axios";
import axios from "@/utils/axios";

export default {
  name: "DataProcessing",
  data() {
    return {
      result_serve:{
        result: '',
        updatatime: ''
      },
      loading: false,
      disable_up: true,
      imagebox:[
        {id:0,idView:require('./Images/蔡徐坤1.gif')},
        {id:1,idView:require('./Images/大数据1.jpg')},
        {id:2,idView:require('./Images/大数据2.jpg')},
        {id:3,idView:require('./Images/大数据3.jpg')},
        {id:4,idView:require('./Images/特朗普.jpg')},
        ],
      // 浏览器宽度
      screenWidth :0,
      importBtnDisabled: false, // 按钮是否禁用,
      fileUploadBtnText: '上传到数据库并进行预处理更新',
      loading_excel: false,
      flag: ''
    };
  },
  methods: {
    submitForm() {
      this.loading = true;
      this.dialogFormVisible = false;
      //axios.post()为发送数据到后端 改axios.get 后端用@RequestMapping("/save")@ResponseBody接收
      axios.get("http://36.111.68.174:33380/dataprocessing/deal").then(res=>{
        console.log(res)
        this.result_serve = res.data
        this.loading = false
      })
    },
    resetForm_N(formName) {
      this.$refs[formName].resetFields();
      this.result_serve_N = "错误!";
    },
    setSize:function () {
      // 通过浏览器宽度(图片宽度)计算高度
      this.bannerHeight = 400 / 1920 * this.screenWidth;
    },
    handle (){
      //  路径/home对应我在router目录下index.js中定义的path属性值
      this.$router.push('/analyze/ProcessSet');
    },
    submitUpload() {
      this.fileUploadBtnText = '正在导入';
      this.importBtnDisabled = true
      this.loading_excel = true
      this.loading = true


      this.$refs.upload.submit();
    },
    onChange(file) {
      console.log(file.name)
      console.log(file.name.split('.')[1])
      if (file.name.split('.')[1] === 'xlsx' || file.name.split('.')[1] === 'xls'){
        this.disable_up = false;
      }
      else {
        this.disable_up = true;
      }
    },
    fileUploadSuccess(response) {
      console.log(response)
      if (response === 0) {
        this.fileUploadBtnText = '导入失败';
        this.loading = false
        this.loading_excel = false
      }
      if (response === 1) {
        this.fileUploadBtnText = '正在进行数据预处理与更新';
          axios.get("http://36.111.68.174:33380/dataprocessing/deal").then(res=>{
            // console.log(res)
            this.fileUploadBtnText = '数据预处理与更新完成';
            this.loading_excel = false;
            this.loading = false;
            this.$message({
              type: 'success',
              message: "成功"
            })
          })
        }
    },
    fileUploadError(response) {
      this.fileUploadBtnText = '导入失败'
      this.loading = false
      this.loading_excel = false
      this.$message({
        type: 'error',
        message: '导入失败'
      })
    },
    createexcel(){
      axios.get("http://36.111.68.174:33380/excel/createexcel").then(res1 => {
        console.log(res1);
        if (res1.data === '成功'){
          alert("excel模板已生成至桌面")
        }else {
          alert("excel模板已存在")
        }
      })
    },
  },
  mounted() {

    // 首次加载时,需要调用一次
    this.screenWidth =  window.innerWidth;
    this.setSize();
    // 窗口大小发生改变时,调用一次
    window.onresize = () =>{
      this.screenWidth =  window.innerWidth;
      this.setSize();
    }
    axios.get("http://36.111.68.174:33380/dataprocessing/uptime").then(res=>{
      // console.log(res)
      this.result_serve = res.data
    })
  }
}

</script>

<style scoped>
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
img{
  /*设置图片宽度和浏览器宽度一致*/
  width:100%;
  height:inherit;
}
</style>
