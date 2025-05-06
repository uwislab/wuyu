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

                <el-form ref="form" :model="form" label-width="80px" :rules="rules">
                    <el-form-item label="教师ID">
                    <el-input v-model="form.id" style="width: 20%;" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="姓名" prop="teacherName">
                    <el-input v-model="form.teacherName" style="width: 20%;"></el-input>
                </el-form-item>
                <el-form-item label="年龄" prop="age">
                    <el-input v-model="form.age" style="width: 20%;"></el-input>
                </el-form-item>
                <el-form-item label="性别" prop="gender">
                    <el-radio-group v-model="form.gender">
                    <el-radio :label="1">男</el-radio>
                    <el-radio :label="0">女</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="籍贯" prop="birthplace">
                    <el-input v-model="form.birthPlace" style="width: 20%;"></el-input>
                </el-form-item>
                <el-form-item label="政治面貌" prop="politicalAppearance">
                  <el-select style="width: 20%;" v-model="form.politicalAppearance" placeholder="请选择">
                    <el-option label="党员" value="党员"></el-option>
                    <el-option label="群众" value="群众"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="联系方式" prop="phoneNum">
                    <el-input v-model="form.phoneNum" style="width: 30%;"></el-input>
                </el-form-item>
                <el-form-item label="职称" prop="title">
                    <el-select v-model="form.title" placeholder="请选择">
                    <el-option label="初级教师" value="初级教师"></el-option>
                    <el-option label="中级教师" value="中级教师"></el-option>
                    <el-option label="高级教师" value="高级教师"></el-option>
                    <el-option label="特级教师" value="特级教师"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="角色" prop="role">
                  <el-select v-model="form.role" placeholder="请选择">
                    <el-option label="班主任" value="班主任"></el-option>
                    <el-option label="管理员" value="管理员"></el-option>
                    <el-option label="任课老师" value="任课老师"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="备注信息" prop="info">
                    <el-input type="textarea" v-model="form.info" :rows="8" style="width: 70%"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="submitForm">完成</el-button>
                    <!-- <el-button>取消</el-button> -->
                </el-form-item>
                </el-form>
            </el-main>
    </el-container>
</el-container>
</template>

<script>
import {getTeacherInfo,updateTeacherInfo} from "@/api/teacher";
import Aside from "@/views/teacher_workspace/Aside"
import Header from "@/views/teacher_workspace/Header"
import axios from "axios"
import request from "@/utils/re_request";

export default{

    data() {
      return {
        form: {
            id:1,
            teacherName:"",
            gender:1,
            phoneNum:"",
            position:"",
            title:"",
            role:"",
            deleted:0,
            schoolId:1,
            username:"",
            password:"",
            politicalAppearance:"",
            birthPlace:"",
            age:"",
            info:""
        },
        rules: {
          teacherName: [
            { required: true, message: '请输入姓名', trigger: 'blur' }
          ],
          age: [
            { required: true, message: '请输入年龄', trigger: 'blur' },
            { pattern: /^\d{1,2}$/, message: '年龄必须为1到2位数字', trigger: 'blur' }
          ],
          gender: [
            { required: true, message: '请选择性别', trigger: 'change' }
          ],
          birthPlace: [
            { required: true, message: '请输入籍贯', trigger: 'blur' }
          ],
          politicalAppearance: [
            { required: true, message: '请选择政治面貌', trigger: 'change' }
          ],
          phoneNum: [
            { required: true, message: '请输入联系方式', trigger: 'blur' },
            { pattern: /^1[3|4|5|7|8][0-9]\d{8}$/, message: '请输入正确的手机号', trigger: 'blur' }
          ],
          title: [
            { required: true, message: '请选择职称', trigger: 'change' }
          ],
          role: [
            { required: true, message: '请选择角色', trigger: 'change' }
          ],
          info: [
            { required: false }
          ]
        }
        }
      },


    created(){
      this.teacherId=localStorage.getItem("teacherId");
      this.load()
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

      load(){
        request.get("teacher/getTeacherInfo",{
          params:{
            teacher_id:this.teacherId
          }
        }).then(res =>{
          this.form = res.data;
          const  gender = this.form.gender;
        })},

      onSubmit() {
        console.log(this.form);
      },

      submitForm() {
        this.$refs.form.validate(valid => {
          if (valid) {
            // 表单验证通过，执行提交逻辑
            this.save();
          } else {
            // 表单验证失败，不执行提交逻辑
            this.$message.error('表单验证失败，请检查输入');
          }
        });
      },

      save(){
        request.post("teacher/updateTeacherInfo",this.form

        ).then(res =>{

          res.data=this.form;
          if(res.data){
            this.$message.success("修改成功")
            // this.dialogFormVisible = false
            this.load();
          }else{
            this.$message.error("修改失败")
            // this.dialogFormVisible = false
            this.load();
          }
        })
      },

      },

}

</script>
