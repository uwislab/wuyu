<template>
    <el-container style="height: 100%; ">
        <el-aside width="200px" style="background-color: rgb(238, 241, 246); margin: 0;padding: 0; height: 100%;">
            <Aside />
        </el-aside>

        <el-container>
            <el-header style="font-size: 12px; border-bottom: 1px solid #ccc; line-height: 60px;">
            <Header></Header>
            </el-header>

            <el-main>
              <div class="container">
                <div class="login-wrapper">
                  <div class="header">{{enterScore.class_name}}{{this.lessonList["course"]}}成绩录入</div>
                  <div style="font-size: 20px; margin-bottom: 10px">
                  </div>
                  <div class="form-wrapper">
                    <div class="btn" style="display: block;width: 95%">
                    <el-upload action="http://49.51.69.99:33380/coursescore/upload"
                               :file-list="fileList"
                               :auto-upload="false"
                               :on-change="handleChange"
                    ><span>上传文件</span>
                    </el-upload>
                    </div>
                    <span class="btn" @click="Submit()">提交</span><span class="btn" @click="Back()">返回</span>
                  </div>
                </div>
              </div>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
import Aside from "@/views/teacher_workspace/Aside"
import Header from "@/views/teacher_workspace/Header"
import router from "@/router";
import {enterScore} from "@/api/fuScore";
import axios from "axios";
import {baseUrl} from "@/api/baseapi";


export default{

    data() {
      return {
        teacherId:"100000",
        lessonList:[],
        enterScore:{
          id:"",
          grade_ID:"",
          class_ID:"",
          class_name:"",
          morality_score:"",
          intelligence_score:"",
          physical_score:"",
          aesthetic_score:"",
          labour_score:"",
          evaluateDate:"",
          isenter:1,
          isreview:1,
        },
        fileList:[]
      }
    },

  //工程实践 古
    created(){
      this.teacherId=localStorage.getItem("teacherId");
      this.lessonList = this.$route.query;
      this.enterScore.grade_ID=this.lessonList["grade"]
      this.enterScore.class_ID=this.lessonList["classNum"]
      this.enterScore.class_name=this.lessonList["className"]
    },

    components: {
      Aside,
      Header
    },


    methods:{
        // 按钮点击事件 -- 退出登录
      async logout () {
        await this.$store.dispatch('user/resetToken');
        this.$router.push(`/login?redirect=${this.$route.fullPath}`);
      },

      Submit() {
        var param = new FormData();
        this.fileList.forEach(
          (val, index) => {
            param.append("file", val.raw);
          }
        );
        if(confirm("是否确认提交的数据完整?"))
          axios.post(baseUrl+"/coursescore/upload", param).then(responce => {
            if(responce.data["code"]===600){
              this.$message.success("成绩已导入")
              router.push({path: "/teacher_workspace/index"})
            }
            else {
              this.$message.error("导入失败")
            }
          });


      },

      Back(){
        router.push({path: "/teacher_workspace/index"})
      },

      handleChange(file, fileList){
        this.fileList = fileList;
      }

    }
}

</script>


<style scoped>
/* 主容器 */
.container {
  width: 100%;
  height: 100%;
  background-image: linear-gradient(to right, #fbc2eb, #a6c1ee);
}

/* 登录板块 */
.login-wrapper {
  background-color: #fff;
  width: 520px;
  height: 550px;
  border-radius: 15px;
  padding: 0 50px;
  position: relative;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

/* 标题 */
.header {
  font-size: 38px;
  font-weight: bold;
  text-align: center;
  line-height: 100px;
}

/* 输入区域 */
.form-wrapper {
  width: 100%;
}

/* 输入框 */
.input-item {
  display: block;
  width: 100%;
  margin-bottom: 20px;
  border: 0;
  padding: 10px;
  border-bottom: 1px solid black;
  font-size: 20px;
  outline: none;
}

.input-item::placeholder {
  text-transform: uppercase;
}

/* 确认按钮 */
.btn {
  text-align: center;
  padding: 10px;
  width: 45%;
  margin-top: 40px;
  margin-right: 20px;
  background-image: linear-gradient(to right, #a6c1ee, #fbc2eb);
  color: #fff;
  font-size: large;
}
</style>

