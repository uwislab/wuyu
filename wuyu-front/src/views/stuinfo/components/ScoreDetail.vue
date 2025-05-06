<template>
  <el-card class="form-container" shadow="never">
    <el-form :model="student" :rules="rules" ref="studentFrom" label-width="150px">
      <el-form-item label="ID：" >
        <el-input  v-model="student.id" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="学号：">
        <el-input  v-model="student.snumber" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="德育：">
        <el-input  v-model="student.deyu" ></el-input>
      </el-form-item>
      <el-form-item label="智育：">
        <el-input  v-model="student.zhiyu" ></el-input>
      </el-form-item>
      <el-form-item label="体育：">
        <el-input  v-model="student.tiyu" ></el-input>
      </el-form-item>
      <el-form-item label="美育：">
        <el-input  v-model="student.meiyu" ></el-input>
      </el-form-item>
      <el-form-item label="劳育：">
        <el-input  v-model="student.laoyu" ></el-input>
      </el-form-item>
      <el-form-item label="家长评价：">
        <el-input type="textarea" v-model="student.parentEvaluate" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="教师评价：">
        <el-input type="textarea" v-model="student.teacherEvaluate"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('studentFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('studentFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
  import {createStudent, getStudent, updateStudent,getScore,updateScore} from "@/api/stuinfo";

  const defaultStudent= {
    id: '',
    name:'',
    snumber: '',
    deyu:'',
    zhiyu: '',
    tiyu: '',
    meiyu:'',
    laoyu: '',
    parentEvaluate: '',
    teacherEvaluate:''
  }
  export default {
    name: "ScoreDetail",
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        student: Object.assign({}, defaultStudent),
        // rules: {
        //   name: [
        //     {required: true, message: '请输入学生姓名', trigger: 'blur'},
        //     {min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur'}
        //   ],
        //   snumber: [
        //     {required: true, message: '请输入学生学号', trigger: 'blur'},
        //     {min: 2, max: 50, message: '长度在 10 到 50 个字符', trigger: 'blur'}
        //   ]
        // }
      }
    },
    created() {
      if (this.isEdit) {
          getScore(this.$route.query.id).then(response => {
          this.student = response.data;
        });
      } else {
        this.student = Object.assign({}, defaultStudent);
      }
    },
    methods: {
      onSubmit(formName) {
        console.log(this.$route.query.id, this.student);
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$confirm('是否提交数据', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              if (this.isEdit) {

                updateScore(this.student).then(response => {
                  this.$refs[formName].resetFields();
                  this.$message({
                    message: '修改成功',
                    type: 'success',
                    duration:1000
                  });
                  this.$router.back();
                });
              } else {
                createStudent(this.student).then(response => {
                  this.$refs[formName].resetFields();
                  this.student = Object.assign({},defaultStudent);
                  this.$message({
                    message: '提交成功',
                    type: 'success',
                    duration:1000
                  });
                });
              }
            });

          } else {
            this.$message({
              message: '验证失败',
              type: 'error',
              duration:1000
            });
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
        this.student = Object.assign({},defaultStudent);
      }
    }
  }
</script>
