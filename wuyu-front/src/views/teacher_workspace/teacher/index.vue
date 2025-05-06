<template>
  <!--  工程实践 古  -->
  <el-container style="height: 100%; ">
    <el-aside width="200px" style="background-color: rgb(238, 241, 246); margin: 0;padding: 0; height: 100%;">
      <Aside/>
    </el-aside>

    <el-container>
      <el-header style="font-size: 12px; border-bottom: 1px solid #ccc; line-height: 60px;">
        <Header></Header>
      </el-header>
      <el-main>
        <div class="container">
          <div class="header">
            <div class="welcome_word">{{ teacherInfo.teacherName }}教师: 您好</div>
            <el-button class="logout_button" @click="logout">退出系统</el-button>
          </div>
          <div class="basic_info">
            <div class="block_name">您的课程</div>
            <div class="class_list">
              <el-table :data="lessonList" border>
                <el-table-column
                  prop="grade"
                  label="年级"
                  align="center"
                ></el-table-column>
                <el-table-column
                  prop="classNum"
                  label="班级"
                  align="center"
                ></el-table-column>
                <el-table-column
                  prop="course"
                  label="课程名称"
                  align="center"
                ></el-table-column>
                <el-table-column
                  prop="className"
                  label="班级名称"
                  align="center"
                ></el-table-column>
                <el-table-column
                  prop="teacherName"
                  label="授课人"
                  align="center"
                ></el-table-column>


              </el-table>
            </div>
          </div>
          <div class="message_info">
            <div class="block_name">待办工作</div>
            <div class="class_list">
              <el-table :data="workList[1]" border>
                <el-table-column
                  prop="course"
                  label="课程名称"
                  align="center"
                ></el-table-column>
                <el-table-column
                  prop="className"
                  label="班级名称"
                  align="center"
                ></el-table-column>
                <el-table-column
                  prop="teacherName"
                  label="授课人"
                  align="center"
                ></el-table-column>
                <el-table-column
                  label="操作项"
                  align="center"
                ><template slot-scope="scope">
                  <el-button @click="enter(scope.row)" type="text" size="small">成绩录入</el-button>
                </template></el-table-column>
              </el-table>
            </div>
          </div>
          <div class="work_info">
            <div class="block_name">已办工作</div>
            <div class="class_list">
              <el-table :data="workList[0]" border>
                <el-table-column
                  prop="course"
                  label="课程名称"
                  align="center"
                ></el-table-column>
                <el-table-column
                  prop="className"
                  label="班级名称"
                  align="center"
                ></el-table-column>
                <el-table-column
                  prop="teacherName"
                  label="授课人"
                  align="center"
                ></el-table-column>
                <el-table-column
                  label="操作项"
                  align="center"
                ><template slot-scope="scope">
                  成绩已录入
                </template></el-table-column>
              </el-table>
            </div>
          </div>
        </div>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>


//工程实践 古
import {getClassBasicInfo, getLesson, getTeacherInfo, } from "@/api/teacher";
import {getWorkList} from "@/api/fuScore";

import Aside from "@/views/teacher_workspace/Aside";
import Header from "@/views/teacher_workspace/Header";
import router from "@/router";

export default {
  name: "index",

  //工程实践 古
  components: {
    Aside,
    Header
  },

  data() {
    return {
      teacherInfo: {},
      classBasicInfoList: [],
      lessonList:[],
      workList:[]
    };
  },

  //工程实践 古
  mounted() {
    let teacher_id = localStorage.getItem("teacherId");

    // let teacher_id = 100001;
    getTeacherInfo(teacher_id).then(res => {
      console.log('TeacherInfo:' + res.data);
      this.teacherInfo = res.data
    })
    getLesson(teacher_id).then(res => {
      console.log(res.data);
      this.lessonList = res.data
    })

    //获取事项
    getWorkList(teacher_id).then(res => {
      console.log(res.data);
      this.workList = res.data
    })

  },

  methods: {
    // 按钮点击事件 -- 退出登录
    async logout () {
            await this.$store.dispatch('user/resetToken');
            localStorage.removeItem("teacherId");
            this.$router.push('/login');
    },

    // 按钮点击事件 -- 表格按钮1
    handleClick(object) {
      console.log(object)
    },

    enter(object){
      router.push({path: "/teacher_workspace/lessonInfo", query: object})
    }
  }
}
</script>

<style scoped>
.container {
  width: 100%;
  height: auto;
  border: solid 1px black;
}

.header {
  width: 100%;
  height: 60px;
  border-bottom: solid 1px black;
  margin-top: 30px;
  padding: 10px;
  display: flex;
  flex-direction: row;
  align-items: center;
}

.welcome_word {
  font-size: 20px;
  margin-right: 10px;
}

.logout_button {
  font-size: 15px;
}

.basic_info, .work_info, .message_info {
  width: 100%;
  height: auto;
  border: solid 1px red;
  margin-top: 10px;
  padding: 10px;
}

.block_name {
  font-size: large;
  font-family: "Cooper Black";
  margin: 5px;
  border-bottom: solid 1px black;
}

.teacher_info {
  width: 100%;
  height: 40%;
  border: solid 1px green;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
}

.teacher_name, .teacher_gender, .teacher_phone, .teacher_position {
  border: solid 1px black;
  font-size: 18px;
}

.class_list {
  width: 100%;
  height: auto;
  border: solid 1px blue;
  margin-top: 10px;
}
</style>
