<template>
    <div class="app-container">

  <el-form label-width="120px">
  <el-form-item label="教案标题" ><el-input v-model="teachingProgramSet.title" /></el-form-item>
          
  <el-form-item label="教案编号"><el-input v-model="teachingProgramSet.code" /></el-form-item>
          
  <el-form-item label="制定人"><el-input v-model="teachingProgramSet.username" /></el-form-item>

  <el-form-item label="创建时间"><el-input v-model="teachingProgramSet.createTime" /></el-form-item>

  <el-form-item label="修改时间"><el-input v-model="teachingProgramSet.updateTime" /></el-form-item>

    <el-form-item label="概要"><el-input v-model="teachingProgramSet.outline" /></el-form-item>

    <el-form-item label="内容"><el-input v-model="teachingProgramSet.content" /></el-form-item>
  <el-form-item>
    <el-button type="primary" @click="saveOrUpdate">保存</el-button>
  </el-form-item>
</el-form>
     
</div>
</template>


<script>

  import teachingProgram from '@/api/teaching_program'

  export default {
    data () {
      return {
        teachingProgramSet: {}

      }
    },
    created () {
      //获取路由id值 调用接口得到教案信息
      if (this.$route.params && this.$route.params.code) {
        const id = this.$route.params.code
        this.getSet(id)
      } else {
        // 表单数据清空
        this.teachingProgramSet = {}
      }
    },
    methods: {
      saveOrUpdate () {
        //判断添加还是修改
        if (!this.teachingProgramSet.id) { //没有id，做添加
          this.save();
        } else {//修改
          this.update()
        }
      },
      // 添加
      save () {
        teachingProgram.saveTeachingProgram(this.teachingProgramSet)
          .then(response => {
            //提示
            this.$message({
              type: 'success',
              message: '添加成功!'
            })
            //跳转列表页面，使用路由跳转方式实现
            this.$router.push({ path: '/teaching_program/list' })
          })
      },
      //修改
      update () {
        teachingProgram.updateTeachingProgram(this.hospitalSet)
          .then(response => {
            //提示
            this.$message({
              type: 'success',
              message: '修改成功!'
            })
            //跳转列表页面，使用路由跳转方式实现
            this.$router.push({ path: '/teaching_program/list' })
          })
      },
      //根据id查询
      getSet (id) {
        teachingProgram.getTeachingProgramById(id)
          .then(response => {
            this.teachingProgramSet = response.data
          })
      },
    }
  }
</script>
