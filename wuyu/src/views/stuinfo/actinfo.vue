<template>
  <el-card class="form-container" shadow="never">
    <el-form :model="actinfo"  ref="viewFrom" label-width="150px">
      <el-form-item label="ID：" prop="name">
        <el-input v-model="actinfo.id"></el-input>
      </el-form-item>
      <el-form-item label="姓名：" prop="name">
        <el-input v-model="actinfo.name"></el-input>
      </el-form-item>
      <el-form-item label="学号：" prop="name">
        <el-input v-model="actinfo.snumber"></el-input>
      </el-form-item>
      <el-form-item label="班级号：" prop="name">
        <el-input v-model="actinfo.classId"></el-input>
      </el-form-item>
      <el-form-item label="活动名：" prop="name">
        <el-input v-model="actinfo.theme"></el-input>
      </el-form-item>
      <el-form-item label="教师评价：" prop="name">
        <el-input v-model="actinfo.teacherremark"></el-input>
      </el-form-item>
      <el-form-item label="家长评价：" prop="name">
        <el-input v-model="actinfo.parentremark"></el-input>
      </el-form-item>
      <el-form-item label="教师评分：">
        <el-input type="textarea" v-model="actinfo.teacherscore"></el-input>
      </el-form-item>
      <el-form-item label="家长评分：">
        <el-input type="textarea" v-model="actinfo.parentscore"></el-input>
      </el-form-item>
      <el-form-item label="活动类型：">
        <el-input type="textarea" v-model="actinfo.themetype"></el-input>
      </el-form-item>
      <el-form-item label="活动描述：">
        <el-input type="textarea" v-model="actinfo.themecontent"></el-input>
      </el-form-item>
      <el-form-item label="考试成绩：">
        <el-input type="textarea" v-model="actinfo.examJoinScore"></el-input>
      </el-form-item>

    </el-form>
  </el-card>
</template>

<script>
  import { fetchAllViewList, getView,deleteView ,updateView ,fetchViewList} from '@/api/classview'

  const defaultView= {
    id: '',
    name: '',
    snumber: '',
    classId: '',
    theme:'',
    teacherremark:'',
    parentremark:'',
    teacherscore:'',
    parentscore:'',
    themetype:'',
    themecontent:'',
    examJoinScore:''
  }
  export default {
    name: 'actinfo',
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data(){
      return {
        actinfo: Object.assign({},defaultView),
      }
    },
    created() {
        getView(this.$route.query.id).then(response => {
          console.log(response);
          this.actinfo = response.data;
        });
    },
    methods:{
      resetForm(formName) {
        this.$refs[formName].resetFields();
        this.actinfo = Object.assign({},defaultView);
      }
    },

  }
</script>

<style scoped>

</style>
