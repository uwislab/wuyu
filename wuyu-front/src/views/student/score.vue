<template>
   <div class="app-container">

 <!--<el-form label-width="120px">
    <el-form-item label="学生姓名"><el-input v-model="" /></el-form-item>
            
    <el-form-item label="学生学号"><el-input v-model="" /></el-form-item>
            
    <el-form-item label="学生性别"><el-input v-model="" /></el-form-item>

    <el-form-item label="学生班级"><el-input v-model="" /></el-form-item>

    <el-form-item label="德育成绩"><el-input v-model="" /></el-form-item>

    <el-form-item label="智育成绩"><el-input v-model="" /></el-form-item>

    <el-form-item label="体育成绩"><el-input v-model="" /></el-form-item>

    <el-form-item label="美育成绩"><el-input v-model="" /></el-form-item>

    <el-form-item label="劳育成绩"><el-input v-model="" /></el-form-item>

  </el-form>
-->
</div>
</template>


<script>


  export default {
    data () {
      return {
     }
    },
    created () {
       if (this.$route.params && this.$route.params.id) {
         const id = this.$route.params.id
       }
    },
    methods: {
      getSet (id) {
        teachingProgram.getTeachingProgramById(id).then(response => {
        this.teachingProgramSet = response.data
          })
       },
    }
  }
</script>
