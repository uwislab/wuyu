<template>
  <div class="app-container">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>排课管理</span>
      </div>
      <el-button type="primary" @click="showTeacherSel">教师检索</el-button>
    </el-card>

    <!-- 教师检索弹窗 -->
    <teacher-sel :visible.sync="teacherSelVisible" />
  </div>
</template>

<script>
import TeacherSel from '@/components/TeacherSel'

export default {
  name: 'CourseSchedule',
  components: {
    TeacherSel
  },
  data() {
    return {
      teacherSelVisible: false
    };
  },
  methods: {
    showTeacherSel() {
      this.teacherSelVisible = true
    }
  }
};
</script>

<style lang="scss" scoped>
.app-container {
  padding: 20px;
}
.box-card {
  margin-bottom: 20px;
}
</style> 