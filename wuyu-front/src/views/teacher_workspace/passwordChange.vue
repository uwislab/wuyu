<template>
  <el-container style="height: 100%; ">
    <el-aside width="200px" style="background-color: rgb(238, 241, 246); margin: 0;padding: 0; height: 100%;">
      <Aside/>
    </el-aside>
    <el-container>
      <el-header style="font-size: 12px; border-bottom: 1px solid #ccc; line-height: 60px;">
        <Header></Header>
      </el-header>

      <el-main class="login-wrapper">
        <el-form ref="form" :model="form" label-width="70px">
          <el-form-item label="用户名">
            <el-input v-model="form.username"></el-input>
          </el-form-item>
          <el-form-item label="原密码">
            <el-input show-password v-model="form.password" type="password" >
            </el-input>
          </el-form-item>
          <el-form-item label="新密码">
            <el-input show-password v-model="newpassword" type="password">
            </el-input>
          </el-form-item>
          <el-form-item label="再次输入">
            <el-input show-password v-model="confirmpassword" type="password">
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">修改</el-button>
            <el-button @click="back">取消</el-button>
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>
  </el-container>

</template>

<script>
import Aside from "@/views/teacher_workspace/Aside"
import Header from"@/views/teacher_workspace/Header"
import request from "@/utils/re_request";

export default {
  name: "passwordChange",

  components: {
    Aside,
    Header
  },

  data() {
    return {
      form: {
      },
      showPassword:false,

      newpassword:'',
      confirmpassword:'',
    };
  },

  created(){
    this.teacherId=localStorage.getItem("teacherId");
    this.load()
  },

  methods: {
    onSubmit() {
      if (this.newpassword != this.confirmpassword) {
        this.$message("两次密码不一致！")
        return;
      }
      if(this.newpassword == this.form.password){
        this.$message("不能使用原密码!")
        return;
      }
      try {
      request.post("teacher/updatePassword",
        {
          password: this.newpassword,
          id: this.teacherId,
          teacher_name:this.form.teacherName,
          gender:this.form.gender,
          phone_number:this.form.phoneNum,
          position:this.form.position,
          title:this.form.title,
          role:this.form.role,
          deleted:this.form.deleted,
          school_id:this.form.schoolId,
          username:this.form.username,
          political_appearance:this.form.politicalAppearance,
          birth_place:this.form.birthPlace,
          age:this.form.age,
          info:this.form.info
        }
      ).then(res => {
        res.data = this.form;

        if (res.data) {
          this.$message.success("保存成功")
          // this.dialogFormVisible = false
          this.load();
          this.back();
        } else {
          this.$message.error("保存失败")
          // this.dialogFormVisible = false
          this.load();
        }
      })
    }catch (error){
        console.error(error);
        alert("错误")
      }
    },

    load(){
      request.get("teacher/getTeacherInfo",{
        params:{
          teacher_id:this.teacherId
        }
      }).then(res =>{
        this.form = res.data;
      })},

    back(){
      this.newpassword="";
      this.confirmpassword="";
    },

  }
}

</script>

<style>
.login-wrapper {
  background-color: #ffecec;
  width: 580px;
  height: 0px;
  border-radius: 20px;
  padding: 0 20px;
  position: relative;
  left: 50%;
  top: 40%;
  transform: translate(-50%, -40%);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
</style>

