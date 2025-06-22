<template>
    <div>
        <span style="font-size: 25px; "><b>>教师ID:{{ userTeacherId }}</b></span>

        <el-button class="logout_button" @click="logout" style="float: right; margin-top: 13px;">退出</el-button>
        <span style="font-size: large; float: right;">欢迎：<b>{{userTeacherName}}（{{userTitle}}）</b></span>

    </div>


</template>

<script>
import {getTeacherInfo} from "@/api/teacher";

export default{

    props:{
        // teacherId:String,
    },

    data(){
        return{
            userTeacherId:100000,
            userTeacherName:"",
            userTitle:"",
            form:{}
        }
    },

  //工程实践 古
    created(){
      this.userTeacherId = localStorage.getItem("teacherId");

      this.showTeacher()
    //   console.log("自定义组件");
    },

    methods:{


        // 按钮点击事件 -- 退出登录
        async logout () {
            await this.$store.dispatch('user/resetToken');
            localStorage.removeItem("teacherId");
            this.$router.push('/login');
        },



        showTeacher() {
          getTeacherInfo(this.userTeacherId).then(res => {
            this.userTeacherName = res.data.teacherName,
              this.userTitle = res.data.title
          })
        },
    }
}
</script>
